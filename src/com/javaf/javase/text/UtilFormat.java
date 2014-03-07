package com.javaf.javase.text;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;



import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.javaf.Bagman;
import com.javaf.ObjectPool;
import com.javaf.Utility;
import com.javaf.Constants.DEFAULT;
import com.javaf.Constants.DOUBLE;
import com.javaf.Constants.ENCODING;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.LONG;
import com.javaf.Constants.MASK;
import com.javaf.Constants.REFLECTION;
import com.javaf.Constants.REGEX;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.reflect.InvokeException;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilFormat implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilFormat();
		}
		
	};
	
	private static final ILogging LOGGING = Logging.loggerOf(UtilFormat.class);
	
	private final UtilReflection reflection = UtilReflection.getInstance();
	private final ObjectPool opool          = ObjectPool.getPool();
	private UtilFormat(){
		
	}
	
	public static final synchronized UtilFormat getInstance(){
		return Bagman.utilOf(UtilFormat.class, FACTORY);
	}
	
	public String toString(final Object o){
		final StringBuilder _result = new StringBuilder();
		
		if( !(o instanceof Class) ){
			
			_result.append(o);
			
		} else {
			final Class<?> _class = (Class<?>)o;
			_result.append(_class.getName());
		}
		
		return _result.toString();
	}
	
	private void toString(final Throwable t, final StringBuilder s){
		if(null!= t){
			final StringWriter _swriter = new StringWriter();
			final PrintWriter _writer   = new PrintWriter(_swriter);
			
			t.printStackTrace(_writer);
			
			s.append(_swriter.toString() + STRING.NEW_LINE);
			
			if(null!= t.getCause()){
				
				toString(t.getCause(), s);
				
			} else {
				
				if(t instanceof InvocationTargetException){
					
					toString(((InvocationTargetException)t).getTargetException(), s);
					
				} else {
					try{
						final Object _nested = reflection.invoke(t, REFLECTION.METHOD_GET_NESTED_EXCEPTION, REFLECTION.NO_ARGS);
						if(_nested instanceof Throwable){
							toString((Throwable)_nested, s);
						}
					}catch(InvokeException _e){
					}
				}
			}
		}
	}
	
	public String toString(final Throwable t){
		final StringBuilder _result = new StringBuilder(STRING.EMPTY);
		
		toString(t, _result);
		
		return _result.toString();
	}
	
	public boolean isInteger(final String s){
		boolean _result = Boolean.FALSE;
		
		try{
			Integer.parseInt(s);
			_result = Boolean.TRUE;
			
		}catch(NumberFormatException _e){
			LOGGING.trace(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public boolean isLong(final String s){
		boolean _result = Boolean.FALSE;
		
		try{
			Long.parseLong(s);
			_result = Boolean.TRUE;
			
		}catch(NumberFormatException _e){
			LOGGING.trace(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public Long toLong(final String s){
		Long _result = 0L;
		
		try{
			_result = Long.valueOf(s);
			
		}catch(NumberFormatException _e){
			LOGGING.trace(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public Date toDate(final XMLGregorianCalendar gregorian){
		Date _result = null;
		
		if(null!= gregorian){
			_result = gregorian.toGregorianCalendar().getTime();
		}
		
		return _result;
	}
	
	public XMLGregorianCalendar toXMLGregorianCalendar(final Date date){
		XMLGregorianCalendar _result = null;
		
		final GregorianCalendar _gregorian = new GregorianCalendar();
		_gregorian.setTime(date);
		
		try{
			_result = DatatypeFactory.newInstance().newXMLGregorianCalendar(_gregorian);
			
		}catch(DatatypeConfigurationException _e){
			LOGGING.error(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public String toString(final Map<?, ?> map){
		final StringBuilder _result = new StringBuilder(STRING.EMPTY);
		
		if(null!= map){
			final Set<?> _keys = map.keySet();
			_result.append(STRING.COLCHETES_ABRE);
			for(Object _key : _keys){
				_result.append(_key);
				_result.append(STRING.IGUAL);
				_result.append(map.get(_key));
				
				_result.append(STRING.NEW_LINE);
				
			}
			_result.append(STRING.COLCHETES_FECHA);
		}
		
		return _result.toString();
	}
	
	public String toString(final Enumeration<?> e){
		final StringBuilder _result = new StringBuilder(STRING.EMPTY);
		
		if(null!= e){
			_result.append(STRING.COLCHETES_ABRE);
			while(e.hasMoreElements()){
				
				_result.append(e.nextElement());
				_result.append(STRING.VIRGULA);
				
			}
			_result.append(STRING.COLCHETES_FECHA);
		}
		
		return _result.toString();
	}
	
	public String toString(final Enum<?> e){
		final StringBuilder _result = new StringBuilder(STRING.EMPTY);
		
		if(null!= e){
			_result.append(STRING.COLCHETES_ABRE);
			final Method _values     = reflection.methodOf(e.getClass(), REFLECTION.VALUES_METHOD);
			final Object[] _returned = (Object[])reflection.invoke(e, _values, REFLECTION.NO_ARGS);
			
			for(Object _value : _returned){
				_result.append(_value);
				_result.append(STRING.VIRGULA);
			}
			
			_result.append(STRING.COLCHETES_FECHA);
		}
		
		return _result.toString();
	}
	
	public String toString(final Class<? extends Enum<?>> e){
		final StringBuilder _result = new StringBuilder(STRING.EMPTY);
		
		if(null!= e){
			_result.append(STRING.COLCHETES_ABRE);
			//esse é estatico
			final Method _values     = reflection.methodOf(e, REFLECTION.VALUES_METHOD);
			final Object[] _returned = (Object[])reflection.invoke(null, _values, REFLECTION.NO_ARGS);
			
			for(Object _value : _returned){
				_result.append(_value);
				_result.append(STRING.VIRGULA);
			}
			
			_result.append(STRING.COLCHETES_FECHA);
		}
		
		return _result.toString();
	}
	
	public <T> String toString(final T[] o){
		final StringBuilder _result = new StringBuilder(STRING.EMPTY);
		
		if(null!= o){
			_result.append(STRING.COLCHETES_ABRE);
			for(Object _o : o){
				_result.append(_o);
				_result.append(STRING.VIRGULA);
			}
			_result.append(STRING.COLCHETES_FECHA);
		}
		
		return _result.toString();
	}
	
	public String toString(final int value, final Format format){
		String _result = STRING.EMPTY;
		if(null== format){
			throw new NullPointerException();
		}
		
		_result = format.format(value);
		
		return _result;
	}
	
	/**
	 * @param bool
	 * @return <code>false</code> se <code>bool</code> for <code>null</code>
	 */
	public boolean toBoolean(final Boolean bool){
    	return (bool != null ? bool : Boolean.FALSE);
    }
	
	public boolean toBoolean(final Object bool, final boolean defaultValue){
		
		boolean _result = defaultValue;
		if(bool instanceof Boolean){
			_result = (Boolean)bool;
		}
		
		return _result;
	}
	
	public boolean toBoolean(final Object bool){
		return toBoolean(bool, Boolean.FALSE);
	}
	
	public boolean toBoolean(final String value){
		
		boolean _result = Boolean.FALSE;
		
		if(null!= value){
			_result = Boolean.parseBoolean(value);
		}
		
		return _result;
	}
    
    /**
     * Previne lançamento de <code>NullPointerException</code>
     * @param integer
     * @return <code>0</code> para <code>null</code> e o própio valor, caso contrário
     * @see Constants.INTEGERS
     */
    public int toInteger(final Integer integer){
    	
    	if(null== integer){
    		return INTEGER._0;
    	}
    	
    	return integer;
    }
    
    public int toInteger(final Object object){
    	
    	if(null!= object){
    		if(object instanceof Integer){
    			return (Integer)object;
    		}
    	}
    	
    	return INTEGER._0;
    }
    
    public int toInteger(final String string){
    	
    	int _result = INTEGER._0;
    	
    	try{
    		_result = Integer.parseInt(string);
    		
    	}catch(NumberFormatException _e){
    		_e.printStackTrace();
    	}
    	
    	return _result;
    }
    
    /**
     * Previne lançamento de <code>NullPointerException</code>
     * @param value
     * @return <code>Constants.STRINGS.EMPTY</code> para <code>value = null</code>, caso contrário retornará <code>value</code>
     * @see Constants.STRINGS
     */
    public String toString(final String value){
    	
    	if(null== value){
    		return STRING.EMPTY;
    	}
    	
    	return value;
    }
    
    public String toHttp(final String value){
    	String _result = STRING.EMPTY;
    	
    	try{
	    	if(null!= value){
	    		_result = URLEncoder.encode(value, ENCODING.UTF_8);
	    	}
	    	
    	}catch(UnsupportedEncodingException _e){
    	}
    	
    	return _result;
    }
    
    /**
     * Previne lançamento de <code>NullPointerException</code>
     * @param bool
     * @return <code>Constants.STRINGS.N</code> se <code>bool</code> for null ou false e true caso contrário.
     */
    public String toString(final Boolean bool){
    	if(null== bool){
    		return DEFAULT.STRING_NAO;
    	}
    	
    	return (bool ? DEFAULT.STRING_SIM : DEFAULT.STRING_NAO );
    }
    
    /**
     * Previne lançamento de <code>NullPointerException</code>
     * @param integer
     * @return <code>integer</code> formatado sem os caracteres separadores ou uma String vazia se for <code>null</code>
     */
    public String toString(final Integer integer){
    	
    	if(null!= integer){
    		return DEFAULT.DEFAULT_INTEGER_FORMAT.format(integer);
    	}
    	
    	return STRING.EMPTY;
    }
    
    /**
     * Previne lançamento de <code>NullPointerException</code>
     * 
     * @param value Um valor Long
     * @return <code>value</code> formatado sem os caracteres separadores ou uma String vazia se for <code>null</code>
     */
    public String toString(final Long value){
    	
    	if(null!= value){
    		return DEFAULT.DEFAULT_LONG_FORMAT.format(value);
    	}
    	
    	return STRING.EMPTY;
    }
    
    /**
     * Previne lançamento de <code>NullPointerException</code> e aplica a fomatação persolizada
     * @param value Valor para fomatação
     * @param formatter fomatação personalizada
     * @return <code>value</code> formatada ou <code>zeros</code> formatados caso <code>value</code> seja <code>null</code>
     */
    public String toString(final Long value, final DecimalFormat formatter){
    	if(null!= value){
    		return formatter.format(value);
    	}
    	
    	return formatter.format(INTEGER._0);
    }
    
    /**
     * Previne lançamento de <code>NullPointerException</code> e aplica a fomatação persolizada
     * @param value Valor para fomatação
     * @param formatter fomatação personalizada
     * @return <code>value</code> formatada ou <code>zeros</code> formatados caso <code>value</code> seja <code>null</code>
     */
    public String toString(final Integer value, final DecimalFormat formatter){
    	if(null!= value){
    		return formatter.format(value);
    	}
    	
    	return formatter.format(INTEGER._0);
    }
    
    public String toString(final Object value, final Format formatter){
    	
    	String _result = STRING.EMPTY;
    	
    	if(null!= value){
    		formatter.format(value);
    	}
    	
    	return _result;
    }
    
    /**
     * Previne lançamento de <code>NullPointerException</code>
     * 
     * @param value Valor para fomatação
     * @return <code>value</code> formatada ou <code>zeros</code> formatados caso <code>value</code> seja <code>null</code>
     */
    public String toString(final Double value){
    	if(null!= value){
    		return DEFAULT.DEFAULT_FLOAT_POINT_FORMAT.format(value);
    	}
    	
    	return DEFAULT.DEFAULT_FLOAT_POINT_FORMAT.format(DOUBLE._0);
    }
    
    /**
     * Previne lançamento de <code>NullPointerException</code>
     * 
     * @param value Valor para fomatação
     * @return <code>value</code> formatada ou <code>zeros</code> formatados caso <code>value</code> seja <code>null</code>
     */
    public String toString(final Float value){
    	if(null!= value){
    		return DEFAULT.DEFAULT_FLOAT_POINT_FORMAT.format(value);
    	}
    	
    	return DEFAULT.DEFAULT_FLOAT_POINT_FORMAT.format(DOUBLE._0);
    }
    
    public String toString(final List<String> strings){
    	return toString(strings, STRING.EMPTY);
    }
    
    public String toString(final List<String> strings, final String marcador){
    	
    	final StringBuilder _result = new StringBuilder(STRING.EMPTY);
    	for(String _string : strings){
    		if(null!= marcador && !STRING.EMPTY.equals(marcador)){
    			_result.append(marcador);
    		}
    		
    		_result.append(_string);
    		_result.append(STRING.NEW_LINE);
    	}
    	
    	return _result.toString();
    }
    
    /**
     * Previne lançamento de <code>NullPointerException</code>
     * @param value
     * @return <code>Constants.DOUBLES.DOUBLE_0</code> se <code>value</code> for null, caso contrário o seu valor
     */
    public double toDouble(final Double value){
    	
    	if(null!= value){
    		return value.doubleValue();
    	}
    	
    	return DOUBLE._0;
    }
    
    public float toFloat(final Float value){
    	
    	float _result = (float)DOUBLE._0;
    	
    	if(null!= value){
    		_result = value.floatValue();
    	}
    	
    	return _result;
    }
    
    /**
     * Previne lançamento de <code>NullPointerException</code>
     * @param date Instância 
     * @return <code>date</code> no formato ABNT (<code>dd/MM/yyyy HH:mm:ss</code>) ou String vazia se <code>date</date> for <code>null</code>
     */
    public String toString(final Date date){
    	
    	if(null!= date){
    		return DEFAULT.ABNT_DATE_TIME_FORMAT.format(date);
    	}
    	
    	return STRING.EMPTY;
    }
    
    /**
     * Previne lançamento de <code>NullPointerException</code> e aplica fomatação personalizada
     * @param date Instância para fomatação
     * @param formatter fomatação personalizada
     * @return Data corrente formatada, caso a instância para fomatação seja <code>null</code>
     */
    public String toString(final Date date, final DateFormat formatter){
    	
    	if(null!= date){
    		return formatter.format(date);
    	}
    	
    	return formatter.format(new Date());
    }
    
    /**
     * Transforma a instância base para o formato personalizado
     * @param string Instância base para transformação
     * @param formatterFrom 
     * Formato atual da <code>string</code> passada no parâmetro.
     * Geralmente a fomatação usada para este está no <code>FORMATTERS.TIMESTAMP_COBOL</code>
     * @param formatterTo Formato desejado da <code>string</code> (e.g. <code>FORMATTERS.ABNT_DATE_TIME_FORMAT</code>)
     * @return Retorna a data formatada
     */
    public String toString(final String string, final DateFormat formatterFrom, final DateFormat formatterTo) {
    	final Date _date;
    	
    	if(string != null && !string.isEmpty()){
    		try {
    			_date = parseDate(string, formatterFrom);
    			
    			return toString(_date, formatterTo);
    		} catch (ParsingException e) {
    			LOGGING.error(e.getMessage(), e);
			}
    	}
    	
    	return STRING.EMPTY;
    }
    
    /**
     * Transformar data aplicando padrão default.
     * @param string
     * @return
     * @throws ParsingException
     * @see {@link DEFAULT#ABNT_DATE_FORMAT}
     */
    public Date parseDate(final String string) throws ParsingException {
    	Date _result = null;
    	
    	_result = parseDate(string, DEFAULT.ABNT_DATE_FORMAT);
    	
    	return _result;
    }
    
    /**
     * Transforma uma String em Date.
     * 
     * @param string Instância base para transformação
     * @param formatter Formato da String que transformará
     * @return
     * @throws ParsingException Lançada quando não puder transformar a String
     */
    public Date parseDate(final String string, final DateFormat formatter) throws ParsingException {
    	
    	try{
    		return formatter.parse(string);
    		
    	}catch(ParseException _e){
    		throw new ParsingException(_e.getMessage(), _e);
    	}
    }
    
    public Date parseDate(final String string, final DateFormat formatter, final Date defaultValue) throws ParsingException {
    	
    	try{
    		return formatter.parse(string);
    		
    	}catch(ParseException _e){
    		
    		return defaultValue;
    	}
    }
    
    public Object parse(final String string, final Format formatter) throws ParsingException {
    	
    	Object _result = null;
    	
    	try{
    		_result = formatter.parseObject(string);
    	}catch(ParseException _e){
    		throw new ParsingException(_e.getMessage(), _e);
    	}
    	
    	return _result;
    }
    
    /**
     * Transforma uma String em Double utilizando o formatador padrão.
     *  
     * @param string Instância base para transformação
     * @return
     * @throws ParsingException Lançada quando não puder transformar a String
     */
    public Double parseDouble(final String string) throws ParsingException{
    	
    	try{
    		return DEFAULT.DEFAULT_FLOAT_POINT_FORMAT.parse(string).doubleValue();
    		
    	}catch(ParseException _e){
    		throw new ParsingException(_e.getMessage(), _e);
    	}
    }
    
    /**
     * Transforma uma String em Long utilizando o formatador padrão.
     * 
     * @param string Instância base para transformação
     * @return
     * @throws ParsingException Lançada quando não puder transformar a String
     */
    public Long parseLong(final String string) throws ParsingException {
    	
    	try{
    		return DEFAULT.DEFAULT_LONG_FORMAT.parse(string).longValue();
    		
    	}catch(ParseException _e){
    		throw new ParsingException(_e.getMessage(), _e);
    	}
    }
    
    private JFormattedTextField getCnpjFormatted(){
		
		JFormattedTextField _result = opool.get(MASK.CNPJ, JFormattedTextField.class);
		if(null== _result){
			_result = new JFormattedTextField();
			
			try{
				final MaskFormatter _mask = new MaskFormatter(MASK.CNPJ);
				_mask.install(_result);
				
				opool.put(MASK.CNPJ, _result);
			}catch(ParseException _e){
				_e.printStackTrace();
			}
		}
		
		return _result;
	}
    
    private JFormattedTextField getCnpjFormatted15Dig(){
		
		JFormattedTextField _result = opool.get(MASK.CNPJ_15DIG, JFormattedTextField.class);
		if(null== _result){
			_result = new JFormattedTextField();
			
			try{
				final MaskFormatter _mask = new MaskFormatter(MASK.CNPJ_15DIG);
				_mask.install(_result);
				
				opool.put(MASK.CNPJ_15DIG, _result);
			}catch(ParseException _e){
				_e.printStackTrace();
			}
		}
		
		return _result;
	}
	
	private JFormattedTextField getCpfFormatted(){
		
		JFormattedTextField _result = opool.get(MASK.CPF, JFormattedTextField.class);
		if(null== _result){
			_result = new JFormattedTextField();
			
			try{
				final MaskFormatter _mask = new MaskFormatter(MASK.CPF);
				_mask.install(_result);
				
				opool.put(MASK.CPF, _result);
			}catch(ParseException _e){
				_e.printStackTrace();
			}
		}
		
		return _result;
	}
	
	public String toCnpj(final long cnpj){
		
		String _result = STRING.EMPTY;

		final String _cnpj = DEFAULT.DOCUMENTO_CNPJ.format(cnpj);
		
		JFormattedTextField _field = null;
		if(_cnpj.length() == INTEGER._15){
			_field = getCnpjFormatted15Dig();
		} else {
			_field = getCnpjFormatted();
		}

		_field.setText(_cnpj);
		_result = _field.getText();

		
		return _result;
	}
	
	public long toCnpj(final String cnpj){
		
		if(null!= cnpj){
			return toLong( cnpj.replaceAll(REGEX.NAO_NUMEROS, STRING.EMPTY) );
		}
		return INTEGER._0;
	}
	
	public String toCnpj(final int corpo, final int filial, final int controle){
		
		String _cnpj = DEFAULT.DOCUMENTO_CORPO.format(corpo);
		      _cnpj += DEFAULT.DOCUMENTO_FILIAL.format(filial);
		      _cnpj += DEFAULT.DOCUMENTO_CONTROLE.format(controle);
		
		return toCnpj(toLong(_cnpj));
	}
	
	public String toCpf(final long cpf){
		
		String _result = STRING.EMPTY;
		final JFormattedTextField _field = getCpfFormatted();
		
		_field.setText(DEFAULT.DOCUMENTO_CPF.format(cpf));
		_result = _field.getText();
		
		return _result;
	}
	
	public String toCpf(final int corpo, final int controle){
		
		String _cpf = DEFAULT.DOCUMENTO_CORPO.format(corpo);
		      _cpf += DEFAULT.DOCUMENTO_CONTROLE.format(controle);
		
		return toCpf(toLong(_cpf));
	}
	
	public long toCpf(final String cpf){
		return toCnpj(cpf);
	}
	
	public Object fromCobol(final String cobol, final Format parser) throws ParsingException {
		
		Object _result = null;
		try{
			_result = parser.parseObject(cobol);
			
		}catch(ParseException _e){
			throw new ParsingException(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public boolean isDefault(final Object t){
		
		boolean _result = Boolean.TRUE;
		if(null!= t){
			final Class<?> _c = t.getClass();
			
			if(Byte.class.isAssignableFrom(_c) || Byte.TYPE.equals(_c)){
				final Byte _byte = (Byte)t;
				_result = _byte == (byte)0;
				
			} else if(Short.class.isAssignableFrom(_c) || Short.TYPE.equals(_c)){
				final Short _short = (Short)t;
				_result = _short == (short)0;
								
			} else if(Integer.class.isAssignableFrom(_c) || Byte.TYPE.equals(_c)){
				final Integer _integer = (Integer)t;
				_result = INTEGER._0 == _integer.intValue();
				
			} else if(Long.class.isAssignableFrom(_c) || Long.TYPE.equals(_c)){
				final Long _long = (Long)t;
				_result = LONG._0 == _long.longValue();
				
			} else if(Float.class.isAssignableFrom(_c) || Float.TYPE.equals(_c)){
				final Float _float = (Float)t;
				_result = _float == (float)0;
				
			} else if(Double.class.isAssignableFrom(_c) || Double.TYPE.equals(_c)){
				final Double _double = (Double)t;
				_result = _double == (double)0;
						
			} else if(Character.class.isAssignableFrom(_c) || Character.TYPE.equals(_c)){
				final Character _char = (Character)t;
				_result = _char == (char)0;
								
			} else if(String.class.isAssignableFrom(_c)){
				final String _string = (String)t;
				_result = STRING.EMPTY.equals(_string);
				
			} else if(Boolean.class.isAssignableFrom(_c) || Boolean.TYPE.equals(_c)){
				final Boolean _boolean = (Boolean)t;
				_result = Boolean.FALSE.equals(_boolean);
			
			} else if(Collection.class.isAssignableFrom(_c)){
				final Collection<?> _collection = (Collection<?>)t;
				_result = _collection.isEmpty();
				
			} else if(Map.class.isAssignableFrom(_c)){
				final Map<?, ?> _map = (Map<?, ?>)t;
				_result = _map.isEmpty();
				
			} else {//objetos
				_result = Boolean.FALSE;
			}
		}
		
		return _result;
	}

	public <T> List<T> toList(final T[] array){
		
		List<T> _result = new ArrayList<T>();
		if(null!= array){
			_result = Arrays.asList(array);
		}
		
		return _result;
	}
}
