package com.zandgall.csc422.a1;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static ArrayList<Pet> pets = new ArrayList<>();
	private static Scanner in;

	public static void main(String[] args){
		pets.add(new Pet("Kitty", 9));
		pets.add(new Pet("Bruno", 13));
		pets.add(new Pet("Boomer", 8));
		printTable();
		
		while(true) {
			System.out.printf("Pet Database%nl) List Pets%na) Add Pet%ne) exit%n> ");
			in = new Scanner(System.in);
			String choice = in.nextLine().toLowerCase().strip();
			switch(choice.charAt(0)) {
				case 'a':
					addPet();
					break;
				case 'l':
					printTable();
					break;
				case 'e':
					return;
			}
		}
	}

	public static void addPet() {
		System.out.printf("Name: ");
		String name = in.nextLine();
		System.out.printf("Age: ");
		int age = in.nextInt();
		pets.add(new Pet(name, age));
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
		
		for(int i = 0; i < pets.size(); i++)
			System.out.printf("|%-3d|%-"+max_name_length+"s|%-3d|%n", i, pets.get(i).name, pets.get(i).age);
	
		System.out.println(split);

		System.out.println(pets.size() + " rows in set");

	}

	private static class Pet {
		protected String name;
		protected int age;

		public Pet(String name, int age) {
			this.name = name;
			this.age = age;
		}
	}
}
