package rs.ac.metropolitan.cs330_pz.presentation.flipCardStatistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rs.ac.metropolitan.cs330_pz.data.repository.FlipCardStatisticRepository
import rs.ac.metropolitan.cs330_pz.presentation.statistics.FlipCardStatisticViewModel

class FlipCardStatisticViewModelFactory(private val repository: FlipCardStatisticRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlipCardStatisticViewModel::class.java)) {
            return FlipCardStatisticViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}