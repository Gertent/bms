package com.rmd.bms.controller.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rmd.bms.util.web.ResponseUtils;
import com.rmd.bms.util.web.WebMessageCode;
import com.rmd.bms.util.web.WebMessages;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * @ClassName:	AbstractAjaxController 
 * @Description:AbstractAjaxController抽象类
 * @author:	hanguoshuai
 * @date:	2015年11月3日 下午1:31:06 
 *  
 */
public abstract class AbstractAjaxController implements AbstractController{
	
	private static Logger logger = Logger.getLogger(AbstractAjaxController.class);
	
	
	
	
	/** 
	* @Title:writeOut 
	* @Description:将字符串写到页面，即ajax方法执行后的结果
	* @author:hanguoshuai  
	* @param:@param str
	* @param:@param response
	* @return:void
	* @throws 
	*/
	protected void writeOut(String str,HttpServletResponse response){
		try {
			
			ResponseUtils.renderJson(response, str);
		} catch (Exception e) {
			logger.error("AbstractAjaxController.writeOut(String str,HttpServletResponse response) is error",e);
		}
	}
	
	protected void writeOutWebMessages(WebMessages webMessages,HttpServletResponse response){
		try {
			ResponseUtils.renderJson(response,mapToObjString(webMessages.getMessagesMap()));
		} catch (Exception e) {
			logger.error("AbstractAjaxController.writeOutWebMessages(WebMessages webMessages,HttpServletResponse response) is error",e);
		}
	}
	
	protected void writeOutWebMessage(WebMessageCode code,HttpServletRequest request,HttpServletResponse response){
		try {
			WebMessages webMessages = new WebMessages(request);
			webMessages.addMessageCode(code);
			
			ResponseUtils.renderJson(response,mapToObjString(webMessages.getMessagesMap()));
		} catch (Exception e) {
			logger.error("AbstractAjaxController.writeOutWebMessage(WebMessageCode code,HttpServletRequest request,HttpServletResponse response) is error",e);
		}
	}
	
	protected void writeOutWebMessageSend(WebMessageCode code,int id,HttpServletRequest request,HttpServletResponse response){
		try {
			WebMessages webMessages = new WebMessages(request);
			webMessages.addMessageCodeSend(code,id+"");
			
			ResponseUtils.renderJson(response,mapToObjString(webMessages.getMessagesMap()));
		} catch (Exception e) {
			logger.error("AbstractAjaxController.writeOutWebMessage(WebMessageCode code,HttpServletRequest request,HttpServletResponse response) is error",e);
		}
	}
	
	protected void writeOutWebMessageFormDB(String DBMsg,HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,String> msg = new HashMap<String, String>();
			msg.put("desc", DBMsg);
			ResponseUtils.renderJson(response,mapToObjString(msg));
		} catch (Exception e) {
			logger.error("AbstractAjaxController.writeOutWebMessageFormDB(String DBMsgStr,HttpServletRequest request,HttpServletResponse response) is error",e);
		}
	}
	
	/*// JSON返回页面MAP方式
	public  void writeOut(Pagination p,HttpServletResponse response) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", p.getTotalCount());
			map.put("rows", p.getList());
			map.put("pageNumber", p.getPageNo());
			ResponseUtils.renderJson(response, mapToObjString(map));
		} catch (Exception e) {
			logger.error("AbstractAjaxController.writeOut(Pagination p,HttpServletResponse response) is error",e);
		}
	}
	
	protected Map<String, Object> writeOutMap(Pagination p){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", p.getTotalCount());
			map.put("rows", p.getList());
			map.put("pageNumber", p.getPageNo());
			return map;
		} catch (Exception e) {
			logger.error("AbstractAjaxController.writeOutMap(Pagination p) is error",e);
			return new HashMap<String, Object>();
		}
	}*/
	
	/** 
	* @Title:objectToArrayString 
	* @Description:将一个Object对象转化为集合类型的json字符串
	* @param:@param obj
	* @param:@return
	* @return:String
	* @throws 
	*/
	protected String objectToArrayString(Object obj){
		JSONArray array = JSONArray.fromObject(obj);
		return array.toString();
	}
	
	/** 
	* @Title:mapToObjString 
	* @Description:将map类型转化为对象型json字符串</br>
	* 			      对象中也有可能含有collection
	* @param:@param data
	* @param:@return
	* @return:String
	* @throws 
	*/
	protected String mapToObjString(Map<?, ?> data){
		JSONObject object = JSONObject.fromObject(data);
		return object.toString();
	}
	
	/** 
	* @Title:objToObjString 
	* @Description:将object类型转化为对象型json字符串</br>对象中也有可能含有collection
	* @author:hanguoshuai  
	* @param:@param data
	* @param:@return
	* @return:String
	* @throws 
	*/
	protected String objToObjString(Object data){
		JSONObject object = JSONObject.fromObject(data);
		return object.toString();
	}
	

	/** 
	* @Title:collectionToArrayString 
	* @Description:将list对象转化为集合型json字符串 
	* @author:hanguoshuai
	* @param:@param data
	* @param:@return
	* @return:String
	* @throws 
	*/
	protected String collectionToArrayString(List<?> data){
		return objectToArrayString(data);
	}
	

	/** 
	* @Title:writeOutXml 
	* @Description:输出xml文本 
	* @author:hanguoshuai  
	* @param:@param xmlTxt
	* @param:@param response
	* @return:void
	* @throws 
	*/
	protected void writeOutXml(String xmlTxt,HttpServletResponse response) {
		try {
			ResponseUtils.renderXml(response, xmlTxt);
		} catch (Exception e) {
			logger.error("AbstractAjaxController.writeOutXml(String xmlTxt,HttpServletResponse response) is error",e);
		}
	}
	
	/** 
	* @Title:writeOutText 
	* @Description:输出纯文本
	* @author:hanguoshuai  
	* @param:@param text
	* @param:@param response
	* @return:void
	* @throws 
	*/
	protected void writeOutText(String text,HttpServletResponse response) {
		try {
			ResponseUtils.renderText(response, text);
		} catch (Exception e) {
			logger.error("AbstractAjaxController.writeOutText(String text,HttpServletResponse response) is error",e);
		}
	}
}
