/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge2.main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import com.steve6472.sge2.main.gfx.Font;
import com.steve6472.sge2.main.gfx.Screen;
import com.steve6472.sge2.main.gfx.Sprite;
import com.steve6472.sge2.main.gui.Gui;

public abstract class MainApplication
{
	
	private Screen screen;
	private MouseHandler mouseHandler;
	private KeyHandler keyHandler;
	private Font font;

	IntBuffer windowXBuffer, windowWidthBuffer;
	IntBuffer windowYBuffer, windowHeightBuffer;
	
	private List<Gui> guis;
	
	public MainApplication()
	{
		guis = new ArrayList<Gui>();
		windowXBuffer = BufferUtils.createIntBuffer(1);
		windowYBuffer = BufferUtils.createIntBuffer(1);
		
		windowWidthBuffer = BufferUtils.createIntBuffer(1);
		windowHeightBuffer = BufferUtils.createIntBuffer(1);
		run();
	}

	private GLFWKeyCallback keyCallback;

	public long window;
	
	Sprite texture1;

	private void initApplication()
	{
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		setWindowHints();

		window = glfwCreateWindow(getWidth(), getHeight(), getTitle(), NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() 
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) 
			{
				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
					glfwSetWindowShouldClose(window, true);
				}
		});
		
//		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> 
//		{
//			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
//				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
//		});

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - getWidth()) / 2, (vidmode.height() - getHeight()) / 2);

		glfwMakeContextCurrent(window);
		
		//Enable V-Sync
		glfwSwapInterval(1);

		glfwShowWindow(window);
		
		glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback()
		{
			@Override
			public void invoke(long window, int width, int height)
			{
				glViewport(0, 0, width, height);
				glMatrixMode(GL_PROJECTION);
				glLoadIdentity();
				glOrtho(0, width, height, 0, -1, 1); // 2D projection matrix

				glMatrixMode(GL_MODELVIEW);
			}
        });
	}

	private void loop() 
	{
		GL.createCapabilities();
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glMatrixMode(GL_PROJECTION);
		glOrtho(0, getWidth(), getHeight(), 0, -1, 1); // 2D projection matrix
		glMatrixMode(GL_MODELVIEW);
		
		/*
		System.out.println("----------------------------");
		System.out.println("OpenGL Version : " + glGetString(GL_VERSION));
		System.out.println("OpenGL Max Texture Size : " + glGetInteger(GL_MAX_TEXTURE_SIZE));
		System.out.println("OpenGL Vendor : " + glGetString(GL_VENDOR));
		System.out.println("OpenGL Renderer : " + glGetString(GL_RENDERER));
		System.out.println("----------------------------");
		*/
		//Set background color I guess
		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		
		this.screen = new Screen();
		this.mouseHandler = new MouseHandler(window, this);
		this.keyHandler = new KeyHandler(window);
		this.font = new Font(screen);
		
		init();

		while (!glfwWindowShouldClose(window))
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			mouseHandler.tick();
			
			glfwGetWindowPos(window, windowXBuffer, windowYBuffer);
			glfwGetWindowSize(window, windowWidthBuffer, windowHeightBuffer);
			
			tick();
			render(screen);
			glfwSwapBuffers(window);

			glfwPollEvents();
		}
	}
	
	public void tickGui()
	{
		for (Gui g : guis)
		{
			g.tick();
		}
	}
	
	public void renderGui()
	{
		for (Gui g : guis)
			g.renderGui(getScreen());
	}
	
    public void run() 
	{/*
        System.out.println("LWJGL " + Version.getVersion() + "!");
	 */
    	initApplication();
        loop();
        
        try
		{
            keyCallback.free();
            glfwDestroyWindow(window);
        } finally 
		{
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }
    
    public abstract void init();
    
    public abstract void tick();
    public abstract void render(Screen screen);
    
    public abstract void setWindowHints();
    
    public abstract int getWidth();
    public abstract int getHeight();
    
    public abstract String getTitle();
    
    public int getMouseX() { return mouseHandler.getMouseX(); }
    public int getMouseY() { return mouseHandler.getMouseY(); }

    public int getWindowX() { return windowXBuffer.get(0); }
    public int getWindowY() { return windowYBuffer.get(0); }
    public int getCurrentWidth() { return windowWidthBuffer.get(0); }
    public int getCurrentHeight() { return windowHeightBuffer.get(0); }
    
    public boolean isKeyPressed(int key) { return keyHandler.isKeyPressed(key); }
    
    public MouseHandler getMouseHandler() { return mouseHandler; }
    public KeyHandler getKeyHandler() { return keyHandler; }
    public Font getFont() { return font; }
    public Screen getScreen() { return screen; }
    
    public long getWindow() { return window; }
    
    public void addGui(Gui gui)
    {
    	this.guis.add(gui);
    }
    
    public List<Gui> getGuis()
    {
    	return guis;
    }
    
}
