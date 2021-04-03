/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 2. 2018
* Project: LWJGL
*
***********************/

package steve6472.sge.test;

import steve6472.sge.gfx.RainbowColor;
import steve6472.sge.gfx.font.ColoredTextBuilder;
import steve6472.sge.gfx.font.CustomChar;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gui.Gui;
import steve6472.sge.gui.components.*;
import steve6472.sge.gui.components.context.ContextMenu;
import steve6472.sge.gui.components.context.ContextMenuButton;
import steve6472.sge.gui.components.context.ContextMenuSubmenu;
import steve6472.sge.gui.components.context.ContextMenuToggleButton;
import steve6472.sge.gui.components.dialog.FileSelectorDialog;
import steve6472.sge.gui.components.dialog.MessageDialog;
import steve6472.sge.gui.components.dialog.TextInputDialog;
import steve6472.sge.gui.components.dialog.YesNoDialog;
import steve6472.sge.gui.components.schemes.SchemeButton;
import steve6472.sge.gui.components.schemes.SchemeListItemButton;
import steve6472.sge.gui.components.schemes.SchemeSlider;
import steve6472.sge.gui.planner.Planner;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.util.RandomUtil;

public class TestGui extends Gui
{
	private Planner planner;

	public TestGui(MainApp main)
	{
		super(main);
		planner = new Planner(this);
		
		showGui();
	}

	private Slider widthSider, heightSlider;

	@Override
	public void createGui()
	{
		Background.createComponent(this);

		Button buttonEnabledDark = new Button("Enabled")
		{
			@Override
			public void tick()
			{
				super.tick();
				hovered = false;
			}
		};
		buttonEnabledDark.setLocation(20, 20);
		buttonEnabledDark.setSize(100, 30);
		addComponent(buttonEnabledDark);

		Button buttonHoveredDark = new Button("Hovered")
		{
			@Override
			public void tick()
			{
				super.tick();
				hovered = true;
			}
		};
		buttonHoveredDark.setLocation(20, 60);
		buttonHoveredDark.setSize(100, 30);
		addComponent(buttonHoveredDark);

		Button buttonDisabledDark = new Button("Disabled");
		buttonDisabledDark.setLocation(20, 100);
		buttonDisabledDark.setSize(100, 30);
		buttonDisabledDark.setEnabled(false);
		addComponent(buttonDisabledDark);

		Button buttonDark = new Button("Dark");
		buttonDark.setLocation(20, 140);
		buttonDark.setSize(100, 30);
		addComponent(buttonDark);

		SchemeButtonLight schemeButtonLight = new SchemeButtonLight();
		schemeButtonLight.load("*light/button.txt");


		Button buttonEnabledLight = new Button("Enabled")
		{
			@Override
			public void tick()
			{
				super.tick();
				hovered = false;
			}
		};
		buttonEnabledLight.setLocation(140, 20);
		buttonEnabledLight.setSize(100, 30);
		buttonEnabledLight.setScheme(schemeButtonLight);
		addComponent(buttonEnabledLight);

		Button buttonHoveredLight = new Button("Hovered")
		{
			@Override
			public void tick()
			{
				super.tick();
				hovered = true;
			}
		};
		buttonHoveredLight.setLocation(140, 60);
		buttonHoveredLight.setSize(100, 30);
		buttonHoveredLight.setScheme(schemeButtonLight);
		addComponent(buttonHoveredLight);

		Button buttonDisabledLight = new Button("Disabled");
		buttonDisabledLight.setLocation(140, 100);
		buttonDisabledLight.setSize(100, 30);
		buttonDisabledLight.setEnabled(false);
		buttonDisabledLight.setScheme(schemeButtonLight);
		addComponent(buttonDisabledLight);

		Button buttonLignt = new Button("Light");
		buttonLignt.setLocation(140, 140);
		buttonLignt.setSize(100, 30);
		buttonLignt.setScheme(schemeButtonLight);
		buttonLignt.addClickEvent(c ->
				mainApp.showDialog(new FileSelectorDialog("D:\\Minecraft\\.minecraft").setIdealAccept(a -> System.out.println(a.getAbsolutePath()))).center());
		addComponent(buttonLignt);

		StringBuilder sb = new StringBuilder();
		Font.characters.values().forEach(c -> sb.append(c.getCharacter()));
		String random = sb.toString();

		buttonLignt.addClickEvent(c -> {
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < RandomUtil.randomInt(4, 16); i++)
			{
				b.append(random.charAt(RandomUtil.randomInt(0, random.length() - 1)));
			}
			System.out.println(b.toString());
			buttonLignt.setText(b.toString());
		});

