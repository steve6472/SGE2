package steve6472.sge.main.game.mixable;

import org.joml.Vector3f;

/**********************
 * Created by steve6472
 * On date: 2.1.2019
 * Project: SGE2
 *
 ***********************/
public interface IPosition3f
{
	Vector3f getPosition();

	default void setPosition(float x, float y, float z)
	{
		getPosition().set(x, y, z);
	}

	default void addPosition(float x, float y, float z)
	{
		getPosition().add(x, y, z);
	}

	default void addPosition(Vector3f position)
	{
		getPosition().add(position);
	}

	default void setPosition(Vector3f position) { getPosition().set(position); }

	default float getX()
	{
		return getPosition().x;
	}

	default float getY()
	{
		return getPosition().y;
	}

	default float getZ()
	{
		return getPosition().z;
	}

	default void setX(float x)
	{
		getPosition().x = x;
	}

	default void setY(float y)
	{
		getPosition().y = y;
	}

	default void setZ(float z)
	{
		getPosition().z = z;
	}
}
