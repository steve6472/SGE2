package com.steve6472.sge.gui.components.schemes;

import com.steve6472.sss2.SSS;
import org.joml.Vector4f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.12.2018
 * Project: SGE2
 *
 ***********************/
public class SchemeSlider extends Scheme
{
	public Vector4f buttonEnabledOutsideBorder;
	public Vector4f buttonEnabledInsideBorder;
	public Vector4f buttonEnabledFill;

	public Vector4f buttonDisabledOutsideBorder;
	public Vector4f buttonDisabledInsideBorder;
	public Vector4f buttonDisabledFill;

	public Vector4f buttonHoveredOutsideBorder;
	public Vector4f buttonHoveredInsideBorder;
	public Vector4f buttonHoveredFill;

	public Vector4f sliderOutsideBorder;
	public Vector4f sliderInsideBorder;
	public Vector4f sliderFill;

	@Override
	public SchemeSlider load(String path)
	{
		SSS sss = SchemeLoader.load(path);

		buttonEnabledOutsideBorder  = toVector(sss.getHexInt("buttonEnabledOutsideBorder"));
		buttonEnabledInsideBorder   = toVector(sss.getHexInt("buttonEnabledInsideBorder"));
		buttonEnabledFill           = toVector(sss.getHexInt("buttonEnabledFill"));

		buttonDisabledOutsideBorder = toVector(sss.getHexInt("buttonDisabledOutsideBorder"));
		buttonDisabledInsideBorder  = toVector(sss.getHexInt("buttonDisabledInsideBorder"));
		buttonDisabledFill          = toVector(sss.getHexInt("buttonDisabledFill"));

		buttonHoveredOutsideBorder  = toVector(sss.getHexInt("buttonHoveredOutsideBorder"));
		buttonHoveredInsideBorder   = toVector(sss.getHexInt("buttonHoveredInsideBorder"));
		buttonHoveredFill           = toVector(sss.getHexInt("buttonHoveredFill"));

		sliderOutsideBorder         = toVector(sss.getHexInt("sliderOutsideBorder"));
		sliderInsideBorder          = toVector(sss.getHexInt("sliderInsideBorder"));

		sliderFill                  = toVector(sss.getHexInt("sliderFill"));

		return this;
	}

	public String getId()
	{
		return "slider";
	}
}
