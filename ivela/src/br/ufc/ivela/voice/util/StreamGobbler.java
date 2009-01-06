package br.ufc.ivela.voice.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.ufc.ivela.voice.julius.IJuliusParent;

/**
 * This class is used for julius input and output manipulation.
 * @author jefferson
 */
public class StreamGobbler extends Thread {

    private InputStream is;
    private IJuliusParent parent = null;
     
    private boolean pause = true;
    private boolean killMe = false;
    
    public StreamGobbler(InputStream is, String type, IJuliusParent parent) {
        this.is = is;
        this.parent = parent;
        //System.out.println("Criando Stream Globber for " + type);
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while ((line = br.readLine()) != null && !killMe) {
             //System.out.println(line);
            	if(!pause)
                    Parser.parse(line, this.parent);
            	 
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
    
     
    
    public void pause(){
        this.pause = true;
    }
    
    public void unpause(){
        this.pause = false;
        //this.pauseSemaphore.release();
        
    }

    public boolean isPause() {
        return pause;
    }
    
    public void killMe(){
    	
    }
    
    
}
