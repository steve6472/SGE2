package com.steve6472.sge.main.callbacks;

@FunctionalInterface
public interface MouseButtonCallback
{
	public abstract void invoke(int x, int y, int button, int action, int mods);
}