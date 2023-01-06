/**********************
* Created by steve6472
* On date: 9. 7. 2018
* Project: SGE2
*
***********************/

package steve6472.sge.main.events;

public class CharEvent extends AbstractEvent
{
	private final int codepoint;

	public CharEvent(int codepoint)
	{
		this.codepoint = codepoint;
	}
	
	public int getCodepoint()
	{
		return codepoint;
	}
}
