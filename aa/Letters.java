package aa;


public class Letters {
	private String letter;
	private Letters down;
	private Words right;
	
	public Letters(String dataToAdd) {
		letter=dataToAdd;
		down=null;
		right=null;
	}
	public String getLetter() {return letter;}
	public void setLetter(String data) {this.letter=data;}
	public Letters getDown() {return down;}
	public void setDown(Letters down) {this.down=down;}
	public Words getRight() {return right;}
	public void setRight(Words right) {this.right=right;}
}
