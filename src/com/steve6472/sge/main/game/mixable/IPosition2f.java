package com.steve6472.sge.main.game.mixable;

import org.joml.Vector2f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 2.1.2019
 * Project: SGE2
 *
 ***********************/
public interface IPosition2f
{
	Vector2f getPosition();

	default void setPosition(float x, float y)
	{
		getPosition().set(x, y);
	}

	default float getX()
	{
		return getPosition().x;
	}

	default float getY()
	{
		return getPosition().y;
	}

	default void setX(float x)
	{
		getPosition().x = x;
	}

	default void setY(float y)
	{
		getPosition().y = y;
	}
}
