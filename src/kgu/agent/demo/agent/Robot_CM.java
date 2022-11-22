package kgu.agent.demo.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import kgu.agent.demo.action.GUIAction;
import kgu.agent.demo.action.LatestPerceptionAction;
import kgu.agent.demo.action.ReasoningQueryAction;
import kgu.agent.demo.actionArgument.ContextOntologyMonitorArgument;
import kgu.agent.demo.actionArgument.GUIArgument;
import kgu.agent.demo.actionArgument.LatestPerceptionArgument;
import kgu.agent.demo.actionArgument.LowLevelContextMonitorArgument;
import kgu.agent.demo.actionArgument.ReasoningQueryArgument;
import kr.ac.uos.ai.arbi.Broker;
import kr.ac.uos.ai.arbi.BrokerType;
import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.agent.logger.AgentAction;
import kr.ac.uos.ai.arbi.agent.logger.LogTiming;
import kr.ac.uos.ai.arbi.agent.logger.LoggerManager;
import kr.ac.uos.ai.arbi.ltm.DataSource;
import kgu.agent.demo.actionArgument.SubscribeArgument;

import kgu.agent.demo.paser.ContextMonitorParser;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.parser.ParseException;
//import kgu.agent.demo.agent.RobotCMDataSource;


public class Robot_CM extends ArbiAgent {

	// private GeneralizedList eventGL;
	private Map<String, String> taskManagerSubsList;
	public AgentAction reasoningAction;
	public AgentAction subscribeAction;
	public AgentAction notifyAction;
	public AgentAction perceptionSubscriptionsAction;
	public AgentAction tripleCountAction;
	public AgentAction workingMemory_MonitorsAction;
	public AgentAction batteryAction;
	public AgentAction latestPerceptionAction;
	public AgentAction availableMemoryAction;
	public AgentAction initializeSemanticMapAction;
	public AgentAction ontologyUpdateAction;
	public AgentAction guiAction;



