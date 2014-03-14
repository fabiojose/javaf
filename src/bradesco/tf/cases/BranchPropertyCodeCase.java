package bradesco.tf.cases;

import com.javaf.Constants.INTEGER;
import com.javaf.Constants.STRING;
import com.javaf.pattern.ICase;

import br.com.bradesco.core.serviceImpl.ServiceProvider;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class BranchPropertyCodeCase implements ICase<String> {

	public String execute() {
		String _result = STRING.EMPTY;
		
		String _code = ServiceProvider.getInstance().getEnvironmentManager().getBranchCode();
		if(_code.startsWith(STRING._0)){
			_code = _code.substring(INTEGER._1, INTEGER._5);
		}
		_result = _code;
		
		return _result;
	}

}
