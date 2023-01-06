package steve6472.sge.main.node.nodes.gui;

import steve6472.sge.gui.components.Button;
import steve6472.sge.gui.components.TextField;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.node.core.GuiNode;
import steve6472.sge.main.node.core.AbstractNode;
import steve6472.sge.main.node.core.NodeJoint;
import steve6472.sge.main.node.nodes.logic.AbstractConstantNode;

/**********************
 * Created by steve6472
 * On date: 3/20/2021
 * Project: GameMaker
 *
 ***********************/
public class ConstantGuiNode extends GuiNode
{
	public TextField number;
	private Button button;

	public ConstantGuiNode(AbstractNode node)
	{
		super(node);
	}

	@Override
	public void init(MainApp mainApp)
	{
		super.init(mainApp);
		NodeJoint nj = (NodeJoint) getComponent(0);
		nj.setSize(20, 20);
		nj.setRelativeLocation(nj.getRelX() - 5, nj.getRelY());

		AbstractConstantNode<?> node = ((AbstractConstantNode<?>) getNode());

		number = new TextField();
		number.setRelativeLocation(7, 25);
		number.setSize(94, 20);
		number.setText("" + node.constant);
		number.endCarret();
		addComponent(number);

		button = new Button("Set");
		button.setRelativeLocation(7, 50);
		button.setSize(120, 20);
		button.addIfClickEvent((c) -> node.isClickable(number.getText()), (c) -> {
			node.setConstant(number.getText());
			node.updateOutputs();
		});
		addComponent(button);
	}

	@Override
	protected void initSize()
	{
		setSize(134, 77);
	}

	@Override
	public void tick()
	{
		super.tick();
		colorButton(((AbstractConstantNode<?>) getNode()).isClickable(number.getText()), button);
	}

	@Override
	protected void renderNode()
	{

	}

	public static void colorButton(boolean flag, Button button)
	{
		if (flag)
		{
			button.getScheme().setFontColor(0.2f, 0.9f, 0.2f);
		} else
		{
			button.getScheme().setFontColor(0.9f, 0.2f, 0.2f);
		}
	}
}
