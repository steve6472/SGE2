package steve6472.sge.gfx.game.blockbench.model;

import java.util.UUID;

/**********************
 * Created by steve6472
 * On date: 22.10.2020
 * Project: CaveGame
 *
 ***********************/
public abstract class OutlinerElement
{
	public String name;
	public float originX, originY, originZ;
	public float rotationX, rotationY, rotationZ;
	// used in animations
	public float positionX, positionY, positionZ;
	public float scaleX, scaleY, scaleZ;
	public UUID uuid;

	@Override
	public String toString()
	{
		return "OutlinerElement{" + "name='" + name + '\'' + ", originX=" + originX + ", originY=" + originY + ", originZ=" + originZ + ", rotationX=" + rotationX + ", rotationY=" + rotationY + ", rotationZ=" + rotationZ + ", positionX=" + positionX + ", positionY=" + positionY + ", positionZ=" + positionZ + ", scaleX=" + scaleX + ", scaleY=" + scaleY + ", scaleZ=" + scaleZ + ", uuid=" + uuid + '}' + '\n';
	}
}
