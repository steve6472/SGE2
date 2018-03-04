/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 3. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main;

@FunctionalInterface
public interface KeyCallback
{
	public abstract void invoke(int key, int scancode, int action, int mods);
}
