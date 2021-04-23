package steve6472.sge.main.node.core;

import steve6472.sge.main.Log;

import java.util.Objects;
import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 15.04.2020
 * Project: NoiseGenerator
 *
 ***********************/
public abstract class AbstractNode
{
	public NodePair[] inputConnections;
	public NodePairList[] outputConnections;

	private String id;
	private UUID uuid;

	protected NodeData inputData, outputData;

	protected Object[] inputStates;
	protected Object[] outputStates;

	private boolean[] inputManual;

	protected GuiNode guiNode;
	// Used when converting from gui -> normal and back
	protected int guiX, guiY;

	public AbstractNode()
	{
		createData();

		inputStates = new Object[inputData.getSize()];
		outputStates = new Object[outputData.getSize()];
		inputManual = new boolean[inputData.getSize()];

		uuid = UUID.randomUUID();

		inputConnections = new NodePair[inputData.getSize()];
		outputConnections = new NodePairList[outputData.getSize()];
		initNode();
	}

	public AbstractNode(NodeData inputData, NodeData outputData)
	{
		if (inputData == null)
			this.inputData = NodeData.empty();
		else
			this.inputData = inputData;

		if (outputData == null)
			this.outputData = NodeData.empty();
		else
			this.outputData = outputData;

		inputStates = new Object[this.inputData.getSize()];
		outputStates = new Object[this.outputData.getSize()];
		inputManual = new boolean[this.inputData.getSize()];

		uuid = UUID.randomUUID();

		inputConnections = new NodePair[this.inputData.getSize()];
		outputConnections = new NodePairList[this.outputData.getSize()];
		initNode();
	}

	protected void createData()
	{
		NodeData inputDataTemp = createInputData();
		NodeData outputDataTemp = createOutputData();

		if (inputDataTemp == null)
			inputData = NodeData.empty();
		else
			inputData = inputDataTemp;

		if (outputDataTemp == null)
			outputData = NodeData.empty();
		else
			outputData = outputDataTemp;
	}

	public void disconnectOutput(int outputIndex, AbstractNode node, int inputIndex)
	{
		if (outputConnections[outputIndex] != null)
		{
			outputConnections[outputIndex].getList().removeIf(c -> c.getNode() == node && c.getIndex() == inputIndex);
			if (outputConnections[outputIndex].getList().isEmpty())
				outputConnections[outputIndex] = null;
		}
	}

	public void disconnectInput(int inputIndex)
	{
		inputConnections[inputIndex] = null;
	}

	public void connectOutput(int outputIndex, AbstractNode node, int inputIndex)
	{
		if (outputConnections[outputIndex] == null)
			outputConnections[outputIndex] = new NodePairList();
		outputConnections[outputIndex].getList().add(new NodePair(node, inputIndex));
		updateOutputs();
	}

	public void connectInput(int inputIndex, AbstractNode node, int outputIndex)
	{
		if (inputConnections[inputIndex] != null)
			throw new IllegalStateException("Input join can have only one connection! NodeJoint check failed!");
		inputConnections[inputIndex] = new NodePair(node, outputIndex);
	}

	public boolean hasOutput(int index)
	{
		return outputConnections[index] != null;
	}

	public boolean hasInput(int index)
	{
		return inputConnections[index] != null;
	}

	public NodeData getInputData()
	{
		return inputData;
	}

	public NodeData getOutputData()
	{
		return outputData;
	}

	protected void runFlow(int index)
	{
		if (outputData.getTypes()[index] != JointTypeRegistry.FLOW)
		{
			Log.err("Tried to run flow on non-flow output type!");
			return;
		}

		if (outputConnections[index] == null)
		{
			Log.err("Can not run flow. No output at " + index);
			return;
		}

		if (NodeJoint.ENABLE_MULTIFLOW)
		{
			for (NodePair nodePair : outputConnections[index].getList())
			{
				nodePair.getNode().flowRun();
			}
		}
		else
		{
			outputConnections[index].getList().get(0).getNode().flowRun();
		}
	}

	public void printStates()
	{
		System.out.println("InputStates: ");
		for (Object inputState : inputStates)
		{
			System.out.println("\t" + inputState);
		}

		System.out.println("OutputStates: ");
		for (Object outputState : outputStates)
		{
			System.out.println("\t" + outputState);
		}
	}

	public void flowRun()
	{

	}

	public void tick()
	{
		tickNode();
	}

	public void updateInputState(int index, Object state)
	{
		if (!Objects.equals(inputStates[index], state))
		{
			inputStates[index] = state;
			inputManual[index] = false;
			updateOutputState();
			updateOutputs();
		}
	}

	public void updateInputStateManual(int index, Object state)
	{
		if (!Objects.equals(inputStates[index], state))
		{
			inputStates[index] = state;
			inputManual[index] = true;
			updateOutputState();
			updateOutputs();
		}
	}

	public boolean isManual(int inputIndex)
	{
		return inputManual[inputIndex];
	}

	public boolean setManual(int inputIndex, boolean manual)
	{
		return inputManual[inputIndex] = manual;
	}

	protected abstract void updateOutputState();

	public void updateOutputs()
	{
		for (int i = 0; i < outputConnections.length; i++)
		{
			NodePairList out = outputConnections[i];
			if (out != null)
			{
				for (NodePair c : out.getList())
				{
					c.getNode().updateInputState(c.getIndex(), outputStates[i]);
				}
			}
		}
	}

	protected abstract void initNode();
	protected void tickNode() {}

	protected abstract NodeData createInputData();
	protected abstract NodeData createOutputData();

	public abstract String getName();

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}
}
