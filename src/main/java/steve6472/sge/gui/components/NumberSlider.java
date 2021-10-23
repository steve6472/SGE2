package steve6472.sge.gui.components;

import org.joml.Vector2f;
import org.joml.Vector4f;
import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gui.Component;
import steve6472.sge.main.KeyList;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.Procedure;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 28.08.2020
 * Project: CaveGame
 *
 ***********************/
public class NumberSlider extends Component
{
	private static final Vector2f OUT = new Vector2f();
	private final List<Procedure> changeEvents;

	private final Vector4f cornerColor;

	public NumberSlider()
	{
		cornerColor = new Vector4f();
		changeEvents = new ArrayList<>();
	}

	@Override
	public void init(MainApp mainApp)
	{

	}

	public void setCornerColor(float r, float g, float b)
	{
		cornerColor.set(r, g, b, 1);
	}

	int value, oldValue, olderValue, clickChange;
	double oldMul = 1;
	boolean mouseFlag, ignore, buttonFlag;
	int startX;

	public int getRealValue()
	{
		return value;
	}

	public float getValue()
	{
		return value / 100f;
	}

	public void setValue(float v)
	{
		this.value = (int) Math.floor(v * 100f);
	}

	public void addChangeEvent(Procedure e)
	{
		changeEvents.add(e);
	}

	@Override
	public void tick()
	{
		double mul = 1;
		if (isKeyPressed(KeyList.L_CONTROL) || isKeyPressed(KeyList.R_CONTROL))
			mul = 0.1d;
		else if (isKeyPressed(KeyList.L_SHIFT) || isKeyPressed(KeyList.R_SHIFT))
			mul = 0.25d;

		if (mul != oldMul)
		{
			oldMul = mul;
			oldValue = value;
			olderValue = value;
			startX = getMouseX() - getMouseX() % 10;
		}

		if (isLMBHolded() && !isCursorInComponent() && !ignore && !mouseFlag)
			ignore = true;
		if (!isLMBHolded() && ignore && !mouseFlag)
			ignore = false;

		if (isLMBHolded() && isCursorInComponent() && !ignore)
		{
			if (isCursorInComponent(getX() + 4, (int) OUT.y - 2, 10, 12) && !mouseFlag && !buttonFlag)
			{
				clickChange = -100;
				ignore = true;
				mouseFlag = true;
				buttonFlag = true;
			}
			if (isCursorInComponent(getX() + getWidth() - 14, (int) OUT.y - 2, 10, 12) && !mouseFlag && !buttonFlag)
			{
				clickChange = 100;
				ignore = true;
				mouseFlag = true;
				buttonFlag = true;
			}
		}

		if (buttonFlag && !isCursorInComponent() && !isLMBHolded())
		{
			buttonFlag = false;
		}

		if (!isLMBHolded() && buttonFlag)
		{
			if (clickChange < 0 && isCursorInComponent(getX() + 4, (int) OUT.y - 2, 10, 12))
			{
				ignore = false;
				mouseFlag = false;
				buttonFlag = false;
				value += clickChange * mul;
				changeEvents.forEach(Procedure::process);
				return;
			}

			if (clickChange > 0 && isCursorInComponent(getX() + getWidth() - 14, (int) OUT.y - 2, 10, 12))
			{
				ignore = false;
				mouseFlag = false;
				buttonFlag = false;
				value += clickChange * mul;
				changeEvents.forEach(Procedure::process);
				return;
			}
		}

		if (isLMBHolded() && !mouseFlag && isCursorInComponent() && !ignore)
		{
			mouseFlag = true;
			startX = getMouseX();
			oldValue = value;
		}
		if (!isLMBHolded() && mouseFlag)
			mouseFlag = false;

		if (isActivated())
		{
			int currX = getMouseX();
			int diff = startX - currX;
			diff /= 10;
			diff *= 100 * mul;
			value = oldValue - diff;
			if (value != olderValue)
			{
				olderValue = value;
				changeEvents.forEach(Procedure::process);
			}
		}
	}

	public boolean isActivated()
	{
		return mouseFlag && !ignore;
	}

	@Override
	public void render()
	{
		SpriteRender.renderSingleBorderComponent(this, 0.0F, 0.0F, 0.0F, 1.0F, 0.5019608F, 0.5019608F, isActivated() ? 0 : 0.5019608F, 1.0F);

		Font.stringCenter(getX(), getY(), getWidth(), getHeight(), "" + (value / 100d), 1, OUT);

		Font.render((int) OUT.x - 2, (int) OUT.y, "" + (value / 100d));

		if (isCursorInComponent())
		{
			Font.render(getX() + 6, (int) OUT.y, "<");
			Font.render(getX() + getWidth() - 15, (int) OUT.y, ">");
		}

		for (int i = 0; i < 8; i++)
		{
			SpriteRender.fillRect(getX() + getWidth() - i - 2, getY() + 9 - i, i, 1, cornerColor);
		}
	}
}












