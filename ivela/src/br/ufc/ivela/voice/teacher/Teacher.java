/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.ivela.voice.teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JApplet;

import br.ufc.ivela.voice.gui.FancyBoardPanel;
import br.ufc.ivela.voice.gui.IBoard;
import br.ufc.ivela.voice.julius.AnswerAnalyser;
import br.ufc.ivela.voice.julius.IJuliusParent;
import br.ufc.ivela.voice.julius.JuliusCommandLine;
import br.ufc.ivela.voice.util.DebugPrinter;
import br.ufc.ivela.voice.util.GenerateResult;
import br.ufc.ivela.voice.util.Message;
import br.ufc.ivela.voice.util.Parser;
import br.ufc.ivela.voice.util.ServletComm;
import br.ufc.ivela.voice.util.StreamGobbler;
import br.ufc.ivela.voice.util.TimeBomber;

/**
 * This classe implement the exercise logic. It is a thread running in background and acts like the voice recognizer parent.
 * This means that this class must control, in its run method, the voice recognizer instation.
 * @author jefferson
 */
public class Teacher extends Thread implements IJuliusParent {

    private List<Exercise> exercises;
    private IBoard blackBoard;
    private Semaphore control;
    private JuliusCommandLine juliusCommandLine;
    private Exercise currentExercise;
    private StreamGobbler outputGobbler;
    private Text focusText;
    private TimeBomber timeBomber;
     
    
    private int focusTextPosition = 0;
    private int totalText;
    private int errors;
    private int score;
     
    
    private boolean finished = false;
    private boolean kill = false;
    private boolean readWordByWord = false;
    private boolean bomberActive = false;
    private boolean offState = false;
     
    private JApplet parent;
     
    
    public Teacher(JApplet parent) {

        this.exercises = new ArrayList<Exercise>();
        this.control = new Semaphore(0);
        this.juliusCommandLine = new JuliusCommandLine();
        this.parent = parent;
    }

    /**
     * 
     * @param exercise Is a list of phrases that must be recognize by the voice recognizer.
     * The phrases are in one String object, separated by '.'(dot).
     * @param grammarFile Each Exercise has its grammar file e specific vocabulary.
     * @param question Is the question title 
     * @param audio Is list of audio files, one for each phrase.
     * The audio files names are in one String object, separated by #.
     */
    public void createExercise(String exercise, String grammarFile, String question, String audio, String chances) {
        Exercise e = Parser.parseAppletParamTextBySeparator(exercise, audio, chances,"#");
        this.exercises.add(e);
        e.setNumber(this.exercises.size());
        e.setExerciseTitle(question);
        e.setConfJulianFile(grammarFile);
        this.totalText = e.getPhrases().size();
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    /**
     * This methos controls the logic of a Teacher. First, the Teacher presents a focus phrase. 
     * After, it waits clients responses (wich will be given by the voice recognizer).
     * If the answer is right, the teacher jumps for the next question and so on. An wrong question
     * is repeated until the clients get it right.
     */
    public void run() {
    	this.getBoard().initiateGlow();
    	
        while (!kill) {
        	
            this.sleep();
            this.setFinished(false);
            this.focusTextPosition = 0;
            
            for (Exercise t : this.exercises) {
                //Current exercise
                this.currentExercise = t;
                //run the voice recognizer
                this.blackBoard.setMessage(new Message(Message.WAIT));
               
                //System.out.println("INICIANDO");
                //this.outputGobbler = this.juliusCommandLine.runJulius(this);
                 
                int index = 1;
                //sleeps some time
                dummySleep(2000);
                 
               
                restart:
                for (Phrase p : t.getPhrases()) {


                    this.focusText = p;
                    p.setFocus(true);

                    this.blackBoard.setMessage(new Message(Message.DEFAULT));

                    
                    sleep();
                    
                    if(offState){
                    	p.setFocus(false);
                    	break restart;
                    	
                    }
                    	
                    
                    if (this.readWordByWord) {
                        this.repeatWordByWord(p);                    
                    }
                    
                    //well, client killed applet or pressed restart.
                    if (this.kill) {
                        p.setFocus(false);
                        break restart;
                        
                    }

                    p.setFocus(false);
                    this.focusTextPosition++;
                    
                    this.calculateExercisePercentage(index++,t.getPhrases().size());
                    
                    System.out.println("-->"+p.getErrors());
                }
               
                //this.juliusCommandLine.killJulius();
                
            }

            //the following updates the board messages.
            if(!offState){
	            if (!kill) {
	                this.blackBoard.setMessage(new Message(Message.FINISH));
	                this.setFinished(true);
	                dummySleep(2000);
	                //this.blackBoard.setMessage(new Message(Message.SEND_RESULTS));
	                //dummySleep(2000);
	                //this.blackBoard.setMessage(new Message(Message.SEND_RESULTS_DONE));
	       
	                this.blackBoard.toggleButton(FancyBoardPanel.OFF_BTN);
	                this.blackBoard.toggleButton(FancyBoardPanel.ON_BTN);
	                this.blackBoard.updateProgress(0);
	                
	                //List<Phrase> listOfPhrases = this.exercises.get(0).getPhrases();
	                //String res = GenerateResult.generateResultForNpdUser(listOfPhrases);
	                //System.out.println("--"+res);
	                //ServletComm.sendDataToNpdUser("1A2A3A4A5A",res);
	                this.resetTries();
	                
	                //save in database
	                /*if(stat){
	                	List<Phrase> listOfPhrases = this.exercises.get(0).getPhrases();
	                	String res = GenerateResult.generateResultForUserVoice(listOfPhrases);
	                	ServletComm.sendDataToUserVoice(res);
	                	
	                	//clean
	                    this.cleanPhrases(listOfPhrases);
	                    
	                }*/
	                
	                //pop up form
	                if(this.getBoard().isBoardRequireJavaScript())
	                	ServletComm.callJavasScript(this.parent);
	                
	                
	            } else {
	                kill = false;
	                this.getBoard().setStart(false);
	                this.getBoard().setMessage(new Message(Message.START));
	                this.setFinished(false);
	            }

            }else{
            	
            	focusTextPosition = 0;
            	score = 0;
            	errors = 0;
            	this.resetChances();
            	this.blackBoard.setMessage(new Message(Message.TURN_OFF));
                this.setFinished(true);
                dummySleep(2000);
                this.blackBoard.setMessage(new Message(Message.START));
                //this.blackBoard.setMessage(new Message(Message.SEND_RESULTS));
                //dummySleep(2000);
                //this.blackBoard.setMessage(new Message(Message.SEND_RESULTS_DONE));
       
                this.blackBoard.toggleButton(FancyBoardPanel.OFF_BTN);
                this.blackBoard.toggleButton(FancyBoardPanel.ON_BTN);
                this.blackBoard.updateProgress(0);
                offState = false;
            }
            	
            
        }//while
        
         
    }

    /**
     * Acquires semaphore permit, blocking this thread until a right answer be given. 
     */
    public void sleep() {
        try {
            this.control.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
        	
            //e.printStackTrace();
        	//System.out.println("PAU");
            DebugPrinter.print("Applet thread interrupted. Killing julian anyway...", DebugPrinter.DBG_WARNING_MESG);
        	this.juliusCommandLine.killJulius();
        }
    }

    /**
     * Dummy sleep stops this thread for a certain time.
     */
    public void dummySleep(int time) {
    	
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        	DebugPrinter.print("Dummy sleep error", DebugPrinter.DBG_WARNING_MESG);
        }
    }

