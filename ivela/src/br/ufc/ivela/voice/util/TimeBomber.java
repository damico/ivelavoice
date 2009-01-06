package br.ufc.ivela.voice.util;

import br.ufc.ivela.voice.gui.FancyBoardPanel;
import br.ufc.ivela.voice.teacher.Teacher;

public class TimeBomber extends Thread{

	public static final int TIMER = 20;
	private int timer = TimeBomber.TIMER;
	private Teacher teacher;
	private boolean active;
	
	public TimeBomber(Teacher teacher){
		this.teacher = teacher;
	}
	 
	
	public void run() {
		this.active = true;
		//restartBomb:
		while(timer!=0 && active){
			//this.teacher.getBoard().repaint();
			timer--;
			
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(timer==0){
				//this.teacher.forceKillJulius();
				//this.teacher.forceRunJulius()
				this.teacher.getBoard().setMessage(new Message(Message.SKIP));
				this.teacher.dummySleep(2000);
				if(teacher.getBoard().isActivateBtn(FancyBoardPanel.MIC_BTN)){
					this.teacher.getBoard().activateBtn(FancyBoardPanel.MIC_BTN);
	                this.teacher.getBoard().toogleCapture();
	                this.teacher.capture();
				}
				this.teacher.skipText();
				//this.timer =  TimeBomber.TIMER;
				//continue restartBomb;
				
			}else if (timer == 18){
				this.teacher.getBoard().setMessage(new Message(Message.NOW_TALK));
			}
			
		}
		
		this.teacher.setBomberActive(false);
	}


	public int getTimer() {
		return timer;
	}


	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public void turnOff(){
		this.active = false;
	}
	
}
