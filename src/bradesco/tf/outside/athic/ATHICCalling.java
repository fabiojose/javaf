package bradesco.tf.outside.athic;

import java.util.Map;

import com.javaf.Constants;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.javase.lang.UtilString;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.model.Ambiente;

import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import bradesco.tf.TFConstants;
import bradesco.tf.TerminalFinanceiro;
import bradesco.tf.UnsuccessfulException;
import bradesco.tf.outside.SOCommandCalling;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T>
 */
public abstract class ATHICCalling<T extends ATHICResult> extends SOCommandCalling {

	private final TerminalFinanceiro arquitetura = TerminalFinanceiro.getInstance();
	private final UtilString string              = UtilString.getInstance();
	
	public int invoke(final IEventsServiceProvider provider, final Map<String, Object> parameters) throws UnsuccessfulException {
		int _result = INTEGER._1NEG;
		final ILogging _logging = Logging.loggerOf(getClass());
		
		_logging.debug("ATHIC SCANNER - PARAMETERS: " + parameters);
	
		final Ambiente _ambiente = arquitetura.getAmbiente();
		String _command              = _ambiente.getProperty(TFConstants.AMBIENTE.ATHIC_SCANNER);
		
		_logging.debug("ATHIC SCANNER - COMMAND: " + _command);
		_command = string.named(_command, parameters);
		_logging.debug("ATHIC SCANNER - COMMAND (PARÂMETROS APLICADOS): " + _command);
		
		_logging.debug("ATHIC SCANNER - EXECUTAR APLICATIVO . . .");
		_result = invoke(_command, provider);
		_logging.debug("ATHIC SCANNER - APLICATIVO FINALIZADO COM RESULTADO: " + _result + ".");
		
		if(Constants.DEFAULT.RESULT_SUCCESS != _result){
			throw new UnsuccessfulException(arquitetura.getApplText(I18N.APLICATIVO_ATHIC_NAO_SUCESSO, provider));
		}
		
		return _result;
	}
	
	public abstract T invoke(final IEventsServiceProvider provider, final ATHICInput input) throws UnsuccessfulException;
	
}
