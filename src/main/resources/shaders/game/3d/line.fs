#version 330 core

out vec4 outTexture;

in vec4 vColor;

void main()
{
	outTexture = vColor;
}