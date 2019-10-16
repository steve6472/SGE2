#version 330 core

uniform sampler2D sampler;

in vec2 vTex;

out vec4 fragColor;

void main()
{
	vec4 t = texture(sampler, vTex);
	if (t.a == 0) discard;
	fragColor = t;
}