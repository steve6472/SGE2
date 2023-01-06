package steve6472.sge.gui.planner;

import steve6472.sge.gfx.font.CustomChar;
import steve6472.sge.gui.Component;
import steve6472.sge.gui.components.Button;
import steve6472.sge.gui.components.TextField;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.util.MathUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**********************
 * Created by steve6472
 * On date: 12.10.2019
 * Project: SJP
 *
 ***********************/
public class NumberInput extends Component
{
	private TextField field;
	private Button button;
	private int min = Integer.MIN_VALUE;
	private int max = Integer.MAX_VALUE;
	private Check check;

	/**
	 * @param check min, max, number
	 *              Runs only if there is valid number in the TextField
	 */
	public NumberInput(Check check)
	{
		this.check = check;
	}

	@Override
	public void init(MainApp main)
	{
		field = new TextField();
		field.setRelativeLocation(0, 0);
		field.setSize(60, 20);
		addComponent(field);

		button = new Button(CustomChar.CHECK);
		button.setRelativeLocation(65, 0);
		button.setSize(20, 20);
		button.addIfClickEvent(b -> checkNumber(), b -> runSetEvents());
		addComponent(button);
	}

	@Override
	public void tick()
	{
		colorButton();
		tickComponents();
	}

	@Override
	public void render()
	{
		renderComponents();
	}

	public void setCheckValues(int min, int max)
	{
		this.min = min;
		this.max = max;
	}

	public void setNumber(int number)
	{
		field.setText("" + number);
		field.setCarretPosition(field.getText().length());
	}

	private boolean checkNumber()
	{
		if (!MathUtil.isInteger(field.getText())) return false;
		return check.check(min, max, Integer.parseInt(field.getText()));
	}

	private void colorButton()
	{
		if (checkNumber())
		{
			button.getScheme().setFontColor(0.2f, 0.9f, 0.2f);
		} else
		{
			button.getScheme().setFontColor(0.9f, 0.2f, 0.2f);
		}
	}

	@FunctionalInterface
	public interface Check
	{
		boolean check(int min, int max, int i);
	}

	/* Events */
	private List<Consumer<Integer>> setEvent = new ArrayList<>();

	public void addSetEvent(Consumer<Integer> c)
	{
		setEvent.add(c);
	}

	private void runSetEvents()
	{
		setEvent.forEach(c -> c.accept(Integer.parseInt(field.getText())));
	}
}
