package bradesco.tf.dsn;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.Format;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import br.com.bradesco.core.aq.dataservice.IContainerObject;
import br.com.bradesco.core.aq.dataservice.IDataObject;
import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.dataservice.IListObject;
import br.com.bradesco.core.aq.dataservice.ISimpleObject;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import br.com.bradesco.core.ui.beans.basedialog.BradescoDialog;
import bradesco.tf.ITabulate;
import bradesco.tf.ITabulate2;
import bradesco.tf.LayerMediator;
import bradesco.tf.OPERATION;
import bradesco.tf.OUTCOME;
import bradesco.tf.StorageType;
import bradesco.tf.TFApplication;
import bradesco.tf.TFConstants;
import bradesco.tf.TerminalFinanceiro;
import bradesco.tf.TransferBeanWrapper;
import bradesco.tf.UtilIdentify;
import bradesco.tf.TFConstants.BUSINESS;
import bradesco.tf.TFConstants.DSN;
import bradesco.tf.annotation.DynamicsToView;
import bradesco.tf.annotation.Property;

import com.javaf.Application;
import com.javaf.Constants;
import com.javaf.ObjectPool;
import com.javaf.Constants.APPLICATION;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.REFLECTION;
import com.javaf.Constants.REGEX;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.reflect.GenericsWorkaround;
import com.javaf.javase.lang.reflect.ReflectionException;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.annotation.BePersistent;
import com.javaf.javase.persistence.annotation.Transient;
import com.javaf.javase.text.NullFormat;
import com.javaf.javase.text.ParsingException;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.text.annotation.Formatter;
import com.javaf.javase.util.Entryc;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;
import com.javaf.model.IDynamic;
import com.javaf.model.Textual;
import com.javaf.model.ValuePlace;

/**
 * Implementa métodos facilitadores para manipulações do Data Service Node.
 * @author fabiojm - Fábio José de Moraes
 */
@SuppressWarnings("deprecation")
public final class UtilDSN {
	private static final UtilDSN INSTANCE = new UtilDSN();
	
	private final UtilReflection reflection;
	private final UtilFormat format;
	private final ILogging logging;
	private final LayerMediator mediator;
	private final UtilPersistence serializer;
	private final UtilIdentify identify;
	private final TFApplication application;
	private ILocalization localize;
	private TerminalFinanceiro arquitetura;
	
	private UtilDSN(){
		reflection  = UtilReflection.getInstance();
		format      = UtilFormat.getInstance();
		logging     = Logging.loggerOf(getClass());
		mediator    = LayerMediator.getInstance();
		serializer  = UtilPersistence.getInstance();
		identify    = UtilIdentify.getInstance();
		application = TFApplication.getInstance();
	}
	
	private final ILocalization getLocalize(){
		if(null== localize){
			localize = Localization.getInstance();
		}
		
		return localize;
	}
	
	private final TerminalFinanceiro getArquitetura(){
		
		if(null== arquitetura){
			arquitetura = TerminalFinanceiro.getInstance();
		}
		
		return arquitetura;
	}
	
	/**
	 * Obter uma instância de trabalho
	 * 
	 * @return Instância de UtilDSN
	 */
	public static synchronized UtilDSN getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Obter ou adicionar uma instância de IContainerObject em um Data Service Node.
	 * 
	 * @param id Identificador do container que será obtido ou adicionado
	 * @param node Data Service Node
	 * @return Instância de IContainerObject que foi obtido ou adicionado
	 */
	public IContainerObject getOrAddContainerObject(final String id, final IDataServiceNode node) {

		IContainerObject _container = node.getContainerObject(id);
		if (null == _container) {
			_container = node.addContainerObject(id);
		}

		return _container;
	}

	/**
	 * Obter ou adicionar uma instância de IContainerObject em um outro container.
	 * 
	 * @param id Identificador do container que será obtido ou adicionado
	 * @param container Instância de IContainerObject
	 * @return Instância de IContainerObject que foi obtido ou adicionado
	 */
	public IContainerObject getOrAddContainerObject(final String id, final IContainerObject container) {

		IContainerObject _container = container.getContainerObject(id);
		if (null == _container) {
			_container = container.addContainerObject(id);
		}

		return _container;
	}

	/**
	 * Obter ou adicionar uma instância de ISimpleObject em um Data Service Node.
	 * 
	 * @param id Identificador do simple que será obtido ou adicionado
	 * @param node Data Service Node
	 * @return Instância de ISimpleObject que foi obtido ou adicionado
	 */
	public ISimpleObject getOrAddSimpleObject(final String id, final IDataServiceNode node) {

		ISimpleObject _simple = node.getSimpleObject(id);
		if (null == _simple) {
			_simple = node.addSimpleObject(id);
		}

		return _simple;
	}
	
	public ISimpleObject getOrAddSimpleObject(final String id, final IDataObject object) throws IllegalArgumentException {
		
		ISimpleObject _result = null;
		if(object instanceof IListObject){
			_result = ((IListObject)object).addSimpleObject();
			
		} else if(object instanceof IContainerObject) {
			final IContainerObject _container = (IContainerObject)object;
			_result = getOrAddSimpleObject(id, _container);
		} else {		
			throw new IllegalArgumentException(getLocalize().localize(I18N.ARGUMENTO_N_VALOR_ILEGAL, INTEGER._2, object));
		}
		
		return _result;
	}
	
	public ISimpleObject getSimpleObject(final String id, final IDataObject object) throws IllegalArgumentException {
		
		ISimpleObject _result = null;
		
		if(object instanceof IListObject){
			_result = ((IListObject)object).addSimpleObject();
			
		} else if(object instanceof IContainerObject) {
			final IContainerObject _container = (IContainerObject)object;
			_result = _container.getSimpleObject(id);
		} else {		
			throw new IllegalArgumentException(getLocalize().localize(I18N.ARGUMENTO_N_VALOR_ILEGAL, INTEGER._2, object));
		}
		
		return _result;
	}

	/**
	 * Obter ou adicionar uma instância de ISimpleObject em um container.
	 * 
	 * @param id Identificador do simple que será obtido ou adicionado
	 * @param container Instância de IContainerObject
	 * @return Instância de ISimpleObject que foi obtido ou adicionado
	 */
	public ISimpleObject getOrAddSimpleObject(final String id, final IContainerObject container) {

		ISimpleObject _simple = container.getSimpleObject(id);
		if (null == _simple) {
			_simple = container.addSimpleObject(id);
		}

		return _simple;
	}

	/**
	 * Obter ou adicionar uma instância de ISimpleObject passando um valor Date em um container.
	 * 
	 * @param id Identificador do simple que será obtido ou adicionado
	 * @param container Instância de IContainerObject
	 * @param value Valor para atualizar ou adicionar no ISimpleObject
	 * @return Instância de ISimpleObject que foi obtido ou adicionado com o valor Date
	 */
	public ISimpleObject getOrAddSimpleObject(final String id, final IContainerObject container, final Date value) {

		ISimpleObject _datenode = getOrAddSimpleObject(id, container);
		_datenode.setValue(format.toString(value, TFConstants.DEFAULT.DSN_DATE_FORMAT));

		return _datenode;
	}
	
