package com.steve6472.sge.gui.components.dialog;

import com.steve6472.sge.main.util.ColorUtil;
import com.steve6472.sge.gfx.Render;
import com.steve6472.sge.gui.components.Button;
import com.steve6472.sge.gui.components.Slider;
import com.steve6472.sge.main.MainApp;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 07.06.2019
 * Project: SJP
 *
 ***********************/
public class SliderColorSelectDialog extends AdvancedMoveableDialog
{
	private Slider redSlider, greenSlider, blueSlider;
	private int redMin, redMax, greenMin, greenMax, blueMin, blueMax;

	private int startingColor;

	private BiConsumer<Button, SliderColorSelectDialog> okEvent;
	private Consumer<Button> closeEvent;
	private Consumer<SliderColorSelectDialog> changeEvent;

	public SliderColorSelectDialog(BiConsumer<Button, SliderColorSelectDialog> okEvent)
	{
		this.okEvent = okEvent;
	}

	public SliderColorSelectDialog(BiConsumer<Button, SliderColorSelectDialog> okEvent, int color)
	{
		this.okEvent = okEvent;
		this.startingColor = color;
	}

	@Override
	public void init(MainApp main)
	{
		setSize(276 + 60, 138);
		initCloseButton().addClickEvent(closeEvent);

		redSlider = new Slider()
		{
			@Override
			public void renderBar()
			{
				super.renderBar();
				Render.fillRect(x + 4, y + 4, width - 8, height - 8, redMin, redMin, redMax, redMax);
			}
		};
		redSlider.setSize(256, 16);
		redSlider.setRelativeLocation(10, 30);
		redSlider.setButtonSize(16, 30);
		redSlider.setMaxValue(255);
		redSlider.setValue(ColorUtil.getRed(startingColor));
		redSlider.addChangeEvent(c -> changeEvent.accept(this));
		addComponent(redSlider);

		greenSlider = new Slider()
		{
			@Override
			public void renderBar()
			{
				super.renderBar();
				Render.fillRect(x + 4, y + 4, width - 8, height - 8, greenMin, greenMin, greenMax, greenMax);
			}
		};
		greenSlider.setSize(256, 16);
		greenSlider.setRelativeLocation(10, 70);
		greenSlider.setButtonSize(16, 30);
		greenSlider.setMaxValue(255);
		greenSlider.setValue(ColorUtil.getGreen(startingColor));
		greenSlider.addChangeEvent(c -> changeEvent.accept(this));
		addComponent(greenSlider);

		blueSlider = new Slider()
		{
			@Override
			public void renderBar()
			{
				super.renderBar();
				Render.fillRect(x + 4, y + 4, width - 8, height - 8, blueMin, blueMin, blueMax, blueMax);
			}
		};
		blueSlider.setSize(256, 16);
		blueSlider.setRelativeLocation(10, 110);
		blueSlider.setButtonSize(16, 30);
		blueSlider.setMaxValue(255);
		blueSlider.setValue(ColorUtil.getBlue(startingColor));
		blueSlider.addChangeEvent(c -> changeEvent.accept(this));
		addComponent(blueSlider);

		Button okButton = new Button("Ok");
		okButton.setRelativeLocation(276, 64);
		okButton.setSize(50, 30);
		okButton.addClickEvent(c -> okEvent.accept(c, this));
		okButton.addClickEvent(c -> close());
		addComponent(okButton);
	}

	@Override
	public void tick()
	{
		redMin = ColorUtil.getColor(0, greenSlider.getIValue(), blueSlider.getIValue());
		greenMin = ColorUtil.getColor(redSlider.getIValue(), 0, blueSlider.getIValue());
		blueMin = ColorUtil.getColor(redSlider.getIValue(), greenSlider.getIValue(), 0);

		redMax = ColorUtil.getColor(255, greenSlider.getIValue(), blueSlider.getIValue());
		greenMax = ColorUtil.getColor(redSlider.getIValue(), 255, blueSlider.getIValue());
		blueMax = ColorUtil.getColor(redSlider.getIValue(), greenSlider.getIValue(), 255);
	}

	public SliderColorSelectDialog setCloseEvent(Consumer<Button> closeEvent)
	{
		this.closeEvent = closeEvent;
		return this;
	}

	public SliderColorSelectDialog setChangeEvent(Consumer<SliderColorSelectDialog> changeEvent)
	{
		this.changeEvent = changeEvent;
		return this;
	}

	public int getColor()
	{
		return ColorUtil.getColor(redSlider.getIValue(), greenSlider.getIValue(), blueSlider.getIValue());
	}

	@Override
	public void render()
	{
		renderTitle("Slider Color Selector");
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
