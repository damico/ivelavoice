/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.ivela.voice.gui;

import br.ufc.ivela.voice.util.Message;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author jefferson
 */
public class MessagePanel extends JPanel{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
    private JLabel messageLabel, percentLabel; 

    public MessagePanel() {
    
        this.setLayout(null);
        //Fancy Labe
        this.messageLabel = new JLabel(Message.INSTALL_MSG);
        this.messageLabel.setLocation(8, 8);
        this.messageLabel.setSize(420,15);
        Font arial = new Font("arial", Font.BOLD, 14);
        this.messageLabel.setFont(arial);
        
        //percent label
        this.percentLabel = new JLabel("0%");
        this.percentLabel.setSize(45,18);
        this.percentLabel.setLocation(8, 38);
        
        //Progress
        this.progressBar = new JProgressBar();
        this.progressBar.setMinimum(0);
        this.progressBar.setSize(365, 10);
        this.progressBar.setLocation(8,30);
        this.progressBar.setBorderPainted(false);
         
        //ivela style
        //this.progressBar.setForeground(new Color(200,140,79));
        //this.progressBar.setBackground(new Color(255, 231, 156));
        
        //new fancy style
        this.progressBar.setForeground(new Color(62,88,162));
        this.progressBar.setBackground(Color.white);
        
        this.add(this.progressBar);
        this.add(this.messageLabel);
        this.add(this.percentLabel);
        this.setPreferredSize(new Dimension(400,65));
        this.setBackground(Color.WHITE);
    }
    
    public void setValue(int v){
        this.progressBar.setValue(v);
        int percent = (int)((v*100)/this.progressBar.getMaximum());
        this.percentLabel.setText(percent+"%");
    }
    
    public void setMaximum(int n){
        this.progressBar.setMaximum(n);
    }
    
    public int getMaximum(){
        return this.progressBar.getMaximum();
    }
    public boolean isComplete(){
        return (this.progressBar.getPercentComplete()==1)?true:false;
    }
    
    public void setVisibleBar(boolean v){
        this.progressBar.setVisible(v);
    }
    
    public void setLabel(String msg){
        this.messageLabel.setText(msg);
    }
    
    public void hideProgressBar(){
        this.progressBar.setVisible(false);
        this.percentLabel.setVisible(false);
    }

	public JProgressBar getProgressBar() {
		return progressBar;
	}
    
    
}
