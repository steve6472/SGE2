package steve6472.sge.test.node;

import steve6472.sge.gui.Gui;
import steve6472.sge.gui.components.Background;
import steve6472.sge.gui.components.Button;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.node.core.GuiNode;
import steve6472.sge.main.node.core.NodeContainer;
import steve6472.sge.main.node.core.Nodes;

import java.io.File;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/21/2021
 * Project: StevesGameEngine
 *
 ***********************/
class NodeGuiTest extends Gui
{
	private final NodeContainer nodeContainer;

	public NodeGuiTest(MainApp mainApp)
	{
		super(mainApp);
		setVisible(true);
		nodeContainer = new NodeContainer(mainApp);
	}

	@Override
	public void createGui()
	{
		Background.createComponent(this);

		Button constantInt = new Button("Constant Int Node");
		constantInt.setSize(200, 30);
		constantInt.setLocation(7, 7);
		constantInt.addClickEvent(c -> {
			final GuiNode node = TestNodeRegistry.CONSTANT_INT.createGuiNode();
			nodeContainer.addNode(node);
			node.center();
		});
		addComponent(constantInt);

		Button printInt = new Button("Print Int Node");
		printInt.setSize(200, 30);
		printInt.setLocation(7, 42);
		printInt.addClickEvent(c -> {
			final GuiNode node = TestNodeRegistry.PRINT_INT.createGuiNode();
			nodeContainer.addNode(node);
			node.center();
		});
		addComponent(printInt);

		Button saveGui = new Button("Save Gui");
		saveGui.setSize(200, 30);
		saveGui.setLocation(7, 77);
		saveGui.addClickEvent(c -> {
			Nodes.saveGui(new File("test/nodes_gui_save.json"), nodeContainer);
		});
		addComponent(saveGui);

		Button save = new Button("Save");
		save.setSize(200, 30);
		save.setLocation(7, 112);
		save.addClickEvent(c -> {
			Nodes.save(new File("test/nodes_save.json"), nodeContainer);
		});
		addComponent(save);

		Button loadGui = new Button("Load Gui");
		loadGui.setSize(200, 30);
		loadGui.setLocation(7, 147);
		loadGui.addClickEvent(c -> {
			Nodes.loadGui(new File("test/nodes_gui_save.json"), nodeContainer);
		});
		addComponent(loadGui);
	}

	@Override
	public void guiTick()
	{

	}

	@Override
	public void render()
	{

	}
}
