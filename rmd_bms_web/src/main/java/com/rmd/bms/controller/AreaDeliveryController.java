package com.rmd.bms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.rmd.bms.bean.AreaDeliveryInfo;
import com.rmd.bms.constant.Constant;
import com.rmd.bms.entity.DeliveryType;
import com.rmd.bms.service.AreaDeliveryService;

/**
 * 区划管理控制
 * @author 陈赟 
 * @date 2017年4月1日
 */
@Controller
@RequestMapping("/areaDelivery")
public class AreaDeliveryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaDeliveryController.class);

    @Autowired
    private AreaDeliveryService areaDeliveryService;

    /**
     * 跳转到区划管理主页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/areaDeliveryPage")
    public ModelAndView areaDeliveryPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("areaDelivery/areaDeliveryManage");
        
        LOGGER.debug("areaDelivery areaDeliveryPage listDeliveryType");
        List<DeliveryType> deliveryTypeList = areaDeliveryService.listDeliveryType();
        LOGGER.debug("areaDelivery areaDeliveryPage listDeliveryType result{}", deliveryTypeList == null ? "null" : JSON.toJSONString(deliveryTypeList));
        
        model.addObject("deliveryTypeList", deliveryTypeList);
        return model;
    }

    /**
     * 列出区划信息
     * @param params
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/listAreaDelivery")
    @ResponseBody
    public List<AreaDeliveryInfo> listAreaDelivery(AreaDeliveryInfo params, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(params.getParentCode())) {
            params.setParentCode(Constant.PARENT_AREA_CODE);
        }
        LOGGER.debug("areaDelivery listAreaDelivery params:{}", JSON.toJSONString(params));
        List<AreaDeliveryInfo> areaDeliveryList = areaDeliveryService.listAreaDeliveryInfo(params);
        LOGGER.debug("areaDelivery listAreaDelivery result:{}", areaDeliveryList == null ? "null" : JSON.toJSONString(areaDeliveryList));

        return areaDeliveryList;
    }

    /**
     * 搜索区划信息
     * @param params
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/searchAreaDelivery")
    @ResponseBody
    public List<AreaDeliveryInfo> searchAreaDelivery(AreaDeliveryInfo params, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("areaDelivery searchAreaDelivery params:{}", JSON.toJSONString(params));
        List<AreaDeliveryInfo> areaDeliveryList = areaDeliveryService.listAreaDeliveryInfo(params);
        LOGGER.debug("areaDelivery searchAreaDelivery result:{}", areaDeliveryList == null ? "null" : JSON.toJSONString(areaDeliveryList));

        return areaDeliveryList;
    }

    /**
     * 跳转到区划编辑页面
     * @param request
     * @param response
     * @param areaId
     * @return
     */
    @RequestMapping("/areaDeliveryEditPage")
    @ResponseBody
    public ModelAndView areaDeliveryEditPage(HttpServletRequest request, HttpServletResponse response, @RequestParam String areaIds) {
        ModelAndView model = new ModelAndView("areaDelivery/areaDeliveryEdit");
        
        LOGGER.debug("areaDelivery areaDeliveryEditPage listDeliveryType");
        List<DeliveryType> deliveryTypeList = areaDeliveryService.listDeliveryType();
        LOGGER.debug("areaDelivery areaDeliveryEditPage listDeliveryType result{}", deliveryTypeList == null ? "null" : JSON.toJSONString(deliveryTypeList));
        
        model.addObject("areaIds", areaIds);
        model.addObject("deliveryTypeList", deliveryTypeList);
        return model;
    }
    
    /**
     * 保存设置的配送方式
     * @param request
     * @param response
     * @param params
     * @return
     */
    @RequestMapping("/saveEdit")
    @ResponseBody
    public String saveEdit(HttpServletRequest request, HttpServletResponse response, @RequestBody AreaDeliveryInfo params) {
        LOGGER.debug("areaDelivery saveEdit saveAreaDeliveryInfo params:{}", params == null ? "null" : JSON.toJSONString(params));
        try {
            boolean saveResult = areaDeliveryService.saveAreaDeliveryInfo(params);
            if (saveResult) {
                return "true";
            }
        } catch (Exception e) {
            LOGGER.error("areaDelivery saveEdit error:", e);
        }
        return "false";
    }

}
