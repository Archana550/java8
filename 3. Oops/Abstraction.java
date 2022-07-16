package oops;

abstract class Animal{
	//abstract methods
	abstract void walk();
	
	//non-abstract methods
	public void eats() {
		System.out.println("Eats");
	}
	
	//constructor
	Animal(){
		System.out.println("You are creating an animal");
	}
}

class Horse extends Animal{
	
	Horse(){
		System.out.println("You are creating an Horse");
	}
	public void walk() {
		System.out.println("Walks on 4 legs");
	}
}

class Chicken extends Animal{
	public void walk() {
		System.out.println("Walks on 2 legs");
	}
}

public class Abstraction {
   public static void main() {
	   Horse horse = new Horse();
	   horse.walk();
	   horse.eats();
	  // Animal animal = new Animal();
	  // animal.walk();
   }
}
