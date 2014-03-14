package bradesco.tf.outside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import bradesco.tf.TerminalFinanceiro;

import com.javaf.Constants.I18N;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.reflect.InvokeException;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;

/**
 * Invoca uma url qualquer.
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class URLCalling {
	
	private final ILogging logging;
	private final ILocalization localization;
	public URLCalling(){
		logging      = Logging.loggerOf(URLCalling.class);
		localization = Localization.getInstance();
	}

	/**
	 * Invoca a url e retorna o valor.
	 * 
	 * @param url URL que será invocada
	 * @return Resultado do <code>readLine()</code>
	 * @throws InvokingException Lançada se algum problema ocorrer com a formatacao da URL ou na sua abertura
	 */
	public StringBuilder invoke(final String url) throws InvokeException {
		
		StringBuilder _result = new StringBuilder();
		try{
			final URL _url = new URL(url);
			logging.debug(localization.localize(I18N.INVOCANDO_URL, _url));
			
			final URLConnection _connection = _url.openConnection();
			final BufferedReader _reader = new BufferedReader(new InputStreamReader(_connection.getInputStream()));
			
			try{
				logging.debug(localization.localize(I18N.INICIANDO_LEITURA_DAS_LINHAS));
				String _line = STRING.EMPTY;
				while((_line = _reader.readLine()) != null){
					_result.append(_line).append(STRING.NEW_LINE);
					
					logging.debug(STRING.TAB + _line);
				}
				logging.debug(localization.localize(I18N.FINALIZANDO_LEITURA_DAS_LINHAS));
				
			}finally{
				_reader.close();
			}
		}catch(MalformedURLException _e){
			logging.error(_e.getMessage(), _e);
			
			if(TerminalFinanceiro.isDeployment()){
				throw new InvokeException(_e.getMessage(), _e);
			}
		}catch(IOException _e){
			logging.error(_e.getMessage(), _e);
			
			if(TerminalFinanceiro.isDeployment()){
				throw new InvokeException(_e.getMessage(), _e);
			}
		}
		
		return _result;
	}
}
