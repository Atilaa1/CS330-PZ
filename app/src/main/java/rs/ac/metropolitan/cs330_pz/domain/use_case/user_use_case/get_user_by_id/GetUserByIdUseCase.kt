package rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.get_user_by_id

import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: Int): User? {
        return repository.getUserById(userId)
    }
}