/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 9. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.main.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.main.MainApplication;

public class EntityList implements Serializable
{
	private static final long serialVersionUID = 6609428400048323323L;
	private final List<Class<? extends BaseEntity>> entities = new ArrayList<Class<? extends BaseEntity>>();
	private final List<Sprite> entitySprites = new ArrayList<Sprite>();
	private MainApplication game = null;
	
	public EntityList(MainApplication game)
	{
		this.game = game;
	}
	
	public final BaseEntity getEntity(int id, Object...objects)
	{
		try
		{
			BaseEntity e = getEntities().get(id).newInstance();
			e.setMainApp(game);
			e.setEntityList(this);
			e.initEntity(game, objects);
			e.setId(id);
			return e;
		} catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final BaseEntity getEntity(String name, Object...objects)
	{
		int id = 0;
		for (int i = 0; i < getEntities().size(); i++)
		{
			try
			{
				if (getEntities().get(i).newInstance().getName().equals(name))
					id = i;
			} catch (InstantiationException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		return getEntity(id, objects);
	}

	public final BaseEntity getEntityByClass(Class<? extends BaseEntity> clazz)
	{
		return getEntity(getLastName(clazz));
	}

	public String getLastName(Class<?> clazz) { return clazz.getSimpleName(); }

	public final List<Class<? extends BaseEntity>> getEntities() { return entities; }

	public final List<BaseEntity> getInitEntities()
	{
		List<BaseEntity> en = new ArrayList<BaseEntity>();
		int i = 0;
		for (@SuppressWarnings("unused") Class<? extends BaseEntity> c : getEntities())
		{
			en.add(getEntity(i));
			i++;
		}
		return en;
	}

	public final List<Sprite> getSprites() { return entitySprites; }

	public final void addEntity(Class<? extends BaseEntity> entity)
	{
		try
		{
			BaseEntity e = entity.newInstance();
			
			entities.add(entity);
			entitySprites.add(e.setSprite());
			
		} catch (InstantiationException | IllegalAccessException e)
		{
			System.err.println("Can't add " + entity.getTypeName() + "! ");
			e.printStackTrace();
		}
	}

}
