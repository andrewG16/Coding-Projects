
public class IntCLL {
	public class IntNode{
		
		int data;
		IntNode next;
		
		public IntNode (int data, IntNode next){
			this.data = data;
			this.next = next;
		}
	}
	
	IntNode rear;
	
	public IntCLL(){
		rear = null;
	}
	
	public void addToFront(int data){
		IntNode node1 = new IntNode(data,null);
		
		if (rear == null){
			rear = node1;
			node1.next = node1;
		}
		else{
			node1.next = rear.next;
			rear.next = node1;
		}
	}
	
	public void deleteFront(){
		IntNode ptr = rear;
		
		if (ptr == null || ptr.next == ptr){
			rear = null;
		}
		else if (ptr != null){
			rear.next = rear.next.next;
		}
	}
	
	public void printCll(){
		IntNode ptr = rear.next;
		
		if (ptr == null || ptr.next == ptr){
			System.out.println(ptr.data);
		}
		else{
			do {
				System.out.println(ptr.data);
				ptr = ptr.next;
			}while(ptr != rear.next);
		}
	}
	
	
	public void search(int target){
		IntNode ptr = rear.next;
		if(ptr == null || ptr == ptr.next && ptr.data != target){
			System.out.println("Target not found");
		}
		else{
			do{
				if(ptr.data == target){
					System.out.print("Target was found");
					break;
				}
				else{
					ptr = ptr.next;
				}
			}while (ptr != rear.next);
			
			if (ptr == rear.next && ptr.data != target){
				System.out.println("Target was not found");
			}
		}
	}
	
	
	public boolean delete(int target){
		IntNode ptr = rear.next;
		IntNode prev = rear;
		if(ptr == null || ptr == ptr.next && ptr.data != target){
			return false;
		}
		else if (ptr.data == target){
			if(ptr == ptr.next){
				ptr = null;
				return true;
			}
			else{
				prev.next = ptr.next;
				ptr = prev;
				ptr.next = prev.next;
				return true;
			}
		
		}
		else{
			do{
				ptr = ptr.next;
				prev = prev.next;
			}while (ptr != rear.next);
			return false;
		}
	}
	
	public static void main (String [] args){
		IntCLL node1 = new IntCLL();
		
		node1.addToFront(9);
		node1.addToFront(8);
		node1.addToFront(7);
		node1.addToFront(6);
		node1.addToFront(5);
		
		node1.printCll();
		
		System.out.println("*******");
		
		node1.deleteFront();
		node1.printCll();
		
		System.out.println("*******");
		
		node1.deleteFront();
		node1.printCll();
		
		System.out.println("******");
		
		node1.search(9);
		System.out.println(node1.delete(8));



	}
	

}
