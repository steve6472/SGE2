package com.steve6472.sge.gui.components.schemes;

import com.steve6472.sss2.SSS;
import org.joml.Vector4f;

import static com.steve6472.sge.main.util.ColorUtil.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.12.2018
 * Project: SGE2
 *
 ***********************/
public class SchemeButton extends Scheme
{
	public Vector4f enabledOutsideBorder;
	public Vector4f enabledInsideBorder;
	public Vector4f enabledFill;

	public Vector4f disabledOutsideBorder;
	public Vector4f disabledInsideBorder;
	public Vector4f disabledFill;

	public Vector4f hoveredOutsideBorder;
	public Vector4f hoveredInsideBorder;
	public Vector4f hoveredFill;

	public float enabledRed;
	public float enabledGreen;
	public float enabledBlue;

	public float disabledRed;
	public float disabledGreen;
	public float disabledBlue;

	public float hoveredRed;
	public float hoveredGreen;
	public float hoveredBlue;

	@Override
	public SchemeButton load(String path)
	{
		//		SSS sss = SchemeLoader.load(true, "light", "button.txt");
		SSS sss = SchemeLoader.load(path);

		enabledOutsideBorder  = toVector(sss.getHexInt("enabledOutsideBorder"));
		enabledInsideBorder   = toVector(sss.getHexInt("enabledInsideBorder"));
		enabledFill           = toVector(sss.getHexInt("enabledFill"));

		disabledOutsideBorder = toVector(sss.getHexInt("disabledOutsideBorder"));
		disabledInsideBorder  = toVector(sss.getHexInt("disabledInsideBorder"));
		disabledFill          = toVector(sss.getHexInt("disabledFill"));

		hoveredOutsideBorder  = toVector(sss.getHexInt("hoveredOutsideBorder"));
		hoveredInsideBorder   = toVector(sss.getHexInt("hoveredInsideBorder"));
		hoveredFill           = toVector(sss.getHexInt("hoveredFill"));

		enabledRed    = getRed(sss.getHexInt("enabledRed")) / 255f;
		enabledGreen  = getGreen(sss.getHexInt("enabledGreen")) / 255f;
		enabledBlue   = getBlue(sss.getHexInt("enabledBlue")) / 255f;

		disabledRed   = getRed(sss.getHexInt("disabledRed")) / 255f;
		disabledGreen = getGreen(sss.getHexInt("disabledGreen")) / 255f;
		disabledBlue  = getBlue(sss.getHexInt("disabledBlue")) / 255f;

		hoveredRed    = getRed(sss.getHexInt("hoveredRed")) / 255f;
		hoveredGreen  = getGreen(sss.getHexInt("hoveredGreen")) / 255f;
		hoveredBlue   = getBlue(sss.getHexInt("hoveredBlue")) / 255f;

		return this;
	}

	public String getId()
	{
		return "button";
	}
}
