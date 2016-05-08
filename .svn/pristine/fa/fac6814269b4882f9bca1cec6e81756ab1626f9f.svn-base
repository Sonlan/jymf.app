package cn.net.prot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Prot01Login extends ProtBase {
	private final short iTaskCode1 = 1;
	/**
	 * 是否成功
	 */
	private short sResult;
	/**
	 * 错误码
	 */
	private short sErrCode;

	/**
	 * 企业Id
	 */
	private int iCompanyId;
	/**
	 * 产品Id
	 */
	private long iProductId;
	/**
	 * 企业工作模式：0 追溯 1 防伪
	 */
	private short iworkStyle;

	/**
	 * imei号
	 */
	private String sInspectorId;

	/**
	 * 1.消费者app登陆
	 * <p>
	 * 2014-12-23下午2:08:15
	 * </p>
	 * 
	 * @param socket
	 * @param dis
	 * @param dos
	 * @param iLabelId
	 *            消费者，输入
	 * @return
	 * @throws IOException
	 */
	public void dealCpzsLogin(Socket socket, DataInputStream dis,
			DataOutputStream dos, long iLabelId) throws IOException {
		short iUserType = 0;// 用户类型: 0-消费者 1-稽查者 2-终端设备
		int iCompanyId = 0; // 企业Id
		long iDeviceId = 0L;// 设备Id必须
		String sAccount = "0";
		String sPassword = "0";
		recProt(socket, dos, dis, iUserType, iLabelId, iCompanyId, iDeviceId,
				sAccount, sPassword);
	}

	/**
	 * 1.稽查用户登陆
	 * <p>
	 * 2014-12-23下午2:27:06
	 * </p>
	 * 
	 * @param socket
	 * @param dis
	 * @param dos
	 * @param iCompanyId
	 * @return
	 * @throws IOException
	 */
	public void dealCpzsCheck(Socket socket, DataInputStream dis,
			DataOutputStream dos, int iCompanyId) throws IOException {
		long iLabelId = 0l;
		short iUserType = 1;// 用户类型: 0-消费者 1-稽查者 2-终端设备
		long iDeviceId = 0L;// 设备Id必须
		String sAccount = "0";
		String sPassword = "0";
		recProt(socket, dos, dis, iUserType, iLabelId, iCompanyId, iDeviceId,
				sAccount, sPassword);
	}

	/**
	 * 1.终端机登陆
	 * <p>
	 * 2014-12-23下午2:49:40
	 * </p>
	 * 
	 * @param socket
	 * @param dis
	 * @param dos
	 * @param iDeviceId
	 *            设备id
	 * @return
	 * @throws IOException
	 */
	public void dealCpzsHandset(Socket socket, DataInputStream dis,
			DataOutputStream dos, long iDeviceId) throws IOException {
		short iUserType = 2;// 用户类型: 0-消费者 1-稽查者 2-终端设备
		long iLabelId = 0l;
		int iCompanyId = 0;
		String sAccount = "0";
		String sPassword = "0";
		recProt(socket, dos, dis, iUserType, iLabelId, iCompanyId, iDeviceId,
				sAccount, sPassword);
	}

	/**
	 * 1.登陆协议
	 * <p>
	 * 2014-12-23下午1:59:12
	 * </p>
	 * 
	 * @param socket
	 * @param dos
	 * @param dis
	 * @param iUserType
	 *            用户类型: <br>
	 *            0:消费者<br>
	 *            1:稽查用户 <br>
	 *            2:终端设备 <br>
	 *            3:监管用户<br>
	 * @param iLabelId
	 *            消费用户直接用标签号登录
	 * @param iCompanyId
	 *            企业Id
	 * @param iDeviceId
	 *            设备用户Id
	 * @param sAccount
	 *            监管登录用户
	 * @param sPassword
	 *            监管登录密码
	 * @return
	 * @throws IOException
	 */
	private void recProt(Socket socket, DataOutputStream dos,
			DataInputStream dis, short iUserType, long iLabelId,
			int iCompanyId, long iDeviceId, String sAccount, String sPassword)
			throws IOException {
		sInspectorId = "0";
		if (socket.isConnected()) {
			if (!socket.isOutputShutdown()) {
				sendProt(dos, iUserType, iLabelId, iCompanyId, iDeviceId,
						sInspectorId, sAccount, sPassword);
			}
		}
		if (socket.isConnected()) {
			if (!socket.isInputShutdown()) {
				recHeader(dis);
				debugLog(iTaskCode1, toStringRecHeader());
				recLoginDis(dis);
				debugLog(iTaskCode1, toString());
				recCheckCode(dis);
				if (getsResult() == 0) {
					// 成功
					debugLog(iTaskCode1, "读取成功");
				} else {
					if (getsResult() == 1) {

						String ret = "很遗憾，发生异常，异常码为(" + getsErrCode() + ")";
						errLog(iTaskCode1, ret);
					} else {
						// 未知错误
						errLog(iTaskCode1, "un err");
					}
				}

			}
		}
	}

	private void recLoginDis(DataInputStream dis) throws IOException {
		this.sResult = dis.readShort();
		this.sErrCode = dis.readShort();
		this.iCompanyId = dis.readInt();
		this.iProductId = dis.readLong();
		this.iworkStyle = dis.readShort();
	}

	/**
	 * 1.登录 发送写一部分
	 * 
	 * @param dos
	 * @param iUserType
	 *            用户类型: 0-消费者 1-稽查者 2-终端设备
	 * @param iLabelId标签号
	 * @param iCompanyId企业Id
	 * @param iDeviceId
	 *            设备Id
	 * @param sUserTag用户唯一标识码
	 *            String 32
	 * @param sAccount
	 *            监管登录用户String() 16
	 * @param sPassword
	 *            监管登录密码long
	 * @throws IOException
	 * 
	 */
	private void sendProt(DataOutputStream dos, short iUserType, long iLabelId,
			int iCompanyId, long iDeviceId, String sUserTag, String sAccount,
			String sPassword) throws IOException {

		byte[] sUserTags = sUserTag.getBytes();
		byte[] sendUserTags = new byte[32];
		// 后面补零，服务器遇0认为是空
		System.arraycopy(sUserTags, 0, sendUserTags, 0, sUserTags.length);

		// sAccount 16位 监管登录用户
		byte[] sendAccount = new byte[16];
		System.arraycopy(sAccount.getBytes(), 0, sendAccount, 0,
				sAccount.getBytes().length);

		// sPassword监管登录密码
		byte[] sendPassword = new byte[8];
		System.arraycopy(sPassword.getBytes(), 0, sendPassword, 0,
				sPassword.getBytes().length);

		short bodySize = countBodySize(iUserType, iLabelId, iCompanyId,
				iDeviceId, sendUserTags, sendAccount, sendPassword);

		sendHeader(dos, iTaskCode1, bodySize);// 84
		// 用户类型: 0-消费者 1-稽查者 2-终端设备
		dos.writeShort(iUserType);
		// 标签号
		dos.writeLong(iLabelId);
		// 公司ID
		dos.writeInt(iCompanyId);
		// 设备id
		dos.writeLong(iDeviceId);
		// 稽查id
		dos.write(sendUserTags);
		// 登陆账户
		dos.write(sendAccount);
		// 登陆账密码
		dos.write(sendPassword);
		sendCheckCode(dos);
	}

	public short getsResult() {
		return sResult;
	}

	public void setsResult(short sResult) {
		this.sResult = sResult;
	}

	public short getsErrCode() {
		return sErrCode;
	}

	public void setsErrCode(short sErrCode) {
		this.sErrCode = sErrCode;
	}

	public int getiCompanyId() {
		return iCompanyId;
	}

	public void setiCompanyId(int iCompanyId) {
		this.iCompanyId = iCompanyId;
	}

	public long getiProductId() {
		return iProductId;
	}

	public void setiProductId(long iProductId) {
		this.iProductId = iProductId;
	}

	public short getIworkStyle() {
		return iworkStyle;
	}

	public void setIworkStyle(short iworkStyle) {
		this.iworkStyle = iworkStyle;
	}

	public String getsInspectorId() {
		return sInspectorId;
	}

	public void setsInspectorId(String sInspectorId) {
		this.sInspectorId = sInspectorId;
	}

	@Override
	public String toString() {
		return "Prot01Login [sResult=" + sResult + ", sErrCode=" + sErrCode
				+ ", iCompanyId=" + iCompanyId + ", iProductId=" + iProductId
				+ ", iworkStyle=" + iworkStyle + ", sInspectorId="
				+ sInspectorId + "]";
	}

}
