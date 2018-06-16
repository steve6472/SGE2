/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 10. 6. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test.dof3;

import com.steve6472.sge.gfx.Model3;
import com.steve6472.sge.main.Util;

public class Cube
{
	
	public static float[] createFrontCubeFace(float n, float p, float x, float y, float z)
	{
		return new float[]
		{
				// Front
				n + x, n + y, p + z,
				p + x, n + y, p + z,
				p + x, p + y, p + z,

				p + x, p + y, p + z,
				n + x, p + y, p + z,
				n + x, n + y, p + z, };
	}

	public static float[] createBackCubeFace(float n, float p, float x, float y, float z)
	{
		return new float[]
		{
				// Back
				n + x, n + y, n + z,
				p + x, n + y, n + z,
				p + x, p + y, n + z,

				p + x, p + y, n + z,
				n + x, p + y, n + z,
				n + x, n + y, n + z, };
	}

	public static float[] createTopCubeFace(float n, float p, float x, float y, float z)
	{
		return new float[]
		{
				// Top
				n + x, p + y, n + z,
				p + x, p + y, n + z,
				p + x, p + y, p + z,

				p + x, p + y, p + z,
				n + x, p + y, p + z,
				n + x, p + y, n + z, };
	}

	public static float[] createSide1CubeFace(float n, float p, float x, float y, float z)
	{
		return new float[]
		{
				// Side 1
				p + x, n + y, p + z,
				p + x, n + y, n + z,
				p + x, p + y, n + z,

				p + x, p + y, n + z,
				p + x, p + y, p + z,
				p + x, n + y, p + z, };
	}

	public static float[] createSide2CubeFace(float n, float p, float x, float y, float z)
	{
		return new float[]
		{
				// Side 2
				n + x, n + y, p + z,
				n + x, n + y, n + z,
				n + x, p + y, n + z,

				n + x, p + y, n + z,
				n + x, p + y, p + z,
				n + x, n + y, p + z, };
	}

	public static float[] createBottomCubeFace(float n, float p, float x, float y, float z)
	{
		return new float[]
		{
				// Bottom
				n + x, n + y, n + z,
				p + x, n + y, n + z,
				p + x, n + y, p + z,

				p + x, n + y, p + z,
				n + x, n + y, p + z,
				n + x, n + y, n + z, };
	}

	public static int[] tex = new int[]
			{
					1, 1,
					0, 1,
					0, 0,
					
					0, 0,
					1, 0,
					1, 1,
			};

	
	static float s1 = -0.02f;
	static float s2 = -0.05f;
	static float s3 = -0.1f;
	
	public static float[] shadeFrontBack()
	{
		return new float[]
			{
					s1, s1, s1, 1,
					s1, s1, s1, 1,
					s1, s1, s1, 1,
				 
					s1, s1, s1, 1,
					s1, s1, s1, 1,
					s1, s1, s1, 1,
			};
	}
	
	public static float[] shadeSides()
	{
		return new float[]
			{
					s2, s2, s2, 1,
					s2, s2, s2, 1,
					s2, s2, s2, 1,
				 
					s2, s2, s2, 1,
					s2, s2, s2, 1,
					s2, s2, s2, 1,
			};
	}
	
	public static float[] shadeBottom()
	{
		return new float[]
			{
					s3, s3, s3, 1,
					s3, s3, s3, 1,
					s3, s3, s3, 1,
				 
					s3, s3, s3, 1,
					s3, s3, s3, 1,
					s3, s3, s3, 1,
			};
	}
	
	public static float[] shadeTop()
	{
		return new float[]
			{
					0, 0, 0, 1,
					0, 0, 0, 1,
					0, 0, 0, 1,

					0, 0, 0, 1,
					0, 0, 0, 1,
					0, 0, 0, 1,
			};
	}
	
	public static Model3 createCubeModel()
	{
		int[] tex_ = connectArraysi(tex, tex, tex, tex, tex, tex);
		
		float[] text = new float[tex_.length];
		
		int index = 0;
		for (int i : tex_)
		{
			text[index] = i;
			index++;
		}
		
		float p = 1;
		float n = 0;
		
		float[] stage5 = connectArraysf(			
				createFrontCubeFace(p, n, 0, 0, 0), 
				createBackCubeFace(p, n, 0, 0, 0),
				createSide1CubeFace(p, n, 0, 0, 0),
				createSide2CubeFace(p, n, 0, 0, 0),
				createBottomCubeFace(p, n, 0, 0, 0),
				createTopCubeFace(p, n, 0, 0, 0)
				);
		
		float[] color = connectArraysf(
				shadeFrontBack(), 
				shadeFrontBack(), 
				shadeSides(), 
				shadeSides(), 
				shadeBottom(), 
				shadeTop()
				);
		
		return new Model3(stage5, text, color);
	}
	
	public static float[] connectArraysf(float[]... in)
	{
		float[] out = new float[0];
		for (float[] f : in)
		{
			out = Util.combineArrays(out, f);
		}
		return out;
	}
	
	public static int[] connectArraysi(int[]... in)
	{
		int[] out = new int[0];
		for (int[] f : in)
		{
			out = Util.combineArrays(out, f);
		}
		return out;
	}
	public static final Model3 CUBE = createCubeModel();
}
