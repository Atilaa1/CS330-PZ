package rs.ac.metropolitan.cs330_pz.data.repository

import rs.ac.metropolitan.cs330_pz.data.db.dao.WordGameStatisticDao
import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic
import rs.ac.metropolitan.cs330_pz.domain.repository.WordGameStatisticRepository

class WordGameStatisticRepositoryImpl(private val dao: WordGameStatisticDao): WordGameStatisticRepository {
   override suspend fun insert(statistic: WordGameStatistic) {
        dao.insert(statistic)
    }

    override suspend fun getStatisticsForUser(userId: Int): List<WordGameStatistic> {
        return dao.getStatisticsForUser(userId)
    }
}