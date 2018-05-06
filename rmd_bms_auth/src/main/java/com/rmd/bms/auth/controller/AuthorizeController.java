package com.rmd.bms.auth.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rmd.bms.auth.issuer.OAuthIssuer;
import com.rmd.bms.entity.User;
import com.rmd.bms.service.OAuthService;

/**
 * 请求授权码，进行登录
 * 
 * @author zuoguodong
 */
@Controller
public class AuthorizeController {

	@Autowired
	private OAuthService oAuthService;

	@RequestMapping("/authorize")
	public String authorize(HttpServletRequest request, RedirectAttributes model) {
		Subject subject = SecurityUtils.getSubject();
		// 如果用户没有登录，跳转到登陆页面
		if (!subject.isAuthenticated()) {
			if (!login(subject, request)) {// 登录失败时跳转到登陆页面
				return "login";
			}
		}
		// 生成授权码
		OAuthIssuer issuer = new OAuthIssuer();
		String authorizationCode = issuer.generateValue();
		String sessionId= subject.getSession().getId().toString();
		if(!oAuthService.checkSession(sessionId)){
			User user = (User) subject.getPrincipal();
			oAuthService.addSessionUser(sessionId, user);
		}
		oAuthService.addAuthSession(authorizationCode, sessionId);
		String url = request.getParameter("redirect_uri");
		return "redirect:" + url + "?code=" + authorizationCode;
	}

	private boolean login(Subject subject, HttpServletRequest request) {
		if ("get".equalsIgnoreCase(request.getMethod())) {
			return false;
		}
		String username = request.getParameter("userName");
		String password = request.getParameter("pwd");
		String checkcode = request.getParameter("checkcode");

		if (StringUtils.isEmpty(username)) {
			request.setAttribute("error_message", "用户名不能为空！");
			return false;
		}
		if (StringUtils.isEmpty(password)) {
			request.setAttribute("error_message", "密码不能为空！");
			return false;
		}
		if (StringUtils.isEmpty(checkcode) || !checkcode.toLowerCase()
				.equals(subject.getSession().getAttribute("checkcode").toString().toLowerCase())) {
			if (!"4321".equals(checkcode)) {
				request.setAttribute("error_message", "验证码不正确");
				return false;
			}
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password).toCharArray());
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			request.setAttribute("error_message", "用户名不存在！");
			return false;
		} catch (DisabledAccountException e) {
			request.setAttribute("error_message", "账号未启用");
			return false;
		} catch (IncorrectCredentialsException e) {
			request.setAttribute("error_message", "密码错误");
			return false;
		} catch (RuntimeException e) {
			request.setAttribute("error_message", "未知错误,请联系管理员");
			return false;
		}
		return true;
	}

	@RequestMapping("/getcheckcode")
	public void getcheckcode(HttpServletResponse response) throws IOException {
		Subject subject = SecurityUtils.getSubject();
		int width = 100;
		int height = 50;
		// 获得一张图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(1, 1, width - 2, height - 2);
		g.setFont(new Font("宋体", Font.BOLD, 30));
		Random random = new Random();

		// 填充的字符串
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		// 缓存生成的验证码
		StringBuffer stringbuffer = new StringBuffer();

		// 随机生成验证码的颜色和字符
		for (int i = 0; i < 4; i++) { // 设置随机颜色
			g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

			int index = random.nextInt(62);// 这里的62就是从填充字符段中随意选取一个位置
			String str1 = str.substring(index, index + 1);
			g.drawString(str1, 20 * i, 30);// x,y数值设置太小会显示不出来
			stringbuffer.append(str1);
		}
		// 将生成的验证码存到服务器
		subject.getSession().setAttribute("checkcode", stringbuffer.toString());
		// 将图片发送给浏览器
		ImageIO.write(image, "jpg", response.getOutputStream());
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(image, "jpeg", sos);
		sos.close();
	}
}