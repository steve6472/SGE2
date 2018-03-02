/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 2. 2018
* Project: LWJGL
*
***********************/

package com.steve6472.sge2.test;

import com.steve6472.sge2.main.MainApplication;
import com.steve6472.sge2.main.gfx.Screen;
import com.steve6472.sge2.main.gui.Gui;
import com.steve6472.sge2.main.gui.GuiUtils;
import com.steve6472.sge2.main.gui.components.Background;
import com.steve6472.sge2.main.gui.components.CheckBox;
import com.steve6472.sge2.main.gui.components.Image;
import com.steve6472.sge2.main.gui.components.ItemList;

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
		itemList.addItem("L", 0.5f, 0.75f, 0.25f);
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
