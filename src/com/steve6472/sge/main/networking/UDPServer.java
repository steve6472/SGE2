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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.steve6472.sge.main.Util;
import com.steve6472.sge.main.networking.packet.DataStream;
import com.steve6472.sge.main.networking.packet.IPacketHandler;
import com.steve6472.sge.main.networking.packet.Packet;

public abstract class UDPServer extends Thread
{
	private DatagramSocket socket;
	protected List<ConnectedClient> clients;
	protected IPacketHandler packetHandler;
	
	public UDPServer(int port)
	{
		clients = new ArrayList<ConnectedClient>();
		
		try
		{
			this.socket = new DatagramSocket(port);
			System.out.println("Started server on port " + port + " Ip: " + InetAddress.getLocalHost().getHostAddress());
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void setIPacketHandler(IPacketHandler packetHandler)
	{
		this.packetHandler = packetHandler;
	}
	
	@Override
	public synchronized void start()
	{
		super.start();
		System.out.println("Started server!");
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			byte[] data = new byte[65535];
			DatagramPacket p = new DatagramPacket(data, data.length);
			try
			{
				socket.receive(p);
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
			String msg = new String(p.getData()).trim();
			
			//Generic client connection
			if (msg.substring(0, 4).equals("0000"))
			{
				connectClient(p.getAddress(), p.getPort());
				clientConnectEvent(p);
				
			} else if (msg.substring(0, 4).equals("0001"))
			{
				disconnectClientByPort(p.getPort());
				clientDisconnectEvent(p);
				
			} else
			{
//				System.out.println("Server " + Util.getFormatedTime() + "> " + msg.substring(0, 4));
				
				recievePacket(p.getData(), p);
			}
		}
	}
	
	public abstract void clientConnectEvent(DatagramPacket packet);
	
	public abstract void clientDisconnectEvent(DatagramPacket packet);
	
	public void connectClient(InetAddress ip, int port)
	{
		System.out.println(ip.getHostAddress() + ":" + port + " Connected");
		clients.add(new ConnectedClient(ip, port));
	}
	
	public static byte[] setPacket(String id, Packet<? extends IPacketHandler> packet)
	{
		DataStream stream = new DataStream();
		packet.output(stream);
		if (stream.isEmpty())
			stream = null;
		String serialized = Util.toString(stream);
		byte[] compressed = Util.compress(serialized);
		byte[] combined = Util.combineArrays(id.getBytes(), compressed);
		return combined;
	}
	
	public final void sendPacket(Packet<? extends IPacketHandler> packet)
	{
		String id = Packet.getPacketIdHex(Packet.getPacketId(packet));
		sendDataToAllClients(setPacket(id, packet));
	}
	
	public final void sendPacketWithException(Packet<? extends IPacketHandler> packet, DatagramPacket p)
	{
		String id = Packet.getPacketIdHex(Packet.getPacketId(packet));
		sendDataToAllClientsWithException(setPacket(id, packet), p.getAddress(), p.getPort());
	}
	
	public final void sendPacket(Packet<? extends IPacketHandler> packet, InetAddress ip, int port)
	{
		String id = Packet.getPacketIdHex(Packet.getPacketId(packet));
		sendData(setPacket(id, packet), ip, port);
	}
	
	public final void sendPacket(Packet<? extends IPacketHandler> packet, DatagramPacket p)
	{
		String id = Packet.getPacketIdHex(Packet.getPacketId(packet));
		sendData(setPacket(id, packet), p.getAddress(), p.getPort());
	}
	
	public final void recievePacket(byte[] data, DatagramPacket p)
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
			return;
		
		//Write data back to packet
		packet.input(stream);
		
		//Handle packet
		handlePacket(packet, id, p);
	}
	
//	public abstract void handlePacket(Packet packet, int packetId, DataStream packetData);
	
//	public abstract void handlePacket(Packet<?> packet, int packetId, DatagramPacket sender);
	
	@SuppressWarnings("unchecked")
	public void handlePacket(Packet<? extends IPacketHandler> packet, int packetId, DatagramPacket sender)
	{
		packet.setSender(sender);
		((Packet<IPacketHandler>)packet).handlePacket(packetHandler);
	}
	
	public final void disconnectClientByIndex(int index)
	{
		clients.remove(index);
	}
	
	public final void disconnectClientByPort(int port)
	{
		int index = 0;
		for (ConnectedClient cc : clients)
		{
			if (cc.getPort() == port)
				break;
			index++;
		}
		clients.remove(index);
		System.out.println(port + " has disconnected");
	}

	protected final void sendData(byte[] data, InetAddress ipAddress, int port)
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		
		try
		{
			socket.send(packet);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	protected final void sendDataToAllClientsWithException(byte[] data, InetAddress ip, int port)
	{
		for (ConnectedClient cc : clients)
		{
			if (!(cc.getPort() == port && cc.getIp().equals(ip)))
			{
				sendData(data, cc.getIp(), cc.getPort());
			}
		}
	}
	
	protected final void sendDataToAllClients(byte[] data)
	{
		for (ConnectedClient cc : clients)
		{
			sendData(data, cc.getIp(), cc.getPort());
		}
	}
}
