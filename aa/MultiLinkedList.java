package aa;
public class MultiLinkedList {
	private Letters head;
	public MultiLinkedList() {
		this.head=null;
	}
	public void AddLetter(String dataToAdd) {
		if(head==null) {
			Letters newnode =  new Letters(dataToAdd);
			head=newnode;
		}
		else {
			Letters temp=head;
			while(temp.getDown()!=null)temp=temp.getDown();
			Letters newnode=new Letters(dataToAdd);
			temp.setDown(newnode);
			}
	}
	public void addWord(String letter,String word) {
		if(head==null)System.out.println("ADD A LETTER BEFORE WORD");
		else {
			Letters temp=head;
			while(temp!=null)
			{
				if(letter.equals(temp.getLetter())) {
					Words temp2=temp.getRight();
					if(temp2==null) {
						Words newnode= new Words(word);
						temp.setRight(newnode);
					}
					else {
						while(temp2.getNext()!=null)temp2=temp2.getNext();
						Words newnode= new Words(word);
						temp2.setNext(newnode);
					}
				}
				temp=temp.getDown();
			}
		}
	}
	public int sizeLetters() {
		int count=0;
		if(head==null)System.out.println("list is empty");
		else {
			Letters temp=head;
			while(temp!=null) {
				count++;
				temp=temp.getDown();
			}
		}
		return count;
	}
	
	public void display() {
		if(head==null)System.out.println("list is empty");
		else {
			Letters temp=head;
			while(temp!=null) {//ilk kolonu yazdırmak için
				System.out.print(temp.getLetter()+" => ");
				Words temp2=temp.getRight();
				while(temp2!=null)//diğer kolonları yazdırmak için
				{
					System.out.print(" "+temp2.getWord()+" ");
					temp2=temp2.getNext();
				}
				temp=temp.getDown();
				System.out.println();
			}
			
		}
	}
	public String[] LETTERwords(MultiLinkedList MLL,String letter) {
		String[] LETTERSword=new String[MLL.sizeLetters()];
		int count=0;
		if(head==null)return LETTERSword;
		else {
			Letters temp=head;
			while(temp!=null) {
				if(temp.getLetter().equals(letter)) {
					Words temp2=temp.getRight();
					while(temp2!=null)//diğer kolonları yazdırmak için
					{
						LETTERSword[count]=temp2.getWord();
						count++;
						temp2=temp2.getNext();
					}
				}
				temp=temp.getDown();
			}
			return LETTERSword;
		}
	}
	public void SearchAndDeleteWord(MultiLinkedList MLL,String letter,String word) {
		String[] LETTERSword=new String[MLL.sizeLetters()];
		int count=0;
		if(head==null) {
			//do nothing
		}
		else {
			Letters temp=head;
			while(temp!=null) {
				if(temp.getLetter().equals(letter)) {
					Words temp2=temp.getRight();
					while(temp2!=null)//diğer kolonları yazdırmak için
					{
						if(temp2.getWord()!=null&&temp2.getWord().equals(word)) {
							temp2.setWord(null);
							break;
						}
						temp2=temp2.getNext();
					}
				}
				temp=temp.getDown();
			}

		}
	}
	public int getLetterCount(MultiLinkedList MLL) {
		String[] LETTERSword=new String[MLL.sizeLetters()];
		int count=0;
		if(head==null)return count;
		else {
			Letters temp=head;
			while(temp!=null) {
					Words temp2=temp.getRight();
					while(temp2!=null)//diğer kolonları yazdırmak için
					{
						count++;
						temp2=temp2.getNext();
					}
				temp=temp.getDown();
			}
			return count;
		}
	}
}

