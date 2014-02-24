package com.javaf.javase.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.Proxy.Type;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class ProxyExample {

	public static void main(String[] args) {
		
		final Proxy _proxy = new Proxy(Type.HTTP, new InetSocketAddress("10.10.80.87", 8080));
	
		Authenticator.setDefault(new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication("fabiojm", "BLK8254".toCharArray());
			}
		});
		
		try{
			System.out.println("Abrindo conexão . . .");
			final URL _url = new URL("http://stackoverflow.com");
			final URLConnection _connection = _url.openConnection(_proxy);
			
			final BufferedReader _reader = new BufferedReader(new InputStreamReader(_connection.getInputStream()));
			System.out.println(_reader.readLine());
			
		}catch(MalformedURLException _e){
			_e.printStackTrace();
			
		}catch(IOException _e){
			_e.printStackTrace();
			
		}
	}

}
