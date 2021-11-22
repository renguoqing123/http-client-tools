package com.http.client.tools.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import com.http.client.tools.controller.FxmlLoader;

public class MapCache {
	public static FxmlLoader loader = new FxmlLoader();
	
	public static String body = "body";
	public static String parameter = "parameter";
	public static String header = "header";
	public static String contentType = "contentType";
	
	public static Map cacheMapData = new LinkedHashMap<>();
	
	public static Object getCache(String key){
		return cacheMapData.get(key);
	}
	
	public static void setCache(String key,Object value){
		cacheMapData.put(key, value);
	}
}
