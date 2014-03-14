package bradesco.tf.outside.athic;

import com.javaf.Constants.STRING;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public abstract class ATHICResult {

	private int code;
	private String identificador;

	public int getCode() {
		return code;
	}

	void setCode(int code) {
		this.code = code;
	}

	public String getIdentificador() {
		return identificador;
	}

	void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	public String toString(){
		return STRING.PARENTESES_ABRE + code + STRING.PARENTESES_FECHA + STRING.SPACE1 + identificador;
	}
}
