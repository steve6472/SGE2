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
	public EntryType type;
	public int id;
	
	public KeyEntry(int defaultKey, String name, EntryType type)
	{
		this.key = defaultKey;
		this.defaultKey = defaultKey;
		this.name = name;
		this.type = type;
	}
}

enum EntryType
{
	KEYBOARD, MOUSE, JOYSTICK_BUTTON, JOYSTICK_AXIS
}
