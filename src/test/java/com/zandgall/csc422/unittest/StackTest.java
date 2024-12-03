package com.zandgall.csc422.unittest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.zandgall.csc422.unittest.Stack.EmptyStackException;
import com.zandgall.csc422.unittest.Stack.FullStackException;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class StackTest {
	@Test
	@DisplayName("Stack initializes empty")
	void stackInitEmpty() {
		Stack<Integer> stack = new Stack<Integer>();
		assertTrue(stack.empty());
	}

	@Test
	@DisplayName("Popping empty Stack throws Exception")
	void stackThrowsEmptyPop() {
		Stack<Integer> stack = new Stack<Integer>();
		assertTrue(stack.empty());
		assertThrows(EmptyStackException.class, () -> {stack.pop();});
	}

	@Test
	@DisplayName("Peeking empty Stack throws Exception")
	void stackThrowsEmptyPeek() {
		Stack<Integer> stack = new Stack<Integer>();
		assertTrue(stack.empty());
		assertThrows(EmptyStackException.class, () -> {stack.peek();});
	}


	@ParameterizedTest
	@DisplayName("Pushing n-capacity stack n+1 times throws Full Exception")
	@ValueSource(ints = {0, 1, 2, 3})
	void stackThrowsFull(int n) {
		Stack<Integer> stack = new Stack<Integer>(n);
		assertTrue(stack.empty());
		for(int i = 0; i < n; i++) {
			try { stack.push(i); } catch (FullStackException e) { fail(e); }
		}
		assertThrows(FullStackException.class, () -> {stack.push(n);});
	}

	@Test
	@DisplayName("Stack initializes with 20 capacity")
	void stackIs30Capacity() {
		Stack<Integer> stack = new Stack<Integer>();
		assertTrue(stack.empty());
		for(int i = 0; i < 20; i++)
			try { stack.push(i); } catch (FullStackException e) { fail(e); }
		assertThrows(FullStackException.class, () -> {stack.push(30);});
	}

	@Test
	@DisplayName("Stack push returns what you push")
	void stackPushTest() {
		Stack<Integer> stack = new Stack<Integer>();
		assertTrue(stack.empty());
		for(int i = 8; i >= 0; i--)
			try {
				assertEquals(i, stack.push(i));
			} catch(FullStackException e) {
				fail(e);
			}
	}

	@Test
	@DisplayName("Stack peeks what you push")
	void stackPeeksPush() {
		Stack<Integer> stack = new Stack<Integer>();
		assertTrue(stack.empty());
		for(int a = 8; a >= 0; a--) {
			try {
				assertEquals(a, stack.push(a));
				assertEquals(a, stack.peek());
			} catch(FullStackException | EmptyStackException e) {
				fail(e);
			}
		}
	}

	@Test
	@DisplayName("Stack pops what you push")
	void stackPopsPush() {
		Stack<Integer> stack = new Stack<Integer>();
		assertTrue(stack.empty());
		for(int a = 8; a >= 0; a--) {
			try {
				assertEquals(a, stack.push(a));
				assertEquals(a, stack.pop());
			} catch(FullStackException | EmptyStackException e) {
				fail(e);
			}

		}
		assertTrue(stack.empty());
	}

	@ParameterizedTest
	@DisplayName("Push and Pop Stack n times results in empty stack")
	@ValueSource(ints = {1, 2, 4, 8, 16})
	void stackPushPopEmpty(int n) {
		Stack<Integer> stack = new Stack<Integer>();
		assertTrue(stack.empty());
		for(int i = 0; i < n; i++)
			try { assertEquals(i, stack.push(i)); } catch (FullStackException e) { fail(e); }
		for(int i = 0; i < n; i++)
			try { assertEquals(n - i - 1, stack.pop()); } catch (EmptyStackException e) { fail(e); }
		assertTrue(stack.empty());
	}
}
