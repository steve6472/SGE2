/**********************
* Created by steve6472
* On date: 9. 6. 2018
* Project: SGE2
*
***********************/
/*
package com.steve6472.sge.test;

import com.steve6472.sge.gfx.*;
import com.steve6472.sge.main.MainApp;
import javafx.scene.Camera;
import org.joml.Matrix4f;

public class Test3D_SACRET_VERSION__DO_NOT_TUCH extends MainApp
{

	public Test3D_SACRET_VERSION__DO_NOT_TUCH()
	{
	}
	
	Shader shader;
	Model3 model;
	Sprite sprite;
	Camera camera;

	@Override
	public void init()
	{
		
		float p = +1f;
		float n = -1f;

		float[] vert = new float[]
					{
						//Front
						n, n, p,
						p, n, p,
						p, p, p,
						
						p, p, p,
						n, p, p,
						n, n, p,
						
						//Back
						n, n, n,
						p, n, n,
						p, p, n,
						
						p, p, n,
						n, p, n,
						n, n, n,
						
						//Top
						n, p, n,
						p, p, n,
						p, p, p,
						
						p, p, p,
						n, p, p,
						n, p, n,
						
						//Side 1
						p, n, p,
						p, n, n,
						p, p, n,
						
						p, p, n,
						p, p, p,
						p, n, p,
						
						//Side 2
						n, n, p,
						n, n, n,
						n, p, n,
						
						n, p, n,
						n, p, p,
						n, n, p,
						
						//Bottom
						n, n, n,
						p, n, n,
						p, n, p,
						
						p, n, p,
						n, n, p,
						n, n, n,
					};
		
		float[] text = new float[]
				{
					//
					1, 1,
					0, 1,
					0, 0,
					
					0, 0,
					1, 0,
					1, 1,
					
					//
					1, 1,
					0, 1,
					0, 0,
					
					0, 0,
					1, 0,
					1, 1,
					
					//
					1, 1,
					0, 1,
					0, 0,
					
					0, 0,
					1, 0,
					1, 1,
					
					//
					1, 1,
					0, 1,
					0, 0,
					
					0, 0,
					1, 0,
					1, 1,
					
					//
					1, 1,
					0, 1,
					0, 0,
					
					0, 0,
					1, 0,
					1, 1,
					
					//
					1, 1,
					0, 1,
					0, 0,
					
					0, 0,
					1, 0,
					1, 1,
				};
		
		float[] color = new float[]
				{
				};
		
		shader = new Shader(SavedShaders.SHADER_3D_VS, SavedShaders.SHADER_3D_FS);
		model = new Model3(vert, text, color);
		camera = new Camera();
		sprite = new Sprite("*grass.png");
		
		shader.setUniform1i("sampler", 0);
		shader.setUniform2i("texture", 0, 0);
		shader.setUniform4f("col", 0, 0, 0, 0);
		
		sprite.bind();
		shader.bind();
		
		Helper.initHelper();
		
//		resetOrtho();
		
//		glMatrixMode(GL_PROJECTION);
//		glLoadIdentity();
//		glOrtho(-1, 1, -1, 1, -100, 100000); // 2D projection matrix
//		glMatrixMode(GL_MODELVIEW);
		
//		perspectiveGL(85, 16f / 9f, -10f, 1f);
	}

	@Override
	public void tick()
	{
		camera.setSize(getWidth(), getHeight());
	}
	
	float rotX, rotY;

	/*
	 * 9.6.2018
	 * 22:38:21
	 * Cube renderSprite: successful
	 * Yatta!
	 */
	/*
	@Override
	public void renderSprite()
	{
//		Tessellator3D tess = Tessellator3D.INSTANCE;
		
		Helper.pushLayer();

//		Helper.scale(64, 64, 0);
		
		rotX++;
		rotY++;
		
		Helper.translate(0, 0, -5);
		
		Helper.rotate(getMouseX(), 0, 1, 0);
		Helper.rotate(getMouseY(), 1, 0, 0);
		
//		Helper.rotate(rotX, 1, 0, 0);
//		Helper.rotate(rotY, 0, 1, 0);
//		Helper.rotate(-rotY, 0, 0, 1);

//		camera.getProjection().mul(Helper.toMatrix(), target);
		
//		target.mul(createTransMat(0, 0, 0, 0, 0, 0, 0), Helper.toMatrix());

		shader.setUniformMat4f("transformation",  Helper.toMatrix());
		shader.setUniformMat4f("projection", createProjectionMatrix());
		
		model.renderSprite();
		
		Helper.popLayer();
	}
	
	private static float FOV = 85;
	private static float NEAR_PLANE = 0.1f;
	private static float FAR_PLANE = 1000f;

	private Matrix4f createProjectionMatrix()
	{
		float aspectRatio = (float) camera.getWindowWidth() / (float) camera.getWindowHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		Matrix4f projectionMatrix = new Matrix4f();
		projectionMatrix._m00(x_scale);
		projectionMatrix._m11(y_scale);
		projectionMatrix._m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
		projectionMatrix._m23(-1);
		projectionMatrix._m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
		projectionMatrix._m33(0);
		return projectionMatrix;
	}

	@Override
	public void setWindowHints()
	{
	}

	@Override
	public int getWindowWidth()
	{
		return 16 * 50;
	}

	@Override
	public int getWindowHeight()
	{
		return 9 * 50;
	}

	@Override
	public void exit()
	{
	}

	@Override
	public String getTitle()
	{
		return "3D Test";
	}
	
	public static void main(String[] args)
	{
		new Test3D_SACRET_VERSION__DO_NOT_TUCH();
	}
	
	@Override
	protected boolean disableAutoPixelOrtho()
	{
		return true;
	}

}
*/