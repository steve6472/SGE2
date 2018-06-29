/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 13. 12. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.gui.components;

import static org.lwjgl.opengl.GL11.*;

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
import com.steve6472.sge.gui.GuiUtils;
import com.steve6472.sge.main.KeyList;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.SGArray;
import com.steve6472.sge.main.Util;
import com.steve6472.sge.main.callbacks.CharCallback;
import com.steve6472.sge.main.callbacks.KeyCallback;

public class TextArea extends Component implements IFocusable, KeyList
{
	private static final long serialVersionUID = 7783560380863267944L;
	boolean isFocused = false;
	private boolean showCarret = false;
	int carretPositionX = 0, carretPositionY = 0;
	int maxXVisible = 0;
	int scrollX = 0;
//	private int fontSize = 1;
	private double carretTick = 0;
	private boolean isEnabled = true;
	List<String> text;
	public int xLimit = Integer.MAX_VALUE;
	
	float br = 1, bg = 1, bb = 1;

	PipedOutputStream pos;
	public PrintStream output;
	PipedInputStream pis;
	BufferedReader reader;

	SGArray<ColorIndex2> colors;
	SGArray<ColorIndex> words;
	
	SGArray<Character> splitters;
	
	static char[] breaks = "()[]{}, '+-*/\\.?_!\":;#&@§".toCharArray();

