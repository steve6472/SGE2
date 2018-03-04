package com.steve6472.sge.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.MouseHandler;

public abstract class Gui implements Serializable
{
	private static final long serialVersionUID = 8547775087992332609L;
	private List<Component> components = new ArrayList<Component>();
	private boolean isVisible = true;
	protected final MainApplication mainApp;
	protected final Font font;
	/**
	 * If true it will render components last
	 */
	private boolean switchedRender = false;

	public Gui(MainApplication mainApp)
	{
		this.mainApp = mainApp;
		mainApp.addGui(this);
		font = mainApp.getFont();
		hideGui();
		createGui();
	}
	
	public abstract void createGui();

	public abstract void guiTick();

	public abstract void render(Screen screen);

	public void showEvent() {};

	public void hideEvent() {};
	
	public void switchRender()
	{
		switchedRender = !switchedRender;
	}
	
	protected void renderComponents(Screen screen)
	{
		try
		{
			if (isVisible())
			{
				for (Component gc : components)
				{
					gc.components.forEach((c) ->
					{
						if (c.isVisible())
						{
							c.render(screen);
						}
							
					});
					if (gc.isVisible())
					{
						gc.render(screen);
					}
				}
			}
		} catch (ConcurrentModificationException ex)
		{
			
		}
	}

	public final boolean isCursorInComponent(MouseHandler m, int x, int y, int w, int h)
	{
		if (!isVisible())
			return false;
		return ( m.getMouseX() >= x && m.getMouseX() <= w + x)   // check if X is within range
				   && ( m.getMouseY() >= y && m.getMouseY() <= h + y);
	}

	public final void setVisible(boolean b)
	{
		this.isVisible = b;
	}

	/**
	 * Opens & recreates the GUI (Best for debugging)
	 * Cleares components list!
	 * 
	 * @param game
	 * @param gui
	 *            Class of the GUI (I suppose that you don't have 2 same guis
	 *            but if so it will open the last registrated);
	 */
	public static final void openGui(MainApplication game, Class<? extends Gui> gui)
	{
		int index = 0;
		int i = 0;
		for (Gui g : game.getGuis())
		{
			if (g.getClass().getName().equals(gui.getName()))
			{
				index = i;
			}
			i++;
		}
		// Removing KeyListener from any IFocusables (Without this code the textfield
		// would have more & more keyListeners resulting in typing multiple keys
		// at once)
//		for (Component c : game.guis.get(index).components)
//		{
//			if (c instanceof IFocusable)
//			{
//				for (Iterator<KeyListener> li = game.getKeyHandler().getListeners().iterator(); li.hasNext();)
//				{
//					KeyListener l = li.next();
//					if (l.ifocusable == c)
//					{
//						li.remove();
//					}
//				}
//			}
//		}
		game.getGuis().get(index).components.clear();
		game.getGuis().get(index).createGui();
		game.getGuis().get(index).showGui();
	}
	
	/**
	 * Calls hideAllComponents();
	 */
	public void hideGui()
	{
		isVisible = false;
		for (Component gc : components)
		{
			gc.hide();
		}
		
		hideEvent();
	}
	
	public void showGui()
	{
		isVisible = true;
		for (Component gc : components)
		{
			gc.show();
		}
		
		showEvent();
	}
	
	public boolean isVisible()
	{
		return isVisible;
	}

	public void renderGui(Screen screen)
	{
		if (isVisible())
		{
			if (switchedRender)
			{
				render(screen);
				renderComponents(screen);
			} else
			{
				renderComponents(screen);
				render(screen);
			}
		}
	}

	public void tick()
	{
		if (isVisible())
		{
			tickComponents();
			guiTick();
		}
	}

	public void tickComponents()
	{
		if (isVisible())
		{
			for (Iterator<Component> co = components.iterator(); co.hasNext();)
			{
				Component c = co.next();
				if (c.isVisible())
				{
					c.tick();
				}
			}
		}
	}

	public void addComponent(Component component)
	{
		if (component == null)
			throw new NullPointerException("Component can't be null");
		component.parentGui = this;
		component.preInit(mainApp);
		component.init(mainApp);
		components.add(component);
	}

	public void removeComponent(Component component)
	{
		components.remove(component);
	}
	
	public void removeComponent(int index)
	{
		components.remove(index);
	}
	
	public int getComponentCount()
	{
		return components.size();
	}
	
	public void removeAllComponents()
	{
		components.clear();
	}
	
	public Component getComponent(int index)
	{
		return components.get(index);
	}
	
	public MainApplication getMainApp()
	{
		return mainApp;
	}
}
