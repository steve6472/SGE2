package steve6472.sge.gfx.game.blockbench.model;

import steve6472.sge.gfx.StaticTexture;
import steve6472.sge.gfx.game.voxelizer.VoxLayer;
import steve6472.sge.gfx.game.voxelizer.VoxLayers;

import java.util.*;
import java.util.function.Supplier;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/8/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class ModelRepository
{
	private final List<BBModel> models = new ArrayList<>();
	private final List<ElementHolder> elements = new ArrayList<>();
	private final ModelTextureAtlas atlas = new ModelTextureAtlas();
	private final VoxLayers layers = new VoxLayers();
	private final HashMap<PropertyClass, List<ModelProperty>> properties = new HashMap<>();

	public static final VoxLayer NORMAL_LAYER = new VoxLayer("normal");
	public static ModelProperty LAYER_PROPERTY;

	public ModelRepository()
	{
		addLayer(NORMAL_LAYER);
		LAYER_PROPERTY = new ModelProperty(PropertyClass.FACE, PropertyType.STRING, "layer", () -> NORMAL_LAYER, (s) -> layers.getLayer((String) s));
		addProperty(LAYER_PROPERTY);
	}

	public void addProperty(ModelProperty property)
	{
		List<ModelProperty> modelProperties = properties.get(property.clazz());
		if (modelProperties == null)
		{
			modelProperties = new ArrayList<>();
			modelProperties.add(property);
			properties.put(property.clazz(), modelProperties);
		} else
		{
			modelProperties.add(property);
		}
	}

	public ElementHolder addElement(Supplier<Element> constructor)
	{
		ElementHolder holder = new ElementHolder(constructor);
		elements.add(holder);
		return holder;
	}

	public BBModel loadModel(String path)
	{
		BBModel model = new BBModel(this, path);
		models.add(model);
		return model;
	}

	public BBModel addModel(OutlinerElement... elements)
	{
		BBModel model = new BBModel(elements);
		models.add(model);
		return model;
	}

	public BBModel addModel(Supplier<OutlinerElement[]> elementCreator)
	{
		BBModel model = new BBModel(elementCreator);
		models.add(model);
		return model;
	}

	public void finish()
	{
		atlas.compileTextures(0);
		models.forEach(atlas::assignTextures);
		elements.forEach(h -> atlas.faces(h.getObject()));
	}

	public void reload()
	{
		atlas.clean();
		models.forEach(m -> m.reload(this));
		elements.forEach(ElementHolder::reload);
		finish();
	}

	public void clear()
	{
		atlas.clean();
		models.clear();
		elements.clear();
	}

	public ModelTextureAtlas getAtlas()
	{
		return atlas;
	}

	public StaticTexture getAtlasTexture()
	{
		return atlas.getTexture();
	}

	public List<BBModel> getModels()
	{
		return models;
	}

	public List<ElementHolder> getElements()
	{
		return elements;
	}

	public HashMap<PropertyClass, List<ModelProperty>> getProperties()
	{
		return properties;
	}

	/*
	 * Layers
	 */

	public void addLayer(VoxLayer layer)
	{
		layers.addLayer(layer);
	}

	public List<VoxLayer> getLayers()
	{
		return layers.getLayers();
	}

	public VoxLayer getLayer(int index)
	{
		return layers.getLayer(index);
	}

	public int getLayerIndex(String id)
	{
		return layers.getLayerIndex(id);
	}

	public VoxLayer getLayer(String id)
	{
		return layers.getLayer(id);
	}
}
