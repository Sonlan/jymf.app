package cn.net.prot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Prot02Logout extends ProtBase {

	private final short taskCode = 2;
	private String sInspectorId;


	/**
	 * 2.退出
	 * 
	 * @param socket
	 * @param dis
	 * @param dos
	 * @param sUserTag
	 *            客户端设备唯一标识码
	 */

	public void dealLogout(Socket socket, DataInputStream dis,
			DataOutputStream dos) {

		sInspectorId = "0";
		if (socket == null) {
			return;
		}
		if (dos != null) {
			try {
				Thread.sleep(100);
				sendLogOut(dos, sInspectorId);
				dos.close();
			} catch (Exception e) {
				e.printStackTrace();
				errLog(taskCode, "dos:" + e.toString());
			}
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException e) {
					e.printStackTrace();
					errLog(taskCode, "dis:" + e.toString());
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
					errLog(taskCode, "socket:" + e.toString());
				}
			}
		}
		debugLog(taskCode, "Logout");
	}

	/**
	 * 2.退出
	 * 
	 * @param dos
	 * @param sUserID
	 *            用户id,这里传手机号码上来
	 * @throws IOException
	 */
	private void sendLogOut(DataOutputStream dos, String sUserID)
			throws IOException {
		byte[] sUserTags = sUserID.getBytes();
		byte[] sendUserTags = new byte[32];
		System.arraycopy(sUserTags, 0, sendUserTags, 0, sUserTags.length);
		short bodySize = countBodySize(sendUserTags);
		sendHeader(dos, taskCode, bodySize);
		dos.write(sendUserTags);
		sendCheckCode(dos);
	}

}
