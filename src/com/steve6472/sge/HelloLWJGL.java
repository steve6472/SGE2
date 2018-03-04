package com.steve6472.sge;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.File;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;

public class HelloLWJGL 
{

	private GLFWKeyCallback keyCallback;

	private long window;

	public static int WIDTH = 16 * 70;
	public static int HEIGHT = 9 * 70;
	
	Sprite texture1;
	Screen screen;

	private void init()
	{
		GLFWErrorCallback.createPrint(System.err).set();;
		
		mouseXBuffer = BufferUtils.createDoubleBuffer(1);
		mouseYBuffer = BufferUtils.createDoubleBuffer(1);

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_FLOATING, GLFW_TRUE);

		window = glfwCreateWindow(WIDTH, HEIGHT, "Hello LWJGL3", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback()
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods)
			{
				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				{
					glfwSetWindowShouldClose(window, true);
				}
			}
		});

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - WIDTH) / 2, (vidmode.height() - HEIGHT) / 2);

		glfwMakeContextCurrent(window);
		
		//Enable V-Sync
		glfwSwapInterval(1);

		glfwShowWindow(window);
		
		screen = new Screen();
	}

	int x = 0;
	int y = 0;
	
	DoubleBuffer mouseXBuffer;
	DoubleBuffer mouseYBuffer;
	
	int mouseX;
	int mouseY;

	private void tick() 
	{
		if (isKeyPressed(GLFW_KEY_W))
			y--;
		
		if (isKeyPressed(GLFW_KEY_S))
			y++;
		
		if (isKeyPressed(GLFW_KEY_A))
			x--;
		
		if (isKeyPressed(GLFW_KEY_D))
			x++;
		
		glfwGetCursorPos(window, mouseXBuffer, mouseYBuffer);
		mouseX = (int) mouseXBuffer.get(0);
		mouseY = (int) mouseYBuffer.get(0);
	}
	
	public boolean isKeyPressed(int key)
	{
		return glfwGetKey(window, key) == GLFW_TRUE;
	}
	
	double ang = 0;
	
	private void render() 
	{
//		ang += 0.25d;
////		for (int i = 0; i < 34; i++)
////			for (int j = 0; j < 19; j++)
////					drawQuad(i * texture1.getWidth() + x, j * texture1.getHeight() + y, texture1);
		screen.fillRect(10, 10, 16, 16, 0xff00ff00);
//		Screen.fillRect(x, y, 32, 32, 0xff00ff00);
//		Screen.fillRect(mouseX - 16, mouseY - 16, 32, 32, 0xff00ff00);
//		Screen.drawCircle(WIDTH / 2, HEIGHT / 2, 6, 256, ang);
		
		drawQuad(256, 256, texture1);
//		
		screen.drawRect(mouseX - 16, mouseY - 16, 32, 32, 2, 0xff00ff00);
//		glColor3f(1f, 1f, 1f);
	}

	/**
	 * Renders Texture with it's size
	 * @param x
	 * @param y
	 * @param texture
	 */
	public static void drawQuad(int x, int y, Sprite texture) 
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glEnable(GL_TEXTURE_2D);
		glColor3f(1f, 1f, 1f);
		texture.bind();
		glBegin(GL_QUADS);
		{
			
			//Left Top
            glTexCoord2f(0, 0);
            glVertex2f(0 + x, 0 + y);
            
            //Right Top
            glTexCoord2f(1, 0);
            glVertex2f(texture.getWidth() + x, 0 + y);
            
            //Right Bottom
            glTexCoord2f(1, 1);
            glVertex2f(texture.getWidth() + x, texture.getHeight() + y);
            
            //Left Bottom
            glTexCoord2f(0, 1);
            glVertex2f(x, texture.getHeight() + y);
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopAttrib();
		glPopMatrix();
	}
	
	private void initTextures()
	{
		texture1 = new Sprite("*hsv.png");
	}

	private void loop() 
	{
		GL.createCapabilities();
		
		glEnable(GL_TEXTURE_2D);
		
		initTextures();

		glMatrixMode(GL_PROJECTION);
		glOrtho(0, WIDTH, HEIGHT, 0, -1, 1); // 2D projection matrix
		glMatrixMode(GL_MODELVIEW);
		
		System.out.println("----------------------------");
		System.out.println("OpenGL Version : " + glGetString(GL_VERSION));
		System.out.println("OpenGL Max Texture Size : " + glGetInteger(GL_MAX_TEXTURE_SIZE));
		System.out.println("OpenGL Vendor : " + glGetString(GL_VENDOR));
		System.out.println("OpenGL Renderer : " + glGetString(GL_RENDERER));
		System.out.println("----------------------------");
		
		//Set background color I guess
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		while (!glfwWindowShouldClose(window))
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			tick();
			render();
			glfwSwapBuffers(window);

			glfwPollEvents();
		}
	}
	
    public void run() 
	{
        System.out.println("LWJGL " + Version.getVersion() + "!");
 
        init();
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
	
    public static void main(String[] args) 
	{
        new HelloLWJGL().run();
    }
    
}
