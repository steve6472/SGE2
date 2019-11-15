package com.steve6472.sge.main.util;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 15.11.2019
 * Project: SJP
 *
 ***********************/
public class Pair<A, B>
{
	private final A a;
	private final B b;

	public Pair(A a, B b)
	{
		this.a = a;
		this.b = b;
	}

	public A getA()
	{
		return a;
	}

	public B getB()
	{
		return b;
	}
}
