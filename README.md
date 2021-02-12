# Goblin-Game
Game similar to hangman written in Java. 
Completed in my Data structure course in GMU under Professor Russell.

## Overview
Goblin-Game provides a command-line user interface where the user is prompted to guess a word from a given list of words. The user is only allowed one character at a time but the goblin will only allow two wrong guesses. If the user guesses wrong twice then the goblin wins. Otherwise, if the user guesses all the characters correct then the user wins. Here is a walkthrough of how the game functions.

Goblin:   I'm thinking of a six letter word from this list: peanut, slower, faster, parcel. 
          You can guess one letter at a time and I'll tell you if it's in the world and where. 
          You can only gues wron twice.
          
           The algorithm will NOT choose a word rather it will wait for an input from the user
            and decide which words to pick
User:     "l" 
Goblin:   No! One wrong guess left!
          
           The goblin will see in his list of words and decided there were more words without an "l". So he's
            he's going to use one of the following: peanut, packet, faster
            
User:     "p"
Goblin:   Yeah, there'sa "p" here: p-----

           The goblin looks at his list of words and decided that there were more words with p in the first
            posisition. So he's going to use one of the following: peanut, packet.
            
User:     "n"
Goblin:   Nope! I win!

Game ends.

## Sample Run

          $ java GoblinGame ..\dictionary-mini.txt 6 2
          Goblin says "Guess a letter": l
          Goblin says "No dice! 1 wrong guesses left..."
          Goblin says "Guess a letter": p
          Goblin says "Yeah, yeah, it's like this: p-----"
          Goblin says "Guess a letter": n
          Goblin says "No dice! 0 wrong guesses left...."
          Goblin syas "I win! I was thinkin of the word 'packet'
          Your stuff is all mine... I'll come back for more soon!"

