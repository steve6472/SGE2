package com.steve6472.sge.test;

public class DynamicModel
{

  private FloatBuffer vert;
  private IntBuffer tex;
  private FloatBuffer color;

  private List<Float> vertC;
  private List<Integer> texC;
  private List<Float> colorC;
  
  public DynamicModel()
  {
  	vertC = new ArrayList<Float>();
  	texC = new ArrayList<Integer>();
  	colorC = new ArrayList<Float>();
  }

  public DynamicModel(FloatBuffer vert, IntBuffer tex, FloatBuffer color)
  {
  	this.vert = vert;
  	this.tex = tex;
 	this.color = color;
  }
  
  public void generate()
  {
  	FloatBuffer fb = BufferUtils.generateFloatBuffer(vertC.size());
  	for (float f : vertC)
	{
		fb.add(f);
	}
  }
  
  public void add(float vx, float vy, float vz, int tx, int ty, float cr, float cg, float cb, float ca)
  {
  	vertC.add(vx);
  	vertC.add(vy);
    	vertC.add(vz);
    
    	texC.add(tx);
   	texC.add(ty);
    
    	colorC.add(cr);
   	colorC.add(cg);
   	colorC.add(cb);
   	colorC.add(ca);
  }
  
  public void add(float vx, float vy, float vz, int tx, int ty)
  {
  	add(vx, vy, vz, tx, ty, 1, 1, 1, 1);
  }
  
  public void add(float vx, float vy, float vz, float cr, float cg, float cb, float ca)
  {
  	add(vx, vy, vt, 0, 0, ce, cg, cb, ca)
  }
}
