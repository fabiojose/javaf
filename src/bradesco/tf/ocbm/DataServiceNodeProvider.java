package bradesco.tf.ocbm;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bradesco.core.aq.dataservice.IContainerObject;
import br.com.bradesco.core.aq.dataservice.IDataObject;
import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.dataservice.IListObject;
import br.com.bradesco.core.aq.dataservice.ISimpleObject;
import bradesco.tf.UtilFWO;
import bradesco.tf.dsn.UtilDSN;
import cobol.CobolDomain;
import cobol.CobolConstants.OCBM;
import cobol.ocbm.AttributeOverride;
import cobol.ocbm.AttributeOverrides;
import cobol.ocbm.Book;
import cobol.ocbm.Dynamic;
import cobol.ocbm.Embedded;
import cobol.ocbm.Field;
import cobol.ocbm.FieldOverride;
import cobol.ocbm.Inheritor;
import cobol.ocbm.Mapping;
import cobol.ocbm.Occurs;
import cobol.ocbm.OccursMapping;
import cobol.ocbm.OccursMappingOverride;
import cobol.ocbm.OccursMappingOverrides;
import cobol.ocbm.Provider;
import cobol.ocbm.ReadingException;
import cobol.ocbm.UtilOCBM;
import cobol.ocbm.WritingException;
import cobol.ocbm.Process;

import com.javaf.ExecutingException;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.REFLECTION;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.reflect.GenericsWorkaround;
import com.javaf.javase.lang.reflect.GenericsWorkarounds;
import com.javaf.javase.lang.reflect.InvokeException;
import com.javaf.javase.lang.reflect.MethodType;
import com.javaf.javase.lang.reflect.ReflectionException;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.annotation.Transient;
import com.javaf.javase.persistence.annotation.TransientPolicy;
import com.javaf.javase.text.ParsingException;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;
import com.javaf.model.IDynamic;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T>
 */
@SuppressWarnings("deprecation")
public class DataServiceNodeProvider<T extends Object> implements Provider<T> {
	
	private final UtilReflection reflection    = UtilReflection.getInstance();
	private final UtilDSN dsn                  = UtilDSN.getInstance();
	private final ILogging logging             = Logging.loggerOf(getClass());
	private final UtilFormat format            = UtilFormat.getInstance();
	private final UtilOCBM ocbm                = UtilOCBM.getInstance();
	private final ILocalization localize       = Localization.getInstance();
	private final UtilFWO fwo                  = UtilFWO.getInstance();
	private final CobolDomain cobol            = CobolDomain.getInstance();
	
	private static enum OPERATION {
		
		WRITE,
		READ;
		
	};
	
	private IContainerObject fluxo;
	private Map<String, Integer> padding;
	
	public DataServiceNodeProvider(){
		
	}

	public DataServiceNodeProvider(final IContainerObject area){
		setArea(area);
	}
	
	private Map<String, Integer> getPadding(){
		if(null== padding){
			padding = new HashMap<String, Integer>();
		}
		
		return padding;
	}
	
	private int paddingOf(final Occurs area){
		return format.toInteger( getPadding().remove(area.area()) );
	}
	
	private void paddingOf(final Occurs area, int padding){
		getPadding().put(area.area(), padding);
	}
	
	public void setArea(final IContainerObject area){
		this.fluxo = area;
	}
	
	public IContainerObject getArea(){
		return this.fluxo;
	}
	
