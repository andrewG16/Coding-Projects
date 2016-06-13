
import java.util.Arrays;
public class Merge {
	
	public class Node{
		int data;
		Node next;
		
		public Node(int data, Node next){
			this.data = data;
			this.next = next;
		}
		
		public Node merge(Node node1, Node node2){
			if(node1 == null){
				return node2;
			}
			if(node2 == null){
				return node2;
			}
			if(node1.data == node2.data){
				node1.next = merge(node1.next, node2.next);
				return node1;
			}
			if (node1.data < node2.data){
				node1.next = merge(node1.next, node2.next);
				return node1;
			}
			node2 = merge(node2.next, node1);
			return node2;
		}
	}
	
	public static void main (String [] args){
		
		int [] sorted = {1,2,3,5,6};
		int [] newSort = new int [sorted.length];
		
		int num1 = 4;
		
		for(int i = 0; i<= sorted.length-1;i++){
			newSort[i] = sorted[i];
			
		}
		
		newSort [newSort.length -1] = num1;
		Arrays.sort(newSort);
		
		for(int k = 0; k <= newSort.length-1; k++){
			System.out.print(newSort[k]);
		}
		
		throw new IllegalArgumentException( "Object does not exist");
		
	}

}

