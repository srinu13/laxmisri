package dct.com.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonObject;

public class ReadingPcapJson {

	public static Map<String, List<JSONObject>> msgNameandListMap = new HashMap<String, List<JSONObject>>();
	public static Map<String, List<Map<String, List<JSONObject>>>> groupNmandMsgMap = new HashMap<String, List<Map<String, List<JSONObject>>>>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONObject readings1apMessage() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject root = new JSONObject();
		try {
			String s1apCmd = "tshark -2 -R \"s1ap\" -r test_epc.pcap -T json >s1ap.json";
			Utils.cmdExecuter(s1apCmd);

			String currentDir = System.getProperty("user.dir");
			Object obj = parser.parse(new FileReader(currentDir + "/s1ap.json"));
			JSONArray json = (JSONArray) obj;

			for (int i = 0; i < json.size(); i++) {
				JSONObject elem = (JSONObject) json.get(i);
				JSONObject _source = (JSONObject) elem.get("_source");

				JSONObject layers = (JSONObject) _source.get("layers");
				JSONObject s1ap = (JSONObject) layers.get("s1ap");

				JSONObject s1ap_S1AP_PDU_tree = (JSONObject) s1ap.get("s1ap.S1AP_PDU_tree");
				JSONObject s1ap_Message_element = gets1apMessageElememt(s1ap_S1AP_PDU_tree);

				JSONObject s1ap_value_element = (JSONObject) s1ap_Message_element.get("s1ap.value_element");
				Set<?> value = s1ap_value_element.keySet();

				String messageName = null;
				for (Iterator<?> j = value.iterator(); j.hasNext();) {
					String s1ap_messageName = (String) j.next();

					messageName = s1ap_messageName.substring(s1ap_messageName.indexOf(".") + 1,
							s1ap_messageName.indexOf("_"));
				}
				putMessgaeNameandHexDump(messageName, s1ap);
			}
			putGroupNameNMsgMap();
			ipsrc();

			Map<String, Object> femto1 = new HashMap<String, Object>();
			femto1.put("femto1", groupNmandMsgMap);
			femto1.put("femto2", "femto2");

			
			Map<String, Map> rootmap = new HashMap<String, Map>();
			rootmap.put("s1ap", femto1);
			rootmap.put("GTP", ipNmanddumpListMap);
			rootmap.put("IPSec", IPSec_JSON.IPSecParsing());

			root.put("Root", rootmap);
			System.out.println("Root--->\n" + root);

			writeJsontoFile(root);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return root;
	}

	public static void putMessgaeNameandHexDump(String messageName, JSONObject s1ap) {
		try {
			if (msgNameandListMap.containsKey(messageName)) {
				List<JSONObject> a = msgNameandListMap.get(messageName);
				a.add(s1ap);
				msgNameandListMap.put(messageName, a);

			} else {
				List<JSONObject> a = new ArrayList<JSONObject>();
				a.add(s1ap);
				msgNameandListMap.put(messageName, a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void putGroupNameNMsgMap() {
		try {
			if (msgNameandListMap != null) {
				for (java.util.Map.Entry<String, List<JSONObject>> entry : msgNameandListMap.entrySet()) {
					String msgname = entry.getKey();
					String groupName = Utils.getUESpecificname(msgname);

					if (groupNmandMsgMap.containsKey(groupName)) {
						Map<String, List<JSONObject>> m = new HashMap<String, List<JSONObject>>();
						m.put(msgname, entry.getValue());
						List<Map<String, List<JSONObject>>> msglist = groupNmandMsgMap.get(groupName);
						msglist.add(m);
						groupNmandMsgMap.put(groupName, msglist);
					} else {
						Map<String, List<JSONObject>> m = new HashMap<String, List<JSONObject>>();
						m.put(msgname, entry.getValue());
						List<Map<String, List<JSONObject>>> msglist = new ArrayList<Map<String, List<JSONObject>>>();
						msglist.add(m);
						groupNmandMsgMap.put(groupName, msglist);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JSONObject gets1apMessageElememt(JSONObject obj) {
		JSONObject s1ap_Message_element;
		if ((JSONObject) obj.get("s1ap.initiatingMessage_element") != null)
			return s1ap_Message_element = (JSONObject) obj.get("s1ap.initiatingMessage_element");
		else if ((JSONObject) obj.get("s1ap.successfulOutcome_element") != null) {
			return s1ap_Message_element = (JSONObject) obj.get("s1ap.successfulOutcome_element");
		}
		return null;
	}

	private static void writeJsontoFile(JSONObject rootJson) throws IOException {
		String currentDir = System.getProperty("user.dir");
		FileWriter fileWriter = null;
		try {
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss", Locale.US).format(new Date());
			fileWriter = new FileWriter(currentDir + "/" + "root_" + timeStamp + ".json");
			fileWriter.write(rootJson.toJSONString());
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			fileWriter.close();
		}

	}

	public static Map<String, List<JSONObject>> ipNmanddumpListMap = new HashMap<String, List<JSONObject>>();

	public static void ipsrc() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("/home/user/Desktop/gtp1_Two_data"));
		JSONArray json = (JSONArray) obj;
		for (int i = 0; i < json.size(); i++) {
			JSONObject elem = (JSONObject) json.get(i);
			JSONObject _source = (JSONObject) elem.get("_source");
			JSONObject layers = (JSONObject) _source.get("layers");
			JSONObject ip = (JSONObject) layers.get("ip");
			JSONObject gtp = (JSONObject) layers.get("gtp");
			String ip_src = (String) ip.get("ip.src");

			if (ipNmanddumpListMap.containsKey(ip_src)) {
				List<JSONObject> l = ipNmanddumpListMap.get(ip_src);
				l.add(gtp);
				ipNmanddumpListMap.put(ip_src, l);
			} else {
				List<JSONObject> l = new ArrayList<JSONObject>();
				l.add(gtp);
				ipNmanddumpListMap.put(ip_src, l);
			}

		}
		
	}

}
