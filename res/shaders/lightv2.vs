#version 330 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 tex;
layout(location = 2) in vec4 color;

out vec4 vColor;
out vec2 vTexture;
out vec2 vPosition;

void main()
{
	vPosition = position;
    vColor = color;
    vTexture = tex;
    
    gl_Position = vec4(position, 0.0, 1.0);
}