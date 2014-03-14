package bradesco.tf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.exceptions.BradescoBranchException;
import br.com.bradesco.core.aq.service.IEvent;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import br.com.bradesco.core.ui.beans.basedialog.BradescoDialog;
import bradesco.tf.TFConstants.EVENT;
import bradesco.tf.TFConstants.LAYOUT;
import bradesco.tf.TFConstants.VALIDATION;
import bradesco.tf.layout.UtilLayout;
import bradesco.tf.validate.DataServiceNodeValidatorVisitor;
import bradesco.tf.validate.Event;
import bradesco.tf.validate.EventValidator;
import bradesco.tf.validate.IValidatorMediator;
import bradesco.tf.validate.Loader;
import bradesco.tf.validate.NullValidatorMediator;
import bradesco.tf.validate.Operation;
import bradesco.tf.validate.Pesquisar;
import bradesco.tf.validate.Validation;
import bradesco.tf.validate.Validator;
import bradesco.tf.validate.ValidatorHelper;
import bradesco.tf.validate.ValidatorMediator;
import bradesco.tf.validate.ValidatorMediatorLayoutFocuserDecorator;
import bradesco.tf.validate.View;

import com.javaf.ObjectPool;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.Localization;
import com.javaf.model.CruddType;
import com.javaf.model.IDynamic;
import com.javaf.pattern.Visitor;

/**
 * Implementação que suprime diversos pontos programáticos no arranjo, configurações, manipulação, etc., de itens relacionados aos layouts.
 * @author fabiojm
 *
 */
@SuppressWarnings("deprecation")
public class LayoutHelper {

	private final UtilLayout        layout       = UtilLayout.getInstance();
	private final TerminalFinanceiro arquitetura = TerminalFinanceiro.getInstance();
	private final UtilIdentify identify          = UtilIdentify.getInstance();
	private final UtilReflection reflection      = UtilReflection.getInstance();
	private final UtilFormat format              = UtilFormat.getInstance();
	private final ValidatorHelper vhelper        = new ValidatorHelper();
	private final ShowMessage show               = ShowMessage.getInstance();
	
	public String doArrange(final IEventsServiceProvider provider, final IDataServiceNode node, final CruddType type, final String...componentes){
		
		final boolean _isEnabled = Boolean.FALSE;
		final ILogging _logging = Logging.loggerOf(getClass());
		
		try{
			layout.setEnabled(node, provider, _isEnabled, LAYOUT.BOTAO_DETALHAR);
		}catch(BradescoBranchException _e){
			_logging.warn(Localization.getInstance().localize(I18N.BOTAO_NAO_EXISTE_LAYOUT, LAYOUT.BOTAO_DETALHAR, node.getNodeName()));
		}
		
		try{
			layout.setEnabled(node, provider, _isEnabled, LAYOUT.BOTAO_ALTERAR);
		}catch(BradescoBranchException _e){
			_logging.warn(Localization.getInstance().localize(I18N.BOTAO_NAO_EXISTE_LAYOUT, LAYOUT.BOTAO_ALTERAR, node.getNodeName()));
		}
		
		try{
			layout.setEnabled(node, provider, _isEnabled, LAYOUT.BOTAO_EXCLUIR);
		}catch(BradescoBranchException _e){
			_logging.warn(Localization.getInstance().localize(I18N.BOTAO_NAO_EXISTE_LAYOUT, LAYOUT.BOTAO_EXCLUIR, node.getNodeName()));
		}
		
		try{
			layout.setEnabled(node, provider, _isEnabled, LAYOUT.BOTAO_VISUALIZAR);
		}catch(BradescoBranchException _e){
			_logging.warn(Localization.getInstance().localize(I18N.BOTAO_NAO_EXISTE_LAYOUT, LAYOUT.BOTAO_VISUALIZAR, node.getNodeName()));
		}
		
		try{
			layout.setEnabled(node, provider, _isEnabled, LAYOUT.BOTAO_DESBLOQUEAR);
		}catch(BradescoBranchException _e){
			_logging.warn(Localization.getInstance().localize(I18N.BOTAO_NAO_EXISTE_LAYOUT, LAYOUT.BOTAO_DESBLOQUEAR, node.getNodeName()));
		}
		
		try{
			layout.setEnabled(node, provider, _isEnabled, LAYOUT.BOTAO_DOCUMENTOS);
		}catch(BradescoBranchException _e){
			_logging.warn(Localization.getInstance().localize(I18N.BOTAO_NAO_EXISTE_LAYOUT, LAYOUT.BOTAO_DOCUMENTOS, node.getNodeName()));
		}
		
		if(CruddType.READ.equals(type)){
			try{
				layout.setEnabled(node, provider, _isEnabled, LAYOUT.BOTAO_MAIS_RESULTADOS);
			}catch(BradescoBranchException _e){
				_logging.warn(Localization.getInstance().localize(I18N.BOTAO_NAO_EXISTE_LAYOUT, LAYOUT.BOTAO_MAIS_RESULTADOS, node.getNodeName()));
			}
		}
		
		if(null!= componentes){
			for(String _componente : componentes){
				try{
					layout.setEnabled(node, provider, _isEnabled, _componente);
				}catch(BradescoBranchException _e){
					_logging.warn(Localization.getInstance().localize(I18N.BOTAO_NAO_EXISTE_LAYOUT, _componente, node.getNodeName()));
				}
			}
		}
		
		return EVENT.EXECUTADO;
	}
	
