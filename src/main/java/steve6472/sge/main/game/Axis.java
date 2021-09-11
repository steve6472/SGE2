package steve6472.sge.main.game;

import org.joml.Vector3f;
import org.joml.Vector3i;

public enum Axis
{
	X, Y, Z;

	public Vector3f getNormal(int offset)
	{
		return switch (this)
			{
				case X -> new Vector3f(offset, 0, 0);
				case Y -> new Vector3f(0, offset, 0);
				case Z -> new Vector3f(0, 0, offset);
			};
	}

	public Vector3i getNormalI(int offset)
	{
		return switch (this)
			{
				case X -> new Vector3i(offset, 0, 0);
				case Y -> new Vector3i(0, offset, 0);
				case Z -> new Vector3i(0, 0, offset);
			};
	}
}