package steve6472.sge.gfx.font;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/3/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class ColoredText
{
	String text;
	ICustomChar customChar;
	float colorRed, colorGreen, colorBlue;
	float alpha;
	float offsetX, offsetY;
	boolean shade;

	public ColoredText()
	{
		colorRed = 1.0f;
		colorGreen = 1.0f;
		colorBlue = 1.0f;
		alpha = 1.0f;
		shade = true;
	}

	public ColoredText setText(String text)
	{
		this.text = text;
		this.customChar = null;
		return this;
	}

	public ColoredText setColor(float r, float g, float b)
	{
		this.colorRed = r;
		this.colorGreen = g;
		this.colorBlue = b;
		return this;
	}

	public ColoredText setColor(float r, float g, float b, float a)
	{
		this.colorRed = r;
		this.colorGreen = g;
		this.colorBlue = b;
		this.alpha = a;
		return this;
	}

	public ColoredText setAlpha(float alpha)
	{
		this.alpha = alpha;
		return this;
	}

	public ColoredText setOffset(float offsetX, float offsetY)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		return this;
	}

	public ColoredText setCustomChar(ICustomChar customChar)
	{
		this.customChar = customChar;
		this.text = null;
		return this;
	}

	public ColoredText setShade(boolean shade)
	{
		this.shade = shade;
		return this;
	}

	public ColoredText copyColor()
	{
		return new ColoredText().setColor(colorRed, colorGreen, colorBlue);
	}

	public ColoredText setColor(ColoredText color)
	{
		if (color != null)
			setColor(color.colorRed, color.colorGreen, color.colorBlue);
		return this;
	}

	public boolean isString()
	{
		return text != null && customChar == null;
	}

	@Override
	public String toString()
	{
		return "ColoredText{" + "text='" + text + '\'' + ", customChar=" + customChar + ", colorRed=" + colorRed + ", colorGreen=" + colorGreen + ", colorBlue=" + colorBlue + ", alpha=" + alpha + ", offsetX=" + offsetX + ", offsetY=" + offsetY + ", shade=" + shade + '}';
	}
}