	public IValidatorMediator doValidator(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final Class<? extends BradescoDialog> layout) throws ResourceException{
		 
		IValidatorMediator _result;
		final ILogging _logging = Logging.loggerOf(getClass());
		
		final ObjectPool _opool = arquitetura.getObjectPool(provider);
		final String _id        = identify.doValidador(layout);
		
		_result = _opool.get(_id, IValidatorMediator.class);
		if(null== _result){
			_result = new ValidatorMediatorLayoutFocuserDecorator( new ValidatorMediator() );
			
			final View _view;
			try{
				_view = Loader.view(layout);
				final Visitor<Validator, Validator> _visitor = new DataServiceNodeValidatorVisitor(provider, operation, node);
				
				final List<Validation> _validations = new ArrayList<Validation>();
				if(format.isDefault(_view.getValidationsId())){
					_validations.addAll(_view.getValidations().getValidation());
					
				} else {
					_validations.addAll(Loader.validations(_view.getValidationsId()).getValidation());
				}
				
				for(Validation _validation : _validations){
					Validator _validator = null;
					
					if(!format.isDefault(_validation.getValidatorId())){
						_validator = Loader.validator(_validation.getValidatorId());
						if(null== _validator){
							throw new ResourceException(I18N.IDENTIFICADOR_VALIDADOR_NAO_ENCONTRADO_NAS_CONFIGURACOES + _validation.getValidatorId());
						}
					} else {
						_validator = reflection.clone( _validation.getValidator() );
					}
					
					//ligar validador
					_validator.setTurnedOn(VALIDATION.LOAD_EAGER.equals( _validation.getLoadPolicy() ));
					
					//visitor para configurar mediação entre validador e DSN
					_visitor.visit(_validator);
					
					//armazenar no mediator
					_result.put(_validator.getId(), _validator);
				}
				
			} catch(ResourceException _e) {
				_logging.warn(_e.getMessage(), _e);
				
				_result = new NullValidatorMediator();
			}
			
			//armazenar o mediador de validação
			_opool.put(_id, _result);
		}
		
		vhelper.onDecorated(_result, provider, operation, node);
		
		return _result;
	}
	
