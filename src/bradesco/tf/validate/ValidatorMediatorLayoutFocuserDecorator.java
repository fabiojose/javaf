package bradesco.tf.validate;

import java.util.List;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.exceptions.BradescoBranchException;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import bradesco.tf.LayerMediator;
import bradesco.tf.UtilIdentify;
import bradesco.tf.layout.UtilLayout;

import com.javaf.ObjectPool;
import com.javaf.javase.logging.Logging;

@SuppressWarnings("deprecation")
public class ValidatorMediatorLayoutFocuserDecorator implements IValidatorMediator {
	
	private final UtilLayout layout;
	private final ObjectPool opool;
	private final UtilIdentify identify;
	
	private IValidatorMediator decorated;
	
	private IEventsServiceProvider provider;
	private IOperation operation;
	private IDataServiceNode node;
	
	public ValidatorMediatorLayoutFocuserDecorator(final IValidatorMediator decorated){
		this.decorated = decorated;
		
		layout = UtilLayout.getInstance();
		opool  = LayerMediator.getInstance().getObjectPool();
		identify = UtilIdentify.getInstance();
	}

	public void setNode(IDataServiceNode node) {
		this.node = node;
	}
	public void setOperation(IOperation operation) {
		this.operation = operation;
	}

	public void setProvider(IEventsServiceProvider provider) {
		this.provider = provider;
	}

	public void clear() {
		decorated.clear();
	}

	public List<String> getMessages() {
		return decorated.getMessages();
	}

	public void put(Object key, IValidator validator) {
		decorated.put(key, validator);
	}

	public IValidator remove(Object key) {
		return decorated.remove(key);
	}

	public void turnOff(Object key) {
		decorated.turnOff(key);
	}

	public void turnOn(Object key) {
		decorated.turnOn(key);
	}

	public boolean validate() {
		decorated.getMessages().clear();
		return validate(decorated.getMessages());
	}

	public boolean validate(final List<String> messages) {
		boolean _result = decorated.validate(messages);
		
		if(!_result){
			
			try{
				layout.setFocus(provider, node, 
						layout.toLayout(decorated.getFirst(), opool.get(identify.doPrefixo(operation), String.class)) );
			
			}catch(BradescoBranchException _e){
				Logging.loggerOf(getClass()).warn(_e.getMessage());
			}
			
		}
		
		return _result;
	}

	public IValidator validator(Object key) {
		return decorated.validator(key);
	}

	public String getFirst() {
		return decorated.getFirst();
	}

	public boolean isTurnedOn() {
		return decorated.isTurnedOn();
	}

	public void setFirst(String first) {
		decorated.setFirst(first);
	}

	public void setTurnedOn(boolean turnedOn) {
		decorated.setTurnedOn(turnedOn);
	}

}
