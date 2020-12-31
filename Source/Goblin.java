import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The Goblin class holds the algorithm of the goblin game.
 * the game is reading a file of words, and asks the user to enter a character.
 * The goblin chooses the greatest number of words with either the character in a specific position
 * or the list without the character.
 * Improvising every input.
 * Uses BetterArray as the data structure.
 * @author Andres Reyes
*/
class Goblin {

	/**
	 * Scanner userIn used for input.
	 */
	private Scanner userIn;

	/**
     * String used to hold the file name.
	 */
	private String dictFileName;

	/**
	 * holds the max letters.
	 */
	private int numLetters;

	/**
	 * holds the max guessed.
	 */
	private int numGuesses;

	//ADD MORE PRIVATE MEMBERS HERE IF NEEDED!

	/**
     * used as the goblins current dictionary.
     */
	private BetterArray<String> currentDictionary;

	/**
     * used to store the valid guesses.
     */
	private BetterArray<Character> guesses;


	/**
	 * locked is used to store correct characters guessed.
	 */
	private BetterArray<Character> locked;


	/**
	 * userRepeats is used to check to see if the user has repeated a character.
	 */
	private boolean userRepeats;


	/**
	 * answer is used to hold the user input.
	 */
	private String answer;


	/**
	 * The currentDictionary is a BetterArray that holds the words from the file read.
	 * Through out the class it will be updated, so calling getWords() gives the current copy of the dictionary.
	 *
	 * @return a copy of all the words in the current dictionary.
	 */
	public BetterArray<String> getWords(){
		/*
		Returns a _copy_ of the goblin's _current_ dictionary.
		*/
		return currentDictionary.clone();
	}

	/**
	 * getGuesses() returns the users input characters.
	 * It returns a new copy each time called.
	 *
	 * @return  a new copy of letters guessed.
	 */
	public BetterArray<Character> getGuesses() {
		/*
		Returns a _copy_ of the user's current guesses.
		*/

		return guesses.clone();
	}

	/**
	 * This method returns a copy of the array holding the word the goblin is thinking of.
	 * Each time the user guesses a correct letter, the corresponding spot will be replaced with that letter.
	 * If the user guesses one word it will return for example p----.
	 * where each dash is a null place.
	 *
	 * @return a copy of the current word to be guessed.
	 */
	public BetterArray<Character> getCurrentWord() {
		/*
		Returns a _copy_ of the letters the user has locked in.
		Any slots not locked in, should be set to null.
		*/

		int index = 0;
		BetterArray<Character> temp = locked.clone();
		//in order for the size of the BetterArray to be equal to capacity, append(null).
		if(guesses.size() == 0) {
			while (index < temp.capacity()) {
				//locked.add(index, null);
				temp.append(null);
				index++;
			}

			return temp;
		}else {
			return locked.clone();
		}
	}

	/**
	 * This method simply returns the value of numGuess which holds the max number of guesses the usr is allowed.
	 *
	 * @return number of wrong guesses left.
	 */
	public int getGuessesRemaining() {
		/*
		Returns the number of wrong guesses the user has left.
		*/
		
		return numGuesses;
	}

	/**
	 * This method initializes all the private members used throughout the game. It should be the first method called.
	 * from a new Goblin object.
	 * Goes through a series of checks before the game starts, and some messages are printed if there is something wrong.
	 *
	 * @return true if everything works or false if the file was not found.
	 */
	public boolean init() {
		/*
		Setup anything you need here. Check that the number of letters
		is valid (at least 2) and userIn in the dictionary. The dictionary
		contains main words which are not of the proper length, and many with
		words with duplicate letters, don't add these to your goblin's list
		of options!
		
		If the dictionary can't be found, print the appropriate message
		and return false. If the dictionary contains no words of the given
		length, print the appropriate message and return false. If everything
		works, return true;
		
		Here are some quotes:
			"Goblin can't find enough words! You win!"
			"Goblin lost his dictionary! You win!"
		
		Can't remember how to use a Scanner?
		See: https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html
		You'll want to use the following:
			- Scanner(File) <- constructor
			- hasNextLine()
			- nextLine()
		*/
		//initiate all the private members
		Boolean valid = false;
		answer ="a";
		guesses = new BetterArray<>();
		locked = new BetterArray<>(numLetters);
		currentDictionary = new BetterArray<>();


		//if the number of letters given is greater or equal to 2.
		if(numLetters >= 2) {
			try {
				File file = new File(dictFileName);
				Scanner read = new Scanner(file);
				while(read.hasNextLine())
				{
					String temp = read.nextLine();
					if(temp.length() == numLetters && (characterCheck(temp) == false)) {
						currentDictionary.append(temp);
					}
				}
				valid = true;
			}catch (FileNotFoundException e){
				System.out.println("Goblin lost his dictionary! You win!");
			}

		}else if(currentDictionary.get(0) == null){ // else if the dictionary is empty
			System.out.println("Goblin can't find enough words! You win!");
		}
		return valid;
	}

