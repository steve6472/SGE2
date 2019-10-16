package com.steve6472.sge.gui.components.dialog;

import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.context.ContextMenu;
import com.steve6472.sge.main.KeyHandler;
import com.steve6472.sge.main.MainApp;
import com.steve6472.sge.main.MouseHandler;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 17.03.2019
 * Project: SGE2
 *
 ***********************/
public abstract class Dialog
{
	private boolean closed = false;
	protected DialogContainer dialogContainer;

	public void close()
	{
		this.closed = true;

		main.getEventHandler().removeSpecialCaseObject(this);
		components.forEach(this::removeComponentSpecialCase);
	}

	private void removeComponentSpecialCase(Component c)
	{
		c.getComponents().forEach(this::removeComponentSpecialCase);
		main.getEventHandler().removeSpecialCaseObject(c);
		main.getEventHandler().unregister(c);
	}

	public boolean isClosed()
	{
		return closed;
	}




	protected int x, y, width, height;
	private List<Component> components = new ArrayList<>();

	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	private MainApp main;

	private ContextMenu contextMenu;

	public void setContextMenu(ContextMenu contextMenu)
	{
		if (contextMenu != null)
		{
			contextMenu.preInit(getMain());
			contextMenu.init(getMain());
			getMain().getEventHandler().register(contextMenu);
		}
		this.contextMenu = contextMenu;
	}

	public final void preInit(MainApp main)
	{
		this.main = main;
		dialogContainer = main.getDialogContainer();
		if (this.main != null)
		{
			this.keyHandler = this.main.getKeyHandler();
			this.mouseHandler = this.main.getMouseHandler();
		} else
		{
			this.keyHandler = null;
			this.mouseHandler = null;
		}
	}

	/*
	 * Abstract methods
	 */

	public abstract void init(MainApp main);

	public abstract void tick();

	public abstract void render();

	public abstract boolean freezeGui();

	public abstract boolean disableEvents();

	protected boolean isCursorInComponent()
	{
		return (mouseHandler.getMouseX() >= getX() && mouseHandler.getMouseX() <= getWidth() + getX())
				&& (mouseHandler.getMouseY() >= getY() && mouseHandler.getMouseY() <= getHeight() + getY());
	}

	protected void renderComponents()
	{
		for (Component gc : components)
		{
			if (gc.isVisible())
			{
				gc.fullRender();
			}
		}
	}

	public final void fullRender()
	{
		boolean flag = false;
		if (contextMenu != null)
		{
			if (flag = contextMenu.spoofCursor)
			{
				spoofRender(contextMenu.mx, contextMenu.my);
			}
		}

		if (!flag)
		{
			render();
			renderComponents();
		}

		if (contextMenu != null)
			contextMenu.fullRender();
	}

	private void spoofRender(int x, int y)
	{
		getMouseHandler().spoof(x, y);

		render();
		renderComponents();

		getMouseHandler().tick();
	}

	public final void fullTick()
	{
		boolean flag = false;
		if (contextMenu != null)
		{
			if (flag = contextMenu.spoofCursor)
			{
				spoofTick(contextMenu.mx, contextMenu.my);
			}
			contextMenu.fullTick();
		}

		if (!flag)
		{
			tickComponents();
			tick();
		}
//		tick();
//		tickComponents();
	}

	private void spoofTick(int x, int y)
	{
		getMouseHandler().spoof(x, y);

		tickComponents();
		tick();

		getMouseHandler().tick();
	}

	public Dialog center()
	{
		setLocation(main.getWidth() / 2 - width / 2, main.getHeight() / 2 - height / 2);
		return this;
	}

	/*
	 * Operators
	 */

	protected void addComponent(Component component)
	{
		if (component == null)
			throw new NullPointerException("Component can't be null");

		component.preInit(main);
		component.init(main);
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

	public List<Component> getComponents()
	{
		return components;
	}

	protected void hideAllComponents()
	{
		components.forEach(c -> c.setVisible(false));
	}

	protected void showAllComponents()
	{
		components.forEach(c -> c.setVisible(true));
	}

	protected void tickComponents()
	{
		for (Component gc : components)
		{
			//			System.out.println("COM: " + gc.getClass().getSimpleName() + " " + gc.isVisible());
			if (gc.isVisible())
			{
				gc.fullTick();
			}
		}
	}

	/*
	 * Setters
	 */

	public void setChildVisibility(boolean b)
	{
		components.forEach(c -> c.setVisible(b));
	}

	public Dialog setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;

		for (Component gc : components)
		{
			gc.setLocation(gc.getRelX() + getX(),  gc.getRelY() + getY());
		}
		return this;
	}

	public Dialog setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		return this;
	}

	/*
	 * Getters
	 */

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
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

	public KeyHandler getKeyHandler()
	{
		return keyHandler;
	}

	public MouseHandler getMouseHandler()
	{
		return mouseHandler;
	}

	public MainApp getMain()
	{
		return main;
	}
}
