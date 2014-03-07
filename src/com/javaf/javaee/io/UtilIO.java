package com.javaf.javaee.io;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.javaf.Bagman;
import com.javaf.javaee.Context;
import com.javaf.javaee.faces.context.ExternalContextProxy;
import com.javaf.javaee.faces.context.FacesContextProxy;
import com.javaf.javaee.servlet.ServletContextProxy;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilIO extends com.javaf.javase.io.UtilIO {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilIO();
		}
		
	};
	
	private final Context context = Context.getInstance();
	private ServletContextProxy servlet;
	private UtilIO(){
		
	}
	
	public static synchronized UtilIO getInstance(){
		return Bagman.utilOf(UtilIO.class, FACTORY);
	}
	
	private ServletContextProxy getServlet(){
		if(null== servlet){
			//FACES
			final FacesContextProxy _faces = FacesContextProxy.getCurrentInstance();
			if(null!= _faces){
				final ExternalContextProxy _external = _faces.getExternalContext();
				if(null!= _external){
					servlet = _external.getContext();
				}
			}
			
			//SERVLET
			if(context.contains(Context.SERVLET_CONTEXT)){
				
				servlet = new ServletContextProxy(context.get(Context.SERVLET_CONTEXT));
				
			} else {
				logging.info(Context.SERVLET_CONTEXT + " DO NOT REGISTERED.");
			}
		}
		
		return servlet;
	}
	

	@Override
	public URL absoluteOf(final String resource){
		URL _result = null;
		
		final ServletContextProxy _servlet = getServlet();
		if(null!= _servlet){
			final String _real = _servlet.getRealPath(resource);
			if(null!= _real){
				
				try{
					_result = new File( _servlet.getRealPath(resource) ).toURI().toURL();
					
				}catch(MalformedURLException _e){
					logging.error(_e.getMessage(), _e);
				}
				
			}
		}
		
		return _result;
	}
}
