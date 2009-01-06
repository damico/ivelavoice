package br.ufc.ivela.voice.julius;

import br.ufc.ivela.voice.util.StreamGobbler;

/**
 * IJulius intefarce of execution. Its implementation depends on target operational system. 
 * @author jefferson
 */
public interface IJuliusExec {

    /**
     * Set the parent object.
     * @param va
     */
    public void setParent(IJuliusParent parent);

    /**
     * Run julius on client machine. 
     */
    public void runJulius();

    /**
     * Kill julius on client machine
     */
    public void killJulius();

    /**
     * 
     */
    public boolean isJuliusAlive();
    
    /**
     * 
     * @return
     */
    public StreamGobbler getOutpuGobbler();
}