	/**
	 * Obter ou adicionar uma instância de ISimpleObject passando um valor Date em um container.
	 * 
	 * @param id Identificador do simple que será obtido ou adicionado
	 * @param container Instância de IContainerObject
	 * @param defaultValue Valor para adicionar ao ISimpleObject se um novo for criado
	 * @return Instância de ISimpleObject que foi obtido ou adicionado
	 */
	public ISimpleObject getOrAddSimpleObject(final String id, final IContainerObject container, final boolean defaultValue) {

		ISimpleObject _simple = container.getSimpleObject(id);
		if (null == _simple) {
			_simple = container.addSimpleObject(id);
			_simple.setBooleanValue(defaultValue);
		}

		return _simple;
	}
	
	/**
	 * Obter ou adicionar uma instância de ISimpleObject passando um valor Date em um container.
	 * 
	 * @param id Identificador do simple que será obtido ou adicionado
	 * @param node Data Service Node
	 * @param defaultValue Valor para adicionar ao ISimpleObject se um novo for criado
	 * @return Instância de ISimpleObject que foi obtido ou adicionado
	 */
	public ISimpleObject getOrAddSimpleObject(final String id, final IDataServiceNode node, final boolean defaultValue) {

		ISimpleObject _simple = node.getSimpleObject(id);
		if (null == _simple) {
			_simple = node.addSimpleObject(id);
			_simple.setBooleanValue(defaultValue);
		}

		return _simple;
	}
	
	/**
	 * Obter ou adicionar uma instância de ISimpleObject passando um valor Date em um container.
	 * 
	 * @param id Identificador do simple que será obtido ou adicionado
	 * @param container Instância de IContainerObject
	 * @param defaultValue Valor para adicionar ao ISimpleObject se um novo for criado
	 * @return Instância de ISimpleObject que foi obtido ou adicionado
	 */
	public ISimpleObject getOrAddSimpleObject(final String id, final IContainerObject container, final long defaultValue) {

		ISimpleObject _simple = container.getSimpleObject(id);
		if (null == _simple) {
			_simple = container.addSimpleObject(id);
			_simple.setBigDecimalValue(new BigDecimal(defaultValue));
		}

		return _simple;
	}
	
	/**
	 * Obter ou adicionar uma instância de ISimpleObject passando um valor Date em um container.
	 * 
	 * @param id Identificador do simple que será obtido ou adicionado
	 * @param container Instância de IContainerObject
	 * @param defaultValue Valor para adicionar ao ISimpleObject se um novo for criado
	 * @return Instância de ISimpleObject que foi obtido ou adicionado
	 */
	public ISimpleObject getOrAddSimpleObject(final String id, final IContainerObject container, final String defaultValue) {

		ISimpleObject _simple = container.getSimpleObject(id);
		if (null == _simple) {
			_simple = container.addSimpleObject(id);
			_simple.setValue(defaultValue);
		}

		return _simple;
	}

	/**
	 * Obter ou adicionar uma instância de IListObject em um container.
	 * 
	 * @param id Identificador do list que será obtido ou adicionado
	 * @param container Instância de IContainerObject
	 * @return Instância de IListObject que foi obtido ou adicionado
	 */
	public IListObject getOrAddListObject(final String id, final IContainerObject container) {

		IListObject _list = container.getListObject(id);
		if (null == _list) {
			_list = container.addListObject(id);
		}

		return _list;
	}
	
	public IListObject getOrAddListObject(final String id, final IDataServiceNode container) {
		
		IListObject _result = container.getListObject(id);
		if(null== _result){
			_result = container.addListObject(id);
		}
		
		return _result;
	}
	
	public IListObject getOrAddListObject(final String id, final IDataObject object) throws IllegalArgumentException {
	
		IListObject _result = null;
		if(object instanceof IListObject){
			_result = ((IListObject)object).addListObject();
			
		} else if(object instanceof IContainerObject){
			final IContainerObject _container = (IContainerObject)object;
			_result = getOrAddListObject(id, _container);
			
		} else {
			throw new IllegalArgumentException(getLocalize().localize(I18N.ARGUMENTO_N_VALOR_ILEGAL, INTEGER._2, object));
		}
		
		return _result;
	}
	
	/**
	 * Obter ou adicioanr o container OP_CALLERS utilizado na comunicação entre projetos.
	 * 
	 * @param node Data Service Node
	 * @return Instância de IContainerObject que foi obtido ou adicionado 
	 */
	public IContainerObject getOrAddOpCallers(final IDataServiceNode node){
		return getOrAddContainerObject(DSN.OP_CALLERS, node);
	}
	
	public IContainerObject removeOpCallers(final IDataServiceNode node){
		final IContainerObject _result = getOrAddOpCallers(node);
		
		node.removeDataObject(DSN.OP_CALLERS);
		
		return _result;
	}
	
	/**
	 * Obter ou adicionar a estrutura base do input para comunicação entre projetos
	 * @param node Data Service Node
	 * @param projeto Nome do container que representa o projeto destino da comunicação
	 * @return Instância de IContainerObject que foi obtido ou adicionado
	 * @see #getOrAddOpCallers(IDataServiceNode)
	 */
	public IContainerObject getOrAddOpCallersInput(final IDataServiceNode node, final String projeto){
		
		final IContainerObject _opcallers = getOrAddOpCallers(node);
		final IContainerObject _projeto   = getOrAddContainerObject(projeto, _opcallers);
		return getOrAddContainerObject(DSN.OP_INPUT, _projeto);
	}
	
	/**
	 * Obter ou adicionar a estrutura base do output da comunicação entre projetos
	 * @param node Data Service Node
	 * @param projeto Nome do container que representa o projeto destino da comunicação
	 * @return Instância de IContainerObject que foi obtido ou adicionado
	 * @see #getOrAddOpCallers(IDataServiceNode)
	 */
	public IContainerObject getOrAddOpCallersOutput(final IDataServiceNode node, final String projeto){
		
		final IContainerObject _opcallers = getOrAddOpCallers(node);
		final IContainerObject _projeto   = getOrAddContainerObject(projeto, _opcallers);
		return getOrAddContainerObject(DSN.OP_OUTPUT, _projeto);
	}
	
	private void toString(final IDataObject object, final StringBuilder builder, final StringBuilder offset){

		if(object instanceof ISimpleObject){
			final ISimpleObject _simple = (ISimpleObject)object;
			builder.append(offset).append(object.getKey() + STRING.SPACE1 + STRING.IGUAL + STRING.SPACE1 + _simple.getValue() + STRING.NEW_LINE);
			
		} else if (object instanceof IContainerObject){
			builder.append(offset).append(object.getKey()).append(STRING.NEW_LINE);
			final IContainerObject _container = (IContainerObject)object;
			
			offset.append(STRING.SPACE2);
			for(IDataObject _item : _container.getAllDataObjects()){
				toString(_item, builder, offset);
			}
			offset.delete(offset.length() - INTEGER._2, offset.length());
			
		} else if(object instanceof IListObject){
			builder.append(offset).append(object.getKey()).append(STRING.NEW_LINE);
			
			final IListObject _list = (IListObject)object;
			
			offset.append(STRING.SPACE2);
			for(IDataObject _item : _list.getAllDataObjects()){
				toString(_item, builder, offset);
			}
			offset.delete(offset.length() - INTEGER._2, offset.length());
		} else {
			builder.append(offset).append(object.getKey() + STRING.SPACE1 + STRING.IGUAL + STRING.SPACE1 + object + STRING.NEW_LINE);
		}

	}
	
