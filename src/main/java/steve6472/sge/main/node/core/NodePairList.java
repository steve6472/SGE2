package steve6472.sge.main.node.core;

import java.util.ArrayList;
import java.util.Iterator;

/**********************
 * Created by steve6472
 * On date: 20.04.2020
 * Project: NoiseGenerator
 *
 ***********************/
public class NodePairList
{
	private final ArrayList<NodePair> list;

	public NodePairList()
	{
		list = new ArrayList<>();
	}

	public Iterator<NodePair> iterator()
	{
		return list.iterator();
	}

	public ArrayList<NodePair> getList()
	{
		return list;
	}
}