	public <T extends IEvent> IValidatorMediator doValidator(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node, final T event) throws ResourceException{
		
		IValidatorMediator _result;
		final ILogging _logging = Logging.loggerOf(getClass());
		
		final ObjectPool _opool = arquitetura.getObjectPool(provider);
		final String     _id    = event.getClass().getName();
		
		_result = _opool.get(_id, IValidatorMediator.class);
		if(null== _result){			
			final Event _event;
			final Operation _operation;
			try{
				_operation = Loader.operation(operation.getOperationProjectBaseClass());
				_event     = Loader.match(_operation, event);
				
				if(_event instanceof EventValidator){
					_result = new ValidatorMediatorLayoutFocuserDecorator( new ValidatorMediator() );
					
					final Visitor<Validator, Validator> _visitor = new DataServiceNodeValidatorVisitor(provider, operation, node);
					final EventValidator _evalidator = (EventValidator)_event;
					
					if(!format.isDefault(_evalidator.getValidatorId())){
						for(String _vid : _evalidator.getValidatorId()){
							
							final Validator _validator = Loader.validator(_vid);
							if(null== _validator){
								throw new ResourceException(I18N.IDENTIFICADOR_VALIDADOR_NAO_ENCONTRADO_NAS_CONFIGURACOES + _vid);
							}
							
							_visitor.visit(_validator);
							_validator.setTurnedOn(Boolean.TRUE);
							
							_result.put(_vid, _validator);
						}
					} else {
						if(!format.isDefault(_evalidator.getValidator())){
							for(Validator __validator : _evalidator.getValidator()){
								final Validator _validator = reflection.clone(__validator);
								_visitor.visit(_validator);
								_validator.setTurnedOn(Boolean.TRUE);
								
								_result.put(_validator.getId(), _validator);
							}
						} else {
							throw new ResourceException(I18N.NENHUMA_CONFIGURACAO_DE_VALIDADORES_ENCONTRADA_NO_EVENT_VALIDATOR);
						}
					}
					
				} else {
					_result = new NullValidatorMediator();
				}
			}catch(ResourceException _e){
				_logging.warn(_e.getMessage(), _e);
				
				_result = new NullValidatorMediator();
				
			}
			
			//armazenar o mediador de validação
			_opool.put(_id, _result);
		}
		
		vhelper.onDecorated(_result, provider, operation, node);
		
		return _result;
	}
	
	public Operation getOperation(final IEventsServiceProvider provider, final IOperation operation) throws ResourceException{
		
		Operation _result = null;
		final ObjectPool _opool = arquitetura.getObjectPool(provider);
		final String _id = identify.doOperation(operation);
		final String _package = operation.getOperationProjectBaseClass();
		
		_result = _opool.get(_id, Operation.class);
		if(null== _result){
			_result = Loader.operation(_package);
			
			if(null== _result){
				throw new ResourceException(I18N.NAO_FOI_ENCONTRADA_CONFIGURACAO_PARA_OPERACAO + _package);
			}
			
			//armazenar no pool para consultas posteriores
			_opool.put(_id, _result);
		}
		
		return _result;
	}
	
	private Class<? extends BradescoDialog> view;
	@SuppressWarnings("unchecked")
	public <T extends IEvent> Class<? extends BradescoDialog> getView(final IEventsServiceProvider provider, final IOperation operation, final T event) throws ResourceException{
		
		if(null== view){
			final Operation _operation = getOperation(provider, operation);
			
			final Event _event = Loader.match(_operation, event);
			
			String _view   = _event.getViewClass();
			if(format.isDefault(_view)){
				_view = Loader.view( _event.getViewId() ).getViewClass();
			}
			
			try{
				view = (Class<? extends BradescoDialog>)Class.forName(_view);
				
			}catch(ClassNotFoundException _e){
				throw new ResourceException(I18N.NAO_FOI_ENCONTARDA_CLASSE_DE_VIEW + _view, _e);
			}
		}
		
		return view;
	}
	
