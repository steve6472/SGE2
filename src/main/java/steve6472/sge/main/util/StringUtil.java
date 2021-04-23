package steve6472.sge.main.util;

import java.util.regex.Pattern;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/23/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class StringUtil
{
	private static final String TRUE = "true";
	private static final String FALSE = "false";

	public static final Pattern IS_INTEGER = Pattern.compile("([+-]?\\d)+");
	public static final Pattern IS_DECIMAL = Pattern.compile("([+-]?\\d*(\\.\\d+)?)+");

	public static boolean isInteger(String text)
	{
		return IS_INTEGER.matcher(text).matches();
	}

	public static boolean isDecimal(String text)
	{
		return IS_DECIMAL.matcher(text).matches();
	}

	public static boolean isBoolean(String text)
	{
		return text.equalsIgnoreCase(TRUE) || text.equalsIgnoreCase(FALSE);
	}
}
