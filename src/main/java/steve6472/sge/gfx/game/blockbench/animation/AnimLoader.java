package steve6472.sge.gfx.game.blockbench.animation;

import org.json.JSONArray;
import org.json.JSONObject;
import steve6472.sge.main.util.MathUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 23.10.2020
 * Project: CaveGame
 *
 ***********************/
public class AnimLoader
{
	public static double load(JSONObject animation, List<Bone> boneList)
	{
		double length = animation.getDouble("length");
		JSONObject animators = animation.getJSONObject("animators");

		for (String s : animators.keySet())
		{
			loadAnimator(animators.getJSONObject(s), boneList);
		}

		return length;
	}

	private static void loadAnimator(JSONObject boneJson, List<Bone> bones)
	{
		JSONArray keyframes = boneJson.getJSONArray("keyframes");

		List<IKey> pos = new ArrayList<>();
		List<IKey> rot = new ArrayList<>();
		List<IKey> siz = new ArrayList<>();

		for (int i = 0; i < keyframes.length(); i++)
		{
			JSONObject keyframe = keyframes.getJSONObject(i);

			List<IKey> list = switch (keyframe.getString("channel"))
			{
				case "position" -> pos;
				case "rotation" -> rot;
				case "scale" -> siz;
				default -> throw new IllegalStateException("Unexpected value: " + keyframe.getString("channel"));
			};

			loadDataPoints(keyframe, list);
		}

		pos.sort(Comparator.comparingDouble(IKey::time));
		rot.sort(Comparator.comparingDouble(IKey::time));
		siz.sort(Comparator.comparingDouble(IKey::time));

		Bone bone = new Bone(boneJson.getString("name"), pos, rot, siz);
		bones.add(bone);
	}

	private static void loadDataPoints(JSONObject json, List<IKey> list)
	{
		JSONArray data_points = json.getJSONArray("data_points");
		if (data_points.length() > 1)
			System.err.println("Animation has more than one data_point JSONObject! (Info from AnimLoader)");
		JSONObject dataPoints = data_points.getJSONObject(0);

		list.add(new Key(
			json.getDouble("time"),
			loadValue(dataPoints.get("x")),
			loadValue(dataPoints.get("y")),
			loadValue(dataPoints.get("z")),
			getType(json.getString("interpolation"))
			)
		);
	}

	private static EnumKeyType getType(String interpolation)
	{
		return switch (interpolation)
		{
			case "linear" -> EnumKeyType.LINEAR;
			case "catmullrom" -> EnumKeyType.CATMULL_ROM;
			default -> throw new IllegalStateException("Unexpected value: " + interpolation);
		};
	}

	private static IKeyValue loadValue(Object o)
	{
		if (o instanceof Number n)
		{
			return new NumericValue(n.floatValue());
		} else if (o instanceof String s)
		{
			if (MathUtil.isDecimal(s))
				return new NumericValue(Float.parseFloat(s));

			return new ExpressionValue(s);
		}

		throw new IllegalArgumentException(o.getClass().getCanonicalName());
	}
}
