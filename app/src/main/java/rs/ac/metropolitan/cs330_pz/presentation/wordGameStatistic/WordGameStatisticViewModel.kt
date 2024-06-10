package rs.ac.metropolitan.cs330_pz.presentation.wordGameStatistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic
import rs.ac.metropolitan.cs330_pz.data.repository.WordGameStatisticRepository

class WordGameStatisticViewModel(private val repository: WordGameStatisticRepository) : ViewModel() {
    fun insertStatistic(statistic: WordGameStatistic) {
        viewModelScope.launch {
            repository.insert(statistic)
        }
    }

    fun getStatisticsForUser(userId: Int): Flow<List<WordGameStatistic>> = flow {
        val statistics = repository.getStatisticsForUser(userId)
        emit(statistics)
    }
}