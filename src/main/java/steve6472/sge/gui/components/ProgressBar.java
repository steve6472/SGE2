package steve6472.sge.gui.components;

import steve6472.sge.gfx.Render;
import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gui.Component;
import steve6472.sge.main.MainApp;
import steve6472.sge.test.Fex;

import static steve6472.sge.gfx.Render.color;
import static org.lwjgl.opengl.GL11.*;

public class ProgressBar extends Component
{
	private static final long serialVersionUID = 5988633394949666887L;

	private int value = 0, maxValue = 100, minValue = 0;
	
	/**
	 * The rendered value
	 */
	private int phantomValue = 0;
	
	/**
	 * Time of animation
	 */
	int time = 0;
	
	@Override
	public void init(MainApp game)
	{
	}

	@Override
	public void render()
	{
//		UIHelper.renderDoubleBorderComponent(this, 0xff000000, 0xffa8a8a8, 0xff6f6f6f);
		SpriteRender.renderDoubleBorderComponent(this, 0, 0, 0, 1, Fex.Ha8, Fex.Ha8, Fex.Ha8, 1, Fex.H6f, Fex.H6f, Fex.H6f, 1);
		renderSlider();
	}
	
	private void renderSlider()
	{
		int borderColor = 0xff9297B3;
		int fillColor = 0xff7D87BE;
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_QUADS);
		color(borderColor);
		renderPart(x + 2, y + 2, phantomValue, 2);
		renderPart(x + 2, y + getHeight() - 4, phantomValue, 2);
		renderPart(x + 2, y + 4, 2, getHeight() - 8);
		if (phantomValue == maxValue)
		{
			renderPart(x + getWidth() - 4, y + 4, 2, getHeight() - 8);
		}
		color(fillColor);
		renderPart(x + 4, y + 4, phantomValue, getHeight() - 8);
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}
	
	private void renderPart(int x, int y, int w, int h)
	{
		Render.fillRect(x, y, w, h);
	}

	@Override
	public void tick()
	{
		phantomValue = (int) ((value * Math.max(getWidth(), 1)) / Math.max(getMaxValue(), 1));
		if (phantomValue == value)
			return;
		
		double vP = ((value * getMaxValue()) / getWidth());
		double vPercentage = (vP / (double) getMaxValue()) * (double) getWidth();
		
		double pP = (double) ((double) phantomValue * 100d) / (double) getWidth();
		
//		int add = (int) (Math.cos(Math.toRadians(time)) * 5);
		int add = 1;

		if ((int) pP < (int) vPercentage)
		{
			time++;
			phantomValue += add;
			
			updateFilled();
		}

		if ((int) pP > (int) vPercentage)
		{
			time++;
			phantomValue -= add;
			
			updateFilled();
		}
		
		if ((int) pP == (int) vPercentage)
			time = 0;
	}
	
	int oldValue = 0;

	protected void update()
	{
		if (oldValue == value)
			return;
		
		oldValue = value;
	}
	
	private void updateFilled()
	{	
		phantomValue = Math.min(getWidth(), phantomValue);
	}
	
	/*
	 * Operators
	 */
	
	/*
	 * Setters
	 */
	
	public void setValue(int value)
	{
		this.value = value;
		update();
	}
	
	public void setMaxValue(int maxValue)
	{
		this.maxValue = maxValue;
		update();
	}
	
	public void setMinValue(int minValue)
	{
		this.minValue = minValue;
		update();
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		update();
	}
	
	@Override
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		update();
	}
	
	public void forceRepaint()
	{
		updateFilled();
	}
	
	/*
	 * Getters
	 */
	
	public int getValue()
	{
		return value;
	}
	
	public int getMaxValue()
	{
		return maxValue;
	}
	
	public int getMinValue()
	{
		return minValue;
	}
	
	@Override
	protected int getMinHeight()
	{
		return 10;
	}
	
	@Override
	protected int getMinWidth()
	{
		return 10;
	}

}
