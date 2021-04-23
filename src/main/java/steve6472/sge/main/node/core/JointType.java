package steve6472.sge.main.node.core;

import steve6472.sge.gfx.font.CustomChar;
import steve6472.sge.main.util.ColorUtil;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/22/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class JointType
{
	public final float r, g, b;
	public final String colorTag;
	public final CustomChar unconnected, connected;

	public JointType(int color, CustomChar connected, CustomChar unconnected)
	{
		float[] col = ColorUtil.getColors(color);
		this.r = col[0];
		this.g = col[1];
		this.b = col[2];
		colorTag = "[" + r + "," + g + "," + b + "]";
		this.connected = connected;
		this.unconnected = unconnected;
	}
}
