package bradesco.tf;

import com.javaf.ObjectPool;
import com.javaf.model.Attribute;

import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IUserSession;
import bradesco.tf.TFConstants.SESSION;

/**
 * Implementa a mediação entre a Arquitetura TF e Classes que não tem acesso direto aos seus recursos.
 * 
 * @author fabiojm - Fábio José de Moraes
 */
public final class LayerMediator {
	private static final LayerMediator INSTANCE = new LayerMediator();
	
	private final TerminalFinanceiro arquitetura;
	private final ObjectPool pool;
	
	private IUserSession userSession;
	
	private LayerMediator(){
		arquitetura = TerminalFinanceiro.getInstance();
		pool        = ObjectPool.newPool();
	}
	
	/**
	 * Obter instância de trabalho.
	 * 
	 * @return Instância singleton para trabalho.
	 */
	public static synchronized LayerMediator getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Registra a sessão que será mediada entre Arquitetura e outras camadas de Classes que não tem acesso direto à arquitetura.
	 * 
	 * @param provider Povedor de serviços para obter a sessão do usuário.
	 */
	public synchronized void register(final IEventsServiceProvider provider){
		registerUserSession(provider.getSessionManager().getUserSession());
		
		pool.put(TFConstants.OBJECT_POOL.EVENTS_PROVIDER, provider);
	}
	
	/**
	 * Registra a sessão que será mediada entre Arquitetura e outras camadas de Classes que não tem acesso direto à arquitetura.
	 * 
	 * @param session Sessão do usuário
	 */
	public synchronized void registerUserSession(final IUserSession session){
		arquitetura.setSessionAttribute(SESSION.OBJECT_POOL, session, pool);
		
		userSession = session;
	}
	
	/**
	 * Retorna o pool de objetos mediado entre Arquitetura e Classes externas.
	 * 
	 * @return Pool de objetos compartilhado
	 */
	public synchronized ObjectPool getObjectPool(){
		return pool;
	}
	
	/**
	 * Armazenar valor de atribudo no mediador
	 * @param <T> Tipo armazenado do atributo
	 * @param name Nome do atribudo
	 * @param value Valor do atributo
	 */
	public synchronized <T> void setAttribute(final Attribute<T> name, final T value){
		arquitetura.setSessionAttribute(name, userSession, value);
	}
	
	/**
	 * Obter valor de atributo no mediador
	 * @param <T> Tipo armazenado do atributo
	 * @param name Nome do atributo
	 * @return <code>null</code> se o atributo não for encontrado.
	 */
	public synchronized <T> T getAttribute(final Attribute<T> name){
		return arquitetura.getSessionAttribute(name, userSession);
	}
}
