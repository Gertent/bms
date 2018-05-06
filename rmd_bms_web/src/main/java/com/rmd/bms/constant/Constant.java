package com.rmd.bms.constant;


/**
 * 系统常量定义
 * @author YYL
 *
 */
public class Constant {
	
	public static final String SESSION_CURRENT_USER_KEY="CURRENTUSER";//用户session id
	
	//公司信息
	public static final String COMPANY_NAME = "雷蒙德国际电子商务有限公司";
	public static final String COMPANY_PHONE = "400-0000-0000";
	public static final String COMPANY_EMAIL = "@raymondgroups.com";
	public static final String COMPANY_WEBSITE = "http://www.raymondgroups.com0";
	
	/**存储sesison_user 的 key  {@value}**/
    public static final String SESSION_USER="s_user";
    public static final String SESSION_SHIROUSER="shiro_user";
    
    public static final int  PAGESIZE=3;
    
    //  0-暂停,1-待投放,2-投放中,3-投放完成,4-删除 5-启用
    public static final Integer ADV_STATU_ZERO = 0;
    public static final Integer ADV_STATU_ONE = 1;
    public static final Integer ADV_STATU_TWO = 2;
    public static final Integer ADV_STATU_THREE = 3;
    public static final Integer ADV_STATU_FOUR = 4;
    public static final Integer ADV_STATU_FIVE = 5;
    
    // 0-停用 ,1-启用, 2-删除
    public static final Integer ADV_POS_STATUS_ZERO =0;
    public static final Integer ADV_POS_STATUS_ONE =1;
    public static final Integer ADV_POS_STATUS_TWO =2;

    // 父级区域编码
    public static final String PARENT_AREA_CODE = "86";
}
