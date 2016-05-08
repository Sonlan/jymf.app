package org.jymf.entity;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 虫草培育中心记录表
 * @author Administrator
 *
 */
public class Cordyceps {
	private String materialBatchId;//原料批号
	private String formerLeader;//前处理负责人
	private String wheatBatchId;//原料小麦批号
	private String wheatInventory;//原料小麦投料量
	private String wheatQualifiedId;//小麦检验合格编号
	private String bacterialBatchId;//菌种批号
	private String bacterialQualId;//菌种检验合格编号
	private String bacterialSeqNum;//菌种生产序列号
	private String cultivateLeader ;//培育车间负责人
	private String vacTime;//接种时间
	private String bacterialRoom;//培养房
	private String bacterialTime;//培育时间
	private String postProcLeader;//后处理负责人
	private String childQualId;//子实体检测合格编号
	@DateTimeFormat(pattern ="YYYY/MM/DD")
	private String childInTime;//子实体入库时间
	private int childInNum;//子实体入库数量
	private String childYield;//子实体收率
	private int conPowderInNum;//孢子粉入库数量
	private String conPowderYield;//孢子粉收率
	private int myceliumInNum;//菌丝体入库数量
	private int mycoplasmaInNum;//菌质入库数量
	public String getMaterialBatchId() {
		return materialBatchId;
	}
	public void setMaterialBatchId(String materialBatchId) {
		this.materialBatchId = materialBatchId;
	}
	public String getFormerLeader() {
		return formerLeader;
	}
	public void setFormerLeader(String formerLeader) {
		this.formerLeader = formerLeader;
	}
	public String getWheatBatchId() {
		return wheatBatchId;
	}
	public void setWheatBatchId(String wheatBatchId) {
		this.wheatBatchId = wheatBatchId;
	}
	public String getWheatInventory() {
		return wheatInventory;
	}
	public void setWheatInventory(String wheatInventory) {
		this.wheatInventory = wheatInventory;
	}
	public String getWheatQualifiedId() {
		return wheatQualifiedId;
	}
	public void setWheatQualifiedId(String wheatQualifiedId) {
		this.wheatQualifiedId = wheatQualifiedId;
	}
	public String getBacterialBatchId() {
		return bacterialBatchId;
	}
	public void setBacterialBatchId(String bacterialBatchId) {
		this.bacterialBatchId = bacterialBatchId;
	}
	public String getBacterialQualId() {
		return bacterialQualId;
	}
	public void setBacterialQualId(String bacterialQualId) {
		this.bacterialQualId = bacterialQualId;
	}
	public String getBacterialSeqNum() {
		return bacterialSeqNum;
	}
	public void setBacterialSeqNum(String bacterialSeqNum) {
		this.bacterialSeqNum = bacterialSeqNum;
	}
	public String getCultivateLeader() {
		return cultivateLeader;
	}
	public void setCultivateLeader(String cultivateLeader) {
		this.cultivateLeader = cultivateLeader;
	}
	public String getVacTime() {
		return vacTime;
	}
	public void setVacTime(String vacTime) {
		this.vacTime = vacTime;
	}
	public String getBacterialRoom() {
		return bacterialRoom;
	}
	public void setBacterialRoom(String bacterialRoom) {
		this.bacterialRoom = bacterialRoom;
	}
	public String getBacterialTime() {
		return bacterialTime;
	}
	public void setBacterialTime(String bacterialTime) {
		this.bacterialTime = bacterialTime;
	}
	public String getPostProcLeader() {
		return postProcLeader;
	}
	public void setPostProcLeader(String postProcLeader) {
		this.postProcLeader = postProcLeader;
	}
	public String getChildQualId() {
		return childQualId;
	}
	public void setChildQualId(String childQualId) {
		this.childQualId = childQualId;
	}
	public String getChildInTime() {
		return childInTime;
	}
	public void setChildInTime(String childInTime) {
		this.childInTime = childInTime;
	}
	public int getChildInNum() {
		return childInNum;
	}
	public void setChildInNum(int childInNum) {
		this.childInNum = childInNum;
	}
	public String getChildYield() {
		return childYield;
	}
	public void setChildYield(String childYield) {
		this.childYield = childYield;
	}
	public int getConPowderInNum() {
		return conPowderInNum;
	}
	public void setConPowderInNum(int conPowderInNum) {
		this.conPowderInNum = conPowderInNum;
	}
	public String getConPowderYield() {
		return conPowderYield;
	}
	public void setConPowderYield(String conPowderYield) {
		this.conPowderYield = conPowderYield;
	}
	public int getMyceliumInNum() {
		return myceliumInNum;
	}
	public void setMyceliumInNum(int myceliumInNum) {
		this.myceliumInNum = myceliumInNum;
	}
	public int getMycoplasmaInNum() {
		return mycoplasmaInNum;
	}
	public void setMycoplasmaInNum(int mycoplasmaInNum) {
		this.mycoplasmaInNum = mycoplasmaInNum;
	}
	
	
}
