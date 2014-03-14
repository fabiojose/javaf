package bradesco.tf.validate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import bradesco.tf.ResourceException;
import bradesco.tf.TFConstants;

import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.text.UtilFormat;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class Loader {
	private static Application APPLICATION_BIND;
	
	private static UtilFormat FORMAT         = UtilFormat.getInstance();
	private static UtilReflection REFLECTION = UtilReflection.getInstance();
	
	static synchronized Application getBindding(){
		if(null== APPLICATION_BIND){
			final InputStream _input = Loader.class.getResourceAsStream(TFConstants.VALIDATION.XML_BINDDING);
			if(null!= _input){
				try{
					final JAXBContext _context = JAXBContext.newInstance(TFConstants.VALIDATION.PACKAGE_BINDDING);
					final Unmarshaller _unmarshaller = _context.createUnmarshaller();
					
					final JAXBElement<?> _element = (JAXBElement<?>)_unmarshaller.unmarshal(_input);
					if(null!= _element.getValue()){
						APPLICATION_BIND = (Application)_element.getValue();
						
					} else {
						throw new ResourceException("Não existe uma configuração valida no arquivo: " + TFConstants.VALIDATION.XML_BINDDING);
					}
				}catch(JAXBException _e){
					throw new ResourceException(_e.getMessage(), _e);
				}
				
			} else {
				throw new ResourceException("Arquivo com as configurações para bidding de eventos, layouts e operações não encontrado: " + TFConstants.VALIDATION.XML_BINDDING);
			}
		}
		
		return APPLICATION_BIND;
	}

	public static synchronized Operation operation(final String pack) throws ResourceException{
		
		final Application _bindding = getBindding();
		
		Operation _result = null;
		for(Operation _operation : _bindding.getOperation()){
			if(pack.equals(_operation.getPackage())){
				_result = _operation;
				break;
			}
		}
		
		if(null== _result){
			throw new ResourceException("Configurações do bidding de operação não foram encontradas: " + pack);
		}
		
		return _result;
	}
	
	public static synchronized Event match(final Operation operation, final Object event) throws ResourceException{
		
		Event _result = null;
		
		if(null!= event){
			final String _sclazz = event.getClass().getName();
			
			for(Event _event : operation.getEvents().getEvent()){
				
				if(!FORMAT.isDefault(_event.getEventClass())){//DEFINIDO por classes
					for(String _eclass : _event.getEventClass()){
						if(_sclazz.equals(_eclass)){
							_result = _event;
							break;
						}
					}
					
				} else if(!FORMAT.isDefault(_event.getRegex())) {//DEFINIDO por expressoes regulares
					for(String _regex : _event.getRegex()){
						if(_sclazz.matches(_regex)){
							_result = _event;
							break;
						}
					}
				} else {
					throw new ResourceException("Nenhuma configuração válida nos valores de Event");
				}
			}
		} else {
			throw new NullPointerException("Argumento 2 está null.");
		}
		
		if(null== _result){
			throw new ResourceException("Nenhuma configuração encontrada para o evento: " + event);
		}
		
		return _result;
	}
	
	public static synchronized View view(final String id) throws ResourceException {
		
		View _result = null;
		
		for(View _view : getBindding().getViews().getView()){
			if(_view.getId().equals(id)){
				_result = _view;
				break;
			}
		}
		
		if(null== _result){
			throw new ResourceException("Identificador da View não foi encontrado nas configurações: " + id);
		}
		
		return _result;
	}
	
	public static synchronized View view(final Class<?> layout) throws ResourceException {
		final Application _application = getBindding();
		
		View _result = null;
		final List<View> _views = _application.getViews().getView();
		for(View _view : _views){
			if(_view.getViewClass().equals(layout.getName())){
				_result = _view;
				break;
			}
		}
		
		if(null== _result){
			throw new ResourceException("Classe da View não foi encontrada nas configurações: " + layout.getName());
		}
		
		return _result;
	}
	
	public static synchronized Validator validator(final String id){
		final Application _application = getBindding();
		
		Validator _result = null;
		final List<Validator> _validators = _application.getValidators().getValidator();
		for(Validator _validator : _validators){
			if(_validator.getId().equals(id)){
				_result = _validator;
				break;
			}
		}
		
		//clonar a instancia da configuracao do validador
		_result = REFLECTION.clone(_result);
		
		return _result;
	}
	
	public static synchronized List<Validator> validator() throws ResourceException {
		
		final List<Validator> _result = new ArrayList<Validator>();
		final Application _application = getBindding();
		
		_result.addAll(_application.getValidators().getValidator());
		return _result;
	}
	
	public static synchronized Validations validations(final String id) throws ResourceException {
		
		Validations _result = null;
		final List<Validations> _validations = getBindding().getValidations();
		for(Validations _vs : _validations){
			if(_vs.getId().equals(id)){
				_result = _vs;
				break;
			}
		}
		
		if(null== _result){
			throw new ResourceException("Identificador do Validations não encontrado nas configurações: " + id);
		}
		
		return _result;
	}
}
