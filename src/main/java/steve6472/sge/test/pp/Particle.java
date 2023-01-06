package steve6472.sge.test.pp;

import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.main.game.mixable.IMotion2f;
import steve6472.sge.main.game.mixable.IPosition2f;
import steve6472.sge.main.game.mixable.Killable;
import steve6472.sge.main.util.RandomUtil;
import org.joml.Vector2f;

/**********************
 * Created by steve6472
 * On date: 13.08.2019
 * Project: SJP
 *
 ***********************/
public class Particle implements IMotion2f, IPosition2f, Killable, IMotionUtil
{
	private Vector2f motion, position;
	private float r, g, b, a;

	boolean isDead = false;

	float startingSoftness;
	float size, softness;
	int lifeTime;

	private static float angle;

	public Particle()
	{
		motion = new Vector2f();
		position = new Vector2f();
		lifeTime = RandomUtil.randomInt(60 * 2, 60 * 5);
		size = RandomUtil.randomFloat(3, 6);
		softness = RandomUtil.randomFloat(0.2f, 0.35f);
		startingSoftness = softness;
		circleMotion(angle += 360 / 5 + 1);
		if (angle > 360) angle = angle - 360;
		r = RandomUtil.randomFloat(0, 1);
		g = RandomUtil.randomFloat(0, 1);
		b = RandomUtil.randomFloat(0, 1);
		a = 1;
	}

	@Override
	public Vector2f getMotion()
	{
		return motion;
	}

	@Override
	public Vector2f getPosition()
	{
		return position;
	}

	public void tick()
	{
		if (--lifeTime <= 0)
			setDead();

		if (lifeTime <= 90)
		{
			softness += 1f / 200f;
		}

		if (lifeTime <= 60)
		{
			a -= 1f / 60f;
		}

		getPosition().add(getMotion());
	}

	public void render()
	{
		SpriteRender.drawSoftCircle(getX(), getY(), size, softness, r, g, b, a);
	}

	@Override
	public void setDead()
	{
		isDead = true;
	}

	@Override
	public boolean isDead()
	{
		return isDead;
	}
}
