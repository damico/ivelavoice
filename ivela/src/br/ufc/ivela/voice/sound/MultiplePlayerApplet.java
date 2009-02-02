/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.ivela.voice.sound;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufc.ivela.voice.julius.JuliusConstants;

/**
 *
 * @author jefferson
 */
public class MultiplePlayerApplet extends JApplet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
     
    
    private String[] titleTokenArray;
    private String[] audioTokenArray;
    private String server;
    private int divisor;
 
    public void init() {
     
    	
    	this.readParameters();
    	this.setBackground(Color.WHITE);
    	int audioFileNumber = audioTokenArray.length;
    	int cols,rows;
    	if(audioFileNumber<=divisor){
    		rows = audioFileNumber;
    		cols = 1;
    	}else{
    		rows = divisor;
    		double ceil = Math.ceil(audioFileNumber/((double)divisor));
    		System.out.println(ceil);
    		cols = (int) ceil;
    	}
    	System.out.println(rows);
    	System.out.println(cols);
    	this.setLayout(new GridLayout(rows,cols,0,0));
    	for(int i=0;i<audioFileNumber;i++){
    		PlayerPanel player = new PlayerPanel(server+this.audioTokenArray[i],
    											 this.titleTokenArray[i]);
    		
    		this.add(player);
    	}
    }

    public void readParameters() {
        //String audioURL = getParameter("audio_url");
        String audioServer = getParameter("audio_server");
        String titleArray = getParameter("title_array");
        String audioArray = getParameter("audio_array");
        String divisor = getParameter("divisor");
        String color = getParameter("bkg");
        
        if(titleArray!=null && audioArray!=null && audioServer!=null){
        	this.server = audioServer;
        	this.divisor = Integer.parseInt(divisor);
        	this.titleTokenArray = titleArray.split("#");
        	this.audioTokenArray = audioArray.split("#");	
        	if(color!=null){
        		JuliusConstants.BG_COLOR = color;
        	}
        }    
    }
    
    public static void main(String[] args) {
		
		double ceil = Math.ceil(8/5.0);
		System.out.println(ceil);
		System.out.println((int)(ceil)+"");
	}
}

class PlayerPanel extends JPanel{
 
	private static final long serialVersionUID = 1L;
	private StandAlonePlayer player;
	
	public PlayerPanel(final String url, String title){
		final StandAlonePlayerFactory playerFactory = new StandAlonePlayerFactory();
		this.setBackground(new Color(Integer.parseInt(JuliusConstants.BG_COLOR,
                16)));
        //btn play
        Icon playImg = new ImageIcon(this.getClass().getResource("/br/ufc/ivela/voice/images/play_on_orange.png"));
        Icon stopImg = new ImageIcon(this.getClass().getResource("/br/ufc/ivela/voice/images/stop_on_orange.png"));
        Icon pauseImg = new ImageIcon(this.getClass().getResource("/br/ufc/ivela/voice/images/pause_on.png"));
        
        JLabel label = new JLabel(title);
        label.setSize(120, 12);
        label.setLocation(2, 0);
        label.setFont(new Font("arial",Font.PLAIN,10));
        
        JButton btnPlay = new JButton();
        btnPlay.setIcon(playImg);
        btnPlay.setFocusPainted(false);
        btnPlay.setSize(new Dimension(37,26));
        btnPlay.setLocation(2,15);
        btnPlay.setBackground(new Color(Integer.parseInt(JuliusConstants.BG_COLOR,
                16)));

        btnPlay.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	
        		player = playerFactory.getPlayer(url);
        		player.start();

            }
        });
        
        //pause btn
        JButton btnPause = new JButton();
        btnPause.setIcon(pauseImg);
        btnPause.setFocusPainted(false);
        btnPause.setSize(new Dimension(37,26));
        btnPause.setLocation(39,15);
        btnPause.setBackground(new Color(Integer.parseInt(JuliusConstants.BG_COLOR,
                16)));

        btnPause.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (player!=null) {
                    player.togglePause();
                }

            }
        });
        
        //stop btn
        JButton btnStop = new JButton();
        btnStop.setIcon(stopImg);
        btnStop.setFocusPainted(false);
        btnStop.setSize(new Dimension(37,26));
        btnStop.setLocation(76,15);
        btnStop.setBackground(new Color(Integer.parseInt(JuliusConstants.BG_COLOR,
                16)));
        btnStop.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (player!=null) {
                     player._stopPlay();
                }

            }
        });
        
        

        
        this.setLayout(null);
        this.add(label);
        this.add(btnPlay);
        this.add(btnPause);
        this.add(btnStop);
        this.setBackground(new Color(Integer.parseInt(JuliusConstants.BG_COLOR,
                16)));
	}
}