	private T delegate(final T object, final OPERATION operation) throws ExecutingException, IllegalArgumentException, NullPointerException {
		
		if(null!= object){
			final Class<?> _clazz = object.getClass();
			final Process _process = reflection.annotationOf(_clazz, cobol.ocbm.Process.class);
			
			AttributeOverrides _overrides = null;
			//verificar heranca
			final Inheritor _inheritor = reflection.annotationOf(_clazz, Inheritor.class);
			if(null!= _inheritor){
				//herdeiro e sobrescritor de mapeamentos
				_overrides = reflection.annotationOf(_clazz, AttributeOverrides.class);
			}
			
			if(null!= _process){
				if(_process.fluxo().equals(this.fluxo.getKey())){
					
					Book _book = null;
					IContainerObject _areaio = null;
					if(OPERATION.WRITE.equals(operation)){
						_book = _process.entrada();
						_areaio = this.fluxo.getContainerObject(OCBM.INPUT);
						
					} else if(OPERATION.READ.equals(operation)) {
						_book = _process.saida();
						_areaio = this.fluxo.getContainerObject(OCBM.OUTPUT);
						
					} else {
						throw new IllegalArgumentException(localize.localize(I18N.OPERACAO_DESCONHECIDA, operation));
					}
					
					if(!OCBM.AUSENTE.equals( _book.area() )){
						final IContainerObject _area = dsn.getOrAddContainerObject(_book.area(), _areaio);
						if(null!= _area){
							final String _prefixo = _book.prefixo();
							
							//popular os valores-padrão
							for(Field _defaults : _book.defaults()){
								final ISimpleObject _simple = dsn.getOrAddSimpleObject(_prefixo + _defaults.nome(), _area);
								_simple.setValue(_defaults.defaultValue());
								
								logging.debug(I18N.DEFAULT +  _simple.getFullKey() + STRING.IGUAL_SPACE + _simple.getValue());
							}
							
							if(OPERATION.WRITE.equals(operation)){
								//WRITE
								write(_clazz, object, _prefixo, _area, ocbm.getOverrides(_overrides), new ArrayList<FieldOverride>(), STRING.EMPTY);
								
							} else {
								//READ
								read(_clazz, object, _prefixo, _area, ocbm.getOverrides(_overrides), new ArrayList<FieldOverride>(), STRING.EMPTY);
							}
						} else {
							throw new WritingException(localize.localize(I18N.CONTAINER_DSN_X_NAO_POSSUI, _area, _book.area()));
						}
					}
				} else {
					throw new ExecutingException(localize.localize(I18N.CONTAINER_DSN_NAO_E, _process.fluxo(), this.fluxo.getKey()));
				}
			} else {
				throw new ExecutingException(localize.localize(I18N.CLASSE_X_NAO_ANOTADA_COM, _clazz, Process.class));
			}
		} else {
			throw new NullPointerException();
		}
		
		return object;
	}
	