    /**
     * Unblock semaphore. So, this thread can go on
     */
    public void awake() {
        this.blackBoard.setStart(true);
        this.control.release();

    }

    /**
     * The panel wich will be used to draw feedback
     * @param blackBoard
     */
    public void setBlackBoard(IBoard blackBoard) {
        this.blackBoard = blackBoard;
    }

    /**
     * Set Message objetc for a specific scenario
     * @param message
     */
    public synchronized void setMessageOutput(String message) {
        
    	this.blackBoard.activateBtn(FancyBoardPanel.MIC_BTN);
    	
    	// here, I will check the message understood by julian 
        // and compare with my focus phrase
        int grade = AnswerAnalyser.analyse(message, focusText);


        //teste
        this.forceKillJulius();
        
        //the grade is how close the voice recognizer recognized the client's voice.
        if (message != null && !message.equals(""));
        {
        	
        	this.outputGobbler.pause();
            if (grade == Message.EXCELLENT) {

            	this.timeBomber.turnOff();
                this.blackBoard.setMessage(new Message(Message.EXCELLENT));
                this.dummySleep(3000);
                this.score ++;
                this.awake();
                

            } else if (grade == Message.VERY_WELL) {
            	this.timeBomber.turnOff();
                this.blackBoard.setMessage(new Message(Message.VERY_WELL));
                this.dummySleep(3000);
                this.score++;
                this.awake();
                

            }else if (grade == Message.GOOD) {
            	this.timeBomber.turnOff();
                this.blackBoard.setMessage(new Message(Message.GOOD));
                this.dummySleep(3000);
                this.score++;
                this.awake();
                

            }else if (grade == Message.RECOGNIZER_SEARCH_ERROR){
            	this.timeBomber.turnOff();
            	this.blackBoard.setMessage(new Message(Message.RECOGNIZER_SEARCH_ERROR));
                this.dummySleep(2000);
                //this.blackBoard.setMessage(new Message(Message.PAUSE));
                
            }else {
            	this.timeBomber.turnOff();
            	this.focusText.incErrors();
                this.focusText.decreaseChance();
                
            	if(focusText instanceof Phrase){
            		this.errors++;
            		if(this.focusText.getChances() == 0){
            			 this.blackBoard.setMessage(new Message(Message.CHANCES_OVER));
                    	 this.dummySleep(3000);
            			 //this.readWordByWord = true;
                         this.awake();
            		}else{
            			this.blackBoard.setMessage(new Message(Message.WRONG));
            			if(this.getBoard().isActivateBtn(FancyBoardPanel.MIC_BTN))
                            this.getBoard().activateBtn(FancyBoardPanel.MIC_BTN);
                        this.dummySleep(2000);
            		}
            		
            	}else if (focusText instanceof Word){
            		if(focusText.getChances()==0){
                    	this.blackBoard.setMessage(new Message(Message.CHANCES_OVER));
                    	this.dummySleep(3000);
                    	this.awake();
                    }else{
                    	this.blackBoard.setMessage(new Message(Message.WRONG));
                    	if(this.getBoard().isActivateBtn(FancyBoardPanel.MIC_BTN))
                            this.getBoard().activateBtn(FancyBoardPanel.MIC_BTN);
                        this.dummySleep(3000);
                    }
            	}
            	 
                
                //word by word
                /*if(focusText instanceof Phrase && this.focusText.getErrors()==phraseTolerance){
                    this.readWordByWord = true;
                    this.awake();
                    return;
                }*/
                    
                 
                /*if (this.focusText instanceof Word) {
                    this.blackBoard.setMessage(new Message(Message.WORD));
                }else
                    this.blackBoard.setMessage(new Message(Message.DEFAULT));*/
                 
            }
        }

        //Some times, the voice recognizer dies. So, this method prevents this.
        //I think this is a serious BUG!
        /*if (!juliusCommandLine.isJuliusAlive() && !this.finished) {
            DebugPrinter.print("Julian is dead for some kind of reason. Running it again...", DebugPrinter.DBG_WARNING_MESG);

            this.outputGobbler = this.juliusCommandLine.runJulius(this);
        }*/

    }

