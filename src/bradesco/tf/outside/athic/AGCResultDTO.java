package bradesco.tf.outside.athic;

import com.javaf.Constants.STRING;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public class AGCResultDTO {

	private String icor;
	private String identificador;
	private String nome;
	
	public String getIcor() {
		return icor;
	}
	public void setIcor(String icor) {
		this.icor = icor;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String toString(){
		return icor + STRING.SPACE2 + identificador + STRING.SPACE2 + nome;
	}
}