	@SuppressWarnings("unchecked")
	private Object read(final Class<?> root, final Object object, final String prefixo, final IDataObject area, final List<AttributeOverride> overrides, final List<FieldOverride> overfields, final String embedded) throws ReadingException {
		
		final Class<?> _clazz = object.getClass();
		
		logging.debug(I18N.AREA_DE_TRABALHO_NO_DSN + area);
		
		//ordernar conforme override em OccurrsOverride
		final List<Member> _mappings = sort( reflection.methodsOf(_clazz, Mapping.class, MethodType.GETTER), overrides, overfields, embedded);
		
		for(Member _member : _mappings){
			
			if(_member instanceof Method){
				final Method _method = (Method)_member;
			
				logging.debug(I18N.LEITURA_ATRIBUTO_SIMPLES + reflection.attributeOf(_method) + I18N.DO_TIPO + _method.getReturnType().getSimpleName());
				final String _attribute = reflection.attributeOf(_method);
				try{
					final Method _setter    = reflection.setterOf(_clazz, _attribute, _method.getReturnType());
					final Mapping _mapping  = reflection.annotationOf(_method, Mapping.class);
					
					Field _field = _mapping.output();
					
					final AttributeOverride _override = ocbm.getOverride(overrides, embedded + _attribute);
					if(null!= _override){
						_field = _override.mapping().output();
						
					} else {//buscar em overfields
						final FieldOverride _overfield = ocbm.getOverride(overfields, embedded + _attribute);
						if(null!= _overfield){
							_field = _overfield.value();
						}
					}
					
					if(!OCBM.AUSENTE.equals( _field.nome() )){
						logging.debug(I18N.OBTENDO_SIMPLE_OBJECT_NA_AREA_DE_TRABALHO + prefixo + _field.nome());
						final ISimpleObject _simple = dsn.getOrAddSimpleObject(prefixo + _field.nome(), area);
						
						Object _value = null;
						try{
							logging.debug(_simple.getFullKey() + STRING.IGUAL_SPACE + _simple.getValue());
							
							_value = cobol.toObject(_simple.getValue(), _setter.getParameterTypes()[INTEGER._0], _field.formatter());
						}catch(ParsingException _e){
							logging.warn(I18N.VALOR_NAO_FOI_POSSIVEL_TRANSFORMAR + STRING.MAIOR + _simple.getValue() + STRING.MENOR, _e);
						}
						
						//DEFAULT VALUE
						if(null== _value){
							try{
								logging.debug(I18N.DEFAULT_APLICAR + STRING.MAIOR + _field.defaultValue() + STRING.MENOR);
								
								_value = cobol.toObject(_field.defaultValue(), _setter.getParameterTypes()[INTEGER._0]);
							}catch(ParsingException _e){
								logging.warn(I18N.DEFAULT_NAO_FOI_POSSIVEL_TRANSFORMAR + STRING.MAIOR + _field.defaultValue() + STRING.MENOR, _e);
							}
						}
						
						try{
							reflection.invoke(object, _setter, new Object[]{_value});
							
						}catch(InvokeException _e){
							throw new ReadingException(I18N.NAO_FOI_POSSIVEL_INVOCAR_SET_DO_ATRIBUTO + _attribute, _e);
						}
					}
				} catch (ReflectionException _e){
					logging.warn(I18N.ATRIBUTO + STRING.ASPAS_SIMPLES + _attribute + STRING.ASPAS_SIMPLES + I18N.NAO_POSSUI_SEU_RESPECTIVO_SETTER, _e);
				}
				
			} else {
				//Dynamic
				
				final String _attribute           = _member.getName();
				final FieldOverride _overfield    = ocbm.getDynamicOverride(overfields, embedded + _attribute);
				final AttributeOverride _override = ocbm.getDynamicOverride(embedded + _attribute, overrides);
				
				Dynamic _dynamic = null;
				if(null!= _overfield){
					_dynamic = _overfield.dynamic();
				} else {
					_dynamic = _override.dynamic();
				}
					
				Field _field = null;
				
				if(null!= _overfield){
					_field = _overfield.value();
				} else {
					_field = _override.mapping().output();
				}
				
				if(object instanceof IDynamic){
					if(!OCBM.AUSENTE.equals( _field.nome()) ){
						final IDynamic _odynamic = (IDynamic)object;
						
						logging.debug(I18N.OBTENDO_SIMPLE_OBJECT_NA_AREA_DE_TRABALHO + prefixo + _field.nome());
						final ISimpleObject _simple = dsn.getOrAddSimpleObject(prefixo + _field.nome(), area);
											
						Object _value = null;
						try{
							logging.debug(_simple.getFullKey() + STRING.IGUAL_SPACE + _simple.getValue());
							
							_value = cobol.toObject(_simple.getValue(), _dynamic.type(), _field.formatter());
						}catch(ParsingException _e){
							logging.warn(I18N.VALOR_NAO_FOI_POSSIVEL_TRANSFORMAR + STRING.MAIOR + _simple.getValue() + STRING.MENOR, _e);
						}
						
						//DEFAULT VALUE
						if(null== _value){
							try{
								logging.debug(I18N.DEFAULT_APLICAR + STRING.MAIOR + _field.defaultValue() + STRING.MENOR);
								
								_value = cobol.toObject(_field.defaultValue(), _dynamic.type());
							}catch(ParsingException _e){
								logging.warn(I18N.DEFAULT_NAO_FOI_POSSIVEL_TRANSFORMAR + STRING.MAIOR + _field.defaultValue() + STRING.MENOR, _e);
							}
						}
						
						_odynamic.set(_dynamic.nome(), _value);					
						logging.debug(I18N.DYNAMIC + _simple.getFullKey() + STRING.IGUAL_SPACE + _simple.getValue());
					}
					
				} else {
					throw new WritingException(I18N.INSTANCIA_NAO_E_UMA_SUBCLASSE_DE + IDynamic.class);
				}
			}
		}
		
		final List<Method> _embeddeds = reflection.methodsOf(_clazz, Embedded.class, MethodType.GETTER);
		for(Method _method : _embeddeds) {
			final String _attribute = reflection.attributeOf(_method);
			logging.debug(I18N.LEITURA_ATRIBUTO_EMBARCADO + _attribute + I18N.DO_TIPO + _method.getReturnType().getSimpleName());
			
			try{
				final Transient _transient = reflection.annotationOf(_method, Transient.class);
				
				if(null== _transient || 
						(!TransientPolicy.READ.equals(_transient.value()) && !TransientPolicy.READ_WRITE.equals(_transient.value())) ){
					
					final Object _object = reflection.invoke(object, _method, REFLECTION.NO_ARGS);
					if(null!= _object){
						String _embedded = embedded;
						
						final List<FieldOverride> _overfields = new ArrayList<FieldOverride>();
						
						//verificar e montar lista de possives sobrescritas do atributo embarcado ex: periodo.inicio
						final List<AttributeOverride> _overs = ocbm.getOverrides(_method);
						if(reflection.hasAnnotation(root, Inheritor.class)){//verificar se classe root está anotada como herdeira
							_embedded += _attribute + STRING.DOT;
							
							//obter da classe root possiveis sobrescritas
							final AttributeOverrides _overrides = reflection.annotationOf(root, AttributeOverrides.class);
							if(null!= _overrides){
								for(AttributeOverride _override : _overrides.value()){
									if(_override.nome().startsWith(_embedded)){
										_overs.add(_override);
									}
								}
							}
							
							//obter lista de overfields
							for(FieldOverride _overfield : overfields){
								if(_overfield.nome().startsWith(_embedded)){
									_overfields.add(_overfield);
								}
							}
							
						}
						
						read(root, _object, prefixo, area, _overs, _overfields, _embedded);
					
					} else {
						throw new WritingException(I18N.ATRIBUTO_MARCADO_COMO + Embedded.class.getSimpleName() + I18N.ESTA_NULL);
					}
					
				} else {
					logging.info(I18N.ATRIBUTO_MARCADO_COMO_TRANSIENT + _attribute);
				}
			}catch(InvokeException _e){
				logging.debug(I18N.ATRIBUTO_MARCADO_COMO + Embedded.class.getSimpleName() + I18N.NAO_PODE_SER_OBTIDO, _e);
			}
		}
		
		final List<Method> _occurrs = reflection.methodsOf(_clazz, OccursMapping.class, MethodType.GETTER);
		for(Method _method : _occurrs){
			final String _attribute = reflection.attributeOf(_method);
			logging.debug(I18N.LEITURA_ATRIBUTO_OCCURS + _attribute);
			try{
				
				final OccursMapping _om = reflection.annotationOf(_method, OccursMapping.class);
				Occurs _antt = _om.output();
				
				//tipo da lista
				Class<?> _occurrsType = _method.getReturnType();
				
				String _embedded = embedded;
				//verificar e montar lista de possives sobrescritas do atributo occurrs
				if(reflection.hasAnnotation(root, Inheritor.class)){//verificar se classe root está anotada como herdeira
					_embedded += _attribute + STRING.DOT;
					
					final OccursMappingOverride _overrides = reflection.annotationOf(root, OccursMappingOverride.class);
					
					if(null!= _overrides){
						_antt = _overrides.output();
					} else {
						//tentar na outra forma de overrides
						final OccursMappingOverrides _overridess = reflection.annotationOf(root, OccursMappingOverrides.class);
						if(null!= _overridess){
							final OccursMappingOverride _omo = ocbm.getOverrides(embedded + _attribute, _overridess);
							if(null!= _omo){
								_antt = _omo.output();
							}
						}
					}
					
					//obter GenericsWorkaround
					final GenericsWorkaround _workaround = reflection.workaroundOf(embedded + _attribute, reflection.annotationOf(root, GenericsWorkarounds.class));
					if(null!= _workaround){						
						_occurrsType = _workaround.value();
					}
				}
				
				if(!OCBM.AUSENTE.equals(_antt.area())){
					final Object _object = reflection.invoke(object, _method, REFLECTION.NO_ARGS);
					if(null!= _object){
						try{
							final List _objects = (List)_object;
							logging.debug(I18N.OBTENDO_LIST_OBJECT_NA_AREA_DE_TRABALHO + _antt.area());
							final IListObject _listo = dsn.getOrAddListObject(_antt.area(), area);
							int _index = INTEGER._0;
							
							//falta algum para ler?
							if(_index < _listo.getAllDataObjects().size()){
								
								do{
									final IDataObject _area = _listo.getDataObject(_index);
									final Object _o = reflection.newInstance(_occurrsType);
									
									final Object _item = read(root, _o, prefixo, _area, ocbm.getOverrides( reflection.annotationOf(_method, AttributeOverrides.class)), ocbm.getOverrides(_antt), STRING.EMPTY);;
									
									_objects.add(_item);
									_index++;
								}while(_index < _listo.getAllDataObjects().size());
							}
						}catch(ClassCastException _e){
							throw new WritingException(I18N.ATRIBUTO_MARCADO_COMO + Occurs.class.getSimpleName() + I18N.DEVE_SER_UMA_IMPLEMENTACAO_DE + List.class.getName() + STRING.DOIS_PONTOS + _object.getClass());
						}
					} else {
						logging.debug(I18N.ATRIBUTO_MARCADO_COMO + Occurs.class.getSimpleName() + I18N.ESTA_NULL);
					}
				} else {
					logging.warn(I18N.OCCURS_NAO_ESTA_MAPEADO_PARA_ESCRITA + reflection.attributeOf(_method));
				}
			}catch(InvokeException _e){
				logging.debug(I18N.ATRIBUTO_MARCADO_COMO + Occurs.class.getSimpleName() + I18N.NAO_PODE_SER_OBTIDO, _e);
			}
		}
		
		return object;		
	}
	
