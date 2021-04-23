package steve6472.sge.test.node;

import steve6472.sge.main.MainApp;
import steve6472.sge.main.MainFlags;
import steve6472.sge.main.Window;
import steve6472.sge.main.events.Event;
import steve6472.sge.main.events.RegisterNodesEvent;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/21/2021
 * Project: StevesGameEngine
 *
 ***********************/
class NodeTestMain extends MainApp
{
	public static void main(String[] args)
	{
//		ConstantInt constantInt = new ConstantInt();
//		constantInt.setConstant("5");
//
//		PrintIntNode printIntNode = new PrintIntNode();
//
//		Nodes.connect(constantInt, printIntNode, 0, 0);

		new NodeTestMain();
	}

	@Override
	public void init()
	{
		TestNodeRegistry.init();

		Window.enableVSync(true);

		new NodeGuiTest(this);
	}

	@Event
	public void registerNodes(RegisterNodesEvent e)
	{
		e.registerNode("constant:int", "Integer", ConstantInt::new);
		e.registerNode("constant:int:print", "Print Integer", PrintIntNode::new);
	}

	@Override
	public void tick()
	{
		tickGui();
		tickDialogs();
	}

	@Override
	public void render()
	{
		renderGui();
		renderDialogs();
	}

	@Override
	public void setWindowHints()
	{

	}

	@Override
	public int getWindowWidth()
	{
		return 16 * 70;
	}

	@Override
	public int getWindowHeight()
	{
		return 9 * 70;
	}

	@Override
	public void exit()
	{

	}

	@Override
	protected int[] getFlags()
	{
		return new int[] {MainFlags.ADD_BASIC_ORTHO};
	}

	@Override
	public String getTitle()
	{
		return "Node Test";
	}
}
