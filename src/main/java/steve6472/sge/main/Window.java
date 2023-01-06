/**********************
* Created by steve6472
* On date: 18. 6. 2018
* Project: SGE2
*
***********************/

package steve6472.sge.main;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;
import steve6472.sge.main.events.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{
	private long window;
	private final MainApp mainApp;
	private String title;

	private boolean fullscreen;

	private GLFWWindowSizeCallback windowSizeCallback;
	private GLFWScrollCallback scrollCallback;
	private GLFWKeyCallback keyCallback;
	private GLFWCharCallback charCallback;
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	private GLFWCursorEnterCallback cursorEnterCallback;
	private Callback debugProc;

	public Window(MainApp mainApp, String title, boolean startInFullscreen)
	{
		this.mainApp = mainApp;
		this.fullscreen = startInFullscreen;
		this.title = title;

		openWindow();
	}

	public void openWindow()
	{
		if (fullscreen)
		{
			GLFWVidMode vidmode = getVidMode(0);
			window = glfwCreateWindow(vidmode.width(), vidmode.height(), title != null ? title : "", glfwGetPrimaryMonitor(), NULL);
		} else
		{
			window = glfwCreateWindow(mainApp.getWindowWidth(), mainApp.getWindowHeight(), title != null ? title : "", NULL, NULL);
		}

		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		glfwMakeContextCurrent(window);
		enableVSync(false);

		GL.createCapabilities();
		glClear(GL_COLOR_BUFFER_BIT);

		forceUpdateWindowSize();
		centerWindow();

		mainApp.windowId = getWindow();
		glfwShowWindow(window);
		initCallbacks();

		if (mainApp.enableGLDebug())
		debugProc = GLUtil.setupDebugMessageCallback();
	}

	public void destroyWindow()
	{
		freeCallbacks();
		glfwDestroyWindow(window);
	}

	public void freeCallbacks()
	{
		windowSizeCallback.free();
		scrollCallback.free();
		keyCallback.free();
		charCallback.free();
		mouseButtonCallback.free();
		cursorPosCallback.free();
		cursorEnterCallback.free();

		if (debugProc != null)
			debugProc.free();
	}

	public void initCallbacks()
	{
		glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback()
		{
			@Override
			public void invoke(long window, int width, int height)
			{
				mainApp.runEvent(new WindowSizeEvent(width, height));
			}
		});

		glfwSetScrollCallback(window, scrollCallback = new GLFWScrollCallback()
		{
			@Override
			public void invoke(long window, double xoffset, double yoffset)
			{
				mainApp.runEvent(new ScrollEvent(yoffset));
			}
		});

		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback()
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods)
			{
				mainApp.runEvent(new KeyEvent(key, scancode, action, mods));
			}
		});

		glfwSetCharCallback(window, charCallback = new GLFWCharCallback()
		{
			public void invoke(long window, int codepoint)
			{
				mainApp.runEvent(new CharEvent(codepoint));
			}
		});

		glfwSetMouseButtonCallback(window, mouseButtonCallback = new GLFWMouseButtonCallback()
		{
			@Override
			public void invoke(long window, int button, int action, int mods)
			{
				mainApp.runEvent(new MouseEvent(mainApp.getMouseX(), mainApp.getMouseY(), button, action, mods));
			}
		});

		glfwSetCursorPosCallback(window, cursorPosCallback = new GLFWCursorPosCallback()
		{
			public void invoke(long window, double xpos, double ypos)
			{
				mainApp.runEvent(new CursorPosEvent(xpos, ypos));
			}
		});

		glfwSetCursorEnterCallback(window, cursorEnterCallback = new GLFWCursorEnterCallback()
		{
			public void invoke(long window, boolean entered)
			{
				mainApp.runEvent(new CursorEnterEvent(entered));
			}
		});
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
		glfwSetWindowTitle(window, text != null ? text : "");
	}
	
	public void centerWindow()
	{
		centerWindow(0);
	}
	
	@Deprecated
	public void centerWindow(int monitor)
	{
		PointerBuffer pb = glfwGetMonitors();
		if (pb == null)
			return;
		GLFWVidMode vidmode = glfwGetVideoMode(pb.get(monitor));
		if (vidmode == null)
			return;
		glfwSetWindowPos(window, (vidmode.width() - mainApp.getWidth()) / 2, (vidmode.height() - mainApp.getHeight()) / 2);
	}

	public GLFWVidMode getVidMode(int monitor)
	{
		PointerBuffer pb = glfwGetMonitors();
		if (pb == null)
			return null;
		return glfwGetVideoMode(pb.get(monitor));
	}
	
	public void updateViewPort()
	{
		glViewport(0, 0, mainApp.getWidth(), mainApp.getHeight());
	}
	
	public int getWidth()
	{
		return mainApp.getWidth();
	}
	
	public int getHeight()
	{
		return mainApp.getHeight();
	}
	
	public long getWindow()
	{
		return window;
	}

	public void close()
	{
		glfwSetWindowShouldClose(window, true);
	}

	public static void enableVSync(boolean enabled)
	{
		glfwSwapInterval(Util.toInt(enabled));
	}

	public static void setResizable(boolean resizable)
	{
		glfwWindowHint(GLFW_RESIZABLE, Util.toInt(resizable));
	}

	public static void setFloating(boolean floating)
	{
		glfwWindowHint(GLFW_FLOATING, Util.toInt(floating));
	}

	public void maximize()
	{
		glfwMaximizeWindow(window);
		forceUpdateWindowSize();
		mainApp.runEvent(new WindowSizeEvent(mainApp.getWidth(), mainApp.getHeight()));
	}

	public boolean isFullscreen()
	{
		return fullscreen;
	}

	public void forceUpdateWindowSize()
	{
		glfwGetWindowSize(window, mainApp.windowWidthBuffer, mainApp.windowHeightBuffer);
	}

}
