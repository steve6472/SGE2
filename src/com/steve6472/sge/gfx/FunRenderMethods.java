/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 5. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gfx;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2i;
import static org.lwjgl.opengl.GL11.glVertex2d;

import com.steve6472.sge.main.Util;

public class FunRenderMethods
{
	public static void renderPulsatingSquare(Screen screen, int x, int y, double angle, Sprite texture, int size, int indexX0, int indexY0, int indexX1, int indexY1)
	{
		int indexX = indexX0;
		int indexY = indexY0;
		int sizeX = indexX1;
		int sizeY = indexY1;

		double rads0 = Math.toRadians(angle + 45);
		double rads1 = Math.toRadians(angle - 45);
		double rads2 = Math.toRadians(angle + 135);
		double rads3 = Math.toRadians(angle - 135);
		
		double xp11 = Math.cos(rads0) * size * Util.PYThAGORASRATIO + x;
		double yp11 = Math.sin(rads0) * size * Util.PYThAGORASRATIO + y;
//		screen.drawCircle(IValues.WIDTH / 2 + xp11, IValues.HEIGHT / 2 + yp11, 3, 0xff00ffff);
		
		double xp10 = Math.cos(rads1) * size * Util.PYThAGORASRATIO + x;
		double yp10 = Math.sin(rads1) * size * Util.PYThAGORASRATIO + y;
//		screen.drawCircle(IValues.WIDTH / 2 + xp10, IValues.HEIGHT / 2 + yp10, 3, 0xff00ffff);
		
		double xp01 = Math.cos(rads2) * size * Util.PYThAGORASRATIO + x;
		double yp01 = Math.sin(rads2) * size * Util.PYThAGORASRATIO + y;
//		screen.drawCircle(IValues.WIDTH / 2 + xp01, IValues.HEIGHT / 2 + yp01, 3, 0xff00ffff);
		
		double xp00 = Math.cos(rads3) * size * Util.PYThAGORASRATIO + x;
		double yp00 = Math.sin(rads3) * size * Util.PYThAGORASRATIO + y;
		
		glMatrixMode(GL_TEXTURE);
		glScalef(1f / (float) texture.getWidth(), 1f / (float) texture.getHeight(), 1f);

		glPushMatrix();
//		glRotated(getAngle(), mainApp.getCenter().getIntX(), mainApp.getCenter().getIntY(), 0);
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		glBegin(GL_QUADS);
		{
			//Left Top
            glTexCoord2i(indexX, indexY);
//            glVertex2f(x, y);
            glVertex2d(xp00, yp00);
            
            //Right Top
            glTexCoord2i(sizeX, indexY);
//            glVertex2f(sizeX + x - indexX, 0 + y);
            glVertex2d(sizeX + xp10 - indexX, yp10);
            
            
            //Right Bottom
            glTexCoord2i(sizeX, sizeY);
//            glVertex2f(sizeX + x - indexX, sizeY + y - indexY);
            glVertex2d(sizeX + xp11 - indexX, sizeY + yp11 - indexY);
            
            //Left Bottom
            glTexCoord2i(indexX, sizeY);
//            glVertex2f(x, sizeY + y - indexY);
            glVertex2d(xp01, sizeY + yp01 - indexY);
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
		
		glScalef((float) texture.getWidth(), (float) texture.getHeight(), 1f);
	}
}
