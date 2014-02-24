package com.javaf.javase.io;

import java.io.File;
import java.io.FileFilter;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class GIFFilesFilter implements FileFilter {

	public boolean accept(final File file) {
		return file.getName().toLowerCase().endsWith("gif");
	}

}
