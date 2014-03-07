package com.javaf.model;

import java.io.Serializable;

import com.javaf.Constants.STRING;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class PairDTO extends AbstractDynamic implements Serializable, IPair {
	private static final long serialVersionUID = -860188660086323425L;

	private long codigo;
	private String string;
	
	public PairDTO(){
		
	}
	
	public PairDTO(final long codigo, final String string){
		setCodigo(codigo);
		setString(string);
	}

	//@Mapping
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	//@Mapping
	public String getString() {
		if(null== string){
			string = STRING.EMPTY;
		}
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (int) (codigo ^ (codigo >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PairDTO other = (PairDTO) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}
	
	
}
