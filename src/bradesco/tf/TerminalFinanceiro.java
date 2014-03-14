package bradesco.tf;

import java.text.MessageFormat;
import java.util.Properties;
import java.util.Set;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.exceptions.EventException;
import br.com.bradesco.core.aq.exceptions.RollbackException;
import br.com.bradesco.core.aq.service.IArquitecturalActions;
import br.com.bradesco.core.aq.service.IBusinessRuleServiceProvider;
import br.com.bradesco.core.aq.service.IEvent;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import br.com.bradesco.core.aq.service.IServiceProvider;
import br.com.bradesco.core.aq.service.IUserSession;
import br.com.bradesco.core.serviceImpl.ServiceProvider;
import bradesco.tf.TFConstants.AMBIENTE;
import bradesco.tf.TFConstants.BUSINESS;
import bradesco.tf.TFConstants.SESSION;
import bradesco.tf.visitor.PropertiesPlaceholderVisitor;

import com.javaf.ObjectPool;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.REGEX;
import com.javaf.Constants.STRING;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.IProperties;
import com.javaf.javase.util.Localization;
import com.javaf.model.Ambiente;
import com.javaf.model.Attribute;
import com.javaf.pattern.Visitor;

/**
 * Implementa métodos utilitários para acesso aos recursos programáticos do Terminal Financeiro.
 * @author fabiojm
 *
 */
@SuppressWarnings("deprecation")
public final class TerminalFinanceiro {
	private static final TerminalFinanceiro INSTANCE = new TerminalFinanceiro();
	private static final IProperties PROPERTIES      = com.javaf.Properties.getInstance();
	private static Boolean DEVELOPMENT = null;
	
	private final UtilIdentify identify;
	private final TFObjectFactory factory;
	private final TFApplication application;
	private final ILocalization localization;
	
	private Ambiente ambiente;
	
	private TerminalFinanceiro(){
		identify     = UtilIdentify.getInstance();
		factory      = TFObjectFactory.getInstance();
		application  = TFApplication.getInstance();
		localization = Localization.getInstance();
	}
	
	static{
		//armazenar valores de PRJ_FIRP.properties que possuem prefixo "system."
		final Set<Object> _keys = PROPERTIES.keySet();
		for(Object _key : _keys){
			final String _string = _key.toString();
			if(_string.startsWith(AMBIENTE.SYSTEM)){
				
				//armazenar em System
				System.setProperty(_string, PROPERTIES.getProperty(_string));
			}
		}
	}
	
	public static final void doConfigure(){
		
	}
	
	/**
	 * Obter uma instância da trabalho
	 */
	public static synchronized TerminalFinanceiro getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Obter provedor de serviços.
	 * @return
	 */
	public final IServiceProvider getProvider(){
		return ServiceProvider.getInstance();
	}
	
	/**
	 * Verifica o tempo da aplicação é o tempo de desenvolvimento.
	 * @return <code>true</code> Está configurado como tempo de desenvolvimento.
	 */
	public static boolean isDevelopment(){
		if(null== DEVELOPMENT){
			final String _runtime = PROPERTIES.getProperty(AMBIENTE.RUNTIME);
			
			DEVELOPMENT = TFConstants.AMBIENTE.DEVELOPMENT_TIME.equals(_runtime);
		}
		return DEVELOPMENT;
	}
	
	/**
	 * Verifica o tempo da aplicação é o tempo de implantanção.
	 * @return <code>true</code> Está configurado como tempo de implantação.
	 */
	public static boolean isDeployment(){
		return !isDevelopment();
	}
		
	public Ambiente getAmbiente(){
		if(null== ambiente){
			ambiente = new Ambiente();
			ambiente.setCodigo(System.getProperty(AMBIENTE.SYSTEM_PROPERTY));

			final Set<Object> _keys      = PROPERTIES.keySet();
			final String _ambiente       = ambiente.getCodigo();
			for(Object _key : _keys){
				if(_key.toString().startsWith(_ambiente)){
					final String __key = _key.toString().substring(_ambiente.length() + INTEGER._1);
					
					ambiente.getProperties().put(__key, PROPERTIES.getProperty(_key.toString()));
				}
			}
			
			//processar placeholders
			final Visitor<Properties, Properties> _visitor = new PropertiesPlaceholderVisitor();
			_visitor.visit(ambiente.getProperties());

		}
		
		return ambiente;
	}

