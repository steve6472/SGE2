package com.steve6472.sge.gui.components.dialog;

import com.steve6472.sge.gui.components.TextField;
import com.steve6472.sge.main.MainApp;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 08.05.2019
 * Project: SJP
 *
 ***********************/
public class OkTextInputDialog extends OkDialog
{
	private TextField tf;
	String baseText;

	public OkTextInputDialog(String baseText, String title)
	{
		super("Input:", title);
		this.baseText = baseText;
	}

	@Override
	public void init(MainApp main)
	{
		setSize(260, height + 30);
		super.init(main);

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
