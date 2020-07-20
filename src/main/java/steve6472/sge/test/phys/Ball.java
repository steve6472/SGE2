package steve6472.sge.test.phys;

import steve6472.sge.main.KeyList;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.util.RandomUtil;
import org.joml.Vector2f;

public class Ball
{
	Vector2f pos, gra, vel;
	MainApp main;
	boolean onBox = false;

	public Ball(MainApp main)
	{
		this.main = main;

		gra = new Vector2f(0, 9.8f);
		pos = new Vector2f(RandomUtil.randomInt(40, 16 * 70 - 40), 100);
		vel = new Vector2f(RandomUtil.randomFloat(-1, 1), RandomUtil.randomFloat(-1, 1));
	}

	public void tick()
	{
		if (Float.isNaN(vel.x) || Float.isNaN(vel.y))
			vel.set(0, 0);

		double d = 1f / 60f;

		if (!onBox)
			vel.add(new Vector2f(gra).mul((float) d));

		pos.add(vel);

		if (vel.y > 9.5f)
			vel.y = 9.5f;

//		if (onBox)
//		{
//			if (Math.abs(vel.y) < 0.16)
//			{
//				pos.y = 384;
//			} else
//			{
//				vel.y *= 0.85f;
//			}
//		}

		if (Math.abs(vel.x) < 10e-4)
			vel.x = 0;
		if (Math.abs(vel.y) < 10e-4)
			vel.y = 0;

		if (!onBox)
		{
			vel.mul(0.97f);
		} else
		{
			vel.mul(0.995f);
		}

		if (main.getMouseHandler().getButton() == KeyList.MMB)
		{
			Vector2f newVel = new Vector2f(pos);
			newVel.sub(main.getMouseX(), main.getMouseY());
			vel.sub(newVel.normalize().mul(0.6f));
		}
	}

	boolean collide(Vector2f plane)
	{
		reflect(vel, plane);
		return false;
	}

	public static Vector2f reflect(Vector2f vel, Vector2f norm)
	{
		float dot = norm.x * vel.x + norm.y * vel.y;
		vel.x = vel.x - (dot + dot) * norm.x;
		vel.y = vel.y - (dot + dot) * norm.y;
		return vel;
	}

	boolean collide(Ball ball)
	{
		/*
		Let Norm1 be a unit reflection of Sphere1's velocity vector onto the collision plane
		Let Norm2 be a unit reflection of Sphere2's velocity vector onto the collision plane

		Let Vel1 be the velocity vector of Sphere1
		Let Vel2 be the velocity vector of Sphere2

		Let Mass1 be the mass of Sphere1
		Let Mass2 be the mass of Sphere2

		Let P1, P2 be the momentums(scalars) of Sphere1, Sphere2 respectively ( Momentum = Mass * Velocity )
		P1 = Mass1 * |Vel1|
		P2 = Mass2 * |Vel2|

		Let A1 be the acceleration of Sphere1 on the time of collision
		Let A2 be the acceleration of Sphere2 on the time of collision
		Let Time be the amount of time both sphere's retain contact
		( Force = Mass * acceleration && Force = Momentum / Time <=> acceleration = Momentum / (Mass * Time) )
		A1 = P2 / (Mass1 * Time)
		A2 = P1 / (Mass2 * Time)

		Then newVel1 = Vel1 + A1 * Time = Vel1 + (P2 / Mass1) * Norm1
		And newVel2 = Vel2 + A2 * Time = Vel2 + (P1 / Mass2) * Norm2
		*/

//		Vector2f norm1 = ball.vel.

//		return true;
		return collide(pos, vel, 16, ball.pos, ball.vel, 16);
	}

	boolean collide(Vector2f ball0, Vector2f ball0_vel, float ball0Radius, Vector2f ball1, Vector2f ball1_vel, float ball1Radius)
	{
		Vector2f delta = new Vector2f();
		ball0.sub(ball1, delta);
		float r = ball0Radius + ball1Radius;
		float dis2 = delta.dot(delta);

		if (dis2 > r * r) return false; // Check if balls are colliding

		float d = delta.length();
		Vector2f mtd = new Vector2f(0, 0);
		float f = ((ball0Radius + ball1Radius) - d) / d; // This F is a fix to NaN problem
		if (d != 0.0f)
		{
			if (f == 0.0f)
			{
				delta.mul(0.000001f, mtd);
			} else
			{
				delta.mul(f, mtd);
			}
		} else
		{
			d = r - 1.0f;
			delta.set(r, 0.0f);

			delta.mul(((ball0Radius + ball1Radius) - d) / d, mtd);
		}

		float m1 = 1f / 1f; // 1f / mass
		float m2 = 1f / 1f; // 1f / mass

		ball0.add(new Vector2f(mtd).mul(m1 / (m1 + m2)));
		ball1.sub(new Vector2f(mtd).mul(m2 / (m1 + m2)));

		Vector2f v = new Vector2f();
		ball0_vel.sub(ball1_vel, v);
//				float vn = v.dot(new Vector2f(mtd).normalize());
		float vn = v.dot(mtd.normalize());

		if (vn > 0.0f) return false;

		final float restitution = 1f;

		float i = (-(1.0f + restitution) * vn) / (m1 + m2);
		Vector2f impulse = new Vector2f(mtd).mul(i);

		Vector2f im0 = new Vector2f(impulse).mul(m1);
		Vector2f im1 = new Vector2f(impulse).mul(m2);

		ball0_vel.add(im0);
		ball1_vel.sub(im1);

		return true;
	}
}