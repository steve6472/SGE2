/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 18. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test;

import org.joml.Matrix4f;

import com.steve6472.sge.gfx.Shader;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.Util;

public class Chunk
{
	int[] arr;
	public float scale;
	public float x, y;
	MainApplication ma;
	Camera camera;
	int chunkSize = 32;
	
	public Chunk(MainApplication ma, Camera camera)
	{
		this.ma = ma;
		this.camera = camera;
		scale = 16;
		arr = new int[chunkSize * chunkSize];
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = Util.getRandomInt(7, 0);
		}
	}
	
	int minX;
	int maxX;
	int minY;
	int maxY;
	
	public void render()
	{
//		System.out.println((int) x / scale);
		minX = 0;
		minY = 0;
		maxX = chunkSize;
		maxY = chunkSize;
		
		Matrix4f pro = new Matrix4f()
			.scale(scale)
			.translate(x, y, 0);
		
		ShaderTest2.smallAtlas.bind();
		ShaderTest2.matTest.bind();
		ShaderTest2.matTest.setUniform1i("sampler", 0);
		
		for (int x = minX; x < maxX; x++)
		{
			for (int y = minY; y < maxY; y++)
			{
				renderTile(x, y, arr[x + y * chunkSize], ShaderTest2.matTest, pro, camera);
			}
		}
	}
	
	private void renderTile(float x, float y, int tileId, Shader shader, Matrix4f worldMat, Camera camera)
	{
		float tileIndexX = (tileId % 4) / 4f;
		float tileIndexY = (tileId / 4) / 4f;
		
		Matrix4f tilePos = new Matrix4f().translate(x * 2, y * 2, 0);
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(worldMat, target);
		target.mul(tilePos);
		
		shader.setUniform2f("texture", tileIndexX, tileIndexY);
		/*
		 * This rotation can be used but.... 
		 * I have to somehow calculate the new position of tile 
		 * [1,1] => 90° => [2,1]
		 * [0,0] => 90° => [3,0]
		 * [1,1] => 180° => [2,2]
		 * ...
		 */
		shader.setUniform1f("angle", (float) Math.toRadians(90 * 0));
		
		ShaderTest2.matTest.setUniformMat4f("projection", target);
		ShaderTest2.matModel.render();
	}
}
