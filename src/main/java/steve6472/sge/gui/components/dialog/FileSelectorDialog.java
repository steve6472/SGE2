package steve6472.sge.gui.components.dialog;

import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gfx.font.CustomChar;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gui.components.Button;
import steve6472.sge.gui.components.ScrollBar;
import steve6472.sge.gui.components.TextField;
import steve6472.sge.gui.components.context.ContextMenu;
import steve6472.sge.gui.components.context.ContextMenuButton;
import steve6472.sge.main.KeyList;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.Util;
import steve6472.sge.main.util.MathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 31.05.2019
 * Project: SJP
 *
 ***********************/
public class FileSelectorDialog extends AdvancedMoveableDialog
{
	private File path;

	private ContextMenu cm;

	private ScrollBar slider;
	private TextField search;
	private Button acceptButton;

	private long firstClick;
	private boolean flag, holdFlag, clickFlag, contextFlag, anotherClick;
	private File over, click;

	private BiConsumer<File, File> biAccept;
	private Consumer<File> accept;

	private String title;

	public FileSelectorDialog()
	{
		this.path = new File("C:");
		title = "File Selector";
	}

	/**
	 *
	 * @param biAccept 1st File - Directory, 2nd File - Selected File
	 */
	public FileSelectorDialog(BiConsumer<File, File> biAccept)
	{
		this.path = new File("C:");
		this.biAccept = biAccept;
		title = "File Selector";
	}

	/**
	 *
	 * @param accept Ideal File
	 */
	public FileSelectorDialog(Consumer<File> accept)
	{
		this.path = new File("C:");
		this.accept = accept;
		title = "File Selector";
	}

	public FileSelectorDialog(File path)
	{
		this.path = path;
		title = "File Selector";
	}

	/**
	 *
	 * @param biAccept 1st File - Directory, 2nd File - Selected File
	 */
	public FileSelectorDialog(File path, BiConsumer<File, File> biAccept)
	{
		this.path = path;
		this.biAccept = biAccept;
		title = "File Selector";
	}

	/**
	 *
	 * @param accept Ideal File
	 */
	public FileSelectorDialog(File path, Consumer<File> accept)
	{
		this.path = path;
		this.accept = accept;
		title = "File Selector";
	}

	public FileSelectorDialog(String path)
	{
		this.path = new File(path);
		title = "File Selector";
	}

	/**
	 *
	 * @param biAccept 1st File - Directory, 2nd File - Selected File
	 */
	public FileSelectorDialog(String path, BiConsumer<File, File> biAccept)
	{
		this.path = new File(path);
		this.biAccept = biAccept;
		title = "File Selector";
	}

	/**
	 *
	 * @param accept Ideal File
	 */
	public FileSelectorDialog(String path, Consumer<File> accept)
	{
		this.path = new File(path);
		this.accept = accept;
		title = "File Selector";
	}

	@Override
	public void init(MainApp main)
	{
		setSize(16 * 20, 9 * 25 - 6);

		initCloseButton();
		slider = new ScrollBar();
		slider.setSize(20, 147);
		slider.setRelativeLocation(width - 28, 58);
		slider.visi = 9;
		slider.used = 39;
		addComponent(slider);

		search = new TextField();
		search.setRelativeLocation(8, 191);
		search.setSize(259, 20);
		search.focus();
		addComponent(search);

		Button back = new Button();
		back.setText("" + CustomChar.BACK_ARROW);
		back.setSize(20, 20);
		back.setRelativeLocation(8, 25);
		back.addClickEvent(c ->
		{
			if (path.getParentFile() != null)
				path = path.getParentFile();
		});
		addComponent(back);

		acceptButton = new Button();
		acceptButton.setText("" + CustomChar.CHECK);
		acceptButton.setSize(40, 20);
		acceptButton.setRelativeLocation(width - 48, height - 28);
		acceptButton.addClickEvent(c -> {
			if (this.accept != null) this.accept.accept(getIdealFile());
			if (this.biAccept != null) this.biAccept.accept(getDirectory(), getSelectedFile());
			close();
		});
		addComponent(acceptButton);


		cm = new ContextMenu();
		cm.setSize(70);
		cm.darkenGui = false;

		for (File f : File.listRoots())
		{
			ContextMenuButton b = new ContextMenuButton();
			b.setName(f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 1));
			b.addClickEvent(c ->
			{
				path = f;
				cm.hide();
			});
			cm.add(b);
		}

