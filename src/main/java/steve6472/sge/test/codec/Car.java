package steve6472.sge.test.codec;

/**********************
 * Created by steve6472
 * On date: 4/20/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Car
{
	@IntCodec(id = "age")
	int age;

	@IntCodec(id = "wheels", defVal = 4)
	int wheels;

	@ObjectCodec(id = "engine")
	Engine engine;

	public Car()
	{

	}

	@Override
	public String toString()
	{
		return "Car{" + "age=" + age + ", wheels=" + wheels + ", engine=" + engine + '}';
	}
}
