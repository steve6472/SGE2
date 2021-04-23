package steve6472.sge.main.game;

import java.util.Objects;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 17.10.2020
 * Project: CaveGame
 *
 ***********************/
public class VoxPos
{
	private int x;
	private int y;
	private int z;

	public VoxPos(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public VoxPos()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public VoxPos(VoxPos other)
	{
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	public VoxPos up(int n)
	{
		this.y += n;
		return this;
	}

	public VoxPos up()
	{
		return up(1);
	}

	public VoxPos down(int n)
	{
		this.y -= n;
		return this;
	}

	public VoxPos down()
	{
		return down(1);
	}

	public VoxPos north(int n)
	{
		this.z -= n;
		return this;
	}

	public VoxPos north()
	{
		return north(1);
	}

	public VoxPos east(int n)
	{
		this.x += n;
		return this;
	}

	public VoxPos east()
	{
		return east(1);
	}

	public VoxPos south(int n)
	{
		this.z += n;
		return this;
	}

	public VoxPos south()
	{
		return south(1);
	}

	public VoxPos west(int n)
	{
		this.x -= n;
		return this;
	}

	public VoxPos west()
	{
		return west(1);
	}

	public void setPos(int x, int y, int z)
	{
		setX(x);
		setY(y);
		setZ(z);
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getZ()
	{
		return z;
	}

	public void setZ(int z)
	{
		this.z = z;
	}

	public void set(VoxPos other)
	{
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	public void add(VoxPos other)
	{
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
	}

	public VoxPos copy()
	{
		return new VoxPos(this);
	}

	public VoxPos floorMod(int mod)
	{
		this.x = Math.floorMod(x, mod);
		this.y = Math.floorMod(y, mod);
		this.z = Math.floorMod(z, mod);
		return this;
	}

	public boolean equals(int x, int y, int z)
	{
		return this.x == x && this.y == y && this.z == z;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VoxPos blockPos = (VoxPos) o;
		return x == blockPos.x && y == blockPos.y && z == blockPos.z;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(x, y, z);
	}
}
