package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.SpriteRender;
import com.steve6472.sge.gfx.font.CustomChar;
import com.steve6472.sge.gfx.font.Font;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.schemes.Scheme;
import com.steve6472.sge.gui.components.schemes.SchemeButton;
import com.steve6472.sge.main.KeyList;
import com.steve6472.sge.main.MainApp;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.steve6472.sge.main.util.ColorUtil.getColors;
import static com.steve6472.sge.main.util.ColorUtil.getVectorColor;

public class Button extends Component
{
	private static final long serialVersionUID = -4734082970298391201L;
	private int fontSize = 1;

	protected boolean enabled = true, hovered = false;

	private String text = "";
	private Object[] customText;

	public SchemeButton scheme;

	/*
	 * Font Colors
	 */
	public Vector4f enabledColor, disabledColor, hoveredColor;
//	public float enabledRed, enabledGreen, enabledBlue;
//	public float disabledRed, disabledGreen, disabledBlue;
//	public float hoveredRed, hoveredGreen, hoveredBlue;

	public Button(String text)
	{
		this.text = text;
		enabledColor = new Vector4f();
		disabledColor = new Vector4f();
		hoveredColor = new Vector4f();
	}

	public Button(CustomChar text)
	{
		this.text = text.toString();
		enabledColor = new Vector4f();
		disabledColor = new Vector4f();
		hoveredColor = new Vector4f();
	}
	
	public Button()
	{
		enabledColor = new Vector4f();
		disabledColor = new Vector4f();
		hoveredColor = new Vector4f();
	}
	
	@Override
	public void init(MainApp main)
	{
		if (scheme == null)
			setScheme(main.getSchemeRegistry().getCurrentScheme("button"));
	}

	public void setScheme(Scheme scheme)
	{
		this.scheme = (SchemeButton) scheme;
		resetAllColors();
	}
	
	@Override
	public void tick()
	{
		if (isVisible() && enabled)
		{
			boolean isHovered = isCursorInComponent(x, y, width, height);
			
			if (isHovered)
			{
				if (isLMBHolded())
					runHoldEvents();

				hovered = true;
			} else
			{
				hovered = false;
			}

			onMouseClicked(KeyList.LMB, b -> forceDoClick());
		}
	}
	
	public void doClick()
	{
		if (isEnabled())
		{
			forceDoClick();
		}
	}

	/**
	 * Ignores {@code isEnabled()} and runs all ButtonEvents
	 */
	public void forceDoClick()
	{
		runClickEvents();
		runIfClickEvents();
	}

	@Override
	public void render()
	{
		if (enabled && !hovered)
			SpriteRender.renderDoubleBorder(x, y, width, height, scheme.enabledOutsideBorder, scheme.enabledInsideBorder, scheme.enabledFill);

		if (!enabled)
			SpriteRender.renderDoubleBorder(x, y, width, height, scheme.disabledOutsideBorder, scheme.disabledInsideBorder, scheme.disabledFill);

		if (enabled && hovered)
			SpriteRender.renderDoubleBorder(x, y, width, height, scheme.hoveredOutsideBorder, scheme.hoveredInsideBorder, scheme.hoveredFill);

		renderText();
	}
	
	protected void renderText()
	{
		if (customText != null)
		{
			int fontWidth = Font.getTextWidth(text, fontSize) / 2;
			int fontHeight = ((8 * fontSize)) / 2;

			if (enabled && !hovered)
				Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, fontSize, customText);

			if (!enabled)
				Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, fontSize, customText);

