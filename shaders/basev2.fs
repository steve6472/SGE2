#version 330 core

uniform sampler2D sampler;
uniform vec2 texture;
uniform float angle;

in vec4 vColor;
in vec2 vTexture;

out vec4 fragColor;

void main()
{
	vec2 coord = vTexture;

    float sin_factor = sin(angle);
    float cos_factor = cos(angle);
    coord = (coord - 0.5) * mat2(cos_factor, sin_factor, -sin_factor, cos_factor);
    coord += 0.5;
        
	fragColor = texture2D(sampler, coord + texture) + vColor;
}