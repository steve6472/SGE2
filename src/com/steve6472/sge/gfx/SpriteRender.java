package com.steve6472.sge.gfx;

import com.steve6472.sge.gfx.font.Font;
import com.steve6472.sge.gfx.shaders.*;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.dialog.Dialog;
import com.steve6472.sge.main.MainApp;
import com.steve6472.sge.main.events.Event;
import com.steve6472.sge.main.events.WindowSizeEvent;
import com.steve6472.sge.test.Fex;
import com.steve6472.sge.test.Test;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import static com.steve6472.sge.gfx.VertexObjectCreator.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 28.04.2019
 * Project: SJP
 *
 ***********************/
public class SpriteRender
{
	private static int vao, v_vbo, t_vbo;
	private static int w, h;

	private static SpriteShader spriteShader;
	private static SpriteAtlasShader spriteAtlasShader;
	private static DoubleBorderShader doubleBorderShader;
	private static SingleBorderShader singleBorderShader;
	private static FillRectangleShader fillRectangleShader;
	private static BorderShader borderShader;
	private static CircleShader circleShader;
	private static SoftCircleShader softCircleShader;
	private static RingShader ringShader;
	public static SoftRingShader softRingShader;

	private static boolean manual = false;
	private static boolean manualSprite = false;

	public SpriteRender(MainApp main)
	{
		init(main);
		main.getEventHandler().register(this);
	}

	public static int getVao()
	{
		return vao;
	}

	@Event
	public void basicResizeOrtho(WindowSizeEvent event)
	{
		w = event.getWidth();
		h = event.getHeight();

		Matrix4f ortho = new Matrix4f().ortho(0, w, h, 0, 1, -1);

		doubleBorderShader.bind();
		doubleBorderShader.setProjection(ortho);

		singleBorderShader.bind();
		singleBorderShader.setProjection(ortho);

		fillRectangleShader.bind();
		fillRectangleShader.setProjection(ortho);

		borderShader.bind();
		borderShader.setProjection(ortho);

		spriteShader.bind();
		spriteShader.setProjection(ortho);

		spriteAtlasShader.bind();
		spriteAtlasShader.setProjection(ortho);

		circleShader.bind();
		circleShader.setProjection(ortho);

		softCircleShader.bind();
		softCircleShader.setProjection(ortho);

		ringShader.bind();
		ringShader.setProjection(ortho);

		softRingShader.bind();
		softRingShader.setProjection(ortho);

		Shader.releaseShader();
	}

	/**
	 *
	 * Doesn't work with resizing the window!
	 *
	 * @param main Main
	 */
	private static void init(MainApp main)
	{
		w = main.getWidth();
		h = main.getHeight();

		vao = createVAO();

		v_vbo = storeFloatDataInAttributeList(0, 2, new float[] { -1, +1, -1, -1, +1, -1, +1, -1, +1, +1, -1, +1 });
		t_vbo = storeFloatDataInAttributeList(1, 2, new float[] { 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1 });

		unbindVAO();

		transformation = new Matrix4f();

		spriteShader = new SpriteShader();
		spriteShader.bind();
		spriteShader.setUniform(SpriteShader.SAMPLER, 0);
		spriteShader.setTransformation(transformation);

		doubleBorderShader = new DoubleBorderShader();
		doubleBorderShader.bind();
		doubleBorderShader.setTransformation(transformation);

		singleBorderShader = new SingleBorderShader();
		singleBorderShader.bind();
		singleBorderShader.setTransformation(transformation);

		fillRectangleShader = new FillRectangleShader();
		fillRectangleShader.bind();
		fillRectangleShader.setTransformation(transformation);

		borderShader = new BorderShader();
		borderShader.bind();
		borderShader.setTransformation(transformation);

		spriteAtlasShader = new SpriteAtlasShader();
		spriteAtlasShader.bind();
		spriteAtlasShader.setUniform(SpriteAtlasShader.SAMPLER, 0);
		spriteAtlasShader.setTransformation(transformation);

		circleShader = new CircleShader();
		circleShader.bind();
		circleShader.setTransformation(transformation);

		ringShader = new RingShader();
		ringShader.bind();
		ringShader.setTransformation(transformation);

		softRingShader = new SoftRingShader();
		softRingShader.bind();
		softRingShader.setTransformation(transformation);

		softCircleShader = new SoftCircleShader();
		softCircleShader.bind();
		softCircleShader.setTransformation(transformation);

		Shader.releaseShader();
	}

