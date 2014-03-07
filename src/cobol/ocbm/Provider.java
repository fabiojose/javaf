package cobol.ocbm;


/**
 * Define os métodos importantes à um provedor de OCBM
 * @author fabiojm - Fábio José de Moraes
 * 
 */
public interface Provider<T> {

	/**
	 * Escrever a instância no meio
	 * @param t
	 * @return <code>true</code> caso existam mais dados que serão escritos em uma nova chamada ao write
	 * @throws WritingException Lançada sempre que houverem problemas para escrita no meio
	 * @throws NullPointerException Lançada quando {@code t} for {@code null}
	 */
	boolean write(T t) throws WritingException, NullPointerException;
	
	/**
	 * Ler a instância do meio
	 * @param t Instância base para para gravação dos dados lidos
	 * @throws ReadingException Lançada sempre que houverem problemas na leitura do meio
	 * @return Instância passada como parâmetro já com os atributos populados.
	 */
	T read(T t) throws ReadingException;
}
