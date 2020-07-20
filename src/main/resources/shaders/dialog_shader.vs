#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texture;

uniform mat4 transformation;
uniform mat4 projection;
uniform mat4 view;

out vec2 vTexture;

void main()
{
	vTexture = texture;
	gl_Position = projection * view * transformation * vec4(position, 1.0);
}