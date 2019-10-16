package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColorIndex
{
	public String index;
	public List<String> words;
	
	public ColorIndex(String index, String... words)
	{
		this.words = new ArrayList<>();
		this.index = index;

		this.words.addAll(Arrays.asList(words));
	}
	
}