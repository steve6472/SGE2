/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 13. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gfx;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.steve6472.sge.main.Util;

/**
 * 
 * Source copied and modified from https://www.youtube.com/watch?v=q_dS3JuoeDw
 * 
 * @author ElegantWhelp (I guess)
 *
 */
public class Shader
{
	private int program;
	private int vs;
	private int fs;
	
	public Shader(String fileName)
	{
		program = glCreateProgram();
		
		vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, readFile(fileName + ".vs"));
		
		glCompileShader(vs);
		
		if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1)
		{
			System.err.println(glGetShaderInfoLog(vs));
			System.exit(1);
		}
		
		
		fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, readFile(fileName + ".fs"));
		
		glCompileShader(fs);
		
		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1)
		{
			System.err.println(glGetShaderInfoLog(fs));
			System.exit(1);
		}

		glAttachShader(program, vs);
		glAttachShader(program, fs);

		glBindAttribLocation(program, 0, "vertices");
		glBindAttribLocation(program, 1, "textures");
		
		glLinkProgram(program);
		
		if (glGetProgrami(program, GL_LINK_STATUS) != 1)
		{
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		
		glValidateProgram(program);
		
		if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1)
		{
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		
		System.out.println("Created shader from " + fileName + " vs:" + vs + " fs:" + fs + " program:" + program);
	}
	
	public Shader setUniform1f(String name, float v1)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform1f(location, v1);
		return this;
	}
	
	public Shader setUniform2f(String name, float v1, float v2)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform2f(location, v1, v2);
		return this;
	}
	
	public Shader setUniform3f(String name, float v1, float v2, float v3)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform3f(location, v1, v2, v3);
		return this;
	}
	
	public Shader setUniform4f(String name, float v1, float v2, float v3, float v4)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform4f(location, v1, v2, v3, v4);
		return this;
	}
	
	public Shader setUniform1i(String name, int v1)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform1i(location, v1);
		return this;
	}
	
	public Shader setUniform2i(String name, int v1, int v2)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform2i(location, v1, v2);
		return this;
	}
	
	public Shader setUniform3i(String name, int v1, int v2, int v3)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform3i(location, v1, v2, v3);
		return this;
	}
	
	public Shader setUniform4i(String name, int v1, int v2, int v3, int v4)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform4i(location, v1, v2, v3, v4);
		return this;
	}
	
	public Shader setUniformMat4f(String name, Matrix4f m1)
	{
		int location = glGetUniformLocation(program, name);
		FloatBuffer b1 = BufferUtils.createFloatBuffer(16);
		m1.get(b1);
		if (location != -1)
			glUniformMatrix4fv(location, false, b1);
		return this;
	}
	
	public void bind()
	{
		glUseProgram(program);
	}
	
	private String readFile(String file)
	{
		String[] arr = Util.loadDataFromFile(file);
		StringBuilder string = new StringBuilder();
		for (String s : arr)
		{
			string.append(s);
			string.append("\n");
		}
		return string.toString();
	}
}
