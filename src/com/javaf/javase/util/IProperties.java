package com.javaf.javase.util;

import java.util.Properties;
import java.util.Set;

import com.javaf.pattern.Acceptable;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public interface IProperties extends Acceptable<IProperties> {

	String getProperty(String name);
	
	/**
	 * Obter algum valor na coleção de propriedades do Projeto
	 * @param key Nome-chave da propriedade
	 * @param forNull Valor padrão para ser retornado caso a propriedade não exista
	 * @return <code>forNull</code> caso a propriedade não exista
	 * @see Properties
	 */
	String getProperty(String name, String forNull);
	
	Set<Object> keySet();
	
}
