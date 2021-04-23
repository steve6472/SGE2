package steve6472.sge.main.node.core;

import steve6472.sge.gui.components.dialog.Dialog;
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
	private final List<GuiNode> nodes;
	private final MainApp main;
	private BiConsumer<MainApp, GuiNode> addNode = MainApp::showDialog;

	public NodeContainer(MainApp main)
	{
		nodes = new ArrayList<>();
		this.main = main;
	}

	public void addNode(GuiNode node)
	{
		nodes.add(node);
		addNode.accept(main, node);
	}

	public void setAddNodeFunction(BiConsumer<MainApp, GuiNode> a)
	{
		this.addNode = a;
	}

	public List<GuiNode> getNodes()
	{
		return nodes;
	}

	public void clear()
	{
		nodes.forEach(Dialog::close);
		nodes.clear();
	}
}
