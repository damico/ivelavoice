package br.ufc.ivela.voice.julius;

/**
 * Some constants 
 * @author jefferson
 */
public final class JuliusConstants {

	
    //public static String JULIUS_FILE_HOST;
    public static final String PATH_SANDBOX_LINUX = System.getProperty("user.home") +
            System.getProperty("file.separator") + ".julius" +
            System.getProperty("file.separator");
    public static final String PATH_SANDBOX_WINDOWS = "C:" + System.getProperty("file.separator") + ".julius" +
            System.getProperty("file.separator");
    
    public static final String URL_WINDOWS = "windows/julius.zip";
    public static final String URL_LINUX = "linux/julius.zip";
    public static final String BUILD_VERSION = "2.0.3 JROD";
    
    //public static final String WORD_GRAMMAR = "julian_word.jconf";
    //public static final String PRESENT_CONT = "julian_present_cont.jconf";
    //public static final String SIMPLE_PRESENT = "julian_simple_present.jconf";
    
    public static String AUDIO_URL;
    //public static String SERVLET_HOST = "http://200.17.41.212/";
    public static String JULIUS_VERSION = "1.1.9";
    public static String BG_COLOR = "FFFFFF";
    
    public static String SND_MSG_CON = "connecting";
    public static String SND_MSG_PLN = "playing";
    public static String SND_MSG_STP = "stopped";
    public static String SND_MSG_ERR = "!error!";
    
    public static String EXE_ID = "-1";
	public static String JULIUS_FILE_HOST;
    
}
