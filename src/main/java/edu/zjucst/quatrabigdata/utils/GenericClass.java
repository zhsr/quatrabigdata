package edu.zjucst.quatrabigdata.utils;

import java.lang.reflect.ParameterizedType;

public class GenericClass {
	/**
	 * 获取父类的泛型类型
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class getGenericClass(Class clazz) {
		ParameterizedType type = (ParameterizedType) clazz
				.getGenericSuperclass();
		Class entityClass = (Class) type.getActualTypeArguments()[0];

		return entityClass;
	}
}
