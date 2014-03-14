package bradesco.tf.validate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.javaf.ObjectPool;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.PLACE_HOLDER;
import com.javaf.javase.lang.reflect.MethodStructure;
import com.javaf.javase.lang.reflect.ReflectionException;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;
import com.javaf.model.ValuePlace;
import com.javaf.pattern.Visitor;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.dataservice.IListObject;
import br.com.bradesco.core.aq.dataservice.ISimpleObject;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import bradesco.tf.TerminalFinanceiro;
import bradesco.tf.UtilIdentify;
import bradesco.tf.dsn.DSNPlaceBoolean;
import bradesco.tf.dsn.DSNPlaceFloat;
import bradesco.tf.dsn.DSNPlaceInteger;
import bradesco.tf.dsn.DSNPlaceObjectList;
import bradesco.tf.dsn.DSNPlaceString;
import bradesco.tf.dsn.UtilDSN;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public class DataServiceNodeValidatorVisitor implements Visitor<Validator, Validator> {
	
	private final UtilReflection reflection      = UtilReflection.getInstance();
	private final UtilDSN dsn                    = UtilDSN.getInstance();
	private final TerminalFinanceiro arquitetura = TerminalFinanceiro.getInstance();
	private final UtilIdentify identify          = UtilIdentify.getInstance();
	private final ILogging logging               = Logging.loggerOf(getClass());
	private final ILocalization localize;
	
	private IEventsServiceProvider provider;
	private IOperation operation;
	private IDataServiceNode node;
	public DataServiceNodeValidatorVisitor(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node){
		this.provider = provider;
		this.operation = operation;
		this.node = node;
		
		localize = Localization.getInstance();
	}
	
	private ValuePlace<?> visit(final ValuePlace<?> vp, final Class<?> tyype){
		
		ValuePlace<?> _result = vp;
		
		Class<?> _type = tyype;
		
		if(vp instanceof ConfigurePlace){
			final ConfigurePlace<?> _cp = (ConfigurePlace<?>)vp;
			
			final ObjectPool _opool = arquitetura.getObjectPool(provider);
			
			final String _prefixo = _opool.get(identify.doPrefixo(operation), String.class);
			String _id = _cp.getValueName();
			_id = _id.replace(PLACE_HOLDER.INSTANCIA_PREFIXO, _prefixo);
			
			//tentar o tipo configurado no XML
			if(Object.class.isAssignableFrom(_type)){
				final String _stype = _cp.getValueClass();
				
				try{
					_type = Class.forName(_stype);
				}catch(ClassNotFoundException _e){
					logging.warn(_e.getMessage(), _e);
				}
			}

			if(List.class.isAssignableFrom(_type)){//IListObject
				final IListObject _list = dsn.getOrAddListObject(_id, node);
				
				_result = new DSNPlaceObjectList(_cp.getLabel(), _list);
				
			} else {//ISimpleObject
				final ISimpleObject _simple = dsn.getOrAddSimpleObject(_id, node);
				
				if(Boolean.class.isAssignableFrom(_type)){
					_result = new DSNPlaceBoolean(_cp.getLabel(), _simple);
					
				} else if(Integer.class.isAssignableFrom(_type)){
					_result = new DSNPlaceInteger(_cp.getLabel(), _simple);
					
				} else if(Float.class.isAssignableFrom(_type)){
					_result = new DSNPlaceFloat(_cp.getLabel(), _simple);
					
				} else {//default is String
					_result = new DSNPlaceString(_cp.getLabel(), _simple);
				}
			}
			
			//configurar label
			_result.setLabel(localize.localize(vp.getLabel()));
			
		} else {
			throw new IllegalArgumentException(localize.localize(I18N.METHODO_X_NAO_RETORNOU_INSTANCIA_DE, ConfigurePlace.class, vp));
		}
		
		return _result;
	}

	public Validator visit(final Validator v){

		final List<Method> _getters = reflection.getterOf(v.getClass(), ValuePlace.class);
		_getters.addAll(reflection.getterOf(v.getClass(), List.class));
		
		for(Method _getter : _getters){
			
			final String _attribute = reflection.attributeOf(_getter);
			
			boolean _isList = Boolean.FALSE;
			
			Method _setter = null;
			try{
				_setter = reflection.setterOf(v.getClass(), _attribute, ValuePlace.class);
				
			}catch(ReflectionException _e){
				
				_setter = reflection.setterOf(v.getClass(), _attribute, List.class);
				_isList = Boolean.TRUE;
			}
			
			final List<Class<?>> _types = reflection.genericsOf(_getter, MethodStructure.RETURN);
			Object _toSet = null;
			
			ValuePlace<?> _vp = null;
			
			if(!_types.isEmpty() && _types.size() == INTEGER._1){
				
				if(!_isList){
					_vp = (ValuePlace<?>)reflection.invoke(v, _getter);
					_toSet = visit(_vp, _types.get(INTEGER._0));
					
				} else {
					final List<Object> __toSet = new ArrayList<Object>();
					final List<?> _list = (List<?>)reflection.invoke(v, _getter);
					for(Object _object : _list){
						final ValuePlace<?> __vp = (ValuePlace<?>)_object;
						
						__toSet.add(visit(__vp, _types.get(INTEGER._0)));
					}
					
					_toSet = __toSet;
				}
			} else {
				throw new IllegalArgumentException(localize.localize(I18N.CLASSE_X_NAO_SEGUE_GENERICS, v.getClass()));
			}
			
			reflection.invoke(v, _setter, new Object[]{_toSet});
		}
		
		//configurar mensagens
		v.setMessage(localize.localize(v.getMessage()));
		
		return v;
	}

}
