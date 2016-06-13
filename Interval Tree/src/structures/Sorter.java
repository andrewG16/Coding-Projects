package structures;

import java.util.ArrayList;

/**
 * This class is a repository of sorting methods used by the interval tree.
 * It's a utility class - all methods are static, and the class cannot be instantiated
 * i.e. no objects can be created for this class.
 * 
 * @author runb-cs112
 */
public class Sorter {

	private Sorter() { }
	
	/**
	 * Sorts a set of intervals in place, according to left or right endpoints.  
	 * At the end of the method, the parameter array list is a sorted list. 
	 * 
	 * @param intervals Array list of intervals to be sorted.
	 * @param lr If 'l', then sort is on left endpoints; if 'r', sort is on right endpoints
	 */
	public static void sortIntervals(ArrayList<Interval> intervals, char lr) {
		
		ArrayList<Interval> sortedIntervals = new ArrayList<Interval>(intervals.size());
		
		/* FIND OUT IF THE FOLLOWING CODE IS NECESSARY:
		 * if(lr != 'l' || lr != 'r'){
		 * 		REPORT ERROR!!
		 * }
		 * 
		 * if(intervals == null || intervals.size() == 0){
		 * 		REPORT ERROR!!!
		 * }
		 */
		
		if(lr == 'l'){
			for(int i = 0; i<= sortedIntervals.size()-1; i++){
				for (int k = i+1; k <= sortedIntervals.size()-1;k++){
					
					if(intervals.get(k).leftEndPoint < intervals.get(i).leftEndPoint){
						Interval tmp = intervals.get(i);
						sortedIntervals.set(i, intervals.get(k));
						sortedIntervals.set(k, tmp);
					}
				}
			}
		}
		
		else if(lr == 'r'){
			for(int i = 0; i<= sortedIntervals.size()-1; i++){
				for (int k = i+1; k <= sortedIntervals.size()-1;k++){
					
					if(intervals.get(k).rightEndPoint < intervals.get(i).rightEndPoint){
						Interval tmp = intervals.get(i);
						sortedIntervals.set(i, intervals.get(k));
						sortedIntervals.set(k, tmp);
					}
				}
			}
		}
		
		
		
		
	}
	
	/**
	 * Given a set of intervals (left sorted and right sorted), extracts the left and right end points,
	 * and returns a sorted list of the combined end points without duplicates.
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 * @return Sorted array list of all endpoints without duplicates
	 */
	public static ArrayList<Integer> getSortedEndPoints(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
		if(leftSortedIntervals == null && rightSortedIntervals == null){
			return null;
		}
		
		ArrayList<Integer> sortedIntervals = new ArrayList<Integer>();
		
		int index = 0;
			//adds the LEFT sorted Intervals first
			for(int i = 0; i <= leftSortedIntervals.size()-1; i++){
				sortedIntervals.add(i, leftSortedIntervals.get(i).leftEndPoint);
				index = i;
			}
			// adds the RIGHT sorted Intervals now
			for(int k = 0; k <= rightSortedIntervals.size()-1; k++){
				sortedIntervals.add(index+1, rightSortedIntervals.get(k).rightEndPoint);
				index++;
			}
			// Sorts the list containing BOTH right and left points
			for(int i = 0; i<= sortedIntervals.size()-1; i++){
				for (int k = i+1; k <= sortedIntervals.size()-1;k++){
					
					if(sortedIntervals.get(k) < sortedIntervals.get(i)){
						int tmp = sortedIntervals.get(i);
						sortedIntervals.set(i, sortedIntervals.get(k));
						sortedIntervals.set(k, tmp);
					}
				}
			}
			//deletes duplicates from the list
			for(int i = 0; i <= sortedIntervals.size()-1; i++){
				
				if(i+1 == sortedIntervals.size()){
					break;
				}
				
				if(sortedIntervals.get(i) == sortedIntervals.get(i+1)){
					sortedIntervals.remove(i);
					i--;
				}
			}
			
		return sortedIntervals;
		
		
	}
}
