package cn.net.prot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 通讯协议帮助类
 * <p>
 * 1.发送头文件
 * </p>
 * <p>
 * 2.发送验证信息
 * </p>
 * 
 * @author zwj
 * 
 *         2014-10-22
 */
public class ProtBase {
	protected short iErrTaskCode = (short) 0xFFFF;// 错误码
	private static int iSendTaskIndex = 1;
	private final static boolean isDebug = false;
	private final static boolean isInfo = false;
	private final static boolean isErr = false;
	private final String strTag = "@JYMF";

	/**
	 * 任务序号，客户端决定，服务器不解释
	 */
	private int iRecTaskIndex;
	/**
	 * 任务子序号
	 */
	private short iRecTaskSubIndex;
	/**
	 * 任务代码
	 */
	private short iRecTaskCode;
	/**
	 * 包括包尾2字节长度
	 */
	private short iRecBodySize;
	/**
	 * 0：成功 1：失败
	 */
	private short iRecResult;
	/**
	 * 失败码，参考附录1错误代码表
	 */
	private short iRecErrcode;

	public static final class WorkType {
		/**追溯*/
		public static final int Default = 0;
		/**工厂*/
		public static final int Factory = 1;
		/**商城*/
		public static final int Mall = 2;
		/**汽配*/
		public static final int Car = 3;
		/**书*/
		public static final int Book = 4;
		/**佛教*/
		public static final int Buddha = 5;
		/**酒*/
		public static final int Wine = 6;
		/**快消品*/
		public static final int FastCons = 7;
		/**红酒城*/
		public static final int RedWine = 10;
		
	
	}

	/**
	 * 获取头信息
	 * 
	 * @param dos
	 *            DataOutputStream
	 * @param iTaskCode
	 *            任务代码
	 * @param iBodySize
	 *            包括包尾2字节长度
	 * @throws IOException
	 *             2014-10-22下午1:54:34
	 */
	public void sendHeader(DataOutputStream dos, short iTaskCode, short iBodySize) throws IOException {
		dos.write(strTag.getBytes("UTF-8"));// sTag
		dos.writeInt(iSendTaskIndex++);// iTaskIndex
		dos.writeShort(0);// iTaskSubIndex
		dos.writeShort(iTaskCode);// iTaskCode任务号
		dos.writeShort(iBodySize);// iBodySize
	}
	
	/**
	 * 发送标签 <br/>
	 * 2015-4-1下午4:20:27
	 * 
	 * @param dos
	 * @param iLabel
	 *            16位追溯码
	 * @throws IOException
	 */
	protected void sendiLabel(DataOutputStream dos, long iLabel)
			throws IOException {
		dos.writeLong(iLabel);
	}

	/**
	 * 发送公司id
	 * 
	 * @param dos
	 * @param iCompanyId
	 *            公司id
	 * @throws IOException
	 */
	protected void sendiCompanyId(DataOutputStream dos, int iCompanyId)
			throws IOException {
		dos.writeInt(iCompanyId);
	}

	/**
	 * 产品id
	 * 
	 * @param dos
	 * @param iProductId
	 *            产品id
	 * @throws IOException
	 */
	protected void sendiProductId(DataOutputStream dos, long iProductId)
			throws IOException {
		dos.writeLong(iProductId);
	}

	/**
	 * 发送地区号
	 * 
	 * @param dos
	 * @param iAreaCode
	 *            地区号
	 * @throws IOException
	 */
	protected void sendiAreaCode(DataOutputStream dos, int iAreaCode)
			throws IOException {
		dos.writeInt(iAreaCode);
	}


	/**
	 * 接收数据
	 * <p>
	 * 2014-12-24上午10:00:30
	 * </p>
	 * 
	 * @param dis
	 * @throws IOException
	 */
	public void recHeader(DataInputStream dis) throws IOException {
		byte[] sTag = new byte[5];
		int sTagLength = sTag.length;
		for (int i = 0; i < sTagLength; i++) {
			sTag[i] = dis.readByte();
		}
		if (!Arrays.equals(sTag, strTag.getBytes("UTF-8"))) {
			errLog("recHeader", "recHeader-->" + Arrays.toString(sTag));
		}

		this.iRecTaskIndex = dis.readInt();
		this.iRecTaskSubIndex = dis.readShort();
		this.iRecTaskCode = dis.readShort();
		this.iRecBodySize = dis.readShort();
	}

