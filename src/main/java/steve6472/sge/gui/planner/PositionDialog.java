package steve6472.sge.gui.planner;

import steve6472.sge.gfx.font.CustomChar;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gui.components.Button;
import steve6472.sge.gui.components.TextField;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.util.MathUtil;

/**********************
 * Created by steve6472
 * On date: 04.06.2019
 * Project: SJP
 *
 ***********************/
public class PositionDialog extends PlannerDialog
{
	private TextField fieldX, fieldY, fieldW, fieldH;
	private Button buttX, buttY, buttW, buttH;

	private Planner.Box box;

	public PositionDialog(Planner.Box box, Planner planner, BoxOptionsDialog bod)
	{
		super(planner, c -> bod.setAllEnabled(true));
		this.box = box;
	}

	@Override
	public void Init(MainApp main)
	{
		fieldX = new TextField();
		fieldX.setText("" + box.x);
		fieldX.setCarretPosition(fieldX.getText().length());
		fieldX.setRelativeLocation(19, 24);
		fieldX.setSize(60, 20);
		addComponent(fieldX);

		buttX = new Button(CustomChar.CHECK);
		buttX.setRelativeLocation(84, 24);
		buttX.setSize(20, 20);
		buttX.addIfClickEvent(b -> checkNumber0(fieldX), b -> box.x = Integer.parseInt(fieldX.getText()));
		addComponent(buttX);


		fieldY = new TextField();
		fieldY.setText("" + box.y);
		fieldY.setCarretPosition(fieldY.getText().length());
		fieldY.setRelativeLocation(19, 54);
		fieldY.setSize(60, 20);
		addComponent(fieldY);

		buttY = new Button(CustomChar.CHECK);
		buttY.setRelativeLocation(84, 54);
		buttY.setSize(20, 20);
		buttY.addIfClickEvent(b -> checkNumber0(fieldY), b -> box.y = Integer.parseInt(fieldY.getText()));
		addComponent(buttY);




		fieldW = new TextField();
		fieldW.setText("" + box.w);
		fieldW.setCarretPosition(fieldW.getText().length());
		fieldW.setRelativeLocation(169, 24);
		fieldW.setSize(60, 20);
		addComponent(fieldW);

		buttW = new Button(CustomChar.CHECK);
		buttW.setRelativeLocation(234, 24);
		buttW.setSize(20, 20);
		buttW.addIfClickEvent(b -> checkNumber1(fieldW), b -> box.w = Integer.parseInt(fieldW.getText()));
		addComponent(buttW);


		fieldH = new TextField();
		fieldH.setText("" + box.h);
		fieldH.setCarretPosition(fieldH.getText().length());
		fieldH.setRelativeLocation(169, 54);
		fieldH.setSize(60, 20);
		addComponent(fieldH);

		buttH = new Button("" + CustomChar.CHECK);
		buttH.setRelativeLocation(234, 54);
		buttH.setSize(20, 20);
		buttH.addIfClickEvent(b -> checkNumber1(fieldH), b -> box.h = Integer.parseInt(fieldH.getText()));
		addComponent(buttH);
	}

	@Override
	public void tick()
	{
		colorButton(checkNumber0(fieldX), buttX);
		colorButton(checkNumber0(fieldY), buttY);
		colorButton(checkNumber1(fieldW), buttW);
		colorButton(checkNumber1(fieldH), buttH);
	}

	private boolean checkNumber0(TextField tf)
	{
		if (!MathUtil.isInteger(tf.getText())) return false;

		int i = Integer.parseInt(tf.getText());
		return i >= 0;
	}

	private boolean checkNumber1(TextField tf)
	{
		if (!MathUtil.isInteger(tf.getText())) return false;

		int i = Integer.parseInt(tf.getText());
		return i > 0;
	}

	private void colorButton(boolean flag, Button button)
	{
		if (flag)
		{
			button.getScheme().setFontColor(0.2f, 0.9f, 0.2f);
		} else
		{
			button.getScheme().setFontColor(0.9f, 0.2f, 0.2f);
		}
	}

	@Override
	public void render()
	{
		renderTitle("Dimensions");
		Font.render("X:", x + 4, y + 29);
		Font.render("Y:", x + 4, y + 59);
		Font.render("Width:", x + 115, y + 29);
		Font.render("Height:", x + 115, y + 59);
	}

	@Override
	protected int[] getSize()
	{
		return new int[] {260, 80};
	}
}
