package com.javaf.javase.lang;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class ClassLoaderTesting {

	public static void main(String[] args) {
		System.out.println("User Dir............: " + System.getProperty("user.dir"));
		System.out.println("Boot class path.....: " + System.getProperty("sun.boot.class.path"));  
	    System.out.println("Extension class path: " + System.getProperty("java.ext.dirs"));  
	    System.out.println("AppClassPath........: " + System.getProperty("java.class.path"));
	    
	    final UtilClass _class = UtilClass.getInstance();
	    final ClassLoaderTesting _testing = new ClassLoaderTesting();
	    System.out.println("Boot CL........: " + Object.class.getClassLoader());
	    
	    
	    System.out.println("App ClassLoader: " + _class.rootOf( _testing.getClass().getClassLoader() ));  
	    
	}

}
