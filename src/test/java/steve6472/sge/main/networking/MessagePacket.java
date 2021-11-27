package steve6472.sge.main.networking;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class MessagePacket extends Packet<PHandler>
{
	String message;
	boolean fromServer;

	public MessagePacket()
	{

	}

	public MessagePacket(String message, boolean fromServer)
	{
		this.message = message;
		this.fromServer = fromServer;
	}

	@Override
	public void output(PacketData output)
	{
		output.writeBoolean(fromServer);
		output.writeString(message);
	}

	@Override
	public void input(PacketData input)
	{
		fromServer = input.readBoolean();
		message = input.readString();
	}

	@Override
	public void handlePacket(PHandler handler)
	{
		if (fromServer)
			handler.handleFromServer(message);
		else
			handler.handleFromClient(message);
	}
}
