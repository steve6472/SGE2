package steve6472.sge.test.vox;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL11;
import steve6472.sge.gfx.game.blockbench.ModelRepository;
import steve6472.sge.gfx.game.blockbench.animation.BBAnimController;
import steve6472.sge.gfx.game.blockbench.animation.BBAnimation;
import steve6472.sge.gfx.game.stack.RenderType;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.stack.tess.AbstractTess;
import steve6472.sge.gfx.game.stack.tess.BBTess;
import steve6472.sge.gfx.game.stack.tess.LineTess;
import steve6472.sge.gfx.game.stack.tess.TriangleTess;
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

	private Stack stack;
	private Camera camera;

	private TriangleTess triangleTess;
	private LineTess lineTess;
	private BBTess bbtess;

	private VoxModel worldModel;
	private World world;
	private ThreadedChunkModelBuilder builder;

	private BBAnimController controller;
	private BBAnimation animation;

	public static final VoxLayer MAIN_LAYER = new VoxLayer("main");

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
		Models.init();
		models.finish();

		builder = new ThreadedChunkModelBuilder();
		builder.start();

		worldModel = new VoxModel(MAIN_LAYER, new Vector3i(0, 0, 0));

		world = new World();
		builder.setModelAccessor(world);
		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; j < 2; j++)
			{
				for (int k = 0; k < 2; k++)
				{
					world.setState(Blocks.ROCK.getDefaultState(), new VoxPos(i, k, j));
				}
			}
		}
		world.setState(Blocks.GIZMO.getDefaultState(), new VoxPos(0, 2, 0));
		world.setState(Blocks.DEBUG.getDefaultState(), new VoxPos(2, 0, 0));

		builder.addToQueue(worldModel);

		entityShader = new BBShader();
		entityShader.bind();
		entityShader.setUniform(BBShader.ATLAS, 0);
		entityShader.setProjection(MathUtil.createProjectionMatrix(getWindowWidth(), getWindowHeight(), 256, 80));

		plainColorShader = new PlainColorShader();
		plainColorShader.bind();
		plainColorShader.setProjection(MathUtil.createProjectionMatrix(getWindowWidth(), getWindowHeight(), 256, 80));

		stack = new Stack();

		bbtess = new BBTess(stack, 1024 * 64);
		stack.addRenderType("blockbench", new RenderType(entityShader, bbtess, (StaticShaderBase s, AbstractTess t) -> {

			BBShader shader = (BBShader) s;
			shader.setUniform(BBShader.NORMAL_MATRIX, new Matrix3f(new Matrix4f(stack).invert().transpose3x3()));
			models.getAtlasTexture().bind();

			t.draw(GL11.GL_TRIANGLES);
		}));

		lineTess = new LineTess(stack, 1024 * 64);
		stack.addRenderType("line", new RenderType(plainColorShader, lineTess, (StaticShaderBase s, AbstractTess t) -> t.draw(GL11.GL_LINES)));

		triangleTess = new TriangleTess(stack, 1024 * 64);
		stack.addRenderType("triangle", new RenderType(plainColorShader, triangleTess, (StaticShaderBase s, AbstractTess t) -> t.draw(GL11.GL_TRIANGLES)));

		camera = new Camera();

		/*
		 * Animation
		 */

		controller = new BBAnimController();
		animation = new BBAnimation("game/props/block/piston", "extend", Models.PISTON);

//		controller.setLoop(true);
		controller.start();
		controller.setStayAtLastFrame(true);
	}

	@Override
	public void tick()
	{
		if (isLMBHolded())
		{
			camera.setPosition(0f, 1, 0f);
			camera.headOrbit(getMouseX(), getMouseY(), 0.5f, distance);
			camera.updateViewMatrix();
		}

		if (controller.hasEnded())
		{
			controller.setReverse(!controller.isReversed());
			controller.start();
		}
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

		animation.tick(controller, Models.PISTON);
		Models.PISTON.render(stack);

//		entityShader.bind(camera.getViewMatrix());
//		entityShader.setUniform(BBShader.NORMAL_MATRIX, new Matrix3f(new Matrix4f(stack).invert().transpose3x3()));
//		models.getAtlasTexture().bind();
//		VertexObjectCreator.basicRender(worldModel.getVao(), 4, worldModel.getTriangleCount() * 3, GL11.GL_TRIANGLES);
//
		stack.render(camera.getViewMatrix());
//
//		if (builder.canTake())
//		{
//			PassData take = builder.take();
//			worldModel.update(take);
//		}
	}

	@Event
	public void key(KeyEvent e)
	{
		if (e.getAction() == KeyList.PRESS && e.getKey() == KeyList.R)
		{
			models.reload();
			builder.addToQueue(worldModel);
			animation.reload();
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
