package steve6472.sge.gui;

import steve6472.sge.main.*;
import steve6472.sge.main.events.AbstractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class Component
{
	private boolean isVisible = true;
	protected int x, y, width, height, relx, rely;
	private List<Component> components = new ArrayList<>();
	protected Gui parentGui;
	protected Component parentComponent;

	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	private MainApp main;
	
	public final void preInit(MainApp main)
	{
		this.main = main;
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

	protected boolean isCursorInComponent(int x, int y, int w, int h)
	{
		if (!isVisible())
			return false;
		return (mouseHandler.getMouseX() >= x && mouseHandler.getMouseX() <= w + x) && (mouseHandler.getMouseY() >= y && mouseHandler.getMouseY() <= h + y);
	}

	protected boolean isCursorInComponent()
	{
		if (!isVisible())
			return false;
		return (mouseHandler.getMouseX() >= getX() && mouseHandler.getMouseX() <= getWidth() + getX())
				&& (mouseHandler.getMouseY() >= getY() && mouseHandler.getMouseY() <= getHeight() + getY());
	}

	private boolean flag;

	protected final void onMouseClicked(int button, final Consumer<Component> action)
	{
		Objects.requireNonNull(action);
		if (isCursorInComponent())
		{
			if (!flag)
			{
				if (getMain().getMouseHandler().getButton() == button)
				{
					flag = true;
				}
			} else
			{
				if (getMain().getMouseHandler().getButton() != button)
				{
					flag = false;
					action.accept(this);
				}
			}
		} else
		{
			flag = false;
		}
	}

	protected final void onMouseClicked(int button, final Procedure action)
	{
		onMouseClicked(isCursorInComponent(), button, action);
	}

	protected final void onMouseClicked(boolean canClick, int button, final Procedure action)
	{
		Objects.requireNonNull(action);
		if (canClick)
		{
			if (!flag)
			{
				if (getMain().getMouseHandler().getButton() == button)
				{
					flag = true;
				}
			} else
			{
				if (getMain().getMouseHandler().getButton() != button)
				{
					flag = false;
					action.process();
				}
			}
		} else
		{
			flag = false;
		}
	}

	/**
	 * Runs if mouse is pressed over the component
	 * @param action - Lambda expresion
	 */
	protected final void onMousePressed(int button, final Consumer<Component> action)
	{
		Objects.requireNonNull(action);
		if (getButton() == button)
		{
			action.accept(this);
		}
	}
	
	/*
	 * RIP ToolTip tick method
	 * You will prob. never work ever again
	 *
	 * Update: It'overlays 14.12.2018 and I did not work on it
	 * Update: It'overlays 10.02.2019 and I still did not work on it
	 */
	
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
		if (!isVisible())
			return;
		
		render();
		renderComponents();
	}
	
	public final void fullTick()
	{
		if (!isVisible())
			return;
		
		tick();
		tickComponents();
	}

	/*
	 * Operators
	 */
	
	public void toggleVisibility()
	{
		this.isVisible = !isVisible;
	}
	
	protected void addComponent(Component component)
	{
		if (component == null)
			throw new NullPointerException("Component can't be null");
		
		component.parentComponent = this;
		component.preInit(main);
		component.init(main);
		getMain().getEventHandler().register(component);
		components.add(component);

		/* Reposition Components Children */
		component.setLocation(component.getX(), component.getY());
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

	public void setVisible(boolean b)
	{
		this.isVisible = b;
		
//		components.forEach(c -> c.setVisible(b));
	}

	public void setChildVisibility(boolean b)
	{
		components.forEach(c -> c.setVisible(b));
	}
	
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		for (Component gc : components)
		{
			gc.setLocation(gc.relx + getX(), gc.rely + getY());
		}
	}

	public void setRelativeLocation(int x, int y)
	{
		this.relx = x;
		this.rely = y;
	}
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		if (width < getMinWidth())
			this.width = getMinWidth();

		if (height < getMinHeight())
			this.height = getMinHeight();
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

	public int getRelX() { return relx; }
	public int getRelY() { return rely; }
	
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

	protected boolean isKeyPressed(int key)
	{
		return main.isKeyPressed(key);
	}

	public MouseHandler getMouseHandler()
	{
		return mouseHandler;
	}

	protected int getMouseX() { return getMouseHandler().getMouseX(); }
	protected int getMouseY() { return getMouseHandler().getMouseY(); }
	protected int getButton() { return getMouseHandler().getButton(); }
	protected boolean isLMBHolded() { return getButton() == KeyList.LMB; }
	protected boolean isMMBHolded() { return getButton() == KeyList.MMB; }
	protected boolean isRMBHolded() { return getButton() == KeyList.RMB; }
	
	public Gui getParentGui()
	{
		return parentGui;
	}
	
	public MainApp getMain()
	{
		return main;
	}

	public void runEvent(AbstractEvent event)
	{
		getMain().runEvent(event);
	}
}
