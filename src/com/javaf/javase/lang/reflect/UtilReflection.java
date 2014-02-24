package com.javaf.javase.lang.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.javaf.Bagman;
import com.javaf.Utility;
import com.javaf.Constants.ATTRIBUTE;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.REFLECTION;
import com.javaf.Constants.REGEX;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.UtilClass;
import com.javaf.javase.lang.annotation.PropertyAliases;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.Localization;
import com.javaf.javase.util.UtilCollection;
import com.javaf.model.AbstractDynamic;
import com.javaf.model.IDynamic;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;



/**
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 */
public final class UtilReflection implements Utility {	
	private static final IFactory FACTORY = new IFactory(){

		public Object newInstance() throws FactoryException {
			return new UtilReflection();
		}
		
	};
	
	private static final ILogging LOGGING = Logging.loggerOf(UtilReflection.class);
	
	private final UtilClass clazz;
	private final UtilCollection collection;
	private UtilReflection(){
		clazz      = UtilClass.getInstance();
		collection = UtilCollection.getInstance();
	}
	
	public static final synchronized UtilReflection getInstance(){
		return Bagman.utilOf(UtilReflection.class, FACTORY);
	}
	
	private Class<?>[] argumentsOf(final String signature){
		Class<?>[] _result = new Class<?>[]{};
		
		final int _i1         = signature.indexOf(STRING.PARENTESES_ABRE);
		final int _i2         = signature.indexOf(STRING.PARENTESES_FECHA);
		final String _args    = signature.substring(_i1 + INTEGER._1, _i2);
		final String[] _argsa = _args.split(REGEX.VIRGULA);
		
		_result = new Class<?>[_argsa.length];
		int _index = INTEGER._0;
		for(String _arg : _argsa){
			try{
				_result[_index] = clazz.load(_arg);
				_index++;
			}catch(TypeNotPresentException _e){
				throw _e;
			}
		}
		
		return _result;
	}
	
	private String nameOf(final String signature) {
		String _result = STRING.EMPTY;
		
		final int _i = signature.indexOf(STRING.PARENTESES_ABRE);
		_result = signature.substring(INTEGER._0, _i);
		
		return _result;
	}

	/**
	 * O primeiro metodo declarado com o nome igual a <code>name</code> sera retornado, que não possua parâmetros.<br/>
	 * Caso <code>name</code> esteja formatado como uma assinatura de metodo Java, sera executada consulta como na API Reflection.
	 * @param clazz
	 * @param name
	 * @return
	 */
	public Method methodOf(final Class<?> clazz, final String name) throws MethodNotFoundException{
		Method _result = null;
		
		//caso nao seja uma assinatura de metodo
		if(!name.matches(REGEX.REFLECTION_METHOD_SIGNATURE)){
			if(null!= clazz){
				for(Method _method : clazz.getMethods()){
					if(_method.getName().equals(name) 
							&& INTEGER._0== _method.getParameterTypes().length){
						
						_result = _method;
						break;
					}
				}
			}
			
			if(null== _result){
				throw new MethodNotFoundException("Método não encontrado: " + name);
			}
		} else {
			//caso seja uma assinatura de metodo, entao extrair tipos dos argumentos
			final Class<?>[] _args = argumentsOf(name);
			final String _name     = nameOf(name);
			
			try{
				_result = clazz.getMethod(_name, _args);
				
			}catch(NoSuchMethodException _e){
				throw new MethodNotFoundException(_e.getMessage(), _e);
			}
		}
		
		return _result;
	}
	
	public Class<?>[] typeOf(final Object... args){
		Class<?>[] _result = new Class<?>[]{};
		
		final List<Class<?>> _local = new ArrayList<Class<?>>();
		if(null!= args){
			for(Object _arg : args){
				if(null!= _arg){
					_local.add(_arg.getClass());
				} else {
					throw new NullPointerException("there is one null value in the args at least.");
				}
			}
		}
		
		_result = new Class<?>[_local.size()];
		UtilCollection.getInstance().copy(_local, _result);
		
		return _result;
	}
	
	public String signatureOf(final Method method){
		final StringBuilder _result = new StringBuilder(STRING.EMPTY);
		
		_result.append(method.getName());
		if(INTEGER._0 != method.getParameterTypes().length){
			_result.append(STRING.PARENTESES_ABRE);
			_result.append(UtilFormat.getInstance().toString(method.getParameterTypes()));
			_result.append(STRING.PARENTESES_FECHA);
		}
		
		return _result.toString();
	}
	
	public String signatureOf(final String method, final Class<?>...arguments){
		final StringBuilder _result = new StringBuilder(STRING.EMPTY);
		
		_result.append(method);
		if(INTEGER._0 != arguments.length){
			_result.append(STRING.PARENTESES_ABRE);
			_result.append(UtilFormat.getInstance().toString(arguments));
			_result.append(STRING.PARENTESES_FECHA);
		}
		
		return _result.toString();
	}
	
	public Object invoke(final Class<?> target, final String method, final Object...args) throws MethodNotFoundException, InvokeException {
		Object _result = null;
		
		if(null!= target){
			final Method _method = methodOf(target, method);
			if(null!= _method){
				_result = invoke(null, _method, args);
			}
		}
		return _result;
	}
	
	public Object invoke(final Object target, final MethodWrapper wrapper, final Object...args){
		Object _result = null;
		
		try{
			_result = wrapper.invoke(target, args);
			
		}catch(InvocationTargetException _e){
			throw new InvokeException(_e.getMessage(), _e);
			
		}catch(IllegalAccessException _e){
			throw new InvokeException(_e.getMessage(), _e);
		}
		
		return _result;
	}
		
