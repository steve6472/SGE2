/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 9. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.gui;

import java.util.Objects;
import java.util.function.Consumer;

public class LambdaControl
{
	Component c;
	
	private boolean holdIt = false;
	
	public void initLambdaControl(Component com)
	{
		this.c = com;
	}

	/**
	 * Runs if mouse clicked (runs only once!)
	 * @param action - Lambda expresion
	 */
	protected final void onMouseClicked(final Consumer<Component> action)
	{
		Objects.requireNonNull(action);
		if (c.isCursorInComponent())
		{
			boolean holded = false;
			if (c.getMouseHandler().isMouseHolded())
			{
				holdIt = true;
			} else
			{
				if (holdIt)
				{
					holdIt = false;
					holded = true;
				}
			}
			if (holded)
			{
				action.accept(c);
				holdIt = false;
			}
		}
	}

	/**
	 * Runs if mouse clicked (runs only once!)
	 * @param action - Lambda expresion
	 */
	protected final void onMouseClicked(final boolean canRun, final Consumer<Component> action)
	{
		Objects.requireNonNull(action);
		if (canRun)
		{
			boolean holded = false;
			if (c.getMouseHandler().isMouseHolded())
			{
				holdIt = true;
			} else
			{
				if (holdIt)
				{
					holdIt = false;
					holded = true;
				}
			}
			if (holded)
			{
				action.accept(c);
				holdIt = false;
			}
		}
	}

	/**
	 * Runs if mouse is pressed over the component
	 * @param action - Lambda expresion
	 */
	protected final void onMousePressed(final Consumer<Component> action)
	{
		Objects.requireNonNull(action);
		if (c.isCursorInComponent())
		{
			if (c.getMouseHandler().isMouseHolded())
			{
				action.accept(c);
			}
		}
	}

	/**
	 * Runs if mouse is pressed over the component
	 * @param action - Lambda expresion
	 */
	protected final void onMousePressed( final boolean canRun, final Consumer<Component> action)
	{
		Objects.requireNonNull(action);
		if (canRun)
		{
			if (c.getMouseHandler().isMouseHolded())
			{
				action.accept(c);
			}
		}
	}


	/**
	 * If cursor is in component -> run
	 * @param key - Key from KeyHandler
	 * @param action - Lambda expresion
	 */
//	protected final void onKeyTyped(final Key key, final Consumer<Component> action)
//	{
//		Objects.requireNonNull(action);
//		if (c.isCursorInComponent())
//		{
//			if (key.isPressed() && !key.typed)
//			{
//				action.accept(c);
//				key.typed = true;
//			}
//		}
//	}


	/**
	 * If cursor is in component -> run
	 * @param key - Key from KeyHandler
	 * @param action - Lambda expresion
	 */
//	protected final void onKeyHold(final Key key, final Consumer<Component> action)
//	{
//		Objects.requireNonNull(action);
//		if (key.isPressed())
//		{
//			action.accept(c);
//			holdIt = false;
//		}
//	}

	/**
	 * If cursor is in component -> run
	 * @param key - Key from KeyHandler
	 * @param action - Lambda expresion
	 */
//	protected final void onKeyHoldWhenFocused(final Key key, final Consumer<Component> action)
//	{
//		Objects.requireNonNull(action);
//		
//		if (c.isCursorInComponent())
//		{
//			if (key.isPressed())
//			{
//				action.accept(c);
//				holdIt = false;
//			}
//		}
//	}
}
