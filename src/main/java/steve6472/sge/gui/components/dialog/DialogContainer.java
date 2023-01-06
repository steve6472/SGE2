package steve6472.sge.gui.components.dialog;

import steve6472.sge.main.MainApp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**********************
 * Created by steve6472
 * On date: 17.03.2019
 * Project: SGE2
 *
 ***********************/
public class DialogContainer
{
	public List<Dialog> dialogs, dialogsToAdd;
	private MainApp main;
	public boolean isCursorInAnyDialog = false;

	public DialogContainer(MainApp main)
	{
		this.main = main;
		dialogs = new ArrayList<>();
		dialogsToAdd = new ArrayList<>();
	}

	public Dialog showDialog(Dialog dialog)
	{
		dialog.preInit(main);
		dialog.init(main);
		dialogsToAdd.add(dialog);
		return dialog;
	}

	public void tickDialogs()
	{
		isCursorInAnyDialog = false;

		dialogs.stream().filter(d -> d instanceof MoveableDialog).forEach(d -> ((MoveableDialog) d).dragTick());
		dialogs.forEach(c -> isCursorInAnyDialog = isCursorInAnyDialog || c.isCursorInComponent());

		dialogs.addAll(dialogsToAdd);
		dialogsToAdd.clear();

		for (Iterator<Dialog> iter = dialogs.iterator(); iter.hasNext();)
		{
			Dialog d = iter.next();

			d.fullTick();

			if (d.isClosed())
				iter.remove();
		}
	}

	public void renderDialogs()
	{
		dialogs.forEach(Dialog::fullRender);
	}
}
