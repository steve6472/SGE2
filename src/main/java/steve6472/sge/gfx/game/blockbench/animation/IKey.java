package steve6472.sge.gfx.game.blockbench.animation;

/**********************
 * Created by steve6472
 * On date: 25.10.2020
 * Project: CaveGame
 *
 ***********************/
public interface IKey
{
	double time();
	float x(double time);
	float y(double time);
	float z(double time);
	EnumKeyType keyType();
}
