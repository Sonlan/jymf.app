package cn.net.prot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

public class SocketCreate {

	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private boolean result = false;// 登录是否成功
	private int iWorkStyle = 0;// 工作模式
	private int iCompanyId = 0;// 公司ID
	private long iProductId = 0;// 产品ID
	private long iLabel = 0;//标签

	private final short userTypeCons = 0;// 消费者
	private final short userTypeCheck = 1;// 稽查
	private final short userTypeHandset = 2;// 终端
	private final short userTypeSuper = 3;// 终端
	private long iDeviceId;

	/**
	 * 消费者app登陆
	 * <p>
	 * 2014-12-24下午4:41:22
	 * </p>
	 * 
	 * @param context
	 * @param labelId
	 * @param mHandler
	 * @param ip
	 * @param port
	 * @param timeOut
	 * @throws IOException
	 */
	public void createLoginCpzs(long labelId, String ip, int port)
			throws IOException {
		int timeOut = 1000 * 30;
		// 消费者app登陆
		result = createServeConnect(labelId, ip, port, timeOut, userTypeCons);
	}

	/**
	 * 稽查app登陆
	 * <p>
	 * 2014-12-30下午1:23:31
	 * </p>
	 * 
	 * @param ip
	 * @param port
	 * @param timeOut
	 * @param iCompanyId
	 * @throws IOException
	 */
	public void createLoginCpzsCheck(String ip, int port, int timeOut,
			int iCompanyId) throws IOException {
		this.iCompanyId = iCompanyId;
		result = createServeConnect(0L, ip, port, timeOut, userTypeCheck);
	}

	/**
	 * 终端app登陆
	 * <p>
	 * 2014-12-30下午1:22:53
	 * </p>
	 * 
	 * @param ip
	 * @param port
	 * @param timeOut
	 * @param iDeviceId
	 * @throws IOException
	 */
	public void createLoginCpzsHandset(String ip, int port, int timeOut,
			long iDeviceId) throws IOException {
		this.iDeviceId = iDeviceId;
		result = createServeConnect(0L, ip, port, timeOut, userTypeHandset);
	}

	/**
	 * 2014-5-23下午1:13:57
	 * 
	 * @param context
	 * @param laelId
	 * @param mHandler
	 * @return 返回true，Login成功；false:无网络，login失败,，Socket没有关闭(不需要重新建立连接)
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public boolean createServeConnect(long labelId, String ip, int port,
			int timeOut, int iUserType) throws IOException {
		this.iLabel = labelId;
		try {
			if (!createSocketConnect(ip, port, timeOut)) {
				// 错误直接返回
				return false;
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
			System.out.println("链接超时");
		}
		Prot01Login prot01Login = new Prot01Login();
		switch (iUserType) {
		case userTypeCons:
			// 消费者app
			prot01Login.dealCpzsLogin(socket, dis, dos, labelId);
			break;
		case userTypeCheck:
			// 稽查app
			prot01Login.dealCpzsCheck(socket, dis, dos, iCompanyId);
			break;
		case userTypeHandset:
			// 终端设备 app
			prot01Login.dealCpzsHandset(socket, dis, dos, iDeviceId);
			break;
		case userTypeSuper:
			// 监管用户 app--暂时用不到

			break;

		default:
			break;
		}

		if (prot01Login.getsErrCode() == 0) {
			result = true;
			iWorkStyle = prot01Login.getIworkStyle();
			iCompanyId = prot01Login.getiCompanyId();
			iProductId = prot01Login.getiProductId();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 普通登录，没有用户名和密码
	 * 
	 * @param context
	 * @param mHandler
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private boolean createSocketConnect(String ip, int port, int timeOut)
			throws TimeoutException {
		try {
			socket = new Socket(ip, port);
			socket.setSoTimeout(timeOut);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (ConnectException e) {
			// 连接错误
			System.out.println("连接错误");
			return false;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Socket getSocket() {
		return socket;
	}

	public DataInputStream getDis() {
		return dis;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	public boolean isResult() {
		return result;
	}

	public int getiWorkStyle() {
		return iWorkStyle;
	}

	public void setiWorkStyle(int iWorkStyle) {
		this.iWorkStyle = iWorkStyle;
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
public long getiLabel() {
	return iLabel;
}
}