	/**
	 * Constrói uma representação String do container dentro de um Data Service Node
	 * @param container
	 * @return String representativa do container
	 */
	public String toString(final IContainerObject container){
		
		final StringBuilder _builder = new StringBuilder();
		_builder.append(container.getParentDataObject().getFullKey()).append(STRING.NEW_LINE);
		
		final StringBuilder _offset  = new StringBuilder();
		_offset.append(STRING.SPACE2);
		
		toString(container, _builder, _offset);
		
		return _builder.toString();
	}
	
	/**
	 * Marcar o nó padrão sobre a execução da operação corrente.
	 * 
	 * @param node Data Service Node
	 * @param execute Valor representantivo para execução da operação
	 * @return Instância de ISimpleObject com o valor armazenado
	 */
	public ISimpleObject doExecute(final IDataServiceNode node, final boolean execute){
		
		final ISimpleObject _result = getOrAddSimpleObject(DSN.DO_EXECUTE, node);
		_result.setBooleanValue(execute);
		
		return _result;
	}
	
	/**
	 * Instancia o ISimpleObject para armazenar o caminho acessado pelo usuário desde o menu até o layout corrente
	 * @param provider
	 * @param operation
	 * @param node
	 * @return
	 */
	public ISimpleObject doPath(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		
		final String _prefixo = mediator.getObjectPool().get(identify.doPrefixo(operation), String.class);
		final IContainerObject _instancia = getOrAddContainerObject(_prefixo, node);
		
		final ISimpleObject _result = getOrAddSimpleObject(DSN.PATH, _instancia);
		
		return _result;
	}
	
	/**
	 * Instancia o ISimpleObject para armazenar o caminho acessado pelo usuário desde o menu até o layout corrente
	 * @param provider
	 * @param operation
	 * @param node
	 * @param value Valor inicial do caminho
	 * @return
	 */
	public ISimpleObject doPath(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final String value){
		
		final String _prefixo = mediator.getObjectPool().get(identify.doPrefixo(operation), String.class);
		final IContainerObject _instancia = getOrAddContainerObject(_prefixo, node);
		
		final ISimpleObject _result = getOrAddSimpleObject(DSN.PATH, _instancia);
		_result.setValue(value);
		
		return _result;
	}
	
	public ISimpleObject doPath(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final int localized){
		return doPath(provider, operation, node, getLocalize().localize(localized, provider));
	}
	
	public ISimpleObject doRunTo(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final String outcome){
		
		final String _prefixo             = mediator.getObjectPool().get(identify.doPrefixo(operation), String.class);
		final IContainerObject _container = getOrAddContainerObject(_prefixo, node);
		
		final ISimpleObject _result = getOrAddSimpleObject(DSN.RUN_TO, _container);
		if(null!= outcome){
			_result.setValue(outcome);
		}
		
		return _result;
	}
	
	public void unRunTo(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		
		final String _prefixo             = mediator.getObjectPool().get(identify.doPrefixo(operation), String.class);
		final IContainerObject _container = getOrAddContainerObject(_prefixo, node);
		
		final ISimpleObject _result = getOrAddSimpleObject(DSN.RUN_TO, _container);
		_result.setValue(STRING.EMPTY);
		
	}
	
	public String getRunTo(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		
		String _result = null;
		final String _prefixo             = mediator.getObjectPool().get(identify.doPrefixo(operation), String.class);
		final IContainerObject _container = getOrAddContainerObject(_prefixo, node);
		
		final ISimpleObject _runto = getOrAddSimpleObject(DSN.RUN_TO, _container);
		if(!STRING.EMPTY.equals(_runto.getValue())){
			_result = _runto.getValue();
		}
		
		return _result;
	}
	
	public IContainerObject doOutcome(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
	
		final String _prefixo             = mediator.getObjectPool().get(identify.doPrefixo(operation), String.class);
		final IContainerObject _container = getOrAddContainerObject(_prefixo, node);
		
		final IContainerObject _result = getOrAddContainerObject(DSN.OUTCOME, _container);
		
		return _result;
	}
	
	public ISimpleObject doOutcome(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final OUTCOME from, final String to){
		
		final IContainerObject _outcome = doOutcome(provider, operation, node);
		final ISimpleObject _result     = getOrAddSimpleObject(from.toString(), _outcome);
		
		if(null!= to){
			_result.setValue(to);
			
		} else {
			
			_result.setValue(STRING.EMPTY);
		}
		
		return _result;
	}
	
	public ISimpleObject doOutcome(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final OUTCOME from, final String to, final Class<? extends BradescoDialog> forLayout){
		
		return doOutcome(provider, operation, node, from, to, forLayout.getSimpleName());
	}
	
	public ISimpleObject doOutcome(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final OUTCOME from, final String to, final String forLayout){
		final IContainerObject _outcome = doOutcome(provider, operation, node);
		final IContainerObject _for     = getOrAddContainerObject(forLayout, _outcome);
		final ISimpleObject _result     = getOrAddSimpleObject(from.toString(), _for);
		
		_result.setValue(to);
		
		return _result;
	}
	
	public String getOutcome(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final OUTCOME from){
		String _result = null;
		
		final IContainerObject _outcome = doOutcome(provider, operation, node);
		final IContainerObject _for     = _outcome.getContainerObject(node.getNodeName());
	
		ISimpleObject _simple     = _outcome.getSimpleObject(from.toString());
		if(null!= _for){
			_simple = _for.getSimpleObject(from.toString());
			_outcome.removeDataObject(_for.getKey());
		}
		
		if(null!= _simple && !STRING.EMPTY.equals(_simple.getValue())){
			_result = _simple.getValue();
		}
		
		return _result;
	}
	
	/**
	 * Copia valores e estrutura de um ISimpleObject para outro.<br/>
	 * Caso a estrutura na exista no <code>destino</code> ela será criada.
	 * 
	 * @param origem
	 * @param destino
	 */
	public void copy(final ISimpleObject origem, final IContainerObject destino){
		
		final IDataServiceNode _node = origem.getDataServiceNode();
		_node.copySimpleTo(origem, destino, IDataServiceNode.ADD_IF_NOT_EXIST);
		
	}

	/**
	 * Copia valores e estrutura de um IContainerObject para outro.<br/>
	 * - caso a estrutura na exista no <code>destino</code> ela será criada;<br/>
	 * - somente a estrutura e valores abaixo do nó base serão processadas.
	 * 
	 * @param origem
	 * @param destino
	 */
	public void copy(final IContainerObject origem, final IContainerObject destino){
		
		final IDataServiceNode _node = origem.getDataServiceNode();
		
		for(IDataObject _object : origem.getAllDataObjects()){
			if(_object instanceof ISimpleObject){
				copy((ISimpleObject)_object, destino);
				
			} else if(_object instanceof IContainerObject) {
				_node.copyContainerTo((IContainerObject)_object, destino, IDataServiceNode.ADD_IF_NOT_EXIST);
				
			} else {
				_node.copyListTo((IListObject)_object, destino, IDataServiceNode.ADD_IF_NOT_EXIST);
			}
		}
	}
	
	public void copy(final IDataServiceNode origem, final IContainerObject destino){
		
		for(IDataObject _object : origem.getAllDataObjects()){
			if(_object instanceof ISimpleObject){
				copy((ISimpleObject)_object, destino);
				
			} else if(_object instanceof IContainerObject) {
				origem.copyContainerTo((IContainerObject)_object, destino, IDataServiceNode.ADD_IF_NOT_EXIST);
				
			} else {
				origem.copyListTo((IListObject)_object, destino, IDataServiceNode.ADD_IF_NOT_EXIST);
			}
		}
	}
	
