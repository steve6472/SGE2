/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge2.main.gfx;

//import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Screen
{

	public static int getColor(int r, int g, int b, int a)
	{
		int re = ((a & 0xff) << 24) | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
		return re;
	}

	public static int getColor(int r, int g, int b)
	{
		return getColor(r, g, b, 255);
	}
	
	public static int getColor(int gray)
	{
		return getColor(gray, gray, gray, 255);
	}
	
	public static int getColor(int gray, float red, float green, float blue)
	{
		return getColor((int) (gray * red), (int) (gray * green), (int) (gray * blue));
	}
	
	public static int getColor(double gray)
	{
		return getColor((int) gray);
	}
	
	public static float[] getColors(int color)
	{
		return new float[] { (float) getRed(color) / 255f, (float) getGreen(color) / 255f, (float) getBlue(color) / 255f, (float) getAlpha(color) / 255f };
	}
	
	public static int getRed(int color)
	{
		return (color >> 16) & 0xff;
	}
	
	public static int getGreen(int color)
	{
		return (color >> 8) & 0xff;
	}
	
	public static int getBlue(int color)
	{
		return color & 0xff;
	}
	
	public static int getAlpha(int color)
	{
		return (color >> 24) & 0xff;
	}
	
	public static void color(int color)
	{
		glColor4fv(getColors(color));
	}
	
	public void fillRect(int x, int y, int width, int height, int color)
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		color(color);
		glBegin(GL_QUADS);
		{
			//Left Top
            glVertex2i(x, y);
            
            //Right Top
            glVertex2i(x + width, y);
            
            //Right Bottom
            glVertex2i(x + width, y + height);
            
            //Left Bottom
            glVertex2i(x, y + height);
		}
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}
	
	public void drawCircle(int x, int y, int edgeCount, int radius, double addAng)
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		color(0xffffffff);
		glBegin(GL_LINES);
		{
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
					glVertex2i(lastX, lastY);
					glVertex2i(X, Y);
					
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
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}
	
	public void drawLine(int x1, int y1, int x2, int y2, int color)
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		color(color);
		glBegin(GL_LINES);
		{
			glVertex2i(x1, y1);
			glVertex2i(x2, y2);
		}
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}
	
	public void drawPoint(int x, int y, int color)
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		color(color);
		glBegin(GL_POINTS);
		{
			glVertex2i(x, y);
		}
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}

	public void fillRect(int x, int y, int width, int height, int color, int maxX, int maxY, int minX, int minY)
	{
		glPushAttrib(GL_CURRENT_BIT);
		fillRect(x, y, width, height, maxX, maxY, minX, minY, (X, Y, W, H) -> fillRect(X, Y, W, H, color));
		glPopAttrib();
	}
	
	public void drawRect(int x, int y, int w, int h, int thickness, int color)
	{
		if (thickness == 0)
			return;

		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		color(color);
		glBegin(GL_QUADS);
		{
			// LT - RT
			fillRawRect(x, y, w, thickness);
			// RT-BR
			fillRawRect(x + w - thickness, y + thickness, thickness, h - 2 * thickness);
			// LB-BR
			fillRawRect(x, y + h - thickness, w, thickness);
			// LZ-BL
			fillRawRect(x, y + thickness, thickness, h - 2 * thickness);
		}
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}
	
	private static void fillRawRect(int x, int y, int w, int h)
	{
        glVertex2i(x, y);
        glVertex2i(x + w, y);
        glVertex2i(x + w, y + h);
        glVertex2i(x, y + h);
	}

	/**
	 * Renders Texture with it's size
	 * @param x
	 * @param y
	 * @param texture
	 */
	public void drawSprite(int x, int y, Sprite texture) 
	{
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		glBegin(GL_QUADS);
		{
			
			//Left Top
            glTexCoord2f(0, 0);
            glVertex2f(0 + x, 0 + y);
            
            //Right Top
            glTexCoord2f(1, 0);
            glVertex2f(texture.getWidth() + x, 0 + y);
            
            //Right Bottom
            glTexCoord2f(1, 1);
            glVertex2f(texture.getWidth() + x, texture.getHeight() + y);
            
            //Left Bottom
            glTexCoord2f(0, 1);
            glVertex2f(x, texture.getHeight() + y);
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	public void drawSprite(int x, int y, Sprite texture, int indexX, int indexY, int sizeX, int sizeY)
	{
		glMatrixMode(GL_TEXTURE);
		glScalef(1f / (float) texture.getWidth(), 1f / (float) texture.getHeight(), 1f);

		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		glBegin(GL_QUADS);
		{

			//Left Top
            glTexCoord2i(indexX, indexY);
            glVertex2f(0 + x, 0 + y);
            
            //Right Top
            glTexCoord2i(sizeX, indexY);
            glVertex2f(sizeX + x - indexX, 0 + y);
            
            
            //Right Bottom
            glTexCoord2i(sizeX, sizeY);
            glVertex2f(sizeX + x - indexX, sizeY + y - indexY);
            
            //Left Bottom
            glTexCoord2i(indexX, sizeY);
            glVertex2f(x, sizeY + y - indexY);
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
		
		glScalef((float) texture.getWidth(), (float) texture.getHeight(), 1f);
	}

	public void drawSprite(int x, int y, Sprite texture, int width, int height)
	{
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		glBegin(GL_QUADS);
		{

			//Left Top
            glTexCoord2i(0, 0);
            glVertex2f(0 + x, 0 + y);
            
            //Right Top
            glTexCoord2i(1, 0);
            glVertex2f(width + x, 0 + y);
            
            
            //Right Bottom
            glTexCoord2i(1, 1);
            glVertex2f(width + x, height + y);
            
            //Left Bottom
            glTexCoord2i(0, 1);
            glVertex2f(x, height + y);
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}
	
	public void drawSpriteRepeat(int x, int y, Sprite texture, int repeatX, int repeatY)
	{
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		texture.bind();
		
		glBegin(GL_QUADS);
		{

			//Left Top
            glTexCoord2i(0, 0);
            glVertex2f(0 + x, 0 + y);
            
            //Right Top
            glTexCoord2i(repeatX, 0);
            glVertex2f(texture.getWidth() * repeatX + x, 0 + y);
            
            
            //Right Bottom
            glTexCoord2i(repeatX, repeatY);
            glVertex2f(texture.getWidth() * repeatX + x, texture.getHeight() * repeatY + y);
            
            //Left Bottom
            glTexCoord2i(0, repeatY);
            glVertex2f(x, texture.getHeight() * repeatY + y);
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	@FunctionalInterface
	public interface Interface4<I1, I2, I3, I4>
	{
		public void apply(I1 i1, I2 i2, I3 i3, I4 i4);
	}
	
	private static void fillRect(int x, int y, int width, int height, int maxX, int maxY, int minX, int minY, Interface4<Integer, Integer, Integer, Integer> di)
	{
		
		// Ignore 0 width or height
		if (width == 0 || height == 0)
			return;
			
		// If Width is less than zero -> invert
		if (width < 0)
		{
			x = width + x;
			width = -width;
		}
			
		// If Height is less than zero -> invert
		if (height < 0)
		{
			y = height + y;
			height = -height;
		}
		
		// Don't render if x or y are outside selected box
		// Can be here even thou original width or height were negative cuz I changed it.
		if (x > maxX) return;
		if (y > maxY) return;

		// Limit to screen size
		if (x < minX) { width  = width  - minX + x; x = minX; }
		if (y < minY) { height = height - minY + y; y = minY; }
		if (x + width  > maxX) { width  -= x   + width  - maxX; }
		if (y + height > maxY) { height -= y   + height - maxY; }
		
		// Ignore 0 width or height again cuz of that math up there could mess it up
		if (width == 0 || height == 0)
			return;

		// Render
		di.apply(x, y, width, height);
	}

}
