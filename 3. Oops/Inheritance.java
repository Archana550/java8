package oops;

class Shape{
	String color;
	public void area() {
		System.out.println("Displays class");
	}
}
//single level inheritance
class Triangle extends Shape{
	public void area(int l, int h) {
		System.out.println((1/2)*l*h);
	}
}
//multi level inheritance
class EquilateralTriangle extends Triangle{
	public void area(int l, int h) {
		System.out.println((1/2)*l*h);
	}
	
}
//Hierarchical inheritance
class Circle extends Shape{
	public void area(int r) {
		System.out.println((3.14)*r*r);
	}
}

public class Inheritance {
      public static void main() {
    	  Triangle t1 = new Triangle();
    	  t1.color = "red";
      }
}
