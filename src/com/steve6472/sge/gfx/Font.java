/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.gfx;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;

import org.joml.Matrix4f;

import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;
import com.steve6472.sge.test.ShaderTest2;

public class Font
{
	
	Sprite font, invertedFont;
	Screen screen;
	public static final String chars = 	"ABCDEFGHIJKLMNOPQRSTUVWXYZ&♥|�* abcdefghijklmnopqrstuvwxyz      " + 
										"0123456789.,:;'\"!?$%()-=+/><_#§\\@                             ";
	
	
	public static final HashMap<Character, Char> characters = new HashMap<Character, Char>();
	
	static
	{
		characters.put('A', new Char(0, 'A', 8));
		characters.put('B', new Char(1, 'B', 8));
		characters.put('C', new Char(2, 'C', 8));
		characters.put('D', new Char(3, 'D', 8));
		characters.put('E', new Char(4, 'E', 8));
		characters.put('F', new Char(5, 'F', 8));
		characters.put('G', new Char(6, 'G', 8));
		characters.put('H', new Char(7, 'H', 8));
		characters.put('I', new Char(8, 'I', 8));
		characters.put('J', new Char(9, 'J', 8));
		characters.put('K', new Char(10, 'K', 8));
		characters.put('L', new Char(11, 'L', 8));
		characters.put('M', new Char(12, 'M', 8));
		characters.put('N', new Char(13, 'N', 8));
		characters.put('O', new Char(14, 'O', 8));
		characters.put('P', new Char(15, 'P', 8));
		characters.put('Q', new Char(16, 'Q', 8));
		characters.put('R', new Char(17, 'R', 8));
		characters.put('S', new Char(18, 'S', 8));
		characters.put('T', new Char(19, 'T', 8));
		characters.put('U', new Char(20, 'U', 8));
		characters.put('V', new Char(21, 'V', 8));
		characters.put('W', new Char(22, 'W', 8));
		characters.put('X', new Char(23, 'X', 8));
		characters.put('Y', new Char(24, 'Y', 8));
		characters.put('Z', new Char(25, 'Z', 8));

		characters.put('&', new Char(26, '&', 8));
		characters.put('♥', new Char(27, '♥', 8));
		characters.put('|', new Char(28, '|', 3));
		characters.put('�', new Char(29, '�', 8));
		characters.put('*', new Char(30, '*', 8));
		characters.put(' ', new Char(31, ' ', 8));
		
		characters.put('a', new Char(32, 'a', 7));
		characters.put('b', new Char(33, 'b', 7));
		characters.put('c', new Char(34, 'c', 7));
		characters.put('d', new Char(35, 'd', 7));
		characters.put('e', new Char(36, 'e', 7));
		characters.put('f', new Char(37, 'f', 6));
		characters.put('g', new Char(38, 'g', 7));
		characters.put('h', new Char(39, 'h', 7));
		characters.put('i', new Char(40, 'i', 5));
		characters.put('j', new Char(41, 'j', 7));
		characters.put('k', new Char(42, 'k', 7));
		characters.put('l', new Char(43, 'l', 5));
		characters.put('m', new Char(44, 'm', 8));
		characters.put('n', new Char(45, 'n', 7));
		characters.put('o', new Char(46, 'o', 7));
		characters.put('p', new Char(47, 'p', 7));
		characters.put('q', new Char(48, 'q', 7));
		characters.put('r', new Char(49, 'r', 7));
		characters.put('s', new Char(50, 's', 7));
		characters.put('t', new Char(51, 't', 7));
		characters.put('u', new Char(52, 'u', 7));
		characters.put('v', new Char(53, 'v', 7));
		characters.put('w', new Char(54, 'w', 7));
		characters.put('x', new Char(55, 'x', 8));
		characters.put('y', new Char(56, 'y', 7));
		characters.put('z', new Char(57, 'z', 7));

		characters.put('0', new Char(64, '0', 8));
		characters.put('1', new Char(65, '1', 8));
		characters.put('2', new Char(66, '2', 8));
		characters.put('3', new Char(67, '3', 8));
		characters.put('4', new Char(68, '4', 8));
		characters.put('5', new Char(69, '5', 8));
		characters.put('6', new Char(70, '6', 8));
		characters.put('7', new Char(71, '7', 8));
		characters.put('8', new Char(72, '8', 8));
		characters.put('9', new Char(73, '9', 8));
		
		characters.put('.', new Char(74, '.', 4));
		characters.put(',', new Char(75, ',', 4));
		characters.put(':', new Char(76, ':', 4));
		characters.put(';', new Char(77, ';', 4));
		characters.put('\'', new Char(78, '\'', 4));
		characters.put('"', new Char(79, '"', 8));
		characters.put('!', new Char(80, '!', 4));
		characters.put('?', new Char(81, '?', 8));
		characters.put('$', new Char(82, '$', 8));
		characters.put('%', new Char(83, '%', 8));
		characters.put('(', new Char(84, '(', 5));
		characters.put(')', new Char(85, ')', 5));
		characters.put('-', new Char(86, '-', 7));
		characters.put('=', new Char(87, '=', 8));
		characters.put('+', new Char(88, '+', 8));
		characters.put('/', new Char(89, '/', 8));
		characters.put('>', new Char(90, '>', 8));
		characters.put('<', new Char(91, '<', 7));
		characters.put('_', new Char(92, '_', 8));
		characters.put('#', new Char(93, '#', 7));
		characters.put('§', new Char(94, '§', 8));
		characters.put('\\', new Char(95, '\\', 8));
		
		characters.put('@', new Char(97, '@', 8));
		characters.put('}', new Char(98, '}', 5));
		characters.put('{', new Char(99, '{', 5));
		characters.put('[', new Char(100, '[', 5));
		characters.put(']', new Char(101, ']', 5));
		characters.put('~', new Char(102, '~', 8));
		
		Steve : for(int age = 0; age < 5; age++)
		{
			if (age > 18)
				break Steve;
			else
				continue Steve;
		}
	}
	
