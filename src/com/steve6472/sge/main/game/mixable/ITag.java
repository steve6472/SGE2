package com.steve6472.sge.main.game.mixable;

import com.steve6472.sge.main.game.Tag;

import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 2.1.2019
 * Project: SGE2
 *
 ***********************/
public interface ITag
{
	default void addTag(Tag tag)
	{
		getTags().add(tag);
	}

	default Tag getTag(String name)
	{
		for (Tag tag : getTags())
		{
			if (tag.getName().equals(name))
			{
				return tag;
			}
		}
		return null;
	}

	/**
	 *
	 * @param tagName
	 *            Name of tag that should be removed
	 * @return true if tag has been removed. False otherwise
	 */
	default boolean removeTag(String tagName)
	{
		int tagIndex = 0;
		boolean found = false;

		for (Tag tag : getTags())
		{
			if (tag.getName().equals(tagName))
			{
				found = true;
				break;
			}
			tagIndex++;
		}

		if (found)
			getTags().remove(tagIndex);

		return found;
	}

	default void changeTag(String name, Object newValue)
	{
		Tag tag = getTag(name);
		if (tag != null)
		{
			tag.value = newValue;
		}
	}

	default void listTags()
	{
		for (Tag tag : getTags())
		{
			System.out.println(tag.toString());
		}
	}

	default boolean hasTag(String name)
	{
		return getTag(name) != null;
	}

	List<Tag> getTags();
}
