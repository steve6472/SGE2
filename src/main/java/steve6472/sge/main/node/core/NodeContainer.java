package steve6472.sge.main.node.core;

import steve6472.sge.main.MainApp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/20/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class NodeContainer
{
	private final List<AbstractNode> nodes;
	private final MainApp main;
	private BiConsumer<MainApp, GuiNode> addNode;

	public NodeContainer(MainApp main)
	{
		nodes = new ArrayList<>();
		this.main = main;
		addNode = (mainApp, dialog) ->
		{
			mainApp.showDialog(dialog);
			dialog.getCloseButton().addClickEvent(c -> {
				nodes.remove(dialog.getNode());
			});
		};
	}

	public void addNode(GuiNode node)
	{
		if (!nodes.contains(node.getNode()))
			nodes.add(node.getNode());
		addNode.accept(main, node);
	}

	public void setAddNodeFunction(BiConsumer<MainApp, GuiNode> a)
	{
		this.addNode = a;
	}

	public List<AbstractNode> getNodes()
	{
		return nodes;
	}

	public void clear()
	{
		nodes.forEach(n -> n.guiNode.close());
		nodes.clear();
	}
}
