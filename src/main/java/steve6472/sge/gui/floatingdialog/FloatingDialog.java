package steve6472.sge.gui.floatingdialog;

import org.joml.Vector3f;
import steve6472.sge.gfx.FrameBuffer;
import steve6472.sge.gui.Gui;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.game.mixable.IPosition3f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 19.07.2020
 * Project: FloatingDialogs
 *
 ***********************/
public abstract class FloatingDialog extends Gui implements IPosition3f
{
	private final Vector3f position;
	private final FrameBuffer frameBuffer;
	private float scaleModifier;
	private final int width, height;

	public FloatingDialog(MainApp main, int width, int height)
	{
		super(main);
		main.getGuis().remove(this);

		scaleModifier = 1f;
		this.width = width;
		this.height = height;
		position = new Vector3f();
		frameBuffer = new FrameBuffer(width, height, true);
		createGUI();
	}

	/**
	 * Do NOT use this for gui construcion!
	 * width & height are not initialized
	 */
	@Override
	@Deprecated
	public void createGui()
	{

	}

	public abstract void createGUI();

	public abstract float getSizeX();
	public abstract float getSizeY();

	public abstract boolean repaint();
	public abstract boolean isActive();
	public abstract boolean shouldBeRemoved();

	@Override
	public Vector3f getPosition()
	{
		return position;
	}

	public float getScaleModifier()
	{
		return scaleModifier;
	}

	public void setScaleModifier(float scaleModifier)
	{
		this.scaleModifier = scaleModifier;
	}

	public FrameBuffer getFrameBuffer()
	{
		return frameBuffer;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
