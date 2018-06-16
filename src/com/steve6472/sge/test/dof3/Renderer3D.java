/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 11. 6. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test.dof3;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import com.steve6472.sge.gfx.Camera;
import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.Helper;
import com.steve6472.sge.gfx.Model;
import com.steve6472.sge.gfx.PlayerLocation;
import com.steve6472.sge.gfx.Shader;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gfx.Tessellator;
import com.steve6472.sge.gfx.Tessellator3D;
import com.steve6472.sge.main.MainApplication;

public class Renderer3D
{
	static Shader shader;

	static Sprite skyboxBottom;
	static Sprite skyboxTop;
	static Sprite skyboxSide;
	
	public static void init(Shader shader)
	{
		Renderer3D.shader = shader;
		skyboxBottom = new Sprite("*skybox\\bottom.png");
		skyboxTop = new Sprite("*skybox\\top.png");
		skyboxSide = new Sprite("*skybox\\side.png");
	}
	
	public static void renderSkybox(PlayerLocation playerLocation)
	{
		Tessellator3D tess = Tessellator3D.INSTANCE;
		
		float size = 512f;

		float x = 0, y = 0, z = 0;

		x = playerLocation.getX() * 1f;
		y = playerLocation.getY() * 1f - 2f * size;
		z = playerLocation.getZ() * 1f;

		float p = size;
		float n = -size;
		
		skyboxTop.bind();
		
		float[] col = new float[] {
				0, 0, 0, 0, 
				0, 0, 0, 0, 
				0, 0, 0, 0, 
				0, 0, 0, 0, 
				0, 0, 0, 0, 
				0, 0, 0, 0,};
		
		tess.put(Cube.createTopCubeFace(n, p, x, y + size * 2f, z), Cube.tex, col);
		
		tess.render(Tessellator.TRIANGLES);

		
		skyboxBottom.bind();
		
		tess.put(Cube.createBottomCubeFace(n, p, x, y + size * 2f, z), Cube.tex, col);
		
		tess.render(Tessellator.TRIANGLES);
		
		
		
		skyboxSide.bind();
		
		tess.put(Cube.createFrontCubeFace(n, p, x, y + size * 2f, z), Cube.tex, col);
		tess.put(Cube.createBackCubeFace(n, p, x, y + size * 2f, z), Cube.tex, col);
		tess.put(Cube.createSide1CubeFace(n, p, x, y + size * 2f, z), Cube.tex, col);
		tess.put(Cube.createSide2CubeFace(n, p, x, y + size * 2f, z), Cube.tex, col);
		
		tess.render(Tessellator.TRIANGLES);
	}
	
	public static void render3DMark(float x, float y, float z)
	{
		Tessellator3D tess = Tessellator3D.INSTANCE;
		
		//X
		tess.put(0 + x, 0 + y, 0 + z, 0, 0, 1, 0, 0, 1);
		tess.put(1 + x, 0 + y, 0 + z, 0, 0, 1, 0, 0, 1);

		//Y
		tess.put(0 + x, 0 + y, 0 + z, 0, 0, 0, 1, 0, 1);
		tess.put(0 + x, 1 + y, 0 + z, 0, 0, 0, 1, 0, 1);

		//Z
		tess.put(0 + x, 0 + y, 0 + z, 0, 0, 0, 0, 1, 1);
		tess.put(0 + x, 0 + y, 1 + z, 0, 0, 0, 0, 1, 1);
		
		shader.setUniformMat4f("transformation",  new Matrix4f());
		
		GL11.glLineWidth(4f);
		tess.render(Tessellator.LINE_LOOP);
		GL11.glLineWidth(1f);
	}
	
	public static void renderWireframe(float w,float h, float d, float x, float y, float z)
	{
		Tessellator3D tess = Tessellator3D.INSTANCE;
		
		tess.put(0 + x, 0 + y, 0 + z, 1, 1, 1, 1);
		tess.put(w + x, 0 + y, 0 + z, 1, 1, 1, 1);
		tess.put(w + x, 0 + y, d + z, 1, 1, 1, 1);
		tess.put(0 + x, 0 + y, d + z, 1, 1, 1, 1);
		tess.put(0 + x, 0 + y, 0 + z, 1, 1, 1, 1);
		tess.put(0 + x, h + y, 0 + z, 1, 1, 1, 1);
		tess.put(w + x, h + y, 0 + z, 1, 1, 1, 1);
		tess.put(w + x, 0 + y, 0 + z, 1, 1, 1, 1);
		tess.put(w + x, 0 + y, d + z, 1, 1, 1, 1);
		tess.put(w + x, h + y, d + z, 1, 1, 1, 1);
		tess.put(w + x, h + y, 0 + z, 1, 1, 1, 1);
		tess.put(w + x, h + y, d + z, 1, 1, 1, 1);
		tess.put(0 + x, h + y, d + z, 1, 1, 1, 1);
		tess.put(0 + x, 0 + y, d + z, 1, 1, 1, 1);
		tess.put(0 + x, h + y, d + z, 1, 1, 1, 1);
		tess.put(0 + x, h + y, 0 + z, 1, 1, 1, 1);
		
		shader.setUniformMat4f("transformation",  new Matrix4f());
		
		GL11.glLineWidth(4f);
		tess.render(Tessellator.LINE_STRIP);
		GL11.glLineWidth(1f);
	}
	
	public static void renderCube(double x, double y, double z)
	{
		Helper.pushLayer();

		Helper.translate((float) x, (float) y, (float) z);
		
		shader.setUniformMat4f("transformation",  Helper.toMatrix());
		Cube.CUBE.render();
		
		Helper.popLayer();
		shader.setUniformMat4f("transformation",  new Matrix4f());
	}
	
	public static void renderArrays(float[] face, int[] tex, float[] color)
	{
		Tessellator3D tess = Tessellator3D.INSTANCE;
		
		tess.put(face, tex, color);
		
		tess.render(Tessellator.TRIANGLES);
	}
	
	
	
	

	
	public static void drawFont(MainApplication mainApp, String text, int x, int y, Camera camera)
	{
		float size = 1 * 4;
		
		Matrix4f pro = new Matrix4f()
				.translate(camera.getWidth() / 2f - size - x, camera.getHeight() / 2f - size - y, 0)
				.scale(size);
			
		mainApp.getFont().getFont().bind();
		Font.fontShader.bind();

		for (int i = 0; i < text.length(); i++)
		{
			int char_index = Font.chars.indexOf(text.charAt(i));
			if (char_index >= 0)
			{
				renderChar(-i * 2, 0, char_index, (int) size, pro, camera, Font.fontShader, Font.fontModel);
			}
		}
	}
	
	private static void renderChar(float x, float y, int index, int size, Matrix4f mat, Camera camera, Shader fontShader, Model fontModel)
	{
		size = 1;
		float indexX = (index % 64) / 64f;
		float indexY = (index / 64) / 64f;
		
		Matrix4f tilePos = new Matrix4f().translate(x, y, 0.25f);
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(mat, target);
		target.mul(tilePos);
		
		fontShader.setUniform2f("texture", indexX, indexY);
		
		fontShader.setUniformMat4f("projection", target);
//		fontShader.setUniform4f("col", 0.21f, 0.21f, 0.21f, 1f);
		
		fontModel.render();
	}
}
