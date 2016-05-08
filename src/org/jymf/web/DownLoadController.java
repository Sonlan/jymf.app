package org.jymf.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 16位追溯码的验证Controller
 * 用于手机扫描端
 * @author cqs
 * @date   2014年12月16日
 */
@Controller
@RequestMapping(value = "/download")
public class DownLoadController {
	
	@RequestMapping(value="/start", method = RequestMethod.GET)
	public String start(HttpServletRequest request,HttpServletResponse response) 
			          throws ServletException, IOException {
		String User_Agent = request.getHeader("User-Agent");
		if (User_Agent.contains("Android")) {
			if (User_Agent.contains("micromessenger") || User_Agent.contains("MicroMessenger")) {
				return "http://d.cpzs.org/downloadb";
			}else{
				return "redirect:http://218.245.0.108:8080/si/cpzs.apk";
			}
		} else if (User_Agent.contains("iPhone")) {
			return "redirect:https://itunes.apple.com/cn/app/zhong-guo-zhui-su/id629880427?mt=8";
		} else if (User_Agent.contains("iPad")) {
			return "redirect:https://itunes.apple.com/cn/app/zhong-guo-zhui-su/id629880427?mt=8";
		} else {
			return "redirect:http://218.245.0.108:8080/si/cpzs.apk";
		}
	}

}
