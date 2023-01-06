package steve6472.sge.gfx.game.voxelizer;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.blockbench.model.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**********************
 * Created by steve6472
 * On date: 7/18/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Builder
{
	public static class BuilderData
	{
		private final List<Element> elements = new ArrayList<>();
		private final List<BBModel> models = new ArrayList<>();
		private final Matrix4f transformation = new Matrix4f();
		private final Vector4f color = new Vector4f(1, 1, 1, 1);

		public void clear()
		{
			elements.clear();
			models.clear();
			transformation.identity();
		}

		public List<Element> getElements()
		{
			return elements;
		}

		public List<BBModel> getModels()
		{
			return models;
		}

		public Matrix4f transform()
		{
			return transformation;
		}

		public Vector4f getColor()
		{
			return color;
		}
	}

	private final List<BuilderData> data = new ArrayList<>();

	public Builder()
	{
		data.add(new BuilderData());
	}

	public Builder addElement(Element element)
	{
		getElements().add(element);
		return this;
	}

	public Builder addElements(Element... elements)
	{
		Collections.addAll(this.getElements(), elements);
		return this;
	}

	public Builder addModel(BBModel model)
	{
		getModels().add(model);
		return this;
	}

	public Builder addModel(BBModel... models)
	{
		Collections.addAll(this.getModels(), models);
		return this;
	}

	public void clear()
	{
		data.forEach(BuilderData::clear);
		data.clear();
		data.add(new BuilderData());
	}

	public List<Element> getElements()
	{
		return data.get(data.size() - 1).elements;
	}

	public List<BBModel> getModels()
	{
		return data.get(data.size() - 1).models;
	}

	public Matrix4f transform()
	{
		return data.get(data.size() - 1).transformation;
	}

	public Vector4f getColor()
	{
		return data.get(data.size() - 1).color;
	}

	public void color(float r, float g, float b, float a)
	{
		getColor().set(r, g, b, a);
	}

	public void next()
	{
		data.add(new BuilderData());
	}

	public void removeLast()
	{
		data.remove(data.size() - 1).clear();
	}

	public List<BuilderData> getData()
	{
		return data;
	}

	public boolean isEmpty()
	{
		return getElements().isEmpty() && getModels().isEmpty();
	}
}
