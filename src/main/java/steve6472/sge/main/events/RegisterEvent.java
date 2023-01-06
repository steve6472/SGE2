package steve6472.sge.main.events;

import steve6472.sge.main.game.registry.ID;
import steve6472.sge.main.game.registry.Register;
import steve6472.sge.main.game.registry.RegistryObject;

/**********************
 * Created by steve6472
 * On date: 7/21/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class RegisterEvent<T extends ID> extends AbstractEvent implements Cancellable
{
	private final Register<T> register;
	private boolean cancelled = false;

	private RegisterEvent(Register<T> registry)
	{
		this.register = registry;
	}

	public Register<T> getRegister()
	{
		return register;
	}

	public Class<T> getRegistryClass()
	{
		return register.registry().getClazz();
	}

	public String getNamespace()
	{
		return register.getNamespace();
	}

	@Override
	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}

	public static class Pre<T extends ID> extends RegisterEvent<T>
	{
		public Pre(Register<T> registry)
		{
			super(registry);
		}
	}

	public static class Post<T extends ID> extends RegisterEvent<T>
	{
		public Post(Register<T> registry)
		{
			super(registry);
		}

		@Override
		public void setCancelled(boolean cancelled)
		{
			throw new IllegalStateException("Post events can not be cancelled!");
		}
	}


	public static class Object<T extends ID> extends RegisterEvent<T>
	{
		private final RegistryObject<T> object;

		private Object(Register<T> registry, RegistryObject<T> object)
		{
			super(registry);
			this.object = object;
		}

		public String getId()
		{
			return object.getId().id();
		}

		public RegistryObject<T> getObject()
		{
			return object;
		}

		public static class Pre<T extends ID> extends Object<T>
		{
			public Pre(Register<T> registry, RegistryObject<T> object)
			{
				super(registry, object);
			}
		}

		public static class Post<T extends ID> extends Object<T>
		{
			public Post(Register<T> registry, RegistryObject<T> object)
			{
				super(registry, object);
			}

			@Override
			public void setCancelled(boolean cancelled)
			{
				throw new IllegalStateException("Post events can not be cancelled!");
			}
		}
	}
}
