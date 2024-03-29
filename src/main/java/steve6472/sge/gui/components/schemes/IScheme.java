package steve6472.sge.gui.components.schemes;

/**********************
 * Created by steve6472
 * On date: 01.11.2019
 * Project: SJP
 *
 ***********************/
public interface IScheme<T extends Scheme>
{
	T getScheme();

	void setScheme(T scheme);
}
