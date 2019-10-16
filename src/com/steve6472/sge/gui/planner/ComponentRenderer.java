package com.steve6472.sge.gui.planner;

import com.steve6472.sge.gui.components.*;
import com.steve6472.sge.main.MainApp;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 08.06.2019
 * Project: SGE2 - Planner
 *
 ***********************/
class ComponentRenderer
{
	Button button;
	CheckBox checkBox;
//	ItemList itemList;
	ProgressBar progressBar;
	ScrollBar scrollBar;
	Slider slider;
	SliderVertical sliderVertical;
	TextField textField;
	ToggleButton toggleButton;

	public ComponentRenderer(MainApp main)
	{
		button = new Button();
		checkBox = new CheckBox();
		progressBar = new ProgressBar();
		scrollBar = new ScrollBar();
		slider = new Slider();
		sliderVertical = new SliderVertical();
		textField = new TextField();
		toggleButton = new ToggleButton();

		button.preInit(main);
		checkBox.preInit(main);
		progressBar.preInit(main);
		scrollBar.preInit(main);
		slider.preInit(main);
		sliderVertical.preInit(main);
		textField.preInit(main);
		toggleButton.preInit(main);

		button.init(main);
		checkBox.init(main);
		progressBar.init(main);
		scrollBar.init(main);
		slider.init(main);
		sliderVertical.init(main);
		textField.init(main);
		toggleButton.init(main);
	}

	void render(Planner.Box box)
	{
		if (box.component == button)
		{
			button.setLocation(box.x, box.y);
			button.setSize(box.w, box.h);
			button.setText(box.name);
			button.tick();
			button.render();
		} else if (box.component == checkBox)
		{
			checkBox.setLocation(box.x, box.y);
			checkBox.setSize(box.w, box.h);
			checkBox.tick();
			checkBox.render();
		} else if (box.component == progressBar)
		{
			progressBar.setLocation(box.x, box.y);
			progressBar.setSize(box.w, box.h);
			progressBar.tick();
			progressBar.render();
		} else if (box.component == scrollBar)
		{
			scrollBar.setLocation(box.x, box.y);
			scrollBar.setSize(box.w, box.h);
			scrollBar.tick();
			scrollBar.render();
		} else if (box.component == slider)
		{
			slider.setLocation(box.x, box.y);
			slider.setSize(box.w, box.h);
			slider.tick();
			slider.render();
		} else if (box.component == sliderVertical)
		{
			sliderVertical.setLocation(box.x, box.y);
			sliderVertical.setSize(box.w, box.h);
			sliderVertical.tick();
			sliderVertical.render();
		} else if (box.component == textField)
		{
			textField.setLocation(box.x, box.y);
			textField.setSize(box.w, box.h);
			textField.setText(box.name);
			textField.tick();
			textField.render();
		} else if (box.component == toggleButton)
		{
			toggleButton.setLocation(box.x, box.y);
			toggleButton.setSize(box.w, box.h);
			toggleButton.setText(box.name);
			toggleButton.tick();
			toggleButton.render();
		}
	}
}