	/**
	 * bestPartition method always returns the list of words that contain the greatest number words.
	 * If the list of words chosen do not contain the character guessed by the user, the method returns -1.
	 * Else it will choose the list with words that contain the character, and returns the position of that letter.
	 *
	 * @param guess contains the character guessed by the user.
	 * @return either -1 if character not in words, or the index of the character in the word.
	 */
	public int bestPartition(char guess) {
		/*
		Divide (partition) the goblin's _current_ dictionary into X+1 lists where:
			X is the number of letter positions
			The +1 is for words where the letter doesn't occur
		
		Pick the biggest partition as the new dictionary and set that as the
		goblin's current dictionary. If the goblin picks a partition with the
		chosen letter (guess), return the position of that letter. If the goblin
		picks a partition without the chosen letter, return -1.
		
		If there are ties for partition size, choose the one with the earlier
		letter slot.
		*/

		BetterArray<BetterArray<String>> partition = new BetterArray<>(numLetters +1);
		initiate(partition);
		int index = 0;
		int position = 0;
		boolean valid = true;
		int check;
		int result;

		//loop goes through the entire goblin's dictionary.
		while(index < currentDictionary.size())
		{
			while(valid) {
				//check holds the position of where the character is in one word.
				//this will run one word at a time.
				//if check is -1, the character is not in the word. therefore we add it to index 0 of partition.
				//else it will be added to its according position.
				check = guessCheck(guess, currentDictionary.get(index));
				if (check == -1) {
					partition.get(0).append(currentDictionary.get(index));
					valid = false;
				} else if (check >- 1 ) {
					//since check contains index starting from 0, we have to add 1 to correctly place it in the index.
					partition.get(check+1).append(currentDictionary.get(index));
					valid = false;
				}else{
					valid = false;
				}
			}
			index++;
			valid = true;
		}

		// check which partition is the biggest.
		// set the current dictionary to the be the biggest partition.
		int biggest = 0;
		int i = 0;
		while(i < partition.capacity())
		{
			if(partition.get(i).size() > biggest){
				biggest = partition.get(i).size();
				position= i;
			}else if(partition.get(i).size() == biggest)
			{
				i++;
			}else {
				i++;
			}
		}
		//position holds the index of the biggest partition.
		if(position == 0)
		{
			currentDictionary = partition.get(0);
			result = -1;
		}else {
			currentDictionary = partition.get(position);
			result = position-1;
		}
		
		return result;
	}

	/**
	 * step() will be called inside a loop where the condition is the return of this method.
	 * It will return false only when the number of guesses is 0. Otherwise it will continue to be called.
	 * This method plays one round, testing for all conditions and outputting the correct messages.
	 * It will also ask user for input, which then is processed to see if the input is valid.
	 *
	 * @return true if the condition to keep playing still hold true, else it will return false if game should end.
	 */
	public boolean step() {
		/*
		Prompt and get a guess from the user.
		
		Check that the guess is valid (one letter which has not been
		guessed before). Give appropriate re-prompt message if not
		valid and get a new guess.
		
		Once you have a valid guess, partition the goblin's dictionary
		and choose a new set of words. The goblin should respond with
		the appropriate message and you need to update appropriate items
		you're tracking as well.
		
		Return true if the game should continue, or false if the game is over.
		
		Here are some quotes:
			"Goblin says \"Guess a letter\": "
			"Goblin says \"One letter! Guess a single letter\": "
			"Goblin says \"You already guessed: " + getGuesses().toString() + "\nGuess a new letter\": "
			"Goblin says \"No dice! "+getGuessesRemaining()+" wrong guesses left...\""
			"Goblin says \"Yeah, yeah, it's like this: "+ getCurrentWord().toString().replaceAll("null","-").replaceAll("[ ,]","")+"\"");
			
		Note: these two expressions:
			getGuesses().toString()
			getCurrentWord().toString().replaceAll("null","-").replaceAll("[ ,]","")
		are very inefficient. You can make them better if you want,
		but these should give you correct output.
		*/

		boolean lockedFull;
		boolean notDone = false;

		//check if the locked in answers complete the word.
		lockedFull = finished();
		if(numGuesses > 0 && !lockedFull){
			notDone = true;

			//answer holds "a" in order to enter the first if condition to ask for input.
			//from there no out, only the correct condition will be executed.
			//inside each statement the processUserInput checks if the answer is valid, calls bestPartition().
			// it does all processing with the user input.
			if(!userRepeats &&  !answer.isEmpty() && answer.length() == 1) {
				System.out.print("Goblin says \"Guess a letter\": ");
				answer = userIn.nextLine();
				processUserInput(answer, notDone);
			}else if(userRepeats ){
				System.out.print("Goblin says \"You already guessed: " +getGuesses().toString() +"\nGuess a new letter\": ");
				answer = userIn.nextLine();
				processUserInput(answer, notDone);
			}else if(answer.length() >1 && !answer.isEmpty()){
				System.out.print("Goblin says \"One letter! Guess a single letter\": ");
				answer = userIn.nextLine();
				processUserInput(answer, notDone);
			}else if(answer.isEmpty()){
				System.out.print("Goblin says \"One letter! Guess a single letter\": ");
				answer = userIn.nextLine();
				processUserInput(answer, notDone);
			}
		}else if(lockedFull){
			notDone = false;
		}
		return notDone;
	}

