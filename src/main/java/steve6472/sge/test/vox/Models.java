package steve6472.sge.test.vox;

import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.blockbench.model.OutlinerElement;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/8/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Models
{
	public static final BBModel AIR = VoxRenderTest.models.addModel(new BBModel(new OutlinerElement[] {}));
	public static final BBModel ROCK = addModel("game/props/rock");
	public static final BBModel DEBUG = addModel("game/props/debug");
	public static final BBModel GIZMO = addModel("game/props/gizmo");

	private static BBModel addModel(String path)
	{
		return VoxRenderTest.models.addModel(path);
	}

	public static void init()
	{

	}
}
