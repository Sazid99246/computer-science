# Problem Set 2, hangman.py
# Name:
# Collaborators:
# Time spent:

import random
import string

# -----------------------------------
# HELPER CODE
# -----------------------------------

WORDLIST_FILENAME = "words.txt"


def load_words():
    """
    returns: list, a list of valid words. Words are strings of lowercase letters.

    Depending on the size of the word list, this function may
    take a while to finish.
    """
    print("Loading word list from file...")
    # inFile: file
    inFile = open(WORDLIST_FILENAME, 'r')
    # line: string
    line = inFile.readline()
    # wordlist: list of strings
    wordlist = line.split()
    print(" ", len(wordlist), "words loaded.")
    return wordlist


def choose_word(wordlist):
    """
    wordlist (list): list of words (strings)

    returns: a word from wordlist at random
    """
    return random.choice(wordlist)

# -----------------------------------
# END OF HELPER CODE
# -----------------------------------


# Load the list of words to be accessed from anywhere in the program
wordlist = load_words()


def has_player_won(secret_word, letters_guessed):
    """
    secret_word: string, the lowercase word the user is guessing
    letters_guessed: list (of lowercase letters), the letters that have been
        guessed so far

    returns: boolean, True if all the letters of secret_word are in letters_guessed,
        False otherwise
    """
    for letter in secret_word:
        if letter not in letters_guessed:
            return False
    return True


def get_word_progress(secret_word, letters_guessed):
    """
    secret_word: string, the lowercase word the user is guessing
    letters_guessed: list (of lowercase letters), the letters that have been
        guessed so far

    returns: string, comprised of letters and asterisks (*) that represents
        which letters in secret_word have not been guessed so far
    """

    resulted_str = ""
    for letter in secret_word:
        if letter in letters_guessed:
            resulted_str += letter
        else:
            resulted_str += "*"
    return resulted_str


def get_available_letters(letters_guessed):
    """
    letters_guessed: list (of lowercase letters), the letters that have been
        guessed so far

    returns: string, comprised of letters that represents which
      letters have not yet been guessed. The letters should be returned in
      alphabetical order
    """

    letters_remained = ""
    for letter in string.ascii_lowercase:
        if letter not in letters_guessed:
            letters_remained += letter
    return letters_remained


def choose_revealed_letter(secret_word, available_letters):
    """
    secret_word: string
    available_letters: string

    returns: one random letter from secret_word that is still available
    """

    choose_from = ""

    for letter in secret_word:
        if letter in available_letters and letter not in choose_from:
            choose_from += letter

    new = random.randint(0, len(choose_from) - 1)
    revealed_letter = choose_from[new]

    return revealed_letter


def hangman(secret_word, with_help):
    """
    secret_word: string, the secret word to guess.
    with_help: boolean, enables help functionality if True.
    """

    guesses = 10
    letters_guessed = []

    print("Welcome to Hangman!")
    print(f"I am thinking of a word that is {len(secret_word)} letters long.")

    while guesses > 0:

        # win check
        if has_player_won(secret_word, letters_guessed):

            unique_letters = ""

            for letter in secret_word:
                if letter not in unique_letters:
                    unique_letters += letter

            total_score = (
                guesses +
                (4 * len(unique_letters)) +
                (3 * len(secret_word))
            )

            print("--------------")
            print("Congratulations, you won!")
            print(f"Your total score for this game is: {total_score}")
            return

        print("--------------")
        print(f"You have {guesses} guesses left.")
        print(f"Available letters: {get_available_letters(letters_guessed)}")

        guess = input("Please guess a letter: ").lower()

        # help functionality
        if with_help and guess == "!":

            if guesses < 3:
                print(
                    f"Oops! Not enough guesses left: "
                    f"{get_word_progress(secret_word, letters_guessed)}"
                )

            else:
                available_letters = get_available_letters(letters_guessed)

                revealed_letter = choose_revealed_letter(
                    secret_word,
                    available_letters
                )

                letters_guessed.append(revealed_letter)

                guesses -= 3

                print(f"Letter revealed: {revealed_letter}")
                print(get_word_progress(secret_word, letters_guessed))

            continue

        # invalid input
        if len(guess) != 1 or not guess.isalpha():

            print(
                "Oops! That is not a valid letter. "
                f"Please input a letter from the alphabet: "
                f"{get_word_progress(secret_word, letters_guessed)}"
            )

            continue

        # already guessed
        if guess in letters_guessed:

            print(
                f"Oops! You've already guessed that letter: "
                f"{get_word_progress(secret_word, letters_guessed)}"
            )

            continue

        # add guess
        letters_guessed.append(guess)

        # correct guess
        if guess in secret_word:

            print(
                f"Good guess: "
                f"{get_word_progress(secret_word, letters_guessed)}"
            )

        # incorrect guess
        else:

            print(
                f"Oops! That letter is not in my word: "
                f"{get_word_progress(secret_word, letters_guessed)}"
            )

            if guess in "aeiou":
                guesses -= 2
            else:
                guesses -= 1

    print("--------------")
    print(f"Sorry, you ran out of guesses. The word was {secret_word}.")


# When you've completed your hangman function, scroll down to the bottom
# of the file and uncomment the lines to test
if __name__ == "__main__":
    # To test your game, uncomment the following three lines.

    secret_word = choose_word(wordlist)
    with_help = False
    hangman(secret_word, with_help)

    # After you complete with_help functionality, change with_help to True
    # and try entering "!" as a guess!

    ###############

    # SUBMISSION INSTRUCTIONS
    # -----------------------
    # It doesn't matter if the lines above are commented in or not
    # when you submit your pset. However, please run ps2_student_tester.py
    # one more time before submitting to make sure all the tests pass.
    pass