			if (enabled && hovered)
				Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, fontSize, customText);
		}
		else if (text != null)
		{
			int fontWidth = Font.getTextWidth(text, fontSize) / 2;
			int fontHeight = ((8 * fontSize)) / 2;

			if (enabled && !hovered)
				Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, fontSize, getColor(enabledColor), text);

			if (!enabled)
				Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, fontSize, getColor(disabledColor), text);

			if (enabled && hovered)
				Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, fontSize, getColor(hoveredColor), text);
		}
	}

	protected String getColor(Vector4f c)
	{
		return "[" + c.x + "," + c.y + "," + c.z + "," + c.w + "]";
	}
	
	/*
	 * Setters
	 */

	public void setText(String text)
	{
		this.text = text;
	}

	public void setCustomText(Object... customText)
	{
		this.customText = customText;
	}

	public void setEnabled(boolean b)
	{
		this.enabled = b;
	}

	public void setSize(int w, int h)
	{
		this.width = w;
		this.height = h;
	}

	public void setFontSize(int s)
	{
		fontSize = Math.max(1, s);
	}

	public void setFontColor(int color)
	{
		float[] colors = getColors(color);
		setFontColor(colors[0], colors[1], colors[2]);
	}

	public void setEnabledFontColor(int color)
	{
		enabledColor = new Vector4f(getVectorColor(color), 1.0f);
//		float[] colors = getColors(color);
//		enabledRed = colors[0];
//		enabledGreen = colors[1];
//		enabledBlue = colors[2];
	}

	public void setDisabledFontColor(int color)
	{
		disabledColor = new Vector4f(getVectorColor(color), 1.0f);
//		float[] colors = getColors(color);
//		disabledRed = colors[0];
//		disabledGreen = colors[1];
//		disabledBlue = colors[2];
	}

	public void setHoveredFontColor(int color)
	{
		hoveredColor = new Vector4f(getVectorColor(color), 1.0f);
//		float[] colors = getColors(color);
//		hoveredRed = colors[0];
//		hoveredGreen = colors[1];
//		hoveredBlue = colors[2];
	}

	public void setFontColor(float red, float green, float blue)
	{
		setEnabledFontColor(red, green, blue);
		setDisabledFontColor(red, green, blue);
		setHoveredFontColor(red, green, blue);
	}
	
	public void setEnabledFontColor(float red, float green, float blue)
	{
		enabledColor.x = red;
		enabledColor.y = green;
		enabledColor.z = blue;
//		this.enabledRed = red;
//		this.enabledGreen = green;
//		this.enabledBlue = blue;
	}

	public void setDisabledFontColor(float red, float green, float blue)
	{
		disabledColor.x = red;
		disabledColor.y = green;
		disabledColor.z = blue;
//		this.disabledRed = red;
//		this.disabledGreen = green;
//		this.disabledBlue = blue;
	}

	public void setHoveredFontColor(float red, float green, float blue)
	{
		hoveredColor.x = red;
		hoveredColor.y = green;
		hoveredColor.z = blue;
//		this.hoveredRed = red;
//		this.hoveredGreen = green;
//		this.hoveredBlue = blue;
	}

	public void resetEnabledColors()
	{
		enabledColor.x = scheme.enabledRed;
		enabledColor.y = scheme.enabledGreen;
		enabledColor.z = scheme.enabledBlue;
//		enabledRed = scheme.enabledRed;
//		enabledGreen = scheme.enabledGreen;
//		enabledBlue = scheme.enabledBlue;
	}

	public void resetDisabledColors()
	{
		disabledColor.x = scheme.disabledRed;
		disabledColor.y = scheme.disabledGreen;
		disabledColor.z = scheme.disabledBlue;
//		disabledRed = scheme.disabledRed;
//		disabledGreen = scheme.disabledGreen;
//		disabledBlue = scheme.disabledBlue;
	}

	public void resetHoveredColors()
	{
		hoveredColor.x = scheme.hoveredRed;
		hoveredColor.y = scheme.hoveredGreen;
		hoveredColor.z = scheme.hoveredBlue;
//		hoveredRed = scheme.hoveredRed;
//		hoveredGreen = scheme.hoveredGreen;
//		hoveredBlue = scheme.hoveredBlue;
	}

	public void resetAllColors()
	{
		resetEnabledColors();
		resetDisabledColors();
		resetHoveredColors();
	}
	
	/*
	 * Getters
	 */

	public String getText()
	{
		return text;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getFontSize()
	{
		return fontSize;
	}

	public boolean isHovered()
	{
		return hovered;
	}

	/* Events */

	private List<Consumer<Button>> clickEvents = new ArrayList<>();
	private List<IfClickEvent> ifClickEvents = new ArrayList<>();
	private List<Consumer<Button>> holdEvents = new ArrayList<>();

	/* Click Event */

	public void addClickEvent(Consumer<Button> c)
	{
		clickEvents.add(c);
	}

	private void runClickEvents()
	{
		clickEvents.forEach(c -> c.accept(this));
	}

	/* If Click Event */

	/**
	 * Runs only if condition in <b> f </b> is met
	 * @param f Condition
	 * @param c Event
	 */
	public void addIfClickEvent(Function<Button, Boolean> f, Consumer<Button> c)
	{
		ifClickEvents.add(new IfClickEvent(f, c));
	}

	private void runIfClickEvents()
	{
		ifClickEvents.forEach(c -> {
			if (c.f.apply(this))
				c.c.accept(this);
		});
	}

	/* Hold Event */

	public void addHoldEvent(Consumer<Button> c)
	{
		holdEvents.add(c);
	}

	private void runHoldEvents()
	{
		holdEvents.forEach(c -> c.accept(this));
	}

	/* Class required for events */

	private class IfClickEvent
	{
		Function<Button, Boolean> f;
		Consumer<Button> c;

		private IfClickEvent(Function<Button, Boolean> f, Consumer<Button> c)
		{
			this.f = f;
			this.c = c;
		}
	}
}
