/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 9. 6. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test.dof3;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.steve6472.sge.gfx.Camera;
import com.steve6472.sge.gfx.Helper;
import com.steve6472.sge.gfx.PlayerLocation;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Shader;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gfx.Tessellator3D;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.Util;
import com.steve6472.sge.main.callbacks.KeyCallback;

public class Test3D extends MainApplication
{

	public Test3D()
	{
	}
	
	Shader shader;
	Camera camera;
	
	Sprite sprite;
	Sprite blackPixel;
	
	World3D world;
	
	static boolean lock = false;
	static boolean mark = true;
	PlayerLocation playerLocation;
	
	static float minY = 0;
	static float maxY = 0;
	
	static int distance = 1;
	static float worldSize = 0.25f;

	static Biome[] biomes = new Biome[]
	{ new OceanBiome(), new Mountians(), new TestBiome()};
	
	public static Biome getBiome(double height)
	{
		if (height > 100)
		{
			return new OceanBiome();
		} else if (height > 90)
		{
			return new TestBiome();
		} else
		{
			return new Mountians();
		}
	}

	@Override
	public void init()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		shader = new Shader("shaders\\shad3d");
		camera = new Camera();
		sprite = new Sprite("*grass.png");
		blackPixel = new Sprite(new int[] {0x00000000}, 1, 1);
		
		playerLocation = new PlayerLocation();
		
		shader.setUniform1i("sampler", 0);
		shader.setUniform2i("texture", 0, 0);
		shader.setUniform4f("col", 0, 0, 0, 0);
		
		sprite.bind();
		shader.bind();
		
		world = new World3D();
		
		Helper.initHelper();
		shader.setUniformMat4f("transformation",  Helper.toMatrix());
		Renderer3D.init(shader);
		
		getKeyHandler().addKeyCallback(new KeyCallback()
		{
			
			@Override
			public void invoke(int key, int scancode, int action, int mods)
			{
				if (action == GLFW.GLFW_PRESS)
				{
					if (key == GLFW.GLFW_KEY_L)
					{
						GLFW.glfwSetCursorPos(window, getCurrentWidth() / 2, getCurrentHeight() / 2);
						lock = !lock;
					}
					if (key == GLFW.GLFW_KEY_F2)
					{
						takeScreenshot();
					}
					if (key == GLFW.GLFW_KEY_R)
					{
						world = new World3D();
					}
					if (key == GLFW.GLFW_KEY_M)
					{
						mark = !mark;
					}
					if (key == GLFW.GLFW_KEY_UP)
						distance++;
					if (key == GLFW.GLFW_KEY_DOWN)
						distance--;
					if (key == GLFW.GLFW_KEY_KP_0)
						distance = 0;
					if (key == GLFW.GLFW_KEY_F)
					{
						GLFW.glfwMaximizeWindow(window);
					}
				}
			}
		});

