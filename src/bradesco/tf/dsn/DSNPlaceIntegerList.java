package bradesco.tf.dsn;

import java.util.ArrayList;
import java.util.List;

import com.javaf.Constants.I18N;
import com.javaf.javase.text.ParsingException;
import com.javaf.javase.util.Localization;
import com.javaf.model.ValuePlace;

import br.com.bradesco.core.aq.dataservice.IDataObject;
import br.com.bradesco.core.aq.dataservice.IListObject;
import br.com.bradesco.core.aq.dataservice.ISimpleObject;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public class DSNPlaceIntegerList extends ValuePlace<List<Integer>> {

	private IListObject list;
	private List<Integer> bridge;
	public DSNPlaceIntegerList(final String label, final IListObject list){
		setLabel(label);
		this.list = list;
		this.bridge = new ArrayList<Integer>();
	}

	public List<Integer> getValue() {

		bridge.clear();
		for(IDataObject _object : list.getAllDataObjects()){
			if(_object instanceof ISimpleObject){
				bridge.add( ((ISimpleObject)_object).getIntValue() );
				
			} else {
				throw new ParsingException( Localization.getInstance().localize(I18N.COLECAO_NAO_ARMAZENA, ISimpleObject.class, _object.getClass()));
			}
		}
		
		return bridge;
	}

	public void reset() {
		list.reset();
	}

	public String getName(){
		return list.getFullKey();
	}
}
