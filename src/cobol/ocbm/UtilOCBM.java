package cobol.ocbm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cobol.CobolConstants.OCBM;

import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.text.UtilFormat;

/**
 * Utilidades para Object-Cobol-Book Mapping.
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class UtilOCBM {
	private static final UtilOCBM INSTANCE = new UtilOCBM();
	
	private UtilReflection reflection;
	private UtilFormat format;
	private UtilOCBM(){
		reflection = UtilReflection.getInstance();
		format = UtilFormat.getInstance();
	}
	
	/**
	 * Obter uma instância de trabalho.
	 * @return
	 */
	public static synchronized UtilOCBM getInstance(){
		return INSTANCE;
	}
	
	public Process getProcess(final Object ocbm){
		
		Process _result = reflection.annotationOf(ocbm.getClass(), Process.class);
		
		return _result;
	}
	
	public List<AttributeOverride> getOverrides(final Method method){
		
		final List<AttributeOverride> _result = new ArrayList<AttributeOverride>();
		final AttributeOverrides _overrides = reflection.annotationOf(method, AttributeOverrides.class);
		if(null!= _overrides){
			for(AttributeOverride _override : _overrides.value()){
				_result.add(_override);
			}
		}
		
		return _result;
	}
	
	public AttributeOverride getOverride(final AttributeOverrides overrides, final String attribute){
		
		if(null!= overrides){
			for(AttributeOverride _override : overrides.value()){
				if(_override.nome().equals(attribute)){
					return _override;
				}
			}
		}
		
		return null;
	}
	
	public AttributeOverride getOverride(final List<AttributeOverride> overrides, final String attribute){
		
		AttributeOverride _result = null;
		if(null!= overrides){
			for(AttributeOverride _override : overrides){
				if(_override.nome().equals(attribute)){
					_result = _override;
					break;
				}
			}
		}
		
		return _result;
	}
	
	public FieldOverride getOverride(final List<FieldOverride> overrides, final String attribute){
		
		FieldOverride _result = null;
		if(null!= overrides){
			for(FieldOverride _override : overrides){
				if(_override.nome().equals(attribute)){
					_result = _override;
					break;
				}
			}
		}
		return _result;
	}
	
	public List<AttributeOverride> getOverrides(final AttributeOverrides overrides){
		
		List<AttributeOverride> _result = new ArrayList<AttributeOverride>();
		if(null!= overrides){
			_result = format.toList(overrides.value());
		}
		
		return _result;
	}
	
	public List<FieldOverride> getOverrides(final Occurs occurrs){
		
		List<FieldOverride> _result = new ArrayList<FieldOverride>();
		if(null!= occurrs){
			_result = format.toList(occurrs.overrides().value());
		}
		
		return _result;
	}
	
	public OccursMappingOverride getOverrides(final String property, final OccursMappingOverrides overrides){
		
		OccursMappingOverride _result = null;
		for(OccursMappingOverride _override : overrides.value()){
			if(_override.nome().equals(property)){
				_result = _override;
				break;
			}
		}
		return _result;
	}
	
	public List<Dynamic> getDynamic(final Class<?> clazz){
		final List<Dynamic> _result = new ArrayList<Dynamic>();
		
		final AttributeOverrides _overrides = reflection.annotationOf(clazz, AttributeOverrides.class);
		for(AttributeOverride _override : _overrides.value()){
			final Dynamic _dynamic = 	_override.dynamic();
			if( !OCBM.AUSENTE.equals(_dynamic.nome())) {
				_result.add(_dynamic);
			}
		}
		
		return _result;
	}
	
	public List<AttributeOverride> getDynamicOverride(final Class<?> clazz){
		final List<AttributeOverride> _result = new ArrayList<AttributeOverride>();
		
		final AttributeOverrides _overrides = reflection.annotationOf(clazz, AttributeOverrides.class);
		
		if(null!= _overrides){
			for(AttributeOverride _override : _overrides.value()){
				final Dynamic _dynamic = 	_override.dynamic();
				if( !OCBM.AUSENTE.equals(_dynamic.nome())) {
					_result.add(_override);
				}
			}
		}
		return _result;
	}
	
	public FieldOverride getDynamicOverride(final List<FieldOverride> overrides, final String attribute){
		
		FieldOverride _result = null;
		if(null!= overrides){
			for(FieldOverride _override : overrides){
				final Dynamic _dynamic = _override.dynamic();
				
				if(_dynamic.nome().equals(attribute)){
					_result = _override;
					break;
				}
			}
		}
		return _result;
	}
	
	public AttributeOverride getDynamicOverride(final String attribute, final List<AttributeOverride> overrides){
		
		AttributeOverride _result = null;
		if(null!= overrides){
			for(AttributeOverride _override : overrides){
				final Dynamic _dynamic = _override.dynamic();
				
				if(_dynamic.nome().equals(attribute)){
					_result = _override;
					break;
				}
			}
		}
		return _result;
	}
	
	public List<FieldOverride> getDynamicFieldOverride(final FieldOverrides overrides){
		final List<FieldOverride> _result = new ArrayList<FieldOverride>();
		
		for(FieldOverride _override : overrides.value()){
			final Dynamic _dynamic = 	_override.dynamic();
			if( !OCBM.AUSENTE.equals(_dynamic.nome())) {
				_result.add(_override);
			}
		}
		
		return _result;
	}
} 
