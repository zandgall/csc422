package com.zandgall.csc422.petdb;

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
			System.out.printf("Pet Database%nl) List Pets%np) Add Pet%nn) Search Pets by Name%na) Search Pets by Age%nr) Remove Pet%nu) Update Pet%ne) exit%n> ");
			in = new Scanner(System.in);
			String choice = in.nextLine().toLowerCase().strip();
			switch(choice.charAt(0)) {
				case 'p':
					addPet();
					break;
				case 'n':
					searchByName();
					break;
				case 'a':
					searchByAge();
					break;
				case 'r':
					removePet();
					break;
				case 'u':
					updatePet();
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

	public static void searchByName() {
		System.out.printf("Name: ");
		String name = in.nextLine().toLowerCase();

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

		int num_pets = 0;
		for(int i = 0; i < pets.size(); i++) {
			if(!pets.get(i).name.toLowerCase().equals(name))
				continue;

			System.out.printf("|%-3d|\033[1m%-"+max_name_length+"s\033[0m|%-3d|%n", i, pets.get(i).name, pets.get(i).age);
			num_pets++;
		}

		System.out.println(split);
		System.out.println(pets.size() + " rows in set");
	}

	public static void searchByAge() {
		System.out.printf("Age: ");
		int age = in.nextInt();

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

		int num_pets = 0;
		for(int i = 0; i < pets.size(); i++) {
			if(pets.get(i).age != age)
				continue;

			System.out.printf("|%-3d|%-"+max_name_length+"s|\033[1m%-3d\033[0m|%n", i, pets.get(i).name, pets.get(i).age);
			num_pets++;
		}

		System.out.println(split);
		System.out.println(pets.size() + " rows in set");

	}

	public static void removePet() {
		System.out.printf("ID: ");
		int id = in.nextInt();
		if(id < 0 || id >= pets.size()) {
			System.out.printf("ID out of range! 0 <= ID < %d%n", pets.size());
			return;
		}
		
		pets.remove(id);
	}

	public static void updatePet() {
		System.out.printf("ID: ");
		int id = in.nextInt();
		if(id < 0 || id >= pets.size()) {
			System.out.printf("ID out of range! 0 <= ID < %d%n", pets.size());
			return;
		}

		// Clear buffer to read only after prompting user
		if(in.hasNextLine())
			in.nextLine();

		System.out.printf("Name: ");
		String name = in.nextLine();
		System.out.printf("Age: ");
		int age = in.nextInt();

		pets.get(id).name = name;
		pets.get(id).age = age;
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
