package steve6472.sge.main.game.registry;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

/**********************
 * Created by steve6472
 * On date: 7/21/2021
 * Project: StevesGameEngine
 *
 ***********************/
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, TYPE})
public @interface ObjectHolder
{
	String namespace();
	String id();
}
