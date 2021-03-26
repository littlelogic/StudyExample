package com.study.dataSave.serializable;

import java.io.Serializable;

public class Person extends Biology implements Serializable{

	/**
	 * 类的版本兼容判断
	 */
	private static final long serialVersionUID = 1L;

	private String name = "init";
	//todo 瞬时态，添加了该关键字的变量是不会被序列化出去的
	private transient int age;

	private Animal mAnimal = new Animal();
	private Animal nullAnimal;
	private Animal mAnimalArray[] = new Animal[2];
	//--不可循环Student mStudent = new Student();

	public void setDate(){
		mAnimalArray[0] = null;
		mAnimalArray[1] = new Animal();
		super.kind = "Person";
		super.live = "city";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age +
				"\n"+", mAnimal=" +mAnimal.toString() +
				"\n"+", nullAnimal=" +nullAnimal +
				"\n"+", mAnimalArray[0]=" +mAnimalArray[0] +
				"\n"+", super.kind=" +super.kind +
				"\n"+", super.live=" +super.live +
				"\n"+", mAnimal.kind=" +mAnimal.kind +
				"\n"+", mAnimal.live=" +mAnimal.live +
				"]";
	}
	
}
