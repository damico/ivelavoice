/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.ivela.voice.sound;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import br.ufc.ivela.voice.util.FileUtil;

/**
 *
 * @author jefferson
 */
public class TestGui extends JFrame {

    JFileChooser fileChooser = new JFileChooser();
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestGui() {
            
       
       fileChooser.setDialogTitle("Escolha o arquivo");

      // final PlayerFactory playerFactory = new PlayerFactory();
       //Icon sndImg = new ImageIcon(this.getClass().getResource("/br/ufc/ivela/voice/gui/images/sound.png"));
       
       JButton btnPlay = new JButton("arquivo" );
       btnPlay.setFocusPainted(false);
//      / btnPlay.setIcon(sndImg);
       btnPlay.setBackground(Color.white);
       //btnPlay.setForeground(Color.white);
       btnPlay.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //Player player = playerFactory.getPlayer("http://200.17.41.203/audio/finale.ogg");
                //player.start();
                File fileToSave = retornaArquivo();
                if(fileToSave!=null){
                    FileUtil.saveFile(fileToSave, "/home/jefferson/teste.txt");
                }
                
            }
        });

        

        this.setLayout(new FlowLayout());
        this.add(btnPlay);
         

        
        
        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                dispose();
                
                System.exit(0);
            }
        });

        this.pack();
        this.setVisible(true);
        
    }

    public File retornaArquivo()
        {
            int result = this.fileChooser.showOpenDialog(this);
            if(result == JFileChooser.CANCEL_OPTION)
                return null;
            return fileChooser.getSelectedFile();
        }
        
    public static void main(String args[]) {
        new TestGui();
    }
    
    
}
