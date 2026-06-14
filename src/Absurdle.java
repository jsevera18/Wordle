import java.util.*;

/**
 * A class used to simulate a game of Absurdle
 * @author Jimmy Severa
 *
 */
public class Absurdle extends Wordle {
	protected HashMap<String, ArrayList<String>> wordMap;
	protected ArrayList<String> words = new ArrayList<String>();
	protected String letterString;
	
	/**
	 * Determines the dictionary of words to use for the game and then
	 * sets up a game
	 * @param dictFilename
	 */
	public Absurdle(String dictFilename) {
		super(dictFilename);
		this.wordMap = new HashMap<String, ArrayList<String>>();
		for (int i = 0; i < wordList.size(); i++) {
			if (this.correctWord.length() == this.wordList.get(i).length())	{
				this.words.add(this.wordList.get(i));
			}
		}
	}
	
	/**
	 * Returns the correct word for the game
	 * @return - the word the user is trying to guess
	 */
	public String getCorrectWord()	{
		Random rand = new Random();
		int nextRand = rand.nextInt(this.words.size());
		this.correctWord = this.words.get(nextRand);
		this.correctChars = this.correctWord.toCharArray();
		return this.correctWord;
	}
	
	/**
	 * Takes a players guess and then returns the response for that guess. 
	 * The guess must be a word in the dictionary that is the same length 
	 * as the actual word. Based on the word, the boxes for each letter 
	 * of the guess are determined (G = Green, Y = Yellow, N = Gray). If a 
	 * guess has duplicate letters, the letter will only light up as many times 
	 * as it occurs in the word, with green boxes getting priority over yellow boxes.
	 * @param word - the word to guess
	 * @return - If it is a valid guess, it will return a string of G, Y, or N 
	 * representing the box color for each guess ; If it is not a valid guess, it will 
	 * return a message that starts Error: and then details the issue with the guess
	 */
	public String makeGuess(String word) {
		this.guessChars = word.toCharArray();
		if (this.correctWord.toCharArray().length == this.guessChars.length)	{
			for (int j = 0; j < this.words.size(); j++)	{
				this.correctWord = this.words.get(j);
				this.guessResult = "";
				this.guessChars = word.toCharArray();
				this.correctChars = this.words.get(j).toCharArray();
				super.runOne();
				if (this.wordMap.containsKey(this.guessResult))	{
					this.wordMap.get(this.guessResult).add(this.words.get(j));
				}
				else {
					this.wordMap.put(this.guessResult, new ArrayList<String>());
					this.wordMap.get(this.guessResult).add(this.words.get(j));
				}
			}
			String mapKey = "";
			ArrayList<String> mapValues = new ArrayList<String>();
			int valueLength = 0;
			for (Map.Entry<String, ArrayList<String>> set: this.wordMap.entrySet()) {
				if (set.getValue().size() > valueLength)	{
					valueLength = set.getValue().size();
					mapKey = set.getKey();
					mapValues = set.getValue();
				}
			}
			this.words = mapValues;
			this.letterString = mapKey.toString();
			this.wordMap.clear();
			this.guessesLeft-=1;
		}
		else	{
			//throw error
			return "Error, enter a word with " + this.correctWord.length() + " letters";
		}
		return this.letterString;
		
	}
	
	/**
	 * Determines whether the game is over. A game is considered over when the player 
	 * has no guesses remaining or has correctly guessed the word
	 * @return - true if the game is over, false otherwise
	 */
	public boolean gameOver()	{
		if (this.guessesRemaining() == 0 || (this.words.size() == 1 && this.currGuess.toString().equals(this.correctWord))) {
			return true;
		}
		return false;
	}
	
	/**
	 * Resets for a new game of Wordle, selecting a new word and resetting the guesses 
	 * remaining
	 */
	public void reset()	{
		this.guessesLeft = 6;
		if (this.gameOver() == true) {
			this.words = this.wordListCopy;
		}
	}

}

