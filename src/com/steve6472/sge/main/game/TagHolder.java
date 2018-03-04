/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 22. 12. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.main.game;

import java.util.ArrayList;
import java.util.List;

public class TagHolder
{
	protected List<Tag> tags;

	public TagHolder()
	{
		clear();
	}
	
	public final void clear() { tags = new ArrayList<Tag>(); }
	
	public final void addTag(Tag tag) { tags.add(tag); }
	
	public final Tag getTag(String name)
	{
		for (Tag tag : tags)
		{
			if (tag.getName().equals(name))
			{
				return tag;
			}
		}
		return null;
	}
	
	public final List<Tag> getTags() { return tags; }
	
	public final boolean hasTag(String name) { return getTag(name) != null; }
	
	/**
	 * If entity doesn't have the tag it will add it
	 * @param tag
	 */
	public final void addTagIfDoesNotHave(Tag tag)
	{
		if (!hasTag(tag.getName()))
			addTag(tag);
	}
	
	/**
	 * 
	 * @param tagName
	 *            Name of tag that should be removed
	 * @return true if tag has been removed. False otherwise
	 */
	public final boolean removeTag(String tagName)
	{
		int tagIndex = 0;
		boolean found = false;
		
		for (Tag tag : tags)
		{
			if (tag.getName().equals(tagName))
			{
				found = true;
				break;
			}
			tagIndex++;
		}
		
		if (found)
			tags.remove(tagIndex);
		
		return found;
	}
	
	public final void changeTag(String name, Object newValue)
	{
		Tag tag = getTag(name);
		if (tag != null)
		{
			tag.value = newValue;
		}
	}

}
