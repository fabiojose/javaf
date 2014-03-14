package bradesco.tf.dsn;

import java.lang.reflect.Method;

import com.javaf.Constants.INTEGER;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.persistence.annotation.TransientPolicy;
import com.javaf.javase.text.annotation.Formatter;

import br.com.bradesco.core.aq.dataservice.ISimpleObject;
import bradesco.tf.StorageType;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public class DataServiceNodeSimplePersistence implements Serializer {
	
	private UtilReflection reflection = UtilReflection.getInstance();
	private UtilDSN dsn = UtilDSN.getInstance();
	
	private ISimpleObject simple;
	
	private Object instancia;
	private Method getter;
	private Method setter;
	
	private StorageType storage;
	
	private TransientPolicy policy;
	
	public DataServiceNodeSimplePersistence(final ISimpleObject simple, final Object instancia, final Method getter, final Method setter, final StorageType storage){
		this.simple    = simple;
		this.instancia = instancia;
		this.getter    = getter;
		this.setter    = setter;
		this.storage   = storage;
	}
	
	public DataServiceNodeSimplePersistence(final ISimpleObject simple, final Object instancia, final Method getter, final Method setter, final TransientPolicy policy, StorageType storage){
		this(simple, instancia, getter, setter, storage);
		this.policy = policy;
	}
	
	public void read() {		
		
		if(!TransientPolicy.READ_WRITE.equals(policy) && !TransientPolicy.READ.equals(policy)){
			final Formatter _formatter = reflection.annotationOf(getter, Formatter.class);

			final Object _value = dsn.getValue(simple, setter.getParameterTypes()[INTEGER._0], _formatter, storage);
			reflection.invoke(instancia, setter, new Object[]{_value});
		}
	}

	public void write() {
		
		if(!TransientPolicy.READ_WRITE.equals(policy) && !TransientPolicy.WRITE.equals(policy)){
			final Formatter _formatter = reflection.annotationOf(getter, Formatter.class);
			
			final Object _value = reflection.invoke(instancia, getter, new Object[]{});
			dsn.setValue(simple, _value, getter.getReturnType(), _formatter);
		}
	}
	
	public String toString(){
		return simple.getFullKey();
	}
}