	public TextArea(int maxY)
	{
		super();

		colors = new SGArray<ColorIndex2>();
		words = new SGArray<ColorIndex>();
		
		splitters = new SGArray<Character>();
		splitters.add(' ');
		
		text = new ArrayList<String>();
		
		for (int i = 0; i <= maxY; i++)
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
				if (!isFocused || !isVisible())
					return;
				
				if (isEnabled() && (action == 2 || action == 1))
				{
					switch (key)
					{
					case ENTER:
						if (carretPositionX == 0)
							text = Util.insert("", text, carretPositionY);
						else if (carretPositionX == text.get(carretPositionY).length())
							text = Util.insert("", text, carretPositionY + 1);
						moveCarretDown();
						break;
					case BACKSPACE:
						//Delete whole line
						if (getMainApp().isKeyPressed(L_CONTROL))
						{
							if (carretPositionX == 0)
							{
								//Merge current line with the upper one & move everything underneeth it up
								text.remove(carretPositionY);
								text.add("");
								moveCarretUp();
								carretPositionX = text.get(carretPositionY).length();
							} else
							{
								String s = text.get(carretPositionY);
								
								int break_ = 0;
								
								main : for (int i = carretPositionX - 2; i >= 0; i--)
								{
									char currentChar = s.charAt(i);
									for (char b : breaks)
									{
										if (currentChar == b)
										{
											break_ = i + 1;
											break main;
										}
									}
								}
								
								text.set(carretPositionY, s.substring(0, break_));
								carretPositionX = text.get(carretPositionY).length();
							}
							break;
						}
						String t = text.get(carretPositionY);
						if (t.equals(""))
						{
							text.remove(carretPositionY);
							
							if (text.isEmpty())
								text.add("");
							
							moveCarretUp();
							
							if (carretPositionY > scrollX && scrollX > 0)
							{
								scrollX--;
							}
							
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
				if (getMainApp().isKeyPressed(L_CONTROL) || getMainApp().isKeyPressed(R_CONTROL))
					return;
				
				if (!isFocused || !isVisible())
					return;
				
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
		Screen.drawRect(x, y, getWidth(), getHeight(), 2, 0xffff00ff);
		
//		if (!text.isEmpty() && text.get(0).equals("C"))
//		{
//			text.clear();
//			carretPositionX = 0;
//			carretPositionY = 0;
//			text.add("");
//		}
		
//		boolean overUDS = GuiUtils.isCursorInRectangle(getMouseHandler(), x + width - 18, y + 2, 16, height - 22);
//		boolean overLRS = GuiUtils.isCursorInRectangle(getMouseHandler(), x + 2, y + height - 18, width - 22, 16);
		
		double usedX = text.size();
		double fulX = height - 22;
		double hiddenX = usedX - maxXVisible;
		int hei = Math.max((int) ((maxXVisible / usedX) * fulX), 20);
		
		//Base frame
		Screen.drawRect(x, y, getWidth() - 18, getHeight() - 18, 2, 0xff020202);
		Screen.fillRect(x + 2, y + 2, getWidth() - 4 - 18, getHeight() - 4 - 18, 0xff414041);
		Screen.fillRect(x + width - 18, y + height - 18, 18, 18, 0xff000000);
		
		//UP DOWN Slider
		Screen.drawRect(x + width - 20, y, 20, height - 18, 2, 0xff020202);
		Screen.fillRect(x + width - 18, y + 2, 16, height - 22, 0xff151415);
		
		if (hiddenX > 0)
			Screen.fillRect(x + width - 18, y + 2 + xSliderRelX, 16, hei, 0xff8d8c8d);
		else
			Screen.fillRect(x + width - 18, y + 2, 16, (int) fulX, 0xff8d8c8d);
		if (hiddenX > 0 && GuiUtils.isCursorInRectangle(getMouseHandler(), x + width - 18, y + 2 + xSliderRelX, 16, hei))
		{
			Screen.fillRect(x + width - 18, y + 2 + xSliderRelX, 16, hei, 0xffb1b0b1);
		}
		//0xff151415
		
		//LEFT RIGHT Slider
		Screen.drawRect(x, y + height - 20, width - 18, 20, 2, 0xff020202);
		Screen.fillRect(x + 2, y + height - 18, width - 22, 16, 0xff8d8c8d);
		
		start();
		
		for (int i = scrollX; i < Math.min(maxXVisible + scrollX, text.size()); i++)
		{
			if (words != null)
			{
				int width = 0;
				String[] text = separateWords(this.text.get(i));

				for (int j = 0; j < text.length; j++)
				{
					width += renderIndexedColors(text[j], screen, x + 3 + width, y + 3 + i * 9 - scrollX * 9, colors);
				}
			} else
			{
				end();
				Font.render(text.get(i), x + 3, y + 3 + i * 9);
				start();
			}
		}

		end();

		if (isEnabled())
		{
			if (showCarret && isFocused)
			{
				String l = text.get(carretPositionY).substring(0, carretPositionX);
				Screen.fillRect(Font.getTextWidth(l, 1) + x + 5, carretPositionY * 9 + y + 10 - scrollX * 9, 8, 2, 0xffa9a8aa);
			}
		}
	}
	
	int xSliderRelX = 0;
	int xSliderOldX = 0;
	boolean holded = false;
	
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

		double usedX = text.size();
		double fulX = height - 22;
		int hei = Math.max((int) ((maxXVisible / usedX) * fulX), 20);
		
		if (GuiUtils.isCursorInRectangle(getMouseHandler(), x + width - 18, y + 2 + xSliderRelX, 16, hei))
		{
			if (getMouseHandler().isMouseHolded() && !holded)
			{
				xSliderOldX = xSliderRelX - getMouseHandler().getMouseY();
				holded = true;
			}
		}
		
		if (getMouseHandler().isMouseHolded() && holded)
			xSliderRelX = getMouseHandler().getMouseY() + xSliderOldX;
		
		
		if (xSliderRelX > (fulX - hei))
		{
			xSliderRelX = (int) (fulX - hei);
		}
		
		if (xSliderRelX < 0)
		{
			xSliderRelX = 0;
		}
		
		if (!getMouseHandler().isMouseHolded())
		{
			holded = false;
			xSliderOldX = x;
		}
		
		double p = (1d - (maxXVisible / usedX));
		
//		System.out.println((p * fulX) / (usedX - maxXVisible));
//		System.out.println(p);
//		System.out.println(xSliderRelX);
		scrollX = (int) ((double) xSliderRelX / ((p * fulX) / (usedX - maxXVisible)));
	}
	
	public int renderIndexedColors(String msg, Screen screen, int x, int y, SGArray<ColorIndex2> colorIndexes)
	{
		if (msg == null)
			return 0;
		
		int totalSize = 0;
		
		int ignore = 0;
		
		float red = br, green = bg, blue = bb;

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
				int w = 8;
				if (c == null)
				{
					System.err.println("'" + msg.charAt(i) + "' doesn't exist in characters list");
				} else
				{
					w = c.getWidth();
				}
				renderChar(c, x + totalSize, y, red, green, blue);
				totalSize += w;
			} else
			{
				ignore--;
			}
		}
		
		return totalSize;
	}
	
	private void start()
	{
		if (text == null || text.isEmpty())
			return;
		
		glMatrixMode(GL_TEXTURE);
		
		glPushMatrix();
		glPushAttrib(GL_CURRENT_BIT);
		glScalef(1f / (float) Font.getFont().getWidth(), 1f / (float) Font.getFont().getHeight(), 1f);
		glEnable(GL_TEXTURE_2D);
		Font.getFont().bind();
		glBegin(GL_QUADS);
	}
	
