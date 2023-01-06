package steve6472.sge.main.node.core;

import java.util.function.Function;
import java.util.function.Supplier;

/**********************
 * Created by steve6472
 * On date: 16.04.2020
 * Project: NoiseGenerator
 *
 ***********************/
public class NodeEntry
{
	private static final Function<AbstractNode, GuiNode> GUI_NODE = GuiNode::new;

	private final String name;
	private final String id;
	private final Supplier<AbstractNode> create;
	private final Function<AbstractNode, GuiNode> guiNode;

	public NodeEntry(String id, String name, Supplier<AbstractNode> create)
	{
		this.name = name;
		this.id = id;
		this.create = create;
		this.guiNode = GUI_NODE;
	}

	public NodeEntry(String id, String name, Supplier<AbstractNode> create, Function<AbstractNode, GuiNode> guiNode)
	{
		this.name = name;
		this.id = id;
		this.create = create;
		this.guiNode = guiNode;
	}

	public AbstractNode create()
	{
		final AbstractNode abstractNode = create.get();
		abstractNode.setId(id);
		return abstractNode;
	}

	public GuiNode createGuiNode(AbstractNode node)
	{
		return guiNode.apply(node);
	}

	public GuiNode createGuiNode()
	{
		return guiNode.apply(create());
	}

	public String getName()
	{
		return name;
	}

	public String getId()
	{
		return id;
	}
}
