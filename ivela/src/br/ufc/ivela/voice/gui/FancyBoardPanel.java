/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.ivela.voice.gui;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JProgressBar;

import br.ufc.ivela.voice.handler.FancyMouseHandler;
import br.ufc.ivela.voice.julius.JuliusConstants;
import br.ufc.ivela.voice.teacher.Exercise;
import br.ufc.ivela.voice.teacher.Phrase;
import br.ufc.ivela.voice.teacher.Teacher;
import br.ufc.ivela.voice.teacher.Word;
import br.ufc.ivela.voice.util.Message;

/**
 * 
 * @author jefferson
 */
public class FancyBoardPanel extends IBoard {

	private static final long serialVersionUID = 1L;
	// logical stuff
	private Teacher teacher;
	private boolean start = false;
	private boolean installed = false;
	private boolean finish = false;
	private boolean captureToogle = false;
	private boolean enableControlButton = false;
	private boolean simpleBkg = false;
	private String build = JuliusConstants.BUILD_VERSION;
	private String focusTip = null;
	
	private float glowFactor = 0.1f;
	private int dotCount = 0;
	//private float glowBkg = 0f;

	// Grapical stuff
	private Icon playerBackground = new ImageIcon(this.getClass().getResource(
			"/br/ufc/ivela/voice/images/player_bg.png"));
	
	
	private Icon exerciseProgress0 = new ImageIcon(this.getClass().getResource(
	"/br/ufc/ivela/voice/images/0.png"));
	private Icon exerciseProgress1 = null;
	private Icon exerciseProgress2 = null;
	private Icon exerciseProgress3 = null;
	private Icon exerciseProgress4 = null;
	private Icon exerciseProgress5 = null;
	private Icon exerciseProgress6 = null;
	private Icon exerciseProgress7 = null;
	private Icon exerciseProgress8 = null;
	private Icon exerciseProgress9 = null;
	private Icon exerciseProgress10 = null;
	 
	
	private Icon tipIcon = new ImageIcon(this.getClass().getResource(
	"/br/ufc/ivela/voice/images/ballon.png"));
	
	private Icon tipIconSd = new ImageIcon(this.getClass().getResource(
	"/br/ufc/ivela/voice/images/ballon_sd.png"));
	
	private Icon exerciseProgress =  exerciseProgress0;
	
	private Icon playBtnOn = new ImageIcon(this.getClass().getResource(
			"/br/ufc/ivela/voice/images/play_on.png"));
	private Icon stopBtnOn = new ImageIcon(this.getClass().getResource(
			"/br/ufc/ivela/voice/images/stop_on.png"));
	private Icon skipBtnOn = new ImageIcon(this.getClass().getResource(
			"/br/ufc/ivela/voice/images/skip_on.png"));
	private Icon micBtnOn = new ImageIcon(this.getClass().getResource(
			"/br/ufc/ivela/voice/images/mic_on.png"));

	private Icon turn_on = new ImageIcon(this.getClass().getResource(
	"/br/ufc/ivela/voice/images/turn_on.png"));
	
	private Icon turn_off = new ImageIcon(this.getClass().getResource(
	"/br/ufc/ivela/voice/images/turn_off.png"));
	
	
	private Icon turn_default = turn_off;

	private Icon speakerOn = new ImageIcon(this.getClass().getResource(
			"/br/ufc/ivela/voice/images/speaker.gif"));
	private Icon speakerOff = new ImageIcon(this.getClass().getResource(
			"/br/ufc/ivela/voice/images/speaker.png"));
	private Icon speaker = speakerOff;

	private Icon playBtnOff = new ImageIcon(this.getClass().getResource(
			"/br/ufc/ivela/voice/images/play_off.png"));
	private Icon stopBtnOff = new ImageIcon(this.getClass().getResource(
			"/br/ufc/ivela/voice/images/stop_off.png"));
	private Icon skipBtnOff = new ImageIcon(this.getClass().getResource(
			"/br/ufc/ivela/voice/images/skip_off.png"));
	private Icon micBtnOff = new ImageIcon(this.getClass().getResource(
			"/br/ufc/ivela/voice/images/mic_off.png"));

	 
	private Icon playBtn = playBtnOff;
	private Icon stopBtn = stopBtnOff;
	private Icon skipBtn = skipBtnOff;
	private Icon micBtn = micBtnOff; 

