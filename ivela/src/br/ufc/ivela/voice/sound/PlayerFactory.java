package br.ufc.ivela.voice.sound;

public class PlayerFactory {

    private Player player;

    public Player getPlayer(String URL) {
        if (player != null) {
            player._stopPlay();
            
        }
        this.player = new Player(URL,null);
        return this.player;
    }
}
