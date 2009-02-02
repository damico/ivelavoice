/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.ivela.voice.sound;

import javax.swing.JLabel;

/**
 *
 * @author jefferson
 */
public class StandAlonePlayerFactory {

    private StandAlonePlayer player;
     

     
    
    public StandAlonePlayer getPlayer(String URL, JLabel stat) {
         
    	if (player != null) {
            player._stopPlay();
            
        }
        this.player = new StandAlonePlayer(URL, stat);
        return this.player;
    }
}