		/* Sliders */

		/*Slider sliderEnabledDark = new Slider()
		{
			@Override
			public void tick()
			{
				super.tick();
				hovered = false;
				setValue(50);
			}
		};
		sliderEnabledDark.setLocation(260, 27);
		sliderEnabledDark.setSize(100, 16);
		sliderEnabledDark.setButtonSize(16, 30);
		addComponent(sliderEnabledDark);

		Slider sliderHoveredDark = new Slider()
		{
			@Override
			public void tick()
			{
				super.tick();
				hovered = true;
				setValue(50);
			}
		};
		sliderHoveredDark.setLocation(260, 67);
		sliderHoveredDark.setSize(100, 16);
		sliderHoveredDark.setButtonSize(16, 30);
		addComponent(sliderHoveredDark);

		Slider sliderDisabledDark = new Slider();
		sliderDisabledDark.setLocation(260, 107);
		sliderDisabledDark.setSize(100, 16);
		sliderDisabledDark.setButtonSize(16, 30);
		sliderDisabledDark.setValue(50);
		sliderDisabledDark.setEnabled(false);
		addComponent(sliderDisabledDark);

		Slider sliderDark = new Slider();
		sliderDark.setLocation(260, 147);
		sliderDark.setSize(100, 16);
		sliderDark.setButtonSize(16, 30);
		sliderDark.setMaxValue(512);
		sliderDark.setValue(256);
		addComponent(sliderDark);

		SchemeSliderLight schemeSliderLight = new SchemeSliderLight();
		schemeSliderLight.load("*light/slider.txt");

		Slider sliderEnabledLight = new Slider()
		{
			@Override
			public void tick()
			{
				super.tick();
				hovered = false;
				setValue(50);
			}
		};
		sliderEnabledLight.setLocation(380, 27);
		sliderEnabledLight.setSize(100, 16);
		sliderEnabledLight.setButtonSize(16, 30);
		sliderEnabledLight.setScheme(schemeSliderLight);
		addComponent(sliderEnabledLight);

		Slider sliderHoveredLight = new Slider()
		{
			@Override
			public void tick()
			{
				super.tick();
				hovered = true;
				setValue(50);
			}
		};
		sliderHoveredLight.setLocation(380, 67);
		sliderHoveredLight.setSize(100, 16);
		sliderHoveredLight.setButtonSize(16, 30);
		sliderHoveredLight.setScheme(schemeSliderLight);
		addComponent(sliderHoveredLight);

		Slider sliderDisabledLight = new Slider();
		sliderDisabledLight.setLocation(380, 107);
		sliderDisabledLight.setSize(100, 16);
		sliderDisabledLight.setButtonSize(16, 30);
		sliderDisabledLight.setValue(50);
		sliderDisabledLight.setEnabled(false);
		sliderDisabledLight.setScheme(schemeSliderLight);
		addComponent(sliderDisabledLight);

		Slider sliderLight = new Slider();
		sliderLight.setLocation(380, 147);
		sliderLight.setSize(100, 16);
		sliderLight.setButtonSize(16, 30);
		sliderLight.setMaxValue(512);
		sliderLight.setValue(256);
		sliderLight.setScheme(schemeSliderLight);
		addComponent(sliderLight);

		widthSider = sliderLight;
		heightSlider = sliderDark;

		/* Item List */
/*
		ItemList darkItemList = new ItemList(4);
		darkItemList.setLocation(20, 180);
		darkItemList.setSize(100, 120);
		addComponent(darkItemList);
		for (int i = 0; i < 3; i++)
		{
			darkItemList.addItem("Item #" + i);
		}

		SchemeListItemButtonLight schemeListItemButtonLight = new SchemeListItemButtonLight();
		schemeListItemButtonLight.load("*light/listItemButton.txt");

		ItemList lightItemList = new ItemList(4);
		lightItemList.setLocation(140, 180);
		lightItemList.setSize(100, 120);
		addComponent(lightItemList);
		for (int i = 0; i < 8; i++)
		{
			ListItem li = lightItemList.addItem("Item #" + i);
			li.setScheme(schemeListItemButtonLight);
			li.setScheme(MainApp.getSchemeRegistry().copyDefaultScheme(SchemeListItemButton.class));

//			li.addClickEvent(c -> c.setText("Item #" + Util.getRandomInt(64, 0)));
//			li.addIfClickEvent(ListItem::isSelected, c -> c.setSelected(false));
		}

		/* Text Field */
/*
		TextField darkTextField = new TextField();
		darkTextField.setLocation(500, 20);
		darkTextField.setSize(200, 30);
		darkTextField.setFontSize(2);
		addComponent(darkTextField);

		TextField darkTextField_ = new TextField();
		darkTextField_.setLocation(500, 65);
		darkTextField_.setSize(200, 20);
		darkTextField_.setFontSize(1);
		addComponent(darkTextField_);

		TextField smallField = new TextField();
		smallField.setLocation(500, 85);
		smallField.setSize(200, 30);
		smallField.setFontSize(1);
		addComponent(smallField);

		/* Context Menu */
		ContextMenu contextMenu = new ContextMenu("main");
		contextMenu.setSize(128);
		addContextMenu(contextMenu);
		setContextMenu("main");

