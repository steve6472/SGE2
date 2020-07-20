package steve6472.sge.gui.planner;

import steve6472.sge.gui.components.ItemList;
import steve6472.sge.gui.components.dialog.AdvancedMoveableDialog;
import steve6472.sge.main.MainApp;

import java.util.List;
import java.util.function.Consumer;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 02.06.2019
 * Project: SJP
 *
 ***********************/
class BoxSelectDialog extends AdvancedMoveableDialog
{
	private List<Planner.Box> boxList;
	private Consumer<Integer> consumer;

	private Planner planner;

	public BoxSelectDialog(List<Planner.Box> b, Planner planner)
	{
		boxList = b;
		this.planner = planner;
	}

	@Override
	public void init(MainApp main)
	{
		setSize(128, 192);
		initCloseButton().addClickEvent(c -> planner.inDialog = false);
		ItemList il = new ItemList(7);
		il.setRelativeLocation(7, 24);
		il.setSize(114, 161);
		il.setMultiselect(false);
		il.addChangeEvent(c ->
		{
			consumer.accept(il.getSelectedItemsIndices().get(0));
			close();
		});
		addComponent(il);

		boxList.forEach(c -> il.addItem(c.name));
	}

	public BoxSelectDialog addSelectEvent(Consumer<Integer> accept)
	{
		this.consumer = accept;
		return this;
	}

	@Override
	public void tick()
	{

	}

	@Override
	public void render()
	{
		renderTitle("Select Box");
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
