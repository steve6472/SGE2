package steve6472.sge.gfx.game.stack.mix;

import steve6472.sge.gfx.game.stack.buffer.Buffer3f;
import steve6472.sge.main.util.ColorUtil;

import static steve6472.sge.main.util.MathUtil.INV_255;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public interface IColor3<T> extends IMain<T>
{
	Buffer3f getColorBuffer();

	default T color(float r, float g, float b)
	{
		getColorBuffer().set(r, g, b);
		return getTess();
	}

	default T color(int rgb)
	{
		float red = ColorUtil.getRed(rgb) * INV_255;
		float green = ColorUtil.getGreen(rgb) * INV_255;
		float blue = ColorUtil.getBlue(rgb) * INV_255;

		return color(red, green, blue);
	}
}
