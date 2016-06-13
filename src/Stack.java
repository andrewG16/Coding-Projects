import java.util.NoSuchElementException;
public class Stack<T> {
	
	public class Node<G>{
		G data;
		Node<G> next;
		
		public Node(G data, Node<G> next){
			this.data = data;
			this.next = next;
		}
	}
	
	Node<T> front;
	int size;
	
	public Stack(){
		front =  null;
		size = 0;
	}
	
	public void push (T item){
		front = new Node <T>(item, front);
		size++;
	}
	
	public T pop() throws NoSuchElementException{
		if(front == null){
			throw new NoSuchElementException();
		}
		else{
			T temp = front.data;
			front = front.next;
			size--;
			return temp;
		}
	}
	
	public T peek(){
		if(front == null){
			return null;
		}
		else{
			return front.data;
		}
	}
	
	public boolean isEmpty(){
		if(front == null){
			return true;
		}else{
			return false;
		}
	}
	/***
	 * The following Method just Prints the Data in ONE entire String
	 */
	public String toString(){
		String string = "Stack: ";
		for(Node<T> ptr = front; ptr != null; ptr = ptr.next){
			string += ptr.data + " ";
			
		}
		return string;
	}
	
	public int Size(){
		return size;
	}
	
	public static void main(String args[]){
		
		Stack<String> s = new Stack();
		s.push("Out");
		s.push("First");
		s.push("In");
		s.push("Last");
		
		System.out.println(s.toString());
		s.pop();
		System.out.println(s.toString());
		
		Stack<Integer> i = new Stack();
		for(int k = 10; k>=1; k--){
			i.push(k);
		}
		
		System.out.println(i.toString());
		i.pop();
		System.out.println(i.toString());

	}

}
