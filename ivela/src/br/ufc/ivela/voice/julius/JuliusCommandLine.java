package br.ufc.ivela.voice.julius;

import br.ufc.ivela.voice.util.DebugPrinter;
import br.ufc.ivela.voice.util.StreamGobbler;

/**
 * This class initializes julius in client machine. It depends on client's Operational System.
 * @author jefferson
 */
public class JuliusCommandLine {

    private IJuliusExec juliusExec = null;
    // private boolean start = false;
    public JuliusCommandLine() {
        this.init();
    }

    private void init() {
        if (System.getProperty("os.name").equalsIgnoreCase("linux")) {
            this.juliusExec = new LinuxExec();
        } else {
            this.juliusExec = new WindowsExec();
        }

    }

    public StreamGobbler runJulius(IJuliusParent parent) {


        DebugPrinter.print("Starting julius.", DebugPrinter.DBG_WARNING_MESG);
         
        this.juliusExec.setParent(parent);
        this.juliusExec.runJulius();
       
        return this.juliusExec.getOutpuGobbler();

    }

    public void killJulius() {

    	 
        if (isJuliusAlive()) {
            DebugPrinter.print("Killing julius.", DebugPrinter.DBG_WARNING_MESG);
             
            this.juliusExec.killJulius();
        }
       
    }

    public boolean isJuliusAlive() {
        return this.juliusExec.isJuliusAlive();
    }
    
    public static void main(String[] args) {
    	JuliusCommandLine jc = new JuliusCommandLine();
		System.out.println("Teste: " + jc.isJuliusAlive());
	}
}
