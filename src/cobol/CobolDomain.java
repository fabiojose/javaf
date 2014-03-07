package cobol;

import java.math.BigDecimal;
import java.text.Format;
import java.text.ParseException;
import java.util.Date;

import cobol.CobolConstants.DEFAULT;

import com.javaf.Constants.I18N;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.UtilObject;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.text.NullFormat;
import com.javaf.javase.text.ParsingException;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.Localization;
import com.javaf.pattern.Deen;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public final class CobolDomain {
	private static final CobolDomain INSTANCE = new CobolDomain();
	
	private static final ILogging LOGGING = Logging.loggerOf(CobolDomain.class);
	
	private final UtilReflection reflection = UtilReflection.getInstance();
	private final UtilFormat format         = UtilFormat.getInstance();
	private final UtilObject object         = UtilObject.getInstance();

	private CobolDomain(){
		
	}
	
	public static final synchronized CobolDomain getInstance(){
		return INSTANCE;
	}
	
	public String toString(final Object value, final Class<? extends Format> formatter){
		
		String _result = STRING.EMPTY;
		if(null!= formatter && !NullFormat.class.isAssignableFrom(formatter)){
		
			final Format _formatter = reflection.newInstance(formatter);
			_result = toString(value, _formatter);
		} else {
			_result = toString(value);
		}
		
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	public String toString(final Object value){
		
		String _result = STRING.EMPTY;
		
		if(value instanceof Date){
			_result = toString(value, DEFAULT.COBOL_DATE_FORMAT);
		
		}else if(value instanceof BigDecimal){
			_result = toString(value, DEFAULT.COBOL_DECIMAL_FORMAT);
		
		} else if(value instanceof Boolean){
			_result = toString(value, DEFAULT.COBOL_BOOLEAN_FORMAT);
			
		} else if(value instanceof Float){
			_result = toString(value, DEFAULT.COBOL_DECIMAL_FORMAT);
			
		} else if(value instanceof Integer){
			_result = toString(value, DEFAULT.COBOL_DECIMAL_FORMAT);
			
		} else if(value instanceof Double){
			_result = toString(value, DEFAULT.COBOL_DECIMAL_FORMAT);
	
		} else if(value instanceof Long){
			_result = toString(value, DEFAULT.COBOL_DECIMAL_FORMAT);
			
		} else if(Deen.class.isAssignableFrom(value.getClass())){
			try{
				//_result = reflection.getResolver(value.getClass()).encode(value).toString();
				_result = object.implementOf(Deen.class, value.getClass()).encode(value).toString();
				
			}catch(IllegalArgumentException _e){
				LOGGING.warn(Localization.getInstance().localize(I18N.X_NAO_PODE_SER_OBTIDO, Deen.class, value.getClass().getName()), _e);
			}
		} else {
			_result = STRING.EMPTY + value;
		}
		
		return _result;
	}
	
	public String toString(final Object value, final Format formatter){
		return formatter.format(value);
	}
	
	@SuppressWarnings("unchecked")
	public Object toObject(final String cobol, final Class<?> type) throws ParsingException{
		
		Object _result = cobol;
		
		if(Date.class.isAssignableFrom(type)){
			
			try{
				_result = toObject(cobol, DEFAULT.COBOL_DATE_FORMAT);
			}catch(ParsingException _e){
				_result = format.parseDate(cobol);
			}
			
		} else if(BigDecimal.class.isAssignableFrom(type)){
			_result = toObject(cobol, DEFAULT.COBOL_DECIMAL_FORMAT);
			
		} else if(Boolean.class.isAssignableFrom(type) || Boolean.TYPE.equals(type)){
			_result = toObject(cobol, DEFAULT.COBOL_BOOLEAN_FORMAT);
			
		} else if(Float.class.isAssignableFrom(type) || Float.TYPE.equals(type)){
			_result = toObject(cobol, DEFAULT.COBOL_DECIMAL_FORMAT);
			
		} else if(Integer.class.isAssignableFrom(type) || Integer.TYPE.equals(type)){
			
			try{
				_result = Integer.valueOf(cobol);
				
			}catch(NumberFormatException _e){
				throw new ParsingException(_e.getMessage(), _e);
			}
			
		} else if(Double.class.isAssignableFrom(type) || Double.TYPE.equals(type)){
			_result = toObject(cobol, DEFAULT.COBOL_DECIMAL_FORMAT);
			
		} else if(Long.class.isAssignableFrom(type) || Long.TYPE.equals(type)){
			try{
				_result = Long.valueOf(cobol);
				
			}catch(NumberFormatException _e){
				throw new ParsingException(_e.getMessage(), _e);
			}
			
		} else if(Deen.class.isAssignableFrom(type)){
			try{

				_result = object.implementOf(Deen.class, type).decode(cobol);
				
			}catch(IllegalArgumentException _e){
				throw new ParsingException(Localization.getInstance().localize(I18N.X_NAO_PODE_SER_OBTIDO, Deen.class, type.getName()), _e);
			}
			
		} else if(type.isEnum()){
			_result = reflection.valueOf(type, cobol);
			
		} else {
			_result = cobol;
		}
		
		return _result;
	}
	
	public Object toObject(final String cobol, final Format parser) throws ParsingException {
		
		Object _result = null;
		try{
			_result = parser.parseObject(cobol);
			
		}catch(ParseException _e){
			throw new ParsingException(_e.getMessage(), _e);
		}
		
		return _result;
	}
}
