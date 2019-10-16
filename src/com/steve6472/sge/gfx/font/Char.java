package com.steve6472.sge.gfx.font;

public class Char
{
	private int index;
	private char character;
	private int width;
	
	public Char(int index, char character, int width)
	{
		this.index = index;
		this.character = character;
		this.width = width;
	}
	
	public char getCharacter()
	{
		return character;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public int getWidth()
	{
		return width;
	}
}