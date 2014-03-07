package com.javaf.brazil;

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import com.javaf.Bagman;
import com.javaf.Constants;
import com.javaf.ObjectPool;
import com.javaf.Utility;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.MASK;
import com.javaf.Constants.REGEX;
import com.javaf.Constants.STRING;
import com.javaf.javase.text.UtilFormat;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class BrazilFormat implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new BrazilFormat();
		}
		
	};
	
	private final ObjectPool opool  = ObjectPool.getPool();;
	private final UtilFormat format = UtilFormat.getInstance();
	private BrazilFormat(){
		
	}
	
	public static final synchronized BrazilFormat getInstance(){
		return Bagman.utilOf(BrazilFormat.class, FACTORY);
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

		final String _cnpj = Constants.DEFAULT.DOCUMENTO_CNPJ.format(cnpj);
		
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
			return format.toLong( cnpj.replaceAll(REGEX.NAO_NUMEROS, STRING.EMPTY) );
		}
		return INTEGER._0;
	}
	
	public String toCnpj(final int corpo, final int filial, final int controle){
		
		String _cnpj = Constants.DEFAULT.DOCUMENTO_CORPO.format(corpo);
		      _cnpj += Constants.DEFAULT.DOCUMENTO_FILIAL.format(filial);
		      _cnpj += Constants.DEFAULT.DOCUMENTO_CONTROLE.format(controle);
		
		return toCnpj(format.toLong(_cnpj));
	}
	
	public String toCpf(final long cpf){
		
		String _result = STRING.EMPTY;
		final JFormattedTextField _field = getCpfFormatted();
		
		_field.setText(Constants.DEFAULT.DOCUMENTO_CPF.format(cpf));
		_result = _field.getText();
		
		return _result;
	}
	
	public String toCpf(final int corpo, final int controle){
		
		String _cpf = Constants.DEFAULT.DOCUMENTO_CORPO.format(corpo);
		      _cpf += Constants.DEFAULT.DOCUMENTO_CONTROLE.format(controle);
		
		return toCpf(format.toLong(_cpf));
	}
	
	public long toCpf(final String cpf){
		return toCnpj(cpf);
	}
	
	public String toString(final long numero, final PessoaType tipo){
		if(PessoaType.JURIDICA.equals(tipo)){
			return toCnpj(numero);
		}
		
		return toCpf(numero);
	}
	
	public int toCorpo(final long numero, final PessoaType tipo){
		
		if(PessoaType.JURIDICA.equals(tipo)){
			final String _formatted = Constants.DEFAULT.DOCUMENTO_CNPJ.format(numero);
			return Integer.parseInt( _formatted.substring(INTEGER._0, _formatted.length() - INTEGER._6) );
		}
		
		final String _formatted = Constants.DEFAULT.DOCUMENTO_CPF.format(numero); 
		return Integer.parseInt( _formatted.substring(INTEGER._0, _formatted.length() - INTEGER._2) );
	}
	
	public int toFilial(final long numero, final PessoaType tipo){
		
		if(PessoaType.JURIDICA.equals(tipo)){
			final String _formatted = Constants.DEFAULT.DOCUMENTO_CNPJ.format(numero);
			return Integer.parseInt( _formatted.substring(_formatted.length() - INTEGER._6, _formatted.length() - INTEGER._2) );
		}
		
		return INTEGER._0;
	}
	
	public int toControle(final long numero, final PessoaType tipo){
		
		if(PessoaType.JURIDICA.equals(tipo)){
			final String _formatted = Constants.DEFAULT.DOCUMENTO_CNPJ.format(numero);
			return Integer.parseInt( _formatted.substring(_formatted.length() - INTEGER._2) );
		}
		
		final String _formatted = Constants.DEFAULT.DOCUMENTO_CPF.format(numero); 
		return Integer.parseInt( _formatted.substring(_formatted.length() - INTEGER._2) );
	}
}
