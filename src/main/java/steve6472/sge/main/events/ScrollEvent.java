package steve6472.sge.main.events;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 26.8.2018
 * Project: SGE2
 *
 ***********************/
public class ScrollEvent extends AbstractEvent
{
	private final double yOffset;

	public ScrollEvent(double yOffset)
	{
		this.yOffset = yOffset;
	}

	public double getyOffset()
	{
		return yOffset;
	}
}
