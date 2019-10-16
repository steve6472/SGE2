package com.steve6472.sge.gui.components.schemes;

import com.steve6472.sss2.SSS;

import static com.steve6472.sge.main.util.ColorUtil.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 19.2.2019
 * Project: SGE2
 *
 ***********************/
public class SchemeTextField extends Scheme
{
	public float borderRed, borderGreen, borderBlue, borderAlpha;
	public float fillRed, fillGreen, fillBlue, fillAlpha;

	public float selectFillRed, selectFillGreen, selectFillBlue, selectFillAlpha;

	public float carretColorRed, carretColorGreen, carretColorBlue, carretColorAlpha;

	public float textColorRed;
	public float textColorGreen;
	public float textColorBlue;

	public float selectedTextColorRed;
	public float selectedTextColorGreen;
	public float selectedTextColorBlue;

	@Override
	public SchemeTextField load(String path)
	{
		SSS sss = SchemeLoader.load(path);

		int border              = sss.getHexInt("border");

		int fill                = sss.getHexInt("fill");
		int selectFill          = sss.getHexInt("selectFill");

		borderRed = getRed(border) / 255f;
		borderGreen = getGreen(border) / 255f;
		borderBlue = getBlue(border) / 255f;
		borderAlpha = getAlpha(border) / 255f;

		fillRed = getRed(fill) / 255f;
		fillGreen = getGreen(fill) / 255f;
		fillBlue = getBlue(fill) / 255f;
		fillAlpha = getAlpha(fill) / 255f;

		selectFillRed = getRed(selectFill) / 255f;
		selectFillGreen = getGreen(selectFill) / 255f;
		selectFillBlue = getBlue(selectFill) / 255f;
		selectFillAlpha = getAlpha(selectFill) / 255f;

		int carretColor         = sss.getHexInt("carretColor");

		carretColorRed = getRed(carretColor) / 255f;
		carretColorGreen = getGreen(carretColor) / 255f;
		carretColorBlue = getBlue(carretColor) / 255f;
		carretColorAlpha = getAlpha(carretColor) / 255f;

		textColorRed            = getRed(sss.getHexInt("textColor")) / 255f;
		textColorGreen          = getGreen(sss.getHexInt("textColor")) / 255f;
		textColorBlue           = getBlue(sss.getHexInt("textColor")) / 255f;

		selectedTextColorRed    = getRed(sss.getHexInt("selectedTextColor")) / 255f;
		selectedTextColorGreen  = getGreen(sss.getHexInt("selectedTextColor")) / 255f;
		selectedTextColorBlue   = getBlue(sss.getHexInt("selectedTextColor")) / 255f;

		return this;
	}

	@Override
	public String getId()
	{
		return "textField";
	}

	@Override
	public String toString()
	{
		return "SchemeTextField{" + "borderRed=" + borderRed + ", borderGreen=" + borderGreen + ", borderBlue=" + borderBlue + ", borderAlpha=" + borderAlpha + ", fillRed=" + fillRed + ", fillGreen=" + fillGreen + ", fillBlue=" + fillBlue + ", fillAlpha=" + fillAlpha + ", selectFillRed=" + selectFillRed + ", selectFillGreen=" + selectFillGreen + ", selectFillBlue=" + selectFillBlue + ", selectFillAlpha=" + selectFillAlpha + ", carretColorRed=" + carretColorRed + ", carretColorGreen=" + carretColorGreen + ", carretColorBlue=" + carretColorBlue + ", carretColorAlpha=" + carretColorAlpha + ", textColorRed=" + textColorRed + ", textColorGreen=" + textColorGreen + ", textColorBlue=" + textColorBlue + ", selectedTextColorRed=" + selectedTextColorRed + ", selectedTextColorGreen=" + selectedTextColorGreen + ", selectedTextColorBlue=" + selectedTextColorBlue + '}';
	}
}
