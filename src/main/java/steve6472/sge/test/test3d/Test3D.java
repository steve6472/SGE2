package steve6472.sge.test.test3d;

import steve6472.sge.gfx.StaticTexture;
import steve6472.sge.gfx.shaders.BBShader;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.MainFlags;
import steve6472.sge.main.Window;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/3/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Test3D extends MainApp
{
	public static BBShader entityShader;
	public static StaticTexture debugAtlas;

//	private Itest game;

	@Override
	public void init()
	{
		Window.enableVSync(true);

		debugAtlas = StaticTexture.fromTexture("game/debug_atlas.png");

		entityShader = new BBShader();
		entityShader.bind();
		entityShader.setUniform(BBShader.ATLAS, 0);

//		game = new Robotest(this);
	}

	@Override
	public void tick()
	{
//		game.tick();
	}

	@Override
	protected float[] clearColorColors()
	{
		return new float[] {0.1f, 0.5f, 0.8f, 1.0f};
	}

	@Override
	public void render()
	{
//		game.render();
	}

	@Override
	public void setWindowHints()
	{
		Window.setResizable(false);
	}

	@Override
	public int getWindowWidth()
	{
		return 16 * 70;
	}

	@Override
	public int getWindowHeight()
	{
		return 9 * 70;
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

	@Override
	protected int[] getFlags()
	{
		return new int[] {MainFlags.ENABLE_GL_DEPTH_TEST};
	}

	public static void main(String[] args)
	{
		new Test3D();
	}
}
