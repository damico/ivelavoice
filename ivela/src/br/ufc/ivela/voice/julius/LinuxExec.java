package br.ufc.ivela.voice.julius;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import br.ufc.ivela.voice.util.StreamGobbler;

/**
 * Linux facade for Julius and linux command line. This class is very, VERY 
 * close to the Operational System.
 * @author jefferson
 */
public class LinuxExec implements IJuliusExec {

    private IJuliusParent parent;
    private StreamGobbler outGobbler;
    
    public void runJulius() {

        Runtime runtime = Runtime.getRuntime();
        Process proc;
         
        String juliusBin = JuliusConstants.PATH_SANDBOX_LINUX + "julius" +
                System.getProperty("file.separator") + "julian";

        String juliusConf = JuliusConstants.PATH_SANDBOX_LINUX + "julius" +
                System.getProperty("file.separator") + parent.getCurrentExercise().getConfJulianFile();

        try {
            proc = runtime.exec(juliusBin + " -input mic -C " + juliusConf);


            //any error message?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR", null);

            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT", this.parent);
            this.outGobbler = outputGobbler;

            // kick them off
            errorGobbler.start();
            outputGobbler.start();



        //System.exit(0);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }

    public void killJulius() {
        Runtime runtime = Runtime.getRuntime();
        Process proc;

        try {
            proc = runtime.exec("pidof julian");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            String pid = "";
            while ((pid = stdInput.readLine()) != null) {


                break;

            }

            proc = runtime.exec("kill " + pid);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isJuliusAlive() {

        Runtime runtime = Runtime.getRuntime();
        Process proc;
        try {
            proc = runtime.exec("pidof julian");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            //String pid = "";
            while ((stdInput.readLine()) != null) {
            	
                return true;


            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    public void setParent(IJuliusParent parent) {
        this.parent = parent;

    }

     

   

    public StreamGobbler getOutpuGobbler() {
        return this.outGobbler;
    }
}
