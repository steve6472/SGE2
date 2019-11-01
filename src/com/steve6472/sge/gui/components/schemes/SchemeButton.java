package com.steve6472.sge.gui.components.schemes;

import com.steve6472.sge.main.util.ColorUtil;
import com.steve6472.sss2.SSS;
import org.joml.Vector3f;
import org.joml.Vector4f;

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

	public Vector3f enabled;
	public Vector3f disabled;
	public Vector3f hovered;

	@Override
	public SchemeButton load(String path)
	{
		SSS sss = SchemeLoader.load(path);

		enabledOutsideBorder  = ColorUtil.getVector4Color(sss.getHexInt("enabledOutsideBorder"));
		enabledInsideBorder   = ColorUtil.getVector4Color(sss.getHexInt("enabledInsideBorder"));
		enabledFill           = ColorUtil.getVector4Color(sss.getHexInt("enabledFill"));

		disabledOutsideBorder = ColorUtil.getVector4Color(sss.getHexInt("disabledOutsideBorder"));
		disabledInsideBorder  = ColorUtil.getVector4Color(sss.getHexInt("disabledInsideBorder"));
		disabledFill          = ColorUtil.getVector4Color(sss.getHexInt("disabledFill"));

		hoveredOutsideBorder  = ColorUtil.getVector4Color(sss.getHexInt("hoveredOutsideBorder"));
		hoveredInsideBorder   = ColorUtil.getVector4Color(sss.getHexInt("hoveredInsideBorder"));
		hoveredFill           = ColorUtil.getVector4Color(sss.getHexInt("hoveredFill"));

		enabled    = ColorUtil.getVector3Color(sss.getHexInt("enabledTextColor"));
		disabled   = ColorUtil.getVector3Color(sss.getHexInt("disabledTextColor"));
		hovered    = ColorUtil.getVector3Color(sss.getHexInt("hoveredTextColor"));

		return this;
	}

	public void setFontColor(float red, float green, float blue)
	{
		setEnabledFontColor(red, green, blue);
		setDisabledFontColor(red, green, blue);
		setHoveredFontColor(red, green, blue);
	}

	public void setEnabledFontColor(float red, float green, float blue)
	{
		enabled.x = red;
		enabled.y = green;
		enabled.z = blue;
	}

	public void setDisabledFontColor(float red, float green, float blue)
	{
		disabled.x = red;
		disabled.y = green;
		disabled.z = blue;
	}

	public void setHoveredFontColor(float red, float green, float blue)
	{
		hovered.x = red;
		hovered.y = green;
		hovered.z = blue;
	}

	public SchemeButton()
	{
	}

	@SuppressWarnings("IncompleteCopyConstructor")
	public SchemeButton(SchemeButton other)
	{
		super(other);
		this.enabledOutsideBorder = new Vector4f(other.enabledOutsideBorder);
		this.enabledInsideBorder = new Vector4f(other.enabledInsideBorder);
		this.enabledFill = new Vector4f(other.enabledFill);
		this.disabledOutsideBorder = new Vector4f(other.disabledOutsideBorder);
		this.disabledInsideBorder = new Vector4f(other.disabledInsideBorder);
		this.disabledFill = new Vector4f(other.disabledFill);
		this.hoveredOutsideBorder = new Vector4f(other.hoveredOutsideBorder);
		this.hoveredInsideBorder = new Vector4f(other.hoveredInsideBorder);
		this.hoveredFill = new Vector4f(other.hoveredFill);
		this.enabled = new Vector3f(other.enabled);
		this.disabled = new Vector3f(other.disabled);
		this.hovered = new Vector3f(other.hovered);
	}

	public String getId()
	{
		return "button";
	}
}
