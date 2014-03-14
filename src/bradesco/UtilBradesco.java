package bradesco;

import com.javaf.Bagman;
import com.javaf.Utility;
import com.javaf.Constants.INTEGER;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilBradesco implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilBradesco();
		}
		
	};

	private UtilBradesco(){
		
	}
	
	public static final synchronized UtilBradesco getInstance(){
		return Bagman.utilOf(UtilBradesco.class, FACTORY);
	}
	
	/**
	 * Traduzir o nome de usuário com prefixos alfanuméricos para somente numéricos.
	 * 
	 * @param userName Nome de usuário que contenha tanto letras quanto números
	 * @return Nome de usuário com a letra traduzida para o número correto
	 * @see BradescoConstants#GSEG
	 */
	public String doUsername(final String name) {
		String _first = name.substring(INTEGER._0, INTEGER._1);

		String _result = name;
		try {
			Integer.parseInt(_first);
		} catch (NumberFormatException _e) {
			_result = String.valueOf(BradescoConstants.GSEG.get(_first.toUpperCase())) + name.substring(INTEGER._1);
		}

		return _result;
	}
	
	/*public String toString(final PeriodoDTO periodo){
    	String _result = STRING.EMPTY;
    	
    	if(null!= periodo){
    		
    		_result = periodo.getVigencia().getString();
    		if(periodo.getVigencia().isDeterminada()){
    			_result += STRING.SPACE1 + STRING.PARENTESES_ABRE + toString(periodo.getInicio(), DEFAULT.ABNT_DATE_FORMAT) + STRING.HIFEM_SPACE + toString(periodo.getFim(), DEFAULT.ABNT_DATE_FORMAT) + STRING.SPACE1 + STRING.PARENTESES_FECHA;
    		} 
    	}
    	
    	return _result;
    }
    
    public String toString(final ContaDTO conta){
    	final StringBuilder _result = new StringBuilder(STRING.EMPTY);
    	
    	if(null!= conta){
    		_result.append( toString(conta.getNumero()) );
    		_result.append(STRING.HIFEM);
    		_result.append( toString(conta.getDigito()) );
    	}
    	
    	return _result.toString();
    }*/
}
