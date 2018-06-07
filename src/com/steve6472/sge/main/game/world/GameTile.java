/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 4. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.game.world;

import org.joml.Matrix4f;

import com.steve6472.sge.gfx.Helper;
import com.steve6472.sge.gfx.Model;
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
	static float startX;
	static float startY;
	static int lastId;
	
	public static void initGameTiles(Atlas tileAtlas, int tileWidth, int tileHeight, Shader baseShader, float startX, float startY)
	{
		GameTile.inited = true;
		GameTile.tileAtlas = tileAtlas;
		GameTile.tileWidth = tileWidth;
		GameTile.tileHeight = tileHeight;
		GameTile.baseShader = baseShader;
		GameTile.startX = startX;
		GameTile.startY = startY;
		GameTile.tileModel = new Model(ShaderTest2.fillScreen(), ShaderTest2.createTexture(tileWidth, tileHeight, tileAtlas.getAtlas()), ShaderTest2.createArray(0));
	}
	
	public static void prepare()
	{
		GameTile.tileAtlas.getAtlas().bind(0);
		GameTile.baseShader.bind();
		GameTile.baseShader.setUniform1f("sampler", 0);
	}
	
	public static void quickRender(float x, float y, int tileId, GameCamera camera)
	{
		float tileIndexX = (tileId % tileAtlas.getSize()) / (float) tileAtlas.getSize();
		float tileIndexY = (tileId / tileAtlas.getSize()) / (float) tileAtlas.getSize();
		
		Helper.pushLayer();
		
		Helper.scale(tileWidth / 2f, tileHeight / 2f, 0f);
		Helper.translate(startX + (float) camera.getX() / (float) tileWidth * 2f, startY + (float) camera.getY() / (float) tileHeight * 2f, 0);
		
		Helper.translate(x * -2, y * -2, 0);
		
		drawSpriteFromAtlas(tileIndexX, tileIndexY, tileId, camera);
		
		Helper.popLayer();
	}
	
	public static void drawSpriteFromAtlas(float indexX, float indexY, int tileId, GameCamera camera)
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
