package rs.ac.metropolitan.cs330_pz.domain.use_case.word_game_statistic.get_statistics_for_user

import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic
import rs.ac.metropolitan.cs330_pz.domain.repository.WordGameStatisticRepository
import javax.inject.Inject

class GetStatisticsForUserUseCase @Inject constructor(
    private val repository: WordGameStatisticRepository
) {
    suspend operator fun invoke(userId: Int): List<WordGameStatistic> {
        return repository.getStatisticsForUser(userId)
    }
}