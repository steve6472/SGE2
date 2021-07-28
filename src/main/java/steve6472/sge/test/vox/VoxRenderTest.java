package steve6472.sge.test.vox;

import org.joml.Vector3i;
import org.lwjgl.opengl.GL11;
import steve6472.sge.gfx.game.blockbench.animation.BBAnimController;
import steve6472.sge.gfx.game.blockbench.model.ModelProperty;
import steve6472.sge.gfx.game.blockbench.model.ModelRepository;
import steve6472.sge.gfx.game.blockbench.model.PropertyClass;
import steve6472.sge.gfx.game.blockbench.model.PropertyType;
import steve6472.sge.gfx.game.stack.RenderType;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.stack.tess.*;
import steve6472.sge.gfx.game.voxelizer.ThreadedChunkModelBuilder;
import steve6472.sge.gfx.game.voxelizer.VoxLayer;
import steve6472.sge.gfx.game.voxelizer.VoxModel;
import steve6472.sge.gfx.shaders.BBShader;
import steve6472.sge.gfx.shaders.PlainColorShader;
import steve6472.sge.gfx.shaders.StaticShaderBase;
import steve6472.sge.main.KeyList;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.MainFlags;
import steve6472.sge.main.Window;
import steve6472.sge.main.events.Event;
import steve6472.sge.main.events.KeyEvent;
import steve6472.sge.main.events.ScrollEvent;
import steve6472.sge.main.game.Camera;
import steve6472.sge.main.game.VoxPos;
import steve6472.sge.main.util.MathUtil;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glEnable;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class VoxRenderTest extends MainApp
{
	public BBShader entityShader;
	public PlainColorShader plainColorShader;
	public static ModelRepository models;

	public static Stack stack;
	private Camera camera;

	private TriangleTess triangleTess;
	private LineTess lineTess;
	private BBTess bbtess;

	private VoxModel worldModel;
	private World world;
	private ThreadedChunkModelBuilder builder;

	private BBAnimController controller;

	private static final VoxLayer DOUBLE_SIDED = new VoxLayer("double_sided");

	@Override
	protected boolean enableGLDebug()
	{
		return false;
	}

	@Override
	public void init()
	{
//		Window.enableVSync(true);
		glEnable(GL_CULL_FACE);

		models = new ModelRepository()
		{
			@Override
			public void reload()
			{
				super.reload();
				builder.clearCache();
			}
		};
		models.addLayer(DOUBLE_SIDED);
		models.addProperty(new ModelProperty(PropertyClass.CUBE, PropertyType.BOOLEAN, "collision", () -> false, o -> o));
		models.addProperty(new ModelProperty(PropertyClass.CUBE, PropertyType.BOOLEAN, "hitbox", () -> false, o -> o));
		Models.init();
		models.finish();

		stack = new Stack();

		builder = new ThreadedChunkModelBuilder();
		builder.start();

		worldModel = new VoxModel(ModelRepository.NORMAL_LAYER, new Vector3i(0, 0, 0));

		world = new World();
		builder.setModelAccessor(world);
//		builder.DEBUG = true;
//		for (int i = 0; i < 2; i++)
//		{
//			for (int j = 0; j < 2; j++)
//			{
//				for (int k = 0; k < 2; k++)
//				{
//					world.setState(Blocks.ROCK.getDefaultState(), new VoxPos(i, k, j));
//				}
//			}
//		}
		world.setState(Blocks.OUTLINE.getDefaultState(), new VoxPos(0, 0, 0));
//		world.setState(Blocks.ROCK.getDefaultState(), new VoxPos(0, 0, 0));
//		world.setState(Blocks.DEBUG.getDefaultState(), new VoxPos(0, 1, 0));

		System.out.println(Models.OUTLINE_);

		entityShader = new BBShader();
		entityShader.bind();
		entityShader.setUniform(BBShader.ATLAS, 0);
		entityShader.setProjection(MathUtil.createProjectionMatrix(getWindowWidth(), getWindowHeight(), 256, 80));

		plainColorShader = new PlainColorShader();
		plainColorShader.bind();
		plainColorShader.setProjection(MathUtil.createProjectionMatrix(getWindowWidth(), getWindowHeight(), 256, 80));

		bbtess = new BBTess(stack, 1024 * 64);
		stack.addRenderType("blockbench", new RenderType(entityShader, bbtess, (StaticShaderBase s, AbstractTess t) -> {

			models.getAtlasTexture().bind();

			t.draw(GL11.GL_TRIANGLES);
		}));

		lineTess = new LineTess(stack, 1024 * 64);
		stack.addRenderType("line", new RenderType(plainColorShader, lineTess, (StaticShaderBase s, AbstractTess t) -> t.draw(GL11.GL_LINES)));

		triangleTess = new TriangleTess(stack, 1024 * 64);
		stack.addRenderType("triangle", new RenderType(plainColorShader, triangleTess, (StaticShaderBase s, AbstractTess t) -> t.draw(GL11.GL_TRIANGLES)));

		GL11.glPointSize(2f);

		PointTess pointTess = new PointTess(stack, 1024 * 64);
		stack.addRenderType("point", new RenderType(plainColorShader, pointTess, (StaticShaderBase s, AbstractTess t) -> t.draw(GL11.GL_POINTS)));

		camera = new Camera();

		/*
		 * Animation
		 */

		controller = new BBAnimController();

//		controller.setLoop(true);
		controller.start();
		controller.setStayAtLastFrame(true);
	}

	@Override
	public void tick()
	{
		if (isLMBHolded())
		{
			camera.setPosition(0f, 0, 0f);
			camera.headOrbit(getMouseX(), getMouseY(), 0.5f, distance);
			camera.updateViewMatrix();
		}

		if (controller.hasEnded())
		{
			controller.setReverse(!controller.isReversed());
			controller.start();
		}
		controller.setSpeed(0.1);
	}

	private float distance = 16f;

	@Event
	public void scroll(ScrollEvent e)
	{
		distance -= e.getyOffset() / 10f;
	}

	@Override
	public void render()
	{
		stack.reset();
		stack.identity();

		lineTess.axisGizmo(0.5f, 0.05f, 0.25f);
		triangleTess.axisGizmo(0.5f, 0.05f, 0.1f);

		Models.PISTON.getAnimation("extend").tick(controller);
		Models.PISTON.render(stack);

		worldModel.update(builder.rebuild_(worldModel));

//		entityShader.bind(camera.getViewMatrix());
//		models.getAtlasTexture().bind();
//		VertexObjectCreator.basicRender(worldModel.getVao(), 4, worldModel.getTriangleCount() * 3, GL11.GL_TRIANGLES);

//		GL11.glPointSize(4f);
//		glDisable(GL_DEPTH_TEST);
		stack.render(camera.getViewMatrix());
//		glEnable(GL_DEPTH_TEST);

//		if (builder.canTake())
//		{
//			PassData take = builder.take();
//			worldModel.update(take);
//
//			builder.addToQueue(worldModel);
//		}
	}

	@Event
	public void key(KeyEvent e)
	{
		if (e.getAction() == KeyList.PRESS && e.getKey() == KeyList.R)
		{
			models.reload();
		}
		if (e.getAction() == KeyList.PRESS && e.getKey() == KeyList.SPACE)
		{
			controller.start();
		}
	}

	@Override
	protected float[] clearColorColors()
	{
		return new float[] {0.1f, 0.5f, 0.8f, 1.0f};
	}

	@Override
	public void setWindowHints()
	{
		Window.setResizable(false);
//		Window.setFloating(true);
	}

	@Override
	public int getWindowWidth()
	{
		return 16 * 60;
	}

	@Override
	public int getWindowHeight()
	{
		return 9 * 60;
	}

	@Override
	protected int[] getFlags()
	{
		return new int[] {MainFlags.ENABLE_GL_DEPTH_TEST};
	}

	@Override
	public void exit()
	{
		getWindow().close();
	}

	@Override
	public String getTitle()
	{
		return "Blockbench render test";
	}

	public static void main(String[] args)
	{
		System.setProperty("joml.format", "false");
		System.setProperty("joml.fastmath", "true");
		System.setProperty("joml.sinLookup", "true");

		new VoxRenderTest();
	}
}
