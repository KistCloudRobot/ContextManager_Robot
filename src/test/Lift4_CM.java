package test;
import kgu.agent.demo.agent.Robot_CM;

public class Lift4_CM {
	public static void main(String[] args) {
		String brokerAddress;
		String robotID;
		if(args.length == 0) {
			robotID = "AMR_LIFT4";	
			brokerAddress = "tcp://127.0.0.1:61113";
//			brokerAddress = "tcp://192.168.100.10:63113";
//			brokerAddress = "tcp://172.16.165.141:61113";
		} else {
			robotID = args[0];
			brokerAddress = args[1];
		}
		Robot_CM agent = new Robot_CM(robotID, brokerAddress);
	}
}
