class Students{
	String name;
	static String school;
	
	public static void changeSchool() {
		school = "newschool";
	}
}
public class Static {
public static void main(String[]args) {
	Students.school ="ABC";
	Students student1 = new Students();
	student1.name = "tony";
	System.out.println(student1.school);
}
}
