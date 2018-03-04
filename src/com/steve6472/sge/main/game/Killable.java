package com.steve6472.sge.main.game;

public abstract class Killable
{
	private boolean isDead = false;
	
	public void setDead()
	{
		isDead = true;
	}
	
	public boolean isDead()
	{
		return isDead;
	}
}
