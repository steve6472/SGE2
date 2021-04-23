package steve6472.sge.gfx.game.voxelizer;

import steve6472.sge.gfx.game.blockbench.ModelTextureAtlas;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.blockbench.model.Element;
import steve6472.sge.main.game.VoxPos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/4/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Voxelizer
{
	private static final List<VoxPos> FACES = Arrays.asList(new VoxPos(0, 1, 0), new VoxPos(0, -1, 0), new VoxPos(0, 0, -1), new VoxPos(1, 0, 0), new VoxPos(0, 0, 1), new VoxPos(-1, 0, 0));
	private static final VoxPos TEMP = new VoxPos();

	private final HashMap<VoxPos, Element> builder;
	private final int textureSize;

	public Voxelizer(int textureSize)
	{
		builder = new HashMap<>();
		this.textureSize = textureSize;
	}

	public Voxelizer addVoxel(int x, int y, int z, int up, int down, int north, int east, int south, int west)
	{
		final VoxPos pos = new VoxPos(x, y, z);

		if (builder.containsKey(pos))
			throw new IllegalStateException("Two Voxels can not occupy the same place (" + x + "," + y + "," + z + ")");

		Element element = new Element();

		element.up = new Element.Face(0, 0, textureSize, textureSize, (byte) 0, up);
//		element.down = new Element.Face(0, 0, textureSize, textureSize, (byte) 0, down);
		element.north = new Element.Face(0, 0, textureSize, textureSize, (byte) 0, north);
		element.east = new Element.Face(0, 0, textureSize, textureSize, (byte) 0, east);
		element.south = new Element.Face(0, 0, textureSize, textureSize, (byte) 0, south);
		element.west = new Element.Face(0, 0, textureSize, textureSize, (byte) 0, west);

		element.fromX = x;
		element.fromY = y;
		element.fromZ = z;
		element.toX = x + 1;
		element.toY = y + 1;
		element.toZ = z + 1;

		for (VoxPos p : FACES)
		{
			TEMP.set(pos);
			TEMP.add(p);

			Element e = builder.get(TEMP);
			if (e == null)
				continue;

			if (p.equals(0, 1, 0))
			{
				element.up = null;
				e.down = null;
			} else if (p.equals(0, -1, 0))
			{
				element.down = null;
				e.up = null;
			} else if (p.equals(0, 0, -1))
			{
				element.north = null;
				e.south = null;
			} else if (p.equals(0, 0, 1))
			{
				element.south = null;
				e.north = null;
			} else if (p.equals(1, 0, 0))
			{
				element.east = null;
				e.west = null;
			} else if (p.equals(-1, 0, 0))
			{
				element.west = null;
				e.east = null;
			}
		}

		builder.put(pos, element);
		return this;
	}

	public Voxelizer addVoxel(int x, int y, int z, int texture)
	{
		return addVoxel(x, y, z, texture, texture, texture, texture, texture, texture);
	}

	public HashMap<VoxPos, Element> getBuilder()
	{
		return builder;
	}

	public BBModel build(ModelTextureAtlas atlas)
	{
		BBModel model = new BBModel("<GENERATED_VOX>", builder.values().toArray(new Element[0]));
		atlas.assignTextures(model);
		return model;
	}
}
