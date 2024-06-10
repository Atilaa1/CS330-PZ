package rs.ac.metropolitan.cs330_pz.presentation.wordGameStatistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic
import rs.ac.metropolitan.cs330_pz.data.repository.WordGameStatisticRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.use_case.word_game_statistic.get_statistics_for_user.GetStatisticsForUserUseCase
import rs.ac.metropolitan.cs330_pz.domain.use_case.word_game_statistic.insert.InsertWordGameStatisticUseCase

class WordGameStatisticViewModel(
    private val insertWordGameStatisticUseCase: InsertWordGameStatisticUseCase,
    private val getStatisticsForUserUseCase: GetStatisticsForUserUseCase
) : ViewModel() {
    fun insertStatistic(statistic: WordGameStatistic) {
        viewModelScope.launch {
            insertWordGameStatisticUseCase(statistic)
        }
    }

    fun getStatisticsForUser(userId: Int): Flow<List<WordGameStatistic>> = flow {
        val statistics = getStatisticsForUserUseCase(userId)
        emit(statistics)
    }
}