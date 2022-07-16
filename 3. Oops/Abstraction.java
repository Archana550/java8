package oops;

abstract class Animal{
	abstract void walk();
		
	
}

class Horse extends Animal{
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
	   Animal animal = new Animal();
	   animal.walk();
   }
}
