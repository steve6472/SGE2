/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.gfx.font;

import com.steve6472.sge.gfx.*;
import com.steve6472.sge.gfx.shaders.FontShader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.HashMap;

public class Font
{
	private static Sprite font;
	private static FontShader fontShader;
	private static Matrix4f transformation;
	private static FontTessellator tessellator;

	public static final HashMap<Character, Char> characters = new HashMap<>();

	private static final Char EMPTY = new Char(4096, ' ', 8);

	static int lastCustomIndex = 4096;
	
	static
	{
		addChar('A', 0, 8);
		addChar('B',1, 8);
		addChar('C',2, 8);
		addChar('D',3, 8);
		addChar('E',4, 8);
		addChar('F',5, 8);
		addChar('G',6, 8);
		addChar('H',7, 8);
		addChar('I',8, 8);
		addChar('J',9, 8);
		addChar('K',10, 8);
		addChar('L',11, 8);
		addChar('M',12, 8);
		addChar('N',13, 8);
		addChar('O',14, 8);
		addChar('P',15, 8);
		addChar('Q',16, 8);
		addChar('R',17, 8);
		addChar('S',18, 8);
		addChar('T',19, 8);
		addChar('U',20, 8);
		addChar('V',21, 8);
		addChar('W',22, 8);
		addChar('X',23, 8);
		addChar('Y',24, 8);
		addChar('Z',25, 8);

		addChar('&',26, 8);
		addChar('♥',27, 8);
		addChar('|',28, 3);
		addChar('�',29, 8);
		addChar('*',30, 6);
		addChar(' ',31, 8);
		
		addChar('a',32, 7);
		addChar('b',33, 7);
		addChar('c',34, 7);
		addChar('d',35, 7);
		addChar('e',36, 7);
		addChar('f',37, 6);
		addChar('g',38, 7);
		addChar('h',39, 7);
		addChar('i',40, 5);
		addChar('j',41, 7);
		addChar('k',42, 7);
		addChar('l',43, 5);
		addChar('m',44, 8);
		addChar('n',45, 7);
		addChar('o',46, 7);
		addChar('p',47, 7);
		addChar('q',48, 7);
		addChar('r',49, 7);
		addChar('s',50, 7);
		addChar('t',51, 7);
		addChar('u',52, 7);
		addChar('v',53, 7);
		addChar('w',54, 8);
		addChar('x',55, 8);
		addChar('y',56, 7);
		addChar('z',57, 7);

		addChar('0',64, 8);
		addChar('1',65, 8);
		addChar('2',66, 8);
		addChar('3',67, 8);
		addChar('4',68, 8);
		addChar('5',69, 8);
		addChar('6',70, 8);
		addChar('7',71, 8);
		addChar('8',72, 8);
		addChar('9',73, 8);
		
		addChar('.',74, 5);
		addChar(',',75, 4);
		addChar(':',76, 4);
		addChar(';',77, 4);
		addChar('\'', 78, 4);
		addChar('"',79, 8);
		addChar('!',80, 4);
		addChar('?',81, 8);
		addChar('$',82, 8);
		addChar('%',83, 8);
		addChar('(',84, 6);
		addChar(')',85, 6);
		addChar('-',86, 7);
		addChar('=',87, 8);
		addChar('+',88, 8);
		addChar('/',89, 8);
		addChar('>',90, 8);
		addChar('<',91, 8);
		addChar('_',92, 8);
		addChar('#',93, 7);
		addChar('§',94, 8);
		addChar('\\', 95, 8);
		
		addChar('@',97, 8);
		addChar('}',98, 5);
		addChar('{',99, 6);
		addChar('[',100, 6);
		addChar(']',101, 5);
		addChar('~',102, 8);
		addChar('	',103, 32);
		addChar('^',104, 8);
		addChar('\n',409, 8);
		addChar('¨',4095, 8);

		//Special characters
		//TODO: Fix NumberSelector2
		addChar('\u0000',4095, 8); // Error Char
		addChar('\u0001',4094, 8); // Arrow Up
		addChar('\u0002',4093, 8); // Arrow Down
		addChar('\u0003',4092, 8); // Box Plus
		addChar('\u0004',4091, 8); // Box Minus
		addChar('\u0005',4090, 8); // Full Triangle
		addChar('\u0006',4089, 8); // Bin Icon
		addChar('\u0007',4088, 8); // Folder Icon
		addChar('\u0008',4087, 8); // Back Arrow
		addChar('\u0009',4086, 8); // Forward Arrow
		addChar('\u0010',4085, 8); // Eye
		addChar('\u0011',4084, 8); // Point
		addChar('\u0012',4083, 8); // Line
		addChar('\u0013',4082, 8); // Triangle
		addChar('\u0014',4081, 8); // Exit
		addChar('\u0015',4080, 8); // Unselected Box
		addChar('\u0016',4079, 8); // Selected Box
		addChar('\u0017',4078, 8); // Unselected Circle
		addChar('\u0018',4077, 8); // Selected Circle
		addChar('\u0019',4076, 8); // Square
		addChar('\u000b',4075, 8); // Check
		addChar('\u000c',4074, 8); // X
	}

