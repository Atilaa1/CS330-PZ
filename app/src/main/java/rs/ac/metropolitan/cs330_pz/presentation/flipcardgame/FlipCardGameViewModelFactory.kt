package rs.ac.metropolitan.cs330_pz.presentation.flipcardgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rs.ac.metropolitan.cs330_pz.data.repository.FlipCardStatisticRepository

class FlipCardGameViewModelFactory(
    private val repository: FlipCardStatisticRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlipCardGameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlipCardGameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}