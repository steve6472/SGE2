/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 3. 6. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gfx;

public class SavedShaders
{
	public static final String TESS_FS = "" +
			  "#version 330 core														\n"
			
			+ "uniform sampler2D sampler;												\n"
			+ "uniform vec2 texture;													\n"
			+ "uniform vec4 col;														\n"
			+ ""
			+ "in vec4 vColor;															\n"
			+ "in vec2 vTexture;														\n"
			
			+ "out vec4 fragColor;														\n"
			
			+ "void main()																\n"
			+ "{																		\n"
			+ "		fragColor = texture2D(sampler, vTexture + texture) + col + vColor;	\n"
			+ "}																		\n";

	public static final String TESS_VS = "" +
			  "#version 330 core														\n"
			
			+ "layout(location = 0) in vec2 position;									\n"
			+ "layout(location = 1) in vec2 texture;									\n"
			+ "layout(location = 2) in vec4 color;										\n"
			
			+ "uniform mat4 projection;													\n"
			
			+ "out vec4 vColor;															\n"
			+ "out vec2 vTexture;														\n"
			
			+ "void main()																\n"
			+ "{																		\n"
			+ "    vColor = color;														\n"
			+ "    vTexture = texture;													\n"
			+ "    gl_Position = projection * vec4(position, 0.0, 1.0);					\n"
			+ "}																		\n";
	
	

	public static final String TILE_FS = "" +
			  "#version 330 core														\n"
			
			+ "uniform sampler2D sampler;												\n"
			+ "uniform vec2 texture;													\n"
			
			+ "in vec2 vTexture;														\n"
			
			+ "out vec4 fragColor;														\n"
			
			+ "void main()																\n"
			+ "{																		\n"
			+ "		fragColor = texture2D(sampler, vTexture + texture);					\n"
			+ "}																		\n";

	public static final String TILE_VS = "" +
			  "#version 330 core														\n"
			
			+ "layout(location = 0) in vec2 position;									\n"
			+ "layout(location = 1) in vec2 texture;									\n"
			
			+ "uniform mat4 projection;													\n"
			
			+ "out vec2 vTexture;														\n"
			
			+ "void main()																\n"
			+ "{																		\n"
			+ "    vTexture = texture;													\n"
			+ "    gl_Position = projection * vec4(position, 0.0, 1.0);					\n"
			+ "}																		\n";
	
	
	

	
	public static final String SHADER_3D_VS = ""
			+ "#version 330 core														\n"
			
			+ "layout(location = 0) in vec3 position;									\n"
			+ "layout(location = 1) in vec2 texture;									\n"
			+ "layout(location = 2) in vec4 color;										\n"
			
			+ "uniform mat4 transformation;												\n"
			+ "uniform mat4 projection;													\n"
			
			+ "out vec4 vColor;															\n"
			+ "out vec2 vTexture;														\n"
			
			+ "void main()																\n"
			+ "{																		\n"
			+ "    vColor = color;														\n"
			+ "    vTexture = texture;													\n"
			+ "    gl_Position = projection * transformation * vec4(position, 1.0);		\n"
			+ "}";
	
	public static final String SHADER_3D_FS = ""
			+ "#version 330 core														\n"
			
			+ "uniform sampler2D sampler;												\n"
			+ "uniform vec2 texture;													\n"
			+ "uniform vec4 col;														\n"
			
			+ "in vec4 vColor;															\n"
			+ "in vec2 vTexture;														\n"
			
			+ "out vec4 fragColor;														\n"
			
			+ "void main()																\n"
			+ "{																		\n"
			+ "		fragColor = texture2D(sampler, vTexture + texture) + col;			\n"
			+ "}																		\n";
}
