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
public class SchemeSlider extends Scheme
{
	public Vector4f buttonEnabledOutsideBorder;
	public Vector4f buttonEnabledInsideBorder;
	public Vector4f buttonEnabledFill;

	public Vector4f buttonDisabledOutsideBorder;
	public Vector4f buttonDisabledInsideBorder;
	public Vector4f buttonDisabledFill;

	public Vector4f buttonHoveredOutsideBorder;
	public Vector4f buttonHoveredInsideBorder;
	public Vector4f buttonHoveredFill;

	public Vector4f sliderOutsideBorder;
	public Vector4f sliderInsideBorder;
	public Vector4f sliderFill;

	@Override
	public SchemeSlider load(String path)
	{
		SSS sss = SchemeLoader.load(path);

		buttonEnabledOutsideBorder  = ColorUtil.getVector4Color(sss.getHexInt("buttonEnabledOutsideBorder"));
		buttonEnabledInsideBorder   = ColorUtil.getVector4Color(sss.getHexInt("buttonEnabledInsideBorder"));
		buttonEnabledFill           = ColorUtil.getVector4Color(sss.getHexInt("buttonEnabledFill"));

		buttonDisabledOutsideBorder = ColorUtil.getVector4Color(sss.getHexInt("buttonDisabledOutsideBorder"));
		buttonDisabledInsideBorder  = ColorUtil.getVector4Color(sss.getHexInt("buttonDisabledInsideBorder"));
		buttonDisabledFill          = ColorUtil.getVector4Color(sss.getHexInt("buttonDisabledFill"));

		buttonHoveredOutsideBorder  = ColorUtil.getVector4Color(sss.getHexInt("buttonHoveredOutsideBorder"));
		buttonHoveredInsideBorder   = ColorUtil.getVector4Color(sss.getHexInt("buttonHoveredInsideBorder"));
		buttonHoveredFill           = ColorUtil.getVector4Color(sss.getHexInt("buttonHoveredFill"));

		sliderOutsideBorder         = ColorUtil.getVector4Color(sss.getHexInt("sliderOutsideBorder"));
		sliderInsideBorder          = ColorUtil.getVector4Color(sss.getHexInt("sliderInsideBorder"));

		sliderFill                  = ColorUtil.getVector4Color(sss.getHexInt("sliderFill"));

		return this;
	}

	public SchemeSlider()
	{
	}

	@SuppressWarnings("IncompleteCopyConstructor")
	public SchemeSlider(SchemeSlider other)
	{
		super(other);
		this.buttonEnabledOutsideBorder = new Vector4f(other.buttonEnabledOutsideBorder);
		this.buttonEnabledInsideBorder = new Vector4f(other.buttonEnabledInsideBorder);
		this.buttonEnabledFill = new Vector4f(other.buttonEnabledFill);
		this.buttonDisabledOutsideBorder = new Vector4f(other.buttonDisabledOutsideBorder);
		this.buttonDisabledInsideBorder = new Vector4f(other.buttonDisabledInsideBorder);
		this.buttonDisabledFill = new Vector4f(other.buttonDisabledFill);
		this.buttonHoveredOutsideBorder = new Vector4f(other.buttonHoveredOutsideBorder);
		this.buttonHoveredInsideBorder = new Vector4f(other.buttonHoveredInsideBorder);
		this.buttonHoveredFill = new Vector4f(other.buttonHoveredFill);
		this.sliderOutsideBorder = new Vector4f(other.sliderOutsideBorder);
		this.sliderInsideBorder = new Vector4f(other.sliderInsideBorder);
		this.sliderFill = new Vector4f(other.sliderFill);
	}

	public String getId()
	{
		return "slider";
	}
}
