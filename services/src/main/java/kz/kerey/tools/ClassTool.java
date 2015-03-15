package kz.kerey.tools;

import java.lang.reflect.Field;

public class ClassTool {

	public static Object getValuableProperty(Class<?> clazz, String propertyName, String value) {
		Class<?> type = null;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(propertyName)) {
				type = field.getType();
				break;
			}
		}
		
		if (type.equals(Integer.class)) {
			return Integer.valueOf(value);
		} 
		if (type.equals(Long.class)) {
			return Long.valueOf(value);
		}
		if (type.equals(String.class)) {
			return value;
		}
		if (type.equals(Boolean.class)) {
			return Boolean.valueOf(value);
		}
		
		return null;
	}
	
}
