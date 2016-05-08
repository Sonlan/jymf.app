package org.jymf.entity;

import java.util.List;

import org.core.modules.mapper.JsonMapper;

public class UrlPara {

	/**
	 * 路径
	 */
	private String url;
	
	/**
	 * 图片名称
	 */
	private List<ImgPara> list;

	public List<ImgPara> getList() {
		return list;
	}

	public void setList(List<ImgPara> list) {
		this.list = list;
	}

	/**
	 * json 字符串转换为类参数
	 * @param jsonStr
	 * @return
	 */
	public UrlPara jsonToPara(String jsonStr){
		JsonMapper json =new JsonMapper();
		UrlPara para = json.fromJson(jsonStr, UrlPara.class);
		return para;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
