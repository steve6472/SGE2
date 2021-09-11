package steve6472.sge.gfx.game.blockbench.animation;

import org.joml.Vector3f;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.blockbench.model.OutlinerElement;
import steve6472.sge.main.util.MathUtil;
import steve6472.sge.main.util.Pair;

import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 23.10.2020
 * Project: CaveGame
 *
 ***********************/
public class BBAnimation
{
	private static final Vector3f TEMP = new Vector3f();

	private final String name;
	private final double dLength;
	private final List<Bone> bones;

	private BBModel model;

	public BBAnimation(String name, List<Bone> bones, double dLength)
	{
		this.name = name;
		this.bones = bones;
		this.dLength = dLength;
	}

	public void setModel(BBModel model)
	{
		this.model = model;
	}

	public void tick(BBAnimController controller)
	{
		tick(controller, model);
	}

	public void tick(BBAnimController controller, BBModel model)
	{
		double time;

		if (controller.isPaused())
			time = controller.getPauseTime();
		else
			time = controller.calculateTime(System.currentTimeMillis());

		time *= controller.getSpeed();

		for (Bone bones : bones)
		{
			int posIndex = binarySearch(bones.positions(), time);
			int rotIndex = binarySearch(bones.rotations(), time);
			int scaIndex = binarySearch(bones.scales(), time);

			Pair<IKey, IKey> pos, rot, sca;

			if (controller.isReversed())
			{
				pos = getKeysReversed(bones.positions(), posIndex);
				rot = getKeysReversed(bones.rotations(), rotIndex);
				sca = getKeysReversed(bones.scales(), scaIndex);
			} else
			{
				pos = getKeys(bones.positions(), posIndex);
				rot = getKeys(bones.rotations(), rotIndex);
				sca = getKeys(bones.scales(), scaIndex);
			}

			if (rot != null)
			{
				animate(rot, time, posIndex, bones.positions(), controller.isReversed());

				OutlinerElement element = model.getAnimElements().get(bones.name());
				element.rotationX = (float) Math.toRadians(-TEMP.x());
				element.rotationY = (float) Math.toRadians(-TEMP.y());
				element.rotationZ = (float) Math.toRadians(TEMP.z());
			}

			if (pos != null)
			{
				animate(pos, time, posIndex, bones.positions(), controller.isReversed());

				OutlinerElement element = model.getAnimElements().get(bones.name());
				element.positionX = TEMP.x();
				element.positionY = TEMP.y();
				element.positionZ = TEMP.z();
			}

			if (sca != null)
			{
				animate(sca, time, posIndex, bones.scales(), controller.isReversed());

				OutlinerElement element = model.getAnimElements().get(bones.name());
				element.scaleX = TEMP.x();
				element.scaleY = TEMP.y();
				element.scaleZ = TEMP.z();
			}
		}

		if (time >= dLength)
		{
			if (controller.getAnimationEndEvent() != null)
				controller.getAnimationEndEvent().process();
			if (controller.isLooping())
				controller.start();
			else
				controller.setRunning(false);
		}
	}

	private void animate(Pair<IKey, IKey> pair, double time, int index, List<IKey> keys, boolean reverse)
	{
		IKey before = pair.a();
		IKey after = pair.b();
		double t = MathUtil.time(before.time(), after.time(), time);

		float vx;
		float vy;
		float vz;

		if (before.keyType() == EnumKeyType.CATMULL_ROM)
		{
			IKey past, future;

			if (index > 0)
				past = keys.get(index - 1);
			else
				past = before;

			if (index + 2 < keys.size())
				future = keys.get(index + 2);
			else
				future = after;

			if (reverse)
			{
				vx = (float) MathUtil.catmullLerp(future.x(t), after.x(t), before.x(t), past.x(t), t);
				vy = (float) MathUtil.catmullLerp(future.y(t), after.y(t), before.y(t), past.y(t), t);
				vz = (float) MathUtil.catmullLerp(future.z(t), after.z(t), before.z(t), past.z(t), t);
			} else
			{
				vx = (float) MathUtil.catmullLerp(past.x(t), before.x(t), after.x(t), future.x(t), t);
				vy = (float) MathUtil.catmullLerp(past.y(t), before.y(t), after.y(t), future.y(t), t);
				vz = (float) MathUtil.catmullLerp(past.z(t), before.z(t), after.z(t), future.z(t), t);
			}
		} else
		{
			if (reverse)
			{
				vx = (float) MathUtil.lerp(after.x(t), before.x(t), t);
				vy = (float) MathUtil.lerp(after.y(t), before.y(t), t);
				vz = (float) MathUtil.lerp(after.z(t), before.z(t), t);
			} else
			{
				vx = (float) MathUtil.lerp(before.x(t), after.x(t), t);
				vy = (float) MathUtil.lerp(before.y(t), after.y(t), t);
				vz = (float) MathUtil.lerp(before.z(t), after.z(t), t);
			}
		}

		TEMP.set(vx, vy, vz);
	}

	private int binarySearch(List<IKey> keys, double target)
	{
		if (keys.size() == 0)
			return -1;
		else if (keys.size() == 1)
			return 1;

		if (target <= keys.get(0).time())
			return 0;
		else if (target > keys.get(keys.size() - 1).time())
			return keys.size() - 1;

		int min = 0;
		int max = keys.size() - 1;

		while (min <= max)
		{
			int mid = (min + max) / 2;

			if (target < keys.get(mid).time())
			{
				max = mid - 1;
			} else if (target > keys.get(mid).time())
			{
				min = mid + 1;
			} else
			{
				return mid;
			}
		}

		// Returns closest double, that I do not want
//		return (keys.get(min).time() - target) < (target - keys.get(max).time()) ? min : max;
		// Returns the lower closest value
		return max;
	}

	private Pair<IKey, IKey> getKeys(List<IKey> keys, int index)
	{
		if (index == -1)
			return null;

		if (index == keys.size())
			index -= 1;

		if (index + 1 >= keys.size())
			return new Pair<>(keys.get(index), keys.get(index));

		return new Pair<>(keys.get(index), keys.get(index + 1));
	}

	private Pair<IKey, IKey> getKeysReversed(List<IKey> keys, int index)
	{
		if (index == -1)
			return null;

		index = keys.size() - index;

		if (index == keys.size())
		{
			return new Pair<>(keys.get(index - 2), keys.get(index - 1));
		}

		if (index - 1 <= 0)
		{
			return new Pair<>(keys.get(0), keys.get(0));
		}

		return new Pair<>(keys.get(index), keys.get(index - 1));
	}

	public String getName()
	{
		return name;
	}

	public BBModel getModel()
	{
		return model;
	}

	public double getLength()
	{
		return dLength;
	}
}
