package steve6472.sge.main.node.nodes.logic;

import org.json.JSONObject;
import steve6472.sge.gui.components.TextField;
import steve6472.sge.main.node.core.AbstractNode;
import steve6472.sge.main.node.core.ExtraData;
import steve6472.sge.main.node.core.JointType;
import steve6472.sge.main.node.core.NodeData;
import steve6472.sge.main.node.nodes.gui.ConstantGuiNode;

/**********************
 * Created by steve6472
 * On date: 3/20/2021
 * Project: GameMaker
 *
 ***********************/
public abstract class AbstractConstantNode<T> extends AbstractNode implements ExtraData
{
	public T constant;

	protected abstract T parseConstant(String text);
	protected abstract JointType getType();
	protected abstract String name();
	public abstract boolean isClickable(String text);
	protected abstract T loadFromJSON(JSONObject json);
	protected abstract T defaultValue();

	public void setConstant(String text)
	{
		constant = parseConstant(text);
		outputStates[0] = constant;
	}

	@Override
	protected void initNode()
	{
		constant = defaultValue();
		outputStates[0] = constant;
	}

	@Override
	protected void updateOutputState()
	{

	}

	@Override
	protected NodeData createInputData()
	{
		return null;
	}

	@Override
	protected NodeData createOutputData()
	{
		return NodeData.createSingle(getType(), "");
	}

	@Override
	public String getName()
	{
		return name() + " " + constant;
	}

	@Override
	public JSONObject save()
	{
		return new JSONObject().put("constant", constant);
	}

	@Override
	public void load(JSONObject json)
	{
		constant = loadFromJSON(json);
		final TextField number = ((ConstantGuiNode) guiNode).number;
		number.setText("" + constant);
		number.endCarret();
	}
}
