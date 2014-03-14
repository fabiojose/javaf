package bradesco.tf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javaf.ObjectPool;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.UtilObject;
import com.javaf.javase.lang.UtilString;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;
import com.javaf.model.Pesquisa;
import com.javaf.model.IDynamic;
import com.javaf.pattern.ICommand;

import br.com.bradesco.core.aq.dataservice.IContainerObject;
import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.exceptions.EventException;
import br.com.bradesco.core.aq.service.IEvent;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import br.com.bradesco.core.ui.beans.basedialog.BradescoDialog;
import bradesco.tf.TFConstants.IDENTIFY;
import bradesco.tf.annotation.Model;
import bradesco.tf.builder.TFBuilder;
import bradesco.tf.dsn.SerializerMediator;
import bradesco.tf.dsn.UtilDSN;
import bradesco.tf.dsn.UtilPersistence;
import bradesco.tf.layout.TFLayoutMediators;
import bradesco.tf.layout.UtilLayout;
import bradesco.tf.mediator.EventMediator;
import bradesco.tf.model.IHelper;
import bradesco.tf.validate.IValidatorMediator;
import bradesco.tf.validate.Pesquisar;
import bradesco.tf.visitor.TFVisitors;

/**
 * Evento base para implementação no Terminal Financeiro.
 * @author fabiojm - Fábio José de Moraes
 */
@SuppressWarnings("deprecation")
public abstract class AbstractEvent implements IEvent {

	final protected UtilLayout           layout = UtilLayout.getInstance();
	final protected UtilIdentify         identify = UtilIdentify.getInstance();
	final protected TerminalFinanceiro   arquitetura = TerminalFinanceiro.getInstance();
	final protected UtilDSN              dsn = UtilDSN.getInstance();
	final protected LayoutHelper         helper = new LayoutHelper();
	final protected UtilPersistence      serializer = UtilPersistence.getInstance();
	final protected UtilFormat           format = UtilFormat.getInstance();
	final protected UtilString           string = UtilString.getInstance();
	final protected UtilDynamic          dynamic = UtilDynamic.getInstance();
	final protected UtilBusiness         business = UtilBusiness.getInstance();
	final protected UtilObject           object = UtilObject.getInstance();
	final protected TFVisitors           visitor  = TFVisitors.getInstance();
	final protected TFLayoutMediators    layoutm = TFLayoutMediators.getInstance();
	final protected UtilReflection       reflection = UtilReflection.getInstance();
	final protected TFObjectFactory      factory = TFObjectFactory.getInstance();
	final protected ShowMessage          show = ShowMessage.getInstance();
	final protected SendMessage          send = SendMessage.getInstance();
	final protected TFBuilder            builder = TFBuilder.getInstance();
	final protected EventMediator        mediator = EventMediator.getInstance();
	
	/**
	 * Para obter a instância desse atributo utilize o método getLocalize.<br/>
	 * Nunca utilize diretamento o atributo, pois somente o método garantirá que haverá uma instância.
	 * @see #getLocalize(IEventsServiceProvider)
	 */
	private ILocalization localize;
	private ILogging logging;
	
	public AbstractEvent(){
		
	}
	
	/**
	 * Obtem a implementação de ILocalization para localização de strings.
	 * @param provider
	 * @return Instância obtida da fábrica de objetos padrão.
	 */
	public ILocalization getLocalize(){
		if(null== localize){
			localize = Localization.getInstance();
		}
		
		return localize;
	}
	
	public ILogging getLogging(){
		if(null== logging){
			logging = Logging.loggerOf(getClass());
		}
		
		return logging;
	}

