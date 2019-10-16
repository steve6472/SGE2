package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.font.Font;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 14.08.2019
 * Project: SJP
 *
 ***********************/
public class GCLog
{
	private List<Integer> lives;
	private List<String> texts;

	private GarbageCollectorMXBean gc;
	private long lastCollection;

	public GCLog()
	{
		gc = ManagementFactory.getGarbageCollectorMXBeans().get(0);
		lives = new ArrayList<>(8);
		texts = new ArrayList<>(8);
	}

	public void tick()
	{
		for (int j = 0; j < lives.size(); j++)
		{
			lives.set(j, lives.get(j) - 1);
		}

		int i = 0;
		for (Iterator<Integer> iter = lives.iterator(); iter.hasNext(); )
		{
			Integer integer = iter.next();
			if (integer <= 0)
			{
				iter.remove();
				texts.remove(i);
			}
			i++;

		}

		if (gc.getCollectionTime() != lastCollection)
		{
			String t = gc.getCollectionCount() + " " + (gc.getCollectionTime() - lastCollection);
			lastCollection = gc.getCollectionTime();
			if (lives.size() >= 8)
			{
				lives.remove(0);
				texts.remove(0);
			}
			lives.add(120);
			texts.add(t);
		}
	}

	public void render(int x, int y)
	{
		for (int i = 0; i < lives.size(); i++)
		{
			float l = lives.get(i);
			if (l < 60)
				Font.renderCustom(x, y + i * 10, 1, "[1.0,1.0,1.0," + l / 60f + "]", texts.get(i));
			else
				Font.renderCustom(x, y + i * 10, 1, texts.get(i));
		}
	}
}
