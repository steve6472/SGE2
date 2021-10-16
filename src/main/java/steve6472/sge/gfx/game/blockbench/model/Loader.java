package steve6472.sge.gfx.game.blockbench.model;

import org.joml.Vector3f;
import org.json.JSONArray;
import org.json.JSONObject;
import steve6472.sge.gfx.game.blockbench.animation.AnimLoader;
import steve6472.sge.gfx.game.blockbench.animation.BBAnimation;
import steve6472.sge.gfx.game.blockbench.animation.Bone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 22.10.2020
 * Project: CaveGame
 *
 ***********************/
public class Loader
{
	private static final float TO_RAD = 0.017453292519943295f;

	public static OutlinerElement[] load(ModelRepository repository, HashMap<String, BBAnimation> animationsMap, String modelName)
	{
		JSONObject model = new JSONObject(read(new File(modelName + ".bbmodel")));

		JSONObject res = model.getJSONObject("resolution");
		HashMap<UUID, OutlinerElement> elements = loadElements(repository, model.getJSONArray("elements"), model.getJSONArray("textures"), res.getFloat("width"), res.getFloat("height"));

		if (model.has("animations"))
		{
			JSONArray animationsJson = model.getJSONArray("animations");
			for (int i = 0; i < animationsJson.length(); i++)
			{
				JSONObject o = animationsJson.getJSONObject(i);

				// Existing empty animation
				if (o.has("animators"))
				{
					List<Bone> bones = new ArrayList<>();
					double len = AnimLoader.load(o, bones);
					String name = o.getString("name");
					BBAnimation animation = new BBAnimation(name, bones, len);
					animationsMap.put(name, animation);
				}
			}
		}

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

	private static HashMap<UUID, OutlinerElement> loadElements(ModelRepository repository, JSONArray elements, JSONArray textures, float resX, float resY)
	{
		HashMap<UUID, OutlinerElement> map = new HashMap<>();

		for (Object element : elements)
		{
			if (element instanceof JSONObject jsonElement)
			{
				loadElement(repository, map, jsonElement, textures, resX, resY);
			}
		}

		return map;
	}

	private static void loadElement(ModelRepository repository, HashMap<UUID, OutlinerElement> map, JSONObject json, JSONArray textures, float resX, float resY)
	{
		if (json.has("type"))
		{
			if ("mesh".equals(json.getString("type")))
			{
				loadMeshElement(repository, map, json, textures, resX, resY);
				return;
			} else
			{
				throw new IllegalStateException("Unexpected value: " + json.getString("type"));
			}
		}
		loadCubeElement(repository, map, json, textures, resX, resY);
	}

	private static void loadMeshElement(ModelRepository repository, HashMap<UUID, OutlinerElement> map, JSONObject json, JSONArray textures, float resX, float resY)
	{
		MeshElement element = new MeshElement();
		element.uuid = loadCommon(element, json);

		HashMap<String, Vector3f> verticesMap = new HashMap<>();

		JSONObject vertices = json.getJSONObject("vertices");
		for (String id : vertices.keySet())
		{
			JSONArray vertexJson = vertices.getJSONArray(id);
			Vector3f vertex = new Vector3f();
			vertex.x = vertexJson.getFloat(0);
			vertex.y = vertexJson.getFloat(1);
			vertex.z = vertexJson.getFloat(2);
			verticesMap.put(id, vertex);
		}

		JSONObject faces = json.getJSONObject("faces");
		for (String id : faces.keySet())
		{
			JSONObject faceJson = faces.getJSONObject(id);

			int textureId = faceJson.getInt("texture");
			String texturePath = findTexture(textures, textureId);
			repository.getAtlas().putTexture(texturePath);
			int texture = repository.getAtlas().getTextureId(texturePath);

			JSONArray verticesArray = faceJson.getJSONArray("vertices");

			JSONObject uv = faceJson.getJSONObject("uv");

			Set<String> uvKeys = uv.keySet();
			if (uvKeys.size() > 4)
				throw new IllegalStateException("Too many vertices for one face! (" + uvKeys.size() + ")");

			if (uvKeys.size() == 3)
			{
				MeshElement.Face face = element.new Face(texture);
				for (int i = 0; i < 3; i++)
				{
					String vertId = verticesArray.getString(i);
					JSONArray uvsJson = uv.getJSONArray(vertId);

					face.getVerts()[i].set(verticesMap.get(vertId));
					face.getUvs()[i].set(uvsJson.getFloat(0) / resX, uvsJson.getFloat(1) / resY);
				}
				element.addFace(face);
			}
			else if (uvKeys.size() == 4)
			{
				{
					int[] order = {0, 1, 2};
					MeshElement.Face face = element.new Face(texture);
					for (int i = 0; i < 3; i++)
					{
						String vertId = verticesArray.getString(order[i]);
						JSONArray uvsJson = uv.getJSONArray(vertId);

						face.getVerts()[i].set(verticesMap.get(vertId));
						face.getUvs()[i].set(uvsJson.getFloat(0) / resX, uvsJson.getFloat(1) / resY);
					}
					element.addFace(face);
				}

				int[] order = {3, 2, 1};
				MeshElement.Face face = element.new Face(texture);
				for (int i = 0; i < 3; i++)
				{
					String vertId = verticesArray.getString(order[i]);
					JSONArray uvsJson = uv.getJSONArray(vertId);

					face.getVerts()[i].set(verticesMap.get(vertId));
					face.getUvs()[i].set(uvsJson.getFloat(0) / resX, uvsJson.getFloat(1) / resY);
				}
				element.addFace(face);
			}
		}

//		loadProperties(repository, json, element, PropertyClass.MESH);

		map.put(element.uuid, element);
	}

	private static void loadCubeElement(ModelRepository repository, HashMap<UUID, OutlinerElement> map, JSONObject json, JSONArray textures, float resX, float resY)
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

		if (faces.has("north")) element.north = loadFace(repository, faces.getJSONObject("north"), textures, resX, resY);
		if (faces.has("east")) element.east = loadFace(repository, faces.getJSONObject("east"), textures, resX, resY);
		if (faces.has("south")) element.south = loadFace(repository, faces.getJSONObject("south"), textures, resX, resY);
		if (faces.has("west")) element.west = loadFace(repository, faces.getJSONObject("west"), textures, resX, resY);
		if (faces.has("up")) element.up = loadFace(repository, faces.getJSONObject("up"), textures, resX, resY);
		if (faces.has("down")) element.down = loadFace(repository, faces.getJSONObject("down"), textures, resX, resY);

		loadProperties(repository, json, element, PropertyClass.CUBE);

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

	private static Element.Face loadFace(ModelRepository repository, JSONObject json, JSONArray textures, float resX, float resY)
	{
		if (!json.has("texture") || json.get("texture") == null || json.isNull("texture"))
			return null;

		int textureId = json.getInt("texture");
		String texturePath = findTexture(textures, textureId);
		repository.getAtlas().putTexture(texturePath);
		int id = repository.getAtlas().getTextureId(texturePath);

		JSONArray uv = json.getJSONArray("uv");

		Element.Face face = new Element.Face(uv.getFloat(0) / resX, uv.getFloat(1) / resY, uv.getFloat(2) / resX, uv
			.getFloat(3) / resY, (byte) (json.optInt("rotation", 0) / 90), id);

		loadProperties(repository, json, face, PropertyClass.FACE);

		return face;
	}

	private static void loadProperties(ModelRepository repository, JSONObject json, IProperties propertyObject, PropertyClass propertyClass)
	{
		List<ModelProperty> modelProperties = repository.getProperties().get(propertyClass);

		for (ModelProperty modelProperty : modelProperties)
		{
			if (json.has(modelProperty.name()))
			{
				propertyObject.getProperties().put(modelProperty, modelProperty.value().apply(json.get(modelProperty.name())));
			} else
			{
				propertyObject.getProperties().put(modelProperty, modelProperty.defaultValue().get());
			}
		}
	}

	private static OutlinerElement[] loadOutliner(JSONArray outliner, HashMap<UUID, OutlinerElement> elements)
	{
		List<OutlinerElement> outlinerElements = new ArrayList<>();
		for (Object o : outliner)
		{
			outlinerElements.add(loadOutlinerElement(o, elements));
		}
		return outlinerElements.toArray(OutlinerElement[]::new);
	}

	private static OutlinerElement loadOutlinerElement(Object obj, HashMap<UUID, OutlinerElement> elements)
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
		JSONObject texture = textures.getJSONObject(id);
		String path = texture.getString("path");
		path = path.replace("\\", "/");
		path = path.substring(path.indexOf("textures") + 9);
		path = path.substring(0, path.length() - 4);
		return path;
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
