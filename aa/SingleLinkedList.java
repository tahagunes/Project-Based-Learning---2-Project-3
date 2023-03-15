package aa;
public class SingleLinkedList {
	private Node head;

	public SingleLinkedList() {
	
		this.head=null;
	}
	public void insert(Object dataToAdd) {
		if(head==null)head= new Node(dataToAdd);
		else {
			Node temp=head;
			while(temp.getLink()!=null)temp=temp.getLink();
			Node newnode= new Node(dataToAdd);
			temp.setLink(newnode);
		}		
	}
	public int size() {
		int count=0;
		if(head==null)System.out.println("");
		else {
			Node temp=head;
			while(temp!=null) {
				count++;
				temp=temp.getLink();
			}
		}
		return count;
	}
	public void display() {
		if(head==null)System.out.println("");
		else {
			Node temp=head;
			while(temp!=null) {
				System.out.print(" "+temp.getData());
				temp = temp.getLink();
			}
		}
	}
	public String getHeadElement() {
		if(head==null)return null;
		else {
			Node temp=head;
			return (String)temp.getData();
				
		}
	}
	public boolean ListHorizontalCheck() {	
		Node check = head;
		Node save=check;
		for (int i = 0; i < 6; i++) {
			check=save;
			int save1=0,save2=0,save3=0;			
			if(check!=null)save1=(int)check.getData();
			else break;
			check=check.getLink();
			save=check;
			if(check!=null)save2=(int)check.getData();
			else break;
			check=check.getLink();
			if(check!=null)save3=(int)check.getData();
			else break;
			if((save3==save2+1&&save2==save1+1)||save1==save2+1&&save2==save3+1)return true;
		}
		return false;
	}
	public int ListVerticalData() {
		Node check = head;
		int LHD=(int)check.getData();		
		return LHD;
	}
	public void delete() {
		if(head==null);
		else {
			head.setData(null);
			head=head.getLink();
		}
	}
	public void SearchandDel(SingleLinkedList SLL,String delword) {
		if(head==null) {}
		else {
			Node temp=head;
			while(temp!=null) {
				if(temp.getData().equals(delword))temp.setData("completed");
				temp = temp.getLink();
			}
		}
	}
}