	/**
	 * 打印打印的header信息
	 * <p>
	 * 2014-12-24上午10:18:53
	 * </p>
	 * 
	 * @return
	 */
	protected String toStringRecHeader() {
		return "BaseProt [iRecTaskIndex=" + iRecTaskIndex + ", iRecTaskSubIndex=" + iRecTaskSubIndex
				+ ", iRecTaskCode=" + iRecTaskCode + ", iRecBodySize=" + iRecBodySize + "]";
	}

	/**
	 * 判断接受和发送的iTaskCode是否一致
	 * <p>
	 * 2014-12-24上午10:38:44
	 * </p>
	 * 
	 * @param send
	 *            发送的协议号
	 * @return
	 */
	protected boolean isTaskCode(short send) {
		return getiRecTaskCode() == send;
	}

	/**
	 * 判断是否为通用错误包,是不是等于0xFFFF
	 * <p>
	 * 2014-12-24上午10:37:06
	 * </p>
	 * 
	 * @return
	 */
	protected boolean isFFFF() {
		return getiRecTaskCode() == iErrTaskCode;
	}

	/**
	 * 读取0XFFFF通讯包<br>
	 * 读取到checkCode
	 * <p>
	 * 2014-12-29下午5:12:53
	 * </p>
	 * 
	 * @param dis
	 * @throws IOException
	 */
	protected void recFFFF(DataInputStream dis) throws IOException {
		iRecResult = dis.readShort();
		iRecErrcode = dis.readShort();
		recCheckCode(dis);
	}
	
	/**
	 * 获取销售地区的code <br/>
	 * 2015-4-1下午4:33:14
	 * 
	 * @param dis
	 * @return
	 * @throws IOException
	 */
	protected int reciAreadCode(DataInputStream dis) throws IOException {
		return dis.readInt();
	}

	/**
	 * 读取产品Id <br/>
	 * 2015-4-1下午4:34:37
	 * 
	 * @param dis
	 * @return
	 * @throws IOException
	 */
	protected long reciProductId(DataInputStream dis) throws IOException {
		return dis.readLong();
	}

	/**
	 * 接收公司id <br/>
	 * 2015-4-2下午4:50:40
	 * 
	 * @param dis
	 * @return
	 * @throws IOException
	 */
	protected int reciCompanyId(DataInputStream dis) throws IOException {
		return dis.readInt();
	}

	/**
	 * 接收公司id <br/>
	 * 2015-4-2下午4:50:40
	 * 
	 * @param dis
	 * @return
	 * @throws IOException
	 */
	protected long reciLabel(DataInputStream dis) throws IOException {
		return dis.readLong();
	}

	/**
	 * 一个字节一个字节读取
	 * 
	 * @param dis
	 * @param length
	 *            数据的长度
	 * @return
	 * @throws IOException
	 */
	protected byte[] recString(DataInputStream dis, int length)
			throws IOException {
		byte[] bs = new byte[length];
		for (int i = 0; i < length; i++) {
			bs[i] = dis.readByte();
		}
		return bs;
	}

	/**
	 * 一个字节一个字节读取，转换成utf-8形式的String <br/>
	 * 2015-7-15下午4:07:50
	 * 
	 * @param dis
	 * @param length
	 *            数据的长度
	 * @return
	 * @throws IOException
	 */
	protected String getUTF8(DataInputStream dis, int length)
			throws IOException {
		return new String(recString(dis, length), "UTF-8");
	}
	
	private boolean success = false;

	/**
	 * 错误包处理 <br/>
	 * 2015-4-2上午10:26:59
	 * 
	 * @param dis
	 * @return
	 * @throws IOException
	 */
	protected boolean recFFFF(DataInputStream dis, short iTaskCode) throws IOException {
		if (getiRecTaskCode() == iErrTaskCode) {
			// -1错误包
			iRecResult = dis.readShort();
			iRecErrcode = dis.readShort();
			recCheckCode(dis);
			if (this.iRecResult == 0) {
				// 成功
				this.success = true;
			} else if (this.iRecResult == 1) {
				// 错误
				this.success = false;
				errLog(iTaskCode, "iRecResult:" + iRecResult + " ; iRecErrcode:" + iRecErrcode);
				// TODO 根据错误码来返回错误信息
				switchErrCode(iTaskCode, this.iRecErrcode);
			} else {
				this.success = false;
				// 未知的错误
				errLog(iTaskCode, "iRecResult err :" + iRecResult);
			}
		} else {
			// 未知错误包
			this.success = false;
			errLog(iTaskCode, "unknow err");
		}
		return this.success;
	}

