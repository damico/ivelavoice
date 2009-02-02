package br.ufc.ivela.voice.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JApplet;

import netscape.javascript.JSObject;

public class ServletComm {

	 

	public static void sendDataToUserVoice(String params) {

		try{
		String location = "http://200.17.41.212:8080/ivela-web/VoiceStatServlet?"
				+ params;
		//System.out.println("location: " + location);
		URL testServlet = new URL(location);
		URLConnection servletConnection = testServlet.openConnection();
		servletConnection.getInputStream();
		}catch(MalformedURLException mue){
			mue.printStackTrace();
		}catch(IOException io){
			io.printStackTrace();
		}
		// inputStreamFromServlet = servletConnection.getInputStream();
	}

	public static void sendDataToNpdUser(String phrase, String tries) {

		String params = "phrase="+phrase+"&tries="+tries;
		try{
		String location = "http://200.17.41.215:8080/ivela-web/NpdServlet?"
				+ params;
		//System.out.println(location);
		//System.out.println("location: " + location);
		URL testServlet = new URL(location);
		URLConnection servletConnection = testServlet.openConnection();
		servletConnection.getInputStream();
		}catch(MalformedURLException mue){
			mue.printStackTrace();
		}catch(IOException io){
			io.printStackTrace();
		}
		// inputStreamFromServlet = servletConnection.getInputStream();
	}
	 

	public static void callJavasScript(JApplet parent){
		System.out.println("Javascript");
		JSObject win = (JSObject) JSObject.getWindow(parent);
	       win.eval("callScript()");

	}
	
	public static void callJavasScriptComputeExe(JApplet parent, String param){
		System.out.println("Javascript");
		JSObject win = (JSObject) JSObject.getWindow(parent);
	       win.eval("computeExe(\" "+param +"\")");

	}
	
	public static void callJavasScriptComputeUrl(JApplet parent, String param){
		System.out.println("Javascript");
		JSObject win = (JSObject) JSObject.getWindow(parent);
	       win.eval("computeUrl(\" "+param +"\")");

	}
	
	public static String getHostName() {
		String res = "";
		try {
			InetAddress thisIp = InetAddress.getLocalHost();
			res = thisIp.getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public static void main(String[] args) {
		 sendDataToNpdUser("1", "2");
	}
}
