package steve6472.sge.main.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

/**********************
 * Created by steve6472
 * On date: 10. 2. 2018
 * Project: SCR2
 *
 ***********************/
public abstract class UDPServer extends Thread
{
	private DatagramSocket socket;
	protected Set<ConnectedClient> clients;
	protected IPacketHandler packetHandler;
	private int maxPacketSize = 508;

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

	protected void setMaxPacketSize(int maxPacketSize)
	{
		this.maxPacketSize = maxPacketSize;
	}

	public int getMaxPacketSize()
	{
		return maxPacketSize;
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
			byte[] data = new byte[maxPacketSize];
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
				ConnectedClient connectedClient = connectClient(p.getAddress(), p.getPort());
				clientConnectEvent(connectedClient);
			}
			else if (Packets.getPacketId(packet) == DisconnectPacket.ID)
			{
				ConnectedClient connectedClient = disconnectClientByPort(p.getPort());
				clientDisconnectEvent(connectedClient);
			}
		}
	}

	public abstract void clientConnectEvent(ConnectedClient client);
	
	public abstract void clientDisconnectEvent(ConnectedClient client);
	
	public ConnectedClient connectClient(InetAddress ip, int port)
	{
//		System.out.println(ip.getHostAddress() + ":" + port + " Connected");
		ConnectedClient e = new ConnectedClient(ip, port);
		clients.add(e);
		return e;
	}
	
	public void sendPacket(Packet<? extends IPacketHandler> packet)
	{
		sendDataToAllClients(Packets.packetToByteArray(packet));
	}

	@SuppressWarnings("unchecked")
	public void handlePacket(Packet<? extends IPacketHandler> packet, DatagramPacket sender)
	{
		packet.setSender(sender);
		((Packet<IPacketHandler>)packet).handlePacket(packetHandler);
	}

	public ConnectedClient disconnectClientByPort(int port)
	{
		ConnectedClient[] dcdClients = new ConnectedClient[1];
		clients.removeIf(c ->
		{
			if (c.getPort() == port)
			{
				dcdClients[0] = c;
				return true;
			}
			return false;
		});
		System.out.println(port + " has disconnected");
		return dcdClients[0];
	}

	protected void sendData(byte[] data, InetAddress ipAddress, int port)
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

	protected void sendDataToAllClients(byte[] data)
	{
		for (ConnectedClient cc : clients)
		{
			sendData(data, cc.getIp(), cc.getPort());
		}
	}

	public final int getClientCount()
	{
		return clients.size();
	}
}
