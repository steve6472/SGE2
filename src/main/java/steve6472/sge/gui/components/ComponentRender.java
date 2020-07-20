/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 29. 6. 2018
* Project: SGE2
*
***********************/

package steve6472.sge.gui.components;

import steve6472.sge.gui.Component;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.Procedure;

public class ComponentRender extends Component
{
	private static final long serialVersionUID = 3941323655020960510L;
	
	private Procedure render;

	public ComponentRender(Procedure render)
	{
		this.render = render;
	}

	@Override
	public void init(MainApp game)
	{
	}

	@Override
	public void render()
	{
		render.process();
	}

	@Override
	public void tick()
	{
	}

}
