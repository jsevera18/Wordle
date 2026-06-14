import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.lang.*;
import java.io.*;
import java.util.*;

/**
 * A class used to play a game of Wordle
 * @author Catie Baker, <Jimmy Severa>
 *
 */
public class Wordle {
	
	protected int guessesLeft = 6;
	protected String correctWord;
	protected String currGuess = "";
	protected ArrayList<String> wordList = new ArrayList<String>();
	protected ArrayList<String> wordListCopy = new ArrayList<String>();
	protected char[] correctChars;
	protected char[] guessChars;
	protected String guessResult = "";
	protected char currCharacter;
	
	/**
	 * Determines the dictionary of words to use for the game and then
	 * sets up a game
	 * @param dictFilename
	 */
	public  Wordle(String dictFilename)	{
		
		try {
			Scanner infile = new Scanner(new File(dictFilename));
			while (infile.hasNextLine())	{
				String nextWord = infile.nextLine();
				this.wordList.add(this.wordList.size(), nextWord);
			}
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("FILE NOT FOUND "+e);
		}
		Random rand = new Random();
		int nextRand = rand.nextInt(this.wordList.size());
		this.correctWord = this.wordList.get(nextRand);
		this.correctChars = this.correctWord.toCharArray();
		this.wordListCopy = this.wordList;
		System.out.println(correctWord);
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
		this.guessResult = "";
		this.currGuess = word;
		this.guessChars = word.toCharArray();
		this.correctChars = this.correctWord.toCharArray();
		
		if (this.correctChars.length == this.guessChars.length)	{
			this.runOne();
			this.guessesLeft-=1;
		}
		else {
			return "Error, enter a word with " + this.correctWord.length() + " letters";
		}
		
		return this.guessResult;
	}
	
	/**
	 * Determines whether the game is over. A game is considered over when the player 
	 * has no guesses remaining or has correctly guessed the word
	 * @return - true if the game is over, false otherwise
	 */
	public boolean gameOver()	{
		if (this.guessesRemaining() == 0 || this.currGuess.toString().equals(this.correctWord)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns how many guesses the player has left
	 * @return - the number of guesses remaining
	 */
	public int guessesRemaining(){
		return this.guessesLeft;
	}
	
	/**
	 * Resets for a new game of Wordle, selecting a new word and resetting the guesses 
	 * remaining
	 */
	public void reset()	{
		this.guessesLeft = 6;
	}
	
	/**
	 * Returns the correct word for the game
	 * @return - the word the user is trying to guess
	 */
	public String getCorrectWord()	{
		return this.correctWord;
	}
	
	/**
	 * a method for determining if the input words characters match the correct word
	 */
	public void runOne()	{
		for (int i = 0; i < this.guessChars.length; i++)	{
			this.currCharacter = this.guessChars[i];
			//compare the chars
			if (this.guessChars[i] == this.correctChars[i])	{
				this.guessChars[i] = '-';
				this.correctChars[i] = '-';
			}
		}
		for (int i = 0; i < this.guessChars.length; i++)	{
			this.currCharacter = this.guessChars[i];
			//compare the chars
			if (this.guessChars[i] == this.correctChars[i])	{
				this.guessResult = this.guessResult + 'G';
			}
			else if (this.guessChars[i] != this.correctChars[i] && this.correctChars.toString().contains(String.valueOf(this.currCharacter)))	{
				this.guessResult = this.guessResult + 'Y';
			}
			else	{
				this.guessResult = this.guessResult + 'N';
			}
		}
	}
}
