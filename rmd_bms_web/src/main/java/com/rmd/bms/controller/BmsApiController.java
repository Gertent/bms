package com.rmd.bms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmd.bms.entity.Vo.UserVo;
import com.rmd.bms.service.BmsApiService;
import com.rmd.commons.servutils.Notification;

@Controller
@RequestMapping("/bmsApi")
public class BmsApiController {

	@Autowired
	private BmsApiService bmsApiService;
	
	
	@RequestMapping("/login")
	@ResponseBody
	public void login(String name, String password, String appNo){
		Notification<UserVo> selectCodeByUser = bmsApiService.selectCodeByUser(name, password,appNo);
		System.out.println(selectCodeByUser);
	}
	
	@RequestMapping("/code")
	@ResponseBody
	public void code(String menuCode,String appNo){
		bmsApiService.selectCodeByUserIdandSystem(menuCode,appNo);
	}
	
	@RequestMapping("/getloginname")
	@ResponseBody
	public void selectUserByName(String name){
		bmsApiService.selectUserByName(name);
	}
	
	@RequestMapping("/selectUserByUserid")
	@ResponseBody
	public void selectUserByUserid(Integer id){
		bmsApiService.selectUserByUserid(id);
	}
	
	@RequestMapping("/selectUserandRoleBySystemId")
	@ResponseBody
	public void selectUserandRoleBySystemId(String appNo){
		bmsApiService.selectUserandRoleBySystemId(appNo);
	}
	
	@RequestMapping("/selectUserByOperationCode")
	@ResponseBody
	public void selectUserByOperationCode(String appNo, String operationCode){
		bmsApiService.selectUserByOperationCode(appNo,operationCode);
	}
	
	@RequestMapping("/updatePassword")
	@ResponseBody
	public void updatePassword(Integer id,String password){
		bmsApiService.updatePasswordByUserId(id,password);
	}
	
	@RequestMapping("/selectMenuUrlByMessagesName")
	@ResponseBody
	public void selectMenuUrlByMessagesName(String appNo,String messagesName){
		bmsApiService.selectMenuUrlByMessagesName(appNo,messagesName);
	}
	
}
