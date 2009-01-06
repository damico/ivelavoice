/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.ivela.voice.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author jefferson
 */
public class DrawPanel extends JPanel{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Icon okImg = new ImageIcon(this.getClass().getResource("/br/ufc/ivela/voice/images/ok.jpg"));
    Icon nokImg = new ImageIcon(this.getClass().getResource("/br/ufc/ivela/voice/images/error.jpg"));
    Icon defaultImg = new ImageIcon(this.getClass().getResource("/br/ufc/ivela/voice/images/normal.jpg"));
    Icon current = defaultImg;
    public DrawPanel() {
        this.setPreferredSize(new Dimension(85,100));
        this.setBackground(Color.white);
    }
    
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        current.paintIcon(this, g, 0, 0);
    }

    public void ok(){
        this.current = this.okImg;
        this.repaint();
    }
    
    public void nok(){
        this.current = this.nokImg;
        this.repaint();
        
    }
    
    public void defaultIcon(){
        this.current = this.defaultImg;
        this.repaint();
    }
}
