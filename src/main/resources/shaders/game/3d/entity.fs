#version 330 core

#define AMBIENT 0.5
#define XFAC -0.15
#define ZFAC 0.05

out vec4 outTexture;

in vec4 vColor;
in vec2 vTexture;
in vec3 vNormal;

uniform sampler2D atlas;

void main()
{
	vec4 tex = texture(atlas, vTexture) * vColor;

	if (tex.a == 0)
		discard;

	float yLight = (1.0 + vNormal.y) * 0.5;
	float light = yLight * (1.0 - AMBIENT) + vNormal.x * vNormal.x * XFAC + vNormal.z * vNormal.z * ZFAC + AMBIENT;

	tex.rgb *= light;

	outTexture = tex;
}