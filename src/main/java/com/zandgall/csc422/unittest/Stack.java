package com.zandgall.csc422.unittest;

import java.util.ArrayList;
import java.util.List;

public class Stack<E> {
	private final static int DEFAULT_CAPACITY = 20;

	private List<E> array;
	private int size = 0;

	public Stack() {
		array = new ArrayList<E>(DEFAULT_CAPACITY);
		for(int i = 0; i < DEFAULT_CAPACITY; i++)
			array.add((E)null);
	}

	public Stack(int capacity) {
		array = new ArrayList<E>(capacity);
		for(int i = 0; i < capacity; i++)
			array.add((E)null);
	}

	public E pop() throws EmptyStackException {
		if(empty())
			throw new EmptyStackException();
		size--;
		return array.get(size);
	}

	public E push(E item) throws FullStackException {
		if(size >= array.size())
			throw new FullStackException();
		array.set(size, item);
		size++;
		return item;
	}

	public E peek() throws EmptyStackException {
		if(empty())
			throw new EmptyStackException();
		return array.get(size-1);
	}

	public boolean empty() {
		return size == 0;
	}

	public static class FullStackException extends Exception {
		public FullStackException() {}
	}

	public static class EmptyStackException extends Exception {
		public EmptyStackException() {}
	}
}
