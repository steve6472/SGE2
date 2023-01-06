package steve6472.sge.main.networking;

import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**********************
 * Created by steve6472
 * On date: 11/27/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Packets
{
	private static final Map<Integer, Supplier<Packet<? extends IPacketHandler>>> idPacketMap;
	private static final Map<Class<? extends Packet<? extends IPacketHandler>>, Integer> packetIdMap;

	static
	{
		idPacketMap = new HashMap<>();
		packetIdMap = new HashMap<>();

		registerPacket(ConnectPacket::new);
		registerPacket(DisconnectPacket::new);
	}

	public static int getPacketId(Packet<? extends IPacketHandler> packet)
	{
		Integer integer = packetIdMap.get(packet.getClass());
		if (integer == null)
			throw new IllegalArgumentException("Packet '" + packet.getClass() + "' is not registrated!");
		return integer;
	}

	public static void registerPacket(Supplier<Packet<? extends IPacketHandler>> packet)
	{
		Class<? extends Packet<? extends IPacketHandler>> clazz = (Class<? extends Packet<? extends IPacketHandler>>) packet.get().getClass();

		int id = idPacketMap.size();

		if (idPacketMap.containsKey(id))
		{
			throw new IllegalArgumentException("Duplicate packet id:" + id);
		}
		if (packetIdMap.containsKey(clazz))
		{
			throw new IllegalArgumentException("Duplicate packet class:" + clazz);
		}
		idPacketMap.put(id, packet);
		packetIdMap.put(clazz, id);
	}

	public static Packet<? extends IPacketHandler> getPacket(int id)
	{
		Supplier<Packet<? extends IPacketHandler>> packetSupplier = idPacketMap.get(id);
		if (packetSupplier == null)
			return null;
		return packetSupplier.get();
	}

	public static byte[] packetToByteArray(Packet<? extends IPacketHandler> packet)
	{
		PacketData packetData = new PacketData();
		packetData.writePacketId(packet);
		packet.output(packetData);
		return packetData.toByteArray();
	}

	public static Packet<?> recievePacket(byte[] data, DatagramPacket p, BiConsumer<Packet<?>, DatagramPacket> c)
	{
		PacketData packetData = new PacketData(data);
		int packetId = packetData.readPacketId();
		Packet<?> packet = Packets.getPacket(packetId);

		if (packet == null)
			throw new IllegalArgumentException("Packet with id '" + packetId + "' not found!");

		packet.input(packetData);

		c.accept(packet, p);
		//Handle packet
//		handlePacket(packet, p);

		return packet;
	}
}