	private static Matrix4f transformation;

	public static void drawSoftCircle(float x, float y, float radius, float softness, Vector4f fill)
	{
		drawSoftCircle(x, y, radius, softness, fill.x, fill.y, fill.z, fill.w);
	}

	public static void drawSoftCircle(float x, float y, float radius, float softness, float red, float green, float blue, float alpha)
	{
		start();

		transformation.identity()
				.translate(x, y, 0)
				.scale(radius, radius, 1);

		softCircleShader.bind();
		softCircleShader.setTransformation(transformation);
		softCircleShader.setUniform(SoftCircleShader.FILL, red, green, blue, alpha);
		softCircleShader.setUniform(SoftCircleShader.SOFTNESS, softness);

		renderSprite();
		end();
	}

	public static void drawSoftRing(int x, int y, float radius, float width, float softness, float red, float green, float blue, float alpha)
	{
		start();

		transformation.identity()
			.translate(x, y, 0)
			.scale(radius, radius, 1);

		softRingShader.bind();
		softRingShader.setTransformation(transformation);
		softRingShader.setUniform(SoftRingShader.FILL, red, green, blue, alpha);
		softRingShader.setUniform(SoftRingShader.SOFTNESS, softness);
		softRingShader.setUniform(SoftRingShader.HOLE, (radius - width) / (radius * 2f));

		renderSprite();
		end();
	}

	public static void drawRing(int x, int y, float radius, float width, float red, float green, float blue, float alpha)
	{
		start();

		transformation.identity()
			.translate(x, y, 0)
			.scale(radius, radius, 1);

		ringShader.bind();
		ringShader.setTransformation(transformation);
		ringShader.setUniform(RingShader.FILL, red, green, blue, alpha);
		ringShader.setUniform(RingShader.HOLE, (radius - width) / (radius * 2f));

		renderSprite();
		end();
	}

	public static void drawCircle(int x, int y, float radius, Vector4f fill)
	{
		drawCircle(x, y, radius, fill.x, fill.y, fill.z, fill.w);
	}

	public static void drawCircle(int x, int y, float radius, float red, float green, float blue, float alpha)
	{
		start();

		transformation.identity()
				.translate(x, y, 0)
				.scale(radius, radius, 1);

		circleShader.bind();
		circleShader.setTransformation(transformation);
		circleShader.setUniform(CircleShader.FILL, red, green, blue, alpha);

		renderSprite();
		end();
	}

	public static void drawRect(int x, int y, int w, int h, Vector4f border)
	{
		drawRect(x, y, w, h, border.x, border.y, border.z, border.w);
	}

	public static void drawRect(int x, int y, int w, int h, float borderRed, float borderGreen, float borderBlue, float borderAlpha)
	{
		start();

		transformation.identity()
				.translate(w * 0.5f + x, h * 0.5f + y, 0)
				.scale(w * 0.5f, h * 0.5f, 1);

		borderShader.bind();
		borderShader.setTransformation(transformation);
		borderShader.setUniform(BorderShader.BORDER, borderRed, borderGreen, borderBlue, borderAlpha);

		renderSprite();
		end();
	}

	public static void fillRect(int x, int y, int w, int h, Vector4f fill)
	{
		fillRect(x, y, w, h, fill.x, fill.y, fill.z, fill.w);
	}

