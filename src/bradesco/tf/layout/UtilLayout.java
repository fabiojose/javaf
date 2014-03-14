package bradesco.tf.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.bradesco.core.aq.dataservice.IContainerObject;
import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.dataservice.ISimpleObject;
import br.com.bradesco.core.aq.exceptions.BradescoBranchException;
import br.com.bradesco.core.aq.service.IArquitecturalActions;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import br.com.bradesco.core.ui.beans.basedialog.BradescoDialog;
import bradesco.tf.OPERATION;
import bradesco.tf.ShowMessage;
import bradesco.tf.TFConstants;
import bradesco.tf.UtilIdentify;
import bradesco.tf.TFConstants.DSN;
import bradesco.tf.TFConstants.EVENT;
import bradesco.tf.TFConstants.LAYOUT;
import bradesco.tf.TFConstants.MESSAGE;
import bradesco.tf.dsn.UtilDSN;

import com.javaf.ObjectPool;
import com.javaf.Constants.CHAR;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.REGEX;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.RemovePolicy;
import com.javaf.javase.lang.UtilString;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;
import com.javaf.model.GenericPair;

/**
 * Implementa métodos facilitadores para trabalho com os componentes introduzidos nos layouts.
 * @author fabiojm - Fábio José de Moraes
 */
@SuppressWarnings("deprecation")
public final class UtilLayout {
	private static final UtilLayout INSTANCE = new UtilLayout();
	
	private final UtilDSN dsn;
	private final UtilIdentify identify;
	private final UtilFormat format;
	private final UtilString string;
	private final ObjectPool opool;
	private final ILocalization localization;
	private final ShowMessage show;
	
	private UtilLayout(){
		dsn          = UtilDSN.getInstance();
		identify     = UtilIdentify.getInstance();
		format       = UtilFormat.getInstance();
		string       = UtilString.getInstance();
		opool        = ObjectPool.getPool();
		localization = Localization.getInstance();
		show         = ShowMessage.getInstance();
	}
	
	/**
	 * Obter uma instância de trabalho
	 */
	public static synchronized UtilLayout getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Permite atribuir valor a qualquer atribute <code>boolean</code> ao componente de tela.
	 * @param node instância do Data Service Node
	 * @param actions implementação do IArquitecturalActions
	 * @param componente identificador do componente no layout
	 * @param attribute nome do atributo
	 * @param value valor boolean do atributo
	 */
	public void setBeanAttribute(final IDataServiceNode node, final IArquitecturalActions actions, final String componente, final String attribute, final boolean value) throws BradescoBranchException {
		actions.setBeanAttribute(node, componente, attribute, value);
	}
	
	/**
	 * Requisitar o foco para uma determinado componente
	 * @param node
	 * @param provider
	 * @param componente
	 */
	public void setRequestFocus(final IDataServiceNode node, final IEventsServiceProvider provider, final String componente) {
		setBeanAttribute(node, provider.getArquitecturalActions(), componente, IArquitecturalActions.REQUEST_FOCUS, Boolean.TRUE);
	}
	
	/**
	 * Determinar a obrigatoriodade de um determinado componente
	 * @param node
	 * @param provider
	 * @param componente
	 * @param mandatory <code>true</code> preenchimento obrigatório, <code>false</code> preenchimento opcional
	 */
	public void setMandatory(final IDataServiceNode node, IEventsServiceProvider provider, final String componente, final boolean mandatory) {
		setBeanAttribute(node, provider.getArquitecturalActions(), componente, IArquitecturalActions.MANDATORY, mandatory);
	}
	
	/**
	 * Determinar se o componente está ou não habilitado.
	 * @param node
	 * @param provider
	 * @param enabled <code>true</code> ficará habilitado, <code>false</code> ficará desabilitado
	 * @param componente
	 */
	public void setEnabled(final IDataServiceNode node, final IEventsServiceProvider provider, final boolean enabled, final String componente) {
		setBeanAttribute(node, provider.getArquitecturalActions(), componente, IArquitecturalActions.ENABLED, enabled);
	}
	
