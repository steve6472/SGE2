package com.steve6472.sge.gfx;

import com.steve6472.sge.main.Util;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

/**********************
 * 
 * Source copied and modified from https://www.youtube.com/watch?v=q_dS3JuoeDw
 * 
 * @author ElegantWhelp (I guess)
 *
 * Created by steve6472 (Mirek Jozefek)
 * On date: 25.11.2018
 * Project: SJP
 *
 ***********************/
public class GeometryShader
{
	private int program;
	private int vs;
	private int gs;
	private int fs;

	public GeometryShader(String fileName)
	{
		program = glCreateProgram();

		vs = glCreateShader(GL_VERTEX_SHADER);
		String vsS = readFile(fileName + ".vs");
		glShaderSource(vs, vsS);

		glCompileShader(vs);

		if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1)
		{
			System.err.println("VS:\n" + glGetShaderInfoLog(vs));
			System.exit(1);
		}


		gs = glCreateShader(GL_GEOMETRY_SHADER);
		String gsS = readFile(fileName + ".gs");
		glShaderSource(gs, gsS);

		glCompileShader(gs);

		if (glGetShaderi(gs, GL_COMPILE_STATUS) != 1)
		{
			System.err.println("GS:\n" + glGetShaderInfoLog(gs));
			System.exit(1);
		}


		fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, readFile(fileName + ".fs"));

		glCompileShader(fs);

		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1)
		{
			System.err.println("FS:\n" + glGetShaderInfoLog(fs));
			System.exit(1);
		}

		glAttachShader(program, vs);
		glAttachShader(program, gs);
		glAttachShader(program, fs);

		glBindAttribLocation(program, 0, "vertices");
		glBindAttribLocation(program, 1, "textures");

		glLinkProgram(program);

		if (glGetProgrami(program, GL_LINK_STATUS) != 1)
		{
			System.err.println("Program:\n" + glGetProgramInfoLog(program));
			System.exit(1);
		}

		glValidateProgram(program);

		if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1)
		{
			System.err.println("Program but different:\n" + glGetProgramInfoLog(program));
			System.exit(1);
		}

//		System.out.println("Created Geometry Shader from " + fileName + " gs:" + gs + " fs:" + fs + " program:" + program);
	}

	public int getProgram()
	{
		return program;
	}

	public GeometryShader setUniform1f(String name, float v1)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform1f(location, v1);
		return this;
	}
	
	public GeometryShader setUniform2f(String name, float v1, float v2)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform2f(location, v1, v2);
		return this;
	}
	
	public GeometryShader setUniform3f(String name, float v1, float v2, float v3)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform3f(location, v1, v2, v3);
		return this;
	}
	
	public GeometryShader setUniform4f(String name, float v1, float v2, float v3, float v4)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform4f(location, v1, v2, v3, v4);
		return this;
	}
	
	public GeometryShader setUniform1i(String name, int v1)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform1i(location, v1);
		return this;
	}
	
	public GeometryShader setUniform2i(String name, int v1, int v2)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform2i(location, v1, v2);
		return this;
	}
	
	public GeometryShader setUniform3i(String name, int v1, int v2, int v3)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform3i(location, v1, v2, v3);
		return this;
	}
	
	public GeometryShader setUniform4i(String name, int v1, int v2, int v3, int v4)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform4i(location, v1, v2, v3, v4);
		return this;
	}
	
	public GeometryShader setUniformMat4f(String name, Matrix4f m1)
	{
		int location = glGetUniformLocation(program, name);
		FloatBuffer b1 = BufferUtils.createFloatBuffer(16);
		m1.get(b1);
		if (location != -1)
			glUniformMatrix4fv(location, false, b1);
		return this;
	}

	private static int currentShader;
	
	public void bind()
	{
		glUseProgram(program);
		currentShader = program;
	}
	
	public static void releaseShader()
	{
		glUseProgram(0);
		currentShader = 0;
	}

	public static void bindShader(int id)
	{
		glUseProgram(id);
		currentShader = id;
	}

	public static int getCurrentShader()
	{
		return currentShader;
	}

	public static String readFile(String file)
	{
		String[] arr = Util.loadDataFromFile(file);
		StringBuilder string = new StringBuilder();
		for (String s : arr)
		{
			if (s.startsWith("#") && s.substring(0, 8).equals("#include"))
			{
				System.out.println("Including from " + s);
				string.append(include(s));
			} else
			{
				if (s.contains("texture2D"))
					System.err.println(file + " contains Deprecated 'texture2D'");
				string.append(s);
				string.append("\n");
			}
		}
		return string.toString();
	}
	
	private static StringBuilder include(String line)
	{
		String path = line.split(" ")[1];
		String[] arr = Util.loadDataFromFile(path);
		
		StringBuilder sb = new StringBuilder();
		for (String s : arr)
		{
			if (s.startsWith("#") && s.substring(0, 8).equals("#include"))
			{
				sb.append(include(s));
			} else
			{
				if (s.contains("texture2D"))
					System.err.println("#include" + " contains Deprecated 'texture2D'");
				sb.append(s);
				sb.append("\n");
			}
		}
		
		return sb;
	}
}
