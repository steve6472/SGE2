/**********************
* Created by steve6472
* On date: 13. 3. 2018
* Project: SGE2
*
***********************/

package steve6472.sge.gfx.shaders;

import steve6472.sge.main.MainApp;

import java.io.*;

import static org.lwjgl.opengl.GL20.*;

/**
 * 
 * Source copied and modified from https://www.youtube.com/watch?v=q_dS3JuoeDw
 * 
 * @author ElegantWhelp (I guess)
 *
 */
public class Shader extends AbstractShader
{
	private int program;
	private int vs;
	private int fs;

	private Shader() {}

	private static InputStream newStream(String path)
	{
		InputStream stream = MainApp.class.getResourceAsStream(path);
		if (stream == null)
		{
			throw new NullPointerException("'" + path + "' not found");
		}
		return stream;
	}

	public static Shader fromShaders(String path)
	{
		return fromResource(
			newStream("/shaders/" + path + ".vs"),
			newStream("/shaders/" + path + ".fs"));
	}

	public static Shader fromFile(String path)
	{
		return fromFile(new File(path));
	}

	public static Shader fromFile(String pathVs, String pathFs)
	{
		return fromFile(new File(pathVs), new File(pathFs));
	}

	public static Shader fromFile(File path)
	{
		return fromFile(new File(path.getPath() + ".vs"), new File(path.getPath() + ".fs"));
	}

	public static Shader fromFile(File pathVs, File pathFs)
	{
		try
		{
			return fromSource(readFile(pathVs), readFile(pathFs));
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static Shader fromSource(String vertexShader, String fragmentShader)
	{
		Shader shader = new Shader();
		shader.setupIds();
		shader.setupVertexShader(vertexShader);
		shader.setupFragmentShader(fragmentShader);
		shader.setupProgram();

		return shader;
	}

	public static Shader fromResource(String path)
	{
		return fromResource(path + ".vs", path + ".fs");
	}

	public static Shader fromResource(String pathVs, String pathFs)
	{
		Shader shader = new Shader();
		shader.setupIds();
		try
		{
			shader.setupVertexShader(readResource(pathVs));
			shader.setupFragmentShader(readResource(pathFs));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		shader.setupProgram();

		return shader;
	}

	public static Shader fromResource(InputStream streamVs, InputStream streamFs)
	{
		Shader shader = new Shader();
		shader.setupIds();
		try
		{
			shader.setupVertexShader(readResource(streamVs));
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

	/**
	 * Doesn't throw any exceptions
	 * Should not be used
	 */
	@Deprecated
	public void updateShader(String fileName)
	{
		try
		{
			setupVertexShader(readFile(fileName + ".vs"));
			setupFragmentShader(readFile(fileName + ".fs"));
		}
		catch (ExceptionInInitializerError | IOException ignored) { }

		glLinkProgram(program);

		if (glGetProgrami(program, GL_LINK_STATUS) != 1)
		{
			System.err.println(glGetProgramInfoLog(program));
		}

		glValidateProgram(program);

		if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1)
		{
			System.err.println(glGetProgramInfoLog(program));
		}
	}

	@Deprecated(forRemoval = true)
	public Shader(String VS, String FS)
	{
		program = glCreateProgram();
		
		vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, VS);
		
		glCompileShader(vs);
		
		if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1)
		{
			System.err.println("VS:\n" + glGetShaderInfoLog(vs));
			throw new ExceptionInInitializerError();
		}
		
		
		fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, FS);
		
		glCompileShader(fs);
		
		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1)
		{
			System.err.println("FS:\n" + glGetShaderInfoLog(fs));
			throw new ExceptionInInitializerError();
		}

		glAttachShader(program, vs);
		glAttachShader(program, fs);

		glBindAttribLocation(program, 0, "vertices");
		glBindAttribLocation(program, 1, "textures");
		
		glLinkProgram(program);
		
		if (glGetProgrami(program, GL_LINK_STATUS) != 1)
		{
			System.err.println(glGetProgramInfoLog(program));
			throw new ExceptionInInitializerError();
		}
		
		glValidateProgram(program);
		
		if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1)
		{
			System.err.println(glGetProgramInfoLog(program));
			throw new ExceptionInInitializerError();
		}
	}

	public int getProgram()
	{
		return program;
	}

	@Deprecated(forRemoval = true)
	public Shader setUniform1i(String name, int v1)
	{
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform1i(location, v1);
		return this;
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
		return read(new BufferedReader(new InputStreamReader(newStream(file))));
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
