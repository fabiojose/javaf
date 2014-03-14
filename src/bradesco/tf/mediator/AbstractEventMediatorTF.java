package bradesco.tf.mediator;

import bradesco.tf.TerminalFinanceiro;
import bradesco.tf.UtilIdentify;
import bradesco.tf.dsn.UtilDSN;
import bradesco.tf.layout.UtilLayout;

import com.javaf.javase.lang.UtilObject;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public abstract class AbstractEventMediatorTF implements IEventMediatorTF {

	protected final TerminalFinanceiro arquitetura;
	protected final UtilDSN dsn;
	protected final UtilIdentify identify;
	protected final UtilObject object;
	protected final UtilLayout layout;
	protected final ILocalization localization;
	
	public AbstractEventMediatorTF(){
		arquitetura  = TerminalFinanceiro.getInstance();
		dsn          = UtilDSN.getInstance();
		identify     = UtilIdentify.getInstance();
		object       = UtilObject.getInstance();
		layout       = UtilLayout.getInstance();
		localization = Localization.getInstance();
	}
}
