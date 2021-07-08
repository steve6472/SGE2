package steve6472.sge.gfx.game.stack;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public record SmallModel(int vao, int[] vbos, int vertexCount)
{
	public void draw(int mode)
	{
		glBindVertexArray(vao);

		for (int vbo : vbos)
			glEnableVertexAttribArray(vbo);

		glDrawArrays(mode, 0, vertexCount);

		for (int vbo : vbos)
			glDisableVertexAttribArray(vbo);

		glBindVertexArray(0);
	}
}
