package bradesco.tf;

import br.com.bradesco.core.aq.service.IOperation;
import br.com.bradesco.core.ui.beans.basedialog.BradescoDialog;
import bradesco.tf.TFConstants.FWO;
import bradesco.tf.TFConstants.IDENTIFY;

import com.javaf.Constants.STRING;
import com.javaf.model.CruddType;

/**
 * Implementa a criação dinâmica de indetificadores.
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilIdentify {
	private static final UtilIdentify INSTANCE = new UtilIdentify();
	
	private UtilIdentify(){
		
	}
	
	public static synchronized UtilIdentify getInstance(){
		return INSTANCE;
	}
	
	public String doFWOCalling(final IOperation operation, final CruddType type){
		return doFWOCalling(operation, type.name());
	}
	
	public String doFWOCalling(final IOperation operation, final String discriminator){
		String _result = FWO.INSTANCE;
		
		if(null!= operation){
			_result = operation.getOperationProjectBaseClass() + IDENTIFY.KEY_FWO_CALLING + discriminator;
		}
		
		return _result;
	}
	
	public String doFWOCalling(final IOperation operation, final Class<?> clazz){
		String _result = FWO.INSTANCE;
		
		if(null!= operation){
			_result = operation.getOperationProjectBaseClass() + IDENTIFY.KEY_FWO_CALLING + clazz.getSimpleName();
		}
		
		return _result;
	}
	
	public String doInstanceOrchestrator(){
		return IDENTIFY.INSTANCE_ORCHESTRATOR;
	}
	
	/**
	 * Monta o identificador convencionado para a instância que representa os dados em tela.
	 * @param operation Operador de serviços
	 * @return Identificador convencionado
	 */
	public String doInstancia(final IOperation operation){
		return operation.getOperationProjectBaseClass();
	}
	
	public String doLayoutVisible(final IOperation operation, final Class<? extends BradescoDialog> layout, boolean isVisible){
		return operation.getOperationProjectBaseClass() + STRING.DOT + layout.getSimpleName() + IDENTIFY.KEY_LAYOUT_VISIBLE + Boolean.toString(isVisible) ;
	}
	
	public String doLayoutEnabled(final IOperation operation, final Class<? extends BradescoDialog> layout, boolean isEnabled){
		return operation.getOperationProjectBaseClass() + STRING.DOT + layout.getSimpleName() + IDENTIFY.KEY_LAYOUT_ENABLED + Boolean.toString(isEnabled) ;
	}
	
	public String doEventReplacer(final IOperation operation){
		return doInstancia(operation) + IDENTIFY.KEY_EVENT_REPLACERS;
	}
	
	public String doOnEventStart(final IOperation operation){
		return doInstancia(operation) + IDENTIFY.KEY_EVENT_ON_START;
	}
	
	public String doOnEventFinish(final IOperation operation){
		return doInstancia(operation) + IDENTIFY.KEY_EVENT_ON_FINISH;
	}
	
	private String doOnEventFinishCommand(final String operation){
		return operation + IDENTIFY.KEY_EVENT_ON_FINISH_COMMAND;
	}
	
	public String doOnEventFinishCommand(final OPERATION operation){
		return doOnEventFinishCommand(operation.getNome());
	}
	
	public String doOnEventFinishCommand(final IOperation operation){
		return doOnEventFinishCommand(operation.getProjectOperationBean().getOperationAlias());
	}
	
	public String doHelper(final IOperation operation){
		return doInstancia(operation) + IDENTIFY.KEY_HELPER;
	}
	
	public String doInstanciaType(final IOperation operation){
		return doInstancia(operation) + IDENTIFY.KEY_TYPE;
	}
	
	public String doHelperType(final IOperation operation){
		return doHelper(operation) + IDENTIFY.KEY_TYPE;
	}
	
	public String doLocalPool(final IOperation operation){
		return doInstancia(operation) + IDENTIFY.KEY_LOCAL_POOL;
	}
	
	/**
	 * Monta o nome natural da operação<br/>
	 * Ex: PRJ FIRP.ConcessaoRP
	 * 
	 * @param operation
	 * @return
	 */
	/*public String doNatural(final IOperation operation){
		
		String _sufixo = operation.getOperationProjectBaseClass();
		_sufixo = _sufixo.substring(_sufixo.lastIndexOf(CHAR.DOT) + INTEGER._1);
		
		return ProjectConstants.PROJETO + STRING.DOT + _sufixo;
	}*/
	
	/**
	 * Monta o identificador convencionado para a instância de pesquisa
	 * @param operation Operador de serviços
	 * @return Idenficador convencionado
	 */
	public String doPesquisar(final IOperation operation){
		return doInstancia(operation) + IDENTIFY.KEY_PESQUISAR;
	}
	
	/**
	 * Monta o identificador convencionadao para acessar o profixo armazenado da operação
	 * @param operation
	 * @return
	 */
	public String doPrefixo(final IOperation operation){
		return doInstancia(operation) + IDENTIFY.KEY_PREFIXO;
	}
	
	public String doPrefixoPesquisar(final IOperation operation){
		return doPesquisar(operation) + IDENTIFY.KEY_PREFIXO;
	}
	
	/**
	 * Monta o identificador convencionado para a instância de validador
	 * @param layout Layout que terar o identificador de validor pessoal
	 * @return Identificador convencionado
	 * @see ValidatorMediator
	 */
	public String doValidador(final Class<? extends BradescoDialog> layout){
		return layout.getName() + IDENTIFY.KEY_VALIDADOR;
	}
	
	/**
	 * Monta o indentificador convencionado para sinalizar que a operação já está rodando
	 * @param operation Operador de serviços
	 * @return Indetificador convencionado
	 */
	public String doOperationRunning(final IOperation operation){
		return operation.getOperationProjectBaseClass() + IDENTIFY.KEY_RUNNING;
	}
	
	public String doOperation(final IOperation operation){
		return operation.getOperationProjectBaseClass() + IDENTIFY.KEY_BINDDING;
	}
	
	public String doOperationNode(final IOperation operation){
		return operation.getOperationProjectBaseClass() + IDENTIFY.KEY_NODE;
	}
	
	public String doOperationFinalizar(final IOperation operation){
		return operation.getOperationProjectBaseClass() + IDENTIFY.KEY_EXECUTE_FINALIZAR;
	}
	
	public String doID(final Class<?> clazz){
		
		String _result = STRING.EMPTY;
		if(null!= clazz){
			_result = clazz.getName();
		}
		return _result;
	}
	
	public String doID(final Class<?> clazz, final String suffix){
		return suffix + doID(clazz);
	}
	
	public String doID(final Object instancia){
		
		String _result = STRING.EMPTY;
		
		if(null!= instancia){
			_result = doID(instancia.getClass());
		}
		
		return _result;
	}
	
	public String doID(final IOperation operation){
		return operation.getOperationProjectBaseClass();
	}
	
	public String doID(final IOperation operation, final String discriminator){
		return operation.getOperationProjectBaseClass() + STRING.DOT + discriminator;
	}
	
	private String doLabelPrefix(final String operation){
		return operation + IDENTIFY.KEY_LABELS;
	}
	
	private String doLabel(final String operation, final int index){
		return doLabelPrefix(operation) + String.valueOf(index);
	}
	
	public String doLabel(final IOperation operation, final int index){
		return doLabel(operation.getProjectOperationBean().getOperationAlias(), index);
	}
	
	public String doLabelPrefix(final IOperation operation){
		return doLabelPrefix(operation.getProjectOperationBean().getOperationAlias());
	}
	
	public String doLabel(final OPERATION operation, final int index){
		return doLabel(operation.getNome(), index);
	}
	
	public String doLabelPrefix(final OPERATION operation){
		return doLabelPrefix(operation.getNome());
	}
	
	public String doPsdcProduto(){
		return IDENTIFY.KEY_PSDC_PRODUTO;
	}
	
	public String doPsdcParticipacao(){
		return IDENTIFY.KEY_PSDC_PARTICIPACAO;
	}
	
	public String doPsdcRepresentacao(){
		return IDENTIFY.KEY_PSDC_REPRESENTACAO;
	}
	
	public String doShotInstance(final Object o){
		return o.toString() + IDENTIFY.KEY_SHOT_INSTANCE;
	}
}
