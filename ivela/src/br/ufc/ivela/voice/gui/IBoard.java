/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.ivela.voice.gui;

import br.ufc.ivela.voice.teacher.Teacher;
import br.ufc.ivela.voice.util.Message;
import javax.swing.JPanel;

/**
 *
 * @author jefferson
 */
public abstract class IBoard extends JPanel {

    //set the message that will be diplayed on board
    public abstract void setMessage(Message msg);
    
    //tell the board thar the exercise began
    public abstract void setStart(boolean start);
    
    //check
    public abstract boolean isStart();
    
    //tell board that julius is started
    public abstract void setInstalled(boolean value);
    
    //set min max downloaded
    public abstract void setMinMaxDownloaded(int min, int max);
    
    public abstract void toogleCapture();
    
    public abstract void updateProgress(int index);
    
    public abstract void toggleButton(int type);
    
    public abstract void activateBtn(int type);
    
    public abstract boolean isActivateBtn(int type);
    
    public abstract void initiateGlow();
    
    public abstract boolean isBoardRequireJavaScript();
    
    public abstract void setTeacher(Teacher teacher);
    
    public abstract void setSoundMsg(String newMsg);
}
