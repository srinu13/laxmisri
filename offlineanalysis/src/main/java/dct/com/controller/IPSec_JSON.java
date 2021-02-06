package dct.com.controller;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class IPSec_JSON {
	
	public static void main(String args[]) {
		try {
			String ipsek_command="tshark -2 -R \"isakmp or esp\" -r test_epc.pcap -T json >ipsec.json";
			Utils.cmdExecuter(ipsek_command);
	        IPSecParsing();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

	public static HashMap IPSecParsing() {
		JSONParser parser=new JSONParser();
		HashMap IPSecMap=new HashMap();
        try {
        	
        	String currentDir = System.getProperty("user.dir");
			Object obj = parser.parse(new FileReader(currentDir + "/ipsec.json"));
           // Object obj = parser.parse(new FileReader("/home/user/Desktop/ipsec_Large_data.json"));
            JSONArray jo = (JSONArray) obj;
      
            FileWriter fileWriter =null;
            JSONObject treeData = new JSONObject();
            JSONObject treeChild = new JSONObject();
            JSONObject treeResponseData = new JSONObject();

            //treeData.put("text", "IPSec");
            JSONArray subArray = new JSONArray();
            JSONArray subESPArray = new JSONArray();
            JSONArray subDataArray = new JSONArray();
            JSONObject dataInfo = new JSONObject();

            for (int i = 0; i < jo.size(); i++) {
                JSONObject element=(JSONObject)jo.get(i);
                JSONObject source=(JSONObject) element.get("_source");
                JSONObject layers=(JSONObject) source.get("layers");
                JSONObject isakmp=(JSONObject)layers.get("isakmp");
                JSONObject esp=(JSONObject)layers.get("esp");
                JSONObject data = new JSONObject();
                
                if(isakmp!=null && !isakmp.isEmpty()) {
                	data.put("MessageDump", isakmp);
                    subArray.add(data);
                }
                if(esp!=null && !esp.isEmpty()) {
                	layers.remove("frame");
                	layers.remove("eth");
                	layers.remove("ip");
                	Set<String> sData=layers.keySet();
            		JSONObject treeEsp = new JSONObject();
                	for (String myVal : sData) {
                		JSONObject espData = new JSONObject();
                        JSONObject myValue=(JSONObject)layers.get(myVal);
                        espData.put("MessageDump",myValue);
                        treeEsp.put(myVal, espData);
                      }
                	 subESPArray.add(treeEsp);
                }
            }
            if(subArray!=null && !subArray.isEmpty()) {
            	treeData.put("ISAKMP", subArray);
            }
            if(subESPArray!=null && !subESPArray.isEmpty()) {
            	treeData.put("Packets", subESPArray);
            }
            subDataArray.add(treeData);
            
            dataInfo.put("text", "RootNode");
            dataInfo.put("IPSEC", subDataArray);

            treeChild.put("data", dataInfo);
            treeResponseData.put("treeData", treeChild);
            
            System.out.println("TreeData---->"+treeData);
            IPSecMap.put("IPSec",treeData);

	        fileWriter = new FileWriter("/home/user/Desktop/JSON file/Example1.json");
	        fileWriter.write(treeResponseData.toJSONString());
	        fileWriter.flush();
	        System.out.println("Successfully json file created...");
	        
        }catch(Exception e) {
            e.printStackTrace();
        }
		return IPSecMap;
	}
}
