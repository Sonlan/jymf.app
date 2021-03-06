package org.jymf.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.jymf.entity.ProductInfo;
import org.jymf.entity.UserRecord;
import org.jymf.service.IProductInfoService;
import org.jymf.service.UserRecordService;
import org.jymf.utils.Common;
import org.jymf.utils.HttpUtils;
import org.jymf.utils.SysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jymf.common.LableUtil;

/**
 * 16位追溯码的验证Controller 用于手机扫描端
 * 
 * @author cqs
 * @date 2014年12月16日
 */
@Controller
@RequestMapping(value = "/m")
public class MobileCheckController {

	@Autowired
	private IProductInfoService productService;
	
	@Autowired
	private UserRecordService userRecordService;

	@Resource
	private SysConfig sysConfig;

	private Logger logger = LoggerFactory.getLogger(MobileCheckController.class);
	/**
	 * 验证中烟产品是否有微信红包活动URL
	 */
	private final String WXRED_URL = "http://waptest.ooyanjing.com/zyzshb/activity/isActivity";
	/**
	 * 微信红包跳转URL
	 */
	private final String WXRED_GOTO_URL = "http://waptest.ooyanjing.com/zyzshb/activity/index?%s";

	/**
	 * @param id
	 *            16位追溯码
	 * @return
	 */
	@RequestMapping(value = "/lchk/{id}", method = RequestMethod.GET)
	public String labelCheck(@PathVariable("id") String id, Model model, HttpSession session) {
		// 追溯码的验证
		if (!LableUtil.checkLabel16(id).equals("0")) {
			// 验证失败 ==> 失败画面
			// TODO
			model.addAttribute("message", "非法的追溯凭证！");
			return "/error/mobile";
		}
		session.removeAttribute("proInfo");

		ProductInfo proInfo = null;

		try {
			// 22位标签烟草模式
			if (id.length() == 22) {
				proInfo = productService.getZyInfo(id);
			} else {
				proInfo = productService.getInfo(id);
			}
		} catch (Exception e) {
			proInfo = null;
			e.printStackTrace();
		}

		if (null == proInfo) {
			// 验证失败 ==> 失败画面
			// TODO
			model.addAttribute("message", "追溯凭证无效！");
			return "/error/mobile";
		}
		session.removeAttribute("error");
		proInfo.setLabelId(id);
		proInfo.setLabelIdSpace(Common.getPer4Space(id));
		session.setAttribute("proInfo", proInfo);

		/*
		 * if(proInfo.getConsCnt()>=2 && proInfo.getWorkMode()!=10){
		 * proInfo.setIsCanceled(1); }
		 */
		// workmode = 7,为快消品
		if (proInfo.getWorkMode() == 7) {
			String yzm = LableUtil.getConsCode(id);
			model.addAttribute("yzm", yzm);
			return "/m/indexkxp";
		} else {
			return "/m/index";
		}

	}

	@RequestMapping(value = "/consCode", method = RequestMethod.GET)
	public String consCode() {
		return "/m/index";
	}

