package com.javaf.javase.util;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.javaf.Constants.INTEGER;
import com.javaf.Constants.REFLECTION;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.pattern.Breathable;
import com.javaf.pattern.IFilter;



/**
 * Encapsula uma coleção de objetos, facilitando a interação com Tables, Grids, Combos, etc.
 * 
 * @author fabiojm - Fábio José de Moraes
 * @param <T> Tipo armazenado na coleção de objetos
 * @see List
 */
public class Entryc<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 7069149776559644586L;

	private String string;
	
	private int index;
	private int offset;
	private List<T> entries;
	private List<T> original;
	
	private List<Integer> selectedIndexes;
	
	private boolean filterOn;
	private IFilter<T> filter;
	
	public Entryc(){
		original = new ArrayList<T>();
		offset = INTEGER._0;
	}
	
	public Entryc(final IFilter<T> filter){
		this();
		
		this.filter   = filter;
		this.filterOn = Boolean.TRUE;
	}
	
	public Entryc(final List<T> entries){
		this();
		setEntries(new ArrayList<T>( entries));
	}
	
	@SuppressWarnings("unchecked")
	public Entryc(final Class<T> clazz){
		this();
		
		if(Enum.class.isAssignableFrom(clazz)){
			try{
				final Method _values = clazz.getMethod(REFLECTION.VALUES_METHOD, new Class[]{});
				if(null!= _values){
					final Object _returned = _values.invoke(clazz, new Object[]{});
					
					if(null!= _returned){
						final Object[] _array = (Object[])_returned;
						for(Object _object : _array){
							add((T)_object);
						}
					}
				}
			}catch(NoSuchMethodException _e){
			}catch(InvocationTargetException _e){
			}catch(IllegalAccessException _e){
			}
			
		} else {
			
			add(UtilReflection.getInstance().newInstance(clazz));
			
		}
	}
	
	public int getOffset(){
		return offset;
	}
	public void setOffset(final int offset){
		this.offset = offset;
	}
	
	/**
	 * Interventor para o metodo add ou addAll que delegará para original e qualquer outro para entries
	 * @author fabiojm
	 */
	private class Invocation implements InvocationHandler{

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			
			Object _result = null;
			
			if(method.getName().equals(REFLECTION.METHOD_ADD)
					|| method.getName().equals(REFLECTION.METHOD_ADD_ALL)){
				
				//add vai para original
				_result = method.invoke(original, args);
				
			} else if(method.getName().equals(REFLECTION.METHOD_BREATH)){
				
				//respirar e atualizar a lista de entradas.
				getEntries();
				
			} else {
				//qualquer outro vai para entries
				_result = method.invoke(entries, args);
				
			}
			
			return _result;
		}
		
	};
	private InvocationHandler invocation = new Invocation();
	private Object proxy;

	@SuppressWarnings("unchecked")
	public List<T> getEntries() {
		if(null== entries){
			entries = new ArrayList<T>();
		}
		
		List<T> _result = entries;
		
		entries.clear();
		
		//filtrar entradas que seráo retornadas
		if(null!= filter && filterOn){
			
			for(T _t : original){
				if(filter.applyTo(_t)){
					entries.add(_t);
				}
			}
				
		} else {
			//nao filtrar, retornar todas
			entries.addAll(original);
		}
		
		//configurar proxy
		if(null== proxy){
			proxy = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{ List.class, Breathable.class }, invocation);
		}
		
		//retorna o proxy ao inves da lista
		_result = (List<T>)proxy;
		
		return _result;
	}

	public void setEntries(List<T> entries) {
		this.entries = entries;
		
		//copia interna das entradas originais
		original.addAll(entries);
	}
	
	public void add(final T value){
		
		if(!original.contains(value)){
			original.add(value);
		}
	}
	
	public void addAll(final List<T> values){
		
		for(T _value : values){
			add(_value);
		}
	}

	public List<Integer> getSelectedIndexes() {
		if(null== selectedIndexes){
			selectedIndexes = new ArrayList<Integer>();
		}
		return selectedIndexes;
	}

	public void setSelectedIndexes(List<Integer> selectedIndexes) {
		this.selectedIndexes = selectedIndexes;
	}
	
	public T getSelectedValue() throws IllegalStateException {
		
		T _result = null;
		
		if(getSelectedIndexes().size() > INTEGER._0 && getEntries().size() > INTEGER._0){
			if(getSelectedIndexes().size() == INTEGER._1 || getEntries().size() == INTEGER._1){
				_result = getEntries().get(getSelectedIndexes().get(INTEGER._0));
			}
		}
		
		return _result;
	}
	
	public void noSelect(){
		getSelectedIndexes().clear();
	}
	
	public void setSelectedValue(final T value){
		
		int _index = INTEGER._0;
		for(T _entry : getEntries()){
			
			if(_entry.equals(value)){
				this.index = _index;
				
				getSelectedIndexes().clear();
				getSelectedIndexes().add(this.index + offset);
				
				break;
			}
			_index++;
		}
	}
	
	public List<T> getSelectedValues(){
		
		final List<T> _result = new ArrayList<T>();
		
		for(Integer _index : getSelectedIndexes()){
			_result.add(getEntries().get(_index - offset));
		}
		return _result;
	}

	public int getSelectedIndex(){
		return index + offset;
	}
	
	public void clear(){
		original.clear();
		getEntries().clear();
		getSelectedIndexes().clear();
		this.index = INTEGER._1NEG;
	}
	
	public boolean isEmpty(){
		return getEntries().isEmpty();
	}

	public String getString(){
		return string;
	}
	public void setString(final String string){
		this.string = string;
	}

	
	public void removeItem(int index){
		final T _removed = getEntries().remove(index - offset);
		this.index = INTEGER._0;
		
		//remover de original
		final int _index = original.indexOf(_removed);
		original.remove(_index);
	
	}
	
	/**
	 * Remove a entrada selecionada e a retorna
	 * @return
	 * @see #getSelectedValue()
	 * @see #getSelectedIndex()
	 */
	public T remove() throws IllegalStateException{
		
		final T _result = getSelectedValue();
		
		getEntries().remove(getSelectedIndex());
		getSelectedIndexes().clear();
		
		//remover de original
		final int _index = original.indexOf(_result);
		original.remove(_index);
		
		return _result;
	}
	
	public T remove(final T o){
		T _result = null;
		
		final int _index = getEntries().indexOf(o);
		if(_index >= INTEGER._0){
			_result = getEntries().get(_index);
			getEntries().remove(_index);
			
			//remover do original
			original.remove(original.indexOf(o));
		}
		
		return _result;
	}
	
	public Set<T> remove(final Set<T> toRemove) {
		final Set<T> _result = new HashSet<T>();
		
		if(null!= toRemove){
			for(T _toRemove : toRemove){
				_result.add(remove(_toRemove));
			}
		}
		
		return _result;
	}
	
	/**
	 * Remove todas as entradas selecionadas e as retona
	 * @return
	 * @see #getSelectedValues()
	 */
	public List<T> removeAll() {
		
		final List<T> _result = new ArrayList<T>();
		for(T _t : getSelectedValues()){
			_result.add(_t);
			getEntries().remove(_t);
			
			
			//remover de original
			original.remove(original.indexOf(_t));
		}
		
		return _result;
	}
	
	public int size(){
		return getEntries().size();
	}

	public int getSize(){
		return size();
	}
	
	public boolean contains(final T t){
		return getEntries().contains(t);
	}
	
	public int indexOf(T value){
	
		int _result = INTEGER._1NEG;
		int _index  = INTEGER._0;
		
		for(T _value : getEntries()){
			if(_value.equals(value)){
				_result = _index + offset;
				break;
			}
			
			_index++;
		}
		
		return _result;
	}

	public IFilter<T> getFilter() {
		return filter;
	}

	public void setFilter(IFilter<T> filter) {
		this.filter = filter;
	}

	public void setFilterOn(final boolean on){
		this.filterOn = on;
	}

	public boolean isFilterOn(){
		return filterOn;
	}
	
	public boolean isSelected(){
		return getSelectedIndexes().size() > INTEGER._0;
	}
	
	public T get(final T t){
	
		T _result = null;
		for(T _value : getEntries()){
			if(_value.equals(t)){
				_result = _value;
				break;
			}
		}
		return _result;
	}
	
	public T get(final int index){
		return getEntries().get(index - offset);
	}

	public T getFirst(){
		T _result = null;
		
		if(!getEntries().isEmpty()){
			_result = getEntries().get(INTEGER._0);
		}
		
		return _result;
	}
}
