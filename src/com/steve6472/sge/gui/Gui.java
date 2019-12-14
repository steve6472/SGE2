package com.steve6472.sge.gui;

import com.steve6472.sge.gui.components.context.ContextMenu;
import com.steve6472.sge.gui.components.dialog.Dialog;
import com.steve6472.sge.main.KeyHandler;
import com.steve6472.sge.main.MainApp;
import com.steve6472.sge.main.MouseHandler;
import com.steve6472.sge.main.events.AbstractEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Gui implements Serializable
{
	private List<Component> components = new ArrayList<>();
	private boolean isVisible = true;
	protected final MainApp mainApp;
	/**
	 * If true it will render components last
	 */
	private boolean switchedRender = false;
	private boolean canHide;

	private HashMap<String, ContextMenu> contextMenuMap;

	/* Context Menu */
	protected ContextMenu currentContextMenu;

	public Gui(MainApp mainApp)
	{
		this.mainApp = mainApp;
		contextMenuMap = new HashMap<>();
		mainApp.addGui(this);
		hideGui();
		canHide = true;
		createGui();
	}
	
	public abstract void createGui();

	public abstract void guiTick();

	public abstract void render();

	public void showEvent() {}

	public void hideEvent() {}
	
	public void switchRender()
	{
		switchedRender = !switchedRender;
	}
	
	protected void renderComponents()
	{
		// If GUI is visible render components
		if (isVisible())
		{
			components.forEach(Component::fullRender);
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
		showEvent();
	}

	/**
	 * Calls hideAllComponents();
	 */
	public void hideGui()
	{
		isVisible = false;
//		components.forEach(c -> c.setVisible(false));
		
		if (canHide)
			hideEvent();
	}
	
	public void showGui()
	{
		isVisible = true;
//		components.forEach(c -> c.setVisible(true));
		
		showEvent();
	}
	
	public boolean isVisible()
	{
		return isVisible;
	}

	public void tick()
	{
		if (isVisible())
		{
			boolean flag = false;
			if (currentContextMenu != null)
			{
				if (flag = currentContextMenu.spoofCursor)
				{
					spoofTick(currentContextMenu.mx, currentContextMenu.my);
				}
				currentContextMenu.fullTick();
			}

			for (Dialog c : mainApp.getDialogContainer().dialogs)
			{
				if (c.freezeGui())
				{
					spoofTick(-1, -1);
					flag = true;
				}
			}

			if (!flag)
			{
				tickComponents();
				guiTick();
			}
		}
	}

	private void spoofTick(int x, int y)
	{
		getMouseHandler().spoof(x, y);

		tickComponents();
		guiTick();

		getMouseHandler().tick();
	}

	public void renderGui()
	{
		if (isVisible())
		{
			boolean flag = false;
			if (currentContextMenu != null)
			{
				if (flag = currentContextMenu.spoofCursor)
				{
					spoofRender(currentContextMenu.mx, currentContextMenu.my);
				}
			}

			for (Dialog c : mainApp.getDialogContainer().dialogs)
			{
				if (c.freezeGui())
				{
					spoofRender(-1, -1);
					flag = true;
				}
			}

			if (!flag)
			{
				if (switchedRender)
				{
					render();
					renderComponents();
				} else
				{
					renderComponents();
					render();
				}
			}

			if (currentContextMenu != null)
				currentContextMenu.fullRender();
		}
	}

	private void spoofRender(int x, int y)
	{
		getMouseHandler().spoof(x, y);

		if (switchedRender)
		{
			render();
			renderComponents();
		} else
		{
			renderComponents();
			render();
		}

		getMouseHandler().tick();
	}

	public void tickComponents()
	{
		for (Component cg : components)
		{
//			System.out.println("GUI: " + cg.getClass().getSimpleName() + " " + cg.isVisible());
			if (cg.isVisible())
			{
				cg.fullTick();
			}
		}
	}

	public void addComponent(Component component)
	{
		if (component == null)
			throw new NullPointerException("Component can't be null");

		if (component instanceof ContextMenu)
			throw new IllegalArgumentException("Can not add ContextMenu to normal component list. Please use setContextMenu method instead!");
		
		component.parentGui = this;
		component.preInit(mainApp);
		component.init(mainApp);
		getMainApp().getEventHandler().register(component);
		components.add(component);

		/* Reposition Components Children */
		component.setLocation(component.getX(), component.getY());
	}

	public void addContextMenu(ContextMenu contextMenu)
	{
		if (contextMenuMap.containsKey(contextMenu.getId()))
			throw new IllegalArgumentException("Duplicate ContextMenu id:" + contextMenu.getId());

		contextMenu.parentGui = this;
		contextMenu.preInit(mainApp);
		contextMenu.init(mainApp);
		getMainApp().getEventHandler().register(contextMenu);

		contextMenuMap.put(contextMenu.getId(), contextMenu);
	}

	public void setContextMenu(String id)
	{
		this.currentContextMenu = contextMenuMap.get(id);
	}

	public ContextMenu getCurrentContextMenu()
	{
		return currentContextMenu;
	}

	public void removeComponent(Component component)
	{
		components.remove(component);
	}
	
	public void removeComponent(int index)
	{
		components.remove(index);
	}

	public List<Component> getComponents()
	{
		return components;
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
	
	public MainApp getMainApp()
	{
		return mainApp;
	}
	
	public MouseHandler getMouseHandler()
	{
		return getMainApp().getMouseHandler();
	}
	
	public KeyHandler getKeyHandler()
	{
		return getMainApp().getKeyHandler();
	}

	public void runEvent(AbstractEvent event)
	{
		getMainApp().runEvent(event);
	}
}
