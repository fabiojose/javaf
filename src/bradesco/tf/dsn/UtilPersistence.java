package bradesco.tf.dsn;

import com.javaf.Constants.REFLECTION;
import com.javaf.Constants.STRING;
import com.javaf.javase.persistence.annotation.Transient;
import com.javaf.javase.persistence.annotation.TransientPolicy;

/**
 * Utilidades para manipulações nas persistências.
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilPersistence {
	private static final UtilPersistence INSTANCE = new UtilPersistence();
	
	private UtilPersistence(){
		
	}
	
	public static synchronized UtilPersistence getInstance(){
		return INSTANCE;
	}
	
	public boolean isTransientRead(final Transient t){
		
		if(null!= t){
			return TransientPolicy.READ.equals( t.value() ) || isTransient(t);
		}
		
		return Boolean.FALSE;
	}
	
    public boolean isTransientWrite(final Transient t){
		
		if(null!= t){
			return TransientPolicy.WRITE.equals( t.value() ) || isTransient(t);
		}
		
		return Boolean.FALSE;
	}
    
    public boolean isTransient(final Transient t){
		
		if(null!= t){
			return TransientPolicy.READ_WRITE.equals(t.value());
		}
		
		return Boolean.FALSE;
	}
    
    public void writeEntryCollection(final SerializerMediator mediator, final String property){
    	mediator.write(property + STRING.DOT + REFLECTION.ATT_ENTRIES);
    	mediator.write(property + STRING.DOT + REFLECTION.ATT_SELECTED_INDEXES);
    }
    
    public void readEntryCollection(final SerializerMediator mediator, final String property){
    	mediator.read(property + STRING.DOT + REFLECTION.ATT_SELECTED_INDEXES);
    }
}
