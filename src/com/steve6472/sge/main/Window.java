/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 18. 6. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVidMode;

public class Window
{
	private final long window;
	private final MainApplication mainApp;

	public Window(MainApplication mainApp, String title)
	{
		this.mainApp = mainApp;

		window = glfwCreateWindow(mainApp.getWidth(), mainApp.getHeight(), title != null ? title : "", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
	}
	
	public void setSize(int width, int height)
	{
		glfwSetWindowSize(window, width, height);
		updateViewPort();
	}
	
	public void setPos(int xpos, int ypos)
	{
		glfwSetWindowPos(window, xpos, ypos);
	}
	
	public void setWindowText(String text)
	{
		glfwSetWindowTitle(window, text);
	}
	
	public void enableVSync(boolean enabled)
	{
		glfwSwapInterval(Util.toInt(enabled));
	}
	
	public void centerWindow()
	{
		centerWindow(0);
	}
	
	@Deprecated
	public void centerWindow(int monitor)
	{
		PointerBuffer pb = glfwGetMonitors();
		GLFWVidMode vidmode = glfwGetVideoMode(pb.get(monitor));
		glfwSetWindowPos(window, (vidmode.width() - mainApp.getCurrentWidth()) / 2, (vidmode.height() - mainApp.getCurrentHeight()) / 2);
	}
	
	public void updateViewPort()
	{
		glViewport(0, 0, mainApp.getCurrentWidth(), mainApp.getCurrentHeight());
	}
	
	public int getWidth()
	{
		return mainApp.getCurrentWidth();
	}
	
	public int getHeight()
	{
		return mainApp.getCurrentHeight();
	}
	
	public long getWindow()
	{
		return window;
	}

}
