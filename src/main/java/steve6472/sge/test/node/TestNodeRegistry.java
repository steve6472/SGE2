package steve6472.sge.test.node;

import steve6472.sge.main.node.core.NodeEntry;
import steve6472.sge.main.node.core.Nodes;
import steve6472.sge.main.node.nodes.gui.ConstantGuiNode;

/**********************
 * Created by steve6472
 * On date: 4/22/2021
 * Project: StevesGameEngine
 *
 ***********************/
class TestNodeRegistry
{
	public static final NodeEntry CONSTANT_INT = Nodes.registerNode("constant:int", "Constant Int", ConstantInt::new, ConstantGuiNode::new);
	public static final NodeEntry PRINT_INT = Nodes.registerNode("print:int", "Print Int", PrintIntNode::new);

	public static void init()
	{

	}
}
