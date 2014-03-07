package com.javaf.javax.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.javaf.Bagman;
import com.javaf.ConversionException;
import com.javaf.Utility;
import com.javaf.Constants.STRING;
import com.javaf.javase.io.UtilIO;
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
public final class UtilImage implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilImage();
		}
		
	};
	
	private static final UtilIO IO = UtilIO.getInstance();
	
	private final ILogging logging = Logging.loggerOf(getClass());
	private UtilImage(){
		
	}
	
	public static final synchronized UtilImage getInstance(){
		return Bagman.utilOf(UtilImage.class, FACTORY);
	}
	
	public BufferedImage convertTo(final BufferedImage source, final ImageType type) throws ConversionException{
		BufferedImage _result = null;
		
		try{
			logging.trace("Converter imagem para >" + type + "<");
			
			final ByteArrayOutputStream _output = new ByteArrayOutputStream();
			ImageIO.write(source, type.name(), _output);
			
			final ByteArrayInputStream _input = new ByteArrayInputStream(_output.toByteArray());
			_result = ImageIO.read(_input);
			
		}catch(IOException _e){
			logging.debug(_e.getMessage(), _e);
			throw new ConversionException(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public File write(final Image image, final File directory)  throws IOException {
		File _result = null;
		
		_result = new File(directory, image.getName() + STRING.DOT + image.getType().name());
		
		try{
			ImageIO.write(image.getBuffered(), image.getType().name(), _result);
			
		}catch(IOException _e){
			throw _e;
		}
		
		return _result;
	}
	
	public Image read(final File input) throws IOException, IllegalArgumentException {
		Image _result = null;
		
		final BufferedImage _buffered = ImageIO.read(input);
		final String _extension       = IO.extensionOf(input);
		final ImageType _type         = ImageType.valueOf(_extension);
		final String _name            = IO.nameOf(input);
		
		_result = new Image(_buffered, _type, _name, input);
		
		return _result;
	}

}
