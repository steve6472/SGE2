/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 13. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import com.steve6472.sge.gfx.Model;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Shader;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.Util;

public class ShaderTest2 extends MainApplication
{

	Shader base;
	Model back;
	Sprite sprite;
	public static Sprite smallAtlas;
	public static Model matModel;
	public static Shader matTest;
	
	float scale;
	float transX;
	float transY;
	float speed;
	
	Camera camera;
	Chunk chunk;

	@Override
	public void init()
	{
		matTest = new Shader("shaders\\basev2");
		base = new Shader("shaders\\base");
		sprite = new Sprite("*grass.png");
		smallAtlas = new Sprite("*smallAtlas.png");
		back = new Model(fillScreen(), createBasicTexture(), createNonColoredArray());
		matModel = new Model(fillScreen(), createTexture(32f, 32f, smallAtlas), createNonColoredArray());
		
		scale = 16;
		speed = 1f / 32f;
		camera = new Camera();
		camera.setSize(getCurrentWidth(), getCurrentHeight());
		chunk = new Chunk(this, camera);
	}
	
	int oldW;
	int oldH;
	boolean updateSize = false;

	@Override
	public void tick()
	{
		if (isKeyPressed(GLFW_KEY_KP_ADD))
			scale += 1;
		if (isKeyPressed(GLFW_KEY_KP_SUBTRACT))
			scale -= 1;

		if (isKeyPressed(GLFW_KEY_W))
			transY -= speed;
		if (isKeyPressed(GLFW_KEY_S))
			transY += speed;
		if (isKeyPressed(GLFW_KEY_A))
			transX -= speed;
		if (isKeyPressed(GLFW_KEY_D))
			transX += speed;
		
		if (oldW != getCurrentWidth())
		{
			oldW = getCurrentWidth();
			updateSize = true;
		}
		
		if (oldH != getCurrentHeight())
		{
			oldH = getCurrentHeight();
			updateSize = true;
		}
		
		if (updateSize)
		{
			camera.setSize(getCurrentWidth(), getCurrentHeight());
		}
		
		scale = Util.getNumberBetween(16, 128, scale);
		
		chunk.x = transX;
		chunk.y = transY;
		chunk.scale = scale;
		
		glfwSetWindowTitle(window, /*"UPS:" + getFPS() + */"Scale:" + scale + " Speed:" + speed);
	}
	
	@Override
	public void render(Screen screen)
	{
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		{
//			sprite.bind();

//			base.bind();
//			base.setUniform1i("sampler", 0);
//			
//			back.render();
		}
		{
//			float mouseX = getMouseX();
//			float mouseY = getMouseY();
//			float width = getCurrentWidth();
//			float height = getCurrentHeight();
			
//			Matrix4f projection = new Matrix4f()
//					.ortho2D(getCurrentWidth() / 2, -getCurrentWidth() / 2, -getCurrentHeight() / 2, getCurrentHeight() / 2)
//					.scale(scale)
//					.translate(transX, transY, 0);
//			
//			matTest.bind();
//			matTest.setUniform1i("sampler", 0);
//			matTest.setUniformMat4f("projection", projection);
//			matTest.setUniform2f("texture", 0.25f, 0.25f);
//			
//			matModel.render();
			chunk.render();
		}
	}

	@Override
	protected void preLoop()
	{
		GL.createCapabilities();
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glMatrixMode(GL_MODELVIEW);
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}

	@Override
	public void setWindowHints()
	{
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
	}

	@Override
	public int getWidth()
	{
		return 70 * 16;
	}

	@Override
	public int getHeight()
	{
		return 70 * 9;
	}

	@Override
	public void exit()
	{
		glfwWindowShouldClose(window);
	}

	@Override
	public String getTitle()
	{
		return "Shader test2";
	}

	public static void main(String[] args)
	{
		new ShaderTest2();
	}
	
	public static float[] createTexture(float w, float h, Sprite sprite)
	{
		float W = w / (float) sprite.getWidth();
		float H = h / (float) sprite.getHeight();
		System.out.println(W + " " + H);
		
		return new float[]
				{
					W, H,
					0, H,
					0, 0,
					
					0, 0,
					W, 0,
					W, H,
				};
	}
	
	public static float[] createBasicTexture()
	{
		float[] texture = new float[]
				{
					1, 1,
					0, 1,
					0, 0,
					
					0, 0,
					1, 0,
					1, 1,
				};
		return texture;
	}
	
	public static float[] createNonColoredArray()
	{
		return new float[]
				{
					0, 0, 0, 1,
					0, 0, 0, 1,
					0, 0, 0, 1,
					
					0, 0, 0, 1,
					0, 0, 0, 1,
					0, 0, 0, 1
				};
	}
	
	public static float[] createColorfulModel()
	{
		return new float[]
				{
					1, 0, 0, 1,
					0, 1, 0, 1,
					0, 0, 1, 1,
					
					0, 0, 1, 1,
					1, 0, 1, 1,
					1, 0, 0, 1
				};
	}
	
	public static float[] createBigTriangle()
	{
		return new float[]
				{
					+0.0f, +0.8f,    // Top coordinate
		            -0.8f, -0.8f,    // Bottom-left coordinate
		            +0.8f, -0.8f     // Bottom-right coordinate
				};
	}

	public static float[] fillScreen()
	{
		return new float[]
					{
						-1, -1,
						1, -1,
						1, 1,
						
						1, 1,
						-1, 1,
						-1, -1
					};
	}
}
