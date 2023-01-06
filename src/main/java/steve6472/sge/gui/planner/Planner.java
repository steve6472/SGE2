package steve6472.sge.gui.planner;

import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gui.Component;
import steve6472.sge.gui.Gui;
import steve6472.sge.gui.components.dialog.Dialog;
import steve6472.sge.gui.components.dialog.OkTextInputDialog;
import steve6472.sge.main.KeyList;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.Util;
import steve6472.sge.main.events.Event;
import steve6472.sge.main.events.KeyEvent;
import steve6472.sge.main.util.RandomUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**********************
 * Created by steve6472
 * On date: 02.06.2019
 * Project: SGE2
 *
 ***********************/
public class Planner
{
//	private Gui gui;
	private MainApp main;

	private List<Box> boxList;

	private boolean toggleFlag, toggled;
	private int toggleKey;

	private boolean creatingNewBox;
	private int nbx, nby;
	private float nbr, nbg, nbb;

	Function<MainApp, Dialog> dialog;
	boolean inDialog;

	ComponentRenderer cr;

	public Planner(Gui gui)
	{
//		this.gui = gui;
		this.main = gui.getMainApp();
		toggleKey = KeyList.N;
		boxList = new ArrayList<>();
		cr = new ComponentRenderer(main);
	}

	@Event
	public void keys(KeyEvent e)
	{
		if (e.getMods() != KeyList.M_SHIFT && e.getAction() == KeyList.PRESS)
		{
			if (e.getKey() == KeyList.UP) moveCursor(0, -1);
			if (e.getKey() == KeyList.DOWN) moveCursor(0, 1);
			if (e.getKey() == KeyList.LEFT) moveCursor(-1, 0);
			if (e.getKey() == KeyList.RIGHT) moveCursor(1, 0);
		}

		if (e.getAction() != KeyList.PRESS || !toggled || inDialog)
			return;
		int mx = main.getMouseX(), my = main.getMouseY();

		if (e.getKey() == KeyList.B)
		{
			if (!creatingNewBox)
			{
				creatingNewBox = true;
				nbx = mx;
				nby = my;
				nbr = RandomUtil.randomFloat(0.3f, 1);
				nbg = RandomUtil.randomFloat(0.3f, 1);
				nbb = RandomUtil.randomFloat(0.3f, 1);
			} else
			{
				creatingNewBox = false;
				Box b = new Box();
				boxList.add(b);
				dialog = (m) -> {
					if (b.invalid)
					{
						inDialog = false;
						return null;
					}
					OkTextInputDialog otid = new OkTextInputDialog("Box #" + (boxList.size() - 1), "Box Name");
					return main.showDialog(otid).addOkClickEvent(c ->
					{
						b.name = otid.getText();
						inDialog = false;
					}).center();
				};
			}
		}

		/*
		* If anybody is reading this code, I'm sorry
		* I'm terribly sorry.
		*
		* You probably have no idea how this works. Don't worry... me neither.
		*
		* If hovering over only one box -> open boxs options
		* If hovering over more than one box -> open box list to select the box -> open boxs options
		*/
		if (e.getKey() == KeyList.O)
		{
			/*
			 * Iterate over all boxes and if cursor is over one add it to the list. Ignore invalid boxes!
			 */
			List<Box> b = new ArrayList<>();
			boxList.forEach(c -> { if (Util.isCursorInRectangle(main, c.x, c.y, c.w, c.h) && !c.invalid) b.add(c); });

			/*
			 * Don't do anything if there is no Box
			 */
			if (b.size() != 0)

			/*
			* This is here cuz I can't open new dialog from @Event
			* cuz you'd be adding to specialCase or whatever WHILE iterating over -> ConcurrentModificationException
			* Opens Dialog Window the next tick
			*/
			dialog = (m) ->
			{

				/*
				 * If there is only ONE box cursor is over open boxs options
				 */
				if (b.size() == 1)
				{
					BoxOptionsDialog bod = new BoxOptionsDialog(b.get(0), this, B -> inDialog = false);
					return main.showDialog(bod).center();
				}
				/*
				 * If not open box list
				 */
				else
				{

					BoxSelectDialog bsd = new BoxSelectDialog(b, this);
					/*
					 * Add Select Event
					 *
					 * Open Boxs options after clicking it's name
					 */
					return main.showDialog(bsd).addSelectEvent(c -> dialog = (M) ->
					{
						BoxOptionsDialog bod = new BoxOptionsDialog(b.get(c), this, B -> inDialog = false);
						return main.showDialog(bod).center();
					}).center();
				}
			};
		}
	}

