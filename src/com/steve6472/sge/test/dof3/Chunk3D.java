/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 4. 7. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test.dof3;

import com.steve6472.sge.gfx.Model3;
import com.steve6472.sge.gfx.Tessellator;
import com.steve6472.sge.test.DynamicModel3D;

public class Chunk3D
{
	public int cx, cz;
	public float[] heightMap;
	Model3 model;

	@SuppressWarnings("deprecation")
	public Chunk3D(DynamicModel3D worldModel, int cx, int cz)
	{
		model = new Model3();
		model.changeData(worldModel.getVert(), worldModel.getTex(), worldModel.getColor());
		worldModel = null;
		this.cx = cx;
		this.cz = cz;
	}
	
	public static void prepareRender()
	{
		Model3.start();
	}
	
	public static void stopRender()
	{
		Model3.end();
	}
	
	public void render()
	{
		Model3.bindBuffers(model);
		Model3.drawArrays(model, Tessellator.TRIANGLES);
	}
}
