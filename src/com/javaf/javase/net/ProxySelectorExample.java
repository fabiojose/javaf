package com.javaf.javase.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class ProxySelectorExample {

	public static void main(String[] args) {

		System.setProperty("java.net.useSystemProxies", "true");
		
		try{
			final List<Proxy> _proxies = ProxySelector.getDefault().select(new URI("http://registro.br"));
			
			Proxy _selected = null;
			if(!_proxies.isEmpty()){
				for(Proxy _proxy : _proxies){
					System.out.println("type: " + _proxy.type());
					final SocketAddress _address = _proxy.address();
					if(null!= _address){
						System.out.println("hostname: " + _address.toString());
						_selected = _proxy;
						break;
					} else {
						System.out.println("Não há configuração de proxy!");
					}
				}
				
				if(null!= _selected){
					System.out.println("Abrindo conexão . . .");
					
					Authenticator.setDefault(new Authenticator(){
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("fabiojm", "BLK8254".toCharArray());
						}
					});
					
					final URL _url = new URL("http://www.java2s.com");
					final URLConnection _connection = _url.openConnection(_selected);
					_connection.connect();
					final BufferedInputStream _i = new BufferedInputStream(_connection.getInputStream());
					InputStreamReader _reader = new InputStreamReader(_i);
					BufferedReader _r = new BufferedReader(_reader);
					System.out.println(_r.readLine());
				}
			} else {
				System.out.println("Nenhuma configuração de proxy encontrada!");
			}
		}catch(URISyntaxException _e){
			_e.printStackTrace();
			
		}catch(MalformedURLException _e){
			_e.printStackTrace();
			
		}catch(IOException _e){
			_e.printStackTrace();
		}
	}

}
