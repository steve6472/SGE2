package steve6472.sge.test.test3d;

import steve6472.sge.gfx.font.Font;
import steve6472.sge.gfx.game.blockbench.ModelTextureAtlas;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.blockbench.animation.BBAnimController;
import steve6472.sge.gfx.game.blockbench.animation.BBAnimation;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.shaders.Shader;
import steve6472.sge.main.game.Camera;
import steve6472.sge.main.util.MathUtil;

import static org.lwjgl.opengl.GL11.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/3/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class AnimTest implements Itest
{
	private final Test3D main;
	private final Stack stack;
	private final Camera camera3D;

	BBModel model;
	BBAnimation animation;
	BBAnimController controller;
	ModelTextureAtlas atlas;

	public AnimTest(Test3D main)
	{
		this.main = main;

		stack = new Stack();
		camera3D = new Camera();
		atlas = new ModelTextureAtlas();

		model = new BBModel(atlas, "game/model");
		animation = new BBAnimation(model.getName(), "test", model);
		controller = new BBAnimController();
		controller.setLoop(true);
		controller.start();
	}

	@Override
	public void tick()
	{
		Test3D.entityShader.bind();
		Test3D.entityShader.setProjection(MathUtil.createProjectionMatrix(main.getWindowWidth(), main.getWindowHeight(), 256, 80));
		Shader.releaseShader();

		stack.reset();
		stack.identity();

		animation.tick(controller);
		model.render(stack);

		camera3D.setYaw((float) Math.sin(Math.toRadians((System.currentTimeMillis() % 3600) / 10f)));
		camera3D.setPitch((float) Math.toRadians(-45));
		camera3D.calculateOrbit(3);

		camera3D.updateViewMatrix();
	}

	@Override
	public void render()
	{
//		stack.render(camera3D.getViewMatrix(), Test3D.debugAtlas);

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		glViewport(0, 0, main.getWidth(), main.getHeight());

		Font.renderFps(5, 5, main.getFps());

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glViewport(0, 0, main.getWidth(), main.getHeight());
	}
}
