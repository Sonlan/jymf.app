package org.jymf.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jymf.entity.UserRecord;
import org.jymf.service.UserRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="app")
public class APPRecordController {
	
	@Autowired
	private UserRecordService userRecordService;
	
	@RequestMapping(value = "/getTrackRecords", method=RequestMethod.POST)
	public String getRecords(HttpServletRequest request) {
		String userid = request.getParameter("userid");
		System.out.println(userid);
		if (StringUtils.isEmpty(userid)) {
			// 没有追溯记录
			return "";
		}
		
		List<UserRecord> list = userRecordService.getRecordByUserId(userid);
		if(list==null) return null;
		//request.setAttribute("list", list);
		return "/m/record";
	}
}