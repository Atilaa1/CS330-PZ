package rs.ac.metropolitan.cs330_pz.presentation.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rs.ac.metropolitan.cs330_pz.data.db.entity.FlipCardStatistic
import rs.ac.metropolitan.cs330_pz.data.repository.FlipCardStatisticRepository

class FlipCardStatisticViewModel(private val repository: FlipCardStatisticRepository) : ViewModel() {
    private val _statistics = MutableStateFlow<List<FlipCardStatistic>>(emptyList())
    val statistics: StateFlow<List<FlipCardStatistic>> = _statistics

    fun loadStatistics(userId: Int) {
        viewModelScope.launch {
            _statistics.value = repository.getStatisticsByUserId(userId)
        }
    }
}
