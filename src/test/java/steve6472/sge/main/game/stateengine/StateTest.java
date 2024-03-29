package steve6472.sge.main.game.stateengine;

import org.junit.jupiter.api.*;

/**********************
 * Created by steve6472
 * On date: 9/22/2021
 * Project: StevesGameEngine
 *
 ***********************/
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StateTest
{
	static TestStateObject obj;
	static State defaultState;

	@BeforeAll
	public static void setUp()
	{
		obj = new TestStateObject();
		defaultState = obj.getDefaultState();
	}

	@Test
	@Order(1)
	@DisplayName("Get (value from property)")
	void testGet()
	{
		Assertions.assertInstanceOf(Integer.class, defaultState.get(TestStateObject.FACING));
		Assertions.assertInstanceOf(Boolean.class, defaultState.get(TestStateObject.LIT));
	}

	@Test
	@Order(2)
	@DisplayName("Get Object")
	void testGetObject()
	{
		Assertions.assertEquals(obj, defaultState.getObject());
	}

	@Test
	@Order(3)
	@DisplayName("Default State")
	void testDefaultState()
	{
		Assertions.assertEquals(0, defaultState.get(TestStateObject.FACING));
		Assertions.assertEquals(false, defaultState.get(TestStateObject.LIT));
	}

	@Test
	@Order(4)
	@DisplayName("Facing")
	void testFacing()
	{
		defaultState = defaultState.with(TestStateObject.FACING, 2);
		Assertions.assertEquals(2, defaultState.get(TestStateObject.FACING));
		Assertions.assertEquals(false, defaultState.get(TestStateObject.LIT));
	}

	@Test
	@Order(5)
	@DisplayName("Lit")
	void testLit()
	{
		defaultState = defaultState.with(TestStateObject.LIT, true);
		Assertions.assertEquals(2, defaultState.get(TestStateObject.FACING));
		Assertions.assertEquals(true, defaultState.get(TestStateObject.LIT));
	}

	@Test
	@Order(6)
	@DisplayName("No property")
	void testNoProperty()
	{
		Assertions.assertThrows(IllegalStateException.class, () -> defaultState.with(TestStateObject.UNUSED_BOOL, false), "Property '" + TestStateObject.UNUSED_BOOL.getName() + "' does not exist!");
	}

	@Test
	@Order(7)
	@DisplayName("No value")
	void testNoValue()
	{
		Assertions.assertThrows(IllegalStateException.class, () -> defaultState.with(TestStateObject.FACING, 7), "Could not find desired value '" + 7 + "' for property '" + TestStateObject.FACING.getName() + "'");
	}
}