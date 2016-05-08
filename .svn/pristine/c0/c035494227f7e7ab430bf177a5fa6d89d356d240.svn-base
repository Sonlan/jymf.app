package cn.net.prot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 20号，获取标签产品信息<br>
 * 2001号协议获取产品信息（Json文件）
 * <p>
 * 使用情况：云南汽配模式使用
 * </p>
 * 
 * @author zwj 2014-11-6
 */
public class Prot20ProductDesc extends ProtBase {
	/**
	 * 2001汽配模式
	 */
	private final short iTaskCode2001 = 2001;
	/**
	 * 普通模式
	 */
	private final short iTaskCode20 = 20;

	private String jsonData = null;// json数据
	private Map<String, String> map20or2001 = null;
	private final short INVALID_20_LABEL_ID = (short) 0xFFEE;

	/**
	 * 
	 * @author zwj
	 * @Time 2015年5月25日 下午1:06:51
	 * @param socketCreate
	 * @throws IOException
	 */
	public void dealProdInfoSwitch(SocketCreate socketCreate) throws IOException {
		switch (socketCreate.getiWorkStyle()) {
		case WorkType.Car:
			dealYunCarProdInfo(socketCreate.getDos(), socketCreate.getDis(), socketCreate.getiLabel(),
					socketCreate.getiCompanyId(), socketCreate.getiProductId());
			this.map20or2001 = this.getMap20();
			break;
		default:
			dealProdInfo(socketCreate.getDos(), socketCreate.getDis(), socketCreate.getiLabel(),
					socketCreate.getiCompanyId(), socketCreate.getiProductId());
			this.map20or2001 = this.getMap2001();
			break;
		}
	}

	/**
	 * 2001汽配模式使用
	 * <p>
	 * 2014-12-1上午10:38:12
	 * </p>
	 * 
	 * @param dos
	 * @param dis
	 * @param iLabelId
	 * @param iCompanyId
	 * @param iProductId
	 * @throws IOException
	 */
	public void dealYunCarProdInfo(DataOutputStream dos, DataInputStream dis, long iLabelId, int iCompanyId,
			long iProductId) throws IOException {
		sendProt(dos, iLabelId, iCompanyId, iProductId, iTaskCode2001);
		recProt(dis, (short) iTaskCode2001);
	}

	/**
	 * 普通20号协议发送<br/>
	 * 2014-12-1上午10:38:02
	 * 
	 * @param dos
	 * @param dis
	 * @param iLabelId
	 * @param iCompanyId
	 * @param iProductId
	 * @throws IOException
	 */
	public void dealProdInfo(DataOutputStream dos, DataInputStream dis, long iLabelId, int iCompanyId, long iProductId)
			throws IOException {
		sendProt(dos, iLabelId, iCompanyId, iProductId, iTaskCode20);
		recProt(dis, iTaskCode20);
	}

	/**
	 * 接收并处理数据 <br/>
	 * 2014-11-6下午1:59:24
	 * 
	 * @param dis
	 * @param dos
	 * @param iTaskCode
	 * @param companyId
	 * @param mHandler
	 * @throws IOException
	 * @throws JSONException
	 */
	private void recProt(DataInputStream dis, short iTaskCode) throws IOException {
		recHeader(dis);
		int jsonSize = getUnsignedByte(getiRecBodySize()) - 2;
		if (jsonSize <= 0) {
			// 接收到了少于2个字节的数据
			return;
		}

		int sendTaskCode = getUnsignedByte(iTaskCode);
		int recTaskCode = getUnsignedByte(getiRecTaskCode());

		if (isFFFF()) {
			recCheckCode(dis);
			recFFFF(dis);
			debugLog(sendTaskCode, "iResult:" + getiRecResult() + ";iErrcode:" + getiRecErrcode());
			if (getiRecResult() == 1) {
				if(getiRecErrcode() == (short) INVALID_20_LABEL_ID){
					errLog(sendTaskCode, "无效标签");
				}else{
					errLog(sendTaskCode, "无数据");
				}
			} else {
				errLog(sendTaskCode, "未知错误");
			}
		} else if (sendTaskCode == recTaskCode) {
			jsonData = recJsonData(dis, recTaskCode, jsonSize);
		}
	}


