package steve6472.sge.main.game.mixable;

import org.joml.Vector3f;

/**********************
 * Created by steve6472
 * On date: 2.1.2019
 * Project: SGE2
 *
 ***********************/
public interface IMotion3f
{
	Vector3f getMotion();

	default void setMotion(float x, float y, float z)
	{
		getMotion().set(x, y, z);
	}

	default void setMotion(Vector3f motion) { getMotion().set(motion); }

	default float getMotionX()
	{
		return getMotion().x;
	}

	default float getMotionY()
	{
		return getMotion().y;
	}

	default float getMotionZ()
	{
		return getMotion().z;
	}

	default void setMotionX(float x)
	{
		getMotion().x = x;
	}

	default void setMotionY(float y)
	{
		getMotion().y = y;
	}

	default void setMotionZ(float z)
	{
		getMotion().z = z;
	}
}
