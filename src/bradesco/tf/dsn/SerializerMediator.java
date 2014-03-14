package bradesco.tf.dsn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.javaf.Constants.I18N;
import com.javaf.javase.logging.Logging;

/**
 * Mediar a leitura e gravação de um conjunto de persistências.
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class SerializerMediator implements Serializer {

	private Map<String, Serializer> persistences;
	
	public SerializerMediator(){
		this.persistences = new HashMap<String, Serializer>();
	}
	
	public void read() {
		
		final Set<String> _keys = persistences.keySet();
		for(String _key : _keys){
			final Serializer _persistence = persistences.get(_key);
			
			if(null!= _persistence){
				_persistence.read();
			}

		}
	}
	
	public void read(final String property){
		final Serializer _persistence = persistences.get(property);
		
		if(null!= _persistence){
			_persistence.read();
			
		}
	}
	
	public void readByPattern(final String regex){
		final Set<String> _keys = persistences.keySet();
		
		for(String _key : _keys){
			if(_key.matches(regex)){
				persistences.get(_key).read();
			}
			
			Logging.loggerOf(getClass()).debug(_key);
		}
	}
	
	public void removeByPattern(final String regex){
		final Set<String> _keys = persistences.keySet();
		
		final List<String> _toRemove = new ArrayList<String>();
		for(String _key : _keys){
			if(_key.matches(regex)){
				_toRemove.add(_key);
				
				Logging.loggerOf(getClass()).debug(_key + I18N.WAS_REMOVED);
			}
		}
		
		for(String _remove : _toRemove){
			persistences.remove(_remove);
		}
	}

	public void write() {
		for(Serializer _persistence : persistences.values()){
			_persistence.write();
		}
	}
	
	public void write(final String property){
		final Serializer _persistence = persistences.get(property);
		
		if(null!= _persistence){
			_persistence.write();
		}
	}
	
	public void addPersistence(final String property, final Serializer persistence) {
		this.persistences.put(property, persistence);
	}
	
}
