/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 18. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test;

import org.joml.Matrix4f;

import com.steve6472.sge.gfx.Camera;
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
	int chunkSize = 8;
	
	public Chunk(MainApplication ma, Camera camera)
	{
		this.ma = ma;
		this.camera = camera;
		scale = 16;
		arr = new int[chunkSize * chunkSize];
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = Util.getRandomInt(7, 0);
//			arr[i] = 0;
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
				renderTile(x, y, arr[x + y * chunkSize], ShaderTest2.matTest, pro, camera, Util.getRandomInt(3, 0, Util.locationValue(x, y)));
			}
		}
	}
	
	private void renderTile(float x, float y, int tileId, Shader shader, Matrix4f worldMat, Camera camera, int rotation)
	{
//		rotation = 3;
		float tileIndexX = (tileId % 4) / 4f;
		float tileIndexY = (tileId / 4) / 4f;
		
		Matrix4f tilePos = new Matrix4f().translate(x * 2, y * 2, 0);
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(worldMat, target);
		target.mul(tilePos);
		
		shader.setUniform2f("texture", tileIndexX, tileIndexY);
		
		shader.setUniformMat4f("projection", target);
		
		switch(rotation)
		{
			default: ShaderTest2.matModel0.render(); break;
			case 1 : ShaderTest2.matModel1.render(); break;
			case 2 : ShaderTest2.matModel2.render(); break;
			case 3 : ShaderTest2.matModel3.render(); break;
		}
	}
}
