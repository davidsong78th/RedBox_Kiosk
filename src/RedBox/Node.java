package RedBox;

public class Node<E> {
	String title;
	int available;
	int rental;
	Node<E> left;
	Node<E> right;
	
	public Node(String title) {
		this.title = title;
		this.available = 0;
		this.rental = 0;
		left = null;
		right = null;
	}
	public Node(String title, int available) {
		this.title = title;
		this.available = available;;
		this.rental = 0; 
		left = null;
		right = null;
	}
	public Node(String title, int available, int rental) {
		this.title = title;
		this.available = available;
		this.rental = rental;
		left = null;
		right = null;
	}

}
