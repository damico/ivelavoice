package br.ufc.ivela.voice.util;

import br.ufc.ivela.voice.julius.JuliusConstants;
import java.util.List;
import java.util.StringTokenizer;

import br.ufc.ivela.voice.julius.IJuliusParent;
import br.ufc.ivela.voice.teacher.Exercise;
import br.ufc.ivela.voice.teacher.Phrase;
import br.ufc.ivela.voice.teacher.Word;

/**
 * This class parses the input and output string of julius. I actually think
 * this class is pure POG.
 * 
 * @author jefferson
 */
public class Parser {

	public static String lastPass1 = "";

	public static void parse(String in, IJuliusParent parent) {

		if (parent == null) {

			return;
		}

		if (parent.isFinished()) {
			return;
		}
		

		if (in.startsWith("pass1_best:")) {

			String pass1 = branchString(in);
			lastPass1 = pass1;

		}
		
		if (in.startsWith("sentence1:")) {

			String sentence1 = branchString(in);
			System.out.println("SENT: " + sentence1);
			parent.setMessageOutput(sentence1);

			// parent.setMessageOutputScndChance(branchString(in));
		}else if (in.startsWith("<search failed>")){
			
			System.out.println("PASS1: " + lastPass1);
			parent.setMessageOutput("<search failed>");
		}

		

		 

	}

	public static String branchString(String s) {

		StringBuffer sb = new StringBuffer(s);
		int index = s.indexOf(":");
		sb.delete(0, index + 1);
		int i = sb.lastIndexOf("<s>");
		if (i >= 0) {
			sb.delete(i, i + 3);
		}
		i = sb.lastIndexOf("</s>");
		if (i >= 0) {
			sb.delete(i, i + 4);
		}
		return sb.toString();
	}

	public static Exercise parseAppletParamTextBySeparator(String text,
			String audio, String chances, String separator, String audioHost) {
		Exercise exe = null;
		StringTokenizer phrases = new StringTokenizer(text, separator);

		if (phrases.countTokens() != 0)
			exe = new Exercise();

		while (phrases.hasMoreElements()) {
			String phrase = phrases.nextToken();
			StringTokenizer words = new StringTokenizer(phrase, " ");

			Phrase juliusPhrase = null;
			if (words.countTokens() != 0)
				juliusPhrase = new Phrase();

			while (words.hasMoreTokens()) {
				Word juliusWord = new Word(words.nextToken());
				juliusPhrase.addWord(juliusWord);
			}
			// System.out.println("-->"+juliusPhrase.getContent());
			juliusPhrase.setTip("I am right here, inside this little fellow who turned us apart. But no problem. I love you anyway.");
			exe.addPhrase(juliusPhrase);
		}

		// audio
		if (audio == null || audio.equalsIgnoreCase(""))
			return exe;

		StringTokenizer audios = new StringTokenizer(audio, "#");
		List<Phrase> phrasesFromJulius = exe.getPhrases();
		int counter = 0;
		while (audios.hasMoreTokens()) {
			String audioFile = audios.nextToken();
			// System.out.println("@" + audioFile);
			Phrase p = phrasesFromJulius.get(counter++);
			if (p != null)
				p.setAudioURL(audioHost + audioFile);
		}

		// chances
		if (chances == null || chances.equalsIgnoreCase(""))
			return exe;

		StringTokenizer chancesTokenizer = new StringTokenizer(chances, "#");
		phrasesFromJulius = exe.getPhrases();
		counter = 0;
		while (chancesTokenizer.hasMoreTokens()) {
			String chanceNumber = chancesTokenizer.nextToken();
			// System.out.println("&"+chanceNumber);
			Phrase p = phrasesFromJulius.get(counter++);
			if (p != null) {
				int chanceNumberInt = Integer.parseInt(chanceNumber);
				p.setChances(chanceNumberInt);
				p.setOriginalChanceNumber(chanceNumberInt);
				
			}

		}

		return exe;
	}

	 
}
