package kgu.agent.demo.action;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jpl7.Query;

import kgu.agent.demo.actionArgument.LatestPerceptionArgument;

import kr.ac.uos.ai.arbi.agent.logger.ActionBody;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.GLParser;


public class LatestPerceptionAction implements ActionBody {


   int visualObjectPerceptionCount = 0;
   int visualRobotBodyPerceptionCount = 0;
   int visualRobotHandPerceptionCount = 0;
   int visualRobotLeftHandPerceptionCount = 0;
   int[] visualRobotLeftFingerPerceptionCount = new int[3];
   int visualRobotRightHandPerceptionCount = 0;
   int[] visualRobotRightFingerPerceptionCount = new int[3];
   int jointPerceptionCount = 0;

   int visualObjectPerceptionInterval = 0;
   int visualRobotBodyPerceptionInterval = 0;
   int visualRobotHandPerceptionInterval = 0;
   int visualRobotLeftHandPerceptionInterval = 0;
   int[] visualRobotLeftFingerPerceptionInterval = new int[3];
   int visualRobotRightHandPerceptionInterval = 0;
   int[] visualRobotLefRightFingerPerceptionInterval = new int[3];
   int jointPerceptionInterval = 0;

   int removeTime = 10;
   int removeInterval = 10;

   static List<String> oSubject = new ArrayList<String>();
   static List<String> oProperty = new ArrayList<String>();
   static List<String> oObject = new ArrayList<String>();
   static List<String> oGraph = new ArrayList<String>();
   static int[] oStatus = new int[1000];
   static int oInd;
   static List<String> oManager = new ArrayList<String>();

   static List<String> bSubject = new ArrayList<String>();
   static List<String> bProperty = new ArrayList<String>();
   static List<String> bObject = new ArrayList<String>();
   static List<String> bGraph = new ArrayList<String>();
   static int[] bStatus = new int[1000];
   static int bInd;
   static List<String> bManager = new ArrayList<String>();

   static List<String> hSubject = new ArrayList<String>();
   static List<String> hProperty = new ArrayList<String>();
   static List<String> hObject = new ArrayList<String>();
   static List<String> hGraph = new ArrayList<String>();
   static int[] hStatus = new int[1000];
   static int hInd;
   static List<String> hManager = new ArrayList<String>();

   static int[][] oIdCount = new int[50][2];
   static int oIdInd;

   static int[][] bIdCount = new int[50][2];
   static int bIdInd;

   static int[][] hIdCount = new int[50][2];
   static int hIdInd;

   String assertString = "";
   String retractString = "";
   String t;
   String data;
   String perceptionType;
   String contents;

   public LatestPerceptionAction() {
//      System.out.println("LatestPerceptionAction Start");
   }

