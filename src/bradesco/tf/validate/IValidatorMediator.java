package bradesco.tf.validate;

import java.util.List;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public interface IValidatorMediator extends IValidator{

	List<String> getMessages();
	
	void put(final Object key, final IValidator validator);
	IValidator remove(final Object key);
	
	void clear();
	
	boolean validate();
	boolean validate(final List<String> messages);
	
	void turnOn(final Object key);	
	void turnOff(final Object key);	
	
	IValidator validator(final Object key);
}
