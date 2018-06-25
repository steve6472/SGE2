/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.gfx;

//import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Screen
{

	public Screen()
	{
	}
	
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
	
	public static int getColor(float red, float green, float blue, float alpha)
	{
		return getColor((int) (red * 255f), (int) (green * 255f), (int) (blue * 255f), (int) (alpha * 255f));
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
	
	public static void drawRotatedPartOfTexture(int x, int y, Sprite texture, float rotation, int subImageSizeX, int subImageSizeY, int indexX, int indexY)
	{
		float width = texture.getWidth();
		float height = texture.getHeight();
		
		//Tile size <>
		float tsx = subImageSizeX;
		float tsy = subImageSizeY;
		
		float sx = 1f / width * tsx;
		float sy = 1f / height * tsy;
		
		//Index <>
		float ix = indexX;
		float iy = indexY;
		
		float iX = ix * sx;
		float iY = iy * sy;
		
		float iix = iX + sx;
		float iiy = iY + sy;

		glPushMatrix();
		
		texture.bind();
		glEnable(GL_TEXTURE_2D);
		
		glTranslatef(x + tsx / 2f, y + tsy / 2f, 0);
		glRotatef(rotation, 0f, 0f, 1f);
		glTranslatef(-x - tsx / 2f, -y - tsy / 2f, 0);
		
		glBegin(GL_QUADS);

		//
		
		//Left Top 00
        glTexCoord2f(iX, iY);
        glVertex2f(x, y);
        
        //Right Top 10
        glTexCoord2f(iix, iY);
        glVertex2f(width * sx + x, y);
        
        
        //Right Bottom 11
        glTexCoord2f(iix, iiy);
        glVertex2f(width * sx + x, height * sy + y);
        
        //Left Bottom 01
        glTexCoord2f(iX, iiy);
        glVertex2f(x, height * sy + y);

		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}
	
	public static void fillRect(int x, int y, int width, int height, int color)
	{
		fillRect(x, y, width, height, color, color, color, color);
	}
	
	public static void fillRect(int x, int y, int width, int height, int c00, int c10, int c11, int c01)
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_QUADS);
		{
			//Left Top
			color(c00);
            glVertex2i(x, y);
            
            //Right Top
			color(c10);
            glVertex2i(x + width, y);
            
            //Right Bottom
			color(c11);
            glVertex2i(x + width, y + height);
            
            //Left Bottom
			color(c01);
            glVertex2i(x, y + height);
		}
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}
	
	public static void drawCircle(int x, int y, int edgeCount, int radius, double addAng, int color)
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		color(color);
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
	
	public static void drawLine(int x1, int y1, int x2, int y2, int color)
	{
		drawLine(x1, y1, x2, y2, color, color);
	}
	
	public static void drawLine(int x1, int y1, int x2, int y2, int c00, int c11)
	{
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glBegin(GL_LINES);
		{
			color(c00);
			glVertex2i(x1, y1);
			color(c11);
			glVertex2i(x2, y2);
		}
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}
	
	public static void drawPoint(int x, int y, int color)
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

	public static void fillRect(int x, int y, int width, int height, int color, int maxX, int maxY, int minX, int minY)
	{
		glPushAttrib(GL_CURRENT_BIT);
		fillRect(x, y, width, height, maxX, maxY, minX, minY, (X, Y, W, H) -> fillRect(X, Y, W, H, color));
		glPopAttrib();
	}
	
	public static void drawRect(int x, int y, int w, int h, int thickness, int color)
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
	
	public static void fillRawRect(int x, int y, int w, int h)
	{
        glVertex2i(x, y);
        glVertex2i(x + w, y);
        glVertex2i(x + w, y + h);
        glVertex2i(x, y + h);
	}
	
	public static void drawRawSprite(int x, int y, int width, int height, Sprite texture)
	{
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        
        //Right Top
        glTexCoord2f(1, 0);
        glVertex2f(width + x, y);
        
        //Right Bottom
        glTexCoord2f(1, 1);
        glVertex2f(width + x, height + y);
        
        //Left Bottom
        glTexCoord2f(0, 1);
        glVertex2f(x, height + y);
	}

	/**
	 * Renders Texture with it's size
	 * @param x
	 * @param y
	 * @param texture
	 */
	public static void drawSprite(int x, int y, Sprite texture) 
	{
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		glBegin(GL_QUADS);
		{
			drawRawSprite(x, y, texture.getWidth(), texture.getHeight(), texture);
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}
	
	public static void drawSprite(int x, int y, Sprite texture, int color)
	{
		float[] colors = getColors(color);
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glColor3f(colors[0], colors[1], colors[2]);
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		glBegin(GL_QUADS);
		{
			drawRawSprite(x, y, texture.getWidth(), texture.getHeight(), texture);
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopAttrib();
		glPopMatrix();
	}

	public static void drawSprite(int x, int y, Sprite texture, int indexX, int indexY, int sizeX, int sizeY)
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

	public static void drawSprite(int x, int y, Sprite texture, float rotation)
	{
		drawSprite(x, y, texture, rotation, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff);
	}
	
	public static void drawSprite(int x, int y, Sprite texture, float rotation, int c00, int c10, int c11, int c01)
	{
		glEnable(GL_TEXTURE_2D);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glTranslatef(texture.getWidth() / 2f + x, texture.getHeight() / 2f + x, 0);
		glRotatef(rotation, 0f, 0f, 1f);
		glTranslatef(-texture.getWidth() / 2f - x, -texture.getHeight() / 2f - y, 0);
		texture.bind();
		glBegin(GL_QUADS);
		{
			//Left Top
            glTexCoord2i(0, 0);
            color(c00);
            glVertex2f(x, y);
            
            //Right Top
            glTexCoord2i(1, 0);
            color(c10);
            glVertex2f(x + texture.getWidth(), y);
            
            
            //Right Bottom
            glTexCoord2i(1, 1);
            color(c11);
            glVertex2f(x + texture.getWidth(), y + texture.getHeight());
            
            //Left Bottom
            glTexCoord2i(0, 1);
            color(c01);
            glVertex2f(x, y + texture.getHeight());
		}
		glEnd();
		glMatrixMode(GL_MODELVIEW);
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	/**
	 * Ehm... render... sprite...
	 * @param x
	 * @param y
	 * @param texture
	 * @param translateX
	 * @param translateY
	 * @param width
	 * @param height
	 * @param rotation
	 * @param repeatX
	 * @param repeatY
	 * @param flip
	 * @param repeat
	 * @param scaleX
	 * @param scaleY
	 * @param c00
	 * @param c10
	 * @param c11
	 * @param c01
	 */
	public static void drawSprite(int x, int y, Sprite texture, float translateX, float translateY, int width, int height, float rotation, int repeatX,
			int repeatY, boolean flip, boolean repeat, float scaleX, float scaleY, int c00, int c10, int c11, int c01)
	{
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		if (repeat)
		{
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		}
		glTranslatef(translateX, translateY, 0);
		glRotatef(rotation, 0f, 0f, 1f);
		glTranslatef(-translateX, -translateY, 0);
		//TODO: Scale
		//TODO: Add flip
		texture.bind();
		glBegin(GL_QUADS);
		{
			//Left Top
            glTexCoord2i(0, 0);
            color(c00);
            glVertex2f(x, y);
            
            //Right Top
            glTexCoord2i(repeatX, 0);
            color(c10);
            glVertex2f(x + width, y);
            
            
            //Right Bottom
            glTexCoord2i(repeatX, repeatY);
            color(c11);
            glVertex2f(x + width, y + height);
            
            //Left Bottom
            glTexCoord2i(0, repeatY);
            color(c01);
            glVertex2f(x, y + height);
		}
		glEnd();
		glMatrixMode(GL_MODELVIEW);
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	public static void drawSprite(int x, int y, Sprite texture, float translateX, float translateY, int width, int height, float rotation, int repeatX,
			int repeatY, boolean flip, boolean repeat, float scaleX, float scaleY)
	{
		drawSprite(x, y, texture, translateX, translateY, width, height, rotation, repeatX, repeatY, flip, repeat, scaleX, scaleY, 0xffffffff,
				0xffffffff, 0xffffffff, 0xffffffff);
	}
	
	public static void drawSprite(int x, int y, Sprite texture, float translateX, float translateY, int width, int height, float rotation, int repeatX,
			int repeatY, boolean repeat, int scaleX, int scaleY)
	{
		drawSprite(x, y, texture, translateX, translateY, width, height, rotation, repeatX, repeatY, repeat, false, scaleX, scaleY);
	}
	
	public static void drawSprite(int x, int y, Sprite texture, float translateX, float translateY, int width, int height, float rotation, int scaleX, int scaleY)
	{
		drawSprite(x, y, texture, translateX, translateY, width, height, rotation, 0, 0, false, scaleX, scaleY);
	}

	public static void drawSprite(int x, int y, Sprite texture, int width, int height)
	{
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		glBegin(GL_QUADS);
		{
			drawRawSprite(x, y, width, height, texture);
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}
	
	public static void drawSpriteRepeat(int x, int y, Sprite texture, int repeatX, int repeatY)
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
	
	public static void rotateScreen(int x, int y, float rotation)
	{
		glMatrixMode(GL_MODELVIEW);
		glTranslatef(x, y, 0);
		glRotatef(rotation, 0f, 0f, 1f);
		glTranslatef(-x, -y, 0);
	}

}
