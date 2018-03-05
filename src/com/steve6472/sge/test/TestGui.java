/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge.test;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Gui;
import com.steve6472.sge.gui.GuiUtils;
import com.steve6472.sge.gui.components.Background;
import com.steve6472.sge.gui.components.CheckBox;
import com.steve6472.sge.gui.components.Image;
import com.steve6472.sge.gui.components.FileBrowser;
import com.steve6472.sge.gui.components.ItemGridList;
import com.steve6472.sge.gui.components.ItemList;
import com.steve6472.sge.gui.components.NumberSelector;
import com.steve6472.sge.gui.components.ProgressBar;
import com.steve6472.sge.gui.components.Slider;
import com.steve6472.sge.gui.components.TextField;
import com.steve6472.sge.main.MainApplication;

public class TestGui extends Gui
{
	private static final long serialVersionUID = 487027792076258720L;

	public TestGui(MainApplication game)
	{
		super(game);
		
		showGui();
		switchRender();
	}

	Slider slider;
	
	@Override
	public void createGui()
	{
		GuiUtils.createBasicLayout(this);
		
		CheckBox box = new CheckBox();
		box.setLocation(14, 37);
		addComponent(box);
		
		ItemList itemList = new ItemList();
		itemList.setVisibleItems(8);
		itemList.setSize(100, 160);
		itemList.setLocation(14 + 50, 37);
		itemList.addItem("H", 0xffff0000);
		itemList.addItem("E", 0xff00ff00);
		itemList.addItem("L", 0xff0000ff);
		itemList.addItem("L", 0.5f, 0.75f, 0.25f, 1f);
		itemList.addItem("O");
		itemList.addItem(" ");
		itemList.addItem("W");
		itemList.addItem("O");
		itemList.addItem("R");
		itemList.addItem("L");
		itemList.addItem("D");
		addComponent(itemList);

		Image image = new Image(Test.sprite);
		image.setLocation(174, 37);
		image.setRepeat(true);
		image.setSize(1, 2);
//		image.setSize(32, 64);
		addComponent(image);
		
		ProgressBar progressBar = new ProgressBar();
		progressBar.setLocation(14, 200);
		progressBar.setSize(300, 40);
		progressBar.setValue(50);
		addComponent(progressBar);
		
		ItemGridList itemGridList = new ItemGridList();
		itemGridList.setLocation(174 + 62, 37);
		itemGridList.setVisibleItems(4, 4);
		itemGridList.setSize(36 * 4, 36 * 4);
		for (int i = 0; i < 8; i++)
		{
			itemGridList.addItem("", Test.sprite, 1f, 1f, 1f, 1f);
			itemGridList.addItem("", Test.sprite, 0.5f, 0.5f, 0.5f, 1f);
			itemGridList.addItem("", Test.sprite, 0f, 0.5f, 1f, 1f);
			itemGridList.addItem("");
		}
		addComponent(itemGridList);
		
		NumberSelector numberSelector = new NumberSelector();
		numberSelector.setLocation(14, 260);
		numberSelector.setSize(40 * 4, 40);
		addComponent(numberSelector);
		
		TextField textField = new TextField();
		textField.setLocation(14, 310);
		textField.setSize(160, 40);
		textField.setFontSize(2);
		addComponent(textField);
		
		slider = new Slider();
		slider.setLocation(14 + 310, 204);
		slider.setSize(300);
		slider.setMaxValue(360);
		slider.addChangeEvent(() ->
		{
			progressBar.setValue((int) (slider.getValue() / 360d * 100d));
		});
		addComponent(slider);
		
		FileBrowser fileBrowser = new FileBrowser();
		addComponent(fileBrowser);
		fileBrowser.setLocation(630, 30);
		

	}

	{
		String[] types =
		//		  0						      1				                           2							3				4
		{ " wdith, height ", " rotation, translateX, translateY ", " repeatX, repeatY, repeat, scaleX, scaleY ", " flip ", "c00, c10, c11, c01 " };
		int[][] methods =
		{
				{0, 1, 2, 3, 4, 5},
				{0, 1, 2, 3, 4},
				{0, 1, 2, 3},
				{0, 1, 2},
				{0, 1},
				{0},
				{0, 1, 3, 4},
				{0, 2, 3, 4},
				{0, 3, 4},
				{0, 4},
				{0, 2}, 
				{0, 3},
		};
	}

	@Override
	public void guiTick()
	{
	}
	
	float rot = 0f;

	@Override
	public void render(Screen screen)
	{
		Background.renderFrame(screen, getMainApp());
		
		rot += 1f;
		
		screen.drawSprite(256, 256, Test.sprite, 64 + 256, 16 + 256, 32 * 4, 32, slider.getValue(), 4, 1, false, true, 0, 0, 0xffff00ff, 0xffffffff, 0xffffffff, 0xffffffff);

//		screen.drawSprite(50, 0, Test.hsv, rot);
//		screen.drawSprite(256, 256, Test.hsv, rot);
//		FunRenderMethods.renderPulsatingSquare(screen, 256, 256, rot, Test.hsv, Test.hsv.getWidth(), 0, 0, Test.hsv.getWidth(), Test.hsv.getHeight());
//		screen.drawSprite(256, 256, Test.hsv, (float) slider.getValue());
	}

}
