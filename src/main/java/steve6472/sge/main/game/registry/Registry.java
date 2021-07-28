package steve6472.sge.main.game.registry;

import steve6472.sge.main.events.EventHandler;
import steve6472.sge.main.events.RegisterEvent;
import steve6472.sge.main.game.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/17/2021
 * Project: VoxWorld
 *
 ***********************/
public class Registry<T extends ID>
{
	private static final HashMap<Class<? extends ID>, Registry<? extends ID>> REGISTRIES = new HashMap<>();

	private final List<Register<? extends ID>> registers = new ArrayList<>();

	private static EventHandler EVENT_HANDLER;

	private final HashMap<Id, RegistryObject<T>> objects = new HashMap<>();
	private final Class<T> clazz;

	private Registry(Class<T> clazz)
	{
		this.clazz = clazz;
	}

	public static void setEventHandler(EventHandler eventHandler)
	{
		EVENT_HANDLER = eventHandler;
	}

	static EventHandler getEventHandler()
	{
		return EVENT_HANDLER;
	}

	public void addRegister(Register<?> register)
	{
		registers.add(register);
	}

	public static <T extends ID> Registry<T> createRegistry(Class<T> clazz)
	{
		Registry<T> registry = new Registry<>(clazz);
		REGISTRIES.put(clazz, registry);
		return registry;
	}

	public void register(Id id, RegistryObject<T> object)
	{
		if (objects.containsKey(id))
			throw new IllegalArgumentException("Duplicate id:" + id);

		objects.put(id, object);
	}

	public void register(Id id, T object)
	{
		if (objects.containsKey(id))
			throw new IllegalArgumentException("Duplicate id:" + id);

		objects.put(id, new RegistryObject<>(() -> object));
	}

	public boolean unregister(Id id)
	{
		return objects.remove(id) != null;
	}

	public void create()
	{
		objects.clear();
		registers.forEach(r -> {
			if (EVENT_HANDLER != null)
			{
				RegisterEvent<?> e = new RegisterEvent<>(r);
				EVENT_HANDLER.runEvent(e);
				if (!e.isCancelled())
					r.create();
			} else
			{
				r.create();
			}

			r.getObjects().forEach((i, o) ->
			{
				if (o.get() != null)
				{
					objects.put(i, (RegistryObject<T>) o);
				}
			});
		});
	}

	public void clear()
	{
		objects.clear();
	}

	public RegistryObject<T> getObject(Id id)
	{
		return objects.get(id);
	}

	public HashMap<Id, RegistryObject<T>> getObjects()
	{
		return objects;
	}

	public Class<T> getClazz()
	{
		return clazz;
	}

	public static <T extends ID> Registry<T> getRegistry_(Class<T> clazz)
	{
		return (Registry<T>) REGISTRIES.get(clazz);
	}

	public static Registry<?> getRegistry(Class<?> clazz)
	{
		return REGISTRIES.get(clazz);
	}
}
