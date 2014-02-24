package com.javaf.model;

import java.io.Serializable;
import java.util.Map;

import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.util.Entryc;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T>
 * @param <F>
 */
public class Pesquisa<T extends Serializable, F extends Serializable> extends AbstractDynamic {
	private static final long serialVersionUID = 6024991602871799488L;

	private F filtro;
	private Entryc<T> resultado;
	
	private int maximo;
	private int tipo;

	public Pesquisa(){

	}

	//@Embedded
	public F getFiltro() {
		return filtro;
	}

	public void setFiltro(final F filtro) {
		this.filtro = filtro;
	}

	//@Embedded
	public Entryc<T> getResultado() {
		if(null== resultado){
			resultado = new Entryc<T>();
		}
		return resultado;
	}

	public void setResultado(Entryc<T> resultado) {
		this.resultado = resultado;
	}

	/*@Mapping(
		input  = @Field(nome = OCBM.AUSENTE)
	)*/
	public int getMaximo() {
		return maximo;
	}

	public void setMaximo(int maximo) {
		this.maximo = maximo;
	}

	/*@Mapping(
		input  = @Field(nome = OCBM.AUSENTE)
	)*/
	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	@SuppressWarnings("unchecked")
	public void clear(){
		
		if(null!= getFiltro()){
			final F _filtro = getFiltro();
			final Class<?> _fclass = _filtro.getClass();
			
			final F _newFiltro = (F)reflection.newInstance(_fclass);
			setFiltro(_newFiltro);
		}
		
		if(null!= getResultado()){
			getResultado().clear();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void clear(final String...dontClear){
		
		if(null!= getFiltro()){
			final Map<String, Object> _noClear = reflection.valuesOf(this, dontClear);
			
			final F _filtro = getFiltro();
			final Class<?> _fclass = _filtro.getClass();
			
			final F _newFiltro = (F)UtilReflection.getInstance().newInstance(_fclass);
			setFiltro(_newFiltro);
			
			reflection.setValues(this, _noClear);
		}
		
		if(null!= getResultado()){
			getResultado().clear();
		}
		
	}
}