	/**
	 * Retomar uma mensagem localizada na arquitetura.<br/>
	 * Esse método deve ser utilizado nas classes de regra de negócio.
	 * 
	 * @param id Identificador da mensagem
	 * @param provider Provedor de serviços
	 * @return Texto localizado
	 */
	/*public String getApplText(final int id, final IBusinessRuleServiceProvider provider) {
		return provider.getLocaleManager().getApplText(ProjectConstants.PROJETO, String.valueOf(id));
	}*/

	/**
	 * Retomar uma mensagem localizada na arquitetura e formatada.<br/>
	 * Esse método deve ser utilizado nas classes de evento de operação.
	 * 
	 * @param id Identificador da mensagem
	 * @param provider Provedor de serviços
	 * @param arguments Argumentos para aplicar na formatação
	 * @return Texto localizado e formatado
	 * @see MessageFormat
	 */
	/*public String getApplText(final int id, final IEventsServiceProvider provider, Object... arguments) {
		return MessageFormat.format(getApplText(id, provider), arguments);
	}*/

	/**
	 * Retomar uma mensagem localizada na arquitetura e formatada.<br/>
	 * Esse método deve ser utilizado nas classes de regra de negócio.
	 * 
	 * @param id Identificador da mensagem
	 * @param provider Provedor de serviços
	 * @param arguments Argumentos para aplicar na formatação
	 * @return Texto localizado e formatado
	 * @see MessageFormat
	 */
	/*public String getApplText(final int id, final IBusinessRuleServiceProvider provider, Object... arguments) {
		return MessageFormat.format(getApplText(id, provider), arguments);
	}*/
	
	public String getApplText(final String i18n, final IEventsServiceProvider provider){
		
		String _result = STRING.EMPTY;
		if(null!= i18n && i18n.matches(REGEX.I18N_TEXT)){
			final String _key  = i18n.substring(INTEGER._0, i18n.indexOf(STRING.ASTERISCO2));
			final String _text = localization.localize(Integer.parseInt(_key));
			if(!_text.equals(_key)){
				_result = _text;
				
			} else {
				_result = i18n.substring(i18n.indexOf(STRING.ASTERISCO2) + STRING.ASTERISCO2.length());
			}
		}
		
		return _result;
	}	
	
	/**
	 * Obtem a sessão do usuário.
	 * 
	 * @param provider Provedor de Serviços
	 * @return Sessão do usuário gerenciada pela Arquitetura
	 */
	public IUserSession getSession(final IEventsServiceProvider provider){
		return provider.getSessionManager().getUserSession();
	}
	
	/**
	 * Obtem a sessão do usuário.
	 * 
	 * @param provider Provedor de Serviços
	 * @return Sessão do usuário gerenciada pela Arquitetura
	 */
	public IUserSession getSession(final IBusinessRuleServiceProvider provider){
		return provider.getSessionManager().getUserSession();
	}
	
	/**
	 * Armazena atributos na sessão do usuário.
	 * @param <T> Tipo armazenado do atributo
	 * @param name Nome do atributo
	 * @param provider Provedor de Serviços
	 * @param value Instância do atributo
	 * @see SESSION
	 */
	public <T> void setSessionAttribute(final Attribute<T> name, final IEventsServiceProvider provider, final T value){
	    setSessionAttribute(name, getSession(provider), value);
	}
	
	/**
	 * Armazena atributos na sessão do usuário.
	 * 
	 * @param <T> Tipo armazenado do atributo
	 * @param name Nome do atributo
	 * @param session Sessão do usuário
	 * @param value Instância do atributo
	 * @see SESSION
	 */
	public <T> void setSessionAttribute(final Attribute<T> name, final IUserSession session, final T value){
		session.setAttribute(name.getKey(), value);
	}
	
	/**
	 * Obtem atributos na sessão do usuário.
	 * 
	 * @param <T> Tipo armazenado do atributo
	 * @param name Nome do atributo
	 * @param provider Provedor de Serviços
	 * @return <code>null</code> caso o atributo não seja encontrado
	 */
	
	public <T> T getSessionAttribute(final Attribute<T> name, final IEventsServiceProvider provider){
		return getSessionAttribute(name, getSession(provider));
	}
	
