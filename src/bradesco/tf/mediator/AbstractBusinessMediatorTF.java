package bradesco.tf.mediator;

import bradesco.tf.TerminalFinanceiro;
import bradesco.tf.UtilBusiness;
import bradesco.tf.UtilIdentify;

import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public abstract class AbstractBusinessMediatorTF implements IBusinessMediatorTF {
	
	protected final TerminalFinanceiro arquitetura = TerminalFinanceiro.getInstance();
	protected final UtilReflection reflection      = UtilReflection.getInstance();
	protected final UtilIdentify identify          = UtilIdentify.getInstance();
	protected final UtilFormat format              = UtilFormat.getInstance();
	protected final UtilBusiness business          = UtilBusiness.getInstance();
	protected final ILocalization localization     = Localization.getInstance();
	
}
