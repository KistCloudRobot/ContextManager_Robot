package test;

import kgu.agent.demo.agent.Robot_CM;

public class ContextManager_Robot_Docker {
	public static void main(String[] args) {
		String robotID = System.getenv("ROBOT");
		String brokerAddress = System.getenv("BROKER_ADDRESS");
		String stringPort = System.getenv("BROKER_PORT");
		int brokerPort = Integer.parseInt(stringPort);
		
		Robot_CM contextManager = new Robot_CM(robotID, brokerAddress, brokerPort);
	}
}
