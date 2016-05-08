package org.jymf.entity;

public class Company {
    /**
     * 公司ID
     */
	private int id;
	
	/**
	 * 公司是否为订制画面
	 */
    private Boolean isCompany;
    
    /**
     * 产品是否为订制画面
     */
    private Boolean isProduct;

    /**
     * 定制产品
     */
    private String ids;
    
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getIsCompany() {
		return isCompany;
	}

	public void setIsCompany(Boolean isCompany) {
		this.isCompany = isCompany;
	}

	public Boolean getIsProduct() {
		return isProduct;
	}

	public void setIsProduct(Boolean isProduct) {
		this.isProduct = isProduct;
	}
}
