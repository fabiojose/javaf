package bradesco.tf;

import java.io.Serializable;

import com.javaf.ObjectPool;
import com.javaf.model.Pesquisa;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import bradesco.tf.TFConstants.BUSINESS;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
@SuppressWarnings("deprecation")
public final class TFObjectFactory {
	private static final TFObjectFactory INSTANCE = new TFObjectFactory();
	
	private final ObjectPool opool = ObjectPool.getPool();
	private TFObjectFactory(){
		
	}
	
	public static final synchronized TFObjectFactory getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Constr�i uma inst�ncia de BeanWrapper com a estrutura base para utilizar no contexto regra de neg�cio.<br/>
	 * Utilize este m�todo no contexto de Eventos quando for invocar uma nova regra.<br/>
	 * - <code>BUSINESS.DATA_SERVICE_NODE</code>: Data Service Node<br/>
	 * - <code>BUSINESS.OBJECT_POOL</code>: Pool de Objetos padr�o
	 * 
	 * @param provider Provedor de servi�os
	 * @param node Servi�os de dados
	 * @param operation Operador de servi�os
	 * @return Nova inst�ncia
	 * @see TransferBeanWrapper
	 * @see BUSINESS#DATA_SERVICE_NODE
	 * @see BUSINESS#OBJECT_POOL
	 * @see #getObjectPool(IEventsServiceProvider)
	 */
	public TransferBeanWrapper doBusinessBeanWrapper(final IEventsServiceProvider provider, final IDataServiceNode node, final IOperation operation){
		
		final TransferBeanWrapper _result = new TransferBeanWrapper();
		_result.put(BUSINESS.DATA_SERVICE_NODE, node);
		_result.put(BUSINESS.OBJECT_POOL,       opool);
		_result.put(BUSINESS.OPERATION,         operation);
		
		return _result;
	}
	
	public void doBusinessBeanWrapper(final IEventsServiceProvider provider, final IDataServiceNode node, final IOperation operation, final TransferBeanWrapper wrapper){
		
		wrapper.put(BUSINESS.DATA_SERVICE_NODE, node);
		wrapper.put(BUSINESS.OBJECT_POOL,       opool);
		wrapper.put(BUSINESS.OPERATION,         operation);

	}
	
	/**
	 * Constr�i uma inst�ncia de BeanWrapper com a estrutura base para utilizar no contexto regra de neg�cio.<br/>
	 * Utilize este m�todo no contexto de Eventos quando for invocar uma nova regra.<br/>
	 * - <code>BUSINESS.DATA_SERVICE_NODE</code>: Data Service Node<br/>
	 * - <code>BUSINESS.OBJECT_POOL</code>: Pool de Objetos padr�o
	 * 
	 * @param provider Provedor de servi�os
	 * @param node Servi�os de dados
	 * @param instancia Inst�ncia de trabalho da funcionalidade corrente
	 * @param operation Operador de servi�os
	 * @return Nova inst�ncia
	 * @see TransferBeanWrapper
	 * @see BUSINESS#DATA_SERVICE_NODE
	 * @see BUSINESS#OBJECT_POOL
	 * @see #getObjectPool(IEventsServiceProvider)
	 */
	public TransferBeanWrapper doBusinessBeanWrapper(final IEventsServiceProvider provider, final IDataServiceNode node, final IOperation operation, final Object instancia){
		return doBusinessBeanWrapper(provider, node, operation, instancia, opool, null);
	}
	
	public TransferBeanWrapper doBusinessBeanWrapper(final IEventsServiceProvider provider, final IDataServiceNode node, final IOperation operation, final Object instancia, final Class<?> caller){
		return doBusinessBeanWrapper(provider, node, operation, instancia, opool, caller);
	}
	
	/**
	 * Constr�i uma inst�ncia de BeanWrapper com a estrutura base para utilizar no contexto regra de neg�cio.<br/>
	 * Utilize este m�todo no contexto de Eventos quando for invocar uma nova regra.<br/>
	 * - <code>BUSINESS.DATA_SERVICE_NODE</code>: Data Service Node<br/>
	 * - <code>BUSINESS.OBJECT_POOL</code>: Pool de Objetos padr�o
	 * 
	 * @param provider Provedor de servi�os
	 * @param node Servi�os de dados
	 * @param instancia Inst�ncia de trabalho da funcionalidade corrente
	 * @param operation Operador de servi�os
	 * @param opool ObjectPool
	 * @return Nova inst�ncia
	 * @see TransferBeanWrapper
	 * @see BUSINESS#DATA_SERVICE_NODE
	 * @see BUSINESS#OBJECT_POOL
	 * @see #getObjectPool(IEventsServiceProvider)
	 */
	public TransferBeanWrapper doBusinessBeanWrapper(final IEventsServiceProvider provider, final IDataServiceNode node, final IOperation operation, final Object instancia, final ObjectPool opool, final Class<?> caller){
		
		final TransferBeanWrapper _result = new TransferBeanWrapper();
		_result.put(BUSINESS.DATA_SERVICE_NODE, node);
		_result.put(BUSINESS.OBJECT_POOL,       opool);
		_result.put(BUSINESS.OPERATION,         operation);
		_result.put(BUSINESS.INSTANCIA,         instancia);
		_result.put(BUSINESS.CALLER,            caller);
		
		return _result;
	}
	
	/**
	 * Constr�i uma inst�ncia de BeanWrapper com a estrutura base para utilizar no contexto regra de neg�cio numa funcionalidade Pesquisar.<br/>
	 * 
	 * @param <T> Tipo parametrizado do GenericPesquisarDTO
	 * @param provider Provedor de servi�os
	 * @param node Servi�os de dados
	 * @param operation Operador de servi�os
	 * @param pesquisar
	 * @return Nova int�ncia
	 * @see #doBusinessBeanWrapper(IEventsServiceProvider, IDataServiceNode)
	 * @see BUSINESS#INSTANCIA
	 */
	public <T extends Serializable> TransferBeanWrapper doBusinessBeanWrapper(final IEventsServiceProvider provider, final IDataServiceNode node, final IOperation operation, final Pesquisa<T, T> pesquisar){
		
		final TransferBeanWrapper _result = doBusinessBeanWrapper(provider, node, operation);
		_result.put(BUSINESS.INSTANCIA, pesquisar);
		
		return _result;
	}
}
