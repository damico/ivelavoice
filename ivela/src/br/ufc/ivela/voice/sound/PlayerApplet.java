package br.ufc.ivela.voice.sound;
 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import br.ufc.ivela.voice.julius.JuliusConstants;
import br.ufc.ivela.voice.util.ServletComm;

/**
 *
 * @author jefferson
 */
public class PlayerApplet extends JApplet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String url = "";
    private StandAlonePlayer player;
    private JLabel staLabel = new JLabel("stopped");

    public void init() {
        final StandAlonePlayerFactory playerFactory = new StandAlonePlayerFactory();
        
        this.staLabel.setSize(new Dimension(111,20));
        this.staLabel.setLocation(0, 27);
        this.staLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //this.staLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        
        //btn play
        Icon playImg = new ImageIcon(this.getClass().getResource("/br/ufc/ivela/voice/images/play_on_orange.png"));
        Icon stopImg = new ImageIcon(this.getClass().getResource("/br/ufc/ivela/voice/images/stop_on_orange.png"));
        Icon pauseImg = new ImageIcon(this.getClass().getResource("/br/ufc/ivela/voice/images/pause_on.png"));
        
        JButton btnPlay = new JButton();
        btnPlay.setIcon(playImg);
        btnPlay.setFocusPainted(false);
        btnPlay.setSize(new Dimension(37,26));
        btnPlay.setLocation(0,0);
        btnPlay.setBackground(Color.white);

        btnPlay.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	//CHAMADA JAVASCRIPT!!!!
            	callJS();
            	staLabel.setText(JuliusConstants.SND_MSG_CON);
        		player = playerFactory.getPlayer(url,staLabel);
        		player.start();

            }
        });
        
        //pause btn
        JButton btnPause = new JButton();
        btnPause.setIcon(pauseImg);
        btnPause.setFocusPainted(false);
        btnPause.setSize(new Dimension(37,26));
        btnPause.setLocation(37,0);
        btnPause.setBackground(Color.white);

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
        btnStop.setLocation(74,0);
        btnStop.setBackground(Color.white);
        btnStop.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	
                if (player!=null) {
                     player._stopPlay();
                }
                staLabel.setText(JuliusConstants.SND_MSG_STP);

            }
        });
        
        
        this.readParameters();
        
        this.setLayout(null);
        
        this.add(btnPlay);
        this.add(btnPause);
        this.add(btnStop);
        this.add(staLabel);
        this.setBackground(Color.white);

    }

    public void readParameters() {
        String paramURL = getParameter("audio_url");
        String audioHost = getParameter("audioHost");

        if (paramURL != null) {
        	JuliusConstants.AUDIO_URL= audioHost;
        	this.url = JuliusConstants.AUDIO_URL+paramURL;
             
        }
    }

    public void callJS(){
    	//CHAMADA JAVASCRIPT!!!
    	ServletComm.callJavasScriptComputeUrl(this, url);
    }
	public JLabel getStaLabel() {
		return staLabel;
	}

	public void setStaLabel(JLabel staLabel) {
		this.staLabel = staLabel;
	}
}
