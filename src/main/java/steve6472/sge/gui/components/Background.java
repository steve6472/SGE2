/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 2. 2018
* Project: LWJGL
*
***********************/

package steve6472.sge.gui.components;

import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gui.Component;
import steve6472.sge.gui.Gui;
import steve6472.sge.gui.components.schemes.IScheme;
import steve6472.sge.gui.components.schemes.SchemeBackground;
import steve6472.sge.main.MainApp;

public class Background
{
	public static void renderFrame(MainApp app)
	{
		SchemeBackground scheme = ((SchemeBackground) MainApp.getSchemeRegistry().getDefaultScheme(SchemeBackground.class));
		SpriteRender.renderDoubleBorder(0, 0, app.getWidth(), app.getHeight(), scheme.outsideBorder, scheme.insideBorder, scheme.fill);
	}

	public static void renderFrame(MainApp app, SchemeBackground scheme)
	{
		SpriteRender.renderDoubleBorder(0, 0, app.getWidth(), app.getHeight(), scheme.outsideBorder, scheme.insideBorder, scheme.fill);
	}
	
	public static void createComponent(Gui gui)
	{
		gui.addComponent(new BackComp());
	}

}

class BackComp extends Component implements IScheme<SchemeBackground>
{
	private static final long serialVersionUID = 1786536281333133003L;

	private SchemeBackground scheme;

	@Override
	public void init(MainApp game)
	{
		setScheme(MainApp.getSchemeRegistry().copyDefaultScheme(SchemeBackground.class));
	}

	@Override
	public void render()
	{
		Background.renderFrame(getMain(), scheme);
	}

	@Override
	public void tick()
	{
	}

	@Override
	public SchemeBackground getScheme()
	{
		return null;
	}

	@Override
	public void setScheme(SchemeBackground scheme)
	{
		this.scheme = scheme;
	}
}