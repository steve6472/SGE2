package steve6472.sge.gfx.game.blockbench.model;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**********************
 * Created by steve6472
 * On date: 10/16/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class MeshElement extends OutlinerElement implements IProperties
{
	private final List<Face> faces = new ArrayList<>();
	public HashMap<ModelProperty, Object> properties;

	public void addFace(Face face)
	{
		this.faces.add(face);
	}

	public List<Face> getFaces()
	{
		return faces;
	}

	@Override
	public HashMap<ModelProperty, Object> getProperties()
	{
//		if (properties == null)
//			properties = new HashMap<>();

		return properties;
	}

	@Override
	public PropertyClass getPropertyClass()
	{
		return PropertyClass.MESH;
	}

	public class Face implements IProperties
	{
		public final HashMap<ModelProperty, Object> properties;
		private final Vector3f[] verts = new Vector3f[3];
		private final Vector2f[] uvs = new Vector2f[3];
		private int texture;

		public Face(int texture)
		{
			this.texture = texture;
			this.properties = new HashMap<>();

			for (int i = 0; i < 3; i++)
			{
				verts[i] = new Vector3f();
				uvs[i] = new Vector2f();
			}
		}

		public Vector3f[] getVerts()
		{
			return verts;
		}

		public Vector2f[] getUvs()
		{
			return uvs;
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

		public int texture()
		{
			return texture;
		}

		public void setTexture(int texture)
		{
			this.texture = texture;
		}
	}
}