	public static void fillRect(
			float x, float y, float w, float h,
			float fillRed, float fillGreen, float fillBlue, float fillAlpha)
	{
		start();

		transformation.identity()
				.translate(w * 0.5f + x, h * 0.5f + y, 0)
				.scale(w * 0.5f, h * 0.5f, 1);

		fillRectangleShader.bind();
		fillRectangleShader.setTransformation(transformation);
		fillRectangleShader.setUniform(FillRectangleShader.FILL, fillRed, fillGreen, fillBlue, fillAlpha);

		renderSprite();
		end();
	}

	public static void renderBorder(int x, int y, int w, int h, float r, float g, float b, float a)
	{
		renderSingleBorder(x, y, w, h, r, g, b, a, 0, 0, 0, 0);
	}

	public static void renderSingleBorder(int x, int y, int w, int h, Vector4f border, Vector4f fill)
	{
		renderSingleBorder(x, y, w, h, border.x, border.y, border.z, border.w, fill.x, fill.y, fill.z, fill.w);
	}

	public static void renderSingleBorder(
			float x, float y, float w, float h,
			float br, float bg, float bb, float ba,
			float fr, float fg, float fb, float fa)
	{
		start();

		transformation.identity()
				.translate(w * 0.5f + x, h * 0.5f + y, 0)
				.scale(w * 0.5f, h * 0.5f, 1);

		singleBorderShader.bind();
		singleBorderShader.setTransformation(transformation);
		singleBorderShader.setUniform(SingleBorderShader.SIZE, w, h);
		singleBorderShader.setUniform(SingleBorderShader.BORDER, br, bg, bb, ba);
		singleBorderShader.setUniform(SingleBorderShader.FILL, fr, fg, fb, fa);

		renderSprite();
		end();
	}

	public static void renderDoubleBorder(int x, int y, int w, int h, Vector4f outside, Vector4f inside, Vector4f fill)
	{
		renderDoubleBorder(x, y, w, h, outside.x, outside.y, outside.z, outside.w, inside.x, inside.y, inside.z, inside.w, fill.x, fill.y, fill.z, fill.w);
	}

	public static void renderDoubleBorder(
			int x, int y, float w, float h,
			float or, float og, float ob, float oa,
			float ir, float ig, float ib, float ia,
			float fr, float fg, float fb, float fa)
	{
		start();

		transformation.identity()
				.translate(w * 0.5f + x, h * 0.5f + y, 0)
				.scale(w * 0.5f, h * 0.5f, 1);

		doubleBorderShader.bind();
		doubleBorderShader.setTransformation(transformation);

		doubleBorderShader.setUniform(DoubleBorderShader.SIZE, w, h);
		doubleBorderShader.setUniform(DoubleBorderShader.OUTSIDEBORDER, or, og, ob, oa);
		doubleBorderShader.setUniform(DoubleBorderShader.INSIDEBORDER, ir, ig, ib, ia);
		doubleBorderShader.setUniform(DoubleBorderShader.FILL, fr, fg, fb, fa);

		renderSprite();
		end();
	}

	public static void renderSpriteInverted(int x, int y, int width, int height, float angRot, int spriteId, int spriteWidth, int spriteHeight)
	{
		start();

		transformation
			.identity()
			.translate(spriteWidth * 0.5f - (spriteWidth - width) * 0.5f, spriteHeight * 0.5f - (spriteHeight - height) * 0.5f, 0)
			.translate(x, y, 0)
			.rotate((float) Math.toRadians(angRot), 0, 0, 1)
			.scale(width * 0.5f, height * 0.5f, 1)
			.scale(1, -1f, 1);

		spriteShader.bind();
		spriteShader.setTransformation(transformation);

		bindSprite(spriteId);

		renderSprite();
		end();
	}

	public static void renderSpriteInverted(int x, int y, int width, int height, int spriteId, int spriteWidth, int spriteHeight)
	{
		start();

		transformation
			.identity()
			.translate(spriteWidth * 0.5f - (spriteWidth - width) * 0.5f, spriteHeight * 0.5f - (spriteHeight - height) * 0.5f, 0)
			.translate(x, y, 0)
			.scale(width * 0.5f, height * 0.5f, 1)
			.scale(1, -1f, 1);

		spriteShader.bind();
		spriteShader.setTransformation(transformation);

		bindSprite(spriteId);

		renderSprite();
		end();
	}

