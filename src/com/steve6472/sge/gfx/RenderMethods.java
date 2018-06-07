/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 16. 5. 2018
* Project: AITest2
*
***********************/

package com.steve6472.sge.gfx;

import org.joml.Matrix4f;

import com.steve6472.sge.gfx.Helper;
import com.steve6472.sge.gfx.Model;
import com.steve6472.sge.gfx.Shader;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.main.Util;
import com.steve6472.sge.test.Camera;
import com.steve6472.sge.test.ShaderTest2;

public class RenderMethods
{
	private static Camera camera;
	
	private static Shader squareShader;
	private static Model fullModel;
	private static Sprite blackPixel;
	
	private static Shader currentShader;
	private static Model currentModel;
	private static Sprite currentSprite;
	
	// options
	private static boolean pixelPerfect = false;
	private static boolean autoPush = true;
	private static boolean autoPop = true;
	private static boolean autoRender = true;
	private static boolean ignoreNullColor = true;
	private static boolean useParameterColor = true;
	
	
	public static void init(Camera camera, boolean pixelPerfect)
	{
		init(camera);
		RenderMethods.pixelPerfect = pixelPerfect;
	}
	
	public static void init(Camera camera)
	{
		RenderMethods.camera = camera;
		
		squareShader = new Shader("shaders\\basev2");
		squareShader.setUniform1f("sampler", 0);
		squareShader.setUniform2f("texture", 0, 0);
		
		blackPixel = new Sprite(new int[] {0x00000000}, 1, 1);
		
		fullModel = new Model(ShaderTest2.fillScreen(), ShaderTest2.createBasicTexture(), ShaderTest2.createArray(0f));
		
		currentShader = squareShader;
		currentModel = fullModel;
		currentSprite = blackPixel;
	}
	
	//---------------------Options---------------------
	
	public static void setPixelPerfect(boolean pixelPerfect)
	{
		RenderMethods.pixelPerfect = pixelPerfect;
	}
	
	public static boolean isPixelPerfect()
	{
		return pixelPerfect;
	}
	
	public static void setAutoPush(boolean autoPush)
	{
		RenderMethods.autoPush = autoPush;
	}
	
	public static boolean isAutoPush()
	{
		return autoPush;
	}
	
	public static void setAutoPop(boolean autoPop)
	{
		RenderMethods.autoPop = autoPop;
	}
	
	public static boolean isAutoPop()
	{
		return autoPop;
	}
	
	public static void setAutoRender(boolean autoRender)
	{
		RenderMethods.autoRender = autoRender;
	}
	
	public static boolean isAutoRender()
	{
		return autoRender;
	}
	
	public static void setIgnoreNullColor(boolean ignoreNullColor)
	{
		RenderMethods.ignoreNullColor = ignoreNullColor;
	}
	
	public static boolean isIgnoreNullColor()
	{
		return ignoreNullColor;
	}
	
	public static void setUseParameterColor(boolean useParameterColor)
	{
		RenderMethods.useParameterColor = useParameterColor;
	}
	
	public static boolean isUseParameterColor()
	{
		return useParameterColor;
	}
	
	//-----------------------Preparations-------------------------------
	
	public static void prepare()
	{
		blackPixel.bind();
		squareShader.bind();
		
		currentSprite = blackPixel;
		currentShader = squareShader;
		currentModel = fullModel;
	}
	
	public static void prepare(Sprite sprite)
	{
		sprite.bind();
		currentSprite = sprite;
	}
	
	public static void prepare(Shader shader)
	{
		shader.bind();
		currentShader = shader;
	}
	
	public static void prepare(Model model)
	{
		currentModel = model;
	}
	
	public static void prepare(Sprite sprite, Shader shader, Model model)
	{
		prepare(sprite);
		prepare(shader);
		prepare(model);
	}
	
	public static Model getCurrentModel()
	{
		return currentModel;
	}
	
	public static Sprite getCurrentSprite()
	{
		return currentSprite;
	}
	
	public static Shader getCurrentShader()
	{
		return currentShader;
	}
	
	//----------------------------Utils------------------------------
	
