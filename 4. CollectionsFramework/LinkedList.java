package oops;

import java.util.LinkedList;

public class Linkedlist {

public static void main() {
	
	  LinkedList<String> list = new LinkedList<String>();
	  list.addFirst("a");
	  list.addFirst("is");
	  
	  
	  list.addFirst("this");
	  list.addLast("list");
	  
	  System.out.println(list);
	  System.out.println(list.size());
	  
	  for(int i =0;i<list.size();i++) {
		  System.out.println(list.get(i)+ "->");
	  }
	  System.out.println("null");
	  
	  list.removeFirst();
	  System.out.println(list);
	  
	  list.removeLast();
	  System.out.println(list);
	  
	  list.remove(0);
	  System.out.println(list);
  }
}
