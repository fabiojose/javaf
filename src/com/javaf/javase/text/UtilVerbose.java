package com.javaf.javase.text;

import com.javaf.Bagman;
import com.javaf.Utility;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.STRING;
import com.javaf.Constants.VERBOSE;
import com.javaf.javase.util.ILocalization;
import com.javaf.javase.util.Localization;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilVerbose implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilVerbose();
		}
		
	};
	
	private final ILocalization localization = Localization.getInstance();
	private final UtilFormat format;
	private UtilVerbose(){
		format = UtilFormat.getInstance();		
	}
	
	public static final synchronized UtilVerbose getInstance(){
		return Bagman.utilOf(UtilVerbose.class, FACTORY);
	}
	
	public String verbalize(final int i) throws IllegalArgumentException {
		
		final StringBuilder _result = new StringBuilder(STRING.EMPTY);
		if(i <= VERBOSE.MAXIMO){
			final String _valor = format.toString(i);
			final int _length = _valor.length();
			
			switch(_length){
				case INTEGER._4:
					final int _milhar = Integer.parseInt(_valor.substring(INTEGER._0, INTEGER._1));
					_result.append(VERBOSE.UNIDADE.get(_milhar));
					_result.append(STRING.SPACE1);
					_result.append(I18N.MIL);
					_result.append(STRING.SPACE1);
					
					final String _verb4 = verbalize(Integer.parseInt(_valor.substring(INTEGER._1)));
					if(!format.isDefault(_verb4)){
						_result.append(_verb4);
					}
					
					break;
				
				case INTEGER._3:
					final int _centena = Integer.parseInt(_valor.substring(INTEGER._0, INTEGER._1)) * INTEGER._100;
					_result.append(VERBOSE.CENTENA.get(_centena));
					_result.append(STRING.SPACE1);
					
					final String _verb3 = verbalize(Integer.parseInt(_valor.substring(INTEGER._1)));
					if(!format.isDefault(_verb3)){
						_result.append(I18N.E);
						_result.append(_verb3);
					}
					break;
					
				case INTEGER._2:
					final int _dezena = Integer.parseInt(_valor.substring(INTEGER._0, INTEGER._1)) * INTEGER._10;
					_result.append(VERBOSE.DEZENA.get(_dezena));
					_result.append(STRING.SPACE1);
					
					final String _verb2 = verbalize(Integer.parseInt(_valor.substring(INTEGER._1)));
					if(!format.isDefault(_verb2)){
						_result.append(I18N.E);
						_result.append(_verb2);
					}
					
					break;
					
				case INTEGER._1:
					final int _unidade = Integer.parseInt(_valor.substring(INTEGER._0));
					if(VERBOSE.UNIDADE.containsKey(_unidade)){
						_result.append(VERBOSE.UNIDADE.get(_unidade));
						_result.append(STRING.SPACE1);
					}
			}
	
		} else {
			throw new IllegalArgumentException(localization.localize(I18N.VALOR_MAXIMO_PARA_VERBALIZE_E_DE, String.valueOf(VERBOSE.MAXIMO)));
		}
		
		return _result.toString();
	}
	
	public static void main(String[] args){
		System.out.println(getInstance().verbalize(9999));
	}
}
