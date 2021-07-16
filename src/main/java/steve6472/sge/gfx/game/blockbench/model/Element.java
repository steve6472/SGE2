package steve6472.sge.gfx.game.blockbench.model;

import steve6472.sge.gfx.game.voxelizer.VoxLayer;

import java.util.Objects;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 22.10.2020
 * Project: CaveGame
 *
 ***********************/
public final class Element extends OutlinerElement
{
	public float fromX, fromY, fromZ;
	public float toX, toY, toZ;
	public Face north, east, south, west, up, down;

	public static final class Face
	{
		private float u0;
		private float v0;
		private float u1;
		private float v1;
		private byte rotation;
		private int texture;
		private VoxLayer layer;

		public Face(float u0, float v0, float u1, float v1, byte rotation, int texture, VoxLayer layer)
		{
			this.u0 = u0;
			this.v0 = v0;
			this.u1 = u1;
			this.v1 = v1;
			this.rotation = rotation;
			this.texture = texture;
			this.layer = layer;
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

		public void setLayer(VoxLayer layer)
		{
			this.layer = layer;
		}

		public VoxLayer getLayer()
		{
			return layer;
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
			return "Face{" + "u0=" + u0 + ", v0=" + v0 + ", u1=" + u1 + ", v1=" + v1 + ", rotation=" + rotation + ", texture=" + texture + '}' + '\n';
		}
	}

	@Override
	public String toString()
	{
		return "Element{" + "fromX=" + fromX + ", fromY=" + fromY + ", fromZ=" + fromZ + ", toX=" + toX + ", toY=" + toY + ", toZ=" + toZ + ", north=" + north + ", east=" + east + ", south=" + south + ", west=" + west + ", up=" + up + ", down=" + down + '}' + '\n';
	}
}
