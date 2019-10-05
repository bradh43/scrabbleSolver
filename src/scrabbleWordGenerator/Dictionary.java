package scrabbleWordGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * reads in a dictionary file and checks if words are valid
 * @author Brad
 */
public class Dictionary {
	//ArrayList to hold words in dictionary
	private static ArrayList<String> wordList;
	
	/**
	 * default constructor that reads in words from file
	 * @param fileName of dictionary text
	 */
	public Dictionary(String fileName) {
		super();
		wordList = new ArrayList<String>();
		Scanner infile = null;
		try {
			infile = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File " + fileName + " not found");
			System.exit(0);
		}
		while(infile.hasNext()) {
			wordList.add(infile.nextLine());
		}
		
	}
	
	/**
	 * @param pos to search for word
	 * @return word found at position
	 */
	public String getWord(int pos) {
		String word = wordList.get(pos);
		return word;
	}
	
	public boolean removeWord(String removeWord){
		for(int i = 0; i < wordList.size(); i++) {
			if(removeWord.equalsIgnoreCase(wordList.get(i))) {
				wordList.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return number of words in dictionary
	 */
	public int getSize() {
		return wordList.size();
	}
	
	/**
	 * @param target of word to search for
	 * @return postiton of target or -1 if not found
	 */
	public static int findWord(String target) {
		for(int i = 0; i < wordList.size(); i++) {
			if(target.equalsIgnoreCase(wordList.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * binary search for word in dictionary
	 * @param target word to search for
	 * @param first element
	 * @param last element
	 * @return the position of word or -1 if not found
	 */
	public int binarySearch(String target, int first, int last) {
		if(first > last){
			return -1;
		}
		else {
			int mid = (last + first) / 2;
			int compResult = target.compareTo(wordList.get(mid));
			if(compResult == 0) {
				return mid;
			}
			else if(compResult < 0) {
				return binarySearch(target, first, mid - 1);
			}
			else {
				return binarySearch(target, mid + 1, last);
			}
		}
	}
	
	/**
	 * 
	 * @param target word to see if in dictionary
	 * @return true if found in dictionary false if not
	 */
	public boolean isWord(String target) {
		if(binarySearch(target, 0, wordList.size()) == -1) {
			return false;
		}
		else {
			return true;
		}
	}
}