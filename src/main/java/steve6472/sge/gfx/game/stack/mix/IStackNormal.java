package steve6472.sge.gfx.game.stack.mix;

import steve6472.sge.gfx.game.stack.buffer.Buffer3f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public interface IStackNormal<T> extends IMain<T>, IStack
{
	Buffer3f getNormalBuffer();

	default T normal(float nx, float ny, float nz)
	{
		// Todo: keep ?
		TempValues.TEMP_MATRIX4F.identity();
		//TempValues.TEMP_MATRIX4F.rotateY((float) (Math.PI / 2f));
		TempValues.TEMP_MATRIX4F.transformPosition(nx, ny, nz, TempValues.TEMP_VECTOR);
		TempValues.TEMP_VECTOR.normalize();
		getNormalBuffer().set(TempValues.TEMP_VECTOR);
		return getTess();
	}
}
