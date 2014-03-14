package bradesco.tf.outside.athic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.javaf.brazil.Pessoa;
import com.javaf.javase.lang.UtilString;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.text.ParsingException;
import com.javaf.javase.util.UtilCollection;
import com.javaf.model.Ambiente;

import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import bradesco.tf.TFConstants;
import bradesco.tf.TerminalFinanceiro;
import bradesco.tf.UnsuccessfulException;
import bradesco.tf.TFConstants.ATHIC;
import bradesco.tf.outside.UtilExterno;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class AGCCalling extends ATHICCalling<AGCResult> {
	
	private TerminalFinanceiro arquitetura = TerminalFinanceiro.getInstance();
	private UtilString string             = UtilString.getInstance();
	private UtilCollection collection     = UtilCollection.getInstance();
	private UtilExterno project           = UtilExterno.getInstance();
	
	@Override
	public AGCResult invoke(final IEventsServiceProvider provider, final ATHICInput input) throws UnsuccessfulException {
		final AGCResult _result = new AGCResult();
		final ILogging _logging = Logging.loggerOf(getClass());
		final Ambiente _ambiente = arquitetura.getAmbiente();
		
		input.validate();
		final Pessoa _cliente = (Pessoa)input.getParameters().get(ATHIC.CLIENTE);		
		String _urlcsv           = _ambiente.getProperty(TFConstants.AMBIENTE.ATHIC_URLCSV);
		
		try{
			
			final String _product = _ambiente.getProperty(TFConstants.AMBIENTE.ATHIC_AGENCIA_PRODUCT);
			_logging.debug("ATHIC SCANNER - AGC - PRODUCT: " + _product);
			
			final String _id = project.newATHICId(_cliente);
			_logging.debug("ATHIC SCANNER - AGC - ID GERADO: " + _id);
			
			final Map<String, Object> _parameters = collection.clone(input.getParameters());
			_parameters.put(ATHIC.PRODUCT,  _product);
			_parameters.put(ATHIC.ID,       _id);
			
			_logging.debug("ATHIC SCANNER - AGC - URL CSV: " + _urlcsv);
			_urlcsv = string.named(_urlcsv, _parameters);
			_logging.debug("ATHIC SCANNER - AGC - URL CSV (PARAMÊTROS APLICADOS): " + _urlcsv);
			
			final URL _url   = new URL(_urlcsv);
			final File _file = new File(_url.getFile());
			
			_parameters.put(ATHIC.CSV_PATH, _file.getAbsoluteFile());
			
			int _returned = super.invoke(provider, _parameters);
			
			if(_file.exists()){
				
				_result.setCode(_returned);
				_result.setIdentificador(_id);
				_result.setCsv(_url);
				
				try{
					// processar dados do arquivo
					final AGCResultParser _parser = new AGCResultParser();
					_result.setData(_parser.parse(_url));
					
				}catch(IOException _e){
					_logging.error(_e.getMessage(), _e);
					throw new UnsuccessfulException("Problemas na leitura do arquivo: " + _url.toString());
					
				}catch(ParsingException _e){
					_logging.error(_e.getMessage(), _e);
					throw new UnsuccessfulException("Problemas no processamento do arquivo: " + _url.toString());
				}
				
			} else {
				_logging.debug("ATHIC SCANNER - AGC - ARQUIVO DE RETORNO NÃO ENCONTRADO: " + _url);
				throw new UnsuccessfulException("Arquivo de retorno não encontrado: " + _url.toString());
			}

		}catch(MalformedURLException _e){
			throw new IllegalArgumentException(_e.getMessage(), _e);
		}
		
		return _result;
	}
}
