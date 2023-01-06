package steve6472.sge.main.events;

import steve6472.sge.main.node.core.AbstractNode;
import steve6472.sge.main.node.core.NodeEntry;

import java.util.LinkedHashMap;
import java.util.function.Supplier;

/**********************
 * Created by steve6472
 * On date: 4/20/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class RegisterNodesEvent extends AbstractEvent
{
	private final LinkedHashMap<String, NodeEntry> nodes;

	public RegisterNodesEvent(LinkedHashMap<String, NodeEntry> nodes)
	{
		this.nodes = nodes;
	}

	public NodeEntry registerNode(String id, String name, Supplier<AbstractNode> constructor)
	{
		if (nodes.get(id) != null)
			throw new IllegalArgumentException("Duplicate node id:" + id);
		final NodeEntry entry = new NodeEntry(id, name, constructor);
		nodes.put(id, entry);
		return entry;
	}
}
