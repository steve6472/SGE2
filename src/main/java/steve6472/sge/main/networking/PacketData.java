package steve6472.sge.main.networking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class PacketData
{
	ByteBuf buffer;

	public PacketData()
	{
		buffer = Unpooled.buffer();
	}

	public PacketData(byte[] bytes)
	{
		buffer = Unpooled.copiedBuffer(bytes);
	}

	public byte[] toByteArray()
	{
		byte[] bytes = new byte[buffer.capacity()];
		buffer.getBytes(0, bytes);
		return bytes;
	}
	
	public void writePacketId(Packet<?> packet)
	{
		writeInt(Packets.getPacketId(packet));
	}
	
	public int readPacketId()
	{
		return readInt();
	}

	public void writeString(String s)
	{
		buffer.writeInt(s.length());
		buffer.writeBytes(s.getBytes());
	}

	public void writeByte(byte value)
	{
		buffer.writeByte(value);
	}

	public void writeShort(short value)
	{
		buffer.writeShort(value);
	}

	public void writeInt(int value)
	{
		buffer.writeInt(value);
	}

	public void writeBoolean(boolean value)
	{
		buffer.writeBoolean(value);
	}

	public void writeFloat(float value)
	{
		buffer.writeFloat(value);
	}

	public void writeDouble(double value)
	{
		buffer.writeDouble(value);
	}

	public String readString()
	{
		int len = buffer.readInt();
		byte[] bytes = new byte[len];
		buffer.readBytes(bytes);
		return new String(bytes);
	}

	public byte readByte()
	{
		return buffer.readByte();
	}

	public short readShort()
	{
		return buffer.readShort();
	}

	public int readInt()
	{
		return buffer.readInt();
	}

	public boolean readBoolean()
	{
		return buffer.readBoolean();
	}

	public float readFloat()
	{
		return buffer.readFloat();
	}

	public double readDouble()
	{
		return buffer.readDouble();
	}
}
