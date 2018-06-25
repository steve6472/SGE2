/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 13. 12. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.gui.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Char;
import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.KeyList;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.SGArray;
import com.steve6472.sge.main.callbacks.CharCallback;
import com.steve6472.sge.main.callbacks.KeyCallback;

public class TextArea extends Component implements IFocusable, KeyList
{
	private static final long serialVersionUID = 7783560380863267944L;
	boolean isFocused = false;
	private boolean showCarret = false;
	int carretPositionX = 0, carretPositionY = 0;
//	private int fontSize = 1;
	private double carretTick = 0;
	private boolean isEnabled = true;
	List<String> text;
	public int xLimit = Integer.MAX_VALUE;

	PipedOutputStream pos;
	public PrintStream output;
	PipedInputStream pis;
	BufferedReader reader;

	SGArray<ColorIndex2> colors;
	SGArray<ColorIndex> words;

	public TextArea(int maxY)
	{
		super();

		colors = new SGArray<ColorIndex2>();
		words = new SGArray<ColorIndex>();
		
		text = new ArrayList<String>();
		for (int i = 0; i < maxY; i++)
			text.add("");
	}

	@Override
	public void init(MainApplication mainApp)
	{
		getKeyHandler().addKeyCallback(new KeyCallback()
		{
			@Override
			public void invoke(int key, int scancode, int action, int mods)
			{
				if (isEnabled() && (action == 2 || action == 1))
				{
					switch (key)
					{
					case ENTER:
						moveCarretDown();
						carretPositionX = text.get(carretPositionY).length();
						break;
					case BACKSPACE:
						//Delete whole line
						if (getMainApp().isKeyPressed(L_CONTROL))
						{
							text.set(carretPositionY, "");
							carretPositionX = 0;
							break;
						}
						String t = text.get(carretPositionY);
						if (t.equals(""))
						{
							moveCarretUp();
							carretPositionX = text.get(carretPositionY).length();
							break;
						}
						moveCarretLeft();
						
						if (text.get(carretPositionY).length() == carretPositionX + 1)
							text.set(carretPositionY, t.substring(0, t.length() > 0 ? t.length() - 1 : t.length()));
						else
							text.set(carretPositionY, text.get(carretPositionY).substring(0, Math.min(carretPositionX, text.get(carretPositionY).length())) + text.get(carretPositionY).substring(Math.min(carretPositionX + 1, text.get(carretPositionY).length())));
						
						break;
					case UP: 	moveCarretUp(); 	break;
					case DOWN: 	moveCarretDown(); 	break;
					case LEFT: 	moveCarretLeft(); 	break;
					case RIGHT: moveCarretRight(); 	break;
					default: break;
					}
				}
			}
		});
		getKeyHandler().addCharCallback(new CharCallback()
		{
			@Override
			public void invoke(int codePoint)
			{
				char c = Character.toChars(codePoint)[0];
				
				if (text.get(carretPositionY).length() >= xLimit)
					moveCarretDown();
				
				text.set(carretPositionY,
						text.get(carretPositionY).substring(0, Math.min(carretPositionX, text.get(carretPositionY).length()))
								+ Character.toString(c)
								+ text.get(carretPositionY).substring(Math.min(carretPositionX, text.get(carretPositionY).length())));

				moveCarretRight();
				
				if (text.get(text.size() - 1).length() >= xLimit)
				{
					text.set(text.size() - 1, text.get(text.size() - 1).substring(0, xLimit));
					moveCarretLeft();
				}
			}
		});
	}

	@Override
	public void render(Screen screen)
	{
		Screen.drawRect(x, y, getWidth(), getHeight(), 2, 0xff020202);
		Screen.fillRect(x + 2, y + 2, getWidth() - 4, getHeight() - 4, 0xff414041);

		for (int i = 0; i < text.size(); i++)
		{
			if (words != null)
			{
				int width = 0;
				String[] text = separateWords(this.text.get(i));

				for (int j = 0; j < text.length; j++)
				{
					width += renderIndexedColors(text[j], screen, x + 3 + width, y + 3 + i * 9, 1, colors);
				}
			} else
			{
				getFont().render(text.get(i), x + 3, y + 3 + i * 9);
			}

			if (isEnabled())
			{
				if (i == carretPositionY)
				{
					if (showCarret && isFocused)
					{
						String l = text.get(carretPositionY).substring(0, carretPositionX);
						Screen.fillRect(Font.getTextWidth(l, 1) + x + 5, carretPositionY * 9 + y + 10, 8, 2, 0xffa9a8aa);
					}
				}
			}
		}
	}
	
