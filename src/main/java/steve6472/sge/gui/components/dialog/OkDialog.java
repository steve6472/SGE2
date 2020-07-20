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
public class OkDialog extends MessageDialog
{
	/**
	 * @param message    Message to be shown
	 * @param title      The title of the Dialog Window
	 */
	public OkDialog(String message, String title)
	{
		super(message, title, -1);
		height += 50;
	}

	/**
	 * @param message    Message to be shown
	 * @param title      The title of the Dialog Window
	 */
	public OkDialog(String message[], String title)
	{
		super(message, title, -1);
		height += 50;
	}

	/**
	 *
	 * @param message    Message to be shown
	 * @param title      The title of the Dialog Window
	 * @param okEvent   Ok Button Click Event
	 */
	public OkDialog(String message, String title, Consumer<Button> okEvent)
	{
		super(message, title, -1);
		height += 50;
		addOkClickEvent(okEvent);
	}

	protected Button ok;

	@Override
	public void init(MainApp main)
	{
		ok = new Button("Ok");
		ok.setSize(getWidth() - 34, 30);
		ok.setRelativeLocation(17, getHeight() - 42);
		ok.addClickEvent(c -> close());
		addComponent(ok);
	}

	public OkDialog addOkClickEvent(Consumer<Button> c)
	{
		ok.addClickEvent(c);
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