	private List<Member> sort(final List<Method> mappings, final List<AttributeOverride> overrides, final List<FieldOverride> overfields, final String embedded){
		
		final List<Member> _result = new ArrayList<Member>();
		
		for(AttributeOverride _ao : overrides){
			
			for(Method _mapping : mappings){
				final String _property = embedded + reflection.attributeOf(_mapping);
				
				if(_ao.nome().equals(_property)){
					_result.add((Member)_mapping);
					break;
				}
			}
			
			if(!_ao.dynamic().nome().equals(OCBM.AUSENTE)){
				final Dynamic _dynamic = _ao.dynamic();
				
				final Member _member = new Member(){
					public Class<?> getDeclaringClass() {
						return null;
					}

					public int getModifiers() {
						return INTEGER._0;
					}

					public String getName() {
						return _dynamic.nome();
					}

					public boolean isSynthetic() {
						return Boolean.FALSE;
					}
					
				};
				
				_result.add(_member);
			}
			
		}
		
		for(FieldOverride _fo : overfields){
			
			for(Method _mapping : mappings){
				final String _property = embedded + reflection.attributeOf(_mapping);
				
				if(_fo.nome().equals(_property)){
					_result.add((Member)_mapping);
					break;
				}
			}
			
			if(!_fo.dynamic().nome().equals(OCBM.AUSENTE)){
				final Dynamic _dynamic = _fo.dynamic();
				
				final Member _member = new Member(){
					public Class<?> getDeclaringClass() {
						return null;
					}

					public int getModifiers() {
						return INTEGER._0;
					}

					public String getName() {
						return _dynamic.nome();
					}

					public boolean isSynthetic() {
						return Boolean.FALSE;
					}
					
				};
				
				_result.add(_member);
			}
		}
		
		if(_result.isEmpty()){
			_result.addAll( mappings );
			
		} else {
			
			for(Method _mapping : mappings){
				final String _property = embedded + reflection.attributeOf(_mapping);
				
				int _index = INTEGER._0;
				for(_index = INTEGER._0; _index < _result.size(); _index++){
					final Member _member = _result.get(_index);
					
					if(_member.getName().equals(_property)){
						break;
					}
				}
				
				//nao foi encontrado no resultado
				if(_index == _result.size()){
					_result.add(_mapping);
				}
			}
		}
		
		return _result;
	}
	
