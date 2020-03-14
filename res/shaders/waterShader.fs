#version 330 core

uniform sampler2D sampler;

uniform float maxLight;
uniform vec3 lightColor[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 attenuation[4];

in vec4 vColor;
in vec2 vTexture;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;

out vec4 fragColor;

void main()
{
	//fragColor = vec4(surfaceNormal / vec3(1.0), 1.0);
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitVectorToCamera = normalize(toCameraVector);
	
	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);
	
	for (int i = 0; i < 4; i++)
	{
		float distance = length(toLightVector[i]);
		//float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		float attFactor = 1;
		vec3 unitLightVector = normalize(toLightVector[i]);
		float nDot = dot(unitNormal, unitLightVector);
		float brightness = max(nDot, 0.0);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor, shineDamper);
		totalDiffuse = totalDiffuse + (brightness * lightColor[i]) / attFactor;
		totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i]) / attFactor;
	}
	
	totalDiffuse = max(totalDiffuse, maxLight);

	
	fragColor = vec4(totalDiffuse, 1.0) * vColor + vec4(totalSpecular, 1.0);
}








