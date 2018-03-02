/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge2.test;

//import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import com.steve6472.sge2.main.MainApplication;
import com.steve6472.sge2.main.gfx.Screen;
import com.steve6472.sge2.main.gfx.Sprite;

public class Test extends MainApplication
{
	public static Sprite sprite, cursor, sprite2;

	@Override
	public void init()
	{
//		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
		cursor = new Sprite("*sprite.png");
		sprite = new Sprite("*grass.png");
		sprite2 = new Sprite("grass.png");

		new TestGui(this);
	}

	@Override
	public void tick()
	{
		tickGui();
	}

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
	}

	@Override
	public void setWindowHints()
	{
//		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
//		glfwWindowHint(GLFW_FLOATING, GLFW_TRUE);
		glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
	}

	@Override
	public int getWidth()
	{
		return 16 * 40;
	}

	@Override
	public int getHeight()
	{
		return 9 * 40;
	}
	
	@Override
	public String getTitle()
	{
		return "LWJGL";
	}
	
	public static void main(String[] args)
	{
		new Test();
	}

}
