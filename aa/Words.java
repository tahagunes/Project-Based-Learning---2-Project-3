package aa;

public class Words {
	private String word;
	private Words next;
	
	public Words(String dataToAdd) {
		word=dataToAdd;
		next=null;
	}
	public String getWord() {return word;}
	public void setWord(String data){this.word=data;}
	public Words getNext() {return next;}
	public void setNext(Words next) {this.next=next;}
}