package steve6472.sge.main.node.nodes.gui;

import steve6472.sge.gfx.font.Font;
import steve6472.sge.gui.components.Button;
import steve6472.sge.gui.components.TextField;
import steve6472.sge.gui.components.dialog.AdvancedMoveableDialog;
import steve6472.sge.main.MainApp;

import java.util.function.Consumer;
import java.util.function.Function;

/**********************
 * Created by steve6472
 * On date: 4/23/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class ConstantDialog<T> extends AdvancedMoveableDialog
{
	private String oldText;
	private TextField number;
	private Button setButton;
	private final Function<String, T> parse;
	private final Consumer<T> set;

	private final String example;

	private T constant;

	public ConstantDialog(String example, Function<String, T> parse, Consumer<T> set)
	{
		this.example = example;
		this.parse = parse;
		this.set = set;
	}

	@Override
	public void init(MainApp mainApp)
	{
		setSize(234, 92);

		number = new TextField();
		number.setRelativeLocation(7, 40);
		number.setSize(220, 20);
		number.setText("");
		number.endCarret();
		addComponent(number);

		setButton = new Button("Set");
		setButton.setRelativeLocation(7, 65);
		setButton.setSize(220, 20);
		setButton.addClickEvent((c) -> {
			set.accept(constant);
			close();
		});
		addComponent(setButton);

		initCloseButton();
	}

	@Override
	public void tick()
	{
		if (!number.getText().equals(oldText))
		{
			oldText = number.getText();
			constant = parse.apply(number.getText());
			ConstantGuiNode.colorButton(constant != null, setButton);
		}
	}

	@Override
	public void render()
	{
		renderTitle("Set Constant");
		Font.render(getX() + 7, getY() + 27, "e.g. " + example);
	}

	@Override
	public boolean freezeGui()
	{
		return true;
	}

	@Override
	public boolean disableEvents()
	{
		return true;
	}
}