	/**
	 * this method determines if the user wins or looses.
	 * it is only called when the step() returns false.
	 *
	 */
	public void finish() {
		/*
		This will be called after step() returns false. Print the appropriate
		win/lose message here.
		
		Here are some quotes:
			"Goblin says \"You win... here's your stuff. But I'll get you next time!\""
			"Goblin says \"I win! I was thinking of the word '"+getWords().get(0)+"'"
			"Your stuff is all mine... I'll come back for more soon!\""
		*/
		if(numGuesses == 0) {
			System.out.print("Goblin says \"I win! I was thinking of the word '"+getWords().get(0)+"'\n"+
					"Your stuff is all mine... I'll come back for more soon!\"\n");
		}else {
			System.out.print("Goblin says \"You win... here's your stuff. But I'll get you next time!\"\n");
		}

	}

	/**
	 * checks if there are duplicate letters in the word.
	 *
	 * @param str holds the word.
	 * @return false if there are no duplicates, else true if there are duplicates.
	 */
	private boolean characterCheck(String str){
		boolean valid = false;
		for(int i = 0; i < str.length()-1; i++) {
			for(int j = i+1; j < str.length(); j++) {
				if(str.charAt(i) == str.charAt(j)) {
					valid = true;
				}
			}
		}
		return valid;
	}

