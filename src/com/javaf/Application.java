package com.javaf;

import com.javaf.Constants.APPLICATION;
import com.javaf.Constants.STRING;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class Application {
	private static final Application INSTANCE = new Application();
	
	protected final ILogging logging = Logging.loggerOf(getClass());
	
	protected Application(){
		
	}
	
	public static synchronized Application getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Atualizar as propriedades default, bem como acrescente novas.<br>
	 * **Atenção** Os valores devem ser aplicados antes de obter quaisquer atributos da aplicação. Caso
	 * alguma classe utilitária utilize propriedades vitais para seu funcionamento, essas devem ser
	 * configuradas antes de mais nada.
	 * @param property Nome da propriedade
	 * @param value Valore da propriedade
	 * @see #valueOf(Class)
	 * @see #valueOf(Class, Object)
	 * @see APPLICATION
	 */
	public void register(final Object property, final Object value){
		final Object _previous = APPLICATION.PROPERTIES.put(property, value);
		
		if(null!= _previous){
			logging.info("REGISTRANDO *NOVO* VALOR PARA PROPRIEDADE DE APLICAÇÃO " + property + ": >" + value + "<, antigo: >" + _previous + "<");
		} else {
			logging.info("REGISTRANDO VALOR PARA PROPRIEDADE DE APLICAÇÃO " + property + ": >" + value + "<");
		}
	}
	
	/**
	 * Intermediar a obtenção dos valores configurados ou programaticamente definidos das propriedades.
	 * @param <T> Tipo genérico da propriedade
	 * @param property Chave da propriedade.
	 * @return <code>null</code> caso o valor da propriedade não seja resolvido.
	 */
	@SuppressWarnings("unchecked")
	public <T> T valueOf(final Class<T> property){
		T _result = null;
		
		_result = (T)APPLICATION.PROPERTIES.get(property);
		logging.debug(property + STRING.IGUAL_SPACE + _result);
		
		return _result;
	}
	
	/**
	 * Intermediar a obtenção dos valores configurados ou programaticamente definidos das propriedades.
	 * @param <T> Tipo genérico da propriedade
	 * @param type Tipo da propriedade
	 * @param property Chave da propriedade
	 * @return <code>null</code> caso o valor da propriedade não seja resolvido.
	 */
	@SuppressWarnings("unchecked")
	public <T> T valueOf(final Class<T> type, final Object property){
		T _result = null;
		
		_result = (T)APPLICATION.PROPERTIES.get(property);
		logging.debug("OBTENDO VALOR DA PROPRIEDADE DE APLICAÇÃO " + property + STRING.IGUAL_SPACE + _result + STRING.PARENTESES_ABRE + type + STRING.PARENTESES_FECHA);
		
		return _result;
	}
	
	/**
	 * Intermediar a obtenção dos valores configurados ou programaticamente definidos das propriedades.
	 * @param property Chave da propriedade
	 * @return <code>null</code> caso a propriedade não exista
	 */
	public Object valueOf(final Object property){
		Object _result = null;
		
		_result = APPLICATION.PROPERTIES.get(property);
		logging.debug("OBTENDO VALOR DA PROPRIEDADE DE APLICAÇÃO " + property + STRING.IGUAL_SPACE + _result);
		
		return _result;
	}
}
