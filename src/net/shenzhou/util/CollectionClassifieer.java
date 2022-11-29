package net.shenzhou.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CollectionClassifieer {

	public static String classify(Set<?>s){
		return "Set";
	}
	
	public static String classify(List<?>lst){
		return "Llist";
	}
	
	public static String classify(Collection<?>c){
		return "Unknown Collection";
	}
	
	
	
	public static void main(String[] args) {
//		Collection<?>[] collections = {
//				new HashSet<String>(),
//				new ArrayList<BigInteger>(),
//				new HashMap<String,String>().values()
//				
//		};
//		for (Collection<?> c : collections) {
//			System.out.println(classify(c));
//		}
		
		Set<Integer> set = new TreeSet<Integer>();
		List<Integer> list = new ArrayList<Integer>();
		for (int i = -3; i < 3; i++) {
			set.add(i);
			list.add(i);
		}
		for (int i = 0; i < 3; i++) {
			set.remove(i);
//			list.remove(i);
			list.remove((Integer)i);
		}
		System.out.println(set+"   "+list);
				
		
		
	}
}
