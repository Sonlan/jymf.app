package cn.net.prot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSONException;

public class Prot09UpDown extends ProtBase {

	private final short taskCode9 = 9;
	private final short taskCode901 = 901;// 汽配使用
	private final short taskCode902 = 902;// 汽配wap使用
	private final short typeUP = 1;// 取父标签
	private final short typeDOWN = 2;// 取子标签
	// 901和902共有
	/**
	 * 配件名称
	 */
	public static final String kName = "name";
	/**
	 * 配件id
	 */
	public static final String kProductID = "productID";

	// --------------901----------------
	/**
	 * 追溯码901
	 */
	public static final String kLabelID = "labelID";
	// --------------902----------------
	/**
	 * producer":"902"
	 */
	public static final String kProducer = "producer";
	/**
	 * number_id:902
	 */
	public static final String kNumber_id = "number_id";
	/**
	 * number:902
	 */
	public static final String kNumber = "number";

	private List<Long> list09 = null;
	private List<Map<String, String>> list901 = null;// 汽配使用
	// 902：获取父标签/子标签操作,同一产品返回一次(汽配模式)
	private List<Map<String, String>> list902 = null;// 汽配wap使用

	/**
	 * 发送协议：获取包和子的协议9和901通用头文件
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
	private void sendProt(DataOutputStream dos, short iTaskCode, long label,
			short shType) throws IOException {
		short iBodySize = countBodySize(label, shType);
		sendHeader(dos, iTaskCode, iBodySize);
		dos.writeLong(label);
		dos.writeShort(shType);
		sendCheckCode(dos);
	}

	private void recProt(DataInputStream dis, DataOutputStream dos,
			short iTaskCode, long label, short shType) throws IOException {
		sendProt(dos, iTaskCode, label, shType);
		List<Long> result = new ArrayList<Long>();
		List<Map<String, String>> list901 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> list902 = new ArrayList<Map<String, String>>();
		do {
			recHeader(dis);
			debugLog(iTaskCode, toStringRecHeader());
			if (getiRecTaskCode() == taskCode901) {
				// 901
				if (getiRecBodySize() <= 2) {
					set901List(null);
					// 这里没数据
					recCheckCode(dis);
					break;
				} else {
					int bodySize = getUnsignedByte(getiRecBodySize()) - 2;
					byte[] jsons = new byte[bodySize];
					for (int i = 0; i < jsons.length; i++) {
						jsons[i] = dis.readByte();
					}
					recCheckCode(dis);
					String jsonData = new String(jsons, "utf-8");

					list901.addAll(parse901Json(jsonData));
					debugLog(getiRecTaskSubIndex(), "----json----" + jsonData);
					if (getiRecTaskSubIndex() <= 0) {
						set901List(list901);
					}
				}

			} else if (getiRecTaskCode() == taskCode9) {
				// 9
				// 普通模式
				for (int i = 0; i < (getiRecBodySize() - 2) / 8; i++) {
					long label_16 = dis.readLong();
					if (label_16 == 0) {
						result.add(0L);
					} else {
						result.add(label_16);
					}
				}
				recCheckCode(dis);
				if (getiRecTaskSubIndex() <= 0) {
					// 证明里面没有数据
					break;
				}
			} else if (getiRecTaskCode() == taskCode902) {
				// 902
				if (getiRecBodySize() <= 2) {
					set902List(null);
					// 这里没数据
					recCheckCode(dis);
					break;
				} else {
					int bodySize = getUnsignedByte(getiRecBodySize()) - 2;
					byte[] jsons = new byte[bodySize];
					for (int i = 0; i < jsons.length; i++) {
						jsons[i] = dis.readByte();
					}
					recCheckCode(dis);
					String jsonData = new String(jsons, "utf-8");

					list902.addAll(parse902Json(jsonData));
					debugLog(getiRecTaskSubIndex(), "----json902----"
							+ jsonData);
					if (getiRecTaskSubIndex() <= 0) {
						set902List(list902);
					}
				}

			} else if (isFFFF()) {
				// 服务器返回错误报,当taskCode为-1的时候，才是正确的错误包
				recFFFF(dis);
				// 服务器回馈错误
				debugLog(iTaskCode, toStringFFFF());
				break;
			} else {
				// 未知错误
				break;
			}

			try {
				// 休息1ms
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (getiRecTaskSubIndex() > 0);

		if (iTaskCode == taskCode901) {
			// 901不需要走这里
			return;
		}

		// 这里是901协议后续操作
		if (result.size() != 0 && result.get(0) != 0) {
			// 有数据,并且第0个数据不为0
			switch (shType) {
			case 1:
				// 1，获取父标签
				set09List(result);
				break;
			case 2:
				// 2，获取子标签
				set09List(result);
				break;
			default:
				break;
			}
		} else {
			// 没数据
			switch (shType) {
			case 1:
				// 1，没有获取父标签；
				break;
			case 2:
				// 2，没有获取子标签
				break;
			default:
				break;
			}
		}

	}

	/**
	 * 901汽配<br>
	 * Json解析数据 JSon格式(6)
	 * JSon格式[{"labelID":"XXX","name":"XXX",”productID”:”XXX”} …]
	 * <p>
	 * 2014-10-30上午11:33:17
	 * </p>
	 * 
	 * @param jsonStr
	 * @throws JSONException
	 */
	private List<Map<String, String>> parse901Json(String jsonData) {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, String>> list;
		try {
			list = mapper.readValue(jsonData, List.class);
			// for (int i = 0; i < list.size(); i++) {
			// Map<String, String> map = list.get(i);
			// Set<String> set = map.keySet();
			// for (Iterator<String> it = set.iterator(); it.hasNext();) {
			// String key = it.next();
			// System.out.println(key + ":" + map.get(key));
			// }
			// }
			return list;
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
	 * 902json解析，wap使用<br>
	 * [{"name":"XXX",”number_id”:”XXX”, "producer":"XXX",”number”:”XXX”} …]
	 * 
	 * @param jsonData
	 * @return
	 */
	private List<Map<String, String>> parse902Json(String jsonData) {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, String>> list;
		try {
			list = mapper.readValue(jsonData, List.class);
			// for (int i = 0; i < list.size(); i++) {
			// Map<String, String> map = list.get(i);
			// Set<String> set = map.keySet();
			// for (Iterator<String> it = set.iterator(); it.hasNext();) {
			// String key = it.next();
			// System.out.println(key + ":" + map.get(key));
			// }
			// }
			return list;
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
	 * 提升 普通模式下的上下级 这个handler返回的数据为list<Long>
	 * <p>
	 * 2014-10-30下午2:05:48
	 * </p>
	 * 
	 * @param dis
	 * @param dos
	 * @param label
	 * @param shType
	 * @param mHandler
	 * @throws IOException
	 * @throws JSONException
	 */
	public void deal9UpDown(DataInputStream dis, DataOutputStream dos,
			String label, int shType) throws IOException {
		deal9UpDown(dis, dos, Long.valueOf(label), (short) shType);
	}

	/**
	 * 基本 普通模式下的上下级 ，这个handler返回的数据为list<Long>
	 * <p>
	 * 2014-10-30下午2:05:48
	 * </p>
	 * 
	 * @param dis
	 * @param dos
	 * @param label
	 * @param shType
	 * @param mHandler
	 * @throws IOException
	 * @throws JSONException
	 */
	public void deal9UpDown(DataInputStream dis, DataOutputStream dos,
			long label, short shType) throws IOException {
		recProt(dis, dos, taskCode9, label, shType);
	}

	/**
	 * 902这里有json解析的，为了云南汽配模式做的901 这个handler返回的数据为list<Map<String,String>>
	 * <p>
	 * 2014-10-30下午2:05:48
	 * </p>
	 * 
	 * @param dis
	 * @param dos
	 * @param label
	 * @param mHandler
	 * @throws IOException
	 * @throws JSONException
	 */
	public void deal901UpDown(DataInputStream dis, DataOutputStream dos,
			long label) throws IOException {
		short shType = typeDOWN;// 云南汽配直接查下级
		recProt(dis, dos, taskCode901, label, shType);
	}

	/**
	 * 902这里有json解析的，为了云南汽配模式做的901 这个handler返回的数据为list<Map<String,String>>
	 * <p>
	 * 2014-10-30下午2:05:48
	 * </p>
	 * 
	 * @param dis
	 * @param dos
	 * @param label
	 * @param mHandler
	 * @throws IOException
	 * @throws JSONException
	 */
	public void deal902UpDown(DataInputStream dis, DataOutputStream dos,
			long label) throws IOException {
		short shType = typeDOWN;// 云南汽配直接查下级
		recProt(dis, dos, taskCode902, label, shType);
	}

	/**
	 * 提升 这里有json解析的，为了云南汽配模式做的901 这个handler返回的数据为list<Map<String,String>>
	 * <p>
	 * 2014-10-30下午2:05:48
	 * </p>
	 * 
	 * @param dis
	 * @param dos
	 * @param label
	 * @param mHandler
	 * @throws IOException
	 * @throws JSONException
	 */
	public void deal901UpDown(DataInputStream dis, DataOutputStream dos,
			String label) throws IOException {
		deal901UpDown(dis, dos, Long.valueOf(label));
	}

	/**
	 * 获取901上下级的List<br>
	 * 
	 * @return null或者empty是为无数据。
	 */
	public List<Map<String, String>> get901List() {
		return list901;
	}

	/**
	 * 获取902上下级的List<br>
	 * 
	 * @return null或者empty是为无数据。
	 */
	public List<Map<String, String>> get902List() {
		return list901;
	}

	/**
	 * 901设置List
	 * 
	 * @param list
	 */
	private void set901List(List<Map<String, String>> list) {
		if (list == null) {
			list = new ArrayList<Map<String, String>>();
		}
		this.list901 = list;
	}

	/**
	 * 902设置List
	 * 
	 * @param list
	 */
	private void set902List(List<Map<String, String>> list) {
		if (list == null) {
			list = new ArrayList<Map<String, String>>();
		}
		this.list901 = list;
	}

	/**
	 * 09设置List
	 * 
	 * @param list
	 */
	private void set09List(List<Long> list) {
		if (list == null) {
			list = new ArrayList<Long>();
		}
		this.list09 = list;
	}

}