	private void write(final Class<?> root, final Object object, final String prefixo, final IDataObject area, final List<AttributeOverride> overrides, final List<FieldOverride> overfields, final String embedded) throws WritingException  {
		
		final Class<?> _clazz = object.getClass();
		
		//ordernar conforme override em OccursOverride
		final List<Member> _mappings = sort( reflection.methodsOf(_clazz, Mapping.class, MethodType.GETTER), overrides, overfields, embedded);
		
		int _counter = INTEGER._0;
		for(Member _member : _mappings){
			
			if(_member instanceof Method){
				final Method _method = (Method)_member;
				
				final String _attribute = reflection.attributeOf(_method);
				logging.debug(I18N.GRAVANDO_ATRIBUTO_SIMPLES + _attribute + I18N.DO_TIPO + _method.getReturnType().getSimpleName());

				final Mapping _mapping = reflection.annotationOf(_method, Mapping.class);
				Field _field = _mapping.input();
			
				//verificar se tem override, se positivo sobrescreve o mapeamento original
				final AttributeOverride _override = ocbm.getOverride(overrides, embedded + _attribute);
				if(null!= _override){
					_field = _override.mapping().input();
					
					final FieldOverride _overfield = ocbm.getOverride(overfields, embedded + _attribute);
					if(null!= _overfield){
						_field = _overfield.value();
					}
				} else {//buscar em overfields
					final FieldOverride _overfield = ocbm.getOverride(overfields, embedded + _attribute);
					if(null!= _overfield){
						_field = _overfield.value();
					}
				}
				
				//nao esta configurado como ausente
				if(! OCBM.AUSENTE.equals( _field.nome() )){
					ISimpleObject _simple = dsn.getSimpleObject(prefixo + _field.nome(), area);
					if(null== _simple){
						_simple = dsn.getOrAddSimpleObject(prefixo + _field.nome(), area);

						fwo.normalize(_simple, _counter + INTEGER._1);
					}
					_counter++;
					
					Object _value = null;
					try{
						_value = reflection.invoke(object, _method, REFLECTION.NO_ARGS);
						
					}catch(InvokeException _e){
						logging.warn(I18N.INVOCACAO_DO_GETTER_GEROU_EXCECAO, _e);
					}
					
					if(null== _value){
						_value = _field.defaultValue();
					}
					
					final String _cobol = cobol.toString(_value, _field.formatter());
					_simple.setValue(_cobol);
					logging.debug(_simple.getFullKey() + STRING.IGUAL_SPACE + _simple.getValue());
					
				}
				
			} else {
				//Dynamic
				
				final String _attribute           = _member.getName();
				final AttributeOverride _override = ocbm.getDynamicOverride(embedded + _attribute, overrides);
				final FieldOverride _overfield    = ocbm.getDynamicOverride(overfields, embedded + _attribute);
				
				Field _field = null;
				
				if(null!= _override){
					_field = _override.mapping().input();
					
				} else if(null!= _overfield){

					_field = _overfield.value();
					
				}
				
				if(object instanceof IDynamic){
					final IDynamic _odynamic = (IDynamic)object;
					
					//nao lançar excetions
					_odynamic.setExceptions(Boolean.FALSE);
					
					//obter valor dinamico
					Object _value = _odynamic.get(_attribute);
					
					//aplicar default, caso nao tenha retornado
					if(null== _value){
						_value = _field.defaultValue();
					}
					
					final String _cobol = cobol.toString(_value, _field.formatter());
					
					ISimpleObject _simple = dsn.getSimpleObject(prefixo + _field.nome(), area);
					if(null== _simple){
						_simple = dsn.getOrAddSimpleObject(prefixo + _field.nome(), area);

						fwo.normalize(_simple, _counter + INTEGER._1);
					}
					_simple.setValue(_cobol);
					logging.debug(I18N.DYNAMIC + _simple.getFullKey() + STRING.IGUAL_SPACE + _simple.getValue());
					
				} else {
					throw new WritingException(I18N.INSTANCIA_NAO_E_UMA_SUBCLASSE_DE + IDynamic.class);
				}
			}
		}
		
		final List<Method> _embeddeds = reflection.methodsOf(_clazz, Embedded.class, MethodType.GETTER);
		for(Method _method : _embeddeds){
			final String _attribute = reflection.attributeOf(_method);
	
			logging.debug(I18N.GRAVANDO_ATRIBUTO_EMBARADO + _attribute + I18N.DO_TIPO + _method.getReturnType().getSimpleName());
			try{
				final Object _object = reflection.invoke(object, _method, REFLECTION.NO_ARGS);
				if(null!= _object){
					String _embedded = embedded;
					
					final List<FieldOverride> _overfields = new ArrayList<FieldOverride>();
					
					//verificar e montar lista de possives sobrescritas do atributo embarcado ex: periodo.inicio
					final List<AttributeOverride> _overs = ocbm.getOverrides(_method);
					if(reflection.hasAnnotation(root, Inheritor.class)){//verificar se classe root está anotada como herdeira
						_embedded += _attribute + STRING.DOT;
						
						//obter da classe root possiveis sobrescritas
						final AttributeOverrides _overrides = reflection.annotationOf(root, AttributeOverrides.class);
						if(null!= _overrides){
							for(AttributeOverride _override : _overrides.value()){
								if(_override.nome().startsWith(_embedded)){
									_overs.add(_override);
								}
							}
						}
						
						//obter lista de overfields
						for(FieldOverride _overfield : overfields){
							if(_overfield.nome().startsWith(_embedded)){
								_overfields.add(_overfield);
							}
						}
					}
					
					write(root, _object, prefixo, area, _overs, _overfields, _embedded);
					
				} else {
					throw new WritingException(I18N.ATRIBUTO_MARCADO_COMO + Embedded.class.getSimpleName() + I18N.ESTA_NULL);
				}
				
			}catch(InvokeException _e){
				logging.debug(I18N.ATRIBUTO_MARCADO_COMO + Embedded.class.getSimpleName() + I18N.NAO_PODE_SER_OBTIDO, _e);
			}
		}
		
		final List<Method> _occurrs = reflection.methodsOf(_clazz, OccursMapping.class, MethodType.GETTER);
		for(Method _method : _occurrs){
			final String _attribute = reflection.attributeOf(_method);
			logging.debug(I18N.GRAVANDO_ATRIBUTO_OCCURS + reflection.attributeOf(_method));
			try{
				final OccursMapping _om = reflection.annotationOf(_method, OccursMapping.class);
				Occurs _antt = _om.input();
				
				String _embedded = embedded;
				
				//tipo da lista
				@SuppressWarnings("unused")
				Class<?> _occurrsType = _method.getReturnType();
				
				//verificar e montar lista de possives sobrescritas do atributo occurrs
				if(reflection.hasAnnotation(root, Inheritor.class)){//verificar se classe root está anotada como herdeira
					_embedded += _attribute + STRING.DOT;
					
					final OccursMappingOverride _overrides = reflection.annotationOf(root, OccursMappingOverride.class);
					
					if(null!= _overrides){
						_antt = _overrides.input();
					} else {
						//tentar na outra forma de overrides
						final OccursMappingOverrides _overridess = reflection.annotationOf(root, OccursMappingOverrides.class);
						if(null!= _overridess){
							final OccursMappingOverride _omo = ocbm.getOverrides(embedded + _attribute, _overridess);
							if(null!= _omo){
								_antt = _omo.input();
							}
						}
					}
					
					//obter GenericsWorkaround
					final GenericsWorkaround _workaround = reflection.workaroundOf(embedded + _attribute, reflection.annotationOf(root, GenericsWorkarounds.class));
					if(null!= _workaround){						
						_occurrsType = _workaround.value();
					}
				}
				
				if(!OCBM.AUSENTE.equals(_antt.area())){
					final Object _object = reflection.invoke(object, _method, REFLECTION.NO_ARGS);
					if(null!= _object){
						try{
							final List<?> _objects      = (List<?>)_object;
							final IListObject _listo = dsn.getOrAddListObject(_antt.area(), area);

							fwo.normalize(_listo, INTEGER._1);
							
							Object _source = null;
							final int _padding = paddingOf(_antt);
							for( _counter = INTEGER._0; 							
								(_counter < _antt.size()) && (_counter+_padding < _objects.size());								
								 _counter++){
								
								final Object _o = _objects.get(_counter + _padding);
								
								IDataObject _area = _listo;
								if(dsn.isContainerObjectCandidate(_o.getClass()) ||
										_o.getClass().isEnum()){
									
									_area = _listo.getContainerObject(_counter);
									if(null== _area){
										_area = _listo.addContainerObject();

										fwo.normalize((IContainerObject)_area, _counter + INTEGER._1);
									}
								}
								
								write(root, _o, prefixo, _area, ocbm.getOverrides( reflection.annotationOf(_o, AttributeOverrides.class) ), ocbm.getOverrides(_antt), STRING.EMPTY);
								_source = _o;
								
							}
							
							//ainda sobraram ocorrencias que devem ser enviadas numa nova chamada
							if(_counter == _antt.size() && _counter != _objects.size()){
								paddingOf(_antt, _counter);
							}
							
							//configurar o dependsOn
							if(!OCBM.AUSENTE.equals(_antt.dependsOn())){
								final ISimpleObject _depends = dsn.getOrAddSimpleObject(prefixo + _antt.dependsOn(), area);
								_depends.setIntValue(_counter);
							}
							
							//gravar ocorrencias offset
							if(_antt.offset() && _counter < _antt.size()){
								try{
									logging.info(I18N.ANOTADO_PARA_GRAVAR_OCORRENCIAS_OFFSET + _counter + I18N.OFFSET + _antt.size());
									final Object _base = reflection.newInstance(_source.getClass());
									do{
										IDataObject _area = _listo;
										if(dsn.isContainerObjectCandidate(_base.getClass())){
											_area = _listo.getContainerObject(_counter);
											if(null== _area){
												_area = _listo.addContainerObject();
											}
										}
										
										write(root, _base, prefixo, _area, ocbm.getOverrides( reflection.annotationOf(_base, AttributeOverrides.class) ), ocbm.getOverrides(_antt), STRING.EMPTY);
										_counter++;
									}while(_counter < _antt.size());
									
									logging.info(I18N.OCORRENCIAS_OFFSET_FORAM_GRAVADAS + _counter);
								}catch(InvokeException _e){
									logging.warn(I18N.NAO_FORAM_GRAVADAS_OFFSET, _e);
								}
							}
						}catch(ClassCastException _e){
							throw new WritingException(I18N.ATRIBUTO_MARCADO_COMO + Occurs.class.getSimpleName() + I18N.DEVE_SER_UMA_IMPLEMENTACAO_DE + List.class.getName() + STRING.DOIS_PONTOS + _object.getClass());
						}
					} else {
						logging.debug(I18N.ATRIBUTO_MARCADO_COMO + Occurs.class.getSimpleName() + I18N.ESTA_NULL);
					}
				} else {
					logging.warn(I18N.OCCURS_NAO_ESTA_MAPEADO_PARA_ESCRITA + reflection.attributeOf(_method));
				}
			
			}catch(InvokeException _e){
				logging.warn(I18N.ATRIBUTO_MARCADO_COMO + Occurs.class.getSimpleName() + I18N.NAO_PODE_SER_OBTIDO, _e);
			}			
		}
	}