		ContextMenuButton contextMenuButtonExit = new ContextMenuButton();
		contextMenuButtonExit.setName("Exit");
		contextMenuButtonExit.setEnabled(true);
		contextMenuButtonExit.setImage('\u0014');
		contextMenuButtonExit.addClickEvent(c -> c.contextMenu.hide());
		contextMenuButtonExit.addClickEvent(c ->
				getMainApp().showDialog(
						new YesNoDialog("Do you wish to Exit ?", "Are you sure ?"))
						.addYesClickEvent(y -> getMainApp().exit())
						.center());

		ContextMenuButton contextMenuButtonShowDialog = new ContextMenuButton();
		contextMenuButtonShowDialog.setName("Show Dialog");
		contextMenuButtonShowDialog.setEnabled(true);
		contextMenuButtonShowDialog.setImage('\u0010');
		contextMenuButtonShowDialog.addClickEvent(c -> c.contextMenu.hide());

		String message0 = "This message\nis really important\nto the development\nof Message Dialog";
		String message1 = "This message\nis not important\nat all.";
		String message2 = "This message\nis not important";

		contextMenuButtonShowDialog.addClickEvent(c ->
				getMainApp().showDialog(
						new MessageDialog(message0, "Simple Dialog", 250))
						.center());

		ContextMenuButton contextMenuButtonShowDialogs = new ContextMenuButton();
		contextMenuButtonShowDialogs.setName("Show Dialogs");
		contextMenuButtonShowDialogs.setEnabled(true);
		contextMenuButtonShowDialogs.setImage('\u0010');
		contextMenuButtonShowDialogs.addClickEvent(c -> c.contextMenu.hide());

		contextMenuButtonShowDialogs.addClickEvent(c ->
				getMainApp().showDialog(
						new MessageDialog(message0, "Simple Dialog 0", 250))
						.center());
		contextMenuButtonShowDialogs.addClickEvent(c ->
				getMainApp().showDialog(
						new MessageDialog(message1, "Simple Dialog 1", 250))
						.center());
		contextMenuButtonShowDialogs.addClickEvent(c ->
				getMainApp().showDialog(
						new MessageDialog(message2, "Simple Dialog 2", 250))
						.center());

