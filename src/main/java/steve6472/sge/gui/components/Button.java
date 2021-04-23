package steve6472.sge.gui.components;

import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gfx.font.CustomChar;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gui.Component;
import steve6472.sge.gui.components.schemes.IScheme;
import steve6472.sge.gui.components.schemes.SchemeButton;
import steve6472.sge.main.KeyList;
import steve6472.sge.main.MainApp;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Button extends Component implements IScheme<SchemeButton>
{
	private int fontSize = 1;

	protected boolean enabled = true, hovered = false;

	private String text;
	private Object[] customText;

	private SchemeButton scheme;

	public Button()
	{
		setScheme(MainApp.getSchemeRegistry().copyDefaultScheme(SchemeButton.class));
	}

	public Button(String text)
	{
		setScheme(MainApp.getSchemeRegistry().copyDefaultScheme(SchemeButton.class));
		this.text = text;
	}

	public Button(CustomChar text)
	{
		setScheme(MainApp.getSchemeRegistry().copyDefaultScheme(SchemeButton.class));
		this.text = text.toString();
	}

	@Override
	public void init(MainApp main)
	{

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
				Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, fontSize, getColor(getScheme().enabled), text);

			if (!enabled)
				Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, fontSize, getColor(getScheme().disabled), text);

			if (enabled && hovered)
				Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, fontSize, getColor(getScheme().hovered), text);
		}
	}

	protected String getColor(Vector4f c)
	{
		return "[" + c.x + "," + c.y + "," + c.z + "," + c.w + "]";
	}

	protected String getColor(Vector3f c)
	{
		return "[" + c.x + "," + c.y + "," + c.z + ",1.0]";
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

	@Override
	public void setScheme(SchemeButton scheme)
	{
		this.scheme = scheme;
	}

	@Override
	public SchemeButton getScheme()
	{
		return scheme;
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

	private static class IfClickEvent
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