	/**
	 * Obtem a instância de trabalho corrente da operação em curso.<br/>
	 * Caso essa instância ainda não exista será consultada a classe configurada em <code>Application.xml</code>, instanciada e armazenada.
	 * @param <T> Tipo que estende à IDynamic para casting automático via generics
	 * @param provider Provedor de serviços
	 * @param operation Operação em curso
	 * @return Instância de trabalho corrente da operação em curso
	 * @see #getInstanciaPersistence(IEventsServiceProvider, IOperation)
	 * @throws EventException
	 */
	@SuppressWarnings("unchecked")
	public final <T extends IDynamic> T getInstancia(final IEventsServiceProvider provider, final IOperation operation) throws EventException {
		
		T _result = null;
		
		//obter instancia no pool
		_result = (T)getObjectPool(provider).get(identify.doInstancia(operation));
		
		//caso não existar, parte para instânciar
		if(null== _result){
			
			Class<? extends Serializable> _cmodel = null;
			try{
				//obter no XML
				_cmodel = helper.getModel(provider, operation);
				
			}catch(ResourceException _e){
				
				//obter por annotation
				final Model _ann = reflection.annotationOf(this.getClass(), Model.class);
				if(null!= _ann){
					
					_cmodel = _ann.value();
					
				} else {
					throw new EventException(getLocalize().localize(I18N.ANOTADO_OU_EXTENDER, I18N.EVENT, Model.class, AbstractEvent.class));
				}
			}
			
			//instanciar e armazenar no pool
			_result = (T)getObjectPool(provider).getOrCreate(identify.doInstancia(operation), _cmodel);
		}
		
		return _result;
	}
	
	/**
	 * Configura uma nova instância de trabalho corrente para a operação em curso.<br/>
	 * Sempre que invocado, esse método criará uma nova instância da classe configurada em <code>Application.xml</code> que será configurada como corrente.<br/>
	 * A chamada à esse método apenas cria uma nova instância da classe de trabalho mas não atualiza a persistência.
	 * @param <T> Tipo que estende à IDynamic para casting automático via generics
	 * @param provider Provedor de serviços
	 * @param operation Operação em curso
	 * @return Instância de trabalho corrente da operação em curso
	 * @see #getInstancia(IEventsServiceProvider, IOperation)
	 * @see #instanciaToView(IEventsServiceProvider, IOperation, IDataServiceNode) Para atualizar a persistência.
	 * @throws EventException
	 */
	@SuppressWarnings("unchecked")
	public final <T extends IDynamic> T newInstancia(final IEventsServiceProvider provider, final IOperation operation) throws EventException {
		
		T _result = null;
		final T _old = getInstancia(provider, operation);
		
		_result = (T)reflection.newInstance(_old.getClass());
		_result.set(IDENTIFY.PESSOA, _old.get(IDENTIFY.PESSOA));
		
		setInstancia(_result, provider, operation);
		
		return _result;
	}
	
	/**
	 * Obtem a instância corrente do helper na operação em curso.
	 * @param <T> Tipo que estende à IHelper para casting automático via generics
	 * @param provider Provedor de serviços
	 * @param operation Operação em curso
	 * @return Instância do helper na operação em curso.
	 * @see #getHelperPersistence(IEventsServiceProvider, IOperation)
	 * @throws EventException
	 */
	@SuppressWarnings("unchecked")
	public final <T extends IHelper> T getHelper(final IEventsServiceProvider provider, final IOperation operation) throws EventException {
		
		Object _result = null;
		
		_result = getObjectPool(provider).get(identify.doHelper(operation));
		
		if(null== _result){
			Class<?> _chelper = null;
			try{
				//obter configuracao no XML
				_chelper = helper.getHelper(provider, operation);
				
			}catch(ResourceException _e){
				throw new EventException(_e.getMessage());
			}
			
			//o helper é opcional
			if(null!= _chelper){
				_result = getObjectPool(provider).getOrCreate(identify.doHelper(operation), _chelper);
			}
		}
		
		return (T)_result;
	}
	
	/**
	 * Em casos em que houver a necessidade de configurar programaticamente a instância de trabalho corrente para a operação em curso, utilize esse método.<br/>
	 * A chamada desse método somente sobrescreve a instância corrente mas não atualiza a persistência.
	 * @param <T> Tipo que estende à IDynamic para casting automático via generics.
	 * @param instancia Nova instância de trabalho que será configurada como corrente.
	 * @param provider Provedor de serviços
	 * @param operation Operação em curso
	 * @see #instanciaToView(IEventsServiceProvider, IOperation, IDataServiceNode) Para atualizar a persistência.
	 */
	public final <T extends IDynamic> void setInstancia(final T instancia, final IEventsServiceProvider provider, final IOperation operation){
		getObjectPool(provider).put(identify.doInstancia(operation), instancia);
	}
	
