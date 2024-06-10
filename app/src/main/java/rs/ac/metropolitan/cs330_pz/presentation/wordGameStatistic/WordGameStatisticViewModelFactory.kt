package rs.ac.metropolitan.cs330_pz.presentation.wordGameStatistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rs.ac.metropolitan.cs330_pz.data.repository.WordGameStatisticRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.use_case.word_game_statistic.get_statistics_for_user.GetStatisticsForUserUseCase
import rs.ac.metropolitan.cs330_pz.domain.use_case.word_game_statistic.insert.InsertWordGameStatisticUseCase

class WordGameStatisticViewModelFactory(
    private val insertWordGameStatisticUseCase: InsertWordGameStatisticUseCase,
    private val getStatisticsForUserUseCase: GetStatisticsForUserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordGameStatisticViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordGameStatisticViewModel(insertWordGameStatisticUseCase, getStatisticsForUserUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}