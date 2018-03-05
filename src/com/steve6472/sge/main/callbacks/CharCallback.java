/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 4. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.callbacks;

@FunctionalInterface
public interface CharCallback
{
	public abstract void invoke(int codePoint);
}
