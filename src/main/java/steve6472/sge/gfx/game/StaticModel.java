package steve6472.sge.gfx.game;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import steve6472.sge.gfx.StaticTexture;
import steve6472.sge.gfx.VertexObjectCreator;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.stack.RenderType;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.stack.tess.AbstractTess;
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
		RenderType blockbench = stack.getRenderType("blockbench");
		AbstractTess tess = blockbench.getTess();
		VertexObjectCreator.storeFloatDataInAttributeList(0, 3, tess.getBuffers()[0].getBuffer());
		VertexObjectCreator.storeFloatDataInAttributeList(1, 4, tess.getBuffers()[1].getBuffer());
		VertexObjectCreator.storeFloatDataInAttributeList(2, 2, tess.getBuffers()[2].getBuffer());
		VertexObjectCreator.storeFloatDataInAttributeList(3, 3, tess.getBuffers()[3].getBuffer());
		VertexObjectCreator.unbindVAO();
		staticModel.vertexCount = tess.getBuffers()[0].getBuffer().position() / 3;
		return staticModel;
	}

	public void render(Matrix4f transformation, Matrix4f view, BBShader shader, StaticTexture texture)
	{
		shader.bind(view);
		shader.setTransformation(transformation);
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
