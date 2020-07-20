package steve6472.sge.gui.components;

import steve6472.sge.gui.components.context.ContextMenuToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11.3.2019
 * Project: SJP
 *
 ***********************/
public class RadioGroup
{
	public static void addToggleButtons(ToggleButton... tbs)
	{
		List<ToggleButton> buttons = new ArrayList<>();
		Collections.addAll(buttons, tbs);

		buttons.forEach(tb ->
		{
			tb.addIfClickEvent(b -> ((ToggleButton) b).isToggled(), c ->
			{
				buttons.forEach(tml ->
				{ /* Too Many Lambdas */
					if (tml != c)
						tml.setToggled(false);
				});
			});
			tb.addIfClickEvent(b -> !((ToggleButton) b).isToggled(), c -> ((ToggleButton) c).setToggled(true));
		});
	}

	public static void addContextMenuToggleButtons(ContextMenuToggleButton... tbs)
	{
		List<ContextMenuToggleButton> buttons = new ArrayList<>();
		Collections.addAll(buttons, tbs);

		buttons.forEach(tb ->
		{
			tb.allowRadio();
			tb.addIfClickEvent(ContextMenuToggleButton::isToggled, c ->
			{
				buttons.forEach(tml ->
				{
					if (tml != c)
						tml.setToggled(false);
				});
			});
			tb.addIfClickEvent(b -> !b.isToggled(), c -> c.setToggled(true));
		});
	}
}






