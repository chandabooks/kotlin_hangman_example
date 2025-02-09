package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val words = listOf("carnation", "daisy", "shoeflower", "rose", "tulip", "sunflower", "poppy", "azalea", "hydrangea", "dahlia", "orchid", "marigold", "begonia")
    val view = HangmanView()
    val model = HangmanModel()
    val viewmodel = HangmanViewModel(model, view, words)
    viewmodel.play()
}

class HangmanModel() {
    var word: String = ""
    val maxAttempts = 6
    var wrongGuesses = 0
    var guessedLetters = mutableSetOf<Char>()
    var displayWord = CharArray(word.length) { '-' }

    fun reset(newWord:String) {
        word = newWord
        wrongGuesses = 0
        guessedLetters = mutableSetOf<Char>()
        displayWord = CharArray(word.length) { '-' }
    }

    fun guessLetter(letter: Char): Boolean {
        if (letter in word) {
            word.forEachIndexed { index, c ->
                if (c == letter) {
                    displayWord[index] = letter
                }
            }
            return true
        } else {
            wrongGuesses++
            guessedLetters.add(letter)
            return false
        }
    }

    fun isGameOver(): Boolean {
        return wrongGuesses >= maxAttempts || !displayWord.contains('-')
    }

    fun isWordGuessed(): Boolean {
        return !displayWord.contains('-')
    }
}




class HangmanView {
    fun printWelcomeMessage() {
        println("Welcome to Hangman!")
        println("Guess the word:")
    }

    fun printBoard(displayWord: CharArray, wrongGuesses: Int, guessedLetters: Set<Char>, attempts: Int) {
        print("Guessed letters:[ ${guessedLetters.joinToString(", ")}]")
        println("Attempts Left: ${attempts - wrongGuesses}")
        println(displayWord.joinToString(" "))
    }

    fun printGameOverMessage(word: String, isWordGuessed: Boolean) {
        if (isWordGuessed) {
            println("Congratulations! You guessed the word: $word")
        } else {
            println("Game Over! The word was $word.")
        }
    }

    fun getGuess() : String? {
        print("Enter a letter: ")
        val input = readLine()?.uppercase()
        return input
    }

    fun getContinue(): Boolean {
        print("Play Another Game?(Y/N)")
        val input = readLine()?.uppercase()
        return (input == "Y")

    }
}

class HangmanViewModel(private val model: HangmanModel, private val view: HangmanView, val wordList:List<String>) {
    fun play() {
        do {
            var word = wordList.random().uppercase()
            model.reset(word)
            single_play()
        } while (view.getContinue())

    }
    fun single_play() {
        view.printWelcomeMessage()
        while (!model.isGameOver()) {
            view.printBoard(model.displayWord, model.wrongGuesses, model.guessedLetters, model.maxAttempts)
            val input = view.getGuess()
            if (input != null && input.isNotEmpty()) {
                model.guessLetter(input[0])
            }
        }
        view.printGameOverMessage(model.word, model.isWordGuessed())
    }
}




