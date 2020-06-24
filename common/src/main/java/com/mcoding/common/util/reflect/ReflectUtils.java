package com.mcoding.common.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * 反射工具类
 * @author hzy
 *
 */
public abstract class ReflectUtils {
	
    /**
     * 获取对象的属性值
     * @param instance
     * @param propertyName
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static Class<?> getFieldType(Object instance, String propertyName){
    	if (instance == null) {
			throw new IllegalArgumentException("instance doesn't accept null");
		}
    	
    	if (StringUtils.isBlank(propertyName)) {
    		throw new IllegalArgumentException("propertyName doesn't accept null");
    	}
    	
    	propertyName = propertyName.trim();
    	if (instance instanceof Map) {
    		Map map = (Map) instance;
    		if (map.containsKey(propertyName)) {
    			Object value = map.get(propertyName);
    			if (value == null) {
					return null;
				}
    			
    			return value.getClass();
    		}
    	}
    	
    	return FieldUtils.getField(instance.getClass(), propertyName).getClass();
//    	MetaObject reflectObject = MetaObject.forObject(instance, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
//    	return reflectObject.getValue(propertyName);
    }
    
	/**
	 * 获取对象的属性值
	 * @param instance
	 * @param propertyName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getValue(Object instance, String propertyName){
		if (StringUtils.isBlank(propertyName)) {
			throw new IllegalArgumentException("can not accept null");
		}
		
		propertyName = propertyName.trim();
		while (propertyName.contains(".")) {
			String currentPropertyName = propertyName.split("\\.")[0];
			propertyName = propertyName.replaceAll(currentPropertyName + "\\.", "");
			instance = getPropertyValue(instance, currentPropertyName);
		}
		
		return getPropertyValue(instance, propertyName);
		
//		if (propertyName.contains(".")) {
//			
//			return getValue(instance, propertyName);
//		}else{
//			return getPropertyValue(instance, propertyName);
//		}
	}
	
	private static Object getPropertyValue(Object instance, String propertyName){
		if (StringUtils.isBlank(propertyName)) {
			throw new IllegalArgumentException("can not accept null");
		}
		
		propertyName = propertyName.trim();
		if (instance instanceof Map) {
			Map map = (Map) instance;
			if (map.containsKey(propertyName)) {
				return map.get(propertyName);
			}else {
				throw new IllegalArgumentException("找不到属性:"+propertyName);
			}
		}
		
		String methodName = "get" + StringUtils.capitalize(propertyName.trim());
		try {
			return invokeMethod(instance, methodName);
		} catch (IllegalAccessException |NoSuchMethodException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	/**
	 * 设置对象的属性值
	 * @param instance
	 * @param propertyName
	 * @param value
	 */
	public static void setValue(Object instance, String propertyName, Object value){
		if (StringUtils.isBlank(propertyName)) {
			throw new IllegalArgumentException("can not accept null");
		}
		
		propertyName = propertyName.trim();
		if (instance instanceof Map) {
			Map map = (Map) instance;
			map.put(propertyName, value);
			return;
		}
		
		String methodName = "set" + StringUtils.capitalize(propertyName.trim());
		try {
			invokeMethod(instance, methodName, value);
		} catch (IllegalAccessException |NoSuchMethodException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 调用对象的方法
	 * @param instance
	 * @param methodName
	 * @param objects
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 */
	public static Object invokeMethod(Object instance, String methodName, Object...objects) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		if (instance == null) {
			throw new NullPointerException("instance 为空");
		}
		if (StringUtils.isBlank(methodName)) {
			throw new NullPointerException("methodName 为空");
		}
		
		Method matchMethod = findMethod(instance, methodName, objects);
		matchMethod.setAccessible(true);
		return matchMethod.invoke(instance, objects);
	}

	private static Method findMethod(Object instance, String methodName, Object[] objects) {
		// 所有public方法，父类或接口，或自身的
		List<Method> allPublicMethods = Arrays.asList(instance.getClass().getMethods());
		
		// 所有自身的方法，包括 public private..
		List<Method> allDeclaredMethods = new ArrayList<>();
		CollectionUtils.addAll(allDeclaredMethods, instance.getClass().getDeclaredMethods());
		
		// 排除自身的方法，剩下父类或接口的public
		List<Method> allFooClassPublicMethods = allPublicMethods.stream().filter(method->{
			return allDeclaredMethods.stream().noneMatch(declareMethod-> isSameMethod(declareMethod, method));
		}).collect(Collectors.toList());
		
		CollectionUtils.addAll(allDeclaredMethods, allFooClassPublicMethods.iterator());
		
		List<Method> methodList = allDeclaredMethods.stream()
			.filter(method-> method.getName().equals(methodName.trim()))
			.filter(method-> {
				if (objects == null) {
					return method.getParameters().length == 0;
				}
				
				return objects.length == method.getParameters().length;
			})
			.collect(Collectors.toList());
	
		if (CollectionUtils.isEmpty(methodList)) {
			throw new IllegalArgumentException("找不到该方法:"+methodName);
		}
		
		Method matchMethod = null;
		if (methodList.size() > 1) {
			matchMethod = findMethod(methodList, objects); 
		}else {
			matchMethod = methodList.get(0);
		}
		
		if (matchMethod == null) {
			throw new IllegalArgumentException("找不到该方法");
		}
		
		return matchMethod;
	}

	private static boolean isSameMethod(Method declareMethod, Method method) {
		if (!method.getName().equals(declareMethod.getName())) {
			return false;
		}
		
		if (method.getParameters().length != declareMethod.getParameters().length) {
			return false;
		}
		
		for (int i = 0; i < method.getParameters().length; i++) {
			if (!method.getParameterTypes()[i].equals(declareMethod.getParameterTypes()[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据传参的类型，找方法
	 * @param methodList
	 * @param objects
	 * @return
	 */
	private static Method findMethod(List<Method> methodList, Object[] objects) {
		for (Method method : methodList) {
			boolean isMatch = true;
			
			for (int i = 0; i < method.getParameters().length; i++) {
				if (objects[i] == null) {
					throw new IllegalArgumentException("方法出现重载，且参数传值为null，无法识别合适的方法");
				}
				
				if (!method.getParameterTypes()[i].equals(objects[i].getClass())) {
					isMatch = false;
					break;
				}
			}
			
			if (isMatch) {
				return method;
			}
		}
		return null;
	}

}
