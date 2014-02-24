package com.javaf.brazil;

import java.io.Serializable;

import cobol.ocbm.Mapping;

import com.javaf.Constants.INTEGER;
import com.javaf.Constants.LONG;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.UtilString;
import com.javaf.javase.persistence.annotation.Transient;
import com.javaf.javase.persistence.annotation.TransientPolicy;
import com.javaf.javase.text.UtilFormat;

/**
 * Implementa a representação de documento, seja Pessoa Física ou Jurídica.
 * 
 * @author fabiojm - Fábio José de Moraes
 */
public final class Federal implements Serializable {
	private static final long serialVersionUID = 5625785710402868915L;

	private final UtilFormat format   = UtilFormat.getInstance();
	private final BrazilFormat brazil = BrazilFormat.getInstance();
	private final UtilString string   = UtilString.getInstance();
	
	private long numero;
	private int corpo;
	private int filial;
	private int controle;
	
	private PessoaType tipo;
	
	@Transient(TransientPolicy.READ)
	public long getNumero() {
		if(LONG._0 == numero){
			if(PessoaType.JURIDICA.equals(getTipo())){
				numero = format.toCnpj( format.toCnpj(getCorpo(), getFilial(), getControle()) );
			} else {
				numero = format.toCpf( format.toCpf(getCorpo(), getControle()) );
			}
		}
		
		return numero;
	}

	public void setNumero(final long numero) {
		this.numero = numero;
		
		corpo = INTEGER._0;
		filial = INTEGER._0;
		controle = INTEGER._0;
	}
	
	public void setNumeroString(final String numero){
		setNumero(format.toLong( string.remove(numero, STRING.CPF_CNPJ_REMOVE) ));
	}

	@Mapping
	public PessoaType getTipo() {
		return tipo;
	}

	public void setTipo(final PessoaType tipo) {
		this.tipo = tipo;
	}
	
	public void setCorpo(final int corpo){
		this.corpo = corpo;
		numero = LONG._0;
	}
	
	@Mapping
	public int getCorpo(){
		if(INTEGER._0 == corpo){
			corpo = brazil.toCorpo(numero, getTipo());
		}
		return corpo; 
	}
	
	public void setFilial(final int filial){
		this.filial = filial;
		numero = LONG._0;
		
		if(INTEGER._0 == filial){
			setTipo(PessoaType.FISICA);
		} else {
			setTipo(PessoaType.JURIDICA);
		}
	}
	
	@Mapping
	public int getFilial(){
		if(INTEGER._0 == filial && PessoaType.JURIDICA.equals(getTipo())){
			filial = brazil.toFilial(numero, getTipo());
		}
		return filial;
	}
	
	public void setControle(final int controle){
		this.controle = controle;
		numero = LONG._0;
	}
	
	@Mapping
	public int getControle(){
		if(INTEGER._0 == controle){
			controle = brazil.toControle(numero, getTipo());
		}
		return controle;
	}

	public String getString(){
		
		String _result = STRING.EMPTY;
		
		if(null!= getTipo()){
			if(PessoaType.JURIDICA.equals(getTipo())){
				
				_result = format.toCnpj(getNumero());
				
			} else {
			
				_result = format.toCpf(getNumero());
				
			}
		}
		
		return _result;
	}
	
	public String toString(){
		return getString();
	}
}
