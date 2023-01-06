package steve6472.sge.main.node.core;

import org.json.JSONArray;
import org.json.JSONObject;
import steve6472.sge.main.Log;
import steve6472.sge.main.util.JSONUtil;
import steve6472.sge.main.util.Pair;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**********************
 * Created by steve6472
 * On date: 4/20/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Nodes
{
	public static LinkedHashMap<String, NodeEntry> nodes = new LinkedHashMap<>();

	public static Pair<AbstractNode, NodeJoint> selectedFrom;

	private static void saveInputsOutputs(AbstractNode node, JSONObject nodeJson)
	{
		JSONArray inputs = new JSONArray();
		for (Object o : node.inputStates)
		{
			inputs.put(o);
		}

		JSONArray outputs = new JSONArray();
		for (Object o : node.outputStates)
		{
			outputs.put(o);
		}

		nodeJson.put("inputs", inputs);
		nodeJson.put("outputs", outputs);
	}

	private static void saveManualInputs(AbstractNode node, JSONObject nodeJson)
	{
		JSONArray manualInputs = new JSONArray();
		for (int i = 0; i < node.inputStates.length; i++)
		{
			manualInputs.put(node.isManual(i));
		}

		nodeJson.put("manual_inputs", manualInputs);
	}

	private static void saveInputConnections(AbstractNode node, JSONObject nodeJson)
	{
		JSONObject connections = new JSONObject();

		for (int i = 0; i < node.inputConnections.length; i++)
		{
			NodePair pair = node.inputConnections[i];
			if (pair == null)
				continue;

			String uuid = pair.getNode().getUuid().toString();

			if (connections.has(uuid))
			{
				JSONArray arr = connections.getJSONArray(uuid);
				arr.put(pair.getIndex()).put(i);
				connections.put(uuid, arr);
			} else
			{
				connections.put(uuid, new JSONArray().put(pair.getIndex()).put(i));
			}
		}

		nodeJson.put("connections", connections);
	}

	private static JSONObject saveNode(AbstractNode node, JSONArray nodeArray)
	{
		JSONObject nodeJson = new JSONObject();
		if (node.getId() == null || node.getId().isEmpty())
		{
			throw new IllegalStateException("Node has no ID");
		}
		nodeJson.put("id", node.getId());

		saveInputsOutputs(node, nodeJson);
		saveInputConnections(node, nodeJson);
		saveManualInputs(node, nodeJson);

		if (node instanceof ExtraData e)
		{
			nodeJson.put("extra", e.save());
		}

		nodeJson.put("uuid", node.getUuid().toString());

		nodeArray.put(nodeJson);
		return nodeJson;
	}

	public static void save(File file, NodeContainer container)
	{
		JSONObject main = new JSONObject();
		JSONArray array = new JSONArray();

		for (AbstractNode node : container.getNodes())
		{
			final JSONObject nodeJson = saveNode(node, array);
			nodeJson.put("x", node.guiX);
			nodeJson.put("y", node.guiY);
		}

		main.put("nodes", array);

		JSONUtil.writeJSON(file, main);
	}

	public static void saveGui(File file, NodeContainer container)
	{
		JSONObject main = new JSONObject();
		JSONArray array = new JSONArray();

		for (AbstractNode node : container.getNodes())
		{
			final JSONObject nodeJson = saveNode(node, array);
			nodeJson.put("x", node.guiNode.getX());
			nodeJson.put("y", node.guiNode.getY());
		}

		main.put("nodes", array);

		JSONUtil.writeJSON(file, main);
	}

	private static void loadInputsOutputs(AbstractNode node, JSONObject nodeJson)
	{
		JSONArray inputs = nodeJson.getJSONArray("inputs");
		for (int j = 0; j < inputs.length(); j++)
		{
			node.inputStates[j] = inputs.get(j);
		}

		JSONArray outputs = nodeJson.getJSONArray("outputs");
		for (int j = 0; j < outputs.length(); j++)
		{
			node.outputStates[j] = outputs.get(j);
		}
	}

	private static void loadManualInputs(AbstractNode node, JSONObject nodeJson)
	{
		JSONArray inputs = nodeJson.getJSONArray("manual_inputs");
		for (int i = 0; i < inputs.length(); i++)
		{
			node.setManual(i, inputs.getBoolean(i));
		}
	}

	private static AbstractNode getNode(String uuid, AbstractNode[] array)
	{
		for (AbstractNode node : array)
		{
			if (node.getUuid().toString().equals(uuid))
				return node;
		}

		Log.err("No node with uuid of " + uuid + " was found! No connection will be created.");
		return null;
	}

	private static JSONObject loadJson(File file)
	{
		JSONObject json = null;

		if (!file.exists())
		{
			Log.err("Save not found!");
		} else
		{
			json = JSONUtil.readJSON(file);
		}

		if (json == null)
		{
			Log.err("Could not load save!");
		}

		return json;
	}

	private static AbstractNode[] loadNodeArray(JSONArray nodeJsonArray, BiConsumer<AbstractNode, JSONObject> perNode)
	{
		AbstractNode[] nodeArray = new AbstractNode[nodeJsonArray.length()];

		for (int i = 0; i < nodeJsonArray.length(); i++)
		{
			JSONObject nodeJson = nodeJsonArray.getJSONObject(i);

			if (!nodeJson.has("id"))
			{
				Log.err("Node has no id!\nNode will not be loaded!");
				continue;
			}

			AbstractNode node = createNode(nodeJson.getString("id"));
			if (node == null)
			{
				Log.err("No node with id of '" + nodeJson.getString("id") + "' was found!\nNo node will be loaded!");
				continue;
			}

			loadInputsOutputs(node, nodeJson);
			loadManualInputs(node, nodeJson);

			nodeArray[i] = node;
			node.setUuid(UUID.fromString(nodeJson.getString("uuid")));

			perNode.accept(node, nodeJson);
		}

		return nodeArray;
	}

	private static void loadExtraData(AbstractNode node, JSONObject nodeJson)
	{
		if (node instanceof ExtraData e)
		{
			if (!nodeJson.has("extra"))
			{
				Log.err("Node " + e.getClass().getSimpleName() + " with UUID " + node.getUuid() + " has ExtraData but no ExtraData was found in JSON!");
			} else
			{
				e.load(nodeJson.getJSONObject("extra"));
			}
		}
	}

	private static void connectNodes(JSONArray nodesArray, AbstractNode[] nodeArray, NodeContainer container)
	{
		for (int i = 0; i < nodesArray.length(); i++)
		{
			JSONObject node = nodesArray.getJSONObject(i);

			AbstractNode thisNode = nodeArray[i];
			if (container != null)
				container.getNodes().add(thisNode);

			JSONObject connections = node.getJSONObject("connections");

			for (String uuid : connections.keySet())
			{
				AbstractNode otherNode = getNode(uuid, nodeArray);
				if (otherNode == null)
				{
					Log.err("No node with uuid of " + uuid + " was found! No connection will be created.");
					continue;
				}

				JSONArray fromTo = connections.getJSONArray(uuid);

				for (int j = 0; j < fromTo.length() / 2; j++)
				{
					otherNode.connectOutput(fromTo.getInt(j * 2), thisNode, fromTo.getInt(j * 2 + 1));
					thisNode.connectInput(fromTo.getInt(j * 2 + 1), otherNode, fromTo.getInt(j * 2));
				}
			}
		}
	}

	public static void load(File file, NodeContainer container)
	{
		JSONObject json;

		if ((json = loadJson(file)) == null)
		{
			return;
		}

		JSONArray nodes = json.getJSONArray("nodes");

		final AbstractNode[] nodeArray = loadNodeArray(nodes, (node, nodeJson) -> {
			node.guiX = nodeJson.getInt("x");
			node.guiY = nodeJson.getInt("y");
			loadExtraData(node, nodeJson);
		});
		connectNodes(nodes, nodeArray, container);
	}

	public static void loadGui(File file, NodeContainer container)
	{
		JSONObject json;

		if ((json = loadJson(file)) == null)
		{
			return;
		}

		JSONArray nodes = json.getJSONArray("nodes");

		final AbstractNode[] nodeArray = loadNodeArray(nodes, (node, nodeJson) ->
		{
			GuiNode guiNode = createGuiNode(node);
			guiNode.setLocation(nodeJson.getInt("x"), nodeJson.getInt("y"));
			container.addNode(guiNode);
			loadExtraData(node, nodeJson);
		});

		connectNodes(nodes, nodeArray, null);
	}

	public static AbstractNode createNode(String id)
	{
		return nodes.get(id).create();
	}

	public static GuiNode createGuiNode(String id)
	{
		return nodes.get(id).createGuiNode();
	}

	public static GuiNode createGuiNode(AbstractNode node)
	{
		return nodes.get(node.getId()).createGuiNode(node);
	}

	public static NodeEntry registerNode(String id, String name, Supplier<AbstractNode> constructor)
	{
		System.out.println("registered " + id);
		if (Nodes.nodes.get(id) != null)
			throw new IllegalArgumentException("Duplicate node id:" + id);
		final NodeEntry entry = new NodeEntry(id, name, constructor);
		Nodes.nodes.put(id, entry);
		return entry;
	}

	public static NodeEntry registerNode(String id, String name, Supplier<AbstractNode> constructor, Function<AbstractNode, GuiNode> guiConstructor)
	{
		System.out.println("registered " + id);
		if (Nodes.nodes.get(id) != null)
			throw new IllegalArgumentException("Duplicate node id:" + id);
		final NodeEntry entry = new NodeEntry(id, name, constructor, guiConstructor);
		Nodes.nodes.put(id, entry);
		return entry;
	}

	public static void connect(AbstractNode from, AbstractNode to, int fromOutput, int toInput)
	{
		from.connectOutput(fromOutput, to, toInput);
		to.connectInput(toInput, from, fromOutput);
		to.updateInputState(toInput, from.outputStates[fromOutput]);
	}

	public static void disconnect(AbstractNode node, int input)
	{
		final NodePair inputConnection = node.inputConnections[input];
		inputConnection.getNode().disconnectOutput(inputConnection.getIndex(), node, input);
		node.disconnectInput(input);
	}

	public static void convertGuiToNormal(NodeContainer container)
	{
		for (AbstractNode node : container.getNodes())
		{
			node.guiX = node.guiNode.getX();
			node.guiY = node.guiNode.getY();

			node.guiNode.close();
			node.guiNode = null;
		}
	}

	public static void convertNormalToGui(NodeContainer container)
	{
		for (AbstractNode node : container.getNodes())
		{
			GuiNode guiNode = createGuiNode(node);
			guiNode.setLocation(node.guiX, node.guiY);
			container.addNode(guiNode);
		}
	}
}
