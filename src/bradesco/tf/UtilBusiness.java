package bradesco.tf;

import com.javaf.ObjectPool;
import com.javaf.javase.lang.reflect.InvokeException;
import com.javaf.javase.text.UtilFormat;

import br.com.bradesco.core.aq.exceptions.BusinessRuleException;
import br.com.bradesco.core.aq.exceptions.RollbackException;
import br.com.bradesco.core.aq.persistence.BradescoPersistenceException;
import br.com.bradesco.core.aq.service.IBusinessRuleServiceProvider;
import br.com.bradesco.core.aq.service.IConnectionManager;
import br.com.bradesco.core.aq.service.IOperation;
import bradesco.tf.TFConstants.BUSINESS;
import bradesco.tf.TFConstants.FWO;
import bradesco.tf.ocbm.DataServiceNodeProvider;
import bradesco.tf.outside.FWOCalling;

/**
 * Implementação de utilidades para a manipulação e invocação da camada de negócio.
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilBusiness {
	private static final UtilBusiness INSTANCE = new UtilBusiness();
	
	private final UtilIdentify identify;
	private final UtilFormat format;
	private UtilBusiness(){
		identify = UtilIdentify.getInstance();
		format   = UtilFormat.getInstance();
	}
	
	public static final synchronized UtilBusiness getInstance(){
		return INSTANCE;
	}
	
	public Object fwoInternalObject(final TransferBeanWrapper wrapper){
		Object _result = null;
		
		final ObjectPool _opool     = wrapper.get(BUSINESS.OBJECT_POOL, ObjectPool.class);
		final IOperation _operation = wrapper.get(BUSINESS.OPERATION, IOperation.class);
		final Class<?> _caller      = wrapper.get(BUSINESS.CALLER, Class.class);
		final String _fwoID         = identify.doFWOCalling(_operation, _caller);
		
		if(_opool.has(_fwoID)){
			final FWOCalling _fwo = (_opool.get(_fwoID, FWOCalling.class));
			_result               = _fwo.getInstancia();
		}
		
		return _result;
	}
	
	public void removeFwoRecall(final TransferBeanWrapper wrapper, final Class<? extends AbstractBusinessComponent> business){
		
		wrapper.put(BUSINESS.CALLER, business);
		removeFwoRecall(wrapper);
		
	}
	
	public void removeFwoRecall(final TransferBeanWrapper wrapper){
		
		final ObjectPool _opool     = wrapper.get(BUSINESS.OBJECT_POOL, ObjectPool.class);
		final IOperation _operation = wrapper.get(BUSINESS.OPERATION, IOperation.class);
		final Class<?> _caller      = wrapper.get(BUSINESS.CALLER, Class.class);
		final String _fwoID         = identify.doFWOCalling(_operation, _caller);
		
		_opool.remove(_fwoID);
	}
	
	public boolean isFwoRecall(final TransferBeanWrapper wrapper){
		boolean _result = Boolean.FALSE;
		
		final ObjectPool _opool     = wrapper.get(BUSINESS.OBJECT_POOL, ObjectPool.class);
		final IOperation _operation = wrapper.get(BUSINESS.OPERATION, IOperation.class);
		final Class<?> _caller      = wrapper.get(BUSINESS.CALLER, Class.class);
		
		final String _fwoID         = identify.doFWOCalling(_operation, _caller);
		
		_result = (null!= _opool.get(_fwoID, FWOCalling.class));
			
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	public void fwo(final IBusinessRuleServiceProvider provider, final TransferBeanWrapper wrapper, final Object ocbm, final InvokeType invoke, final boolean fetchAll) throws BusinessRuleException, RollbackException, BradescoPersistenceException{
		
		final ObjectPool _opool     = wrapper.get(BUSINESS.OBJECT_POOL, ObjectPool.class);
		final IOperation _operation = wrapper.get(BUSINESS.OPERATION, IOperation.class);
		final Class<?>   _caller    = wrapper.get(BUSINESS.CALLER, Class.class);
		final String _fwoID         = identify.doFWOCalling(_operation, _caller);
		final boolean _start        = format.toBoolean( wrapper.get(BUSINESS.RIGHT_FROM_START, Boolean.class) );

		boolean _recall = Boolean.TRUE;
		FWOCalling _fwo  = _opool.get(_fwoID, FWOCalling.class);
		if(null== _fwo || _start){
			_fwo    = new FWOCalling();
			_recall = Boolean.FALSE;
		}
		
		DataServiceNodeProvider<Object> _provider = _opool.get(BUSINESS.OCBM_PROVIDER, DataServiceNodeProvider.class);
		if(null== _provider){
			_provider = new DataServiceNodeProvider<Object>();
			_opool.put(BUSINESS.OCBM_PROVIDER, _provider);
		}
		
		try{
			
			if(!_recall){
				wrapper.put(FWO.PAGINATION_FLAG, IConnectionManager.PAGINATION_INIT);
				
				int _returned = _fwo.invoke(ocbm, _provider, provider, wrapper, invoke);
				
				if(IConnectionManager.RETURN_WITH_MORE_DATA== _returned){
					if(!fetchAll){
						//armazenar para recall
						_opool.put(_fwoID, _fwo);
						
					} else {
						
						wrapper.put(FWO.PAGINATION_FLAG, IConnectionManager.PAGINATION_NEXT);
						do{
							_returned = _fwo.invoke(provider, wrapper);
							
						}while(IConnectionManager.RETURN_WITH_MORE_DATA== _returned);
					}
				}
				
			} else {
				wrapper.put(FWO.PAGINATION_FLAG, IConnectionManager.PAGINATION_NEXT);
				
				int _returned = _fwo.invoke(provider, wrapper);
				if(IConnectionManager.RETURN_WITH_MORE_DATA != _returned){
					_opool.remove(_fwoID);
				}
			}
			
		}catch(InvokeException _e){
			throw new BradescoPersistenceException(_e.getMessage(), _e);
			
		}catch(UnsuccessfulException _e){
			throw new BradescoPersistenceException(_e.getMessage(), _e);
		}
		
	}

}
