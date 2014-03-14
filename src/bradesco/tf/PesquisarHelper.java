package bradesco.tf;

import br.com.bradesco.core.aq.dataservice.IContainerObject;
import br.com.bradesco.core.aq.dataservice.IListObject;
import bradesco.tf.TFConstants.DSN;
import bradesco.tf.dsn.UtilDSN;

import com.javaf.Constants.INTEGER;

/**
 * Implementa facilidades para manipulação de resultados nas funcionalidades Pesquisar.
 * @author fabiojm
 *
 */
@SuppressWarnings("deprecation")
public final class PesquisarHelper {
	private UtilDSN dsn = UtilDSN.getInstance();
	
	private IContainerObject pesquisar;
	public PesquisarHelper(final IContainerObject pesquisar){
		this.pesquisar = pesquisar;
	}
	
	public IContainerObject getPesquisar(){
		return pesquisar;
	}
	
	public IContainerObject getResultado(){
		return dsn.getOrAddContainerObject(DSN.RESULTADO, getPesquisar());
	}
	
	public IListObject getEntries(){
		return dsn.getOrAddListObject(DSN.ENTRIES, getResultado());
	}
	
	public IListObject getSelectedIndexes(){
		return dsn.getOrAddListObject(DSN.SELECTED_INDEXES, getResultado());
	}
	
	public boolean isAnySelectedIndex(){
		return getSelectedIndexes().getAllDataObjects().size() > INTEGER._0;
	}
}
