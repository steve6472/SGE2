/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 12. 10. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.main.game;

import java.io.Serializable;

public abstract class Task implements Serializable
{
	private static final long serialVersionUID = -2571705598379443113L;

	public abstract void tickTask(BaseEntity e);
}
