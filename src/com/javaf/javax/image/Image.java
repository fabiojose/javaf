package com.javaf.javax.image;

import java.awt.image.BufferedImage;
import java.io.File;

import com.javaf.Constants.STRING;


/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class Image {

	private BufferedImage buffered;
	private ImageType type;
	private String name;
	private File file;
	
	public Image(final BufferedImage buffered, final ImageType type, final String name, final File file){
		if(null== buffered){
			throw new NullPointerException("arg1 is null!");
		}
		
		if(null== type){
			throw new NullPointerException("arg2 is null!");
		}
		
		if(null== name){
			
			throw new NullPointerException("arg3 is null!");
			
		} else if(name.isEmpty()){
			
			throw new IllegalArgumentException("arg3 is empty!");
			
		}
		
		if(null== file){
			throw new IllegalArgumentException("arg4 is empty!");
		}
		
		this.buffered = buffered;
		this.type     = type;
		this.name     = name;
		this.file     = file;
	}
	
	public BufferedImage getBuffered() {
		return buffered;
	}
	public ImageType getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public File getFile(){
		return file;
	}
	
	public String toString(){
		String _result = null;
		
		_result = name + STRING.DOT + type;
		
		return _result;
	}
}
