/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 13. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL;

import com.steve6472.sge.gfx.Model;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Shader;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.callbacks.MouseButtonCallback;
import com.steve6472.sge.main.game.Vec2;

public class ShaderTest extends MainApplication
{
	float[] vertices;
	float[] texture;
	float[] colors;
	
	float red;
	float green;
	float blue;
	float alpha;
	float radius;
	float softness;
	
	/**
	 * 0 = red
	 * 1 = green
	 * 2 = blue
	 * 3 = radius
	 * 4 = softness
	 * 5 = alpha
	 */
	byte mode = 0;
	
	List<Light> lights;

	@Override
	public void tick()
	{
		if (isKeyPressed(GLFW_KEY_R))
			mode = 0;
		else if (isKeyPressed(GLFW_KEY_G))
			mode = 1;
		else if (isKeyPressed(GLFW_KEY_B))
			mode = 2;
		else if (isKeyPressed(GLFW_KEY_F))
			mode = 3;
		else if (isKeyPressed(GLFW_KEY_S))
			mode = 4;
		else if (isKeyPressed(GLFW_KEY_A))
			mode = 5;

		if (isKeyPressed(GLFW_KEY_KP_ADD))
		{
		if (mode == 0)
			red = Math.min(1f, red + 0.01f);
		else if (mode == 1)
			green = Math.min(1f, green + 0.01f);
		else if (mode == 2)
			blue = Math.min(1f, blue + 0.01f);
		else if (mode == 3)
			radius += 0.01f;
		else if (mode == 4)
			softness += 0.01f;
		else if (mode == 5)
			alpha = Math.min(1f, alpha + 0.01f);
		}
		
		if (isKeyPressed(GLFW_KEY_KP_SUBTRACT))
		{
			if (mode == 0)
				red = Math.max(0f, red - 0.01f);
			else if (mode == 1)
				green = Math.max(0f, green - 0.01f);
			else if (mode == 2)
				blue = Math.max(0f, blue - 0.01f);
			else if (mode == 3)
				radius = Math.max(radius - 0.01f, 0);
			else if (mode == 4)
				softness -= 0.01f;
			else if (mode == 5)
				alpha = Math.max(0f, alpha - 0.01f);
		}
		
		glfwSetWindowTitle(window, "R: " + red + " G:" + green + " B:" + blue + " A:" + alpha + " Rad:" + radius + " S:" + softness);
	}

	Shader lightShader, base;
	Model back, lightModel;
	Sprite sprite;

	@Override
	public void init()
	{
		red = 1f;
		green = 1f;
		blue = 1f;
		alpha = 0.25f;
		radius = 0.5f;
		
		lights = new ArrayList<Light>();
		createBigQuad();
		lightShader = new Shader("shaders\\lightv2");
		base = new Shader("shaders\\base");
		sprite = new Sprite("*grass.png");
		back = new Model(fillScreen(), createBasicTexture(), createNonColoredArray());
		lightModel = new Model(fillScreen(), createBasicTexture(), createNonColoredArray());
		
		getMouseHandler().addMouseButtonCallback(new MouseButtonCallback()
		{
			@Override
			public void invoke(int x, int y, int button, int action, int mods)
			{
				if (action == GLFW_PRESS)
					lights.add(new Light(red, green, blue, radius, new Vec2(x, -y + getCurrentHeight())));
			}
		});
	}

	private void createBigQuad()
	{
		vertices = fillScreen();
		
		texture = createBasicTexture();
		
		colors = createNonColoredArray();
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
	
	@Override
	public void render(Screen screen)
	{
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		{
			sprite.bind();

			base.bind();
			base.setUniform1i("sampler", 0);
			
			back.render();
		}
		{
			float mouseX = getMouseX();
			float mouseY = getMouseY();
//			float width = getCurrentWidth();
			float height = getCurrentHeight();
			
			for (Light l : lights)
			{
				renderLight((float) l.location.getX(), (float) l.location.getY(), l.radius, l.red, l.green, l.blue);
			}
			
			renderLight(mouseX, -mouseY + height, radius, red, green, blue);
		}
	}
	
	public void renderLight(float X, float Y, float radius, float red, float green, float blue)
	{
		lightShader.bind();
		lightShader.setUniform2f("res", getCurrentWidth(), getCurrentHeight());
		lightShader.setUniform1f("radius", radius);
		lightShader.setUniform1f("softness", radius - softness);
		lightShader.setUniform4f("lightColor", red, green, blue, alpha);
		
		lightShader.setUniform2f("lightLocation", X, Y);
		
		lightModel.render();
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
		return 32 * 16;
	}

	@Override
	public int getHeight()
	{
		return 32 * 16;
	}

	@Override
	public void exit()
	{
		glfwWindowShouldClose(window);
	}

	@Override
	public String getTitle()
	{
		return "Shader test";
	}

	public static void main(String[] args)
	{
		new ShaderTest();
	}

	class Light
	{
		float red;
		float green;
		float blue;
		float radius;
		Vec2 location;
		
		public Light(float red, float green, float blue, float radius, Vec2 location)
		{
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.radius = radius;
			this.location = location;
			System.out.println("Created new light " + red + " " + green + " " + blue + " " + radius + " " + location);
		}
	}
}
