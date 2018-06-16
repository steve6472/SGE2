/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 5. 10. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.test.dof3;

public class NoiseParam
{
	public int octaves;
	public int amplitude;
	public int smoothness;
	public int heightOffset;
	
	public double roughness;
	
	public NoiseParam(int octaves, int amplitude, int smoothness, int heightOffset, double roughness)
	{
		super();
		this.octaves = octaves;
		this.amplitude = amplitude;
		this.smoothness = smoothness;
		this.heightOffset = heightOffset;
		this.roughness = roughness;
	}

	public void setParameters(int octaves, int amplitude, int smoothness, int heightOffset, double roughness)
	{
		this.octaves = octaves;
		this.amplitude = amplitude;
		this.smoothness = smoothness;
		this.heightOffset = heightOffset;
		this.roughness = roughness;
	}

}
