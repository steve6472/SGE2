#version 120

attribute vec3 vertices;
uniform vec3 xyz;
uniform vec3 wh;
uniform vec3 col;

void main()
{
	gl_Position = vec4(vertices * xyz + vec2(wh.x, wh.y), 1);
}