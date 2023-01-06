package steve6472.sge.main.networking;

/**********************
 * Created by steve6472
 * On date: 11/27/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class TestServer extends UDPServer
{
	public TestServer(int port)
	{
		super(port);
		start();
	}

	@Override
	public void clientConnectEvent(ConnectedClient client)
	{

	}

	@Override
	public void clientDisconnectEvent(ConnectedClient client)
	{

	}
}
