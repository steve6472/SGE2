package steve6472.sge.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 05.02.2020
 * Project: SJP
 *
 ***********************/
public enum ResParam
{
	/**
	 * GL_TEXTURE_MIN_FILTER - GL_NEAREST
	 */
	MIN_N(GL_TEXTURE_MIN_FILTER, GL_NEAREST),
	/**
	 * GL_TEXTURE_MIN_FILTER - GL_LINEAR
	 */
	MIN_L(GL_TEXTURE_MIN_FILTER, GL_LINEAR),
	/**
	 * GL_TEXTURE_MIN_FILTER - GL_NEAREST_MIPMAP_NEAREST
	 */
	MIN_MIP_NN(GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST),
	/**
	 * GL_TEXTURE_MIN_FILTER - GL_LINEAR_MIPMAP_NEAREST
	 */
	MIN_MIP_LN(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_NEAREST),
	/**
	 * GL_TEXTURE_MIN_FILTER - GL_NEAREST_MIPMAP_LINEAR
	 */
	MIN_MIP_NL(GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR),
	/**
	 * GL_TEXTURE_MIN_FILTER - GL_LINEAR_MIPMAP_LINEAR
	 */
	MIN_MIP_LL(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR),


	/**
	 * GL_TEXTURE_MAG_FILTER - GL_NEAREST
	 */
	MAG_N(GL_TEXTURE_MAG_FILTER, GL_NEAREST),
	/**
	 * GL_TEXTURE_MAG_FILTER - GL_LINEAR
	 */
	MAG_L(GL_TEXTURE_MAG_FILTER, GL_LINEAR),


	/**
	 * GL_TEXTURE_WRAP_S - GL_CLAMP_TO_EDGE
	 */
	WRAP_S_EDGE(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE),

	/**
	 * GL_TEXTURE_WRAP_T - GL_CLAMP_TO_EDGE
	 */
	WRAP_T_EDGE(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE),
	/**
	 * GL_TEXTURE_WRAP_R - GL_CLAMP_TO_EDGE
	 */
	WRAP_R_EDGE(GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);



	private final int pname;
	private final int param;

	ResParam(int pname, int param)
	{
		this.pname = pname;
		this.param = param;
	}

	public int getParam()
	{
		return param;
	}

	public int getPname()
	{
		return pname;
	}
}
