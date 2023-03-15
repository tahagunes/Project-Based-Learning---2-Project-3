package aa;
public class Player {
	private int score;
	private String name;
	private String surname;
	private Player previous;
	private Player next;
	Player(String name,String surname){
		score=0;
		this.name=name;
		this.surname=surname;
	}
	Player(String name,int score){
		this.name=name;
		this.score=score;
		this.previous=null;
		this.next=null;
		
	}
	

	public Player getPrevious() {
		return previous;
	}
	public void setPrevious(Player previous) {
		this.previous = previous;
	}
	public Player getNext() {
		return next;
	}
	public void setNext(Player next) {
		this.next = next;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
}
