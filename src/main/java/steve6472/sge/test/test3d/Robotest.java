package steve6472.sge.test.test3d;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/4/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Robotest// implements Itest
{/*
	private final Test3D main;
	private final Stack stack;
	private final Camera camera3D;
	private float cameraDistance = 1;

	private int cursorTileX, cursorTileY;
	private float cameraX, cameraY, cameraZ;

	private Matrix4f projectionMatrix;

	 *
	 * World objects
	 *
	private final List<Entity> entities;

	StaticModel world;
	BBModel robotModel, tree_01, log_01;
	BBAnimation animation, hand;
	BBAnimController controller, controller2;

	public Robotest(Test3D main)
	{
		this.main = main;
		main.getEventHandler().register(this);

		stack = new Stack();
		camera3D = new Camera();

		robotModel = new BBModel("game/robot");
		tree_01 = new BBModel("game/props/tree_01");
		log_01 = new BBModel("game/props/log_01");
		animation = new BBAnimation(robotModel.getName(), "antenna_wiggle");
		hand = new BBAnimation(robotModel.getName(), "work");
		controller = new BBAnimController();
		controller2 = new BBAnimController();

		ModelTextureAtlas.putTexture("grass");
		ModelTextureAtlas.putTexture("water");
		ModelTextureAtlas.putTexture("sand");
		ModelTextureAtlas.putTexture("dirt");
		ModelTextureAtlas.putTexture("rock_06");

		ModelTextureAtlas.compileTextures(128);
		ModelTextureAtlas.assignTextures(robotModel, tree_01, log_01);

		entities = new ArrayList<>();

		buildWorld();
	}

	private void buildWorld()
	{
		int grassTexture = ModelTextureAtlas.getTextureId("grass");
		int waterTexture = ModelTextureAtlas.getTextureId("water");
		int sandTexture = ModelTextureAtlas.getTextureId("sand");
		int dirtTexture = ModelTextureAtlas.getTextureId("dirt");
		int rockTexture = ModelTextureAtlas.getTextureId("rock_06");

		final Voxelizer voxelizer = new Voxelizer(1);

		float scale = 1f / 32f;
		int size = 48;
		for (int i = -size; i < size; i++)
		{
			for (int k = -size; k < size; k++)
			{
				float value = SimplexNoise.noise(i * scale, k * scale);

				if (value < -0.7f)
				{
					voxelizer.addVoxel(i, 0, k, waterTexture);
				} else if (value < -0.6f)
				{
					voxelizer.addVoxel(i, 0, k, sandTexture);
				} else if (value > 0.7f)
				{
					voxelizer.addVoxel(i, 0, k, rockTexture);
				} else
				{
					voxelizer.addVoxel(i, 0, k, grassTexture);

					if (RandomUtil.randomDouble(0, 1, (long) Objects.hash(i, k) * 133073) <= 0.5 && SimplexNoise.noise(i * scale * 2f + 1000, k * scale * 2f - 8000) * (SimplexNoise.noise(i * scale / 2f - 1000, k * scale / 2f + 8000) - 0.5) > 0)
					{
						Entity tree = new Tree();
						tree.setPosition(i, 0, k);

						entities.add(tree);
					}
				}
			}
		}

		world = StaticModel.fromBBModel(voxelizer.build());
	}

	@Override
	public void tick()
	{
		projectionMatrix = MathUtil.createProjectionMatrix(main.getWindowWidth(), main.getWindowHeight(), 256, 80);

		Test3D.entityShader.bind();
		Test3D.entityShader.setProjection(projectionMatrix);
		Shader.releaseShader();

		if (main.isRMBHolded())
		{
//			camera3D.headOrbit(main.getMouseX(), main.getMouseY(), 0.4f, cameraX, cameraY, cameraZ, cameraDistance);

			if (camera3D.getPitch() > Math.toRadians(-20))
			{
				camera3D.setPitch(Math.toRadians(-20));
			}
		} else
		{
			camera3D.oldx = main.getMouseX();
			camera3D.oldy = main.getMouseY();
		}

//		camera3D.calculateOrbit(cameraX, cameraY, cameraZ, cameraDistance);
		camera3D.updateViewMatrix();

		Vector3f mouseRay = MathUtil.calculateMouseRay(camera3D.getViewMatrix(), projectionMatrix, main.getMouseX(), main.getMouseY(), main.getWidth(), main.getHeight());
		float d = Intersectionf.intersectRayPlane(camera3D.getPosition(), mouseRay, new Vector3f(), new Vector3f(0, 1, 0), 0.001f);
		final Vector3f pos = new Vector3f(mouseRay).mul(d).add(camera3D.getPosition());
		cursorTileX = (int) Math.floor(pos.x);
		cursorTileY = (int) Math.floor(pos.z);

		renderStack();
	}

	private void renderStack()
	{
		stack.reset();
		stack.identity();

		// Render the xyz marker
		stack.getBlockbenchTess().color(1, 0, 0, 1);
		stack.getBlockbenchTess().box(0, 0, 0, 1, 0.01f, 0.01f);
		stack.getBlockbenchTess().color(0, 1, 0, 1);
		stack.getBlockbenchTess().box(0, 0, 0, 0.01f, 1f, 0.01f);
		stack.getBlockbenchTess().color(0, 0, 1, 1);
		stack.getBlockbenchTess().box(0, 0, 0, 0.01f, 0.01f, 1f);
		stack.getBlockbenchTess().color(1, 1, 1, 1);

		// Robot test
		stack.pushMatrix();
		stack.scale(2f);
		animation.tick(controller, robotModel);
		hand.tick(controller2, robotModel);
		robotModel.render(stack);
		stack.popMatrix();

		// World
		stack.pushMatrix();
		stack.translate(0, -1, 0);
		world.render(stack, camera3D.getViewMatrix(), ModelTextureAtlas.getTexture());
		stack.popMatrix();

		// Cursor over 3d object
		stack.pushMatrix();

		Vector3f mouseRay = MathUtil.calculateMouseRay(camera3D.getViewMatrix(), projectionMatrix, main.getMouseX(), main.getMouseY(), main.getWidth(), main.getHeight());
		float d = Intersectionf.intersectRayPlane(camera3D.getPosition(), mouseRay, new Vector3f(), new Vector3f(0, 1, 0), 0.001f);
		final Vector3f pos = new Vector3f(mouseRay).mul(d).add(camera3D.getPosition());
		stack.translate(Math.floor(pos.x) + 0.5f, 0, Math.floor(pos.z) + 0.5f);

		final double c = (Math.sin(Math.toRadians((System.currentTimeMillis() % 3600) / 10f)) * 0.5 + 0.5) * 0.5 + 0.25;

		stack.translate(-0.5f, 0, -0.5f);
		stack.getBlockbenchTess().color((float) c, (float) c, (float) c, 0.5f);
		stack.getBlockbenchTess().box(0, 0, 0, 1, 1f / 64f, 1);

		stack.popMatrix();

		stack.getBlockbenchTess().color(1, 1, 1, 1);

		for (Entity entity : entities)
		{
			stack.pushMatrix();
			stack.translate(entity.getPosition());
			stack.translate(0.5f, 0, 0.5f);

			entity.render(stack);

			stack.popMatrix();
		}
	}

	@Event
	public void key(KeyEvent e)
	{
		if (e.getAction() == KeyList.PRESS)
		{
			if (e.getKey() == KeyList.F)
			{
				controller.start();
			}
			if (e.getKey() == KeyList.R)
			{
				controller2.start();
			}
		}
	}

	private boolean moveFlag;
	private int moveCursorX, moveCursorY;
	
	@Event
	public void mouse(MouseEvent e)
	{
		if (e.getAction() == KeyList.PRESS)
		{
			if (e.getButton() == KeyList.RMB && !moveFlag)
			{
				moveCursorX = main.getMouseX();
				moveCursorY = main.getMouseY();
				moveFlag = true;
			}
		} else if (e.getAction() == KeyList.RELEASE)
		{
			if (e.getButton() == KeyList.RMB && moveFlag)
			{
				moveFlag = false;
				if (moveCursorX == main.getMouseX() && moveCursorY == main.getMouseY())
				{
					cameraX = cursorTileX + 0.5f;
					cameraZ = cursorTileY + 0.5f;
				}
			}
		}
	}

	@Event
	public void scroll(ScrollEvent e)
	{
		cameraDistance = (float) MathUtil.clamp(cameraDistance - e.getyOffset(), 2, 128f);
	}

	@Override
	public void render()
	{
		stack.render(camera3D.getViewMatrix(), ModelTextureAtlas.getTexture());

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		glViewport(0, 0, main.getWidth(), main.getHeight());

		Font.renderFps(5, 5, main.getFps());

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glViewport(0, 0, main.getWidth(), main.getHeight());
	}

	class Tree extends Entity
	{

		@Override
		protected BBModel getModel()
		{
			return tree_01;
		}
	}

	static abstract class Entity implements IPosition3f
	{
		private final Vector3f position = new Vector3f();
		private final BBAnimController animController = new BBAnimController();

		@Override
		public Vector3f getPosition()
		{
			return position;
		}

		public BBAnimController getAnimController()
		{
			return animController;
		}

		public void render(Stack stack)
		{
			getModel().render(stack);
		}

		protected abstract BBModel getModel();
	}*/
}
