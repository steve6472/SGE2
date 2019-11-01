/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 2. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main;

import com.steve6472.sge.gfx.SpriteRender;
import com.steve6472.sge.gfx.font.Font;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.Gui;
import com.steve6472.sge.gui.components.dialog.Dialog;
import com.steve6472.sge.gui.components.dialog.DialogContainer;
import com.steve6472.sge.gui.components.schemes.*;
import com.steve6472.sge.main.events.*;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public abstract class MainApp
{
	private Window window;
	private MouseHandler mouseHandler;
	private KeyHandler keyHandler;
	private EventHandler eventHandler;

	private SpriteRender spriteRender;

	IntBuffer windowXBuffer, windowWidthBuffer;
	IntBuffer windowYBuffer, windowHeightBuffer;

	private List<Gui> guis;

	private DialogContainer dialogContainer;

	private int exitKey = -1;
	private boolean exitKeyEnabled = false;

	private long renderStart, renderEnd, renderTime;
	private long tickStart, tickEnd, tickTime;
	private long loopStart, loopEnd, loopTime;

	private float fps;
	private long lastFps;

	private static SchemeRegistry schemeRegistry;

	public MainApp()
	{
		guis = new ArrayList<>();
		eventHandler = new EventHandler();
		schemeRegistry = new SchemeRegistry();
		dialogContainer = new DialogContainer(this);
		registerSchemes();
		schemeRegistry.loadCurrentSchemes();
		windowXBuffer = BufferUtils.createIntBuffer(1);
		windowYBuffer = BufferUtils.createIntBuffer(1);

		windowWidthBuffer = BufferUtils.createIntBuffer(1);
		windowHeightBuffer = BufferUtils.createIntBuffer(1);

		eventHandler.addForcedObject(this);

		run();
	}

	protected void registerSchemes()
	{
		schemeRegistry.registerScheme(SchemeButton.class, new SchemeButton(), SchemeButton::new);
		schemeRegistry.registerScheme(SchemeBackground.class, new SchemeBackground(), SchemeBackground::new);
		schemeRegistry.registerScheme(SchemeSlider.class, new SchemeSlider(), SchemeSlider::new);
		schemeRegistry.registerScheme(SchemeListItemButton.class, new SchemeListItemButton(), SchemeListItemButton::new);
		schemeRegistry.registerScheme(SchemeTextField.class, new SchemeTextField(), SchemeTextField::new);
	}

	public long windowId;

	protected void initApplication()
	{
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		setWindowHints();
		window = new Window(this, getTitle(), startInFullscreen());

		for (int flag : getFlags())
		{
			switch(flag)
			{
				case MainFlags.ADD_BASIC_ORTHO: addBasicResizeOrtho(); break;
				case MainFlags.ENABLE_EXIT_KEY: exitKeyEnabled = true; break;
			}
		}
	}

	@Event
	public void exitKeyEvent(KeyEvent event)
	{
		if (event.getKey() == exitKey && event.getAction() == GLFW_RELEASE && exitKeyEnabled)
		{
			getEventHandler().runEvent(new ExitEvent());
			exit();
		}
	}

	protected abstract int[] getFlags();

	private boolean enableBasicResizeOrtho = false;

	private void addBasicResizeOrtho()
	{
		enableBasicResizeOrtho = true;
	}

	@Event
	public void basicResizeOrtho(WindowSizeEvent event)
	{
		if (enableBasicResizeOrtho)
		{
			glViewport(0, 0, event.getWidth(), event.getHeight());
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, event.getWidth(), event.getHeight(), 0, 1, -1); // 2D projection matrix
			glMatrixMode(GL_MODELVIEW);
		}
	}

	protected void preLoop()
	{
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		for (int flag : getFlags())
		{
			switch (flag)
			{
				case MainFlags.ENABLE_GL_DEPTH_TEST -> glEnable(GL_DEPTH_TEST);
			}
		}


		if (enableBasicResizeOrtho)
		{
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, getWindowWidth(), getWindowHeight(), 0, -1, 1); // 2D projection matrix
			glMatrixMode(GL_MODELVIEW);
		}

		glClearColor(clearColorColors()[0], clearColorColors()[1], clearColorColors()[2], clearColorColors()[3]);

