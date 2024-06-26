package test;

import kgu.agent.demo.agent.Robot_CM;

public class Lift2_CM {
	public static void main(String[] args) {
		String brokerAddress;
		String robotID;
		int port = 0;
		if(args.length == 0) {
			robotID = "AMR_LIFT2";	
//			brokerAddress = "127.0.0.1";
			brokerAddress = "192.168.100.11";
//			brokerAddress = "172.16.165.164";
			port = 61115;
		} else {
			robotID = args[0];
			brokerAddress = args[1];
		}
		Robot_CM agent = new Robot_CM(robotID, brokerAddress, port);
	}
}
