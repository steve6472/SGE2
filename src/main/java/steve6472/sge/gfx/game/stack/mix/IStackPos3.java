package steve6472.sge.gfx.game.stack.mix;

import org.joml.Vector3f;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.stack.buffer.Buffer3f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public interface IStackPos3<T> extends IMain<T>, IStack
{
	Buffer3f getPositionBuffer();

	default T pos(float x, float y, float z)
	{
		getPositionBuffer().set(getTransformedVector(x, y, z));
		return getTess();
	}

	default T posUntransformed(float x, float y, float z)
	{
		getPositionBuffer().set(x, y, z);
		return getTess();
	}

	default T posUntransformed(Vector3f vec)
	{
		return posUntransformed(vec.x, vec.y, vec.z);
	}

	default Vector3f getTransformedVector(float x, float y, float z)
	{
		getStack().transformPosition(x, y, z, Stack.TEMP_VECTOR);
		return Stack.TEMP_VECTOR;
	}

	default Vector3f getTransformedVector(Vector3f vec)
	{
		getStack().transformPosition(vec, Stack.TEMP_VECTOR);
		return Stack.TEMP_VECTOR;
	}

	default T pos(Vector3f pos)
	{
		return pos(pos.x, pos.y, pos.z);
	}
}