	@RequestMapping(value = "/consCode", method = RequestMethod.POST)
	public String consCode(ProductInfo proInfo, HttpSession session, RedirectAttributes redirectAttributes) {
		if (!LableUtil.checkLabel20(proInfo.getLabelId() + proInfo.getConsId()).equals("0")) {
			// session.setAttribute("proInfo",proInfo);
			// session.setAttribute("error","请输入四位有效验证码");
			redirectAttributes.addFlashAttribute("error", "验证码错误，请输入有效验证码");
			return "redirect:/m/consCode";
		}
		session.removeAttribute("error");
		session.removeAttribute("proInfo");

		// 22位标签烟草模式
		String tempLabel = proInfo.getLabelId();
		String tempCons = proInfo.getConsId();

		try {
			if (proInfo.getLabelId().length() == 22) {
				proInfo = productService.getZyInfo(proInfo.getLabelId(), proInfo.getConsId());
			} else {
				proInfo = productService.getInfo(String.format("%s%s", proInfo.getLabelId(), proInfo.getConsId()));
			}
		} catch (Exception e) {
			proInfo = null;
			e.printStackTrace();
		}
		

		if (null == proInfo) {
			// 验证失败 ==> 失败画面
			// TODO
			// model.addAttribute("message", "追溯凭证错误，请确认！");
			return "/error/mobile";
		}

		proInfo.setLabelId(tempLabel);
		proInfo.setConsId(tempCons);
		// 获取公司的基本信息（画面是否被订制）
		productService.companybase(proInfo.getCompanyId(), proInfo);

		session.setAttribute("proInfo", proInfo);

		if (proInfo.getConsCnt() > 2 && proInfo.getWorkMode() != 10) {
			proInfo.setIsCanceled(1);
		}

		// 中烟模式下首次消费查询，将验证后的产品信息发送到微信红包端
		if (proInfo.getWorkMode() == 9 && proInfo.getConsCnt() == 1) {

			String argPre = "prodno=%s";
			String realPre = String.format(argPre, proInfo.getLabelId());
			logger.info(String.format("微信红包post：%s", realPre));
			String resp = HttpUtils.sendPost(WXRED_URL, realPre);
			logger.info(String.format("微信红包rec：%s", resp));

			// 接口返回字符串为空
			if (null != resp && !resp.trim().equals("") && resp.trim().startsWith("{") && resp.trim().endsWith("}")) {
				JSONObject obj = JSONObject.fromObject(resp);
				obj = obj.getJSONObject("header");
				int code = obj.getInt("act_code");
				// 该产品存在红包活动
				if (code == 1) {
					logger.info(String.format("微信红包跳转URL：" + WXRED_GOTO_URL, realPre));
					return String.format("redirect:" + WXRED_GOTO_URL, realPre);
				}
			}
		} 
		
		// 手机APP扫描后首次消费验证的时候新增用户追溯记录
		// 有用户登录的时候
		if (session.getAttribute("userid")!=null && proInfo.getConsCnt() == 1) {
			UserRecord userRecord = new UserRecord();
			userRecordService.insertUserRecord(userRecord);
		}

		return String.format("/m/%s/%smain", proInfo.getWorkMode(), getPath(proInfo, false));
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public String product(HttpSession session) {
		ProductInfo proInfo = (ProductInfo) session.getAttribute("proInfo");
		if (null == proInfo) {
			return "/error/mobile";
		}
		return String.format("/m/%s/%smain", proInfo.getWorkMode(), getPath(proInfo, false));
	}

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public String company(HttpSession session) {
		ProductInfo proInfo = (ProductInfo) session.getAttribute("proInfo");
		if (null == proInfo) {
			return "/error/mobile";
		}

		return String.format("/m/%s/%scompany", proInfo.getWorkMode(), getPath(proInfo, true));
	}

	/**
	 * 页面中嵌入其他信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/other", method = RequestMethod.GET)
	public String other(HttpSession session) {
		ProductInfo proInfo = (ProductInfo) session.getAttribute("proInfo");
		if (null == proInfo) {
			return "/error/mobile";
		}
		String path = String.format("/m/%s/%sother", proInfo.getWorkMode(), getPath(proInfo, false));
		return path;
	}

	private String getPath(ProductInfo proInfo, boolean isCompany) {
		
		String path="";
		
		if (proInfo.isProduct()) {
			boolean isFind = false;
			for (String id : proInfo.getProductIds().split(",")) {
				if (id.equals(String.valueOf(proInfo.getProductId()))) {
					isFind = true;
					break;
				}
			}
			if (isFind)
				path = String.format("%s/%s/", proInfo.getCompanyId(), proInfo.getProductId());
			else
				path = String.format("%s/", proInfo.getCompanyId());
		}
		 
		if (isCompany) {
			if (proInfo.isCompany() && !proInfo.isProduct()) {
				path=String.format("%s/", proInfo.getCompanyId());
			}
			if (!proInfo.isCompany() && proInfo.isProduct()) {
				path="";
			}
		}
		 
		return path;
	}

	/**
	 * @param id
	 *            16 + 4位追溯码,验证
	 * @return
	 */
	@RequestMapping(value = "consChk")
	@ResponseBody
	public String labelConsCheck(@RequestParam("labelId") String labelId, @RequestParam("consId") String consId) {
		// 追溯码的验证
		if (LableUtil.checkLabel20(labelId + consId).equals("0")) {
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * @param id
	 *            16 + 4位追溯码,验证
	 * @return
	 */
	@RequestMapping(value = "idChk")
	@ResponseBody
	public String labelIdCheck(@RequestParam("labelId") String labelId) {
		// 追溯码的验证
		if (LableUtil.checkLabel16(labelId).equals("0")) {
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * 验证码和验证码同时输入的check
	 * 
	 * @param proInfo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/labelcons", method = RequestMethod.POST)
	public String labelCons(ProductInfo proInfo, HttpSession session) {
		String labelId = proInfo.getLabelId();
		String consId = proInfo.getConsId();
		logger.info(String.format("追溯码：%s 验证码： %s", labelId, consId));
		if (!LableUtil.checkLabel20(proInfo.getLabelId() + proInfo.getConsId()).equals("0")) {
			session.setAttribute("labelInfo", proInfo);
			session.setAttribute("error", "追溯码或验证码错误<br/> 请输入正确的追溯码和验证码");
			logger.info("追溯码或验证码错误,请输入正确的追溯码和验证码");
			return "redirect:/check";
		}

		ProductInfo pInfo = productService.getInfo(String.format("%s%s", proInfo.getLabelId(), proInfo.getConsId()));

		if (null == pInfo) {
			session.setAttribute("labelInfo", proInfo);
			session.setAttribute("error", "您所查询的不是中国追溯的合法凭证!");
			logger.info("您所查询的不是中国追溯的合法凭证!");
			return "redirect:/check";
		}
		proInfo = pInfo;
		session.removeAttribute("error");
		session.removeAttribute("labelInfo");

		// 获取公司的基本信息（画面是否被订制）
		productService.companybase(proInfo.getCompanyId(), proInfo);

		proInfo.setLabelId(labelId);
		proInfo.setConsId(consId);
		proInfo.setLabelIdSpace(Common.getPer4Space(labelId));

		session.setAttribute("proInfo", proInfo);

		if (proInfo.getConsCnt() > 2 && proInfo.getWorkMode() != 10) {
			proInfo.setIsCanceled(1);
		}

		return String.format("/m/%s/%smain", proInfo.getWorkMode(), getPath(proInfo, false));
	}

}
