package aa;
public class Node {
	private Object data;
	private Node link;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data=data;
	}
	public Node getLink() {
		return link;
	}
	public void setLink(Node link) {
		this.link=link;
	}
	public Node(Object dataToAdd) {
		data=dataToAdd;
		link=null;
	}
}