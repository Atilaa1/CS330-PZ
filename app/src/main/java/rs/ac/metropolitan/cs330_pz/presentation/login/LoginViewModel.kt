package rs.ac.metropolitan.cs330_pz.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.get_user_by_id.GetUserByIdUseCase
import rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.get_user_by_username_and_password.GetUserByUsernameAndPasswordUseCase

class LoginViewModel(
    private val getUserByUsernameAndPasswordUseCase: GetUserByUsernameAndPasswordUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    fun getUserByUsernameAndPassword(username: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = getUserByUsernameAndPasswordUseCase(username, password)
            onResult(user)
        }
    }
     fun fetchUserById(userId: Int): User? {
        return getUserByIdUseCase(userId)
    }
}