	/**
	 * this method checks if the character guessed is in a word.
	 * returns -1 if there is no character in the word.
	 * else it will return the index of the the character in the word.
	 *
	 * @param guess holds the character guess by the user.
	 * @param str   holds the word to be checked.
	 * @return the index of where the character is in the word, or -1 if no in word.
	 */
	private int guessCheck(char guess, String str)
	{
		int result = -1;
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == guess) {
				result = i;
			}
		}
		return result;
	}

	/**
	 * This method receives a 2D BetterArray of BetterArray.
	 * At each index we need a new reference to a memory block of type BetterArray.
	 * Therefore we need to call new on a BetterArray inside a loop, save it to the inPartition at index i.
	 * set the temp to null before looping again so that the next time loop enters, it calls new again but with a different
	 * memory block.
	 *
	 * @param inPartition 2D BetterArray to filled with memory references.
	 */
	private void initiate(BetterArray<BetterArray<String>> inPartition)
	{
		int index = 0;
		while(index < inPartition.capacity())
		{
			//calls a new memory allocation for this BetterArray.
			BetterArray<String> temp = new BetterArray<>();
			inPartition.add(index, temp);
			temp = null; //sets it to null before exiting the loop.
			index++;
		}
	}

	/**
	 * This method checks if a character is inside a BetterArray chars.
	 * Used to check characters from user. It will return false if it is not inside.
	 * otherwise true if there exists a character equal to the parameter.
	 *
	 * @param character holds the character to check.
	 * @param chars holds a list of characters.
	 * @return  true if character already in list, otherwise false.
	 */
	private boolean characterInArray(char character, BetterArray<Character> chars){
		boolean repeated = false;
		int index = 0;
		if(chars.size()!= 0) {
			while(index < chars.size()) {
				if(character == '-' && chars.get(index) == character){
					repeated = false;
				}else if (chars.get(index) == character) {
					repeated = true;
				}
				index++;
			}
		}
		return repeated;
	}

	/**
	 * This method prints a "-" if and only if there is a null in the locked BetterArray.
	 * used a String builder to not use a '+' which is n^2.
	 * once the string holds for example p--c--.
	 * it will be printed.
	 */
	private void printLockedInChars(){
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < locked.capacity(); i++)
		{
			if(locked.get(i) == null){
				str.append("-");
			}else{
				str.append(locked.get(i));
			}
		}
		System.out.println("Goblin says \"Yeah, yeah, it's like this: "+ str.toString() +"\"");
	}

	/**
	 * This method processes the user input. Answer is checked if its not empty in order to process it.
	 * Then it is checked to see if the character is not already been guessed.
	 * if its not empty and not guessed before then it is added to user guesses BetterArray.
	 * bestPartition()is called when the answer is valid so that the currentDictionary now points to the biggest partition.
	 * receives input parameters by reference, in order to update their value from where they have been called.
	 *
	 * @param answer the answer from the user.
	 * @param notDone whether the step is done or not.
	 */
	private void processUserInput(String answer, boolean notDone){
		int getPartition;
		if(!answer.isEmpty() && answer.length() ==1) {
			//checks for repeated characters.
			userRepeats = characterInArray(answer.charAt(0), guesses);

			//if it's only one character, and not repeated. then we add and partition the dictionary.
			if (answer.length() == 1 && answer != "\n" && !userRepeats) {
				guesses.append(answer.charAt(0));
				getPartition = bestPartition(answer.charAt(0));
				if (getPartition > -1) {
					locked.replace(getPartition, answer.charAt(0));
					printLockedInChars();
				} else {
					//if getPartition is return witht -1 we know that the new dictionary does not hold that character.
					//in any word, therefore the user is wrong.
					numGuesses--;
					System.out.print("Goblin says \"No dice! " + getGuessesRemaining() + " wrong guesses left...\" \n");
					notDone = false;
				}
			}
		}else if(answer.length() > 1){
		    //makes sure the userRepeates returns false in order to not fall in the wrong if statement.
		    userRepeats = false;
			notDone = false;
		}
	}

	/**
	 * This method checks if the locked in BetterArray has been filled with values other than nulls.
	 * if it is full with real values, then the word has been completed.
	 * returns true if its complete otherwise it will return false.
	 *
	 * @return whether word is completed or not.
	 */
	private boolean finished(){
		boolean full = false;
		int counter = 0;
		for(int i = 0; i < locked.capacity(); i++) {
			if(locked.get(i) != null){
				counter++;
			}
		}

		if(counter == locked.capacity()){
			full= true;
		}

		return full;
	}

	/**
	 * constructor of the Goblin class is used when the command line arguments are added.
	 * @param dictFileName the name of the file.
	 * @param numLetters the number of letters of the words.
	 * @param numGuesses the max guesses of the user.
	 */
	// --------------------------------------------------------
	// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
	// --------------------------------------------------------
	public Goblin(String dictFileName, int numLetters, int numGuesses) {
		this.userIn = new Scanner(System.in);
		this.dictFileName = dictFileName;
		this.numLetters = numLetters;
		this.numGuesses = numGuesses;
	}

	/**
	 * main is used for testing the correctness of the methods written above.
	 * it can be edited as much as possible.
	 * @param args holds the command line arguments.
	 */
	// --------------------------------------------------------
	// example testing code... edit this as much as you want!
	// --------------------------------------------------------
	public static void main(String[] args) {
		//if you don't have the mini dictionary one folder above your
		//user folder, this won't work!
		
		/*
		Sample successful run with output:
			> java Goblin
			Goblin can't find enough words! You win!
			Goblin lost his dictionary! You win!
			Yay 1
			Yay 2
			Yay 3
			Yay 4
			Yay 5
		*/
		Goblin g1 = new Goblin("../dictionary-mini.txt", 3, 10);
		Goblin g2 = new Goblin("../dictionary-mini.txt", 6, 6);
		Goblin g3 = new Goblin("../dictionary.txt", 1, 6);
		Goblin g4 = new Goblin("banana.txt", 3, 3);


		if(g1.init() && g2.init() && !g3.init() && !g4.init()) {
			System.out.println("Yay 1");
		}
		
		if(g1.getGuessesRemaining() == 10 && g1.getWords().size() == 1
			&& g2.getGuessesRemaining() == 6 && g2.getWords().size() == 5
			&& g1.getGuesses().size() == 0 && g2.getCurrentWord().size() == 6) {
			System.out.println("Yay 2");
		}
		
		BetterArray<Character> g1word = g1.getCurrentWord();
		if(g1word.get(0) == null  && g1word.get(1) == null && g1word.get(2) == null) {
			System.out.println("Yay 3");
		}
		
		//remember what == does on objects... not the same as .equals()
		if(g1.getWords() != g1.getWords() && g1.getGuesses() != g1.getGuesses()
			&& g1.getCurrentWord() != g1.getCurrentWord()) {
			System.out.println("Yay 4");
		}
		
		if(g2.bestPartition('l') == -1 && g2.getWords().size() == 3
			&& g2.bestPartition('p') == 0 && g2.getWords().size() == 2
			&& g2.bestPartition('n') == -1 && g2.getWords().size() == 1) {
			System.out.println("Yay 5");
		}
		
		//can't test step() or finish() this way... requires user input!
		//maybe you need to test another way...
	}
}