	public static  String CONTEXTMANAGER_ADRESS = "agent://www.arbi.com/ContextManager";
	public static  String TASKMANAGER_ADDRESS = "agent://www.arbi.com/TaskManager";
	public static String brokerAddress;
	LatestPerceptionAction action8;
	DataSource ds;
	public Robot_CM(String robotID, String brokerAddress) {
		this.brokerAddress = brokerAddress;
		ArbiAgentExecutor.execute(brokerAddress, CONTEXTMANAGER_ADRESS, this, BrokerType.ACTIVEMQ);
		init_prolog();
		ds = new DataSource(){
			boolean Subscripting_start = false;

			@Override
			public void onNotify(String data) {

				if (!Subscripting_start) {

					Subscripting_start = true;
				}
				System.out.println("Notification : " + data);

				GeneralizedList gl = null;
				
				try {
					gl = GLFactory.newGLFromGLString(data);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					LatestPerceptionArgument argument = new LatestPerceptionArgument(data);
					// LatestPerceptionAction action8 = new LatestPerceptionAction();

					if(argument == null){
						   return;
						}		
					String latestPerception = (String) action8.execute(argument);

					
					String sender = "FakeTM";
					String queryGL;
			
					//queryGL = "(context (preparationVertex \"http://www.arbi.com/ontologies/arbi.owl#station2\" $B))";
//					queryGL = "(context (faceToFace $A $B))";
//					queryGL = "(context (faceToFace $A $B))";
//				    onQuery(sender, queryGL);
//					sleep(300);	
//					
//					queryGL = "(context (nearBy $A $B $C))";
//				    onQuery(sender, queryGL);
//					sleep(300);	
//					
//					queryGL = "(context (hwanSong $A $B))";
//				    onQuery(sender, queryGL);
//					sleep(300);	
//					
//					queryGL = "(context (currentRobotBodyXY $A $B))";
//				    onQuery(sender, queryGL);
//					sleep(300);	
					//queryGL = "(context (deadLock $A $B))";
					//queryGL = "(context (preparationVertex \"http://www.arbi.com/ontologies/arbi.owl#station2\" $B))";
				   // onQuery(sender, queryGL);
					//sleep(300);	
//////					
//					queryGL = "(context (stationMoveType \"http://www.arbi.com/ontologies/arbi.owl#station1\" \"in\" $C))";
//					onQuery(sender, queryGL);
//					sleep(300);	
//					
//					queryGL = "(context (stationMoveType \"http://www.arbi.com/ontologies/arbi.owl#station26\" \"out\" $C))";
//					onQuery(sender, queryGL);					
//					sleep(300);					
			}
		};
				
		ReasoningQueryAction action1 = new ReasoningQueryAction(ds);
		reasoningAction = new AgentAction("ContextService", action1);
		LoggerManager.getInstance().registerAction(reasoningAction, LogTiming.Later);

		action8 = new LatestPerceptionAction();
		latestPerceptionAction = new AgentAction("RobotContext", action8);
		LoggerManager.getInstance().registerAction(latestPerceptionAction, LogTiming.Later);

		ds.connect(brokerAddress, "ds://www.arbi.com/ContextManager", BrokerType.ACTIVEMQ);
		ds.subscribe("(rule (fact (robotPosition $robotID $x $y)) --> (notify (robotPosition $robotID $x $y)))");
		ds.subscribe("(rule (fact (robotStatus $robotID $x)) --> (notify (robotStatus $robotID $x)))");
		ds.subscribe("(rule (fact (robotSpeed $robotID $x)) --> (notify (robotSpeed $robotID $x)))");
		ds.subscribe("(rule (fact (robotDegree $robotID $x)) --> (notify (robotDegree $robotID $x)))");
		ds.subscribe("(rule (fact (robotBattery $robotID $x)) --> (notify (robotBattery $robotID $x)))");
		

	}

	public void onNotify(String sender, String notification) {
		System.out.println(notification);
	}
	
	
	public String onQuery(String sender, String queryGL) {

		System.out.println("ONQuery start");
		System.out.println("sender : " + sender);
		System.out.println("queryGL : " + queryGL);
		String message, result, latestPerception, predicate;
		
		//predicate = queryGL.split(" ")[1].replace("(", "");
				
		ReasoningQueryArgument rqArgument = new ReasoningQueryArgument(sender, queryGL);
		ReasoningQueryAction action1 = new ReasoningQueryAction(ds);
		
		reasoningAction = new AgentAction("ContextService", action1);
		
		String queryResult = (String) reasoningAction.execute(rqArgument);
		System.out.println("queryResult : " + queryResult);
		
		return queryResult;
	}

	public static void main(String[] args) {
		String brokerAddress;
		String robotID;
		if(args.length == 0) {
			brokerAddress = "tcp://127.0.0.1:61120";
			robotID = "AMR_LIFT1";	
		} else {
			robotID = args[0];
			brokerAddress = args[1];
		}
		Robot_CM agent = new Robot_CM("AMR_LIFT1", "tcp://127.0.0.1:61120");
//		Robot_CM agent2 = new Robot_CM("AMR_LIFT2", "tcp://127.0.0.1:61121");
		Robot_CM agent2 = new Robot_CM("AMR_LIFT2", "tcp://127.0.0.1:61123");
	}
	public static void log(String log) {
		try {
			File file = new File("E:\\contextLog.txt" );
			FileWriter fw = new FileWriter(file, true);
			
			fw.write(log + "\n");
			
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void init_prolog() {

		String t = "";
		t = "[cmProlog/prolog/init_isaac]";
		// t = "[cmProlog/prolog/init]";// "[prolog/init]";
		System.out.println(t + " " + (Query.hasSolution(t) ? "succeeded" : "failed"));

		Query q = new Query(t);

		while (q.hasMoreSolutions()) {
			Map<String, Term> s3 = q.nextSolution();


		}

		System.out.println("SWI-Prolog working from now on");

	}

	public void sleep(int n) {
		try {
			Thread.sleep(n);
		} catch (Exception e) {
		}
	}

}