package rs.ac.metropolitan.cs330_pz.presentation.wordgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rs.ac.metropolitan.cs330_pz.data.repository.WordGameStatisticRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.use_case.word_game_statistic.insert.InsertWordGameStatisticUseCase
import rs.ac.metropolitan.cs330_pz.domain.use_case.word_use_case.fetch_use_case.FetchWordsUseCase

class WordGameViewModelFactory(
    private val fetchWordsUseCase: FetchWordsUseCase,
    private val insertWordGameStatisticUseCase: InsertWordGameStatisticUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordGameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordGameViewModel(fetchWordsUseCase, insertWordGameStatisticUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}