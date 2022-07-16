


class Student{
	String name;
	int age;
	
	//No parameter Constructor
	Student(){
		System.out.println("Constructor called!");
	}
	
	
	//Parameterized constructor
	Student(String name, int age){
		System.out.println("Parametrized Constructor called!");
		this.name = name;
		this.age = age;
	}
	
	//Copy Constructor
	Student(Student s2){
		System.out.println("Copy Constructor called!");
		this.name = s2.name;
		this.age = s2.age;
	}
	
	public void printInfo() {
		System.out.println(this.name);
		System.out.println(this.age);
	}
	
}

public class Constructors{
    public static void main(String[] args){
    
    	Student s1 = new Student();
        s1.name= "Archana";
        s1.age= 24;
        s1.printInfo();
           
        Student s2 = new Student("Sachin",28);
        s2.printInfo();
        
        Student s3 = new Student(s1);
        s3.printInfo();
    }
 
}
