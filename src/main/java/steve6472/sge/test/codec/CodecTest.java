package steve6472.sge.test.codec;

import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**********************
 * Created by steve6472
 * On date: 4/20/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class CodecTest
{
	private static final String rawJson = """
        {
            "age": 3,
            "engine":
            {
                "horse_power": 30,
                "speed": 10
            }
        }
        """;

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException
	{
		final Car object = new Car();
		load(object, new JSONObject(rawJson));
		System.out.println(object);
	}

	private static void load(Object object, JSONObject json) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException
	{
		for (Field declaredField : object.getClass().getDeclaredFields())
		{
			if (declaredField.isAnnotationPresent(IntCodec.class))
			{
				IntCodec codec = declaredField.getAnnotation(IntCodec.class);

				if (json.has(codec.id()))
				{
					declaredField.set(object, json.get(codec.id()));
				} else
				{
					declaredField.set(object, codec.defVal());
				}
			} else if (declaredField.isAnnotationPresent(ObjectCodec.class))
			{
				ObjectCodec codec = declaredField.getAnnotation(ObjectCodec.class);

				if (json.has(codec.id()))
				{
					final Constructor<?> declaredConstructors = declaredField.getType().getDeclaredConstructor();
					final Object o = declaredConstructors.newInstance();

					load(o, json.getJSONObject(codec.id()));

					declaredField.set(object, o);
				} else
				{
					declaredField.set(object, null);
				}
			}
		}
	}
}