	public static void renderSprite(int x, int y, int width, int height, float angRot, Sprite sprite)
	{
		renderSprite(x, y, width, height, angRot, sprite.id);
	}

	public static void renderSprite(int x, int y, int width, int height, float angRot, int spriteId)
	{
		start();

		transformation
			.identity()
			.translate(width * 0.5f, height * 0.5f, 0)
			.translate(x, y, 0)
			.rotate((float) Math.toRadians(angRot), 0, 0, 1)
			.scale(width * 0.5f, height * 0.5f, 1);

		spriteShader.bind();
		spriteShader.setTransformation(transformation);

		bindSprite(spriteId);

		renderSprite();
		end();
	}

	public static void renderSprite(int x, int y, int width, int height, Sprite sprite)
	{
		renderSprite(x, y, width, height, sprite.id);
	}

	public static void renderSprite(int x, int y, int width, int height, int spriteId)
	{
		start();

		transformation
			.identity()
			.translate(width * 0.5f, height * 0.5f, 0)
			.translate(x, y, 0)
			.scale(width * 0.5f, height * 0.5f, 1);

		spriteShader.bind();
		spriteShader.setTransformation(transformation);

		bindSprite(spriteId);

		renderSprite();
		end();
	}

	public static void renderSpriteFromAtlas(int x, int y, int width, int height, float angRot, int spriteId, float tileX, float tileY, float tileW, float tileH)
	{
		start();

		transformation
			.identity()
			.translate(width * 0.5f, height * 0.5f, 0)
			.translate(x, y, 0)
			.rotate((float) Math.toRadians(angRot), 0, 0, 1)
			.scale(width * 0.5f, height * 0.5f, 1);

		spriteAtlasShader.bind();
		spriteAtlasShader.setTransformation(transformation);
		spriteAtlasShader.setUniform(SpriteAtlasShader.SPRITEDATA, tileW, tileH, tileX, tileY);

		bindSprite(spriteId);

		renderSprite();
		end();
	}

	public static void renderSpriteFromAtlas(int x, int y, int width, int height, int spriteId, float tileX, float tileY, float tileW, float tileH)
	{
		start();

		transformation
			.identity()
			.translate(width * 0.5f, height * 0.5f, 0)
			.translate(x, y, 0)
			.scale(width * 0.5f, height * 0.5f, 1);

		spriteAtlasShader.bind();
		spriteAtlasShader.setTransformation(transformation);
		spriteAtlasShader.setUniform(SpriteAtlasShader.SPRITEDATA, tileW, tileH, tileX / tileW, tileY / tileH);

		bindSprite(spriteId);

		renderSprite();
		end();
	}

	public static void renderDoubleBorderComponent(Component component,
	                                               float outsideRed, float outsideGreen, float outsideBlue, float outsideAlpha,
	                                               float insideRed, float insideGreen, float insideBlue, float insideAlpha,
	                                               float fillRed, float fillGreen, float fillBlue, float fillAlpha)
	{
		SpriteRender.renderDoubleBorder(
				component.getX(), component.getY(), component.getWidth(), component.getHeight(),
				outsideRed, outsideGreen, outsideBlue, outsideAlpha,
				insideRed, insideGreen, insideBlue, insideAlpha,
				fillRed, fillGreen, fillBlue, fillAlpha);
	}

	public static void renderDoubleBorderComponent(Component component, Vector4f outsideBorder, Vector4f insideBorder, Vector4f fill)
	{
		SpriteRender.renderDoubleBorder(component.getX(), component.getY(), component.getWidth(), component.getHeight(), outsideBorder, insideBorder, fill);
	}

	public static void renderSingleBorderComponent(Component component,
	                                               float br, float bg, float bb, float ba,
	                                               float fr, float fg, float fb, float fa)
	{
		SpriteRender.renderSingleBorder(
				component.getX(), component.getY(), component.getWidth(), component.getHeight(),
				br, bg, bb, ba,
				fr, fg, fb, fa);
	}

