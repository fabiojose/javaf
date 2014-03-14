package bradesco.tf.cases;

import com.javaf.Constants.STRING;
import com.javaf.pattern.ICase;

import br.com.bradesco.core.serviceImpl.ServiceProvider;
import bradesco.tf.TerminalFinanceiro;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class BranchPropertyUserCase implements ICase<String> {

	public String execute() {
		String _result = STRING.EMPTY;
		
		_result = TerminalFinanceiro.getInstance().getUserName(ServiceProvider.getInstance().getEventsServiceProvider());
		
		return _result;
	}

}