	/**
	 * Obtem atributos na sessão do usuário.
	 * 
	 * @param <T> Tipo armazenado do atributo
	 * @param name Nome do atributo
	 * @param provider Provedor de Serviços
	 * @return <code>null</code> caso o atributo não seja encontrado
	 */
	
	public <T> T getSessionAttribute(final Attribute<T> name, final IBusinessRuleServiceProvider provider){
		return getSessionAttribute(name, getSession(provider));
	}
	
	/**
	 * Obtem atributos na sessão do usuário.
	 * 
	 * @param <T> Tipo armazenado do atributo
	 * @param name Nome do atributo
	 * @param session Sessão do usuário
	 * @return <code>null</code> caso o atributo não seja encontrado
	 */
	@SuppressWarnings("unchecked")
	public <T> T getSessionAttribute(final Attribute<T> name, final IUserSession session){
		
		return (T)session.getAttribute(name.getKey());
	}
	
	/**
	 * Remove e retorna o valor de atributo armazenado na sessão do usuário
	 * @param <T> Tipo armazenado do atributo
	 * @param name Nome do atributo
	 * @param provider Provedor de Serviços
	 * @return <code>null</code> caso o atributo não seja encontrado
	 */
	public <T> T removeSessionAttribute(final Attribute<T> name, final IEventsServiceProvider provider){
		
		final T _result = getSessionAttribute(name, provider);
		setSessionAttribute(name, provider, null);
		
		return _result;
	}
	
	/**
	 * Obter nome do usuário, exatamente como foi digitado no login
	 * @param provider Provedor de serviços
	 * @return Nome do usuário
	 */
	public String getUserName(final IEventsServiceProvider provider){
		return provider.getSecurityManager().getUserInfo().getUserName();
	}
	
	/**
	 * Obter nome do usuário, exatamente como foi digitado no login
	 * @param provider Provedor de serviços
	 * @return Nome do usuário
	 */
	public String getUserName(final IBusinessRuleServiceProvider provider){
		return provider.getSecurityManager().getUserInfo().getUserName();
	}
	
	/**
	 * Obtem o pool de objetos que está armazenado na sessão do usuário
	 * @param provider Provedor de serviços
	 * @return Instância de ObjectPool
	 */
	public ObjectPool getObjectPool(final IEventsServiceProvider provider){
		return getSessionAttribute(SESSION.OBJECT_POOL, provider);
	}
	
	/**
	 * Obtem o pool de objetos que está armazenado na sessão do usuário
	 * @param provider Provedor de serviços
	 * @return Instância de ObjectPool
	 */
	public ObjectPool getObjectPool(final IBusinessRuleServiceProvider provider){
		return getSessionAttribute(SESSION.OBJECT_POOL, provider);
	}
	
	/*public boolean doResource(final ResourceType tipo, final String nome, final Object receiver, final IArquitecturalActions actions, final IDataServiceNode node) {
		
		boolean _result = Boolean.TRUE;
		
		boolean _continue = Boolean.TRUE;
		if(ResourceType.FWO.equals(tipo)){
			final IDataServiceNode _node = (IDataServiceNode)receiver;
			_continue = null== _node.getContainerObject(nome);
		}
		
		if(_continue){
			final TransferBeanWrapper _wrapper = new TransferBeanWrapper();
			_wrapper.put(RESOURCE.REQUESTING       , tipo);
			_wrapper.put(RESOURCE.RESPONSE_RECEIVER, receiver);
			_wrapper.put(RESOURCE.NAME             , nome);
			
			LayerMediator.getInstance().getObjectPool().put(RESOURCE.RESOURCE_REQUEST, _wrapper);
			
			_result = executeOperation(OPERATION.Application, actions, node);
		} 
		
		return _result;
	}
	
	public boolean doResource(final ResourceType tipo, final String nome, final Object receiver, final IArquitecturalActions actions, final TransferBeanWrapper wrapper) throws ResourceException {
		
		final IDataServiceNode _node = wrapper.get(BUSINESS.DATA_SERVICE_NODE, IDataServiceNode.class);
		if(null!= _node){
			return doResource(tipo, nome, receiver, actions, _node);
		} else {
			throw new ResourceException(BUSINESS.DATA_SERVICE_NODE);
		}
	}
	
	public boolean doResource(final ResourceType tipo, final String nome, final Object receiver, final IEventsServiceProvider provider, final IDataServiceNode node) {
		return doResource(tipo, nome, receiver, provider.getArquitecturalActions(), node);
	}*/
	
