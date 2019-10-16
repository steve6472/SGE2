/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 11. 7. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main;

import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;

public class KeyNamer implements KeyList
{
	public static void main(String[] args)
	{
		KeyNamer namer = new KeyNamer();
		try
		{
			namer.generate();
		} catch (IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
	
	private void generate() throws IllegalArgumentException, IllegalAccessException
	{
		Class<?> clazz = KeyList.class;
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields)
		{
			System.out.println(f.getName() + "(KeyList." + f.getName() + ", \"" + f.getName() + "\"),");
		}
	}
	
	public static String getName(int key)
	{
		for (Key k : Key.values())
		{
			if (k.key == key)
			{
				return k.name;
			}
		}
		return "null";
	}
	
	enum Key
	{
		A(KeyList.A, "A"),
		B(KeyList.B, "B"),
		C(KeyList.C, "C"),
		D(KeyList.D, "D"),
		E(KeyList.E, "E"),
		F(KeyList.F, "F"),
		G(KeyList.G, "G"),
		H(KeyList.H, "H"),
		I(KeyList.I, "I"),
		J(KeyList.J, "J"),
		K(KeyList.K, "K"),
		L(KeyList.L, "L"),
		M(KeyList.M, "M"),
		N(KeyList.N, "N"),
		O(KeyList.O, "O"),
		P(KeyList.P, "P"),
		Q(KeyList.Q, "Q"),
		R(KeyList.R, "R"),
		S(KeyList.S, "S"),
		T(KeyList.T, "T"),
		U(KeyList.U, "U"),
		V(KeyList.V, "V"),
		W(KeyList.W, "W"),
		X(KeyList.X, "X"),
		Y(KeyList.Y, "Y"),
		Z(KeyList.Z, "Z"),
		
		SPACE(KeyList.SPACE, "Space"),
		
		KP_0(KeyList.KP_0, "0"),
		KP_1(KeyList.KP_1, "1"),
		KP_2(KeyList.KP_2, "2"),
		KP_3(KeyList.KP_3, "3"),
		KP_4(KeyList.KP_4, "4"),
		KP_5(KeyList.KP_5, "5"),
		KP_6(KeyList.KP_6, "6"),
		KP_7(KeyList.KP_7, "7"),
		KP_8(KeyList.KP_8, "8"),
		KP_9(KeyList.KP_9, "9"),
		
		_0(KeyList._0, "0"),
		_1(KeyList._1, "1"),
		_2(KeyList._2, "2"),
		_3(KeyList._3, "3"),
		_4(KeyList._4, "4"),
		_5(KeyList._5, "5"),
		_6(KeyList._6, "6"),
		_7(KeyList._7, "7"),
		_8(KeyList._8, "8"),
		_9(KeyList._9, "9"),
		
		L_SHIFT(KeyList.L_SHIFT, "Left Shift"),
		R_SHIFT(KeyList.R_SHIFT, "Right Shift"),
		
		L_CONTROL(KeyList.L_CONTROL, "Left Control"),
		R_CONTROL(KeyList.R_CONTROL, "Right Control"),
		
		L_ALT(KeyList.L_ALT, "Left Alt"),
		R_ALT(KeyList.R_ALT, "Right Alt"),
		
		BACKSPACE(KeyList.BACKSPACE, "Backspace"),
		ENTER(KeyList.ENTER, "Enter"),
		
		LEFT(KeyList.LEFT, "Left"),
		RIGHT(KeyList.RIGHT, "Right"),
		UP(KeyList.UP, "Up"),
		DOWN(KeyList.DOWN, "Down"),
		
		TAB(KeyList.TAB, "Tab"),
		
		
		
		
		
		LMB(GLFW.GLFW_MOUSE_BUTTON_1, "Left Mouse Button"),
		RMB(GLFW.GLFW_MOUSE_BUTTON_2, "Right Mouse Button"),
		MMB(GLFW.GLFW_MOUSE_BUTTON_3, "Middle Mouse Button")
		
		;
		
		int key;
		String name;
		
		Key(int key, String name)
		{
			this.key = key;
			this.name = name;
		}
	}
}
