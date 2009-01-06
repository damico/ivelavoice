/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.ivela.voice.util;

/**
 * A set of messages that are useful for client visual feedback. And system too.
 * @author jefferson
 */
public class Message {

    public static final int  EXCELLENT = 0;
    public static final int  VERY_WELL = 1;
    public static final int  GOOD = 2;
    public static final int  WRONG = 3;
    
    public static final int DEFAULT = 10;
    public static final int FINISH = 11;
    public static final int START = 12;
    public static final int INSTALL = 13;
    public static final int WAIT = 14;
    public static final int WORD = 15;
    public static final int PAUSE = 16;
    public static final int REPEAT_TEXT = 17;
    public static final int SEND_RESULTS = 18;
    public static final int SEND_RESULTS_DONE = 19;
    public static final int CHANCES_OVER = 20;
    public static final int SOUND_ERROR = 21;
    public static final int WORD_BY_WORD_MODULE = 22;
    public static final int SKIP = 23;
    public static final int RECOGNIZER_SEARCH_ERROR = 24;
    public static final int TURN_OFF = 25;
    public static final int WAIT_INIT = 26;
    public static final int NOW_TALK = 27;
    
    public static final String VERY_WELL_MSG = "Very well!";
    public static final String EXC_MSG = "Excellent!";
    public static final String GOOD_MSG = "Good!";
    public static final String WRONG_MSG = "Sorry, click \"MIC\" and try again please.";
    public static final String SKIP_MSG = "Sorry, time over. Skipping...";
    
    public static final String DEFAULT_MSG = "Please, click \"PLAY\" to listen.";
    public static final String WORD_MSG = "Please, repeat the word below.";
    public static final String FINISH_MSG = "Good! You have finished.";
    public static final String SEND_RESULTS_MSG = "Your results are being sent to our server...";
    public static final String SEND_RESULTS_DONE_MSG = "Done! The exercise is finished.";
    public static final String START_MSG = "Please, click \"ON\" to begin.";
    public static final String INSTALL_MSG = "Please, wait. The voice recognizer is being installed...";
    public static final String WAIT_MSG = "Please, wait while voice recognizer is being initiated...";
    public static final String REPEAT_TEXT_MSG = "Now, click \"MIC\" and repeat the the text below.";
    public static final String PAUSE_MSG = "Wait";
    public static final String CHANCES_OVER_MSG = "Wrong. Your chances were over. Skipping...";
    public static final String SOUND_ERROR_MSG = "Error playing sound.";
    public static final String WORD_BY_WORD_MODULE_MSG = "Chances gone. Initializing word by word module."; 
    public static final String RECOGNIZER_SEARCH_ERROR_MSG = "Sentence error. Click mic and try change your pronounce.";
    public static final String TURN_OFF_MSG = "Turning off the system...";
    public static final String WAIT_INIT_MSG = "Wait!";
    public static final String NOW_TALK_MSG = "Now talk";
    
    private int type;
    public String content;
    
    public Message(int type){
        this.type = type;
        switch(type){
            case Message.EXCELLENT:
                 this.content = Message.EXC_MSG;
            break;
            case Message.VERY_WELL:
                this.content = Message.VERY_WELL_MSG;
            break;
            case Message.GOOD:
                this.content = Message.GOOD_MSG;
            break;
            case Message.WRONG:
                this.content = Message.WRONG_MSG;
            break;
            case Message.DEFAULT:
                 this.content = Message.DEFAULT_MSG;
            break;
            case Message.FINISH:
                this.content = Message.FINISH_MSG;
            break;
            case Message.START:
                this.content = Message.START_MSG;
            break;
            case Message.INSTALL:
                this.content = Message.INSTALL_MSG;
            break;
            case Message.WAIT:
                this.content = Message.WAIT_MSG;
            break;
            case Message.WORD:
                this.content = Message.WORD_MSG;
            break;
            case Message.PAUSE:
                this.content = Message.PAUSE_MSG;
            break;
            case Message.SEND_RESULTS:
                this.content = Message.SEND_RESULTS_MSG;
            break;
            case Message.SEND_RESULTS_DONE:
                this.content = Message.SEND_RESULTS_DONE_MSG;
            break;
            case Message.CHANCES_OVER:
                this.content = Message.CHANCES_OVER_MSG;
            break;
            case Message.SOUND_ERROR:
                this.content = Message.SOUND_ERROR_MSG;
            break;
            case Message.REPEAT_TEXT:
                this.content = Message.REPEAT_TEXT_MSG;
            break;
            case Message.WORD_BY_WORD_MODULE:
                this.content = Message.WORD_BY_WORD_MODULE_MSG;
            break;
            case Message.SKIP:
                this.content = Message.SKIP_MSG;
            break;
            case Message.RECOGNIZER_SEARCH_ERROR:
                this.content = Message.RECOGNIZER_SEARCH_ERROR_MSG;
            break;
            case Message.TURN_OFF:
                this.content = Message.TURN_OFF_MSG;
            break;
            case Message.WAIT_INIT:
                this.content = Message.WAIT_INIT_MSG;
            break;
            case Message.NOW_TALK:
                this.content = Message.NOW_TALK_MSG;
            break;
     
        }
       
    }
    
   
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
       
    }

 
    public String toString() {
        return this.content;
    }
    
    
    
}
