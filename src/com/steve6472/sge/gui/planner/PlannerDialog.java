package com.steve6472.sge.gui.planner;

import com.steve6472.sge.gui.components.Button;
import com.steve6472.sge.gui.components.dialog.AdvancedMoveableDialog;
import com.steve6472.sge.gui.components.dialog.Dialog;
import com.steve6472.sge.main.MainApp;

import java.util.function.Consumer;
import java.util.function.Function;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 04.06.2019
 * Project: SJP
 *
 ***********************/
abstract class PlannerDialog extends AdvancedMoveableDialog
{
	protected Consumer<Button> close;
	protected Planner planner;

	public PlannerDialog(Planner planner, Consumer<Button> close)
	{
		this.close = close;
		this.planner = planner;
	}

	@Override
	public void init(MainApp main)
	{
		setSize(getSize()[0], getSize()[1]);
		initCloseButton();
		((Button) getComponents().get(0)).addClickEvent(c -> close.accept(c));

		Init(main);
	}

	public abstract void Init(MainApp main);

	protected abstract int[] getSize();

	protected void showDialog(Function<MainApp, Dialog> dialogFunction)
	{
		planner.dialog = dialogFunction;
	}

	@Override
	public boolean freezeGui()
	{
		return true;
	}

	@Override
	public boolean disableEvents()
	{
		return true;
	}
}