   @Override
   public Object execute(Object o) {
//	  System.out.println("Latest Execute");
      // System.out.println(o);
      LatestPerceptionArgument Log = (LatestPerceptionArgument) o;
      // System.out.println(o);

      data = Log.getPerceptionGl();

      perceptionType = "";
      contents = "";

      GLParser parser = new GLParser();

      GeneralizedList gl = null; // 입력 값 GL
      try {

         gl = parser.parseGL(data); // GL
//         System.out.println("gl in LatPer" + gl);

      } catch (Exception ex) {
         ex.getStackTrace();
         System.out.println("Request format error. GL Fomat wrong");
      }

         
         if (gl.getName().equals("robotPosition")) {           
               if (gl.getExpressionsSize() == 0)
               return null;
               
                String robot_name = gl.getExpression(0).asValue().stringValue();
                String ID = "";
                int subVisualRobotBodyPerceptionCount = 0;
                int robot_id = -1 ;
                int time = 0;
                  
               if(robot_name.equals("AMR_LIFT1")){
                   ID = "AMR_Lift01";
               }
               else if(robot_name.equals("AMR_LIFT2")){
                  ID = "AMR_Lift02";
               }                  

              String x = gl.getExpression(1).asValue().stringValue();
              String y = gl.getExpression(2).asValue().stringValue();
        
            visualRobotBodyPerceptionCount++;
            subVisualRobotBodyPerceptionCount = bIdCount[bIdInd][1]++;
            time = (int) (System.currentTimeMillis() / 1000);
//
            assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
                  + visualRobotBodyPerceptionCount
                  + "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#VisualRobotBodyPerception'"
                  + " robotPerception";
//
            assertTriple(assertString);
            // startTime
            assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
                  + visualRobotBodyPerceptionCount
                  + "' 'http://knowrob.org/kb/knowrob.owl#startTime' 'http://www.arbi.com/ontologies/arbi.owl#timepoint_"
                  + time + "'" + " robotPerception";

            assertTriple(assertString);

            // objectActedOn
            assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
                  + visualRobotBodyPerceptionCount
                  + "' 'http://knowrob.org/kb/knowrob.owl#objectActedOn' 'http://www.arbi.com/ontologies/arbi.owl#"
                  + ID + "'" + " robotPerception";

            assertTriple(assertString);
            // eventOccursAt
            assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
                  + visualRobotBodyPerceptionCount
                  + "' 'http://knowrob.org/kb/knowrob.owl#eventOccursAt' 'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_"
                  + ID + subVisualRobotBodyPerceptionCount + "'" + " robotPerception";

            assertTriple(assertString);

            // rotationMatrix3D
            assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
                  + subVisualRobotBodyPerceptionCount
                  + "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#RotationMatrix3D'"
                  + " robotPerception";
            assertTriple(assertString);
            // x,y,z
            assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
                  + subVisualRobotBodyPerceptionCount
                  + "' 'http://knowrob.org/kb/knowrob.owl#m03' literal(type('http://www.w3.org/2001/XMLSchema#double','"
                  + x + "'))" + " robotPerception";

            assertTriple(assertString);

            assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
                  + subVisualRobotBodyPerceptionCount
                  + "' 'http://knowrob.org/kb/knowrob.owl#m13' literal(type('http://www.w3.org/2001/XMLSchema#double','"
                  + y + "'))" + " robotPerception";

            assertTriple(assertString);

            assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
                  + subVisualRobotBodyPerceptionCount
                  + "' 'http://knowrob.org/kb/knowrob.owl#m32' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
                  + " robotPerception";

            assertTriple(assertString);

         

            if (bIdCount[bIdInd][1] % removeTime == 0) {
               for (int i = bIdCount[bIdInd][0]; i < bIdCount[bIdInd][1] - removeInterval; i++) {
                  retractTriple("'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception" + i + "' A"
                        + " B C");

                  retractTriple(
                        "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID + i + "' A" + " B C");
               }
               bIdCount[bIdInd][0] = bIdCount[bIdInd][1] - removeInterval;
               // sleep(5);
            }
               
            
            
         }
         else if (gl.getName().equals("robotDegree")) {           
             if (gl.getExpressionsSize() == 0)
             return null;
             
              String robot_name = gl.getExpression(0).asValue().stringValue();
              String ID = "";
              int subVisualRobotBodyPerceptionCount = 0;
              int robot_id = -1 ;
              int time = 0;
                
             if(robot_name.equals("AMR_LIFT1")){
                 ID = "AMR_Lift01";
             }
             else if(robot_name.equals("AMR_LIFT2")){
                ID = "AMR_Lift02";
             }                  

            String direction = gl.getExpression(1).asValue().stringValue();
        
      
          visualRobotBodyPerceptionCount++;
          subVisualRobotBodyPerceptionCount = bIdCount[bIdInd][1]++;
          time = (int) (System.currentTimeMillis() / 1000);
//
          assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
                + visualRobotBodyPerceptionCount
                + "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#VisualRobotBodyPerception'"
                + " robotPerception";
//
          assertTriple(assertString);
          // startTime
          assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
                + visualRobotBodyPerceptionCount
                + "' 'http://knowrob.org/kb/knowrob.owl#startTime' 'http://www.arbi.com/ontologies/arbi.owl#timepoint_"
                + time + "'" + " robotPerception";

          assertTriple(assertString);

          // objectActedOn
          assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
                + visualRobotBodyPerceptionCount
                + "' 'http://knowrob.org/kb/knowrob.owl#objectActedOn' 'http://www.arbi.com/ontologies/arbi.owl#"
                + ID + "'" + " robotPerception";

          assertTriple(assertString);
          // eventOccursAt
          assertString = "'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception"
                + visualRobotBodyPerceptionCount
                + "' 'http://knowrob.org/kb/knowrob.owl#eventOccursAt' 'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_"
                + ID + subVisualRobotBodyPerceptionCount + "'" + " robotPerception";

          assertTriple(assertString);

          // rotationMatrix3D
          assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
                + subVisualRobotBodyPerceptionCount
                + "' 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 'http://knowrob.org/kb/knowrob.owl#RotationMatrix3D'"
                + " robotPerception";
          assertTriple(assertString);
          // x,y,z
          assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
                + subVisualRobotBodyPerceptionCount
                + "' 'http://knowrob.org/kb/knowrob.owl#m12' literal(type('http://www.w3.org/2001/XMLSchema#double','"
                + direction + "'))" + " robotPerception";

          assertTriple(assertString);

  

          assertString = "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID
                + subVisualRobotBodyPerceptionCount
                + "' 'http://knowrob.org/kb/knowrob.owl#m32' literal(type('http://www.w3.org/2001/XMLSchema#double','0'))"
                + " robotPerception";

          assertTriple(assertString);

       

          if (bIdCount[bIdInd][1] % removeTime == 0) {
             for (int i = bIdCount[bIdInd][0]; i < bIdCount[bIdInd][1] - removeInterval; i++) {
                retractTriple("'http://www.arbi.com/ontologies/arbi.owl#visualRobotBodyPerception" + i + "' A"
                      + " B C");

                retractTriple(
                      "'http://www.arbi.com/ontologies/arbi.owl#rotationMatrix3D_" + ID + i + "' A" + " B C");
             }
             bIdCount[bIdInd][0] = bIdCount[bIdInd][1] - removeInterval;
             // sleep(5);
          }
             
          
          
       }
   

       
      return "Contents :" + contents + " PerceptionType :" + perceptionType;
   }
   
   

   public void assertTriple(String triple) {

      triple = triple.replace(" ", ",");
      Query.hasSolution("rdf_assert(" + triple + ")");
   }

   public void retractTriple(String triple) {
      triple = triple.replace(" ", ",");
   }

   public void updateTriple(String triple) {
      triple = triple.replace(" ", ",");
      Query.hasSolution("rdf_update(" + triple + ")");
   }

}