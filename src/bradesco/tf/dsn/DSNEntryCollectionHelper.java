package bradesco.tf.dsn;

import br.com.bradesco.core.aq.dataservice.IContainerObject;
import br.com.bradesco.core.aq.dataservice.IListObject;
import bradesco.tf.TFConstants.DSN;

import com.javaf.Constants.INTEGER;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public class DSNEntryCollectionHelper {
	private UtilDSN dsn = UtilDSN.getInstance();

	private IContainerObject container;
	public DSNEntryCollectionHelper(final IContainerObject container){
		this.container = container;
	}
	
	public IListObject getSelectedIndexes(){
		return dsn.getOrAddListObject(DSN.SELECTED_INDEXES, container);
	}
	
	public IListObject getEntries(){
		return dsn.getOrAddListObject(DSN.ENTRIES, container);
	}
	
	public boolean isSelected(){
		return getSelectedIndexes().getAllDataObjects().size() > INTEGER._0;
	}
	
	public void selectAll(){
		
		final int _size = getEntries().size();
		final IListObject _indexes = getSelectedIndexes();
		_indexes.reset();
		
		for(int _index = INTEGER._0; _index < _size; _index++){
			_indexes.addSimpleObject().setIntValue(_index);
		}
		
	}
	
	public void unSelectAll(){

		final IListObject _indexes = getSelectedIndexes();
		_indexes.reset();
	}
	
	public void clear(){
		getEntries().reset();
	}
}
