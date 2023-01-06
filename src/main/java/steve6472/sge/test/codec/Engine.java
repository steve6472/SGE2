package steve6472.sge.test.codec;

/**********************
 * Created by steve6472
 * On date: 4/20/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Engine
{
	@IntCodec(id = "horse_power")
	int horsePower;

	@IntCodec(id = "speed")
	int speed;

	public Engine()
	{

	}

	@Override
	public String toString()
	{
		return "Engine{" + "horsePower=" + horsePower + ", speed=" + speed + '}';
	}
}
