package bradesco.tf;

import java.util.HashMap;
import java.util.Map;

import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;
import com.javaf.pattern.Visitor;

/**
 * Orquestrador de instâncias.
 * @author fabiojm
 *
 */
public final class InstanceOrchestrator {
	private static final InstanceOrchestrator INSTANCE = new InstanceOrchestrator();
	
	private final Map<Class<?>, Visitor<?, ?>> reseters = new HashMap<Class<?>, Visitor<?, ?>>();
	private final ILocalization localization = Localization.getInstance();
	private InstanceOrchestrator(){
	
	}

	public static synchronized InstanceOrchestrator getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Registra reseter por classe.
	 * @param clazz Classe que terá o reseter registrado
	 * @param reseter Instancia do reseter
	 */
	public void register(final Class<?> clazz, final Visitor<?, ?> reseter){
		reseters.put(clazz, reseter);
	}
	
	/**
	 * Obtem o reseter da classe e visita a instância para executar a regra de valores-default
	 * @param instancia
	 * @throws NullPointerException Lançada caso o argumento seja nulo
	 * @throws InstanceNotFoundException Lançada caso o reseter não esteja registrado 
	 */
	@SuppressWarnings("unchecked")
	public void doReset(final Object instancia) throws NullPointerException, InstanceNotFoundException{
		
		if(null!= instancia){
			final Class<?> _key = instancia.getClass();
			final Visitor _reseter = reseters.get(_key);
			if(null!= _reseter){
				_reseter.visit(instancia);
			} else {
				throw new InstanceNotFoundException(localization.localize(I18N.NENHUM_RESETER_REGISTRADO_PARA, _key.getName()));
			}
		} else {
			throw new NullPointerException(localization.localize(I18N.ARGUMENTO_N_ESTA_NULL, INTEGER._1));
		}
	}
}
