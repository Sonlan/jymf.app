package org.jymf.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.core.modules.mapper.JsonMapper;
import org.jymf.entity.Company;
import org.jymf.entity.ProCompany;
import org.jymf.entity.ProductCar;
import org.jymf.entity.ProductInfo;
import org.jymf.entity.RedWineAuthImage;
import org.jymf.service.IProductInfoService;
import org.jymf.utils.Common;
import org.jymf.utils.FileManager;
import org.jymf.utils.SysConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import cn.net.cpzslibsAct.Prot;
import cn.net.cpzslibsAct.bean.comm.Packet;
import cn.net.cpzslibsAct.bean.djson.DJson10001;
import cn.net.cpzslibsAct.bean.djson.DJson10013;
import cn.net.cpzslibsAct.bean.djson.DJson10020;
import cn.net.cpzslibsAct.bean.ujson.UJson10001;
import cn.net.cpzslibsAct.bean.ujson.UJson10002;
import cn.net.cpzslibsAct.bean.ujson.UJson10003;
import cn.net.cpzslibsAct.bean.ujson.UJson10013;
import cn.net.cpzslibsAct.bean.ujson.UJson10020;
import cn.net.cpzslibsAct.bean.ujson.UJson10021;
import cn.net.cpzslibsAct.bean.ujson.UJson10022;
import cn.net.cpzslibsAct.utils.JsonUtils;
import cn.net.prot.AddressUtil;
import cn.net.prot.Prot02Logout;
import cn.net.prot.Prot03GetImage;
import cn.net.prot.Prot09UpDown;
import cn.net.prot.Prot13QueryCount;
import cn.net.prot.Prot20ProductDesc;
import cn.net.prot.Prot21ProductInfo;
import cn.net.prot.Prot22CompanyDesc;
import cn.net.prot.ProtBase;
import cn.net.prot.SocketCreate;
import net.sf.json.JSONObject;

@Service("productService")
public class ProductInfoService implements IProductInfoService {

	@Resource
	private SysConfig sysConfig;

	/**
	 * strLabel 16位追溯码 20 = 16 + 4
	 */
	public ProductInfo getInfo(String strLabel) {
		long iLabel;
		int iLabelType = 16;
		if (strLabel.length() == 16) {
			iLabelType = 16;
			iLabel = Long.valueOf(strLabel);
		} else if (strLabel.length() == 20) {
			iLabelType = 20;
			iLabel = Long.valueOf(strLabel.substring(0, 16));
		} else {
			// 追溯码不正确
			return null;
		}

		boolean isConnect = false;
		SocketCreate socketCreate = new SocketCreate();
		try {
			socketCreate.createLoginCpzs(iLabel, sysConfig.getIp(), Integer.valueOf(sysConfig.getPort()));
			isConnect = socketCreate.isResult();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!isConnect) {
			System.err.println(String.format("连接%s:%s错误", sysConfig.getIp(), sysConfig.getPort()));
			return null;
		}
		ProductInfo productInfo = dealLabel(strLabel, iLabelType, socketCreate);

		productInfo.setCompanyId(socketCreate.getiCompanyId());
		productInfo.setProductId(socketCreate.getiProductId());
		// 工作模式
		productInfo.setWorkMode(socketCreate.getiWorkStyle());
		productInfo.setSpath(sysConfig.getUrl());
		return productInfo;
	}
	
	@Override
	public ProductInfo getZyInfo(String strLabel) {
		Prot prot = new Prot(sysConfig.getIp(), Integer.valueOf(sysConfig.getPort()));
		prot.init();
		
		// 10001 几个ID信息
		UJson10001 uj01 = new UJson10001();
		uj01.setLabelID(strLabel);
		uj01.setUserType(0);

		Packet sp01 = new Packet();
		sp01.setJson(JsonUtils.toJsonStr(uj01));
		Packet rp01 = prot.sendP_10001(sp01);
		DJson10001 dj01 = JsonUtils.fillDBean(rp01, DJson10001.class);
		
		// 10020 产品基本信息
		UJson10020 uj20 = new UJson10020();
		uj20.setCompany_id(dj01.getCompanyID());
		uj20.setLabel_id(strLabel);
		uj20.setProduct_id(dj01.getProductID());

		Packet sp20 = new Packet();
		sp20.setJson(JsonUtils.toJsonStr(uj20));
		Packet rp20 = prot.sendP_10020(sp20);
		DJson10020 dj20 = JsonUtils.fillDBean(rp20, DJson10020.class);
		
		// 10013 产品消费信息
		UJson10013 uj13 = new UJson10013();
		uj13.setApp_type(1);
		uj13.setLabel_id(strLabel);
		uj13.setLabel_type(0);
		uj13.setTerminal_type(3000);

		Packet sp13 = new Packet();
		sp13.setJson(JsonUtils.toJsonStr(uj13));
		Packet rp13 = prot.sendP_10013(sp13);
		DJson10013 dj13 = JsonUtils.fillDBean(rp13, DJson10013.class);
		
		// 10002 退出
		UJson10002 uj02 = new UJson10002();
		uj02.setUserID(strLabel);
		Packet sp02 = new Packet();
		sp02.setJson(JsonUtils.toJsonStr(uj02));
		prot.sendP_10002(sp02);
		prot.closeRes();

		ProductInfo productInfo = new ProductInfo();
		// 描述
		productInfo.setQueryDes(dj20.getQuery_des());
		// url
		productInfo.setUrl(dj20.getProductUrl());
		// 电话
		productInfo.setTel(dj20.getCompanyTel());
		// 消费查询次数
		productInfo.setConsCnt(dj13.getCons_count());
		productInfo.setCompanyId(Integer.valueOf(dj01.getCompanyID()));
		productInfo.setProductId(Long.valueOf(dj01.getProductID()));
		// 工作模式
		productInfo.setWorkMode(dj01.getWorkMode());
		productInfo.setSpath(sysConfig.getUrl());
		return productInfo;
	}
	
