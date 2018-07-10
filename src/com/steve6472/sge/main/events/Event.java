package com.steve6472.sge.main.events;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
public @interface Event
{

}