	/**
	 * Obtem a instância corrente de pesquisa para a operação em curso.<br/>
	 * Toda vez que for instância a pesquisa, automaticamente o tipo dos resultados serão instâncias do tipo da classe configurada como base da operação.
	 * @param <T> Tipo genérico da lista de resultados na pesquisa.
	 * @param <F> Tipo genérico do filtro na pesquisa.
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @see #getInstanciaPersistence(IEventsServiceProvider, IOperation)
	 * @see #pesquisarToView(IEventsServiceProvider, IOperation, IDataServiceNode, boolean)
	 * @return Instância de {@link Pesquisa} cujo filtro é configurado em <code>Application.xml</code>
	 */
	@SuppressWarnings("unchecked")
	public final <T extends IDynamic, F extends IDynamic> Pesquisa<T, F> getPesquisar(final IEventsServiceProvider provider, final IOperation operation){
		
		final ObjectPool _opool = getObjectPool(provider);
		Pesquisa<T, F> _result = _opool.get(identify.doPesquisar(operation), Pesquisa.class);
		
		if(null== _result){
			_result = _opool.getOrCreate(identify.doPesquisar(operation), Pesquisa.class);
		}
		
		if(null== _result.getFiltro()){
			
			final Class<? extends IDynamic> _fclazz = helper.getFiltro(provider, operation);
			final F _filtro = (F)reflection.newInstance(_fclazz);
			
			_result.setFiltro(_filtro);
		}
		
		if(null== _result.get(IDENTIFY.PESQUISAR, Pesquisar.class)){
			_result.set(IDENTIFY.PESQUISAR, helper.getPesquisar(provider, operation));
		}
		
		return _result;
	}
	
	/**
	 * Obtem a instância de ObjectPool no escopo da sessão do usuário.
	 * @param provider
	 * @return Instância obtida ou instanciada e armazenada na sessão do usuário.
	 */
	public final ObjectPool getObjectPool(final IEventsServiceProvider provider){
		return arquitetura.getObjectPool(provider);
	}
	
	/**
	 * Obtem a instância de ObjectPool no escopo da operação em curso.
	 * @param provider
	 * @param operation
	 * @return Instância obtida ou instanciada e armazenada temporariamente no ObjectPool da sessão.
	 * @see #getObjectPool(IEventsServiceProvider)
	 */
	public final ObjectPool getLocalPool(final IEventsServiceProvider provider, final IOperation operation){
		
		ObjectPool _result = null;
		final ObjectPool _opool = getObjectPool(provider);
		_result = _opool.getOrCreate(identify.doLocalPool(operation), ObjectPool.class);
		
		return _result;
	}
	
	/**
	 * Obtem a instância do mediador de persistência para a instância de trabalho corrente na operação em curso.
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @see #instanciaToView(IEventsServiceProvider, IOperation, IDataServiceNode)
	 * @return Instância do mediador de persistência para ler ou gravar dados, ou <code>null</code> caso não exista persistência.
	 */
	public final SerializerMediator getInstanciaPersistence(final IEventsServiceProvider provider, final IOperation operation){
		
		final ObjectPool _opool = arquitetura.getObjectPool(provider);
		return _opool.get(getInstanciaPrefixo(provider, operation), SerializerMediator.class);
	}
	
	/**
	 * Obtem a instância do mediador de persistência para a perquisa corrente na operação em curso.<br/>
	 * Caso o método {@link #instanciaToView(IEventsServiceProvider, IOperation, IDataServiceNode)} não tenha sido invocado 
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @see #pesquisarToView(IEventsServiceProvider, IOperation, IDataServiceNode, boolean)
	 * @return Instância do mediador de persisência para ler ou gravar dados, ou <code>null</code> caso não exista persistência.
	 */
	public final SerializerMediator getPesquisarPersistence(final IEventsServiceProvider provider, final IOperation operation){
		
		final ObjectPool _opool = arquitetura.getObjectPool(provider);
		return _opool.get(getPesquisarPrefixo(provider, operation), SerializerMediator.class);
	}
	
	/**
	 * Obtem a instância do mediador de persistência do helper na operação em curso.<br/>
	 * Caso o método {@link #helperToView(IEventsServiceProvider, IOperation, IDataServiceNode))} não tenha sido invocado 
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @see #helperToView(IEventsServiceProvider, IOperation, IDataServiceNode)
	 * @return Instância do mediador de persisência para ler ou gravar dados, ou <code>null</code> caso não exista persistência.
	 */
	public final SerializerMediator getHelperPersistence(final IEventsServiceProvider provider, final IOperation operation){
		
		final ObjectPool _opool = arquitetura.getObjectPool(provider);
		return _opool.get(getHelperPrefixo(provider, operation), SerializerMediator.class);
	}
	
