package steve6472.sge.main.node.core;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 20.04.2020
 * Project: NoiseGenerator
 *
 ***********************/
public class NodePair
{
	private final AbstractNode node;
	private final int index;

	public NodePair(AbstractNode node, int index)
	{
		this.node = node;
		this.index = index;
	}

	public AbstractNode getNode()
	{
		return node;
	}

	public int getIndex()
	{
		return index;
	}
}
