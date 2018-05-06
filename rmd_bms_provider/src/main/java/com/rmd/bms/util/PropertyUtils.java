package com.rmd.bms.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
	private static Properties properties = null;
	
	//加载property文件到io流里面
	public static Properties loadProperties(String propertyFile) {
		try {
			InputStream is = PropertyUtils.class.getClassLoader().getResourceAsStream(propertyFile);
			properties = new Properties();
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	/**
	 * 根据key值取得对应的value值
	 * @param key
	 * @return
	 */
	public static String getValue(String propertyFile , String key) {
		properties = loadProperties(propertyFile);
		return properties.getProperty(key);
	}
}
