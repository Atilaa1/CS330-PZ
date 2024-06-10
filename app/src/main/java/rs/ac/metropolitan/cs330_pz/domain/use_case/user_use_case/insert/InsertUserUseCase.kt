package rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.insert


import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.domain.repository.UserRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Long {
        return repository.insert(user)
    }
}