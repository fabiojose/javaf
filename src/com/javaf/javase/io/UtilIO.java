package com.javaf.javase.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import com.javaf.Bagman;
import com.javaf.Utility;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.REFLECTION;
import com.javaf.Constants.REGEX;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class UtilIO implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilIO();
		}
		
	};
	
	private static final String JEE_UTIL_IO = "com.javaf.javaee.io.UtilIO";

	protected final ILogging logging        = Logging.loggerOf(getClass());
	private final UtilReflection reflection = UtilReflection.getInstance();
	protected UtilIO(){
		
	}
	
	public static synchronized UtilIO getInstance(){
		return Bagman.utilOf(UtilIO.class, FACTORY);
	}
	
	public FileFilter newFilter(final String regex){
		final FileFilter _result = new FileFilter(){

			public boolean accept(File pathname) {
				return pathname.getAbsolutePath().matches(regex);
			}
			
		};
		
		return _result;
	}
	
	public FileFilter newFilterXML(){
		final FileFilter _result = new FileFilter(){

			public boolean accept(final File pathname) {
				return pathname.getAbsolutePath().matches(REGEX.XML);
			}
			
		};
		
		return _result;
	}
	
	public void move(final File from, final File to) throws IOException {
		
		if (!from.renameTo(to)){
			throw new IOException(I18N.NAO_FOI_POSSIVEL_RENOMEAR_ARQUIVO + from.toString());
		}
	}
	
	public DataHandler asDataHandler(final File file){
		return new DataHandler( new FileDataSource(file) );
	}
	
	public boolean exists(final String path) {
		boolean _result = Boolean.FALSE;
		
		final File _file = new File(path);
		_result = _file.exists();
		
		return _result;
	}
	
	private URL local(final String resource){
		URL _result = null;
		
		final File _file = new File(resource);
		if(_file.exists()){
			try{
				_result = _file.toURI().toURL();
				
			}catch(MalformedURLException _e){
				logging.trace(_e.getMessage(), _e);
			}
		}
		
		return _result;
	}
	
	private URL jee(final String resource){
		URL _result = null;
		
		logging.debug("TRYING 'ABSOLUTE OF' THROUGH JEE . . .");
		try{
			final Class<?> _jeeu     = Class.forName(JEE_UTIL_IO);
			final Object _ijeeu      = reflection.newInstance(_jeeu);
			final Method _absoluteOf = _jeeu.getMethod(REFLECTION.METHOD_ABSOLUTE_OF, new Class<?>[]{String.class});
			
			_result = (URL)_absoluteOf.invoke(_ijeeu, new Object[]{resource});
		
		}catch(Exception _e){
			logging.debug("THERE IS NO JEE UTIL I/O.", _e);
		}
		
		return _result;
	}
	
	private URL directory(final String resource){
		URL _result = null;
		
		logging.debug("TRYING 'ABSOLUTE OF' THROUGH BYTECODE DIRECTORY . . .");
		final URL _jail = getRunningOn();
		if(null!= _jail){
			final File _fjail = new File(_jail.getPath());
			String _path = _fjail.getAbsolutePath();
			_path += File.separatorChar;
			_path += resource;
			
			logging.trace("ABSTRACT PATH >" + _path + "<");
			
			final File _file = new File(_path);
			if(_file.exists()){
				try{
					_result = _file.toURI().toURL();
					
				}catch(MalformedURLException _e){
					logging.debug(_e.getMessage(), _e);
				}
			}
		}
		
		return _result;
	}
	
	private URL jar(final String resource){
		URL _result = null;
		
		logging.debug("TRYING 'ABSOLUTE OF' THROUGH BYTECODE JAR . . .");
		logging.warn("TRYING 'ABSOLUTE OF' THROUGH BYTECODE JAR: não implementado!");
		
		return _result;
	}
	
	/**
	 * Obter o caminho onde residem os bytecodes, a partir do class loader passado como argumento.
	 * @param loader Carregador de classes onde será obtido o diretório.
	 * @return <code>null</code> caso os bytecodes residam em um jar.
	 */
	public URL getRunningOn(final ClassLoader loader){
		URL _result = null;
		 
		_result = loader.getResource(STRING.EMPTY);
		logging.debug("RUNNING ON >" + _result + "<");
		
		return _result;
	}
	
	/**
	 * Obter o caminho onde residem os bytecodes, a partir do class loader de UtilIO.
	 * @return <code>null</code> caso os bytecodes residam em um jar.
	 */
	public URL getRunningOn(){
		URL _result = null;
		
		_result = getRunningOn(getClass().getClassLoader());
		
		return _result;
	}
	
	public URL absoluteOf(final String resource){
		URL _result = null;
		
		
		_result = local(resource);
		if(null== _result){
			
			_result = jee(resource);
			if(null== _result){
				_result = directory(resource);
				
				if(null== _result){
					_result = jar(resource);
					
					if(null== _result){
						final File _resource = new File(resource);
						
						logging.debug("TRYING 'ABSOLUTE OF' USING RESOURCE PATH . . .");
						
						if(_resource.exists()){
							try{
								_result = _resource.toURI().toURL();
								
							}catch(MalformedURLException _e){
								logging.debug(_e.getMessage(), _e);
							}
						}
					}
				}
			}
		}
		
		if(null!= _result){
			
			logging.debug("THE ABSOLUTE OF >" + resource + "< is >" + _result + "<");
			
		} else {
			
			logging.info("ABSOLUTE OF >" + resource + "< NOT FOUND!");
			
		}
		
		return _result;
	}
	
	public File toFile(final URL url){
		File _result = null;
		
		try{
			_result = new File(url.toURI());
			
		}catch(URISyntaxException _e){
			logging.debug(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public File toExtension(final File source, final String extension){
		File _result = null;
		
		final String _source = source.getAbsolutePath();
		final int _lindex    = _source.lastIndexOf(STRING.DOT);
		
		if(_lindex >= INTEGER._0){
			String _new = _source.substring(INTEGER._0, _lindex);
			_new += STRING.DOT + extension;
			
			_result = new File(_new);
		}
		
		return _result;
	}
	
	public String nameOf(final File source){
		String _result = null;
		
		_result = source.getName();
		_result = _result.substring(INTEGER._0, _result.lastIndexOf(STRING.DOT));
		
		return _result;
	}
	
	public String extensionOf(final File source){
		String _result = null;
		
		final String _path = source.getAbsolutePath();
		final int _lindex   = _path.lastIndexOf(STRING.DOT);
		if(_lindex >= INTEGER._0){
			_result = _path.substring(_lindex + INTEGER._1);
			_result = _result.toUpperCase();
		}
		
		return _result;
	}
	
	public static void main(String...args){
		final UtilIO _io = UtilIO.getInstance();
		System.out.println(_io.absoluteOf("com"));
		
		System.out.println(_io.toExtension(new File("C:/temp/image.gic"), "png"));
		System.out.println(_io.nameOf(new File("C:/temp/image.gic")));
		System.out.println(_io.extensionOf(new File("C:/temp/image.gic")));
	}
}
