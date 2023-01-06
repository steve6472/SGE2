package steve6472.sge.main.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**********************
 * Created by steve6472
 * On date: 10. 2. 2018
 * Project: SCR2
 *
 ***********************/
public abstract class UDPClient extends Thread
{
	InetAddress ip;
	int port;
	DatagramSocket socket;
	
	private final DatagramPacket sendingPacket;
	protected IPacketHandler packetHandler;
	
	/**
	 * 
	 * @param ip Client is gonna connect to this IP
	 * @param port Client is gonna connect to this PORT
	 */
	public UDPClient(String ip, int port)
	{
		this.port = port;
		
		try
		{
			this.socket = new DatagramSocket();
			this.ip = InetAddress.getByName(ip);
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		sendingPacket = new DatagramPacket(new byte[0], 0, this.ip, port);
	}
	
	public void setIPacketHandler(IPacketHandler packetHandler)
	{
		this.packetHandler = packetHandler;
	}

	public void connect()
	{
		sendPacket(new ConnectPacket());
	}

	public void disconnect()
	{
		sendPacket(new DisconnectPacket());
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			byte[] data;
			data = new byte[65535];
			DatagramPacket p = new DatagramPacket(data, data.length);
			try
			{
				socket.receive(p);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
			Packets.recievePacket(p.getData(), p, this::handlePacket);
		}
	}

	public final void sendPacket(Packet<? extends IPacketHandler> packet)
	{
		sendData(Packets.packetToByteArray(packet));
	}

	@SuppressWarnings("unchecked")
	public void handlePacket(Packet<?> packet, DatagramPacket p)
	{
		((Packet<IPacketHandler>)packet).handlePacket(packetHandler);
	}
	
	protected void sendData(byte[] data)
	{
		sendingPacket.setData(data);
		
		try
		{
			socket.send(sendingPacket);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