	private void moveCursor(int dx, int dy)
	{
		GLFW.glfwSetCursorPos(main.getWindowId(), main.getMouseX() + dx, main.getMouseY() + dy);
	}

	public void tick()
	{
		if (dialog != null)
		{
			inDialog = true;
			dialog.apply(main);
			dialog = null;
		}

		if (main.isKeyPressed(KeyList.L_SHIFT))
		{
			if (main.isKeyPressed(KeyList.UP)) moveCursor(0, -1);
			if (main.isKeyPressed(KeyList.DOWN)) moveCursor(0, 1);
			if (main.isKeyPressed(KeyList.LEFT)) moveCursor(-1, 0);
			if (main.isKeyPressed(KeyList.RIGHT)) moveCursor(1, 0);
		}

		if (main.isKeyPressed(toggleKey) && !toggleFlag)
		{
			toggleFlag = true;
			toggled = !toggled;

			if (toggled)
			{
				main.getEventHandler().register(this);
				main.getEventHandler().addSpecialCaseObject(this);
			} else
			{
				main.getEventHandler().unregister(this);
				main.getEventHandler().removeSpecialCaseObject(this);
			}
		}

		if (!main.isKeyPressed(toggleKey) && toggleFlag)
		{
			toggleFlag = false;
		}

		boxList.forEach(c -> {
			if (c.invalid)
			{
				if (c.a > 0) c.a -= 0.01f;
			}
		});

		boxList.removeIf(next -> next.a <= 0);
	}

	public void render()
	{
		if (!toggled)
			return;

		int mx = main.getMouseX(), my = main.getMouseY();

		SpriteRender.manualStart();
		if (creatingNewBox) SpriteRender.fillRect(nbx, nby, mx - nbx, my - nby, nbr, nbg, nbb, 0.4f);
		boxList.forEach(c -> {if (c.render) SpriteRender.fillRect(c.x, c.y, c.w, c.h, c.r, c.g, c.b, c.a);});
		SpriteRender.manualEnd();

		boxList.forEach(c -> {if (c.render) Font.renderCustom(c.x + 3, c.y + 3, 1, c.name);});
		boxList.forEach(c -> {if (c.render) cr.render(c);});

		Font.renderCustom(mx, my - 10, 1, "[#FF6B68]", mx, "[#ffffff]", "/", "[#6Bff68]", my);
		if (creatingNewBox)
		{
			Font.renderCustom(mx, my + 20, 1, (mx - nbx <=0) ? "[#FF0000]" : "[#686BFF]", mx - nbx, "[#ffffff]", "/", (my - nby <=0) ? "[#FF0000]" : "[#FF6aFF]", my - nby);
		}
	}

	class Box
	{
		int x, y, w, h;
		float r, g, b, a;
		boolean invalid, render;
		String name;
		Component component;

		Box()
		{
			this.x = nbx;
			this.y = nby;
			this.w = main.getMouseX() - nbx;
			this.h = main.getMouseY() - nby;
			this.r = nbr;
			this.g = nbg;
			this.b = nbb;
			this.a = 0.2f;
			this.render = true;

			invalid = w <= 0 || h <= 0 || x < 0 || y < 0 || x > main.getWidth() || y > main.getHeight() || x + w > main.getWidth() || y + h > main.getHeight();
		}

		@Override
		public String toString()
		{
			return "Box{" + "name='" + name + '\'' + '}';
		}
	}
}














