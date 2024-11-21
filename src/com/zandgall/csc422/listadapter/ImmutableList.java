package com.zandgall.csc422.listadapter;

public class ImmutableList implements IImmutableList {

	private int[] array;

	public ImmutableList(int[] array) {
		this.array = array;
	}

	public ImmutableList(IImmutableList list) {
		int size = list.size();
		array = new int[size];
		for(int i = 0; i < size; i++)
			array[i] = list.get(i);
	}

	public int get(int index) {
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		return array[index];
	}

	public IImmutableList concat(IImmutableList list) {
		ImmutableList out = new ImmutableList(new int[size() + list.size()]);

		for(int i = 0; i < size(); i++)
			out.array[i] = get(i);
		for(int i = 0; i < list.size(); i++)
			out.array[i + size()] = list.get(i);

		return out;
	}

	public int size() {
		return array.length;
	}

	public String toString() {
		String out = "[";
		for(int i : array)
			out += i + ", ";
		out = out.substring(0, out.length()-2) + "]";
		return out;
	}
}
