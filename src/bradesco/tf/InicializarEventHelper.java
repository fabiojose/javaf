package bradesco.tf;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.exceptions.EventException;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import bradesco.tf.TFConstants.FACTORY;
import bradesco.tf.dsn.UtilDSN;

import com.javaf.ObjectPool;
import com.javaf.model.Pesquisa;
import com.javaf.model.IDynamic;

/**
 * Auxilia em operações comuns dos eventos
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public final class InicializarEventHelper {

	private TerminalFinanceiro arquitetura;
	private UtilIdentify identify;
	private UtilDSN dsn;
	
	public InicializarEventHelper(){
		arquitetura = TerminalFinanceiro.getInstance();
		identify    = UtilIdentify.getInstance();
		dsn         = UtilDSN.getInstance();
	}
	
	/**
	 * Base da executação do evento inicializar de operações.<br/>
	 * Aqui são montadas estruturas de trabalho e pesquisa que populam dados no layout.
	 * @param provider Providor de serviços
	 * @param operation Operador de métodos
	 * @param node Provedor de dados
	 * @return Instância de trabalho armazenada no ObjectPool da sessão do usuário
	 * @throws EventException
	 */
	@SuppressWarnings("unchecked")
	public IDynamic execute(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final IDynamic instancia) throws EventException{
		
		final ObjectPool _opool = arquitetura.getObjectPool(provider);
		return execute(provider, operation, node, instancia, _opool.getOrCreate(identify.doPesquisar(operation), Pesquisa.class));
	}

	public IDynamic execute(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final IDynamic instancia, final Pesquisa<? extends IDynamic, ?> pesquisar) throws EventException{
		final ObjectPool _opool = arquitetura.getObjectPool(provider);
		
		//configurar orquestrador de objetos único na sessão do usuário
		_opool.getOrCreate(identify.doInstanceOrchestrator(), InstanceOrchestrator.class, FACTORY.FACTORY_INSTANCE_ORCHESTRATOR);
		
		//configurar model de trabalho
		_opool.put(identify.doInstancia(operation), instancia);
		
		//montar view de pesquisa
		dsn.toContainerObject(node, pesquisar, _opool.get(identify.doPrefixoPesquisar(operation), String.class), Boolean.TRUE);

		//montar view de trabalho
		dsn.toContainerObject(node, instancia, _opool.get(identify.doPrefixo(operation), String.class), Boolean.TRUE);
		
		return instancia;
	}
	
}
