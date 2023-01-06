package steve6472.sge.main.networking;

import org.junit.jupiter.api.Assertions;

/**********************
 * Created by steve6472
 * On date: 11/27/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class PHandler implements IPacketHandler
{
	public void handleFromServer(String message)
	{
		System.out.println(message);
		if (!message.equals(TestNet.SERVER_MESSAGE))
			Assertions.fail();
	}

	public void handleFromClient(String message)
	{
		System.out.println(message);
		if (!message.equals(TestNet.CLIENT_MESSAGE))
			Assertions.fail();
	}
}
