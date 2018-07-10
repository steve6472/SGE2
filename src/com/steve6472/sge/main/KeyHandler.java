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

import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import com.steve6472.sge.main.callbacks.CharCallback;
import com.steve6472.sge.main.callbacks.KeyCallback;
import com.steve6472.sge.main.events.CharEvent;
import com.steve6472.sge.main.events.KeyEvent;

public class KeyHandler
{
	private final long window;
	
	GLFWKeyCallback keyCallback;
	GLFWCharCallback charCallback;
	
	List<KeyCallback> keyCallbacks;
	List<CharCallback> charCallbacks;

	public KeyHandler(long window, MainApplication mainApp)
	{
		this.window = window;
		
		keyCallbacks = new ArrayList<KeyCallback>();
		charCallbacks = new ArrayList<CharCallback>();

		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() 
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods)
			{
				keyCallbacks.forEach(c -> c.invoke(key, scancode, action, mods));
				mainApp.getEventHandler().runEvent(new KeyEvent(key, scancode, action, mods));
			}
		});
		
		glfwSetCharCallback(window, charCallback = new GLFWCharCallback()
		{
			public void invoke(long window, int codepoint)
			{
				charCallbacks.forEach(c -> c.invoke(codepoint));
				mainApp.getEventHandler().runEvent(new CharEvent(codepoint));
			}
		});
		
	}
	
	public boolean isKeyPressed(int key)
	{
		return glfwGetKey(window, key) == GLFW_TRUE;
	}
	
	public void addKeyCallback(KeyCallback callback)
	{
		this.keyCallbacks.add(callback);
	}
	
	public void addCharCallback(CharCallback callback)
	{
		this.charCallbacks.add(callback);
	}
	
	public GLFWKeyCallback getKeyCallback()
	{
		return keyCallback;
	}
	
	public GLFWCharCallback getCharCallback()
	{
		return charCallback;
	}

}