		setContextMenu(cm);
	}

	@Override
	public void tick()
	{
		if (getMain().isKeyPressed(KeyList.ENTER) || getMain().isKeyPressed(KeyList.KP_ENTER))
		{
			acceptButton.forceDoClick();
		}

		slider.specialScroll = Util.isCursorInRectangle(getMain(), x + 8, y + 58, 303, 129);

		if (getMain().isLMBHolded())
		{
			holdFlag = true;
		}

		if (System.currentTimeMillis() - firstClick > 1001 && firstClick != 0 || over == null)
		{
			flag = false;
			firstClick = 0;
			holdFlag = false;
		}

		if (!getMain().isLMBHolded() && holdFlag && !flag)
		{
			firstClick = System.currentTimeMillis();
			flag = true;
			holdFlag = false;

			if (over == null)
			{
				flag = false;
				firstClick = 0;
				holdFlag = false;
			}
		}

		if (firstClick != 0 && !getMain().isLMBHolded() && holdFlag && flag)
		{
			flag = false;
			firstClick = 0;
			holdFlag = false;

			doubleClick();
		}

		/* Root Selector */
		if (getMain().isLMBHolded() && Util.isCursorInRectangle(getMain(), x + 74, y + 24, 20, 20) && !contextFlag)
		{
			contextFlag = true;
			cm.show(x + 34, y + 44);
			cm.canClose = false;
		}

		if (contextFlag && !getMain().isLMBHolded())
		{
			contextFlag = false;
			cm.canClose = true;
		}

		if (getMain().isLMBHolded() && !Util.isCursorInRectangle(getMain(), x + 8, y + 58, 303, 129) && !anotherClick)
		{
			anotherClick = true;
			if (!acceptButton.isHovered()) click = null;
			over = null;
			flag = false;
			firstClick = 0;
			holdFlag = false;
		}

		if (anotherClick && !getMain().isLMBHolded())
		{
			anotherClick = false;
		}
	}

	private void doubleClick()
	{
		if (click != null && click.isDirectory())
		{
			path = new File(click.getAbsolutePath());
			click = null;
			over = null;
			search.setText("");
			slider.scroll(1000000);
		}
	}

	@Override
	public void render()
	{
		renderTitle(title);
		SpriteRender.manualStart();

		SpriteRender.fillRect(x + 2, y + 20, width - 4, 30, 0.65f, 0.65f, 0.65f, 1.0f);
		SpriteRender.fillRect(x + 2, y + 50, width - 4, 2, 0.0f, 0.0f, 0.0f, 1.0f);

		/* Root List */
		SpriteRender.renderSingleBorder(x + 34, y + 25, 60, 20, 0, 0, 0, 1, 0.4f, 0.4f, 0.4f, 1);
		SpriteRender.fillRect(x + 74, y + 27, 2, 16, 0, 0, 0, 1);
		if (Util.isCursorInRectangle(getMain(), x + 74, y + 24, 20, 20))
		{
			SpriteRender.fillRect(x + 76, y + 27, 16, 16, 0.3f, 0.3f, 0.3f, 1);
		} else
		{
			SpriteRender.fillRect(x + 76, y + 27, 16, 16, 0.2f, 0.2f, 0.2f, 1);
		}

		/* Folder List */
		SpriteRender.renderSingleBorder(x + 8, y + 58, 286, 129, 0, 0, 0, 1, 0.3f, 0.3f, 0.3f, 1.0f);

		SpriteRender.manualEnd();

		try
		{
			renderFileList();
		} catch (Exception ex)
		{
			ex.printStackTrace();

			String red = "[#FF6B68]";
			String tab = "[x32]";

			List<String> text = new ArrayList<>();
			text.add(red);
			text.add(ex.getMessage());
			for (int i = 0; i < ex.getStackTrace().length; i++)
			{
				text.add("\n");
				text.add(tab);
				text.add("at " + ex.getStackTrace()[i]);
			}
			getMain().showDialog(new MessageDialog(text.toArray(new String[0]), "Error", 100)).center();
		}

		String root = "";
		for (File f : File.listRoots())
		{
			if (path.getAbsolutePath().startsWith(f.getAbsolutePath()))
			{
				root = f.getAbsolutePath();
				break;
			}
		}

		/* Root List */
		Font.renderCustom(x + 80, y + 31, 1, CustomChar.ARROW_DOWN_NOSHAFT);
		Font.renderCustom(x + 39, y + 31, 1, root.substring(0, root.length() - 1));

		Font.renderCustom(x + 100, y + 30, 1, "[#ffaa00]", "\\", "[#ffffff]", path.getAbsolutePath().substring(root.length()));
	}

	private void renderFileList()
	{
		over = null;

		File f = new File(path.getAbsolutePath());

		if (f.exists())
		{
			List<File> out = new ArrayList<>();
			for (File file : Objects.requireNonNull(f.listFiles())) if (file.isDirectory() && !file.isHidden() && file.getName().startsWith(search.getText())) out.add(file);
			for (File file : Objects.requireNonNull(f.listFiles())) if (!file.isDirectory() && !file.isHidden() && file.getName().startsWith(search.getText())) out.add(file);

			slider.used = out.size();

			for (int i = slider.scrl; i < slider.visi + slider.scrl; i++)
			{
				if (i >= out.size())
					break;
				File d = out.get(i);
				int q = i * 14 - slider.scrl * 14;
				{
					if (click != null && d.getPath().equals(click.getPath()))
					{
						SpriteRender.fillRect(x + 10, y + 60 + q, getWidth() - 38, 13, 0.7f, 0.7f, 1f, 1);
					} else
					{
						SpriteRender.fillRect(x + 10, y + 60 + q, getWidth() - 38, 13, 0.55f, 0.55f, 0.55f, 1);
					}

					// Limit name to 260 pixels
					String name = d.getName();
					if (Font.getTextWidth(name, 1) > 260)
					{
						for (int j = name.length(); j >= 0; j--)
						{
							if (Font.getTextWidth(name.substring(0, j), 1) <= 260)
							{
								Font.render(name.substring(0, j), x + 30, y + 62 + q, 1);
								break;
							}
						}
					} else
					{
						Font.render(name, x + 30, y + 62 + q, 1);
					}

					boolean onFile = MathUtil.isInRectangle(x + 10, y + 60 + q, x + getWidth() - 28, y + 73 + q, getMain().getMouseX(), getMain().getMouseY());

					if (onFile)
					{
						over = d;
					}

					if (onFile && !clickFlag && getMain().isLMBHolded())
					{
						if (flag && click != null && !click.getPath().equals(d.getPath()))
						{
							flag = false;
							firstClick = 0;
							holdFlag = false;
						}

						clickFlag = true;
						click = d;
					}

					if (!getMain().isLMBHolded())
					{
						clickFlag = false;
					}

					// Little Thingie on the left
					if (d.isDirectory())
					{
						Font.renderCustom(x + 12, y + 60 + q, 1.5f, "[#FFE793]", CustomChar.FOLDER_ICON);
					}
					else if (Util.getExtension(name).equals("txt"))
					{
						Font.renderCustom(x + 12, y + 60 + q, 1.5f, "[#cccccc]", CustomChar.TEXT_FOLDER_ICON);
					} else if (Util.getExtension(name).equals("json"))
					{
						Font.renderCustom(x + 12, y + 60 + q, 1.5f, "[#70ff70]", CustomChar.TEXT_FOLDER_ICON);
					} else if (Util.getExtension(name).equals(""))
					{
						Font.renderCustom(x + 12, y + 60 + q, 1.5f, CustomChar.UNKNOWN_FILE);
					} else if (Util.getExtension(name).equals("nbt"))
					{
						Font.renderCustom(x + 12, y + 60 + q, 1.5f, "[#ff8c00]", CustomChar.NBT_FILE);
					} else if (Util.getExtension(name).equals("bat"))
					{
						Font.renderCustom(x + 12, y + 60 + q, 1.5f, "[#A4D3EE]", CustomChar.BAT_FILE);
					}
				}
			}
		}
	}

	public File getSelectedFile()
	{
		return click;
	}

	public File getDirectory()
	{
		return path;
	}

	public File getIdealFile()
	{
		return click == null ? path : click;
	}

	public FileSelectorDialog setIdealAccept(Consumer<File> accept)
	{
		this.accept = accept;
		this.biAccept = null;
		return this;
	}

	public FileSelectorDialog setBiAccept(BiConsumer<File, File> biAccept)
	{
		this.biAccept = biAccept;
		this.accept = null;
		return this;
	}

	public String getTitle()
	{
		return title;
	}

	public FileSelectorDialog setTitle(String title)
	{
		this.title = title;
		return this;
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
