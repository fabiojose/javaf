package com.javaf.javase.persistence;

import java.util.LinkedList;
import java.util.List;

import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;



public class Criteria {
	
	private static final ILogging LOGGING = Logging.loggerOf(Criteria.class);
	
	private final LinkedList<Object> stack = new LinkedList<Object>();
	private final IDML dml;
	
	public Criteria(final Object start, final IDML dml){
		stack.add(start);
		this.dml = dml;
		
		LOGGING.trace(" - - - - " + dml.getClass().getSimpleName().toUpperCase() + " - - - - WHERE " + start);
	}
	
	public List<Object> stack(){
		return stack;
	}
	
	public <T> Criteria and(final  Column<T> column){
		stack.add(OperatorType.AND);
		stack.add(column);
		
		LOGGING.trace(" - - - - " + dml.getClass().getSimpleName().toUpperCase() + " - - - - - AND " + column);
		
		return this;
	}
	
	public <T> Criteria or(final  Column<T> column){
		stack.add(OperatorType.OR);
		stack.add(column);
		
		LOGGING.trace(" - - - - " + dml.getClass().getSimpleName().toUpperCase() + " - - - - -OR " + column);
		
		return this;
	}
	
	/**
	 * Equal To
	 * @param <T>
	 * @param value
	 * @return
	 */
	public <T> Criteria eq(final T value){
		stack.add(OperatorType.EQUAL);
		stack.add(value);
		
		LOGGING.trace(" - - - - " + dml.getClass().getSimpleName().toUpperCase() + " - - - - EQUAL >" + value + "<");
		
		return this;
	}
}