		addBasicResizeOrtho2();
		if (!lock)
			GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
	}

	public float[] getColor(float height)
	{
		float m = Util.getRandomFloat(1f, 0.9f, (long) (height * 10000f));
		if (height < 40) // water
		{
			return new float[]
			{ 0f, 0f, Util.normalise(height, -20, 50) * m, 1f };
		}
		if (height > 40 && height < 70) // grass
		{
			return new float[]
			{ 0f, Util.normalise(height, -20, 80) * m, 0f, 1f };
		}
		if (height > 70) // ice
		{
			return new float[]
			{ Util.normalise(height, 0, 90) * m, Util.normalise(height, 0, 90) * m, Util.normalise(height, 0, 90) * m, 1f };
		}

		return new float[]
		{ Util.normalise(height, minY, maxY), Util.normalise(height, minY, maxY), Util.normalise(height, minY, maxY), 1f };
//		float f = ;
//		return new float[] {f, f, f, 1};
	}

	@Override
	public void tick()
	{
//		world = new World3D();
		camera.setSize(getCurrentWidth(), getCurrentHeight());
		playerLocation.setSize(getCurrentWidth(), getCurrentHeight());

		double speed = 0.1d;
		if (isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT))
		{
			speed = 1d;
		}
		if (isKeyPressed(GLFW.GLFW_KEY_LEFT_ALT))
		{
			speed = 0.01d;
		}
		if (isKeyPressed(GLFW.GLFW_KEY_KP_6))
		{
			playerLocation.roll += Math.PI / 360d;
		}
		if (isKeyPressed(GLFW.GLFW_KEY_KP_4))
		{
			playerLocation.roll -= Math.PI / 360d;
		}
		if (isKeyPressed(GLFW.GLFW_KEY_KP_5))
		{
			playerLocation.roll = 0;
		}
		
		if (!lock)
			playerLocation.head(window, getMouseX(), getMouseY(), SENSITIVITY);
		playerLocation.move(this, speed);
		
		if (isKeyPressed(GLFW.GLFW_KEY_SPACE))
		{
			playerLocation.setY(-playerLocation.getY() - speed);
		}
		if (isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL))
		{
			playerLocation.setY(-playerLocation.getY() + speed);
		}
		if (isKeyPressed(GLFW.GLFW_KEY_KP_MULTIPLY))
		{
			playerLocation.setX(0);
			playerLocation.setY(0);
			playerLocation.setZ(0);
		}
	}
	
	float rotX, rotY;
	
	static float SENSITIVITY = 0.2f;
	static float playerWidth = 0.8f;
	static float playerHeight = 1.7f;
	static float eyeHeight = 1.5f;
	
	@Override
	public void render(Screen screen)
	{
		shader.setUniformMat4f("projection", createProjectionMatrix());
		shader.setUniformMat4f("view", camera.getViewMatrix(playerLocation));

		blackPixel.bind();
		if (mark)
			Renderer3D.renderAxisHelper(0, 0, 0);
		Renderer3D.renderSkybox(playerLocation);

		blackPixel.bind();
		
//		Renderer3D.renderWireframe(playerWidth, playerHeight, playerWidth, playerLocation.getX() - playerWidth / 2, playerLocation.getY() - eyeHeight,
//				playerLocation.getZ() - playerWidth / 2);

		blackPixel.bind();
		
//		world.render(shader, playerLocation);

		sprite.bind();
		
		Renderer3D.drawFont(this, "UPS:" + getFPS(), 5, 5, camera);
		Renderer3D.drawFont(this, "X:" + playerLocation.getX(), 5, 15, camera);
		Renderer3D.drawFont(this, "Y:" + playerLocation.getY(), 5, 25, camera);
		Renderer3D.drawFont(this, "Z:" + playerLocation.getZ(), 5, 35, camera);
		Renderer3D.drawFont(this, "Memory (MB): " + Runtime.getRuntime().freeMemory() / (1024 * 1024) + "/" + Runtime.getRuntime().totalMemory() / (1024 * 1024), 5, 55, camera);

		Renderer3D.drawFont(this, "X/Z:" + (int) (playerLocation.getX() / (World3D.CHUNK_SIZE * worldSize)) + "/" + (int) (playerLocation.getZ() / (World3D.CHUNK_SIZE * worldSize)), 5, 75, camera);
		Renderer3D.drawFont(this, "Pitch:" + playerLocation.pitch, 5, 85, camera);
		Renderer3D.drawFont(this, "Yaw:" + playerLocation.yaw, 5, 95, camera);
		Renderer3D.drawFont(this, "Roll:" + playerLocation.roll, 5, 105, camera);
		
		shader.bind();
		
//		System.out.println("XYZ: " + playerLocation.getPosition().getIntX() + " " + playerLocation.getPosition().getFY() + " " + playerLocation.getPosition().getIntZ());
	}
	
	private static float FOV = 85;
	private static float NEAR_PLANE = 0.01f;
	private static float FAR_PLANE = 2048f;

	private Matrix4f createProjectionMatrix()
	{
		float aspectRatio = (float) camera.getWidth() / (float) camera.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		Matrix4f projectionMatrix = new Matrix4f();
		projectionMatrix._m00(x_scale);
		projectionMatrix._m11(y_scale);
		projectionMatrix._m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
		projectionMatrix._m23(-1);
		projectionMatrix._m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
		projectionMatrix._m33(0);
		return projectionMatrix;
	}

	@Override
	public void setWindowHints()
	{
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, 1);
	}

	@Override
	public int getWidth()
	{
		return 16 * 50;
	}

	@Override
	public int getHeight()
	{
		return 9 * 50;
	}

	@Override
	public void exit()
	{
	}

	@Override
	public String getTitle()
	{
		return "3D Test";
	}
	
	public static void main(String[] args)
	{
		new Test3D();
	}

}
