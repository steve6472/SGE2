package steve6472.sge.gui.components.dialog;

import steve6472.sge.gui.components.Button;
import steve6472.sge.gui.components.TextField;
import steve6472.sge.main.MainApp;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 08.05.2019
 * Project: SJP
 *
 ***********************/
public class TextInputDialog extends YesNoDialog
{
	private TextField tf;
	String baseText;

	public TextInputDialog(String baseText, String title)
	{
		super("Input:", title);
		this.baseText = baseText;
	}

	@Override
	public void init(MainApp main)
	{
		setSize(260, height + 30);
		super.init(main);
		((Button)getComponent(0)).setText("Ok");
		((Button)getComponent(1)).setText("Cancel");

		tf = new TextField();
		tf.setText(baseText);
		tf.setRelativeLocation(10, 30);
		tf.setSize(10, 10);
		tf.setCarretPosition(tf.getText().length());
		addComponent(tf);
	}

	@Override
	public void tick()
	{
		super.tick();
		tf.setRelativeLocation(18, 58);
		tf.setSize(width - 36, 26);
	}

	public String getText()
	{
		return tf.getText();
	}
}
