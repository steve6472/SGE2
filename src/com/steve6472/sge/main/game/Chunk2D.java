package com.steve6472.sge.main.game;

import com.steve6472.sge.gfx.Atlas;
import com.steve6472.sge.gfx.VertexObjectCreator;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 26.03.2019
 * Project: SJP
 *
 ***********************/
public class Chunk2D
{
	protected final int chunkSize;
	
	protected int vao;   // VAO
	protected int v_vbo; // Vertex VBO

	protected int vertexCount;

	protected Atlas tileAtlas;

	protected List<Layer> layers;

	public Chunk2D(Atlas tileAtlas, int chunkSize)
	{
		layers = new ArrayList<>();
		this.tileAtlas = tileAtlas;
		this.chunkSize = chunkSize;
		vertexCount = 6 * chunkSize * chunkSize;
	}

	public void init(int textureLayers)
	{
		vao = VertexObjectCreator.createVAO();

		List<Float> vertices = buildVertices();
		v_vbo = VertexObjectCreator.storeFloatDataInAttributeList(0, 2, vertices);

		for (int i = 0; i < textureLayers; i++)
		{
			int[] tiles = new int[chunkSize * chunkSize];
			List<Float> textures = buildTextures(tiles);
			int t_vbo = VertexObjectCreator.storeFloatDataInAttributeList(1 + i, 2, textures);
			Layer l = new Layer(tiles, t_vbo);
			layers.add(l);
		}

		VertexObjectCreator.unbindVAO();
	}

	public void setTileId(int x, int y, int layer, int tile)
	{
		layers.get(layer).tiles[x * chunkSize + y] = tile;
	}

	public int getTileId(int x, int y, int layer)
	{
		return layers.get(layer).getTiles()[x * chunkSize + y];
	}

	public Layer getLayer(int layer)
	{
		return layers.get(layer);
	}

	protected List<Float> buildVertices()
	{
		List<Float> vertices = new ArrayList<>();

		for (int x = 0; x < chunkSize; x++)
		{
			for (int y = chunkSize - 1; y >= 0; y--)
			{
				{
					vertices.add((float) x); vertices.add((float) y + 1f - chunkSize);
					vertices.add((float) x); vertices.add((float) y - chunkSize);
					vertices.add((float) x + 1f); vertices.add((float) y - chunkSize);
				}
				{
					vertices.add((float) x + 1f); vertices.add((float) y - chunkSize);
					vertices.add((float) x + 1f); vertices.add((float) y + 1f - chunkSize);
					vertices.add((float) x); vertices.add((float) y + 1f - chunkSize);
				}
			}
		}

		return vertices;
	}

	protected List<Float> buildTextures(int[] tiles)
	{
		List<Float> textures = new ArrayList<>();

		float f = 1f / (float) tileAtlas.getTileCount();

		for (int x = 0; x < chunkSize; x++)
		{
			for (int y = 0; y < chunkSize; y++)
			{
				float tx = (float) (tiles[x * chunkSize + y] % tileAtlas.getTileCount()) / (float) tileAtlas.getTileCount();
				float ty = (float) (tiles[x * chunkSize + y] / tileAtlas.getTileCount()) / (float) tileAtlas.getTileCount();

				textures.add(tx); textures.add(ty);
				textures.add(tx); textures.add(ty + f);
				textures.add(tx + f); textures.add(ty + f);

				textures.add(tx + f); textures.add(ty + f);
				textures.add(tx + f); textures.add(ty);
				textures.add(tx); textures.add(ty);
			}
		}

		return textures;
	}

	public void retextureAll()
	{
		VertexObjectCreator.bindVAO(vao);
		for (int i = 0; i < layers.size(); i++)
		{
			List<Float> textures = buildTextures(layers.get(i).getTiles());
			VertexObjectCreator.storeFloatDataInAttributeList(1 + i, 2, layers.get(i).getVbo(), textures);
		}
		VertexObjectCreator.unbindVAO();
	}

	public void retexture(int layer, int[] tiles)
	{
		VertexObjectCreator.bindVAO(vao);
		List<Float> textures = buildTextures(layers.get(layer).getTiles());
		VertexObjectCreator.storeFloatDataInAttributeList(1 + layer, 2, layers.get(layer).getVbo(), textures);
		VertexObjectCreator.unbindVAO();
	}

	public void render()
	{
		glBindVertexArray(vao);

		for (int i = 0; i < layers.size() + 1; i++)
			glEnableVertexAttribArray(i);

		glDrawArrays(GL_TRIANGLES, 0, vertexCount);

		for (int i = 0; i < layers.size() + 1; i++)
			glDisableVertexAttribArray(i);

		glBindVertexArray(0);
	}

	public void render(int layer)
	{
		glBindVertexArray(vao);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(layer + 1);

		glDrawArrays(GL_TRIANGLES, 0, vertexCount);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(layer + 1);

		glBindVertexArray(0);
	}

	public int getChunkSize()
	{
		return chunkSize;
	}

	public class Layer
	{
		int[] tiles;
		int vbo;

		public Layer(int[] tiles, int vbo)
		{
			this.tiles = tiles;
			this.vbo = vbo;
		}

		public int[] getTiles()
		{
			return tiles;
		}

		public int getVbo()
		{
			return vbo;
		}
	}
}
