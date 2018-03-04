/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 3. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main;

@FunctionalInterface
public interface CursorPosCallback
{
	public abstract void invoke(double x, double y);
}
