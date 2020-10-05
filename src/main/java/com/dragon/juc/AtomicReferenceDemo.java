package com.dragon.juc;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
	public static void main(String[] args) {
		User z3 = new User("z3", 22);
		User l4 = new User("li4", 22);

		AtomicReference<User> atomicReference = new AtomicReference<User>();
		atomicReference.set(z3);

		System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" +
				atomicReference.get());
		System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" +
				atomicReference.get());
	}
}

class User{
	String userName;
	int age;
	public User(String userName, int age) {
		this.userName = userName;
		this.age = age;
	}
	@Override
	public String toString() {
		return "User(userName="+ userName + ", age" + age +")";
	}
}