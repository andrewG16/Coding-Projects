package structures;

import java.util.*;

/**
 * Encapsulates an interval tree.
 * 
 * @author runb-cs112
 */
public class IntervalTree {
	
	/**
	 * The root of the interval tree
	 */
	IntervalTreeNode root;
	
	/**
	 * Constructs entire interval tree from set of input intervals. Constructing the tree
	 * means building the interval tree structure and mapping the intervals to the nodes.
	 * 
	 * @param intervals Array list of intervals for which the tree is constructed
	 */
	public IntervalTree(ArrayList<Interval> intervals) {
		
		// make a copy of intervals to use for right sorting
		ArrayList<Interval> intervalsRight = new ArrayList<Interval>(intervals.size());
		for (Interval iv : intervals) {
			intervalsRight.add(iv);
		}
		
		// rename input intervals for left sorting
		ArrayList<Interval> intervalsLeft = intervals;
		
		// sort intervals on left and right end points
		Sorter.sortIntervals(intervalsLeft, 'l');
		Sorter.sortIntervals(intervalsRight,'r');
		
		// get sorted list of end points without duplicates
		ArrayList<Integer> sortedEndPoints = Sorter.getSortedEndPoints(intervalsLeft, intervalsRight);
		
		// build the tree nodes
		root = buildTreeNodes(sortedEndPoints);
		
		// map intervals to the tree nodes
		mapIntervalsToTree(intervalsLeft, intervalsRight);
	}
	
	/**
	 * Builds the interval tree structure given a sorted array list of end points.
	 * 
	 * @param endPoints Sorted array list of end points
	 * @return Root of the tree structure
	 */
	public static IntervalTreeNode buildTreeNodes(ArrayList<Integer> endPoints) {
		Queue<IntervalTreeNode> qT = new Queue<IntervalTreeNode>();
		IntervalTreeNode t = null;
		IntervalTreeNode t1 = null;
		IntervalTreeNode t2 = null;
		
		//STEP 5: for each point create a tree T and set it's split value to the point itself
		float splitVal;
		
		for(int i = 0; i<=endPoints.size()-1; i++){
			
			splitVal = endPoints.get(i);
			t = new IntervalTreeNode(splitVal, splitVal, splitVal);
			qT.enqueue(t);
		}
		
		int s = qT.size();
		
		while(s > 0){
			
			if (s == 1){
				t = qT.dequeue();
				return t;
			}
			
			int temp = s;
			
			while(temp>1){
				t1 = qT.dequeue();
				t2 = qT.dequeue();
				
				float v1 = t1.maxSplitValue;
				float v2 = t2.minSplitValue;
				float x = (v1+v2)/2;
				
				IntervalTreeNode n = new IntervalTreeNode(x,t1.minSplitValue,t2.maxSplitValue);
				n.leftChild = t1;
				n.rightChild = t2;
				qT.enqueue(n);
				temp -= 2;
			}
			
			if (temp == 1){
				qT.enqueue(qT.dequeue());
			}
			
			s = qT.size();
		}
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE
		return t;
	}
	
	/**
	 * Maps a set of intervals to the nodes of this interval tree. 
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 */
	public void mapIntervalsToTree(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
		
		root.rightIntervals = new ArrayList<Interval>();
		
		for(int i = 0; i <= leftSortedIntervals.size()-1;i++){
			leftMap(leftSortedIntervals.get(i), root);
		} 
		
		for(int k = 0; k <= rightSortedIntervals.size()-1;k++){
			rightMap(rightSortedIntervals.get(k), root);
		}
		
		
		// see if you have to initialize list within the private method or not
		
		// COMPLETE THIS METHOD
	}
	
	private void leftMap (Interval p , IntervalTreeNode root){
		////////Just added to fix null pointer error
		if(root == null){
			return;
		}
		if(p.leftEndPoint <= root.splitValue && p.rightEndPoint >= root.splitValue){
			if(root.leftIntervals == null){
				root.leftIntervals = new ArrayList<Interval>();
			}
			root.leftIntervals.add(p);
			return;
		}
		
		leftMap(p, root.leftChild);
		leftMap(p, root.rightChild);
	}
	
	private void rightMap(Interval p, IntervalTreeNode root){
		////////Just added to fix null pointer error
		if(root == null){
			return;
		}
		if(p.leftEndPoint <= root.splitValue && p.rightEndPoint>= root.splitValue){
			if(root.rightIntervals == null){
				root.rightIntervals = new ArrayList<Interval>();
			}
			root.rightIntervals.add(p);
			return;
		}
		
		rightMap(p,root.leftChild);
		rightMap(p,root.rightChild);
	}
	
	/**
	 * Gets all intervals in this interval tree that intersect with a given interval.
	 * 
	 * @param q The query interval for which intersections are to be found
	 * @return Array list of all intersecting intervals; size is 0 if there are no intersections
	 */
	public ArrayList<Interval> findIntersectingIntervals(Interval q) {
		ArrayList<Interval> results = new ArrayList<Interval>();
		IntervalTreeNode r = getRoot();
		Interval target = q;
		return(searchTree(target,r, results));
		
		
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE
		//return null;
	}
	
	private ArrayList<Interval> searchTree(Interval target, IntervalTreeNode r, ArrayList<Interval> results){
	
		
		float splitValue = r.splitValue;
		ArrayList<Interval> leftList = r.leftIntervals;
		ArrayList<Interval> rightList = r.rightIntervals;
		IntervalTreeNode lChild = r.leftChild;
		IntervalTreeNode rChild = r.rightChild;
		
		if(r == null || r.leftChild == null && r.rightChild == null){
			return null;
		}
		
		if(target.contains(splitValue)){
			//just did a switch on the null coming first then the <=, see if that works
			for(int i = 0; leftList != null && i <= leftList.size()-1;i++){ 
				results.add(leftList.get(i));
			}
			searchTree(target,rChild, results);
			searchTree(target,lChild, results);
		}
		else if(splitValue < target.leftEndPoint){ 
		/*** Sloppy code, but found out the following:
		 * for some reason the while loop doesn't work here, but a for loop does,
		 * sorry for only commenting it out and not deleting it*/
			
			//////int i = results.size()-1;
			
			// do the same here with null/comparison order
			if(rightList != null){
				
				for(int i = rightList.size()-1; i >= 0; i--){
					if(rightList.get(i).intersects(target)){
						results.add(rightList.get(i));
					}
				
				////while( i>=0){ //took out the && intersect statement
					////if(rightList.get(i).intersects(target)){
						////results.add(rightList.get(i));
						////}
						////i = i-1;
				}
			}
			searchTree(target, rChild,results);
		}
		else if(splitValue > target.rightEndPoint ){
			/*** found out that here it doesn't matter if it is a for loop or a while loop
			 * so I just stuck with my original while loop. Again sorry for comments*/
			
			int i = 0;
			//do the same here as above with null/compare
			while(leftList != null && i<=leftList.size()-1){ //took out the && intersect statement
				if(leftList.get(i).intersects(target)){
					results.add(leftList.get(i));
				}
				i = i+1;
			}
			
			 //for(int i = 0; leftList !=null && i<=leftList.size()-1;i++){
			  	//	if(leftList.get(i).intersects(target)){
			 	//		results.add(leftList.get(i));
			  //		}
			 //}
			 
			searchTree(target, lChild,results);
		}
		//To make compiler happy
		return results;	
	}
	/**
	 * Returns the root of this interval tree.
	 * 
	 * @return Root of interval tree.
	 */
	public IntervalTreeNode getRoot() {
		return root;
	}
}