	public boolean write(final T object) throws WritingException {
		boolean _result = Boolean.FALSE;
		
		if(null!= object){
			final Class<?> _clazz = object.getClass();
			final Process _process = reflection.annotationOf(_clazz, Process.class);
			if(null!= _process){
				delegate(object, OPERATION.WRITE);
				_result = (null!= padding ? !padding.isEmpty() : Boolean.FALSE);
			}
		}
		
		return _result;
	}
	
	public T read(final T object) throws ReadingException {
		
		if(null!= object){
			final Class<?> _clazz = object.getClass();
			final Process _process = reflection.annotationOf(_clazz, Process.class);
			if(null!= _process){
				delegate(object, OPERATION.READ);
			}
		}
		
		return object;
	}
	

	public static void clear(final Process process, final IDataServiceNode node){
		
		final UtilDSN _dsn = UtilDSN.getInstance();
		final ILogging _logging = Logging.loggerOf(DataServiceNodeProvider.class);
		
		final IContainerObject _fluxo = _dsn.getOrAddContainerObject(process.fluxo(), node);
		
		final Book _entrada = process.entrada();
		if(!OCBM.AUSENTE.equals(_entrada.area())){
			
			final IContainerObject _input = _dsn.getOrAddContainerObject(OCBM.INPUT,      _fluxo);
			final IContainerObject _area  = _dsn.getOrAddContainerObject(_entrada.area(), _input);
			
			if(_entrada.toClean().length > INTEGER._0){
				for(String _toClean : _entrada.toClean()){
					final IDataObject _object = _dsn.getDataObject(_toClean, _area); 
					_dsn.clear(_object);
					
					_logging.debug(I18N.LIMPANDO_AREA_DO_FWO + (null!= _object ? _object.getFullKey() : STRING.EMPTY) );
				}
			}
		}
		
		final Book _saida = process.saida();
		if(!OCBM.AUSENTE.equals(_saida.area())){
			final IContainerObject _output = _dsn.getOrAddContainerObject(OCBM.OUTPUT,   _fluxo);
			final IContainerObject _area   = _dsn.getOrAddContainerObject(_saida.area(), _output);
			
			if(_saida.toClean().length > INTEGER._0){
				for(String _toClean : _saida.toClean()){
					final IDataObject _object = _dsn.getDataObject(_toClean, _area); 
					_dsn.clear(_object);
					
					_logging.debug(I18N.LIMPANDO_AREA_DO_FWO + (null!= _object ? _object.getFullKey() : STRING.EMPTY) );
				}
			}
		}
	}
}
