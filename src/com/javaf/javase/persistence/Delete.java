package com.javaf.javase.persistence;


public class Delete extends DML {

	private Class<? extends Table> from;
	
	private Criteria criteria;
	
	public Delete(){
		
	}
	
	public Class<? extends Table> table() {
		return from;
	}
	
	public Criteria criteria(){
		return criteria;
	}
	
	public Delete from(final Class<? extends Table> from){
		this.from = from;
		
		logging.trace(" - - - - DELETE - - - - FROM " + from.getSimpleName());
		
		return this;
	}
	
	public <T> Criteria where(final Column<T> column){
		criteria = new Criteria(column, this);
		
		return criteria;
	}
	
}
