package steve6472.sge.gui.components.dialog;

import steve6472.sge.gui.components.Button;
import steve6472.sge.main.MainApp;

import java.util.function.Consumer;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 19.03.2019
 * Project: SGE2
 *
 ***********************/
public class YesNoDialog extends MessageDialog
{
	/**
	 * @param message    Message to be shown
	 * @param title      The title of the Dialog Window
	 */
	public YesNoDialog(String message, String title)
	{
		super(message, title, -1);
		height += 50;
	}

	/**
	 *
	 * @param message    Message to be shown
	 * @param title      The title of the Dialog Window
	 * @param yesEvent   Yes Button Click Event
	 * @param noEvent    No Button Click Event
	 */
	public YesNoDialog(String message, String title, Consumer<Button> yesEvent, Consumer<Button> noEvent)
	{
		super(message, title, -1);
		height += 50;
		addYesClickEvent(yesEvent);
		addNoClickEvent(noEvent);
	}

	private Button yes, no;

	@Override
	public void init(MainApp main)
	{
		yes = new Button("Yes");
		yes.setSize(getWidth() / 2 - 22, 30);
		yes.setRelativeLocation(17, getHeight() - 42);
		yes.addClickEvent(c -> close());
		addComponent(yes);

		no = new Button("No");
		no.setSize(getWidth() / 2 - 22, 30);
		no.setRelativeLocation(getWidth() / 2 + 5, getHeight() - 42);
		no.addClickEvent(c -> close());
		addComponent(no);
	}

	public YesNoDialog addYesClickEvent(Consumer<Button> c)
	{
		yes.addClickEvent(c);
		return this;
	}

	public YesNoDialog addNoClickEvent(Consumer<Button> c)
	{
		no.addClickEvent(c);
		return this;
	}

	@Override
	public void tick()
	{
		super.tick();
		tickComponents();
	}

	@Override
	public void render()
	{
		super.render();
		renderComponents();
	}

	@Override
	public boolean freezeGui()
	{
		return true;
	}
}
