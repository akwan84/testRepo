import java.util.*;
public class Interface {
	public static void main(String[] args) {
		//simple interface
		Scanner sc = new Scanner(System.in);
		Solver s = new Solver();
		
		String permutation = "";
		System.out.println("Colours on the top side");
		String r1 = sc.next();
		String r2 = sc.next();
		permutation += "" + r1.charAt(0) + r1.charAt(1) + r2.charAt(1) + r2.charAt(0);
		
		System.out.println("Turn cube towards the right so the left side is now facing up \nColours on the top side");
		r1 = sc.next();
		r2 = sc.next();
		permutation += "" + r1.charAt(1) + r2.charAt(1) + r2.charAt(0) + r1.charAt(0);
	
		for(int i = 0; i < 3; i++) {
			System.out.println("Turn cube away from you so that the front side is now facing up \nColours on the top side");
			r1 = sc.next();
			r2 = sc.next();
			permutation += "" + r1.charAt(1) + r2.charAt(1) + r2.charAt(0) + r1.charAt(0);
		}
		
		System.out.println("Turn cube towards the right so the left side is now facing up \nColours on the top side");
		r1 = sc.next();
		r2 = sc.next();
		permutation += "" + r2.charAt(0) + r1.charAt(0) + r1.charAt(1) + r2.charAt(1);
		
		
		System.out.println("Reposition the cube so that the left side is in front, and bottom side is on top \nFinding Solution...");
		String a = s.solve(new Permutation(permutation, null, null));
		System.out.println("Solution: " + a);
		String time = s.getTime() + "";
		if(time.length() == 11) {
			System.out.println("Solution found in : " + time.substring(0, 2) + "." + time.substring(2, 4) + "s");
		}else {
			System.out.println("Solution found in : " + time.charAt(0) + "." + time.substring(1, 3) + "s");
		}
	}
}
