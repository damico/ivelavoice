/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.ivela.voice.sound;

/**
 *
 * @author jefferson
 */
public class StandAlonePlayerFactory {

    private StandAlonePlayer player;

    public StandAlonePlayer getPlayer(String URL) {
        if (player != null) {
            player._stopPlay();
            
        }
        this.player = new StandAlonePlayer(URL);
        return this.player;
    }
}
