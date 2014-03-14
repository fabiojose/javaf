package bradesco.tf.validate;

import bradesco.tf.TFConstants.IDENTIFY;

import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.text.UtilFormat;
import com.javaf.model.ValuePlace;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class ValidatorHelper {

	private final UtilFormat format         = UtilFormat.getInstance();
	private final UtilReflection reflection = UtilReflection.getInstance();
	public ValidatorHelper(){
		
	}
	
	public String onInvalid(final Validator validator, final String defaultMessage){
		
		String _result = defaultMessage;
		if(!format.isDefault(validator.getMessage())){
			_result = validator.getMessage();
		}
		
		return _result;
	}
	
	public void onInvalid(final Validator validator, final ValuePlace<?> place){
		
		if(null== validator.getFirst()){
			validator.setFirst(place.getName());
		}
	}
	
	public void onDecorated(final IValidatorMediator mediator, final Object provider, final Object operation, final Object node){
		if(mediator instanceof ValidatorMediatorLayoutFocuserDecorator){
			
			reflection.setValue(mediator, IDENTIFY.PROVIDER, provider);
			reflection.setValue(mediator, IDENTIFY.operation, operation);
			reflection.setValue(mediator, IDENTIFY.NODE, node);
		}
	}
}
