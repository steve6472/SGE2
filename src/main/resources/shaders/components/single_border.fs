#version 330 core

in vec2 vTex;

out vec4 fragColor;

uniform vec4 border;
uniform vec4 fill;

uniform vec2 size;

void main()
{
	float width = size.x;
	float height = size.y;
	
	float w = 2.0 / width;
	float h = 2.0 / height;
	
	if (vTex.x < w || vTex.x > 1 - w || vTex.y < h || vTex.y > 1 - h)
	{
		fragColor = border;
	} else
	
	{
		fragColor = fill;
	}
}