	public static void renderSingleBorderComponent(Component component, Vector4f border, Vector4f fill)
	{
		SpriteRender.renderSingleBorder(component.getX(), component.getY(), component.getWidth(), component.getHeight(), border, fill);
	}

	public static void renderFrame(String text, int x, int y, int w, int h)
	{
		SpriteRender.renderSingleBorder(x, y, w, h, 0.15f, 0.15f, 0.15f, 1f, 0, 0, 0, 0);
		SpriteRender.fillRect(x + 5, y - 3, Font.getTextWidth(text, 1) + 2, 8, Fex.H70, Fex.H70, Fex.H70, 1f);
		Font.render(text, x + 5, y - 3);
	}

	public static void renderFrame(Component parent, String text, int relX, int relY, int w, int h)
	{
		SpriteRender.renderSingleBorder(parent.getX() + relX, parent.getY() + relY, w, h, 0.15f, 0.15f, 0.15f, 1f, 0, 0, 0, 0);
		SpriteRender.fillRect(parent.getX() + relX + 5, parent.getY() + relY - 3, Font.getTextWidth(text, 1) + 2, 8, Fex.H70, Fex.H70, Fex.H70, 1f);
		Font.render(text, parent.getX() + relX + 5, parent.getY() + relY - 3);
	}

	public static void renderFrame(Dialog parent, String text, int relX, int relY, int w, int h)
	{
		SpriteRender.renderSingleBorder(parent.getX() + relX, parent.getY() + relY, w, h, 0.15f, 0.15f, 0.15f, 1f, 0, 0, 0, 0);
		SpriteRender.fillRect(parent.getX() + relX + 5, parent.getY() + relY - 3, Font.getTextWidth(text, 1) + 2, 8, Fex.H70, Fex.H70, Fex.H70, 1f);
		Font.render(text, parent.getX() + relX + 5, parent.getY() + relY - 3);
	}

	// Fuck immediate mode
	public static void start()
	{
		if (manual)
			return;
		glViewport(0, 0, w, h);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-1, 1, -1, 1, -1, 1); // 2D projection matrix
		glMatrixMode(GL_MODELVIEW);
	}

	// Fuck immediate mode
	public static void manualStart()
	{
		manual = true;
		glViewport(0, 0, w, h);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-1, 1, -1, 1, -1, 1); // 2D projection matrix
		glMatrixMode(GL_MODELVIEW);

		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
	}

	public static void renderSprite()
	{
		if (!manual)
		{
			glBindVertexArray(vao);
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);

			GL11.glDrawArrays(GL_TRIANGLES, 0, 6);

			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glBindVertexArray(0);
		} else
		{
			GL11.glDrawArrays(GL_TRIANGLES, 0, 6);
		}
	}

	public static void end()
	{
		if (manual)
			return;

		Shader.releaseShader();

		if (Test.flag)
		{
			glViewport(0, 0, SpriteRender.w, SpriteRender.h);
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, SpriteRender.w, SpriteRender.h, 0, 1, -1); // 2D projection matrix
			glMatrixMode(GL_MODELVIEW);
		}
	}

	public static void manualEnd()
	{
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);

		manual = false;
		manualSprite = false;
		Shader.releaseShader();

		glViewport(0, 0, SpriteRender.w, SpriteRender.h);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, SpriteRender.w, SpriteRender.h, 0, 1, -1); // 2D projection matrix
		glMatrixMode(GL_MODELVIEW);
	}

	public static void bindSprite(Sprite sprite)
	{
		if (manualSprite) return;
		sprite.bind(0);
	}

	public static void manualSprite(Sprite sprite)
	{
		manualSprite = true;
		sprite.bind(0);
	}

	public static void bindSprite(int spriteId)
	{
		if (manualSprite) return;
		Sprite.bind(0, spriteId);
	}

	public static void manualSprite(int spriteId)
	{
		manualSprite = true;
		Sprite.bind(0, spriteId);
	}
}