	/**
	 * 错误码返回机制 <br/>
	 * 2015-4-2下午4:21:12
	 * 
	 * @param iTaskCode
	 * @param iErrCode
	 */
	public void switchErrCode(short iTaskCode, short iErrCode) {
		int key = getUnsignedByte(iErrCode);
		// switch (key) {
		// case value:
		//
		// break;
		//
		// default:
		// break;
		// }
	}

	/**
	 * 发送验证码
	 * 
	 * @param dos
	 * @throws IOException
	 */
	public void sendCheckCode(DataOutputStream dos, Object... objs) throws IOException {
		dos.writeShort(0);
	}

	/**
	 * 读取iCheckCode
	 * 
	 * @param dis
	 * @return
	 * @throws IOException
	 */
	public boolean recCheckCode(DataInputStream dis, Object... objs) throws IOException {
		dis.readShort();
		return true;
	}

	/**
	 * 统计bodySize 2014-6-24上午10:22:59
	 * 
	 * @param objs
	 * @return
	 */
	public short countBodySize(Object... objs) {
		int checkCodeSize = 2;// 验证码的2位
		int size = checkCodeSize;
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] instanceof Integer) {
				size = size + 4;
			} else if (objs[i] instanceof Short) {
				size = size + 2;
			} else if (objs[i] instanceof Long) {
				size = size + 8;
			} else if (objs[i] instanceof byte[]) {
				size = size + ((byte[]) objs[i]).length;
			} else if (objs[i] instanceof long[]) {
				size = size + ((long[]) objs[i]).length * 8;// 手持终端用到了这个
			} else if (objs[i] instanceof String) {
				// 适配String类型
				size = size + String.valueOf(objs[i]).getBytes().length;
			}
		}
		return (short) size;
	}

	/**
	 * 将data字节型数据转换为0~65535 (0xFFFF 即 WORD)。
	 * 
	 * @param data
	 * @return
	 */
	public int getUnsignedByte(short data) {
		return data & 0x0FFFF;
	}

	/**
	 * 展示log
	 * 
	 * @param taskCode
	 *            任务号，或者是位置信息
	 * @param obj
	 *            2014-10-22上午9:23:22
	 */
	public void debugLog(Object taskCode, Object obj) {
		if (isDebug) {
			System.out.println(">" + taskCode.toString() + "<" + obj.toString());
		}
	}

	/**
	 * 临时展示
	 * 
	 * @param obj
	 *            2014-10-22上午9:24:59
	 */
	public static void infoLog(Object obj) {
		if (isInfo) {
			System.out.println(obj.toString());
		}
	}

	/**
	 * 错误信息
	 * 
	 * @param obj
	 *            2014-10-22上午9:24:59
	 */
	public void errLog(Object taskCode, Object obj) {
		if (isErr) {
			System.out.println(">" + taskCode.toString() + "<" + obj.toString());
		}
	}

	// ---------------------------------
	public int getiRecTaskIndex() {
		return iRecTaskIndex;
	}

	public short getiRecTaskSubIndex() {
		return iRecTaskSubIndex;
	}

	public short getiRecTaskCode() {
		return iRecTaskCode;
	}

	public short getiRecBodySize() {
		return iRecBodySize;
	}

	// ----------------------
	/**
	 * 0：成功 1：失败
	 */
	public short getiRecResult() {
		return iRecResult;
	}

	public short getiRecErrcode() {
		return iRecErrcode;
	}

	public String toStringFFFF() {
		return "BaseProt [iRecResult=" + iRecResult + ", iRecErrcode=" + iRecErrcode + "]";
	}
	
	/**
	 * 是否成功 <br/>
	 * 2015-4-1下午5:15:42
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 设置是否成功 <br/>
	 * 2015-4-1下午5:16:02
	 * 
	 * @param isSuccess
	 */
	public void setSuccess(boolean isSuccess) {
		this.success = isSuccess;
	}
}