	/*public final InstanceOrchestrator getOrchestrator(final IEventsServiceProvider provider){
		return getObjectPool(provider).getOrCreate(identify.doInstanceOrchestrator(), InstanceOrchestrator.class);
	}*/
	
	/**
	 * Obtem o validador configurado para um determinado layout da operação em curso.
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @param node Serviço de dados.
	 * @param layout Classe do layout
	 * @return Instância do validador ou uma instância de {@link NullValidatorMediator}
	 */
	public final IValidatorMediator getValidator(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final Class<? extends BradescoDialog> layout){
		
		final IValidatorMediator _result = helper.doValidator(provider, operation, node, layout);
		
		return _result;
	}
	
	/**
	 * Obtem o validador configurado para a operação em curso, independente de layout.
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @param node Serviços de dados.
	 * @return Instância do validador ou uma instância de {@link NullValidatorMediator}
	 */
	public final IValidatorMediator getValidator(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		
		final IValidatorMediator _result = helper.doValidator(provider, operation, node, getLayout(provider, operation));
		
		return _result;
	}
	
	public final IValidatorMediator getEventValidator(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		
		final IValidatorMediator _result = helper.doValidator(provider, operation, node, this);
		
		return _result;
	}
	
	public final Class<? extends BradescoDialog> getLayout(final IEventsServiceProvider provider, final IOperation operation){
		
		return helper.getView(provider, operation, this);
	}
	
	public final String getInstanciaPrefixo(final IEventsServiceProvider provider, final IOperation operation){
		return getObjectPool(provider).get(identify.doPrefixo(operation), String.class);
	}
	
	public final IContainerObject getInstanciaContainer(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		return dsn.getOrAddContainerObject(getInstanciaPrefixo(provider, operation), node);
	}
	
	public final IContainerObject getHelperContainer(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		return dsn.getOrAddContainerObject(IDENTIFY.HELPER, getInstanciaContainer(provider, operation, node));
	}
	
	public final IContainerObject addContainerOnHelper(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final String id, final Object value){
		return dsn.toContainerObject(getHelperContainer(provider, operation, node), value, id, Boolean.TRUE, getHelperPrefixo(provider, operation), StorageType.CONTROL);
	}
	
	public final String getHelperPrefixo(final IEventsServiceProvider provider, final IOperation operation){
		return getInstanciaPrefixo(provider, operation) + IDENTIFY.KEY_HELPER;
	}

	public final String prefixar(final IEventsServiceProvider provider, final IOperation operation, final String...posteriores){
		
		final StringBuilder _result = new StringBuilder( getInstanciaPrefixo(provider, operation) );
		
		for(String _p : posteriores){
			_result.append(STRING.DOT).append(_p);
		}
		
		return _result.toString();
		
	}
	
	public final String getPesquisarPrefixo(final IEventsServiceProvider provider, final IOperation operation){
		return getObjectPool(provider).get(identify.doPrefixoPesquisar(operation), String.class);
	}
	
	/**
	 * Escreve toda a estrutura da instância de trabalho corrente no DSN, bem como atualiza a persistência.
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @param node Serviço de dados.
	 * @see #getInstancia(IEventsServiceProvider, IOperation)
	 * @see #getInstanciaPersistence(IEventsServiceProvider, IOperation)
	 */
	public final void instanciaToView(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		instanciaToView(provider, operation, node, Boolean.TRUE);
	}
	
	private final void instanciaToView(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final boolean doPersistence){
		dsn.toContainerObject(node, getInstancia(provider, operation), getInstanciaPrefixo(provider, operation), doPersistence, StorageType.CONTROL);
	}
	
	/**
	 * Escreve toda a estrutura do helper no DSN, bem como atualiza a persistência.
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @param node Serviço de dados.
	 */
	public final void helperToView(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node) {
		
		final Object _helper = getHelper(provider, operation);
		if(null!= _helper){
			final IContainerObject _container = getInstanciaContainer(provider, operation, node);
			
			dsn.toContainerObject(_container, _helper, IDENTIFY.HELPER, Boolean.TRUE, getHelperPrefixo(provider, operation), StorageType.CONTROL);
		}
	}
	
