package steve6472.sge.test.test3d;

import org.joml.Vector2d;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.shaders.Shader;
import steve6472.sge.main.KeyList;
import steve6472.sge.main.events.Event;
import steve6472.sge.main.events.KeyEvent;
import steve6472.sge.main.game.Camera;
import steve6472.sge.main.game.stateengine.State;
import steve6472.sge.test.test3d.tiles.DoorTile;
import steve6472.sge.test.test3d.tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.lwjgl.opengl.GL11.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/3/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Topdown implements Itest
{
	private Test3D main;

	private Camera camera2D, camera3D;
	private Stack stack;

	private boolean enable3D = false;

	private Vector2d playerLocation, playerMotion;
	private Facing facing;
	private int animTime;
	private int animFade;

	private State[][] map;

	public Topdown(Test3D main)
	{
		this.main = main;
		main.getEventHandler().register(this);


		camera2D = new Camera();
		camera3D = new Camera();
		stack = new Stack();

		facing = Facing.NORTH;
		playerLocation = new Vector2d();
		playerMotion = new Vector2d();
		animTime = facing.startIndex;

		Tile.init();

		map = new State[36][36];

		BufferedImage mapImage = null;
		try
		{
			mapImage = ImageIO.read(new File("map.png"));
		} catch (Exception ex)
		{
			ex.printStackTrace();
			main.exit();
			System.exit(0);
		}

		for (int i = 0; i < 36; i++)
		{
			for (int j = 0; j < 36; j++)
			{
				int col = mapImage.getRGB(i, j);

				if (col == 0xffffffff || col == 0xffff0000)
				{
					if (mapImage.getRGB(i, j - 1) == 0xff000000)
					{
						map[i][j - 1] = Tile.BRICKS.getDefaultState();
						//						System.out.println("Set brick at " + i + " " + (j - 1));
					}
				}
				if (col == 0xffffffff)
				{
					map[i][j] = Tile.STONE_GROUND.getDefaultState();
					//					System.out.println("Set floor at " + i + " " + (j - 1));
				}

				if (col == 0xffff0000)
				{
					if (mapImage.getRGB(i, j - 1) == 0xffffffff)
					{
						map[i][j] = Tile.DOOR.getDefaultState();
						//						System.out.println("Set door at " + i + " " + j);
					} else
					{
						map[i][j] = Tile.DOOR.getDefaultState().with(DoorTile.FACING_TOP, false).with(DoorTile.OPEN, false).get();
						//						System.out.println("Set other door at " + i + " " + j);
					}
				}
			}
		}

		Shader.releaseShader();
	}

	public void tick()
	{/*
		float aspectRatio = main.getWindowWidth() / (float) main.getWindowHeight();
		Test3D.entityShader.bind();

		if (enable3D)
		{
			Test3D.entityShader.setProjection(MathUtil.createProjectionMatrix(main.getWindowWidth(), main.getWindowHeight(), 256, 80));
		} else
		{
			Test3D.entityShader.setProjection(new Matrix4f().ortho(-1 * aspectRatio, 1 * aspectRatio, -1, 1, 0.001f, 128f));
		}

		Shader.releaseShader();

		stack.reset();
		stack.identity();
		float scale = 4;
		if (!enable3D)
		{
			stack.scale(1f / scale);
		}

		// Player
		stack.pushMatrix();
		stack.translate((float) playerLocation.x, 0, (float) playerLocation.y);
		stack.translate(0, (float) ((playerLocation.y + 1)), 0);
		stack.rotate((float) Math.toRadians(45), 1, 0, 0);
		stack.scale(1, 1, (float) Math.sqrt(2));
		stack.getBlockbenchTess().rectangle(1, 1, facing.startIndex + (animTime / 60) % 3);
		stack.popMatrix();

		for (int i = 0; i < 36; i++)
		{
			for (int j = 0; j < 36; j++)
			{
				final State state = map[i][j];
				if (state != null)
					renderTile(state, i, j);
			}
		}

		int groundSize = 6;
		stack.getBlockbenchTess().color(1, 1, 1, 0.1f);
		stack.getBlockbenchTess().box(-groundSize / 2f, -1, -groundSize / 2f, groundSize, 0, groundSize);

		stack.getBlockbenchTess().color(0.1f, 0.1f, 0.1f, 0.3f);

		for (float i = 0; i < groundSize; i++)
		{
			if (i == 0)
			{
				stack.getBlockbenchTess().color(0.1f, 0.1f, 0.8f, 0.3f);
				stack.getBlockbenchTess().box(-groundSize / 2f + i, -0.999f, -groundSize / 2f, 0.1f, 0, groundSize);
				stack.getBlockbenchTess().color(0.8f, 0.1f, 0.1f, 0.3f);
				stack.getBlockbenchTess().box(-groundSize / 2f, -0.999f, -groundSize / 2f, groundSize, 0, 0.1f);
				stack.getBlockbenchTess().color(0.1f, 0.1f, 0.1f, 0.3f);
				continue;
			}
			stack.getBlockbenchTess().box(-groundSize / 2f + i, -0.999f, -groundSize / 2f, 0.1f, 0, groundSize);
			stack.getBlockbenchTess().box(-groundSize / 2f, -0.999f, groundSize / 2f - i, groundSize, 0, 0.1f);
		}

		if (enable3D)
		{
			//			camera3D.setYaw((float) Math.sin(Math.toRadians((System.currentTimeMillis() % 3600) / 10f)));
			camera3D.setPitch((float) Math.toRadians(-70));
			camera3D.setPosition((float) playerLocation.x, (float) ((playerLocation.y + 1)), (float) playerLocation.y);
			camera3D.calculateOrbit(10);
			//			camera3D.calculateOrbit((float) playerLocation.x, 0, (float) playerLocation.y, 10);

			camera3D.updateViewMatrix();
		} else
		{
			camera2D.setPitch((float) Math.toRadians(-90));
			camera3D.setPosition((float) playerLocation.x / scale, 0, (float) playerLocation.y / scale);
			camera2D.calculateOrbit(120);

			camera2D.updateViewMatrix();
		}

		movePlayer();*/
	}

	public void render()
	{
//		stack.render(enable3D ? camera3D.getViewMatrix() : camera2D.getViewMatrix(), Test3D.debugAtlas);

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		glViewport(0, 0, main.getWidth(), main.getHeight());

		Font.renderFps(5, 5, main.getFps());
		Font.render(5, 15, "animTime: " + animTime);
		Font.render(5, 25, "animFade: " + animFade);
		Font.render(5, 35, "a: " + ((animTime / 60) % 6));

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glViewport(0, 0, main.getWidth(), main.getHeight());
	}

	private void renderTile(State state, int x, int y)
	{/*
		final Tile tile = (Tile) state.getObject();

		if (tile.isWall())
		{
			// Wall
			stack.pushMatrix();
			stack.translate(x, 1 + y, y);
			stack.rotate((float) Math.toRadians(45), 1, 0, 0);
			stack.scale(1, 1, (float) Math.sqrt(2));
			stack.getBlockbenchTess().rectangle(1, 1, tile.getIndex(state));
			stack.popMatrix();
		} else
		{
			// Tile
			stack.pushMatrix();
			stack.translate(x, y - 1, y);
			stack.rotate((float) Math.toRadians(-45), 1, 0, 0);
			stack.scale(1, 1, (float) Math.sqrt(2));
			stack.getBlockbenchTess().rectangle(1, 1, tile.getIndex(state));
			stack.popMatrix();
		}*/
	}

	private void movePlayer()
	{
		double speed = 0.05;

		if (main.isKeyPressed(KeyList.W) || main.isKeyPressed(KeyList.A) || main.isKeyPressed(KeyList.S) || main.isKeyPressed(KeyList.D))
		{
			animFade = 20;
		}

		if (animFade > 0)
			animFade--;
		else
			animTime = 60;

		animTime += animFade % 10;

		if (main.isKeyPressed(KeyList.W))
		{
			playerMotion.add(0, -speed);
			facing = Facing.NORTH;
		}
		if (main.isKeyPressed(KeyList.A))
		{
			playerMotion.add(-speed, 0);
			facing = Facing.WEST;
		}
		if (main.isKeyPressed(KeyList.S))
		{
			playerMotion.add(0, speed);
			facing = Facing.SOUTH;
		}
		if (main.isKeyPressed(KeyList.D))
		{
			playerMotion.add(speed, 0);
			facing = Facing.EAST;
		}

		playerLocation.add(playerMotion);
		playerMotion.mul(0.5);
	}

	@Event
	public void keyPress(KeyEvent e)
	{
		if (e.getAction() == KeyList.PRESS)
		{
			if (e.getKey() == KeyList.G)
				enable3D = !enable3D;
		}
	}

	private enum Facing
	{
		NORTH(250), EAST(234), SOUTH(202), WEST(218);

		int startIndex;

		Facing(int startIndex)
		{
			this.startIndex = startIndex;
		}
	}
}
