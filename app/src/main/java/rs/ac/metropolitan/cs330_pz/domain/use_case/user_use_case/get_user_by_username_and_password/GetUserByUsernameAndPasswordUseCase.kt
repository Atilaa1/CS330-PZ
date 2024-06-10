package rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.get_user_by_username_and_password

import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByUsernameAndPasswordUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String): User? {
        return repository.getUserByUsernameAndPassword(username, password)
    }
}