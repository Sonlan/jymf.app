package cn.net.prot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


/**
 * 21号协议获取产品信息
 * 
 * @author zwj <br>
 *         2014-12-29
 */
public class Prot21ProductInfo extends ProtBase {
	private final short iTaskCode21 = 21;
	private String jsonData;

	/**
	 * 21号协议获取产品信息<br/>
	 * 
	 * 2015年4月20日 00:06:05
	 * 
	 * @throws IOException
	 */
	public void dealProductInfo(SocketCreate socketCreate) throws IOException {
		recProt(socketCreate.getDos(), socketCreate.getDis(), iTaskCode21,
				socketCreate.getiCompanyId(), socketCreate.getiProductId());
	}

	/**
	 * 21.获取产品信息（Json文件）
	 * 
	 * @param dos
	 * @param iLabelId
	 * @param iCompanyId公司ID
	 * @param iProductId产品ID
	 * @throws IOException
	 */
	private void sendProt(DataOutputStream dos, short iTaskCode,
			int iCompanyId, long iProductId) throws IOException {
		short bodySize = countBodySize(iCompanyId, iProductId);
		sendHeader(dos, iTaskCode, bodySize);
		sendiCompanyId(dos, iCompanyId);
		sendiProductId(dos, iProductId);
		sendCheckCode(dos);
	}

	/**
	 * 21号协议获取产品信息
	 * <p>
	 * 2014-12-29下午5:15:22
	 * </p>
	 * 
	 * @param dos
	 * @param dis
	 * @param iTaskCode
	 * @param iCompanyId
	 * @param iProductId
	 * @throws IOException
	 */
	private void recProt(DataOutputStream dos, DataInputStream dis,
			short iTaskCode, int iCompanyId, long iProductId)
			throws IOException {

		sendProt(dos, iTaskCode, iCompanyId, iProductId);
		recHeader(dis);// 读取头信息
		debugLog(iTaskCode, toStringRecHeader());
		if (isTaskCode(iTaskCode)) {
			byte[] jsonBuffer = recString(dis, getiRecBodySize() - 2);
			recCheckCode(dis);
			jsonData = new String(jsonBuffer, "UTF-8");
		} else {
			recFFFF(dis, iTaskCode);
		}
	}


	/**
	 * 返回获取到的json数据 <br/>
	 * 2015-4-28上午10:59:55
	 * 
	 * @return
	 */
	public String getJsonData() {
		return jsonData;
	}

}
