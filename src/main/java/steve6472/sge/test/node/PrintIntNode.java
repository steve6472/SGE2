package steve6472.sge.test.node;

import steve6472.sge.main.node.core.AbstractNode;
import steve6472.sge.main.node.core.JointTypeRegistry;
import steve6472.sge.main.node.core.NodeData;

/**********************
 * Created by steve6472
 * On date: 4/21/2021
 * Project: StevesGameEngine
 *
 ***********************/
class PrintIntNode extends AbstractNode
{
	@Override
	protected void updateOutputState()
	{
		System.out.println("Integer: " + inputStates[0]);
	}

	@Override
	protected void initNode()
	{

	}

	@Override
	protected NodeData createInputData()
	{
		return NodeData.createSingle(JointTypeRegistry.INT, "number");
	}

	@Override
	protected NodeData createOutputData()
	{
		return NodeData.empty();
	}

	@Override
	public String getName()
	{
		return "Print Integer";
	}
}
