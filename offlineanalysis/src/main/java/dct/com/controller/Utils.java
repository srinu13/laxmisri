package dct.com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utils {

	public static HashMap<Integer, String> messgaeNames = new HashMap<Integer, String>();
	public static List<String> E_RAB_Management = new ArrayList<String>();
	public static List<String> UE_specific_Messages = new ArrayList<String>();
	public static List<String> Handover_Signalling_Messages = new ArrayList<String>();
	public static List<String> NAS_Transport_Messages = new ArrayList<String>();
	public static List<String> Management_messages = new ArrayList<String>();
	public static List<String> Negative_Messages = new ArrayList<String>();

	public static String getMessageName(int procedure_code) {
		messgaeNames.put(0, "HandoverPreparation");
		messgaeNames.put(1, "HandoverResourceAllocation");
		messgaeNames.put(2, "HandoverNotification");
		messgaeNames.put(3, "PathSwitchRequest");
		messgaeNames.put(4, "HandoverCancel");
		messgaeNames.put(5, "E-RABSetup");
		messgaeNames.put(6, "E-RABModify");
		messgaeNames.put(7, "E-RABRelease");
		messgaeNames.put(8, "E-RABReleaseIndication");
		messgaeNames.put(9, "InitialContextSetup");
		messgaeNames.put(10, "Paging");
		messgaeNames.put(11, "downlinkNASTransport");
		messgaeNames.put(12, "initialUEMessage");
		messgaeNames.put(13, "uplinkNASTransport");
		messgaeNames.put(14, "Reset");
		messgaeNames.put(15, "ErrorIndication");
		messgaeNames.put(16, "NASNonDeliveryIndication");
		messgaeNames.put(17, "S1Setup");
		messgaeNames.put(18, "UEContextReleaseRequest");
		messgaeNames.put(19, "DownlinkS1cdma2000tunneling");
		messgaeNames.put(20, "UplinkS1cdma2000tunneling");
		messgaeNames.put(21, "UEContextModification");
		messgaeNames.put(22, "UECapabilityInfoIndication");
		messgaeNames.put(23, "UEContextRelease");
		messgaeNames.put(24, "eNBStatusTransfer");
		messgaeNames.put(25, "MMEStatusTransfer");
		messgaeNames.put(26, "DeactivateTrace");
		messgaeNames.put(27, "TraceStart");
		messgaeNames.put(28, "TraceFailureIndication");
		messgaeNames.put(29, "ENBConfigurationUpdate");
		messgaeNames.put(30, "MMEConfigurationUpdate");
		messgaeNames.put(31, "LocationReportingControl");
		messgaeNames.put(32, "LocationReportingFailureIndication");
		messgaeNames.put(33, "LocationReport");
		messgaeNames.put(34, "OverloadStart");
		messgaeNames.put(35, "OverloadStop");
		messgaeNames.put(36, "WriteReplaceWarning");
		messgaeNames.put(37, "eNBDirectInformationTransfer");
		messgaeNames.put(38, "MMEDirectInformationTransfer");
		messgaeNames.put(39, "PrivateMessage");
		messgaeNames.put(40, "eNBConfigurationTransfer");
		messgaeNames.put(41, "MMEConfigurationTransfer");
		messgaeNames.put(42, "CellTrafficTrace");
		messgaeNames.put(43, "Kill");
		messgaeNames.put(44, "downlinkUEAssociatedLPPaTransport");
		messgaeNames.put(45, "uplinkUEAssociatedLPPaTransport");
		messgaeNames.put(46, "downlinkNonUEAssociatedLPPaTransport");
		messgaeNames.put(47, "uplinkNonUEAssociatedLPPaTransport");
		messgaeNames.put(48, "UERadioCapabilityMatch");

		return messgaeNames.get(procedure_code);

	}

	public static String getUESpecificname(String messgae_name) {
		// add all the message types to respective Message types
		E_RAB_Management.add("");

		UE_specific_Messages.add("InitialContextSetup");
		UE_specific_Messages.add("UEContextReleaseRequest");
		UE_specific_Messages.add("UEContextRelease");
		UE_specific_Messages.add("UEContextReleaseCommand");
		UE_specific_Messages.add("InitialContextSetupRequest");
		UE_specific_Messages.add("UEContextReleaseComplete");

		Handover_Signalling_Messages.add("");

		NAS_Transport_Messages.add("InitialUEMessage");
		NAS_Transport_Messages.add("uplinkNASTransport");
		NAS_Transport_Messages.add("DownlinkNASTransport");
		NAS_Transport_Messages.add("UplinkNASTransport");
		NAS_Transport_Messages.add("UEContextReleaseComplete");
		NAS_Transport_Messages.add("UECapabilityInfoIndication");

		Management_messages.add("S1SetupResponse");

		Management_messages.add("S1SetupRequest");

		Negative_Messages.add("");

		if (E_RAB_Management.contains(messgae_name)) {
			return "E-RAB Management";
		} else if (UE_specific_Messages.contains(messgae_name)) {
			return "UE specific  Messages";
		} else if (Handover_Signalling_Messages.contains(messgae_name)) {
			return "Handover Signalling Messages";
		} else if (NAS_Transport_Messages.contains(messgae_name)) {
			return "NAS Transport Messages";
		} else if (Management_messages.contains(messgae_name)) {
			return "Management messages";
		} else if (Negative_Messages.contains(messgae_name)) {
			return "Negative Messages";

		} else
			return "No UESpecific name found for message name" + messgae_name;

	}

	
	static void cmdExecuter(String command) throws IOException {
		String s;
		try {
			//new String[] { "bash", "-c", "tshark -2 -R \"s1ap\" -r test_epc.pcap -T json >IPSec.json" });
			Process p = Runtime.getRuntime().exec(
					new String[] { "bash", "-c", command });
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			System.out.println("Here is the standard output of the command:\n");
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			System.out.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong ");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
