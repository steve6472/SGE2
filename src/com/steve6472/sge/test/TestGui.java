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
import com.steve6472.sge.gui.components.ItemGridList;
import com.steve6472.sge.gui.components.ItemList;
import com.steve6472.sge.gui.components.NumberSelector;
import com.steve6472.sge.gui.components.ProgressBar;
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
//		image.setRepeat(true);
//		image.setSize(2, 2);
		image.setSize(32, 64);
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
	}

	@Override
	public void guiTick()
	{
	}

	@Override
	public void render(Screen screen)
	{
		Background.renderFrame(screen, getMainApp());
	}

}
