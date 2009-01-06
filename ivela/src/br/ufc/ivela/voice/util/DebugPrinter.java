/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.ivela.voice.util;


/**
 *
 * @author jefferson
 */
public class DebugPrinter {

    public static final String DBG_ERROR_MESG = "ERROR";
    public static final String DBG_WARNING_MESG = "WARNING";
    public static final String DBG_DBG_MESG = "DEBUG";

    public static void print(String msg, String type) {

        
        
    }
 
    public static void main(String[] args) {
    	int total = 5;
        int f = 2;
        double res = f/(double)total;
        int resi = (int)(res*10);
        System.out.println(resi);
	}
}
