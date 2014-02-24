package com.javaf.pattern.memento;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class TestMemento implements IOriginator, IPropertyOriginator {
	private IOriginatorSupport<TestMemento> support     = new OriginatorSupport<TestMemento>();
	private IPropertyOriginatorSupport<Object> psupport = new PropertyOriginatorSupport();
 	
	private int age;
	private String name;
	private Map<String, Integer> deadlines;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, Integer> getDeadlines() {
		if(null== deadlines){
			deadlines = new HashMap<String, Integer>();
		}
		return deadlines;
	}

	public void setDeadlines(Map<String, Integer> deadlines) {
		this.deadlines = deadlines;
	}

	public String toString(){
		return getName() + " - " + getAge() + " " + getDeadlines();
	}

	public void save() {
		support.save(this);
	}
	
	public void restore() {
		support.restore(this);
	}
	
	public void restore(final String property) {
		psupport.restore(this, property);
	}

	public void save(final String property) {
		psupport.save(this, property);
	}

	public static void main(String[] args) {
		final TestMemento _test = new TestMemento();
		_test.setAge(30);
		_test.setName("FABIO JM 30");
		_test.getDeadlines().put("ONE", 1);
		_test.save();
		System.out.println("SAVE 1 " + _test);
		
		_test.setAge(18);
		_test.setName("FABIO JM 18");
		_test.getDeadlines().put("TWO", 2);
		_test.save();
		System.out.println("SAVE 2 " + _test);
		
		_test.restore();
		System.out.println("RESTORE 1 " + _test);
		
		_test.restore();
		System.out.println("RESTORE 2 " + _test);
		
		_test.save("age");
		System.out.println("SAVE AGE 1 " + _test);
		
		_test.setAge(88);
		_test.save("age");
		System.out.println("SAVE AGE 2 " + _test);
		
		_test.setAge(40);
		_test.save("age");
		System.out.println("SAVE AGE 3 " + _test);
		
		_test.restore("age");
		System.out.println("RESTORE AGE 1 " + _test);
		
		_test.restore("age");
		System.out.println("RESTORE AGE 2 " + _test);
		
		_test.restore("age");
		System.out.println("RESTORE AGE 3 " + _test);
		
		_test.restore("age");
		System.out.println("RESTORE AGE 4 " + _test);
	}

}


