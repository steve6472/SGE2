package steve6472.sge.main;

/**********************
 * Created by steve6472
 * On date: 17.04.2020
 * Project: NoiseGenerator
 *
 ***********************/
public class Log
{
	public static boolean PRINT_INFO = false;
	public static boolean PRINT_DEBUG = false;
	public static boolean PRINT_ERROR = true;

	public static final String BLACK = "\u001b[30m";
	public static final String RED = "\u001b[31m";
	public static final String GREEN = "\u001b[32m";
	public static final String YELLOW = "\u001b[33m";
	public static final String BLUE = "\u001b[34m";
	public static final String MAGENTA = "\u001b[35m";
	public static final String CYAN = "\u001b[36m";
	public static final String WHITE = "\u001b[37m";
	public static final String RESET = "\u001b[0m";

	public static final String BRIGHT_BLACK = "\u001b[30;1m";
	public static final String BRIGHT_RED = "\u001b[31;1m";
	public static final String BRIGHT_GREEN = "\u001b[32;1m";
	public static final String BRIGHT_YELLOW = "\u001b[33;1m";
	public static final String BRIGHT_BLUE = "\u001b[34;1m";
	public static final String BRIGHT_MAGENTA = "\u001b[35;1m";
	public static final String BRIGHT_CYAN = "\u001b[36;1m";
	public static final String BRIGHT_WHITE = "\u001b[37;1m";

	public static void info(String text)
	{
		if (PRINT_INFO)
			System.out.println(WHITE + text);
	}

	public static void debug(String text)
	{
		if (PRINT_DEBUG)
			System.out.println(BRIGHT_WHITE + text + WHITE);
	}

	public static void err(String text)
	{
		if (PRINT_ERROR)
			System.out.println(RED + text + WHITE);
	}
}
