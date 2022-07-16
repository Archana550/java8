
class Pen{
 //data
  String color;
  String type; //ballpoint,gel
  
  //members
  public void write(){
       System.out.println("Wirtting");
  }
  public void printColor(){
	  System.out.println(this.color);
	  }
  
}

public class ClassAndObject{
    public static void main(String[] args){
    
    
      Pen pen1 = new Pen();
      pen1.color = "blue";
      pen1.type = "gel";
      pen1.write();
      
      System.out.println(pen1.color);
      
      Pen pen2 = new Pen();
      pen2.color = "green";
      pen2.type = "ballpoint";
      pen1.write();
      pen1.printColor();
      pen2.printColor();
    }
 
}
