package com.javaf.model;

/**
 * Implementa a representação de chave e tipo de atributo
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T> Tipo genérico do atributo
 */
public final class Attribute<T> {

	private Object key;
	private Class<?> type;
	
	public Attribute(final Object key, final Class<?> type){
		this.key = key;
		this.type = type;
	}

	/**
	 * Obtem a chave que é o nome do atributo
	 * @return
	 */
	public Object getKey() {
		return key;
	}

	/**
	 * Obten o tipo do atributo
	 * @return
	 */
	public Class<?> getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((key == null) ? 0 : key.hashCode());
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
		final Attribute<?> other = (Attribute<?>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
	
}
