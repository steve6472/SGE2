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
	BOOLEAN(Boolean.class), BYTE(Byte.class), CHAR(Character.class), SHORT(Short.class), INT(Integer.class), LONG(Long.class), FLOAT(
			Float.class), DOUBLE(Double.class), STRING(String.class), HEX(null), STRINGARRAY(String[].class);

	protected Class<?> clazz;

	DataType(Class<?> clazz)
	{
		this.clazz = clazz;
	}
	
	public static Object getObject(DataType type, String value)
	{
		switch (type)
		{
		case BOOLEAN:
			return new Boolean(value);
		case BYTE:
			return new Byte(value);
		case CHAR:
			value.charAt(0);
		case DOUBLE:
			return new Double(value);
		case FLOAT:
			return new Float(value);
		case HEX:
			return Util.getIntFromHex(value);
		case INT:
			return new Integer(value);
		case LONG:
			return new Long(value);
		case SHORT:
			return new Short(value);
		case STRING:
			return new String(value);
		case STRINGARRAY:
			return "---Not supported---";
		default:
			return "---This should not happen---";
		
		}
	}
}