	private static void addChar(char character, int index, int width)
	{
		characters.put(character, new Char(index, character, width));
	}

	public static int getTextWidth(String text, int fontSize)
	{
		if (text.startsWith("[c") && text.endsWith("]"))
		{
			String t = text.substring(2, text.length() - 1);
			CustomChar ch = CustomChar.values()[Integer.parseInt(t)];
			return ch.getWidth();
		}
		int size = 0;
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			Char ch = characters.get(c);
			if (ch == null)
				throw new NullPointerException("Character not found: \'" + c + "\'");
			size += ch.getWidth() * fontSize;
		}
		
		return size;
	}

	public static void init()
	{
		font = new Sprite("font.png");
//		font.change(false);

		transformation = new Matrix4f();

		tessellator = new FontTessellator();

		fontShader = new FontShader();
		fontShader.bind();
		fontShader.setSampler(0);
		fontShader.setTransformation(transformation);
		fontShader.setFontSize(font.getWidth() / 8f, font.getHeight() / 8f);

		Shader.releaseShader();
	}

	public static void update(Matrix4f ortho)
	{
		fontShader.bind();
		fontShader.setProjection(ortho);
		Shader.releaseShader();
	}

	private static void renderFont(String text, int x, int y, int size, float red, float green, float blue, boolean shade)
	{
		if (text == null || text.isEmpty())
			return;

		SpriteRender.start();

		fontShader.bind();

		size *= 8;

		transformation
				.identity()
				.translate(font.getWidth() * 0.5f - (font.getWidth() - size) * 0.5f, font.getHeight() * 0.5f - (font.getHeight() - size) * 0.5f, 0)
				.translate(x, y, 0)
				.scale(size * 0.5f, size * 0.5f, 1);

		fontShader.setTransformation(transformation);

		font.bind();
		tessellator.begin(text.length() * 6 * (shade ? 2 : 1)); //Char count * vertex count * shade

		{
			float lx = 0;
			float ly = 0;

			float f = 1f / (size / 2f);

			for (int i = 0; i < text.length(); i++)
			{
				char c = text.charAt(i);

				if (c == '\n')
				{
					lx = 0;
					ly += f * size;
					continue;
				}

				Char ch = characters.get(c);

				if (ch == null)
					ch = EMPTY;

				float indexX = ch.getIndex() % 64 * 8;
				float indexY = (ch.getIndex() >> 6) * 8;
				float dx = indexX / (float) font.getWidth();
				float dy = indexY / (float) font.getHeight();

				tessellator.data(dx, dy);

				if (shade)
				{
					/* Shade */
					tessellator.col(red * 0.21f, green * 0.21f, blue * 0.21f, 1.0f);
					tessellator.pos(lx + f * (size / 8f), ly + f * (size / 8f)).endVertex();
				}

				/* Text */
				tessellator.col(red, green, blue, 1.0f);
				tessellator.pos(lx, ly).endVertex();
				lx += ch.getWidth() / 4f;
			}
		}

		tessellator.loadPos(0);
		tessellator.loadCol(1);
		tessellator.loadData(2);

		tessellator.draw(Tessellator3D.POINTS);

		tessellator.disable(0, 1, 2);
		SpriteRender.end();
	}

	/*

	private static void updateShader(int x, int y, int index, int size)
	{
		float indexX = index % 64 * 8;
		float indexY = index / 64 * 8;

		fontShader.setSpriteData(
				font.getWidth() / (float) size, font.getHeight() / (float) size,
				indexX / (float) font.getWidth(), indexY / (float) font.getHeight());

		transformation
				.identity()
				.translate(font.getWidth() * 0.5f - (font.getWidth() - size) * 0.5f, font.getHeight() * 0.5f - (font.getHeight() - size) * 0.5f, 0)
				.translate(x, y, 0)
				.rotate((float) Math.toRadians(0), 0, 0, 1)
				.scale(size * 0.5f, size * 0.5f, 1);

		fontShader.setTransformation(transformation);
	}

	private static void renderFont(String text, int x, int y, int size, float red, float green, float blue, boolean shade)
	{
		if (text == null || text.isEmpty())
			return;

		SpriteRender.start();

		fontShader.bind();

		size *= 8;

		transformation
				.identity()
				.translate(font.getWidth() * 0.5f - (font.getWidth() - size) * 0.5f, font.getHeight() * 0.5f - (font.getHeight() - size) * 0.5f, 0)
				.translate(x, y, 0)
				.scale(size * 0.5f, size * 0.5f, 1);

		fontShader.setTransformation(transformation);

		font.bind();
		tessellator.begin(text.length() * 6 * 2);

		{
			float lx = 0;

			float f = 1f / (size / 2f);

			for (int i = 0; i < text.length(); i++)
			{
				char c = text.charAt(i);
				Char ch = characters.get(c);

				if (ch == null)
					ch = EMPTY;

				float indexX = ch.getIndex() % 64 * 8;
				float indexY = ch.getIndex() / 64 * 8;

				float dx = font.getWidth() / (float) size, dy = font.getHeight() / (float) size, dw = indexX / (float) font.getWidth(), dh = indexY / (float) font.getHeight();

				tessellator.data(dx, dy, dw, dh);

				// Shade
				tessellator.col(red * 0.21f, green * 0.21f, blue * 0.21f, 1.0f);
				tessellator.pos(-1 + lx + f, +1 + f).tex(0, 1).endVertex();
				tessellator.pos(-1 + lx + f, -1 + f).tex(0, 0).endVertex();
				tessellator.pos(+1 + lx + f, -1 + f).tex(1, 0).endVertex();

				tessellator.pos(+1 + lx + f, -1 + f).tex(1, 0).endVertex();
				tessellator.pos(+1 + lx + f, +1 + f).tex(1, 1).endVertex();
				tessellator.pos(-1 + lx + f, +1 + f).tex(0, 1).endVertex();

				// Text
				tessellator.col(red, green, blue, 1.0f);
				tessellator.pos(-1 + lx, +1).tex(0, 1).endVertex();
				tessellator.pos(-1 + lx, -1).tex(0, 0).endVertex();
				tessellator.pos(+1 + lx, -1).tex(1, 0).endVertex();

				tessellator.pos(+1 + lx, -1).tex(1, 0).endVertex();
				tessellator.pos(+1 + lx, +1).tex(1, 1).endVertex();
				tessellator.pos(-1 + lx, +1).tex(0, 1).endVertex();
				lx += ch.getWidth() / 4f;
			}
		}

		tessellator.loadPos(0);
		tessellator.loadTex(1);
		tessellator.loadCol(2);
		tessellator.loadData(3);

		tessellator.draw(Tessellator3D.TRIANGLES);

		tessellator.disable(0, 1, 2, 3);
		SpriteRender.end();
	}



	private static void renderFont(String text, int x, int y, int size, float red, float green, float blue, boolean shade)
	{
		if (text == null || text.isEmpty())
			return;

		SpriteRender.start();

		fontShader.bind();

		font.bind();
		glBindVertexArray(SpriteRender.getVao());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		{
			int lx = 0;

			// Render shadow
			if (shade)
			{
				fontShader.setColor(0.21f * red, 0.21f * green, 0.21f * blue, 1.0f);

				for (int i = 0; i < text.length(); i++)
				{
					char c = text.charAt(i);
					Char ch = characters.get(c);

					if (ch == null)
						ch = EMPTY;

					updateShader(x + lx + size, y + size, ch.getIndex(), size * 8);
					GL11.glDrawArrays(GL_TRIANGLES, 0, 6);
					lx += ch.getWidth() * size;
				}
			}

			lx = 0;

			fontShader.setColor(red, green, blue, 1.0f);

			for (int i = 0; i < text.length(); i++)
			{
				char c = text.charAt(i);
				Char ch = characters.get(c);

				if (ch == null)
					ch = EMPTY;

				updateShader(x + lx, y, ch.getIndex(), size * 8);
				GL11.glDrawArrays(GL_TRIANGLES, 0, 6);
				lx += ch.getWidth() * size;
			}
		}

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
		SpriteRender.end();
	}



	private static void renderFont(String text, int x, int y, int size, float red, float green, float blue, boolean shade)
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
		////
		{
			int lx = 0;
			for (int i = 0; i < text.length(); i++)
			{
				char c = text.charAt(i);
				Char ch = characters.get(c);

				if (ch == null)
					ch = EMPTY;

				if (shade)
				{
					glColor3f(0.21f * red, 0.21f * green, 0.21f * blue);
					renderChar(x + lx + size, y + size, ch.getIndex(), size);
				}

				glColor3f(red, green, blue);
				renderChar(x + lx, y, ch.getIndex(), size);
				lx += ch.getWidth() * size;
			}
		}//
		{
			int lx = 0;

			// Render shadow
			if (shade)
			{
				glColor3f(0.21f * red, 0.21f * green, 0.21f * blue);
				for (int i = 0; i < text.length(); i++)
				{
					char c = text.charAt(i);
					Char ch = characters.get(c);

					if (ch == null)
						ch = EMPTY;

					renderChar(x + lx + size, y + size, ch.getIndex(), size);
					lx += ch.getWidth() * size;
				}
			}

			lx = 0;

			glColor3f(red, green, blue);
			for (int i = 0; i < text.length(); i++)
			{
				char c = text.charAt(i);
				Char ch = characters.get(c);

				if (ch == null)
					ch = EMPTY;

				renderChar(x + lx, y, ch.getIndex(), size);
				lx += ch.getWidth() * size;
			}
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glScalef((float) font.getWidth(), (float) font.getHeight(), 1f);
		glPopAttrib();
		glPopMatrix();
		
		glMatrixMode(GL_MODELVIEW);
	}

	public static void renderChar(int x, int y, int index, int size)
	{
		int indexX = index % 64 * 8 * size;
		int indexY = index / 64 * 8 * size;

		int sizeX = 8 * size + indexX;
		int sizeY = 8 * size + indexY;
		//Left Top
		glTexCoord2i(indexX, indexY);
		glVertex2f(x, y);

		//Right Top
		glTexCoord2i(sizeX, indexY);
		glVertex2f(sizeX + x - indexX, y);

		//Right Bottom
		glTexCoord2i(sizeX, sizeY);
		glVertex2f(sizeX + x - indexX, sizeY + y - indexY);

		//Left Bottom
		glTexCoord2i(indexX, sizeY);
		glVertex2f(x, sizeY + y - indexY);
	}*/
	
	public static void render(String text, int x, int y, int size, boolean shade)
	{
		render(text, x, y, size, 1f, 1f, 1f, shade);
	}
	
	public static void render(String text, int x, int y, float red, float green, float blue, boolean shade)
	{
		render(text, x, y, 1, red, green, blue, shade);
	}
	
	public static void render(String text, int x, int y, int size, float red, float green, float blue, boolean shade)
	{
		renderFont(text, x, y, size, red, green, blue, shade);
	}

	public static void render(String text, int x, int y, int size)
	{
		render(text, x, y, size, 1f, 1f, 1f, true);
	}
	
	public static void render(String text, int x, int y, float red, float green, float blue)
	{
		render(text, x, y, 1, red, green, blue, true);
	}
	
	public static void render(String text, int x, int y, int size, float red, float green, float blue)
	{
		render(text, x, y, size, red, green, blue, true);
	}

	public static void render(String text, int x, int y, int size, Vector3f color)
	{
		render(text, x, y, size, color.x, color.y, color.z);
	}

	public static void render(String text, int x, int y)
	{
		render(text, x, y, 1);
	}

	public static void render(int x, int y, String text)
	{
		render(text, x, y, 1);
	}

	/**
	 *
	 * @param x x
	 * @param y y
	 * @param size font size
	 * @param text Color: [#ff80ff00] [0.5,1.0,0.0,1.0] [127,255,0,255] (aplha is optional)
	 *             Space: [x10] [y16]
	 *             New line: \n (doesn't need new entry)
	 *             Shade: [s1] [s0] (true, false)
	 */
	public static void renderCustom(int x, int y, float size, Object... text)
	{
		if (text == null)
			return;

		SpriteRender.start();

		fontShader.bind();

		size *= 8f;

		transformation
				.identity()
				.translate(font.getWidth() * 0.5f - (font.getWidth() - size) * 0.5f, font.getHeight() * 0.5f - (font.getHeight() - size) * 0.5f, 0)
				.translate(x, y, 0)
				.scale(size * 0.5f, size * 0.5f, 1);

		fontShader.setTransformation(transformation);

		font.bind();

		float red = 1.0f, green = 1.0f, blue = 1.0f, alpha = 1.0f;
		float lx = 0;
		float ly = 0;
		float f = 1f / (size * 0.5f);
		boolean shade = true;

		int temp = 0;
		for (Object o : text)
		{
			if (o instanceof String)
				temp += ((String) o).length();
			else if (o instanceof Number)
				temp += String.valueOf(((Number) o).doubleValue()).length();
			else if (o == null)
				temp += 5;
			else if (o instanceof CustomChar)
				temp += ((CustomChar) o).toString().length();
			else
				temp += o.toString().length();
		}
		tessellator.begin(temp * 6 * 2); // Char count * vertex count * shade

		for (Object o : text)
		{
			if (o == null)
				continue;

			String s = o.toString();

			if (s == null || s.isEmpty())
				continue;

			/* Check color, space, and stuff */
			if (s.startsWith("[") && s.endsWith("]"))
			{
				if (s.charAt(1) == 's')
				{
					if (s.charAt(2) == '1')
						shade = true;
					else if (s.charAt(2) == '0')
						shade = false;
					continue;
				}
				else if (s.charAt(1) == 'x')
				{
					String t = s.substring(2, s.length() - 1);
					lx += 1f / 4f * Float.parseFloat(t);
					continue;
				}
				else if (s.charAt(1) == 'y')
				{
					String t = s.substring(2, s.length() - 1);
					ly += 1f / 4f * Float.parseFloat(t);
					continue;
				}
				else if (s.charAt(1) == 'c')
				{
					String t = s.substring(2, s.length() - 1);
					CustomChar ch = CustomChar.values()[Integer.parseInt(t)];

					float indexX = ch.getIndex() % 64 * 8;
					float indexY = (ch.getIndex() >> 6) * 8;
					float dx = indexX / (float) font.getWidth();
					float dy = indexY / (float) font.getHeight();

					tessellator.data(dx, dy);

					if (shade)
					{
						/* Shade */
						tessellator.col(red * 0.21f, green * 0.21f, blue * 0.21f, alpha);
						tessellator.pos(lx + f * (size / 8f), ly + f * (size / 8f)).endVertex();
					}

					/* Text */
					tessellator.col(red, green, blue, 1.0f);
					tessellator.pos(lx, ly).endVertex();
					lx += ch.getWidth() / 4f;
					continue;
				}
				else if (s.charAt(1) == '#')
				{
					String t = s.substring(2, s.length() - 1);
					float c0 = Long.parseLong(t.substring(0, 2), 16) / 255f;
					float c1 = Long.parseLong(t.substring(2, 4), 16) / 255f;
					float c2 = Long.parseLong(t.substring(4, 6), 16) / 255f;
					if (t.length() == 6) // Without alpha
					{
						red = c0;
						green = c1;
						blue = c2;
						continue;
					}
					else if (t.length() == 8) // With alpha
					{
						alpha = c0;
						red = c1;
						green = c2;
						blue = Long.parseLong(t.substring(6, 8), 16) / 255f;
						continue;
					}
				}
				else if (s.contains("."))
				{
					long count = s.chars().filter(ch -> ch == '.').count();
					String t = s.substring(1, s.length() - 1);
					String[] g = t.split(",");

					red = Float.parseFloat(g[0].trim());
					green = Float.parseFloat(g[1].trim());
					blue = Float.parseFloat(g[2].trim());

					if (count == 4) // Alpha
					{
						alpha = Float.parseFloat(g[3].trim());
					}
					continue;
				}
				else
				{
					long count = s.chars().filter(ch -> ch == '.').count();
					String t = s.substring(1, s.length() - 1);
					String[] g = t.split(",");

					red = Integer.parseInt(g[0].trim()) / 255f;
					green = Integer.parseInt(g[1].trim()) / 255f;
					blue = Integer.parseInt(g[2].trim()) / 255f;

					if (count == 4) // Alpha
					{
						alpha = Integer.parseInt(g[3].trim()) / 255f;
					}
					continue;
				}
			}

			for (int i = 0; i < s.length(); i++)
			{
				char c = s.charAt(i);

				if (c == '\n')
				{
					lx = 0;
					ly += f * size;
					continue;
				}

				Char ch = characters.get(c);

				if (ch == null)
					ch = EMPTY;

				float indexX = ch.getIndex() % 64 * 8;
				float indexY = (ch.getIndex() >> 6) * 8;
				float dx = indexX / (float) font.getWidth();
				float dy = indexY / (float) font.getHeight();

				tessellator.data(dx, dy);

				if (shade)
				{
					/* Shade */
					tessellator.col(red * 0.21f, green * 0.21f, blue * 0.21f, alpha);
					tessellator.pos(lx + f * (size / 8f), ly + f * (size / 8f)).endVertex();
				}

				/* Text */
				tessellator.col(red, green, blue, alpha);
				tessellator.pos(lx, ly).endVertex();
				lx += ch.getWidth() / 4f;
			}
		}

		tessellator.loadPos(0);
		tessellator.loadCol(1);
		tessellator.loadData(2);

		tessellator.draw(Tessellator3D.POINTS);

		tessellator.disable(0, 1, 2);
		SpriteRender.end();
	}

