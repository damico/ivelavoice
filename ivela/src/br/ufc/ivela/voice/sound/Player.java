/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.ivela.voice.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import br.ufc.ivela.voice.gui.FancyBoardPanel;
import br.ufc.ivela.voice.gui.IBoard;
import br.ufc.ivela.voice.util.Message;

import javazoom.spi.PropertiesContainer;

/**
 * Simple player (based on Vorbis SPI) unit test.
 * It takes around 3%-5% of CPU and 10MB RAM under Win2K/P4/2.4GHz/JDK1.4.1
 * It takes around 4% of CPU and 10MB RAM under Win2K/Athlon/1GHz/JDK1.3.1
 */
public class Player extends Thread {
    //private String filename=null;
    private String fileurl;
    private boolean stop = false;
    private SourceDataLine line = null;
    private AudioInputStream in = null;
    private AudioInputStream din = null;
    private IBoard board;

    public Player(String fileurl, IBoard board) {
        System.out.println("URL: " + fileurl);
    	this.board = board;
    	this.fileurl = fileurl;
        
    }

     

    public AudioInputStream getAudio() {
        return this.in;
    }

    @Override
    public void run() {

        this._testPlayURL();
    }

    public void _testPlayURL() {
        System.out.println("22");
        this.stop = false;
        try {

            URL url = new URL(fileurl);


            in = AudioSystem.getAudioInputStream(url);

            din = null;
            if (in != null) {
                AudioFormat baseFormat = in.getFormat();

                AudioFormat decodedFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        baseFormat.getSampleRate(),
                        16,
                        baseFormat.getChannels(),
                        baseFormat.getChannels() * 2,
                        baseFormat.getSampleRate(),
                        false);

                din = AudioSystem.getAudioInputStream(decodedFormat, in);
                if (din instanceof PropertiesContainer) {
                    System.out.println("PropertiesContainer : OK");
                } else {
                    System.out.println("Wrong PropertiesContainer instance");
                }
                rawplay(decodedFormat, din);
                in.close();

                System.out.println("finished play sound : OK");
                //call gui
                this.board.setMessage(new Message(Message.REPEAT_TEXT));
                this.board.activateBtn(FancyBoardPanel.PLAY_BTN);
                this.board.toggleButton(FancyBoardPanel.SPEAKER_BTN);
            }
        } catch (Exception e) {
        	
        	this.board.setMessage(new Message(Message.SOUND_ERROR));
        	this.board.toggleButton(FancyBoardPanel.SPEAKER_BTN);
        	this.board.activateBtn(FancyBoardPanel.PLAY_BTN);
        	this.dummySleep();
        	this.board.setMessage(new Message(Message.REPEAT_TEXT));
            //System.out.println("testPlay : " + e.getMessage());
        }
    }

    private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {
        SourceDataLine res = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);
        return res;
    }

    private void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException, LineUnavailableException {
        System.out.print(".");
        byte[] data = new byte[4096];
        line = getLine(targetFormat);
        if (line != null) {
            // Start
            line.start();
            int nBytesRead = 0;
            while (nBytesRead != -1 && !stop) {


                nBytesRead = din.read(data, 0, data.length);
                if (nBytesRead != -1) {
                    line.write(data, 0, nBytesRead);
                }
            }
            // Stop
            line.drain();
            line.stop();
            line.close();
            din.close();
        }
    }

    public boolean isSameURL(String url) {
        return (url.equalsIgnoreCase(this.fileurl));

    }

    public void _stopPlay() {
        this.stop = true;
    }
    
    public void dummySleep(){
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
}