	/**
	 * Construir o nome do setter baseando-se no nome de atributo e tipo do argumento.
	 * 
	 * @param attribute Nome do atributo
	 * @return Nome do método getter do atributo
	 */
	public String setterOf(final String attribute){
		
		String _result = attribute.substring(INTEGER._1);
		_result = attribute.substring(INTEGER._0, INTEGER._1).toUpperCase() + _result;
		
		return REFLECTION.SETTER_PREFIX + _result;
	}

	public String getterOf(final String attribute){
		
		String _result = attribute.substring(INTEGER._1);
		_result = attribute.substring(INTEGER._0, INTEGER._1).toUpperCase() + _result;
		
		return REFLECTION.GETTER_PREFIX + _result;
	}
	
	public Method getterOf(final Class<?> clazz, final String property){
		return methodOf(clazz, getterOf(property));
	}
	
	public void setAttribute(final Object target, final String attribute, final Object value){
		invoke(target, setterOf(target.getClass(), attribute), value);
	}
	
	public Throwable causeOf(final Throwable e, final Class<?> cause){
		Throwable _result = null;
		
		if(cause.isAssignableFrom(e.getClass())){
			
			_result = e;
			
		} else if(null!= e.getCause()) {
			if( !(e instanceof InvocationTargetException) ){
				
				try{
					final Method _method    = methodOf(e.getClass(), REFLECTION.METHOD_GET_NESTED_EXCEPTION);
					final Throwable _nested = (Throwable)invoke(e, _method);
					
					_result = causeOf(_nested, cause);
					
				}catch(MethodNotFoundException _e){
					_result = causeOf(e.getCause(), cause);
				}
				
			} else {
				
				_result = causeOf( ((InvocationTargetException)e).getTargetException(), cause);
				
			}
		}
		
		return _result;
	}
	
	/**
	 * Verifica se um determinado método trata-se de um Java Bean getter.<br/>
	 * 
	 * @param method Método para verificação
	 * @return <code>false</code> se for um outro método qualquer.
	 */
	public boolean isGetter(final Method method){
		return (method.getName().startsWith(REFLECTION.GETTER_PREFIX) || method.getName().startsWith(REFLECTION.IS_PREFIX)) 
				&& !method.getReturnType().equals(Void.class)
				&& !REFLECTION.BLACK_METHODS.containsKey(method.getName())
				&& !method.getName().equals(REFLECTION.GETTER_PREFIX);
	}
	
	/**
	 * Verifica se um determinado método trata-se de um Java Bean setter.<br/>
	 * @param method Método para verificação
	 * @return <code>false</code> se for um outro método qualquer.
	 */
	public boolean isSetter(final Method method){
		return method.getName().startsWith(REFLECTION.SETTER_PREFIX) 
				&& method.getParameterTypes().length == INTEGER._1
				&& !REFLECTION.BLACK_METHODS.containsKey(method.getName())
				&& !method.getName().equals(REFLECTION.SETTER_PREFIX);
	}
	
	/**
	 * Obtem o nome do atributo do método getter ou setter.
	 * @param method Método
	 * @return Nome do atribudo se for getter ou setter e String vazia caso contrário.
	 */
	public String attributeOf(final Method method){
		
		String _result = STRING.EMPTY;
		
		if(isGetter(method) || isSetter(method)){
			
			_result = method.getName();
			int _begin = INTEGER._3;
			if(_result.startsWith(REFLECTION.IS_PREFIX)){
				_begin = INTEGER._2;
			} 
			_result = _result.substring(_begin);
			_result = _result.substring(INTEGER._0, INTEGER._1).toLowerCase() + _result.substring(INTEGER._1);
		}
		
		return _result;
	}
	
	/**
	 * Invocar metódo numa instância de objeto qualquer e obter o resultado do retorno.
	 * 
	 * @param target Instância alvo da obtenção e invocação do método
	 * @param method Nome do método
	 * @param args Arranjo com as instâncias de cada argumento na mesma ordem que aparecem no método.<br/>
	 * 				Se o método não possuir argumentos um arranjo vazio deve ser passado.
	 * @return Resultado da invocação, caso não seja <code>void</code>
	 * @throws InvokeException Lançada se houver qualquer tipo de impedimento para obter ou invocar o método.
	 */
	public Object invoke(final Object target, final String method, final Object[] args) throws InvokeException {
		
		final Class<?>[] _argsc = new Class[args.length];
		int _index = INTEGER._0;
		
		for(Object _arg : args){
			_argsc[_index] = _arg.getClass();
			
			_index++;
		}
		
		return invoke(target, method, args, _argsc);
	}
	
	public Object invoke(final Object target, final Method getter) throws InvokeException {
		return invoke(target, getter, REFLECTION.NO_ARGS);
	}
	
