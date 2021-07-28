package steve6472.sge.test;

import org.lwjgl.opengl.GL11;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.blockbench.model.ModelRepository;
import steve6472.sge.gfx.game.stack.RenderType;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.stack.tess.AbstractTess;
import steve6472.sge.gfx.game.stack.tess.BBTess;
import steve6472.sge.gfx.game.stack.tess.LineTess;
import steve6472.sge.gfx.game.stack.tess.TriangleTess;
import steve6472.sge.gfx.shaders.BBShader;
import steve6472.sge.gfx.shaders.PlainColorShader;
import steve6472.sge.gfx.shaders.StaticShaderBase;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.MainFlags;
import steve6472.sge.main.Window;
import steve6472.sge.main.game.Camera;
import steve6472.sge.main.util.MathUtil;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glEnable;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class BlockbenchRenderTest extends MainApp
{
	public BBShader entityShader;
	public PlainColorShader plainColorShader;
	public ModelRepository models;

	private Stack stack;
	private Camera camera;

	private BBModel model;
	private TriangleTess triangleTess;
	private LineTess lineTess;
	private BBTess bbtess;

	@Override
	protected boolean enableGLDebug()
	{
		return true;
	}

	@Override
	public void init()
	{
		Window.enableVSync(true);
		glEnable(GL_CULL_FACE);

		models = new ModelRepository();
		model = models.loadModel("game/robot");
		models.finish();

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
//			shader.setUniform(BBShader.NORMAL_MATRIX, new Matrix3f(new Matrix4f(stack).invert().transpose3x3()));
			models.getAtlasTexture().bind();

			t.draw(GL11.GL_TRIANGLES);
		}));

		lineTess = new LineTess(stack, 1024 * 64);
		stack.addRenderType("line", new RenderType(plainColorShader, lineTess, (StaticShaderBase s, AbstractTess t) -> t.draw(GL11.GL_LINES)));

		triangleTess = new TriangleTess(stack, 1024 * 64);
		stack.addRenderType("triangle", new RenderType(plainColorShader, triangleTess, (StaticShaderBase s, AbstractTess t) -> t.draw(GL11.GL_TRIANGLES)));

		camera = new Camera();
	}

	@Override
	public void tick()
	{
		camera.headOrbit(getMouseX(), getMouseY(), 0.5f, 2f);
		camera.updateViewMatrix();
	}

	@Override
	public void render()
	{
		stack.reset();
		stack.identity();

		model.render(stack);

		lineTess.axisGizmo(0.5f, 0.05f, 0.25f);
		triangleTess.axisGizmo(0.5f, 0.05f, 0.1f);

		lineTess.color(1, 1, 1, 1);
		lineTess.box(1, 1, 1);

		triangleTess.color(1, 1, 1, 0.5f);
		triangleTess.shadedBox(1, 1, 1);

		stack.render(camera.getViewMatrix());
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
		new BlockbenchRenderTest();
	}
}
