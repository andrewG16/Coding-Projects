
public class quicksortPractice {
	public static void main(String args[]){
		int [] array1 = {7,5,9,2,1,54,4,65,7,5,5,46,9,87,4,999,0};
		
		for(int i = 0; i<=array1.length-1; i++){
			System.out.print(" "+array1[i]);
			
		}
		
		System.out.println();
		/** ALL elements to the RIGHT of the PIVOT are GREATER than the PIVOT
		 * 
		 * ALL elements to the LEFT of the PIVOT are LESS than the PIVOT
		 * 
		 * PIVOT points at INDEX 0
		 * 
		 * We TRAVERSE from RIGHT to LEFT
		 * 
		 * IF [0] > [last INDEX]---> WE SWAP them and TRAVERSE from LEFT to RIGHT now
		 * else we continue to traverse from right to left.
		 * 
		 * EVERYTIME the above statement is FALSE while traversing, we SWAP the two elements
		 * and CHANGE the DIRECTION of the traversing.
		 * 
		 * ONCE the LEFT and RIGHT pointer are pointing to the SAME ELEMENT/INDEX we know:
		 * 	that the array is divided into 2 subarrays: 
		 * 		1. subarray GREATER than PIVOT
		 * 		2. subarray LESS than PIVOT
		 */
		
		int beg = array1[0];
		int end = array1[array1.length-1];
		
		
			for(int i = 0; i<=array1.length-1;i++){
				for(int j = i+1; j <=array1.length-1;j++){
					if(array1[j]<array1[i]){
						int temp = array1[i];
						array1[i] = array1[j];
						array1[j] = temp;
					}
				}
			}
			
			for(int i = 0; i<=array1.length-1; i++){
				System.out.print(" "+array1[i]);
			}
		
	}

}
