package com.javaf.pattern;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public abstract class AbstractCommand implements ICommand {
	
	public boolean equals(Object o){
		
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		
		final ICommand _c = (ICommand)o;
		return (getName().equals(_c.getName()));
	}
	
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + getName().hashCode();
		return result;
	}
}
