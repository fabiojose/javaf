package com.javaf.javase.util;

import java.util.Properties;
import java.util.Set;

import com.javaf.pattern.Acceptable;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public interface IProperties extends Acceptable<IProperties> {

	String getProperty(String name);
	
	/**
	 * Obter algum valor na cole��o de propriedades do Projeto
	 * @param key Nome-chave da propriedade
	 * @param forNull Valor padr�o para ser retornado caso a propriedade n�o exista
	 * @return <code>forNull</code> caso a propriedade n�o exista
	 * @see Properties
	 */
	String getProperty(String name, String forNull);
	
	Set<Object> keySet();
	
}
