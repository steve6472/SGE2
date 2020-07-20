package steve6472.sge.test.pp;

import steve6472.sge.gfx.FrameBuffer;
import steve6472.sge.gfx.Sprite;
import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gfx.post.Effect;
import steve6472.sge.gfx.post.PostProcessing;
import steve6472.sge.gui.components.GCLog;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.MainFlags;
import steve6472.sge.main.Window;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.08.2019
 * Project: SJP
 *
 ***********************/
public class PPMain extends MainApp
{
	private FrameBuffer mainFrameBuffer;
	private PostProcessing pp;
	private Effect verticalBlur, horizontalBlur, combine, brightness;
	private Sprite original;

	private GCLog gcLog;

	public static void main(String[] args)
	{
		new PPMain().init();
	}

	@Override
	public void init()
	{
		mainFrameBuffer = new FrameBuffer(this);
		gcLog = new GCLog();
		original = new Sprite("*original.jpg");

		pp = new PostProcessing(getWidth(), getHeight());
//		verticalBlur = new VerticalBlur(getWidth(), getHeight(), 5);
//		horizontalBlur = new HorizontalBlur(getWidth(), getHeight(), 5);
//		combine = new BloomCombine(getWidth(), getHeight());
//		brightness = new Brightness(getWidth(), getHeight());
	}

	@Override
	public void tick()
	{
		gcLog.tick();
	}

	@Override
	public void render()
	{
		mainFrameBuffer.bindFrameBuffer(this);
		FrameBuffer.clearCurrentBuffer();
		Font.renderFps(5, 5, getFps());
		gcLog.render(5, 15);
		mainFrameBuffer.unbindCurrentFrameBuffer(this);

		pp.doPostProcessing(original.getId());

//		SpriteRender.renderSprite(0, 0, getWidth(), getHeight(), 0, original.getId(), getWidth(), getHeight());


//		pp.applyEffect(brightness, original.getId());
//
//		pp.applyEffect(verticalBlur, brightness.getOutTexture());
//		pp.applyEffect(horizontalBlur, verticalBlur.getOutTexture());

//		for (int i = 0; i < 0; i++)
//		{
//			pp.applyEffect(verticalBlur, horizontalBlur.getOutTexture());
//			pp.applyEffect(horizontalBlur, verticalBlur.getOutTexture());
//		}

//		pp.applyEffect(combine, horizontalBlur.getOutTexture(), original.getId());

		SpriteRender.renderSprite(0, 0, getWidth(), getHeight(), 0, pp.combine.getOutTexture());
//		SpriteRender.renderSpriteInverted(0, 0, getWidth(), getHeight(), 0, horizontalBlur.getOutTexture(), getWidth(), getHeight());
//		SpriteRender.renderSpriteInverted(0, 0, getWidth(), getHeight(), 0, combine.getOutTexture(), getWidth(), getHeight());
//		SpriteRender.renderSpriteInverted(0, 0, getWidth(), getHeight(), 0, brightness.getOutTexture(), getWidth(), getHeight());
//		SpriteRender.renderSprite(0, 0, getWidth(), getHeight(), 0, original.getId(), getWidth(), getHeight());

		SpriteRender.renderSprite(0, 0, getWidth(), getHeight(), 0, mainFrameBuffer.texture);
	}

	@Override
	public void setWindowHints()
	{
		Window.setFloating(true);
//		Window.setResizable(true);
	}

	@Override
	public int getWindowWidth()
	{
		return 640;
	}

	@Override
	public int getWindowHeight()
	{
		return 480;
	}

	@Override
	public void exit()
	{
		FrameBuffer.cleanUp();
		getWindow().close();
	}

	@Override
	protected int[] getFlags()
	{
		return new int[] {MainFlags.ADD_BASIC_ORTHO, MainFlags.ENABLE_EXIT_KEY};
	}

	@Override
	public String getTitle()
	{
		return "Post Process Test";
	}
}
