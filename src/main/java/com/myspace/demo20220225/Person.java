package com.myspace.demo20220225;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class Person implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private java.lang.String name;
	private long age;
	private java.lang.Boolean canBuy;

	public Person() {
	}

	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public long getAge() {
		return this.age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public java.lang.Boolean getCanBuy() {
		return this.canBuy;
	}

	public void setCanBuy(java.lang.Boolean canBuy) {
		this.canBuy = canBuy;
	}

	public Person(java.lang.String name, long age, java.lang.Boolean canBuy) {
		this.name = name;
		this.age = age;
		this.canBuy = canBuy;
	}

	@Override
	public String toString() {
		return "Person [age=" + age + ", canBuy=" + canBuy + ", name=" + name + "]";
	}

}