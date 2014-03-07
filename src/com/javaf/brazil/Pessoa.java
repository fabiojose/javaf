package com.javaf.brazil;


import com.javaf.Constants.STRING;
import com.javaf.model.AbstractDynamic;

import cobol.ocbm.Embedded;
import cobol.ocbm.Mapping;

/**
 * Implementa a representação de pessoa, seja física ou jurídica.
 * @author fabiojm - Fábio José de Moraes
 */

public class Pessoa extends AbstractDynamic/* implements ISugestao */{
	private static final long serialVersionUID = 8223158512186851557L;

	private long club;
	private Federal documento;
	private String nome;
	
	public Pessoa(){
		setExceptions(Boolean.FALSE);
	}
	
	@Mapping
	public long getClub() {
		return club;
	}
	public void setClub(long club) {
		this.club = club;
	}
	
	@Embedded
	public Federal getDocumento() {
		if(null== documento){
			documento = new Federal();
		}
		return documento;
	}
	public void setDocumento(Federal documento) {
		this.documento = documento;
	}
	
	@Mapping
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getColuna0() {
		return getNome();
	}

	public String getColuna1() {
		return getDocumento().getString();
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (int) (club ^ (club >>> 32));
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
		final Pessoa other = (Pessoa) obj;
		if (club != other.club)
			return false;
		return true;
	}
	
	public String toString(){
		return nome + STRING.SPACE1 + getDocumento().toString();
	}
}

