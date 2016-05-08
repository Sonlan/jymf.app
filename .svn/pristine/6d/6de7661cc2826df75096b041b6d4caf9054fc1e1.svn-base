package cn.net.prot;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * 3废弃，替代协议301
 * 
 * @author zwj
 * 
 *         2015-4-28
 */
public class Prot03GetImage extends ProtBase {
	private final short iTaskCode3 = 3;
	private final short iTaskCode301 = 301;
	private final short iTaskCode302 = 302;// 广告图
	/**
	 * 企业介绍1
	 */
	public static final short companyIntrodImage = 1;
	/**
	 * 产品介绍2
	 */
	public static final short productIntrodImage = 2;
	/**
	 * 产品概述3
	 */
	public static final short productSummaryImage = 3;
	/**
	 * 广告图片4
	 */
	public static final short ADImage = 4;
	/**
	 * 红酒城图片-检疫证，报关证，许可证 5
	 */
	public static final short redWineAuthIamge = 5;

	/**
	 * 图片名称
	 */
	private List<String> imageNames;
	/**
	 * 接收到的json数据
	 */
	private String jsonData;

	/**
	 * 红酒许可证图片5 <br/>
	 * 2015-7-15下午3:07:13
	 * 
	 * @param socketCreate
	 * @throws IOException
	 */
	public void dealRedWineAuthIamge(SocketCreate socketCreate)
			throws IOException {
		short iImageType = redWineAuthIamge;
		recProt301(socketCreate.getDos(), socketCreate.getDis(),
				socketCreate.getiLabel(), iImageType);
	}

	/**
	 * 产品概述图3 <br/>
	 * 2015-4-28上午10:05:12
	 * 
	 * @throws IOException
	 */
	public void dealProductSummaryImage(SocketCreate socketCreate)
			throws IOException {
		short iImageType = productSummaryImage;
		recProt301(socketCreate.getDos(), socketCreate.getDis(),
				socketCreate.getiLabel(), iImageType);
	}

	/**
	 * 产品介绍图2 <br/>
	 * 2015-4-28上午10:05:12
	 * 
	 * @throws IOException
	 */
	public void dealProductIntrodImage(SocketCreate socketCreate)
			throws IOException {
		short iImageType = productIntrodImage;
		recProt301(socketCreate.getDos(), socketCreate.getDis(),
				socketCreate.getiLabel(), iImageType);
	}

	/**
	 * 公司介绍图1 <br/>
	 * 2015-4-28上午10:05:12
	 * 
	 * @throws IOException
	 */
	public void dealCompanyIntrodImage(SocketCreate socketCreate)
			throws IOException {
		short iImageType = companyIntrodImage;
		recProt301(socketCreate.getDos(), socketCreate.getDis(),
				socketCreate.getiLabel(), iImageType);
	}

	/**
	 * 获取图片 <br/>
	 * 2015-7-16上午9:51:59
	 * 
	 * @param socketCreate
	 * @param iImageType
	 *            ,图片类型1，2，3，5
	 * @throws IOException
	 */
	public void dealImage(SocketCreate socketCreate, int iImageType)
			throws IOException {
		recProt301(socketCreate.getDos(), socketCreate.getDis(),
				socketCreate.getiLabel(), (short) iImageType);
	}

	/**
	 * json数据 <br/>
	 * 2015-4-28上午10:11:58
	 * 
	 * @return
	 */
	public String getJsonData() {
		return jsonData;
	}

	private void recProt301(DataOutputStream dos, DataInputStream dis,
			long iLableID, short iImageType) throws IOException {
		sendProt(dos, iTaskCode301, iLableID, iImageType);
		recHeader(dis);
		debugLog(iTaskCode301, toStringRecHeader());
		if (isTaskCode(iTaskCode301)) {
			jsonData = new String(recString(dis, getiRecBodySize() - 2),
					"UTF-8");
			recCheckCode(dis);
		} else {
			recFFFF(dis, iTaskCode301);
			setSuccess(false);
		}
	}

