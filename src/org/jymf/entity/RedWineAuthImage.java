package org.jymf.entity;
/**
 * 红酒认证图片<br/>
 * 解析json数据使用
 * @author zwj
 * @Time 2015年7月16日 下午4:11:27
 */
public class RedWineAuthImage {
	private String url;
	private Images iamges;
	private Images[] list;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Images getIamges() {
		return iamges;
	}

	public void setIamges(Images iamges) {
		this.iamges = iamges;
	}

	public Images[] getList() {
		return list;
	}

	public void setList(Images[] list) {
		this.list = list;
	}

	public static class Images {
		private String companyUrl;
		private String img;
		private String name;

		public String getCompanyUrl() {
			return companyUrl;
		}

		public void setCompanyUrl(String companyUrl) {
			this.companyUrl = companyUrl;
		}

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
