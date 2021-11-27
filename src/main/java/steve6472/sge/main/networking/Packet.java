package steve6472.sge.main.networking;

import java.net.DatagramPacket;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: StevesGameEngine
 *
 ***********************/
public abstract class Packet<T extends IPacketHandler>
{
	private DatagramPacket sender;

	public Packet()
	{
	}

	public void setSender(DatagramPacket sender)
	{
		this.sender = sender;
	}

	public final DatagramPacket getSender()
	{
		return sender;
	}

	public abstract void output(PacketData output);

	public abstract void input(PacketData input);

	public abstract void handlePacket(T handler);
}
