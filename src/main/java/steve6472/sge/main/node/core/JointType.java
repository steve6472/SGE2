package steve6472.sge.main.node.core;

import steve6472.sge.gfx.font.CustomChar;
import steve6472.sge.main.util.ColorUtil;

import java.util.function.Function;

/**********************
 * Created by steve6472
 * On date: 4/22/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class JointType<T>
{
	public final float r, g, b;
	public final String colorTag;
	public final CustomChar unconnected, connected;
	public final Function<String, T> parse;
	public final String example;

	public JointType(int color, CustomChar connected, CustomChar unconnected, Function<String, T> parse, String example)
	{
		float[] col = ColorUtil.getColors(color);
		this.r = col[0];
		this.g = col[1];
		this.b = col[2];
		colorTag = "[" + r + "," + g + "," + b + "]";
		this.connected = connected;
		this.unconnected = unconnected;
		this.parse = parse;
		this.example = example;
	}

	public JointType(int color, CustomChar connected, CustomChar unconnected)
	{
		this(color, connected, unconnected, null, null);
	}
}
