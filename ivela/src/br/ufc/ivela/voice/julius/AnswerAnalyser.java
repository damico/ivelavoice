/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.ivela.voice.julius;

import br.ufc.ivela.voice.teacher.Text;
import br.ufc.ivela.voice.util.DebugPrinter;
import br.ufc.ivela.voice.util.Message;

/**
 * This class analyses the clients response recognized by the voice recognizer.
 * @author jefferson
 */
public class AnswerAnalyser {

    public static String percentScore;
    public static int analyse(String in, Text pAnsw) {

        String answer = pAnsw.getContent();
        

        //cleaning string
        in = in.toLowerCase();
        in = in.trim();
        in = in.replace(".", "");
        in = in.replace("!", "");
        in = in.replace("?", "");
        in = in.replace(":", "");
        in = in.replace(";", "");
        in = in.replace(",", "");

        //cleaning string
        answer = answer.toLowerCase();
        answer = answer.replace(".", "");
        answer = answer.replace("!", "");
        answer = answer.replace("?", "");
        answer = answer.replace(":", "");
        answer = answer.replace(";", "");
        answer = answer.replace(",", "");
        answer = answer.trim();

        //debug
        DebugPrinter.print("---------------------------------------",DebugPrinter.DBG_DBG_MESG);
        DebugPrinter.print("1st: " + in, DebugPrinter.DBG_DBG_MESG);
        //DebugPrinter.print("2nd: " + inSndChance, DebugPrinter.DBG_DBG_MESG);
        DebugPrinter.print("Aswr: " + answer, DebugPrinter.DBG_DBG_MESG);
        
        //recognizer error searching error
        if(in.startsWith("<search failed>"))
        	return Message.RECOGNIZER_SEARCH_ERROR;
        
        //giving grade
        if (in.equals(answer) /*|| inSndChance.equals(answer)*/) {
        	AnswerAnalyser.percentScore = "100%";
            return Message.EXCELLENT;
        } else if (in.contains(answer) /*|| inSndChance.contains(answer)*/) {
        	AnswerAnalyser.percentScore = "90%";
            return Message.VERY_WELL;
        } else {
        	//return Message.WRONG;
            double percent = AnswerAnalyser.match(in, answer);
            AnswerAnalyser.percentScore = ""+(int)percent*10+"%";
            //System.out.println("PERCENT: "+percent);
            /*if(percent >= 0.5 && percent < 0.7){
                return Message.GOOD;
            }else*/ if(percent>=0.7 && percent<0.9){
                return Message.VERY_WELL;
            }else if (percent>=0.9){
                return Message.EXCELLENT;
            }else{
                return Message.WRONG;
            } 
            
             
        }

    }
    
    /**
     * 
     * @param pIn gave by julius
     * @param pAnsw gave by the exercise
     */
    public static double match(String pIn, String pAnsw){
         
        int counter = 0; 
        
        pIn = pIn.toLowerCase().trim();
        
         
        String[] pInArray = pIn.split(" "); 
        String[] pAnswArray = pAnsw.split(" ");
        
        int total = pAnswArray.length;
        
        for(int i=0;i<pAnswArray.length;i++){
        	
        	for(int j=0; j<pInArray.length; j++){
        		//System.out.println("Comparing: " + pAnswArray[j] +" with "+ pInArray[i] );
        		if(pAnswArray[i].equalsIgnoreCase(pInArray[j])){
        			counter++;
        			break;
        		}
        		
        	}
        	//System.out.println(" ");
        	
        }
         
       
        double percent = (double)counter/total;
        
        DebugPrinter.print("percent:" + percent, DebugPrinter.DBG_DBG_MESG);
        return percent;
    }
    
    public static void main(String[] args){
     
        AnswerAnalyser.match("my name's morning", "my name's tina");
        
    }
}
