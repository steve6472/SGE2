/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge2.main.gfx;

import static org.lwjgl.opengl.GL11.*;

public class Font
{
	
	Sprite font;
	Screen screen;
	public static final String chars = 	"ABCDEFGHIJKLMNOPQRSTUVWXYZ&♥|�?* abcdefghijklmnopqrstuvwxyz      " + 
										"0123456789.,:;'\"!?$%()-=+/><_#§\\@                             ";

	public Font(Screen screen)
	{
		this.screen = screen;
		font = new Sprite("font.png");
	}
	
	public void render(String text, int x, int y, int size, float red, float green, float blue)
	{
		int scale = 1;
		glMatrixMode(GL_TEXTURE);
		glScalef(1f / (float) font.getWidth(), 1f / (float) font.getHeight(), 1f);

		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		font.bind();
		glBegin(GL_QUADS);
		{
			
			for (int i = 0; i < text.length(); i++)
			{
				int char_index = chars.indexOf(text.charAt(i));
				if (char_index >= 0)
				{
					glColor3f(0.21f * red, 0.21f * green, 0.21f * blue);
					renderChar(x + (8 * i * scale) + 1, y + 1, char_index, scale);
					glColor3f(red, green, blue);
					renderChar(x + (8 * i * scale), y, char_index, scale);
				}
			}
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
		
		glScalef((float) font.getWidth(), (float) font.getHeight(), 1f);
	}
	
	public void render(String text, int x, int y, int size)
	{
		render(text, x, y, size, 1f, 1f, 1f);
	}
	
	public void render(String text, int x, int y, float red, float green, float blue)
	{
		render(text, x, y, 1, red, green, blue);
	}
	
	public void render(String text, int x, int y)
	{
		render(text, x, y, 1);
	}
	
	private void renderChar(int x, int y, int index, int size)
	{
		size = 1;
		int indexX = index % 64 * 8;
		int indexY = index / 64 * 8;
		
		int sizeX = 8 * size + indexX;
		int sizeY = 8 * size + indexY;
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
	
	public static String trim(String text, int width, int fontSize)
	{
		String r = "";
		
		int textWidth = text.length() * (8 * fontSize);
		
		int trim = (textWidth - (textWidth - width)) / (8 * fontSize);
		
		r = text.substring(0, Math.min(text.length(), trim > 0 ? trim : text.length()));
		
		return r;
	}

}
