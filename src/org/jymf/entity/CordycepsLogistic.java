package org.jymf.entity;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 虫草产品运输相关信息
 * @author Administrator
 *
 */
public class CordycepsLogistic {
	private int id;//记录ID
	private String proName;//产品名称
	private String proBatchId;//产品批号
	private String materialBatchId;//原料批次
	private String materialDosage;//原料用量
	private String inPacHead;//内包负责人
	private String inPacTime;//内包时间
	private String inPacInstrument;//内包使用仪器
	private String outPacHead ;//外包负责人
	private String outPacTime ;//外包时间
	private String proQualId;//成品检验合格编号
	@DateTimeFormat(pattern="yyyy/mm/dd")
	private String proInTime;//成品入库时间
	private int proInNum;//成品入库数
	private String proYield;//成品收率
	@DateTimeFormat(pattern="yyyy/mm/dd")
	private String deliveryTime;//发货时间
	private String deliveryAddr;//发货地点
	private String saleMethod;//销售方式
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProBatchId() {
		return proBatchId;
	}
	public void setProBatchId(String proBatchId) {
		this.proBatchId = proBatchId;
	}
	public String getMaterialBatchId() {
		return materialBatchId;
	}
	public void setMaterialBatchId(String materialBatchId) {
		this.materialBatchId = materialBatchId;
	}
	public String getMaterialDosage() {
		return materialDosage;
	}
	public void setMaterialDosage(String materialDosage) {
		this.materialDosage = materialDosage;
	}
	public String getInPacHead() {
		return inPacHead;
	}
	public void setInPacHead(String inPacHead) {
		this.inPacHead = inPacHead;
	}
	public String getInPacTime() {
		return inPacTime;
	}
	public void setInPacTime(String inPacTime) {
		this.inPacTime = inPacTime;
	}
	public String getInPacInstrument() {
		return inPacInstrument;
	}
	public void setInPacInstrument(String inPacInstrument) {
		this.inPacInstrument = inPacInstrument;
	}
	public String getOutPacHead() {
		return outPacHead;
	}
	public void setOutPacHead(String outPacHead) {
		this.outPacHead = outPacHead;
	}
	public String getOutPacTime() {
		return outPacTime;
	}
	public void setOutPacTime(String outPacTime) {
		this.outPacTime = outPacTime;
	}
	public String getProQualId() {
		return proQualId;
	}
	public void setProQualId(String proQualId) {
		this.proQualId = proQualId;
	}
	public String getProInTime() {
		return proInTime;
	}
	public void setProInTime(String proInTime) {
		this.proInTime = proInTime;
	}
	public int getProInNum() {
		return proInNum;
	}
	public void setProInNum(int proInNum) {
		this.proInNum = proInNum;
	}
	public String getProYield() {
		return proYield;
	}
	public void setProYield(String proYield) {
		this.proYield = proYield;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getDeliveryAddr() {
		return deliveryAddr;
	}
	public void setDeliveryAddr(String deliveryAddr) {
		this.deliveryAddr = deliveryAddr;
	}
	public String getSaleMethod() {
		return saleMethod;
	}
	public void setSaleMethod(String saleMethod) {
		this.saleMethod = saleMethod;
	}
	
}
