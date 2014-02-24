package com.javaf.javase.persistence.dialect;

import com.javaf.javase.persistence.Context;

public interface IDialect<T> {
	
	Context context();

	<E> T create(E entity);
	<E> T read(E entity);
	<E> T update(E entity);
	<E> T delete(E entity);
	
}
