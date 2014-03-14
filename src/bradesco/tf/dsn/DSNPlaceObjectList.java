package bradesco.tf.dsn;

import java.util.ArrayList;
import java.util.List;

import com.javaf.model.ValuePlace;

import br.com.bradesco.core.aq.dataservice.IDataObject;
import br.com.bradesco.core.aq.dataservice.IListObject;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public class DSNPlaceObjectList extends ValuePlace<List<Object>> {
	
	private IListObject list;
	public DSNPlaceObjectList(final String label, final IListObject list){
		setLabel(label);
		this.list = list;
	}

	public List<Object> getValue() {

		final List<Object> _result = new ArrayList<Object>();
		
		for(IDataObject _object : list.getAllDataObjects()){
			_result.add(_object);
		}
		
		return _result;
	}

	public void reset() {
		list.reset();
	}
	
	public String getName(){
		return list.getFullKey();
	}

}
