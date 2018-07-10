/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 9. 7. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test.keybind;

public class KeyEntry
{
	public int key;
	public int defaultKey;
	public String name;
	
	public KeyEntry(int defaultKey, String name)
	{
		this.key = defaultKey;
		this.defaultKey = defaultKey;
		this.name = name;
	}

}
