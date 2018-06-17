/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 15. 4. 2018
* Project: MultiplayerTest
*
***********************/

package com.steve6472.sge.gfx.animations;

import com.steve6472.sge.gfx.Model;
import com.steve6472.sge.gfx.Shader;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.main.SGArray;
import com.steve6472.sge.main.Util;

public abstract class KeyframedAnimation extends Animation
{
	SGArray<KeyFrame> keyFrames;
	long currentKeyTimestamp = 0;
	long nextKeyTimestamp = 0;
	int keyFrame = -1;
	boolean remove = false;
	
	int lastId = 0;
	int currentId = 0;

	long totalTime = 0;
	
	float x, y, z, r, b, g, a, sx, sy, sz, ang, rx, ry, rz;
	float ox, oy, oz, or, ob, og, oa, osx, osy, osz, oang, orx, ory, orz;
	float px, py, pz, pr, pb, pg, pa, psx, psy, psz, pang, prx, pry, prz;
	
	Model model;
	Sprite sprite;
	Shader shader;

	SGArray<Float> floats;
	
	public KeyframedAnimation()
	{
		keyFrames = new SGArray<KeyFrame>(0);
		floats = new SGArray<Float>();
		setKeyFrames();
	}

	@Override
	public void render()
	{
		if (keyFrame == -1)
		{
			keyFrame++;
			nextKeyTimestamp = keyFrames.getObject(keyFrame + 1).time;
			KeyFrame kf = keyFrames.getObject(keyFrame);

			// XYZ
			
			ox = kf.x;
			oy = kf.y;
			oz = kf.z;
			
			x = ox;
			y = oy;
			z = oz;
			
			// COLOR
			
			or = kf.r;
			og = kf.g;
			ob = kf.b;
			oa = kf.a;
			
			r = or;
			g = og;
			b = ob;
			a = oa;

			// SCALE
			
			osx = kf.sx;
			osy = kf.sy;
			osz = kf.sz;
			
			sx = osx;
			sy = osy;
			sz = osz;
			
			// ROTATION

			oang = kf.ang;
			orx = kf.rx;
			ory = kf.ry;
			orz = kf.rz;

			/*
			 * I don't know why but if this is not here it will do the first
			 * rotation in a weird way
			 */
			if (oang == 0 && orx == 0 && ory == 0 && orz == 0)
			{
				orz = 1;
			}
			
			ang = oang;
			rx = orx;
			ry = ory;
			rz = orz;
			
			//Model
			model = kf.model;
			
			//Shader
			shader = kf.shader;
			
			//Sprite
			sprite = kf.sprite;
			
		}
		
		KeyFrame keyFrame = keyFrames.getObject(this.keyFrame);
		KeyFrame nextKeyFrame = keyFrames.getObject(this.keyFrame + 1);
		
		this.currentId = keyFrame.id;
		
		float t1 = time - currentKeyTimestamp;
		float t2 = nextKeyTimestamp - currentKeyTimestamp;
		
		Mode mode = null;
		
		for (int i = 0; i < nextKeyFrame.getActs().getSize(); i++)
		{
			int action = nextKeyFrame.getActs().getObject(i);
			
//			System.out.println("Action:" + action + " Time:" + time + " Id: " + currentId);
			
			mode = nextKeyFrame.modes.getObject(i);
			
			if (mode == null)
			{
				System.err.println("Unknow mode for action #" + action + " at frame #" + this.keyFrame);
				continue;
			}
			
			switch (action)
			{
			case 0:
				if (mode == Mode.SET)
				{
					x = Util.calculateValue(t1, t2, ox, nextKeyFrame.x);
					y = Util.calculateValue(t1, t2, oy, nextKeyFrame.y);
					z = Util.calculateValue(t1, t2, oz, nextKeyFrame.z);
				} else if (mode == Mode.ADD)
				{
					px = Util.calculateValue(t1, t2, 0, nextKeyFrame.x);
					py = Util.calculateValue(t1, t2, 0, nextKeyFrame.y);
					pz = Util.calculateValue(t1, t2, 0, nextKeyFrame.z);
				} else if (mode == Mode.BRAZIER_ADD)
				{
					px = Util.bezierCurve(x, nextKeyFrame.bezierX, nextKeyFrame.x, t1, t2);
					py = Util.bezierCurve(y, nextKeyFrame.bezierY, nextKeyFrame.y, t1, t2);
					pz = Util.bezierCurve(z, nextKeyFrame.bezierZ, nextKeyFrame.z, t1, t2);
				} else if (mode == Mode.BRAZIER_SET)
				{
					x = Util.bezierCurve(ox, nextKeyFrame.bezierX, nextKeyFrame.x, t1, t2);
					y = Util.bezierCurve(oy, nextKeyFrame.bezierY, nextKeyFrame.y, t1, t2);
					z = Util.bezierCurve(oz, nextKeyFrame.bezierZ, nextKeyFrame.z, t1, t2);
				} else if (mode == Mode.CHANGE)
				{
					x = nextKeyFrame.x;
					y = nextKeyFrame.y;
					z = nextKeyFrame.z;
				}
				break;
			case 1:
				if (mode == Mode.SET)
				{
					sx = Util.calculateValue(t1, t2, osx, nextKeyFrame.sx);
					sy = Util.calculateValue(t1, t2, osy, nextKeyFrame.sy);
					sz = Util.calculateValue(t1, t2, osz, nextKeyFrame.sz);
				} else if (mode == Mode.ADD)
				{
					psx = Util.calculateValue(t1, t2, 0, nextKeyFrame.sx);
					psy = Util.calculateValue(t1, t2, 0, nextKeyFrame.sy);
					psz = Util.calculateValue(t1, t2, 0, nextKeyFrame.sz);
				} else if (mode == Mode.CHANGE)
				{
					sx = nextKeyFrame.sx;
					sy = nextKeyFrame.sy;
					sz = nextKeyFrame.sz;
				}
				break;
			case 2:
				if (mode == Mode.SET)
				{
					ang = Util.calculateValue(t1, t2, oang, nextKeyFrame.ang);
					rx = Util.calculateValue(t1, t2, orx, nextKeyFrame.rx);
					ry = Util.calculateValue(t1, t2, ory, nextKeyFrame.ry);
					rz = Util.calculateValue(t1, t2, orz, nextKeyFrame.rz);
				} else if (mode == Mode.ADD)
				{
					pang = Util.calculateValue(t1, t2, 0, nextKeyFrame.ang);
					rx = Util.calculateValue(t1, t2, orx, nextKeyFrame.rx);
					ry = Util.calculateValue(t1, t2, ory, nextKeyFrame.ry);
					rz = Util.calculateValue(t1, t2, orz, nextKeyFrame.rz);
				} else if (mode == Mode.CHANGE)
				{
					ang = nextKeyFrame.ang;
					rx = nextKeyFrame.rx;
					ry = nextKeyFrame.ry;
					rz = nextKeyFrame.rz;
				}
				break;
			case 3:
				if (mode == Mode.SET)
				{
					r = Util.calculateValue(t1, t2, or, nextKeyFrame.r);
					g = Util.calculateValue(t1, t2, og, nextKeyFrame.g);
					b = Util.calculateValue(t1, t2, ob, nextKeyFrame.b);
					a = Util.calculateValue(t1, t2, oa, nextKeyFrame.a);
				} else if (mode == Mode.ADD)
				{
					pr = Util.calculateValue(t1, t2, 0, nextKeyFrame.r);
					pg = Util.calculateValue(t1, t2, 0, nextKeyFrame.g);
					pb = Util.calculateValue(t1, t2, 0, nextKeyFrame.b);
					pa = Util.calculateValue(t1, t2, 0, nextKeyFrame.a);
				} else if (mode == Mode.BRAZIER_ADD)
				{
					pr = bezierMethod(nextKeyFrame.bezierX, t1 / t2, r, nextKeyFrame.r);
					pg = bezierMethod(nextKeyFrame.bezierX, t1 / t2, g, nextKeyFrame.g);
					pb = bezierMethod(nextKeyFrame.bezierX, t1 / t2, b, nextKeyFrame.b);
					pa = bezierMethod(nextKeyFrame.bezierX, t1 / t2, a, nextKeyFrame.a);
				} else if (mode == Mode.BRAZIER_SET)
				{
					r = bezierMethod(nextKeyFrame.bezierX, t1 / t2, or, nextKeyFrame.r);
					g = bezierMethod(nextKeyFrame.bezierX, t1 / t2, og, nextKeyFrame.g);
					b = bezierMethod(nextKeyFrame.bezierX, t1 / t2, ob, nextKeyFrame.b);
					a = bezierMethod(nextKeyFrame.bezierX, t1 / t2, oa, nextKeyFrame.a);
				} else if (mode == Mode.CHANGE)
				{
					r = nextKeyFrame.r;
					g = nextKeyFrame.g;
					b = nextKeyFrame.b;
					a = nextKeyFrame.a;
				}
				break;
			case 4:
				//sprite
				sprite = nextKeyFrame.sprite;
				break;
			case 5:
				//model
				model = nextKeyFrame.model;
				break;
			case 6:
				//shader
				shader = nextKeyFrame.shader;
				break;
			}
		}
		
		render(time, x + px, y + py, z + pz, r + pr, g + pg, b + pb, a + pa, sx + psx, sy + psy, sz + psz, ang + pang, rx + prx, ry + pry, rz + prz, sprite, model, shader);
		
		if (time == nextKeyTimestamp)
		{
			this.keyFrame++;
			currentKeyTimestamp = keyFrames.getObject(this.keyFrame).time;
			
			printCurrentData();
			printCurrentOData();
			printCurrentPData();
			
			//Adding P-Values
			
			x += px;
			y += py;
			z += pz;
			
			r += pr;
			g += pg;
			b += pb;
			a += pa;
			
			sx += psx;
			sy += psy;
			sz += psz;
			
			ang += pang;
			rx += prx;
			ry += pry;
			rz += prz;
			
			//Updating O-Values
			
			ox = x;
			oy = y;
			oz = z;
			
			or = r;
			og = g;
			ob = b;
			oa = a;
			
			osx = sx;
			osy = sy;
			osz = sz;
			
			oang = ang;
			orx = rx;
			ory = ry;
			orz = rz;
			
			//Resseting P-Values
			
			px = 0;
			py = 0;
			pz = 0;
			
			pr = 0;
			pg = 0;
			pb = 0;
			pa = 0;
			
			psx = 0;
			psy = 0;
			psz = 0;
			
			pang = 0;
			prx = 0;
			pry = 0;
			prz = 0;

			printCurrentOData();
			printCurrentData();
			
			System.out.println("SWITCH");
			
			if (keyFrames.getSize() <= this.keyFrame + 1)
			{
				remove = true;
				return;
			}

			nextKeyTimestamp = keyFrames.getObject(this.keyFrame + 1).time;
		}
	}
	
