package steve6472.sge.main.networking;

import java.net.DatagramPacket;

/**********************
 * Created by steve6472 (Mirek Jozefek)
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
	public void clientConnectEvent(DatagramPacket packet)
	{

	}

	@Override
	public void clientDisconnectEvent(DatagramPacket packet)
	{

	}
}
