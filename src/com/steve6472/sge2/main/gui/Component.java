package com.steve6472.sge2.main.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge2.main.KeyHandler;
import com.steve6472.sge2.main.MainApplication;
import com.steve6472.sge2.main.MouseHandler;
import com.steve6472.sge2.main.gfx.Font;
import com.steve6472.sge2.main.gfx.Screen;

public abstract class Component extends LambdaControl implements Serializable
{

	private static final long serialVersionUID = 62938822794527605L;
	private boolean isVisible = true;
	protected int x, y, width, height;
	List<Component> components = new ArrayList<Component>();
	protected Gui parentGui;
	protected Component parentComponent;

	private Font font;
	private Screen screen;
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	private MainApplication game;
	
	public final void preInit(MainApplication game)
	{
		initLambdaControl(this);
		this.game = game;
		if (game != null)
		{
			this.font = game.getFont();
			this.screen = game.getScreen();
			this.keyHandler = game.getKeyHandler();
			this.mouseHandler = game.getMouseHandler();
		} else
		{
			this.font = null;
			this.screen = null;
			this.keyHandler = null;
			this.mouseHandler = null;
		}
	}

	/*
	 * Abstract methods
	 */
	
	public abstract void init(MainApplication game);
	
	public abstract void render(Screen screen);
	
	public abstract void tick();

	public boolean isCursorInComponent(int x, int y, int w, int h)
	{
		if (!isVisible())
			return false;
		return (mouseHandler.getMouseX() >= x && mouseHandler.getMouseX() <= w + x) && (mouseHandler.getMouseY() >= y && mouseHandler.getMouseY() <= h + y);
	}

	public boolean isCursorInComponent()
	{
		if (!isVisible())
			return false;
		return (mouseHandler.getMouseX() >= getX() && mouseHandler.getMouseX() <= getWidth() + getX())
				&& (mouseHandler.getMouseY() >= getY() && mouseHandler.getMouseY() <= getHeight() + getY());
	}
	
	/*
	 * RIP ToolTip tick method
	 * You will prob. never work ever again
	 */
	
	protected void renderComponents(Screen screen)
	{
		if (isVisible())
		{
			for (Component gc : components)
			{
				if (gc.isVisible())
				{
					gc.render(screen);
				}
			}
		}
	}

	/*
	 * Operators
	 */
	
	public void hide()
	{
		this.isVisible = false;
		hideAllComponents();
	}
	
	public void show()
	{
		this.isVisible = true;
		showAllComponents();
	}
	
	public void toggleVisibility()
	{
		this.isVisible = !isVisible;
	}
	
	protected void addComponent(Component component)
	{
		if (component == null)
			throw new NullPointerException("Component can't be null");
		component.parentComponent = this;
		component.preInit(game);
		component.init(game);
		components.add(component);
	}
	
	protected void removeComponent(Component component)
	{
		components.remove(component);
	}
	
	public void removeComponent(int index)
	{
		components.remove(index);
	}
	
	public Component getComponent(int index)
	{
		return components.get(index);
	}
	
	protected void hideAllComponents()
	{
		for (Component gc : components)
		{
			gc.hide();
		}
	}
	
	protected void showAllComponents()
	{
		for (Component gc : components)
		{
			gc.show();
		}
	}

	protected void tickComponents()
	{
		if (isVisible())
		{
			for (Component gc : components)
			{
				if (gc.isVisible())
				{
					gc.tick();
				}
			}
		}
	}

	/*
	 * Setters
	 */

	public void setVisible(boolean b)
	{
		this.isVisible = b;
		
		components.forEach((c) -> c.setVisible(b));
	}
	
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		for (Component gc : components)
		{
			gc.setLocation(x + gc.getX(), y + gc.getY());
		}
	}
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		if (width < getMinWidth())
			width = getMinWidth();

		if (height < getMinHeight())
			height = getMinHeight();
	}

	/*
	 * Getters
	 */

	public boolean isVisible()
	{
		return isVisible;
	}

	public int getHeight()
	{
		if (height < getMinHeight())
			height = getMinHeight();
		
		return height;
	}

	public int getWidth()
	{
		if (width < getMinWidth())
			width = getMinWidth();
		
		return width;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	protected int getMinWidth()
	{
		return 0;
	}
	
	protected int getMinHeight()
	{
		return 0;
	}
	
	public KeyHandler getKeyHandler()
	{
		return keyHandler;
	}
	
	public MouseHandler getMouseHandler()
	{
		return mouseHandler;
	}
	
	public Gui getParentGui()
	{
		return parentGui;
	}
	
	public Screen getScreen()
	{
		return screen;
	}
	
	public Font getFont()
	{
		return font;
	}
	
	public MainApplication getMainApp()
	{
		return game;
	}
}
