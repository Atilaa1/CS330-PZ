package rs.ac.metropolitan.cs330_pz.presentation.flipcardgame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rs.ac.metropolitan.cs330_pz.R
import rs.ac.metropolitan.cs330_pz.data.db.entity.FlipCardStatistic
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.domain.use_case.flipcard_use_case.insert_statistic.InsertStatisticUseCase

class FlipCardGameViewModel(
    private val insertStatisticUseCase: InsertStatisticUseCase
) : ViewModel() {
    private val images = listOf(
        R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
        R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4
    )

    var cardImages by mutableStateOf(images.shuffled())
    var revealedCards by mutableStateOf(setOf<Int>())
    var matchedCards by mutableStateOf(setOf<Int>())
    var score by mutableStateOf(0)
    var gameStarted by mutableStateOf(false)
    var clickCount by mutableStateOf(0)
    var timeElapsed by mutableStateOf(0)
    var currentUser: User? = null
    var showDialog by mutableStateOf(false)
    private var selectedCards by mutableStateOf(listOf<Int>())

    fun startGame(user: User) {
        currentUser = user
        cardImages = images.shuffled()
        revealedCards = setOf()
        matchedCards = setOf()
        score = 0
        clickCount = 0
        timeElapsed = 0
        gameStarted = true
        showDialog = false

        viewModelScope.launch {
            while (gameStarted) {
                delay(1000)
                timeElapsed++
            }
        }
    }

    fun flipCard(index: Int) {
        if (revealedCards.contains(index) || matchedCards.contains(index)) return

        revealedCards = revealedCards + index
        clickCount++

        if (selectedCards.size == 1) {
            val previousIndex = selectedCards[0]
            if (cardImages[previousIndex] == cardImages[index]) {
                matchedCards = matchedCards + previousIndex + index
                score++
                selectedCards = listOf()

                if (matchedCards.size == cardImages.size) {
                    viewModelScope.launch {
                        delay(1000)
                        gameStarted = false
                        saveStatistic()
                        showDialog = true
                    }
                }
            } else {
                viewModelScope.launch {
                    delay(1000)
                    revealedCards = revealedCards - previousIndex - index
                    selectedCards = listOf()
                }
            }
        } else {
            selectedCards = listOf(index)
        }
    }

    private fun saveStatistic() {
        currentUser?.let {
            val statistic = FlipCardStatistic(
                userId = it.id!!,
                timeElapsed = timeElapsed,
                clickCount = clickCount
            )
            viewModelScope.launch {
                insertStatisticUseCase(statistic)
            }
        }
    }
}
