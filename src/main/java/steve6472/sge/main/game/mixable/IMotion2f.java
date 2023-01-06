package steve6472.sge.main.game.mixable;

import org.joml.Vector2f;

/**********************
 * Created by steve6472
 * On date: 2.1.2019
 * Project: SGE2
 *
 ***********************/
public interface IMotion2f
{
	Vector2f getMotion();

	default void setMotion(float x, float y)
	{
		getMotion().set(x, y);
	}

	default void setMotion(Vector2f motion)
	{
		getMotion().set(motion);
	}

	default float getMotionX()
	{
		return getMotion().x;
	}

	default float getMotionY()
	{
		return getMotion().y;
	}

	default void setMotionX(float x)
	{
		getMotion().x = x;
	}

	default void setMotionY(float y)
	{
		getMotion().y = y;
	}
}
