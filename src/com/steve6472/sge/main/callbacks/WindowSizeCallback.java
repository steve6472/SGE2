/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 20. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.callbacks;

@FunctionalInterface
public interface WindowSizeCallback
{
  public abstract void invoke(int width, int height);
}
