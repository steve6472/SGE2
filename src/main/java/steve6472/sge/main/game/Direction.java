package steve6472.sge.main.game;

import org.joml.Vector3f;
import org.joml.Vector3i;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/10/2021
 * Project: RoboTest
 *
 ***********************/
public enum Direction
{
	UP(Axis.Y, 1), DOWN(Axis.Y, -1), NORTH(Axis.Z, -1), EAST(Axis.X, 1), SOUTH(Axis.Z, 1), WEST(Axis.X, -1);

	private static final Direction[] VALUES = {UP, DOWN, NORTH, EAST, SOUTH, WEST};

	private final Axis axis;
	private final int offset;
	private final Vector3f normal;
	private final Vector3i normali;

	Direction(Axis axis, int offset)
	{
		this.axis = axis;
		this.offset = offset;

		normal = switch (axis)
		{
			case X -> new Vector3f(offset, 0, 0);
			case Y -> new Vector3f(0, offset, 0);
			case Z -> new Vector3f(0, 0, offset);
		};

		normali = switch (axis)
		{
			case X -> new Vector3i(offset, 0, 0);
			case Y -> new Vector3i(0, offset, 0);
			case Z -> new Vector3i(0, 0, offset);
		};
	}

	public Axis getAxis()
	{
		return axis;
	}

	public int getOffset()
	{
		return offset;
	}

	public Vector3f getNormal()
	{
		return normal;
	}

	public Vector3i getNormalI()
	{
		return normali;
	}

	public VoxPos offset(VoxPos pos)
	{
		VoxPos copy = pos.copy();
		switch (axis)
		{
			case X -> copy.setX(copy.getX() + offset);
			case Y -> copy.setY(copy.getY() + offset);
			case Z -> copy.setZ(copy.getZ() + offset);
		}
		return copy;
	}

	public static Direction[] getValues()
	{
		return VALUES;
	}

	public enum Axis
	{
		X, Y, Z
	}
}
