package steve6472.sge.test.node;

import org.json.JSONObject;
import steve6472.sge.main.node.core.JointType;
import steve6472.sge.main.node.core.JointTypeRegistry;
import steve6472.sge.main.node.nodes.logic.AbstractConstantNode;

import java.util.regex.Pattern;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 3/20/2021
 * Project: GameMaker
 *
 ***********************/
class ConstantInt extends AbstractConstantNode<Integer>
{
	private static final Pattern IS_INTEGER = Pattern.compile("([+-]?\\d)+");

	@Override
	protected Integer parseConstant(String text)
	{
		return Integer.parseInt(text);
	}

	@Override
	protected JointType getType()
	{
		return JointTypeRegistry.INT;
	}

	@Override
	protected String name()
	{
		return "Int";
	}

	@Override
	public boolean isClickable(String text)
	{
		return IS_INTEGER.matcher(text).matches();
	}

	@Override
	protected Integer loadFromJSON(JSONObject json)
	{
		return json.getInt("constant");
	}

	@Override
	protected Integer defaultValue()
	{
		return 0;
	}
}