/*
	/**
	 *
	 * @param text
	 * @param keys R:[#ff0000];G:[0.0,1.0,0.0]
	 * @param x
	 * @param y
	 */
/*
	public static void renderCustom(String text, String keys, int x, int y)
	{
		String[] arr = keys.split(";");
		for (String s : arr)
		{
			String[] ar = s.split(":");
			String key = ar[0];
			String col = ar[1];
			text = text.replace(key, col);
		}
		renderCustom(text, x, y);
	}*/

	/*
	/**
	 * Bugs: Doesn't renderSprite [ if is not part of color tag.
	 *       Any non complete color tag just straight up crashes!
	 *
	 *       Render as one text and color the chars with the help of a shader
	 *       Make new space tag [s#]  # - number of pixels
	 *
	 * @param text
	 * @param x
	 * @param y
	 */
	/*
	public static void renderCustom(String text, int x, int y)
	{
		List<ColoredText> coloredText = new ArrayList<>();

		StringBuilder normalText = new StringBuilder();
		StringBuilder color = new StringBuilder();

		boolean storingToColor = false;
		boolean ignoreNext = false;

		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);

			if (c == '\\' && !ignoreNext)
			{
				ignoreNext = true;
				continue;
			}

			if (ignoreNext)
			{
				normalText.append(c);
				ignoreNext = false;
				continue;
			}

			if (c == '[')
			{
				coloredText.add(new ColoredText(normalText.toString(), color.toString()));
				normalText = new StringBuilder();
				color = new StringBuilder();
				storingToColor = true;
				continue;
			}

			if (!storingToColor) normalText.append(c);

			if (c == ']')
			{
				storingToColor = false;
				continue;
			}

			if (storingToColor) color.append(c);
		}

		coloredText.add(new ColoredText(normalText.toString(), color.toString()));

		int tx = 0;

		for (ColoredText s : coloredText)
		{
			render(s.text, x + tx, y, s.r, s.g, s.b);
			tx += getTextWidth(s.text, 1);
		}
	}*/

	public static void stringCenter(int x, int y, int w, int h, String text, int fontSize, Vector2f out)
	{
		int fontWidth = getTextWidth(text, fontSize) / 2;
		int fontHeight = ((8 * fontSize)) / 2;
		out.set(x + w / 2f - fontWidth, y + h / 2f - fontHeight);
	}

	public static int stringCenterX(int x, int width, String text, int fontSize)
	{
		if (text == null)
			return 0;
		int fontWidth = getTextWidth(text, fontSize) / 2;
		return x + width / 2 - fontWidth;
	}

	public static Sprite getFont()
	{
		return font;
	}

	public static Object[] toSGA(String text)
	{
		Object[] o = new Object[text.length()];

		for (int i = 0; i < text.length(); i++)
		{
			o[i] = switch(text.charAt(i))
					{
						case 'a', 'A' -> CustomChar.SGA_A;
						case 'b', 'B' -> CustomChar.SGA_B;
						case 'c', 'C' -> CustomChar.SGA_C;
						case 'd', 'D' -> CustomChar.SGA_D;
						case 'e', 'E' -> CustomChar.SGA_E;
						case 'f', 'F' -> CustomChar.SGA_F;
						case 'g', 'G' -> CustomChar.SGA_G;
						case 'h', 'H' -> CustomChar.SGA_H;
						case 'i', 'I' -> CustomChar.SGA_I;
						case 'j', 'J' -> CustomChar.SGA_J;
						case 'k', 'K' -> CustomChar.SGA_K;
						case 'l', 'L' -> CustomChar.SGA_L;
						case 'm', 'M' -> CustomChar.SGA_M;
						case 'n', 'N' -> CustomChar.SGA_N;
						case 'o', 'O' -> CustomChar.SGA_O;
						case 'p', 'P' -> CustomChar.SGA_P;
						case 'q', 'Q' -> CustomChar.SGA_Q;
						case 'r', 'R' -> CustomChar.SGA_R;
						case 's', 'S' -> CustomChar.SGA_S;
						case 't', 'T' -> CustomChar.SGA_T;
						case 'u', 'U' -> CustomChar.SGA_U;
						case 'v', 'V' -> CustomChar.SGA_V;
						case 'w', 'W' -> CustomChar.SGA_W;
						case 'x', 'X' -> CustomChar.SGA_X;
						case 'y', 'Y' -> CustomChar.SGA_Y;
						case 'z', 'Z' -> CustomChar.SGA_Z;
						default -> text.charAt(i);
					};
		}
		return o;
	}

	public static Object[] toThinSGA(String text)
	{
		Object[] o = new Object[text.length()];

		for (int i = 0; i < text.length(); i++)
		{
			o[i] = switch(text.charAt(i))
					{
						case 'a', 'A' -> CustomChar.SGA_THIN_A;
						case 'b', 'B' -> CustomChar.SGA_THIN_B;
						case 'c', 'C' -> CustomChar.SGA_THIN_C;
						case 'd', 'D' -> CustomChar.SGA_THIN_D;
						case 'e', 'E' -> CustomChar.SGA_THIN_E;
						case 'f', 'F' -> CustomChar.SGA_THIN_F;
						case 'g', 'G' -> CustomChar.SGA_THIN_G;
						case 'h', 'H' -> CustomChar.SGA_THIN_H;
						case 'i', 'I' -> CustomChar.SGA_THIN_I;
						case 'j', 'J' -> CustomChar.SGA_THIN_J;
						case 'k', 'K' -> CustomChar.SGA_THIN_K;
						case 'l', 'L' -> CustomChar.SGA_THIN_L;
						case 'm', 'M' -> CustomChar.SGA_THIN_M;
						case 'n', 'N' -> CustomChar.SGA_THIN_N;
						case 'o', 'O' -> CustomChar.SGA_THIN_O;
						case 'p', 'P' -> CustomChar.SGA_THIN_P;
						case 'q', 'Q' -> CustomChar.SGA_THIN_Q;
						case 'r', 'R' -> CustomChar.SGA_THIN_R;
						case 's', 'S' -> CustomChar.SGA_THIN_S;
						case 't', 'T' -> CustomChar.SGA_THIN_T;
						case 'u', 'U' -> CustomChar.SGA_THIN_U;
						case 'v', 'V' -> CustomChar.SGA_THIN_V;
						case 'w', 'W' -> CustomChar.SGA_THIN_W;
						case 'x', 'X' -> CustomChar.SGA_THIN_X;
						case 'y', 'Y' -> CustomChar.SGA_THIN_Y;
						case 'z', 'Z' -> CustomChar.SGA_THIN_Z;
						default -> text.charAt(i);
					};
		}
		return o;
	}

	public static void renderFps(int x, int y, float fps)
	{
		String color;
		if (fps <= 10)
			color = "[#8b0000]";
		else if (fps > 10 && fps <= 20)
			color = "[#ff4c00]";
		else if (fps > 20 && fps < 40)
			color = "[#90ee90]";
		else
			color = "[#32cd32]";

		renderCustom(x, y, 1, "Fps: ", color, fps);
	}

}