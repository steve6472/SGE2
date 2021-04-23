package steve6472.sge.main.node.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 3/20/2021
 * Project: GameMaker
 *
 ***********************/
public class NodeData
{
	private static final NodeData EMPTY = new NodeData();

	private final JointType[] type;
	private final String[] name;
	private final int size;

	private NodeData(JointType[] type, String[] name)
	{
		this.type = type;
		this.name = name;

		if (type.length != name.length)
			throw new IllegalStateException("Count mismatch of 'type' and 'name'");

		size = type.length;
	}

	private NodeData()
	{
		this.type = new JointType[0];
		this.name = new String[0];
		this.size = 0;
	}

	public JointType[] getTypes()
	{
		return type;
	}

	public String[] getNames()
	{
		return name;
	}

	public int getSize()
	{
		return size;
	}

	@Override
	public String toString()
	{
		return "NodeData{" + "type=" + Arrays.toString(type) + ", name=" + Arrays.toString(name) + ", size=" + size + '}';
	}

	public static NodeData empty()
	{
		return EMPTY;
	}

	public static NodeData createSingle(JointType type, String name)
	{
		return new NodeData(new JointType[] {type}, new String[] {name});
	}

	public static NodeData createFull(JointType[] types, String[] names)
	{
		return new NodeData(types, names);
	}

	public static NodeDataBuilder create(JointType type, String name)
	{
		NodeDataBuilder builder = new NodeDataBuilder();
		builder.add(type, name);
		return builder;
	}

	public static NodeDataBuilder create(JointType type)
	{
		return create(type, "");
	}

	public static NodeDataBuilder create()
	{
		return new NodeDataBuilder();
	}

	public static class NodeDataBuilder
	{
		private final List<JointType> types;
		private final List<String> names;

		private NodeDataBuilder()
		{
			types = new ArrayList<>();
			names = new ArrayList<>();
		}

		public NodeDataBuilder add(JointType type, String name)
		{
			types.add(type);
			names.add(name);
			return this;
		}

		public NodeDataBuilder add(JointType type)
		{
			return add(type, "");
		}

		public NodeData build()
		{
			return new NodeData(types.toArray(new JointType[0]), names.toArray(new String[0]));
		}
	}
}
