package com.rmd.bms.service;

import java.util.List;
import java.util.Map;

import com.rmd.bms.entity.SysArea;

public interface redisService {
	
	void addAreas(Map<String, Object> map);
	
	Map<String, Object> getAreas(String key);
}
