package com.steve6472.sge.gui.components.schemes;

import com.steve6472.sss2.SSS;
import org.joml.Vector4f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.12.2018
 * Project: SGE2
 *
 ***********************/
public class SchemeBackground extends Scheme
{
	public Vector4f outsideBorder, insideBorder, fill;

	@Override
	public SchemeBackground load(String path)
	{
		SSS sss = SchemeLoader.load(path);

		int outsideBorder = sss.getHexInt("outsideBorder");
		int insideBorder  = sss.getHexInt("insideBorder");
		int fill          = sss.getHexInt("fill");

		this.outsideBorder = toVector(outsideBorder);
		this.insideBorder = toVector(insideBorder);
		this.fill = toVector(fill);

		return this;
	}

	@Override
	public String getId()
	{
		return "background";
	}
}
