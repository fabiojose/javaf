package com.javaf.javase.persistence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Read extends DML {

	private final Set<Column<?>> columns = new HashSet<Column<?>>();
	private Class<? extends Table> from;
	
	private Criteria criteria;
	
	public Read(){
		
	}
	
	public Class<? extends Table> table() {
		return from;
	}
	
	public Set<Column<?>> columns(){
		return columns;
	}
	
	public Criteria criteria(){
		return criteria;
	}
	
	public Read select(final Column<?>...columns){
		this.columns.addAll(Arrays.asList(columns));
		
		logging.trace(" - - - - READ - - - - " + Arrays.asList(columns));
		
		return this;
	}
	
	public Read from(final Class<? extends Table> from){
		this.from = from;
		
		logging.trace(" - - - - READ - - - - FROM " + from.getSimpleName());
		
		return this;
	}
	
	public <T> Criteria where(final Column<T> column){
		criteria = new Criteria(column, this);
		
		return criteria;
	}

}
