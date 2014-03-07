package cobol.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.javase.util.Localization;


import cobol.CobolConstants.DEFAULT;


/**
 * Formato padrão de booleano para cobol book.
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class BooleanCobolFormat extends Format {
	private static final long serialVersionUID = -4473185718779875419L;
	
	@Override
	public StringBuffer format(final Object obj, final StringBuffer toAppendTo, final FieldPosition pos) {
		
		if(obj instanceof Boolean){
			final Boolean _boolean = (Boolean)obj;
			if(_boolean){
				//TRUE
				toAppendTo.append(DEFAULT.BOOLEAN_TRUE);
			} else {
				//FALSE
				toAppendTo.append(DEFAULT.BOOLEAN_FALSE);
			}
		} else {
			throw new IllegalArgumentException(Localization.getInstance().localize(I18N.ARGUMENTO_N_DEVE_SER_UMA_INSTANCIA, INTEGER._1, Boolean.class));
		}
		
		return toAppendTo;
	}

	@Override
	public Object parseObject(final String source, final ParsePosition pos) {
		
		Object _result = null;
		_result = DEFAULT.BOOLEAN_MAPPING.get(source);
		pos.setIndex(INTEGER._1);
		
		return _result;
	}
}
