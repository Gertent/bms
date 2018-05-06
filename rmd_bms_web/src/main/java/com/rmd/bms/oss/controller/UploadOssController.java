package com.rmd.bms.oss.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rmd.bms.oss.OssService;
import com.rmd.bms.oss.bean.OssFile;


@Controller
@RequestMapping("/base/upload")
public class UploadOssController {

	
	@RequestMapping("/uploadPic")
	@ResponseBody
	public OssFile ossupload(@RequestParam MultipartFile file){
//		String filename1 = request.getParameter("licenceName");
		Map<String, Object> map = new HashMap<String, Object>();
//		String fileData = request.getParameter("fileData");// Base64编码过的图片数据信息，对字节数组字符串进行Base64解码
		OssFile filename= null;
		try {
			OssService ossService =new OssService();
			filename=ossService.Ossupload(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filename;
	}
}
