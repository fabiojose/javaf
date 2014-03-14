package bradesco.tf.dsn;

import java.util.List;

import com.javaf.Constants.I18N;
import com.javaf.javase.util.Localization;

import br.com.bradesco.core.aq.dataservice.IDataObject;
import br.com.bradesco.core.aq.dataservice.IListObject;
import br.com.bradesco.core.aq.dataservice.ISimpleObject;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public final class DataServiceNodeListIntegerPersistence implements Serializer {
	
	private UtilDSN dsn = UtilDSN.getInstance(); 
	
	private IListObject list;
	private List<? super Integer> instancia;
	
	public DataServiceNodeListIntegerPersistence(final IListObject list, final List<? super Integer> instancia){
		this.list = list;
		this.instancia = instancia;
	}
	
	public void read() {
		
		instancia.clear();

		for(IDataObject _object : list.getAllDataObjects()){
			if(_object instanceof ISimpleObject){
				
				instancia.add(dsn.getValue((ISimpleObject)_object, Integer.class));
			} else {
				throw new IllegalStateException(Localization.getInstance().localize(I18N.COLECAO_NAO_ARMAZENA, ISimpleObject.class, _object.getClass()));
			}
		}
	}

	public void write() {
		
		list.reset();
		
		for(Object _integer : instancia){
			
			list.addSimpleObject().setIntValue((Integer)_integer);
		}
	}

}

