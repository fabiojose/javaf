package com.javaf.javax.image;

import java.awt.image.BufferedImage;


/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class Image {

	private BufferedImage buffered;
	private ImageType type;
	private String name;
	
	public Image(final BufferedImage buffered, final ImageType type, final String name){
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
		
		this.buffered = buffered;
		this.type     = type;
		this.name     = name;
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
	
}
