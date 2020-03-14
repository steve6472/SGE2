#version 330 core

//#include shaders\\rng.glsl

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texture;
layout(location = 2) in vec4 color;
layout(location = 3) in vec3 normal;

uniform mat4 transformation;
uniform mat4 projection;
uniform mat4 view;
uniform vec3 lightPosition[4];
uniform float rng;
uniform float move;

out vec4 vColor;
out vec2 vTexture;
out vec3 surfaceNormal;
out vec3 toLightVector[4];
out vec3 toCameraVector;

void main()
{
    vColor = color;
    vTexture = texture;
	
	vec4 worldPosition = transformation * vec4(position, 1.0);
	//worldPosition.y += noise(worldPosition.x + move, worldPosition.z) * rng;
	//worldPosition.y += getWindowHeight(int(worldPosition.x) + int(move), int(worldPosition.z)) * rng;
	//worldPosition.y = getWindowHeight(int(worldPosition.x) + int(move), int(worldPosition.z)) * rng;
	
	surfaceNormal = (transformation * vec4(normal, 0.0)).xyz;
	
	for (int i = 0; i < 4; i++)
	{
		toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	}
	
	toCameraVector = (inverse(view) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
	
    gl_Position = projection * view * worldPosition;
}