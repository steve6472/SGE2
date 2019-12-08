package com.steve6472.sge.main;

@FunctionalInterface
public interface TriConsumer<A, B, C>
{
	void apply(A x, B y, C z);
}