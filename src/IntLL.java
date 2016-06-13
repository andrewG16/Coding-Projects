
public class IntLL {
	public class IntNode{
		int data;
		IntNode next;
		public IntNode(int data, IntNode next){
			this.data = data;
			this.next = next;
		}
	}
	
	IntNode front;
	public IntLL(){
		front = null;
	}
	
	public void  addToFront(int data){
		front  = new IntNode(data, front);
	}
	
	public void printll(){
		IntNode ptr = front;
		while (ptr != null){
			System.out.print(ptr.data);
			ptr = ptr.next;
		}
		
		System.out.println("*** End of List ***");
	}
	
	public void delete(int target){
		IntNode ptr = front;
		IntNode prev = null;
		
		while(ptr != null && ptr.data != target){
			prev = ptr;
			ptr = ptr.next;
			
		}
		
		if(ptr == null){
			System.out.println("Target was not found");
				
			}
			else if(front.data == target){
				front = new IntNode(target, front);
				
			}
			else{
				prev.next = ptr.next;
			}
	}
	
	public void deleteFront(){
		IntNode ptr = front;
		if (ptr != null){
			front = front.next;
		}
	}
	
	public boolean search(int target){
		IntNode ptr = front;
		boolean isFound = false;
		while(ptr != null && ptr.data != target){
			ptr = ptr.next;
		}
		
		if(ptr == null){
			System.out.println("target not found");
		}
		
		else if(ptr.data == target){
			System.out.println("target is found");
			isFound = true;
			return isFound;
		}
		return isFound;
		
	}
	
	public void addAfter(int target, int data){
		IntNode ptr = front;
		
		while(ptr !=  null && ptr.data != target){
			ptr = ptr.next;
		}
		
		if(ptr == null){
			System.out.println("List is currently empty, your target was not found!");
		}
		else if(ptr.data == target){
			ptr.next = new IntNode (data, ptr.next);
		}
		
	}
	
	
	
	public static void main (String [] args){
		IntLL ll = new IntLL();
		ll.addToFront(4);
		ll.addToFront(3);
		ll.addToFront(2);
		ll.addToFront(1);
		ll.printll();
		ll.deleteFront();
		ll.search(1);
		ll.search(2);
		ll.search(3);
		ll.printll();
		ll.addAfter(2, 1);
		ll.printll();

	}

}
