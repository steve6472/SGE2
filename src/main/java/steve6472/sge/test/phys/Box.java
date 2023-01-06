package steve6472.sge.test.phys;

import steve6472.sge.main.MainApp;
import steve6472.sge.main.util.RandomUtil;
import org.joml.Vector2f;

/**********************
 * Created by steve6472
 * On date: 17.05.2019
 * Project: SJP
 *
 ***********************/
public class Box
{
	Vector2f pos, gra, vel;
	float width, height;
	MainApp main;
	boolean isStatic;

	public Box(MainApp main, float x, float y, float width, float height, boolean isStatic)
	{
		this.main = main;
		if (this.isStatic = isStatic)
			gra = new Vector2f(0, 0);
		else
			gra = new Vector2f(0, 9.8f);
		pos = new Vector2f(x, y);
		this.width = width;
		this.height = height;
		vel = new Vector2f(RandomUtil.randomFloat(-1, 1), RandomUtil.randomFloat(-1, 1));
	}

	public boolean collide(Vector2f pos, Vector2f vel, float radius)
	{
		float xCenter = width / 2f + this.pos.x;
		float yCenter = height / 2f + this.pos.y;

		boolean topBottomMid = pos.x > this.pos.x && pos.x < this.pos.x + width;
		boolean leftRightMid = pos.y > this.pos.y && pos.y < this.pos.y + height;

		float maxX = this.pos.x + width;
		float maxY = this.pos.y + height;

		if (pos.x + radius > this.pos.x && pos.x - radius < maxX && pos.y + radius > this.pos.y && pos.y - radius < maxY)
		{
//			pos.y -= ((pos.y + radius) - this.pos.y) * 0.1f;
		}

		if (testSphereAABB(pos.x, pos.y, radius))
		{
			Vector2f p = closestPtPointAABB(pos);
			p.sub(pos);
			p.normalize();
			Ball.reflect(vel, p);

			return true;
		}

		return false;
	}

	public boolean testSphereAABB(float x, float y, float radius)
	{
		// Compute squared distance between sphere center and AABB
		// the sqrt(dist) is fine to use as well, but this is faster.
		float sqDist = sqDistPointAABB(x, y);

		// Sphere and AABB intersect if the (squared) distance between them is
		// less than the (squared) sphere radius.
		return sqDist <= radius * radius;
	}

	// Returns the squared distance between a point p and an AABB b
	float sqDistPointAABB(float x, float y)
	{
		float maxX = pos.x + width;
		float maxY = pos.y + height;

		float sqDist = 0.0f;

		if(x < pos.x) sqDist += (pos.x - x) * (pos.x - x);
		if(x > maxX) sqDist += (x - maxX) * (x - maxX);

		if(y < pos.y) sqDist += (pos.y - y) * (pos.y - y);
		if(y > maxY) sqDist += (y - maxY) * (y - maxY);

		return sqDist;
	}

	Vector2f closestPtPointAABB(Vector2f p)
	{
		float maxX = pos.x + width;
		float maxY = pos.y + height;

		Vector2f q = new Vector2f();

		// For each coordinate axis, if the point coordinate value is
		// outside box, clamp it to the box, else keep it as is
		float v = p.x;
		if(v < pos.x) v = pos.x; // v = max( v, b.min[i] )
		if(v > maxX) v = maxX; // v = min( v, b.max[i] )
		q.x = v;

		v = p.y;
		if(v < pos.y) v = pos.y; // v = max( v, b.min[i] )
		if(v > maxY) v = maxY; // v = min( v, b.max[i] )
		q.y = v;

		return q;
	}

	public boolean collide(Ball b)
	{
		return collide(b.pos, b.vel, 16);
	}


}
