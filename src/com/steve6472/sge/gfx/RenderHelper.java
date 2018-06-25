/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 1. 3. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.gfx;

import com.steve6472.sge.gui.Component;

public class RenderHelper
{
	public static final int BUTTON_ENABLED_COLORS[] = new int[] { 0xff000000, 0xffa8a8a8, 0xff6f6f6f };
	public static final int BUTTON_DISABLED_COLORS[] = new int[] { 0xff000000, 0xff2b2b2b, 0xff2b2b2b };
	public static final int BUTTON_HOVERED_COLORS[] = new int[] { 0xff000000, 0xffbdc6ff, 0xff7d87be };
	
	public static void renderDoubleBorderComponent(Component component, int outsideBorder, int insideBorder, int fill)
	{
		renderDoubleBorder(component.getX(), component.getY(), component.getWidth(), component.getHeight(), outsideBorder, insideBorder, fill);
	}
	
	public static void renderSingleBorderComponent(Screen screen, Component component, int border, int fill)
	{
		renderSingleBorder(component.getX(), component.getY(), component.getWidth(), component.getHeight(), border, fill);
	}
	
	public static void renderSingleBorder(int x, int y, int width, int height, int border, int fill)
	{
		Screen.drawRect(x, y, width, height, 2, border);
		Screen.fillRect(x + 2, y + 2, width - 4, height - 4, fill);
	}
	
	public static void renderDoubleBorder(int x, int y, int width, int height, int outsideBorder, int insideBorder, int fill)
	{
		Screen.drawRect(x, y, width, height, 2, outsideBorder);
		Screen.drawRect(x + 2, y + 2, width - 4, height - 4, 2, insideBorder);
		Screen.fillRect(x + 4, y + 4, width - 8, height - 8, fill);
	}
	
	public static void renderButton(int x, int y, int width, int height, boolean enabled, boolean hovered)
	{
		if (enabled && hovered)
		{
			renderDoubleBorder(x, y, width, height, BUTTON_HOVERED_COLORS[0], BUTTON_HOVERED_COLORS[1], BUTTON_HOVERED_COLORS[2]);
		} else if (enabled && !hovered)
		{
			renderDoubleBorder(x, y, width, height, BUTTON_ENABLED_COLORS[0], BUTTON_ENABLED_COLORS[1], BUTTON_ENABLED_COLORS[2]);
		} else if (!enabled)
		{
			renderDoubleBorder(x, y, width, height, BUTTON_DISABLED_COLORS[0], BUTTON_DISABLED_COLORS[1], BUTTON_DISABLED_COLORS[2]);
		}
	}
}
