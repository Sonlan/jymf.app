package org.jymf.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jymf.entity.WXRed;
import org.jymf.service.IWXRedService;
import org.jymf.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 中烟微信红包
 */
@Controller
public class ZyWxRedController {
	
	private static Logger logger = LoggerFactory.getLogger(ZyDownLoadController.class);

	 @Autowired
	 private IWXRedService wxredService;

	@RequestMapping(value = "/wxred", method = RequestMethod.POST)
	@ResponseBody
	public String wxred(HttpServletRequest request) {
		String dataStr = request.getParameter("data");
		logger.info(String.format("微信红包结果收到参数：%s", dataStr));
		
		if(null == dataStr || dataStr.trim().equals("") || !dataStr.trim().startsWith("{") || !dataStr.trim().endsWith("}")){
			return "";
		}
		
		JSONObject obj = JSONObject.fromObject(dataStr);
		JSONObject head = obj.getJSONObject("header");
		JSONObject body = obj.getJSONObject("body");
		JSONArray dataArr = body.getJSONArray("data");
		
		if(dataArr != null && dataArr.size() > 0){
			for(int i=0; i<=dataArr.size()-1; i++){
				JSONObject jo = dataArr.getJSONObject(i);
				WXRed wxred = new WXRed();
				wxred.setId(UUID.randomUUID().toString());
				wxred.setRe_openid(jo.getString("re_openid"));
				wxred.setNickname(jo.getString("nickname"));
				wxred.setTotal_amount(jo.getString("total_amount"));
				wxred.setGet_time(jo.getString("get_time"));
				wxred.setQrcode(jo.getString("qrcode"));
				wxred.setMark(jo.getString("mark"));
				wxred.setProdno(jo.getString("prodno"));
				wxred.setProdunit(jo.getString("produnit"));
				wxred.setCompany(jo.getString("company"));
				wxred.setAddress(jo.getString("address"));
				wxred.setLet_num(jo.getString("let_num"));
				wxred.setReceive_flag(jo.getString("receive_flag"));
				wxred.setResult_code(jo.getString("result_code"));
				wxred.setMsg_id(head.getString("id"));
				wxred.setCreate_time(Common.fromDateH());
				wxredService.addWXRed(wxred);
			}
		}

		JSONObject js = new JSONObject();
		JSONObject he = new JSONObject();
		he.put("id", head.getString("id"));
		he.put("result_rec", 0);
		he.put("msg", "");
		js.put("header", he);
		
		JSONObject bo = new JSONObject();
		js.put("body", bo);
		
		String resp = js.toString();
		logger.info(String.format("微信红包结果返回参数：%s", resp));
		
		return resp;
	}
}
