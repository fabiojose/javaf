package bradesco.tf.dsn;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;

import com.javaf.pattern.Bridge;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public class FWODSNCopyBridge implements Bridge<String, IDataServiceNode> {

	public IDataServiceNode cross(final String fluxo) {

		return null;
	}

	

}
