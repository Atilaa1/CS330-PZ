package rs.ac.metropolitan.cs330_pz.domain.use_case.word_game_statistic.insert

import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic
import rs.ac.metropolitan.cs330_pz.domain.repository.WordGameStatisticRepository
import javax.inject.Inject

class InsertWordGameStatisticUseCase @Inject constructor(
    private val repository: WordGameStatisticRepository
) {
    suspend operator fun invoke(statistic: WordGameStatistic) {
        repository.insert(statistic)
    }
}