		ContextMenuToggleButton contextMenuToggleButton = new ContextMenuToggleButton();
		contextMenuToggleButton.setName("Something");


		ContextMenuToggleButton contextMenuToggleButtonLight = new ContextMenuToggleButton();
		contextMenuToggleButtonLight.setName("Light");

		ContextMenuToggleButton contextMenuToggleButtonDark = new ContextMenuToggleButton();
		contextMenuToggleButtonDark.setName("Dark");

		ContextMenuToggleButton contextMenuToggleButtonMythic = new ContextMenuToggleButton();
		contextMenuToggleButtonMythic.setName("Mythic");

		RadioGroup.addContextMenuToggleButtons(
				contextMenuToggleButtonLight,
				contextMenuToggleButtonDark,
				contextMenuToggleButtonMythic);

		ContextMenuSubmenu contextMenuSubmenu = new ContextMenuSubmenu();
		contextMenuSubmenu.setName("Submenu");
		contextMenuSubmenu.setImage('#');

		contextMenu.add(contextMenuButtonExit);
		contextMenu.addSeparator(3);
		contextMenu.add(contextMenuButtonShowDialog);
		contextMenu.add(contextMenuButtonShowDialogs);
		contextMenu.addSeparator(3);
		contextMenu.add(contextMenuToggleButton);
		contextMenu.addSeparator(11);
		contextMenu.add(contextMenuToggleButtonLight);
		contextMenu.add(contextMenuToggleButtonDark);
		contextMenu.add(contextMenuToggleButtonMythic);
		contextMenu.addSeparator(11);
		contextMenu.add(contextMenuSubmenu);

		ContextMenu submenu = new ContextMenu();
		submenu.setSize(160);

		ContextMenuButton submenuButton = new ContextMenuButton();
		submenuButton.addClickEvent(c ->
		{
			TextInputDialog tid = new TextInputDialog(c.name, "Change the Text");
			mainApp.showDialog(tid).center();
			tid.addYesClickEvent(b -> c.setName(tid.getText()));
		});
		submenuButton.addClickEvent(c -> contextMenu.hide());
		submenuButton.setName("Change my Text");

		ContextMenuButton openFileSelector = new ContextMenuButton();
		openFileSelector.setName("Open File Selector");
		openFileSelector.setImage(CustomChar.FOLDER_ICON);
		openFileSelector.addClickEvent(c -> mainApp.showDialog(new MessageDialog("Sike", "Denied", 250)).center());

		submenu.add(openFileSelector);
		submenu.addSeparator();
		submenu.add(submenuButton);

		contextMenuSubmenu.setSubmenu(submenu);

		buttonDark.addClickEvent(c -> contextMenu.show(buttonDark.getX(), buttonDark.getY() + buttonDark.getHeight(), getMainApp().getMouseX(), getMainApp().getMouseY()));

		/* Check Box */

		NamedCheckBox checkBoxLight = new NamedCheckBox();
		checkBoxLight.setLocation(335, 245);
		checkBoxLight.setSize(20, 20);
		checkBoxLight.setSelectedChar('\u000b');
		checkBoxLight.setText("Text");
		addComponent(checkBoxLight);
		checkBoxLight.setScheme(schemeButtonLight);

		CheckBox checkBoxDark = new CheckBox();
		checkBoxDark.setLocation(290, 180);
		checkBoxDark.setSize(40, 40);
		checkBoxDark.setFontSize(3);
		addComponent(checkBoxDark);

