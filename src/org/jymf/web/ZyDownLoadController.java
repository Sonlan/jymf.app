package org.jymf.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 中烟下载页面 用于手机扫描端
 * 
 * @author cqs
 * @date 2015年9月17日
 */
@Controller
public class ZyDownLoadController {
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String start(HttpServletRequest request, @PathVariable("id") String id) {
		String User_Agent = request.getHeader("User-Agent");

		if (User_Agent.contains("micromessenger") || User_Agent.contains("MicroMessenger")) {
			return String.format("redirect:http://d.cpzs.org/download?id=%s", id);
		} else if (User_Agent.contains("Android") || User_Agent.contains("iPhone") || User_Agent.contains("iPad")) {
			return "redirect:http://a.app.qq.com/o/simple.jsp?pkgname=com.cpzs.productvalidate";
		} else {
			return "redirect:http://d.cpzs.org/download.html";
		}
	}
}
