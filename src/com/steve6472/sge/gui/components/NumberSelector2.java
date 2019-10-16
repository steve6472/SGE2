package com.steve6472.sge.gui.components;

@Deprecated
public class NumberSelector2// extends Component
{/*
	private static final long serialVersionUID = 6097537167658644896L;
	private double leftValue, rightValue, rightMax = 16, leftMax = 16, rightMin = -16, leftMin = -16;
	private List<ChangeEvent> changeEvents = new ArrayList<>();
	private boolean enabled = true;
	public int decimal = 4;
	
	private boolean addRightHovered, removeRightHovered, round, addLeftHovered, removeLeftHovered;
	private Vector2f addCenter = new Vector2f(), removeCenter = new Vector2f();
	
	@Override
	public void init(MainApp game)
	{
	}

	@Event
	public void mouseEvent(MouseEvent event)
	{
		if (enabled && event.getAction() == PRESS)
		{
			double modifyValue = 1;
			if (event.getButton() == 0)
			{
				if (isKeyPressed(L_SHIFT)) 		modifyValue = 10;
				if (isKeyPressed(L_CONTROL)) 	modifyValue = 100;
				if (isKeyPressed(L_ALT)) 		modifyValue = 1000;
			}
			if (event.getButton() == 1)
			{
				modifyValue = 0.1;
				if (isKeyPressed(L_SHIFT)) 		modifyValue = 0.01;
				if (isKeyPressed(L_CONTROL)) 	modifyValue = 0.001;
				if (isKeyPressed(L_ALT)) 		modifyValue = 0.0001;
			}

			if (addLeftHovered)
				addLeftValue(modifyValue);
			if (removeLeftHovered)
				removeLeftValue(modifyValue);
			if (addRightHovered)
				addRightValue(modifyValue);
			if (removeRightHovered)
				removeRightValue(modifyValue);

			if (addLeftHovered || removeLeftHovered || addRightHovered || removeRightHovered)
			{
				changeEvents.forEach(ChangeEvent::change);
			}
		}
	}
	
	@Override
	public void tick()
	{
		addRightHovered = isCursorInComponent(x + (int) ((double) width / 4d * 3d), y, (int) ((double) width / 4d), height / 2 - 1);
		removeRightHovered = isCursorInComponent(x + (int) ((double) width / 4d * 3d), y + height / 2, (int) ((double) width / 4d), height / 2 - 1);

		addLeftHovered = isCursorInComponent(x, y, (int) ((double) width / 4d), height / 2 - 1);
		removeLeftHovered = isCursorInComponent(x, y + height / 2, (int) ((double) width / 4d), height / 2 - 1);
	}

	@Override
	public void render()
	{
		UIHelper.renderSingleBorder(x + width / 4, y, width / 2, height, 0xff7f7f7f, 0xff000000);

		UIHelper.renderButton(x, y, width / 4, height / 2 + 1, enabled, addLeftHovered);
		UIHelper.renderButton(x, y + height / 2 - 1, width / 4, height / 2 + 1, enabled, removeLeftHovered);
		Font.render("\u0000", (int) removeCenter.x(), (int) removeCenter.y() - 9);
		Font.render("\u0001", (int) removeCenter.x(), (int) removeCenter.y() + 9);

		UIHelper.renderButton(x + (int) ((double) width / 4d * 3d), y, width / 4, height / 2 + 1, enabled, addRightHovered);
		UIHelper.renderButton(x + (int) ((double) width / 4d * 3d), y + height / 2 - 1, width / 4, height / 2 + 1, enabled, removeRightHovered);
		Font.render("\u0000", (int) addCenter.x(), (int) addCenter.y() - 9);
		Font.render("\u0001", (int) addCenter.x(), (int) addCenter.y() + 9);
		
		Render.fillRect(x + width / 4, y + 19, width / 2, 2, 0xff7f7f7f);

		String df = "%." + decimal + "f";
		
		Font.render(String.format(Locale.US, df, leftValue), getX() + getWidth() / 2 - String.format(Locale.US, df, leftValue).length() * 4, getY() + getHeight () / 2 - 4 - 8);
		Font.render(String.format(Locale.US, df, rightValue), getX() + getWidth() / 2 - String.format(Locale.US, df, rightValue).length() * 4, getY() + getHeight () / 2 - 4 + 9);
	}

	/*
	 * Operators
	 *//*

	public void addLeftValue(double i)
	{
		leftValue += i;
		if (leftValue > rightMax)
			if (round)
				leftValue = leftMin;
			else
				leftValue = leftMax;
	}

	public void removeLeftValue(double i)
	{
		leftValue -= i;
		if (leftValue < leftMin)
			if (round)
				leftValue = leftMax;
			else
				leftValue = leftMin;
	}

	public void addRightValue(double i)
	{
		rightValue += i;
		if (rightValue > rightMax)
			if (round)
				rightValue = rightMin;
			else
				rightValue = rightMax;
	}

	public void removeRightValue(double i)
	{
		rightValue -= i;
		if (rightValue < rightMin)
			if (round)
				rightValue = rightMax;
			else
				rightValue = rightMin;
	}
	
	public void addChangeEvent(ChangeEvent ce)
	{
		changeEvents.add(ce);
	}

	public void addLeftValue()
	{
		addLeftValue(1);
	}

	public void removeLeftValue()
	{
		removeLeftValue(1);
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		Font.stringCenter(x, y, width / 4, height, " ", 1, removeCenter);
		Font.stringCenter(x + (width / 4 * 3), y, width / 4, height, " ", 1, addCenter);
	}
	
	/*
	 * Setters
	 *//*

	public void setLeftValue(double value)
	{
		this.leftValue = value;
	}

	public void setRightValue(double value)
	{
		this.rightValue = value;
	}
	
	public void setLeftMax(double leftMax)
	{
		this.leftMax = leftMax;
	}
	
	public void setRightMax(double rightMax)
	{
		this.rightMax = rightMax;
	}
	
	public void setLeftMin(double leftMin)
	{
		this.leftMin = leftMin;
	}
	
	public void setRightMin(double rightMin)
	{
		this.rightMin = rightMin;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public void setRound(boolean round)
	{
		this.round = round;
	}
	
	/*
	 * Getters
	 *//*
	
	public double getLeftMax()
	{
		return leftMax;
	}
	
	public double getLeftMin()
	{
		return leftMin;
	}
	
	public double getLeftValue()
	{
		return leftValue;
	}
	
	public double getRightMax()
	{
		return rightMax;
	}
	
	public double getRightMin()
	{
		return rightMin;
	}
	
	public double getRightValue()
	{
		return rightValue;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public boolean isRound()
	{
		return round;
	}*/
}
