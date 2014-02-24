package com.javaf.javase.lang;

public final class StringBuilderWrapper {

	private StringBuilder sb;
	private String separator;
	
	public StringBuilderWrapper(){
		sb = new StringBuilder();
		
		separator = "";
	}
	
	public StringBuilderWrapper(final String separator){
		this();
		this.separator = separator;
	}
	
	public StringBuilderWrapper append(final String s){
		sb.append(s);
		sb.append(separator);
		
		return this;
	}
	
	public StringBuilderWrapper append(final CharSequence cs){
		sb.append(cs);
		sb.append(separator);
		
		return this;
	}
	
	public String toString(){
		return sb.toString();
	}
}
