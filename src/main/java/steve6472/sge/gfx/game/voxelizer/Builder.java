package steve6472.sge.gfx.game.voxelizer;

import org.joml.Matrix4f;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.blockbench.model.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/18/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Builder
{
	private final List<Element> elements = new ArrayList<>();
	private final List<BBModel> models = new ArrayList<>();
	private final Matrix4f transformation = new Matrix4f();

	public Builder addElement(Element element)
	{
		elements.add(element);
		return this;
	}

	public Builder addElements(Element... elements)
	{
		Collections.addAll(this.elements, elements);
		return this;
	}

	public Builder addModel(BBModel model)
	{
		models.add(model);
		return this;
	}

	public Builder addModel(BBModel... models)
	{
		Collections.addAll(this.models, models);
		return this;
	}

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

	public boolean isEmpty()
	{
		return getElements().isEmpty() && getModels().isEmpty();
	}
}