	protected static void translate(float x, float y, float w, float h)
	{
		if (isPixelPerfect())
		{
			Helper.translate((float) camera.getWidth() / 2f - w / 2f, (float) camera.getHeight() / 2f - h / 2f, 0);
			Helper.translate(-x, -y, 0);
		} else
		{
			Helper.translate(x, y, 0);
		}
	}
	
	protected static void push()
	{
		if (isAutoPush())
			Helper.pushLayer();
	}
	
	protected static void pop()
	{
		if (isAutoPop())
			Helper.popLayer();
	}
	
	public static void render()
	{
		if (isAutoRender())
			currentModel.render();
	}
	
	public static void forceRender()
	{
		currentModel.render();
	}
	
	public static void color(int color)
	{
		if ((!isIgnoreNullColor() || color != 0) && isUseParameterColor())
			Helper.color(color);
	}
	
	//-----------------------------Drawing--------------------------------
	
	public static void drawSquare(float x, float y, float w, float h, int color)
	{
		push();
		translate(x, y, w, h);
		Helper.scale(w / 2f, h / 2f, 0);
		color(color);

		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(Helper.toMatrix(), target);

		if (color != 0 || !isIgnoreNullColor() || !isUseParameterColor())
			currentShader.setUniform4f("col", Helper.getRed(), Helper.getGreen(), Helper.getBlue(), Helper.getAlpha());

		currentShader.setUniformMat4f("projection", target);
		
		render();
		
		pop();
	}
	
	public static void drawLine(float x0, float y0, float x1, float y1, int color)
	{
		float l = (float) Util.getDistance((int) x0, (int) y0, (int) x1, (int) y1);
		
		float w = x1 - x0;
		float h = y1 - y0;
		
		push();
		translate(x0, y0, w, h);
		Helper.rotate((float) Util.countAngle(x0, y0, x1, y1) + 90, 0, 0, 1);
		Helper.scale(l / 2f, 0.5f, 0);
		color(color);

		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(Helper.toMatrix(), target);

		if (color != 0 || !isIgnoreNullColor())
			currentShader.setUniform4f("col", Helper.getRed(), Helper.getGreen(), Helper.getBlue(), Helper.getAlpha());

		currentShader.setUniformMat4f("projection", target);
		
		render();
		
		pop();
	}

	/**
	 * Ignores auto push/pop options due to rendering line multiple times
	 * If auto render is disabled this method wont run at all
	 * @param x
	 * @param y
	 * @param edgeCount
	 * @param radius
	 * @param addAng
	 * @param color
	 */
	public static void drawCircle(float x, float y, int edgeCount, float radius, double addAng, int color)
	{
		if (!isAutoRender())
			return;
		
		int lastX = 0;
		int lastY = 0;
		boolean flag = false;
		double slice = 360d / (double) edgeCount;
		for (int i = 0; i < edgeCount + 1; i++)
		{
			double rad = Math.toRadians(i * slice + addAng);
			double sin = Math.sin(rad) * radius;
			double cos = Math.cos(rad) * radius;
			int X = (int) (x + sin);
			int Y = (int) (y + cos);
			if (flag)
			{
				if (!isAutoPush())
					Helper.pushLayer();
				
				drawLine(lastX, lastY, X, Y, color);
				
				if (!isAutoPop())
					Helper.popLayer();

				lastX = X;
				lastY = Y;
			} else
			{
				lastX = X;
				lastY = Y;
				flag = true;
			}
		}
	}
	
	public static void drawLines(int[] locations, int[] color)
	{
		if (!isAutoRender())
			return;

		int lastX = locations[0];
		int lastY = locations[1];
		
		for (int i = 2; i < locations.length; i += 2)
		{
			int X = locations[i];
			int Y = locations[i + 1];
			if (!isAutoPush())
				Helper.pushLayer();

			drawLine(lastX, lastY, X, Y, color[i / 2 - 1]);

			if (!isAutoPop())
				Helper.popLayer();

			lastX = X;
			lastY = Y;
		}
	}
	
	//----------------------Sprite render--------------------
	