	/**
	 * 3. 图片版本验证任务
	 * 
	 * @param dos
	 * @param iLabel
	 *            标签号
	 * @param iImageType
	 *            图片类型
	 * @throws IOException
	 */
	private void sendProt(DataOutputStream dos, short iTaskCode, long iLabel,
			short iImageType) throws IOException {
		short bodySize = countBodySize(iLabel, iImageType);
		sendHeader(dos, iTaskCode, bodySize);
		sendiLabel(dos, iLabel);
		dos.writeShort(iImageType);
		sendCheckCode(dos);
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * 302好协议，读取广告图，不需要登陆 <br/>
	 * 超时时间默认30s<br/>
	 * 2015-5-20下午2:41:46
	 * 
	 * @param ip
	 *            IP地址
	 * @param port
	 *            端口
	 * @return 返回json数据
	 * @throws BindException
	 * @throws ConnectException
	 * @throws UnknownHostException
	 * @throws SocketTimeoutException
	 * @throws IOException
	 */
	public String getAdImageJson(String ip, int port) throws BindException,
			ConnectException, UnknownHostException, SocketTimeoutException,
			IOException {
		return this.getAdImageJson(ip, port, 1000 * 30);
	}

	/**
	 * 302好协议，读取广告图，不需要登陆 <br/>
	 * 2015-5-20下午2:37:07
	 * 
	 * @param ip
	 *            IP地址
	 * @param port
	 *            端口
	 * @param iTimeout
	 *            超时时间
	 * @return 返回json数据
	 * @throws BindException
	 * @throws ConnectException
	 * @throws UnknownHostException
	 * @throws SocketTimeoutException
	 * @throws IOException
	 */
	public String getAdImageJson(String ip, int port, int iTimeout)
			throws BindException, ConnectException, UnknownHostException,
			SocketTimeoutException, IOException {

		SocketAddress socketAddress = new InetSocketAddress(ip, port);
		Socket socket = new Socket();
		socket.connect(socketAddress, iTimeout);
		socket.setSoTimeout(iTimeout);
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		if (null != socket && socket.isConnected()
				&& !socket.isOutputShutdown() && !socket.isInputShutdown()) {
			try {
				short iImageType = ADImage;

				short bodySize = countBodySize(iImageType);
				sendHeader(dos, iTaskCode302, bodySize);
				dos.writeShort(iImageType);
				sendCheckCode(dos);
				recHeader(dis);
				debugLog(iTaskCode302, toStringRecHeader());
				if (isTaskCode(iTaskCode302)) {
					jsonData = new String(
							recString(dis, getiRecBodySize() - 2), "UTF-8");
					recCheckCode(dis);
					return jsonData;
				} else {
					jsonData = null;
					recFFFF(dis, iTaskCode302);
					setSuccess(false);
					return jsonData;
				}
			} finally {
				if (dos != null) {
					try {
						Thread.sleep(100);
						dos.close();
					} catch (Exception e) {
						e.printStackTrace();
						errLog(iTaskCode302, "dos:" + e.toString());
					}
					if (dis != null) {
						try {
							dis.close();
						} catch (IOException e) {
							e.printStackTrace();
							errLog(iTaskCode302, "dis:" + e.toString());
						}
					}
					if (socket != null) {
						try {
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
							errLog(iTaskCode302, "socket:" + e.toString());
						}
					}
				}
			}
		}
		return null;
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * 废弃
	 * 
	 * @param socketCreate
	 * @param iImageType
	 *            图片类型
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void dealGetImage(SocketCreate socketCreate, short iImageType)
			throws IOException {
		rec03Prot(socketCreate.getDos(), socketCreate.getDis(),
				socketCreate.getiLabel(), iImageType);
	}

	/**
	 * 3.获取图片版本 2014-5-23下午3:24:25
	 * 
	 * @param dos
	 * @param dis
	 * @param iLableID
	 * @param iImageType
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	private void rec03Prot(DataOutputStream dos, DataInputStream dis,
			long iLableID, short iImageType) throws IOException {
		sendProt(dos, iTaskCode3, iLableID, iImageType);
		recHeader(dis);
		debugLog(iTaskCode3, toStringRecHeader());
		if (isTaskCode(iTaskCode3)) {
			// 是同个任务
			reciCompanyId(dis);
			reciProductId(dis);
			short iImageCount = dis.readShort();
			if (iImageCount > 0) {
				for (int i = 0; i < iImageCount; i++) {
					// 有图
					byte[] imageID = recString(dis, 32);
					imageNames.add((new String(imageID)).trim());
				}
				recCheckCode(dis);
				setSuccess(true);
			} else {
				setSuccess(false);
				recCheckCode(dis);
			}

		} else {
			recFFFF(dis, iTaskCode3);
			setSuccess(false);
		}
	}
}
