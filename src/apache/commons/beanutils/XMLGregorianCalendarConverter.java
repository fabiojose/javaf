package apache.commons.beanutils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import com.javaf.Constants.PATTERN;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.text.UtilFormat;

public class XMLGregorianCalendarConverter implements Converter {
	
	private final ILogging logging = Logging.loggerOf(getClass());

	@SuppressWarnings("unchecked")
	public Object convert(final Class type, final Object value) {
		XMLGregorianCalendar _result = null;
		
		try{
			final String _source     = (String)value;
			final DateFormat _isodatetime = new SimpleDateFormat(PATTERN.ISO_DATE_TIME);
			
			Date _date = null;
			try{
				logging.info( "APACHE - COMMONS - CONVERT '" + value + "' TO CLASS '" + type + "' TRYING PATTERN '" + PATTERN.ISO_DATE_TIME + "' . . .");
				_date = _isodatetime.parse(_source);
				logging.info("APACHE - COMMONS - SUCCESS - CONVERT '" + value + "' TO CLASS '" + type + "' USING PATTERN '" + PATTERN.ISO_DATE_TIME + "'.");
				
			}catch(ParseException _e){
				
				logging.info( "APACHE - COMMONS - CONVERT '" + value + "' TO CLASS '" + type + "' TRYING PATTERN '" + PATTERN.ISO_DATE + "' . . .");
				final DateFormat _isodate = new SimpleDateFormat(PATTERN.ISO_DATE);
				_date = _isodate.parse(_source);
				logging.info("APACHE - COMMONS - SUCCESS - CONVERT '" + value + "' TO CLASS '" + type + "' USING PATTERN '" + PATTERN.ISO_DATE + "'.");
				
			}
			
			//criar instancia to tipo correto
			_result = UtilFormat.getInstance().toXMLGregorianCalendar(_date);
			
		}catch(ClassCastException _e){
			logging.error("APACHE - COMMONS - EXCEPTION - CONVERT '" + value + "' TO CLASS '" + type + "': " + _e.getMessage(), _e);
			throw new ConversionException(_e.getMessage(), _e);
		}catch(ParseException _e){
			logging.error("APACHE - COMMONS - EXCEPTION - CONVERT '" + value + "' TO CLASS '" + type + "': " + _e.getMessage(), _e);
			throw new ConversionException(_e.getMessage(), _e);
		}
		
		return _result;
	}

}