	public Object invoke(final Object target, final String method, final Object[] args, final Class<?>[] types) throws InvokeException {
		
		Object _result = null;
		
		final Class<?> _targetc = target.getClass();
		
		try{
			final Method _toInvoke = _targetc.getMethod(method, types);
			
			_result = invoke(target, _toInvoke, args);
			
		}catch(NoSuchMethodException _e){
			throw new InvokeException(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	/**
	 * Invocar metódo numa instância de objeto qualquer e obter o resultado do retorno.
	 * @param target Instância alvo da obtenção e invocação do método
	 * @param method Método que será invocado
	 * @param args Arranjo com as instâncias de cada argumento na mesma ordem que aparecem no método.<br/>
	 * 				Se o método não possuir argumentos um arranjo vazio deve ser passado.
	 * @return Resultado da invocação, caso não seja <code>void</code>
	 * @throws InvokeException Lançada se houver qualquer tipo de impedimento para invocar o método.
	 */
	public Object invoke(final Object target, final Method method, final Object...args) throws InvokeException {
		
		Object _result = null;
		
		try{
			_result = method.invoke(target, args);
			
		}catch(InvocationTargetException _e){
			throw new InvokeException(_e.getMessage(), _e);
			
		}catch(IllegalAccessException _e){
			throw new InvokeException(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public Enum<?> valueOf(final Class<?> e, final String name) throws IllegalArgumentException {
		
		Enum<?> _result = null;
		if(e.isEnum()){
			for(Object _o: e.getEnumConstants()){
				final Enum<?> _enum = (Enum<?>)_o;
				if(_enum.name().equalsIgnoreCase(name)){
					_result = _enum;
					break;
				}
			}
		} else {
			throw new IllegalArgumentException(Localization.getInstance().localize(I18N.CLASSE_INFORMADA_NAO_EXTENSAO, Enum.class));
		}
		return _result;
	}
	
	private String doIsName(final String attribute){
		
		String _result = attribute.substring(INTEGER._1);
		_result = attribute.substring(INTEGER._0, INTEGER._1).toUpperCase() + _result;
		
		return REFLECTION.IS_PREFIX + _result;
	}
	/**
	 * Construir o nome do getter baseando-se no nome de atributo.
	 * 
	 * @param attribute Nome do atributo
	 * @param type Tipo do attributo
	 * @return Nome do método getter do atributo
	 */
	public String getterOf(final String attribute, final Class<?> type){
		
		String _result = null;
		
		if(!Boolean.class.isAssignableFrom(type)){
			_result = getterOf(attribute);
		} else {
			_result = doIsName(attribute);
		}
		
		return _result;
	}
	
	public Method getterOf(final Class<?> c, final String attribute, final Class<?> type) throws ReflectionException {
		
		try{
			return c.getMethod(getterOf(attribute, type), new Class[]{});
			
		}catch(NoSuchMethodException _e){
			throw new ReflectionException(_e.getMessage(), _e);
		}
	}
	
	public List<Method> getterOf(final Class<?> c) {
		
		final List<Method> _result = new ArrayList<Method>();
		for(Method _method : c.getMethods()){
			if(isGetter(_method)){
				_result.add(_method);
			}
		}
		
		return _result;
	}
	
	public List<Method> getGetterBased(final Class<?> c, final Class<?> basedOn){
		
		final List<Method> _result = new ArrayList<Method>();
		final List<Method> _based  = new ArrayList<Method>();
		if(null!= basedOn){
			_based.addAll(getterOf(basedOn));
		}
		
		for(Method _method : c.getMethods()){
			if(isGetter(_method)){
				if(!_based.isEmpty()){
					if(collection.contains(_based, ATTRIBUTE.NAME, _method.getName())){
						_result.add(_method);
					}
					
				} else {
					_result.add(_method);
				}
			}
		}
		return _result;
	}
	
	/**
	 * Buscar método pelo nome, sendo aquele que possui nome igual a name será retornado.
	 * @param c
	 * @param name
	 * @return
	 */
	/*public Method getMethod(final Class<?> c, final String name){
		Method _result = null;
		
		if(null!= c){
			for(Method _method : c.getMethods()){
				if(_method.getName().equals(name)){
					_result = _method;
					break;
				}
			}
		}
		
		return _result;
	}*/
	
	public List<Method> getterOf(final Class<?> c, final Class<?> returnType){
		 
		final List<Method> _result = new ArrayList<Method>();
		final List<Method> _getters = getterOf(c);
		for(Method _getter : _getters){
			if(_getter.getReturnType().equals(returnType)){
				_result.add(_getter);
			}
		}
		return _result;
	}
	
	public Method setterOf(final Class<?> c, final String attribute, final Class<?> type) throws ReflectionException {
		
		try{
			return c.getMethod(setterOf(attribute), new Class[]{type});
			
		}catch(NoSuchMethodException _e){
			throw new ReflectionException(_e.getMessage(), _e);
		}
	}
	
	public Method setterOf(final Class<?> c, final String attribute) throws ReflectionException {
		
		Method _result = null;
		
		final List<Method> _setters = methodsOf(c, MethodType.SETTER);
		for(Method _setter : _setters){
			if(attributeOf(_setter).equals(attribute)){
				_result = _setter;
				break;
			}
		}
		
		if(null== _result){
			final List<Method> _getters = methodsOf(c, PropertyAliases.class, MethodType.GETTER);
			
			if(!_getters.isEmpty()){
				for(Method _getter : _getters){
					Method __result = null;
					final PropertyAliases _alises = _getter.getAnnotation(PropertyAliases.class);
					if(null!= _alises){
						for(String _alias : _alises.value()){
							if(_alias.equals(attribute)){
								final String _sname = setterOf(attributeOf(_getter));
								__result = methodOf(c, _sname);
								break;
							}
						}
					}
					
					if(null!= __result){
						_result = __result;
						break;
					}
				}
			}
			
			if(null== _result){
				throw new ReflectionException(Localization.getInstance().localize(I18N.METODO_SETTER_NAO_ENCONTRADO_ATT, attribute));
			}
		}
		
		return _result;
	}
	
	/**
	 * Verifica se um determinado método possui uma anotação em particular.
	 * 
	 * @param method Método
	 * @param annotation Classe da anotação
	 * @return <code>false</code> caso o método não possua a anotação
	 */
	public boolean hasAnnotation(final Method method, final Class<? extends Annotation> annotation){
		
		return (null!= method.getAnnotation(annotation));
	}
	
	/**
	 * Verifica se uma determinada possui uma anotação em particular.
	 * 
	 * @param method Método
	 * @param annotation Classe da anotação
	 * @return <code>false</code> caso o método não possua a anotação
	 */
	public boolean hasAnnotation(final Class<?> clazz, final Class<? extends Annotation> annotation){
		
		return (null!= clazz.getAnnotation(annotation));
	}
	
	public boolean hasGetter(final Class<?> clazz, final String attribute){
		
		for(Method _method : clazz.getMethods()){
			final String _name = _method.getName();
			if(_name.equals(getterOf(attribute)) || _name.equals(doIsName(attribute))){
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}
	
	/**
	 * Obtem a anotação em um determinado método.
	 * 
	 * @param <T> Tipo da anotação
	 * @param method Método
	 * @param annotation Classe da anotação
	 * @return <code>null</code> caso o método não possui a anotação.
	 */
	public <T extends Annotation> T annotationOf(final Method method, final Class<T> annotation){
		return method.getAnnotation(annotation);
	}
	
	/**
	 * Obtem a anotação em determinada objet
	 * @param <T> Tipo da anotação 
	 * @param object Objeto
	 * @param annotation Classe da anotação
	 * @return <code>null</code> caso a classe do objeto não possua a anotação.
	 */
	public <T extends Annotation> T annotationOf(final Object object, final Class<T> annotation){
		return annotationOf(object.getClass(), annotation);
	}
	
	/**
	 * Obterm a anotação em um determinada Classe
	 * @param <T> Tipo da anotação
	 * @param clazz Classe
	 * @param annotation Classe da anotação
	 * @return <code>null</code> caso a classe não possua a anotação.
	 */
	public <T extends Annotation> T annotationOf(final Class<?> clazz, final Class<T> annotation){
		return clazz.getAnnotation(annotation);
	}
	
	/**
	 * Obtem todos os métodos anotados em uma determinada classe
	 * @param <T> Tipo da anotação 
	 * @param clazz Classe para busca dos métodos anotados
	 * @param annotation Anotação
	 * @param type Tipo de método que será retornado na lista.<br/>
	 * Forneça <code>null</code> caso queria qualquer tipo de método.
	 * @return 
	 */
	public <T extends Annotation> List<Method> methodsOf(final Class<?> clazz, final Class<T> annotation, final MethodType type){
		
		final List<Method> _result = new ArrayList<Method>();
		for(Method _method : clazz.getMethods()){
			if(hasAnnotation(_method, annotation)){
				if(null!= type){
					if(match(_method, type)){
						_result.add(_method);
					}
				}
			}
		}
		return _result;
	}
	
    public List<Method> methodsOf(final Class<?> clazz, final MethodType type){
		
		final List<Method> _result = new ArrayList<Method>();
		for(Method _method : clazz.getMethods()){
			if(null!= type){
				if(match(_method, type)){
					_result.add(_method);
				}
			}
		}
		return _result;
	}
    
    public List<Method> methodsOf(final Class<?> clazz, final MethodType type, final Class<?> returned, final String prefix){
    	final List<Method> _result = new ArrayList<Method>();
    	
    	for(Method _method : clazz.getMethods()){
    		if(match(_method, type, returned, prefix)){
    			_result.add(_method);
    		}
    	}
    	
    	return _result;
    } 
    
    public Method methodOf(final Enum<?> e, final String name){
    	
    	Method _result = null;
    	for(Method _method : e.getClass().getMethods()){
    		if(_method.getName().equals(name)){
    			_result = _method;
    			break;
    		}
    	}
    	
    	return _result;
    }
	
	/**
	 * Verifica que o método informado trata-se do tipo informado
	 * @param method Método
	 * @param type Tipo para verificação
	 * @return
	 */
	public boolean match(final Method method, final MethodType type){
		
		if(MethodType.GETTER.equals(type)){
			return isGetter(method);
			
		} else if(MethodType.SETTER.equals(type)) {
			return isSetter(method);
		}
		
		return Boolean.FALSE;
	}
	
	public boolean match(final Method method, final MethodType type, final Class<?> returned, final String prefix){
		
		boolean _result = match(method, type);
		_result &= method.getReturnType().equals(returned);
		_result &= method.getName().startsWith(prefix);
		
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getInstance(final Class<T> clazz) {
		T _result = null;
		
		try{
			final Method _getInstance = methodOf(clazz, REFLECTION.METHOD_GET_INSTANCE);
			
			if(null!= _getInstance){
				_result = (T)invoke(null, _getInstance);
				LOGGING.trace("INSTÂNCIA >" + _result + "< OBTIDA ATRAVÉS DO MÉTODO ESTÁTICO >" + REFLECTION.METHOD_GET_INSTANCE + "<");
			}
			
		}catch(MethodNotFoundException _e){
			LOGGING.trace("MÉTODO >" + REFLECTION.METHOD_GET_INSTANCE + "< NÃO ENCONTRADO NA CLASSE >" + clazz + "<");
			
		}catch(InvokeException _e){
			LOGGING.trace("MÉTODO >" + REFLECTION.METHOD_GET_INSTANCE + "< NÃO PÔDE SER EXECUTADO! >" + clazz + "<");
		}
		
		return _result;
	}
	
	public <T> T newInstance(final Class<T> clazz) throws InvokeException{
		
		T _result = null;
		try{
			_result = getInstance(clazz);
			
			if(null== _result){
				_result = clazz.newInstance();
				LOGGING.trace("INSTANCIADO ATRAVÉS DO CONSTRUTOR DEFAULT >" + _result + "<");
			}
			
		}catch(IllegalAccessException _e){
			throw new InvokeException(_e.getMessage(), _e);
			
		}catch(InstantiationException _e){
			throw new InvokeException(_e.getMessage(), _e);
		}
		return _result;
	}
	
	public <T> T newInstance(final Class<T> clazz, final Object...args) {

		T _result = null;
		try{
			if(INTEGER._0== args.length){
				//utilizar construtor default
				_result = clazz.newInstance();
			} else {
				//tentar obter construtor
				final Class<?>[] _typeOf          = typeOf(args);
				final Constructor<T> _constructor = clazz.getConstructor(_typeOf);
				
				//utilizar construtor com argumentos
				_result = _constructor.newInstance(args);
			}
			
		}catch(IllegalAccessException _e){
			
			throw new InstanceNotCreatedException(_e.getMessage(), _e);
			
		}catch(InstantiationException _e){
			
			throw new InstanceNotCreatedException(_e.getMessage(), _e);
			
		}catch(NoSuchMethodException _e){
			
			throw new InstanceNotCreatedException(_e.getMessage(), _e);
			
		}catch(InvocationTargetException _e){
			
			throw new InstanceNotCreatedException(_e.getMessage(), _e);
			
		}
		
		return _result;
	}
	
	public <T> void copy(final T from, final T to, final CopyPolicyType policy) throws InvokeException {
		if(CopyPolicyType.PLAIN.equals(policy)){
			
			plainCopy(from, to);
			
		} else if(CopyPolicyType.DEEP.equals(policy)){
			
			deepCopy(from, to);
			
		} else {
			throw new IllegalArgumentException("UKNOW COPY POLICY: " + policy);
		}
	}
	
	private <T> void plainCopy(final T from, final T to) throws InvokeException {
		
		for(Method _getter : methodsOf(from.getClass(), MethodType.GETTER)){
			
			try{
				final Method _setter = setterOf(to.getClass(), attributeOf(_getter), _getter.getReturnType());
				final Object _value = invoke(from, _getter, REFLECTION.NO_ARGS);
				
				invoke(to, _setter, new Object[]{_value});
			}catch(ReflectionException _e){
				LOGGING.debug(I18N.METODO_SETTER_NAO_ENCONTRADO, _e);
			}
		}
		
		if(from instanceof AbstractDynamic 
				&& to instanceof AbstractDynamic){
			
			final AbstractDynamic _fdynamic = (AbstractDynamic)from;
			final AbstractDynamic _tdynamic = (AbstractDynamic)to;
			
			_tdynamic.setDynamics(_fdynamic.getDynamics());
		}
	}
	
	private <F, T extends F> void deepCopy(final F from, final T to) throws InvokeException {
		
		for(Method _getter : methodsOf(from.getClass(), MethodType.GETTER)){
			
			try{
				final Method _setter = setterOf(to.getClass(), attributeOf(_getter), _getter.getReturnType());
				final Object _value  = invoke(from, _getter);
				
				if(null!= _value){
					if(isSimple(_value.getClass())){//Valores primitivos, wrappers, dates e enums
						final Object _toSet = cloneSimple(_value);
						invoke(to, _setter, new Object[]{_toSet});
						
					} else if(isComplex(_value.getClass())){//Classe composta
						
						final Object _toSet = newInstance(_value.getClass());
						deepCopy(_value, _toSet);
						invoke(to, _setter, new Object[]{_toSet});
						
					} else if(isList(_value.getClass())) {//Lista
						
						final Object _toSet = cloneCollection(_value);
						invoke(to, _setter, new Object[]{_toSet});
						
					} else {//Map
						
						final Object _toSet = cloneMap(_value);
						invoke(to, _setter, new Object[]{_toSet});
					}
				}
			}catch(ReflectionException _e){
				LOGGING.debug(I18N.METODO_SETTER_NAO_ENCONTRADO, _e);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> T cloneSimple(final T t) throws IllegalArgumentException {
		
		T _result = null;
		if(null!= t){
			final Class<?> _c = t.getClass();
			
			if(isSimple(_c)){
				if(Byte.class.isAssignableFrom(_c) || Byte.TYPE.equals(_c)){
					final Byte _byte = (Byte)t;
					_result = (T) new Byte(_byte.byteValue());
					
				} else if(Short.class.isAssignableFrom(_c) || Short.TYPE.equals(_c)){
					final Short _short = (Short)t;
					_result = (T) new Short(_short.shortValue());
					
				} else if(Integer.class.isAssignableFrom(_c) || Byte.TYPE.equals(_c)){
					final Integer _integer = (Integer)t;
					_result = (T) new Integer(_integer.intValue());
					
				} else if(Long.class.isAssignableFrom(_c) || Long.TYPE.equals(_c)){
					final Long _long = (Long)t;
					_result = (T) new Long(_long.longValue());
					
				} else if(Float.class.isAssignableFrom(_c) || Float.TYPE.equals(_c)){
					final Float _float = (Float)t;
					_result = (T) new Float(_float.intValue());
					
				} else if(Double.class.isAssignableFrom(_c) || Double.TYPE.equals(_c)){
					final Double _double = (Double)t;
					_result = (T) new Double(_double.doubleValue());
					
				} else if(Character.class.isAssignableFrom(_c) || Character.TYPE.equals(_c)){
					final Character _char = (Character)t;
					_result = (T) new Character(_char.charValue());
					
				} else if(String.class.isAssignableFrom(_c)){
					final String _string = (String)t;
					_result = (T) new String(_string);
					
				} else if(Boolean.class.isAssignableFrom(_c) || Boolean.TYPE.equals(_c)){
					final Boolean _boolean = (Boolean)t;
					_result = (T) new Boolean(_boolean.booleanValue());
					
				} else if(Date.class.isAssignableFrom(_c)){
					final Date _date = (Date)t;
					_result = (T) new Date(_date.getTime());
					
				} else if(_c.isEnum()){
					_result = t;
					
				} else {
					throw new IllegalArgumentException(I18N.ARGUMENTO_NAO_SIMPLES);
				}
				
			} else {
				throw new IllegalArgumentException(I18N.ARGUMENTO_NAO_SIMPLES);
			}
		}

		return _result;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T cloneComplex(final T t) throws IllegalArgumentException {
		
		T _result = null;
		if(null!= t){
			final Class<?> _c = t.getClass();
			if(isComplex(_c)){
				_result = (T)newInstance(_c);
				
				deepCopy(t, _result);
			}
		}
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T cloneMap(final T t) throws IllegalArgumentException {
		T _result = null;
		
		if(null!= t){
			final Class<?> _c = t.getClass();
			
			if(t instanceof Map){
				final Map _map  = (Map)t;
				final Set _keys = _map.keySet();
				
				final Map _new = (Map)newInstance(_c);
				for(Object _key : _keys){
					final Object _value  = _map.get(_key);
					final Object _newKey = clone(_key);
					
					final Object _newValue = clone(_value);
					
					_new.put(_newKey, _newValue);
				}
				
				_result = (T)_new;
			} else {
				throw new IllegalArgumentException(Localization.getInstance().localize(I18N.ARGUMENTO_NAO_SUBCLASSE, Map.class));
			}
		}
		
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T cloneCollection(final T t) throws IllegalArgumentException {
		
		T _result = null;
		
		if(null!= t){
			final Class<?> _c = t.getClass();
			
			if(isList(_c)){
				if(t instanceof Collection){
					final Collection _collection = (Collection)t;
					final Iterator<Object> _iterator = _collection.iterator();
					
					final Collection _new = (Collection)newInstance(_c);
					while(_iterator.hasNext()){
						final Object _item = _iterator.next();
						
						Object _nitem = null;
						if(null!= _item){
							final Class<?> _iclass = _item.getClass();
							if(isSimple(_iclass)){
								_nitem = cloneSimple(_item);
								
							} else if(isComplex(_iclass)){
								
								_nitem = cloneComplex(_item);
								
							} else {
								
								_nitem = cloneCollection(_item);
							}
						}
						
						_new.add(_nitem);
					}
					
					_result = (T) _new;
				} else {
					throw new IllegalArgumentException(Localization.getInstance().localize(I18N.ARGUMENTO_NAO_SUBCLASSE, Collection.class));
				}
			} else {
				throw new IllegalArgumentException(Localization.getInstance().localize(I18N.ARGUMENTO_NAO_SUBCLASSE, Collection.class));
			}
		} 
		
		return _result;
	}
	
	public <T> T clone(final T t) throws IllegalArgumentException {
		
		T _result = null;
		
		if(null!= t){
			final Class<?> _c = t.getClass();
			
			if(isSimple(_c)){
				_result = cloneSimple(t);
				
			} else if(isComplex(_c)){
				_result = cloneComplex(t);
				
			} else if(isList(_c)) {
				
				_result = cloneCollection(t);
				
			} else /*Map*/ {
				_result = cloneMap(t);
			}
		}
		
		return _result;
	}
	
	public boolean isSimple(final Class<?> c){
		
		return   c.isPrimitive()
		       || c.isEnum()
		       || String.class.isAssignableFrom(c)
		       || Number.class.isAssignableFrom(c)
		       || Boolean.class.isAssignableFrom(c)
		       || Character.class.isAssignableFrom(c)
		       || Date.class.isAssignableFrom(c);
	}
	
	public boolean isComplex(final Class<?> c){
		return !isSimple(c) && !isList(c) && !isMap(c);
	}
	
	public boolean isList(final Class<?> c){
		
		return Collection.class.isAssignableFrom(c);
		
	}
	
	public boolean isMap(final Class<?> c){
		
		return Map.class.isAssignableFrom(c);
		
	}
	
	public Object valueOf(final Object instancia, final String property) throws ReflectionException {
		return valueOf(instancia, property, Boolean.FALSE);
	}
	
	public Object valueOf(final Object instancia, final String property, final boolean doThrow) throws ReflectionException {
		Object _result = null;
		
		if(null!= instancia){
			final Class<?> _clazz = instancia.getClass();
			
			final String[] _attributes = property.split(REGEX.DOT);
			try{
				final String _attribute = _attributes[INTEGER._0];
				
				final Method _getter = getterOf(_clazz, _attribute);
				_result = invoke(instancia, _getter, REFLECTION.NO_ARGS);
				
				if(_attributes.length > INTEGER._1){
					_result = valueOf(_result, property.substring(_attribute.length() + INTEGER._1));
				}
			}catch(InvokeException _e){
				if(doThrow){
					throw new ReflectionException(_e.getMessage(), _e);
				}
			}catch(ReflectionException _e){
				if(doThrow){
					throw _e;
				}
			}
		}
		return _result;
	}
	
	public Map<String, Object> valuesOf(final Object instancia, final String...properties) throws ReflectionException{
		
		final Map<String, Object> _result = new HashMap<String, Object>();
		if(null!= instancia){
			final Class<?> _clazz = instancia.getClass();
			
			for(String _property : properties){
				Object _value = null;
				
				final String[] _attributes = _property.split(REGEX.DOT);
				try{
					final String _attribute = _attributes[INTEGER._0];
					
					final Method _getter = getterOf(_clazz, _attribute);
					_value = invoke(instancia, _getter, REFLECTION.NO_ARGS);
					
					if(_attributes.length > INTEGER._1){
						_value = valueOf(_value, _property.substring(_attribute.length() + INTEGER._1));
					}
					
					_result.put(_property, _value);
					
				}catch(InvokeException _e){
					throw new ReflectionException(_e.getMessage(), _e);
				}
			}
		}
		
		return _result;
	}
	
	public void setValue(final Object instancia, final String property, final Object value) throws ReflectionException {
		
		if(null!= instancia){
			final Class<?> _clazz = instancia.getClass();
			
			final String[] _attributes = property.split(REGEX.DOT);
			try{
				final String _attribute = _attributes[INTEGER._0];
				
				if(_attributes.length > INTEGER._1){
					final Method _getter = getterOf(_clazz, _attribute);
					final Object _got    = invoke(instancia, _getter, REFLECTION.NO_ARGS);
					
					setValue(_got, property.substring(_attribute.length() + INTEGER._1), value);
					
				} else {
						
					final Method _setter = setterOf(_clazz, _attribute);
					invoke(instancia, _setter, value);
						
				}
				
			}catch(InvokeException _e){
				throw _e;
			}
 		}
	}
	
	public void setValue(final String field, final Object instancia, final Object value) throws ReflectionException{
		
		if(null!= instancia){
			final Class<?> _clazz = instancia.getClass();
			final Field _field    = fieldOf(_clazz, field);
			
			try{
				_field.set(instancia, value);
			}catch(IllegalAccessException _e){
				throw new ReflectionException(_e.getMessage(), _e);
			}
		}
	}
	
	public void setValues(final Object instancia, final Map<String, Object> mapping) throws ReflectionException {
		
		if(null!= instancia){
			final Class<?> _clazz = instancia.getClass();
			
			final Set<String> _properties = mapping.keySet();
			for(String _property : _properties){
				final Object _value        = mapping.get(_property);
				final String[] _attributes = _property.split(REGEX.DOT);
				
				try{
					final String _attribute = _attributes[INTEGER._0];
					
					if(_attributes.length > INTEGER._1){
						final Method _getter = getterOf(_clazz, _attribute);
						final Object _got    = invoke(instancia, _getter, REFLECTION.NO_ARGS);
						
						setValue(_got, _property.substring(_attribute.length() + INTEGER._1), _value);
						
					} else {
						final Method _setter = setterOf(_clazz, _attribute);
						invoke(instancia, _setter, _value);
					}
					
				}catch(InvokeException _e){
					throw _e;
				}
			}
		}
	}
	
	public List<Class<?>> genericsOf(final Class<?> clazz) {
		
		final List<Class<?>> _result = new ArrayList<Class<?>>();
		try{
			final Type _superg = clazz.getGenericSuperclass();
			if(_superg instanceof ParameterizedType){
				final ParameterizedType _ptype = (ParameterizedType)_superg;
				
				for(Type _arg : _ptype.getActualTypeArguments()){
					_result.add((Class<?>)_arg);
				}
			}
		}catch(GenericSignatureFormatError _e){
			_result.clear();
			
		}catch(TypeNotPresentException _e){
			_result.clear();
			
		}catch(MalformedParameterizedTypeException _e){
			_result.clear();
		}
		
		return _result;
	}
	
	public List<Class<?>> genericsOf(final Method method, final MethodStructure toReturn) throws IllegalArgumentException{
		
		final List<Class<?>> _result = new ArrayList<Class<?>>();
		if(MethodStructure.RETURN.equals(toReturn)){
			
			final Type _type = method.getGenericReturnType();
			if(_type instanceof ParameterizedType){
				final ParameterizedType _pty = (ParameterizedType)_type;
				
				final Type _acty = _pty.getActualTypeArguments()[INTEGER._0]; 
				if(_acty instanceof Class){
					
					if(_pty.getActualTypeArguments()[INTEGER._0] instanceof Class){
						_result.add((Class<?>)_pty.getActualTypeArguments()[INTEGER._0]);
					} else {
						_result.add(Object.class);
					}
					
				} else if(_acty instanceof ParameterizedType) {
					final ParameterizedType __pty = (ParameterizedType)_acty;
					
					if(__pty.getActualTypeArguments()[INTEGER._0] instanceof Class){
						_result.add((Class<?>)__pty.getActualTypeArguments()[INTEGER._0]);
					} else {
						_result.add(Object.class);
					}
				}
			}
		} else if(MethodStructure.PARAMETER.equals(toReturn)){
			throw new UnsupportedOperationException();
			
		} else if(MethodStructure.THROWS.equals(toReturn)){
			throw new UnsupportedOperationException();
			
		} else {
			throw new IllegalArgumentException(Localization.getInstance().localize(I18N.ARGUMENTO_N_VALOR_DESCONHECIDO, INTEGER._2, toReturn));
		}
		
		return _result;
	}
	
	public List<Enum<?>> valueOf(final Enum<?> e){
		
		final List<Enum<?>> _result = new ArrayList<Enum<?>>();
		final Method _values = methodOf(e, REFLECTION.VALUES_METHOD);
		if(null!= _values){
			try{
				final Object _o = invoke(e, _values, new Object[]{});
				if(null!= _o){
					final Enum<?>[] _array = (Enum[])_o;
					for(Enum<?> _enum : _array){
						_result.add(_enum);
					}
				}
			}catch(InvokeException _e){
				LOGGING.warn(_e.getMessage(), _e);
			}
		}
		
		return _result;
	}
	
	public boolean anyMatch(final List<? extends IDynamic> dynamics, final String property, final Object...value){
		
		boolean _result = Boolean.FALSE;
		
		for(IDynamic _dynamic : dynamics){
			final Object _value = _dynamic.get(property);
			
			for(Object _another : value){
				_result |= _another.equals(_value);
			}
		}
		
		return _result;
	}
	
	public boolean anyMatch(final IDynamic dynamic, final String property, final Object...value){
		
		boolean _result = Boolean.FALSE;
		
		final Object _value = dynamic.get(property);
		
		for(Object _another : value){
			_result |= _another.equals(_value);
		}
		
		return _result;
	}
	
	/**
	 * Atribuir valor field static e publico
	 * @param field
	 * @param value
	 */
	public void setValue(final Field field, final Object value){
		
		try{
			field.set(null, value);
		}catch(IllegalAccessException _e){
		}
		
	}
	
	public Field fieldOf(final Class<?> clazz, final String name) throws MemberNotFoundException {
		Field _result = null;
		
		try{
			_result = clazz.getField(name);
			
		}catch(NoSuchFieldException _e){
			throw new MemberNotFoundException(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public List<Field> fieldsOf(final Class<?> clazz, final Class<?> fieldType){
		final List<Field> _result = new ArrayList<Field>();
		
		for(Field _field : clazz.getFields()){
			if(fieldType.isAssignableFrom(_field.getType())){
				_result.add(_field);
			}
		}
		
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T valueOf(final Object target, final String field, final Class<T> type) throws InaccessibleMemberException {
		T _result = null;
		
		try{
			Class<?> _class = target.getClass();
			if(target instanceof Class){
				_class = (Class<?>)target;
			}
			_result = (T)fieldOf(_class, field).get(target);
			
		}catch(IllegalAccessException _e){
			throw new InaccessibleMemberException(_e.getMessage(), _e);
		}
		
		
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T valueOf(final Object target, final Field field, final Class<T> type) throws InaccessibleMemberException {
		T _result = null;
		
		try{
			_result = (T)field.get(target);
			
		}catch(IllegalAccessException _e){
			throw new InaccessibleMemberException(_e.getMessage(), _e);
		}
		
		
		return _result;
	}
	
	public <T> List<T> clone(final List<T> toClone){
		final List<T> _result = new ArrayList<T>();
		
		for(T _value : toClone){
			_result.add(_value);
		}
		
		return _result;
	}
	
	public GenericsWorkaround workaroundOf(final String property, final GenericsWorkarounds workarounds){
		
		GenericsWorkaround _result = null;
		if(null!= workarounds){
			for(GenericsWorkaround _workaround : workarounds.value()){
				if(_workaround.property().equals(property)){
					_result = _workaround;
					break;
				}
			}
		}
		return _result;
	}
	
	public static void main(String...args){
		System.out.println(UtilCollection.getInstance());
		System.out.println(UtilClass.getInstance());
		System.out.println(getInstance());
		System.out.println(getInstance());
		System.out.println(getInstance());
		System.out.println(getInstance());
		System.out.println(getInstance());
		
		final Thread _thread = new Thread(){
			public void run(){
				
				for(long _i = 0; _i<=5000000L; _i++){
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
				}
			}
		};
		_thread.start();
		
		final Thread _thread2 = new Thread(){
			public void run(){
				
				for(long _i = 0; _i<=5000000L; _i++){
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
				}
			}
		};
		_thread2.start();
		
		final Thread _thread3 = new Thread(){
			public void run(){
				
				for(long _i = 0; _i<=5000000L; _i++){
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
				}
			}
		};
		_thread3.start();
		
		final Thread _thread5 = new Thread(){
			public void run(){
				
				for(long _i = 0; _i<=5000000L; _i++){
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
				}
			}
		};
		_thread5.start();
		
		final Thread _thread6 = new Thread(){
			public void run(){
				
				for(long _i = 0; _i<=5000000L; _i++){
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
				}
			}
		};
		_thread6.start();
		
		final Thread _thread7 = new Thread(){
			public void run(){
				
				for(long _i = 0; _i<=5000000L; _i++){
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
					System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
				}
			}
		};
		_thread7.start();
		
		System.out.println(UtilCollection.getInstance());
		System.out.println(UtilCollection.getInstance());
		System.out.println(UtilClass.getInstance());
		System.out.println(UtilCollection.getInstance());
		System.out.println(UtilClass.getInstance());
		System.out.println(UtilCollection.getInstance());
		System.out.println(UtilClass.getInstance());
		System.out.println(UtilCollection.getInstance());
		System.out.println(UtilClass.getInstance());
		System.out.println(UtilCollection.getInstance());
		System.out.println(UtilClass.getInstance());
		System.out.println(UtilClass.getInstance());
		
		for(long _i = 0; _i<=5000000L; _i++){
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
			System.out.println(Thread.currentThread() + "  " + getInstance());
			System.out.println(Thread.currentThread() + "  " + getInstance());
			System.out.println(Thread.currentThread() + "  " + getInstance());
			System.out.println(Thread.currentThread() + "  " + getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilCollection.getInstance());
			System.out.println(Thread.currentThread() + "  " + UtilClass.getInstance());
		}
	}
}
