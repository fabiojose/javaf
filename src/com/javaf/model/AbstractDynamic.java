package com.javaf.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import com.javaf.Constants.I18N;
import com.javaf.javase.lang.reflect.ReflectionException;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.util.Localization;



public abstract class AbstractDynamic implements IDynamic {
	private static final long serialVersionUID = -3364151037730466912L;

	protected final UtilReflection reflection = UtilReflection.getInstance();
	
	private boolean exceptions;
	
	private PropertyChangeSupport support;
	
	//armazenar propiedades dinamicas do bean
	private final Map<String, Object> dynamics = new HashMap<String, Object>();
	private final ILogging logging             = Logging.loggerOf(getClass());
	public AbstractDynamic(){
		exceptions = Boolean.FALSE;
	}
	
	protected final PropertyChangeSupport getChangeSupport(){
		if(null== support){
			support = new PropertyChangeSupport(this);
		}
		
		return support;
	}
	
	public void addPropertyChangeListener(final String property, final PropertyChangeListener listener){
		getChangeSupport().addPropertyChangeListener(property, listener);
	}
	
	public void removePropertyChangeListener(final String property, final PropertyChangeListener listener){
		getChangeSupport().removePropertyChangeListener(property, listener);
	}
	
	public final Object get(final String property){
		
		Object _result = null;
		
		try{
			
			final Object _o = reflection.valueOf(this, property, Boolean.TRUE);
			_result = _o;
			
		}catch(ReflectionException _e){
			//tentar obter no mapa de propriedades dinamicas
			final Object _o = dynamics.get(property);
			if(null== _o){
				if(isExceptions()){
					throw new PropertyNotFoundException(Localization.getInstance().localize(I18N.PROPRIEDADE_NAO_ENCONTRADA, property), _e);
				} else {
					logging.debug(Localization.getInstance().localize(I18N.PROPRIEDADE_NAO_ENCONTRADA, property));
				}
			} else {
				_result = _o;
			}
		}
		
		return _result;
	}

	@SuppressWarnings("unchecked")
	public final <T> T get(final String property, final Class<T> type) throws PropertyNotFoundException {

		T _result = null;
		
		try{
			
			final Object _o = reflection.valueOf(this, property, Boolean.TRUE);
			_result = (T)_o;
			
		}catch(ReflectionException _e){
			//tentar obter no mapa de propriedades dinamicas
			final Object _o = dynamics.get(property);
			if(null== _o){
				if(isExceptions()){
					throw new PropertyNotFoundException(Localization.getInstance().localize(I18N.PROPRIEDADE_NAO_ENCONTRADA, property), _e);
				} else {
					logging.debug(Localization.getInstance().localize(I18N.PROPRIEDADE_NAO_ENCONTRADA, property));
				}
			} else {
				_result = (T)_o;
			}
		}
		
		return _result;
	}

	public final void set(final String property, final Object value) throws PropertyNotFoundException {
		
		try{
			reflection.setValue(this, property, value);
			
		}catch(ReflectionException _e){
			if(null== value){
				throw new PropertyNotFoundException(Localization.getInstance().localize(I18N.PROPRIEDADE_NAO_ENCONTRADA, property), _e);
			} else {
				//armazenar no mapa de propriedades dinamicas
				dynamics.put(property, value);
			}
		}
	}

	public boolean isExceptions() {
		return exceptions;
	}

	public void setExceptions(boolean exceptions) {
		this.exceptions = exceptions;
	}
	
	public Map<String, Object> getDynamics(){
		return dynamics;
	}
	
	public void setDynamics(final Map<String, Object> dynamics){
		this.dynamics.putAll(dynamics);
	}
}
