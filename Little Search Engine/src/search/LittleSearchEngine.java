package search;

import java.io.*;
import java.util.*;

/**
 * This class encapsulates an occurrence of a keyword in a document. It stores the
 * document name, and the frequency of occurrence in that document. Occurrences are
 * associated with keywords in an index hash table.
 * 
 * @author Sesh Venugopal
 * 
 */
class Occurrence {
	/**
	 * Document in which a keyword occurs.
	 */
	String document;
	
	/**
	 * The frequency (number of times) the keyword occurs in the above document.
	 */
	int frequency;
	
	/**
	 * Initializes this occurrence with the given document,frequency pair.
	 * 
	 * @param doc Document name
	 * @param freq Frequency
	 */
	public Occurrence(String doc, int freq) {
		document = doc;
		frequency = freq;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + document + "," + frequency + ")";
	}
}




/**
 * This class builds an index of keywords. Each keyword maps to a set of documents in
 * which it occurs, with frequency of occurrence in each document. Once the index is built,
 * the documents can searched on for keywords.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in descending
	 * order of occurrence frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash table of all noise words - mapping is from word to itself.
	 */
	HashMap<String,String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashMap<String,String>(100,2.0f);
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.put(word,word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeyWords(docFile);
			mergeKeyWords(kws);
		}
		
	}
	
	
 

	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeyWords(String docFile) 
	throws FileNotFoundException {
	
		HashMap<String,Occurrence> keyword = new HashMap<String,Occurrence>();
		
		boolean fileTest = false;
		
		while(fileTest == false){
			try{
				Scanner text = new Scanner(new File(docFile));
				fileTest = true;
				//System.out.println("test passed");
			}
			catch(FileNotFoundException e){
				//System.out.println("file not found!");
				return keyword;
			}
		}
		
		
		Scanner text = new Scanner(new File(docFile));
		int textFreq = 1;
		
			while (text.hasNext()){
				String nextWord = text.next();

				if(getKeyWord(nextWord) != null){	
					nextWord = getKeyWord(nextWord);
					
					if(keyword.containsKey(nextWord) == false){
						Occurrence count = new Occurrence(docFile,textFreq);
						keyword.put(nextWord, count);
					}
					else{
						keyword.get(nextWord).frequency++;
					}
				}
			}
			// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
			return keyword;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeyWords(HashMap<String,Occurrence> kws) {
		ArrayList<Occurrence> finList = new ArrayList<Occurrence>();
		
		for(String key: kws.keySet()){	
			Occurrence occurence = kws.get(key);
			
			if(keywordsIndex.containsKey(key) == false){
				ArrayList<Occurrence> newList = new ArrayList<Occurrence>();				
				newList.add(occurence);
				keywordsIndex.put(key, newList);
			}
			else{
				finList = keywordsIndex.get(key);
				finList.add(occurence);
				insertLastOccurrence(finList);
				keywordsIndex.put(key, finList);
			}	
		}
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * TRAILING punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyWord(String word) {
		
		word = word.trim(); // gets rid of spaces
		
		char last = word.charAt(word.length()-1); 
		
		//if(last == '.' || last == ',' || last == '?' || last == ':' || last == ';' || last == '!'){
		// ***NOTE:didn't work because it only tested once... needs to loop through
		
		/* I was going to do the for loop as follows and therefore it would skip over
		 * the second test and all it would have to test for is to see if it's a 
		 * noise word and to lower case it... The reason I didn't was because I wanted to
		 * be exact with the punctuation characters that aren't allowed
		 * as to not lose points.
		 */
		
		/***
		 * for(int i = 0; i<= word.length()-1;i ++){
		 * 		if(Character.isLetter(word.charAt(i)) == false){
		 * 			if(i+1 == word.length()){
		 * 				word = word.substring(0,word.length()-1);
		 * 				break;
		 * 			}
		 * 			else if(Character.isLetter(word.charAt(i+1)) == true){
		 * 				return null;
		 * 			}
		 * 			else if(Character.isLetter(word.charAt(i+1)) == false){
		 * 				word = word.substring(0,i);
		 * 				break;
		 * 			}
		 * 			
		 * 		
		 ***/
		
		
		while(last == '.' || last == ',' || last == '?' || last == ':' || last == ';' || last == '!'){
			// will cycle through the word backwards until the last character is a character
			word = word.substring(0, word.length()-1); 
			//System.out.println(word);
			/****last = word.charAt(word.length()-1);
			if(word.length() <= 1){
				break;
			}*/
			
			if(word.length() > 1){
				last = word.charAt(word.length()-1);
			}
			else{
				break;
			}
			
		}
		
		word = word.toLowerCase();
		/**once it passes the first test, now it's time to test if 
		the word is a noise word or if it has a punctuation in the middle*/

		for(String noise: noiseWords.keySet()){
			if(word.equalsIgnoreCase(noise)){
				return null;
			}
		}
		
		for(int i = 0; i <= word.length()-1; i++){
			if(Character.isLetter(word.charAt(i))== false){
				return null;
			}
			//else{
				//return word;
			//}
		}
		//COMPLETE THIS METHOD
		//THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		return word;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * same list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion of the last element
	 * (the one at index n-1) is done by first finding the correct spot using binary search, 
	 * then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		
		if(occs.size() == 1){
			return null;
		}
		ArrayList<Integer> midPoints = new ArrayList<Integer>();
		
		int last = occs.size()-1;
		int first = 0;
		int mid;
		int lastFreq = occs.get(occs.size()-1).frequency;
		
		Occurrence tmp = occs.get(occs.size() -1);
		
		
		//simple binary search
        while(first <= last){ 
            mid = (first + last)/2;
            midPoints.add(mid);
            
            if(lastFreq< occs.get(mid).frequency){
            	first = mid +1;
            }
            else if(lastFreq > occs.get(mid).frequency ){            	
                last = mid-1;
            }
            else{
            	break;
            }
        }
        
        if(midPoints.get(midPoints.size()-1) == 0){
        	if(occs.get(0).frequency > tmp.frequency){
        		occs.add(1, tmp);
        		occs.remove(occs.size()-1);
        		
        		return midPoints;
        	}
        }
        //else{<--- Caused such problems, took so long to debug
        // would cause the test cases to always print AliceCh1.txt first
        // spent hours trying to debug... Just because I put the else statement 
        //in there unconsciously thanks to multitasking
        	occs.add(midPoints.get(midPoints.size()-1), tmp);
        	occs.remove(occs.size()-1);
       // }
     	// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		return midPoints;
	}
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of occurrence frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will appear before doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matching documents, the result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of NAMES of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matching documents,
	 *         the result is null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<Occurrence> kw1List = new ArrayList<Occurrence>();
		ArrayList<Occurrence> kw2List = new ArrayList<Occurrence>();
		
		kw1 = kw1.toLowerCase();
		kw2 = kw2.toLowerCase();
		
		if(keywordsIndex.get(kw1) != null){
			kw1List = keywordsIndex.get(kw1);
		}
	
		if(keywordsIndex.get(kw2) != null){
			kw2List = keywordsIndex.get(kw2);
		}
		

		
		
		for(int i = 0; i < kw1List.size(); i++){
			// add if results.size >5 --> break, else do...
			if(results.size() <= 4){
				String doc = kw1List.get(i).document;
				int kw1occ = kw1List.get(i).frequency;
				
				for(int k = 0; k < kw2List.size(); k++){
					String doc2 = kw2List.get(k).document;
					int kw2occ = kw2List.get(k).frequency;
					
					/***The code adds the text files to the result list even if the word doesn't exist
					 * in the text file because it will have an occurrence of 0.
					 * I didn't see any answer to this question in the write up
					 *  so I assumed that it would as long as it is in descending order
					 *  
					 *  *** EDIT: the above is no longer true***
					 * 
					 */
					if(kw2occ <= kw1occ){
						if(results.contains(doc) == false && results.size() <=4){
							results.add(doc);
							//results.add(doc2); //SEEE IFF THISSS WORKS--->doesn't
												// add another statement; do same for
												//next condition
						}
						
					}
					else if(kw2occ > kw1occ){
						if(results.contains(doc2) == false && results.size() <=4){
							results.add(doc2);
						}
						
					}
				}
			}
		}
		
			for(int j=0; j<=results.size()-1;j++){
				if(j == results.size()-1){
					System.out.print(results.get(j));
				}
				else{
					System.out.print(results.get(j) + ", ");
				}
			}
			if(results.size() == 0){
				return null;
			}
		
		
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		return results;
	}
}