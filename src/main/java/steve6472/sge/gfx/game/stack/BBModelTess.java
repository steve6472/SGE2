package steve6472.sge.gfx.game.stack;

import org.joml.Math;
import org.joml.*;
import steve6472.sge.gfx.StaticTexture;
import steve6472.sge.gfx.Tessellator;
import steve6472.sge.gfx.shaders.StaticShader3D;
import steve6472.sge.main.util.ColorUtil;
import steve6472.sge.gfx.shaders.BBShader;

import java.util.function.BiConsumer;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 14.11.2020
 * Project: CaveGame
 *
 ***********************/
public class BBModelTess extends StackTess
{
	private static final int MAX_SIZE = 1024 * 128;
	private static final float INV_255 = 1f / 255f;

	private final StackTessellator tess;
	private final Vector3f dest3f;
	private final Vector4f lastColor;

	public BBModelTess(Stack stack)
	{
		super(stack);

		tess = new StackTessellator(MAX_SIZE);
		tess.begin(MAX_SIZE);
		tess.color(1, 1, 1, 1);

		dest3f = new Vector3f();
		lastColor = new Vector4f();
		lastColor.set(1, 1, 1, 1);
	}

	public BBModelTess pos(float x, float y, float z)
	{
		stack.transformPosition(x, y, z, dest3f);
		tess.pos(dest3f);
		return this;
	}

	public BBModelTess color(int argb)
	{
		float alpha = ColorUtil.getAlpha(argb) * INV_255;
		float red = ColorUtil.getRed(argb) * INV_255;
		float green = ColorUtil.getGreen(argb) * INV_255;
		float blue = ColorUtil.getBlue(argb) * INV_255;

		return color(red, green, blue, alpha);
	}

	public BBModelTess color(float r, float g, float b, float a)
	{
		tess.color(r, g, b, a);
		lastColor.set(r, g, b, a);
		return this;
	}

	public BBModelTess color(Vector4f color)
	{
		tess.color(color.x, color.y, color.z, color.w);
		lastColor.set(color);
		return this;
	}

	public BBModelTess uv(float u, float v)
	{
		tess.uv(u, v);
		return this;
	}

	public BBModelTess uv(Vector2f uv)
	{
		tess.uv(uv.x, uv.y);
		return this;
	}

	public BBModelTess normal(float nx, float ny, float nz)
	{
		stack.pushMatrix();
		stack.rotateY((float) (Math.PI / 2f));
		stack.setTranslation(0, 0, 0);
		stack.transformPosition(nx, ny, nz, dest3f);
		dest3f.normalize();
		tess.normal(dest3f);
		stack.popMatrix();
		return this;
	}

	public Vector4f getLastColor()
	{
		return lastColor;
	}

	public BBModelTess endVertex()
	{
		tess.endVertex();
		return this;
	}

	@Override
	public void render(Matrix4f view, StaticShader3D shader3D, StaticTexture entityTexture)
	{
		shader3D.bind(view);
		shader3D.setTransformation(new Matrix4f());
		shader3D.setUniform(BBShader.NORMAL_MATRIX, new Matrix3f(new Matrix4f(stack).invert().transpose3x3()));
		entityTexture.bind();

		tess.loadPos(0);
		tess.loadColor(1);
		tess.loadUv(2);
		tess.loadNormal(3);
		tess.draw(Tessellator.TRIANGLES);
		tess.disable(0, 1, 2, 3);
	}

	@Override
	public void reset()
	{
		tess.clear();
		tess.begin(MAX_SIZE);
		tess.color(1, 1, 1, 1);
		lastColor.set(1, 1, 1, 1);
	}

	public void rectangle(float w, float h, int tile)
	{
		final float inv = 1f / 256f;

		int x = (tile % 16) * 16;
		int y = (tile >> 4) * 16;

		BiConsumer<Integer, Integer> uv = (uvx, uvy) -> uv(inv * uvx, inv * uvy);

		uv.accept(x, y);
		pos(0, 0, 0).endVertex();
		uv.accept(x, y + 16);
		pos(0, 0, h).endVertex();
		uv.accept(x + 16, y + 16);
		pos(w, 0, h).endVertex();

		uv.accept(x + 16, y + 16);
		pos(w, 0, h).endVertex();
		uv.accept(x + 16, y);
		pos(w, 0, 0).endVertex();
		uv.accept(x, y);
		pos(0, 0, 0).endVertex();
	}

	public void box(float x, float y, float z, float w, float h, float d)
	{
		uv(1f / 256f * 2f, 1f / 256f * 2f);

		// UP
		normal(0, 1, 0);
		pos(x + w, y + h, z).endVertex();
		pos(x, y + h, z).endVertex();
		pos(x, y + h, z + d).endVertex();

		pos(x, y +h, z +d).endVertex();
		pos(x +w, y +h, z +d).endVertex();
		pos(x +w, y +h, z).endVertex();


		// DOWN
		normal(0, -1, 0);
		pos(x, y +h, z).endVertex();
		pos(x, y, z).endVertex();
		pos(x, y, z +d).endVertex();

		pos(x, y, z +d).endVertex();
		pos(x, y +h, z +d).endVertex();
		pos(x, y +h, z).endVertex();


		// NORTH
		normal(1, 0, 0);
		pos(x, y +h, z +d).endVertex();
		pos(x, y, z +d).endVertex();
		pos(x +w, y, z +d).endVertex();

		pos(x +w, y, z +d).endVertex();
		pos(x +w, y +h, z +d).endVertex();
		pos(x, y +h, z +d).endVertex();


		// EAST
		normal(0, 0, 1);
		pos(x +w, y +h, z +d).endVertex();
		pos(x +w, y, z +d).endVertex();
		pos(x +w, y, z).endVertex();

		pos(x +w, y, z).endVertex();
		pos(x +w, y +h, z).endVertex();
		pos(x +w, y +h, z +d).endVertex();


		// SOUTH
		normal(-1, 0, 0);
		pos(x +w, y +h, z).endVertex();
		pos(x +w, y, z).endVertex();
		pos(x, y, z).endVertex();

		pos(x, y, z).endVertex();
		pos(x, y +h, z).endVertex();
		pos(x +w, y +h, z).endVertex();


		// WEST - ACTUALLY DOWN
		normal(0, 0, -1);
		pos(x, y, z + d).endVertex();
		pos(x, y, z).endVertex();
		pos(x + w, y, z).endVertex();

		pos(x + w, y, z).endVertex();
		pos(x + w, y, z + d).endVertex();
		pos(x, y, z + d).endVertex();

		color(lastColor);
	}

	public StackTessellator getTess()
	{
		return tess;
	}
}
