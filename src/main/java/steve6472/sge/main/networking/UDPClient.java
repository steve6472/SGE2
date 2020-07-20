/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 10. 2. 2018
* Project: SCR2
*
***********************/

package steve6472.sge.main.networking;

import steve6472.sge.main.Util;
import steve6472.sge.main.networking.packet.DataStream;
import steve6472.sge.main.networking.packet.IPacketHandler;
import steve6472.sge.main.networking.packet.Packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public abstract class UDPClient extends Thread
{
	InetAddress ip;
	int port;
	DatagramSocket socket;
	
	private DatagramPacket sendingPacket;
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
		sendData("connect".getBytes());
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
			
			byte[] recieved = p.getData();
			
//			String msg = new String(recieved).trim();
			
//			System.out.println("Client " + Util.getFormatedTime() + "> " + msg.substring(0, 4));
			
			recievePacket(recieved, p);
		}
	}
	
	private void recievePacket(byte[] data, DatagramPacket p)
	{
		String recievedId = new String(data).substring(0, 4);
		byte[] recievedArray = Arrays.copyOfRange(data, 4, data.length);
		String decompressed = Util.decompress(recievedArray);
		Object deserialized = Util.fromString(decompressed);
		DataStream stream = (DataStream) deserialized;
		
		//get Id from message
		int id = Util.getIntFromHex(recievedId);
		
		//Get packet by ID
		Packet<? extends IPacketHandler> packet = Packet.getPacket(id);
		
		//Skip invalid packet
		if (packet == null)
		{
			System.err.println("Skipping invalid packet with id " + id);
			return;
		}
		
		//Write data back to packet
		packet.input(stream);
		
		handlePacket(packet, id, p);
		
	}
	
//	public abstract void handlePacket(Packet<?> packet, int packetId, DatagramPacket p);
	
	@SuppressWarnings("unchecked")
	public void handlePacket(Packet<?> packet, int packetId, DatagramPacket p)
	{
		((Packet<IPacketHandler>)packet).handlePacket(packetHandler);
	}
	
	public void sendPacket(Packet<?> packet)
	{
		String id = Packet.getPacketIdHex(Packet.getPacketId(packet));
		sendData(UDPServer.setPacket(id, packet));
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
