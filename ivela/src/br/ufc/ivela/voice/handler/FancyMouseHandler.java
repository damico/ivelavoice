/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.ivela.voice.handler;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import br.ufc.ivela.voice.gui.FancyBoardPanel;
import br.ufc.ivela.voice.julius.JuliusConstants;
import br.ufc.ivela.voice.sound.Player;
import br.ufc.ivela.voice.teacher.Teacher;
import br.ufc.ivela.voice.util.ServletComm;

/**
 * 
 * @author jefferson
 */
public class FancyMouseHandler implements MouseListener, MouseMotionListener {

	private Teacher teacher;
	
	protected Cursor cursorNormal = new Cursor(Cursor.DEFAULT_CURSOR);
	protected Cursor cursorMao = new Cursor(Cursor.HAND_CURSOR);

	public FancyMouseHandler(Teacher teacher) {
		this.teacher = teacher;
		 
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//System.out.println("X: " + x + " Y: " + y);
		
		boolean isOn = this.teacher.getBoard().isActivateBtn(
				FancyBoardPanel.ON_BTN);

		if ((x >= 58 && x <= 80 & y >= 172 && y <= 200) && !this.teacher.getBoard().isActivateBtn(FancyBoardPanel.TIP_WINDOW)) {
			// mic
                        if(!this.teacher.isFinished()){
                            if (!this.teacher.getBoard().isActivateBtn(FancyBoardPanel.MIC_BTN)
                                            && isOn) {
                            	
                            		this.teacher.forceRunJulius();
                                    this.teacher.getBoard().activateBtn(FancyBoardPanel.MIC_BTN);
                                    this.teacher.getBoard().toogleCapture();
                                    this.teacher.capture();
                                    this.teacher.initiateTimeBomber();
                            }
                        }

		} else if (x >= 100 && x <= 128 & y >= 172 && y <= 200 && !this.teacher.getBoard().isActivateBtn(FancyBoardPanel.TIP_WINDOW)) {
			// play
                        if(!this.teacher.isFinished() && !teacher.getBoard().isActivateBtn(FancyBoardPanel.MIC_BTN)){
                            if (!this.teacher.getBoard()
                                            .isActivateBtn(FancyBoardPanel.PLAY_BTN)
                                            && isOn) {
                            	    //CHAMADA JAVASCRIPT!!!!
                            		ServletComm.callJavasScriptComputeExe(this.teacher.getParent(), JuliusConstants.EXE_ID);
                                    
                            	    this.teacher.getBoard().activateBtn(FancyBoardPanel.PLAY_BTN);
                                    this.teacher.getBoard().toggleButton(FancyBoardPanel.SPEAKER_BTN);

                                    if (this.teacher.getFocusText() != null) {
                                    	    teacher.getBoard().setSoundMsg(JuliusConstants.SND_MSG_CON);
                                            Player player = new Player(this.teacher.getFocusText()
                                                            .getAudioURL(), this.teacher.getBoard());
                                            player.start();
                                    }
                            }
                        }
		}/* else if (x >= 161 && x <= 191 & y >= 247 && y <= 275) {
			// stop
		}*/ else if (x >= 150 && x <= 172 & y >= 172 && y <= 200 && !this.teacher.getBoard().isActivateBtn(FancyBoardPanel.TIP_WINDOW)) {
			// skip
			if (isOn){
				if(teacher.getBoard().isActivateBtn(FancyBoardPanel.MIC_BTN)){
					this.teacher.getTimeBomber().turnOff();
					this.teacher.getBoard().activateBtn(FancyBoardPanel.MIC_BTN);
	                this.teacher.getBoard().toogleCapture();
	                this.teacher.capture();
				}
                this.teacher.skipText();
			}
				
		} else if (x >= 387 && x <= 405 & y >= 78 && y <= 95) {
			// on
			if(!isOn){
				if(!teacher.isAlive())
					this.teacher.start();
				this.teacher.awake();
			}
				

		} else if (x >= 418 && x <= 436 & y >= 78 && y <= 95) {
			// off
			if(isOn && !this.teacher.getBoard().isActivateBtn(FancyBoardPanel.TIP_WINDOW)){
				  
				this.teacher.off();
				
			}
			
		}else if (x >= 436 && x <= 462 & y >= 17 && y <= 42) {
			 //TIP
			
			this.teacher.getBoard().toggleButton(FancyBoardPanel.TIP_WINDOW);
		}

	}

	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		boolean isOn = this.teacher.getBoard().isActivateBtn(
				FancyBoardPanel.ON_BTN);

		if (isOn) {
			if (x >= 58 && x <= 80 & y >= 172 && y <= 200) {
				// mic
                            if(!this.teacher.isFinished()){
				this.teacher.getBoard().toggleButton(FancyBoardPanel.MIC_BTN);
				this.teacher.getBoard().setCursor(this.cursorMao);
                            }
			} else if (x >= 100 && x <= 128 & y >= 172 && y <= 200) {
				// play
                            if(!this.teacher.isFinished() && !teacher.getBoard().isActivateBtn(FancyBoardPanel.MIC_BTN)){
									this.teacher.getBoard().toggleButton(FancyBoardPanel.PLAY_BTN);
									this.teacher.getBoard().setCursor(this.cursorMao);
                            }
			} /*else if (x >= 161 && x <= 191 & y >= 247 && y <= 275) {
				// stop
				this.teacher.getBoard().toggleButton(FancyBoardPanel.STOP_BTN);
				this.teacher.getBoard().setCursor(this.cursorMao);
			}*/ else if (x >= 150 && x <= 172 & y >= 172 && y <= 200) {
				// skip
                            if(!this.teacher.isFinished()){
								this.teacher.getBoard().toggleButton(FancyBoardPanel.SKIP_BTN);
								this.teacher.getBoard().setCursor(this.cursorMao);
                            }
			} else if ((x >= 387 && x <= 405 & y >= 78 && y <= 95)
					|| (x >= 418 && x <= 436 & y >= 78 && y <= 95)||
					(x >= 436 && x <= 462 & y >= 17 && y <= 42)) {
				//on off
				 
				this.teacher.getBoard().setCursor(this.cursorMao);
			} else {
				this.teacher.getBoard().toggleButton(FancyBoardPanel.RESET_BTN);
				this.teacher.getBoard().setCursor(cursorNormal);
			}

		}// on?
		else {
			if (
			// on - off
					(x >= 387 && x <= 405 & y >= 78 && y <= 95)
				 || (x >= 418 && x <= 436 & y >= 78 && y <= 95)) {
				this.teacher.getBoard().setCursor(this.cursorMao);
			} else {
				this.teacher.getBoard().setCursor(cursorNormal);
			}
		}
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

	}

}
