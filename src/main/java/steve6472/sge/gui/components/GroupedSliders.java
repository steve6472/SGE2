/**********************
* Created by steve6472
* On date: 25. 1. 2018
* Project: SGE
*
***********************/

package steve6472.sge.gui.components;

@Deprecated
public class GroupedSliders// extends Component
{
	/*
	private static final long serialVersionUID = -8308406769388191L;
	Slider[] sliders;
	String[] texts;
	private int textOrientation = 0;

	public GroupedSliders()
	{
	}

	@Override
	public void init(MainApp game)
	{
	}

	@Override
	public void render()
	{
		UIHelper.renderSingleBorderComponent(this, 0xff3f3f3f, 0xffbfbfbf);
		
		renderComponents();
		
		for (int i = 0; i < texts.length; i++)
		{
			Font.render(texts[i], x + 12, y + 8 + getHeight() / sliders.length * i);
		}
	}

	@Override
	public void tick()
	{
		tickComponents();
	}
	
	@Override
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		
		int index = 0;
		for (Slider s : sliders)
		{
			s.setLocation(x + 12, y + 8 + getHeight() / sliders.length * index + 22);
			index++;
		}
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		setLocation(x, y);
		
		for (Slider s : sliders)
		{
			s.setSize(getWidth() - 24);
		}
	}

	/**
	 * One Slider takes 75 pixels
	 * @param i
	 *//*
	public void setSliderCount(int i)
	{
		sliders = new Slider[i];
		texts = new String[i];
		for (int j = 0; j < i; j++)
		{
			texts[j] = "";
			sliders[j] = new Slider();
			addComponent(sliders[j]);
		}
	}
	
	public void setText(String text, int index)
	{
		texts[index] = text;
	}
	
	public double getValue(int index)
	{
		return sliders[index].getValue();
	}
	
	public int getIValue(int index)
	{
		return sliders[index].getIValue();
	}
	
	public Slider getSlider(int index)
	{
		return sliders[index];
	}

	public void setTextOrientation(int i)
	{
		textOrientation = i;
	}
	
	public int getTextOrientation()
	{
		return textOrientation;
	}
	
	
*/
}
