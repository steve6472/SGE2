/**********************
* Created by steve6472
* On date: 27. 2. 2018
* Project: LWJGL
*
***********************/

package steve6472.sge.main;

import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class MouseHandler
{
	private DoubleBuffer mouseXBuffer;
	private DoubleBuffer mouseYBuffer;
	
	private final long window;
	private final MainApp mainApp;
	
	private boolean mouseTriggered = false;
	private boolean isCursorInWindow = false;
	
	private int pressedMouseX;
	private int pressedMouseY;
	
	private int pressedMouseXOnScreen;
	private int pressedMouseYOnScreen;
	
	private int mouseXOnScreen;
	private int mouseYOnScreen;
	
	public MouseHandler(long window, MainApp mainApp)
	{
		this.window = window;
		this.mainApp = mainApp;
		
		mouseXBuffer = BufferUtils.createDoubleBuffer(1);
		mouseYBuffer = BufferUtils.createDoubleBuffer(1);
	}
	
	private boolean wasHolded = false;

	public void spoof(int mouseX, int mouseY)
	{
		mouseXBuffer.put(0, mouseX);
		mouseYBuffer.put(0, mouseY);

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
			return GLFW_MOUSE_BUTTON_1;
		}
		if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS)
		{
			return GLFW_MOUSE_BUTTON_2;
		}
		if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_3) == GLFW_PRESS)
		{
			return GLFW_MOUSE_BUTTON_3;
		}
		return -1;
	}

	public int getButton(int action)
	{
		if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == action)
		{
			return GLFW_MOUSE_BUTTON_1;
		}
		if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_2) == action)
		{
			return GLFW_MOUSE_BUTTON_2;
		}
		if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_3) == action)
		{
			return GLFW_MOUSE_BUTTON_3;
		}
		return -1;
	}
	
	public boolean isMouseHolded()
	{
		return getButton() != -1;
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
}
