package bradesco.tf.validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.javaf.Constants.I18N;
import com.javaf.javase.util.Localization;

import bradesco.tf.ResourceException;

/**
 * Mediador de validação do layout
 * @author fabiojm - Fábio José de Moraes
 */
public final class ValidatorMediator implements IValidatorMediator {
	
	private List<String> messages;
	private Map<Object, IValidator> validators;
	
	private boolean turnedOn;
	
	private String first;
	
	public ValidatorMediator(){		
		validators = new LinkedHashMap<Object, IValidator>();
		messages   = new ArrayList<String>();
	}
	
	public List<String> getMessages(){
		return messages;
	}
	
	public void put(final Object key, final IValidator validator){
		validators.put(key, validator);
	}
	
	public IValidator remove(final Object key){
		return validators.remove(key);
	}
	
	public void clear(){
		validators.clear();
		messages = new ArrayList<String>();
	}

	public boolean validate() {
		messages = new ArrayList<String>();
		return validate(messages);
	}
	
	private void reset(){
		final Collection<IValidator> _validators = validators.values();
		for(IValidator _validator : _validators){
			_validator.setFirst(null);
		}
	}
	
	public boolean validate(final List<String> messages){
		
		boolean _result = Boolean.TRUE;
		first = null;
		
		reset();
		
		final Collection<IValidator> _validators = validators.values();
		for(IValidator _validator : _validators){
			
			if(_validator.isTurnedOn()){
				final boolean __result = _validator.validate(messages);
				
				if(!__result){
					if(null== first){
						first = _validator.getFirst();
					}
				}
				_result &= __result;
			}
		}
		
		return _result;
		
	}
	
	private void turn(final Object validator, final boolean turn){
		
		final IValidator _validator = validators.get(validator);
		if(null!= _validator){
			_validator.setTurnedOn(turn);
		} else {
			throw new ResourceException(Localization.getInstance().localize(I18N.X_NAO_FOI_ENCONTRADAO, IValidator.class, validator));
		}
	}

	public void turnOn(final Object validator){
		turn(validator, Boolean.TRUE);
	}
	
	public void turnOff(final Object validator){
		turn(validator, Boolean.FALSE);
	}
	
	public boolean isTurnedOn() {
		return turnedOn;
	}
	public void setTurnedOn(final boolean turnedOn){
		this.turnedOn = turnedOn;
	}

	public IValidator validator(final Object key) {
		return validators.get(key);
	}

	public String getFirst() {
		return first;
	}
	
	public void setFirst(final String first){
		this.first = first;
	}
}
