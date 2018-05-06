package com.rmd.bms.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 监控工具类
 *
 * @author : liu
 * @Date : 2017/4/7
 */
public class MonitorUtil {

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getRemoteHost(HttpServletRequest request) {
        if (request == null) return null;
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr() == null ? null : request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}
