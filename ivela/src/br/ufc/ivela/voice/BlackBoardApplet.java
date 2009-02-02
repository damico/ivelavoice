package br.ufc.ivela.voice;

import java.awt.BorderLayout;

import javax.swing.JApplet;

import br.ufc.ivela.voice.gui.FancyBoardPanel;
import br.ufc.ivela.voice.gui.IBoard;
import br.ufc.ivela.voice.julius.JuliusConstants;
import br.ufc.ivela.voice.julius.JuliusInstaller;
import br.ufc.ivela.voice.teacher.Teacher;
import br.ufc.ivela.voice.util.Message;



public class BlackBoardApplet extends JApplet {

    
    /**
     * 
     */
    
    private static final long serialVersionUID = 1L;
   
    
    private Teacher t = null;
    private int boardVersion = 0 ; // 0 - Normal Applet, with JEditorPane
                                   // 1 - Applet with JPanel, drawing pictures

    @Override
    public void init() {
        //this.setSize(new Dimension(600, 400));
        this.setLayout(new BorderLayout());
        
        t = new Teacher(this);
         //reading input
        this.readParameters();
        //final IBoard mainPanel = (boardVersion==0)? new HTMLBoardPanel(t): new BlackBoardPanel(t);
         
        
        
        //this.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
        //System.out.println(boardVersion);
         
        final IBoard mainPanel = (boardVersion==0)?new FancyBoardPanel(t,false,this):new FancyBoardPanel(t,true,this);
        this.add(mainPanel, BorderLayout.CENTER);
        this.setVisible(true);
        
        
        
         //istalling..client must wait download (just first time).
        //mainPanel.setMessage(new Message(Message.INSTALL));
        Thread installThread = new Thread(){
        	@Override
        	public void run() {
        		JuliusInstaller.panel = mainPanel;
        		JuliusInstaller.install();
                mainPanel.setInstalled(true);
                mainPanel.setMessage(new Message(Message.START));
                //kill julius, if it is already started.
                t.clean();
               
                
                //start application (teacher logic)
                //t.start();
        	}
        };
        installThread.start();
        
        
         
    }

    @Override
    protected void finalize() throws Throwable {
        t.clean();
    }
    
    /**
     * This reads parameters from HTML and assigns them to the applet.
     * The <b>exe</b> parameters is related to a set of phrases.
     * The <b>par</b> parameters is related to its respectivly <b>exe</b>, of
     * a particular grammar file. 
     */
    private void readParameters(){
        
        //String related to exercise
        String exe = "";
        //String related to grammar conf file
        String conf = "";
        //String related to exe title
        String question = "";
        //String related to audio ogg;
        String audio = "";
        //String related 
        String chances = "";
        
         
        String version = "";
        String audioHost = "";
        String installerHost = "";
        String juliusVersion = "";
        String bgColor = "";
        String exeId = "";
         
        
        exe = getParameter("exe");
        conf = getParameter("conf");
        question = getParameter("question");
        audio = getParameter("audio");
        chances = getParameter("chances");
        version = getParameter("boardVersion");
        audioHost = getParameter("audioHost");
        installerHost = getParameter("installerHost");
        juliusVersion = getParameter("juliusVersion");
        bgColor = getParameter("bgColor");
        exeId = getParameter("exeId");
        
        
        if(version==null || version.equals("") || version.equals("0")){
        	 this.boardVersion = 0;
        }else{
        	this.boardVersion = 1;
        }
        
        if(audioHost!=null && !audioHost.equalsIgnoreCase("")){
        	JuliusConstants.AUDIO_URL = audioHost;
        }
        
        if(installerHost!=null && !installerHost.equalsIgnoreCase("")){
        	JuliusConstants.JULIUS_FILE_HOST = installerHost;
        	
        }
        
        if(juliusVersion!=null && !juliusVersion.equalsIgnoreCase("")){
        	JuliusConstants.JULIUS_VERSION = juliusVersion;
        	
        }
        
        if(bgColor!=null && !bgColor.equalsIgnoreCase("")){
        	JuliusConstants.BG_COLOR = bgColor;
        } 
        
        if(exeId!=null && !exeId.equalsIgnoreCase("")){
        	JuliusConstants.EXE_ID = exeId;
        }
        
        t.createExercise(exe, conf, question, audio, chances);
    }
    
}


