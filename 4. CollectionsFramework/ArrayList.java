package oops;

import java.util.ArrayList;
import java.util.Collections;

public class Arraylist {
public static void main(String[] argv) {
	ArrayList list = new ArrayList();
	list.add(1);
	list.add(2);
	list.add(3);
	
	System.out.println(list);
	
	//get elements
	int element = (int) list.get(0);
	System.out.println(element);
	
	//add element in between
	list.add(1,4);
	System.out.println(list);
	
	//set element
	list.set(0,5);
	System.out.println(list);
	
	//delete element
	list.remove(3);
	System.out.println(list);
	
	//count size
	int size=list.size();
	System.out.println(size);
	
	//for loop
	for(int i =0;i<size;i++) {
		System.out.println(list.get(i));
	}
	
	//sorting
	Collections.sort(list);
	System.out.println(list);
	
	
}
}

