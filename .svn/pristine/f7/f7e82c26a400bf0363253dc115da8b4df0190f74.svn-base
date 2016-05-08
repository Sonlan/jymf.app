package org.jymf.entity;

import java.util.List;

import org.core.modules.mapper.JsonMapper;

public class ProductPara {
	
	/**
	 * 规格参数
	 */
    private List<Para> gx;
    
    /**
	 * 工艺流程
	 */
    private List<Para> bz;
       
    /**
	 * 其他信息
	 */
    private List<Para> yl;
    
    public List<Para> getBz() {
		return bz;
	}

	public void setBz(List<Para> bz) {
		
		this.bz = bz;
	}


	public List<Para> getYl() {
		return yl;
	}

	public void setYl(List<Para> yl) {
		this.yl = yl;
	}

	public List<Para> getGx() {
		return gx;
	}

	public void setGx(List<Para> gx) {
		this.gx = gx;
	}
	
	public String toJson(){
		removeNull(gx);
		removeNull(bz);
		removeNull(yl);
		
		JsonMapper json =new JsonMapper();
		String jsonStr = json.toJson(this);
		return jsonStr;
	}
	
	public ProductPara jsonToPara(String jsonStr){
		JsonMapper json =new JsonMapper();
		ProductPara para = json.fromJson(jsonStr, ProductPara.class);
		return para;
	}
	
	private void removeNull(List<Para> plst){
		if(null == plst){
		    return;
		}
		for(int i=0;i<plst.size();i++){
			if(null == plst.get(i).getName() || plst.get(i).getName().isEmpty()){
				plst.remove(i);
			}
		}
	}
}
