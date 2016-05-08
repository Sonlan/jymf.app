package cn.net.prot;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 22号协议获取公司信息（Json文件）
 * 
 * @author zwj 2014-11-6
 */
public class Prot22CompanyDesc extends ProtBase {

	private final short iTaskCode = 22;

	/**
	 * 描述key
	 */
	public final static String kDepicts = "depicts";
	/**
	 * 数据
	 */
	private String depicts;

	private String jsonData;

	/**
	 * 22号协议，获取公司信息<br/>
	 * 2014-11-6下午2:00:46
	 * 
	 * @throws IOException
	 */
	public void dealDesc(SocketCreate socketCreate) throws IOException {
		recProt(socketCreate.getDis(), socketCreate.getDos(), iTaskCode,
				socketCreate.getiCompanyId());
	}

	/**
	 * 发送22协议
	 * <p>
	 * 2014-10-30上午10:38:00
	 * </p>
	 * 
	 * @param dos
	 * @param iTaskCode
	 * @param label
	 * @param shType
	 * @throws IOException
	 */
	private void sendProt(DataOutputStream dos, short iTaskCode, int iCompanyId)
			throws IOException {
		short iBodySize = countBodySize(iCompanyId);
		sendHeader(dos, iTaskCode, iBodySize);
		sendiCompanyId(dos, iCompanyId);
		sendCheckCode(dos);
	}

	/**
	 * 接收并处理数据
	 * <p>
	 * 2014-11-6下午1:59:24
	 * </p>
	 * 
	 * @param dis
	 * @param dos
	 * @param iTaskCode
	 * @param companyId
	 * @throws IOException
	 */
	private void recProt(DataInputStream dis, DataOutputStream dos,
			short iTaskCode, int companyId) throws IOException {
		sendProt(dos, iTaskCode, companyId);

		recHeader(dis);

		if (isTaskCode(iTaskCode)) {
			int jsonSize = getiRecBodySize() - 2;
			if (jsonSize > 0) {
				jsonData = new String(recString(dis, jsonSize),"UTF-8");
				recCheckCode(dis);
				setSuccess(true);
			} else {
				recCheckCode(dis);
				// 无json数据
				setSuccess(false);
			}
		} else {
			// (2) 无法匹配公司信息，iTaskCode=0xFFEC ;CommonErrHandler.BASE_22_NO_INFO
			recFFFF(dis, iTaskCode);
		}
	}

	/**
	 * 数据<br/>
	 * 获取描述,非json格式
	 * 
	 * @return
	 */
	public String getDepicts() {
		if(null == jsonData)
			return null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			return (String) mapper.readValue(jsonData, Map.class).get(kDepicts);
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
	 * json数据 <br/>
	 * 2015-5-7上午11:23:25
	 * 
	 * @return {"depicts":"XXX"}
	 */
	public String getJsonData() {
		return jsonData;
	}

}
