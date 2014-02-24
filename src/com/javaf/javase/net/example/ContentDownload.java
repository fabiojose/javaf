package com.javaf.javase.net.example;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.Proxy.Type;

import org.apache.log4j.Logger;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class ContentDownload {
	
	static private final Logger LOGGER = Logger.getLogger(ContentDownload.class); 

	public static void main(String[] args) {
		final String _basename = "nfe_";
		final String _store    = "D:/CAPTCHA/exemplos/nfe/11";
		final String _surl     = "http://www.nfe.fazenda.gov.br/scripts/srf/intercepta/captcha.aspx?opt=image";
		
		//do not stop donwload
		long _count   = 1000;
		
		final Proxy _proxy = new Proxy(Type.HTTP, new InetSocketAddress("10.10.80.87", 8080));
		
		Authenticator.setDefault(new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication("fabiojm", "	BLK8254".toCharArray());
			}
		});
		
		try{
			final URL _url = new URL(_surl);
		
			for(long _counter = 0; _counter < _count || _count == -1; _counter++){
				
				final URLConnection _connection = _url.openConnection(_proxy);
				
				LOGGER.debug(_connection.getContentType());
				LOGGER.debug(_connection.getContentLength());
				
				final BufferedInputStream _input = new BufferedInputStream(_connection.getInputStream());
				final FileOutputStream _output   = new FileOutputStream(_store + "/" + _basename + _counter + ".gif");
				
				try{
					byte[] _buffer = new byte[1024];
					int _length = 0;
					while( (_length = _input.read(_buffer)) != -1){
						_output.write(_buffer, 0, _length);
					}
					
				}finally{
					_input.close();
					_output.close();
				}
				
				LOGGER.debug(_counter + " donwloaded . . .");
			}
			
			
		}catch(MalformedURLException _e){
			LOGGER.error(_e.getMessage(), _e);
			
		}catch(IOException _e){
			LOGGER.error(_e.getMessage(), _e);
		}
	}

}
