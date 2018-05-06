package com.rmd.bms.service.impl;

import com.rmd.bms.entity.SysArea;
import com.rmd.bms.util.JedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 区域的redis服务
 */
@Service
public class AreaRedisService {

    public void addAllAreas(String key, List<SysArea> areaList) {
        try {
            if (StringUtils.isEmpty(key) || areaList == null || areaList.isEmpty()) {
                return;
            }
            JedisUtil jedisUtil = JedisUtil.getInstance();
            jedisUtil.del(key);

            Map<String, String> map = new HashMap<String, String>();
            for (SysArea sysArea : areaList) {
                /*SysAreaVo sysAreaVo = new SysAreaVo();
                sysAreaVo.setId(sysArea.getId());
                sysAreaVo.setAreaCode(sysArea.getAreaCode());
                sysAreaVo.setAreaName(sysArea.getAreaName());
                sysAreaVo.setParentCode(sysArea.getParentCode());
                String areaJson = JSON.toJSONString(sysAreaVo);*/
                map.put("" + sysArea.getAreaCode(), sysArea.getAreaName());
            }
            jedisUtil.hmset(key, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAreas(String key, List<String> areaStrList) {
        if (StringUtils.isEmpty(key) || areaStrList == null || areaStrList.isEmpty()) {
            return;
        }
        JedisUtil jedisUtil = JedisUtil.getInstance();
        jedisUtil.del(key);

        for (String areaStr : areaStrList) {
            jedisUtil.rpush(key, areaStr);
        }
    }

}
