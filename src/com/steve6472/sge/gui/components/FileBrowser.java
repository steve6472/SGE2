/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 25. 9. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.gui.components;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

import com.steve6472.sge.gfx.RenderHelper;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApplication;

public class FileBrowser extends Component
{
	
	private static final long serialVersionUID = 8751008575519876989L;
	String currentPath = "C:";
	String selected = "";
	boolean isSelectedFolder = false;
	boolean isJar = false;
	ItemList files;
	Button goTo, back;

	public FileBrowser()
	{
	}

	public FileBrowser(String initialPath)
	{
		currentPath = initialPath;
	}

	@Override
	public void init(MainApplication game)
	{
		
//		try
//		{
//			currentPath = new File(FileBrowser.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getPath();
//		} catch (URISyntaxException e)
//		{
//			e.printStackTrace();
//		}
		
		files = new ItemList();
		files.setVisibleItems(12);
		files.setSize(256, 25 * 12);
		files.addChangeEvent(new ChangeEvent()
		{
			@Override
			public void change()
			{
				File f = new File(currentPath);
				if (f.isDirectory())
				{
					File sel = f.listFiles()[files.getSelectedIndex()];
					selected = sel.getPath();
					isSelectedFolder = sel.isDirectory();
				}
			}
		});
		addComponent(files);

		listFilesFromCurrentPath();

		goTo = new Button("->");
		goTo.setSize(40, 40);
		goTo.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				currentPath = selected;
				listFilesFromCurrentPath();
			}
		});
		addComponent(goTo);

		back = new Button("<-");
		back.setSize(40, 40);
		back.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				File f = new File(currentPath);
				if (f.getParent() != null)
				{
					currentPath = new File(currentPath).getParent();
					listFilesFromCurrentPath();
				}
			}
		});
		addComponent(back);
	}

	private void listFilesFromCurrentPath()
	{
		if (currentPath == null)
			return;
		files.getItems().clear();
		File file = new File(currentPath);
		try
		{
			for (File f : file.listFiles())
			{
				files.addItem(f.getName(), getSystemIcon(f));
			}
		} catch (Exception ex)
		{
			currentPath = new File(currentPath).getParent();
			listFilesFromCurrentPath();
		}
		files.scroll = 0;
		files.setSelectedItem(0);
	}

	private Sprite getSystemIcon(File f)
	{
		Icon img = FileSystemView.getFileSystemView().getSystemIcon(f);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(img.getIconWidth(), img.getIconHeight(), Transparency.BITMASK);
		Graphics2D g = image.createGraphics();
		img.paintIcon(null, g, 0, 0);
		g.dispose();
		return new Sprite(image);
	}
	
	@Override
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.files.setLocation(x + 8, y + 8);
		this.goTo.setLocation(x + 256 + 4 + 8, y + 8);
		this.back.setLocation(x + 256 + 4 + 8, y + 8 + 44);
	}
	
	public File getSelectedFile()
	{
		return new File(currentPath).listFiles()[files.getSelectedIndex()];
	}
	
	public void setPath(String newPath)
	{
		this.currentPath = newPath;
		listFilesFromCurrentPath();
	}
	
	public void reloadList()
	{
		listFilesFromCurrentPath();
	}
	
	public void lockDirectory()
	{
		goTo.disable();
		back.disable();
	}
	
	public void unlockDirectory()
	{
		goTo.enable();
		back.enable();
	}
	
	public String getCurrentPath()
	{
		return currentPath;
	}
	
	public String getSelected()
	{
		return selected;
	}

	@Override
	public void render(Screen screen)
	{
		RenderHelper.renderSingleBorder(x, y, 256 + 58, 25 * 12 + 16, 0xff3f3f3f, 0xffbfbfbf);
		renderComponents(screen);
	}

	@Override
	public void tick()
	{
		tickComponents();
	}

}
