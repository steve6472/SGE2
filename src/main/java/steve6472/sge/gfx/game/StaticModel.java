package steve6472.sge.gfx.game;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import steve6472.sge.gfx.StaticTexture;
import steve6472.sge.gfx.VertexObjectCreator;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.shaders.BBShader;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/5/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class StaticModel
{
	private int vao;
	private int vertexCount;

	private StaticModel()
	{

	}

	public static StaticModel fromVAO(int vao, int vertexCount)
	{
		StaticModel model = new StaticModel();
		model.vao = vao;
		model.vertexCount = vertexCount;
		return model;
	}

	public static StaticModel fromBBModel(BBModel model)
	{
		StaticModel staticModel = new StaticModel();
		Stack stack = new Stack();
		model.render(stack);
		staticModel.vao = VertexObjectCreator.createVAO();
		VertexObjectCreator.storeFloatDataInAttributeList(0, 3, stack.getBlockbenchTess().getTess().getPos());
		VertexObjectCreator.storeFloatDataInAttributeList(1, 4, stack.getBlockbenchTess().getTess().getColor());
		VertexObjectCreator.storeFloatDataInAttributeList(2, 2, stack.getBlockbenchTess().getTess().getTexture());
		VertexObjectCreator.storeFloatDataInAttributeList(3, 3, stack.getBlockbenchTess().getTess().getNormal());
		VertexObjectCreator.unbindVAO();
		staticModel.vertexCount = stack.getBlockbenchTess().getTess().getPos().position() / 3;
		return staticModel;
	}

	public void render(Matrix4f transformation, Matrix4f view, BBShader shader, StaticTexture texture)
	{
		shader.bind(view);
		shader.setTransformation(transformation);
		shader.setUniform(BBShader.NORMAL_MATRIX, new Matrix3f(new Matrix4f(transformation).invert().transpose3x3()));
		texture.bind();

		VertexObjectCreator.basicRender(getVao(), 4, getVertexCount(), GL11.GL_TRIANGLES);
	}

	public int getVao()
	{
		return vao;
	}

	public int getVertexCount()
	{
		return vertexCount;
	}
}
