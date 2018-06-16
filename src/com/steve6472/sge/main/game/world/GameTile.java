/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 4. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.game.world;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import org.joml.Matrix4f;

import com.steve6472.sge.gfx.Camera;
import com.steve6472.sge.gfx.Helper;
import com.steve6472.sge.gfx.Model;
import com.steve6472.sge.gfx.SavedShaders;
import com.steve6472.sge.gfx.Shader;
import com.steve6472.sge.main.game.Atlas;
import com.steve6472.sge.test.ShaderTest2;

public class GameTile
{
	/*
	 * Texture variables
	 */
//	int indexX;
//	int indexY;
	
	/*
	 * Static Tile variables
	 */
	static boolean inited = false;
	static Atlas tileAtlas;
	public static int tileWidth;
	public static int tileHeight;
	static Model tileModel;
	public static Shader baseShader;
	static int lastId;
	static float multiplyFactor = 1f;
	
	public static void initGameTiles(Atlas tileAtlas, int tileWidth, int tileHeight)
	{
		GameTile.inited = true;
		GameTile.tileAtlas = tileAtlas;
		GameTile.tileWidth = tileWidth;
		GameTile.tileHeight = tileHeight;
		GameTile.baseShader = new Shader(SavedShaders.TILE_VS, SavedShaders.TILE_FS);
		GameTile.tileModel = new Model(ShaderTest2.fillScreen(), ShaderTest2.createTexture(tileWidth, tileHeight, tileAtlas.getAtlas()), ShaderTest2.createArray(0));
	}
	
	public static void prepare()
	{
		GameTile.tileAtlas.getAtlas().bind(0);
		GameTile.baseShader.bind();
		GameTile.baseShader.setUniform1f("sampler", 0);
	}
	
	public static void setMultiplyFactor(float multiplyFactor)
	{
		GameTile.multiplyFactor = multiplyFactor;
	}
	
	public static float getMultiplyFactor()
	{
		return multiplyFactor;
	}
	
	public static void quickRender(float x, float y, int tileId, Camera camera)
	{
		float tileIndexX = (tileId % tileAtlas.getSize()) / (float) tileAtlas.getSize();
		float tileIndexY = (tileId / tileAtlas.getSize()) / (float) tileAtlas.getSize();
		
		Helper.pushLayer();

		Helper.translate(camera.getWidth() / 2 - tileWidth / 2, camera.getHeight() / 2 - tileHeight / 2, 0);
		Helper.scale(tileWidth / 2f * multiplyFactor, tileHeight / 2f * multiplyFactor, 0f);
		Helper.translate((float) camera.getX() / (float) tileWidth * 2f, (float) camera.getY() / (float) tileHeight * 2f, 0);
		
		Helper.translate(x * -2, y * -2, 0);
		
		drawSpriteFromAtlas(tileIndexX, tileIndexY, tileId, camera);
		
		Helper.popLayer();
	}
	
	private static boolean smartRender = false;

	public static void startSmartRender()
	{
		if (smartRender)
		{
			throw new NullPointerException("(not) \n smart render has not been disabled! Stopping application!");
		}

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		smartRender = true;
	}
	
	public static void stopSmartRender()
	{
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		
		smartRender = false;
	}
	
	public static void smartRender(float x, float y, int tileId, Camera camera)
	{
		if (!smartRender)
		{
			throw new NullPointerException("(not) \n smart render has not been enabled! Stopping application!");
		}
		
		float tileIndexX = (tileId % tileAtlas.getSize()) / (float) tileAtlas.getSize();
		float tileIndexY = (tileId / tileAtlas.getSize()) / (float) tileAtlas.getSize();
		
		Helper.pushLayer();

		Helper.translate(camera.getWidth() / 2 - tileWidth / 2, camera.getHeight() / 2 - tileHeight / 2, 0);
		Helper.scale(tileWidth / 2f * multiplyFactor, tileHeight / 2f * multiplyFactor, 0f);
		Helper.translate((float) camera.getX() / (float) tileWidth * 2f, (float) camera.getY() / (float) tileHeight * 2f, 0);
		
		Helper.translate(x * -2, y * -2, 0);
		
		drawSpriteFromAtlasSmartVersion(tileIndexX, tileIndexY, tileId, camera);
		
		Helper.popLayer();
	}
	
	public static void drawSpriteFromAtlasSmartVersion(float indexX, float indexY, int tileId, Camera camera)
	{
		if (!Helper.isInitialised())
			return;
		
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(Helper.toMatrix(), target);
		
		if (tileId != lastId)
		{
			baseShader.setUniform2f("texture", indexX, indexY);
			lastId = tileId;
		}
		
		baseShader.setUniformMat4f("projection", target);
		
		glBindBuffer(GL_ARRAY_BUFFER, tileModel.getvId());
		glVertexPointer(2, GL_FLOAT, 0, 0);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, tileModel.gettId());
		glTexCoordPointer(2, GL_FLOAT, 0, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, tileModel.getcId());
		glColorPointer(4, GL_FLOAT, 0, 0);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, 0);

		glDrawArrays(GL_TRIANGLES, 0, tileModel.getDrawCount());
		
	}
	
	public static void drawSpriteFromAtlas(float indexX, float indexY, int tileId, Camera camera)
	{
		if (!Helper.isInitialised())
			return;
		
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(Helper.toMatrix(), target);
		
		if (tileId != lastId)
		{
			baseShader.setUniform2f("texture", indexX, indexY);
			lastId = tileId;
		}
		
		baseShader.setUniformMat4f("projection", target);
		
		tileModel.render();
	}
	
	static void check()
	{
		if (!inited)
		{
			throw new NullPointerException("Game Tile has not been initialized!");
		}
	}
}
