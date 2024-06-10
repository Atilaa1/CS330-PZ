package rs.ac.metropolitan.cs330_pz.presentation.flipCardStatistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rs.ac.metropolitan.cs330_pz.data.repository.FlipCardStatisticRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.use_case.flipcard_use_case.get_statistic_by_user_id.GetStatisticByUserIdUseCase
import rs.ac.metropolitan.cs330_pz.presentation.statistics.FlipCardStatisticViewModel

class FlipCardStatisticViewModelFactory(private val getStatisticByUserIdUseCase: GetStatisticByUserIdUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlipCardStatisticViewModel::class.java)) {
            return FlipCardStatisticViewModel(getStatisticByUserIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}