package bradesco.tf.dsn;

import java.util.Collection;

import com.javaf.Constants.INTEGER;
import com.javaf.pattern.Breathable;

import br.com.bradesco.core.aq.dataservice.IListObject;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public class DataServiceNodeListObjectPersistence implements Serializer {

	private UtilDSN dsn = UtilDSN.getInstance();
	
	private IListObject list;
	private Collection<? extends Object> instancia;
	
	public DataServiceNodeListObjectPersistence(final IListObject list, final Collection<? extends Object> instancia){
		this.list = list;
		this.instancia = instancia;
	}
	
	public void read() throws IllegalStateException {

	}

	public void write() throws IllegalStateException {

		list.reset();
		
		if(instancia instanceof Breathable){
			final Breathable _breathable = (Breathable)instancia;
			_breathable.breath();
		}
		
		int _index = INTEGER._0;
		for(Object _object : instancia){
			dsn.toContainerObject(list, _object, _index++);
		}
	}

}
