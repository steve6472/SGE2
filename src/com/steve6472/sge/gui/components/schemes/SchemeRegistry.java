package com.steve6472.sge.gui.components.schemes;

import com.steve6472.sss2.SSS;
import com.steve6472.sss2.ValueHolder;

import java.io.File;
import java.util.HashMap;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 14.12.2018
 * Project: SGE2
 *
 ***********************/
public class SchemeRegistry
{
	private HashMap<String, Scheme> schemeHashMap = new HashMap<>();

	public void loadCurrentSchemes()
	{
		File f = new File("schemes/currentSchemes.txt");

		//Create new files with dark scheme as default
		if (!f.getParentFile().exists())
		{
			f.getParentFile().mkdirs();
			SSS sss = new SSS(f);
			for (String s : schemeHashMap.keySet())
			{
				sss.add(s, "*dark/" + s + ".txt");
			}
			sss.save(f);
		}

		SSS sss = new SSS(f);
		for (ValueHolder vh : sss)
		{
			schemeHashMap.get(vh.getName()).load(vh.getString());
		}
	}

	public void registerScheme(Scheme scheme)
	{
		if (schemeHashMap.containsValue(scheme))
			throw new IllegalArgumentException("Duplicate scheme:" + scheme);
		else
			schemeHashMap.put(scheme.getId(), scheme);
	}

	public Scheme getCurrentScheme(String id)
	{
		return schemeHashMap.get(id);
	}
}
