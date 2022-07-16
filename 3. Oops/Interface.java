package oops;
interface Animals{
	public void walk();
}
interface Herbivore{
	
}

//multiple inheritance
class Horses implements Animals, Herbivore{
	public void walk() {
		System.out.println("Walks on 4 legs");
	}
}

public class Interface {
    public static void main() {
    	Horses horse = new Horses();
    	horse.walk();
    }
}
