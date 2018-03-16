/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.gfx;

import static org.lwjgl.opengl.GL11.*;

import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;

public class Font
{
	
	Sprite font;
	Screen screen;
	public static final String chars = 	"ABCDEFGHIJKLMNOPQRSTUVWXYZ&♥|�* abcdefghijklmnopqrstuvwxyz      " + 
										"0123456789.,:;'\"!?$%()-=+/><_#§\\@                             ";

	public Font(Screen screen)
	{
		this.screen = screen;
		font = new Sprite("font.png");
	}
	
	public void renderFont(String text, int x, int y, int size, float red, float green, float blue, boolean shade)
	{
		if (text == null || text.isEmpty())
			return;
		
		glMatrixMode(GL_TEXTURE);
		
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glScalef(1f / (float) font.getWidth() / size, 1f / (float) font.getHeight() / size, 1f);
		glEnable(GL_TEXTURE_2D);
		font.bind();
		glBegin(GL_QUADS);
		{
			
			for (int i = 0; i < text.length(); i++)
			{
				int char_index = chars.indexOf(text.charAt(i));
				if (char_index >= 0)
				{
					if (shade)
					{
						glColor3f(0.21f * red, 0.21f * green, 0.21f * blue);
						renderChar(x + (8 * i * size) + size, y + size, char_index, size);
					}
					glColor3f(red, green, blue);
					renderChar(x + (8 * i * size), y, char_index, size);
				}
			}
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glScalef((float) font.getWidth(), (float) font.getHeight(), 1f);
		glPopAttrib();
		glPopMatrix();
		
		glMatrixMode(GL_MODELVIEW);
	}
	
	public void render(String text, int x, int y, int size, boolean shade)
	{
		render(text, x, y, size, 1f, 1f, 1f, shade);
	}
	
	public void render(String text, int x, int y, float red, float green, float blue, boolean shade)
	{
		render(text, x, y, 1, red, green, blue, shade);
	}
	
	public void render(String text, int x, int y, int size, float red, float green, float blue, boolean shade)
	{
		renderFont(text, x, y, size, red, green, blue, shade);
	}
	
	public void render(String text, int x, int y, int size)
	{
		render(text, x, y, size, 1f, 1f, 1f, true);
	}
	
	public void render(String text, int x, int y, float red, float green, float blue)
	{
		render(text, x, y, 1, red, green, blue, true);
	}
	
	public void render(String text, int x, int y, int size, float red, float green, float blue)
	{
		render(text, x, y, size, red, green, blue, true);
	}
	
	public void render(String text, int x, int y)
	{
		render(text, x, y, 1);
	}
	
	private void renderChar(int x, int y, int index, int size)
	{
//		size = 1;
		int indexX = index % 64 * 8 * size;
		int indexY = index / 64 * 8 * size;
		
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

	public static Vec2 stringCenter(AABB recSize, String text, int fontSize)
	{
		return new Vec2(recSize.from.getX() + (recSize.getWidth() / 2) - ((text.length() * (8 * fontSize)) / 2), recSize.from.getY() + (recSize.getHeight() / 2) - (4 * fontSize));
	}

}