	public KeyFrame newFrame(long time)
	{
		KeyFrame frame = new KeyFrame(this, time);
		return frame;
	}
	
	public void printCurrentData()
	{
//		Util.printObjects("N:", x, y, z, r, g, b, a, sx, sy, sz, ang, rx, ry, rz, "Time:", time, "Id:", currentId);
		printData("N:", x, y, z, r, g, b, a, sx, sy, sz, ang, rx, ry, rz);
	}
	
	public void printCurrentPData()
	{
//		Util.printObjects("P:", px, py, pz, pr, pg, pb, pa, psx, psy, psz, pang, prx, pry, prz, "Time:", time, "Id:", currentId);
		printData("P:", px, py, pz, pr, pg, pb, pa, psx, psy, psz, pang, prx, pry, prz);
	}
	
	public void printCurrentOData()
	{
//		Util.printObjects("O:", ox, oy, oz, or, og, ob, oa, osx, osy, osz, oang, orx, ory, orz, "Time:", time, "Id:", currentId);
		printData("O:", ox, oy, oz, or, og, ob, oa, osx, osy, osz, oang, orx, ory, orz);
	}
	
	private void printData(String type, float x, float y, float z, float r, float g, float b, float a, float sx, float sy, float sz, float ang,
			float rx, float ry, float rz)
	{
		Util.printObjects(type, " X:", x, " Y:", y, " Z:", z, " R:", r, " G:", g, " B:", b, " A:", a, " SX:", sx, " SY:", sy, " SZ:", sz, " ANG:",
				ang, " RZ:", rx, " RY:", ry, " RZ:", rz, "Time:", time, "Id:", currentId);
	}
	
