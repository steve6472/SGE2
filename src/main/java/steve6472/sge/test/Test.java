/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 2. 2018
* Project: LWJGL
*
***********************/

package steve6472.sge.test;

import steve6472.sge.main.KeyList;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.MainFlags;
import steve6472.sge.main.Window;
import steve6472.sge.main.events.Event;
import steve6472.sge.main.events.KeyEvent;

public class Test extends MainApp
{
	public static boolean flag = true;

	@Override
	public void init()
	{
		new TestGui(this);
//		gameTest = new GameTest(this);
//		getEventHandler().register(gameTest);

//		new ScriptTest().test();
//		exit();

//		String t = "X vs " + CustomChar.X + " Triangle: " + CustomChar.TRIANGLE;
//		System.out.println(t);

		setExitKey(KeyList.ESCAPE);
	}

	@Override
	public void tick()
	{
		tickGui();
		tickDialogs();
	}
	
	@Override
	public void render()
	{
		renderGui();
		renderDialogs();
	}

	@Event
	public void screenshot(KeyEvent event)
	{
		if (event.getAction() == KeyList.PRESS && event.getKey() == KeyList.F2)
			takeScreenshot();

		if (event.getAction() == KeyList.PRESS && event.getKey() == KeyList.F1)
			flag = !flag;
	}

	@Override
	public void setWindowHints()
	{
//		Window.setFloating(true);
		Window.setResizable(true);
	}

	@Override
	public int getWindowWidth()
	{
		return 16 * 60;
	}

	@Override
	public int getWindowHeight()
	{
		return 9 * 60;
	}
	
	@Override
	public String getTitle()
	{
		return "LWJGL";
//		return "Homing Pigs";
	}
	
	@Override
	public void exit()
	{
		getWindow().close();
	}
	
	public static void main(String[] args)
	{
		new Test();
	}

	@Override
	protected int[] getFlags()
	{
		return new int[] {
				MainFlags.ENABLE_EXIT_KEY,
//				MainFlags.ADD_BASIC_ORTHO
		};
	}
}
