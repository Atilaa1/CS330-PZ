package rs.ac.metropolitan.cs330_pz.domain.use_case.flipcard_use_case.get_statistic_by_user_id

import rs.ac.metropolitan.cs330_pz.data.db.entity.FlipCardStatistic
import rs.ac.metropolitan.cs330_pz.domain.repository.FlipCardStatisticRepository
import javax.inject.Inject

class GetStatisticByUserIdUseCase @Inject constructor(
    private val repository: FlipCardStatisticRepository
) {
    suspend operator fun invoke(userId: Int): List<FlipCardStatistic> {
        return repository.getStatisticsByUserId(userId)
    }
}