package steve6472.sge.gui.components.schemes;

import steve6472.sge.main.util.ColorUtil;
import steve6472.SSS;
import org.joml.Vector4f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.12.2018
 * Project: SGE2
 *
 ***********************/
public class SchemeBackground extends Scheme
{
	public Vector4f outsideBorder, insideBorder, fill;

	@Override
	public SchemeBackground load(String path)
	{
		SSS sss = SchemeLoader.load(path);

		this.outsideBorder = ColorUtil.getVector4Color(sss.getHexInt("outsideBorder"));
		this.insideBorder = ColorUtil.getVector4Color(sss.getHexInt("insideBorder"));
		this.fill = ColorUtil.getVector4Color(sss.getHexInt("fill"));

		return this;
	}

	public SchemeBackground()
	{
	}

	@SuppressWarnings("IncompleteCopyConstructor")
	public SchemeBackground(SchemeBackground other)
	{
		super(other);
		this.outsideBorder = new Vector4f(other.outsideBorder);
		this.insideBorder = new Vector4f(other.insideBorder);
		this.fill = new Vector4f(other.fill);
	}

	@Override
	public String getId()
	{
		return "background";
	}
}
