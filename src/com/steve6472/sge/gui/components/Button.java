package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.RenderHelper;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApplication;

public class Button extends Component implements IFocusable
{
	private static final long serialVersionUID = -4734082970298391201L;
	int fontSize = 1, image_offset_x, image_offset_y;
	protected Sprite enabled = null, disabled = null, hovered = null;

	protected boolean enabled_ = true, hovered_ = false;

	public boolean renderFont = true, debug = false;

	private String text = "";
	
	/*
	 * Font Colors
	 */
	private float red = 1f, green = 1f, blue = 1f;

	/*
	 * Events
	 */
	protected List<ButtonEvents> events = new ArrayList<ButtonEvents>();
	
	public Button(String text)
	{
		this.text = text;
	}
	
	public Button()
	{
		
	}

	public Button(int x, int y, int width, int height, String text)
	{
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	@Override
	public void init(MainApplication game)
	{
	}
	
	@Override
	public void tick()
	{
		if (isVisible() && enabled_)
		{
			boolean isHovered = isCursorInComponent(x, y, width, height);
			
			if (isHovered)
			{
				if (getMouseHandler().isMouseHolded())
				{
					for (ButtonEvents e : events)
					{
						e.hold();
					}
				}
				
				onMouseClicked(b ->
				{
					for (ButtonEvents e : events)
					{
						e.click();
					}
				});
				hovered_ = true;
				return;
			} else
			{
				hovered_ = false;
			}
		}
	}
	
	@Override
	public boolean isFocused()
	{
		return isHovered();
	}
	
	public void doClick()
	{
		for (ButtonEvents e : events)
		{
			e.click();
		}
	}

	public void addEvent(ButtonEvents e)
	{
		events.add(e);
	}

	@Override
	public void render(Screen screen)
	{
//		if (debug)
//		{
//			screen.fillRect(x, y, width, height, 0xffff22ff);
//			screen.fillRect(x, y, 1, height, 0xffffff22);
//		}

		if (enabled_ && !hovered_)
		{
			if (enabled != null)
			{
				screen.drawSprite(x + image_offset_x, y + image_offset_y, enabled);
			} else
			{
				RenderHelper.renderDoubleBorderComponent(screen, this, RenderHelper.BUTTON_ENABLED_COLORS[0], RenderHelper.BUTTON_ENABLED_COLORS[1],
						RenderHelper.BUTTON_ENABLED_COLORS[2]);
			}
		}

		if (!enabled_)
		{
			if (disabled != null)
			{
				screen.drawSprite(x + image_offset_x, y + image_offset_y, disabled);
			} else
			{
				RenderHelper.renderDoubleBorderComponent(screen, this, RenderHelper.BUTTON_DISABLED_COLORS[0], RenderHelper.BUTTON_DISABLED_COLORS[1],
						RenderHelper.BUTTON_DISABLED_COLORS[2]);
			}
		}

		if (enabled_ && hovered_)
		{
			if (hovered != null)
			{
				screen.drawSprite(x + image_offset_x, y + image_offset_y, hovered);
			} else
			{
				RenderHelper.renderDoubleBorderComponent(screen, this, RenderHelper.BUTTON_HOVERED_COLORS[0], RenderHelper.BUTTON_HOVERED_COLORS[1],
						RenderHelper.BUTTON_HOVERED_COLORS[2]);
			}
		}
		

		if (renderFont)
		{
			if (text != null)
			{
				getFont().render(text, x + (width / 2) - ((text.length() * (8 * fontSize)) / 2), y + (height / 2) - 3, fontSize, red, green, blue);
			}
		}
	}
	
	/*
	 * Operators
	 */

	public void enable()
	{
		this.enabled_ = true;
	}

	public void disable()
	{
		this.enabled_ = false;
	}
	
	public void removeEvents()
	{
		events.clear();
	}
	
	/*
	 * Setters
	 */

	public void setText(String text)
	{
		this.text = text;
	}

	public void setEnabled(boolean b)
	{
		this.enabled_ = b;
	}

	public void setSize(int w, int h)
	{
		this.width = w;
		this.height = h;
	}

	public void setImageOffset(int x, int y)
	{
		this.image_offset_x = x;
		this.image_offset_y = y;
	}

	public void setHoveredImage(Sprite image)
	{
		hovered = image;
	}

	public void setEnabledImage(Sprite image)
	{
		enabled = image;
	}

	public void setDisabledImage(Sprite image)
	{
		disabled = image;
	}

	public void setFontSize(int s)
	{
		fontSize = Math.max(1, s);
	}
	
	public void setFontColor(int color)
	{
		float[] colors = Screen.getColors(color);
		red = colors[0];
		green = colors[1];
		blue = colors[2];
	}
	
	public void setFontColor(float red, float green, float blue)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
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
		return enabled_;
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
		return hovered_;
	}
}
