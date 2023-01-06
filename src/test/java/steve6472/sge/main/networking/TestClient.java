package steve6472.sge.main.networking;

/**********************
 * Created by steve6472
 * On date: 11/27/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class TestClient extends UDPClient
{
	/**
	 * @param ip   Client is gonna connect to this IP
	 * @param port Client is gonna connect to this PORT
	 */
	public TestClient(String ip, int port)
	{
		super(ip, port);
		start();
	}
}
