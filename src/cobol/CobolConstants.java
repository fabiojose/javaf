package cobol;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.javaf.Constants;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.STRING;


import cobol.text.BooleanCobolFormat;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class CobolConstants {

	private CobolConstants(){
		
	}
	
	/**
	 * Expõe constantes pertinentes ao cobol book.
	 * @author fabiojm
	 *
	 */
	public static final class DEFAULT {
		
		public static final String PESSOA_FISICA = "F";
		
		public static final String PESSOA_JURIDICA = "J";
		
		/**
		 * Valor padrão para booleano false
		 */
		public static final int BOOLEAN_FALSE = INTEGER._0;
		
		/**
		 * Valor padrão para booleano true
		 */
		public static final int BOOLEAN_TRUE = INTEGER._1;
		
		public static final String BOOLEAN_SIM  = Constants.DEFAULT.STRING_SIM;
		public static final String BOOLEAN_NAO  = Constants.DEFAULT.STRING_NAO;
		
		private static final Map<Object, Boolean> MODIFIABLE_BOOLEAN_MAPPING = new HashMap<Object, Boolean>();
		static{
			MODIFIABLE_BOOLEAN_MAPPING.put(BOOLEAN_FALSE, Boolean.FALSE);
			MODIFIABLE_BOOLEAN_MAPPING.put(STRING.F, Boolean.FALSE);
			MODIFIABLE_BOOLEAN_MAPPING.put(STRING.f, Boolean.FALSE);
			MODIFIABLE_BOOLEAN_MAPPING.put(String.valueOf(BOOLEAN_FALSE), Boolean.FALSE);
			MODIFIABLE_BOOLEAN_MAPPING.put(BOOLEAN_NAO, Boolean.FALSE);
			
			MODIFIABLE_BOOLEAN_MAPPING.put(BOOLEAN_TRUE, Boolean.TRUE);
			MODIFIABLE_BOOLEAN_MAPPING.put(STRING.V, Boolean.TRUE);
			MODIFIABLE_BOOLEAN_MAPPING.put(STRING.v, Boolean.TRUE);
			MODIFIABLE_BOOLEAN_MAPPING.put(STRING.T, Boolean.TRUE);
			MODIFIABLE_BOOLEAN_MAPPING.put(STRING.t, Boolean.TRUE);
			MODIFIABLE_BOOLEAN_MAPPING.put(String.valueOf(BOOLEAN_TRUE), Boolean.TRUE);
			MODIFIABLE_BOOLEAN_MAPPING.put(BOOLEAN_SIM, Boolean.TRUE);
		};
		public static final Map<Object, Boolean> BOOLEAN_MAPPING = Collections.unmodifiableMap(MODIFIABLE_BOOLEAN_MAPPING);
		
		/**
		 * Valor decimal no formato Cobol Book
		 */
		public static final DecimalFormat COBOL_DECIMAL_FORMAT = new DecimalFormat("###0.######");
		
		/**
		 * Valor boolean no formato Cobol Book
		 */
		public static final Format COBOL_BOOLEAN_FORMAT = new BooleanCobolFormat();
		
		/**
		 * Data-hora no formato Cobol Book
		 */
		public static final DateFormat COBOL_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		
		/**
		 * Data no formato Cobol Book
		 */
		public static final DateFormat COBOL_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	}
	
	/**
	 * Object-Cobol-Book Mapping.<br/>
	 * Expõe constantes pertinentes ao mapeamento entre objetos e os book cobol.
	 * @author fabiojm - Fábio José de Moraes
	 *
	 */
	public static final class OCBM {
		
		/**
		 * Quando presente no nome do Field trata-se de um campo que não existe no book.
		 */
		public static final String AUSENTE = "$$__AUSENTE__$$";
		
		/**
		 * Container que armazena extrutura de entrada do fluxo.
		 */
		public static final String INPUT = "input";
		
		/**
		 * Container que armazena extrutura de saida do fluxo.
		 */
		public static final String OUTPUT = "output";
	}
}
