package com.steve6472.sge.gui.components;

import static org.lwjgl.glfw.GLFW.*;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApplication;

/**
 * Whole window moving method was copied from https://stackoverflow.com/questions/33052651/glfw-how-to-drag-undecorated-window
 * Thank you very much!
 *
 */
public class DragFrame extends Component
{
	private static final long serialVersionUID = -89023782322619120L;
	protected String text;
	protected boolean renderTextInCenter = true;
	public int textSize = 2;
	
	protected boolean canDrag = true;
	
	private int cp_x;
	private int cp_y;
	private int offset_cpx;
	private int offset_cpy;
	private int w_posx;
	private int w_posy;
	private int buttonEvent;

	private boolean render = true;
	
	@Override
	public void init(MainApplication game)
	{
		getMouseHandler().addCursorPosCallback((x, y) ->
		{
			if (buttonEvent == 1)
			{
				offset_cpx = (int) (x - cp_x);
				offset_cpy = (int) (y - cp_y);
			}
		});

		getMouseHandler().addMouseButtonCallback((x, y, button, action, mods) ->
		{
			if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS)
			{
				buttonEvent = 1;
				cp_x = getMouseHandler().getMouseX();
				cp_y = getMouseHandler().getMouseY();
			}
			
			if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE)
			{
				buttonEvent = 0;
				cp_x = 0;
				cp_y = 0;
			}
		});
	}

	@Override
	public void render(Screen screen)
	{
		if (!render)
			return;
		
		screen.drawRect(x, y, width, height, 2, 0xff7f7f7f);
		screen.fillRect(x + 2, y + 2, width - 4, height - 4, 0xff000000);
		if (text != null && text != "")
		{
			if (renderTextInCenter)
				getFont().render(getText(), getX() + getWidth() / 2 - (getText().length() * (8 * textSize)) / 2,
						getHeight() / 2 - (8 * textSize) / 2 + 3, textSize);
			else
				getFont().render(getText(), getX(), getY() + getHeight() / 2 - (8 * textSize) / 2 + 3, textSize);

		}
	}
	
	boolean dragging = false;
	
	@Override
	public void tick()
	{
		if (!canDrag)
			return;
		
		if (isCursorInComponent() && buttonEvent == 1)
		{
			dragging = true;
		}
		
		if (dragging && buttonEvent != 1)
		{
			dragging = false;
		}
		
		if (dragging)
		{
			w_posx = getMainApp().getWindowX();
			w_posy = getMainApp().getWindowY();
			glfwSetWindowPos(getMainApp().getWindowId(), w_posx + offset_cpx, w_posy + offset_cpy);
			offset_cpx = 0;
			offset_cpy = 0;
			cp_x += offset_cpx;
			cp_y += offset_cpy;
		}
	}

	protected int getMinHeight() { return 2; }
	
	protected int getMinWidth() { return 2; }
	
	public void setFontSize(int textSize)
	{
		this.textSize = textSize;
	}

	public void setText(String text)
	{
		if (text == null)
			return;
		
		if (!text.equals(this.getText()))
		{
			this.text = text;
		}
	}

	public void renderTextInCenter(boolean renderTextInCenter)
	{
		this.renderTextInCenter = renderTextInCenter;
	}
	
	public boolean isTextRenderedInCenter() { return renderTextInCenter; }
	
	public String getText() { return text; }

	public void setCanDrag(boolean c)
	{
		canDrag = c;
	}
	
	public boolean isCanDrag()
	{
		return canDrag;
	}
	
	public void setRender(boolean render)
	{
		this.render = render;
	}

}