	@Override
	public ProductInfo getZyInfo(String strLabel, String consCode) {
		Prot prot = new Prot(sysConfig.getIp(), Integer.valueOf(sysConfig.getPort()));
		prot.init();
		
		// 10001 几个ID信息
		UJson10001 uj01 = new UJson10001();
		uj01.setLabelID(strLabel);
		uj01.setUserType(0);

		Packet sp01 = new Packet();
		sp01.setJson(JsonUtils.toJsonStr(uj01));
		Packet rp01 = prot.sendP_10001(sp01);
		DJson10001 dj01 = JsonUtils.fillDBean(rp01, DJson10001.class);
		
		// 10013 产品消费信息
		UJson10013 uj13 = new UJson10013();
		uj13.setApp_type(1);
		uj13.setCons_check_code(Integer.valueOf(consCode));
		uj13.setLabel_id(strLabel);
		uj13.setLabel_type(0);
		uj13.setTerminal_type(3000);

		Packet sp13 = new Packet();
		sp13.setJson(JsonUtils.toJsonStr(uj13));
		Packet rp13 = prot.sendP_10013(sp13);
		DJson10013 dj13 = JsonUtils.fillDBean(rp13, DJson10013.class);
		
		// 10020 产品基本信息
		UJson10020 uj20 = new UJson10020();
		uj20.setCompany_id(dj01.getCompanyID());
		uj20.setLabel_id(strLabel);
		uj20.setProduct_id(dj01.getProductID());

		Packet sp20 = new Packet();
		sp20.setJson(JsonUtils.toJsonStr(uj20));
		Packet rp20 = prot.sendP_10020(sp20);
		DJson10020 dj20 = JsonUtils.fillDBean(rp20, DJson10020.class);
		
		// 10003  产品介绍图片信息
		UJson10003 uj032 = new UJson10003();
		uj032.setLabel_id(strLabel);
		uj032.setImage_type(2);

		Packet sp032 = new Packet();
		sp032.setJson(JsonUtils.toJsonStr(uj032));
		Packet rp032 = prot.sendP_10003(sp032);
		
		// 10003 企业介绍图片信息
		UJson10003 uj03 = new UJson10003();
		uj03.setLabel_id(strLabel);
		uj03.setImage_type(1);

		Packet sp03 = new Packet();
		sp03.setJson(JsonUtils.toJsonStr(uj03));
		Packet rp03 = prot.sendP_10003(sp03);
		
		// 10022 公司信息
		UJson10022 uj22 = new UJson10022();
		uj22.setCompany_id(dj01.getCompanyID());

		Packet sp22 = new Packet();
		sp22.setJson(JsonUtils.toJsonStr(uj22));
		Packet rp22 = prot.sendP_10022(sp22);
		
		// 10021 产品基本信息
		UJson10021 uj21 = new UJson10021();
		uj21.setCompany_id(dj01.getCompanyID());
		uj21.setProduct_id(dj01.getProductID());

		Packet sp21 = new Packet();
		sp21.setJson(JsonUtils.toJsonStr(uj21));
		Packet rp21 = prot.sendP_10021(sp21);
		
		// 10002 退出
		UJson10002 uj02 = new UJson10002();
		uj02.setUserID(strLabel);
		Packet sp02 = new Packet();
		sp02.setJson(JsonUtils.toJsonStr(uj02));
		prot.sendP_10002(sp02);
		prot.closeRes();


		ProductInfo productInfo = new ProductInfo();
		// 描述
		productInfo.setQueryDes(dj20.getQuery_des());
		// url
		productInfo.setUrl(dj20.getProductUrl());
		// 电话
		productInfo.setTel(dj20.getCompanyTel());
		// 公司名称
		productInfo.setCompanyName(dj20.getCompanyName());
		// 公司URL
		productInfo.setCompanyUrl(dj20.getCompanyUrl());
		// 产品名称
		productInfo.setProductName(dj20.getName());
		// 厂商
		productInfo.setProductProducer(dj20.getProducer());
		// 产品产地
		productInfo.setProductOrigin(dj20.getOrigin());
		// 产品网址
		productInfo.setProductUrl(dj20.getProductUrl());
		// 产品描述
		productInfo.setProductDesc(dj20.getDescription());
		//消费次数
		productInfo.setConsCnt(dj13.getCons_count());
		
		JSONObject jsonObj = JSONObject.fromObject(rp22.getJson()); 
		// 公司描述
		productInfo.setCompanyDesc(jsonObj.getString("depicts"));
		productInfo.setProductParameterJson(rp21.getJson());
		
		// 产品介绍图片
		productInfo.setProductIntrodImages(rp032.getJson());
		productInfo.setCompanyImages(rp03.getJson());
		productInfo.setCompanyId(Integer.valueOf(dj01.getCompanyID()));
		productInfo.setProductId(Long.valueOf(dj01.getProductID()));
		// 工作模式
		productInfo.setWorkMode(dj01.getWorkMode());
		productInfo.setSpath(sysConfig.getUrl());
		
		//非首次消费查询
		if(dj13.getCons_count() >= 1){
			productInfo.setFirstConsAddress(AddressUtil.searchCityNameByNum(dj13.getFirst_cons_area()));
			productInfo.setFirstConsDate(Common.fromDateStr(dj13.getFirst_cons_time()));
		}
		
		return productInfo;
	}

	
	private ProductInfo dealLabel(String strLabel, int iLabelType, SocketCreate socketCreate) {
		long iLabel;
		ProductInfo productInfo = new ProductInfo();
		try {
			if (iLabelType == 16) {
				iLabel = Long.valueOf(strLabel);
				/* 获取查询描述的一句话 */
				Prot20ProductDesc descProt = new Prot20ProductDesc();
				Map<String, String> descMap = null;
				descProt.dealProdInfoSwitch(socketCreate);
				descMap = descProt.getMap20or2001();
				// 描述
				productInfo.setQueryDes(descMap.get(descProt.kQuery_des));
				// url
				productInfo.setUrl(descMap.get(descProt.kProductUrl));
				// 电话
				productInfo.setTel(descMap.get(descProt.kTEL));
				Prot13QueryCount queryCountProt = new Prot13QueryCount();
				queryCountProt.dealQueryCountSwitch(socketCreate);
				// 消费查询次数
				productInfo.setConsCnt(queryCountProt.getiConsCount());
			} else if (iLabelType == 20) {
				// 20位的时候，行驶下面代码
				iLabel = Long.valueOf(strLabel.substring(0, 16));
				short iConsCheckCode = Short.valueOf(strLabel.substring(16));
				Prot20ProductDesc descProt = new Prot20ProductDesc();
				descProt.dealProdInfoSwitch(socketCreate);
				Map<String, String> descMap = descProt.getMap20or2001();
				Prot13QueryCount queryCountProt = new Prot13QueryCount();
				queryCountProt.dealConsInfoSwitch(socketCreate, iConsCheckCode);
				// 描述
				productInfo.setQueryDes(descMap.get(descProt.kQuery_des));
				// url
				productInfo.setUrl(descMap.get(descProt.kProductUrl));
				// 电话
				productInfo.setTel(descMap.get(descProt.kTEL));
				// 公司名称
				productInfo.setCompanyName(descMap.get(descProt.kCompanyname));
				// 公司URL
				productInfo.setCompanyUrl(descMap.get(descProt.kCompanyUrl));
				// 产品名称
				productInfo.setProductName(descMap.get(descProt.kProductname));
				// 厂商
				productInfo.setProductProducer(descMap.get(descProt.kProducer));
				// 产地
				productInfo.setProductOrigin(descMap.get(descProt.kOrigin));
				// 产品产地
				productInfo.setProductOrigin(descMap.get(descProt.kOrigin));
				// 产品网址
				productInfo.setProductUrl(descMap.get(descProt.kProductUrl));
				// 产品描述
				productInfo.setProductDesc(descMap.get(descProt.kDescription));

				if (socketCreate.getiWorkStyle() == ProtBase.WorkType.Car) {
					// 产品描述
					productInfo.setProductDesc(descMap.get(descProt.kDescription));
					// 汽配模式
					List<ProductCar> carLst = new ArrayList<ProductCar>();

					if (socketCreate.getiProductId() == 100) {
						// 包标签,获取下级数据
						Prot09UpDown prot09UpDown = new Prot09UpDown();
						prot09UpDown.deal902UpDown(socketCreate.getDis(), socketCreate.getDos(), iLabel);
						List<Map<String, String>> listDown = prot09UpDown.get902List();
						if (null != listDown && !listDown.isEmpty()) {
							// 获取到下级数据.配件名称,配件型号,配件使用数量，配件厂家
							for (int i = 0; i < listDown.size(); i++) {
								Map<String, String> map = listDown.get(i);
								ProductCar productCar = new ProductCar();
								productCar.setName(map.get(Prot09UpDown.kName));
								productCar.setNumberId(map.get(Prot09UpDown.kNumber_id).trim());
								productCar.setProducer(map.get(Prot09UpDown.kProducer));
								productCar.setNumber(map.get(Prot09UpDown.kNumber));
								carLst.add(productCar);
							}
							productInfo.setCarLst(carLst);
						} else {
							// 无下级
						}
					} else {
						// 配件
						ProductCar productCar = new ProductCar();
						productCar.setName(descMap.get(descProt.kName));
						productCar.setNumberId(descMap.get(descProt.kNumberID).trim());
						productCar.setProducer(descMap.get(descProt.kProducer));
						productCar.setNumber("1");
						carLst.add(productCar);
						productInfo.setCarLst(carLst);
					}
				} else if (socketCreate.getiWorkStyle() == ProtBase.WorkType.RedWine) {
					Prot03GetImage getImage = new Prot03GetImage();
					getImage.dealRedWineAuthIamge(socketCreate);
					String jsonData = getImage.getJsonData();
					ObjectMapper mapper = new ObjectMapper();
					RedWineAuthImage authImage = mapper.readValue(jsonData, RedWineAuthImage.class);
					RedWineAuthImage.Images[] images = authImage.getList();
					for (int i = 0; i < images.length; i++) {
						String imageUrl = images[i].getImg();
						if (imageUrl.contains("xkz")) {
							productInfo.setXkzUrl(authImage.getUrl() + imageUrl);
						} else if (imageUrl.contains("jyz")) {
							productInfo.setJyzUrl(authImage.getUrl() + imageUrl);
						} else if (imageUrl.contains("rgd")) {
							productInfo.setRgdUrl(authImage.getUrl() + imageUrl);
						}
					}
				}

				productInfo.setConsCnt(queryCountProt.getiConsCount());

				Prot22CompanyDesc companyDesc = new Prot22CompanyDesc();
				companyDesc.dealDesc(socketCreate);
				// 公司描述
				productInfo.setCompanyDesc(companyDesc.getDepicts());

				Prot21ProductInfo prot21ProductInfo = new Prot21ProductInfo();
				prot21ProductInfo.dealProductInfo(socketCreate);
				productInfo.setProductParameterJson(prot21ProductInfo.getJsonData());

				Prot03GetImage getImage = new Prot03GetImage();
				getImage.dealProductIntrodImage(socketCreate);
				// 产品介绍图片
				productInfo.setProductIntrodImages(getImage.getJsonData());

				getImage.dealCompanyIntrodImage(socketCreate);
				productInfo.setCompanyImages(getImage.getJsonData());
				
				if (queryCountProt.isFirst()) {
					//System.out.println("第一次消费");
				} else {
					productInfo.setFirstConsAddress(queryCountProt.getFirstConsAreaName());
					productInfo.setFirstConsDate(queryCountProt.getFirstConsTime());
				}

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			Prot02Logout logout = new Prot02Logout();
			logout.dealLogout(socketCreate.getSocket(), socketCreate.getDis(), socketCreate.getDos());
		}
		return productInfo;
	}

	@Override
	public void companybase(int id, ProductInfo productInfo) {
		File cfgFile;

		try {
			cfgFile = ResourceUtils.getFile("classpath:profCompany.json");
		} catch (FileNotFoundException e) {
			return;
		}

		FileManager fileManager = new FileManager();
		String jsonStr = fileManager.readFile(cfgFile);

		JsonMapper json = new JsonMapper();
		ProCompany companys = json.fromJson(jsonStr, ProCompany.class);
		if (null == companys) {
			return;
		}

		for (Company com : companys.getCompanys()) {
			if (com.getId() == id) {
				productInfo.setProduct(com.getIsProduct());
				productInfo.setCompany(com.getIsCompany());
				productInfo.setProductIds(com.getIds());
				break;
			}
		}
	}

}
