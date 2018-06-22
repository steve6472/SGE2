/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 2. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.Gui;
import com.steve6472.sge.main.callbacks.WindowSizeCallback;
import com.steve6472.sge.main.game.Vec2;

public abstract class MainApplication
{
	private Window window_;
	private Screen screen;
	private MouseHandler mouseHandler;
	private KeyHandler keyHandler;
	private Font font;

	IntBuffer windowXBuffer, windowWidthBuffer;
	IntBuffer windowYBuffer, windowHeightBuffer;
	
	private List<Gui> guis;
	protected List<WindowSizeCallback> windowSizeCallbacks;
	
	SGArray<Tick> ticks;
	
	double lastTime = glfwGetTime();
	int nbFrames = 0;
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public MainApplication()
	{
		guis = new ArrayList<Gui>();
		windowXBuffer = BufferUtils.createIntBuffer(1);
		windowYBuffer = BufferUtils.createIntBuffer(1);
		
		windowWidthBuffer = BufferUtils.createIntBuffer(1);
		windowHeightBuffer = BufferUtils.createIntBuffer(1);
		
		ticks = new SGArray<Tick>();
		run();
	}

	public long window;
	
	public static Sprite sprites;

	protected void initApplication()
	{
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		setWindowHints();

//		window = glfwCreateWindow(getWidth(), getHeight(), getTitle() != null ? getTitle() : "", NULL, NULL);
//		if (window == NULL)
//			throw new RuntimeException("Failed to create the GLFW window");

		window_ = new Window(this, getTitle());
		
		window = window_.getWindow();
		
		glfwGetWindowSize(window, windowWidthBuffer, windowHeightBuffer);

		window_.centerWindow();

		glfwMakeContextCurrent(window);
		
		//Enable V-Sync
		//Apperently enabled by default but shhhhhut up
		glfwSwapInterval(1);

		glfwShowWindow(window);
		
		glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback()
		{
			@Override
			public void invoke(long window, int width, int height)
			{
				windowSizeCallbacks.forEach(c -> c.invoke(width, height));
			}
        });
	}
	
	protected void addBasicResizeOrtho()
	{
		addWindowSizeCallback((width, height) ->
		{
			glViewport(0, 0, width, height);
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, width, height, 0, -1, 1); // 2D projection matrix
			glMatrixMode(GL_MODELVIEW);
		});

	}
	
	public void addWindowSizeCallback(WindowSizeCallback callback)
	{
		this.windowSizeCallbacks.add(callback);
	}
	
	private double fps;

	protected void preLoop()
	{
		GL.createCapabilities();
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		if (!disableGlDepthTest())
			glEnable(GL_DEPTH_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		if (!disableAutoPixelOrtho())
		{
			createPixelOrtho();
		} else
		{
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glMatrixMode(GL_MODELVIEW);
		}
		
		//Set background color I guess
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	protected boolean disableGlDepthTest()
	{
		return false;
	}
	
	protected boolean disableAutoPixelOrtho()
	{
		return false;
	}
	
	public void printOpenGLData()
	{
		System.out.println("----------------------------");
		System.out.println("OpenGL Version : " + glGetString(GL_VERSION));
		System.out.println("OpenGL Max Texture Size : " + glGetInteger(GL_MAX_TEXTURE_SIZE));
		System.out.println("OpenGL Vendor : " + glGetString(GL_VENDOR));
		System.out.println("OpenGL Renderer : " + glGetString(GL_RENDERER));
		System.out.println("----------------------------");
	}
	
	public void createPixelOrtho()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, getWidth(), getHeight(), 0, -1, 1); // 2D projection matrix
		glMatrixMode(GL_MODELVIEW);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public void resetOrtho()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		//TODO: Test this method
		glOrtho(-1, 1, -1, 1, -1, 1); // 2D projection matrix
		glMatrixMode(GL_MODELVIEW);
	}
	
	protected void addBasicResizeOrtho2()
	{
		addWindowSizeCallback((width, height) ->
		{
			glViewport(0, 0, width, height);
		});
	}
	
	/**
	 * Stolen from https://stackoverflow.com/questions/35216031/displaying-3d-triangle-using-lwjgl-3
	 * 
	 * @param fov - Field of vision in degrees in the y direction
	 * @param aspect - Aspect ratio of the viewport
	 * @param zNear - The near clipping distance
	 * @param zFar - The far clipping distance
	 */
	public void perspectiveGL(float fov, float aspect, float zNear, float zFar)
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		float fH = (float) Math.tan(fov / 360 * Math.PI) * zNear;
		float fW = fH * aspect;
		glFrustum(-fW, fW, -fH, fH, zNear, zFar);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public void takeScreenshot()
	{
		File file = new File("screenshots");
		if (!file.exists())
		{
			file.mkdirs();
		}
		try
		{
			GL11.glReadBuffer(GL11.GL_FRONT);
			int width = getCurrentWidth();
			int height = getCurrentHeight();
			int bpp = 4;
			ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
			GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			for (int x = 0; x < width; x++)
			{
				for (int y = 0; y < height; y++)
				{
					int i = (x + (width * y)) * bpp;
					int r = buffer.get(i) & 0xFF;
					int g = buffer.get(i + 1) & 0xFF;
					int b = buffer.get(i + 2) & 0xFF;
					image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
				}
			}
			ImageIO.write(image, "PNG", new File(String.format("screenshots\\" + Util.getFormatedTime() + ".png")));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	protected final void loop() 
	{
		
		this.screen = new Screen();
		this.mouseHandler = new MouseHandler(window, this);
		this.keyHandler = new KeyHandler(window);
		this.font = new Font(screen);

		keyHandler.addKeyCallback((key, sancode, action, mods) ->
		{
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE && escExit())
				glfwSetWindowShouldClose(window, true);
		});
		
		sprites = new Sprite("components.png");
		
		init();

		while (!glfwWindowShouldClose(window))
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			double currentTime = glfwGetTime();
			nbFrames++;
			if (currentTime - lastTime >= 1.0)
			{ // If last prinf() was more than 1 sec ago
				// printf and reset timer
				fps = 1000.0 / (double) (nbFrames);
				nbFrames = 0;
				lastTime += 1.0;
			}

			mouseHandler.tick();
			
			glfwGetWindowPos(window, windowXBuffer, windowYBuffer);
			glfwGetWindowSize(window, windowWidthBuffer, windowHeightBuffer);
			
			tick();
			render(screen);
			glfwSwapBuffers(window);

			glfwPollEvents();
		}
	}
	
	/**
	 * 16.666 = 60fps
	 * 33.333 = 30fps
	 * @return
	 */
	public double getFPS()
	{
		return fps;
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
	
	public void tickTicks()
	{
		for (Tick t : ticks)
			t.tick();
	}
	
	public void addTick(Tick tick)
	{
		ticks.addObject(tick);
	}
	
	@FunctionalInterface
	public interface Tick
	{
		public void tick();
	}
	
    public void run() 
	{/*
        System.out.println("LWJGL " + Version.getVersion() + "!");
	 */
		this.windowSizeCallbacks = new ArrayList<WindowSizeCallback>();
    	initApplication();
    	preLoop();
        loop();
        
        try
		{
            getKeyHandler().getKeyCallback().free();
            getKeyHandler().getCharCallback().free();
            getMouseHandler().getMouseButtonCallback().free();
            getMouseHandler().getCursorPosCallback().free();
            glfwDestroyWindow(window);
        } finally 
		{
            glfwTerminate();
            glfwSetErrorCallback(null).free();
            System.exit(0);
        }
    }
    
    public abstract void init();
    
    public abstract void tick();
    public abstract void render(Screen screen);
    
    public abstract void setWindowHints();
    
    public abstract int getWidth();
    public abstract int getHeight();
    
    public abstract void exit();
    
    protected boolean escExit() { return true; }
    
    public abstract String getTitle();
    
    public int getMouseX() { return mouseHandler.getMouseX(); }
    public int getMouseY() { return mouseHandler.getMouseY(); }

    public int getWindowX() { return windowXBuffer.get(0); }
    public int getWindowY() { return windowYBuffer.get(0); }
    public int getCurrentWidth() { return windowWidthBuffer.get(0); }
    public int getCurrentHeight() { return windowHeightBuffer.get(0); }
    
    public int getScreenWidth() { return (int) screenSize.getWidth(); }
    public int getScreenHeight() { return (int) screenSize.getHeight(); }
    
    public boolean isKeyPressed(int key) { return keyHandler.isKeyPressed(key); }
    
    public MouseHandler getMouseHandler() { return mouseHandler; }
    public KeyHandler getKeyHandler() { return keyHandler; }
    public Font getFont() { return font; }
    public Screen getScreen() { return screen; }
    public Vec2 getCenter() { return new Vec2(getCurrentWidth() / 2, getCurrentHeight() / 2); }
    public Window getWindow() { return window_; }
    
    public long getWindowId() { return window; }
    
    public void addGui(Gui gui)
    {
    	this.guis.add(gui);
    }
    
    public List<Gui> getGuis()
    {
    	return guis;
    }
    
}
