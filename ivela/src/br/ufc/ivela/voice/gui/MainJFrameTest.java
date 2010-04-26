/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.ivela.voice.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import br.ufc.ivela.voice.julius.JuliusConstants;
import br.ufc.ivela.voice.julius.JuliusInstaller;
import br.ufc.ivela.voice.teacher.Teacher;
import br.ufc.ivela.voice.util.Message;


/**
 *
 * @author jefferson
 */
public class MainJFrameTest extends JFrame {

    private static final long serialVersionUID = 1L;
    

    public MainJFrameTest() {
        
        //this.setSize(new Dimension(500, 400));
        //this.setLayout(new BorderLayout());
        
        final Teacher t = new Teacher(null);
        
        
        /*t.createExercise(
		        		//"Good morning.#" +
		        		//"My name's Tina.#" +
		        		//"Here's my card.#"+ 
		        		//"Nice too meet you, Tina.#" +
		        		//"I'm Bob.# " +
		        		//"I am with IBM in Mexico.#" +         //hard, not work
		        		//"Nice to meet you, too, Bob.#" +
		        		//"Where are you from?#" +
		        		//"England?#" +
		        		//"Yes, I'm from London.#" +
		        		//"And I'm Jonh.#" +
		        		"I am from England.#" +
		        		"I am very pleased to meet you all.#",
		        		
                         "julian_10.jconf",
                         "Repeat",
                         
                         //"jr.ogg#" +
                         //"hdf.ogg#" +
                         //"tw.ogg#" +
                         //"whc.ogg#" +
                         //"icj.ogg#" +
                         //"wl007m.ogg#"+
                         //"jr.ogg#" +
                         "hdf.ogg#" +
                         //"tw.ogg#" +
                         //"whc.ogg#" +
                         //"icj.ogg#" +
                         //"wl007m.ogg#"+
                         "wl007m.ogg#",
                         
                         //"6#" +
                         //"6#" +
                        // "6#" +
                        // "6#" +
                        // "6#" +
                         //"6#" +
                         //"6#" +
                        // "6#" +
                        // "6#" +
                         //"6#" +
                        // "6#" +
                         "6#" +
                         "6#");*/
        
        t.createExercise("He dial five.# Tina write.#",
                         "julian_0.jconf",
                         "Title",
                         "hdf.ogg#tw.ogg#",
                         "10#10#");
        
        /*t.createExercise("I live with my parents. " +
                         "You play alone. " +
                         "I watch TV. " +
                         "I don't smoke. " +
                         "We have two children. " +
                         "Jordan has homework.",
                         "julian_0.jconf",
                         "Title",
                         "ydf.ogg#ydf.ogg#ydf.ogg#ydf.ogg#ydf.ogg#ydf.ogg#");*/
        
        
        //final IBoard mainPanel = new HTMLBoardPanel(t);
        final IBoard mainPanel = new FancyBoardPanel(t, false, null);
        this.add(mainPanel, BorderLayout.CENTER);
        
        this.setVisible(true);
        
        
         //istalling..client must wait download (just first time).
         //mainPanel.setMessage(new Message(Message.INSTALL));
         Thread installThread = new Thread(){
        	@Override
        	public void run() {
        		JuliusInstaller.panel = mainPanel;
        		JuliusInstaller.install(JuliusConstants.JULIUS_FILE_HOST);
                        mainPanel.setInstalled(true);
                        mainPanel.setMessage(new Message(Message.START));
                        //kill julius, if it is already started.
                        t.clean();
               
                
                        //start application (teacher logic)
                        t.start();
        	}
        };
        installThread.start();
       
        
        
        
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                t.clean();
                System.exit(0);
            }
        });
        this.pack();
        this.setVisible(true);
       
    }

    public static void main(String args[]) {
        new MainJFrameTest();
    }
}
