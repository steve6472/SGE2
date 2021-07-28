package steve6472.sge.main.game.registry;

import steve6472.sge.main.events.RegisterEvent;
import steve6472.sge.main.game.Id;

import java.util.HashMap;
import java.util.function.Supplier;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/17/2021
 * Project: VoxWorld
 *
 ***********************/
public final class Register<T extends ID>
{
	private final HashMap<Id, RegistryObject<T>> objects = new HashMap<>();
	private final Registry<T> registry;
	private final String namespace;

	private Register(Registry<T> registry, String namespace)
	{
		this.registry = registry;
		this.namespace = namespace;
	}

	public static <T extends ID> Register<T> create(Registry<T> registry, String namespace)
	{
		return new Register<>(registry, namespace);
	}

	void create()
	{
		objects.forEach((i, o) ->
		{
			if (Registry.getEventHandler() != null)
			{
				RegisterEvent.Object<?> e = new RegisterEvent.Object(this, o);
				Registry.getEventHandler().runEvent(e);
				if (!e.isCancelled())
					o.create();
			} else
			{
				o.create();
			}
		});
	}

	public RegistryObject<T> register(String id, Supplier<T> constructor)
	{
		RegistryObject<T> object = new RegistryObject<>(constructor);
		Id id1 = new Id(namespace, id);
		object.setId(id1);
		objects.put(id1, object);
		return object;
	}

	public boolean unregister(Id id)
	{
		return registry.unregister(id);
	}

	public String getNamespace()
	{
		return namespace;
	}

	public Registry<T> registry()
	{
		return registry;
	}

	public HashMap<Id, RegistryObject<T>> getObjects()
	{
		return objects;
	}

	@Override
	public String toString()
	{
		return "Register[" + "registry=" + registry + ", " + "namespace=" + namespace + ']';
	}

}
