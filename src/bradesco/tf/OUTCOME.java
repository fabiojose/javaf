package bradesco.tf;

import bradesco.tf.TFConstants.EVENT;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class OUTCOME {
	
	public static final OUTCOME VOLTAR    = new OUTCOME(EVENT.VOLTAR);
	public static final OUTCOME AVANCAR   = new OUTCOME(EVENT.AVANCAR);
	public static final OUTCOME CONFIRMAR = new OUTCOME(EVENT.CONFIRMAR);
	public static final OUTCOME PESQUISAR = new OUTCOME(EVENT.PESQUISAR);
	public static final OUTCOME DETALHAR  = new OUTCOME(EVENT.DETALHAR);
	
	private String value;
	private OUTCOME(final String value){
		this.value = value;
	}
	
	public String toString(){
		return value;
	}
}
