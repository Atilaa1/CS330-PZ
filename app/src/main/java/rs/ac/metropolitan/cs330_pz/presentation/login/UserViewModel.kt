package rs.ac.metropolitan.cs330_pz.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.repository.UserRepository

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun insert(user: User, onResult: (Long) -> Unit) {
        viewModelScope.launch {
            val userId = repository.insert(user)
            onResult(userId)
        }
    }

    fun getUserByUsernameAndPassword(username: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByUsernameAndPassword(username, password)
            onResult(user)
        }
    }

    suspend fun getUserById(userId: Int): User? {
        return withContext(Dispatchers.IO) {
            repository.getUserById(userId)
        }
    }
    suspend fun fetchUserById(userId: Int): User? {
        return repository.getUserById(userId)
    }
}