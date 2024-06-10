package rs.ac.metropolitan.cs330_pz.presentation.wordGameStatistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rs.ac.metropolitan.cs330_pz.data.repository.WordGameStatisticRepository

class WordGameStatisticViewModelFactory(
    private val repository: WordGameStatisticRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordGameStatisticViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordGameStatisticViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}