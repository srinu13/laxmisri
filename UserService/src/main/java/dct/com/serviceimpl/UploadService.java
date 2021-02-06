/*package dct.com.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.pkts.buffer.InputStreamBuffer;

@Service
public class UploadService {
	
	@SuppressWarnings("resource")
	public JSONObject pcapPacketData(MultipartFile file) throws IOException{
		
		FileOutputStream out = null;
		FileInputStream filecontent = null;
		String path="/home/user/PCAPACKET/";
		String fileName = file.getOriginalFilename();
		
		
		
		out = new FileOutputStream(new File(path+fileName));
		filecontent =  (FileInputStream) file.getInputStream();
			
	    InputStreamBuffer i=new InputStreamBuffer(filecontent);
		int s=filecontent.read();
		System.out.println("s::::"+s);
		int read = 0;
		final byte[] bytes = new byte[1048576];

		while((read = filecontent.read(bytes)) != -1)
		{
		out.write(bytes, 0, read);
		System.out.println("out::"+out);
		
		}
		return null;
	
	}
}
*/