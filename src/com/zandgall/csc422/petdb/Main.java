package com.zandgall.csc422.petdb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	private static ArrayList<Pet> pets = new ArrayList<>();
	private static Scanner in;

	public static void main(String[] args){
		pets.add(new Pet("Kitty", 9));
		pets.add(new Pet("Bruno", 13));
		pets.add(new Pet("Boomer", 8));
		printTable();
		
		while(true) {
			System.err.flush(); // Ensure error messages don't overlap with menu.
			System.out.printf("Pet Database%nl) List Pets%np) Add Pet%nn) Search Pets by Name%na) Search Pets by Age%nr) Remove Pet%nu) Update Pet%ns) Save to File%no) Open from file%ne) exit%n> ");
			in = new Scanner(System.in);
			String choice = in.nextLine().toLowerCase().strip();
			if(choice.length() < 1 || choice.isEmpty() || choice.isBlank())
				continue;
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
				case 's':
					saveToFile();
					break;
				case 'o':
					loadFromFile();
					break;
				case 'e':
					return;
			}
		}
	}

	// Add pet to database provided given input
	public static void addPet() {
		if(pets.size() >= 5) {
			System.err.println("Can't have more than 5 pets!");
			return;
		}
			
		if(!addPet(getPetData()))
			System.err.println("Could not add pet.");
	}

	// Print out a table of pets with the user-provided name
	public static void searchByName() {
		System.out.printf("Name: ");
		String name = in.nextLine().toLowerCase();

		ArrayList<Integer> petsView = new ArrayList<>();
		for(int i = 0; i < pets.size(); i++)
			if(pets.get(i).name.equals(name))
				petsView.add(i);

		printTable(petsView, "", "\033[1m");
	}

	// Print out a table of pets with the user-provided age
	public static void searchByAge() {
		System.out.printf("Age: ");

		int age = -1;
		try {
			age = in.nextInt();
		} catch(InputMismatchException e) {
			System.err.println("Expected a whole number");
		}

		ArrayList<Integer> petsView = new ArrayList<>();
		for(int i = 0; i < pets.size(); i++)
			if(pets.get(i).age == age)
				petsView.add(i);

		printTable(petsView, "", "\033[1m");
	}

	// Remove pet with user-provided ID
	public static void removePet() {
		int p = getPetID();
		if(p!=-1)
			pets.remove(p);
	}

	// Update pet with user-provided ID to user provided pet data
	public static void updatePet() {
		int update = getPetID();
		if(update == -1)
			return;
		Pet newData = getPetData();
		if(newData == null)
			return;

		pets.set(update, newData);
	}

	// Print out every pet
	public static void printTable() {
		printTable(IntStream.range(0, pets.size()).boxed().collect(Collectors.toList()));
	}

	public static void saveToFile() {
		System.out.printf("Filename: ");
		File f = new File(in.nextLine());
		PrintWriter w = null;
		try {
			w = new PrintWriter(f);
		} catch(FileNotFoundException e) {
			System.err.println("Invalid Filename! Did not save.");
			return;
		}

		w.println(pets.size());
		for(Pet p : pets) {
			w.println(p.name);
			w.println(p.age);
		}
		w.close();
	}

	public static void loadFromFile() {
		System.out.printf("Filename: ");
		File f = new File(in.nextLine());
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch(FileNotFoundException e) {
			System.err.println("Could not find file! Did not load.");
			return;
		}

		// Save a backup of the current database to fall back on
		ArrayList<Pet> backup = (ArrayList<Pet>)pets.clone();
		pets.clear();
		try {

			// Load pets as per decided structure
			int numPets = Integer.parseInt(s.nextLine());
			
			if(numPets > 5)
				throw new RuntimeException("Cannot load more than 5 pets.");

			for(int i = 0; i < numPets; i++) {
				Pet p = new Pet(s.nextLine(), Integer.parseInt(s.nextLine()));

				// Validate each pet
				if(p.age < 1 || p.age > 20)
					throw new RuntimeException("Pet #" + (i+1) + " could not be added. Invalid age.");

				addPet(p);
			}

		} catch(RuntimeException e) {
			// If any errors are caught, fall back to backup
			System.err.printf("Error while loading \"%s\", did not load.%n", f.getName());
			System.err.println(e.getMessage());
			pets = backup;
		}

		s.close();
	}

	/*** Secondary functions ***/

	private static void printTable(List<Integer> petsView) { printTable(petsView, "", ""); }

	// Print out given list of pets, with nameStyle and ageStyle arguments capable of highlighting names and ages for searching
	private static void printTable(List<Integer> petsView, String nameStyle, String ageStyle) {
		// Find what the longest pet name is to make sure the table is wide enough
		int max_name_length = 4;
		for(int i : petsView)
			if(max_name_length < pets.get(i).name.length())
				max_name_length = pets.get(i).name.length();

		// Create a string that is used in between major table rows
		String split = "+----";
		for(int i = 0; i < max_name_length; i++)
			split += "-";
		split += "----+";

		// Print header of table
		System.out.println(split);
		System.out.printf("|%-3s|%-"+max_name_length+"s|%-3s|%n", "ID", "NAME", "AGE");
		System.out.println(split);
		
		// Print out every pet
		for(int i : petsView)
			System.out.printf("|%-3d|%s%-"+max_name_length+"s\033[0m|%s%-3d\033[0m|%n", i, nameStyle, pets.get(i).name, ageStyle, pets.get(i).age);
	
		System.out.println(split);
		System.out.println(petsView.size() + " rows in set");
		System.out.println();
	}

	// Return a new Pet based on user provided data, or null if invalid
	private static Pet getPetData() {
		in = new Scanner(System.in);
		System.out.printf("Name: ");
		String name = in.nextLine();
		System.out.printf("Age: ");
		int age = -1;
		try {
			age = in.nextInt();
		} catch(InputMismatchException e) {
			System.err.println("Expected a whole number for age");
			return null;
		}

		if(age < 1 || age > 20) {
			System.err.println("Invalid Age, needs to be in range [1, 20]");
			return null;
		}

		return new Pet(name, age); // Thankful I'm not working in C right now
	}

	// Return Pet based on user provided ID, checking input to ensure valid ID
	private static int getPetID() {
		System.out.printf("ID: ");
		int id = -1;
		try {
			id = in.nextInt();
		} catch(InputMismatchException e) {
			System.err.println("Expected whole number for ID");
			return -1;
		}

		if(id < 0 || id >= pets.size()) {
			System.err.printf("ID out of range! 0 <= ID < %d%n", pets.size());
			return -1;
		}

		return id;
	}

	private static boolean addPet(Pet p) {
		if(p == null)
			return false;
		return pets.add(p);
	}

	/* Store Pet information (String name, int age) */
	private static class Pet {
		protected String name;
		protected int age;

		public Pet(String name, int age) {
			this.name = name;
			this.age = age;
		}
	}
}
