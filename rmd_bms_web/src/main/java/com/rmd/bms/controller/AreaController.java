package com.rmd.bms.controller;

import com.alibaba.fastjson.JSONObject;
import com.rmd.bms.bean.AreaInfo;
import com.rmd.bms.service.AreaService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 移动端接口
 *
 * @author 陈赟
 * @date 2017年4月6日
 */
@Controller
@RequestMapping("/area")
public class AreaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    /**
     * 获取所有区域列表信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/listAllAreaInfo")
    @ResponseBody
    public JSONObject listAllAreaInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> params) {
        LOGGER.debug("area listAllAreaInfo param:{}", params);
        JSONObject json = new JSONObject();
        try {
            LOGGER.debug("area listAllAreaInfo getLastUpdateTime");
            long lastUpdateTime = areaService.getLastUpdateTime();
            LOGGER.debug("area listAllAreaInfo getLastUpdateTime result:{}", lastUpdateTime);

            String updateTime = params.get("updateTime");
            // 如果修改时间改变了，则返回数据
            if (StringUtils.isEmpty(updateTime) || Long.parseLong(updateTime) != lastUpdateTime) {
                long l = System.currentTimeMillis();
                List<AreaInfo> areaList = areaService.listAllAreaInfo();
                System.out.println("time: " + (System.currentTimeMillis() - l));
//                LOGGER.debug("area listAllAreaInfo areaList:{}", areaList == null ? "null" : JSON.toJSONString(areaList));
                json.put("result", areaList);
            }

            json.put("updateTime", String.valueOf(lastUpdateTime));
            json.put("status", "200");
        } catch (Exception e) {
            LOGGER.error("area listAllAreaInfo error:", e);
            json.put("status", "500");
        }
//        LOGGER.debug("area listAllAreaInfo result:{}", json);
        return json;
    }

    /**
     * @param request
     * @param response
     * @return 2017年4月13日
     * @Description:将数据缓存到redis里
     * @author:yuyang
     */
    @RequestMapping("/cacheData")
    @ResponseBody
    public String cacheData(HttpServletRequest request, HttpServletResponse response) {
        try {
            areaService.pushArea2Redis();
            return "success";
        } catch (Exception e) {
            LOGGER.error("area cacheData error:", e);
            return e.getMessage();
        }
    }

}
