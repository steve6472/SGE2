package com.steve6472.sge.gui.components.schemes;

import com.steve6472.sge.main.util.ColorUtil;
import com.steve6472.sss2.SSS;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 19.2.2019
 * Project: SGE2
 *
 ***********************/
public class SchemeTextField extends Scheme
{
	public Vector4f border, fill;
	public Vector4f selectFill, carretColor;
	public Vector3f textColor, selectedTextColor;

	@Override
	public SchemeTextField load(String path)
	{
		SSS sss = SchemeLoader.load(path);

		border = ColorUtil.getVector4Color(sss.getHexInt("border"));
		fill = ColorUtil.getVector4Color(sss.getHexInt("fill"));
		selectFill = ColorUtil.getVector4Color(sss.getHexInt("selectFill"));

		carretColor = ColorUtil.getVector4Color(sss.getHexInt("carretColor"));

		textColor = ColorUtil.getVector3Color(sss.getHexInt("textColor"));
		selectedTextColor = ColorUtil.getVector3Color(sss.getHexInt("selectedTextColor"));
		return this;
	}

	public SchemeTextField()
	{
	}

	@SuppressWarnings("IncompleteCopyConstructor")
	public SchemeTextField(SchemeTextField other)
	{
		super(other);
		this.border = new Vector4f(other.border);
		this.fill = new Vector4f(other.fill);
		this.selectFill = new Vector4f(other.selectFill);
		this.carretColor = new Vector4f(other.carretColor);
		this.textColor = new Vector3f(other.textColor);
		this.selectedTextColor = new Vector3f(other.selectedTextColor);
	}

	@Override
	public String getId()
	{
		return "textField";
	}
}
