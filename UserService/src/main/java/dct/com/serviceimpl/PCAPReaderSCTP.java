/*package dct.com.serviceimpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapHandle.TimestampPrecision;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.GtpV1Packet;
import org.pcap4j.packet.IpV4Packet.IpV4Header;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.SctpPacket.SctpHeader;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.UdpPacket.UdpHeader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dct.com.model.PCAPList;

@Service
public class PCAPReaderSCTP {

	private static final int COUNT = 1000;
	private static final String PCAP_FILE_KEY = PCAPReaderSCTP.class.getName() + ".pcapFile";
	//private static final String PCAP_FILE = System.getProperty(PCAP_FILE_KEY, "/home/user/PCAP/test_epc.pcap");
	private static final String sSctp = "132";
	private static final String sUDP = "17";
	private static final String sDesAndSrcPort = "2152";
	private String path = "/home/user/PCAP/";

	@SuppressWarnings({ "unchecked", "resource" })
	public JSONObject pcapPacket(MultipartFile file) throws IOException {

		PCAPList pcapList = new PCAPList();
		ArrayList gtpTunnelList = new ArrayList();
		ArrayList gtpHexData = new ArrayList();
		ArrayList gtpIP = new ArrayList();
		ArrayList sctpPayloadBuffer = new ArrayList();

		PcapHandle handle = null;
		BufferedInputStream bis=null;
		BufferedOutputStream bos=null;
		System.out.println("Input Stream -----> "+file.getInputStream().toString());
		bis=new BufferedInputStream(file.getInputStream());
		File fileName=new File(path +file.getOriginalFilename());
		System.out.println("File Name---->"+ fileName);
		bos=new BufferedOutputStream(new FileOutputStream(fileName));
		int b=0;
		while((b=bis.read())!=-1)
		{
			bos.write(b);
		}
	
		 
		    try {
		    	System.out.println("Path :: "+path +file.getOriginalFilename());
		      handle = Pcaps.openOffline(path +file.getOriginalFilename(), TimestampPrecision.NANO);
		    } catch (PcapNativeException e) {
		      e.printStackTrace();
		    }
		    
		for (int i = 0; i < COUNT; i++) {
			try {
				Packet nextPacket = handle.getNextPacketEx();
				Packet packetPayLoad = nextPacket.getPayload();
				IpV4Header ipv4Header = null;
				try {
					ipv4Header = (IpV4Header) packetPayLoad.getHeader();
				} catch (Exception e) {
					continue;
				}
				if (ipv4Header.getProtocol().toString().contains(sSctp)) {
					SctpHeader sctpHeader = (SctpHeader) packetPayLoad.getPayload().getHeader();

					ArrayList listChunks = (ArrayList) sctpHeader.getChunks();
					if (listChunks != null && !listChunks.isEmpty()) {
						String sChunkValues = listChunks.get(0).toString();
						if (sChunkValues != null && !sChunkValues.isEmpty()) {
							String sChunkType = sChunkValues.substring(0, 8);
							if (sChunkType != null && sChunkType.contains("0")) {
								int iValueIndex = sChunkValues.indexOf("Value");
								System.out.println("====>" + sChunkValues.substring(iValueIndex, sChunkValues.length()));
								System.out.println("payload-->" + packetPayLoad.getPayload());
								sctpPayloadBuffer.add(packetPayLoad.getPayload());
								System.out.println("payload length-->" + packetPayLoad.length());
							}
						}
					}
				}

				if (ipv4Header.getProtocol().toString().contains(sUDP)) {
					UdpHeader utpHeader = (UdpHeader) packetPayLoad.getPayload().getHeader();
					if (utpHeader.getDstPort().toString().contains(sDesAndSrcPort)
							&& utpHeader.getSrcPort().toString().contains(sDesAndSrcPort)) {
						System.out.println("Found GTP packet");
						UdpPacket udpPacket = (UdpPacket) packetPayLoad.getPayload();
						GtpV1Packet gtpPacket = (GtpV1Packet) udpPacket.getPayload();
						gtpTunnelList.add(gtpPacket.getHeader().getTeidAsLong());
						// gtpHexData.add(gtpPacket.getPayload());
						gtpHexData.add(gtpPacket.getRawData());
						gtpIP.add("10.20.120.26");
						// System.out.println("Tunnel id ===>"+gtpPacket.getHeader().getTeidAsLong());
						// System.out.println("HexData ---->"+gtpPacket.getPayload());
						// Packet ue_packet=(IpV4Packet)gtpPacket.getPayload();
						// IpV4Packet ipv4=ue_packet.;
						// System.out.println("gtpPacket.getHeader()--->"+gtpPacket.getHeader());
						// System.out.println("gtpPacket.getpayload--->"+gtpPacket.getPayload().getHeader());
						// System.out.println("ue_packet.length--->"+ue_packet.length() +
						// ue_packet.getRawData());
						// gtpPacket.getHeader();
						// IpV4Packet ue_packet1=ue_packet.get(IpV4Packet.class);
						// System.out.println("ue_packet.getHeader()--->"+ue_packet.getHeader());
						// System.out.println("ue_packet.getPayload()--->"+ue_packet.getPayload());
						// IpV4Header ue_ip_hdr=(IpV4Header)ue_packet.getPayload().getHeader();
						// System.out.println("ue_ip_hdr.getSrcAddr();--->"+ue_ip_hdr.getSrcAddr());
						// ue_ip_hdr.getSrcAddr();
						// System.out.println("gtpPacket.getHeader().getNPduNumber()--->"+gtpPacket.getHeader().getNPduNumber());
						// System.out.println("gtpPacket.getHeader().get-->"+gtpPacket.getHeader().getNPduNumberAsInt());
						// IpV4Header ue_ip_hdr=gtpPacket.getHeader().getNPduNumber();
						// IpV4Header mobile=(IpV4Header)udpPacket.getPayload().getHeader();
						// System.out.println("ipv4Header--->"+ipv4Header.getTtl());
					}
				}
			} catch (EOFException e) {
				break;
			} catch (Exception e) {
				continue;
			}
		}
		pcapList.setTunnelId(gtpTunnelList);
		pcapList.setGtpHexData(gtpHexData);
		pcapList.setGtpIP(gtpIP);
		System.out.println("pcapList- TunnelId->" + pcapList.getTunnelId());
		System.out.println("pcapList- IP->" + pcapList.getGtpIP());
		System.out.println("pcapList-HexData->" + pcapList.getGtpHexData());
		// pcapList.setSctpPayLoad(sctpPayloadBuffer);
		JSONObject treeData = new JSONObject();
		JSONObject treeChild = new JSONObject();
		JSONObject treeResponseData = new JSONObject();

		JSONArray subInfoArray = new JSONArray();

		treeData.put("text", "GTP");
		JSONArray subArray = new JSONArray();
		JSONArray subDataArray = new JSONArray();

		JSONObject dataInfo = new JSONObject();

		@SuppressWarnings({ "unused", "rawtypes" })
		ArrayList TunnelIdInfo = pcapList.getTunnelId();

		for (int i = 0; i < pcapList.getTunnelId().size(); i++) {
			JSONObject data = new JSONObject();

			data.put("TunnelId", pcapList.getTunnelId().get(i));
			data.put("text", "UE-" + pcapList.getGtpIP().get(i));
			data.put("messageDump", pcapList.getGtpHexData().get(i));
			subArray.add(data);
		}
		treeData.put("children", subArray);
		subDataArray.add(treeData);
		dataInfo.put("text", "RootNode");
		dataInfo.put("children", subDataArray);

		treeChild.put("data", dataInfo);
		treeResponseData.put("treeData", treeChild);
		System.out.println("treeResponseData"+treeResponseData);
		handle.close();

		return treeResponseData;

	}

}
*/