// -----------------------------------------------------
// Assignment 3 Question 1
// Written by: Eli Samuel 40122277
// For COMP 248 Section EC B - November 4, 2019
// -----------------------------------------------------

/* This program creates a password by concatenating 3 random words taken from a few pages
* in the book "Alice in Wonderland" by Lewis Carroll. The program chooses 3 words randomly, checks
* checks them for newline and/or if they are only single character. Then, the program checks to
* see if the words are the same, if the length of the password is between 8 and 16 characters long,
* if the password contains and uppercase and lowercase character, and if the password contains one
* special (non-alphabetic) character. If any of these conditions are failed, the new words have to be
* chosen to make another password. If all these conditions are passed, the program then prints out
* the password along with the number of conditions which failed in the previous password generation
* attempts */

import java.util.Random;

public class A3Q1 {

	public static void main(String[] args) {
		int pageNum = 0, paraNum = 0, lineNum = 0;		// The random numbers to be used in the 3D array to find a word are saved here
		int conditionCheck = 0;							// This number represents the amount of conditions passed by the password
		int newLineChar=0, singleCharWord=0, sameWord=0, uppercase=0, lengthReq=0, lowercase=0, specialChar=0;	// Integers to keep track of conditions
		Random rand = new Random();						// Random number variable
		String password = "";							// String to store the password
		String[][] words = new String[3][];				// Array of words from the random line chosen from the book
		int[] wordNum = new int[3];						// Array of positions of words in the words[] array
		final String[][][] book = {						//http://www.gutenberg.org/cache/epub/19033/pg19033.txt
			{
				{"ALICE was beginning to get very tired of sitting by her sister on the\n",
				"bank, and of having nothing to do. Once or twice she had peeped into the\n",
				"book her sister was reading, but it had no pictures or conversations in\n",
				"it, \"and what is the use of a book,\" thought Alice, \"without pictures or\n",
				"conversations?\"\n"},
				{"So she was considering in her OWN mind (as well as she could, for the\n",
				"day made her feel very sleepy and stupid), whether the pleasure of\n",
				"making a daisy-chain would be worth the trouble of getting up and\n",
				"picking the daisies, when suddenly a White Rabbit with pink eyes ran\n",
				"close by her.\n"},
				{"There was nothing so very remarkable in that, nor did Alice think it so\n",
				"very much out of the way to hear the Rabbit say to itself, \"Oh dear! Oh\n",
				"dear! I shall be too late!\" But when the Rabbit actually took a watch\n",
				"out of its waistcoat-pocket and looked at it and then hurried on, Alice\n",
				"started to her feet, for it flashed across her mind that she had never\n",
				"before seen a rabbit with either a waistcoat-pocket, or a watch to take\n",
				"out of it, and, burning with curiosity, she ran across the field after\n",
				"it and was just in time to see it pop down a large rabbit-hole, under\n",
				"the hedge. In another moment, down went Alice after it!"}
			}, //3 lines
			{
				{"\"WHAT a curious feeling!\" said Alice. \"I must be shutting up like a\n",
				"telescope!\"\n"},
				{"And so it was indeed! She was now only ten inches high, and her face\n",
				"brightened up at the thought that she was now the right size for going\n",
				"through the little door into that lovely garden.\n"},
				{"After awhile, finding that nothing more happened, she decided on going\n",
				"into the garden at once; but, alas for poor Alice! When she got to the\n",
				"door, she found she had forgotten the little golden key, and when she\n",
				"went back to the table for it, she found she could not possibly reach\n",
				"it: she could see it quite plainly through the glass and she tried her\n",
				"best to climb up one of the legs of the table, but it was too slippery,\n",
				"and when she had tired herself out with trying, the poor little thing\n",
				"sat down and cried.\n"},
				{"\"Come, there's no use in crying like that!\" said Alice to herself rather\n",
				"sharply. \"I advise you to leave off this minute!\" She generally gave\n",
				"herself very good advice (though she very seldom followed it), and\n",
				"sometimes she scolded herself so severely as to bring tears into her\n",
				"eyes.\n"},
				{"Soon her eye fell on a little glass box that was lying under the table:\n",
				"she opened it and found in it a very small cake, on which the words \"EAT\n",
				"ME\" were beautifully marked in currants. \"Well, I'll eat it,\" said\n",
				"Alice, \"and if it makes me grow larger, I CAN reach the key; and if it\n",
				"makes me grow smaller, I can creep under the door: so either way I'll\n",
				"get into the garden, and I don't care which happens!\"\n"},
				{"She ate a little bit and said anxiously to herself, \"Which way? Which\n",
				"way?\" holding her hand on the top of her head to feel which way she was\n",
				"growing; and she was quite surprised to find that she remained the same\n",
				"size. So she set to work and very soon finished off the cake."}
			}, // 6 lines
			{
				{"The King and Queen of Hearts were seated on their throne when they\n",
				"arrived, with a great crowd assembled about them--all sorts of little\n",
				"birds and beasts, as well as the whole pack of cards: the Knave was\n",
				"standing before them, in chains, with a soldier on each side to guard\n",
				"him; and near the King was the White Rabbit, with a trumpet in one hand\n",
				"and a scroll of parchment in the other. In the very middle of the court\n",
				"was a table, with a large DISH of tarts upon it. \"I wish they'd get the\n",
				"trial done,\" Alice thought, \"and hand 'round the refreshments!\"\n"},
				{"The judge, by the way, was the King and he wore his crown over his great\n",
				"wig. \"That's the jury-box,\" thought Alice; \"and those twelve creatures\n",
				"(some were animals and some were birds) I suppose they are the jurors.\"\n"},
				{"Just then the White Rabbit cried out \"Silence in the court!\"\n"},
				{"\"HERALD! read the accusation!\" said the King."}
			} // 4 lines
		};
		int passwordsGen = 0; 							// Number passwords generated

		// This loop goes for 10000 iterations (to get 10000 passwords)
		for (passwordsGen=0;passwordsGen<10000;passwordsGen++) {
			// Resets all the condition checkers, and empties the password so that it can be regenerated
			newLineChar=0; singleCharWord=0; sameWord=0; uppercase=0; lengthReq=0; lowercase=0; specialChar=0; conditionCheck=0; password = "";

			// This loop is only broken once all the conditions are passed
			while (true) {

				/* This loop chooses 3 random numbers to signify page, paragraph, and line number, then uses
				* takes the line that that gives, finds a random word in it, checks it for newline characters
				* and if the word is less than 2 letters long. If any of these conditions are failed, the program
				* assigns false to the passGood boolean, which means that it won't check for other conditions,
				* and then restarts the for loop to choose different random words. If both of these conditions are
				* passed, the program assigns true to the passGood boolean, and concatenates that word to the
				* existing password. Once this is done successfully 3 times, the loop ends and the rest of the
				* conditions are checked */
				for (int j=0;j<3;j++) {
					// the loop runs until it finds a word that passes the conditions
					while (true) {
						// Random numbers based on the length of the dimension of that array are generated
						pageNum = rand.nextInt(book.length);
						paraNum = rand.nextInt(book[pageNum].length);
						lineNum = rand.nextInt(book[pageNum][paraNum].length);

						// Split the line into an array of words and saves that line for later use
						words[j] = book[pageNum][paraNum][lineNum].split(" ");

						// Creates a random integer to choose a word from the previously chosen line
						wordNum[j] = rand.nextInt(words[j].length);

						// Checks to marks if the word contains a newline character
						if (words[j][wordNum[j]].contains("\n")) newLineChar++;

						// Checks and marks if the word is less than 2 letters long
						if (words[j][wordNum[j]].length() <= 1 ) singleCharWord++;

						// If the word passes all the tests, break out of the while loop
						if (!(words[j][wordNum[j]].contains("\n")) && !(words[j][wordNum[j]].length() <= 1)) break;
					}
					// Since the word is valid, concatenate it to the existing password
					password = password.concat(words[j][wordNum[j]]);
				}

				// Checks to see if any of the words are the same
				if (words[0][wordNum[0]].equals(words[1][wordNum[1]]) || words[0][wordNum[0]].equals(words[2][wordNum[2]]) || words[1][wordNum[1]].equals(words[2][wordNum[2]])) sameWord++;
				else conditionCheck++;

				// Checks to see if the password length meets the reqirements
				if (password.length() >= 16 || password.length() <= 8) lengthReq++;
				else conditionCheck++;

				// Checks to see if the password contains an uppercase letter
				if (password.equals(password.toLowerCase())) uppercase++;
				else conditionCheck++;

				// Checks to see if the password contains an lowercase letter
				if (password.equals(password.toUpperCase())) lowercase++;
				else conditionCheck++;

				int sChar = 0;		// Integer to keep track of the number of special characters in the password

				// Checks each password of the letter for a special character. If it finds one, increment sChar by 1
				for (int i=0; i<password.length(); i++) {
					if (password.charAt(i) < 'A') sChar++;
				}

				// Checks to see if there are 0 or more than 1 special characters
				if (sChar != 1) specialChar++;
				else conditionCheck++;

				// Checks to see if all the conditions have been met, if not, restart the password process
				if (conditionCheck == 5) break;
				else {
					conditionCheck = 0;
					password = "";
				}
			}
			// Printing the password and all the failed conditions
			System.out.print(String.format("%s", "\nPassword = " + password)
							+ String.format("%17s", "\tNewLine = " + newLineChar)
							+ String.format("%s", "\tSingle = " + singleCharWord)
							+ String.format("%s", "\tEqual = " + sameWord)
							+ String.format("%s", "\tLength = " + lengthReq)
							+ String.format("%s", "\tUpper = " + uppercase)
							+ String.format("%s", "\tLower = " + lowercase)
							+ String.format("%s", "\tSpecial = " + specialChar));

			// Checks to see if the lowercase condition not met (contains a lower case character), if so, then stop making passwords
			if (lowercase != 0) break;
		}

		// Print the number of passwords generated and closing message
		System.out.println("\n\nTotal passwords generated: " + passwordsGen);
		System.out.println("Thanks for using the password generator. Goodbye.");

	}
}