//		glMatrixMode(GL_PROJECTION);
//		glLoadIdentity();
//		glMatrixMode(GL_MODELVIEW);

		//Set background color I guess
		glClearColor(clearColorColors()[0], clearColorColors()[1], clearColorColors()[2], clearColorColors()[3]);
	}

	public static void printOpenGLData()
	{
		System.out.println("----------------------------");
		System.out.println("OpenGL Version : " + glGetString(GL_VERSION));
		System.out.println("OpenGL Max Texture Size : " + glGetInteger(GL_MAX_TEXTURE_SIZE));
		System.out.println("OpenGL Vendor : " + glGetString(GL_VENDOR));
		System.out.println("OpenGL Renderer : " + glGetString(GL_RENDERER));
		System.out.println("----------------------------");
	}

	public void resetOrtho()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-1, 1, -1, 1, -1, 1); // 2D projection matrix
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
			GL11.glReadBuffer(GL_FRONT);
			int width = getWidth();
			int height = getHeight();
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
			ImageIO.write(image, "PNG", new File("screenshots\\" + Util.getFormatedTime() + ".png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param fileName path to texture (use * for file select or leave without * for texture select)
	 * @return cursorID
	 */
	public long createCursor(String fileName, int hotspotX, int hotspotY)
	{
		BufferedImage image = null;
		if (fileName.startsWith("*"))
		{
			try
			{
				image = ImageIO.read(new File(fileName.substring(1)));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} else
		{
			if (!fileName.startsWith("/"))
			{
				fileName = "/" + fileName;
			}

			fileName = "/textures" + fileName;
			try
			{
				image = ImageIO.read(MainApp.class.getResourceAsStream(fileName));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		if (image == null)
			return 0;

		int width = image.getWidth();
		int height = image.getHeight();

		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);

		// convert image to RGBA format
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int pixel = pixels[y * width + x];

				buffer.put((byte) ((pixel >> 16) & 0xFF));  // red
				buffer.put((byte) ((pixel >> 8) & 0xFF));   // green
				buffer.put((byte) (pixel & 0xFF));          // blue
				buffer.put((byte) ((pixel >> 24) & 0xFF));  // alpha
			}
		}
		buffer.flip(); // this will flip the cursor image vertically

		// create a GLFWImage
		GLFWImage cursorImg = GLFWImage.create();
		cursorImg.width(width);     // setup the images' width
		cursorImg.height(height);   // setup the images' height
		cursorImg.pixels(buffer);   // pass image data

		// create custom cursor and store its ID
		return org.lwjgl.glfw.GLFW.glfwCreateCursor(cursorImg, hotspotX , hotspotY);
	}

	protected void setExitKey(int exitKey)
	{
		this.exitKey = exitKey;
	}

	private float tempFps = 0;

	public double getTickRate() { return 60d; }

	private void loop()
	{
		this.mouseHandler = new MouseHandler(windowId, this);
		this.keyHandler = new KeyHandler(windowId, this);
		Font.init();
		spriteRender = new SpriteRender(this);

		eventHandler.register(this);
		init();
		eventHandler.runEvent(new WindowSizeEvent(getWidth(), getHeight()));

		lastFps = System.nanoTime();

		double delta = 1d / getTickRate();
		double nextTime = (double) System.nanoTime() / 1000000000.0;

		while (!glfwWindowShouldClose(windowId))
		{

			if (System.nanoTime() - lastFps > 1000000000)
			{
				fps = tempFps;
				tempFps = 0; //reset the FPS counter
				lastFps += 1000000000; //add one second
			}
			tempFps++;

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			mouseHandler.tick();

			glfwGetWindowPos(windowId, windowXBuffer, windowYBuffer);
			glfwGetWindowSize(windowId, windowWidthBuffer, windowHeightBuffer);

			loopStart = System.nanoTime();

			double currTime = (double) System.nanoTime() / 1000000000.0;

			if (currTime >= nextTime)
			{
				nextTime += delta;

				tickStart = System.nanoTime();
				tick();
				tickEnd = System.nanoTime();
			}


			//TODO: Render only if window is visible
			renderStart = System.nanoTime();
			render();
			renderEnd = System.nanoTime();

			loopEnd = System.nanoTime();

			loopTime = loopEnd - loopStart;
			tickTime = tickEnd - tickStart;
			renderTime = renderEnd - renderStart;

			glfwSwapBuffers(windowId);

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
		{
			g.renderGui();
		}
	}

	public void tickDialogs()
	{
		dialogContainer.tickDialogs();
	}

	public void renderDialogs()
	{
		dialogContainer.renderDialogs();
	}

    public void run()
	{/*
        System.out.println("LWJGL " + Version.getVersion() + "!");
	 */
    	initApplication();
    	preLoop();
        loop();

        try
		{
			window.freeCallbacks();
            glfwDestroyWindow(windowId);
        } finally
		{
            glfwTerminate();
            glfwSetErrorCallback(null).free();
            System.exit(0);
        }
    }

    protected float[] clearColorColors()
    {
    	return new float[] {0F, 0F, 0F, 0F};
    }

    public abstract void init();

    public abstract void tick();
    public abstract void render();

    public abstract void setWindowHints();

    public abstract int getWindowWidth();
    public abstract int getWindowHeight();

    public abstract void exit();

    protected boolean startInFullscreen() { return false; }

    public abstract String getTitle();

    public int getMouseX() { return mouseHandler.getMouseX(); }
    public int getMouseY() { return mouseHandler.getMouseY(); }
	public int getButton() { return getMouseHandler().getButton(); }
	public boolean isLMBHolded() { return getButton() == KeyList.LMB; }
	public boolean isMMBHolded() { return getButton() == KeyList.MMB; }
	public boolean isRMBHolded() { return getButton() == KeyList.RMB; }

    public int getWindowX() { return windowXBuffer.get(0); }
    public int getWindowY() { return windowYBuffer.get(0); }
    public int getWidth() { return windowWidthBuffer.get(0); }
    public int getHeight() { return windowHeightBuffer.get(0); }

    public boolean isKeyPressed(int key) { return keyHandler.isKeyPressed(key); }

    public MouseHandler getMouseHandler() { return mouseHandler; }
    public KeyHandler getKeyHandler() { return keyHandler; }
    public Window getWindow() { return window; }
	public DialogContainer getDialogContainer() { return dialogContainer; }

    public long getWindowId() { return windowId; }

    public float getRenderTime()
    {
    	return renderTime / 1000000000f;
    }

    public float getFps()
    {
    	return fps;
    }

	public float getLoopTime()
	{
		return loopTime / 1000000000f;
	}

	public float getTickTime()
	{
		return tickTime / 1000000000f;
	}

    public void addGui(Gui gui)
    {
    	eventHandler.register(gui);
    	this.guis.add(gui);
    }

    private Matrix4f ortho = new Matrix4f();

    public Matrix4f getComponentOrtho()
    {
	    return ortho;
    }

	/**
	 *
	 * Mainly used to update font ortho
	 *
	 * @param e WindowSizeEvent
	 */
	@Event
    public final void orthoUpdate(WindowSizeEvent e)
    {
	    ortho.identity().ortho(0, e.getWidth(), e.getHeight(), 0, 1, -1);
	    Font.update(ortho);
    }

    public <T extends Dialog> T showDialog(T dialog)
    {
    	dialogContainer.showDialog(dialog);
    	if (dialog.disableEvents())
	    {
		    getEventHandler().addSpecialCaseObject(dialog);
		    dialog.getComponents().forEach(this::addComponentSpecialCase);
	    }
    	return dialog;
    }

	private void addComponentSpecialCase(Component c)
	{
		c.getComponents().forEach(this::addComponentSpecialCase);
		getEventHandler().register(c);
		getEventHandler().addSpecialCaseObject(c);
	}

	public void runEvent(AbstractEvent event)
	{
		getEventHandler().runEvent(event);
	}

    public List<Gui> getGuis()
    {
    	return guis;
    }

    public EventHandler getEventHandler()
	{
		return eventHandler;
	}
	public static SchemeRegistry getSchemeRegistry()
	{
    	return schemeRegistry;
    }

	public SpriteRender getSpriteRender()
	{
		return spriteRender;
	}
}
