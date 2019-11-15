/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 25. 12. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.main.game;

import com.steve6472.sge.main.Util;

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
	HEX(null),
	STRINGARRAY(String[].class),
	INTARRAY(int[].class),
	NULL(null),
	OBJECTARRAY(Object[].class),
	INTARRAY2D(int[][].class),
	INTARRAY3D(int[][][].class),
	SHORTARRAY(short[].class);

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
			case HEX -> Util.getIntFromHex(value);
			case INT -> Integer.valueOf(value);
			case LONG -> Long.valueOf(value);
			case SHORT -> Short.valueOf(value);
			case STRING -> value;
			case NULL -> null;
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
		if (o instanceof Object[]) return OBJECTARRAY;
		return NULL;
	}
}