	/**
	 * Determinar se o componente está ou não habilitado, em lotes
	 * @param node
	 * @param provider
	 * @param enabled  <code>true</code> ficará habilitado, <code>false</code> ficará desabilitado
	 * @param componentes 
	 */
	public void setEnabled(final IDataServiceNode node, final IEventsServiceProvider provider, final boolean enabled, final String...componentes) {
		for(String _componente : componentes){
			setEnabled(node, provider, enabled, _componente);
		}
	}

	/**
	 * Determinar se o componente está ou não visível.
	 * @param node
	 * @param provider
	 * @param componente
	 * @param visible <code>true</code> ficará visível, <code>false</code> ficará invisível.
	 */
	public void setVisible(final IDataServiceNode node, final IEventsServiceProvider provider, final boolean visible, final String...componentes) {
		for(String _componente : componentes){
			setBeanAttribute(node, provider.getArquitecturalActions(), _componente, IArquitecturalActions.VISIBLE, visible);
		}
	}
	
	public void refreshTable(final IEventsServiceProvider provider, final IDataServiceNode node, final String componente){
		provider.getArquitecturalActions().getTableFunctions().refreshSelected(node, componente);
	}
	
	public void refreshTable(final IEventsServiceProvider provider, final IDataServiceNode node){
		
		try{
			refreshTable(provider, node, LAYOUT.TABLE_RESULTADOS);
			
		}catch(BradescoBranchException _e){
		}
		
		try{
			refreshTable(provider, node, LAYOUT.TABLE_RESULTADOS + STRING._1);
			
		}catch(BradescoBranchException _e){
		}
		
		try{
			refreshTable(provider, node, LAYOUT.TABLE_RESULTADOS + STRING._2);
			
		}catch(BradescoBranchException _e){
		}
		
		try{
			refreshTable(provider, node, LAYOUT.TABLE_RESULTADOS + STRING._3);
			
		}catch(BradescoBranchException _e){
		}
		
		try{
			refreshTable(provider, node, LAYOUT.TABLE_RESULTADOS + STRING._4);
			
		}catch(BradescoBranchException _e){
		}
		
	}
	
	/**
	 * Determinar se o componente poderá ter seu valor editado.
	 * @param node
	 * @param provider
	 * @param componente
	 * @param editable <code>true</code> poderá ser editado, <code>false</code> será apenas para leitura.
	 */
	public void setEditable(final IDataServiceNode node, final IEventsServiceProvider provider, final String componente, final boolean editable) {
		setBeanAttribute(node, provider.getArquitecturalActions(), componente, IArquitecturalActions.EDITABLE, editable);
	}
	
	public void setFocus(final IEventsServiceProvider provider, final IDataServiceNode node, final String componente){
		setBeanAttribute(node, provider.getArquitecturalActions(), componente, IArquitecturalActions.REQUEST_FOCUS, Boolean.TRUE);
	}
	
	public String pathForward(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final String step){
		
		final ISimpleObject _path = dsn.doPath(provider, operation, node);
		String _result = _path.getValue();
		_result = (null!= _result && !STRING.EMPTY.equals(_result) ? _result + LAYOUT.PATH_SEPARATOR + step : step);
		
		_path.setValue(_result);
		
		return _result;
	}
	
	public String pathForward(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final int step){
		return pathForward(provider, operation, node, Localization.getInstance().localize(step));
	}
	
	public String pathBack(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final String step){
		
		final ISimpleObject _path = dsn.doPath(provider, operation, node);
		String _result = _path.getValue();
		if(_result.length() > (LAYOUT.PATH_SEPARATOR.length() + step.length())){
			_result = _result.substring(INTEGER._0, _result.length() - (LAYOUT.PATH_SEPARATOR.length() + step.length()) );
		} else {
			_result = STRING.EMPTY;
		}
		
		_path.setValue(_result);
		
		return _result;
	}
	
	public String pathBack(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final int step){
		return pathBack(provider, operation, node, Localization.getInstance().localize(step));
	}
	
	public String pathCurrent(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		
		final ISimpleObject _simple = dsn.doPath(provider, operation, node);
		return _simple.getValue();
	}
	
