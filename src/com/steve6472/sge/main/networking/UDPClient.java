/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 10. 2. 2018
* Project: SCR2
*
***********************/

package com.steve6472.sge.main.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.steve6472.sge.main.Util;
import com.steve6472.sge.main.networking.packet.DataStream;
import com.steve6472.sge.main.networking.packet.IPacketHandler;
import com.steve6472.sge.main.networking.packet.Packet;

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
			byte[] data = null;
			data = new byte[65535];
			DatagramPacket p = new DatagramPacket(data, data.length);
			try
			{
				socket.receive(p);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
			String msg = new String(p.getData()).trim();
			
//			System.out.println("Client > " + msg);
			
			recievePacket(msg, p);
		}
	}
	
	public void recievePacket(String msg, DatagramPacket p)
	{
		String hexId = msg.substring(0, 4);
		String dataString = msg.substring(4);
		DataStream data = (DataStream) Util.fromString(dataString);
		int id = Util.getIntFromHex(hexId);
		Packet<?> packet = Packet.getPacket(id);
		if (packet == null)
			return;
		packet.input(data);
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
		DataStream stream = new DataStream();
		packet.output(stream);
		String data = Util.toString(stream);
		String id = Packet.getPacketIdHex(Packet.getPacketId(packet));
		sendData((id + data).getBytes());
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
