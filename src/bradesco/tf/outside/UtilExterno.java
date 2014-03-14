package bradesco.tf.outside;

import java.util.Date;

import bradesco.BradescoConstants;
import bradesco.tf.TFConstants;
import bradesco.tf.TFConstants.ATHIC;

import com.javaf.Application;
import com.javaf.Constants;
import com.javaf.Constants.APPLICATION;
import com.javaf.Constants.FORMATTERS;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.STRING;
import com.javaf.brazil.Pessoa;
import com.javaf.brazil.PessoaType;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.text.UtilFormat;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilExterno {
	private static final UtilExterno INSTANCE = new UtilExterno();
	
	private final ILogging logging  = Logging.loggerOf(getClass());
	private final UtilFormat format = UtilFormat.getInstance();
	private UtilExterno(){
		
	}
	
	public static final synchronized UtilExterno getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Elaborar um novo identificador para inclusão de imagens no Projeto CIMG.
	 * @return Novo identificador
	 */
	public String newCimgID(){
		
		final String _empresa = format.toString(BradescoConstants.BANCO_BRADESCO, FORMATTERS.DECIMAL_4DIGITOS);
		logging.debug(_empresa);
		
		final String _sigla   = Application.getInstance().valueOf(String.class, APPLICATION.CODE_PROPERTY);
		logging.debug(_sigla);
		
		final String _data    = format.toString(new Date(), FORMATTERS.DATE_NO_CHAR_SEP);
		logging.debug(_data);
		
		final int _random = (int)(Math.random() * INTEGER._999999) + INTEGER._1;
		final String _aleatorio = format.toString(_random, FORMATTERS.DECIMAL_5DIGITOS);
		logging.debug( _aleatorio);
		
		final String _result = _empresa
								+ _sigla
								+ _data
								+ _aleatorio;
		
		logging.debug(_result);
		
		return _result;
	}
	
	public String newATHICId(final Pessoa cliente){
		String _result = STRING.EMPTY;
		
		final String _campo0001 = String.valueOf(PessoaType.FISICA.equals(cliente.getDocumento().getTipo()) ? ATHIC.PESSOA_FISICA : ATHIC.PESSOA_JURIDICA);
		final String _campo0216 = Constants.DEFAULT.DOCUMENTO_CNPJ15.format(cliente.getDocumento().getNumero());
		final String _campo1727 = TFConstants.DEFAULT.BRADESCO_CLUB.format(cliente.getClub());
		final String _campo2848 = FORMATTERS.DATE_NO_CHAR_SEP.format(new Date());
		
		_result = _campo0001 + _campo0216 + _campo1727 + _campo2848;
		
		return _result;
	}

}
