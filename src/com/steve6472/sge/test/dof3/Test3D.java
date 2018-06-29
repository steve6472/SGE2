/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 9. 6. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test.dof3;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import com.steve6472.sge.gfx.Camera;
import com.steve6472.sge.gfx.Helper;
import com.steve6472.sge.gfx.PlayerLocation;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Shader;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gfx.Tessellator;
import com.steve6472.sge.gfx.Tessellator3D;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.Util;
import com.steve6472.sge.main.callbacks.KeyCallback;
import com.steve6472.sge.main.game.Atlas;
import com.steve6472.sge.main.game.GravityUtil;
import com.steve6472.sge.main.game.world.Chunk;
import com.steve6472.sge.main.game.world.GameTile;
import com.steve6472.sge.main.game.world.World;
import com.steve6472.sge.main.game.world.World.IRender0;
import com.steve6472.sge.test.DynamicModel3D;

public class Test3D extends MainApplication
{

	public Test3D()
	{
	}
	
	Shader shader;
	Camera camera;
	
	Chunk chunk;
	World world;
	
	Sprite sprite;
	Sprite blackPixel;
	
	static boolean lock = false;
	static boolean gravity = false;
	static boolean clip = false;
	static boolean mark = true;
	PlayerLocation playerLocation;
	
	static float minY = 0;
	static float maxY = 0;
	
	DynamicModel3D worldModel;
	
