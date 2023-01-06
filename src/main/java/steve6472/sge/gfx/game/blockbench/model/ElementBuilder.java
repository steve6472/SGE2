package steve6472.sge.gfx.game.blockbench.model;

import steve6472.sge.gfx.game.voxelizer.VoxLayer;
import steve6472.sge.main.game.Direction;

/**********************
 * Created by steve6472
 * On date: 7/13/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class ElementBuilder
{
	private final Element element;
	private Direction lastFace;

	private ElementBuilder()
	{
		this.element = new Element();
		element.scaleX = 1;
		element.scaleY = 1;
		element.scaleZ = 1;
	}

	public static ElementBuilder create()
	{
		return new ElementBuilder();
	}

	public static Element[] emptyElement()
	{
		return new Element[] {};
	}

	public ElementBuilder name(String name)
	{
		element.name = name;
		return this;
	}

	public ElementBuilder origin(float originX, float originY, float originZ)
	{
		element.originX = originX;
		element.originY = originY;
		element.originZ = originZ;
		return this;
	}

	public ElementBuilder rotation(float rotationX, float rotationY, float rotationZ)
	{
		element.rotationX = rotationX;
		element.rotationY = rotationY;
		element.rotationZ = rotationZ;
		return this;
	}

	public ElementBuilder rotationDeg(float rotationX, float rotationY, float rotationZ)
	{
		element.rotationX = (float) Math.toDegrees(rotationX);
		element.rotationY = (float) Math.toDegrees(rotationY);
		element.rotationZ = (float) Math.toDegrees(rotationZ);
		return this;
	}

	public ElementBuilder scale(float scaleX, float scaleY, float scaleZ)
	{
		element.scaleX = scaleX;
		element.scaleY = scaleY;
		element.scaleZ = scaleZ;
		return this;
	}

	public ElementBuilder rectangle(float fromX, float fromY, float fromZ, float toX, float toY, float toZ)
	{
		element.fromX = fromX - 8f;
		element.fromY = fromY;
		element.fromZ = fromZ - 8f;
		element.toX = toX - 8f;
		element.toY = toY;
		element.toZ = toZ - 8f;
		return this;
	}

	public ElementBuilder rectangleWHD(float fromX, float fromY, float fromZ, float width, float height, float depth)
	{
		element.fromX = fromX - 8f;
		element.fromY = fromY;
		element.fromZ = fromZ - 8f;
		element.toX = fromX + width - 8f;
		element.toY = fromY + height;
		element.toZ = fromZ + depth - 8f;
		return this;
	}

	public ElementBuilder addProperty(ModelProperty property, Object value)
	{
		element.getProperties().put(property, value);
		return this;
	}

	/**
	 * Generates UV based on position
	 * @param texture texture of selected faces
	 * @param faces faces to auto generate
	 */
	public ElementBuilder autoFaces(int texture, float resX, float resY, byte rotation, VoxLayer layer, Direction... faces)
	{
		for (Direction face : faces)
		{
			switch (face)
			{
				case UP -> setFace(face, FaceBuilder.create().setTexture(texture).resolution(resX, resY).addProperty(ModelRepository.LAYER_PROPERTY, layer).rotation(rotation).uv(element.fromX + 8, element.fromZ + 8, element.toX + 8, element.toZ + 8));
				case NORTH -> setFace(face, FaceBuilder.create().setTexture(texture).resolution(resX, resY).addProperty(ModelRepository.LAYER_PROPERTY, layer).rotation(rotation).uv(-element.toX + 8, -element.toY + 16, -element.fromX + 8, -element.fromY + 16));
				case EAST -> setFace(face, FaceBuilder.create().setTexture(texture).resolution(resX, resY).addProperty(ModelRepository.LAYER_PROPERTY, layer).rotation(rotation).uv(-element.toZ + 8, -element.toY + 16, -element.fromZ + 8, -element.fromY + 16));
				case SOUTH -> setFace(face, FaceBuilder.create().setTexture(texture).resolution(resX, resY).addProperty(ModelRepository.LAYER_PROPERTY, layer).rotation(rotation).uv(element.fromX + 8, -element.toY + 16, element.toX + 8, -element.fromY + 16));
				case WEST -> setFace(face, FaceBuilder.create().setTexture(texture).resolution(resX, resY).addProperty(ModelRepository.LAYER_PROPERTY, layer).rotation(rotation).uv(element.fromZ + 8, -element.toY + 16, element.toZ + 8, -element.fromY + 16));
				case DOWN -> setFace(face, FaceBuilder.create().setTexture(texture).resolution(resX, resY).addProperty(ModelRepository.LAYER_PROPERTY, layer).rotation(rotation).uv(element.fromX + 8, -element.toZ + 8, element.toX + 8, -element.fromZ + 8));
			}
		}
		return this;
	}

	public ElementBuilder autoFaces(int texture, Direction... faces)
	{
		return autoFaces(texture, 16f, 16f, (byte) 0, ModelRepository.NORMAL_LAYER, faces);
	}

	public ElementBuilder autoFaces(int texture, float resX, float resY, Direction... faces)
	{
		return autoFaces(texture, resX, resY, (byte) 0, ModelRepository.NORMAL_LAYER, faces);
	}

	public ElementBuilder autoFaces(int texture, float resX, float resY, byte rotation, Direction... faces)
	{
		return autoFaces(texture, resX, resY, rotation, ModelRepository.NORMAL_LAYER, faces);
	}

	public ElementBuilder autoFaces(int texture, byte rotation, Direction... faces)
	{
		return autoFaces(texture, 16f, 16f, rotation, ModelRepository.NORMAL_LAYER, faces);
	}

	public ElementBuilder autoFaces(int texture, VoxLayer layer, Direction... faces)
	{
		return autoFaces(texture, 16f, 16f, (byte) 0, layer, faces);
	}

	public ElementBuilder autoFaces(int texture, float resX, float resY, VoxLayer layer, Direction... faces)
	{
		return autoFaces(texture, resX, resY, (byte) 0, layer, faces);
	}

	public ElementBuilder autoFaces(int texture, byte rotation, VoxLayer layer, Direction... faces)
	{
		return autoFaces(texture, 16f, 16f, rotation, layer, faces);
	}


	public ElementBuilder autoFaces(int texture)
	{
		return autoFaces(texture, 16f, 16f, (byte) 0, ModelRepository.NORMAL_LAYER, Direction.getValues());
	}

	public ElementBuilder autoFaces(int texture, float resX, float resY)
	{
		return autoFaces(texture, resX, resY, (byte) 0, ModelRepository.NORMAL_LAYER, Direction.getValues());
	}

	public ElementBuilder autoFaces(int texture, float resX, float resY, byte rotation)
	{
		return autoFaces(texture, resX, resY, rotation, ModelRepository.NORMAL_LAYER, Direction.getValues());
	}

	public ElementBuilder autoFaces(int texture, byte rotation)
	{
		return autoFaces(texture, 16f, 16f, rotation, ModelRepository.NORMAL_LAYER, Direction.getValues());
	}

	public ElementBuilder autoFaces(int texture, VoxLayer layer)
	{
		return autoFaces(texture, 16f, 16f, (byte) 0, layer, Direction.getValues());
	}

	public ElementBuilder autoFaces(int texture, float resX, float resY, VoxLayer layer)
	{
		return autoFaces(texture, resX, resY, (byte) 0, layer, Direction.getValues());
	}

	public ElementBuilder autoFaces(int texture, byte rotation, VoxLayer layer)
	{
		return autoFaces(texture, 16f, 16f, rotation, layer, Direction.getValues());
	}

	public ElementBuilder setFace(Direction face, FaceBuilder builder)
	{
		switch (face)
		{
			case UP -> element.up = builder.build();
			case DOWN -> element.down = builder.build();
			case NORTH -> element.north = builder.build();
			case EAST -> element.east = builder.build();
			case SOUTH -> element.south = builder.build();
			case WEST -> element.west = builder.build();
		}
		return this;
	}

	public ElementBuilder setFaces(FaceBuilder builder, Direction... faces)
	{
		for (Direction face : faces)
		{
			setFace(face, new FaceBuilder(builder.build()));
		}
		return this;
	}

	void setLastFace(FaceBuilder builder)
	{
		setFace(lastFace, builder);
	}

	public FaceBuilder north()
	{
		lastFace = Direction.NORTH;
		return FaceBuilder.create(this);
	}

	public FaceBuilder east()
	{
		lastFace = Direction.EAST;
		return FaceBuilder.create(this);
	}

	public FaceBuilder south()
	{
		lastFace = Direction.SOUTH;
		return FaceBuilder.create(this);
	}

	public FaceBuilder west()
	{
		lastFace = Direction.WEST;
		return FaceBuilder.create(this);
	}

	public FaceBuilder up()
	{
		lastFace = Direction.UP;
		return FaceBuilder.create(this);
	}

	public FaceBuilder down()
	{
		lastFace = Direction.DOWN;
		return FaceBuilder.create(this);
	}

	public Element build()
	{
		return element;
	}
}
