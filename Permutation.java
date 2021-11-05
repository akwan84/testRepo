
public class Permutation {
	private String perm;
	private String prevMove;
	private Permutation prevPerm;
	
	public Permutation(String perm, Permutation prevPerm, String prevMove) {
		this.perm = perm;
		this.prevPerm = prevPerm;
		this.prevMove = prevMove;
	}
	
	public Permutation getPrevPerm() {
		return prevPerm;
	}
	
	public String getPerm() {
		return perm;
	}
	
	public String getPrevMove() {
		return prevMove;
	}
}
