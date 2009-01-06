/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.ivela.voice.teacher;

/**
 * The Text class gives generic information shared between is childreen such as 
 * an audio file, content and a focus flag.
 * @author jefferson
 */
public class Text {

    private String content;
    private String audioURL;
    private String tip;
    private boolean focus = false;
    private int errors = 0;
    private int chances = 3;
    private int originalChanceNumber = 3;

	public Text() {
        this.content = "";
    }

    public String getAudioURL() {
        return audioURL;
    }

    public String getContent() {
        return content;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }
    
    public int getErrors() {
        return errors;
    }

    public void setErrors(int tries) {
        this.errors = tries;
    }
    
    public void incErrors() {
        this.errors++;
    }
    
    public int getChances() {
		return chances;
	}

	public void setChances(int chances) {
		this.chances = chances;
	}
	
	public void decreaseChance(){
		this.chances--;
	}
	
	public int getOriginalChanceNumber() {
		return originalChanceNumber;
	}

	public void setOriginalChanceNumber(int originalChanceNumber) {
		this.originalChanceNumber = originalChanceNumber;
	}

	public void clean(){
		this.errors = 0;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
	
	
}
