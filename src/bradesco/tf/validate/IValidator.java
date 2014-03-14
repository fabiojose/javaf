package bradesco.tf.validate;

import java.util.List;

/**
 * Definição da interface para validadores de qualquer tipo de regra.
 * @author fabiojm - Fábio José de Moraes
 *
 */
public interface IValidator {

	/**
	 * Valida a regra implementada.
	 * @param messages buffer para armazenar mensagens sobre a validação
	 * @return <code>true</code> se a regra for válida
	 */
	boolean validate(final List<String> messages);
	
	/**
	 * Verifica se validador está ou não ligado.
	 * @return
	 */
	boolean isTurnedOn();
	
	void setTurnedOn(boolean turnedOn);
	
	/**
	 * Primeiro value place inválido.
	 * @return Identificador do value place
	 */
	String getFirst();
	void setFirst(String first);
}
