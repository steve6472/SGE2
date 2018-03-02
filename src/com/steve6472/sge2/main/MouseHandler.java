/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge2.main;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

public class MouseHandler
{
	
	DoubleBuffer mouseXBuffer;
	DoubleBuffer mouseYBuffer;
	
	private final long window;
	private final MainApplication mainApp;
	
	private boolean mouseTriggered = false;
	
	private int pressedMouseX;
	private int pressedMouseY;
	
	private int pressedMouseXOnScreen;
	private int pressedMouseYOnScreen;
	
	private int mouseXOnScreen;
	private int mouseYOnScreen;

	public MouseHandler(long window, MainApplication mainApp)
	{
		this.window = window;
		this.mainApp = mainApp;
		
		mouseXBuffer = BufferUtils.createDoubleBuffer(1);
		mouseYBuffer = BufferUtils.createDoubleBuffer(1);
	}
	
	private boolean wasHolded = false;
	
	public void tick()
	{
		glfwGetCursorPos(window, mouseXBuffer, mouseYBuffer);

		mouseXOnScreen = mainApp.getWindowX() + getMouseX();
		mouseYOnScreen = mainApp.getWindowY() + getMouseY();
		
		if (!wasHolded)
		{
			if (isMouseHolded())
			{
				pressedMouseX = getMouseX();
				pressedMouseY = getMouseY();
				pressedMouseXOnScreen = mainApp.getWindowX() + pressedMouseX;
				pressedMouseYOnScreen = mainApp.getWindowY() + pressedMouseY;
						
				wasHolded = true;
			}
		}
		
		if (!isMouseHolded())
		{
			setTrigger(false);
		}
		
		if (wasHolded && !isMouseHolded())
		{
			wasHolded = false;
		}
	}
	
	public int getMouseX()
	{
		return (int) mouseXBuffer.get(0);
	}
	
	public int getMouseY()
	{
		return (int) mouseYBuffer.get(0);
	}
	
	public int getMouseXOnScreen()
	{
		return mouseXOnScreen;
	}
	
	public int getMouseYOnScreen()
	{
		return mouseYOnScreen;
	}
	
	public int getPressedMouseXOnScreen()
	{
		return pressedMouseXOnScreen;
	}
	
	public int getPressedMouseYOnScreen()
	{
		return pressedMouseYOnScreen;
	}
	
	public int getButton()
	{
		if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS)
		{
			return 1;
		}
		if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS)
		{
			return 2;
		}
		if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_3) == GLFW_PRESS)
		{
			return 3;
		}
		return 0;
	}
	
	public boolean isMouseHolded()
	{
		return getButton() != 0;
	}
	
	public boolean isMouseTriggered()
	{
		return mouseTriggered;
	}
	
	public void trigger()
	{
		mouseTriggered = false;
	}
	
	public void toggleTrigger()
	{
		mouseTriggered = !mouseTriggered;
	}
	
	public void setTrigger(boolean triggered)
	{
		mouseTriggered = triggered;
	}

}
