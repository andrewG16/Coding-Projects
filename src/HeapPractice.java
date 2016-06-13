
public class HeapPractice {
	int list[] = new int [12];
	
	// FINDING Children of a Parent in an Array:
		//  if Parent is at INDEX k ---> it's Children are at INDEXES:
			// **** [2k+1] & [2k+2] ******
	
	//Finding the Parent of a Child in an Array:
		// If Parent = P, and Index = k , then to find the Parent of a Child at Index k:
			//  ***** P = (k-1)/2 ****** UNLESS k = 0 because this means k = root node
			// ***** USE INTEEGER DIVISION ***** 
				//(Only keep the Integer, not the decimal. ex: 0.5--> '0' ; 1.4 --> '1')
	
	
	private void siftUp(){
		int k = list.length - 1;
		while( k > 0){
			int p = (k-1)/2;
			int child = list[k];
			int parent = list[p];
			int temp;
			
			if(child > parent){
				temp = list[p];
				list [p] = list [k];
				list[k] = temp;
				
				k = p;
			}
			else{
				break;
			}
		}
	}
	
	private void siftDown(){
		int k = 0; // Index starts at root node
		int l = 2*(k + 1); //Left child of k/ root node
	}
}