	/**
	 * 接收json数据,转换成String
	 * <p>
	 * 2014-11-7下午2:41:37
	 * </p>
	 * 
	 * @param dis
	 *            输入流
	 * @param key
	 *            任务号
	 * @param jsonSize
	 *            数据的长度
	 * @throws IOException
	 */
	private String recJsonData(DataInputStream dis, int key, int jsonSize) throws IOException {
		byte[] jsons = new byte[jsonSize];
		for (int i = 0; i < jsonSize; i++) {
			jsons[i] = dis.readByte();
		}
		recCheckCode(dis);
		String jsonData = new String(jsons, "UTF-8");
		debugLog("20-2001 json", jsonData);
		return jsonData;
	}

	/**
	 * 2001 云南汽配模式中使用的。获取产品信息 20号协议
	 * <p>
	 * 2014-11-7下午3:44:16
	 * </p>
	 * 
	 * @param dos
	 * @param iLabelId
	 * @param iCompanyId
	 * @param iProductId
	 * @param iTaskCode
	 *            任务号，这里支持20和2001
	 * @throws IOException
	 */
	private void sendProt(DataOutputStream dos, long iLabelId, int iCompanyId, long iProductId, short iTaskCode)
			throws IOException {
		short bodySize = countBodySize(iLabelId, iCompanyId, iProductId);
		sendHeader(dos, iTaskCode, bodySize);
		dos.writeLong(iLabelId);
		dos.writeInt(iCompanyId);
		dos.writeLong(iProductId);
		sendCheckCode(dos);
	}

	// ----通用字段
	/**
	 * 产品的id
	 */
	public final String kId = "id";
	/**
	 * 配件产地
	 */
	public final String kOrigin = "origin";
	/**
	 * 配件厂商
	 */
	public final String kProducer = "producer";
	/**
	 * 查询描述
	 */
	public final String kQuery_des = "query_des";
	/**
	 * 举报电话
	 */
	public final String kTEL = "companyTel";

	// ---2001汽配模式专用字段
	/**
	 * 适用车型
	 */
	public final String kCarType = "carType";
	/**
	 * 配件型号
	 */
	public final String kNumberID = "numberID";
	/**
	 * 配件描述2001
	 */
	public final String kDescrip = "descrip";
	/**
	 * 配件名称
	 */
	public final String kName = "name";
	// ----20号协议特殊字段
	/**
	 * 消费码
	 */
	public final String kCode = "code";
	/**
	 * 公司名称
	 */
	public final String kCompanyname = "companyname";
	/**
	 * 产品描述20
	 */
	public final String kDescription = "description";
	/**
	 * 产品名称
	 */
	public final String kProductname = "name";
	/**
	 * 产品url
	 */
	public final String kProductUrl = "url";
	/**
	 * 企业url
	 */
	public String kCompanyUrl = "companyUrl";

	/**
	 * 2001<br/>
	 * [{"carType":"一汽大众","descrip":"","id":"100053","name":"滚针轴承","numberID":
	 * "002 311 125 A"
	 * ,"origin":"","producer":"上海上汽大众汽车销售有限公司","query_des":"","companyTel":""}]
	 * 
	 * 
	 * @param strResult
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> parse2001Json(String strResult) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<LinkedHashMap<String, String>> list;
		try {
			list = objectMapper.readValue(strResult, List.class);
			return list.get(0);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 20.<br/>
	 * [{"code":"8208","companyname":"云南大理泛亚汽车城有限责任公司","description":"","id":
	 * "100053","origin":"","producer":"上海上汽大众汽车销售有限公司","name":"滚针轴承",
	 * "query_des":"","url":"","companyTel":"","companyUrl":""}]
	 * 
	 * 
	 * @param strResult
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> parse20Json(String strResult) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<LinkedHashMap<String, String>> list;
		try {
			list = objectMapper.readValue(strResult, List.class);
			return list.get(0);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, String> getMap20or2001() {
		return map20or2001;
	}
	public Map<String, String> getMap20() {
		return parse20Json(jsonData);
	}


	public Map<String, String> getMap2001() {
		return parse2001Json(jsonData);
	}

	public String getJsonData() {
		return jsonData;
	}
}
