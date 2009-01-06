/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.ivela.voice.julius;

import br.ufc.ivela.voice.util.FileUtil;
import java.io.File;
import java.util.Properties;

/**
 * controls version of julius/julian
 * @author jefferson
 */
public class JuliusVersionControl {
    
    /**
     * 
     * @param path
     * @return false if I need to reinstall a new version
     */
    public static boolean needReinstall(String path){
        Properties pRemote = FileUtil.getPropertiesFile();
        Properties pLocal = FileUtil.getInstalledPropertiesFile(path);
        
        
        //client version is so old that doesn't have a properties file. Reinstall
        if(pLocal==null){
            System.out.println("properties not found. Delete recursive");
            FileUtil.rmDirRecursive(new File(path));
            return true;
        }
            
        
        //compares remote and local version
        if(pRemote!=null && pLocal!=null){
            String versionRemote = pRemote.getProperty("version");
            String versionLocal = pLocal.getProperty("version");
            if(versionLocal.equalsIgnoreCase(versionRemote)){
                return false;
            }else{
               FileUtil.rmDirRecursive(new File(path));
               return true;
            }
        }
        return false;
    }
    
    

}
