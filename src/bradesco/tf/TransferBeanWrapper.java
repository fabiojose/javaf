package bradesco.tf;

import java.util.Set;

import br.com.bradesco.core.aq.service.TransferBean;

import com.javaf.model.BeanWrapper;

/**
 * Implementa a criação dinâmica de um Java Bean extendendo a BeanWrapper e implementando TransferBean
 * @see BeanWrapper
 * @see TransferBean
 * @author fabiojm - Fábio José de Moraes
 */
public class TransferBeanWrapper extends BeanWrapper implements TransferBean {
	private static final long serialVersionUID = -141640005584037745L;
	
	public TransferBeanWrapper(){
		
	}
	
	public TransferBeanWrapper(final TransferBeanWrapper source){
		if(null!= source){
			final Set<String> _keys = source.keySet();
			for(String _key : _keys){
				put(_key, source.get(_key));
			}
		}
	}
}