    public IBoard getBoard() {
        return this.blackBoard;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
        if (finished) {
            this.juliusCommandLine.killJulius();

        }
         
    }

    public void restartExercise() {
        for (Exercise e : this.exercises) {
            for (Phrase phrase : e.getPhrases()) {
                phrase.setErrors(0);
            }
        }
        this.juliusCommandLine.killJulius();
        this.kill = true;
        this.awake();
    }
    
    public void forceKillJulius(){
    	this.juliusCommandLine.killJulius();
    }
    
    public void forceRunJulius(){
    	this.blackBoard.setMessage(new Message(Message.WAIT_INIT));
    	if(this.outputGobbler!=null)
    		this.outputGobbler.killMe();
    	//this.juliusCommandLine = new JuliusCommandLine();
    	this.outputGobbler = this.juliusCommandLine.runJulius(this);
    	//this.capture();
    }

    public void clean() {
        this.juliusCommandLine.killJulius();
    }

    public Exercise getCurrentExercise() {
        return currentExercise;
    }

    public Text getFocusText() {
        return focusText;
    }

    public void setFocusText(Text focusText) {
        this.focusText = focusText;
    }

    
    private void repeatWordByWord(Phrase p) {
        //word by word
         
                
        for (Word w : p.getWords()) {

            this.focusText = w;
            //System.out.println(w);
            w.setFocus(true);

            this.blackBoard.setMessage(new Message(Message.WORD));

            sleep();
            
            w.setFocus(false);

        }

        this.readWordByWord = false;
    }
    
    public void skipText(){
        this.focusText.setErrors(-1);
        this.awake();
    }
    
    public void capture(){
       
       if(this.outputGobbler.isPause())
           this.outputGobbler.unpause();
       else
           this.outputGobbler.pause();
       
       this.blackBoard.setMessage(new Message(Message.PAUSE));
    }
    
    public void calculateExercisePercentage(int index, int total){
    	//System.out.println("index: " + index);
    	//System.out.println("total: " + total);
    	int percent = (index*100)/total;
    	
    	this.blackBoard.updateProgress(percent);
    	
    }

    public void initiateTimeBomber(){
    	this.timeBomber = new TimeBomber(this);
    	this.setBomberActive(true);
    	this.timeBomber.start();
    }
    
	public int getFocusTextPosition() {
		return focusTextPosition;
	}

	public int getTotalText() {
		return totalText;
	}

	public int getErrors() {
		return errors;
	}

	public int getScore() {
		return score;
	}

	public boolean isReadWordByWord() {
		return readWordByWord;
	}

	public boolean isBomberActive() {
		if(timeBomber!=null && bomberActive)
			return true;
		return bomberActive;
	}

	public void setBomberActive(boolean bomberActive) {
		this.bomberActive = bomberActive;
	}

	public TimeBomber getTimeBomber() {
		return timeBomber;
	}

	private void resetChances(){
		for(Exercise e : this.exercises)
			for(Phrase p : e.getPhrases())
				p.setChances(p.getOriginalChanceNumber());
	}
	
	private void resetTries(){
		this.errors = 0;
		this.score = 0;
		for(Exercise e : this.exercises)
			for(Phrase p : e.getPhrases())
			{
				p.setErrors(0); 
				p.setChances(p.getOriginalChanceNumber());
			}
	}

	public JApplet getParent() {
		return parent;
	}
	
	public void off(){
		if(this.offState)
			return;
		this.timeBomber.turnOff();
		this.offState = true;
		this.awake();
	}
	
	public boolean getOffState(){
		return this.offState;
	}
    
}
