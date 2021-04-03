/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 25. 12. 2017
* Project: SGE
*
***********************/

package steve6472.sge.main.util;

public enum DataType
{
	BOOLEAN(Boolean.class),
	BYTE(Byte.class),
	CHAR(Character.class),
	SHORT(Short.class),
	INT(Integer.class),
	LONG(Long.class),
	FLOAT(Float.class),
	DOUBLE(Double.class),
	STRING(String.class),
	STRINGARRAY(String[].class),
	INTARRAY(int[].class),
	INTARRAY2D(int[][].class),
	INTARRAY3D(int[][][].class),
	SHORTARRAY(short[].class),
	SHORTARRAY2D(short[][].class),
	SHORTARRAY3D(short[][][].class),
	OBJECTARRAY(Object[].class),
	TAG_START(null),
	TAG_END(null);

	protected Class<?> clazz;

	DataType(Class<?> clazz)
	{
		this.clazz = clazz;
	}
	
	public static Object getObject(DataType type, String value)
	{
		return switch (type)
		{
			case BOOLEAN -> Boolean.valueOf(value);
			case BYTE -> Byte.valueOf(value);
			case CHAR -> value.charAt(0);
			case DOUBLE -> Double.valueOf(value);
			case FLOAT -> Float.valueOf(value);
			case INT -> Integer.valueOf(value);
			case LONG -> Long.valueOf(value);
			case SHORT -> Short.valueOf(value);
			case STRING -> value;
			case STRINGARRAY, INTARRAY, OBJECTARRAY, INTARRAY2D, INTARRAY3D, SHORTARRAY -> "---Not supported---";
			default -> "---This should not happen---";
		};
	}

	public static DataType getDataType(Object o)
	{
		if (o instanceof Boolean) return BOOLEAN;
		if (o instanceof Byte) return BYTE;
		if (o instanceof Character) return CHAR;
		if (o instanceof Short) return SHORT;
		if (o instanceof Integer) return INT;
		if (o instanceof Long) return LONG;
		if (o instanceof Float) return FLOAT;
		if (o instanceof Double) return DOUBLE;
		if (o instanceof String) return STRING;
		if (o instanceof String[]) return STRINGARRAY;
		if (o instanceof Integer[] || o instanceof int[]) return INTARRAY;
		if (o instanceof Integer[][] || o instanceof int[][]) return INTARRAY2D;
		if (o instanceof Integer[][][] || o instanceof int[][][]) return INTARRAY3D;
		if (o instanceof Short[] || o instanceof short[]) return SHORTARRAY;
		if (o instanceof Short[][] || o instanceof short[][]) return SHORTARRAY2D;
		if (o instanceof Short[][][] || o instanceof short[][][]) return SHORTARRAY3D;
		if (o instanceof Object[]) return OBJECTARRAY;
		return null;
	}
}