	private static float bezierMethod(float point, float time, float lastColor, float nextColor)
	{
		return Util.bezierCurve(lastColor, 0.30f + point, nextColor, time, 1);
	}

	public abstract void setKeyFrames();

	protected abstract void render(long time, float x, float y, float z, float r, float g, float b, float a, float sx, float sy, float sz, float ang,
			float rx, float ry, float rz, Sprite sprite, Model model, Shader shader);

	@Override
	public boolean hasEnded()
	{
		return remove;
	}
	
	public class KeyFrame
	{
		private long time;
		private int id;
		private SGArray<Mode> modes;
		private SGArray<Integer> acts;
		private Shader shader;
		private Sprite sprite;
		private Model model;
		private float x, y, z, r, b, g, a, sx, sy, sz, ang, rx, ry, rz;
		KeyframedAnimation anim;
		
		public KeyFrame(KeyframedAnimation anim, long time)
		{
			this.time = time + totalTime;
			modes = new SGArray<Mode>();
			acts = new SGArray<Integer>();
			totalTime += time;
			this.id = lastId++;
			this.anim = anim;
		}
		
		public KeyFrame translate(float x, float y, float z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
			acts.addObject(0);
			return this;
		}
		
		public KeyFrame scale(float x, float y, float z)
		{
			this.sx = x;
			this.sy = y;
			this.sz = z;
			acts.addObject(1);
			return this;
		}
		