	@SuppressWarnings("unchecked")
	public Class<? extends IDynamic> getModel(final IEventsServiceProvider provider, final IOperation operation) throws ResourceException{
		
		Class<? extends IDynamic> _result = null;
		final ObjectPool _opool = arquitetura.getObjectPool(provider);
		final String _id = identify.doInstanciaType(operation);
		
		final Operation _operation = getOperation(provider, operation);
		final String _model = _operation.getModelClass();
		
		_result = (Class<? extends IDynamic>)_opool.get(_id);
		if(null== _result){
			try{
				
				_result = (Class<? extends IDynamic>)Class.forName(_model);
				_opool.put(_id, _result);
				
			}catch(ClassNotFoundException _e){
				throw new ResourceException(I18N.NAO_FOI_ENCONTRADA_CLASSE_MODEL_CONFIGURADA + _model, _e);
				
			}catch(ClassCastException _e){
				throw new ResourceException(I18N.CLASSE_CONFIGURADA_FILTRO_NAO_ESTENDE + Serializable.class + STRING.DOIS_PONTOS + _model, _e);
			}
		}
		
		return _result;
	}
	
	public Class<?> getHelper(final IEventsServiceProvider provider, final IOperation operation) throws ResourceException {
		Class<?> _result = null;
		
		final ObjectPool _opool = arquitetura.getObjectPool(provider);
		final String _id = identify.doHelperType(operation);
		
		final Operation _operation = getOperation(provider, operation);
		final String _helper = _operation.getHelperClass();
		
		_result = (Class<?>)_opool.get(_id);
		
		if(null== _result && 
				!format.toString(_helper).equals(STRING.EMPTY)){
			try{
				
				_result = (Class<?>)Class.forName(_helper);
				_opool.put(_id, _result);
				
			}catch(ClassNotFoundException _e){
				throw new ResourceException(I18N.NAO_FOI_ENCONTRADA_CLASSE_HELPER_CONFIGURADA + _helper, _e);
			}
		}
		
		return _result;		
	}
	
	@SuppressWarnings("unchecked")
	public Class<? extends IDynamic> getFiltro(final IEventsServiceProvider provider, final IOperation operation) throws ResourceException {
		
		Class<? extends IDynamic> _result = null;
		
		final Operation _operation = getOperation(provider, operation);
		final Pesquisar _pesquisar = _operation.getPesquisar();
		if(!format.isDefault(_pesquisar)){
			final String _fclazz = _pesquisar.getFiltroClass();
			
			try{
				
				_result = (Class<? extends IDynamic>)Class.forName(_fclazz);
				
			}catch(ClassNotFoundException _e){
				throw new ResourceException(I18N.NAO_FOI_ENCONTRADA_CLASSE_FILTRO_PEQUISAR_CONFIGURADA + _fclazz, _e);
				
			}catch(ClassCastException _e){
				throw new ResourceException(I18N.CLASSE_CONFIGURADA_FILTRO_NAO_ESTENDE + Serializable.class + STRING.DOIS_PONTOS + _fclazz, _e);
			}
		} else {
			//por definição, o model da operação é o filtro default
			_result = getModel(provider, operation);
		}
		
		return _result;
	}
	
	public Pesquisar getPesquisar(final IEventsServiceProvider provider, final IOperation operation) throws ResourceException {		
		final Operation _operation = getOperation(provider, operation);
		final Pesquisar _result    = _operation.getPesquisar();
		
		return _result;
	}
	
	public boolean isConfirmed(final IEventsServiceProvider provider, final IDataServiceNode node, final CruddType type){
		boolean _result = Boolean.FALSE;
		
		Object _message = INTEGER._0;
		if(CruddType.CREATE.equals(type)){
			
			_message = I18N.CONFIRMAR_INCLUSAO;
			
		} else if(CruddType.UPDATE.equals(type)){
			
			_message = I18N.CONFIRMAR_ALTERACAO;
			
		} else if(CruddType.DELETE.equals(type)){
			
			_message = I18N.CONFIRMAR_EXCLUSAO;
		}
			
		_result = TFConstants.MESSAGE.SIM == show.yesnot(_message, node);
		
		return _result;
	}
}
