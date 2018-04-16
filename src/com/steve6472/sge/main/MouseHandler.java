/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.main;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import com.steve6472.sge.main.callbacks.CursorPosCallback;
import com.steve6472.sge.main.callbacks.MouseButtonCallback;
import com.steve6472.sge.main.game.Vec2;

public class MouseHandler
{
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	@SuppressWarnings("unused")
	private GLFWCursorEnterCallback cursorEnterCallback;
	
	DoubleBuffer mouseXBuffer;
	DoubleBuffer mouseYBuffer;
	
	private final long window;
	private final MainApplication mainApp;
	
	private boolean mouseTriggered = false;
	private boolean isCursorInWindow = false;
	
	private int pressedMouseX;
	private int pressedMouseY;
	
	private int pressedMouseXOnScreen;
	private int pressedMouseYOnScreen;
	
	private int mouseXOnScreen;
	private int mouseYOnScreen;
	
	List<MouseButtonCallback> mouseButtonCallbacks;
	List<CursorPosCallback> cursorPosCallbacks;

	public MouseHandler(long window, MainApplication mainApp)
	{
		this.window = window;
		this.mainApp = mainApp;
		
		mouseXBuffer = BufferUtils.createDoubleBuffer(1);
		mouseYBuffer = BufferUtils.createDoubleBuffer(1);
		
		mouseButtonCallbacks = new ArrayList<MouseButtonCallback>();
		cursorPosCallbacks = new ArrayList<CursorPosCallback>();
		
		glfwSetMouseButtonCallback(window, mouseButtonCallback = new GLFWMouseButtonCallback()
		{
			@Override
			public void invoke(long window, int button, int action, int mods)
			{
//				System.out.println("Callback=[" + window + ", " + button +", " + action + ", " + mods + "]");
				mouseButtonCallbacks.forEach(c -> c.invoke(getMouseX(), getMouseY(), button, action, mods));
			}
		});
		
		glfwSetCursorPosCallback(window, cursorPosCallback = new GLFWCursorPosCallback()
		{
			public void invoke(long window, double xpos, double ypos)
			{
				cursorPosCallbacks.forEach(c -> c.invoke(xpos, ypos));
			}
		});
		
		glfwSetCursorEnterCallback(window, cursorEnterCallback = new GLFWCursorEnterCallback()
		{
			public void invoke(long window, boolean entered)
			{
				isCursorInWindow = entered;
			}
		});
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
	
	public int getPressedMouseX()
	{
		return pressedMouseX;
	}
	
	public int getPressedMouseY()
	{
		return pressedMouseY;
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
	
	public boolean isCursorInWindow()
	{
		return isCursorInWindow;
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
	
	public Vec2 toVec() { return new Vec2(getMouseX(), getMouseY()); }
	
	public void addMouseButtonCallback(MouseButtonCallback callback)
	{
		this.mouseButtonCallbacks.add(callback);
	}
	
	public void addCursorPosCallback(CursorPosCallback callback)
	{
		this.cursorPosCallbacks.add(callback);
	}
	
	public GLFWMouseButtonCallback getMouseButtonCallback()
	{
		return mouseButtonCallback;
	}
	
	public GLFWCursorPosCallback getCursorPosCallback()
	{
		return cursorPosCallback;
	}
	
}
