package bradesco.tf.outside.athic;

import java.util.Map;

import com.javaf.Constants.STRING;
import com.javaf.brazil.Pessoa;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
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
public class BPOCalling extends ATHICCalling<BPOResult> {
	
	private final TerminalFinanceiro arquitetura = TerminalFinanceiro.getInstance();
	private final UtilExterno externo            = UtilExterno.getInstance();
	private final UtilCollection collection      = UtilCollection.getInstance();
	
	@Override
	public BPOResult invoke(final IEventsServiceProvider provider, final ATHICInput input) throws UnsuccessfulException {
		final BPOResult _result = new BPOResult();
		final ILogging _logging = Logging.loggerOf(getClass());
		final Ambiente _ambiente = arquitetura.getAmbiente();
		
		input.validate();
		final Pessoa _cliente = (Pessoa)input.getParameters().get(ATHIC.CLIENTE);
		
		String _product = null;
		final DigitalizacaoBPOType _type = (DigitalizacaoBPOType)input.getParameters().get(ATHIC.BPO_TYPE);
		if(DigitalizacaoBPOType.PV.equals(_type)){
			//Povoamento (NC)
			_product = _ambiente.getProperty(TFConstants.AMBIENTE.ATHIC_BPO_PRODUCT_PV);
			_logging.debug("ATHIC SCANNER - BPO - PV - PRODUCT: " + _product);
			
		} else if(DigitalizacaoBPOType.NC.equals(_type)){
			//Nova Concessão (NC)
			_product = _ambiente.getProperty(TFConstants.AMBIENTE.ATHIC_BPO_PRODUCT_NC);
			_logging.debug("ATHIC SCANNER - BPO - NC - PRODUCT: " + _product);
			
		} else {
			throw new IllegalArgumentException("unsupported type: " + _type);
		}
		
		final String _id = externo.newATHICId(_cliente);
		_logging.debug("ATHIC SCANNER - BPO - ID GERADO: " + _id);
		
		final Map<String, Object> _parameters = collection.clone(input.getParameters());
		_parameters.put(ATHIC.PRODUCT,  _product);
		_parameters.put(ATHIC.ID,       _id);
		_parameters.put(ATHIC.CSV_PATH, STRING.EMPTY);
		
		final int _returned = super.invoke(provider, _parameters);
		_result.setCode(_returned);
		_result.setIdentificador(_id);
		
		return _result;
	}
}
