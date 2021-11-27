package steve6472.sge.main.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 10. 2. 2018
 * Project: SCR2
 *
 ***********************/
public abstract class UDPServer extends Thread
{
	private DatagramSocket socket;
	protected Set<ConnectedClient> clients;
	protected IPacketHandler packetHandler;
	
	public UDPServer(int port)
	{
		clients = new HashSet<>();
		
		try
		{
			if (!isPortAviable(port))
			{
				System.err.println(port + " is not aviable!");
				System.exit(2);
			}
			this.socket = new DatagramSocket(port);
//			System.out.println("Started server on port " + port + " Ip: " + InetAddress.getLocalHost().getHostAddress());
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static boolean isPortAviable(int port)
	{
		DatagramSocket s;
		try
		{
			s = new DatagramSocket(port);
		} catch (SocketException e)
		{
			return false;
		}
		
		s.close();
		return true;
	}
	
	public void setIPacketHandler(IPacketHandler packetHandler)
	{
		this.packetHandler = packetHandler;
	}
	
	@Override
	public synchronized void start()
	{
		super.start();
//		System.out.println("Started server!");
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

			Packet<?> packet = Packets.recievePacket(p.getData(), p, this::handlePacket);

			if (Packets.getPacketId(packet) == ConnectPacket.ID)
			{
				connectClient(p.getAddress(), p.getPort());
				clientConnectEvent(p);
			}
			else if (Packets.getPacketId(packet) == DisconnectPacket.ID)
			{
				disconnectClientByPort(p.getPort());
				clientDisconnectEvent(p);
			}
		}
	}

	public abstract void clientConnectEvent(DatagramPacket packet);
	
	public abstract void clientDisconnectEvent(DatagramPacket packet);
	
	public void connectClient(InetAddress ip, int port)
	{
//		System.out.println(ip.getHostAddress() + ":" + port + " Connected");
		clients.add(new ConnectedClient(ip, port));
	}
	
	public final void sendPacket(Packet<? extends IPacketHandler> packet)
	{
		sendDataToAllClients(Packets.packetToByteArray(packet));
	}

	/*
	public final void sendPacketWithException(Packet<? extends IPacketHandler> packet, DatagramPacket p)
	{
		String id = Packet.getPacketIdHex(Packet.getPacketId(packet));
		sendDataToAllClientsWithException(packetToByteArray(id, packet), p.getAddress(), p.getPort());
	}
	
	public final void sendPacket(Packet<? extends IPacketHandler> packet, InetAddress ip, int port)
	{
		String id = Packet.getPacketIdHex(Packet.getPacketId(packet));
		sendData(packetToByteArray(id, packet), ip, port);
	}
	
	public final void sendPacket(Packet<? extends IPacketHandler> packet, DatagramPacket p)
	{
		String id = Packet.getPacketIdHex(Packet.getPacketId(packet));
		sendData(packetToByteArray(id, packet), p.getAddress(), p.getPort());
	}*/
	
//	public abstract void handlePacket(Packet packet, int packetId, DataStream packetData);
	
//	public abstract void handlePacket(Packet<?> packet, int packetId, DatagramPacket sender);
	
	@SuppressWarnings("unchecked")
	public void handlePacket(Packet<? extends IPacketHandler> packet, DatagramPacket sender)
	{
		packet.setSender(sender);
		((Packet<IPacketHandler>)packet).handlePacket(packetHandler);
	}

	public final void disconnectClientByPort(int port)
	{
		clients.removeIf(c -> c.getPort() == port);
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

	protected final void sendDataToAllClients(byte[] data)
	{
		for (ConnectedClient cc : clients)
		{
			sendData(data, cc.getIp(), cc.getPort());
		}
	}
	/*
	protected final void sendDataToAllClientsWithException(byte[] data, InetAddress ip, int port)
	{
		for (ConnectedClient cc : clients)
		{
			if (!(cc.getPort() == port && cc.getIp().equals(ip)))
			{
				sendData(data, cc.getIp(), cc.getPort());
			}
		}
	}*/

	public final int getClientCount()
	{
		return clients.size();
	}
}
