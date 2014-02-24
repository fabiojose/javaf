package com.javaf.javase.persistence;

public interface IResult {

	<T> T get(Column<T> column);
	<T> T get(Column<T> column, T forNull);
	
}
