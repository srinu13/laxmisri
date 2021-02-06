package dct.com.serviceimpl;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class ReadingJSON {
	
	@SuppressWarnings("unchecked")
	public static void main(String args[]) {
		JSONParser parser=new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("/home/user/Downloads/gtp/gtp1"));
			System.out.println("obj::::"+obj);
	        JSONArray jo = (JSONArray) obj;
	   /*     
	        JSONObject jsonObject = new JSONObject();
	        JSONObject jsonObject1 = new JSONObject();
	        JSONObject jsonObject2 = new JSONObject();
	        JSONArray jsonArray = new JSONArray();
	        
	        JSONArray children1 = new JSONArray();
	        JSONArray children2 = new JSONArray();
	        JSONArray children3 = new JSONArray();
	        JSONArray children4 = new JSONArray();
	        
	        
	        JSONObject text1 = new JSONObject();
	        JSONObject text2 = new JSONObject();
	        JSONObject text3 = new JSONObject();
	        JSONObject text4 = new JSONObject();
	        JSONObject text5 = new JSONObject();
	        JSONObject text6 = new JSONObject();
	        
	        
	        
	        FileWriter fileWriter =null;
	        
	        for(int i=0;i<jo.size();i++) {
		        JSONObject element=(JSONObject)jo.get(i);
		        JSONObject source=(JSONObject) element.get("_source");
		        JSONObject layers=(JSONObject) source.get("layers");
		        JSONObject Ip=(JSONObject) layers.get("ip");
		        JSONObject gtp=(JSONObject) layers.get("gtp");
		        
		        System.out.println("IP ----->"+Ip.get("ip.src"));
		        System.out.println("gtp---->"+gtp);
		        
		        text1.put("messageDump","Message");
		        text1.put("text","Payload");
		        
		        children1.add(text1);
		        
		        text2.put("children", children1);
		        text2.put("text","IP");
		        
		        children2.add(text2);
	        }
		        
		        text3.put("children",children2);
		        text3.put("text","GTP");
		        
		        children3.add(text3);
		        
		        text4.put("children", children3);
		        text4.put("text","Root Node");
		        
		        children4.add(text4);
		        
	        
	        
	        text5.put("data", children4);
	        text6.put("TreeData", text5);
	        
	        fileWriter = new FileWriter("/home/user/Desktop/JSON file/Example1.json");
			fileWriter.write(text6.toJSONString());
	        fileWriter.flush();
	        
	        */
	        
	        
	        FileWriter fileWriter =null;
	        
	        JSONObject treeData = new JSONObject();
			JSONObject treeChild = new JSONObject();
			JSONObject treeResponseData = new JSONObject();
			JSONObject treeGtpResponseData = new JSONObject();

			JSONArray subInfoArray = new JSONArray();

			treeData.put("text", "GTP");
			JSONArray subArray = new JSONArray();
			JSONArray subDataArray = new JSONArray();
			JSONArray subSubDataArray = new JSONArray();
			JSONObject dataInfo = new JSONObject();

			/*@SuppressWarnings({ "unused", "rawtypes" })
			ArrayList TunnelIdInfo = pcapList.getTunnelId();*/
			
			for (int i = 0; i < jo.size(); i++) {
				
				JSONObject element=(JSONObject)jo.get(i);
		        JSONObject source=(JSONObject) element.get("_source");
		        JSONObject layers=(JSONObject) source.get("layers");
		        JSONObject Ip=(JSONObject) layers.get("ip");
		        JSONObject gtp=(JSONObject) layers.get("gtp");
		        
		        System.out.println("IP ----->"+Ip.get("ip.src"));
		        System.out.println("gtp---->"+gtp);
				
				
				JSONObject data = new JSONObject();

				//data.put("TunnelId", Ip.get("ip.src"));
				//data.put("text", "UE-" + "child");
				data.put("text", "UE-" + Ip.get("ip.src"));
				data.put("messageDump", gtp);
				subArray.add(data);
			}
			treeData.put("children", subArray);
			subDataArray.add(treeData);
			dataInfo.put("text", "RootNode");
			dataInfo.put("children", subDataArray);
			subSubDataArray.add(dataInfo);
			treeGtpResponseData.put("children", subSubDataArray);

			treeChild.put("data", treeGtpResponseData);
			treeResponseData.put("treeData", treeChild);
			System.out.println("treeResponseData"+treeResponseData);

			
			 fileWriter = new FileWriter("/home/user/Desktop/JSON file/Example1.json");
				fileWriter.write(treeResponseData.toJSONString());
		        fileWriter.flush();
	        
	        
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
