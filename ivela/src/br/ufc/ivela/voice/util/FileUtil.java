package br.ufc.ivela.voice.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import br.ufc.ivela.voice.julius.JuliusConstants;
import java.io.OutputStream;
 

/**
 * This class adds file manipulation for julius instalation in client machine 
 * @author jefferson
 */
public class FileUtil {

    static String PATH;
    public static final String curDir =System.getProperty("user.dir")+File.separatorChar;

    static {
        if (System.getProperty("os.name").equalsIgnoreCase("linux")) {
            PATH = JuliusConstants.PATH_SANDBOX_LINUX;
        } else {
            PATH = JuliusConstants.PATH_SANDBOX_WINDOWS;
        }
    }
    

    public static boolean createDir() {
        boolean exists = false;
        File file = new File(PATH);
        if (!file.exists()) {
        	
            //file.setReadable(true);
            //file.setWritable(true);
            
        	file.mkdir();
            
        }else
        	exists = true;
        
        return exists;
    }

    public static void rmZip() {

        File file = new File(PATH + "julius.zip");
         
        if (file.exists()) {
        	file.delete();
        }
    }

     
    public static void givePermission() {

        if (System.getProperty("os.name").equalsIgnoreCase("linux")) {
            Runtime runtime = Runtime.getRuntime();
            String permString = "chmod +x " + JuliusConstants.PATH_SANDBOX_LINUX + "julius" + System.getProperty("file.separator") + "julian";
            try {
                Process p = runtime.exec(permString);
                p.waitFor();
            } catch (IOException e) {

                e.printStackTrace();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        } else {
        }
    }

    public static void windowsHideFolder(){
        Runtime rt = Runtime.getRuntime();
        StringBuffer sb = new StringBuffer(JuliusConstants.PATH_SANDBOX_WINDOWS);
        String myPath = sb.deleteCharAt(sb.length()-1).toString();
         
         

        try {

            Process child = rt.exec("attrib +H "+myPath);
            child.waitFor();
             
            System.out.println("Exit code is: " + child.exitValue());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    /**
     * Unzip a zip file in a directory
     * @param zipFile the zip file
     * @param outDir the out directory
     * @throws java.io.IOException
     */
    public static final void unzip(String zipFile, String outDir) throws IOException {

    	 
        File zip = new File(zipFile);
        File extractTo = new File(outDir);

        ZipFile archive = new ZipFile(zip);
        
        Enumeration e = archive.entries();
        while (e.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            File file = new File(extractTo, entry.getName());
            if (entry.isDirectory() && !file.exists()) {
                file.mkdirs();
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                InputStream in = archive.getInputStream(entry);
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(file));

                byte[] buffer = new byte[8192];
                int read;

                while (-1 != (read = in.read(buffer))) {
                    out.write(buffer, 0, read);
                }
                
                in.close();
                out.flush();
                out.close();
                 
            }
        }
        archive.close();
    }
    
    /*
     * Getting properties files
     */
    public static Properties getPropertiesFile(){
    	Properties properties = new Properties();
    
    	/*URL url = FileUtil.class.getResource("/br/ufc/ivela/voice/util/config.properties");
        
        try {
            properties.load(new FileInputStream(url.getFile()));
        } catch (IOException e) {
        	e.printStackTrace();
        }*/
        properties.setProperty("version", JuliusConstants.JULIUS_VERSION);
        return properties;

    }
    
    /*
     * Getting properties files
     */
    public static Properties getInstalledPropertiesFile(String path){
    	Properties properties = null;
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream(path+"config.properties");
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
                DebugPrinter.print("Properties not found", DebugPrinter.DBG_ERROR_MESG);
                properties = null;
        }
         
        return properties;
    }
     
    public static boolean rmDirRecursive(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = rmDirRecursive(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
    
        // The directory is now empty so delete it
        //return true;
        return dir.delete();
    }

   
   
   public static void saveFile(File file, String path){
       InputStream data = null;
        try {
            java.io.File f = new java.io.File(path);
            data = new FileInputStream(file);
            OutputStream out = new FileOutputStream(f);
            byte[] buf = new byte[1024];
            int len;
            while ((len = data.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            data.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
   }
   
    
   
}
    

