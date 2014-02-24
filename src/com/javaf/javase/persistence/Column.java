package com.javaf.javase.persistence;

public class Column<T> {

	private int length;
	private String name;
	private boolean nullable;
	private Class<T> type;
	
	private int index;
	
	public Column(){
		setNullable(true);
		index = -1;
	}
	
	public Column(final int index, final Class<T> type){
		this();
		this.index = index;
		setType(type);
	}
	
	public Column(final String name, final Class<T> type){
		this();
		setName(name);
		setType(type);
	}
	
	public Column(final String name, final Class<T> type, final int length){
		this(name, type);
		setLength(length);
	}
	
	public Column(final String name, final Class<T> type, final boolean nullable){
		this(name, type);
		setNullable(nullable);
	}
	
	public Column(final String name, final Class<T> type, final int length, final boolean nullable){
		this(name, type, nullable);
		setLength(length);
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public Class<T> getType() {
		return type;
	}
	public void setType(Class<T> type) {
		this.type = type;
	}
	
	public int getIndex(){
		return index;
	}
	
	public String toString(){
		String _result = name;
		
		if(-1 != index){
			_result = "column[" + index + "]";
		}
		
		return _result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Column other = (Column) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
