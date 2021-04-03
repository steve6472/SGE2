package steve6472;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class Util
{
	public static String getFormatedTime()
	{
		return String.format("%tY.%tm.%te-%tH.%tM.%tS", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance());
	}

	public static String[] readFile(String path)
	{
		File f = new File(path);
		
		List<String> lines = new ArrayList<>();
		
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(f));

			boolean endOfTheFile = false;
			while (!endOfTheFile)
			{
				String line = br.readLine();

				if (line == null)
				{
					endOfTheFile = true;
				} else 
				{
					lines.add(line);
				}
			}

			br.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return lines.toArray(new String[0]);
	}
}
