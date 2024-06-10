

package rs.ac.metropolitan.cs330_pz.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rs.ac.metropolitan.cs330_pz.data.repository.UserRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.get_user_by_id.GetUserByIdUseCase
import rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.get_user_by_username_and_password.GetUserByUsernameAndPasswordUseCase

class LoginViewModelFactory(
    private val getUserByUsernameAndPasswordUseCase: GetUserByUsernameAndPasswordUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(getUserByUsernameAndPasswordUseCase, getUserByIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}