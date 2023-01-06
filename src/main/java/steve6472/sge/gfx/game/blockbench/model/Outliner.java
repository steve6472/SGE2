package steve6472.sge.gfx.game.blockbench.model;

import java.util.Arrays;

/**********************
 * Created by steve6472
 * On date: 22.10.2020
 * Project: CaveGame
 *
 ***********************/
public class Outliner extends OutlinerElement
{
	public OutlinerElement[] children;

	public Outliner(OutlinerElement[] children)
	{
		this.children = children;
	}

	public Outliner()
	{
	}

	@Override
	public String toString()
	{
		return "Outliner{" + "children=" + Arrays.toString(children) + ", name='" + name + '\'' + ", originX=" + originX + ", originY=" + originY + ", originZ=" + originZ + ", rotationX=" + rotationX + ", rotationY=" + rotationY + ", rotationZ=" + rotationZ + ", positionX=" + positionX + ", positionY=" + positionY + ", positionZ=" + positionZ + ", scaleX=" + scaleX + ", scaleY=" + scaleY + ", scaleZ=" + scaleZ + ", uuid=" + uuid + '}';
	}
}
