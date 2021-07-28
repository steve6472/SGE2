package steve6472.sge.test.vox;

import steve6472.sge.gfx.game.blockbench.model.BBModel;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/8/2021
 * Project: StevesGameEngine
 *
 ***********************/
class Models
{
	public static final BBModel AIR = VoxRenderTest.models.addModel();
	public static final BBModel ROCK = addModel("game/props/block/rock");
	public static final BBModel DEBUG = addModel("game/props/block/debug");
	public static final BBModel GIZMO = addModel("game/props/block/gizmo");
	public static final BBModel OUTLINE = addModel("game/props/block/outline");
	public static final BBModel PISTON = addModel("game/props/block/piston_move");
	public static final int grass_side = VoxRenderTest.models.getAtlas().putTexture("props/blocks/grass_side");
	public static final int grass = VoxRenderTest.models.getAtlas().putTexture("props/blocks/grass");
	public static final int dirt = VoxRenderTest.models.getAtlas().putTexture("props/blocks/dirt");

	private static BBModel addModel(String path)
	{
		return VoxRenderTest.models.loadModel(path);
	}

	public static void init()
	{

	}
}
