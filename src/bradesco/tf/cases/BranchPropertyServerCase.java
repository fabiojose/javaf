package bradesco.tf.cases;

import java.text.MessageFormat;

import bradesco.tf.TFConstants.NET;

import com.javaf.pattern.ICase;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class BranchPropertyServerCase implements ICase<String> {
	
	private ICase<String> code = new BranchPropertyCodeCase();

	public String execute() {
		String _result = NET.BRANCH_SERVER_PATTERN;
		
		_result = MessageFormat.format(_result, code.execute());
		
		return _result;
	}

}