	/*
	static
	{
		String cha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		cha = cha.toLowerCase();
		for (int i = 0; i < cha.length(); i++)
			{
			System.out.println("characters.put('" + cha.charAt(i) + "', new Char(" + (i + 7 + 25) + ", '" + cha.charAt(i) + "', 8));");
			}
	}
	*/
	
	public static Shader fontShader;
	public static Model fontModel;

	public Font(Screen screen)
	{
		this.screen = screen;
		font = new Sprite("font.png");
		invertedFont = new Sprite("invertedFont.png");
		String fs = "#version 330 core\n"
				+ "uniform sampler2D sampler;\n"
				+ "uniform vec2 texture;\n"
				+ "in vec4 vColor;\n"
				+ "in vec2 vTexture;\n"
				+ "out vec4 fragColor;\n"
				+ "void main()\n"
				+ "{\n"
				+ "fragColor = texture2D(sampler, vTexture + texture) + vColor;\n"
				+ "}";
		String vs = "#version 330 core\n"
				+ "layout(location = 0) in vec2 position;\n"
				+ "layout(location = 1) in vec2 texture;\n"
				+ "layout(location = 2) in vec4 color;\n"
				+ "uniform mat4 projection;\n"
				+ "out vec4 vColor;\n"
				+ "out vec2 vTexture;\n"
				+ "void main()\n"
				+ "{\n"
				+ "vColor = color;\n"
				+ "vTexture = texture;\n"
				+ "gl_Position = projection *  vec4(position, 0.0, 1.0);\n"
				+ "}";
		fontShader = new Shader(vs, fs);
		fontShader.setUniform1f("sampler", 0);
		fontModel = new Model(ShaderTest2.fillScreen(), ShaderTest2.createTexture(8, 8, font), ShaderTest2.createArray(0f));
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
			/*
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
			}*/
			
			int lx = 0;
			
			for (int i = 0; i < text.length(); i++)
			{
				char c = text.charAt(i);
				Char ch = characters.get(c);
				if (ch == null)
					continue;
				if (shade)
				{
					glColor3f(0.21f * red, 0.21f * green, 0.21f * blue);
					renderChar(x + lx + size, y + size, ch.getIndex(), size);
				}
				glColor3f(red, green, blue);
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
	
	public static void drawFont(MainApplication mainApp, String text, int x, int y, Camera camera)
	{
		float size = 1 * 4;
		
		Matrix4f pro = new Matrix4f()
				.scale(size)
				.translate(size * 32 - 1 - (float) x / size, size * 18 - 1 - (float) y / size, 0);
			
		mainApp.getFont().getFont().bind();
		fontShader.bind();

		for (int i = 0; i < text.length(); i++)
		{
			int char_index = Font.chars.indexOf(text.charAt(i));
			if (char_index >= 0)
			{
				renderChar(-i * 2, 0, char_index, (int) size, pro, camera, fontShader, fontModel);
			}
		}
	}
	
	private static void renderChar(float x, float y, int index, int size, Matrix4f mat, Camera camera, Shader fontShader, Model fontModel)
	{
		size = 1;
		float indexX = (index % 64) / 64f;
		float indexY = (index / 64) / 64f;
		
		Matrix4f tilePos = new Matrix4f().translate(x, y, 0);
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(mat, target);
		target.mul(tilePos);
		
		fontShader.setUniform2f("texture", indexX, indexY);
		
		fontShader.setUniformMat4f("projection", target);
//		fontShader.setUniform4f("col", 0.21f, 0.21f, 0.21f, 1f);
		
		fontModel.render();
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
		return new Vec2(recSize.from.getX() + (recSize.getWidth() / 2) - ((text.length() * (8 * fontSize)) / 2),
				recSize.from.getY() + (recSize.getHeight() / 2) - (4 * fontSize));
	}
	
	public Sprite getFont()
	{
		return font;
	}
	
	public Sprite getInvertedFont()
	{
		return invertedFont;
	}

}