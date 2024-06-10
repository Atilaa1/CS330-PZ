package rs.ac.metropolitan.cs330_pz.presentation.wordgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rs.ac.metropolitan.cs330_pz.data.repository.WordGameStatisticRepository

class WordGameViewModelFactory(private val repository: WordGameStatisticRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordGameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordGameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}