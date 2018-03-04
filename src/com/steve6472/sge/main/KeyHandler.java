/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.main;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyHandler
{
	private final long window;
	
	GLFWKeyCallback keyCallback;
	
	List<KeyCallback> callbacks;

	public KeyHandler(long window)
	{
		this.window = window;
		
		callbacks = new ArrayList<KeyCallback>();

		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() 
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods)
			{
				callbacks.forEach(c -> c.invoke(key, scancode, action, mods));
			}
		});
	}
	
	public boolean isKeyPressed(int key)
	{
		return glfwGetKey(window, key) == GLFW_TRUE;
	}
	
	public void addKeyCallback(KeyCallback callback)
	{
		this.callbacks.add(callback);
	}
	
	public GLFWKeyCallback getKeyCallback()
	{
		return keyCallback;
	}

}
