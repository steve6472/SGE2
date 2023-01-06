package steve6472.sge.gfx.shaders;

import steve6472.sge.main.MainApp;

import java.io.*;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

/**********************
 * 
 * Source copied and modified from https://www.youtube.com/watch?v=q_dS3JuoeDw
 * 
 * @author ElegantWhelp (I guess)
 *
 * Created by steve6472
 * On date: 25.11.2018
 * Project: SJP
 *
 ***********************/
public class GeometryShader extends AbstractShader
{
	private int program;
	private int vs;
	private int gs;
	private int fs;

	private GeometryShader() {}

	private static InputStream newStream(String path)
	{
		InputStream stream = MainApp.class.getResourceAsStream(path);
		if (stream == null)
		{
			throw new NullPointerException("'" + path + "' not found");
		}
		return stream;
	}

	public static GeometryShader fromShaders(String path)
	{
		return fromResource(
			newStream("/shaders/" + path + ".vs"),
			newStream("/shaders/" + path + ".gs"),
			newStream("/shaders/" + path + ".fs"));
	}

	public static GeometryShader fromFile(String path)
	{
		return fromFile(new File(path));
	}

	public static GeometryShader fromFile(String pathVs, String pathGs, String pathFs)
	{
		return fromFile(new File(pathVs), new File(pathGs), new File(pathFs));
	}

	public static GeometryShader fromFile(File path)
	{
		return fromFile(new File(path.getPath() + ".vs"), new File(path.getPath() + ".gs"), new File(path.getPath() + ".fs"));
	}

	public static GeometryShader fromFile(File pathVs, File pathGs, File pathFs)
	{
		GeometryShader shader = new GeometryShader();
		shader.setupIds();
		try
		{
			shader.setupVertexShader(readFile(pathVs));
			shader.setupGeometryShader(readFile(pathGs));
			shader.setupFragmentShader(readFile(pathFs));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		shader.setupProgram();

		return shader;
	}

	public static GeometryShader fromResource(String path)
	{
		return fromResource(path + ".vs", path + ".gs", path + ".fs");
	}

	public static GeometryShader fromResource(String pathVs, String pathGs, String pathFs)
	{
		GeometryShader shader = new GeometryShader();
		shader.setupIds();
		try
		{
			shader.setupVertexShader(readResource(pathVs));
			shader.setupGeometryShader(readResource(pathGs));
			shader.setupFragmentShader(readResource(pathFs));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		shader.setupProgram();

		return shader;
	}

	public static GeometryShader fromResource(InputStream streamVs, InputStream streamGs, InputStream streamFs)
	{
		GeometryShader shader = new GeometryShader();
		shader.setupIds();
		try
		{
			shader.setupVertexShader(readResource(streamVs));
			shader.setupGeometryShader(readResource(streamGs));
			shader.setupFragmentShader(readResource(streamFs));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		shader.setupProgram();

		return shader;
	}

	/**
	 * Generates ids for VS, FS and Program
	 */
	protected void setupIds()
	{
		program = glCreateProgram();
		vs = glCreateShader(GL_VERTEX_SHADER);
		gs = glCreateShader(GL_GEOMETRY_SHADER);
		fs = glCreateShader(GL_FRAGMENT_SHADER);
	}

	/**
	 * Compiles and checks for errors
	 * @param source string with shader code
	 */
	protected void setupFragmentShader(String source)
	{
		glShaderSource(fs, source);

		glCompileShader(fs);

		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1)
		{
			System.err.println("FS:\n" + glGetShaderInfoLog(fs));
			throw new ExceptionInInitializerError();
		}
	}

	/**
	 * Compiles and checks for errors
	 * @param source string with shader code
	 */
	protected void setupGeometryShader(String source)
	{
		glShaderSource(gs, source);

		glCompileShader(gs);

		if (glGetShaderi(gs, GL_COMPILE_STATUS) != 1)
		{
			System.err.println("GS:\n" + glGetShaderInfoLog(gs));
			throw new ExceptionInInitializerError();
		}
	}

	/**
	 * Compiles and checks for errors
	 * @param source string with shader code
	 */
	protected void setupVertexShader(String source)
	{
		glShaderSource(vs, source);

		glCompileShader(vs);

		if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1)
		{
			System.err.println("VS:\n" + glGetShaderInfoLog(vs));
			throw new ExceptionInInitializerError();
		}
	}

	/**
	 * Attaches, links and validates program
	 */
	protected void setupProgram()
	{
		glAttachShader(program, vs);
		glAttachShader(program, gs);
		glAttachShader(program, fs);

		glLinkProgram(program);

		if (glGetProgrami(program, GL_LINK_STATUS) != 1)
		{
			System.err.println("Program link status:\n" + glGetProgramInfoLog(program));
			throw new ExceptionInInitializerError();
		}

		glValidateProgram(program);

		if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1)
		{
			System.err.println("Program validate status:\n" + glGetProgramInfoLog(program));
			throw new ExceptionInInitializerError();
		}
	}

	public int getProgram()
	{
		return program;
	}

	public void bind()
	{
		glUseProgram(program);
	}
	
	public static void releaseShader()
	{
		glUseProgram(0);
	}

	public static void bindShader(int id)
	{
		glUseProgram(id);
	}

	private static String readResource(String file) throws IOException
	{
		InputStream stream = MainApp.class.getResourceAsStream(file);
		return read(new BufferedReader(new InputStreamReader(stream)));
	}

	private static String readResource(InputStream stream) throws IOException
	{
		return read(new BufferedReader(new InputStreamReader(stream)));
	}

	private static String readFile(File file) throws IOException
	{
		return read(new BufferedReader(new FileReader(file)));
	}

	private static String readFile(String file) throws IOException
	{
		return readFile(new File(file));
	}

	private static String read(BufferedReader reader) throws IOException
	{
		StringBuilder stringer = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null)
		{
			stringer.append(line).append("\n");
		}

		return stringer.toString();
	}
}
