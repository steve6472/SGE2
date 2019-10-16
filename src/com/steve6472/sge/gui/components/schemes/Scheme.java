package com.steve6472.sge.gui.components.schemes;

import com.steve6472.sge.main.util.ColorUtil;
import org.joml.Vector4f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 14.12.2018
 * Project: SGE2
 *
 ***********************/
public abstract class Scheme
{
	public abstract Scheme load(String path);
	public abstract String getId();

	protected Vector4f toVector(int hex)
	{
		float[] c = ColorUtil.getColors(hex);
		return new Vector4f(c[0], c[1], c[2], c[3]);
	}
}
