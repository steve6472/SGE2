package steve6472.sge.main.game.registry;

import steve6472.sge.main.game.Id;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/21/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class ObjectHolders
{
	public Set<Class<?>> classes = new HashSet<>();

	public void add(Class<?> clazz)
	{
		classes.add(clazz);
	}

	public void remove(Class<?> clazz)
	{
		classes.remove(clazz);
	}

	public void load() throws IllegalAccessException, NoSuchMethodException
	{
		for (Class<?> clazz : classes)
		{
			for (Field declaredField : clazz.getDeclaredFields())
			{
				if (Modifier.isPublic(declaredField.getModifiers()) && Modifier.isStatic(declaredField.getModifiers()))
				{
					if (declaredField.isAnnotationPresent(ObjectHolder.class))
					{
						ObjectHolder holder = declaredField.getAnnotation(ObjectHolder.class);

						Registry<?> registry = Registry.getRegistry(declaredField.getType());
						RegistryObject<?> object = registry.getObject(new Id(holder.namespace(), holder.id()));
						if (object == null)
							continue;
						Object t = object.get();
						declaredField.set(null, t);
					}
				}
			}
		}
	}
}
