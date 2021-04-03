package steve6472.sge.test.pp;

import org.joml.Vector2f;
import steve6472.sge.main.util.RandomUtil;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.08.2019
 * Project: SJP
 *
 ***********************/
public interface IMotionUtil
{
	Vector2f getMotion();

	default void circleMotion(float angle, float speed)
	{
		circleMotion(angle);
		getMotion().mul(speed);
	}

	default void circleMotion(float angle)
	{
		double rangle = Math.toRadians(angle);
		double sin = Math.sin(rangle);
		double cos = Math.cos(rangle);
		getMotion().set((float) sin, (float) cos);
	}

	default void randomMotionInCircle()
	{
		double rang = RandomUtil.randomRadian();
		circleMotion((float) rang);
	}
}
