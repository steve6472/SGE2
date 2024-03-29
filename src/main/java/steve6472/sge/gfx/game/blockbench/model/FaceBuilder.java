package steve6472.sge.gfx.game.blockbench.model;

/**********************
 * Created by steve6472
 * On date: 7/13/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class FaceBuilder
{
	private final Element.Face face;
	private float resX = 16f, resY = 16f;
	private ElementBuilder elementBuilder;

	private FaceBuilder()
	{
		this.face = new Element.Face(0, 0, 0, 0, (byte) 0, 0);
	}

	FaceBuilder(Element.Face copy)
	{
		this.face = new Element.Face(copy.getU0(), copy.getV0(), copy.getU1(), copy.getV1(), copy.getRotation(), copy.texture());
		copy.getProperties().forEach((k, v) -> this.face.getProperties().put(k, v));
	}

	public static FaceBuilder create()
	{
		return new FaceBuilder();
	}

	public static FaceBuilder create(ElementBuilder elementBuilder)
	{
		FaceBuilder faceBuilder = new FaceBuilder();
		faceBuilder.elementBuilder = elementBuilder;
		return faceBuilder;
	}

	public FaceBuilder resolution(float resolutionX, float resolutionY)
	{
		this.resX = resolutionX;
		this.resY = resolutionY;
		return this;
	}

	public FaceBuilder resolution(float resolution)
	{
		return resolution(resolution, resolution);
	}

	public FaceBuilder uv(float uMin, float vMin, float uMax, float vMax)
	{
		face.setU0(uMin / resX);
		face.setU1(uMax / resX);
		face.setV0(vMin / resY);
		face.setV1(vMax / resY);
		return this;
	}

	public FaceBuilder uvWH(float u, float v, float w, float h)
	{
		face.setU0(u / resX);
		face.setV0(v / resY);
		face.setU1((u + w) / resX);
		face.setV1((v + h) / resY);
		return this;
	}

	/**
	 * 0 - no rotation
	 * 1 - 90 degree rotation
	 * 2 - 180 degree rotation
	 * 3 - 270 degree rotation
	 * @param rot 0-3
	 */
	public FaceBuilder rotation(byte rot)
	{
		face.setRotation(rot);
		return this;
	}

	public FaceBuilder setTexture(int texture)
	{
		face.setTexture(texture);
		return this;
	}

	public FaceBuilder addProperty(ModelProperty property, Object value)
	{
		face.getProperties().put(property, value);
		return this;
	}

	public Element.Face build()
	{
		return face;
	}

	public ElementBuilder finish()
	{
		elementBuilder.setLastFace(this);
		return elementBuilder;
	}
}