	public static void drawSprite(float x, float y, float angle, Sprite sprite)
	{
		push();
		
		if (sprite != null)
			prepare(sprite);

		int w = currentSprite.getWidth();
		int h = currentSprite.getHeight();
		translate(x, y, w, h);
		Helper.rotate(angle, 0, 0, 1);
		Helper.scale(w / 2f, h / 2f, 0);

		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(Helper.toMatrix(), target);

		if (!isUseParameterColor())
			currentShader.setUniform4f("col", Helper.getRed(), Helper.getGreen(), Helper.getBlue(), Helper.getAlpha());

		currentShader.setUniformMat4f("projection", target);
		
		render();
		
		pop();
	}
	
	public static void drawSprite(float x, float y, float sx, float sy, float sz, float angle, Sprite sprite)
	{
		push();
		
		if (sprite != null)
			prepare(sprite);

		int w = currentSprite.getWidth();
		int h = currentSprite.getHeight();
		translate(x, y, w * sx, h * sy);
		Helper.scale(sx, sy, sz);
		Helper.rotate(angle, 0, 0, 1);
		Helper.scale(w / 2f, h / 2f, 0);

		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(Helper.toMatrix(), target);

		if (!isUseParameterColor())
			currentShader.setUniform4f("col", Helper.getRed(), Helper.getGreen(), Helper.getBlue(), Helper.getAlpha());

		currentShader.setUniformMat4f("projection", target);
		
		render();
		
		pop();
	}
	
	public static void drawSprite(float x, float y, float sx, float sy, float sz, Sprite sprite)
	{
		push();
		
		if (sprite != null)
			prepare(sprite);

		int w = currentSprite.getWidth();
		int h = currentSprite.getHeight();
		translate(x, y, w * sx, h * sy);
		Helper.scale(sx, sy, sz);
		Helper.scale(w / 2f, h / 2f, 0);

		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(Helper.toMatrix(), target);

		if (!isUseParameterColor())
			currentShader.setUniform4f("col", Helper.getRed(), Helper.getGreen(), Helper.getBlue(), Helper.getAlpha());

		currentShader.setUniformMat4f("projection", target);
		
		render();
		
		pop();
	}
	
	public static void drawSprite(float x, float y, Sprite sprite)
	{
		if (sprite != null)
			prepare(sprite);
		
		int w = currentSprite.getWidth();
		int h = currentSprite.getHeight();
		push();
		translate(x, y, w, h);
		Helper.scale(w / 2f, h / 2f, 0);

		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(Helper.toMatrix(), target);

		if (!isUseParameterColor())
			currentShader.setUniform4f("col", Helper.getRed(), Helper.getGreen(), Helper.getBlue(), Helper.getAlpha());

		currentShader.setUniformMat4f("projection", target);
		
		render();
		
		pop();
	}

	//--------------------------double types-----------------------------------------
	
	public static void drawSquare(double x, double y, double w, double h, int color)
	{
		drawSquare((float) x, (float) y, (float) w, (float) h, color);
	}
	
	public static void drawLine(double x0, double y0, double x1, double y1, int color)
	{
		drawLine((float) x0, (float) y0, (float) x1, (float) y1, color);
	}
	
	public static void drawCircle(double x, double y, int edgeCount, double radius, double addAng, int color)
	{
		drawCircle((float) x, (float) y, edgeCount, (float) radius, addAng, color);
	}
	
	//----------------------------double sprite types------------------------------
	
	public static void drawSprite(double x, double y, double angle, Sprite sprite)
	{
		drawSprite((float) x, (float) y, (float) angle, sprite);
	}
	
	public static void drawSprite(double x, double y, double sx, double sy, double sz, double angle, Sprite sprite)
	{
		drawSprite((float) x, (float) y, (float) sx, (float) sy, (float) sz, (float) angle, sprite);
	}
	
	public static void drawSprite(double x, double y, double sx, double sy, double sz, Sprite sprite)
	{
		drawSprite((float) x, (float) y, (float) sx, (float) sy, (float) sz, sprite);
	}
	
	public static void drawSprite(double x, double y, Sprite sprite)
	{
		drawSprite((float) x, (float) y, sprite);
	}
	
}
