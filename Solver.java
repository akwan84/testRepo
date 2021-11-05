import java.util.*;
public class Solver {
	private final int[][] turnPatterns = 	{{0, 9, 10, 3, 4, 5, 6, 7, 8, 21, 22, 11, 15, 12, 13, 14, 2, 17, 18, 1, 20, 19, 16, 23}, //R
											{0, 19, 16, 3, 4, 5, 6, 7, 8, 1, 2, 11, 13, 14, 15, 12, 22, 17, 18, 21, 20, 9, 10, 23}, //R'
											{0, 21, 22, 3, 4, 5, 6, 7, 8, 19, 16, 11, 14, 15, 12, 13, 10, 17, 18, 9, 20, 1, 2, 23}, //R2
											{3, 0, 1, 2, 8, 9, 6, 7, 12, 13, 10, 11, 16, 17, 14, 15, 4, 5, 18, 19, 20, 21, 22, 23}, //U
											{1, 2, 3, 0, 16, 17, 6, 7, 4, 5, 10, 11, 8, 9, 14, 15, 12, 13, 18, 19, 20, 21, 22, 23},//U'
											{2, 3, 0, 1, 12, 13, 6, 7, 16, 17, 10, 11, 4, 5, 14, 15, 8, 9, 18, 19, 20, 21, 22, 23},//U2
											{0, 1, 5, 6, 4, 20, 21, 7, 11, 8, 9, 10, 3, 13, 14, 2, 16, 17, 18, 19, 15, 12, 22, 23},//F
											{0, 1, 15, 12, 4, 2, 3, 7, 9, 10, 11, 8, 21, 13, 14, 20, 16, 17, 18, 19, 5, 6, 22, 23},//F'
											{0, 1, 20, 21, 4, 15, 12, 7, 10, 11, 8, 9, 6, 13, 14, 5, 16, 17, 18, 19, 2, 3, 22, 23}}; //F2
	
	private final String[] turnTypes = {"R", "R'", "R2", "U", "U'", "U2", "F", "F'", "F2"};
	private final int[] indices1 = {3, 4, 5, 6, 7, 8}; //previous move was an R
	private final int[] indices2 = {0, 1, 2, 6, 7, 8}; //previous move was a U
	private final int[] indices3 = {0, 1, 2, 3, 4, 5}; //previous move was an F
	private final HashSet<String> SOLVED;
	private HashSet<String> seen;
	private long start = 0;
	private long end = 0;
	public Solver() {
		seen = new HashSet();
		SOLVED = new HashSet();
		
		SOLVED.add("WWWWOOOOGGGGRRRRBBBBYYYY");
		SOLVED.add("WWWWGGGGRRRRBBBBOOOOYYYY");
		SOLVED.add("WWWWRRRRBBBBOOOOGGGGYYYY");
		SOLVED.add("WWWWBBBBOOOOGGGGRRRRYYYY");
		SOLVED.add("YYYYOOOOBBBBRRRRGGGGWWWW");
		SOLVED.add("YYYYBBBBRRRRGGGGOOOOWWWW");
		SOLVED.add("YYYYRRRRGGGGOOOOBBBBWWWW");
		SOLVED.add("YYYYGGGGOOOOBBBBRRRRWWWW");
		SOLVED.add("GGGGWWWWOOOOYYYYRRRRBBBB");
		SOLVED.add("GGGGOOOOYYYYRRRRWWWWBBBB");
		SOLVED.add("GGGGYYYYRRRRWWWWOOOOBBBB");
		SOLVED.add("GGGGRRRRWWWWOOOOYYYYBBBB");
		SOLVED.add("BBBBOOOOWWWWRRRRYYYYGGGG");
		SOLVED.add("BBBBWWWWRRRRYYYYOOOOGGGG");
		SOLVED.add("BBBBRRRRYYYYOOOOWWWWGGGG");
		SOLVED.add("BBBBYYYYOOOOWWWWRRRRGGGG");
		SOLVED.add("OOOOYYYYOOOOWWWWBBBBRRRR");
		SOLVED.add("OOOOGGGGWWWWBBBBYYYYRRRR");
		SOLVED.add("OOOOWWWWBBBBYYYYGGGGRRRR");
		SOLVED.add("OOOOBBBBYYYYGGGGWWWWRRRR");
		SOLVED.add("RRRRBBBBWWWWGGGGYYYYOOOO");
		SOLVED.add("RRRRWWWWGGGGYYYYBBBBOOOO");
		SOLVED.add("RRRRGGGGYYYYBBBBWWWWOOOO");
		SOLVED.add("RRRRYYYYBBBBWWWWGGGGOOOO");
		SOLVED.add("RRRRBBBBWWWWGGGGYYYYOOOO");
	}	
	
	public String solve(Permutation perm) {
		start = System.nanoTime();
		Queue<Permutation> queue = new LinkedList();
		queue.add(perm);
		seen.add(perm.getPerm());
		
		while(!queue.isEmpty()) {
			Permutation curPerm = queue.poll();
			if(SOLVED.contains(curPerm.getPerm())) {
				end = System.nanoTime();
				return reconstructPath(curPerm);
			}
			Permutation[] possibleTurns = turns(curPerm);
			for(Permutation p : possibleTurns) {
				if(!seen.contains(p.getPerm())) {
					queue.add(p);
					seen.add(p.getPerm());
				}
			}
		}
		return null;
	}
	public String reconstructPath(Permutation solvedState) {
		Permutation cur = solvedState;
		String algorithm = "";
		while(cur.getPrevMove() != null) {
			algorithm = cur.getPrevMove() + ", " + algorithm;
			cur = cur.getPrevPerm();
		}
		return algorithm.substring(0, algorithm.length() - 2);
	}
	
	public Permutation[] turns(Permutation perm) {
		if(perm.getPrevMove() == null) {
			Permutation[] arr = new Permutation[9];
			for(int i = 0; i < turnPatterns.length; i++) {
				String s = "";
				for(int j : turnPatterns[i]) {
					s += perm.getPerm().charAt(j);
				}
				arr[i] = new Permutation(s, perm, turnTypes[i]);
			}
			return arr;
		}else if(perm.getPrevMove().equals("R") || perm.getPrevMove().equals("R'") || perm.getPrevMove().equals("R2")) {
			Permutation[] arr = new Permutation[6];
			for(int i = 0; i < 6; i++) {
				String s = "";
				for(int j : turnPatterns[indices1[i]]) {
					s += perm.getPerm().charAt(j);
				}
				arr[i] = new Permutation(s, perm, turnTypes[indices1[i]]);
			}
			return arr;
		}else if(perm.getPrevMove().equals("U") || perm.getPrevMove().equals("U'") || perm.getPrevMove().equals("U2")) {
			Permutation[] arr = new Permutation[6];
			for(int i = 0; i < 6; i++) {
				String s = "";
				for(int j : turnPatterns[indices2[i]]) {
					s += perm.getPerm().charAt(j);
				}
				arr[i] = new Permutation(s, perm, turnTypes[indices2[i]]);
			}
			return arr;
		}else{
			Permutation[] arr = new Permutation[6];
			for(int i = 0; i < 6; i++) {
				String s = "";
				for(int j : turnPatterns[indices3[i]]) {
					s += perm.getPerm().charAt(j);
				}
				arr[i] = new Permutation(s, perm, turnTypes[indices3[i]]);
			}
			return arr;
		}
	}
	
	public long getTime() {
		return end - start;
	}
}
//easy scramble for testing: WYYWOOOOGBBGRRRRGBBGYWWY
