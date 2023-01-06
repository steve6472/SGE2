package steve6472.sge.gui.components.schemes;

/**********************
 * Created by steve6472
 * On date: 14.12.2018
 * Project: SGE2
 *
 ***********************/
public abstract class Scheme
{
	public abstract Scheme load(String path);
	public abstract String getId();

	public Scheme()
	{

	}

	/**
	 * Copy constructor
	 * @param other source of variables
	 */
	public Scheme(Scheme other)
	{

	}
}
