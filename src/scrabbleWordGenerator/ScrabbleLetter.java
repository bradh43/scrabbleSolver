package scrabbleWordGenerator;

/**
 * @author Brad
 *
 */
public class ScrabbleLetter {
	
	char letter;
	int pointValue;
	int letterDistribution;
	
	public ScrabbleLetter(char letter, int pointValue, int letterDistribution){
		this.letter = letter;
		this.pointValue = pointValue;
		this.letterDistribution = letterDistribution;
		
	}
	
	
	@Override
	public String toString() {
		return "ScrabbleLetter [letter=" + letter + ", pointValue=" + pointValue + ", letterDistribution="
				+ letterDistribution + "]";
	}


	public char getLetter() {
		return letter;
	}
	
	public void setLetter(char letter) {
		this.letter = letter;
	}
	
	public int getPointValue() {
		return pointValue;
	}
	
	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}
	
	public int getLetterDistribution() {
		return letterDistribution;
	}
	
	public void setLetterDistribution(int letterDistribution) {
		this.letterDistribution = letterDistribution;
	}
	
}