	private void end()
	{
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glScalef((float) Font.getFont().getWidth(), (float) Font.getFont().getHeight(), 1f);
		glPopAttrib();
		glPopMatrix();
		
		glMatrixMode(GL_MODELVIEW);
	}
	
	private void renderChar(Char ch, int x, int y, float red, float green, float blue)
	{
		int lx = 0;

		if (ch == null)
			return;
		
		glColor3f(0.21f * red, 0.21f * green, 0.21f * blue);
		Font.renderChar(x + lx + 1, y + 1, ch.getIndex(), 1);
		
		glColor3f(red, green, blue);
		Font.renderChar(x + lx, y, ch.getIndex(), 1);
		lx += ch.getWidth();
	}
	
	private String[] separateWords(String text)
	{
		String toSplit = text;
		SGArray<String> splitted = new SGArray<String>();

		String temp = "";
		for (char c : toSplit.toCharArray())
		{
			boolean canAdd = true;
			for (char s : splitters)
			{
				if (c == s)
				{
					splitted.add(temp);
					splitted.add("" + c);
					temp = "";
					canAdd = false;
				}
			}
			if (canAdd)
				temp += c;
		}
		splitted.add(temp);
		temp = "";
		
		for (int i = 0; i < splitted.getSize(); i++)
		{
			String s = splitted.get(i);
			
			if (s == null)
				continue;
			
			for (ColorIndex ci : words)
			{
				for (String word : ci.words)
				{
					if (s.equals(word))
						splitted.set(i, s.replace(word, ci.index + word));
				}
			}
		}
		
		return splitted.toList().toArray(new String[0]);
	}

	
	public void addText(String msg)
	{
//		for (int i = 0; i < text.size(); i++)
//		{
//			if (text.get(i).isEmpty())
//			{
//				text.set(i, msg);
//				return;
//			}
//		}
		text.add(msg);
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

	public void moveCarretLeft()
	{
		if (carretPositionX == 0 && carretPositionY == 0)
			return;
		
		if (carretPositionX == 0)
		{
			moveCarretUp();
			carretPositionX = getText(carretPositionY).length();
		} else
		{
			carretPositionX = Math.max(0, carretPositionX - 1);
			updateCarret();
		}
	};

	public void moveCarretRight()
	{
		if (carretPositionX == getText(carretPositionY).length())
		{
			moveCarretDown();
			carretPositionX = 0;
		} else
		{
			carretPositionX = Math.min(text.get(carretPositionY).length(), carretPositionX + 1);
			updateCarret();
		}
	};

	public void moveCarretUp()
	{
		carretPositionY = Math.max(0, carretPositionY - 1);
		if (carretPositionY < scrollX)
		{
			scrollX--;
		}
		updateCarret();
	};

	public void moveCarretDown()
	{
		carretPositionY = Math.min(text.size() - 1, carretPositionY + 1);
		if (carretPositionY > maxXVisible)
		{
			scrollX++;
		}
		if (scrollX > text.size() - maxXVisible)
		{
			scrollX = text.size() - maxXVisible;
		}
		if (scrollX < 0)
		{
			scrollX = 0;
		}
		updateCarret();
	};

	private void updateCarret()
	{
		carretTick = 0;
		showCarret = true;
		int tl = text.get(carretPositionY).length();
		if (carretPositionX > tl)
		{
			carretPositionX = tl;
		}
	}
	
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
	
	public void addSplitters(char... ch)
	{
		for (char c : ch)
			splitters.add(c);
	}
	
	public void removeSplitter(char c)
	{
		int index = 0;
		for (; index < splitters.getSize(); index++)
		{
			if (splitters.get(index) == c)
				break;
		}
		splitters.remove(index);
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
	
	public void setText(String text, int line)
	{
		while (this.text.size() <= line)
		{
			this.text.add("");
		}
		this.text.set(line, text);
	}
	
	public void setBaseColor(float r, float g, float b)
	{
		this.br = r;
		this.bg = g;
		this.bb = b;
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		maxXVisible = (int) ((height - 24) / 9d);
	}
	
	public void setFocused(boolean isFocused)
	{
		this.isFocused = isFocused;
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
	
	public int getCarretPositionX()
	{
		return carretPositionX;
	}
	
	public int getCarretPositionY()
	{
		return carretPositionY;
	}
	
	public String getText(int line)
	{
		return text.get(line);
	}
}
