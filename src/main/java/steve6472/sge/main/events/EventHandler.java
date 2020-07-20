/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 9. 7. 2018
* Project: SGE2
*
***********************/

package steve6472.sge.main.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class EventHandler
{
	private HashMap<Class<?>, LinkedHashMap<Object, ArrayList<Method>>> events;

	public List<Object> specialCaseObjects, forcedObjects;
	
	public EventHandler()
	{
		events = new HashMap<>();
		specialCaseObjects = new ArrayList<>();
		forcedObjects = new ArrayList<>();
	}

	/**
	 * Can be used for debugging
	 * First use was while creating the EventHandler
	 */
	public void runTest()
	{
		for (Class<?> clazz : events.keySet())
		{
			System.out.println("\n-- Event: " + clazz.getSimpleName());

			HashMap<Object, ArrayList<Method>> m = events.get(clazz);
			
			for (Object o : m.keySet())
			{
				System.out.println("---- Object: " + o.getClass().getSimpleName() + " / " + o.hashCode());
				ArrayList<Method> a = m.get(o);
				for (Method me : a)
				{
					if (specialCaseObjects.isEmpty())
					{
						System.out.println("-------- Method: " + me.getName());
					} else
					{
						for (Object c : specialCaseObjects)
						{
							if (c == o)
								System.out.println("-------- Special Method: " + me.getName());
						}
					}

					if (!forcedObjects.isEmpty() && !specialCaseObjects.isEmpty())
					{
						for (Object ob : forcedObjects)
						{
							if (ob == o)
								System.out.println("-------- Forced Method: " + me.getName());
						}
					}
				}
			}
		}
	}
	
	public void register(Object object)
	{
		Objects.requireNonNull(object);

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
	
	public void unregister(Object object)
	{
		Collection<LinkedHashMap<Object, ArrayList<Method>>> values = events.values();

		for (LinkedHashMap<Object, ArrayList<Method>> value : values)
		{
			value.entrySet().removeIf(next -> next.getKey() == object);
		}
	}
	
	private void register(Class<?> eventType, Object owner, Method method)
	{
		HashMap<Object, ArrayList<Method>> map = events.get(eventType);
		if (map == null)
		{
//			System.out.println("Event is null, expected: " + eventType.getSimpleName());
			LinkedHashMap<Object, ArrayList<Method>> newMap = new LinkedHashMap<>();
			ArrayList<Method> newMethods = new ArrayList<>();
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
				ArrayList<Method> newMethods = new ArrayList<>();
				newMethods.add(method);
				map.put(owner, newMethods);
			} else
			{
//				System.out.println("Owner found");
				if (!methods.contains(method))
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
					if (specialCaseObjects.isEmpty())
					{
						m.invoke(o, event);
					} else
					{
						for (Object c : specialCaseObjects)
						{
							if (c == o)
								m.invoke(o, event);
						}
					}

					if (!forcedObjects.isEmpty() && !specialCaseObjects.isEmpty())
					{
						for (Object ob : forcedObjects)
						{
							if (ob == o)
								m.invoke(o, event);
						}
					}
				} catch (IllegalAccessException | IllegalArgumentException e1)
				{
					illAccEx(e1, o, m);
				} catch (InvocationTargetException e1)
				{
					invocTarEx(e1);
				}
			}
		}
	}

	private void illAccEx(Exception e1, Object o, Method m)
	{
		System.err.println(o.getClass().getName() + " : " + m.getName());
		e1.printStackTrace();
	}

	private void invocTarEx(InvocationTargetException e1)
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

	/**
	 * If set to Object containing Events only this Object will be able to run Events
	 * All other Events in other Objects will not run.
	 * @param o Object with @Events
	 */
	public void addSpecialCaseObject(Object o)
	{
		if (!specialCaseObjects.contains(o))
			specialCaseObjects.add(o);
	}

	public void removeSpecialCaseObject(Object o)
	{
		specialCaseObjects.remove(o);
	}

	public void clearSpecialCaseObjects()
	{
		specialCaseObjects.clear();
	}

	public void addForcedObject(Object o)
	{
		if (!forcedObjects.contains(o))
			forcedObjects.add(o);
	}

	public void removeForcedObject(Object o)
	{
		forcedObjects.remove(o);
	}

	public void clearForcedObjects()
	{
		forcedObjects.clear();
	}
}
