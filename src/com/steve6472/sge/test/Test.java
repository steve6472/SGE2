/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.test;

//import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.main.MainApplication;

public class Test extends MainApplication
{
	public static Sprite sprite, cursor, sprite2, hsv;

	@Override
	public void init()
	{
//		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
		cursor = new Sprite("*sprite.png");
		sprite = new Sprite("*grass.png");
		hsv = new Sprite("*hsv.png");
		sprite2 = new Sprite("grass.png");

		new TestGui(this);
	}

	@Override
	public void tick()
	{
		tickGui();
	}
	
	double shake;

	@Override
	public void render(Screen screen)
	{
//		screen.drawRect(getMouseX() - 16, getMouseY() - 16, 32, 32, 2, 0xff00ff00);
//		for (int i = 0; i < 43; i++)
//		{
//			for (int j = 0; j < 24; j++)
//			{
//				screen.drawSprite(i * sprite.getWidth(), j * sprite.getHeight(), sprite);
//			}
//		}
		
//		screen.drawSpriteRepeat(0, 0, sprite, 8, 8);
		
//		screen.drawRect(getMouseX() - 16, getMouseY() - 16, 32, 32, 2, 0xff00ff00);
		
		renderGui();
//		glPushAttrib(GL_CURRENT_BIT);
//		glColor3f(1f, 0.5f, 0.25f);
//		screen.drawSprite(getMouseX(), getMouseY(), sprite2);
//		screen.drawSprite(getMouseX(), getMouseY(), cursor);
//		glPopAttrib();
		shake += 10;
		screen.rotateScreen(getCurrentWidth() / 2, getCurrentHeight() / 2, (float) Math.cos(Math.toRadians(shake)) / 8f);
	}

	@Override
	public void setWindowHints()
	{
//		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_FLOATING, GLFW_TRUE);
		glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
	}

	@Override
	public int getWidth()
	{
		return 16 * 60;
	}

	@Override
	public int getHeight()
	{
		return 9 * 60;
	}
	
	@Override
	public String getTitle()
	{
		return "LWJGL";
	}
	
	@Override
	public void exit()
	{
		glfwSetWindowShouldClose(getWindow(), true);
	}
	
	public static void main(String[] args)
	{
		new Test();
	}

}