	/**
	 * Escreve toda a estrutura da pesquisa no DSN, bem como pode condicionalmente atualizar a persistência.
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @param node Serviços de dados.
	 * @param doPersistence <code>Boolean.TRUE</code> para atualizar a persistência.
	 */
	public final void pesquisarToView(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final boolean doPersistence){
		dsn.toContainerObject(node, getPesquisar(provider, operation), getPesquisarPrefixo(provider, operation), doPersistence, StorageType.CONTROL);
	}
	
	@SuppressWarnings("unchecked")
	public final Class<? extends IEvent> toExecute(final IEventsServiceProvider provider, final IOperation operation){
		Class<? extends IEvent> _result = null;
		
		final ObjectPool _opool = getObjectPool(provider);
		final Map<Class<? extends IEvent>, Class<? extends IEvent>> _replacers = _opool.getOrCreate(identify.doEventReplacer(operation), HashMap.class);
		
		//obter event replacer
		_result = _replacers.get(getClass());
		
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	public final void doReplacer(final Class<? extends IEvent> original, final Class<? extends IEvent> replacer, final IEventsServiceProvider provider, final IOperation operation){
		final ObjectPool _opool = getObjectPool(provider);
		final Map<Class<? extends IEvent>, Class<? extends IEvent>> _replacers = _opool.getOrCreate(identify.doEventReplacer(operation), HashMap.class);
		
		_replacers.put(original, replacer);
	}
	
	@SuppressWarnings("unchecked")
	public final void unReplacer(final Class<? extends IEvent> original, final IEventsServiceProvider provider, final IOperation operation){
		final ObjectPool _opool = getObjectPool(provider);
		final Map<Class<? extends IEvent>, Class<? extends IEvent>> _replacers = _opool.getOrCreate(identify.doEventReplacer(operation), HashMap.class);
		
		_replacers.remove(original);
	}
	
	@SuppressWarnings("unchecked")
	public final void doOnStart(final Class<? extends IEvent> original, final Class<? extends IEvent> onStart, final IEventsServiceProvider provider, final IOperation operation){
		final ObjectPool _opool = getObjectPool(provider);
		final Map<Class<? extends IEvent>, List<Class<? extends IEvent>>> _onStart = _opool.getOrCreate(identify.doOnEventStart(operation), HashMap.class);
		
		List<Class<? extends IEvent>> _events = _onStart.get(original);
		if(null== _events){
			_events = new ArrayList<Class<? extends IEvent>>();
			_onStart.put(original, _events);
		}
		
		if(!_events.contains(onStart)){
			_events.add(onStart);
		}
	}
	
	@SuppressWarnings("unchecked")
	public final void unOnStart(final Class<? extends IEvent> original, final Class<? extends IEvent> onStart, final IEventsServiceProvider provider, final IOperation operation){
		final ObjectPool _opool = getObjectPool(provider);
		final Map<Class<? extends IEvent>, List<Class<? extends IEvent>>> _onStart = _opool.getOrCreate(identify.doOnEventStart(operation), HashMap.class);
		
		List<Class<? extends IEvent>> _events = _onStart.get(original);
		if(null!= _events){
			if(_events.contains(onStart)){
				_events.remove(onStart);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public final void unOnStart(final Class<? extends IEvent> original, final IEventsServiceProvider provider, final IOperation operation){
		final ObjectPool _opool = getObjectPool(provider);
		final Map<Class<? extends IEvent>, List<Class<? extends IEvent>>> _onStart = _opool.getOrCreate(identify.doOnEventStart(operation), HashMap.class);
		
		_onStart.remove(original);
	}
	
	@SuppressWarnings("unchecked")
	public final void doOnFinish(final Class<? extends IEvent> original, final Class<? extends IEvent> onFinish, final IEventsServiceProvider provider, final IOperation operation){
		final ObjectPool _opool = getObjectPool(provider);
		final Map<Class<? extends IEvent>, List<Class<? extends IEvent>>> _onFinish = _opool.getOrCreate(identify.doOnEventFinish(operation), HashMap.class);
		
		List<Class<? extends IEvent>> _events = _onFinish.get(original);
		if(null== _events){
			_events = new ArrayList<Class<? extends IEvent>>();
			_onFinish.put(original, _events);
		}
		
		if(!_events.contains(onFinish)){
			_events.add(onFinish);
		}
	}
	
	@SuppressWarnings("unchecked")
	public final void doOnFinish(final Class<? extends IEvent> original, final ICommand onFinish, final IEventsServiceProvider provider, final OPERATION operation){
		final ObjectPool _opool = getObjectPool(provider);
		final Map<Class<? extends IEvent>, List<ICommand>> _onFinish = _opool.getOrCreate(identify.doOnEventFinishCommand(operation), HashMap.class);
		
		List<ICommand> _commands = _onFinish.get(original);
		if(null== _commands){
			_commands = new ArrayList<ICommand>();
			_onFinish.put(original, _commands);
		}
		
		if(!_commands.contains(onFinish)){
			_commands.add(onFinish);
		}
	}
	
	@SuppressWarnings("unchecked")
	public final void unOnFinish(final Class<? extends IEvent> original, final IEventsServiceProvider provider, final IOperation operation){
		final ObjectPool _opool = getObjectPool(provider);
		
		final Map<Class<? extends IEvent>, List<Class<? extends IEvent>>> _onFinish0 = _opool.getOrCreate(identify.doOnEventFinish(operation), HashMap.class);
		_onFinish0.remove(original);
		
		final Map<Class<? extends IEvent>, List<ICommand>> _onFinish1 = _opool.getOrCreate(identify.doOnEventFinishCommand(operation), HashMap.class);
		_onFinish1.remove(original);		
	}
	
	@SuppressWarnings("unchecked")
	public final void onStart(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node) throws EventException{
		final ObjectPool _opool = getObjectPool(provider);
		final Map<Class<? extends IEvent>, List<Class<? extends IEvent>>> _onStart = _opool.get(identify.doOnEventStart(operation), HashMap.class);
		
		if(null!= _onStart){
			final List<Class<? extends IEvent>> _events = _onStart.get(getClass());
			
			if(null!= _events){
				for(Class<? extends IEvent> _event : _events){
					arquitetura.execute(_event, operation, node);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public final void onFinish(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node) throws EventException{
		final ObjectPool _opool = getObjectPool(provider);
		final Map<Class<? extends IEvent>, List<Class<? extends IEvent>>> _onFinish0 = _opool.get(identify.doOnEventFinish(operation), HashMap.class);
		
		if(null!= _onFinish0){
			final List<Class<? extends IEvent>> _events = _onFinish0.get(getClass());
			
			if(null!= _events){
				for(Class<? extends IEvent> _event : _events){
					arquitetura.execute(_event, operation, node);
				}
			}
		}
		
		final Map<Class<? extends IEvent>, List<ICommand>> _onFinish1 = _opool.get(identify.doOnEventFinishCommand(operation), Map.class);
		if(null!= _onFinish1){
			final List<ICommand> _commands = _onFinish1.get(getClass());
			
			if(null!= _commands){
				for(ICommand _command : _commands){
					_command.execute();
				}
			}
		}
	}
	/**
	 * Decide se será executar o evento original ou seu substituto.
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @param node Serviços de dados.
	 * @return String retornada pelo evento executado.
	 * @throws EventException
	 */
	public final String execute(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node) throws EventException{
		String _result = null;
		
		final Class<? extends IEvent> _replacer = toExecute(provider, operation);
		if(null== _replacer){
			//executar o original
			if(this instanceof IEventReplace){
				final IEventReplace _original = (IEventReplace)this;
				_result = _original.original(provider, operation, node);
			}
			
		} else {
			_result = arquitetura.execute(_replacer, operation, node);
		}
		
		return _result;
	}
	
	/**
	 * Configura uma navegação que o fluxo de layouts deverá obedecer.<br/>
	 * Nessa navegação não existe origem (from) somente o destino (to) que após consumido pela classe base é eliminado da fila.
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso
	 * @param node Serviços de dados.
	 * @param to Condicionante de navegação previamente configurado no <code>flow.ume</code>
	 * @see FlowEvtDecidir
	 */
	public final void runTo(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final String to){
		
		if(visitor.getExecuteRunto().visit(getObjectPool(provider))){
			dsn.doRunTo(provider, operation, node, to);
			layout.close(provider, node);
		}
	
	}
	
	/**
	 * Faz o mapeamento do fluxo de navegação que será obedecido em função de uma origem (from) para um destino (to).
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @param node Serviço de dados.
	 * @param from Origem.
	 * @param to Destino.
	 */
	public final void doRunTo(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final OUTCOME from, final String to){
		
		if(visitor.getExecuteRunto().visit(getObjectPool(provider))){
			dsn.doOutcome(provider, operation, node, from, to);
		}
	}
	
	/**
	 * Configura uma navegação que o fluxo de layouts deverá obedecer. Aqui o condicionante é baseado no {@link OUTCOME} previamente configurado pelo usuário em {@link #doRunTo(IEventsServiceProvider, IOperation, IDataServiceNode, OUTCOME, String)}.<br/>
	 * O OUTCOME de fato é a ação, ou seja, a origem (from) da navegação e quando você o configura, você está configurando o destino (to).<br/>
	 * Ex: {@link OUTCOME#CONFIRMAR} tem como destino (to) <code>PESQUISAR</code>, sendo esse valor previamente configurado no <code>flow.ume</code>.
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @param node Serviços de dados.
	 * @param from Origem da navegação.
	 * @see FlowEvtDecidir
	 */
	public final void runTo(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final OUTCOME from){
		
		if(visitor.getExecuteRunto().visit(getObjectPool(provider))){
			final String _runTo = dsn.getOutcome(provider, operation, node, from);
			runTo(provider, operation, node, _runTo);
		}
		
	}
	
	/**
	 * Configura uma navegação que o fluxo de layouts deverá obedecer. Aqui o condicionante é base no {@link OUTCOME} previamente configurado.<br/>
	 * O OUTCOME de fato é a ação, ou seja, a origem (from) da navegação e quando você o configura, está configurando o destino (to).<br/>
	 * Ex: {@link OUTCOME#CONFIRMAR} tem como destino (to) <code>PESQUISAR</code>.<br/>
	 * Se {@link OUTCOME#CONFIRMAR} não possuir destino (to) então o valor do paramêtro <code>onNull</code> será o destino.
	 * @param provider Provedor de serviços.
	 * @param operation Operação em curso.
	 * @param node Serviços de dados.
	 * @param from Origem da navegação.
	 * @param onNull Será aplicado como destino se não houver um outro previamente configurado.
	 * @see FlowEvtDecidir
	 */
	public final void runTo(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final OUTCOME from, final String onNull){
		
		if(visitor.getExecuteRunto().visit(getObjectPool(provider))){
			String _runTo = dsn.getOutcome(provider, operation, node, from);
			if(null== _runTo){
				_runTo = onNull;
			}
			
			runTo(provider, operation, node, _runTo);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public final void doEnabled(final IEventsServiceProvider provider, final Class<? extends BradescoDialog> layout, final String...componentes){
		
		final ObjectPool _opool = getObjectPool(provider);
		final String _id = identify.doID(layout, IDENTIFY.KEY_TO_ENABLE); 
		final List<String> _componentes = _opool.getOrCreate(_id, ArrayList.class);
		
		if(null!= componentes 
				&& componentes.length > INTEGER._0){
			_componentes.addAll(Arrays.asList(componentes));
		}
	}
	
	@SuppressWarnings("unchecked")
	public final void enabled(final IEventsServiceProvider provider, final IDataServiceNode node, final Class<? extends BradescoDialog> layout){
		
		final ObjectPool _opool = getObjectPool(provider);
		final String _id = identify.doID(layout, IDENTIFY.KEY_TO_ENABLE); 
		final List<String> _componentes = _opool.get(_id, ArrayList.class);
		
		if(null!= _componentes){
			for(String _componente : _componentes){
				this.layout.setEnabled(node, provider, Boolean.TRUE, _componente);
			}
		}
		
		_opool.remove(_id);
	}
	
	public final void doExecute(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final Class<? extends IEvent> event, final boolean execute){
		
		final ObjectPool _opool = getObjectPool(provider);
		final String _id        = identify.doID(event, IDENTIFY.KEY_EXECUTE);
		
		_opool.put(_id, execute);
	}
	
	public final boolean isExecute(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final Class<? extends IEvent> event){
		boolean _result = Boolean.TRUE;
		
		final ObjectPool _opool = getObjectPool(provider);
		final String _id        = identify.doID(event, IDENTIFY.KEY_EXECUTE);
		
		_result = (Boolean)_opool.remove(_id, Boolean.TRUE);
		
		return _result;
	}
	
	public abstract String event(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node) throws EventException;
}
