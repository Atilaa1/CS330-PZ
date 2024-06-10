package rs.ac.metropolitan.cs330_pz.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.insert.InsertUserUseCase

class RegisterViewModelFactory(
    private val insertUserUseCase: InsertUserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(insertUserUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
