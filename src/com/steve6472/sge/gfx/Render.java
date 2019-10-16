package com.steve6472.sge.gfx;

import com.steve6472.sge.main.util.ColorUtil;

import static org.lwjgl.opengl.GL11.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 19.1.2019
 * Project: SGE2
 *
 ***********************/
public class Render
{
	/*
	 *
	 * Primitive Renderer
	 * 
	 */
	
	/* Universal methods */
	
	public static void color(float r, float g, float b, float a)
	{
		glColor4f(r, g, b, a);
	}

	public static void color(int color)
	{
		glColor4fv(ColorUtil.getColors(color));
	}

	public static void texture(int x, int y)
	{
		glTexCoord2i(x, y);
	}

	public static void texture(float x, float y)
	{
		glTexCoord2f(x, y);
	}

	/* Points */
	
	public static void startPoints()
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_POINTS);
	}

	public static void endPoints()
	{
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}
	
	public static void point(int x, int y)
	{
		glVertex2i(x, y);
	}

	public static void point(float x, float y)
	{
		glVertex2f(x, y);
	}

	public static void point(int x, int y,
	                         int c)
	{
		color(c);
		point(x, y);
	}

	public static void point(float x, float y,
	                         int c)
	{
		color(c);
		point(x, y);
	}

	public static void point(int x, int y,
	                         float r, float g, float b, float a)
	{
		color(r, g, b, a);
		point(x, y);
	}

	public static void point(float x, float y,
	                         float r, float g, float b, float a)
	{
		color(r, g, b, a);
		point(x, y);
	}

	/* ************************************ */
	
	public static void point_(int x, int y)
	{
		startPoints();
		point(x, y);
		endPoints();
	}

	public static void point_(float x, float y)
	{
		startPoints();
		point(x, y);
		endPoints();
	}

	public static void point_(int x, int y,
	                          int c)
	{
		startPoints();
		point(x, y, c);
		endPoints();
	}

	public static void point_(float x, float y,
	                          int c)
	{
		startPoints();
		point(x, y, c);
		endPoints();
	}

	public static void point_(int x, int y,
	                          float r, float g, float b, float a)
	{
		startPoints();
		point(x, y, r, g, b, a);
		endPoints();
	}

	public static void point_(float x, float y,
	                          float r, float g, float b, float a)
	{
		startPoints();
		point(x, y, r, g, b, a);
		endPoints();
	}
	
	/* Lines */
	
	public static void startLines()
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_LINES);
	}

	public static void startLineLoop()
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_LINE_LOOP);
	}

	public static void startLineStrip()
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_LINE_STRIP);
	}
	
	public static void endLines()
	{
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}
	
	public static void line(int x0, int y0,
	                        int x1, int y1)
	{
		point(x0, y0);
		point(x1, y1);
	}

	public static void line(float x0, float y0,
	                        float x1, float y1)
	{
		point(x0, y0);
		point(x1, y1);
	}

	public static void line(int x0, int y0,
	                        int x1, int y1,
	                        int c)
	{
		color(c);
		point(x0, y0);
		point(x1, y1);
	}

	public static void line(float x0, float y0,
	                        float x1, float y1,
	                        int c)
	{
		color(c);
		point(x0, y0);
		point(x1, y1);
	}

	public static void line(int x0, int y0,
	                        int x1, int y1,
	                        float r, float g, float b, float a)
	{
		color(r, g, b, a);
		point(x0, y0);
		point(x1, y1);
	}

	public static void line(float x0, float y0,
	                        float x1, float y1,
	                        float r, float g, float b, float a)
	{
		color(r, g, b, a);
		point(x0, y0);
		point(x1, y1);
	}

	public static void line(int x0, int y0,
	                        int x1, int y1,
	                        int c0, int c1)
	{
		point(x0, y0, c0);
		point(x1, y1, c1);
	}

	public static void line(float x0, float y0,
	                        float x1, float y1,
	                        int c0, int c1)
	{
		point(x0, y0, c0);
		point(x1, y1, c1);
	}

	public static void line(int x0, int y0,
	                        int x1, int y1,
	                        float r0, float g0, float b0, float a0,
	                        float r1, float g1, float b1, float a1)
	{
		point(x0, y0, r0, g0, b0, a0);
		point(x1, y1, r1, g1, b1, a1);
	}

	public static void line(float x0, float y0,
	                        float x1, float y1,
	                        float r0, float g0, float b0, float a0,
	                        float r1, float g1, float b1, float a1)
	{
		point(x0, y0, r0, g0, b0, a0);
		point(x1, y1, r1, g1, b1, a1);
	}

	/* ************************************ */

	public static void line_(int x0, int y0,
	                         int x1, int y1)
	{
		startLines();
		line(x0, y0, x1, y1);
		endLines();
	}

	public static void line_(float x0, float y0,
	                         float x1, float y1)
	{
		startLines();
		line(x0, y0, x1, y1);
		endLines();
	}

	public static void line_(int x0, int y0,
	                         int x1, int y1,
	                         int c)
	{
		startLines();
		line(x0, y0, x1, y1, c);
		endLines();
	}

	public static void line_(float x0, float y0,
	                         float x1, float y1,
	                         int c)
	{
		startLines();
		line(x0, y0, x1, y1, c);
		endLines();
	}

	public static void line_(int x0, int y0,
	                         int x1, int y1,
	                         float r, float g, float b, float a)
	{
		startLines();
		line(x0, y0, x1, y1, r, g, b, a);
		endLines();
	}

	public static void line_(float x0, float y0,
	                         float x1, float y1,
	                         float r, float g, float b, float a)
	{
		startLines();
		line(x0, y0, x1, y1, r, g, b, a);
		endLines();
	}

	public static void line_(int x0, int y0,
	                         int x1, int y1,
	                         int c0, int c1)
	{
		startLines();
		line(x0, y0, x1, y1, c0, c1);
		endLines();
	}

	public static void line_(float x0, float y0,
	                         float x1, float y1,
	                         int c0, int c1)
	{
		startLines();
		line(x0, y0, x1, y1, c0, c1);
		endLines();
	}

	public static void line_(int x0, int y0,
	                         int x1, int y1,
	                         float r0, float g0, float b0, float a0,
	                         float r1, float g1, float b1, float a1)
	{
		startLines();
		line(x0, y0, x1, y1, r0, g0, b0, a0, r1, g1, b1, a1);
		endLines();
	}

	public static void line_(float x0, float y0,
	                         float x1, float y1,
	                         float r0, float g0, float b0, float a0,
	                         float r1, float g1, float b1, float a1)
	{
		startLines();
		line(x0, y0, x1, y1, r0, g0, b0, a0, r1, g1, b1, a1);
		endLines();
	}

	// Triangles

	public static void startTriangles()
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_TRIANGLES);
	}

	public static void startTriangleStrip()
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_TRIANGLE_STRIP);
	}

	public static void startTriangleFan()
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_TRIANGLE_FAN);
	}

	public static void endTriangles()
	{
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}

	public static void triangle(int x0, int y0,
	                            int x1, int y1,
	                            int x2, int y2)
	{
		point(x0, y0);
		point(x1, y1);
		point(x2, y2);
	}

	public static void triangle(float x0, float y0,
	                            float x1, float y1,
	                            float x2, float y2)
	{
		point(x0, y0);
		point(x1, y1);
		point(x2, y2);
	}

	public static void triangle(int x0, int y0,
	                            int x1, int y1,
	                            int x2, int y2,
	                            int c)
	{
		color(c);
		point(x0, y0);
		point(x1, y1);
		point(x2, y2);
	}

	public static void triangle(float x0, float y0,
	                            float x1, float y1,
	                            float x2, float y2,
	                            int c)
	{
		color(c);
		point(x0, y0);
		point(x1, y1);
		point(x2, y2);
	}

	public static void triangle(int x0, int y0,
	                            int x1, int y1,
	                            int x2, int y2,
	                            float r, float g, float b, float a)
	{
		color(r, g, b, a);
		point(x0, y0);
		point(x1, y1);
		point(x2, y2);
	}

	public static void triangle(float x0, float y0,
	                            float x1, float y1,
	                            float x2, float y2,
	                            float r, float g, float b, float a)
	{
		color(r, g, b, a);
		point(x0, y0);
		point(x1, y1);
		point(x2, y2);
	}

	public static void triangle(int x0, int y0,
	                            int x1, int y1,
	                            int x2, int y2,
	                            int c0,
	                            int c1,
	                            int c2)
	{
		point(x0, y0, c0);
		point(x1, y1, c1);
		point(x2, y2, c2);
	}

	public static void triangle(float x0, float y0,
	                            float x1, float y1,
	                            float x2, float y2,
	                            int c0,
	                            int c1,
	                            int c2)
	{
		point(x0, y0, c0);
		point(x1, y1, c1);
		point(x2, y2, c2);
	}

	public static void triangle(int x0, int y0,
	                            int x1, int y1,
	                            int x2, int y2,
	                            float r0, float g0, float b0, float a0,
	                            float r1, float g1, float b1, float a1,
	                            float r2, float g2, float b2, float a2)
	{
		point(x0, y0, r0, g0, b0, a0);
		point(x1, y1, r1, g1, b1, a1);
		point(x2, y2, r2, g2, b2, a2);
	}

	public static void triangle(float x0, float y0,
	                            float x1, float y1,
	                            float x2, float y2,
	                            float r0, float g0, float b0, float a0,
	                            float r1, float g1, float b1, float a1,
	                            float r2, float g2, float b2, float a2)
	{
		point(x0, y0, r0, g0, b0, a0);
		point(x1, y1, r1, g1, b1, a1);
		point(x2, y2, r2, g2, b2, a2);
	}

	/* ************************************ */

	public static void triangle_(int x0, int y0,
	                             int x1, int y1,
	                             int x2, int y2)
	{
		startTriangles();
		triangle(x0, y0, x1, y1, x2, y2);
		endTriangles();
	}

	public static void triangle_(float x0, float y0,
	                             float x1, float y1,
	                             float x2, float y2)
	{
		startTriangles();
		triangle(x0, y0, x1, y1, x2, y2);
		endTriangles();
	}

	public static void triangle_(int x0, int y0,
	                             int x1, int y1,
	                             int x2, int y2,
	                             int c)
	{
		startTriangles();
		triangle(x0, y0, x1, y1, x2, y2, c);
		endTriangles();
	}

	public static void triangle_(float x0, float y0,
	                             float x1, float y1,
	                             float x2, float y2,
	                             int c)
	{
		startTriangles();
		triangle(x0, y0, x1, y1, x2, y2, c);
		endTriangles();
	}

	public static void triangle_(int x0, int y0,
	                             int x1, int y1,
	                             int x2, int y2,
	                             float r, float g, float b, float a)
	{
		startTriangles();
		triangle(x0, y0, x1, y1, x2, y2, r, g, b, a);
		endTriangles();
	}

	public static void triangle_(float x0, float y0,
	                             float x1, float y1,
	                             float x2, float y2,
	                             float r, float g, float b, float a)
	{
		startTriangles();
		triangle(x0, y0, x1, y1, x2, y2, r, g, b, a);
		endTriangles();
	}

	public static void triangle_(int x0, int y0,
	                             int x1, int y1,
	                             int x2, int y2,
	                             int c0,
	                             int c1,
	                             int c2)
	{
		startTriangles();
		triangle(x0, y0, x1, y1, x2, y2, c0, c1, c2);
		endTriangles();
	}

	public static void triangle_(float x0, float y0,
	                             float x1, float y1,
	                             float x2, float y2,
	                             int c0,
	                             int c1,
	                             int c2)
	{
		startTriangles();
		triangle(x0, y0, x1, y1, x2, y2, c0, c1, c2);
		endTriangles();
	}

	public static void triangle_(int x0, int y0,
	                             int x1, int y1,
	                             int x2, int y2,
	                             float r0, float g0, float b0, float a0,
	                             float r1, float g1, float b1, float a1,
	                             float r2, float g2, float b2, float a2)
	{
		startTriangles();
		triangle(x0, y0, x1, y1, x2, y2, r0, g0, b0, a0, r1, g1, b1, a1, r2, g2, b2, a2);
		endTriangles();
	}

	public static void triangle_(float x0, float y0,
	                             float x1, float y1,
	                             float x2, float y2,
	                             float r0, float g0, float b0, float a0,
	                             float r1, float g1, float b1, float a1,
	                             float r2, float g2, float b2, float a2)
	{
		startTriangles();
		triangle(x0, y0, x1, y1, x2, y2, r0, g0, b0, a0, r1, g1, b1, a1, r2, g2, b2, a2);
		endTriangles();
	}

	// Quads

	public static void startQuads()
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_QUADS);
	}

	public static void startQuadStrip()
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_QUAD_STRIP);
	}

	public static void endQuads()
	{
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}

	public static void quad(int x0, int y0,
	                        int x1, int y1,
	                        int x2, int y2,
	                        int x3, int y3)
	{
		point(x0, y0);
		point(x1, y1);
		point(x2, y2);
		point(x3, y3);
	}

	public static void quad(float x0, float y0,
	                        float x1, float y1,
	                        float x2, float y2,
	                        float x3, float y3)
	{
		point(x0, y0);
		point(x1, y1);
		point(x2, y2);
		point(x3, y3);
	}

	public static void quad(int x0, int y0,
	                        int x1, int y1,
	                        int x2, int y2,
	                        int x3, int y3,
	                        int c)
	{
		color(c);
		point(x0, y0);
		point(x1, y1);
		point(x2, y2);
		point(x3, y3);
	}

	public static void quad(float x0, float y0,
	                        float x1, float y1,
	                        float x2, float y2,
	                        float x3, float y3,
	                        int c)
	{
		color(c);
		point(x0, y0);
		point(x1, y1);
		point(x2, y2);
		point(x3, y3);
	}

	public static void quad(int x0, int y0,
	                        int x1, int y1,
	                        int x2, int y2,
	                        int x3, int y3,
	                        float r, float g, float b, float a)
	{
		color(r, g, b, a);
		point(x0, y0);
		point(x1, y1);
		point(x2, y2);
		point(x3, y3);
	}

	public static void quad(float x0, float y0,
	                        float x1, float y1,
	                        float x2, float y2,
	                        float x3, float y3,
	                        float r, float g, float b, float a)
	{
		color(r, g, b, a);
		point(x0, y0);
		point(x1, y1);
		point(x2, y2);
		point(x3, y3);
	}

	public static void quad(int x0, int y0,
	                        int x1, int y1,
	                        int x2, int y2,
	                        int x3, int y3,
	                        int c0,
	                        int c1,
	                        int c2,
	                        int c3)
	{
		point(x0, y0, c0);
		point(x1, y1, c1);
		point(x2, y2, c2);
		point(x3, y3, c3);
	}

	public static void quad(float x0, float y0,
	                        float x1, float y1,
	                        float x2, float y2,
	                        float x3, float y3,
	                        int c0,
	                        int c1,
	                        int c2,
	                        int c3)
	{
		point(x0, y0, c0);
		point(x1, y1, c1);
		point(x2, y2, c2);
		point(x3, y3, c3);
	}

	public static void quad(int x0, int y0,
	                        int x1, int y1,
	                        int x2, int y2,
	                        int x3, int y3,
	                        float r0, float g0, float b0, float a0,
	                        float r1, float g1, float b1, float a1,
	                        float r2, float g2, float b2, float a2,
	                        float r3, float g3, float b3, float a3)
	{
		point(x0, y0, r0, g0, b0, a0);
		point(x1, y1, r1, g1, b1, a1);
		point(x2, y2, r2, g2, b2, a2);
		point(x3, y3, r3, g3, b3, a3);
	}

	public static void quad(float x0, float y0,
	                        float x1, float y1,
	                        float x2, float y2,
	                        float x3, float y3,
	                        float r0, float g0, float b0, float a0,
	                        float r1, float g1, float b1, float a1,
	                        float r2, float g2, float b2, float a2,
	                        float r3, float g3, float b3, float a3)
	{
		point(x0, y0, r0, g0, b0, a0);
		point(x1, y1, r1, g1, b1, a1);
		point(x2, y2, r2, g2, b2, a2);
		point(x3, y3, r3, g3, b3, a3);
	}

	/* ************************************ */

	public static void quad_(int x0, int y0,
	                         int x1, int y1,
	                         int x2, int y2,
	                         int x3, int y3)
	{
		startQuads();
		quad(x0, y0, x1, y1, x2, y2, x3, y3);
		endQuads();
	}

	public static void quad_(float x0, float y0,
	                         float x1, float y1,
	                         float x2, float y2,
	                         float x3, float y3)
	{
		startQuads();
		quad(x0, y0, x1, y1, x2, y2, x3, y3);
		endQuads();
	}

	public static void quad_(int x0, int y0,
	                         int x1, int y1,
	                         int x2, int y2,
	                         int x3, int y3,
	                         int c)
	{
		startQuads();
		quad(x0, y0, x1, y1, x2, y2, x3, y3, c);
		endQuads();
	}

	public static void quad_(float x0, float y0,
	                         float x1, float y1,
	                         float x2, float y2,
	                         float x3, float y3,
	                         int c)
	{
		startQuads();
		quad(x0, y0, x1, y1, x2, y2, x3, y3, c);
		endQuads();
	}

	public static void quad_(int x0, int y0,
	                         int x1, int y1,
	                         int x2, int y2,
	                         int x3, int y3,
	                         float r, float g, float b, float a)
	{
		startQuads();
		quad(x0, y0, x1, y1, x2, y2, x3, y3, r, g, b, a);
		endQuads();
	}

	public static void quad_(float x0, float y0,
	                         float x1, float y1,
	                         float x2, float y2,
	                         float x3, float y3,
	                         float r, float g, float b, float a)
	{
		startQuads();
		quad(x0, y0, x1, y1, x2, y2, x3, y3, r, g, b, a);
		endQuads();
	}

	public static void quad_(int x0, int y0,
	                         int x1, int y1,
	                         int x2, int y2,
	                         int x3, int y3,
	                         int c0,
	                         int c1,
	                         int c2,
	                         int c3)
	{
		startQuads();
		quad(x0, y0, x1, y1, x2, y2, x3, y3, c0, c1, c2, c3);
		endQuads();
	}

	public static void quad_(float x0, float y0,
	                         float x1, float y1,
	                         float x2, float y2,
	                         float x3, float y3,
	                         int c0,
	                         int c1,
	                         int c2,
	                         int c3)
	{
		startQuads();
		quad(x0, y0, x1, y1, x2, y2, x3, y3, c0, c1, c2, c3);
		endQuads();
	}

	public static void quad_(int x0, int y0,
	                         int x1, int y1,
	                         int x2, int y2,
	                         int x3, int y3,
	                         float r0, float g0, float b0, float a0,
	                         float r1, float g1, float b1, float a1,
	                         float r2, float g2, float b2, float a2,
	                         float r3, float g3, float b3, float a3)
	{
		startQuads();
		quad(x0, y0, x1, y1, x2, y2, x3, y3, r0, g0, b0, a0, r1, g1, b1, a1, r2, g2, b2, a2, r3, g3, b3, a3);
		endQuads();
	}

	public static void quad_(float x0, float y0,
	                         float x1, float y1,
	                         float x2, float y2,
	                         float x3, float y3,
	                         float r0, float g0, float b0, float a0,
	                         float r1, float g1, float b1, float a1,
	                         float r2, float g2, float b2, float a2,
	                         float r3, float g3, float b3, float a3)
	{
		startQuads();
		quad(x0, y0, x1, y1, x2, y2, x3, y3, r0, g0, b0, a0, r1, g1, b1, a1, r2, g2, b2, a2, r3, g3, b3, a3);
		endQuads();
	}

	// Polygons

	public static void startPolygons()
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_POLYGON);
	}

	public static void endPolygons()
	{
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}
	
	/*
	 *
	 * Complex Renderer
	 * 
	 */

	/* Fill Rectangle */
	
	public static void startFillRect()
	{
		startQuads();
	}
	
	public static void endFillRect()
	{
		endQuads();
	}
	
	public static void fillRect_(int x, int y, int w, int h)
	{
		quad(x, y, x, y + h, x + w, y + h, x + w, y);
	}

	public static void fillRect_(float x, float y, float w, float h)
	{
		quad(x, y, x, y + h, x + w, y + h, x + w, y);
	}

	public static void fillRect_(int x, int y, int w, int h,
	                             int c)
	{
		quad(x, y, x, y + h, x + w, y + h, x + w, y, c);
	}

	public static void fillRect_(float x, float y, float w, float h,
	                             int c)
	{
		quad(x, y, x, y + h, x + w, y + h, x + w, y, c);
	}

	public static void fillRect_(int x, int y, int w, int h,
	                             float r, float g, float b, float a)
	{
		quad(x, y, x, y + h, x + w, y + h, x + w, y, r, g, b, a);
	}

	public static void fillRect_(float x, float y, float w, float h,
	                             float r, float g, float b, float a)
	{
		quad(x, y, x, y + h, x + w, y + h, x + w, y, r, g, b, a);
	}

	public static void fillRect_(int x, int y, int w, int h,
	                             int c0,
	                             int c1,
	                             int c2,
	                             int c3)
	{
		quad(x, y, x, y + h, x + w, y + h, x + w, y, c0, c1, c2, c3);
	}

	public static void fillRect_(float x, float y, float w, float h,
	                             int c0,
	                             int c1,
	                             int c2,
	                             int c3)
	{
		quad(x, y, x, y + h, x + w, y + h, x + w, y, c0, c1, c2, c3);
	}

	public static void fillRect_(int x, int y, int w, int h,
	                             float r0, float g0, float b0, float a0,
	                             float r1, float g1, float b1, float a1,
	                             float r2, float g2, float b2, float a2,
	                             float r3, float g3, float b3, float a3)
	{
		quad(x, y, x, y + h, x + w, y + h, x + w, y, r0, g0, b0, a0, r1, g1, b1, a1, r2, g2, b2, a2, r3, g3, b3, a3);
	}

	public static void fillRect_(float x, float y, float w, float h,
	                             float r0, float g0, float b0, float a0,
	                             float r1, float g1, float b1, float a1,
	                             float r2, float g2, float b2, float a2,
	                             float r3, float g3, float b3, float a3)
	{
		quad(x, y, x, y + h, x + w, y + h, x + w, y, r0, g0, b0, a0, r1, g1, b1, a1, r2, g2, b2, a2, r3, g3, b3, a3);
	}

	/* ************************************ */

	public static void fillRect(int x, int y, int w, int h)
	{
		startQuads();
		quad(x, y, x, y + h, x + w, y + h, x + w, y);
		endQuads();
	}

	public static void fillRect(float x, float y, float w, float h)
	{
		startQuads();
		quad(x, y, x, y + h, x + w, y + h, x + w, y);
		endQuads();
	}

	public static void fillRect(int x, int y, int w, int h,
	                             int c)
	{
		startQuads();
		quad(x, y, x, y + h, x + w, y + h, x + w, y, c);
		endQuads();
	}

	public static void fillRect(float x, float y, float w, float h,
	                             int c)
	{
		startQuads();
		quad(x, y, x, y + h, x + w, y + h, x + w, y, c);
		endQuads();
	}

	public static void fillRect(int x, int y, int w, int h,
	                             float r, float g, float b, float a)
	{
		startQuads();
		quad(x, y, x, y + h, x + w, y + h, x + w, y, r, g, b, a);
		endQuads();
	}

	public static void fillRect(float x, float y, float w, float h,
	                             float r, float g, float b, float a)
	{
		startQuads();
		quad(x, y, x, y + h, x + w, y + h, x + w, y, r, g, b, a);
		endQuads();
	}

	public static void fillRect(int x, int y, int w, int h,
	                             int c0,
	                             int c1,
	                             int c2,
	                             int c3)
	{
		startQuads();
		quad(x, y, x, y + h, x + w, y + h, x + w, y, c0, c1, c2, c3);
		endQuads();
	}

	public static void fillRect(float x, float y, float w, float h,
	                             int c0,
	                             int c1,
	                             int c2,
	                             int c3)
	{
		startQuads();
		quad(x, y, x, y + h, x + w, y + h, x + w, y, c0, c1, c2, c3);
		endQuads();
	}

	public static void fillRect(int x, int y, int w, int h,
	                             float r0, float g0, float b0, float a0,
	                             float r1, float g1, float b1, float a1,
	                             float r2, float g2, float b2, float a2,
	                             float r3, float g3, float b3, float a3)
	{
		startQuads();
		quad(x, y, x, y + h, x + w, y + h, x + w, y, r0, g0, b0, a0, r1, g1, b1, a1, r2, g2, b2, a2, r3, g3, b3, a3);
		endQuads();
	}

	public static void fillRect(float x, float y, float w, float h,
	                             float r0, float g0, float b0, float a0,
	                             float r1, float g1, float b1, float a1,
	                             float r2, float g2, float b2, float a2,
	                             float r3, float g3, float b3, float a3)
	{
		startQuads();
		quad(x, y, x, y + h, x + w, y + h, x + w, y, r0, g0, b0, a0, r1, g1, b1, a1, r2, g2, b2, a2, r3, g3, b3, a3);
		endQuads();
	}

	/* Draw Rectangle */

	public static void startDrawRect()
	{
		startQuads();
	}

	public static void endDrawRect()
	{
		endQuads();
	}

	public static void drawRect_(int x, int y, int w, int h, int t)
	{
		fillRect_(x, y, w, t);
		fillRect_(x + w - t, y + t, t, h - 2 * t);
		fillRect_(x, y + h - t, w, t);
		fillRect_(x, y + t, t, h - 2 * t);
	}

	public static void drawRect_(float x, float y, float w, float h, int t)
	{
		fillRect_(x, y, w, t);
		fillRect_(x + w - t, y + t, t, h - 2 * t);
		fillRect_(x, y + h - t, w, t);
		fillRect_(x, y + t, t, h - 2 * t);
	}

	public static void drawRect_(int x, int y, int w, int h, int t, int c)
	{
		color(c);
		fillRect_(x, y, w, t);
		fillRect_(x + w - t, y + t, t, h - 2 * t);
		fillRect_(x, y + h - t, w, t);
		fillRect_(x, y + t, t, h - 2 * t);
	}

	public static void drawRect_(float x, float y, float w, float h, int t, int c)
	{
		color(c);
		fillRect_(x, y, w, t);
		fillRect_(x + w - t, y + t, t, h - 2 * t);
		fillRect_(x, y + h - t, w, t);
		fillRect_(x, y + t, t, h - 2 * t);
	}

	public static void drawRect_(int x, int y, int w, int h, int t, float r, float g, float b, float a)
	{
		color(r, g, b, a);
		fillRect_(x, y, w, t);
		fillRect_(x + w - t, y + t, t, h - 2 * t);
		fillRect_(x, y + h - t, w, t);
		fillRect_(x, y + t, t, h - 2 * t);
	}

	public static void drawRect_(float x, float y, float w, float h, int t, float r, float g, float b, float a)
	{
		color(r, g, b, a);
		fillRect_(x, y, w, t);
		fillRect_(x + w - t, y + t, t, h - 2 * t);
		fillRect_(x, y + h - t, w, t);
		fillRect_(x, y + t, t, h - 2 * t);
	}

	/* ************************************ */

	public static void drawRect(int x, int y, int w, int h, int t)
	{
		startDrawRect();
		drawRect_(x, y, w, h, t);
		endDrawRect();
	}

	public static void drawRect(float x, float y, float w, float h, int t)
	{
		startDrawRect();
		drawRect_(x, y, w, h, t);
		endDrawRect();
	}

	public static void drawRect(int x, int y, int w, int h, int t, int c)
	{
		startDrawRect();
		drawRect_(x, y, w, h, t, c);
		endDrawRect();
	}

	public static void drawRect(float x, float y, float w, float h, int t, int c)
	{
		startDrawRect();
		drawRect_(x, y, w, h, t, c);
		endDrawRect();
	}

	public static void drawRect(int x, int y, int w, int h, int t, float r, float g, float b, float a)
	{
		startDrawRect();
		drawRect_(x, y, w, h, t, r, g, b, a);
		endDrawRect();
	}

	public static void drawRect(float x, float y, float w, float h, int t, float r, float g, float b, float a)
	{
		startDrawRect();
		drawRect_(x, y, w, h, t, r, g, b, a);
		endDrawRect();
	}
}