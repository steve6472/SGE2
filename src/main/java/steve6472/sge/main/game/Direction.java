package steve6472.sge.main.game;

import org.joml.Vector3f;
import org.joml.Vector3i;

/**********************
 * Created by steve6472
 * On date: 4/10/2021
 * Project: RoboTest
 *
 ***********************/
public enum Direction
{
	UP(Axis.Y, 1), DOWN(Axis.Y, -1), NORTH(Axis.Z, -1), EAST(Axis.X, 1), SOUTH(Axis.Z, 1), WEST(Axis.X, -1);

	private static final Direction[] VALUES = {UP, DOWN, NORTH, EAST, SOUTH, WEST};
	private static final Direction[] CARDINAL = {NORTH, EAST, SOUTH, WEST};
	private static final Direction[] FACES_REVERSED = {WEST, SOUTH, EAST, NORTH, DOWN, UP};

	private final Axis axis;
	private final int offset;
	private final Vector3f normal;
	private final Vector3i normali;

	Direction(Axis axis, int offset)
	{
		this.axis = axis;
		this.offset = offset;
		normal = axis.getNormal(offset);
		normali = axis.getNormalI(offset);
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

	public static Direction[] getValues()
	{
		return VALUES;
	}

	public static Direction[] getFacesReversed()
	{
		return FACES_REVERSED;
	}

	public static Direction[] getCardinal()
	{
		return CARDINAL;
	}

	public Direction getOpposite()
	{
		return switch (this)
			{
				case UP -> DOWN;
				case DOWN -> UP;
				case NORTH -> SOUTH;
				case SOUTH -> NORTH;
				case EAST -> WEST;
				case WEST -> EAST;
			};
	}
	
	public static Direction fromNormal(Vector3f n)
	{
		if (n.x < 0) return WEST;
		else if (n.x > 0) return EAST;
		else if (n.y < 0) return DOWN;
		else if (n.y > 0) return UP;
		else if (n.z < 0) return NORTH;
		else if (n.z > 0) return SOUTH;
		throw new IllegalStateException("Normal is zero!");
	}
}
