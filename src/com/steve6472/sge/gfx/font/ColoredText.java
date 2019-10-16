package com.steve6472.sge.gfx.font;

import com.steve6472.sge.main.util.ColorUtil;

class ColoredText
{
	float r, g, b;
	String text;

	ColoredText(String text, String tag)
	{
		this.text = text;

		if (tag.startsWith("#"))
		{
			int c = (int) Long.parseLong(tag.substring(1), 16);
			float[] a = ColorUtil.getColors(c);
			r = a[0];
			g = a[1];
			b = a[2];
		} else
		{
			if (tag.isBlank())
			{
				r = 1.0f;
				g = 1.0f;
				b = 1.0f;
				return;
			}

			String[] s;

			if (tag.contains(";"))
				s = tag.split(";");
			else
				s = tag.split(" ");

			r = Float.parseFloat(s[0]);
			g = Float.parseFloat(s[1]);
			b = Float.parseFloat(s[2]);
		}
	}

	@Override
	public String toString()
	{
		return "ColoredText{" + "r=" + r + ", g=" + g + ", b=" + b + ", text='" + text + '\'' + '}';
	}
}