	@Override
	public void init()
	{
		shader = new Shader("shaders\\shad3d");
		camera = new Camera();
		sprite = new Sprite("*grass.png");
		blackPixel = new Sprite(new int[] {0x00000000}, 1, 1);
		
		worldModel = new DynamicModel3D();
		
		GameTile.initGameTiles(new Atlas(sprite, 32), 32, 32);
		Chunk.initChunks(16, 16, 16);
		World.initWorlds(1, 1);
		world = new World();
		chunk = new Chunk(world, 0, 0);
		for (int i = 0; i < Chunk.chunkWidth; i++)
		{
			for (int j = 0; j < Chunk.chunkHeight; j++)
			{
				for (int k = 0; k < Chunk.layerCount; k++)
				{
					if (k == 0)
					{
						chunk.setTileId(i, j, k, 1);
					}
				}
			}
		}
		chunk.setTileIdSafe(3, 3, 1, 1);
		chunk.setTileIdSafe(4, 4, 1, 1);
		chunk.setTileIdSafe(4, 4, 2, 1);
		
		world.setChunk(0, 0, chunk);
		
		playerLocation = new PlayerLocation();
		
		shader.setUniform1i("sampler", 0);
		shader.setUniform2i("texture", 0, 0);
		shader.setUniform4f("col", 0, 0, 0, 0);
		
		sprite.bind();
		shader.bind();
		
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
					if (key == GLFW.GLFW_KEY_C)
					{
						clip = !clip;
					}
					if (key == GLFW.GLFW_KEY_G)
					{
						gravity = !gravity;
					}
					if (key == GLFW.GLFW_KEY_F2)
					{
						takeScreenshot();
					}
					if (key == GLFW.GLFW_KEY_M)
					{
						mark = !mark;
					}
					if (key == GLFW.GLFW_KEY_F)
					{
						GLFW.glfwMaximizeWindow(window);
					}
					if (key == GLFW.GLFW_KEY_O)
					{
						Noise noise = new Noise(); //Seed 5
						noise.setParameters(7, 43, 55, 0, 0.50);
//						noise.setParameters(7, 10, 55, 0, 0.5);
						minY = 10000;
						maxY = -minY;
						
						int sizeX = 512 + 256;
						int sizeZ = 512 + 256;
						
						long start = System.currentTimeMillis();
						System.out.println("Start: " + start);
						
						float[][] heights = new float[sizeX + 2][sizeZ + 2];

						for (int i = 0; i < sizeX + 2; i++)
						{
							for (int j = 0; j < sizeZ + 2; j++)
							{
								float f = (float) noise.getHeight(i, j, 0, 0);
								heights[i][j] = f;
								minY = Math.min(minY, f);
								maxY = Math.max(maxY, f);
							}
						}
						
						worldModel = new DynamicModel3D();
						
						System.out.println("Stage 1: " + (System.currentTimeMillis() - start));
						long S = System.currentTimeMillis();
						
						float s = 0.25f;
						
						for (int i = 0; i < sizeX; i++)
						{
							if (i % 2 == 0)
							{
								int j = 0;
								int k = 0;
								for (j = 0; j < sizeZ; j++)
								{
									for (k = 0 + i; k < 2 + i; k++)
									{
										worldModel.add(j * s, heights[j][k] * s, k * s, getColor(heights[j][k]));
									}
								}
								j-=1;
								k-=1;
								worldModel.add(j  * s, heights[j][k] * s, k * s, getColor(heights[j][k]));
							} else
							{
								int j = 0;
								int k = 0;
								for (j = sizeZ - 1; j >= 0; j--)
								{
									for (k = 0 + i; k < 2 + i; k++)
									{
										worldModel.add(j * s, heights[j][k] * s, k * s, getColor(heights[j][k]));
									}
								}
								j+=1;
								k-=1;
								worldModel.add(j * s, heights[j][k] * s, k * s, getColor(heights[j][k]));
							}
						}

						System.out.println("Stage 2: " + (System.currentTimeMillis() - S));
						
						worldModel.generate();
						
						long end = System.currentTimeMillis();
						System.out.println("End: " + end);
						System.out.println("Took: " + (end - start));
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
		if (height < 40)
			return new float[] {0f, 0f, Util.normalise(height, -20, 50), 1f};//water
		if (height > 40 && height < 70)
			return new float[] {0f, Util.normalise(height, -20, 80), 0f, 1f};//grass
		if (height > 70)
			return new float[] {Util.normalise(height, 0, 90), Util.normalise(height, 0, 90), Util.normalise(height, 0, 90), 1f};//ice
		
//		return new float[] {1f, 0f, 1f, 1f};
		return new float[] {Util.normalise(height, minY, maxY), Util.normalise(height, minY, maxY), Util.normalise(height, minY, maxY), 1f};
	}
	
	double fallTime = 0;
	static boolean clipping = false;
	
	@Override
	public void tick()
	{
		camera.setSize(getCurrentWidth(), getCurrentHeight());
		playerLocation.setSize(getCurrentWidth(), getCurrentHeight());

		double speed = 0.1d;
		if (isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT))
		{
			speed = 1d;
		}
		if (isKeyPressed(GLFW.GLFW_KEY_LEFT_ALT))
		{
			speed = 0.001d;
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

		if (playerLocation.getY() < -10f)
		{
			clipping = true;
			fallTime = 0;
			playerLocation.setY(-10);
		}

		float down = 0f;
		
		if (!clipping)
		{
			float g = (float) GravityUtil.NORMAL_GRAVITY;
			//Mass
			float m = 1f;
			float airDensity20C = 1.2041f;
			float cubeDragCoefficient = 0.8f;
			
			float terminalV = (float) Math.sqrt(2 * m * g / airDensity20C / cubeDragCoefficient);
			
			float v = g * (float) fallTime / 2500f;
			
			if (v > terminalV)
				v = terminalV;
			
//			Renderer3D.drawFont(this, "-- " + terminalV + " V:" + v, 5, 45, camera);
//			shader.bind();
			
			down = v;
			
			fallTime++;
		} else
		{
			fallTime = 0;
		}
		
		clipping = false;

		if (gravity)
			playerLocation.setY(-playerLocation.getY() + down);
		
		float fx = playerLocation.getX();
		float fy = playerLocation.getY() - eyeHeight;
		float fz = playerLocation.getZ();

		int x = (int) fx;
		int y = (int) fy;
		int z = (int) fz;
		
		if (chunk.getTileIdSafe(x, z, y) == 1)
		{
			if (clip)
			{
				playerLocation.setY(-(y + eyeHeight + 1f));
				clipping = true;
			}
		}
		
		float Fy = fy + 0.0001f + down;

		int Y = (int) Fy;
		
		if (chunk.getTileIdSafe(x, z, Y) == 1)
		{
			if (clip)
				playerLocation.setY(-(Y + eyeHeight + 1f));
		}
		
//		Renderer3D.drawFont(this, "-- " + fy + " (int) " + y, 5, 45, camera);
//		shader.bind();
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
			Renderer3D.render3DMark(0, 0, 0);
		Renderer3D.renderSkybox(playerLocation);
		
//		Renderer3D.renderWireframe(playerWidth, playerHeight, playerWidth, playerLocation.getX() - playerWidth / 2, playerLocation.getY() - eyeHeight,
//				playerLocation.getZ() - playerWidth / 2);

		blackPixel.bind();
		if (worldModel != null)
		{
			worldModel.render(Tessellator.TRIANGLE_STRIP);
		}
		Tessellator3D tess = Tessellator3D.INSTANCE;
		for (int i = 0; i < 16; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				tess.put(i / 4f, 10, 10 + j / 4f);
				tess.put(i / 4f, 6, 4 + j / 4f);
			}
		}
		tess.render(Tessellator.LINES);
		
//		blackPixel.bind();
		
//		if (vertices != null)
//			tess.put(vertices, null, colors);
//		if (ver != null)
//		{
//			tess.render(Tessellator.TRIANGLE_STRIP, ver, tex, col, size);
//		}

//		GL11.glLineWidth(2f);
//		tess.render(Tessellator.LINE_STRIP);
//		tess.render(Tessellator.TRIANGLE_STRIP);
//		GL11.glLineWidth(1f);
		
//		for (int k = 0; k < voxelWorld[0].length - 1; k++)
//		{
//			for (int i = 0; i < voxelWorld.length; i++)
//			{
//				for (int j = 0 + k; j < 2 + k; j++)
//				{
//					float y = voxelWorld[i][j];
//					float ny = Util.normalise(y, minY, maxY);
//					tess.put(i, y, j, ny, ny, ny);
//				}
//			}
//		}

//		GL11.glLineWidth(1f);

		sprite.bind();
		
		iterateVisibleTiles(camera, (x, z, y, id) ->
		{
			if (world.getTileInWorldSafe(x + 1, z, y) == 0)
				Renderer3D.renderArrays(Cube.createSide1CubeFace(0, 1, x, y, z), Cube.tex, Cube.shadeSides());
			if (world.getTileInWorldSafe(x - 1, z, y) == 0)
				Renderer3D.renderArrays(Cube.createSide2CubeFace(0, 1, x, y, z), Cube.tex, Cube.shadeSides());
			if (world.getTileInWorldSafe(x, z, y + 1) == 0)
				Renderer3D.renderArrays(Cube.createTopCubeFace(0, 1, x, y, z), Cube.tex, Cube.shadeTop());
			if (world.getTileInWorldSafe(x, z, y - 1) == 0)
				Renderer3D.renderArrays(Cube.createBottomCubeFace(0, 1, x, y, z), Cube.tex, Cube.shadeBottom());
			if (world.getTileInWorldSafe(x, z + 1, y) == 0)
				Renderer3D.renderArrays(Cube.createFrontCubeFace(0, 1, x, y, z), Cube.tex, Cube.shadeFrontBack());
			if (world.getTileInWorldSafe(x, z - 1, y) == 0)
				Renderer3D.renderArrays(Cube.createBackCubeFace(0, 1, x, y, z), Cube.tex, Cube.shadeFrontBack());
		});
		
//		renderCuve(0, 0, 0);
//		renderCuve(1, 0, 0);
//		renderCuve(0, 1, 0);
//		renderCuve(0, 0, 1);
		
		Renderer3D.drawFont(this, "UPS:" + getFPS(), 5, 5, camera);
		Renderer3D.drawFont(this, "X:" + playerLocation.getX(), 5, 15, camera);
		Renderer3D.drawFont(this, "Y:" + playerLocation.getY(), 5, 25, camera);
		Renderer3D.drawFont(this, "Z:" + playerLocation.getZ(), 5, 35, camera);
		shader.bind();
		
//		renderCuve(0, 0, 0);
		
//		System.out.println("XYZ: " + playerLocation.getPosition().getIntX() + " " + playerLocation.getPosition().getFY() + " " + playerLocation.getPosition().getIntZ());
	}
	
