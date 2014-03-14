package bradesco.tf.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import com.javaf.Constants;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.javase.util.Localization;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class BooleanDSNViewFormat extends Format {
	private static final long serialVersionUID = -4539597138838144451L;

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {

		if(obj instanceof Boolean){
			final Boolean _boolean = (Boolean)obj;
			if(_boolean){
				//TRUE
				toAppendTo.append(Constants.DEFAULT.STRING_SIM);
			} else {
				//FALSE
				toAppendTo.append(Constants.DEFAULT.STRING_NAO);
			}
		} else {
			throw new IllegalArgumentException(Localization.getInstance().localize(I18N.ARGUMENTO_N_DEVE_SER_UMA_INSTANCIA, INTEGER._1, Boolean.class));
		}
		
		return toAppendTo;
	}

	@Override
	public Object parseObject(final String source, ParsePosition pos) {

		Object _result = null;
		
		if(Constants.DEFAULT.STRING_SIM.equalsIgnoreCase(source)){
			_result = Boolean.TRUE;
		} else {
			_result = Boolean.FALSE;
		}
		pos.setIndex(INTEGER._1);
		
		return _result;
	}

}
