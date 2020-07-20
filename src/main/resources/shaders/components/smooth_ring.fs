#version 330 core

in vec2 vTex;

out vec4 fragColor;

uniform vec4 fill;
uniform float hole;
uniform float softness;

void main()
{
	vec2 pos = vTex.xy - vec2(0.5);
	float len = length(pos) * 2.0;

    float t = 1.0 + smoothstep(hole, hole + softness, len)
                  - smoothstep(hole - softness, hole, len);

    float f = 1.0 + smoothstep(hole, hole + softness / 2.0, len)
                  - smoothstep(hole - softness / 2.0, hole, len);

    float r = fill.r + smoothstep(0.0, 1.0 / fill.r, 1 - f);
    float g = fill.g + smoothstep(0.0, 1.0 / fill.g, 1 - f);
    float b = fill.b + smoothstep(0.0, 1.0 / fill.b, 1 - f);

	fragColor = vec4(r, g, b, (1 - t) - (1 - fill.a));
}














/*

void main()
{
	vec2 pos = vTex.xy - vec2(0.5);
	float len = length(pos) * 2.0;

    float t = 1.0 + smoothstep(hole, hole + softness, len)
                  - smoothstep(hole - softness, hole, len);

    float f = 1.0 + smoothstep(hole, hole + softness / 2.0, len)
                  - smoothstep(hole - softness / 2.0, hole, len);

    float r = fill.r + smoothstep(0.0, 1.0 / fill.r, 1 - f);
    float g = fill.g + smoothstep(0.0, 1.0 / fill.g, 1 - f);
    float b = fill.b + smoothstep(0.0, 1.0 / fill.b, 1 - f);

	fragColor = vec4(r, g, b, (1 - t) - (1 - fill.a));
}



void main()
{
	vec2 pos = vTex.xy - vec2(0.5);
	float len = length(pos) * 2.0;

    vec4 back = vec4(0.0, 0.0, 0.0, 0.0);
    vec4 outer_fade = vec4(0.0, 0.0, 1.0, 1.0);
    vec4 middle = vec4(1.0, 1.0, 1.0, 1.0);
    vec4 inner_fade = vec4(0.0, 0.0, 1.0, 1.0);
    vec4 inner = vec4(0.0, 0.0, 0.0, 0.0);
    float step1 = 0.3;
    float step2 = 0.45;
    float step3 = 0.5;
    float step4 = 0.55;
    float step5 = 0.7;

    vec4 color = mix(inner, inner_fade, smoothstep(step1, step2, len));
    color = mix(color, middle, smoothstep(step2, step3, len));
    color = mix(color, outer_fade, smoothstep(step3, step4, len));
    color = mix(color, back, smoothstep(step4, step5, len));

    gl_FragColor = color;
}





	vec2 pos = vTex.xy - vec2(0.5);
	float len = length(pos) * 2.0;

    float t = 1.0 + smoothstep(hole, hole + softness, len)
                  - smoothstep(hole - softness, hole, len);
	//fragColor = vec4(fill.rgb, (1 - t) - (1 - fill.a));


    float r = smoothstep(0.0, 1.0 / fill.r, 1 - t);
    float g = smoothstep(0.0, 1.0 / fill.g, 1 - t);
    float b = smoothstep(0.0, 1.0 / fill.b, 1 - t);
	fragColor = vec4(fill.rgb * (1 - t), 1);

	*/