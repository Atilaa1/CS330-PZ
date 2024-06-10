package rs.ac.metropolitan.cs330_pz.presentation.wordgame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rs.ac.metropolitan.cs330_pz.data.api.RandomWordApi
import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic
import rs.ac.metropolitan.cs330_pz.data.repository.WordGameStatisticRepository


class WordGameViewModel(private val repository:WordGameStatisticRepository) : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://random-word-api.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(RandomWordApi::class.java)

    var words by mutableStateOf(listOf<String>())
    var userGuesses by mutableStateOf(listOf<String>())
    var incorrectGuesses by mutableStateOf(listOf<String>())
    var score by mutableStateOf(0)

    fun fetchWords(number: Int) {
        viewModelScope.launch {
            words = api.getWords(number)
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
            repository.insert(statistic)
        }
    }
}