	public boolean checked(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final String checkbox){
		
		boolean _result = Boolean.FALSE;
		
		final String _prefixo         = opool.get(identify.doPrefixo(operation), String.class);
		final IContainerObject _root  = dsn.getOrAddContainerObject(_prefixo, node);
		final IContainerObject _ui    = dsn.getOrAddContainerObject(DSN.UI_CONTAINER, _root);
		
		final ISimpleObject _checkbox = _ui.getSimpleObject(checkbox);
		if(null!= _checkbox){
			_result = format.toBoolean(_checkbox.getValue());
		}
		
		return _result;
	}
	
	public void doChecked(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final String checkbox, final boolean checked){
		
		final String _prefixo         = opool.get(identify.doPrefixo(operation), String.class);
		final IContainerObject _root  = dsn.getOrAddContainerObject(_prefixo, node);
		final IContainerObject _ui    = dsn.getOrAddContainerObject(DSN.UI_CONTAINER, _root);
		
		final ISimpleObject _checkbox = _ui.getSimpleObject(checkbox);
		if(null!= _checkbox){
			_checkbox.setBooleanValue(checked);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void doVisible(final IEventsServiceProvider provider, final IOperation operation, final Class<? extends BradescoDialog> layout, final boolean isVisible, final String...componentes){
		
		final String _key = identify.doLayoutVisible(operation, layout, isVisible);
		
		final GenericPair<Boolean, List<String>> _configure = opool.getOrCreate(_key, GenericPair.class);
		List<String> _componentes = _configure.getValue2();
		if(null== _componentes){
			_componentes = new ArrayList<String>();
			_configure.setValue2(_componentes);
		}
		
		_configure.setValue1(isVisible);
		_componentes.clear();
		
		for(String _c : componentes){
			if(!_componentes.contains(_c)){
				_componentes.add(_c);
			}
		}
	}
	
	public void unVisible(final IEventsServiceProvider provider, final IOperation operation, final Class<? extends BradescoDialog> layout){

		final String _keyt       = identify.doLayoutVisible(operation, layout, Boolean.TRUE);
		final String _keyf       = identify.doLayoutVisible(operation, layout, Boolean.FALSE);
		
		opool.remove(_keyt);
		opool.remove(_keyf);
	}
	
	@SuppressWarnings("unchecked")
	public GenericPair<Boolean, List<String>> getVisible(final IEventsServiceProvider provider, final IOperation operation, final Class<? extends BradescoDialog> layout, final boolean isVisible){
		
		GenericPair<Boolean, List<String>> _result = null;
		
		final String _key = identify.doLayoutVisible(operation, layout, isVisible);
		
		_result = opool.get(_key, GenericPair.class);
		
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	public void doEnabled(final IEventsServiceProvider provider, final IOperation operation, final Class<? extends BradescoDialog> layout, final boolean isEnabled, final String...componentes){

		final String _key = identify.doLayoutEnabled(operation, layout, isEnabled);
		
		final GenericPair<Boolean, List<String>> _configure = opool.getOrCreate(_key, GenericPair.class);
		List<String> _componentes = _configure.getValue2();
		if(null== _componentes){
			_componentes = new ArrayList<String>();
			_configure.setValue2(_componentes);
		}
		
		_configure.setValue1(isEnabled);
		_componentes.clear();
		
		for(String _c : componentes){
			if(!_componentes.contains(_c)){
				_componentes.add(_c);
			}
		}
	}
	
	public void unEnabled(final IEventsServiceProvider provider, final IOperation operation, final Class<? extends BradescoDialog> layout){

		final String _keyt       = identify.doLayoutEnabled(operation, layout, Boolean.TRUE);
		final String _keyf       = identify.doLayoutEnabled(operation, layout, Boolean.FALSE);
		
		opool.remove(_keyt);
		opool.remove(_keyf);
	}
	
	@SuppressWarnings("unchecked")
	public GenericPair<Boolean, List<String>> getEnabled(final IEventsServiceProvider provider, final IOperation operation, final Class<? extends BradescoDialog> layout, final boolean isEnabled){
		GenericPair<Boolean, List<String>> _result = null;
		
		final String _key = identify.doLayoutEnabled(operation, layout, isEnabled);
		
		_result = opool.get(_key, GenericPair.class);
		
		return _result;
	}
	
	/**
	 * Somente configura um novo label caso ele não exista.
	 * 
	 * @param provider
	 * @param operation
	 * @param label
	 */
	public void doLabel(final IEventsServiceProvider provider, final IOperation operation, final String label, final int index){
		
		final String _id        = identify.doLabel(operation, index);
		
		if(!opool.has(_id)){
			opool.put(_id, label);
		}
	}
	
	/**
	 * Somente configura um novo label caso ele não exista.
	 * 
	 * @param provider
	 * @param operation
	 * @param label
	 */
	public void doLabel(final IEventsServiceProvider provider, final OPERATION operation, final String label, final int index){
		
		final String _id        = identify.doLabel(operation, index);
		
		if(!opool.has(_id)){
			opool.put(_id, label);
		}
	}
	
	/**
	 * Somente configura um novo label caso ele não exista.
	 * 
	 * @param provider
	 * @param operation
	 * @param label
	 */
	public void doLabel(final IEventsServiceProvider provider, final IOperation operation, final int label, final int index){
		doLabel(provider, operation, localization.localize(label), index);
	}
	
	/**
	 * Somente configura um novo label caso ele não exista.
	 * 
	 * @param provider
	 * @param operation
	 * @param label
	 */
	public void doLabel(final IEventsServiceProvider provider, final OPERATION operation, final int label, final int index){
		doLabel(provider, operation, localization.localize(label), index);
	}

	public String label(final IOperation operation, final int index){
		String _result = STRING.EMPTY;
		
		final String _id        = identify.doLabel(operation, index);
		final Object _value     = opool.get(_id);
		if(null!= _value){
			_result = (String)_value;
		}
		
		return _result;
	}
	
	public Map<String, String> label(final OPERATION operation){
		final Map<String, String> _result = new HashMap<String, String>();
		
		final String _prefix    = identify.doLabelPrefix(operation);
		final Set<Object> _keys = opool.keySet();
		for(Object _key : _keys){
			if(null!= _key){
				final String _skey = _key.toString();
				
				if(_skey.startsWith(_prefix)){
					_result.put(_skey.substring(operation.getNome().length() + INTEGER._1), opool.get(_key, String.class));
				}
			}
		}
		
		return _result;
	}
	
	public Map<String, String> label(final IEventsServiceProvider provider, final IOperation operation){
		final Map<String, String> _result = new HashMap<String, String>();
		
		final String _prefix    = identify.doLabelPrefix(operation);
		final Set<Object> _keys = opool.keySet();
		for(Object _key : _keys){
			if(null!= _key){
				final String _skey = _key.toString();
				
				if(_skey.startsWith(_prefix)){
					_result.put(_skey.substring(operation.getProjectOperationBean().getOperationAlias().length() + INTEGER._1), opool.get(_key, String.class));
				}
			}
		}
		
		return _result;
	}
	
	public String holders(final OPERATION operation, final String source){
		String _result = source;
		
		if(string.hasHolder(source) && null!= source){
			final Map<String, String> _labels = label(operation);
			final Set<String> _keys           = _labels.keySet();
			for(String _key : _keys){
				_result = _result.replace(string.holderOf(_key), _labels.get(_key));
			}
		}
		
		return _result;
	}
	
	public String holders(final IEventsServiceProvider provider, final IOperation operation, final String source){
		String _result = source;
		
		if(string.hasHolder(source) && null!= source){
			final Map<String, String> _labels = label(provider, operation);
			final Set<String> _keys           = _labels.keySet();
			for(String _key : _keys){
				_result = _result.replace(string.holderOf(_key), _labels.get(_key));
			}
		}
		
		return _result;
	}
	
	public String toLayout(final String identify, final String...toRemove){
		
		final StringBuilder _builder = new StringBuilder(STRING.EMPTY);
		
		String _identify = string.remove(identify, RemovePolicy.FIRST, toRemove);
		_identify        = string.remove(_identify, DSN.UI_CONTAINER + STRING.DOT);
		_identify        = string.remove(_identify, DSN.SELECT_LABEL_ITEMS);
		_identify        = string.remove(_identify, DSN.SELECT_VALUE_ITEMS);
		_identify        = string.remove(_identify, DSN.SELECTED_INDEX);
		_identify        = string.remove(_identify, DSN.SELECTED_INDEXES);
		_identify        = string.trim(_identify, CHAR.DOT);
		
		if(null!= _identify){
			final String[] _splited = _identify.split(REGEX.DOT);
			_builder.append(_splited[INTEGER._0]);
			
			if(_splited.length > INTEGER._1){
				for(int _index = INTEGER._1; _index < _splited.length; _index++){
					final String _slice = _splited[_index];
					
					String _one = _slice.substring(INTEGER._0, INTEGER._1);
					_one = _one.toUpperCase();
					_one = _one + _slice.substring(INTEGER._1);
					
					_builder.append(_one);
				}
			}
		}
		
		return _builder.toString();
	}
	
	/**
	 * Abre um determinado layout de tela
	 * @param <T> Tipo do layout de tela
	 * @param layout Layout de tela
	 * @param node Data Service Node
	 * @param provider Provedor de Serviços
	 * @param operation Contexto de operações
	 */
	public <T extends BradescoDialog> void open(final Class<T> layout, final IDataServiceNode node, final IEventsServiceProvider provider, final IOperation operation){
		open(layout.getSimpleName(), node, provider, operation);
	}
	
	/**
	 * Abre um determinado layout de tela, baseado em seu nome simples.
	 * @param layout Nome simples do layout
	 * @param node Data Service Node
	 * @param provider Provedor de Serviços
	 * @param operation Contexto de operações
	 */
	public void open(final String layout, final IDataServiceNode node, final IEventsServiceProvider provider, final IOperation operation){
		provider.getArquitecturalActions().openDialog(node, layout, operation);
	}

	/**
	 * Força o reabertura do layout que teve seu fechamento acionado pela arquitetura.
	 * 
	 * @param provider Provedor se serviços
	 * @param operation Provedor de operações
	 * @param node Data Service Node
	 * @param layout Nome do layout
	 */
	public void reopen(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final String layout) {
		final IDataServiceNode nulo = null;
		open(layout, nulo, provider, operation);
	}

	/**
	 * Fecha o layout corrente ligado ao <code>node<code>
	 * @param provider Provedor de serviços
	 * @param node Data Service Node
	 */
	public void close(final IEventsServiceProvider provider, final IDataServiceNode node) {
		
		final Object _close = opool.get(EVENT.CLOSE);
		if(null== _close){
			provider.getArquitecturalActions().closeDialog(node);
			
		} else if(UtilFormat.getInstance().toBoolean(_close)){
			provider.getArquitecturalActions().closeDialog(node);
		}
		
		opool.remove(EVENT.CLOSE);
	}
	
	/**
	 * Implemanta o tratamento para acionamento do ESCAPE nos layouts do TF, questionando o usuário com uma mensagem padrão e mantendo o layout aberto caso não seja confirmando o cancelamento.
	 * 
	 * @param provider Provedor de serviços
	 * @param operation Provedor de operações
	 * @param node Data Service Node
	 * @param layout Nome do layout
	 * @return Comando acionado pelo usuário na caixa-de-diálogo
	 * 
	 * @see #escape(IDataServiceNode, IEventsServiceProvider)
	 */
	public int escape(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final String layout) {
		return escape(provider, operation, node, layout, I18N.CONFIRMA_CANCELAR);
	}
	
	/**
	 * Implemanta o tratamento para acionamento do ESCAPE nos layouts do TF, questionando o usuário com uma mensagem personalizada e mantendo o layout aberto caso não seja confirmando o cancelamento.
	 * 
	 * @param provider Provedor de serviços
	 * @param operation Provedor de operações
	 * @param node Data Service Node
	 * @param layout Nome do layout
	 * @param message Mensagem ao usuário
	 * @return Comando acionado pelo usuário na caixa-de-diálogo
	 * 
	 * @see #escape(IDataServiceNode, IEventsServiceProvider)
	 */
	public int escape(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final String layout, final String message) {

		final int _result = show.escape(message, MESSAGE.DEFAULT_TITLE, node);
		switch (_result) {
			case TFConstants.MESSAGE.SIM:
				// nada para fazer
				break;

			default:
				reopen(provider, operation, node, layout);

		}

		return _result;
	}
}
