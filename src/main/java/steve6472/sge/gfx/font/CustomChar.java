package steve6472.sge.gfx.font;

public enum CustomChar
{
	ERROR(8),
	ARROW_UP_NOSHAFT(8), ARROW_DOWN_NOSHAFT(8),
	BOX_PLUS(8), BOX_MINUS(8),
	FULL_TRIANGLE(8),
	BIN_ICON(8),
	FOLDER_ICON(8),
	BACK_ARROW(8),
	FORWARD_ARROW(8),
	EYE_ICON(8),
	POINT(8),
	LINE(8),
	OUTLINE_TRIANGLE(8),
	EXIT_ICON(8),
	UNSELECTED_BOX(8),
	SELECTED_BOX(8),
	UNSELECTED_CIRCLE(8),
	SELECTED_CIRCLE(8),
	SQUARE(8),
	CHECK(8),
	CROSS(8),
	SAVE_ICON(8),
	TEXT_FOLDER_ICON(8),
	UNKNOWN_FILE(7),
	NBT_FILE(8),
	BAT_FILE(8),
	ARROW_DOWN(8), ARROW_UP(8),
	ARROW_BOTTOM_RIGHT(8), ARROW_BOTTOM_LEFT(8), ARROW_TOP_LEFT(8), ARROW_TOP_RIGHT(8),
	ARROW_LEFT_NOSHAFT(8), ARROW_RIGHT_NOSHAFT(8),
	SGA_THIN_Z(7), SGA_THIN_Y(5), SGA_THIN_X(7), SGA_THIN_W(7), SGA_THIN_V(7), SGA_THIN_U(7), SGA_THIN_T(7), SGA_THIN_S(4),
	SGA_THIN_R(6), SGA_THIN_Q(7), SGA_THIN_P(5), SGA_THIN_O(6), SGA_THIN_N(6), SGA_THIN_M(7), SGA_THIN_L(5), SGA_THIN_K(7),
	SGA_THIN_J(3), SGA_THIN_I(3), SGA_THIN_H(7), SGA_THIN_G(5), SGA_THIN_F(7), SGA_THIN_E(7), SGA_THIN_D(7), SGA_THIN_C(4),
	SGA_THIN_B(7), SGA_THIN_A(7),
	SGA_Z(8), SGA_Y(6), SGA_X(8), SGA_W(8), SGA_V(8), SGA_U(8), SGA_T(8), SGA_S(5), SGA_R(7), SGA_Q(8),
	SGA_P(6), SGA_O(7), SGA_N(7), SGA_M(8), SGA_L(6), SGA_K(8), SGA_J(4), SGA_I(4), SGA_H(8), SGA_G(6),
	SGA_F(8), SGA_E(8), SGA_D(8), SGA_C(5), SGA_B(8), SGA_A(8),
	SINGLE_FUCKING_LINE(1),
	FULL_BOX(8), FULL_CIRCLE(8),
	FULL_BOX_FADE(8), FULL_CIRCLE_FADE(8),
	RIGHT_TRIANGLE_FULL(8), RIGHT_TRIANGLE(8), RIGHT_TRIANGLE_FADE(8),






	;

	private static final CustomChar[] values = values();

	public static CustomChar[] getValues()
	{
		return values;
	}

	int index;
	int width;

	CustomChar(int width)
	{
		this.index = --Font.lastCustomIndex;
		this.width = width;
	}

	public int getIndex()
	{
		return index;
	}

	public int getWidth()
	{
		return width;
	}

	@Override
	public String toString()
	{
		return "[c" + ordinal() + "]";
	}
}