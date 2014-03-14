package bradesco;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public final class BradescoConstants {

	private BradescoConstants(){
		
	}
	
	/**
	 * Mapa onde as letras utilizadas com prefixo em nomes de usu�rio s�o mapeadas � d�gitos inteiros.
	 */
	private static final Map<String, Integer> MODIFIABLE_GSEG = new HashMap<String, Integer>();
	static {
		MODIFIABLE_GSEG.put("A", 1);
		MODIFIABLE_GSEG.put("B", 2);
		MODIFIABLE_GSEG.put("C", 3);
		MODIFIABLE_GSEG.put("D", 4);
		MODIFIABLE_GSEG.put("E", 5);
		MODIFIABLE_GSEG.put("F", 6);
		MODIFIABLE_GSEG.put("G", 7);
		MODIFIABLE_GSEG.put("H", 8);
		MODIFIABLE_GSEG.put("I", 9);
	};
	
	public static final Map<String, Integer> GSEG = Collections.unmodifiableMap(MODIFIABLE_GSEG);
	
	/**
	 * C�digo do Banco Bradesco
	 */
	public static final int BANCO_BRADESCO = 237;
	
	/**
	 * C�digo de Pessoa Jur�dica do Banco Bradesco.
	 * 
	 */
	public static final String BANCO_BRADESCO_PJ = "2269651";

}
