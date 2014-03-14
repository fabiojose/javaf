package bradesco.tf.visitor;

import java.util.Properties;
import java.util.Set;

import bradesco.tf.cases.BranchPropertyCase2;

import com.javaf.Constants.REGEX;
import com.javaf.javase.util.regex.UtilRegex;
import com.javaf.pattern.ICase2;

/**
 * Processar propriedades ligadas a arquitetura do TF e agência.
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class PropertiesBranchPlaceholderVisitor extends TFVisitor<Properties, Properties> {

	private final UtilRegex regex               = UtilRegex.getInstance();
	private final ICase2<String, String> branch = new BranchPropertyCase2();
	
	private String branch(final String propertyValue, final Set<String> placesInValue, final Properties properties){
		String _result = propertyValue;
		
		for(String _place : placesInValue){
			final String _property = regex.placeholderOf(_place);
			
			_result = _result.replace(_place, branch.execute(_property));
			
		}
		
		return _result;
	}
	
	public Properties visit(final Properties properties) {

		final Set<Object> _keys = properties.keySet();
		for(Object _key : _keys){
			final String _value = properties.getProperty(_key.toString());
			if(_value.matches(REGEX.BRANCH_PROPERTY_PLACEHOLDER_CONTAINS)){
				final Set<String> _places = regex.valueOf(_value, REGEX.BRANCH_PROPERTY_PLACEHOLDER);
				final String _newValue    = branch(_value, _places, properties);
				
				properties.setProperty(_key.toString(), _newValue);
			}
		}
		
		return properties;
	}

}
