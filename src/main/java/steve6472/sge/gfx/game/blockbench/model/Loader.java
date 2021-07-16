package steve6472.sge.gfx.game.blockbench.model;

import org.json.JSONArray;
import org.json.JSONObject;
import steve6472.sge.gfx.game.blockbench.ModelRepository;
import steve6472.sge.gfx.game.blockbench.ModelTextureAtlas;
import steve6472.sge.gfx.game.voxelizer.VoxLayer;
import steve6472.sge.gfx.game.voxelizer.VoxLayers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 22.10.2020
 * Project: CaveGame
 *
 ***********************/
public class Loader
{
	private static final float TO_RAD = 0.017453292519943295f;

	public static OutlinerElement[] load(ModelTextureAtlas modelTextureAtlas, String modelName)
	{
		return load(modelTextureAtlas, modelName, null);
	}

	public static OutlinerElement[] load(ModelTextureAtlas modelTextureAtlas, String modelName, VoxLayers layers)
	{
		JSONObject model = new JSONObject(read(new File(modelName + ".bbmodel")));

		JSONObject res = model.getJSONObject("resolution");
		HashMap<UUID, Element> elements = loadElements(modelTextureAtlas, model.getJSONArray("elements"), model.getJSONArray("textures"), res.getFloat("width"), res.getFloat("height"), layers);

		return loadOutliner(model.getJSONArray("outliner"), elements);
	}

	public static HashMap<String, OutlinerElement> assignElements(OutlinerElement[] elements)
	{
		HashMap<String, OutlinerElement> elementMap = new HashMap<>();

		for (OutlinerElement el : elements)
		{
			if (el instanceof Outliner o)
			{
				elementMap.put(o.name, o);
				assignElements(o, elementMap);
			}
		}

		return elementMap;
	}

	private static void assignElements(Outliner outliner, HashMap<String, OutlinerElement> elementMap)
	{
		for (OutlinerElement el : outliner.children)
		{
			if (el instanceof Outliner o)
			{
				elementMap.put(o.name, o);
				assignElements(o, elementMap);
			}
		}
	}

	private static HashMap<UUID, Element> loadElements(ModelTextureAtlas modelTextureAtlas, JSONArray elements, JSONArray textures, float resX, float resY, VoxLayers layers)
	{
		HashMap<UUID, Element> map = new HashMap<>();

		for (Object element : elements)
		{
			if (element instanceof JSONObject jsonElement)
			{
				loadElement(modelTextureAtlas, map, jsonElement, textures, resX, resY, layers);
			}
		}

		return map;
	}

	private static void loadElement(ModelTextureAtlas modelTextureAtlas, HashMap<UUID, Element> map, JSONObject json, JSONArray textures, float resX, float resY, VoxLayers layers)
	{
		Element element = new Element();
		element.uuid = loadCommon(element, json);

		JSONArray from = json.getJSONArray("from");
		JSONArray to = json.getJSONArray("to");

		element.fromX = from.getFloat(0);
		element.fromY = from.getFloat(1);
		element.fromZ = from.getFloat(2);

		element.toX = to.getFloat(0);
		element.toY = to.getFloat(1);
		element.toZ = to.getFloat(2);

		JSONObject faces = json.getJSONObject("faces");

		if (faces.has("north")) element.north = loadFace(modelTextureAtlas, faces.getJSONObject("north"), textures, resX, resY, layers);
		if (faces.has("east")) element.east = loadFace(modelTextureAtlas, faces.getJSONObject("east"), textures, resX, resY, layers);
		if (faces.has("south")) element.south = loadFace(modelTextureAtlas, faces.getJSONObject("south"), textures, resX, resY, layers);
		if (faces.has("west")) element.west = loadFace(modelTextureAtlas, faces.getJSONObject("west"), textures, resX, resY, layers);
		if (faces.has("up")) element.up = loadFace(modelTextureAtlas, faces.getJSONObject("up"), textures, resX, resY, layers);
		if (faces.has("down")) element.down = loadFace(modelTextureAtlas, faces.getJSONObject("down"), textures, resX, resY, layers);

		map.put(element.uuid, element);
	}

	private static UUID loadCommon(OutlinerElement element, JSONObject json)
	{
		element.name = json.getString("name");
		JSONArray origin = json.getJSONArray("origin");

		element.originX = origin.getFloat(0);
		element.originY = origin.getFloat(1);
		element.originZ = origin.getFloat(2);

		if (json.has("rotation"))
		{
			JSONArray rotation = json.getJSONArray("rotation");
			element.rotationX = rotation.getFloat(0) * TO_RAD;
			element.rotationY = rotation.getFloat(1) * TO_RAD;
			element.rotationZ = rotation.getFloat(2) * TO_RAD;
		}

		element.scaleX = 1;
		element.scaleY = 1;
		element.scaleZ = 1;

		UUID uuid = UUID.fromString(json.getString("uuid"));
		element.uuid = uuid;
		return uuid;
	}

	private static Element.Face loadFace(ModelTextureAtlas modelTextureAtlas, JSONObject json, JSONArray textures, float resX, float resY, VoxLayers layers)
	{
		if (!json.has("texture") || json.get("texture") == null || json.isNull("texture"))
			return null;

		int textureId = json.getInt("texture");
		String texturePath = findTexture(textures, textureId);
		modelTextureAtlas.putTexture(texturePath);
		int id = modelTextureAtlas.getTextureId(texturePath);

		JSONArray uv = json.getJSONArray("uv");

		VoxLayer layer = layers == null ? ModelRepository.NORMAL_LAYER : layers.getLayer(json.optString("layer", "normal"));

		return new Element.Face(uv.getFloat(0) / resX, uv.getFloat(1) / resY, uv.getFloat(2) / resX, uv.getFloat(3) / resY, (byte) (json.optInt("rotation", 0) / 90), id, layer);
	}


	private static OutlinerElement[] loadOutliner(JSONArray outliner, HashMap<UUID, Element> elements)
	{
		List<OutlinerElement> outlinerElements = new ArrayList<>();
		for (Object o : outliner)
		{
			outlinerElements.add(loadOutlinerElement(o, elements));
		}
		return outlinerElements.toArray(OutlinerElement[]::new);
	}

	private static OutlinerElement loadOutlinerElement(Object obj, HashMap<UUID, Element> elements)
	{
		if (obj instanceof String string)
		{
			UUID uuid = UUID.fromString(string);
			return elements.get(uuid);
		}
		else if (obj instanceof JSONObject jsonObj)
		{
			Outliner outliner = new Outliner();
			loadCommon(outliner, jsonObj);
			outliner.children = loadOutliner(jsonObj.getJSONArray("children"), elements);
			return outliner;
		} else
		{
			throw new IllegalArgumentException(obj.toString());
		}
	}

	private static String findTexture(JSONArray textures, int id)
	{
		for (int i = 0; i < textures.length(); i++)
		{
			JSONObject texture = textures.getJSONObject(i);
			if (texture.getInt("id") == id)
			{
				String path = texture.getString("path");
				path = path.replace("\\", "/");
				path = path.substring(path.indexOf("textures") + 9);
				path = path.substring(0, path.length() - 4);
				return path;
			}
		}
		return "block/null";
	}

	public static String read(File f)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(f));

			boolean endOfTheFile = false;
			while (!endOfTheFile)
			{
				String line = br.readLine();

				if (line == null)
				{
					endOfTheFile = true;
				} else
				{
					sb.append(line);
				}
			}

			br.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}
}