	/**
	 * Determina se a operação já está rodando, com isso pode-se evitar duas ou mais intâncias quando conveniente.
	 * @param operation
	 * @return
	 */
	public boolean isRunning(final IOperation operation){
		
		final ObjectPool _opool = LayerMediator.getInstance().getObjectPool();
		boolean _result = Boolean.FALSE;
		if(_opool.get(identify.doOperationRunning(operation)) instanceof Boolean){
			_result = _opool.get(identify.doOperationRunning(operation), Boolean.class);
		}
		
		return _result;
	}

	public OPERATION operationOf(final IOperation operation){
		OPERATION _result = null;
		
		if(null!= operation){
			_result = application.operationOf(operation.getProjectOperationBean().getOperationAlias());
		}
		
		return _result;
	}
	
	/**
	 * Executa eventos do projeto
	 * @param event Classe do evento
	 * @param operation 
	 * @param node
	 * @return
	 */
	public String execute(final Class<? extends IEvent> event, final IOperation operation, final IDataServiceNode node){
		return operation.executeEvent(event.getSimpleName(), node);
	}
	
	/**
	 * Executa operações do projeto
	 * @param operation
	 * @param provider
	 * @param node
	 * @return
	 * @see OPERATION
	 */
	public boolean execute(final OPERATION operation, final IEventsServiceProvider provider, final IDataServiceNode node){
		return execute(operation, provider, node, Boolean.TRUE);
	}
	
	public boolean execute(final OPERATION operation, final IEventsServiceProvider provider, final IDataServiceNode node, final boolean sincrono){
		return provider.getArquitecturalActions().executeOperation(node, operation.getNome(), sincrono);
	}
	
	public boolean execute(final OPERATION operation, final IArquitecturalActions actions, final IDataServiceNode node){
		return actions.executeOperation(node, operation.getNome(), Boolean.TRUE);
	}
	
	/**
	 * Invoca a uma regra de negócio específica.<br/>
	 * Internamente será criado uma instância de BeanWrapper com a estrutura base para o contexto de negócios.
	 * 
	 * @param business Classe da regra de negócio
	 * @param provider Provedor de serviços
	 * @param node Provedor de dados
	 * @param operation Provedor de operações
	 * @return BeanWrapper básico que foi passado para a regra de negócio<br/>
	 * 			Na chave <code>BUSINESS.RETURNED</code> residirá o valor retornado pela chamada à regra de negócio.
	 * @throws EventException
	 * @throws RollbackException
	 * @see #doBusinessBeanWrapper(IEventsServiceProvider, IDataServiceNode)
	 */
	public TransferBeanWrapper execute(final Class<? extends AbstractBusinessComponent> business, final IEventsServiceProvider provider, final IDataServiceNode node, final IOperation operation) throws EventException, RollbackException{
		
		final TransferBeanWrapper _result = factory.doBusinessBeanWrapper(provider, node, operation);
		final String _returned = operation.callBusinessRule(business.getSimpleName(), node, _result);
		
		_result.put(BUSINESS.RETURNED, _returned);
		return _result;
	}
	
	/**
	 * Invoca a uma regra de negócio específica.<br/>
	 * Internamente será criado uma instância de BeanWrapper com a estrutura base para o contexto de negócios.
	 * 
	 * @param business Classe da regra de negócio
	 * @param provider Provedor de serviços
	 * @param node Provedor de dados
	 * @param operation Provedor de operações
	 * @param wrapper Na chave <code>BUSINESS.RETURNED</code> residirás o valor retornado pela chamada à regra de negócio.
	 * @throws EventException
	 * @throws RollbackException
	 */
	public void execute(final Class<? extends AbstractBusinessComponent> business, final IEventsServiceProvider provider, final IDataServiceNode node, final IOperation operation, final TransferBeanWrapper wrapper) throws EventException, RollbackException{
		
		final String _returned = operation.callBusinessRule(business.getSimpleName(), node, wrapper);
		wrapper.put(BUSINESS.RETURNED, _returned);
	}
	
}
