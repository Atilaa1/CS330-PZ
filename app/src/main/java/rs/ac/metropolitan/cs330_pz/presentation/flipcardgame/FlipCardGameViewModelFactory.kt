package rs.ac.metropolitan.cs330_pz.presentation.flipcardgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rs.ac.metropolitan.cs330_pz.data.repository.FlipCardStatisticRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.use_case.flipcard_use_case.insert_statistic.InsertStatisticUseCase

class FlipCardGameViewModelFactory(
    private val insertStatisticUseCase: InsertStatisticUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlipCardGameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlipCardGameViewModel(insertStatisticUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