		public KeyFrame scale(float xyz)
		{
			scale(xyz, xyz, xyz);
			return this;
		}
		
		public KeyFrame rotate(float ang, float x, float y, float z)
		{
			this.ang = ang;
			this.rx = x;
			this.ry = y;
			this.rz = z;
			acts.addObject(2);
			return this;
		}
		
		float bezierX, bezierY, bezierZ;
		
		/**
		 * 
		 * Uses old Key Frame values as P0, current as P2 and Brazier Curve points as P1
		 * 
		 * @param x Middle X Point
		 * @param y Middle Y Point
		 * @param z Middle Z Point
		 * @return
		 */
		public KeyFrame addBezierCurvePoint(float x, float y, float z)
		{
			bezierX = x;
			bezierY = y;
			bezierZ = z;
			return this;
		}
		
		public KeyFrame color(float red, float green, float blue, float alpha)
		{
			this.r = red;
			this.g = green;
			this.b = blue;
			this.a = alpha;
			acts.addObject(3);
			return this;
		}
		
		public KeyFrame sprite(Sprite sprite)
		{
			this.sprite = sprite;
			acts.addObject(4);
			return this;
		}
		
		public KeyFrame model(Model model)
		{
			this.model = model;
			acts.addObject(5);
			return this;
		}
		
		public KeyFrame shader(Shader shader)
		{
			this.shader = shader;
			acts.addObject(6);
			return this;
		}
		
		public void finish(Mode... modes)
		{
			for (Mode m : modes)
			{
				this.modes.addObject(m);
			}
			anim.keyFrames.addObject(this);
		}
		
		public SGArray<Integer> getActs()
		{
			return acts;
		}
	}
	
	public enum Mode
	{
		/**
		 * Slowly adds to old value
		 */
		ADD, 
		/**
		 * Slowly sets from old value to new value
		 */
		SET, 
		BRAZIER_ADD, 
		BRAZIER_SET,
		/**
		 * Instantly changes the value
		 */
		CHANGE
	}

}
