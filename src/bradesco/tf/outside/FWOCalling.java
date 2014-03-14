package bradesco.tf.outside;

import br.com.bradesco.core.aq.dataservice.IContainerObject;
import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.exceptions.BradescoBranchException;
import br.com.bradesco.core.aq.service.IArquitecturalActions;
import br.com.bradesco.core.aq.service.IBusinessRuleServiceProvider;
import br.com.bradesco.core.aq.service.IConnectionManager;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import bradesco.tf.InvokeType;
import bradesco.tf.ResourceType;
import bradesco.tf.TFObjectFactory;
import bradesco.tf.TransferBeanWrapper;
import bradesco.tf.UnsuccessfulException;
import bradesco.tf.UtilFWO;
import bradesco.tf.TFConstants.BUSINESS;
import bradesco.tf.TFConstants.FWO;
import bradesco.tf.builder.BuilderInput;
import bradesco.tf.builder.TFBuilder;
import bradesco.tf.dsn.UtilDSN;
import bradesco.tf.ocbm.DataServiceNodeProvider;
import cobol.ocbm.Process;
import cobol.ocbm.ReadingException;
import cobol.ocbm.WritingException;

import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.javase.lang.reflect.InvokeException;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;

/**
 * Implementa chamada e facilitadores para invocar o Framework On Line CICS.
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public class FWOCalling {

	private final UtilReflection reflection      = UtilReflection.getInstance();
	private final UtilDSN dsn                    = UtilDSN.getInstance();
	private final TFObjectFactory factory        = TFObjectFactory.getInstance();
	private final UtilFWO fwo                    = UtilFWO.getInstance();
	private final ILocalization localization     = Localization.getInstance();
	private final TFBuilder builder              = TFBuilder.getInstance();
	
	private Object instancia;
	@SuppressWarnings("unchecked")
	private DataServiceNodeProvider provider;
	private TransferBeanWrapper wrapper;
	private InvokeType type;
	private IDataServiceNode node;
	
	private <T> int invoke(final T instancia, final DataServiceNodeProvider<T> provider, final IConnectionManager connection, final TransferBeanWrapper wrapper, final InvokeType type, final IArquitecturalActions actions, final IDataServiceNode node) throws UnsuccessfulException, InvokeException, NullPointerException {
		
		int _result = INTEGER._0;
		
		final ILogging _logging = Logging.loggerOf(getClass());
		
		if(null!= instancia){
			final Class<?> _clazz = instancia.getClass();
			
			final IOperation  _operation = wrapper.get(BUSINESS.OPERATION, IOperation.class);
			//final IDataServiceNode _node = wrapper.get(BUSINESS.DATA_SERVICE_NODE, IDataServiceNode.class);
			final Process _process = reflection.annotationOf(_clazz, Process.class);
			if(null!= _process && null!= _operation){
				
				//obter área (book) do FWO no DSN Application
				//arquitetura.doResource(ResourceType.FWO, _process.fluxo(), node, actions, node);
				builder.build(new BuilderInput(ResourceType.FWO, _process.fluxo(), node));
				
				final IContainerObject _area = dsn.getOrAddContainerObject(_process.fluxo(), node);
				provider.setArea(_area);
				
				try{
					_logging.info(localization.localize(I18N.GRAVANDO_AREA_INPUT_FLUXO, _process.fluxo()));
					
					//gravar area de input do fluxo
					boolean _rewrite = Boolean.FALSE;
					
					//se houver nova escrita para enviar todos os dados
					do{
						//limpeza da area inicial
						DataServiceNodeProvider.clear(_process, node);
						
						//escrever
						_rewrite = provider.write(instancia);
					
						_logging.info(localization.localize(I18N.EXECUTANDO_FLUXO, _process.fluxo()));
						
						//invocar fluxo
						if(InvokeType.DEFAULT.equals(type)){
							_result = connection.executeFWProcess(_process.fluxo(), node, null, Boolean.FALSE);
							
						} else {//PAGINATION
							String _flag = IConnectionManager.PAGINATION_INIT;
							if(null!= wrapper.get(FWO.PAGINATION_FLAG)){
								_flag = wrapper.get(FWO.PAGINATION_FLAG, String.class);
								
								//evitar propagacao
								wrapper.remove(FWO.PAGINATION_FLAG);
							}
							
							_result = connection.executeFrameworkProcessWithPagination(_process.fluxo(), _flag, node, null, Boolean.FALSE);
						}
						
						if(IConnectionManager.RETURN_OK == _result || IConnectionManager.RETURN_WITH_MORE_DATA == _result){						
							_logging.info(localization.localize(I18N.LEITURA_AREA_OUTPUT_FLUXO, _process.fluxo()));
							
							//leitura da area de output do fluxo
							provider.read(instancia);
							
						} else {
							String _message = fwo.resultOf(_area);
							if(_message.isEmpty()){
								 _message = localization.localize(I18N.RESULTADO_FLUXO_NAO_SUCESSO, _result);
							}
							
							_logging.error(_message);
							
							//montar objeto de exceção
							final UnsuccessfulException _exception = new UnsuccessfulException(_message);
							_exception.setData(fwo.codeOf(_area));
							
							throw _exception;
						}
						
						wrapper.put(FWO.MORE_DATA, (IConnectionManager.RETURN_WITH_MORE_DATA == _result));
						wrapper.put(BUSINESS.RETURNED, _result);
						
					}while(_rewrite);
					
				}catch(WritingException _e){
					throw new InvokeException(localization.localize(I18N.PROBLEMA_GRAVACAO_AREA_FLUXO, _e.getMessage()), _e);
					
				}catch(ReadingException _e){
					throw new InvokeException(localization.localize(I18N.PROBLEMA_LEITURA_AREA_FLUXO, _e.getMessage()), _e);
					
				}catch(BradescoBranchException _e){
					throw new InvokeException(localization.localize(I18N.PROBLEMA_EXECUCAO_FLUXO, _e.getMessage()), _e);
				}
			} else if(null== _process) {
				throw new InvokeException(localization.localize(I18N.CLASSE_NAO_ANOTADA, Process.class.getName(), instancia));
				
			} else {
				throw new InvokeException(localization.localize(I18N.INSTANCIA_NAO_ENCONTRADA_WRAPPER, IOperation.class));
			}
			
		} else {
			throw new NullPointerException(I18N.INSTANCIA_NULA);
		}
		
		return _result;
	}
	
	public <T> int invoke(final T instancia, final DataServiceNodeProvider<T> provider, final IEventsServiceProvider services, final IDataServiceNode node, final IOperation operation) throws UnsuccessfulException, InvokeException {
		
		this.instancia = instancia;
		this.provider  = provider;
		this.node      = node;
		
		final TransferBeanWrapper _wrapper = factory.doBusinessBeanWrapper(services, node, operation);
		this.wrapper = _wrapper;
		
		return invoke(instancia, provider, services.getConnectionManager(), _wrapper, type, services.getArquitecturalActions(), node);
	}
	
	public <T> int invoke(final T instancia, final DataServiceNodeProvider<T> provider, final IBusinessRuleServiceProvider services, final TransferBeanWrapper wrapper, final InvokeType type) throws UnsuccessfulException, InvokeException {
		
		this.instancia = instancia;
		this.provider  = provider;
		this.wrapper   = wrapper;
		this.type      = type;
		this.node      = wrapper.get(BUSINESS.DATA_SERVICE_NODE, IDataServiceNode.class);
		
		return invoke(instancia, provider, services.getConnectionManager(), wrapper, type, services.getArquitecturalActions(), node);
	}
	
	@SuppressWarnings("unchecked")
	public <T> int invoke(final IEventsServiceProvider services) throws IllegalStateException, UnsuccessfulException, InvokeException{
		
		if(null!= instancia){
			return invoke(instancia, provider, services.getConnectionManager(), wrapper, type, services.getArquitecturalActions(), node);
		}else {
			throw new IllegalStateException(I18N.FWO_CALLING_EXCEPTION1);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> int invoke(final IBusinessRuleServiceProvider services, final TransferBeanWrapper wrapper) throws IllegalStateException, UnsuccessfulException, InvokeException{
		
		if(null!= instancia){
			wrapper.put(FWO.PAGINATION_FLAG, IConnectionManager.PAGINATION_NEXT);
			wrapper.put(BUSINESS.DATA_SERVICE_NODE, node);
			
			return invoke(instancia, provider, services, wrapper, type);
			
		} else {
			throw new IllegalStateException(I18N.FWO_CALLING_EXCEPTION1);
		}
	}
	
	public Object getInstancia(){
		return instancia;
	}
}
