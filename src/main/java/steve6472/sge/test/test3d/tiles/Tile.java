package steve6472.sge.test.test3d.tiles;

import steve6472.sge.main.game.stateengine.State;
import steve6472.sge.main.game.stateengine.StateObject;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/3/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Tile extends StateObject
{
	public static Tile AIR;
	public static Tile GRASS;
	public static Tile BRICKS;
	public static Tile STONE_GROUND;
	public static Tile DOOR;

	public static Tile DEBUG_WHITE;
	public static Tile DEBUG_0;
	public static Tile DEBUG_1;
	public static Tile DEBUG_TOP_WALL;
	public static Tile DEBUG_TOP;

	public static void init()
	{
		AIR = new Tile(0, false);
		GRASS = new Tile(3, false);
		BRICKS = new Tile(5, true);
		STONE_GROUND = new Tile(6,false);
		DOOR = new DoorTile();

		DEBUG_WHITE = new Tile(0, false);
		DEBUG_0 = new Tile(1, false);
		DEBUG_1 = new Tile(2, false);
		DEBUG_TOP_WALL = new Tile(4, true);
		DEBUG_TOP = new Tile(4, false);
	}

	int index;
	boolean isWall;

	public Tile(int index, boolean isWall)
	{
		this.index = index;
		this.isWall = isWall;
	}

	public Tile(boolean isWall)
	{
		this.isWall = isWall;
	}

	public int getIndex(State state)
	{
		return index;
	}

	public boolean isWall()
	{
		return isWall;
	}
}
