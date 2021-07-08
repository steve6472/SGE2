package steve6472.sge.gfx.game.stack.mix;

import steve6472.sge.gfx.game.stack.buffer.Buffer4f;
import steve6472.sge.main.util.ColorUtil;

import static steve6472.sge.main.util.MathUtil.INV_255;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public interface IColor4<T> extends IMain<T>
{
	Buffer4f getColorBuffer();

	default T color(float r, float g, float b, float a)
	{
		getColorBuffer().set(r, g, b, a);
		return getTess();
	}

	default T color(int argb)
	{
		float alpha = ColorUtil.getAlpha(argb) * INV_255;
		float red = ColorUtil.getRed(argb) * INV_255;
		float green = ColorUtil.getGreen(argb) * INV_255;
		float blue = ColorUtil.getBlue(argb) * INV_255;

		return color(red, green, blue, alpha);
	}
}
