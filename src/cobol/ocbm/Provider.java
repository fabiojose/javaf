package cobol.ocbm;


/**
 * Define os m�todos importantes � um provedor de OCBM
 * @author fabiojm - F�bio Jos� de Moraes
 * 
 */
public interface Provider<T> {

	/**
	 * Escrever a inst�ncia no meio
	 * @param t
	 * @return <code>true</code> caso existam mais dados que ser�o escritos em uma nova chamada ao write
	 * @throws WritingException Lan�ada sempre que houverem problemas para escrita no meio
	 * @throws NullPointerException Lan�ada quando {@code t} for {@code null}
	 */
	boolean write(T t) throws WritingException, NullPointerException;
	
	/**
	 * Ler a inst�ncia do meio
	 * @param t Inst�ncia base para para grava��o dos dados lidos
	 * @throws ReadingException Lan�ada sempre que houverem problemas na leitura do meio
	 * @return Inst�ncia passada como par�metro j� com os atributos populados.
	 */
	T read(T t) throws ReadingException;
}
