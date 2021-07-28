package steve6472.sge.test.registry;

import steve6472.sge.main.events.Event;
import steve6472.sge.main.events.EventHandler;
import steve6472.sge.main.events.RegisterEvent;
import steve6472.sge.main.game.Id;
import steve6472.sge.main.game.registry.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/21/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class RegistryTest
{
	public static final Registry<Test> TEST_REGISTRY = Registry.createRegistry(Test.class);

	public static final Register<Test> TEST_REGISTER = Register.create(TEST_REGISTRY, "test");
	public static final Register<Test> TEST_REGISTER_ = Register.create(TEST_REGISTRY, "test_");

	public static final RegistryObject<Test> HANGER = TEST_REGISTER_.register("hanger", () -> new Test("hanger"));
	public static final RegistryObject<Test> BLANKET = TEST_REGISTER_.register("blanket", () -> new Test("blanket"));
	public static final RegistryObject<Test> BOTTLE = TEST_REGISTER.register("bottle", () -> new Test("bottle"));
	public static final RegistryObject<Test> WALLET = TEST_REGISTER.register("wallet", () -> new Test("wallet"));

	@ObjectHolder(namespace = "test", id = "wallet")
	public static Test wallet = null;

	private final EventHandler eventHandler;

	public RegistryTest() throws IllegalAccessException, NoSuchMethodException
	{
		eventHandler = new EventHandler();
		Registry.setEventHandler(eventHandler);
		eventHandler.register(this);

//		TEST_REGISTRY.addRegister(TEST_REGISTER);
		TEST_REGISTRY.addRegister(TEST_REGISTER_);

		TEST_REGISTRY.create();

		ObjectHolders holders = new ObjectHolders();

		holders.add(RegistryTest.class);
		holders.load();

		System.out.println("Wallet registry object: " + WALLET.get());
		System.out.println("Wallet injected object: " + wallet);

		TEST_REGISTER.getObjects().forEach((i, o) -> {
			System.out.println(i + " " + o);
		});

		TEST_REGISTER_.getObjects().forEach((i, o) -> {
			System.out.println(i + " " + o);
		});
	}

	@Event
	public void regEvent(RegisterEvent<Test> e)
	{
		System.out.println(e.getRegistryClass());
		System.out.println(e.getNamespace());
	}

	@Event
	public void regEvent(RegisterEvent.Object<Test> e)
	{
		System.out.println("object: " + e.getId());
		if (e.getId().equals("blanket"))
		{
			e.setCancelled(true);
		}
	}

	static class Test implements ID
	{
		private Id id;
		private final long creationTime;
		private final String name;

		public Test(String name)
		{
			this.name = name;
			this.creationTime = System.currentTimeMillis();
		}

		@Override
		public void setId(Id id)
		{
			this.id = id;
		}

		@Override
		public Id getId()
		{
			return id;
		}

		@Override
		public String toString()
		{
			return "Test{" + "id=" + id + ", creationTime=" + creationTime + ", name='" + name + '\'' + '}';
		}
	}

	public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException
	{
		new RegistryTest();
	}
}
