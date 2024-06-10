package rs.ac.metropolitan.cs330_pz.domain.use_case.flipcard_use_case.insert_statistic

import rs.ac.metropolitan.cs330_pz.data.db.entity.FlipCardStatistic
import rs.ac.metropolitan.cs330_pz.domain.repository.FlipCardStatisticRepository
import javax.inject.Inject

class InsertStatisticUseCase @Inject constructor(
    private val repository: FlipCardStatisticRepository
){
    suspend operator fun invoke(statistic: FlipCardStatistic) {
        repository.insertStatistic(statistic)
    }
}