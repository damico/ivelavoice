/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.ivela.voice.util;

import java.util.ArrayList;
import java.util.List;

import br.ufc.ivela.voice.teacher.Exercise;
import br.ufc.ivela.voice.teacher.Phrase;
import br.ufc.ivela.voice.teacher.Word;

/**
 * 
 * @author jefferson
 */
public class GenerateResult {

	public static final String WORD1 = "name";
	public static final String WORD2 = "england";
	public static final String WORD3 = "dial";
	public static final String WORD4 = "drink";
	public static final String WORD5 = "student";
	public static final String WORD6 = "computer";
	public static final String WORD7 = "delete";
	public static final String WORD8 = "dictionary";
	public static final String WORD9 = "element";
	public static final String WORD10 = "work";

	public static String generateHTML(List<Exercise> exercises) {
		String s = "";

		s = "<table>"
				+ "<tr>"
				+ "<td><font size='4' face='arial' color='black'>Exercise</font></td>"
				+ "<td><font size='4' face='arial' color='black'>Error</font></td>"
				+ "</tr>";

		for (Exercise exe : exercises) {

			for (Phrase p : exe.getPhrases()) {
				s += "<tr>";
				s += "<td><font size='4' face='arial' color='black'>"
						+ p.getContent() + "</font></td>";
				if (p.getErrors() < 0)
					s += "<td><font size='4' face='arial' color='red'>skipped</font></td>";
				else
					s += "<td><font size='4' face='arial' color='black'>"
							+ p.getErrors() + "</font></td>";
				s += "</tr>";
			}

		}

		s += "</table>";

		return s;
	}

	public static String generateResultForNpdUser(List<Phrase> list){
    	String res = "";
    	
    	 
    	for(Phrase phrase: list){
    		 res+=phrase.getErrors()+"A";
    	}
    	
    	return res;
    }
	
	public static String generateResultForUserVoice(List<Phrase> list){
    	String res = "";
    	
    	res = "uv_name="+ServletComm.getHostName();
    	res += "&words=";
    	for(Phrase phrase: list){
    		int wordId = getWordId(phrase.getContent().trim());
    		String token = wordId+"A"+phrase.getErrors();
    		token+="B";
    		res+=token;
    	}
    	
    	return res;
    }

	public static int getWordId(String word) {

		if (word.equalsIgnoreCase(WORD1)) {
			return 1;
		} else if (word.equalsIgnoreCase(WORD2)) {
			return 2;
		} else if (word.equalsIgnoreCase(WORD3)) {
			return 3;
		} else if (word.equalsIgnoreCase(WORD4)) {
			return 4;
		} else if (word.equalsIgnoreCase(WORD5)) {
			return 5;
		} else if (word.equalsIgnoreCase(WORD6)) {
			return 6;
		} else if (word.equalsIgnoreCase(WORD7)) {
			return 7;
		} else if (word.equalsIgnoreCase(WORD8)) {
			return 8;
		} else if (word.equalsIgnoreCase(WORD9)) {
			return 9;
		} else
			return 10;
	}
	
	public static void main(String[] args) {
		
		Phrase p1 = new Phrase();
		p1.addWord(new Word(WORD1));
		Phrase p2 = new Phrase();
		p2.addWord(new Word(WORD2));
		Phrase p3 = new Phrase();
		p3.addWord(new Word(WORD3));
		Phrase p4 = new Phrase();
		p4.addWord(new Word(WORD4));
		
		List<Phrase> list = new ArrayList<Phrase>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		
		System.out.println(generateResultForUserVoice(list));
	}
}
