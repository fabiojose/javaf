package bradesco.tf;

import java.util.Date;

import br.com.bradesco.core.aq.exceptions.RollbackException;
import br.com.bradesco.core.aq.persistence.BradescoPersistenceException;
import bradesco.tf.TFConstants.MESSAGE;

import com.javaf.Constants.REFLECTION;
import com.javaf.Constants.STRING;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public final class SendMessage {
	private static final SendMessage INSTANCE = new SendMessage();
	
	private static final ILogging LOGGING = Logging.loggerOf(SendMessage.class);
	
	private final ILocalization localization     = Localization.getInstance();
	private final TerminalFinanceiro arquitetura = TerminalFinanceiro.getInstance();
	private SendMessage(){
		
	}
	
	public static final synchronized SendMessage getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Limpar cole��o de mensagens.
	 * 
	 */
	public void clean() {
		arquitetura.getProvider().getArquitecturalActions().getMessageManager().cleanMessageBar();
	}
	
	/**
	 * Enviar uma mensagem de erro (vermelha) ao usu�rio que ser� exibida no rodap� do layout.<br/>
	 * Esse m�todo deve ser utilizado nas classes de evento de opera��o.
	 * 
	 * @param e Exce��o
	 * @param titulo Identificador do t�tulo localizado da opera��o
	 * @param provider Providor de servi�os
	 */
	public void error(final Exception e, final Object titulo) {
		error(e, localization.localize(titulo));
	}
	
	public void error(final Exception e) {
		error(e, MESSAGE.DEFAULT_TITLE);
	}

	/**
	 * Enviar uma mensagem de erro (vermelha) ao usu�rio que ser� exibida no rodap� do layout.<br/>
	 *  
	 * @param e Exce��o
	 */
	public void error(final Exception e, final String titulo) {
		Throwable _cause = e.getCause();

		if (e instanceof BradescoPersistenceException) {
			error(titulo + STRING.SPACE1 + STRING.DOIS_PONTOS + STRING.SPACE1 + (null != _cause ? _cause.getMessage() : e.getMessage()) + STRING.SPACE1 + STRING.PARENTESES_ABRE + UtilFormat.getInstance().toString(new Date()) + STRING.PARENTESES_FECHA);

		} else if (e instanceof RollbackException) {
			error(titulo + STRING.SPACE1 + STRING.DOIS_PONTOS + STRING.SPACE1 + (null != _cause ? _cause.getMessage() : e.getMessage()) + STRING.SPACE1 + STRING.PARENTESES_ABRE + UtilFormat.getInstance().toString(new Date()) + STRING.PARENTESES_FECHA);

		} else {
			error(titulo + STRING.SPACE1 + STRING.DOIS_PONTOS + STRING.SPACE1 + (null != _cause ? _cause.getMessage() : e.getMessage()) + STRING.SPACE1 + STRING.PARENTESES_ABRE + UtilFormat.getInstance().toString(new Date()) + STRING.PARENTESES_FECHA);
		}
		
		LOGGING.error(REFLECTION.METHOD_SEND_ERROR_MESSAGE, e);
	}

	/**
	 * Enviar uma mensagem de erro (vermelha) ao usu�rio que ser� exibida no rodap� do layout.<br/>
	 * 
	 * @param message Mensagem
	 */
	public void error(final Object message) {
		final String _message = localization.localize(message);
		
		arquitetura.getProvider().getArquitecturalActions().getMessageManager().sendErrorMessage(_message);
	}
	
	
	public void message(final Object message){
		message(localization.localize(message));
	}
	
	public void message(final String message){
		arquitetura.getProvider().getArquitecturalActions().getMessageManager().sendMessage(message);
	}
}
