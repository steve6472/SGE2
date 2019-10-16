package com.steve6472.sge.gui.components.schemes;

import com.steve6472.sge.main.MainApp;
import com.steve6472.sss2.SSS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.12.2018
 * Project: SGE2
 *
 ***********************/
public class SchemeLoader
{
	public static SSS load(String path)
	{
		boolean inBuilt = path.startsWith("*");
		SSS sss;
		if (inBuilt)
		{
			List<String> rawString = new ArrayList<>();
			InputStreamReader isr = new InputStreamReader(MainApp.class.getResourceAsStream("/schemes/" + path.substring(1)));
			BufferedReader br = new BufferedReader(isr);
			try
			{
				for (String s = br.readLine(); s != null; s = br.readLine())
				{
					rawString.add(s);
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			sss = new SSS(rawString.toArray(new String[0]));
		} else
		{
			sss = new SSS("schemes/" + path.substring(1));
		}
		return sss;
	}
}
