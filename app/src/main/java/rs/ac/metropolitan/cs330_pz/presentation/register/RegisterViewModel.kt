package rs.ac.metropolitan.cs330_pz.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.insert.InsertUserUseCase


class RegisterViewModel(
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {

    fun insert(user: User, onResult: (Long) -> Unit) {
        viewModelScope.launch {
            val userId = insertUserUseCase(user)
            onResult(userId)
        }
    }
}