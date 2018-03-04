/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 10. 2. 2018
* Project: SCR2
*
***********************/

package com.steve6472.sge.main.networking.packet;

import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;

public abstract class Packet<T extends IPacketHandler>
{
	static Map<Integer, Class<? extends Packet<? extends IPacketHandler>>> packets0;
	static Map<Class<? extends Packet<? extends IPacketHandler>>, Integer> packets1;
	
	private DatagramPacket sender;
	
	static
	{
		packets0 = new HashMap<Integer, Class<? extends Packet<? extends IPacketHandler>>>();
		packets1 = new HashMap<Class<? extends Packet<? extends IPacketHandler>>, Integer>();

		addPacket(0, ConnectPacket.class);
		addPacket(1, DisconnectPacket.class);
	}
	
	public Packet()
	{
	}
	
	public static int getPacketId(Packet<? extends IPacketHandler> packet)
	{
		return packets1.get(packet.getClass());
	}

	public static void addPacket(int id, Class<? extends Packet<? extends IPacketHandler>> clazz)
	{
		if (packets0.containsKey(id))
		{
			throw new IllegalArgumentException("Duplicate packet id:" + id);
		}
		if (packets1.containsKey(clazz))
		{
			throw new IllegalArgumentException("Duplicate packet class:" + clazz);
		}
		packets0.put(id, clazz);
		packets1.put(clazz, id);
	}

	public static Packet<? extends IPacketHandler> getPacket(int id)
	{
		try
		{
			Class<? extends Packet<? extends IPacketHandler>> clazz = packets0.get(id);
			if (clazz == null)
				return null;
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
			System.out.println("Skipping packet with id " + id);
			return null;
		}
	}
	
	public static String getPacketIdHex(int id)
	{
		String hexId = Integer.toHexString(id);
		
		//Hex id must have 4 characters!
		if (hexId.length() == 1)
			hexId = "000" + hexId;
		else if (hexId.length() == 2)
			hexId = "00" + hexId;
		else if (hexId.length() == 3)
			hexId = "0" + hexId;
		
		return hexId;
	}
	
	public void setSender(DatagramPacket sender)
	{
		this.sender = sender;
	}
	
	public final DatagramPacket getSender()
	{
		return sender;
	}
	
	public abstract void output(DataStream output);
	
	public abstract void input(DataStream input);
	
	public abstract void handlePacket(T handler);
}
