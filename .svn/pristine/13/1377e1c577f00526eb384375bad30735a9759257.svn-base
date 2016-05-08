package cn.net.prot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Prot13QueryCount extends ProtBase {
	/**
	 * 终端系统类型 1000：IOS;终端 2000：Android终端; 3000：Web终端; 4000：电话;5000:微信wap
	 */
	private final short imTerminalType = 5000;
	/**
	 * 消费信息 （合并13,14协议）
	 */
	private final short iTaskCode1303 = 1303;
	/**
	 * 消费信息 （汽配，合并13,14协议）
	 */
	private final short iTaskCode1304 = 1304;
	/**
	 * 替代1302协议，稽查使用，只对数据进行查询
	 */
	private final short iTaskCode1305 = 1305;
	/**
	 * 酒类查询
	 */
	private final short iTaskCode1306 = 1306;

	/********************* 以下为暴露出来的数据 *****************************/
	/**
	 * 首次查询时间
	 */
	private long iFirstQueryTime;
	/**
	 * 消费查询次数
	 */
	private int iConsCount;
	/**
	 * 消费:第一次消费地址
	 */
	private int iFirstConsAreaCode;
	/**
	 * 消费:第一次消费时间
	 */
	private long iFirstConsTime;

	/********************* 1303**1304**1305**1306 **************************************/
	/**
	 * 封装了3中模式的查询
	 * 
	 * @author zwj
	 * @Time 2015年5月25日 上午10:49:33
	 * @param create
	 * @param iConsCheckCode
	 * @throws IOException
	 */
	public void dealConsInfoSwitch(SocketCreate create, short iConsCheckCode) throws IOException {
		DataInputStream dis = create.getDis();
		DataOutputStream dos = create.getDos();
		int iWorkType = create.getiWorkStyle();
		long iLabel = create.getiLabel();
		switch (iWorkType) {
		case WorkType.Car:
			// 汽配模式
			dealCarConsInfo1304(dis, dos, iLabel, 0, iConsCheckCode);
			break;
		case WorkType.Wine:
			// 酒
			dealWineConsInfo1306(dis, dos, iLabel, 0, iConsCheckCode);
			break;
		default:
			dealFirstConsInfo1303(dis, dos, iLabel, 0, iConsCheckCode);
			break;
		}
	}

	/**
	 * 封装了3中模式的查询
	 * 
	 * @author zwj
	 * @Time 2015年5月25日 上午10:49:33
	 * @param create
	 * @throws IOException
	 */
	public void dealQueryCountSwitch(SocketCreate create) throws IOException {
		DataInputStream dis = create.getDis();
		DataOutputStream dos = create.getDos();
		int iWorkType = create.getiWorkStyle();
		long iLabel = create.getiLabel();
		switch (iWorkType) {
		case WorkType.Car:
			// 汽配模式
			dealQueryCountCar1304(dis, dos, iLabel);
			break;
		case WorkType.Wine:
			// 酒
			dealQueryCountWine1306(dis, dos, iLabel);
			break;
		default:
			dealQueryCount1303(dis, dos, iLabel);
			break;
		}
	}

	/**
	 * 1303查询次数，增加服务器上的查询的次数 <br/>
	 * 扫描后使用该协议<br/>
	 * 2015-4-13上午10:29:23
	 * 
	 * @param dis
	 * @param dos
	 * @param iLabel
	 *            追溯码
	 * @throws IOException
	 */
	public void dealQueryCount1303(DataInputStream dis, DataOutputStream dos, long iLabel) throws IOException {
		short iTaskCode = this.iTaskCode1303;
		int iConsArea = 0;
		short iConsCheckCode = 0;
		recProt_ConsInfo(dis, dos, iTaskCode, iLabel, iConsArea, iConsCheckCode);
	}

	/**
	 * 1303.替换13号和14号协议 <br/>
	 * 2015-4-10下午4:29:58
	 * 
	 * @param dis
	 * @param dos
	 * @param iLabel
	 *            追溯码
	 * @param iConsArea
	 *            消费地区
	 * @param iConsCheckCode
	 *            消费码
	 * @throws IOException
	 */
	public void dealFirstConsInfo1303(DataInputStream dis, DataOutputStream dos, long iLabel, int iConsArea,
			short iConsCheckCode) throws IOException {
		short iTaskCode = this.iTaskCode1303;
		recProt_ConsInfo(dis, dos, iTaskCode, iLabel, iConsArea, iConsCheckCode);
	}

	/**
	 * 1304.汽配模式使用<br/>
	 * 替换1301号和14号协议 <br/>
	 * 2015-4-10下午4:29:58
	 * 
	 * @param dis
	 * @param dos
	 * @param iLabel
	 *            追溯码
	 * @param iConsArea
	 *            消费地区
	 * @param iConsCheckCode
	 *            消费码
	 * @throws IOException
	 */
	public void dealCarConsInfo1304(DataInputStream dis, DataOutputStream dos, long iLabel, int iConsArea,
			short iConsCheckCode) throws IOException {
		short iTaskCode = this.iTaskCode1304;
		recProt_ConsInfo(dis, dos, iTaskCode, iLabel, iConsArea, iConsCheckCode);
	}

	/**
	 * 查询次数
	 * 
	 * @author zwj
	 * @Time 2015年5月25日 下午1:25:55
	 * @param dis
	 * @param dos
	 * @param iLabel
	 * @throws IOException
	 */
	public void dealQueryCountCar1304(DataInputStream dis, DataOutputStream dos, long iLabel) throws IOException {
		short iTaskCode = this.iTaskCode1304;
		int iConsArea = 0;
		short iConsCheckCode = 0;
		recProt_ConsInfo(dis, dos, iTaskCode, iLabel, iConsArea, iConsCheckCode);
	}

	/**
	 * 1305.稽查使用<br/>
	 * 替换1301号和14号协议 <br/>
	 * 2015-4-14下午3:26:43
	 * 
	 * @param dis
	 * @param dos
	 * @param iLabel
	 *            追溯码
	 * @throws IOException
	 */
	public void dealCheckConsInfo1305(DataInputStream dis, DataOutputStream dos, long iLabel) throws IOException {
		short iTaskCode = this.iTaskCode1305;
		int iConsArea = 0;
		short iConsCheckCode = 0;
		recProt_ConsInfo(dis, dos, iTaskCode, iLabel, iConsArea, iConsCheckCode);
	}

	/**
	 * 1306.酒类使用 <br/>
	 * 2015-4-30下午1:29:55
	 * 
	 * @param socketCreate
	 * @param iConsArea
	 * @param iConsCheckCode
	 * @throws IOException
	 */
	public void dealWineConsInfo1306(DataInputStream dis, DataOutputStream dos, long iLabel, int iConsArea,
			short iConsCheckCode) throws IOException {
		short iTaskCode = this.iTaskCode1306;
		recProt_ConsInfo(dis, dos, iTaskCode, iLabel, iConsArea, iConsCheckCode);
	}

	/**
	 * 酒类查询次数
	 * 
	 * @author zwj
	 * @Time 2015年5月25日 下午1:27:18
	 * @param dis
	 * @param dos
	 * @param iLabel
	 * @throws IOException
	 */
	public void dealQueryCountWine1306(DataInputStream dis, DataOutputStream dos, long iLabel) throws IOException {
		short iTaskCode = this.iTaskCode1306;
		int iConsArea = 0;
		short iConsCheckCode = 0;
		recProt_ConsInfo(dis, dos, iTaskCode, iLabel, iConsArea, iConsCheckCode);
	}

	/**
	 * 1303和1304使用的发送协议 <br/>
	 * 2015-4-10下午3:55:29
	 * 
	 * @param dos
	 * @param iTaskCode
	 *            任务号
	 * @param iLabel
	 *            标签Id
	 * @param iLabelType
	 *            标签类型 0:普通标签(16位数字) 1:出版社标签(20位数字)
	 * @param iConsArea
	 *            消费地区
	 * @param iConsCheckCode
	 *            消费码
	 * @throws IOException
	 */
	private void sendProt_ConsInfo(DataOutputStream dos, short iTaskCode, long iLabel, short iLabelType, int iConsArea,
			short iConsCheckCode, short iTerminalType) throws IOException {
		short bodySize = countBodySize(iLabel, iLabelType, iConsArea, iConsCheckCode, iTerminalType);
		sendHeader(dos, iTaskCode, bodySize);
		dos.writeLong(iLabel);
		;
		dos.writeShort(iLabelType);
		dos.writeInt(iConsArea);
		dos.writeShort(iConsCheckCode);
		dos.writeShort(iTerminalType);
		sendCheckCode(dos);
	}

	/**
	 * 1303和1304 <br/>
	 * 2015-4-10下午4:26:40
	 * 
	 * @param dis
	 * @param dos
	 * @param iTaskCode
	 *            任务号
	 * @param iLabel
	 *            标签Id
	 * @param iLbsAreaCode
	 *            消费地区,本地定位
	 * @param iConsCheckCode
	 *            消费验证码
	 * @throws IOException
	 */
	private void recProt_ConsInfo(DataInputStream dis, DataOutputStream dos, short iTaskCode, long iLabel,
			int iLbsAreaCode, short iConsCheckCode) throws IOException {
		// 选择标签类型，16位和20，目前只有16位的标签
		short iLabelType = 0;
		if (String.valueOf(iLabel).length() == 16) {
			iLabelType = 0;
		} else if (String.valueOf(iLabel).length() == 20) {
			iLabelType = 1;
		}
		sendProt_ConsInfo(dos, iTaskCode, iLabel, iLabelType, iLbsAreaCode, iConsCheckCode, imTerminalType);
		recHeader(dis);

		if (isTaskCode(iTaskCode)) {
			iFirstQueryTime = dis.readLong();
			iConsCount = dis.readInt();
			iFirstConsAreaCode = dis.readInt();
			iFirstConsTime = dis.readLong();
			recCheckCode(dis);
			debugLog(iTaskCode, toString());
		} else {
			recFFFF(dis, iTaskCode);
		}
	}

	/**
	 * 是否第一次消费
	 * 
	 * @author zwj
	 * @Time 2015年5月25日 下午1:41:35
	 * @return
	 */
	public boolean isFirst() {
		return iConsCount <= 1;
	}

	/**
	 * 消费次数<br/>
	 * 当第一次的时候，消费次数为1。
	 * 
	 * @return
	 */
	public int getiConsCount() {
		return iConsCount;
	}

	/**
	 * 首次查询时间
	 */
	public long getiFirstQueryTime() {
		return iFirstQueryTime;
	}

	/**
	 * 首次查询时间，显示格式 2014年7月2日 11:49:46
	 */
	public String getFirstQueryTime() {
		return formatDate(iFirstQueryTime);
	}

	/**
	 * 消费:第一次消费地址Code
	 */
	public int getiFirstConsAreaCode() {
		return iFirstConsAreaCode;
	}

	/**
	 * 消费:第一次消费地址Name
	 */
	public String getFirstConsAreaName() {
		return AddressUtil.searchCityNameByNum(iFirstConsAreaCode);
	}

	/**
	 * 消费:第一次消费时间
	 */
	public long getiFirstConsTime() {
		return iFirstConsTime;
	}

	/**
	 * 消费:第一次消费时间,显示格式 2014年7月2日 11:49:46
	 */
	public String getFirstConsTime() {
		return formatDate(iFirstConsTime);
	}

	/**
	 * 2014年7月2日 11:49:46 <br/>
	 * yyyyMMddHHmmss===>yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	private String formatDate(long date) {
		String retStr = "";
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			retStr = formatter.format(sf1.parse(String.valueOf(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retStr;

	}

	@Override
	public String toString() {
		return "Prot13 [imTerminalType=" + imTerminalType + ", iFirstQueryTime=" + iFirstQueryTime + ", iConsCount="
				+ iConsCount + ", iFirstConsArea=" + iFirstConsAreaCode + ", iFirstConsTime=" + iFirstConsTime + "]";
	}
}
