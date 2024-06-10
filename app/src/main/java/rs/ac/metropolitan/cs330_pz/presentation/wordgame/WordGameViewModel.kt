package rs.ac.metropolitan.cs330_pz.presentation.wordgame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic
import rs.ac.metropolitan.cs330_pz.domain.use_case.word_game_statistic.insert.InsertWordGameStatisticUseCase
import rs.ac.metropolitan.cs330_pz.domain.use_case.word_use_case.fetch_use_case.FetchWordsUseCase

class WordGameViewModel(
    private val fetchWordsUseCase: FetchWordsUseCase,
    private val insertWordGameStatisticUseCase: InsertWordGameStatisticUseCase
) : ViewModel() {
    var words by mutableStateOf(listOf<String>())
    var userGuesses by mutableStateOf(listOf<String>())
    var incorrectGuesses by mutableStateOf(listOf<String>())
    var score by mutableStateOf(0)

    fun fetchWords(number: Int) {
        viewModelScope.launch {
            words = fetchWordsUseCase(number)
        }
    }

    fun addUserGuess(guess: String) {
        userGuesses = userGuesses + guess
        if (guess !in words) {
            incorrectGuesses = incorrectGuesses + guess
        }
        checkScore()
    }

    fun checkScore() {
        score = userGuesses.count { it in words }
    }

    fun saveStatistic(userId: Int, numberOfGuessingWords: Int) {
        val statistic = WordGameStatistic(
            userId = userId,
            numberOfGuessingWords = numberOfGuessingWords,
            score = score
        )
        viewModelScope.launch {
            insertWordGameStatisticUseCase(statistic)
        }
    }
}
