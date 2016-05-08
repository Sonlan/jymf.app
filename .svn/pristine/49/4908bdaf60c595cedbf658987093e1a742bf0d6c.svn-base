package org.jymf.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jymf.entity.ProductInfo;
import org.jymf.entity.ThirdPartParam;
import org.jymf.utils.JYHexString;
import org.jymf.utils.MD5keyBean;
import org.jymf.utils.RSAUtil;
import com.jymf.common.LableUtil;
import net.sf.json.JSONObject;

public class ThirdPartQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String error = "error";
	private String illegal = "<br>该追溯码不是中国追溯合法标码";
	private String noData = "<br>未得到数据";
	private String noAuthority ="<br>未授权的请求来源";
       
    public ThirdPartQuery() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	private void doService(HttpServletRequest request, HttpServletResponse response){
		ProductInfo labelInfo = new ProductInfo();
		
		try{
		
		//if(request.getHeader("User-Agent").contains("micromessenger") ||request.getHeader("User-Agent").contains("MicroMessenger")){
		if(null!=request.getParameter("weixin")){
			String id = request.getParameter("id");
			if(null!=id){
				if(!LableUtil.checkLabel16(id).equals("0")){
					request.setAttribute(error, illegal);
				}	
				labelInfo.setLabelId(id);
			}else{
				request.setAttribute(error, noData);
			}
		}else{
			String data = request.getParameter("data");
			String sign = request.getParameter("sign");
			
			JYHexString hex =  new JYHexString();
			MD5keyBean md5 = new MD5keyBean();
			
			if(null != data && null != sign){
				if(sign.equals(md5.getkeyBeanofStr(RSAUtil.publicKey.substring(0, 10)))){
					//私钥解密
					byte[] resultByte = RSAUtil.decryptByPrivateKey(hex.hexStringToBytes(data),RSAUtil.privateKey);
					String resultStr = new String(resultByte);
					
					JSONObject jsonObject = JSONObject.fromObject(resultStr);
					ThirdPartParam param = (ThirdPartParam) JSONObject.toBean(jsonObject, ThirdPartParam.class);
					labelInfo.setLabelId(param.getLabelid());
					
					if(!LableUtil.checkLabel16(param.getLabelid()).equals("0")){
						request.setAttribute(error, illegal);
					}	
				}else{
					request.setAttribute(error, noAuthority);
				}
			}else{
				request.setAttribute(error, noData);
			}
		}
		
		request.setAttribute("labelInfo", labelInfo);
/*		response.sendRedirect(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()
							  +"/download/check.html?id="+labelInfo.getLabelId());*/
		
		response.sendRedirect("http://d.cpzs.org/check.html?id="+labelInfo.getLabelId());
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
