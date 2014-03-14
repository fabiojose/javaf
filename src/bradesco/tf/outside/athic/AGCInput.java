package bradesco.tf.outside.athic;

import bradesco.tf.TFConstants.ATHIC;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class AGCInput extends ATHICInput {

	@Override
	public void validate() throws IllegalArgumentException {
		
		if(!contains(ATHIC.CLIENTE)){
			throw new IllegalArgumentException("named parameter '" + ATHIC.CLIENTE + "' not found!");
		}
		
		if(!contains(ATHIC.CSV_NAME)){
			throw new IllegalArgumentException("named parameter '" + ATHIC.CSV_NAME + "' not found!");
		}
		
	}
	
}
