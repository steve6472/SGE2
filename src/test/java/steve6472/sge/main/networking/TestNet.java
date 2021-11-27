package steve6472.sge.main.networking;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 9/22/2021
 * Project: StevesGameEngine
 *
 ***********************/
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestNet
{
	public static final String CLIENT_MESSAGE = "Hello From Client";
	public static final String SERVER_MESSAGE = "Hello From Server";

	static TestServer server;
	static TestClient client;
	static PHandler handler;

	@BeforeAll
	public static void setUp()
	{
		Packets.registerPacket(MessagePacket::new);

		handler = new PHandler();
		server = new TestServer(36521);
		client = new TestClient("localhost", 36521);
		client.connect();

		server.setIPacketHandler(handler);
		client.setIPacketHandler(handler);
	}

	@Test
	void testPacketFromServer()
	{
		server.sendPacket(new MessagePacket(SERVER_MESSAGE, true));
	}

	@Test
	void testPacketFromClient()
	{
		client.sendPacket(new MessagePacket(CLIENT_MESSAGE, false));
	}
}