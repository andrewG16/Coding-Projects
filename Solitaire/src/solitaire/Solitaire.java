package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		CardNode ptr = deckRear.next;
		int temp;
		
		do{
			if(ptr.cardValue == 27){
				temp = ptr.next.cardValue;
				ptr.next.cardValue = ptr.cardValue;
				ptr.cardValue = temp;
				break;
			}
			ptr = ptr.next;
		}while (ptr != deckRear.next);
		
		// COMPLETE THIS METHOD
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
		CardNode ptr = deckRear.next;
		CardNode prev = null;
		int temp;
		
		do{
			if(ptr.cardValue == 28){
				temp = ptr.next.cardValue;
				ptr.next.cardValue = ptr.cardValue;
				ptr.cardValue = temp;
				
				ptr = ptr.next;
				
				temp = ptr.next.cardValue;
				ptr.next.cardValue = ptr.cardValue;
				ptr.cardValue = temp;
				
				break;
				
			}
			ptr = ptr.next;
			
		}while(ptr != deckRear.next);
		
		
	    // COMPLETE THIS METHOD
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		CardNode copy = null;
		CardNode frontOL = deckRear.next;
		CardNode frontOR = null;
		CardNode frontOM = null;
		//CardNode temp = null;
		//CardNode temp2 = null;
		int count = 0;
		
		CardNode ptr = deckRear.next;
		CardNode prev = null;
		CardNode ptrM = frontOM;
		CardNode ptrOL = frontOL;
		CardNode ptrOR = frontOR;
		CardNode ptrMerge;
		
		do{
			//copy = // copy the old deck into this new one.
			
		}while(ptr != deckRear.next);
		
		do{
			if(ptr.cardValue == 27 || ptr.cardValue == 28){
				
				if(prev == null){ //If Joker is First Card
					ptr = ptr.next;
					while(ptr.cardValue != 27 && ptr.cardValue != 28){
						ptr = ptr.next;
					}
					deckRear = ptr; // Making the 2nd Joker the last card by changing deckRear
					break;
				} // End of If joker is First Card Case
				
				//Start of other case
				frontOM = ptr; //ADD Dummy = ptr before this line
				ptrM = frontOM;
				
				ptr = ptr.next;
				
				while(ptr.cardValue != 27 || ptr.cardValue != 28){// fix this line because it does not stop 
					if (ptr.cardValue == 27 || ptr.cardValue == 28){
						ptr = ptr.next;
						ptrM = ptrM.next;
						break;
					}
					ptr = ptr.next;
					ptrM = ptrM.next;
				}
				ptrM.next = frontOM; // THIS IS WHERE I LOSE THE LAST HALF OF THE CLL FIX THIS ASAP, try ptr.next =
								
				
				if(ptr != deckRear.next){
					frontOR = ptr;
					ptrOR = frontOR;
					
					while(ptr.next != deckRear.next){
						ptr = ptr.next;
						ptrOR = ptrOR.next;
					}
					ptrOR.next = frontOR;
					ptr = ptr.next; //JUST FINISHED CREATING RIGHT CLL, double check if
									//Ptr needs to be updated or not
					
				}
				//Clear the original Deck because we will merge the other CLL and then
				//we will point the deckRear to the newly Merged CLL
				deckRear = null; 
				if(frontOR == null){ //If the second Joker was the last card
					ptrMerge = frontOM;
					
					while(ptrMerge.next != frontOM){
						ptrMerge = ptrMerge.next;
					}
					ptrMerge.next = frontOL; // Connects Original Mid CLL to Original left CLL
					ptrMerge = ptrMerge.next;
					
					while(ptrMerge.next != frontOL){
						ptrMerge = ptrMerge.next;
					}
					ptrMerge.next = frontOM; // Reconnects the last node to the first node
					deckRear = ptrMerge; // Changes the Deck reference to the new CLL
				}
				else{
					ptrMerge = frontOR;
					
					while(ptrMerge.next != frontOR){
						ptrMerge = ptrMerge.next;
					}
					ptrMerge.next = frontOM;// Connects Original Right CLL to Original Mid CLL
					ptrMerge = ptrMerge.next;
					
					while(ptrMerge.next != frontOM){
						ptrMerge = ptrMerge.next;
					}
					ptrMerge.next = frontOL;// Connects Original Mid CLL to Original left CLL
					ptrMerge = ptrMerge.next;
					
					while(ptrMerge.next != frontOL){
						ptrMerge = ptrMerge.next;
					}
					ptrMerge.next = frontOR;// Reconnects the last node to the first node
					deckRear = ptrMerge;// Changes the Deck reference to the new CLL
				}
				
				break;
				
			}
			
			prev = ptr;
			ptr = ptr.next;
			//temp = ptr.next;/////
			
			if(ptr.cardValue == 27 || ptr.cardValue == 28){
				//temp2 = ptrOL;/////
				ptrOL.next = frontOL; // deckRear now only goes up to 6 (AKA deckRear = 6, and deckRear.next = 1)
				//ptrOL = ptrOL.next;/////
				//temp2.next = temp;////
				//Possibly put a temp variable here
				continue;
			}
			ptrOL = ptrOL.next;
			
		}while(ptr != deckRear.next);
		
		
		// COMPLETE THIS METHOD
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {
		CardNode ptr = deckRear.next;
		CardNode prev = null;
		CardNode grpCards = deckRear.next;
		CardNode ptrGrp = grpCards;
		
		int counter = 1;
		int lastCardValue = deckRear.cardValue;
		
		if(lastCardValue == 28){
			lastCardValue = 27;
		}
		
		while(counter < lastCardValue){ // this While statement groups the cards traversed
			counter++;
			ptr = ptr.next;
			ptrGrp = ptrGrp.next;
		}
		ptr = ptr.next;// so you don't lose the reference to the rest of the list
		ptrGrp.next = grpCards; // makes grouped cards into a CLL
		deckRear.next = ptr; // Gets rid of the grouped cards by changing pointer
		ptr = deckRear.next;// resets the pointer so that we can traverse again
		
		while(ptr != deckRear){// traverse until ptr is the last node
			prev = ptr;
			ptr = ptr.next;
		}
		ptrGrp.next = ptr;// now connects the extracted grouped cards to point to deckRear
		
		if(prev == null){ // this only happens if you traverse 27 cards (Joker is last)
			ptr.next = grpCards;// finishes connecting the two list together
			
		}else{
			prev.next = grpCards;
		}							// finishes connecting the two list together when
							  // the joker isn't the last card
		// COMPLETE THIS METHOD
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
		jokerA();
		jokerB();
		tripleCut();
		countCut();
		
		CardNode ptr = deckRear.next;
		
		int key = 0;
		int cardValue = deckRear.next.cardValue;
		int counter = 1;
		
		if(cardValue == 28){
			cardValue = 27;
		}
		
		while(counter < cardValue){
			counter ++;
			ptr = ptr.next;
		}
		
		if(ptr.next.cardValue != 28 || ptr.next.cardValue != 27){
			key = ptr.next.cardValue;
			return key;
		}
		else{
			jokerA();
			jokerB();
			tripleCut();
			countCut();
			
			ptr = deckRear.next;
			cardValue = deckRear.next.cardValue;
			counter = 0;
			
			if(cardValue == 28){
				cardValue = 27;
			}
			
			while (counter < cardValue){
				counter ++;
				ptr = ptr.next;
			}
			
			key = ptr.next.cardValue;
			return key;
		}
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	   // return -1;
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
		String msgCap = "";
		String encrypt = "";
		int chValue = 0;
		int keyValue = 0;
		
		for(int i = 0; i<message.length();i++){// turns the entire message into caps
			char c = message.charAt(i);
			
			if(Character.isLetter(c) == true){
				msgCap += Character.toUpperCase(c);
				
			}
			
		}
		
		for(int k = 0; k < msgCap.length(); k++){
			char ch = msgCap.charAt(k);
			keyValue = getKey();
			//chValue = ch + keyValue - 64;
			chValue = ch + keyValue;
			
			if(chValue > 90){
				chValue -= 26;
			}
			
			char[] c = Character.toChars(chValue);
			encrypt += c[0]; // Find out how to change the value to its appropriate character
		}
		
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    return encrypt;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		String decrypt = "";
		int chValue = 0;
		int keyValue = 0;
		
		for(int i = 0; i < message.length(); i++){
			char ch = message.charAt(i);
			keyValue = getKey();
			//chValue = ch + keyValue - 64;
			if(ch - keyValue <= 64){
				chValue = (ch + 26) - keyValue;
			}
			else{
				chValue = ch - keyValue;
			}
			char[] c = Character.toChars(chValue);
			decrypt += c[0];
			
		}
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    return decrypt;
	}
}