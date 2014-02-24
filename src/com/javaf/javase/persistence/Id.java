package com.javaf.javase.persistence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Id {

	private Set<Column<?>> columns;

	public Id(){
		
	}
	
	public Id(final Column<?>...columns){
		getColumns().addAll(Arrays.asList(columns));
	}
	
	public Set<Column<?>> getColumns() {
		if(null== columns){
			columns = new HashSet<Column<?>>();
		}
		return columns;
	}

	public void setColumns(Set<Column<?>> columns) {
		this.columns = columns;
	}
	
}
