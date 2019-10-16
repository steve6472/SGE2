/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.main;

import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class KeyHandler
{
	private final long window;
	
	public KeyHandler(long window, MainApp mainApp)
	{
		this.window = window;

	}
	
	public boolean isKeyPressed(int key)
	{
		return glfwGetKey(window, key) == GLFW_TRUE;
	}
}
