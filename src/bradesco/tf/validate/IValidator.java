package bradesco.tf.validate;

import java.util.List;

/**
 * Defini��o da interface para validadores de qualquer tipo de regra.
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public interface IValidator {

	/**
	 * Valida a regra implementada.
	 * @param messages buffer para armazenar mensagens sobre a valida��o
	 * @return <code>true</code> se a regra for v�lida
	 */
	boolean validate(final List<String> messages);
	
	/**
	 * Verifica se validador est� ou n�o ligado.
	 * @return
	 */
	boolean isTurnedOn();
	
	void setTurnedOn(boolean turnedOn);
	
	/**
	 * Primeiro value place inv�lido.
	 * @return Identificador do value place
	 */
	String getFirst();
	void setFirst(String first);
}
