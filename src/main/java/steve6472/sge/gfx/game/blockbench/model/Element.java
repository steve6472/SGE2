package steve6472.sge.gfx.game.blockbench.model;

import java.util.HashMap;
import java.util.Objects;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 22.10.2020
 * Project: CaveGame
 *
 ***********************/
public final class Element extends OutlinerElement implements IProperties
{
	public float fromX, fromY, fromZ;
	public float toX, toY, toZ;
	public Face north, east, south, west, up, down;
	public HashMap<ModelProperty, Object> properties;

	@Override
	public HashMap<ModelProperty, Object> getProperties()
	{
		if (properties == null)
			properties = new HashMap<>();

		return properties;
	}

	@Override
	public PropertyClass getPropertyClass()
	{
		return PropertyClass.CUBE;
	}

	public static final class Face implements IProperties
	{
		private float u0;
		private float v0;
		private float u1;
		private float v1;
		private byte rotation;
		private int texture;
		public final HashMap<ModelProperty, Object> properties;

		public Face(float u0, float v0, float u1, float v1, byte rotation, int texture)
		{
			this.u0 = u0;
			this.v0 = v0;
			this.u1 = u1;
			this.v1 = v1;
			this.rotation = rotation;
			this.texture = texture;
			properties = new HashMap<>();
		}

		@Override
		public HashMap<ModelProperty, Object> getProperties()
		{
			return properties;
		}

		@Override
		public PropertyClass getPropertyClass()
		{
			return PropertyClass.FACE;
		}

		public float getU0()
		{
			return u0;
		}

		public float getV0()
		{
			return v0;
		}

		public float getU1()
		{
			return u1;
		}

		public float getV1()
		{
			return v1;
		}

		public void setU0(float u0)
		{
			this.u0 = u0;
		}

		public void setV0(float v0)
		{
			this.v0 = v0;
		}

		public void setU1(float u1)
		{
			this.u1 = u1;
		}

		public void setV1(float v1)
		{
			this.v1 = v1;
		}

		public byte getRotation()
		{
			return rotation;
		}

		public void setRotation(byte rotation)
		{
			this.rotation = rotation;
		}

		public int texture()
		{
			return texture;
		}

		public void setTexture(int texture)
		{
			this.texture = texture;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Face face = (Face) o;
			return Float.compare(face.u0, u0) == 0 && Float.compare(face.v0, v0) == 0 && Float.compare(face.u1, u1) == 0 && Float
				.compare(face.v1, v1) == 0 && rotation == face.rotation && texture == face.texture;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(u0, v0, u1, v1, rotation, texture);
		}

		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();

			sb.append("Face{\n");
			sb.append("\t\tuv=[").append(u0).append(", ").append(v0).append(", ").append(u1).append(", ").append(v1).append("]\n");
			sb.append("\t\trotation=").append(rotation).append('\n');
			sb.append("\t\ttexture=").append(texture).append('\n');
			sb.append("\t\tproperties={");
			properties.forEach((k, v) -> sb.append("\n\t\t\t").append(k.name()).append("=").append(v));
			sb.append("\n\t\t}");
			sb.append("\n\t}");

			return sb.toString();
		}
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\nElement{\n");
		sb.append("\tname=").append(name).append('\n');
		sb.append("\tfrom=[").append(fromX).append(", ").append(fromY).append(", ").append(fromZ).append("]\n");
		sb.append("\tto=[").append(toX).append(", ").append(toY).append(", ").append(toZ).append("]\n");
		sb.append("\torigin=[").append(originX).append(", ").append(originY).append(", ").append(originZ).append("]\n");
		sb.append("\trotation=[").append(rotationX).append(", ").append(rotationY).append(", ").append(rotationZ).append("]\n");
		sb.append("\tposition=[").append(positionX).append(", ").append(positionY).append(", ").append(positionZ).append("]\n");
		sb.append("\tscale=[").append(scaleX).append(", ").append(scaleY).append(", ").append(scaleZ).append("]\n");
		sb.append("\tproperties={");
		properties.forEach((k, v) -> sb.append("\n\t\t").append(k.name()).append("=").append(v));
		sb.append("\n\t}\n");
		if (up != null) sb.append("\tup=").append(up).append('\n');
		if (down != null) sb.append("\tdown=").append(down).append('\n');
		if (north != null) sb.append("\tnorth=").append(north).append('\n');
		if (east != null) sb.append("\teast=").append(east).append('\n');
		if (south != null) sb.append("\tsouth=").append(south).append('\n');
		if (west != null) sb.append("\twest=").append(west).append('\n');
		sb.append('}');
		return sb.toString();
	}
}