	private MessagePanel messagePanel;
	private JProgressBar progressBar;
	private Message currentMessage;
	private Font arial;
	private Font arialContent;
	private Font arialStatistic;
	private Font arialProgress;
	private Font bombTimerFont;
	private Font buildFont;

	private boolean playBtnOnAct = false;
	private boolean stopBtnOnAct = false;
	private boolean skipBtnOnAct = false;
	private boolean micBtnOnAct = false;
	private boolean tipOn = false;

	private boolean onBtnAct = false;
	private boolean offBtnAct = false;

	public static final int PLAY_BTN = 1;
	public static final int STOP_BTN = 2;
	public static final int SKIP_BTN = 3;
	public static final int MIC_BTN = 4;
	public static final int OFF_BTN = 5;
	public static final int ON_BTN = 6;
	public static final int RESET_BTN = 7;
	public static final int SPEAKER_BTN = 8;
	public static final int CONTROLE_BTN = 9;
	public static final int TIP_WINDOW = 10;
	private static float ALPHA_ALL= 0F;

	public FancyBoardPanel(Teacher teacher, boolean simpleBkg, JApplet parent) {

		  
		this.setPreferredSize(new Dimension(540, 280));
		this.setBackground(new Color(Integer.parseInt(JuliusConstants.BG_COLOR,
                16)));
		 
		this.setLayout(null);
		this.arial = new Font("arial", Font.BOLD, 11);
		this.arialContent = new Font("arial", Font.BOLD, 14);
		this.arialStatistic = new Font("arial", Font.BOLD, 12);
		this.arialProgress = new Font("arial", Font.BOLD, 14);
		this.bombTimerFont = new Font("arial", Font.BOLD, 12);
		this.buildFont = new Font("lucida", Font.PLAIN, 10);
		this.teacher = teacher;
		this.teacher.setBlackBoard(this);
		this.messagePanel = new MessagePanel();
		this.progressBar = this.messagePanel.getProgressBar();
		this.progressBar.setLocation(95, 140);
		this.progressBar.setSize(200, 10);
		this.add(this.progressBar);
		this.simpleBkg = simpleBkg;
		if(this.simpleBkg)
			playerBackground = new ImageIcon(this.getClass().getResource(
			"/br/ufc/ivela/voice/images/ugly.png"));

		if (!installed) {
			this.setMessage(new Message(Message.INSTALL));
		}

		FancyMouseHandler mouseHandler = new FancyMouseHandler(teacher);
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		
		Thread t = new Thread(){
			@Override
			public void run() {
				 while(true){
					 try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(ALPHA_ALL+0.1F>1.0F)
						break;
					else
					ALPHA_ALL+=0.1F;
					repaint();
				 }
				 ALPHA_ALL=1F;
				 repaint();
				 System.out.println(ALPHA_ALL);
				 
			}
		};
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
		
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		// aliasing, fancy stuff
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.WHITE);

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,ALPHA_ALL));
		
		//speaker 
		if(!this.simpleBkg)
			this.speaker.paintIcon(this, g2d, 340, 80);
		 

		// bkg
		if(!this.simpleBkg)
                this.playerBackground.paintIcon(this, g, 0, 0);
		else
			this.playerBackground.paintIcon(this, g, 0, 30);
		 
	 
		if(!this.simpleBkg)
			this.exerciseProgress.paintIcon(this, g2d, 53, 12);
         
                
		// progress count
		g2d.setFont(this.arialProgress);
		g2d.drawString(this.teacher.getFocusTextPosition() + " of "
				+ this.teacher.getTotalText()+" exercises done", 162, 50);

		// buttons
		this.micBtn.paintIcon(this, g2d, 50, 165);
		this.playBtn.paintIcon(this, g2d, 92, 165);
		// this.stopBtn.paintIcon(this, g2d, 150, 170);
		this.skipBtn.paintIcon(this, g2d, 138, 165);
		
		//this.onBtn.paintIcon(this, g2d, 375, 85);
		//this.offBtn.paintIcon(this, g2d, 415, 85);
		this.turn_default.paintIcon(this, g2d, 382, 70);

		 

		// information phrase
		if (currentMessage != null) {
			g2d.setFont(arial);
			if(currentMessage.getType()!= Message.PAUSE && currentMessage.getType()!= Message.NOW_TALK){
				g2d.drawString(currentMessage.getContent(), 41, 120);
			}else{
				String dots= "";
				for(int i=0;i<=dotCount;i++)
					dots+=".";
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,glowFactor));
				g2d.drawString(currentMessage.getContent()+" "+dots, 41, 120);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));
			}
			
			 
		}

		// draw phrases
		
		if (!start && installed) {

		} else if (start && installed && !finish) {
			List<Exercise> exercices = this.teacher.getExercises();
			for (Exercise exe : exercices) {
				List<Phrase> phrases = exe.getPhrases();
				for (Phrase phrase : phrases) {

					if (phrase.isFocus() && !teacher.isReadWordByWord()) {
						g2d.setColor(Color.YELLOW);
						g2d.setFont(this.arialContent);
						g2d.drawString(phrase.getContent(), 45, 155);
						g2d.setColor(Color.white);
						g2d.setFont(this.arialProgress);
						if(!this.simpleBkg)
							g2d.drawString("Chances left: " + phrase.getChances(), 165, 100);
						else
							g2d.drawString("Chances left: " + phrase.getChances(), 165, 90);
						focusTip = phrase.getTip();
					}else if (phrase.isFocus() && teacher.isReadWordByWord()){
						for(Word word : phrase.getWords()){
							if(word.isFocus()){
								g2d.setColor(Color.RED);
								g2d.setFont(this.arialContent);
								g2d.drawString(word.getContent(), 45, 155);
								g2d.setColor(Color.white);
								g2d.setFont(this.arialProgress);
								g2d.drawString("Chances left: " + word.getChances(), 165, 100);
							}
						}
					}
					

				}//for phrase
			}//for
		}

		//score
		if (start) {
			g2d.setFont(this.arialStatistic);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Tries: ", 285, 195);
			//g2d.setColor(Color.red);
			g2d.drawString(teacher.getErrors() + "", 330, 195);
			//g2d.setColor(Color.white);
			g2d.drawString("Score: ", 285, 180);
			//g2d.setColor(Color.green);
			g2d.drawString(teacher.getScore() + "", 330, 180);
		}
		
		//bomber
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillOval(310, 124, 40, 38);
		g2d.setColor(Color.BLACK);
		g2d.fillOval(315, 129, 30, 28);
		g2d.setFont(this.bombTimerFont);
		g2d.setColor(Color.BLACK);
		//g2d.drawString(TimeBomber.TIMER+"", 24, 34);
		 
		if(teacher.isBomberActive()){
			
			g2d.setFont(this.bombTimerFont);
			g2d.setColor(Color.WHITE);
			if(this.teacher.getTimeBomber().getTimer()>=10)
				g2d.drawString(this.teacher.getTimeBomber().getTimer()+"", 323, 146);
			else
				g2d.drawString(this.teacher.getTimeBomber().getTimer()+"", 327, 146);
		}
		
		//TIP
		/*
		if(focusTip!=null && !this.simpleBkg){
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,glowFactor));
			g2d.setColor(Color.RED);
			g2d.fillOval(430, 10, 40, 38);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));
			g2d.setColor(Color.ORANGE);
			g2d.fillOval(435, 15, 30, 28);
			g2d.setFont(this.bombTimerFont);
			g2d.setColor(Color.BLACK);
			 
			g2d.drawString("?", 447, 28);
			
			g2d.drawString("TIP", 442, 40);
		}
		
		//TIPWIndow
		
		if(this.tipOn && !this.simpleBkg){
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6F));
			this.tipIconSd.paintIcon(this, g2d, 120, 40);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));
			this.tipIcon.paintIcon(this, g2d, 120, 30);
			this.writeText(g2d, focusTip);
			
		}
		*/
		
		//build version
		g2d.setColor(Color.black);
		g2d.setFont(buildFont);
		g2d.drawString("build "+this.build, 225, 235);
		
	}// paint

	private void writeText(Graphics2D g2d, String text){
		g2d.setFont(bombTimerFont);
		g2d.setColor(Color.blue);
		String[] combo = text.split(" ");
		String toPaint = "";
		int disY = 80;
		if(combo.length<6)
			g2d.drawString(text, 200, disY);
		else{
			
		
			for(int i=0;i<combo.length;i++){
				toPaint += combo[i]+" ";
				if(i!=0 && i%5==0){
					g2d.drawString(toPaint, 170, disY);
					toPaint="";
					disY+=20;
				}
					
			}
			g2d.drawString(toPaint, 170, disY);
			toPaint="";
		}
	}
	
	@Override
	public void setMessage(Message msg) {
		this.currentMessage = msg;
		this.repaint();
	}

	@Override
	public void setStart(boolean start) {
		this.start = start;
		if (start) {
			//this.onBtn = onBtn2;
			this.onBtnAct = true;
			//this.offBtn = offBtn1;
			this.turn_default = turn_on;
			this.offBtnAct = false;
			this.finish = false;
		}
		this.repaint();
	}

	@Override
	public boolean isStart() {
		return this.start;
	}

	@Override
	public void setInstalled(boolean value) {
		this.installed = value;
		if (this.installed) {
			this.hideProgressBar();
		}
	}

	@Override
	public void setMinMaxDownloaded(int min, int max) {
		if (this.messagePanel.getMaximum() <= 100) {
			this.messagePanel.setValue(0);
			this.messagePanel.setMaximum(max);

		}

		this.messagePanel.setValue(min);
	}

	@Override
	public void toogleCapture() {
		captureToogle = !captureToogle;
	}

	public void hideProgressBar() {
		this.progressBar.setVisible(false);
	}

	@Override
	protected void finalize() throws Throwable {
		this.teacher.clean();
	}

	public void updateProgress(int progress) {

		 if(progress==0){
			 
			 this.exerciseProgress = this.exerciseProgress0;
			 
		 }else if (progress > 0 && progress < 10) {
			 
			 if(this.exerciseProgress1==null)
				 this.exerciseProgress1 = new ImageIcon(this.getClass().getResource(
					"/br/ufc/ivela/voice/images/1.png"));
			 this.exerciseProgress = exerciseProgress1;
			 
		} else if (progress >= 10 && progress < 20) {
			
			 if(this.exerciseProgress2==null)
				 this.exerciseProgress2 = new ImageIcon(this.getClass().getResource(
					"/br/ufc/ivela/voice/images/2.png"));
			 this.exerciseProgress = exerciseProgress2;
		} else if (progress >= 20 && progress < 30) {
			
			 if(this.exerciseProgress3==null)
				 this.exerciseProgress3 = new ImageIcon(this.getClass().getResource(
					"/br/ufc/ivela/voice/images/3.png"));
			 this.exerciseProgress = exerciseProgress3;
		} else if (progress >= 30 && progress < 40) {
			
			 if(this.exerciseProgress4==null)
				 this.exerciseProgress4 = new ImageIcon(this.getClass().getResource(
					"/br/ufc/ivela/voice/images/4.png"));
			 this.exerciseProgress = exerciseProgress4;
		} else if (progress >= 40 && progress < 50) {
			
			 if(this.exerciseProgress5==null)
				 this.exerciseProgress5 = new ImageIcon(this.getClass().getResource(
					"/br/ufc/ivela/voice/images/5.png"));
			 this.exerciseProgress = exerciseProgress5;
		} else if (progress >= 50 && progress < 60) {
			
			if(this.exerciseProgress6==null)
				 this.exerciseProgress6 = new ImageIcon(this.getClass().getResource(
					"/br/ufc/ivela/voice/images/6.png"));
			 this.exerciseProgress = exerciseProgress6;
		} else if (progress >= 60 && progress < 70) {
			
			if(this.exerciseProgress7==null)
				 this.exerciseProgress7 = new ImageIcon(this.getClass().getResource(
					"/br/ufc/ivela/voice/images/7.png"));
			 this.exerciseProgress = exerciseProgress7;
		} else if (progress >= 70 && progress < 80) {
			
			if(this.exerciseProgress8==null)
				 this.exerciseProgress8 = new ImageIcon(this.getClass().getResource(
					"/br/ufc/ivela/voice/images/8.png"));
			 this.exerciseProgress = exerciseProgress8;
		} else if (progress >= 80 && progress < 100) {
			
			if(this.exerciseProgress9==null)
				 this.exerciseProgress9 = new ImageIcon(this.getClass().getResource(
					"/br/ufc/ivela/voice/images/9.png"));
			 this.exerciseProgress = exerciseProgress9;
		} else if (progress == 100) {
			
			if(this.exerciseProgress10==null)
				 this.exerciseProgress10 = new ImageIcon(this.getClass().getResource(
					"/br/ufc/ivela/voice/images/10.png"));
			 this.exerciseProgress = exerciseProgress10;
		}

		this.repaint();
	}

	public void toggleButton(int type) {

		switch (type) {
		case FancyBoardPanel.MIC_BTN:

			if (this.micBtn == this.micBtnOff)
				this.micBtn = this.micBtnOn;
			break;
		case FancyBoardPanel.PLAY_BTN:
			if (this.playBtn == this.playBtnOff)
				this.playBtn = this.playBtnOn;

			break;
		case FancyBoardPanel.SKIP_BTN:
			if (this.skipBtn == this.skipBtnOff)
				this.skipBtn = this.skipBtnOn;

			break;
		case FancyBoardPanel.STOP_BTN:
			if (this.stopBtn == this.stopBtnOff)
				this.stopBtn = this.stopBtnOn;
			break;
		case FancyBoardPanel.OFF_BTN:
			//this.offBtn = this.offBtn2;
			//this.onBtn = this.onBtn1;
			 
			this.turn_default = turn_off;
			this.offBtnAct = true;
			this.onBtnAct = false;
		break;
		case FancyBoardPanel.ON_BTN:
			//this.offBtn = this.offBtn2;
			//this.onBtn = this.onBtn1;
			
			this.onBtnAct = false;
		break;
		case FancyBoardPanel.SPEAKER_BTN:
			if (this.speaker == speakerOff)
				this.speaker = speakerOn;
			else
				this.speaker = speakerOff;
			break;
		case FancyBoardPanel.RESET_BTN:

			if (!this.playBtnOnAct)
				this.playBtn = playBtnOff;

			this.stopBtn = stopBtnOff;
			this.skipBtn = skipBtnOff;

			if (!this.micBtnOnAct)
				this.micBtn = micBtnOff;

			// this.offBtn = offBtn1;
			// this.onBtn = onBtn1;
			break;
			
		case FancyBoardPanel.TIP_WINDOW:
			this.tipOn = !this.tipOn;
			if(this.tipOn==false)
				FancyBoardPanel.ALPHA_ALL = 1F;
			else
				FancyBoardPanel.ALPHA_ALL = 1F;
			break;
		}

		this.repaint();
	}

	public void activateBtn(int type) {
		switch (type) {

		case FancyBoardPanel.MIC_BTN:
			if (!micBtnOnAct) {
				this.micBtn = this.micBtnOn;
				this.micBtnOnAct = true;
			} else {
				this.micBtn = this.micBtnOff;
				this.micBtnOnAct = false;
			}
			break;
		case FancyBoardPanel.PLAY_BTN:
			if (!playBtnOnAct) {
				this.playBtn = this.playBtnOn;
				this.playBtnOnAct = true;
			} else {
				this.playBtn = this.playBtnOff;
				this.playBtnOnAct = false;
			}
			break;
		case FancyBoardPanel.CONTROLE_BTN:
			this.enableControlButton = !enableControlButton;
			break;

		}
		

		this.repaint();
	}

	public boolean isActivateBtn(int type) {
		switch (type) {
		case FancyBoardPanel.MIC_BTN:
			return this.micBtnOnAct;
		case FancyBoardPanel.PLAY_BTN:
			return this.playBtnOnAct;
		case FancyBoardPanel.SKIP_BTN:
			return this.skipBtnOnAct;
		case FancyBoardPanel.STOP_BTN:
			return this.stopBtnOnAct;
		case FancyBoardPanel.ON_BTN:
			return this.onBtnAct;
		case FancyBoardPanel.OFF_BTN:
			return this.offBtnAct;
		case FancyBoardPanel.CONTROLE_BTN:
			return this.enableControlButton;
		case FancyBoardPanel.TIP_WINDOW:
			return this.tipOn;

		}

		return false;
	}

	public void initiateGlow(){
		//glowing
		Thread glow = new Thread(){
			@Override
			public void run() {
				int signal = +1;
				int signalDot = +1;
				while(true){
					repaint();
					try {
						sleep(100);
						dotCount+=signalDot*1;
						if(dotCount==10)
							signalDot=-1;
						else if (dotCount==0)
							signalDot = +1;
						if((glowFactor+(signal*0.1f))>1f)
							glowFactor = 1f;
						else
							if ((glowFactor+(signal*0.1f))<0.1f)
								glowFactor = 0.1f;
							else
						glowFactor+=(signal*0.05f);
						
						if(glowFactor==1f)
							signal=-1;
						else if(glowFactor==0.1f)
							signal=+1;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}//while
				
			}
		};
		glow.setPriority(Thread.MAX_PRIORITY);
		glow.start();
	}
	
	public boolean isBoardRequireJavaScript(){
		return simpleBkg;
	}
	
	public void setTeacher(Teacher teacher){
		this.teacher = teacher;
	}
}

 