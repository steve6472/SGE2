package steve6472.sge.main.node.core;

import steve6472.sge.gfx.font.CustomChar;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/22/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class JointTypeRegistry
{
	private static final List<JointType> TYPES = new ArrayList<>();

	public static final JointType FLOW = registerJoint(0xeeeeee, CustomChar.RIGHT_TRIANGLE_FADE, CustomChar.RIGHT_TRIANGLE);
	public static final JointType INT = registerJoint(0xfc00d1, CustomChar.FULL_CIRCLE_FADE, CustomChar.UNSELECTED_CIRCLE);
	public static final JointType DOUBLE = registerJoint(0x96A9FB, CustomChar.FULL_CIRCLE_FADE, CustomChar.UNSELECTED_CIRCLE);
	public static final JointType FLOAT = registerJoint(0xFCC71F, CustomChar.FULL_CIRCLE_FADE, CustomChar.UNSELECTED_CIRCLE);
	public static final JointType STRING = registerJoint(0x940000, CustomChar.FULL_CIRCLE_FADE, CustomChar.UNSELECTED_CIRCLE);
	public static final JointType BOOLEAN = registerJoint(0x00A8F2, CustomChar.FULL_CIRCLE_FADE, CustomChar.UNSELECTED_CIRCLE);

	public static JointType registerJoint(int color, CustomChar connected, CustomChar unconnected)
	{
		final JointType joint = new JointType(color, connected, unconnected);
		TYPES.add(joint);
		return joint;
	}

	public static List<JointType> getTypes()
	{
		return TYPES;
	}
}
