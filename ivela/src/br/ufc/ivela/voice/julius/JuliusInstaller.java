package br.ufc.ivela.voice.julius;

import java.io.IOException;

import br.ufc.ivela.voice.gui.IBoard;
import br.ufc.ivela.voice.util.FileDownload;
import br.ufc.ivela.voice.util.FileUtil;

/**
 * 
 * @author jefferson
 */
public class JuliusInstaller {
	
	public static IBoard panel;
    /**
     * Generic installation of julius on client side. It depends on its operational system.
     */
    public static void install() {

        if (System.getProperty("os.name").equalsIgnoreCase("linux")) {
            installLinux();
        } else {
            installWindows();
        }
        
        
    }

    /**
     * Intall julius on Linux
     */
    public static void installLinux() {

        //verify directory and creates if it doesn'exists.
        
        
        boolean exists = FileUtil.createDir();
        //there is already an early installation of julius. Check if it is up to date
        if (exists) {
            
            boolean needReInstall = JuliusVersionControl.needReinstall(JuliusConstants.PATH_SANDBOX_LINUX);
            if(!needReInstall)
                return;
            else{
               //.julius was erased. Create folder again.
               FileUtil.createDir(); 
            }
        
        }
        
        //download zip file
        String localZipFile = JuliusConstants.PATH_SANDBOX_LINUX + "julius.zip";
       
        FileDownload.download(JuliusConstants.URL_LINUX, localZipFile);

        //install (unzip)
        
        try {
            FileUtil.unzip(localZipFile, JuliusConstants.PATH_SANDBOX_LINUX);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //give permission
        
        FileUtil.givePermission();

        //erase
        FileUtil.rmZip();
    }

    /**
     * Install julius on windows
     */
    public static void installWindows() {

        //verify directory and creates if it doesn'exists.
        boolean exists = FileUtil.createDir();
       
         //there is already an early installation of julius. Check if it is up to date
        if (exists) {
            
            boolean needReInstall = JuliusVersionControl.needReinstall(JuliusConstants.PATH_SANDBOX_WINDOWS);
            if(!needReInstall)
                return;
            else{
               //.julius was erased. Create folder again.
               FileUtil.createDir(); 
               
            }
        
        }
        
        String localZipFile = JuliusConstants.PATH_SANDBOX_WINDOWS + "julius.zip";
        FileDownload.download(JuliusConstants.URL_WINDOWS, localZipFile);

        //install (unzip)
        try {
            FileUtil.unzip(localZipFile, JuliusConstants.PATH_SANDBOX_WINDOWS);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
        //hide julius folder
        FileUtil.windowsHideFolder();
        
        //erase trash (zip Installer)
        FileUtil.rmZip();
    }
    
    
}
