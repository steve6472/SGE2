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
	private HashMap<Class<? extends Scheme>, Factory> schemes = new HashMap<>();
	private HashMap<Class<? extends Scheme>, Scheme> defaultSchemes = new HashMap<>();

	public void loadCurrentSchemes()
	{
		File f = new File("schemes/currentSchemes.txt");

		createDefault(f);
		loadDefaultSchemes(f);
	}

	private void createDefault(File f)
	{
		if (!f.getParentFile().exists())
		{
			f.getParentFile().mkdirs();
			SSS sss = new SSS(f);
			for (Scheme s : defaultSchemes.values())
			{
				sss.add(s.getId(), "*dark/" + s.getId() + ".txt");
			}
			sss.save(f);
		}
	}

	private void loadDefaultSchemes(File f)
	{
		SSS sss = new SSS(f);
		for (Scheme s : defaultSchemes.values())
		{
			for (ValueHolder vh : sss)
			{
				if (s.getId().equals(vh.getName()))
				{
					s.load(vh.getString());
				}
			}
		}
	}

	public <T extends Scheme> void registerScheme(Class<T> clazz, Scheme defaultScheme, Factory<T> factory)
	{
		if (schemes.containsValue(clazz))
		{
			throw new IllegalArgumentException("Duplicate scheme:" + clazz);
		} else
		{
			schemes.put(clazz, factory);
			defaultSchemes.put(clazz, defaultScheme);
		}
	}

	public <T extends Scheme> T copyDefaultScheme(Class<T> scheme)
	{
		return (T) schemes.get(scheme).create(defaultSchemes.get(scheme));
	}

	public <T extends Scheme> Scheme getDefaultScheme(Class<T> scheme)
	{
		return defaultSchemes.get(scheme);
	}

	@FunctionalInterface
	public interface Factory<T extends Scheme>
	{
		T create(T other);
	}
}
