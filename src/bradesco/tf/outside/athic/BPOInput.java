package bradesco.tf.outside.athic;

import bradesco.tf.TFConstants.ATHIC;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class BPOInput extends ATHICInput {

	@Override
	public void validate() throws IllegalArgumentException {
		
		if(!contains(ATHIC.CLIENTE)){
			throw new IllegalArgumentException("named parameter '" + ATHIC.CLIENTE + "' not found!");
		}

		if(!contains(ATHIC.BPO_TYPE)){
			throw new IllegalArgumentException("named parameter '" + ATHIC.BPO_TYPE + "' not found!");
		}
		
	}

}
