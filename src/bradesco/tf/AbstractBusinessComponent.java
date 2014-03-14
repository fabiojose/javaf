package bradesco.tf;

import com.javaf.ObjectPool;
import com.javaf.javase.lang.UtilClass;
import com.javaf.javase.lang.UtilString;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;

import br.com.bradesco.core.aq.exceptions.BusinessRuleException;
import br.com.bradesco.core.aq.exceptions.RollbackException;
import br.com.bradesco.core.aq.persistence.BradescoPersistenceException;
import br.com.bradesco.core.aq.service.IBusinessRuleServiceProvider;
import br.com.bradesco.core.aq.service.TransferBean;
import bradesco.tf.TFConstants.BUSINESS;
import bradesco.tf.builder.TFBuilder;
import bradesco.tf.mediator.BusinessMediator;

/**
 * Componente de Negócio base para aumentar produtividade na implementação do Terminal Financeiro
 * @author fabiojm - Fábio José de Moraes
 */
public abstract class AbstractBusinessComponent extends br.com.bradesco.core.aq.service.AbstractBusinessComponent {

	protected final UtilReflection reflection      = UtilReflection.getInstance();
	protected final UtilClass clazz                = UtilClass.getInstance();
	protected final TerminalFinanceiro arquitetura = TerminalFinanceiro.getInstance();
	protected final UtilIdentify identify          = UtilIdentify.getInstance();
	protected final UtilFormat format              = UtilFormat.getInstance();
	protected final UtilBusiness business          = UtilBusiness.getInstance();
	protected final ILocalization localization     = Localization.getInstance();
	protected final UtilString string              = UtilString.getInstance();
	protected final TFBuilder builder              = TFBuilder.getInstance();
	protected final BusinessMediator mediator      = BusinessMediator.getInstance();
	
	/*
	protected final ProjectVisitors visitor           = ProjectVisitors.getInstance();
	
	/**
	 * Repositórios com mediadores que implementão necessidades externas ao Projeto.
	 *
	protected final ProjectOutsideMediators outside   = ProjectOutsideMediators.getInstance();*/
	
	@Override
	public String executeFuncionalRollback(IBusinessRuleServiceProvider provider) throws BusinessRuleException {
		return null;
	}

	@Override
	public String executeRule(final IBusinessRuleServiceProvider provider, final TransferBean transfer) throws BusinessRuleException, RollbackException, BradescoPersistenceException{
		return execute(provider, clazz.cast(transfer, TransferBeanWrapper.class));
	}
	
	public final ObjectPool getObjectPool(final IBusinessRuleServiceProvider provider){
		return arquitetura.getObjectPool(provider);
	}
	
	public final String execute(final IBusinessRuleServiceProvider provider, final TransferBeanWrapper wrapper, final Object ocbm, final InvokeType invoke, final boolean fetchAll) throws BusinessRuleException, RollbackException, BradescoPersistenceException{
		String _result = BUSINESS.EXECUTADO;
		
		//atualizar o caller
		wrapper.put(BUSINESS.CALLER, getClass());
		
		business.fwo(provider, wrapper, ocbm, invoke, fetchAll);
		
		return _result;
	}
	
	public abstract String execute(final IBusinessRuleServiceProvider provider, final TransferBeanWrapper wrapper) throws BusinessRuleException, RollbackException, BradescoPersistenceException;
}