		initColors();
	}

	RainbowColor[] rc;

	private void initColors()
	{
		rc = new RainbowColor[255];
		for (int i = 0; i < 255; i++)
		{
			rc[i] = new RainbowColor();
			for (int j = 0; j < i; j++)
			{
				rc[i].tick();
			}
		}
	}

	@Override
	public void guiTick()
	{
		planner.tick();
		for (RainbowColor rainbowColor : rc)
		{
			rainbowColor.tick();
		}

		if (System.currentTimeMillis() % 49 == 0)
		{
			System.out.println("\n".repeat(8));
			getMainApp().getEventHandler().runTest();
		}
	}

	private ColoredTextBuilder cursorText = ColoredTextBuilder
		.create()
		.addText("Cursor ")
		.addCustomChar(CustomChar.FORWARD_ARROW)
		.addColoredText(" X", 1, 0, 0)
		.addText("/")
		.addColoredText("Y", 0, 1, 0)
		.addColoredText("", 1, 0, 0)
		.addText("/")
		.addColoredText("", 0, 1, 0);
	
	@Override
	public void render()
	{
		int mx = mainApp.getMouseX();
		int my = mainApp.getMouseY();

//		Font.renderCustom("This [#ff0000]text [0.0 1.0 0.0]is [0.0;0.0;1.0]colored [#ff0000]![#00ff00]![#0000ff]![#ffff00]![#00ffff]![#ff00ff]!", 7, 7);
//		Font.renderCustom("[#ff0000]X[#ffffff]/[#00ff00]Y[#ffffff]: [#ff0000]" + mx + "[#ffffff]/[#00ff00]" + my, 7, 7);
//		Font.renderCustom("Cursor:  #RX#W/#GY#W: #R" + mx + "#W/#G" + my, "#R:[#ff0000];#G:[#00ff00];#W:[#ffffff]", 7, 7);
		/*Font.renderCustom(7, 7, 1,
				"Cursor ", CustomChar.FORWARD_ARROW, "[#ff0000]", " X", "[1.0,1.0,1.0]", "/", "[0,255,0]", "Y", "[255,0,0]", " ", mx, "[#ffffff]", "/", "[0,255,0]", my);
*/
		cursorText.get(5).setText(" " + mx);
		cursorText.get(7).setText("" + my);
		Font.renderCustom(7, 7, 1, cursorText);
/*
		for (Component c : getComponents())
		{
			if (Util.isInRectangle(c.getX(), c.getY(), c.getX() + c.getWidth(), c.getY() + c.getHeight(), mainApp.getMouseX(), mainApp.getMouseY()))
			{
				Font.renderCustom("Component: " + c.getClass().getSimpleName() + "  #RX#W: " + c.getX() + "  #GY#W: " + c.getY() +
								"  #PW#W: " + c.getWidth() + "  #AH#W: " + c.getHeight(),
						"#R:[#ff0000];#G:[#00ff00];#W:[#ffffff];#P:[#ff00ff];#A:[#00ffff]", 7, 522);
			}
		}*/

		Font.render("This\nIs\nGUI", 7, 450, 1);

//		SpriteRender.renderSprite(mainApp.getMouseX(), mainApp.getMouseY(), widthSider.getIValue(), heightSlider.getIValue(), 0, Font.getFont());
//		SpriteRender.renderSpriteFromAtlas(
//				mainApp.getMouseX(), mainApp.getMouseY(), 8, 8, 0, Font.getFont().getId(),
//				512, 512, 29 * 8, 1 * 8, 8, 8);
//		SpriteRender.renderDoubleBorder(20, 310, 100, 30);

		/*
		Object[] os = new Object[512];
		for (int i = 0; i < 255; i++)
		{
			os[i * 2] = rc[i].toColorTag();
			os[i * 2 + 1] = CustomChar.SINGLE_FUCKING_LINE;
		}

		Font.renderCustom(7, 480, 2, os);*/

		planner.render();
	}

	private class SchemeButtonLight extends SchemeButton
	{}
	private class SchemeSliderLight extends SchemeSlider
	{}
	private class SchemeListItemButtonLight extends SchemeListItemButton {}
}
