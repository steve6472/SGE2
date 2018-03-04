package com.steve6472.sge.gui;

import static org.lwjgl.glfw.GLFW.*;

import com.steve6472.sge.gui.components.Button;
import com.steve6472.sge.gui.components.ButtonEvents;
import com.steve6472.sge.gui.components.DragFrame;
import com.steve6472.sge.main.MouseHandler;

public class GuiUtils
{
	
	/**
	 * 
	 * @param gui
	 * @return Exit & Minimalize buttons
	 */
	public static Button[] createOperationButtons(Gui gui)
	{
		return new Button[]
		{ createExitButton(gui), createMinimalizeButton(gui) };
	}
	
	public static Button createExitButton(Gui gui)
	{
		Button exit = new Button("X");
		exit.setLocation(gui.getMainApp().getWidth() - 40, 0);
		exit.setSize(40, 25);
		exit.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				gui.getMainApp().exit();
			}
		});
		gui.addComponent(exit);
		return exit;
	}
	
	public static Button createMinimalizeButton(Gui gui)
	{
		Button minimalize = new Button("_");
		minimalize.setLocation(gui.getMainApp().getWidth() - 82, 0);
		minimalize.setSize(40, 25);
		minimalize.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				glfwIconifyWindow(gui.getMainApp().getWindow());
			}
		});
		gui.addComponent(minimalize);
		return minimalize;
	}
	
	public static void createBasicLayout(Gui gui)
	{
		DragFrame drag = new DragFrame();
		drag.setLocation(2, 2);
		drag.setSize(gui.mainApp.getWidth() - 84, 25);
		drag.setText(gui.mainApp.getTitle());
		
		gui.addComponent(drag);
		
		Button exit = GuiUtils.createExitButton(gui);
		exit.setLocation(gui.mainApp.getWidth() - 40 - 2, 2);
		gui.addComponent(exit);
		
		Button minimalise = GuiUtils.createMinimalizeButton(gui);
		minimalise.setLocation(gui.mainApp.getWidth() - 82, 2);
		gui.addComponent(minimalise);
	}

	public static boolean isCursorInRectangle(MouseHandler m, int x, int y, int w, int h)
	{
		return ( m.getMouseX() >= x && m.getMouseX() <= w + x)   // check if X is within range
				   && ( m.getMouseY() >= y && m.getMouseY()<= h + y);
	}
}
