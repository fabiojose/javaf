package bradesco.tf;

import java.util.List;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.service.IArquitecturalActions;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import bradesco.tf.TFConstants.MESSAGE;
import bradesco.tf.dsn.UtilDSN;
import bradesco.tf.layout.UtilLayout;

import com.javaf.Properties;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.REFLECTION;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.UtilString;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.IProperties;
import com.javaf.javase.util.Localization;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public final class ShowMessage {
	private static final ShowMessage INSTANCE = new ShowMessage();
	
	private static final ILogging LOGGING = Logging.loggerOf(ShowMessage.class);
	
	private final ILocalization localization = Localization.getInstance();
	private final UtilString string          = UtilString.getInstance();
	private final IProperties properties     = Properties.getInstance();
	private final TerminalFinanceiro arquitetura = TerminalFinanceiro.getInstance();
	private ShowMessage(){
		
	}
	
	public static final synchronized ShowMessage getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Fragmentar uma string em uma quantidade limitada de palavras por linha conforme o limite do Termina Financeiro:<br/>
	 * - 9 palavras por linha
	 *   
	 * @param mensagem Mensagem para fragmentar
	 * @return Mensagem fragmentada
	 * @see UtilString#split(String, int)
	 */
	private String split(final String message){
		String _result = message;
		
		_result = string.split(message, Integer.parseInt( properties.getProperty(TFConstants.PROPERTIES.UI_MESSAGE_LINE_WORDS_LENGTH, String.valueOf(INTEGER._9)) ));
		
		return _result;
	}
	
	/**
	 * Exibir uma mensagem ao usuário no modelo caixa-de-diálogo.<br/>
	 * Esse método deve ser utilizado nas classes de evento de operação.
	 * 
	 * @param node Data Service Node
	 * @param tipo Tipo da mensagem
	 * @param titulo Título da mensagem
	 * @param botoes Array que configura quais o botão serão exibidos
	 * @param mensagem Mensagem ao usuário
	 * @param fragmentar Indicador na necessidade de fragmentação de palavras por linha
	 * 
	 * @see IArquitecturalActions#OK_MESSAGE
	 * @see IArquitecturalActions#ERROR_MESSAGE
	 * @see IArquitecturalActions#WARNING_MESSAGE
	 * @see IArquitecturalActions#QUESTION_MESSAGE
	 */
	public int message(final int tipo, final String titulo, final String[] botoes, final String mensagem, final boolean fragmentar, final IDataServiceNode node) {
		
		String _mensagem = mensagem;
		String _titulo   = titulo;
		if(string.hasHolder(mensagem) 
				|| string.hasHolder(titulo)){
			
			final UtilDSN _dsn         = UtilDSN.getInstance();
			final OPERATION _operation = _dsn.operationOf(node);
			final UtilLayout _layout   = UtilLayout.getInstance();
			
			_mensagem = _layout.holders(_operation, _mensagem);
			_titulo   = _layout.holders(_operation, _titulo);
		}
		
		if(fragmentar){
			_mensagem = split(_mensagem);
		}
		
		return arquitetura.getProvider().getArquitecturalActions().showMessage(node, tipo, titulo, botoes, _mensagem, 0);
	}
		
	/**
	 * Exibir uma mensagem de erro ao usuário no modelo caixa-de-diálogo com um comando OK.<br/>
	 * Esse método deve ser utilizado nas classes de evento de operação.
	 * 
	 * @param e Exceção
	 * @param detalhes Detalhes adicionais sobre a exceção
	 * @param titulo Identificador do título localizado da operação
	 * @param node Data Service Node
	 */
	public void error(final Exception e, final String detalhes, final Object titulo, final IDataServiceNode node){
		LOGGING.error(REFLECTION.METHOD_SHOW_ERROR_MESSAGE, e);
		
		final Throwable _cause = e.getCause();
		final Object _message  = (null!= _cause ? _cause.getMessage() : e.getMessage()) + (null!= detalhes ? STRING.NEW_LINE + detalhes : STRING.EMPTY);
		
		ok(IArquitecturalActions.ERROR_MESSAGE, titulo, _message, Boolean.TRUE, node);
	}
	
	public void error(final Exception e, final IDataServiceNode node){
		error(e, null, MESSAGE.DEFAULT_TITLE, node);
	}
	
	public void error(final Object titulo, final Object mensagem, final IDataServiceNode node){
		final String _mensagem = localization.localize(mensagem);
		ok(IArquitecturalActions.ERROR_MESSAGE, localization.localize(titulo), _mensagem, Boolean.TRUE, node);
	}
	
	/**
	 * Exibir uma mensagem de erro ao usuário no modelo caixa-de-diálogo com um comando OK.<br/>
	 * Esse método deve ser utilizado nas classes de evento de operação.
	 * 
	 * @param e Exceção
	 * @param operacao Identificador do título localizado da operação
	 * @param node Data Service Node
	 * @param provider Provedor de serviços
	 */
	/*public void error(final Exception e, final int operacao, final IDataServiceNode node, final IEventsServiceProvider provider){
		error(e, null, operacao, node, provider);
	}*/

	/**
	 * Exibir uma mensagem ao usuário no modelo caixa-de-diálogo já contendo o comando OK, forçando a fragmentação padrão da mensagem.<br/>
	 * Esse método deve ser utilizado nas classes de evento de operação.
	 * 
	 * @param node Data Service Node
	 * @param tipo Tipo de mensagem
	 * @param titulo Título da mensagem
	 * @param mensagem Mensagem ao usuário
	 * @param provider Provedor de serviços
	 * 
	 * @see IArquitecturalActions#OK_MESSAGE
	 * @see IArquitecturalActions#ERROR_MESSAGE
	 * @see IArquitecturalActions#WARNING_MESSAGE
	 * @see IArquitecturalActions#QUESTION_MESSAGE
	 * @see #split(String)
	 */
	/*public void ok(final IDataServiceNode node, final int tipo, final String titulo, final String mensagem, final IEventsServiceProvider provider) {
		ok(node, tipo, titulo, mensagem, provider, Boolean.TRUE);
	}*/
	
	/*public void ok(final IDataServiceNode node, final int tipo, final int titulo, final int mensagem, final IEventsServiceProvider provider) {
		
		final String _titulo   = localization.localize(titulo);
		final String _mensagem = localization.localize(mensagem);
		ok(node, tipo, _titulo, _mensagem, provider);
	}
	*/
	/**
	 * Exibir uma mensagem ao usuário no modelo caixa-de-diálogo já contendo o comando OK.<br/>
	 * Esse método deve ser utilizado nas classes de evento de operação.
	 * 
	 * @param node Data Service Node
	 * @param tipo Tipo de mensagem
	 * @param titulo Título da mensagem
	 * @param mensagem Mensagem ao usuário
	 * @param provider Provedor de serviços
	 * @param fragmentar Indicador da necessidade de fragmentação de palavras por linha
	 * 
	 * @see IArquitecturalActions#OK_MESSAGE
	 * @see IArquitecturalActions#ERROR_MESSAGE
	 * @see IArquitecturalActions#WARNING_MESSAGE
	 * @see IArquitecturalActions#QUESTION_MESSAGE
	 */
	public void ok(final int tipo, final Object titulo, final Object mensagem, final boolean fragmentar, final IDataServiceNode node) {
		
		final String[] _botoes = { localization.localize(I18N.OK) };
		final String _titulo   = localization.localize(titulo);
		final String _mensagem = localization.localize(mensagem);
		
		message(tipo, _titulo, _botoes, _mensagem, fragmentar, node);
	}
	
	public void ok(final Object mensagem, final IDataServiceNode node) {
		ok(IArquitecturalActions.OK_MESSAGE, MESSAGE.DEFAULT_TITLE, mensagem, Boolean.TRUE, node);
	}
	
	public void ok(final Object titulo, final Object mensagem, final IDataServiceNode node) {
		ok(IArquitecturalActions.OK_MESSAGE, titulo, mensagem, Boolean.TRUE, node);
	}
	
	public void ok(final IDataServiceNode node, final List<String> mensagem, final IEventsServiceProvider provider){
		final String _message = UtilFormat.getInstance().toString(mensagem, properties.getProperty(TFConstants.PROPERTIES.UI_MESSAGE_LINE_MARKER, STRING.EMPTY));
		
		ok(_message, node);
	}
	
	/**
	 * Exibir uma mensagem ao usuário no modelo caixa-de-diálogo já contendo o comando OK, <u>não</u> forçando a fragmentação padrão da mensagem.<br/>
	 * Esse método deve ser utilizado nas classes de regra de negócio.
	 * 
	 * @param node Data Service Node
	 * @param tipo Tipo de mensagem
	 * @param titulo Título da mensagem
	 * @param mensagem Mensagem ao usuário
	 * @param provider Provedor de serviços
	 * 
	 * @see IArquitecturalActions#OK_MESSAGE
	 * @see IArquitecturalActions#ERROR_MESSAGE
	 * @see IArquitecturalActions#WARNING_MESSAGE
	 * @see IArquitecturalActions#QUESTION_MESSAGE
	 * @see #split(String)
	 */
	/*public void ok(final IDataServiceNode node, final int tipo, final String titulo, final String mensagem, final IBusinessRuleServiceProvider provider) {
		
		ok(node, tipo, titulo, mensagem, provider, Boolean.FALSE);
		
	}*/
	
	/**
	 * Exibir uma mensagem ao usuário no modelo caixa-de-diálogo já contendo o comando OK.<br/>
	 * Esse método deve ser utilizado nas classes de regra de negócio.
	 * 
	 * @param node Data Service Node
	 * @param tipo Tipo de mensagem
	 * @param titulo Título da mensagem
	 * @param mensagem Mensagem ao usuário
	 * @param provider Provedor de serviços
	 * @param fragmentar Indicador da necessidade de fragmentação de palavras por linha
	 * 
	 * @see IArquitecturalActions#OK_MESSAGE
	 * @see IArquitecturalActions#ERROR_MESSAGE
	 * @see IArquitecturalActions#WARNING_MESSAGE
	 * @see IArquitecturalActions#QUESTION_MESSAGE
	 */
	/*public void ok(final IDataServiceNode node, final int tipo, final String titulo, final String mensagem, final IBusinessRuleServiceProvider provider, final boolean fragmentar) {
		
		String[] _botoes = { localization.localize(I18N.OK) };
		message(node, tipo, titulo, _botoes, mensagem, provider, fragmentar);
	}*/

	/**
	 * Exibir uma mensagem ao usuário no modelo caixa-de-diálogo já contendo o comando OK e aplica o estilo padrão <code>IArquitecturalActions.OK_MESSAGE</code>.<br/>
	 * Esse método deve ser utilizado nas classes de evento de operação.
	 * 
	 * @param node Data Service Node
	 * @param titulo Título da mensagem
	 * @param mensagem Mensagem ao usuário
	 * @param provider Provedor de serviços
	 * @see #ok(IDataServiceNode, int, String, String, IEventsServiceProvider)
	 */
	/*public void ok(final IDataServiceNode node, final int titulo, final String mensagem, final IEventsServiceProvider provider) {

		final String _titulo = localization.localize(titulo);
		ok(node, IArquitecturalActions.OK_MESSAGE, _titulo, mensagem, provider);

	}*/
	
	/*public void ok(final IDataServiceNode node, final IEventsServiceProvider provider, final int...mensagem) {
		final String _mensagem = localization.localize(mensagem);
		
		ok(node, MESSAGE.DEFAULT_TITLE, _mensagem, provider);
	}*/
	
/*	public void ok(final IDataServiceNode node, final String mensagem, final IEventsServiceProvider provider) {
		ok(node, MESSAGE.DEFAULT_TITLE, mensagem, provider);
	}*/
	
	/*public void ok(final IDataServiceNode node, final int titulo, final int mensagem, final IEventsServiceProvider provider) {

		final String _titulo   = localization.localize(titulo);
		final String _mensagem = localization.localize(mensagem);
		ok(node, IArquitecturalActions.OK_MESSAGE, _titulo, _mensagem, provider);

	}*/

	/**
	 * Exibir uma mensagem ao usuário no modelo caixa-de-diálogo já contendo o comando OK e aplica o estilo padrão <code>IArquitecturalActions.ERROR_MESSAGE</code>.<br/>
	 * Esse método deve ser utilizado nas classes de evento de operação.
	 * 
	 * @param e Exceção
	 * @param operacao Identificador do título localizado da operação
	 * @param node Data Service Node
	 * @param provider Provedor de serviços
	 */
	/*public void ok(final Exception e, final int operacao, final IDataServiceNode node, final IEventsServiceProvider provider) {
		Throwable _cause = e.getCause();
		String[] _botoes = { localization.localize(I18N.OK) };

		message(node, IArquitecturalActions.ERROR_MESSAGE, localization.localize(operacao), _botoes, (null != _cause ? _cause.getMessage() : e.getMessage()), provider);
	}*/
	
	/*public void ok(final Exception e, final IDataServiceNode node, final IEventsServiceProvider provider) {
		ok(e, MESSAGE.DEFAULT_TITLE, node, provider);
	}*/
	
	/**
	 * Exibir mensagem padrão para fechamento de layout. O modelo é caixa-de-diálogo já contendo os comandos Sim e Não.
	 * 
	 * @param node Data Service Node
	 * @return Índice do comando acionado pelo usuário ou <code>-1</code> se ESCAPE for teclado
	 */
	public int escape(final IDataServiceNode node) {
		return escape(I18N.CONFIRMA_CANCELAR, I18N.CANCELAR, node);
	}

	/**
	 * Exibir mensagem personalizada para fechamento de layout. O modelo é caixa-de-diálogo já contendo os comandos Sim e Não.
	 * 
	 * @param node Data Service Node
	 * @param mensagem Mensagem para o usuário
	 * @param titulo Título da mensagem
	 * @return Índice do comando acionado pelo usuário ou <code>-1</code> se ESCAPE for teclado
	 * @see MESSAGE
	 */
	public int escape(final Object mensagem, final Object titulo, final IDataServiceNode node) {
		return yesnot(mensagem, titulo, node);
	}
	
	/**
	 * Exibir mensagem interrogativa onde a resposta possa ser Sim ou Não.
	 * 
	 * @param node Data Service Node
	 * @param mensagem Mensagem para o usuário
	 * @param titulo Título da mensagem
	 * @return Índice do comando acionado pelo usuário ou <code>-1</code> se ESCAPE for teclado
	 */
	public int yesnot(final Object mensagem, final Object titulo, final IDataServiceNode node){
		final String _mensagem = localization.localize(mensagem);
		final String _titulo   = localization.localize(titulo);
		
		return arquitetura.getProvider().getArquitecturalActions().showMessage(node, IArquitecturalActions.QUESTION_MESSAGE, _titulo, _mensagem);
	}
	
	/**
	 * Exibir mensagem interrogativa onde a resposta possa ser Sim ou Não.
	 * 
	 * @param node Data Service Node
	 * @param mensagem Mensagem para o usuário
	 * @return Índice do comando acionado pelo usuário ou <code>-1</code> se ESCAPE for teclado
	 */
	public int yesnot(final Object mensagem, final IDataServiceNode node){
		final String _mensagem = localization.localize(mensagem);
		final String _titulo   = localization.localize(MESSAGE.DEFAULT_TITLE);
		
		return arquitetura.getProvider().getArquitecturalActions().showMessage(node, IArquitecturalActions.QUESTION_MESSAGE, _titulo, _mensagem);
	}
	
	/**
	 * Exibir mensagem interrogativa onde a resposta possa ser Sim ou Não.
	 * 
	 * @param node Data Service Node
	 * @param provider Provedor de Serviços
	 * @param mensagem Mensagem para o usuário
	 * @param titulo Título da mensagem
	 * @return Índice do comando acionado pelo usuário ou <code>-1</code> se ESCAPE for teclado
	 */
	/*public int yesnot(final IDataServiceNode node, final IEventsServiceProvider provider, final String mensagem, final String titulo){
		return yesnot(node, mensagem, titulo);
	}*/
	
	/**
	 * Exibir mensagem interrogativa onde a resposta possa ser Sim ou Não.
	 * 
	 * @param node Data Service Node
	 * @param provider Provedor de Serviços
	 * @param mensagem Identificador da mensagem para o usuário
	 * @param titulo Identificador do título da mensagem
	 * @return Índice do comando acionado pelo usuário ou <code>-1</code> se ESCAPE for teclado
	 * @see MESSAGE
	 */
	/*public int yesnot(final IDataServiceNode node, final IEventsServiceProvider provider, final Object mensagem, final Object titulo){
		return yesnot(node, provider, localization.localize(mensagem), localization.localize(titulo));
	}*/
	
	/**
	 * Exibir mensagem interrogativa onde a resposta possa ser Sim ou Não.
	 * 
	 * @param node Data Service Node
	 * @param provider Provedor de Serviços
	 * @param mensagem Mensagem para o usuário
	 * @param titulo Título da mensagem
	 * @return Índice do comando acionado pelo usuário ou <code>-1</code> se ESCAPE for teclado
	 * @see MESSAGE
	 */
	/*public int yesnot(final IDataServiceNode node, final IEventsServiceProvider provider, final String mensagem){
		return provider.getArquitecturalActions().showMessage(node, IArquitecturalActions.QUESTION_MESSAGE,  localization.localize(MESSAGE.DEFAULT_TITLE), mensagem);
	}*/
	
	/**
	 * Exibir mensagem interrogativa onde a resposta possa ser Sim ou Não.
	 * 
	 * @param node Data Service Node
	 * @param provider Provedor de Serviços
	 * @param mensagem Identificador da mensagem para o usuário
	 * @return Índice do comando acionado pelo usuário ou <code>-1</code> se ESCAPE for teclado
	 * @see MESSAGE
	 */
	/*public int yesnot(final IDataServiceNode node, final IEventsServiceProvider provider, final int mensagem){
		return yesnot(node, provider, mensagem, MESSAGE.DEFAULT_TITLE);
	}*/
	
}
