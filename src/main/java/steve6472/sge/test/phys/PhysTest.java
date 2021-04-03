package steve6472.sge.test.phys;

import steve6472.sge.gfx.Render;
import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.main.KeyList;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.MainFlags;
import steve6472.sge.main.Window;
import steve6472.sge.main.events.Event;
import steve6472.sge.main.events.KeyEvent;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.05.2019
 * Project: SJP
 *
 ***********************/
public class PhysTest extends MainApp
{
	List<Ball> balls;
	List<Box> boxes;

	Vector2f pos, gra, vel;
	Vector4f color;

	@Override
	public void init()
	{
		gra = new Vector2f(0, 9.8f);
		pos = new Vector2f(getWidth() / 2f, 100);
		vel = new Vector2f(0, 0);
		color = new Vector4f(1, 0, 0, 1);

		balls = new ArrayList<>();
		for (int i = 0; i < 0; i++)
		{
			balls.add(new Ball(this));
		}

		boxes = new ArrayList<>();
//		boxes.add(new Box(this, 64, 256, 64, 64, true));
		boxes.add(new Box(this, 64, 256, getWidth() - 128, 64, true));

//		for (int i = 0; i < 256; i++)
//			boxes.add(new Box(this, Util.getRandomFloat(getWidth(), 0), Util.getRandomFloat(400, 300), 64, 32, true));

//		boxes.add(new Box(this, 350, 256, 64, 64, true));

//		bottom = new Box(this, 0, 400, getWidth(), 100, true);

		setExitKey(KeyList.ESCAPE);
		Window.enableVSync(true);
	}

	boolean flag = true;
	boolean fbf = true;

	boolean jumpFlag = false;

	@Override
	public void tick()
	{
		if (fbf && !flag)
		{
			return;
		}

		flag = false;

		double d = 1f / 60f;
		double speed = 2 * d;

		double jumpStrenght = 2;
		double maxJumpSpeed = 5;

		if (Float.isNaN(vel.x) || Float.isNaN(vel.y))
			vel.set(0, 0);

		boolean onBox = false;

		// Player Ball Collision
		for (Box box : boxes)
		{
			if (onBox = box.collide(pos, vel, 16))
				jumpFlag = false;
		}

		if (onBox)
		{
			color.set(0, 1, 0, 1);
		} else
		{
			color.set(1, 0, 0, 1);
		}

		if (!onBox)
			vel.add(new Vector2f(gra).mul((float) d));

		if (vel.y > 9.5f)
		{
			vel.y = 9.5f;
		}

		pos.add(vel);


//		if (pos.y >= 384)
//		{
//			if (Math.abs(vel.y) < 0.16)
//			{
//				pos.y = 384;
//			} else
//			{
//				vel.y *= 0.85f;
////				Ball.reflect(vel, new Vector2f(0, -1));
//			}
//			vel.y = 0;
//			jumpFlag = false;
//		}

		if (pos.x < 0)
			pos.x = getWidth();
		if (pos.x > getWidth())
			pos.x = 0;

		if (isKeyPressed(KeyList.D))
			vel.x += speed;

		if (isKeyPressed(KeyList.A))
			vel.x -= speed;

		if (isKeyPressed(KeyList.W) && !jumpFlag)
			vel.y -= jumpStrenght;

		if (!isKeyPressed(KeyList.W) && !jumpFlag)
			jumpFlag = true;

		if (vel.y < -maxJumpSpeed)
			jumpFlag = true;

		if (isKeyPressed(KeyList.S))
			vel.y += speed;

		//Attractor
		if (getMouseHandler().getButton() == KeyList.RMB)
		{
			Vector2f newVel = new Vector2f(pos);
			newVel.sub(getMouseX(), getMouseY());
			vel.sub(newVel.normalize().mul(0.6f));
		}

		if (getMouseHandler().getButton() == KeyList.LMB)
		{
			pos.set(getMouseX(), getMouseY());
		}

		if (Math.abs(vel.x) < 10e-4)
			vel.x = 0;
		if (Math.abs(vel.y) < 10e-4)
			vel.y = 0;

//		if (!onBox)
//		{
//			vel.mul(0.97f);
//		} else
//		{
//			vel.mul(0.995f);
//		}

		for (Ball b : balls)
		{
			b.onBox = false;
			for (Box box : boxes)
			{
				if (box.collide(b))
					b.onBox = true;
			}

			b.tick();

			for (Ball cb : balls)
			{
				b.collide(cb);
			}

			// Player Ball Collision
			if (b.collide(b.pos, b.vel, 16, pos, vel, 16))
			{
				jumpFlag = true;
			}

			if (b.pos.x < 0)
				b.pos.x = getWidth() - 1;
			if (b.pos.x > getWidth())
				b.pos.x = 1;

			if (b.pos.y < 0)
				b.pos.y = getHeight() - 1;
			if (b.pos.y > getHeight())
				b.pos.y = 1;
		}
	}

	@Override
	public void render()
	{
		SpriteRender.manualStart();

		SpriteRender.fillRect(0, 0, getWidth(), getHeight(), 0.2f, 0.2f, 0.2f, 1.0f);

		for (Ball b : balls)
		{
			SpriteRender.drawSoftCircle(b.pos.x, b.pos.y, 16, 0.05f, 0, 1, 1, 1);
		}

		SpriteRender.drawSoftCircle(pos.x, pos.y, 16, 0.05f, color);

		for (Box box : boxes)
		{
			SpriteRender.renderSingleBorder(box.pos.x, box.pos.y, box.width, box.height, 0, 0, 0, 1, 0.4f, 0.4f, 0.4f, 1);
		}
		SpriteRender.manualEnd();

		Font.renderCustom(5, 5, 1, pos.x, "\n", pos.y, "\n", vel.x, "\n", vel.y);

		Render.startLines();
		Render.line(pos.x, pos.y, vel.x + pos.x, vel.y + pos.y, 0xff00ff00);
		for (Ball b : balls)
		{
			Render.line(b.pos.x, b.pos.y, b.vel.x + b.pos.x, b.vel.y + b.pos.y, 0xffffff00);
		}
		Render.endLines();
	}

	@Event
	public void screen(KeyEvent e)
	{
		if (e.getAction() == KeyList.PRESS)
		{
			if (e.getKey() == KeyList.F2)
			{
				takeScreenshot();
			}
			if (e.getKey() == KeyList.Q)
			{
				flag = true;
			}
			if (e.getKey() == KeyList.E)
			{
				fbf = !fbf;
			}
			if (e.getKey() == KeyList.R)
			{
				vel.set(0, 0);
			}
		}
	}

	@Override
	public void setWindowHints()
	{
		Window.setResizable(true);
		Window.setFloating(true);
	}

	@Override
	public int getWindowWidth()
	{
		return 16 * 70;
	}

	@Override
	public int getWindowHeight()
	{
		return 9 * 70;
	}

	@Override
	public void exit()
	{
		getWindow().close();
	}

	@Override
	public String getTitle()
	{
		return "Phys Engine Test";
	}

	@Override
	protected int[] getFlags()
	{
		return new int[] {MainFlags.ADD_BASIC_ORTHO, MainFlags.ENABLE_EXIT_KEY};
	}

	public static void main(String[] args)
	{
		new PhysTest();
	}
}
