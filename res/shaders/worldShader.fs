#version 330 core

uniform sampler2D sampler;
uniform vec3 lightColor;
uniform float maxLight;

in vec4 vColor;
in vec2 vTexture;
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 fragColor;

void main()
{
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float nDot = dot(unitNormal, unitLightVector);
	float brightness = max(nDot, maxLight);
	
	vec3 diffuse = brightness * lightColor;

	//fragColor = vec4(diffuse, 1.0) * (texture(sampler, vTexture) + vColor);
	fragColor = vec4(diffuse, 1.0) * vColor;
}