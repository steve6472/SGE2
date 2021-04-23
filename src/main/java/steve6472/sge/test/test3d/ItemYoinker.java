package steve6472.sge.test.test3d;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/3/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class ItemYoinker// implements Itest
{/*
	private final Test3D main;
	private final Stack stack;
	private final Camera camera3D;

	BBModel ufo, tree, grass, rock, map, beam;
	BBAnimation beamAnim, shakeAnim;
	BBAnimController beamController;

	Vector2f playerMotion, playerPosition;

	private final List<Entity> entities;

	public ItemYoinker(Test3D main)
	{
		this.main = main;
		main.getEventHandler().register(this);

		stack = new Stack();
		camera3D = new Camera();

		ufo = new BBModel("game/ufo");
		tree = new BBModel("game/tree_01");
		grass = new BBModel("game/grass");
		rock = new BBModel("game/rock");
		beam = new BBModel("game/beam");
		entities = new ArrayList<>();
		initMap();

		ModelTextureAtlas.compileTextures(128);
		ModelTextureAtlas.assignTextures(ufo, tree, grass, rock, beam, map);

		beamAnim = new BBAnimation(beam.getName(), "rotate", beam);
		shakeAnim = new BBAnimation("game/shake", "shake");
		beamController = new BBAnimController();
		beamController.setLoop(true);
		beamController.start();

		playerMotion = new Vector2f();
		playerPosition = new Vector2f();
	}

	private int getHeight(PerlinNoise noise, int x, int y, float scale)
	{
		return (int) ((noise.noise(x * scale, 0, y * scale) + 1) / 2f * -MathUtil.smoothstep(2, 15, Vector2f.distance(0, 0, x - 16, y - 16)) * 10f);
	}

	private void initMap()
	{
		final PerlinNoise noise = new PerlinNoise();

		List<Element> elements = new ArrayList<>();

		float scale = 1f / 8f;
		for (int i = 0; i < 32; i++)
		{
			for (int j = 0; j < 32; j++)
			{
				int v = getHeight(noise, i, j, scale) + 7;

				if (v < 4)
					continue;

				Element element = new Element();

				if (getHeight(noise, i, j + 1, scale) + 7 < v)
					element.south = new Element.Face(0, 0, 0, 0, (byte) 0, 1);
				if (getHeight(noise, i, j - 1, scale) + 7 < v)
					element.north = new Element.Face(0, 0, 0, 0, (byte) 0, 1);
				if (getHeight(noise, i + 1, j, scale) + 7 < v)
					element.east = new Element.Face(0, 0, 0, 0, (byte) 0, 1);
				if (getHeight(noise, i - 1, j, scale) + 7 < v)
					element.west = new Element.Face(0, 0, 0, 0, (byte) 0, 1);

				v += 12;

				element.up = new Element.Face(0, 0, 0, 0, (byte) 0, 1);
				element.fromX = i - 16;
				element.fromY = v - 16;
				element.fromZ = j - 16;
				element.toX = i- 15;
				element.toY = v- 15;
				element.toZ = j- 15;
				elements.add(element);

				if ((noise.noise(i / 2f + 1000, 0, j / 2f) + 1) / 2f > 0.6)
				{
					final Entity e = new Entity(tree);
					e.setPosition(i - 16 + 0.5f, v - 15, j - 16 + 0.5f);
					entities.add(e);
				}

				if ((noise.noise(i / 2f - 1000, 0, j / 2f - 500) + 1) / 2f > 8)
				{
					final Entity e = new Entity(rock);
					e.setPosition(i - 16 + 0.5f, v - 15, j - 16 + 0.5f);
					entities.add(e);
				}

				if ((noise.noise(i / 2f - 5050, 0, j / 2f + 500) + 1) / 2f > 2)
				{
					final Entity e = new Entity(grass);
					e.setPosition(i - 16 + 0.5f, v - 15, j - 16 + 0.5f);
					entities.add(e);
				}
			}
		}

		map = new BBModel("Map", elements.toArray(new Element[0]));
	}

	@Override
	public void tick()
	{
		Test3D.entityShader.bind();
		Test3D.entityShader.setProjection(MathUtil.createProjectionMatrix(main.getWindowWidth(), main.getWindowHeight(), 256, 80));
		Shader.releaseShader();

		stack.reset();
		stack.identity();

		stack.pushMatrix();
		stack.translate(playerPosition.x, 6, playerPosition.y);
		ufo.render(stack);
		stack.scale(16, 8, 16);
		stack.popMatrix();

		stack.pushMatrix();
		map.render(stack);
		stack.popMatrix();
		for (Iterator<Entity> iterator = entities.iterator(); iterator.hasNext(); )
		{
			Entity e = iterator.next();
			stack.pushMatrix();
			stack.translate(e.getPosition());

			if (Vector2f.distance(e.getX(), e.getZ(), playerPosition.x, playerPosition.y) <= 0.5)
				stack.getBlockbenchTess().color(0.9f, 0.1f, 0.1f, 1);
			else
			{
				stack.getBlockbenchTess().color(1, 1, 1, 1);
			}

			shakeAnim.tick(e.controller, e.model);

			if (e.sucked)
			{
				e.addPosition(0, 0.015f, 0);

				if (Math.abs(e.getY() - 6) <= 0.5)
				{
					iterator.remove();
					canMove = true;
				}
			}

			e.model.render(stack);
			stack.popMatrix();
		}
		stack.getBlockbenchTess().color(1, 1, 1, 1);

		beamAnim.tick(beamController);

		stack.pushMatrix();
		stack.getBlockbenchTess().color(1, 1, 1, 0.3f);
		stack.translate(playerPosition.x, 2, playerPosition.y);
		stack.scale(1, 128, 1);
		beam.render(stack);
		stack.popMatrix();
		stack.getBlockbenchTess().color(1, 1, 1, 1);

		stack.getBlockbenchTess().box(0, 0, 0, 1, 1, 1);



		camera3D.setPosition(playerPosition.x, 5, playerPosition.y);
		camera3D.setYaw((float) Math.toRadians(-12.5));
		camera3D.setPitch((float) Math.toRadians(-45));
//		camera3D.headOrbit(main.getMouseX(), main.getMouseY(), 0.3f, playerPosition.x / 8f, 0, playerPosition.y / 8f, 5);
		camera3D.calculateOrbit(4);
//		camera3D.headOrbit(main.getMouseX(), main.getMouseY(), 0.3f, playerPosition.x, 6, playerPosition.y, 5);

		camera3D.updateViewMatrix();

		movePlayer();
	}

	boolean canMove = true;

	private void movePlayer()
	{
		float speed = 0.05f;

		if (canMove)
		{
			if (main.isKeyPressed(KeyList.W))
			{
				playerMotion.add(0, -speed);
			}
			if (main.isKeyPressed(KeyList.A))
			{
				playerMotion.add(-speed, 0);
			}
			if (main.isKeyPressed(KeyList.S))
			{
				playerMotion.add(0, speed);
			}
			if (main.isKeyPressed(KeyList.D))
			{
				playerMotion.add(speed, 0);
			}
		}

		playerPosition.add(playerMotion);
		playerMotion.mul(0.5f);
	}

	@Event
	public void key(KeyEvent e)
	{
		if (e.getAction() == KeyList.PRESS)
		{
			if (e.getKey() == KeyList.R && canMove)
			{
				entities.clear();
				initMap();
				ModelTextureAtlas.assignTextures(map);
			}
			if (e.getKey() == KeyList.SPACE && canMove)
			{
				for (Entity entity : entities)
				{
					if (Vector2f.distance(entity.getX(), entity.getZ(), playerPosition.x, playerPosition.y) <= 0.5)
					{
						entity.controller.start();
						canMove = false;
					}
				}
			}
		}
	}

	@Override
	public void render()
	{
		stack.render(camera3D.getViewMatrix(), ModelTextureAtlas.getTexture());
//		stack.render(camera3D.getViewMatrix(), Test3D.debugAtlas);

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		glViewport(0, 0, main.getWidth(), main.getHeight());

		Font.renderFps(5, 5, main.getFps());
		Font.render(5, 15, "Entities: " + entities.size());
		Font.render(5, 25, "Pos: " + playerPosition.x + "/" + playerPosition.y);
		SpriteRender.renderSprite(0, 50, 128, 128, ModelTextureAtlas.getTexture().getId());

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glViewport(0, 0, main.getWidth(), main.getHeight());
	}

	private static class Entity implements IPosition3f
	{
		BBModel model;
		private final Vector3f position = new Vector3f();
		BBAnimController controller;
		boolean sucked;

		public Entity(BBModel model)
		{
			this.model = model;
			controller = new BBAnimController();
			controller.setAnimationEndEvent(() -> {
				addPosition(0, 0.5f, 0);
				sucked = true;
			});
		}

		@Override
		public Vector3f getPosition()
		{
			return position;
		}
	}*/
}