	public boolean isSimpleObjectCandidate(final Class<?> c){
		
		if(null!= c){
			
			return (c.isPrimitive()
					 || c.isEnum()
					 || String.class.isAssignableFrom(c)
					 || Date.class.isAssignableFrom(c)
					 || Number.class.isAssignableFrom(c)
					 || Boolean.class.isAssignableFrom(c));
		}
		
		return Boolean.FALSE;
	}
	
	public boolean isContainerObjectCandidate(final Class<?> c){
		
		if(null!= c){
			return !isSimpleObjectCandidate(c) && !isListObjectCandidate(c);
		}
		
		return Boolean.FALSE;
	}
	
	public boolean isTextual(final Class<?> c){
		boolean _result = Boolean.FALSE;
		
		if(null!= c){
			_result = Textual.class.isAssignableFrom(c);
		}
		
		return _result;
	}
	
	public boolean isListObjectCandidate(final Class<?> c){
		
		if(null!= c){
			return  Collection.class.isAssignableFrom(c) ;
		}
		
		return Boolean.FALSE;
	}
	
	public void setValue(final ISimpleObject node, final Object value, final Class<?> type){
		setValue(node, value, StorageType.CONTROL, null, type);
	}
	
	public void setValue(final ISimpleObject node, final Object value, final Class<?> type, final StorageType storage){
		
		if(null!= value){
			setValue(node, value, storage, null, type);
			
		} else {
			
			final Object _value = TFConstants.DEFAULT.defaultOf(TFConstants.LAYER.DSN, type);
			if(null!= _value){
				setValue(node, _value, storage, null, type);
			}
		}
	}
	
	public void setValue(final ISimpleObject node, final Object value, final Class<?> type, final Formatter formatter){
		
		final ObjectPool _opool = LayerMediator.getInstance().getObjectPool();
				
		if(null!= formatter){
			final Format _formatter = TFConstants.DEFAULT.FORMATS.get(formatter.pattern());
			if(null!= _formatter){
				setValue(node, value, null, _formatter, type);
				
			} else if( !NullFormat.class.isAssignableFrom( formatter.format() )) {
				
				final Format _format = _opool.getOrCreate(formatter.format(), formatter.format());
				setValue(node, value, null, _format, type);
				
			} else {
				
				setValue(node, value, null, null, type);
				
			}
		} else {
			setValue(node, value, null, null, type);
		}
		
	}
	
	public void setValue(final ISimpleObject node, final Object value, final StorageType storage, final Format formatter, final Class<?> type){
		
		if(null== formatter){
			if(value instanceof Date){
				if(StorageType.VIEW.equals(storage)){
					node.setValue(format.toString((Date)value, Constants.DEFAULT.ABNT_DATE_FORMAT));
					
				}else{
					node.setValue(format.toString((Date)value, TFConstants.DEFAULT.DSN_DATE_FORMAT));
				}
			}else if(value instanceof BigDecimal){
				node.setBigDecimalValue((BigDecimal)value);
			
			} else if(value instanceof Boolean){
				if(StorageType.VIEW.equals(storage)){
					
					node.setValue(format.toString(value, TFConstants.DEFAULT.DSN_VIEW_BOOLEAN_FORMAT));
					
				} else {
					node.setBooleanValue((Boolean)value);
				}
				
			} else if(value instanceof Float){
				
				node.setFloatValue((Float)value);
				
			} else if(value instanceof Integer){
				node.setIntValue((Integer)value);
				
			} else if(value instanceof Double){
				node.setValue(format.toString((Double)value));
		
			} else if(value instanceof Long){
				node.setValue(format.toString((Long)value));
				
			} else if(value instanceof String){
				
				node.setValue(format.toString(value));
				
			} else if(null!= value) {
				
				node.setValue(STRING.EMPTY + value);
				
			} else if(String.class.isAssignableFrom(type)) {
				
				node.setValue(format.toString(value));
				
			} else {
				node.setValue(STRING.EMPTY + format.toString( value ));
			}
			
		} else {
			if(null!= value){
				node.setValue(formatter.format(value));
			}
		}
	}
	
	public <T> T getValue(final ISimpleObject node, final Class<T> c) throws ParsingException, IllegalArgumentException, ClassCastException {
		return getValue(node, c, null, StorageType.CONTROL);
	}
	
