/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 4. 3. 2018
* Project: SGE2
*
***********************/

package steve6472.sge.gui.components;

import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gui.Component;
import steve6472.sge.gui.components.schemes.IScheme;
import steve6472.sge.gui.components.schemes.SchemeTextField;
import steve6472.sge.main.KeyList;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.Util;
import steve6472.sge.main.events.CharEvent;
import steve6472.sge.main.events.Event;
import steve6472.sge.main.events.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class TextField extends Component implements IScheme<SchemeTextField>
{
	private String text = "";
	protected int fontSize = 1;
	private int carretPosition = 0;
	protected double carretTick = 0;
	protected boolean showCarret = false;
	protected boolean isFocused = false;
	private boolean isEditable = true;
	private int selectStart = -1;
	private int selectEnd = -1;
	
	public SchemeTextField scheme;
	
	public TextField()
	{
		setScheme(MainApp.getSchemeRegistry().copyDefaultScheme(SchemeTextField.class));
	}

	@Override
	public void init(MainApp main)
	{

	}

	@Event
	public void keyEvent(KeyEvent event)
	{
		if (!isFocused || !isEditable)
			return;

		if (event.getKey() == KeyList.BACKSPACE && (event.getAction() == KeyList.PRESS || event.getAction() == KeyList.REPEAT) && text.length() >= 1)
		{
			if (selectStart == -1 && selectEnd == -1)
			{
				if (carretPosition != 0)
				{
					text = text.substring(0, carretPosition - 1) + text.substring(carretPosition);
					moveCarretLeft();
				}
			} else
			{
				text = text.substring(0, Math.min(selectStart, selectEnd)) + text.substring(Math.max(selectStart, selectEnd));
				carretPosition = Math.min(selectStart, selectEnd) + 1;
				moveCarretLeft();
				resetSelection();
			}
		}

		if (event.getAction() == KeyList.PRESS || event.getAction() == KeyList.REPEAT)
		{
			if (getMain().isKeyPressed(KeyList.TAB))
			{
				typeChar('	');
			}
			else if (!getMain().isKeyPressed(KeyList.L_SHIFT))
			{
				switch (event.getKey())
				{
					case KeyList.LEFT ->  { moveCarretLeft();               resetSelection(); }
					case KeyList.RIGHT -> { moveCarretRight();              resetSelection(); }
					case KeyList.END ->   { carretPosition = text.length(); resetSelection(); }
					case KeyList.HOME ->  { carretPosition = 0;             resetSelection(); }
				}
			}
			else
			{
				if (event.getKey() == KeyList.LEFT)
				{
					if (selectStart == -1)
					{
						selectStart = carretPosition;
						moveCarretLeft();
						selectEnd = carretPosition;
						checkSelectPosition();
					} else
					{
						moveCarretLeft();
						selectEnd = carretPosition;
						checkSelectPosition();
					}
				}
				if (event.getKey() == KeyList.RIGHT)
				{
					if (selectStart == -1)
					{
						selectStart = carretPosition;
						moveCarretRight();
						selectEnd = carretPosition;
						checkSelectPosition();
					} else
					{
						moveCarretRight();
						selectEnd = carretPosition;
						checkSelectPosition();
					}
				}
			}
		}

		runKeyEvents(event.getKey());
	}

	private void checkSelectPosition()
	{
		if (selectStart == selectEnd)
		{
			resetSelection();
		}
	}

	private void resetSelection()
	{
		selectStart = -1;
		selectEnd = -1;
	}

	@Event
	public void typeChar(CharEvent event)
	{
		if (!isFocused || !isEditable)
			return;

		final char c = Character.toChars(event.getCodepoint())[0];

		if (!Font.isValidChar(c))
			return;

		typeChar(c);

		runTypeEvents(c);
	}

	public void typeChar(char c)
	{
		if (selectStart == -1 && selectEnd == -1)
		{
			text = text.substring(0, carretPosition) + c + text.substring(carretPosition);
			moveCarretRight();
		} else
		{
			text = text.substring(0, Math.min(selectStart, selectEnd)) + c + text.substring(Math.max(selectStart, selectEnd));
			carretPosition = Math.min(selectStart, selectEnd) + 1;
			resetSelection();
		}
	}

	protected void renderBackground()
	{
		SpriteRender.renderSingleBorder(
			x, y, width, height,
			scheme.border,
			scheme.fill
		);
	}

	protected void renderText()
	{
		int fontHeight = ((8 * fontSize)) / 2;
		int ty = y + height / 2 - fontHeight;
		int tx = x + (height - 8 * fontSize) / 2;

		if (selectStart != -1 && selectEnd != -1)
		{
			String left = text.substring(0, Math.min(selectStart, selectEnd));
			String middle = text.substring(Math.min(selectStart, selectEnd), Math.max(selectStart, selectEnd));
			String right = text.substring(Math.max(selectStart, selectEnd));

			int textSize = Font.getTextWidth(middle, fontSize);
			int textStart = Font.getTextWidth(left, fontSize);
			SpriteRender.fillRect(
				x + (height - 8 * fontSize) / 2 + textStart, y + 2, textSize, height - 4,
				scheme.selectFill
			);

			Font.render(left, tx, ty, fontSize, scheme.textColor);
			Font.render(middle, tx + textStart, ty, fontSize, scheme.selectedTextColor);
			Font.render(right, tx + textStart + textSize, ty, fontSize, scheme.textColor);
		} else
		{
			Font.render(text, tx, y + height / 2 - fontHeight, fontSize, scheme.textColor);
		}

		if (showCarret && isFocused && isEditable)
		{
			int textWidth = Font.getTextWidth(text.substring(0, carretPosition), fontSize);
			int cx = textWidth + x + fontSize * 3 + 5;
			int cy = y + height / 2 + fontHeight;
			int carretWidth = 8 * fontSize;
			if (text.length() > 0 && carretPosition != text.length())
			{
				cx = textWidth + x + fontSize * 3 + 2;
			}
			if (carretPosition == text.length())
			{
				SpriteRender.fillRect(
					cx, cy, carretWidth, 2 * fontSize,
					scheme.carretColor
				);
			} else
			{
				SpriteRender.fillRect(
					cx, ty, fontSize, 8 * fontSize,
					scheme.carretColor
				);
			}
		}
	}

	@Override
	public void render()
	{
//		UIHelper.renderSingleBorder(getX(), getY(), getWidth(), getHeight(), scheme.border, scheme.fill);
		renderBackground();
		renderText();
	}

	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
	}

	@Override
	public void tick()
	{
		carretTick += 1;
		if (carretTick >= 60)
		{
			carretTick = 0;
			showCarret = !showCarret;
		}
		
		if (isCursorInComponent() && isLMBHolded())
			isFocused = true;
		
		if (!isCursorInComponent() && isLMBHolded())
			isFocused = false;
	}
	
	public int getFontSize()
	{
		return fontSize;
	}
	
	public void setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
	}
	
	public String getText()
	{
		return text;
	}

	public int getCarretPosition()
	{
		return carretPosition;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}

	public boolean isFocused()
	{
		return isFocused;
	}

	public void focus()
	{
		isFocused = true;
	}

	public void loseFocus()
	{
		isFocused = false;
	}

	private void moveCarretLeft() { carretPosition = Math.max(0, carretPosition - 1); carretTick = 0; showCarret = true; }
	
	private void moveCarretRight() { carretPosition = Math.min(text.length(), carretPosition + 1); carretTick = 0; showCarret = true; }

	public void setEditable(boolean editable)
	{
		this.isEditable = editable;
	}

	public boolean isEditable()
	{
		return isEditable;
	}

	public void setCarretPosition(int position)
	{
		if (position < 0)
			position = text.length() + position + 1;
		this.carretPosition = Util.isNumberInRange(0, text.length(), position) ? position : carretPosition;
	}

	/**
	 * Positions Carret at the end of text
	 */
	public void endCarret()
	{
		setCarretPosition(text.length());
	}

	@Override
	public SchemeTextField getScheme()
	{
		return scheme;
	}

	@Override
	public void setScheme(SchemeTextField scheme)
	{
		this.scheme = scheme;
	}

	/* Events */

	private List<BiConsumer<TextField, Character>> typeEvents = new ArrayList<>();
	private List<BiConsumer<TextField, Integer>> keyEvents = new ArrayList<>();

	public void addTypeEvent(BiConsumer<TextField, Character> c)
	{
		typeEvents.add(c);
	}

	private void runTypeEvents(char ch)
	{
		typeEvents.forEach(c -> c.accept(this, ch));
	}

	public void addKeyEvent(BiConsumer<TextField, Integer> c)
	{
		keyEvents.add(c);
	}

	private void runKeyEvents(int key)
	{
		keyEvents.forEach(c -> c.accept(this, key));
	}
}
