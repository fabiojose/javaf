package bradesco.tf.visitor;

import java.util.Properties;

import com.javaf.Constants.INTEGER;
import com.javaf.Constants.STRING;

import br.com.bradesco.core.aq.browser.BradescoBrowserUtils;
import bradesco.tf.TFConstants.PROPERTIES;

/**
 * Processar propriedades ligadas ao endereço IP configurado no client.
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class PropertiesBradescoBrowserURLVisitor extends TFVisitor<Properties, Properties> {

	public Properties visit(final Properties properties) {

		final String _ambiente = System.getProperty(PROPERTIES.AMBIENTE);
		
		//processar propriedades ligadas ao endereço IP configurado no client
		for(int _index = INTEGER._1; /*break;*/ ; _index++){
			final String _key   = STRING.IP + String.valueOf(_index) + STRING.UNDERSCORE + _ambiente;
			final String _value = BradescoBrowserUtils.getBrowserURLValue(_key);
			
			if(null!= _value){
				properties.put(STRING.IP + String.valueOf(_index), _value);
			} else {
				break;
			}
		}
		
		return properties;
	}

}
