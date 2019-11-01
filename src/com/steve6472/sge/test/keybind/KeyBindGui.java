/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 7. 7. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test.keybind;

import com.steve6472.sge.gfx.Render;
import com.steve6472.sge.gui.Gui;
import com.steve6472.sge.gui.components.Background;
import com.steve6472.sge.gui.components.Button;
import com.steve6472.sge.gui.components.ScrollBar;
import com.steve6472.sge.gui.components.schemes.SchemeButton;
import com.steve6472.sge.main.KeyNamer;
import com.steve6472.sge.main.MainApp;
import com.steve6472.sge.main.events.Event;
import com.steve6472.sge.main.events.KeyEvent;
import com.steve6472.sge.main.events.MouseEvent;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class KeyBindGui extends Gui
{
	private static final long serialVersionUID = 6338357277999055291L;
	List<KeyEntry> keys;
	int scroll;
	
	ScrollBar scrollBar;
	int buttons = 0;
	
	Button activeButton = null;
	int keyId = -1;

	public KeyBindGui(MainApp mainApp)
	{
		super(mainApp);
		showGui();
		switchRender();
		keys = new ArrayList<>();
	}
	
	public void addKeyEntry(KeyEntry entry)
	{
		keys.add(entry);
		entry.id = buttons;
		Button b = new Button("" + KeyNamer.getName(entry.defaultKey));
		b.setLocation(650, 40 + buttons * 60);
		b.setFontSize(2);
		b.setSize(200, 40);
		b.addIfClickEvent(f -> activeButton != b, c -> {
			b.setText("> " + b.getText() + " <");
			b.getScheme().setFontColor(1f, 0.7f, 0f);
			activeButton = b;
			keyId = entry.id;
		});
		addComponent(b);
		
		Button r = new Button("Reset");
		r.setLocation(895, 40 + buttons * 60);
		r.setFontSize(2);
		r.setSize(120, 40);
		r.addClickEvent(c -> {
			b.setText(KeyNamer.getName(entry.defaultKey));
			b.setScheme(MainApp.getSchemeRegistry().copyDefaultScheme(SchemeButton.class));
			activeButton = null;
			keyId = -1;
		});
		addComponent(r);
		buttons++;
	}

	@Override
	public void createGui()
	{
		scrollBar = new ScrollBar();
		scrollBar.setLocation(14, 14);
		scrollBar.setSize(15, getMainApp().getWindowHeight() - 28);
		scrollBar.used = 4;
		scrollBar.visi = 2;
		addComponent(scrollBar);
	}

	@Override
	public void guiTick()
	{
	}

	@Override
	public void render()
	{
		Background.renderFrame(getMainApp());
		for (int i = 0; i < buttons; i++)
		{
			Render.fillRect(100, 35 + i * 60, getMainApp().getWindowWidth() - 200, 50, 0x50808080);
		}
	}
	
	@Event
	public void mouseClick(MouseEvent event)
	{
		if (event.getAction() == GLFW.GLFW_PRESS)
		{
			if (activeButton == null)
				return;
			
			System.out.println(KeyNamer.getName(event.getButton()));
			activeButton.setText(KeyNamer.getName(event.getButton()));
			keys.get(keyId).key = event.getButton();
			keys.get(keyId).type = EntryType.MOUSE;
			activeButton.setScheme(MainApp.getSchemeRegistry().copyDefaultScheme(SchemeButton.class));
			activeButton = null;
			keyId = -1;
		}
	}
	
	@Event
	public void keyEvent(KeyEvent event)
	{
		if (event.getAction() == GLFW.GLFW_PRESS)
		{
			if (activeButton == null)
				return;
			
			System.out.println(KeyNamer.getName(event.getKey()));
			activeButton.setText(KeyNamer.getName(event.getKey()));
			keys.get(keyId).key = event.getKey();
			keys.get(keyId).type = EntryType.KEYBOARD;
			activeButton.setScheme(MainApp.getSchemeRegistry().copyDefaultScheme(SchemeButton.class));
			activeButton = null;
			keyId = -1;
		}
	}

}
