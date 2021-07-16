package steve6472.sge.gfx.game.blockbench.model;

import steve6472.sge.main.game.Direction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
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
	}

	public static ElementBuilder create()
	{
		return new ElementBuilder();
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
		element.fromX = fromX;
		element.fromY = fromY;
		element.fromZ = fromZ;
		element.toX = toX;
		element.toY = toY;
		element.toZ = toZ;
		return this;
	}

	public ElementBuilder rectangleWHD(float fromX, float fromY, float fromZ, float width, float height, float depth)
	{
		element.fromX = fromX;
		element.fromY = fromY;
		element.fromZ = fromZ;
		element.toX = fromX + width;
		element.toY = fromY + height;
		element.toZ = fromZ + depth;
		return this;
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
