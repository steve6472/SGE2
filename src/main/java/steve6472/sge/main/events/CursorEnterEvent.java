/**********************
* Created by steve6472
* On date: 9. 7. 2018
* Project: SGE2
*
***********************/

package steve6472.sge.main.events;

public class CursorEnterEvent extends AbstractEvent
{
	private final boolean entered;
	
	public CursorEnterEvent(boolean entered)
	{
		this.entered = entered;
	}
	
	public boolean isEntered()
	{
		return entered;
	}

}
