package bradesco.tf.cases;

import bradesco.tf.TFConstants.PROPERTIES;

import com.javaf.Constants.STRING;
import com.javaf.pattern.ICase;
import com.javaf.pattern.ICase2;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class BranchPropertyCase2 implements ICase2<String, String> {

	public String execute(final String property) {
		String _result = STRING.EMPTY;

		if(PROPERTIES.CASES.containsKey(property)){
			final ICase<String> _case = PROPERTIES.CASES.get(property);
			if(null!= _case){
				
				_result = _case.execute();
				
			} else {
				throw new IllegalArgumentException("no case brach property found: " + property);
			}
		} else {
			throw new IllegalArgumentException("unknow brach property: " + property);
		}
		
		return _result;
	}

}
