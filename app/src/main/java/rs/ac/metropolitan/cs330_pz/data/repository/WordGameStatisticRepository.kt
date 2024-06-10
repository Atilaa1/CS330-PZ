package rs.ac.metropolitan.cs330_pz.data.repository

import rs.ac.metropolitan.cs330_pz.data.db.dao.WordGameStatisticDao
import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic

class WordGameStatisticRepository(private val dao: WordGameStatisticDao) {
    suspend fun insert(statistic: WordGameStatistic) {
        dao.insert(statistic)
    }

    suspend fun getStatisticsForUser(userId: Int): List<WordGameStatistic> {
        return dao.getStatisticsForUser(userId)
    }
}