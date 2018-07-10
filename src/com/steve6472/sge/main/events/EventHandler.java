/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 9. 7. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

public class EventHandler
{
	private HashMap<Class<?>, HashMap<Object, ArrayList<Method>>> events;
	
	public EventHandler()
	{
		events = new HashMap<Class<?>, HashMap<Object, ArrayList<Method>>>();
	}
	
	public void runTest()
	{
		for (Class<?> clazz : events.keySet())
		{
			System.out.println("--Event: " + clazz.getSimpleName());
			
			HashMap<Object, ArrayList<Method>> m = events.get(clazz);
			
			for (Object o : m.keySet())
			{
				System.out.println("----Object: " + o.getClass().getSimpleName() + " / " + o.hashCode());
				ArrayList<Method> a = m.get(o);
				for (Method me : a)
				{
					System.out.println("--------Method: " + me.getName());
				}
			}
		}
	}
	
	public void register(Object object)
	{
		for (Method method : object.getClass().getMethods())
		{
			if (!Modifier.isStatic(method.getModifiers()))
			{
				if (method.isAnnotationPresent(Event.class))
				{
					Class<?>[] parameterTypes = method.getParameterTypes();
					if (parameterTypes.length != 1)
					{
						throw new IllegalArgumentException("Method " + method + " has wrong number of parameters. Allowed is only one!");
					}
					
					Class<?> eventType = parameterTypes[0];
					
					if (!AbstractEvent.class.isAssignableFrom(eventType))
					{
						throw new IllegalArgumentException("Parameter in method " + method + " is not Event!");
					}
					
					register(eventType, object, method);
				}
			}
		}
	}
	
//	public void unregister(Object object)
//	{
//	}
	
	private void register(Class<?> eventType, Object owner, Method method)
	{
		HashMap<Object, ArrayList<Method>> map = events.get(eventType);
		if (map == null)
		{
//			System.out.println("Event is null, expected: " + eventType.getSimpleName());
			HashMap<Object, ArrayList<Method>> newMap = new HashMap<Object, ArrayList<Method>>();
			ArrayList<Method> newMethods = new ArrayList<Method>();
			newMethods.add(method);
			newMap.put(owner, newMethods);
			events.put(eventType, newMap);
		} else
		{
//			System.out.println("Event " + eventType.getSimpleName() + " found");
			ArrayList<Method> methods = map.get(owner);
			if (methods == null)
			{
//				System.out.println("Owner is null, expected " + owner.getClass().getSimpleName() + " / " + owner.hashCode());
				ArrayList<Method> newMethods = new ArrayList<Method>();
				newMethods.add(method);
				map.put(owner, newMethods);
			} else
			{
//				System.out.println("Owner found");
				methods.add(method);
			}
		}
//		System.out.println("-----------");
	}
	
	public void runEvent(AbstractEvent event)
	{
		HashMap<Object, ArrayList<Method>> e = events.get(event.getClass());
		if (e == null)
			return;
		for (Object o : e.keySet())
		{
			ArrayList<Method> methods = e.get(o);
			if (methods == null)
				return;
			for (Method m : methods)
			{
				try
				{
					m.invoke(o, event);
				} catch (IllegalAccessException e1)
				{
					e1.printStackTrace();
				} catch (IllegalArgumentException e1)
				{
					e1.printStackTrace();
				} catch (InvocationTargetException e1)
				{
//					e1.printStackTrace();
					System.err.println("java.lang.reflect.InvocationTargetException : " + e1.getCause().toString());
//					System.err.println();
					for (StackTraceElement s : e1.getCause().getStackTrace())
					{
						if (s.toString().equals("sun.reflect.GeneratedMethodAccessor1.invoke(Unknown Source)"))
						{
							System.err.println("\t" + "... Called by EventHandler");
							break;
						}
						System.err.println("\t" + s.toString());
					}
				}
			}
		}
	}
}
