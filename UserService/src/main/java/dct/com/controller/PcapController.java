/*package dct.com.controller;

import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dct.com.serviceimpl.PCAPReaderSCTP;

@RestController
@MultipartConfig(maxFileSize = 1024*1024*1024, maxRequestSize = 1024*1024*1024)
public class PcapController {
	
	@Autowired
	PCAPReaderSCTP pcapReaderSCTP;
	
	
public PCAPReaderSCTP getPcapReaderSCTP() {
		return pcapReaderSCTP;
	}


	public void setPcapReaderSCTP(PCAPReaderSCTP pcapReaderSCTP) {
		this.pcapReaderSCTP = pcapReaderSCTP;
	}

@RequestMapping(value="/pcap",method = RequestMethod.POST)	
 public JSONObject pcapPacket(@RequestParam("pCapUpload") MultipartFile file) throws IOException{
	
	 System.out.println("inside pcap controller");
	return getPcapReaderSCTP().pcapPacket(file);
	 
 }
}
*/