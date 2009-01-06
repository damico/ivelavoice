package br.ufc.ivela.voice.julius;

import java.io.IOException;
import br.ufc.ivela.voice.util.StreamGobbler;

/**
 * Windows facade for Julius and windows command line. This class is very, VERY 
 * close to the Operational System.
 * @author jefferson
 */
public class WindowsExec implements IJuliusExec {

    private IJuliusParent parent;
    private StreamGobbler outGobbler;
    
    public void runJulius() {

        
        Runtime rt = Runtime.getRuntime();
        Process proc;
        try {

            String juliusCmd = JuliusConstants.PATH_SANDBOX_WINDOWS + "julius" +
                    System.getProperty("file.separator") +
                    "julian -input mic -C " +
                    JuliusConstants.PATH_SANDBOX_WINDOWS + "julius" +
                    System.getProperty("file.separator") +
                    parent.getCurrentExercise().getConfJulianFile();
            String[] cmd = new String[3];


            cmd[0] = "cmd.exe";
            cmd[1] = "/C";
            cmd[2] = juliusCmd;

            proc = rt.exec(cmd);


            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR", null);

            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT", parent);
            this.outGobbler = outputGobbler;
            
            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            // any error???
            //int exitVal = proc.waitFor();
            //System.out.println("ExitValue: " + exitVal);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void killJulius() {
        Runtime rt = Runtime.getRuntime();


        try {

            Process child = rt.exec("taskkill -F /IM julian.exe");
            child.waitFor();
             
            System.out.println("Exit code is: " + child.exitValue());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public void setParent(IJuliusParent parent) {
        this.parent = parent;
    }

    public void run() {
        this.runJulius();

    }

    public boolean isJuliusAlive() {
    	Runtime rt = Runtime.getRuntime();


        try {

            Process child = rt.exec("tasklist  /FI \"imagename eq julian.exe\"");
            child.waitFor();
            if(child.getInputStream().available()>0)
            	return true;
            else
            	return false;
             

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        return false;
    }

    public StreamGobbler getOutpuGobbler() {
        return this.outGobbler;
    }
    
     
}
