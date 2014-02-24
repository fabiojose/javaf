package com.javaf.javase.persistence.dialect;

import java.sql.PreparedStatement;


public interface INativeExecuter extends IDialect<PreparedStatement> {

	boolean execute(PreparedStatement prepared);
	
}
