package steve6472.sge.main.node.core;

import steve6472.sge.gfx.font.CustomChar;
import steve6472.sge.main.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**********************
 * Created by steve6472
 * On date: 4/22/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class JointTypeRegistry
{
	private static final List<JointType<?>> TYPES = new ArrayList<>();

	public static final JointType<?> FLOW = registerJoint(0xeeeeee, CustomChar.RIGHT_TRIANGLE_FADE, CustomChar.RIGHT_TRIANGLE);
	public static final JointType<Integer> INT = registerJoint(0xfc00d1, CustomChar.FULL_CIRCLE_FADE, CustomChar.UNSELECTED_CIRCLE, text -> StringUtil.isInteger(text) ? Integer.parseInt(text) : null, "42");
	public static final JointType<Double> DOUBLE = registerJoint(0x96A9FB, CustomChar.FULL_CIRCLE_FADE, CustomChar.UNSELECTED_CIRCLE, text -> StringUtil.isDecimal(text) ? Double.parseDouble(text) : null, "4.2");
	public static final JointType<Float> FLOAT = registerJoint(0xFCC71F, CustomChar.FULL_CIRCLE_FADE, CustomChar.UNSELECTED_CIRCLE, text -> StringUtil.isDecimal(text) ? Float.parseFloat(text) : null, "4.2");
	public static final JointType<String> STRING = registerJoint(0x940000, CustomChar.FULL_CIRCLE_FADE, CustomChar.UNSELECTED_CIRCLE, text -> text, "Hello World");
	public static final JointType<Boolean> BOOLEAN = registerJoint(0x00A8F2, CustomChar.FULL_CIRCLE_FADE, CustomChar.UNSELECTED_CIRCLE, text -> StringUtil.isBoolean(text) ? Boolean.parseBoolean(text) : null, "true");

	public static JointType<?> registerJoint(int color, CustomChar connected, CustomChar unconnected)
	{
		final JointType<?> joint = new JointType<>(color, connected, unconnected);
		TYPES.add(joint);
		return joint;
	}

	public static <T> JointType<T> registerJoint(int color, CustomChar connected, CustomChar unconnected, Function<String, T> parse, String example)
	{
		final JointType<T> joint = new JointType<>(color, connected, unconnected, parse, example);
		TYPES.add(joint);
		return joint;
	}

	public static List<JointType<?>> getTypes()
	{
		return TYPES;
	}
}
