package steve6472.sge.main.util;

import org.json.JSONObject;
import steve6472.sge.main.Log;

import java.io.*;

/**********************
 * Created by steve6472
 * On date: 4/20/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class JSONUtil
{
	public static JSONObject readJSON(File file)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			StringBuilder builder = new StringBuilder();
			while ((line = reader.readLine()) != null)
			{
				builder.append(line);
			}
			reader.close();

			return new JSONObject(builder.toString());

		} catch (IOException e)
		{
			e.printStackTrace();
			return new JSONObject();
		}
	}

	public static void writeJSON(File file, JSONObject json)
	{
		try
		{
			if (file.createNewFile())
			{
				Log.info("Successfully (re-)created file");
			}


			FileWriter writer = new FileWriter(file);
			writer.write(prettify(json));
//			json.write(writer, 4, 4);
			writer.flush();
			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String prettify(JSONObject json)
	{
		if (json == null)
			return null;

		byte i = 0;

		StringBuilder sb = new StringBuilder();

		boolean inString = false;
		boolean isValidArray = false;

		String formattedJSON = json.toString(4);
		//		System.out.println(formattedJSON);

		int index = 0;
		for (char c : formattedJSON.toCharArray())
		{
			if (c == '"' && i == 0) i++;
			if (c == ':' && i == 1) i++;
			if (c == '[' && i == 2) i++;

			// Check if array is not object array
			if (i == 3 && !isValidArray)
			{
				boolean inStr = false;
				//				StringBuilder test = new StringBuilder();
				for (int j = index; j < formattedJSON.length(); j++)
				{
					char ch = formattedJSON.charAt(j);
					//					test.append(ch);
					if (ch == '"')
						inStr = !inStr;
					if (inStr)
						continue;
					if (ch == ' ' || ch == '\n')
						continue;
					if (ch == '{' || ch == '}')
					{
						i = 0;
						break;
					}
					if (ch == ']')
					{
						isValidArray = true;
						break;
					}
				}
				//				System.out.println("Test: \n'" + test + "\n', isValid: " + isValidArray);
			}

			if (i == 3)
			{
				if (c == '"')
					inString = !inString;

				if (!inString)
				{
					if (c != ' ' && c != '\n')
						sb.append(c);
					if (c == ']')
					{
						i = 0;
						isValidArray = false;
					}
					if (c == ',')
					{
						sb.append(' ');
					}
				} else
				{
					sb.append(c);
				}
			} else
			{
				sb.append(c);
			}

			index++;
		}

		return sb.toString();
	}
}
