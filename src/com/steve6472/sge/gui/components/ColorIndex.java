package com.steve6472.sge.gui.components;

import com.steve6472.sge.main.SGArray;

public class ColorIndex
{
	public String index;
	public SGArray<String> words;
	
	public ColorIndex(String index, String... words)
	{
		this.words = new SGArray<String>();
		this.index = index;
		
		for (String s : words)
			this.words.add(s);
	}
	
}