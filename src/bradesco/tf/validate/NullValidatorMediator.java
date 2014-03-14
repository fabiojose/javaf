package bradesco.tf.validate;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class NullValidatorMediator implements IValidatorMediator {

	private ArrayList<String> messages;
	public void clear() {

	}

	public List<String> getMessages() {
		if(null== messages){
			messages = new ArrayList<String>();
		}
		
		return messages;
	}

	public void put(Object key, IValidator validator) {
	}

	public IValidator remove(Object key) {
		return null;
	}

	public void turnOff(Object validator) {
	}

	public void turnOn(Object validator) {
	}

	public boolean validate() {
		return Boolean.TRUE;
	}

	public boolean validate(List<String> messages) {
		return Boolean.TRUE;
	}

	public boolean isTurnedOn() {
		return Boolean.TRUE;
	}

	public void setTurnedOn(boolean turnedOn) {
	}

	public IValidator validator(final Object key) {
		return null;
	}

	public String getFirst() {
		return null;
	}
	
	public void setFirst(String first){
	}

}