	public int renderIndexedColors(String msg, Screen screen, int x, int y, int scale, SGArray<ColorIndex2> colorIndexes)
	{
		if (msg == null)
			return 0;
		
		int totalSize = 0;
		
		int ignore = 0;
		
		float red = 1, green = 1, blue = 1;

		for (int i = 0; i < msg.length(); i++)
		{
			if (colorIndexes != null)
			{
				main : for (ColorIndex2 o : colorIndexes)
				{
					String st = o.index;
					if (msg.length() > i + st.length())
					{
						String indexedText = msg.substring(i, i + st.length());
						if (st.equals(indexedText))
						{
							ignore = st.length();
							red = o.red;
							green = o.green;
							blue = o.blue;
							break main;
						}
					}
				}
			}
			
			if (ignore == 0)
			{
				Char c = Font.characters.get(msg.charAt(i));
				if (c == null)
					System.err.println("'" + msg.charAt(i) + "' doesn't exist in characters list");
				int w = c.getWidth();
				getFont().render("" + msg.charAt(i), x + totalSize, y, scale, red, green, blue);
				totalSize += w;
			} else
			{
				ignore--;
			}
		}
		
		return totalSize;
	}
	
	private String[] separateWords(String text)
	{
		String[] r = text.split(" ");
		
		for (int i = 0; i < r.length; i++)
		{
			for (ColorIndex ci : words)
			{
				for (String word : ci.words)
				{
					if (r[i].equals(word))
						r[i] = r[i].replace(word, ci.index + word);
				}
			}
			r[i] += " ";
		}
		
		return r;
	}

	@Override
	public void tick()
	{
		if (!isVisible())
			return;
		if (isCursorInComponent() && getMouseHandler().isMouseHolded())
			isFocused = true;
		
		if (!isCursorInComponent() && getMouseHandler().isMouseHolded())
			isFocused = false;

		carretTick += Math.max(60d / Math.max(getMainApp().getFPS(), 1), 1);
		if (carretTick >= 60)
		{
			carretTick = 0;
			showCarret = !showCarret;
		}
	}
	
	public void addText(String msg)
	{
		for (int i = 0; i < text.size(); i++)
		{
			if (text.get(i).isEmpty())
			{
				text.set(i, msg);
				return;
			}
		}
	}

	public void setAsOutput()
	{
		pos = new PipedOutputStream();
		output = new PrintStream(pos);
		try
		{
			pis = new PipedInputStream(pos);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		reader = new BufferedReader(new InputStreamReader(pis));
		System.setOut(output);
		new CustomOutput().start();
	}
	
	class CustomOutput extends Thread
	{
		@Override
		public void run()
		{
			while (true)
			{
				try
				{
					addText(reader.readLine());
					output.flush();
					pos.flush();
				} catch (IOException e)
				{
					e.printStackTrace();
				}

				try
				{
					Thread.sleep(2);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	protected int getMinHeight()
	{
		return 10;
	}
	
	@Override
	protected int getMinWidth()
	{
		return 10;
	}
	
	/*
	 * Operators
	 */
	
	public void clearTextArea()
	{
		int size = text.size();
		
		text.clear();
		
		for (int i = 0; i < size; i++)
			text.add("");
		
		carretPositionX = 0;
		carretPositionY = 0;
	}
	
	public void clearLine(int line)
	{
		text.remove(line);
		text.add("");
	}
	
	public void moveCarretLeft() { carretPositionX = Math.max(0, carretPositionX - 1); carretTick = 0; showCarret = true; };
	
	public void moveCarretRight() { carretPositionX = Math.min(text.get(carretPositionY).length(), carretPositionX + 1); carretTick = 0; showCarret = true; };
	
	public void moveCarretUp() { carretPositionY = Math.max(0, carretPositionY - 1); carretTick = 0; showCarret = true; };
	
	public void moveCarretDown() { carretPositionY = Math.min(text.size() - 1, carretPositionY + 1); carretTick = 0; showCarret = true; };
	
	/*
	 * Setters
	 */
	
	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}
	
	public void addColor(String index, float r, float g, float b)
	{
		colors.add(new ColorIndex2(index, r, g, b));
	}
	
	public void addWords(String index, String... words)
	{
		ColorIndex ci = null;
		for (ColorIndex c : this.words)
		{
			if (c.index.equals(index))
			{
				ci = c;
				break;
			}
		}
		if (ci != null)
		{
			ci.words.add(words);
		} else
		{
			this.words.add(new ColorIndex(index, words));
		}
	}
	
	public void removeColor(String index)
	{
		int i = 0;
		for (; i < colors.getSize(); i++)
		{
			if (colors.get(i).index.equals(index))
				break;
		}
		colors.remove(i);
	}
	
	public void removeWords(String... words)
	{
		for (ColorIndex c : this.words)
		{
			SGArray<Integer> in = new SGArray<Integer>();
			for (int i = 0; i < c.words.getSize(); i++)
			{
				String s = c.words.get(i);
				for (String ss : words)
				{
					if (s.equals(ss))
					{
						in.add(i);
					}
				}
			}
			in.reverseArray();
			for (int i : in)
			{
				c.words.remove(i);
			}
		}
	}
	
	/*
	 * Getters
	 */

	@Override
	public boolean isFocused()
	{
		return isFocused;
	}
	
	public List<String> getText()
	{
		return text;
	}
	
	public boolean isEnabled()
	{
		return isEnabled;
	}
}
