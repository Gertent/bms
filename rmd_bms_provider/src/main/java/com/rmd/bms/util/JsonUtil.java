package com.rmd.bms.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * @Description JsonUtil
 * @author lc
 * @date 2016年7月13日 下午4:03:59
 */
public class JsonUtil {

	/**
	 * JSONObject转JavaBean
	 * 
	 * @param JSONObject
	 * @param Class
	 * @return Object
	 */
	public static Object jsonToBean(JSONObject json, Class cls) {
		Object obj = null;

		try {
			obj = cls.newInstance();

			// 获取class的字段
			Method[] methods = cls.getMethods();
			for (int i = 0; i < methods.length; i++) {
				// 获取字段名
				String methodName = methods[i].getName();
				// 获取类
				Class[] clss = methods[i].getParameterTypes();
				if (clss.length != 1) {
					continue;
				}
				if (methodName.indexOf("set") < 0) {
					continue;
				}
				String type = clss[0].getSimpleName();
				String key = methodName.substring(3, 4).toLowerCase()
						+ methodName.substring(4);
				if (json.has(key) && json.get(key) != null) {
					setValue(type, json.get(key), methods[i], obj);
				}
			}
		} catch (Exception ex) {
			// LogUtil.errorLog(JsonUtil.class, "JSONObjectתJavaBeanʧ��", ex);
		}

		return obj;
	}

	/**
	 * 根据key判断JSONObject值
	 * 
	 * @param json
	 * @param key
	 * @return
	 * @throws JSONException
	 */
	public static String getString(JSONObject json, String key)
			throws JSONException {
		String retVal = null;
		if (json.containsKey(key)) {
			retVal = "";
		} else {
			retVal = json.getString(key);
		}
		return retVal;
	}

	/**
	 * 获取JavaBean的值
	 * 
	 * @param type
	 * @param value
	 * @param method
	 * @param bean
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	private static void setValue(String type, Object value, Method method,
			Object bean) {
		if (value != null && !"".equals(value)) {
			try {
				if ("String".equals(type)) {
					method.invoke(bean, new Object[] { value });
				} else if ("int".equals(type) || "Integer".equals(type)) {
					method.invoke(bean,
							new Object[] { new Integer("" + value) });
				} else if ("double".equals(type) || "Double".equals(type)) {
					method.invoke(bean, new Object[] { new Double("" + value) });
				} else if ("float".equals(type) || "Float".equals(type)) {
					method.invoke(bean, new Object[] { new Float("" + value) });
				} else if ("long".equals(type) || "Long".equals(type)) {
					method.invoke(bean, new Object[] { new Long("" + value) });
				} else if ("int".equals(type) || "Integer".equals(type)) {
					method.invoke(bean,
							new Object[] { new Integer("" + value) });
				} else if ("boolean".equals(type) || "Boolean".equals(type)) {
					method.invoke(bean,
							new Object[] { Boolean.valueOf("" + value) });
				} else if ("BigDecimal".equals(type)) {
					method.invoke(bean, new Object[] { new BigDecimal(""
							+ value) });
				} else if ("Date".equals(type)) {
					Class dateType = method.getParameterTypes()[0];
					if ("java.util.Date".equals(dateType.getName())) {
						java.util.Date date = null;
						if ("String".equals(value.getClass().getSimpleName())) {
							String time = String.valueOf(value);
							String format = null;
							if (time.indexOf(":") > 0) {
								if (time.indexOf(":") == time.lastIndexOf(":")) {
									format = "yyyy-MM-dd H:mm";
								} else {
									format = "yyyy-MM-dd H:mm:ss";
								}
							} else {
								format = "yyyy-MM-dd";
							}
							SimpleDateFormat sf = new SimpleDateFormat();
							sf.applyPattern(format);
							date = sf.parse(time);
						} else {
							date = (java.util.Date) value;
						}

						if (date != null) {
							method.invoke(bean, new Object[] { date });
						}
					} else if ("java.sql.Date".equals(dateType.getName())) {
						Date date = null;
						if ("String".equals(value.getClass().getSimpleName())) {
							String time = String.valueOf(value);
							String format = null;
							if (time.indexOf(":") > 0) {
								if (time.indexOf(":") == time.lastIndexOf(":")) {
									format = "yyyy-MM-dd H:mm";
								} else {
									format = "yyyy-MM-dd H:mm:ss";
								}
							} else {
								format = "yyyy-MM-dd";
							}
							SimpleDateFormat sf = new SimpleDateFormat();
							sf.applyPattern(format);
							date = new Date(sf.parse(time).getTime());
						} else {
							date = (Date) value;
						}

						if (date != null) {
							method.invoke(bean, new Object[] { date });
						}
					}
				} else if ("Timestamp".equals(type)) {
					Timestamp timestamp = null;
					if ("String".equals(value.getClass().getSimpleName())) {
						String time = String.valueOf(value);
						String format = null;
						if (time.indexOf(":") > 0) {
							if (time.indexOf(":") == time.lastIndexOf(":")) {
								format = "yyyy-MM-dd H:mm";
							} else {
								format = "yyyy-MM-dd H:mm:ss";
							}
						} else {
							format = "yyyy-MM-dd";
						}
						SimpleDateFormat sf = new SimpleDateFormat();
						sf.applyPattern(format);
						timestamp = new Timestamp(sf.parse(time).getTime());
					} else {
						timestamp = (Timestamp) value;
					}

					if (timestamp != null) {
						method.invoke(bean, new Object[] { timestamp });
					}
				} else if ("byte[]".equals(type)) {
					method.invoke(bean,
							new Object[] { new String("" + value).getBytes() });
				}
			} catch (Exception ex) {
				// LogUtil.errorLog(JsonUtil.class,
				// "JSONObject��ֵ��JavaBeanʧ��", ex);
			}
		}
	}

	/** 
     * 
     */
	public static JSONObject covertObjectToJSON(Object o) throws Exception {
		JSONObject json = new JSONObject();
		Class<? extends Object> oClass = o.getClass();
		Field[] fields = oClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			json.put(f.getName(), invokeMethod(oClass, f.getName(), o));
		}
		return json;
	}

	public static JSONArray coverModelToJSONArray(List list) throws Exception {
		JSONArray array = null;
		if (list.isEmpty()) {
			return array;
		}
		array = new JSONArray();
		for (Object o : list) {
			array.add(covertObjectToJSON(o));
		}
		return array;
	}

	private static Object invokeMethod(Class<? extends Object> c,
			String fieldName, Object o) {
		String methodName = fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Method method = null;
		try {
			method = c.getMethod("get" + methodName);
			return method.invoke(o);
		} catch (Exception e) {
			// LogUtil.errorLog(e);
			return "";
		}
	}

}
