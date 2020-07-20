#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texture;
layout(location = 2) in vec4 color;

uniform mat4 transformation;
uniform mat4 projection;
uniform mat4 view;

out vec4 vColor;
out vec2 vTexture;

void main()
{
    vColor = color;
    vTexture = texture;
    gl_Position = projection * view * transformation * vec4(position, 1.0);
}