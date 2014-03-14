package bradesco.tf.outside;

import br.com.bradesco.core.aq.browser.external.BradescoExternalBrowser;
import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import bradesco.tf.TerminalFinanceiro;
import bradesco.tf.TFConstants.PROPERTIES;

import com.javaf.Properties;
import com.javaf.Constants.I18N;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.reflect.InvokeException;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.IProperties;
import com.javaf.javase.util.Localization;
import com.javaf.model.Ambiente;

/**
 * Implementa chamadas ao browser para exibição de páginas.
 * 
 * @author fabiojm - Fábio José de Moraes
 */
@SuppressWarnings("deprecation")
public final class BrowserCalling {

	private final TerminalFinanceiro arquitetura = TerminalFinanceiro.getInstance();
	private final IProperties properties         = Properties.getInstance();
	
	/**
	 * Invocar o navegador abrindo uma URI dentro do servidor <code>{@link PROPERTIES#AWB_IP_ID}</code> AWB configurado no arquivo <code>..\EAGE\CLIENT\{AMBIENTE}\TerminalFinanceiro\config\BradescoBrowserURL.properties</code><br/>
	 * Por padrão o usuário autenticado será o mesmo na tentativa de acesso e não serão exibidos os menus AWB.
	 * 
	 * @param provider Provedor de serviços
	 * @param node Provedor de dados
	 * @param uri Contexto que será invocado no servidor de aplicações web AWB
	 * @throws InvokingException Lançada caso uma das propriedades necessárias não esteja configurada
	 * @return 
	 */
	public Object invoke(final IEventsServiceProvider provider, final IDataServiceNode node, final String uri) throws InvokeException {
		
		final String _id = properties.getProperty(PROPERTIES.AWB_IP_ID);
		if(null!= _id){
			
			invoke(provider, node, uri, _id);
		} else {
			throw new InvokeException(Localization.getInstance().localize(I18N.PROPRIEDADE_NAO_CONFIGURADA_PROJETO, PROPERTIES.AWB_IP_ID));
		}
		
		
		return null;
	}
	
	/**
	 * Invocar o navegador abrindo uma URI dentro do servidor <code>{@link PROPERTIES#AWB_IP_ID}</code> AWB configurado no arquivo <code>..\EAGE\CLIENT\{AMBIENTE}\TerminalFinanceiro\config\BradescoBrowserURL.properties</code><br/>
	 * Por padrão o usuário autenticado será o mesmo na tentativa de acesso e não serão exibidos os menus AWB.
	 * 
	 * @param provider Provedor de serviços
	 * @param node Provedor de dados
	 * @param uri Contexto que será invocado no servidor de aplicações web AWB
	 * @param idip Identificador da chave em <code>BradescoBrowserURL.properties</code> para o ip onde está implantada a uri
	 * @throws InvokingException Lançada caso uma das propriedades necessárias não esteja configurada
	 * @return 
	 */
	public Object invoke(final IEventsServiceProvider provider, final IDataServiceNode node, final String uri, final String idip) throws InvokeException {

		//provider.getArquitecturalActions().openBrowser(node, idip, uri, Boolean.TRUE, Boolean.TRUE);
		
		final Ambiente _ambiente = arquitetura.getAmbiente();
		final StringBuilder _url = new StringBuilder(STRING.EMPTY);
		_url.append( STRING.HTTP_PREFIX );
		_url.append( UtilFormat.getInstance().toString( _ambiente.getProperty(idip)) );
		_url.append( STRING.BARRA );
		_url.append( uri );
		
		final BradescoExternalBrowser _browser = BradescoExternalBrowser.getInstance(_url.toString(), null, Boolean.TRUE, Boolean.TRUE);
		_browser.navigate(_url.toString());
		
		return null;
	}
}