	public <T> T getValue(final ISimpleObject node, final Class<T> c, final Formatter formatter, final StorageType storage){
		T _result = null;
		
		final ObjectPool _opool = LayerMediator.getInstance().getObjectPool();
		
		if(null!= formatter){
			final Format _formatter = TFConstants.DEFAULT.FORMATS.get(formatter.pattern());
			if(null!= _formatter){
				_result = getValue(node, c, storage, _formatter);
				
			} else if( !NullFormat.class.isAssignableFrom( formatter.format() )){
				
				final Format _format = _opool.getOrCreate(formatter.format(), formatter.format());
				_result = getValue(node, c, storage, _format);
				
			} else {
				
				throw new IllegalArgumentException("Formatador não encontrado: " + formatter.pattern());
			}
			
		} else {
			
			_result = getValue(node, c, storage, null);
			
		}
		
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(final ISimpleObject node, final Class<T> c, final StorageType storage, final Format formatter) throws ParsingException, IllegalArgumentException, ClassCastException {
		
		Object _result = null;
		if(isSimpleObjectCandidate(c)){
			if(null== formatter){
				if(Date.class.isAssignableFrom(c)){
	
					try{
						_result = format.parseDate(node.getValue(), TFConstants.DEFAULT.DSN_DATE_FORMAT);
					
					}catch(ParsingException _e){
						
						try{
							_result = format.parseDate(node.getValue(), Constants.DEFAULT.ABNT_DATE_FORMAT);
						}catch(ParsingException __e){
						}
					}
					
				} else if(BigDecimal.class.isAssignableFrom(c)){
					_result = node.getBigDecimalValue();
					
				} else if(Boolean.class.isAssignableFrom(c) || Boolean.TYPE.equals(c)){
					
					if(StorageType.VIEW.equals(storage)){
						_result = format.parse(node.getValue(), TFConstants.DEFAULT.DSN_VIEW_BOOLEAN_FORMAT);
					} else {
						_result = node.getBooleanValue();
					}
					
				} else if(Float.class.isAssignableFrom(c) || Float.TYPE.equals(c)){
					_result = node.getFloatValue();
					
				} else if(Integer.class.isAssignableFrom(c) || Integer.TYPE.equals(c)){
					_result = node.getIntValue();
					
				} else if(Double.class.isAssignableFrom(c) || Double.TYPE.equals(c)){
					_result = format.parseDouble(node.getValue());
					
				} else if(Long.class.isAssignableFrom(c) || Long.TYPE.equals(c)){
					_result = format.parseLong(node.getValue());
					
				} else if(c.isEnum()){
					_result = reflection.valueOf(c, node.getValue());
					
				} else {
					_result = node.getValue();
				}
				
			} else {
				try{
					_result = formatter.parseObject(node.getValue());
				}catch(ParseException _e){
					logging.debug(_e.getMessage(), _e);
				}
			}
			
		} else {
			throw new IllegalArgumentException(getLocalize().localize(I18N.ARGUMENTO_N_VALOR_ILEGAL, INTEGER._2, c));
		}
		
		return (T)_result;
	}
	
	/**
	 * Obtem valor no SimpleObject do node e faz parse.<br/>
	 * Se houver qualquer problema no parsing do valor, defaultValue será retornado.
	 * @param <T>
	 * @param node
	 * @param c
	 * @param defaultValue
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ClassCastException
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(final ISimpleObject node, final Class<T> c, final T defaultValue, final StorageType storage) throws IllegalArgumentException, ClassCastException {
		
		Object _result = null;
		try{
			_result = getValue(node, c, storage, null);
			
		}catch(ParsingException _e){
			_result = defaultValue;
		}
		
		return (T)_result;
	}

	private ISimpleObject toSimpleObjectGeneric(final Object target, final Class<?> c, final Object instancia, final String nome, final int index, final StorageType storage, final Formatter formatter) throws IllegalArgumentException {
		
		ISimpleObject _result = null;
		if(isSimpleObjectCandidate(c)){
			
			Object[] _args = new Object[]{nome};
			if(target instanceof IListObject){
				_args = new Object[]{};				
			}
			
			Object _instance = null;
			if(!(target instanceof IListObject)){
				_instance = reflection.invoke(target, REFLECTION.METHOD_GET_SIMPLE_OBJECT, _args);
			} else {
				_instance = reflection.invoke(target, REFLECTION.METHOD_GET_SIMPLE_OBJECT, new Object[]{index}, new Class[]{Integer.TYPE});
			}
			
			if(null== _instance){
				_instance = reflection.invoke(target, REFLECTION.METHOD_ADD_SIMPLE_OBJECT, _args);
			}
			_result = (ISimpleObject) _instance;
			
			if(null== formatter){
				setValue(_result, instancia, c, storage);
			} else {
				setValue(_result, instancia, c, formatter);
			}
		} else {
			throw new IllegalArgumentException(getLocalize().localize(I18N.ARGUMENTO_N_VALOR_ILEGAL, INTEGER._2, c));
		}
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	private IContainerObject toContainerObjectGeneric(final Object target, final Class<?> c, final Object instancia, final String nome, final int index, final boolean doPersistence, final String persistenceName, final boolean allTransient, final Transient embeddeTransient, final StorageType storage, final String property) throws IllegalArgumentException {
		
		IContainerObject _result = null;
		if(isContainerObjectCandidate(c)){
			
			boolean _onCollection = Boolean.FALSE;
			
			Object[] _args = new Object[]{nome};
			if(target instanceof IListObject){
				_args = new Object[]{};
			}
			
			Object _instance = null;
			if(!(target instanceof IListObject)){
				_instance = reflection.invoke(target, REFLECTION.METHOD_GET_CONTAINER_OBJECT, _args);
			} else {
				_instance = reflection.invoke(target, REFLECTION.METHOD_GET_CONTAINER_OBJECT, new Object[]{index}, new Class[]{Integer.TYPE});
				_onCollection = Boolean.TRUE;
			}
			
			if(null== _instance){
				_instance = reflection.invoke(target, REFLECTION.METHOD_ADD_CONTAINER_OBJECT, _args);
			}
			_result = (IContainerObject) _instance;
			
			//processa DynamicsToView
			final DynamicsToView _dtv = reflection.annotationOf(c, DynamicsToView.class);
			if(null!= _dtv){
				if(instancia instanceof IDynamic){
					final IDynamic _idynamic = (IDynamic)instancia;
					for(Property _property : _dtv.value()){
						final Object _value = _idynamic.get(_property.value());
						
						if(null!= _value){
							toSimpleObjectGeneric(_result, _value.getClass(), _value, _property.value(), INTEGER._0, storage, _property.formatter());
						}
					}
				} else {
					logging.warn("Classe não é uma instância de " + IDynamic.class);
				}
			}
			
			final List<Method> _methods = reflection.getterOf(c);
			if(_onCollection){
				if(instancia instanceof ITabulate){
					_methods.clear();
					_methods.addAll(reflection.getGetterBased(c, ITabulate.class));
					
					if(instancia instanceof ITabulate2){
						_methods.addAll(reflection.getGetterBased(c, ITabulate2.class));
					}
				}
			}
			for(Method _method : _methods){
				
				if(reflection.isGetter(_method)){
					final String _attribute = reflection.attributeOf(_method);
					String _property = property + STRING.DOT + _attribute;
					
					Class<?> _c = _method.getReturnType();
					
					final Transient _transient = reflection.annotationOf(_method, Transient.class);
					boolean _isTransient = (null!= _transient);
					Object _got = null;
					if(null!= instancia){
						_got = reflection.invoke(instancia, _method.getName(), new Object[]{});
						
						if(null!= _got){
							_c = _got.getClass();
						}
					}
					
					if(isSimpleObjectCandidate(_c)){
						final ISimpleObject _simple = toSimpleObjectGeneric(_result, _c, _got, _attribute, INTEGER._0, storage, reflection.annotationOf(_method, Formatter.class) );
						
						//forcar persistencia
						final BePersistent _bep = _method.getAnnotation(BePersistent.class);
						if(null== _bep){
							if(!allTransient){
								// contrucao do PersistenceMediator
								if(doPersistence && null!= instancia){
									try{
										final Method _setter = reflection.setterOf(c, _attribute, _method.getReturnType());
										_isTransient |= reflection.hasAnnotation(_method, Transient.class);
										
										if(!_isTransient){
											final SerializerMediator _persistence = mediator.getObjectPool().getOrCreate(persistenceName, SerializerMediator.class);
											
											if(null!= embeddeTransient){
												_persistence.addPersistence(_property, new DataServiceNodeSimplePersistence(_simple, instancia, _method, _setter, embeddeTransient.value(), storage));
											}else {
												_persistence.addPersistence(_property, new DataServiceNodeSimplePersistence(_simple, instancia, _method, _setter, storage));
											}
										}
										
									}catch(ReflectionException _e){
										logging.warn(getLocalize().localize(I18N.ATRIBUTO_NAO_POSSUI_SETTER, _attribute), _e);
									}
									
								} else if(doPersistence && null== instancia){
									logging.info(I18N.CONFIGURADO_PERSISTENCIA_INSTANCIA_NULL);
								}
							}
							
							
						} else {//passando por cima de tudo para forçar algo a ser persistente
							final SerializerMediator _persistence = mediator.getObjectPool().getOrCreate(persistenceName, SerializerMediator.class);
							final Transient __transient = _method.getAnnotation(Transient.class);
							
							try{
								final Method _setter = reflection.setterOf(c, _attribute, _method.getReturnType());
								
								if(null!= __transient){
									_persistence.addPersistence(_property + STRING.DOT + String.valueOf(index), new DataServiceNodeSimplePersistence(_simple, instancia, _method, _setter, __transient.value(), storage));
								}else {
									_persistence.addPersistence(_property + STRING.DOT + String.valueOf(index), new DataServiceNodeSimplePersistence(_simple, instancia, _method, _setter, storage));
								}
								
							}catch(ReflectionException _e){
								logging.warn(getLocalize().localize(I18N.ATRIBUTO_NAO_POSSUI_SETTER, _attribute), _e);
							}
							
						}
						
					} else if(isContainerObjectCandidate(_c)) {
						
						if(!serializer.isTransient(_transient)){
							
							if(!isTextual(_c)){
								toContainerObjectGeneric(_result, _c, _got, _attribute, INTEGER._0, doPersistence, persistenceName, allTransient, reflection.annotationOf(_method, Transient.class), storage, _property);
							} else {
								final Textual _textual           = (Textual)_got;
								final IContainerObject _ctextual = getOrAddContainerObject(_attribute, _result);
								final Set<String> _keys          = _textual.keySet();
								for(String _key : _keys){
									final Object _value         = _textual.get(_key);
									final ISimpleObject _svalue = getOrAddSimpleObject(_key, _ctextual);
									if(null!= _value){
										_svalue.setValue(_value.toString());
									}
								}
							}
						}
						
					} else if(isListObjectCandidate(_c)) {
						
						//indicador sobre criar ou não a persistencia neste escopo
						boolean _isLocalDo = Boolean.FALSE;
						
						Class<?> _type = null;
						//verificar tipo generico anotado
						final GenericsWorkaround _workaround = reflection.annotationOf(_method, GenericsWorkaround.class);
						if(null!= _workaround){
							if(_workaround.value().equals(Integer.class)){
								_isTransient &= Boolean.TRUE;
								_isLocalDo = Boolean.TRUE;
							} else {
								logging.warn(getLocalize().localize(I18N.COLECAO_NAO_ARMAZENA, Integer.class, _workaround.value()));
							}
							
							_type = _workaround.value();
						} else {
							logging.warn(getLocalize().localize(I18N.COLECAO_NAO_POSSUI_TIPO, _method.getName()));
						}
						
						final IListObject _list = toListObjectGeneric(_result, (Collection)_got, _attribute, INTEGER._0, doPersistence, persistenceName, !_isTransient, reflection.annotationOf(_method, Transient.class), storage, _property, _type);
						
						if(!allTransient){
							if(doPersistence && null!= instancia){
								try{								
									if(!_isTransient && _isLocalDo){
										final SerializerMediator _persistence = mediator.getObjectPool().getOrCreate(persistenceName, SerializerMediator.class);
										_persistence.addPersistence(_property, new DataServiceNodeListIntegerPersistence(_list, (List)_got));
									}
								}catch(ReflectionException _e){
									logging.warn(getLocalize().localize(I18N.ATRIBUTO_NAO_POSSUI_SETTER, _attribute), _e);
								}
							} else if(doPersistence && null== instancia){
								logging.info(I18N.CONFIGURADO_PERSISTENCIA_INSTANCIA_NULL);
							}
						}
					} else {
						throw new IllegalArgumentException(getLocalize().localize(I18N.TIPO_NAO_MAPEADO_DSN, _c));
					}
				}
			}

		} else {
			throw new IllegalArgumentException(getLocalize().localize(I18N.ARGUMENTO_N_VALOR_ILEGAL, INTEGER._2, c));
		}
		
		return _result;
	}
	
	private IListObject toListObjectGeneric(final Object target, final Collection<?> collection, final String nome, final int index, final boolean doPersistence, final String persistenceName, final boolean allTransient, final Transient embeddedTransiente, final StorageType storage, final String property, final Class<?> type) throws IllegalArgumentException {
		
		IListObject _result = null;
		Object[] _args = new Object[]{nome};
		if(target instanceof IListObject){
			_args = new Object[]{};
		}
		
		Object _instance = null;
		if(!(target instanceof IListObject)){
			_instance = reflection.invoke(target, REFLECTION.METHOD_GET_LIST_OBJECT, _args);
		} else {
			_instance = reflection.invoke(target, REFLECTION.METHOD_GET_LIST_OBJECT, new Object[]{index}, new Class[]{Integer.TYPE});
		}
		
		if(null== _instance){
			_instance = reflection.invoke(target, REFLECTION.METHOD_ADD_LIST_OBJECT, _args);
		}
		_result = (IListObject) _instance;
		
		//sempre remove todas as ocorrencias na lista
		_result.reset();
		if(null!= collection){
			int _index = INTEGER._0;
			
			for(Object _item : collection){
				if(null!= _item){
					final Class<?> _c= _item.getClass();
					
					if(isSimpleObjectCandidate(_c)){
						toSimpleObjectGeneric(_result, _c, _item, null, _index, storage, null);
						
					} else if(isContainerObjectCandidate(_c)){
						toContainerObjectGeneric(_result, _c, _item, null, _index, doPersistence, persistenceName, allTransient, embeddedTransiente, storage, property);

					} else if(isListObjectCandidate(_c)){
						toListObjectGeneric(_result, (Collection<?>)_item, null, _index, doPersistence, persistenceName, allTransient, reflection.annotationOf(_c, Transient.class), storage, property, null);
						
					} else {
						throw new IllegalArgumentException(getLocalize().localize(I18N.TIPO_NAO_MAPEADO_DSN, _c));
					}
				}
				
				_index++;
			}
			
			//verificar e montar a persistencia para gravacao de listas de objetos no DSN
			if(!allTransient 
					&& null!= type 
					&& isContainerObjectCandidate(type)){
				
				if(doPersistence){
					final SerializerMediator _persistence = mediator.getObjectPool().getOrCreate(persistenceName, SerializerMediator.class);
					_persistence.addPersistence(property, new DataServiceNodeListObjectPersistence(_result, collection) );
				}
			}
		}
		
		return _result;
	}
	
	public ISimpleObject toSimpleObject(final IDataServiceNode target, final Class<?> c, final String nome) throws IllegalArgumentException {
		return toSimpleObjectGeneric(target, c, null, nome, INTEGER._0, StorageType.CONTROL, null);
	}
	
	public ISimpleObject toSimpleObject(final IContainerObject target, final Class<?> c, final String nome) throws IllegalArgumentException{
		return toSimpleObjectGeneric(target, c, null, nome, INTEGER._0, StorageType.CONTROL, null);
	}
	
	public ISimpleObject toSimpleObject(final IListObject target, final Class<?> c) throws IllegalArgumentException{
		return toSimpleObjectGeneric(target, c, null, null, INTEGER._0, StorageType.CONTROL, null);
	}
	
	public ISimpleObject toSimpleObject(final IContainerObject target, final Object value, final String nome) throws IllegalArgumentException, NullPointerException {
		return toSimpleObjectGeneric(target, value.getClass(), value, nome, INTEGER._0, StorageType.CONTROL, null);
	}
	
	public IContainerObject toContainerObject(final IDataServiceNode target, final Class<?> c, final String nome) throws IllegalArgumentException{
		return toContainerObjectGeneric(target, c, null, nome, INTEGER._0, Boolean.FALSE, null, Boolean.TRUE, null, StorageType.CONTROL, nome);
	}
	
	public IContainerObject toContainerObject(final IDataServiceNode target, final Object instancia, final String nome) throws IllegalArgumentException{
		return toContainerObjectGeneric(target, instancia.getClass(), instancia, nome, INTEGER._0, Boolean.FALSE, null, Boolean.TRUE, null, StorageType.CONTROL, nome);
	}
	
	public IContainerObject toContainerObject(final IDataServiceNode target, final Object instancia, final String nome, final boolean doPersistence) throws IllegalArgumentException{
		return toContainerObjectGeneric(target, instancia.getClass(), instancia, nome, INTEGER._0, doPersistence, nome, !doPersistence, null, StorageType.CONTROL, nome);
	}
	
	public IContainerObject toContainerObject(final IDataServiceNode target, final Object instancia, final String nome, final boolean doPersistence, final StorageType storage) throws IllegalArgumentException{
		return toContainerObjectGeneric(target, instancia.getClass(), instancia, nome, INTEGER._0, doPersistence, nome, !doPersistence, null, storage, nome);
	}
	
	public IContainerObject toContainerObject(final IContainerObject target, final Object instancia, final String nome, final boolean doPersistence) throws IllegalArgumentException{
		return toContainerObjectGeneric(target, instancia.getClass(), instancia, nome, INTEGER._0, doPersistence, nome, !doPersistence, null, StorageType.CONTROL, nome);
	}
	
	public IContainerObject toContainerObject(final IContainerObject target, final Object instancia, final String nome, final boolean doPersistence, final String persistenceName) throws IllegalArgumentException{
		return toContainerObjectGeneric(target, instancia.getClass(), instancia, nome, INTEGER._0, doPersistence, persistenceName, !doPersistence, null, StorageType.VIEW, nome);
	}
	
	public IContainerObject toContainerObject(final IContainerObject target, final Object instancia, final String nome, final boolean doPersistence, final String persistenceName, final StorageType storage) throws IllegalArgumentException{
		return toContainerObjectGeneric(target, instancia.getClass(), instancia, nome, INTEGER._0, doPersistence, persistenceName, !doPersistence, null, storage, nome);
	}
	
	public IContainerObject toContainerObject(final IContainerObject target, final Class<?> c, final String nome) throws IllegalArgumentException{
		return toContainerObjectGeneric(target, c, null, nome, INTEGER._0, Boolean.FALSE, null, Boolean.TRUE, null, StorageType.CONTROL, nome);
	}
	
	public IContainerObject toContainerObject(final IContainerObject target, final Object instancia, final String nome) throws IllegalArgumentException{
		return toContainerObjectGeneric(target, instancia.getClass(), instancia, nome, INTEGER._0, Boolean.FALSE, null, Boolean.TRUE, null, StorageType.CONTROL, nome);
	}
	
	public IContainerObject toContainerObject(final IListObject target, final Class<?> c) throws IllegalArgumentException{
		return toContainerObjectGeneric(target, c, null, null, INTEGER._0, Boolean.FALSE, null, Boolean.TRUE, null, StorageType.CONTROL, STRING.EMPTY);
	}
	
	public IContainerObject toContainerObject(final IListObject target, final Object instancia, int index) throws IllegalArgumentException{
		return toContainerObjectGeneric(target, instancia.getClass(), instancia, null, index, Boolean.FALSE, null, Boolean.TRUE, null, StorageType.CONTROL, STRING.EMPTY);
	}
	
	public IContainerObject toContainerObject(final IListObject target, final Object instancia, int index, final boolean doPersistence) throws IllegalArgumentException{
		return toContainerObjectGeneric(target, instancia.getClass(), instancia, null, index, doPersistence, null, Boolean.TRUE, null, StorageType.CONTROL, STRING.EMPTY);
	}
	
	public IListObject toListObject(final IDataServiceNode target, final Collection<?> collection, final String nome) throws IllegalArgumentException{
		return toListObjectGeneric(target, collection, nome, INTEGER._0, Boolean.FALSE, null, Boolean.TRUE, null, StorageType.CONTROL, nome, null);
	}
	
	public IListObject toListObject(final IContainerObject target, final Collection<?> collection, final String nome) throws IllegalArgumentException{
		return toListObjectGeneric(target, collection, nome, INTEGER._0, Boolean.FALSE, null, Boolean.TRUE, null, StorageType.CONTROL, nome, null);
	}
	
	public IListObject toListObject(final IListObject target, final Collection<?> collection, final String nome) throws IllegalArgumentException{
		return toListObjectGeneric(target, collection, nome, INTEGER._0, Boolean.FALSE, null, Boolean.TRUE, null, StorageType.CONTROL, nome, null);
	}
	
	public IContainerObject doPesquisarContainer(final IDataServiceNode node, final String prefixo){
		
		final IContainerObject _prefixo   = getOrAddContainerObject(prefixo, node);
		final IContainerObject _pesquisar = getOrAddContainerObject(DSN.PESQUISAR_, _prefixo);
		final IContainerObject _resultado = getOrAddContainerObject(DSN.RESULTADO, _pesquisar);
		
		final IListObject _entries = getOrAddListObject(DSN.ENTRIES, _resultado);
		if(_entries.getAllDataObjects().size() == INTEGER._0){
			_entries.addContainerObject();
			
		}
		
		return _pesquisar;
	}
	
	public IContainerObject doUiContainer(final IDataServiceNode node, final String prefixo, final int operation){
		final IContainerObject _prefixo = getOrAddContainerObject(prefixo, node);
		final IContainerObject _ui      = getOrAddContainerObject(DSN.UI_CONTAINER, _prefixo);
		final IContainerObject _title   = getOrAddContainerObject(DSN.TITLE_CONTAINER, _ui);
		
		final ISimpleObject _stitle = getOrAddSimpleObject(DSN.OPERATION, _title);
		_stitle.setValue(getLocalize().localize(operation));
		
		//em caso de ser tempo de desenvolvimento, aplicar versão
		if(TerminalFinanceiro.isDevelopment()){
			final String _version = Application.getInstance().valueOf(String.class, APPLICATION.VERSION_PROPERTY);
			_stitle.setValue(_stitle.getValue() + STRING.SPACE1 + STRING.PARENTESES_ABRE + _version + STRING.HIFEM_SPACE + getArquitetura().getAmbiente().getCodigo() + STRING.PARENTESES_FECHA);
			
		}
		
		return _ui;
	}
	
	public void clear(final IDataObject object){
		
		if(object instanceof ISimpleObject){
			clear((ISimpleObject)object);
			
		} else if(object instanceof IListObject){
			clear((IListObject)object);
		}
	}
	
	public void clear(final ISimpleObject simple){
		if(null!= simple){
			simple.setValue(STRING.EMPTY);
		}
	}
	
	public void clear(final IListObject list){
		if(null!= list){
			list.reset();
		}
	}
	
	public IContainerObject doBradescoComboContainer(final IDataServiceNode node, final String nome){
		
		final IContainerObject _ui     = getOrAddContainerObject(DSN.UI_CONTAINER, node);
		final IContainerObject _result = getOrAddContainerObject(nome, _ui);
		
		getOrAddSimpleObject(DSN.SELECTED_INDEX, _result);
		getOrAddListObject(DSN.SELECT_LABEL_ITEMS, _result); 
		getOrAddListObject(DSN.SELECT_VALUE_ITEMS, _result);
		
		return _result;
	}
	
	public IContainerObject toBradescoCombo(final Entryc<?> objects, final IDataServiceNode node, final String nome, final String labelPropertyName, final String valuePropertyName) throws IllegalArgumentException {
		return toBradescoCombo(objects, node, nome, labelPropertyName, valuePropertyName, Boolean.FALSE);
	}
	
	public IContainerObject toBradescoComboTipoDocumento(final Entryc<?> objects, final IDataServiceNode node, final String labelPropertyName, final String valuePropertyName, final boolean doValuePlace) throws IllegalArgumentException {
		return toBradescoCombo(objects, node, DSN.DEFAULT_LISTBOX_TIPO_DOCUMENTO, labelPropertyName, valuePropertyName, doValuePlace);
	}
	
	public IContainerObject toBradescoCombo(final Entryc<?> objects, final String nome, final String labelPropertyName, final String valuePropertyName, final boolean doValuePlace, final TransferBeanWrapper wrapper) throws IllegalArgumentException {
		
		final IDataServiceNode _node = wrapper.get(BUSINESS.DATA_SERVICE_NODE, IDataServiceNode.class);
		if(null!= _node){
			return toBradescoCombo(objects, _node, nome, labelPropertyName, valuePropertyName, doValuePlace);
		} else {
			throw new IllegalArgumentException(BUSINESS.DATA_SERVICE_NODE);
		}
	}
	
	public IContainerObject toBradescoCombo(final Entryc<?> objects, final IDataServiceNode node, final String nome, final String labelPropertyName, final String valuePropertyName, final boolean doValuePlace) throws IllegalArgumentException {
		return toBradescoCombo(objects, node, nome, labelPropertyName, valuePropertyName, doValuePlace, Boolean.TRUE);		
	}
	
	public IContainerObject toBradescoCombo(final Entryc<?> objects, final IDataServiceNode node, final String nome, final String labelPropertyName, final String valuePropertyName, final boolean doValuePlace, final boolean doSelectFirst) throws IllegalArgumentException {
		return toBradescoCombo(objects, node, nome, labelPropertyName, valuePropertyName, doValuePlace, doSelectFirst, INTEGER._0);
	}
	
	public IContainerObject toBradescoCombo(final Entryc<?> objects, final IDataServiceNode node, final String nome, final String labelPropertyName, final String valuePropertyName, final boolean doValuePlace, final boolean doSelectFirst, final int firstEmpty) throws IllegalArgumentException {
		
		final IContainerObject _combo = doBradescoComboContainer(node, nome);
		final IListObject _labels = _combo.getListObject(DSN.SELECT_LABEL_ITEMS);
		_labels.reset();
		
		final IListObject _values = _combo.getListObject(DSN.SELECT_VALUE_ITEMS);
		_values.reset();
		
		//primeiro item fica vazio
		objects.setOffset(INTEGER._1);
		_labels.addSimpleObject().setValue(STRING.EMPTY);
		_values.addSimpleObject().setValue(String.valueOf(firstEmpty));
		for(Object _object : objects.getEntries()){
			
			try{
				final Object _label = reflection.valueOf(_object, labelPropertyName);
				final Object _value = reflection.valueOf(_object, valuePropertyName);
				
				if(null!= _label){
					String __label     = _label.toString();
					final String _i18n = localize.mapped(_object);
					if(null!= _i18n){
						__label = _i18n;
					}
					_labels.addSimpleObject().setValue(__label);
					
				} else {
					_labels.addSimpleObject().setValue(I18N.NULL_LABEL);
				}
				
				if(null!= _value){
					setValue(_values.addSimpleObject(), _value, _value.getClass());
				} else {
					_values.addSimpleObject().setValue(I18N.NULL_VALUE);
				}
			}catch(ReflectionException _e){
				throw new IllegalArgumentException(_e.getMessage(), _e);
			}
		}
		
		final ISimpleObject _index = _combo.getSimpleObject(DSN.SELECTED_INDEX);
		if(doSelectFirst){
			_index.setIntValue(objects.getSelectedIndex());
		} else {
			_index.setIntValue(INTEGER._0);
		}
		
		if(doValuePlace){			
			final ValuePlace<Integer> _vplace = new DSNPlaceInteger(nome, _combo.getSimpleObject(DSN.SELECTED_INDEX));
			
			mediator.getObjectPool().put(_combo.getFullKey(), _vplace);
		}
		
		return _combo;		
	}
	
	public IDataObject getDataObject(final String id, final IDataObject container){
		
		IDataObject _result = container;
		final String[] _slices = id.split(REGEX.DOT);
		
		for(String _slice : _slices){
			_result = _result.getDataObject(_slice);
		}
		
		if(_slices.length == INTEGER._0){
			_result = null;
		}
		
 		return _result;
	}
	
	public int count(final IDataServiceNode node, final String name){
		
		int _result = INTEGER._0;
		
		final IDataServiceNode _parent = node.getParentNode();
		if(null!= _parent){
			
			for(IDataServiceNode _object : _parent.getChildDataServiceNodes()){
				if(name.equals(_object.getNodeName())){
					_result++;
				}
			}
		}
		
		return _result;
	}
	
	public boolean checked(final IDataServiceNode node, final String prefixo, final String checkbox, final int index){
		
		boolean _result = Boolean.FALSE;
		final IContainerObject _root = getOrAddContainerObject(prefixo, node);
		final IContainerObject _ui   = getOrAddContainerObject(DSN.UI_CONTAINER, _root);
		final ISimpleObject _cbox    = getOrAddSimpleObject(checkbox + index, _ui);
		
		_result = format.toBoolean(_cbox.getValue());
		
		return _result;
	}
	
	public void checked(final IDataServiceNode node, final String prefixo, final String checkbox, final int index, final boolean value){
		
		final IContainerObject _root = getOrAddContainerObject(prefixo, node);
		final IContainerObject _ui   = getOrAddContainerObject(DSN.UI_CONTAINER, _root);
		final ISimpleObject _cbox    = getOrAddSimpleObject(checkbox + index, _ui);
		
		_cbox.setBooleanValue(value);
	}
	
	public OPERATION operationOf(final IDataServiceNode node){
		OPERATION _result = null;
		
		final String _name = node.getNodeName();
		final String _projeto = Application.getInstance().valueOf(String.class, Constants.APPLICATION.NAME_PROPERTY);
		if(_name.contains(_projeto)){
			
			final String _operation = _name.substring(_projeto.length() + INTEGER._1);
			_result = application.operationOf(_operation);
			
		} else {
			final IDataServiceNode _parent = node.getParentNode();
			if(null!= _parent){
				_result = operationOf(_parent);
			}
		}
		
		return _result;
	}
	
	public boolean contains(final IListObject list, final String basek, final String value){
		boolean _result = Boolean.FALSE;
		
		for(int _index = INTEGER._0; _index < list.size(); _index++){
			final IDataObject _item = list.getDataObject(_index);
			
			Object _value = null;
			
			if(_item instanceof ISimpleObject){
				final ISimpleObject _simple = (ISimpleObject)_item;
				_value = _simple.getValue();
				
			} else if(_item instanceof IContainerObject) {
				final IContainerObject _container = (IContainerObject)_item;
				
				if(null!= basek && !STRING.EMPTY.equals(basek)){
					final IDataObject _dob = _container.getDataObject(basek);
					if(_dob instanceof ISimpleObject){
						final ISimpleObject _sob = (ISimpleObject)_dob;
						_value = _sob.getValue();
					} else {
						throw new UnsupportedOperationException();
					}
				} else {
					throw new UnsupportedOperationException();
				}
			} else {
				throw new UnsupportedOperationException();
			}
			
			_result = value.equals(_value);
			if(_result){
				break;
			}
		}
		
		return _result;
	}
	
	public DSNPlaceString doField(final int label, final ISimpleObject simple, final IEventsServiceProvider provider){
		return doField(Localization.getInstance().localize(label), simple);
	}
	
	public DSNPlaceString doField(final String label, final ISimpleObject simple){
		return new DSNPlaceString(label, simple);
	}
	
	public DSNPlaceBoolean doBooleanField(final String label, final ISimpleObject simple){
		return new DSNPlaceBoolean(label, simple);
	}
}
