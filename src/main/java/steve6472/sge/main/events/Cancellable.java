package steve6472.sge.main.events;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/21/2021
 * Project: StevesGameEngine
 *
 ***********************/
public interface Cancellable
{
	default void cancel()
	{
		setCancelled(true);
	}

	void setCancelled(boolean cancelled);

	boolean isCancelled();
}
