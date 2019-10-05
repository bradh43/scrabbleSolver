package scrabbleWordGenerator;
import java.util.*;
/**
 * @author Brad
 *
 */
public class ScrabbleWordGenerator {
	
	public static void main(String[] args) {
		
		//Array for scrabble letters 
		ScrabbleLetter scrabbleLetters[] = setUpGameRules();
		
		//load Dictionary
		String wordFile = "words.txt";
		Dictionary dictionary = new Dictionary(wordFile);
		
		//load keyboard
		Scanner keyboard = new Scanner(System.in);
		
		//flag to keep asking for current letters available 
		boolean gameFlag = true;
		while(gameFlag){
			System.out.println("Enter the current letters avaiable to play? Type q to quit.");
			String availableLetters = keyboard.nextLine();
			
			//check if the user is trying to quit the game
			if(availableLetters.equalsIgnoreCase("quit") || availableLetters.equalsIgnoreCase("q")){
				gameFlag = false;
			} else {
				//get possible words
				ArrayList<String> possibleWords = generatePossibleWords(availableLetters, scrabbleLetters);
				//check if the possible words are valid
				ArrayList<String> validWords = generateValidWords(possibleWords, dictionary);
				//get the total amount of points for each word
				ArrayList<Integer> wordPoints = getPointValues(validWords, scrabbleLetters);
				//sort the words by points
				Object[] sortedArrays = sortWords(validWords, wordPoints);
				wordPoints = (ArrayList<Integer>) sortedArrays[0];
				validWords = (ArrayList<String>) sortedArrays[1];
				
				//display the results
				for(int i=0; i<validWords.size(); i++){
					System.out.println(validWords.get(i) + " : "+wordPoints.get(i).toString()+" points");	
				}
			}
		}
		//close the keyboard
		keyboard.close();
	}
	
	//method to sort words by greatest number of points using a insertion sort algorithm
	private static Object[] sortWords(ArrayList<String> validWords, ArrayList<Integer> wordPoints) {
	    int oldestIndex = wordPoints.size();
	    int tempPoint;
	    String tempWord;

	    for(int i = 0; i < wordPoints.size(); i++){
	      oldestIndex = i;
	      for(int j = i+1; j< wordPoints.size(); j++){
	         if(wordPoints.get(j) < wordPoints.get(oldestIndex)){
	            oldestIndex = j;
	         }
	      }
	      tempPoint = wordPoints.get(i);
	      wordPoints.set(i, wordPoints.get(oldestIndex));
	      wordPoints.set(oldestIndex, tempPoint);
	  	  tempWord = validWords.get(i);
	  	  validWords.set(i, validWords.get(oldestIndex));
	  	  validWords.set(oldestIndex, tempWord);
	    }
	    
	    Object[] sortedArrays = new Object[2];
	    sortedArrays[0] = wordPoints;
	    sortedArrays[1] = validWords;
	    
	    return sortedArrays;
	}
	
	//method to calculate how many points each word is worth
	private static ArrayList<Integer> getPointValues(ArrayList<String> validWords, ScrabbleLetter[] scrabbleLetters) {
		ArrayList<Integer> wordPoints = new ArrayList<Integer>();
		for(String word: validWords){
			int tempWordPointTotal = 0;
			for(char letter: word.toCharArray()){
				for(ScrabbleLetter scrabbleLetter: scrabbleLetters){
					if(scrabbleLetter.getLetter() == letter){
						tempWordPointTotal += scrabbleLetter.getPointValue();
					}
				}
			}
			wordPoints.add(tempWordPointTotal);
		}
		return wordPoints;
	}
	
	//method to check if a word is valid
	private static ArrayList<String> generateValidWords(ArrayList<String> possibleWords, Dictionary dictionary) {
		ArrayList<String> validWords = new ArrayList<String>();
		for(String word: possibleWords){
			//check if the word is in the dictionary
			if(dictionary.isWord(word)){
				//make sure the word is not a duplicate
				if(!validWords.contains(word)){
					validWords.add(word);
				}
			}
		}
		return validWords;
	}
	
	//method containing the game rules, and sets up the scrabble pieces
	public static ScrabbleLetter[] setUpGameRules(){
		char letters[] = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' '};
		int letterDistribution[] = new int[]{9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1,2};
		int pointDistribution[] = new int[]{1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10,0};
		ScrabbleLetter scrabbleLetters[] = new ScrabbleLetter[26];
		
		for(int letterPos = 0; letterPos < scrabbleLetters.length; letterPos++){
			scrabbleLetters[letterPos] = new ScrabbleLetter(letters[letterPos], pointDistribution[letterPos], letterDistribution[letterPos]);
		}
		return scrabbleLetters;
	}
	
	//method to get all the permutations that the available letters can be in
	public static ArrayList<String> generatePossibleWords(String availableLetters, ScrabbleLetter[] scrabbleLetters){
		ArrayList<String> permutations = new ArrayList<String>();
		for(char letter: availableLetters.toCharArray()){
			if(letter == ' '){
				for(ScrabbleLetter scrabbleLetter: scrabbleLetters){
					String tempLetters = availableLetters.replace(' ', scrabbleLetter.getLetter());
					permutations = generatePossibleCombos("", tempLetters, permutations, tempLetters.length());
				}
			} else {
				permutations = generatePossibleCombos("", availableLetters, permutations, availableLetters.length());		

			}
		}
		return permutations;
	}
	
	//method to calculate the total number of permutations a word of length n can have
	public static int getTotalNumberPermutations(int n){
		int ans = 0;
		for(int i=1; i<=n; i++){
			ans += ((factorial(n))/(factorial((n-i))));
		}
		return ans;
	}
	
	//method to compute factorials
	public static int factorial(int x){
		if(x == 1 || x == 0){
			return 1;
		} else {
			return factorial(x-1) * x;
		}
	}
	
	//method to generate possible combinations that the available letters can have
	public static ArrayList<String> generatePossibleCombos(String prefix, String letterCombos, ArrayList<String> permutations, int size){
		int n = letterCombos.length();
		permutations.add(prefix);
	    if(permutations.size() == getTotalNumberPermutations(size)){
	    		return permutations;
	    }else{
	        for (int i = 0; i < n; i++){
	        		generatePossibleCombos(prefix + letterCombos.charAt(i), letterCombos.substring(0, i) + letterCombos.substring(i+1, n), permutations, size);
	        }
        }
	    return permutations;
	}

}
