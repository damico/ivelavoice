package br.ufc.ivela.voice.util;

import java.io.*;
import java.net.*;

import br.ufc.ivela.voice.julius.JuliusInstaller;

/*
 * Command line program to download data from URLs and save
 * it to local files. Run like this:
 * java FileDownload http://schmidt.devlib.org/java/file-download.html
 * @author Marco Schmidt
 */
public class FileDownload {

    public static void download(String address, String localFileName) {
      
    	int total;
    	//int percent;
        OutputStream out = null;
        URLConnection conn = null;
        InputStream in = null;
        try {
            URL url = new URL(address);
            out = new BufferedOutputStream(
                    new FileOutputStream(localFileName));
            conn = url.openConnection();
            in = conn.getInputStream();
            total = conn.getContentLength();
            byte[] buffer = new byte[1024];
            int numRead;
            long numWritten = 0;
            while ((numRead = in.read(buffer)) != -1) {
               
                Thread.sleep(1);
            	out.write(buffer, 0, numRead);
                numWritten += numRead;
                 
                if(JuliusInstaller.panel!=null)
                	JuliusInstaller.panel.setMinMaxDownloaded((int)numWritten,total);
                
            }
             
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
            	
                if (in != null) {
                
                    in.close();
                }
                if (out != null) {
                    
                    out.flush();
                    out.close();
                    
                }
            } catch (IOException ioe) {
                DebugPrinter.print("Could not download file in "+address+".", DebugPrinter.DBG_ERROR_MESG);
                ioe.printStackTrace();
            }
        }
    }
    
     
}
