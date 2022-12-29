package test;

import kgu.agent.demo.agent.Robot_CM;

public class Lift2_CM {
	public static void main(String[] args) {
		String brokerAddress;
		String robotID;
		if(args.length == 0) {
			robotID = "AMR_LIFT2";	
			brokerAddress = "tcp://127.0.0.1:61115";
//			brokerAddress = "tcp://192.168.100.10:65115";
//			brokerAddress = "tcp://172.16.165.141:61115";
		} else {
			robotID = args[0];
			brokerAddress = args[1];
		}
		Robot_CM agent = new Robot_CM(robotID, brokerAddress);
	}
}