	public void renderCuve(float x, float y, float z)
	{
		float n = 0;
		float p = 1;

		blackPixel.bind();
		Tessellator3D tess = Tessellator3D.INSTANCE;
		
		tess.put(n + x, n + y, p + z, 0, 0, 1);
		tess.put(p + x, n + y, p + z, 0, 0, 1);
		tess.put(p + x, p + y, p + z, 0, 0, 1);
		tess.put(n + x, p + y, p + z, 0, 0, 1);

		tess.put(p + x, n + y, p + z, 1, 0, 0);
		tess.put(p + x, n + y, n + z, 1, 0, 0);
		tess.put(p + x, p + y, n + z, 1, 0, 0);
		tess.put(p + x, p + y, p + z, 1, 0, 0);
		
		tess.put(p + x, n + y, n + z);
		tess.put(n + x, n + y, n + z);
		tess.put(n + x, p + y, n + z);
		tess.put(p + x, p + y, n + z);
		
		tess.put(n + x, n + y, n + z);
		tess.put(n + x, n + y, p + z);
		tess.put(n + x, p + y, p + z);
		tess.put(n + x, p + y, n + z);
		
		tess.put(n + x, p + y, p + z, 0, 1, 0);
		tess.put(p + x, p + y, p + z, 0, 1, 0);
		tess.put(p + x, p + y, n + z, 0, 1, 0);
		tess.put(n + x, p + y, n + z, 0, 1, 0);

		tess.put(n + x, n + y, p + z);
		tess.put(p + x, n + y, p + z);
		tess.put(p + x, n + y, n + z);
		tess.put(n + x, n + y, n + z);
		
		tess.render(Tessellator.QUADS);
	}
	
	public void iterateVisibleTiles(Camera camera, IRender0<Integer, Integer, Integer, Integer> iter)
	{
		for (int x = 0; x < Chunk.chunkWidth; x++)
		{
			for (int y = 0; y < Chunk.chunkHeight; y++)
			{
				Chunk c = world.getChunkFromTileCoords(x, y);
				
				if (c == null)
					continue;
				
				for (int l = 0; l < Chunk.layerCount; l++)
				{
					int id = c.getTileId(x % Chunk.chunkWidth, y % Chunk.chunkHeight, l);
					if (id != 0)
					{
						iter.apply(x, y, l, id);
					}
				}
			}
		}
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
