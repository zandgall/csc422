package com.zandgall.csc422.a1;

import java.util.ArrayList;

public class Main {
	private static ArrayList<Pet> pets = new ArrayList<>();

	public static void main(String[] args){ 
		pets.add(new Pet(0, "Kitty", 9));
		pets.add(new Pet(1, "Bruno", 13));
		pets.add(new Pet(2, "Boomer", 8));
		printTable();
	}

	public static void printTable() {
		int max_name_length = 4;
		for(Pet p : pets)
			if(max_name_length < p.name.length())
				max_name_length = p.name.length();

		String split = "+----";
		for(int i = 0; i < max_name_length; i++)
			split += "-";
		split += "----+";

		System.out.println(split);

		System.out.printf("|%-3s|%-"+max_name_length+"s|%-3s|%n", "ID", "NAME", "AGE");

		System.out.println(split);
		
		for(Pet p : pets)
			System.out.printf("|%-3d|%-"+max_name_length+"s|%-3d|%n", p.id, p.name, p.age);
	
		System.out.println(split);

		System.out.println(pets.size() + " rows in set");

	}

	private static class Pet {
		protected String name;
		protected int id, age;

		public Pet(int id, String name, int age) {
			this.id = id;
			this.name = name;
			this.age = age;
		}
	}
}
